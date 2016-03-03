using Compiler6083Project.CompilerComponents.ParserASTClasses;
using Compiler6083Project.CompilerComponents.ParserASTClasses.ExpressionObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.CompilerComponents
{
    class Parser
    {
        public Lexer Scanner { get; set; }
        public string NextTokenText { get { return Scanner.LookAheadToken.Text; } }
        public Token.Types NextTokenType { get { return Scanner.LookAheadToken.Type; } }

        public Parser(string filePath)
        {
            Scanner = new Lexer(filePath);
        }

        public ProgramAST GenerateAST()
        {
            ProgramAST result = new ProgramAST();

            // Program signature
            Scanner.ConsumeKeywordToken(Token.Types.PROGRAM);
            result.ProgramName = Scanner.ConsumeIdentifierToken().Text; // Name
            Scanner.ConsumeKeywordToken(Token.Types.IS);
            // Program body
            result.DeclarationList = ParseDeclarationList();
            Scanner.ConsumeKeywordToken(Token.Types.BEGIN);
            result.StatementList = ParseStatementList();
            Scanner.ConsumeKeywordToken(Token.Types.END);
            Scanner.ConsumeKeywordToken(Token.Types.PROGRAM);

            return result;
        }

        private List<Statement> ParseStatementList()
        {
            List<Statement> statementList = new List<Statement>();

            while (true)
            {
                Statement statement = ParseStatement();
                if (statement == null) // Statement not found
                {
                    break;
                }
                else
                {
                    statementList.Add(statement); // Store statement
                    Scanner.ConsumeOperatorToken(Token.Types.SEMICOLON);
                }
            }

            return statementList;
        }

        /// <summary>
        /// Method used by the parser to capture a statement AST node from the Lexer token stream.
        /// </summary>
        /// <returns>Parsed statement. If statement not found, returns null.</returns>
        private Statement ParseStatement()
        {
            Statement result = null;

            if (NextTokenType == Token.Types.IDENTIFIER) // Assignment statement or procedure call
            {
                Token idToken = Scanner.ConsumeIdentifierToken(); // Capture identifier
                if (NextTokenType == Token.Types.OPEN_PARENTHESIS) // Procedure call
                {
                    ProcedureCall returnProcCall = new ProcedureCall(idToken.CodeLineNumber, idToken.CodeColumnNumber, idToken.CodeCharacterIndex);
                    returnProcCall.ArgumentList = new List<ExpressionClass>();
                    Scanner.ConsumeOperatorToken(Token.Types.OPEN_PARENTHESIS);
                    if (NextTokenType == Token.Types.CLOSE_PARENTHESIS) // No arguments
                    {
                        Scanner.ConsumeOperatorToken(Token.Types.CLOSE_PARENTHESIS);
                    }
                    else
                    {
                        // If next token not close parenthesis, expect an argument
                        returnProcCall.ArgumentList.Add(ParseExpression());
                        while (NextTokenType == Token.Types.COMMA) // More than one argument
                        {
                            Scanner.ConsumeOperatorToken(Token.Types.COMMA);
                            returnProcCall.ArgumentList.Add(ParseExpression());
                        }
                    }

                    result = returnProcCall; // Secure result
                }
                else if (NextTokenType == Token.Types.OPEN_BRACKET || NextTokenType == Token.Types.ASSIGN) // Assignment statement
                {
                    result = ParseAssignmentStatement(ParseMemoryLocation(idToken)); // Secure result
                }
                else // Syntax error
                    ErrorHandler.SyntaxError(Scanner, "Expect either '(' for procedure call or ':=' (or '[' if assigning an array element) for assignment statement.");
            }
            else if (NextTokenType == Token.Types.IF) // If statement
            {
                Token initToken = Scanner.ConsumeKeywordToken(Token.Types.IF);
                IfStatement returnIf = new IfStatement(initToken.CodeLineNumber, initToken.CodeColumnNumber, initToken.CodeCharacterIndex);
                Scanner.ConsumeOperatorToken(Token.Types.OPEN_PARENTHESIS);
                returnIf.ConditionalExpression = ParseExpression();
                Scanner.ConsumeOperatorToken(Token.Types.CLOSE_PARENTHESIS);
                Scanner.ConsumeKeywordToken(Token.Types.THEN);
                returnIf.StatementList = ParseStatementList();
                if (returnIf.StatementList.Count < 1) // No statements found (at least one required)
                {
                    ErrorHandler.SyntaxError(Scanner, "If statement body must have at least one statement.");
                }
                else if (NextTokenType == Token.Types.ELSE) // Has else statement (optional)
                {
                    Scanner.ConsumeKeywordToken(Token.Types.ELSE);
                    returnIf.ElseStatementList = ParseStatementList();
                    if (returnIf.ElseStatementList.Count < 1) // No statements found (at least one required)
                    {
                        ErrorHandler.SyntaxError(Scanner, "Else statement body must have at least one statement.");
                    }
                    Scanner.ConsumeKeywordToken(Token.Types.END);
                    Scanner.ConsumeKeywordToken(Token.Types.IF);

                    result = returnIf; // Secure result
                }
            }
            else if (NextTokenType == Token.Types.FOR) // Loop statement
            {
                Token initToken = Scanner.ConsumeKeywordToken(Token.Types.FOR);
                LoopStatement returnLoop = new LoopStatement(initToken.CodeLineNumber, initToken.CodeColumnNumber, initToken.CodeCharacterIndex);
                Scanner.ConsumeOperatorToken(Token.Types.OPEN_PARENTHESIS);
                returnLoop.Initialization = ParseAssignmentStatement(ParseMemoryLocation(Scanner.ConsumeIdentifierToken()));
                Scanner.ConsumeOperatorToken(Token.Types.SEMICOLON);
                returnLoop.AfterThought = ParseExpression(); // Afterthought
                Scanner.ConsumeOperatorToken(Token.Types.CLOSE_PARENTHESIS);
                returnLoop.StatementList = ParseStatementList(); // Loop body
                Scanner.ConsumeKeywordToken(Token.Types.END);
                Scanner.ConsumeKeywordToken(Token.Types.FOR);

                result = returnLoop; // Secure result
            }
            else if (NextTokenType == Token.Types.RETURN) // Return statement
            {
                Token initToken = Scanner.ConsumeToken();
                // Secure result
                result = new ReturnStatement(initToken.CodeLineNumber, initToken.CodeColumnNumber, initToken.CodeCharacterIndex);
            }

            return result;
        }

        private ExpressionClass ParseExpression()
        {
            ExpressionClass result;

            if (NextTokenType == Token.Types.NOT) // Expression
            {
                Token notToken = Scanner.ConsumeKeywordToken(Token.Types.NOT);
                Expression returnExpr = new Expression(notToken.CodeLineNumber, notToken.CodeColumnNumber, notToken.CodeCharacterIndex);
                returnExpr.Operator = Lexer.Operators.NOT;
                returnExpr.OperandA = ParseFactor();

                result = returnExpr;
            }
            else // Factor
            {
                result = ParseFactor();
            }

            return result;
        }

        private ExpressionClass ParseFactor()
        {
            ExpressionClass result;

            if (NextTokenType == Token.Types.OPEN_PARENTHESIS) // Expression in parentheses
            {
                Scanner.ConsumeOperatorToken(Token.Types.OPEN_PARENTHESIS);
                result = ParseExpression();
                Scanner.ConsumeOperatorToken(Token.Types.CLOSE_PARENTHESIS);
            }
            else if (NextTokenType == Token.Types.STRING_VALUE) // String value
            {
                Token strToken = Scanner.ConsumeToken();
                StringFactor returnStrFactor = new StringFactor(strToken.CodeLineNumber, strToken.CodeColumnNumber, strToken.CodeCharacterIndex);
                returnStrFactor.Value = strToken.Text;

                result = returnStrFactor;
            }
            else if (NextTokenType == Token.Types.TRUE) // True bool value
            {
                Token trueToken = Scanner.ConsumeKeywordToken(Token.Types.TRUE);
                BoolFactor returnBoolFactor = new BoolFactor(trueToken.CodeLineNumber, trueToken.CodeColumnNumber, trueToken.CodeCharacterIndex);
                returnBoolFactor.Value = true;

                result = returnBoolFactor;
            }
            else if (NextTokenType == Token.Types.FALSE) // False bool value
            {
                Token falseToken = Scanner.ConsumeKeywordToken(Token.Types.FALSE);
                BoolFactor returnBoolFactor = new BoolFactor(falseToken.CodeLineNumber, falseToken.CodeColumnNumber, falseToken.CodeCharacterIndex);
                returnBoolFactor.Value = false;

                result = returnBoolFactor;
            }
            else
            {
                Token negToken = null;
                if (NextTokenType == Token.Types.SUB) // Negated variable factor or number factor
                {
                    negToken = Scanner.ConsumeKeywordToken(Token.Types.SUB);
                }

                Token initToken = null;
                if (NextTokenType == Token.Types.IDENTIFIER) // Variable factor
                {
                    Token idToken = Scanner.ConsumeIdentifierToken();
                    if (negToken != null)
                        initToken = negToken; // Negated, minus symbol initial token
                    else
                        initToken = idToken; // Not negated, identifier initial token

                    VariableFactor returnVarFactor = new VariableFactor(initToken.CodeLineNumber, initToken.CodeColumnNumber, initToken.CodeCharacterIndex);
                    returnVarFactor.IsNegated = negToken != null ? true : false;
                    returnVarFactor.Location = ParseMemoryLocation(idToken);

                    result = returnVarFactor;
                }
                else if (NextTokenType == Token.Types.INT_VALUE) // Integer number factor
                {

                }
                else if (NextTokenType == Token.Types.FLOAT_VALUE) // Integer number factor
                {

                }
                else {

                }
            }

            throw new NotImplementedException();
        }

        private MemoryLocation ParseMemoryLocation(Token idToken)
        {
            MemoryLocation result = new MemoryLocation(idToken.CodeLineNumber, idToken.CodeColumnNumber, idToken.CodeCharacterIndex);

            result.VariableName = idToken.Text;
            if (NextTokenType == Token.Types.OPEN_BRACKET) // Array index specified
            {
                Scanner.ConsumeOperatorToken(Token.Types.OPEN_BRACKET);
                result.ArrayIndexExpression = ParseExpression(); // Array index expression
                Scanner.ConsumeOperatorToken(Token.Types.CLOSE_BRACKET);
            }

            return result;
        }

        private AssignmentStatement ParseAssignmentStatement(MemoryLocation destination)
        {
            AssignmentStatement result = new AssignmentStatement(destination);

            Scanner.ConsumeOperatorToken(Token.Types.ASSIGN);
            result.Expression = ParseExpression();

            return result;
        }

        private List<Declaration> ParseDeclarationList()
        {
            List<Declaration> decList = new List<Declaration>();

            while (NextTokenType == Token.Types.GLOBAL || // Global keyword
                NextTokenType == Token.Types.PROCEDURE || // Procedure keyword
                Lexer.IsTypeMark(NextTokenType)) // Typemark (beginning of variable declaration)
            {
                bool isGlobal = false; // Assume not global
                if (NextTokenType == Token.Types.GLOBAL)
                {
                    Scanner.ConsumeToken(); // Skip
                    isGlobal = true;
                }

                if (NextTokenType == Token.Types.PROCEDURE) // Procedure declaration
                {
                    ProcedureDeclaration parsedProcDec = ParseProcedureDeclaration();
                    parsedProcDec.IsGlobal = isGlobal;
                    decList.Add(parsedProcDec);
                }
                else if (Lexer.IsTypeMark(NextTokenType)) // Variable declaration
                {
                    VariableDeclaration parsedVarDec = ParseVariableDeclaration();
                    parsedVarDec.IsGlobal = isGlobal;
                    decList.Add(parsedVarDec);
                }
                else if (isGlobal) // Global keyword found but no declaration followed
                    ErrorHandler.SyntaxError(Scanner, "Expected procedure or variable declaration after 'GLOBAL' keyword.");

                Scanner.ConsumeOperatorToken(Token.Types.SEMICOLON); // Declarations delimitered by semicolon
            }

            return decList;
        }

        private VariableDeclaration ParseVariableDeclaration()
        {
            VariableDeclaration result;

            // Typemark
            Token typeMarkToken = Scanner.ConsumeTypemarkToken();
            result = new VariableDeclaration(typeMarkToken.CodeLineNumber, typeMarkToken.CodeColumnNumber, typeMarkToken.CodeCharacterIndex);
            if (typeMarkToken.Type == Token.Types.BOOL)
                result.TypeMark = Lexer.TypeMarks.BOOL;
            else if (typeMarkToken.Type == Token.Types.FLOAT)
                result.TypeMark = Lexer.TypeMarks.FLOAT;
            else if (typeMarkToken.Type == Token.Types.INT)
                result.TypeMark = Lexer.TypeMarks.INT;
            else if (typeMarkToken.Type == Token.Types.STRING)
                result.TypeMark = Lexer.TypeMarks.STRING;
            else
                ErrorHandler.SyntaxError(Scanner, "Fatal error: unreachable code reached.");
            // Name
            result.VariableName = Scanner.ConsumeIdentifierToken().Text;
            // Optional array size
            if (NextTokenType == Token.Types.OPEN_BRACKET)
            {
                Scanner.ConsumeOperatorToken(Token.Types.OPEN_BRACKET);
                int arraySize = Scanner.ConsumeInt();
                if (arraySize > 0)
                    result.ArraySize = arraySize;
                else
                    ErrorHandler.SyntaxError(Scanner, "Array size must at least 1.");
                Scanner.ConsumeOperatorToken(Token.Types.CLOSE_BRACKET);
            }

            return result;
        }

        private ProcedureDeclaration ParseProcedureDeclaration()
        {
            ProcedureDeclaration result;

            // Procedure signature
            Token initToken = Scanner.ConsumeKeywordToken(Token.Types.PROCEDURE);
            result = new ProcedureDeclaration(initToken.CodeLineNumber, initToken.CodeColumnNumber, initToken.CodeCharacterIndex);
            result.ProcedureName = Scanner.ConsumeIdentifierToken().Text; // Name
            Scanner.ConsumeOperatorToken(Token.Types.OPEN_PARENTHESIS);
            result.ParameterList = ParseParameterList(); // Parameter list (optional)
            Scanner.ConsumeOperatorToken(Token.Types.CLOSE_PARENTHESIS);
            // Procedure body
            result.DeclarationList = ParseDeclarationList();
            Scanner.ConsumeKeywordToken(Token.Types.BEGIN);
            result.StatementList = ParseStatementList();
            Scanner.ConsumeKeywordToken(Token.Types.END);
            Scanner.ConsumeKeywordToken(Token.Types.PROCEDURE);

            return result;
        }

        private List<Parameter> ParseParameterList()
        {
            List<Parameter> result = new List<Parameter>();

            if (Lexer.IsTypeMark(NextTokenType)) // Beginning token of parameter syntax found
            {
                result.Add(ParseParameter()); // First parameter
                while (NextTokenType == Token.Types.COMMA) // Comma delimitered list
                {
                    Scanner.ConsumeOperatorToken(Token.Types.COMMA); // Skip comma token
                    result.Add(ParseParameter()); // Comma found, therefore another parameter is mandatory
                }
            }

            return result;
        }

        private Parameter ParseParameter()
        {
            Parameter result;

            VariableDeclaration varDec = ParseVariableDeclaration();
            Parameter.ArgType argType = Parameter.ArgType.ERROR; // Assume parameter type keyword not found
            if (NextTokenType == Token.Types.IN)
                argType = Parameter.ArgType.IN; // IN keyword found
            else if (NextTokenType == Token.Types.OUT)
                argType = Parameter.ArgType.OUT; // OUT keyword found
            if (argType == Parameter.ArgType.ERROR) // Syntax error: parameter type not found
            {
                ErrorHandler.SyntaxError(Scanner, "Expected 'IN' or 'OUT'.");
                // Unreachable code
                throw new NotImplementedException();
            }
            else // Parameter type found
            {
                Token initToken = Scanner.ConsumeToken(); // Skip in/out keyword; info already stored
                result = new Parameter(initToken.CodeLineNumber, initToken.CodeColumnNumber, initToken.CodeCharacterIndex, varDec, argType);

                return result;
            }
        }
    }
}

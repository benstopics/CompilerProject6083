using Compiler6083Project.ParserClasses;
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

            // TODO: Work on statement list parsing method

            return statementList;
        }

        public List<Declaration> ParseDeclarationList()
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
            VariableDeclaration result = new VariableDeclaration();

            // Typemark
            Token typeMarkToken = Scanner.ConsumeTypemarkToken();
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
            ProcedureDeclaration result = new ProcedureDeclaration();

            // Procedure signature
            Scanner.ConsumeKeywordToken(Token.Types.PROCEDURE);
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

            if (Lexer.IsTypeMark(NextTokenType)) // Optional first parameter
            {
                if (Lexer.IsTypeMark(NextTokenType)) // Beginning token of parameter syntax found
                {
                    result.Add(ParseParameter()); // First parameter
                    while (NextTokenType == Token.Types.COMMA) // Comma delimitered list
                    {
                        Scanner.ConsumeOperatorToken(Token.Types.COMMA); // Skip comma token
                        result.Add(ParseParameter()); // Comma found, therefore another parameter is mandatory
                    }
                }
            }

            return result;
        }

        private Parameter ParseParameter()
        {
            Parameter result = new Parameter(ParseVariableDeclaration(),
                    NextTokenType == Token.Types.IN ? Parameter.ArgType.IN // IN keyword found
                    : NextTokenType == Token.Types.OUT ? Parameter.ArgType.OUT // OUT keyword found
                    : Parameter.ArgType.ERROR); // Syntax error: parameter type not found
            if (result.Type == Parameter.ArgType.ERROR) // Parameter type not found
            {
                ErrorHandler.SyntaxError(Scanner, "Expected 'IN' or 'OUT'.");
                // Unreachable code
                throw new NotImplementedException();
            }
            else // Parameter type found
            {
                Scanner.ConsumeToken(); // Skip in/out keyword; info already stored
                return result;
            }
        }
    }
}

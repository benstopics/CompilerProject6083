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
            // TODO: Continue coding variable declaration parsing method

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
            throw new NotImplementedException();
        }
    }
}

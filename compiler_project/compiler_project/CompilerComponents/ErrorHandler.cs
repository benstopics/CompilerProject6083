using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project
{
    class ErrorHandler
    {
        private static bool exitOnError = true;
        public static bool ExitOnError
        {
            get
            {
                return exitOnError;
            }
            set
            {
                exitOnError = value;
            }
        }

        public static void OutputError(string msg)
        {
            Console.WriteLine(msg);
            if (ExitOnError)
                Environment.Exit(-1);
        }

        public static void LexerError(Lexer lex, string msg)
        {
            OutputError(lex.CurrentLineNumber + ":" + lex.CurrentColumnNumber + " > " + msg);
        }

        public static void ParserError(Lexer lex, string msg)
        {
            OutputError(lex.LookAheadToken.CodeLineNumber + ":" + lex.LookAheadToken.CodeColumnNumber + " > " + msg);
        }

        public static void MissingRequiredKeywordError(Lexer lex, Token.Types keyword)
        {
            ParserError(lex, "Keyword '" + keyword.ToString() + "' required.");
        }

        public static void IdentifierExpected(Lexer lex)
        {
            ParserError(lex, "Identifier expected.");
        }

        public static void FileNotFoundError(string filePath)
        {
            OutputError("File path \"" + filePath + "\" does not exist.");
        }
    }
}

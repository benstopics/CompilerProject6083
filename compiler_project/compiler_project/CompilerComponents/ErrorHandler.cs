using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project
{
    class ErrorHandler
    {
        public static void SyntaxError(Lexer lex, string msg)
        {
            Console.WriteLine(lex.CurrentLineNumber + ":" + lex.CurrentColumnNumber + " > " + msg);
            Environment.Exit(-1);
        }

        public static void MissingRequiredKeywordError(Lexer lex, Token.Types keyword)
        {
            SyntaxError(lex, "Keyword '" + keyword.ToString() + "' required.");
        }

        public static void IdentifierExpected(Lexer lex)
        {
            SyntaxError(lex, "Identifier expected.");
        }
    }
}

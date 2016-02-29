using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project
{
    class ErrorHandler
    {
        public static void PrintError(Lexer lex, string msg)
        {
            Console.WriteLine(lex.CurrentLineNumber + ":" + lex.CurrentColumnNumber + " > " + msg + " ( LookAhead: " + (lex.LookAheadChar == '\n' ? "\\n" : "" + lex.LookAheadChar) + " )");
            Environment.Exit(-1);
        }
    }
}

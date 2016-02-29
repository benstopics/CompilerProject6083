using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project
{
    class Program
    {
        static void Main(string[] args)
        {
            Lexer lexer = new Lexer("test.src");
            while (lexer.LookAheadToken.Type != Token.Types.EOF && lexer.LookAheadToken.Type != Token.Types.ERROR)
            {
                lexer.ConsumeToken();
            }
        }
    }
}

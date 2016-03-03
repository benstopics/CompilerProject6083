using Compiler6083Project.CompilerComponents;
using Compiler6083Project.CompilerComponents.ParserASTClasses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.TestClasses
{
    class TestParser
    {
        public static void ParseFactor()
        {
            ErrorHandler.ExitOnError = false; // Disable exit on error

            Parser parser = new Parser(@"testsrcs\parser\parsefactor.src"); // Load test source code

            while (parser.NextTokenType != Token.Types.EOF)
            {
                parser.ParseFactor();
            }
        }
    }
}

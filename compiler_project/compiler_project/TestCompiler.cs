using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project
{
    class CompilerTester
    {
        public static string[] LoadTestCodeCases(string filePath)
        {
            return Lexer.GetFileText(filePath).Split(new string[] { "Test Case:" }, StringSplitOptions.RemoveEmptyEntries);
        }
    }
}

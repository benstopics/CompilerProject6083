using Compiler6083Project.CompilerComponents;
using Compiler6083Project.CompilerComponents.ParserASTClasses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.TestClasses
{
    class ParserTester : CompilerTester
    {
        public static void ParseFactor()
        {
            ErrorHandler.ExitOnError = false; // Disable exit on error

            string[] testCodeCases = LoadTestCodeCases(@"testsrcs\parser\parsefactor-success.src");

            for (int i = 0; i < testCodeCases.Length; i++)
            {
                Console.WriteLine("Success Test Case #" + (i + 1) + ":");

                // Load test case
                Parser parser = new Parser();
                Lexer lex = new Lexer();
                lex.ProgramText = testCodeCases[i];
                parser.Scanner = lex;

                // Perform test
                parser.ParseFactor();

                Console.WriteLine();
            }

            testCodeCases = LoadTestCodeCases(@"testsrcs\parser\parsefactor-fail.src");

            for (int i = 0; i < testCodeCases.Length; i++)
            {
                Console.WriteLine("Fail Test Case #" + (i + 1) + ":");

                // Load test case
                Parser parser = new Parser();
                Lexer lex = new Lexer();
                lex.ProgramText = testCodeCases[i];
                parser.Scanner = lex;

                // Perform test
                parser.ParseFactor();

                Console.WriteLine();
            }
        }
    }
}

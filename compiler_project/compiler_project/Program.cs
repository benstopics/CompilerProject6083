using Compiler6083Project.CompilerComponents;
using Compiler6083Project.CompilerComponents.ParserASTClasses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project
{
    class Program
    {
        static void Main(string[] args)
        {
            Parser parser = new Parser("test.src");
            ProgramAST ast = parser.GenerateAST();
            Console.WriteLine(ast.ProgramName);
        }
    }
}

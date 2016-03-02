using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses
{
    class ReturnStatement : Statement
    {
        public ReturnStatement (int lineNum, int colNum)
            : base(lineNum, colNum)
        {

        }
    }
}

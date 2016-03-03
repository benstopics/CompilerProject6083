using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses
{
    abstract class ExpressionClass : ParserASTNode
    {
        public ExpressionClass (int lineNum, int colNum, int charIndex)
            : base(lineNum, colNum, charIndex)
        {

        }
    }
}

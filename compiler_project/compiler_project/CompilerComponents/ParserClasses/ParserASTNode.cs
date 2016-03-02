using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses
{
    class ParserASTNode
    {
        private int lineNum;
        private int colNum;
        public int CodeLineNumber { get { return lineNum; } }
        public int CodeColumnNumber { get { return colNum; } }

        public ParserASTNode(int lineNum, int colNum)
        {
            this.lineNum = lineNum;
            this.colNum = colNum;
        }
    }
}

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
        private int charIndex;
        public int CodeLineNumber { get { return lineNum; } }
        public int CodeColumnNumber { get { return colNum; } }
        public int CodeCharacterIndex { get { return charIndex; } }

        public ParserASTNode(int lineNum, int colNum, int charIndex)
        {
            this.lineNum = lineNum;
            this.colNum = colNum;
            this.charIndex = charIndex;
        }
    }
}

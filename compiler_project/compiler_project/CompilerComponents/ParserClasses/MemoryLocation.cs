using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses
{
    class MemoryLocation : ParserASTNode
    {
        public string VariableName { get; set; }
        private int arrayIndex = -1; // Not array by default
        public int ArrayIndex { get { return arrayIndex; } set { arrayIndex = value; } }
        public bool IsArrayElement
        {
            get
            {
                if (arrayIndex >= 0)
                    return true;
                else
                    return false;
            }
        }

        public MemoryLocation(int lineNum, int colNum, int charIndex)
            : base(lineNum, colNum, charIndex)
        {

        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses
{
    class MemoryLocation : ParserASTNode
    {
        public string VariableName { get; set; }
        private ExpressionClass arrayIndexExpr = null;
        public ExpressionClass ArrayIndexExpression
        {
            get
            {
                return arrayIndexExpr;
            }
            set
            {
                arrayIndexExpr = value;
            }
        }
        public bool IsArrayLocation { get { return ArrayIndexExpression != null; } }

        public MemoryLocation(int lineNum, int colNum, int charIndex)
            : base(lineNum, colNum, charIndex)
        {

        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses
{
    abstract class ExpressionClass : ParserASTNode
    {
        private Lexer.Operators op = Lexer.Operators.NONE;
        public Lexer.Operators Operator { get; set; }
        public ExpressionClass OperandA { get; set; }
        public ExpressionClass OperandB { get; set; }

        public ExpressionClass (int lineNum, int colNum, int charIndex)
            : base(lineNum, colNum, charIndex)
        {

        }
    }
}

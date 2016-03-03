using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses
{
    class Expression : ExpressionClass
    {
        private Lexer.Operators op = Lexer.Operators.NONE;
        public Lexer.Operators Operator { get; set; }
        public ExpressionClass OperandA { get; set; }
        public ExpressionClass OperandB { get; set; }

        public Expression(int lineNum, int colNum, int charIndex)
            : base(lineNum, colNum, charIndex)
        {
            Operator = Lexer.Operators.NONE; // Assume only a single operand
        }
    }
}

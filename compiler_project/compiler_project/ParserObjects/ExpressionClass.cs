using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace compiler_project.ParserObjects
{
    abstract class ExpressionClass
    {
        private Lexer.Operators op = Lexer.Operators.NONE;
        public Lexer.Operators Operator { get; set; }
        public ExpressionClass OperandA { get; set; }
        public ExpressionClass OperandB { get; set; }
    }
}

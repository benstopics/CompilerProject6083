using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.ParserClasses
{
    class Expression : ExpressionClass
    {
        public Lexer.Operators Operator
        {
            get
            {
                return base.Operator;
            }
            set
            {
                if (value == Lexer.Operators.AND ||
                    value == Lexer.Operators.OR ||
                    value == Lexer.Operators.NOT ||
                    value == Lexer.Operators.ADD ||
                    value == Lexer.Operators.SUB ||
                    value == Lexer.Operators.LESSTHAN ||
                    value == Lexer.Operators.LESSTHAN_EQUAL ||
                    value == Lexer.Operators.GREATERTHAN ||
                    value == Lexer.Operators.GREATERTHAN_EQUAL ||
                    value == Lexer.Operators.EQUAL ||
                    value == Lexer.Operators.NOT_EQUAL ||
                    value == Lexer.Operators.MUL ||
                    value == Lexer.Operators.DIV ||
                    value == Lexer.Operators.NONE)
                    base.Operator = value;
            }
        }
    }
}

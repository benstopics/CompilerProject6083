using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.ParserClasses.ExpressionObjects
{
    abstract class NumberFactor : ExpressionClass
    {
        public bool IsNegated { get; set; }
        public float Value { get; set; }
    }
}

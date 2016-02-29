using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project.ParserObjects.ExpressionObjects
{
    abstract class NumberFactor : ExpressionClass
    {
        public bool IsNegated { get; set; }
        public float Value { get; set; }
    }
}

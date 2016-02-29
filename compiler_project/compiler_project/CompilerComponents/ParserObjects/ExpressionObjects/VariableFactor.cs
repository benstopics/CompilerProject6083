using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.ParserClasses.ExpressionObjects
{
    class VariableFactor : ExpressionClass
    {
        public bool IsNegated { get; set; }
        public MemoryLocation Location { get; set; }
    }
}

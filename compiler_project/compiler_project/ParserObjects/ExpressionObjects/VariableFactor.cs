using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project.ParserObjects.ExpressionObjects
{
    class VariableFactor : ExpressionClass
    {
        public bool IsNegated { get; set; }
        public MemoryLocation Location { get; set; }
    }
}

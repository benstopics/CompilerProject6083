using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project.ParserObjects
{
    class AssignmentStatement : Statement
    {
        public MemoryLocation Destination { get; set; }
        public ExpressionClass Expression { get; set; }
    }
}

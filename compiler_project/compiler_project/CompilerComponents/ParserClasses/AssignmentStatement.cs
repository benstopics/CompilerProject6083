using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses
{
    class AssignmentStatement : Statement
    {
        public MemoryLocation Destination { get; set; }
        public ExpressionClass Expression { get; set; }
    }
}

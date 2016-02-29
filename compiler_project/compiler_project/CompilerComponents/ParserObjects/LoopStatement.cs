using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.ParserClasses
{
    class LoopStatement : Statement
    {
        public AssignmentStatement Initialization { get; set; }
        public ExpressionClass AfterThought { get; set; }
        public List<Statement> StatementList { get; set; }
    }
}

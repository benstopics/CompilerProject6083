using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project.ParserObjects
{
    class LoopStatement : Statement
    {
        public AssignmentStatement Initialization { get; set; }
        public ExpressionClass AfterThought { get; set; }
        public List<Statement> StatementList { get; set; }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project.ParserObjects
{
    class IfStatement : Statement
    {
        public ExpressionClass ConditionalExpression { get; set; }
        public List<Statement> StatementList { get; set; }
        public List<Statement> ElseStatementList { get; set; }
        public bool HasElse { get { return ElseStatementList.Count > 0; } }
    }
}

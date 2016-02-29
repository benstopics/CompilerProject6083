using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project.ParserObjects
{
    class ProcedureCall : Statement
    {
        public string ProcedureName { get; set; }
        public List<ExpressionClass> ArgumentList { get; set; }
    }
}

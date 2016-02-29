using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.ParserClasses
{
    class ProcedureCall : Statement
    {
        public string ProcedureName { get; set; }
        public List<ExpressionClass> ArgumentList { get; set; }
    }
}

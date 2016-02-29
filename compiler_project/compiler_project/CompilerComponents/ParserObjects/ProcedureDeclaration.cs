using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.ParserClasses
{
    class ProcedureDeclaration : Declaration
    {
        public string ProcedureName { get; set; }
        public List<Parameter> ParameterList { get; set; }
        public List<Declaration> DeclarationList { get; set; }
        public List<Statement> StatementList { get; set; }

        public bool HasParameterList { get { return ParameterList.Count > 0; } }
    }
}

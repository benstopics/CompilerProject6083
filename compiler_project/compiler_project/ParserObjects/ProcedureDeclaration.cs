using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project.ParserObjects
{
    class ProcedureDeclaration : Declaration
    {
        public string ProcedureName { get; set; }
        public List<Parameter> ParameterList { get; set; }
        public List<Declaration> DeclarationList { get; set; }
        public List<Statement> StatementList { get; set; }
    }
}

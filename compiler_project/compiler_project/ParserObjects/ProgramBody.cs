using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace compiler_project.ParserObjects
{
    class ProgramBody
    {
        public List<Declaration> DeclarationList { get; set; }
        public List<Statement> StatementList { get; set; }
    }
}

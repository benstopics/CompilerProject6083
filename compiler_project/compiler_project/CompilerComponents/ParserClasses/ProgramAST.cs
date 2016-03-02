using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses
{
    class ProgramAST
    {
        public string ProgramName { get; set; }
        public List<Declaration> DeclarationList { get; set; }
        public List<Statement> StatementList { get; set; }
    }
}

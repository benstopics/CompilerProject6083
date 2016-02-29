using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project.ParserObjects
{
    class ParsedProgram
    {
        public string ProgramName { get; set; }
        public ProgramBody ProgramBody { get; set; }
    }
}

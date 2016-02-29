using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace compiler_project.ParserObjects
{
    class Parameter : VariableDeclaration
    {
        public static enum ArgType
        {
            IN, OUT
        }

        public ArgType Type { get; set; }
    }
}

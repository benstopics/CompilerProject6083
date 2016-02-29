using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Compiler6083Project.ParserClasses
{
    class Parameter : VariableDeclaration
    {
        public enum ArgType
        {
            IN, OUT
        }

        public ArgType Type { get; set; }
    }
}

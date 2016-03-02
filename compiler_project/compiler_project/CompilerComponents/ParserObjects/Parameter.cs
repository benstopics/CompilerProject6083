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
            IN, OUT,
            ERROR
        }

        public ArgType Type { get; set; }

        public Parameter(VariableDeclaration varDec, ArgType argType)
        {
            this.TypeMark = varDec.TypeMark;
            this.VariableName = varDec.VariableName;
            this.ArraySize = varDec.ArraySize;
            Type = argType;
        }
    }
}

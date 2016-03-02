using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses
{
    class Parameter : VariableDeclaration
    {
        public enum ArgType
        {
            IN, OUT,
            ERROR
        }

        public ArgType Type { get; set; }

        public Parameter(int lineNum, int colNum, int charIndex, VariableDeclaration varDec, ArgType argType)
            : base (lineNum, colNum, charIndex)
        {
            this.TypeMark = varDec.TypeMark;
            this.VariableName = varDec.VariableName;
            this.ArraySize = varDec.ArraySize;
            Type = argType;
        }
    }
}

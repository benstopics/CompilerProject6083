using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.ParserClasses
{
    class VariableDeclaration : Declaration
    {
        public Lexer.TypeMarks TypeMark { get; set; }
        public string VariableName { get; set; }
        private int arraySize = -1; // Not array by default
        public int ArraySize { get { return arraySize; } set { arraySize = value; } }
        public bool IsArray
        {
            get
            {
                if (arraySize >= 0)
                    return true;
                else
                    return false;
            }
        }
    }
}

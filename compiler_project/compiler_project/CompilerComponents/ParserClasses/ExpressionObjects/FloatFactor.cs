using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses.ExpressionObjects
{
    class FloatFactor : NumberFactor
    {
        public float Value { get; set; }
    }
}

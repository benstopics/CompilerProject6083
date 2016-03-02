﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project.CompilerComponents.ParserASTClasses.ExpressionObjects
{
    class BoolFactor : ExpressionClass
    {
        public bool Value { get; set; }

        public BoolFactor(int lineNum, int colNum, int charIndex)
            : base(lineNum, colNum, charIndex)
        {

        }
    }
}

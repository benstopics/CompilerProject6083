﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project.ParserObjects.ExpressionObjects
{
    class IntegerFactor : NumberFactor
    {
        public int Value { get; set; }
    }
}

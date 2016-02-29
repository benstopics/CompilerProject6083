using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project
{
    class Token
    {
        public enum Types { ERROR, EOF, IDENTIFIER, STRING, INT, FLOAT, BOOL, KEYWORD_STRING, KEYWORD_INT, KEYWORD_FLOAT, KEYWORD_BOOL, CASE, FOR, AND, OR, GLOBAL, NOT, OUT, PROCEDURE, THEN, RETURN, ELSE, END }
        public static Token ErrorToken = new Token("", Types.ERROR);
        public static Token EOFToken = new Token("", Types.EOF);
        
        public string Text { get; set; }
        public Types Type { get; set; }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="text"></param>
        /// <param name="type"></param>
        public Token(string text, Types type)
        {
            Text = text;
            Type = type;
        }
    }
}

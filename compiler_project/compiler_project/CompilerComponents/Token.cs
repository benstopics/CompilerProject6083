using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Compiler6083Project
{
    class Token
    {
        public enum Types
        {
            ERROR, EOF, IDENTIFIER, STRING_VALUE, INT_VALUE, FLOAT_VALUE, BOOL_VALUE, STRING, INT, FLOAT,
            BOOL, CASE, FOR, AND, OR, GLOBAL, NOT, OUT, PROCEDURE, THEN, RETURN, ELSE, END, ADD,
            SUB, MUL, DIV, COLON, SEMICOLON, COMMA, OPEN_PARENTHESIS, CLOSE_PARENTHESIS, OPEN_BRACE,
            CLOSE_BRACE, LESSTHAN_EQUAL, LESSTHAN, GREATERTHAN_EQUAL, GREATERTHAN, EQUAL, ASSIGN, NOT_EQUAL,
            TRUE, FALSE, IN, PROGRAM, IS, BEGIN, IF,
            OPEN_BRACKET,
            CLOSE_BRACKET
        }
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

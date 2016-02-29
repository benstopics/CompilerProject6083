using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace compiler_project
{
    class Token
    {
        public static enum Types
        {
            ERROR, EOF, IDENTIFIER, STRING, INT, FLOAT, BOOL, KEYWORD_STRING, KEYWORD_INT, KEYWORD_FLOAT,
            KEYWORD_BOOL, CASE, FOR, AND, OR, GLOBAL, NOT, OUT, PROCEDURE, THEN, RETURN, ELSE, END, ADD,
            SUB, MUL, DIV, COLON, SEMICOLON, COMMA, OPEN_PARENTHESIS, CLOSE_PARENTHESIS, OPEN_BRACE,
            CLOSE_BRACE, LESSTHAN_EQUAL, LESSTHAN, GREATERTHAN_EQUAL, GREATERTHAN, EQUAL, ASSIGN, NOT_EQUAL,
            TRUE, FALSE, IN, PROGRAM, IS, BEGIN, IF
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

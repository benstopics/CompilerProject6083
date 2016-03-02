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
        public static Token ErrorToken = new Token(0, 0, 0, "", Types.ERROR);
        public static Token EOFToken = new Token(0, 0, 0, "", Types.EOF);
        
        public string Text { get; set; }
        public Types Type { get; set; }

        private int lineNum;
        private int colNum;
        private int charIndex;
        public int CodeLineNumber { get { return lineNum; } }
        public int CodeColumnNumber { get { return colNum; } }
        public int CodeCharacterIndex { get { return charIndex; } }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="text"></param>
        /// <param name="type"></param>
        public Token(int lineNum, int colNum, int charIndex, string text, Types type)
        {
            Text = text;
            Type = type;
            this.lineNum = lineNum;
            this.colNum = colNum;
            this.charIndex = charIndex;
        }
    }
}

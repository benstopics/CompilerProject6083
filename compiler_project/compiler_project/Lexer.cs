using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace compiler_project
{
    class Lexer
    {
        public enum Operators
        {
            AND, OR, NOT, ADD, SUB, MUL, DIV, LESSTHAN_EQUAL, LESSTHAN, GREATERTHAN_EQUAL,
            GREATERTHAN, EQUAL, ASSIGN, NOT_EQUAL, BITWISE_AND, BITWISE_OR
        }
        public enum DataTypes
        {
            STRING, INT, FLOAT, BOOL
        }

        private int lineNum = 1; // Start on first line
        private int colNum = 1; // Start on first column
        public int CurrentLineNumber { get { return lineNum; } }
        public int CurrentColumnNumber { get { return colNum; } }
        public string ProgramText { get; set; }
        public int CurrentCharacterIndex { get; set; }
        public char LookAheadChar
        {
            get
            {
                char result = '\0'; // Assuming there is no more text to read by default

                if (CurrentCharacterIndex != ProgramText.Length) // More text to read
                    result = ProgramText[CurrentCharacterIndex]; // Append next character

                return result;
            }
        }
        private Token lookaheadToken;
        public Token LookAheadToken
        {
            get
            {
                return lookaheadToken;
            }
        }

        /// <summary>
        /// Also known as a scanner, the lexer is used to capture valid tokens in the program text sequentially.
        /// </summary>
        /// <param name="filePath">The file path of the program text file to open for reading.</param>
        public Lexer(string filePath)
        {
            CurrentCharacterIndex = 0;

            if (!File.Exists(filePath))
            {
                // Custom exception
                Console.WriteLine("File not found");
            }
            else
            {
                // Read all lines, then join back to single string. Removes \r's
                ProgramText = string.Join("\n", System.IO.File.ReadAllLines(filePath));
                ConsumeToken(); // Load first token into lookahead
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public Token ConsumeToken()
        {
            Token result = new Token("", Token.Types.EOF); // Assume EOF 

            if (LookAheadChar != '\0') // More text to read
            {
                // Consume whitespace
                while (LookAheadChar == '\n' || LookAheadChar == ' ')
                {
                    ConsumeChar();
                }

                if (Char.IsLetter(LookAheadChar)) // Identifier or keyword
                {
                    result.Text += ConsumeChar(); // Append
                    while (Char.IsLetterOrDigit(LookAheadChar) || LookAheadChar == '_') // If next character is a letter, digit, or underscore
                        result.Text += ConsumeChar(); // Append

                    result.Text = result.Text.ToLower(); // Case insensitivity

                    switch (result.Text) // Check if keyword or identifier
                    {
                        case "string":
                            result.Type = Token.Types.KEYWORD_STRING;
                            break;
                        case "case":
                            result.Type = Token.Types.CASE;
                            break;
                        case "int":
                            result.Type = Token.Types.KEYWORD_INT;
                            break;
                        case "for":
                            result.Type = Token.Types.FOR;
                            break;
                        case "bool":
                            result.Type = Token.Types.KEYWORD_BOOL;
                            break;
                        case "true":
                            result.Type = Token.Types.TRUE;
                            break;
                        case "false":
                            result.Type = Token.Types.FALSE;
                            break;
                        case "and":
                            result.Type = Token.Types.AND;
                            break;
                        case "float":
                            result.Type = Token.Types.KEYWORD_FLOAT;
                            break;
                        case "or":
                            result.Type = Token.Types.OR;
                            break;
                        case "global":
                            result.Type = Token.Types.GLOBAL;
                            break;
                        case "not":
                            result.Type = Token.Types.NOT;
                            break;
                        case "in":
                            result.Type = Token.Types.IN;
                            break;
                        case "out":
                            result.Type = Token.Types.OUT;
                            break;
                        case "procedure":
                            result.Type = Token.Types.PROCEDURE;
                            break;
                        case "then":
                            result.Type = Token.Types.THEN;
                            break;
                        case "return":
                            result.Type = Token.Types.RETURN;
                            break;
                        case "else":
                            result.Type = Token.Types.ELSE;
                            break;
                        case "end":
                            result.Type = Token.Types.END;
                            break;
                        case "program":
                            result.Type = Token.Types.PROGRAM;
                            break;
                        case "is":
                            result.Type = Token.Types.IS;
                            break;
                        case "begin":
                            result.Type = Token.Types.BEGIN;
                            break;
                        case "if":
                            result.Type = Token.Types.IF;
                            break;
                        default:
                            result.Type = Token.Types.IDENTIFIER;
                            break;
                    }
                }
                else if (Char.IsDigit(LookAheadChar)) // Number (integer or float)
                {
                    result.Text += ConsumeChar(); // Append
                    while (Char.IsDigit(LookAheadChar)) // If next character is a digit
                        result.Text += ConsumeChar(); // Append

                    if (LookAheadChar == '.') // Float
                    {
                        result.Text += ConsumeChar();
                        while (Char.IsDigit(LookAheadChar)) // If next character is a digit
                            result.Text += ConsumeChar(); // Append

                        result.Type = Token.Types.FLOAT;
                    }
                    else // Integer
                        result.Type = Token.Types.INT;
                }
                else if (LookAheadChar == '"')
                {
                    result.Text += ConsumeChar(); // Append first quote
                    while (LookAheadChar != '"') // Consume until matching quote
                    {
                        if (Char.IsLetterOrDigit(LookAheadChar) || // If valid string char
                            LookAheadChar == ' ' ||
                            LookAheadChar == '_' ||
                            LookAheadChar == ',' ||
                            LookAheadChar == ';' ||
                            LookAheadChar == ':' ||
                            LookAheadChar == '.')
                            result.Text += ConsumeChar(); // Append
                        else
                        {
                            ErrorHandler.PrintError(this, "Non-string character found.");
                        }
                    }
                    result.Text += ConsumeChar(); // Append matching quote

                    result.Type = Token.Types.STRING;
                }
                else if (LookAheadChar == '&')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.BITWISE_AND;
                }
                else if (LookAheadChar == '|')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.BITWISE_OR;
                }
                else if (LookAheadChar == '+')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.ADD;
                }
                else if (LookAheadChar == '-')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.SUB;
                }
                else if (LookAheadChar == '*')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.MUL;
                }
                else if (LookAheadChar == '/')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.DIV;
                }
                else if (LookAheadChar == ';')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.SEMICOLON;
                }
                else if (LookAheadChar == ',')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.COMMA;
                }
                else if (LookAheadChar == '(')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.OPEN_PARENTHESIS;
                }
                else if (LookAheadChar == ')')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.CLOSE_PARENTHESIS;
                }
                else if (LookAheadChar == '{')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.OPEN_BRACE;
                }
                else if (LookAheadChar == '}')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.CLOSE_BRACE;
                }
                else if (LookAheadChar == '=')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.EQUAL;
                }
                else if (LookAheadChar == '<') {
                    result.Text += ConsumeChar(); // Append
                    if (LookAheadChar == '=')
                    {
                        result.Text += ConsumeChar(); // Append
                        result.Type = Token.Types.LESSTHAN_EQUAL;
                    }
                    else
                        result.Type = Token.Types.LESSTHAN;
                }
                else if (LookAheadChar == '>') {
                    result.Text += ConsumeChar(); // Append
                    if (LookAheadChar == '=')
                    {
                        result.Text += ConsumeChar(); // Append
                        result.Type = Token.Types.GREATERTHAN_EQUAL;
                    }
                    else
                        result.Type = Token.Types.GREATERTHAN;
                }
                else if (LookAheadChar == '!') {
                    result.Text += ConsumeChar(); // Append
                    if (LookAheadChar == '=')
                    {
                        result.Text += ConsumeChar(); // Append
                        result.Type = Token.Types.NOT_EQUAL;
                    }
                    else
                        ErrorHandler.PrintError(this, "Invalid operator.");
                }
                else if (LookAheadChar == ':')
                {
                    result.Text += ConsumeChar(); // Append
                    if (LookAheadChar == '=')
                    {
                        result.Text += ConsumeChar(); // Append
                        result.Type = Token.Types.ASSIGN;
                    }
                    else
                        result.Type = Token.Types.COLON;
                }
            }
            else
                result.Type = Token.Types.EOF;

            Token oldLookahead = LookAheadToken; // Save old lookahead token
            lookaheadToken = result; // Set new lookahead token to consumed token

            Console.WriteLine(result.Type.ToString() + "\t" + result.Text);

            return oldLookahead; // Return the consumed lookahead
        }

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        private char ConsumeChar()
        {
            char result = LookAheadChar; // First get lookahead char before moving forward
            if (result == '\n')
            {
                lineNum++; // Next line
                colNum = 1; // Reset column count
            }
            else
                colNum++;
            CurrentCharacterIndex++; // Then consume character by moving index forward
            return result;
        }
    }
}

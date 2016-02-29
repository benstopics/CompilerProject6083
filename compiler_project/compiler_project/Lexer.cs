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
        private int lineNum = 1; // Start on first line
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
        public Token ConsumeToken() {
            Token result = new Token("", Token.Types.EOF); // Assume EOF 

            if (LookAheadChar != '\0') // More text to read
            {
                // Consume whitespace
                while (LookAheadChar == '\n' || LookAheadChar == ' ')
                {
                    if (LookAheadChar == '\n')
                        lineNum++;
                    ConsumeChar();
                }

                if (Char.IsLetter(LookAheadChar)) // Identifier or keyword
                {
                    result.Text += ConsumeChar(); // Append
                    while (Char.IsLetterOrDigit(LookAheadChar) || LookAheadChar == '_') // If next character is a letter, digit, or underscore
                        result.Text += ConsumeChar(); // Append

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
                    result.Text += ConsumeChar(); // Append
                    while (LookAheadChar != '"') // Consume until matching quote
                    {
                        if(Char.IsLetterOrDigit(LookAheadChar) || // If valid string char
                            LookAheadChar == ' ' ||
                            LookAheadChar == '_' ||
                            LookAheadChar == ',' ||
                            LookAheadChar == ';' ||
                            LookAheadChar == ':' ||
                            LookAheadChar == '.')
                            result.Text += ConsumeChar(); // Append
                        else
                        {

                        }
                        
                    }
                }
            }
            else
                result.Type = Token.Types.EOF;

            Token oldLookahead = LookAheadToken; // Save old lookahead token
            lookaheadToken = result; // Set new lookahead token to consumed token
            return oldLookahead; // Return the consumed lookahead
        }

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        private char ConsumeChar()
        {
            char result = LookAheadChar; // First get lookahead char before moving forward
            CurrentCharacterIndex++; // Then consume character by moving index forward
            return result;
        }
    }
}

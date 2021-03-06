﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Compiler6083Project
{
    class Lexer
    {
        public enum Operators
        {
            AND, OR, NOT, ADD, SUB, LESSTHAN, LESSTHAN_EQUAL, GREATERTHAN_EQUAL,
            GREATERTHAN, EQUAL, NOT_EQUAL, MUL, DIV, NONE
        }

        public enum TypeMarks
        {
            STRING, INT, FLOAT, BOOL
        }
        public static bool IsTypeMark(Token.Types typeMark)
        {
            if (typeMark == Token.Types.BOOL ||
                typeMark == Token.Types.FLOAT ||
                typeMark == Token.Types.INT ||
                typeMark == Token.Types.STRING)
                return true;
            else
                return false;
        }

        private int lineNum = 1; // Start on first line
        private int colNum = 1; // Start on first column
        public int CurrentLineNumber { get { return lineNum; } }
        public int CurrentColumnNumber { get { return colNum; } }
        private string programText = "";
        public string ProgramText
        {
            get { return programText; }
            set
            {
                programText = value;
                CurrentCharacterIndex = 0;
                ConsumeToken(); // Load first token into lookahead
            }
        }
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
        /// Debug constructor
        /// </summary>
        public Lexer()
        {
            
        }

        /// <summary>
        /// Also known as a scanner, the lexer is used to capture valid tokens in the program text sequentially.
        /// </summary>
        /// <param name="filePath">The file path of the program text file to open for reading.</param>
        public Lexer(string filePath)
        {
            ProgramText = GetFileText(filePath);
        }

        public static string GetFileText(string filePath)
        {
            string result = "";

            if (!File.Exists(filePath))
            {
                // Custom exception
                ErrorHandler.FileNotFoundError(filePath);
            }
            else
            {
                // Read all lines, then join back to single string. Removes \r's
                result = string.Join("\n", System.IO.File.ReadAllLines(filePath));
            }

            return result;
        }

        public Token ConsumeKeywordToken(Token.Types keyword)
        {
            if (LookAheadToken.Type == keyword)
            {
                return ConsumeToken();
            }
            else
            {
                ErrorHandler.MissingRequiredKeywordError(this, keyword);
                return null; // If ExitOnError is disabled
            }
        }

        public Token ConsumeIdentifierToken()
        {
            if (LookAheadToken.Type == Token.Types.IDENTIFIER)
                return ConsumeToken();
            else
            {
                ErrorHandler.IdentifierExpected(this);
                return null; // If ExitOnError is disabled
            }
        }

        public Token ConsumeOperatorToken(Token.Types op)
        {
            if (LookAheadToken.Type == op)
                return ConsumeToken();
            else
            {
                ErrorHandler.LexerError(this, "Missing '" + op.ToString() + "' operator.");
                return null; // If ExitOnError is disabled
            }
        }

        public Token ConsumeTypemarkToken()
        {
            if (IsTypeMark(LookAheadToken.Type))
                return ConsumeToken();
            else
            {
                ErrorHandler.LexerError(this, "Expected typemark keyword.");
                return null; // If ExitOnError is disabled
            }
        }

        public int ConsumeInt()
        {
            if (LookAheadToken.Type == Token.Types.INT_VALUE)
            {
                try
                {
                    return Convert.ToInt32(ConsumeToken().Text);
                }
                catch
                {
                    ErrorHandler.LexerError(this, "Fatal error: scanner accepted characters as integer value token but token could not be casted to an integer value.");
                    return 0; // If ExitOnError is disabled
                }
            }
            else
            {
                ErrorHandler.LexerError(this, "Expected integer value.");
                return 0; // If ExitOnError is disabled
            }
        }

        public float ConsumeFloat()
        {
            if (LookAheadToken.Type == Token.Types.FLOAT_VALUE)
            {
                try
                {
                    return (float)Convert.ToDouble(ConsumeToken().Text);
                }
                catch
                {
                    ErrorHandler.LexerError(this, "Fatal error: scanner accepted characters as float value token but token could not be casted to a float value.");
                    return 0; // If ExitOnError is disabled
                }
            }
            else
            {
                ErrorHandler.LexerError(this, "Expected float value.");
                return 0; // If ExitOnError is disabled
            }
        }

        /// <summary>
        /// Returns an expression operator if it is the next token.
        /// </summary>
        /// <returns>An expression operator if it is the next token, if not, returns operator NONE.</returns>
        public Operators ConsumeExpressionOperator()
        {
            Operators result = Operators.NONE; // Assume no expression operator found

            switch (LookAheadToken.Type)
            {
                case Token.Types.AND:
                    result = Operators.AND;
                    break;
                case Token.Types.OR:
                    result = Operators.OR;
                    break;
                case Token.Types.NOT:
                    result = Operators.NOT;
                    break;
                case Token.Types.ADD:
                    result = Operators.ADD;
                    break;
                case Token.Types.SUB:
                    result = Operators.SUB;
                    break;
                case Token.Types.LESSTHAN:
                    result = Operators.LESSTHAN;
                    break;
                case Token.Types.LESSTHAN_EQUAL:
                    result = Operators.LESSTHAN_EQUAL;
                    break;
                case Token.Types.GREATERTHAN_EQUAL:
                    result = Operators.GREATERTHAN_EQUAL;
                    break;
                case Token.Types.GREATERTHAN:
                    result = Operators.GREATERTHAN;
                    break;
                case Token.Types.EQUAL:
                    result = Operators.EQUAL;
                    break;
                case Token.Types.NOT_EQUAL:
                    result = Operators.NOT_EQUAL;
                    break;
                case Token.Types.MUL:
                    result = Operators.MUL;
                    break;
                case Token.Types.DIV:
                    result = Operators.DIV;
                    break;
                default: // No operator found
                    ErrorHandler.LexerError(this, "No expression operator found.");
                    break;
            }

            return result;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public Token ConsumeToken()
        {
            Token result;

            // Consume whitespace before doing anything
            while (LookAheadChar == '\n' || LookAheadChar == ' ')
            {
                ConsumeChar();
            }

            if (LookAheadChar != '\0') // More text to read
            {
                result = new Token(CurrentLineNumber, CurrentColumnNumber, CurrentCharacterIndex, "", Token.Types.EOF); // Assume EOF; store current position after consuming whitespace

                if (Char.IsLetter(LookAheadChar)) // Identifier or keyword
                {
                    result.Text += ConsumeChar(); // Append
                    while (Char.IsLetterOrDigit(LookAheadChar) || LookAheadChar == '_') // If next character is a letter, digit, or underscore
                        result.Text += ConsumeChar(); // Append

                    result.Text = result.Text.ToLower(); // Case insensitivity

                    switch (result.Text) // Check if keyword or identifier
                    {
                        case "string":
                            result.Type = Token.Types.STRING;
                            break;
                        case "case":
                            result.Type = Token.Types.CASE;
                            break;
                        case "int":
                            result.Type = Token.Types.INT;
                            break;
                        case "for":
                            result.Type = Token.Types.FOR;
                            break;
                        case "bool":
                            result.Type = Token.Types.BOOL;
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
                            result.Type = Token.Types.FLOAT;
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

                        result.Type = Token.Types.FLOAT_VALUE;
                    }
                    else // Integer
                        result.Type = Token.Types.INT_VALUE;
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
                            ErrorHandler.LexerError(this, "Non-string character found.");
                        }
                    }
                    result.Text += ConsumeChar(); // Append matching quote

                    result.Type = Token.Types.STRING_VALUE;
                }
                else if (LookAheadChar == '&')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.AND;
                }
                else if (LookAheadChar == '|')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.OR;
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
                else if (LookAheadChar == '/') // Comment or DIV
                {
                    result.Text += ConsumeChar(); // Append
                    if (LookAheadChar == '/') // Comment
                    {
                        while (CurrentCharacterIndex < ProgramText.Length && ConsumeChar() != '\n') ; // Ignore comment

                        return ConsumeToken(); // Pass along next valid token
                    }
                    else
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
                else if (LookAheadChar == '[')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.OPEN_BRACKET;
                }

                else if (LookAheadChar == ']')
                {
                    result.Text += ConsumeChar(); // Append
                    result.Type = Token.Types.CLOSE_BRACKET;
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
                    if (LookAheadChar == '=')
                    {
                        result.Text += ConsumeChar(); // Append
                        result.Type = Token.Types.EQUAL;
                    }
                    else
                        ErrorHandler.LexerError(this, "Invalid operator.");
                }
                else if (LookAheadChar == '<')
                {
                    result.Text += ConsumeChar(); // Append
                    if (LookAheadChar == '=')
                    {
                        result.Text += ConsumeChar(); // Append
                        result.Type = Token.Types.LESSTHAN_EQUAL;
                    }
                    else
                        result.Type = Token.Types.LESSTHAN;
                }
                else if (LookAheadChar == '>')
                {
                    result.Text += ConsumeChar(); // Append
                    if (LookAheadChar == '=')
                    {
                        result.Text += ConsumeChar(); // Append
                        result.Type = Token.Types.GREATERTHAN_EQUAL;
                    }
                    else
                        result.Type = Token.Types.GREATERTHAN;
                }
                else if (LookAheadChar == '!')
                {
                    result.Text += ConsumeChar(); // Append
                    if (LookAheadChar == '=')
                    {
                        result.Text += ConsumeChar(); // Append
                        result.Type = Token.Types.NOT_EQUAL;
                    }
                    else
                        ErrorHandler.LexerError(this, "Invalid operator.");
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
                else
                {
                    ErrorHandler.LexerError(this, "Invalid token.");
                }
            }
            else
            {
                result = new Token(CurrentLineNumber, CurrentColumnNumber, CurrentCharacterIndex, "", Token.Types.EOF); // Assume EOF
            }

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

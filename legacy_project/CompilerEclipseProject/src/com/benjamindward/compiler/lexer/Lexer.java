package com.benjamindward.compiler.lexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.benjamindward.compiler.Compiler;
import com.benjamindward.compiler.CompilerComponent;
import com.benjamindward.compiler.errorhandler.ErrorHandler;
import com.benjamindward.compiler.errorhandler.ErrorHandler.SyntaxErrorException;
import com.benjamindward.compiler.semantic.abstractclasses.Factor;

/**
 * 
 * @author ben
 *
 */
public class Lexer extends CompilerComponent {
	private FileInputStream reader; // Instance stream reader
	private File file; // Path of file to analyze
	
	public static String[] reservedWords = {
		"string",
		"case",
		"int",
		"for",
		"bool",
		"and",
		"float",
		"or",
		"global",
		"not",
		"in",
		"program",
		"out",
		"procedure",
		"if",
		"begin",
		"then",
		"return",
		"else",
		"end",
		"true",
		"false"
	};

	// ReadOnly properties

	/**
	 * Get current line number being analyzed
	 * @return Current line number
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}
	private int lineNumber = 1;


	/**
	 * Get current char column number being analyzed
	 * @return Current char column number
	 */
	public int getCharNumber() {
		return this.charNumber;
	}
	private int charNumber = 1;

	/**
	 * Get buffer of recently consumed chars
	 * @return Log of recently consumed chars
	 */
	public String getCharLog() {
		return this.charLog.trim();
	}
	private String charLog = "";

	/**
	 * Gets lookahead char
	 * @return Lookahead char
	 */
	public char getLookaheadChar() {
		return lookaheadChar;
	}
	/**
	 * Sets new lookahead char
	 * @param lookaheadChar New lookahead char
	 */
	public void setLookaheadChar(char lookaheadChar) {
		this.lookaheadChar = lookaheadChar;
	}
	private char lookaheadChar; // Variable to track lookahead when chars are read

	/**
	 * Gets lookahead token
	 * @return Lookahead token
	 */
	public Token getLookaheadToken() {
		return lookaheadToken;
	}
	/**
	 * Sets new lookahead token
	 * @param lookaheadToken New lookahead token
	 */
	public void setLookaheadToken(Token lookaheadToken) {
		this.lookaheadToken = lookaheadToken;
	}
	private Token lookaheadToken; // Variable to track lookahead when token is consumed

	/**
	 * Creates a new lexical analyzer object
	 * @param filePath Path of file to analyze
	 * @param compiler Compiler object the lexer is a component of
	 * @throws SyntaxErrorException 
	 * @throws IOException 
	 */
	public Lexer(String filePath, Compiler compiler) throws SyntaxErrorException, IOException {
		super(compiler);

		this.file = new File(filePath); // Store file path

		// If file could not be found
		if (!file.exists()) {
			getCompiler().getErrorHandler().fileSystemError("The file '" + file.getName() + "' does not exist");
		} // If file cannot be read (already open?)
		else if (!(file.isFile() && file.canRead())) {
			getCompiler().getErrorHandler().fileSystemError("The file '" + file.getName() + "' cannot be read from.");
		}
		// Attempt to initialize file stream
		reader = new FileInputStream(file); // Initialize reader
		consumeChar(); // Generate initial lookahead char
		setLookaheadToken(new Token()); // Initialize lookahead token to prevent null pointer error when cloning at end of getNextToken()
		getNextToken(); // Generate initial lookahead token
	}

	/**
	 * Reads the next char in the source file, consuming it
	 * @return The next char
	 * @throws IOException Could not read from file
	 */
	public char consumeChar() throws IOException {
		char result = lookaheadChar; // Return lookahead
		setLookaheadChar((char) reader.read()); // Consume next char
		// Append char to buffer, or if newline char, flush buffer
		////System.out.println((getLookaheadChar() == '\n' ? "newline" : getLookaheadChar()));
		// If \n, inc line number and reset char number to 1
		if (result == '\n') { // Exclude \r lest double increment
			lineNumber++;
			charLog = ""; // Clear charlog
		} else {
			charLog += result; // Append char to log
		}
		// Returns the next char in the file, consuming it
		return result;
	}

	/**
	 * Returns whether char is an alpha character or not
	 * @param charValue Char in question
	 * @return TRUE if alpha character, FALSE if not
	 */
	private static boolean isAlphaOrUnderscoreChar(char charValue) {
		// If not alpha char
		if (alphaChars.indexOf(charValue) == -1)
			return false; // Not alpha
		else // If alpha char
			return true; // Is alpha
	}
	private static String alphaChars = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * Returns whether char is a digit or not
	 * @param charValue Char in question
	 * @return TRUE if digit, FALSE if not
	 */
	private static boolean isDigitChar(char charValue) {
		// If not digit
		if (digitChars.indexOf(charValue) == -1)
			return false; // Not digit
		else // If digit
			return true; // Is digit
	}
	private static String digitChars = "1234567890";

	/**
	 * Returns whether char is valid string char or not
	 * @param charValue Char in question
	 * @return TRUE if valid string char, FALSE if not
	 */
	private static boolean isStringChar(char charValue) {
		// If not string char
		if (stringChars.indexOf(charValue) == -1)
			return false; // Not string char
		else // If string char
			return true; // Is string char
	}
	private static String stringChars = alphaChars + digitChars + " [_,;:.']*";

	/**
	 * Returns whether char is new line or not
	 * @param charValue Char in question
	 * @return TRUE if new line, FALSE if not
	 */
	public static boolean isNewLine(char charValue) {
		if(charValue == '\n' || charValue == '\r')
			return true;
		else
			return false;
	}

	/**
	 * Removes whitespace (if found) by consuming chars from stream reader, adjusting line number and char number appropriately.
	 * @return TRUE if whitespace was removed, FALSE if not
	 * @throws IOException Could not read from file
	 */
	private boolean removeSingleWhiteSpace() throws IOException {
		// If white space
		if (getLookaheadChar() == ' ' || isNewLine(getLookaheadChar()) || getLookaheadChar() == '\t') {
			// Skip whitespace char
			consumeChar();
			// Whitespace found
			return true;
		} else // If not whitespace
			return false; // No whitespace found
	}

	/**
	 * Consumes all alphas, underscores, and digits, returning the consumed chars
	 * @return String of consumed chars
	 * @throws IOException Could not read from file
	 */
	private String consumeAllAlphasUnderscoresAndDigits() throws IOException {
		// Hold result string
		String result = "";
		// Consume all alphas, underscores, and digits, appending them to return string
		while (isAlphaOrUnderscoreChar(getLookaheadChar()) || isDigitChar(getLookaheadChar()))
			result += consumeChar(); // Consume alpha, underscore, and digit chars
		// Return consumed chars
		return result;
	}

	/**
	 * Consumes all digits, returning the consumed chars
	 * @return String of consumed chars
	 * @throws IOException Could not read from file
	 */
	private String consumeAllDigits() throws IOException {
		// Hold result string
		String result = "";
		// Consume all digits, appending them to return string
		while (isDigitChar(getLookaheadChar()))
			result += consumeChar(); // Consume digit chars
		// Return consumed chars
		return result;
	}

	/**
	 * Reads from the source file and returns the next valid token, consuming each char
	 * @return The next valid token
	 * @throws IOException Could not read from file
	 * @throws SyntaxErrorException 
	 */
	public Token getNextToken() throws IOException, SyntaxErrorException {
		// Hold result value
		Token token = new Token(Token.TokenTypes.NULL, "");
		// Skip whitespace
		while (removeSingleWhiteSpace()); // Continue until non-whitespace found
		// If end of file found
		if (reader.available() == 0) // No more available readable chars indicates EOF
			return Token.EOF; // Return EOF Token constant
		// Save initial char
		char initChar = getLookaheadChar();
		Token initLookaheadToken = getLookaheadToken(); // Store initial lookahead token
		// Consume at least one char during function to keep the char stream moving
		token.appendToStr("" + consumeChar());
		// Entire lexan algorithm contained in switch case, where single char tokens are
		// analyzed in top cases and more complex tokens are handled in default case
		// using more intricate control flow
		switch (initChar)
		{ // Handle definite single char tokens in top-most cases
		case ';': // Semi-colon
			token.setType(Token.TokenTypes.SEMICOL);
			break;
		case ',': // Comma
			token.setType(Token.TokenTypes.COMMA);
			break;
		case '+': // Addition
			token.setType(Token.TokenTypes.ADD);
			break;
		case '-': // Subtraction
			token.setType(Token.TokenTypes.SUB);
			break;
		case '*': // Multiplication
			token.setType(Token.TokenTypes.MUL);
			break;
		case '&': // Bitwise-AND
			token.setType(Token.TokenTypes.AND);
			break;
		case '|': // Bitwise-OR
			token.setType(Token.TokenTypes.OR);
			break;
		case '(': // Open paran
			token.setType(Token.TokenTypes.O_PAR);
			break;
		case ')': // Close paren
			token.setType(Token.TokenTypes.C_PAR);
			break;
		case '{': // Open brace
			token.setType(Token.TokenTypes.O_BRC);
			break;
		case '}': // Close brace
			token.setType(Token.TokenTypes.C_BRC);
			break;
		case '[': // Open bracket
			token.setType(Token.TokenTypes.O_BRK);
			break;
		case ']': // Close bracket
			token.setType(Token.TokenTypes.C_BRK);
			break;
		case '.': // Decimal
			// Skip decimal
			token.appendToStr("" + consumeChar()); // Append to error token
			// If digits follow the decimal
			if (isDigitChar(getLookaheadChar())) { // If char after decimal is a digit
				// Skip decimals
				token.appendToStr(consumeAllDigits()); // Append to error token
				// Output syntax error for invalid number
				getCompiler().getErrorHandler().lexerSyntaxError("Must have at least one digit preceding decimals.", token); // Error handling
			} else // If it is a lone decimal
				getCompiler().getErrorHandler().lexerSyntaxError("Misplaced decimal.", token); // Output syntax error for misplaced decimal
			// Invalid decimal (and following digits if any) skipped successfully
			break;
		default: // Handle more complex token strings
			// If < (possibly <=)
			if (initChar == '<') {
				// Equals following
				if (getLookaheadChar() == '=') {
					token.setType(Token.TokenTypes.LTEQ); // Set type
					token.appendToStr("" + consumeChar()); // Append char
					break; // Token ready
				} else { // Less than by itself
					token.setType(Token.TokenTypes.LT); // Set type
					break; // Token ready
				}
			}
			// If > (possibly >=)
			else if (initChar == '>') {
				// Equals following
				if (getLookaheadChar() == '=') {
					token.setType(Token.TokenTypes.GTEQ); // Set type
					token.appendToStr("" + consumeChar()); // Append char
					break; // Token ready
				} else { // Greater than by itself
					token.setType(Token.TokenTypes.GT); // Set type
					break; // Token ready
				}
			}
			// If = (possibly ==)
			else if (initChar == '=') {
				// Second equals found
				if (getLookaheadChar() == '=') {
					token.setType(Token.TokenTypes.EQ); // Set type
					token.appendToStr("" + consumeChar()); // Append char
				} else { // Error, single equals invalid operator
					token.setStr("" + initChar); // Append to error token
					getCompiler().getErrorHandler().lexerSyntaxError("Invalid operator.", token); // Error handling
				}
				break; // Token ready
			}
			// If ! (possibly !=)
			else if (initChar == '!') {
				// Exclamation-mark found
				if (getLookaheadChar() == '=') {
					token.setType(Token.TokenTypes.NEQ); // Set type
					token.appendToStr("" + consumeChar()); // Append char
				} else { // Error, single exclamation point invalid operator
					token.appendToStr("" + initChar); // Append to error token
					getCompiler().getErrorHandler().lexerSyntaxError("Invalid operator.", token); // Error handling
				}
				break; // Token ready
			}
			// If / (possibly //)
			else if (initChar == '/') {
				// Double slash, comments
				if (getLookaheadChar() == '/') {
					token.setStr(""); // Undo firstChar append
					consumeChar(); // Skip second slash
					// Consume comment chars
					while (getLookaheadChar() != 0 && !isNewLine(getLookaheadChar())) // While lookhead not EOF or new line
						consumeChar(); // Skip char
					// Continue searching for token via self-recursion
					return getNextToken();
				} else // Division
					token.setType(Token.TokenTypes.DIV); // Set type
				break; // Token ready
			}
			// If : (possibly :=)
			else if (initChar == ':') {
				// Equals follows
				if (getLookaheadChar() == '=') {
					token.setType(Token.TokenTypes.ASSIGN); // Set type
					token.appendToStr("" + consumeChar()); // Append char
				} else // Colon by itself
					token.setType(Token.TokenTypes.COLON); // Set type
				break; // Token ready
			}
			// <string> :: = "[a-zA-Z0-9 _,;:.']*"
			else if (initChar == '"') { // Detect beginning quote
				while (true) {
					// If not end of file or new line
					if (reader.available() > 0 && !isNewLine(getLookaheadChar())) {
						// Balancing quote found
						if (getLookaheadChar() == '"') {
							token.setType(Token.TokenTypes.STR); // Set type
							token.appendToStr("" + consumeChar()); // Append balancing quote
							break; // Break from while, token ready
						}
						// Valid string char found
						else if (isStringChar(getLookaheadChar()))
							token.appendToStr("" + consumeChar()); // Append valid string char
						else { // Invalid string char found
							// Skip invalid char, appending it to log
							// Error handling
							token.setStr("" + getLookaheadChar()); // Set error token string
							getCompiler().getErrorHandler().lexerSyntaxError("Invalid string character.", token);
						}
					} else { // EOF or new file, non-balanced quotes
						// Error handling
						getCompiler().getErrorHandler().lexerSyntaxError("Missing end-quote.", token);
						break; // Break from while, token ready
					}
				}
			}
			// <number> ::= [0-9][0-9]*[.[0-9]*]?
			else if (isDigitChar(initChar)) { // First union of regex: [0-9]
				// Second union of regex: [0-9]*
				token.appendToStr(consumeAllDigits()); // Append following digits
				// If alphas follow the digits
				if (isAlphaOrUnderscoreChar(lookaheadChar)) {
					// Invalid identifier detected. Skip following alphas/digits and output error
					token.appendToStr(consumeAllAlphasUnderscoresAndDigits()); // Append rest of invalid chars to error token string
					// Output syntax error for invalid identifer
					getCompiler().getErrorHandler().lexerSyntaxError("Identifiers cannot begin with a number.", token); // Error handling
				} else { // No alphas after first union
					// Third union of regex (optional): [.[0-9]*]?
					if (getLookaheadChar() == '.') {
						// Append decimal
						token.appendToStr("" + consumeChar());
						// Append following digits, if any
						token.appendToStr(consumeAllDigits());
						token.setType(Token.TokenTypes.FLOAT);
					} else
						token.setType(Token.TokenTypes.INTEGER); // Set type
				}
			}
			// <identifier> ::= [a-zA-Z][a-zA-Z0-9_]*
			else if (isAlphaOrUnderscoreChar(initChar)) { // First union of regex: [a-zA-Z]
				// Second union of regex: [a-zA-Z0-9_]*
				token.appendToStr(consumeAllAlphasUnderscoresAndDigits()); // Append char
				// Set type
				token.setType(Token.TokenTypes.ID);
			} else { // No valid tokens with character in first set
				// Error handling
				token.setStr("" + initChar); // Append invalid character
				getCompiler().getErrorHandler().lexerSyntaxError("Invalid character.", token);
			}
			// Break default case
			break;
		}
		// Case-insensitivity for identifiers and check for reserved words
		if(token.getType() == Token.TokenTypes.ID) {
			token.setStr(token.getStr().toLowerCase()); // Lowercase
			// Determine whether new lookahead is a reserved word
			boolean reserved = false; // Assume not research word;
			for(String word : reservedWords) {
				if(token.getStr().equals(word)) {
					reserved = true;
					break;
				}
			}
			if(reserved) { // Could be either boolean or a reserved word in general
				////System.out.println("IS RESERVED: " + token.getStr());
				if(token.getStr().equals("true") || token.getStr().equals("false")) { // Boolean word
					////System.out.println("SETTING TO BOOLEAN");
					token.setType(Token.TokenTypes.BOOL);
				} else // Reserved word in general
					token.setType(Token.TokenTypes.RESERVED);
			}
		}
		// Trim quotes off of string token value
		if(token.getType() == Token.TokenTypes.STR) {
			token.setStr(token.getStr().substring(1, token.getStr().length()-1));
			////System.out.println("Token is STRING: " + token.getStr());
		}
		
		// Debug output, print token
		Compiler.debugOutput("NEXT --> <" + token.getType().toString() + "," + token.getStr() + ">");
		// Update lookahead token
		setLookaheadToken(token);
		// Return result
		return initLookaheadToken;
	}
}
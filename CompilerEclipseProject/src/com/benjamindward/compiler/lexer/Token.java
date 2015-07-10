package com.benjamindward.compiler.lexer;

/**
 * 
 * @author ben
 *
 */
public class Token {
    public static enum TokenTypes {
        NULL, // Null / Error/ Undetermined
        EOF, // End-of-file
        RESERVED, // Reserved word
        BOOL, // Boolean value
        INTEGER, // Integer number value
        FLOAT, // Float-point number value
        ID, // Identity / symbol
        STR, // String
        ERR_NUM_ID, // Token is either an invalid number or an invalid id
        COLON, // Colon, ':'
        SEMICOL, // Semi-colon, ';'
        COMMA, // Comma, ','
        ADD, // Addition, '+'
        SUB, // Subtraction, '-'
        MUL, // Multiplication, '*'
        DIV, // Division, '/'
        AND, // Bitwise-AND, '&'
        OR, // Bitwise-OR, '|'
        NOT, // 'not'
        O_PAR, // Open parantheses, '('
        C_PAR, // Close parantheses, ')'
        GT, // Greater than
        LT, // Less than
        GTEQ, // Greater than or equal
        LTEQ, // Less than or equal
        EQ, // Equal to, '=='
        NEQ, // Not equal to, '!='
        ASSIGN, // Assignment, ':='
        O_BRC, // Open brace, '{'
        C_BRC, // Close brace, '}'
        O_BRK, // Open bracket, '['
        C_BRK, // Close bracket, ']'
        INTERNAL // Type for token placeholder in AST internal token (parser phase)
    }
    
    /**
     * 
     * @return
     */
    public boolean isTypeMarkReservedWordToken() {
    	if(getStr().equals("integer") ||
				getStr().equals("float") ||
				getStr().equals("bool") ||
				getStr().equals("string"))
			return true;
		else
			return false;
    }
    
    public boolean isExpressionOperator() {
    	switch(getType()) {
    	case AND:
    	case OR:
    	case NOT:
    		return true;
    	default:
    		return false;
    	}
    }
    
    public boolean isArithOpOperator() {
    	switch(getType()) {
    	case ADD:
    	case SUB:
    		return true;
    	default:
    		return false;
    	}
    }
    
    public boolean isRelationOperator() {
    	switch(getType()) {
    	case GT:
    	case LT:
    	case GTEQ:
    	case LTEQ:
    	case EQ:
    	case NEQ:
    		return true;
    	default:
    		return false;
    	}
    }
    
    public boolean isTermOperator() {
    	switch(getType()) {
    	case MUL:
    	case DIV:
    		return true;
    	default:
    		return false;
    	}
    }
    
    public static TokenTypes strToTypemark(String typeMarkStr) {
    	switch(typeMarkStr) {
    	case "integer":
    		return TokenTypes.INTEGER;
    	case "float":
    		return TokenTypes.FLOAT;
    	case "bool":
    		return TokenTypes.BOOL;
    	case "string":
    		return TokenTypes.STR;
    	default:
    		return null;
    	}
    }
    
    public static boolean typesAreCompatible(TokenTypes a, TokenTypes b) {
    	if(a == b ||
    			((a == TokenTypes.INTEGER || a == TokenTypes.FLOAT) && (b == TokenTypes.INTEGER || b == TokenTypes.FLOAT)) ||
    			((a == TokenTypes.INTEGER || a == TokenTypes.BOOL) && (b == TokenTypes.INTEGER || b == TokenTypes.BOOL)))
    		return true;
    	else
    		return false;
    }
    
    public static String typeToStr(TokenTypes type) {
    	switch(type) {
    	case EOF:
    		return "end-of-file";
    	case BOOL:
    		return "boolean";
    	case INTEGER:
    		return "integer";
    	case FLOAT:
    		return "float";
    	case ID:
    		return "identifier/name";
    	case STR:
    		return "string";
    	case ERR_NUM_ID:
    		return "error-code";
    	case COLON:
    		return "':'";
    	case SEMICOL:
    		return "';'";
    	case COMMA:
    		return "','";
    	case ADD:
    		return "'+'";
    	case SUB:
    		return "'-'";
    	case MUL:
    		return "'*'";
    	case DIV:
    		return "'/'";
    	case AND:
    		return "'&'";
    	case OR:
    		return "'|'";
    	case NOT:
    		return "'not'";
    	case O_PAR:
    		return "'('";
    	case C_PAR:
    		return "')";
    	case GT:
    		return "'>'";
    	case LT:
    		return "'<'";
    	case GTEQ:
    		return "'>='";
    	case LTEQ:
    		return "'<='";
    	case EQ:
    		return "'=='";
    	case NEQ:
    		return "'!='";
    	case ASSIGN:
    		return "':='";
    	case O_BRC:
    		return "'{'";
    	case C_BRC:
    		return "'}'";
    	case O_BRK:
    		return "'['";
    	case C_BRK:
    		return "']'";
    	default:
    		return "null";
    	}
    }

    public static final Token EOF = new Token(TokenTypes.EOF, ""); // EOF Token constant

    /**
     * Gets token string.  Equivalent to toString()
     * @return The token string
     */
    public String getStr() {
        return this.str;
    }

    /**
     * Sets token string
     * @param str The new token string
     */
    public void setStr(String str) {
        this.str = str;
    }

    /**
     * Appends a single char to the end of the token string
     * @param str String to append
     */
    public void appendToStr(String str) {
        this.str += "" + str;
    }
    private String str;

    /**
     * Gets the token type
     * @return The token type
     */
    public TokenTypes getType() {
        return this.type;
    }

    /**
     * Sets the token type
     * @param type The new token type
     */
    public void setType(TokenTypes type) {
        this.type = type;
    }
    private TokenTypes type;

    /**
     * Creates a new token object
     * @param tokenType New token type
     * @param tokenString New token string
     */
    public Token(TokenTypes tokenType, String tokenString) {
        setType(tokenType);
        setStr(tokenString);
    }

    public Token() {
    	setType(TokenTypes.NULL);
		setStr("");
	}

	@Override
    public String toString() {
        return getStr();
    }
}
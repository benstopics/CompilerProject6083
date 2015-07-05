package com.benjamindward.compiler.errorhandler;

import java.io.IOException;

import com.benjamindward.compiler.Compiler;
import com.benjamindward.compiler.CompilerComponent;
import com.benjamindward.compiler.lexer.Lexer;
import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;
import com.benjamindward.compiler.semantic.abstractclasses.Expression;
import com.benjamindward.compiler.semantic.abstractclasses.ExpressionNode;

/**
 * IDEA: sync to semi-colon, color text (error is red, warning is yellow)
 * @author ward.ben
 *
 */
public class ErrorHandler extends CompilerComponent {
	/**
	 * Creates a new error handler object
	 * @param compiler Parent compiler
	 */
    public ErrorHandler(Compiler compiler) {
    	super(compiler);
    }
    
    /**
     * Prints basic syntax error to console
     * @param msg Generic syntax error message. NOTE: Line number, char number, and char log buffer automatically added.
     * @throws SyntaxErrorException 
     * @throws IOException 
     */
    public void syntaxError(String msg) throws SyntaxErrorException, IOException {
        System.out.println();
        // Generate error msg
        String errorMsg = "Syntax Error @ line " + getCompiler().getLexer().getLineNumber() + " -> " + msg + "\n\n" + getFormatedCharLog(true)
        		+ "\n--------------------";
        System.out.println(errorMsg);
        System.exit(-1);
    }
    
    public void lexerSyntaxError(String msg, Token errorToken) throws SyntaxErrorException, IOException {
    	getCompiler().getLexer().setLookaheadToken(errorToken);
    	syntaxError(msg); // Now output syntax error message
    }
    
    public void warning(String msg) throws IOException, SyntaxErrorException {
    	System.out.println("Warning @ line " + getCompiler().getLexer().getLineNumber() + " -> " + msg + "\n\n" + getFormatedCharLog(false));
    }
    
    public void compilerError(String msg) throws Exception {
    	System.out.println("BUG DETECTED >> " + msg);
        System.exit(-1);
    }
    
    public String getFormatedCharLog(boolean gotoEndOfLine) throws IOException, SyntaxErrorException {
    	String charLogBackup = getCompiler().getLexer().getCharLog(); // Backup charlog because it will reset after new line occurs
    	//getCompiler().getLexer().getNextToken();
    	// Put caret underneath beginning of token
    	String caretLine = "";
    	int lookaheadTokenStrLength = getCompiler().getLexer().getLookaheadToken().getStr().length();
    	int charStretch = charLogBackup.length()-lookaheadTokenStrLength;
    	for(int i=0; i<charStretch; i++) // Subtract token string length from charLog length
    		caretLine += " ";
    	int lookaheadMiddleCharIndex = charStretch + (lookaheadTokenStrLength/2); // Center of token str in charlog
    	for(int i=0; i<lookaheadTokenStrLength; i++) // Append caret underneath token characters
    		caretLine += "~"; // Append caret underneath token characters
    	
    	if(gotoEndOfLine) {
	    	// Add rest of line
	    	int lineNumBackup = getCompiler().getLexer().getLineNumber();
	        while(lineNumBackup == getCompiler().getLexer().getLineNumber() &&
	        		!Lexer.isNewLine(getCompiler().getLexer().getLookaheadChar()) &&
	        		getCompiler().getLexer().getLookaheadToken() != Token.EOF) {
	        	System.out.println(getCompiler().getLexer().getLookaheadToken());
	        	charLogBackup += getCompiler().getLexer().consumeChar();
	        }
    	}
        
        int maxHalfChars = 40; // Maximum number of characters on either side
        if(charLogBackup.length()-lookaheadMiddleCharIndex > maxHalfChars) { // Trim end of token string if right half longer than max half size
        	charLogBackup = charLogBackup.substring(0, lookaheadMiddleCharIndex+maxHalfChars); // Trim end
        }
        if(charLogBackup.length() > maxHalfChars+maxHalfChars) { // If log still too longer, trim beginning
        	int start = lookaheadMiddleCharIndex-maxHalfChars;
        	charLogBackup = charLogBackup.substring(start, charLogBackup.length());
        	caretLine = caretLine.substring(start, caretLine.length());
        }
        return	"\"" + charLogBackup + "\"\n " + caretLine;
    }
    
    public void fileSystemError(String msg) {
    	System.out.println("FileSystem -> " + msg);
    }
    
    public void endProgram() {
    	System.exit(-1);
    }
    
    public static class SyntaxErrorException extends Exception {
		private static final long serialVersionUID = -4686876074146804958L;
		public SyntaxErrorException(String msg) {
    		super(msg);
    	}
    }
    
    public static class TypeCheckErrorException extends SyntaxErrorException {
		private static final long serialVersionUID = 2346241532808436697L;
		public TypeCheckErrorException(ExpressionNode expressionNode) {
    		super("Operation " + Token.typeToStr(expressionNode.getOperator())
    				+ " is not defined between " + Token.typeToStr(expressionNode.getOperandA().getEvaluatedType())
    				+ " and " + Token.typeToStr(expressionNode.getOperandB().getEvaluatedType()) + " operands.");
    	}
		
		public TypeCheckErrorException(Expression expressionNode, TokenTypes operator) {
    		super("Operator " + Token.typeToStr(operator)
    				+ " is not supported for type " + Token.typeToStr(expressionNode.getOperandA().getEvaluatedType()) + ".");
    	}
    }
    
    public static class WarningException extends Exception {
		private static final long serialVersionUID = 4450912418600924658L;
		public WarningException(String msg) {
    		super(msg);
    	}
    }
    
    public static class MissingExpressionException extends Exception {
		private static final long serialVersionUID = 6531890131523109080L;
		public MissingExpressionException(String msg) {
    		super(msg);
    	}
    }
    
    public static class MissingFactorException extends Exception {
		private static final long serialVersionUID = -8110495982799196465L;
		public MissingFactorException(String msg) {
    		super(msg);
    	}
    }
}

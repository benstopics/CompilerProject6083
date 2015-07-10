package com.benjamindward.compiler;

import java.io.IOException;

import com.benjamindward.compiler.emitter.Emitter;
import com.benjamindward.compiler.errorhandler.ErrorHandler;
import com.benjamindward.compiler.errorhandler.ErrorHandler.SyntaxErrorException;
import com.benjamindward.compiler.errorhandler.ErrorHandler.WarningException;
import com.benjamindward.compiler.lexer.Lexer;
import com.benjamindward.compiler.parser.Parser;
import com.benjamindward.compiler.semantic.SemanticAnalyzer;
import com.benjamindward.compiler.semantic.abstractclasses.Program;
import com.benjamindward.compiler.semantic.ast.IRAbstractNode;
import com.benjamindward.legacy.ast.TokenAST;


/**
 * 
 * @author ben
 *
 */
public class Compiler {
	
	private boolean printAST;
	
	public boolean isPrintAST() {
		return printAST;
	}

	public void setPrintAST(boolean printAST) {
		this.printAST = printAST;
	}
	private Emitter emitter;
	public Emitter getEmitter() {
		return emitter;
	}

	public void setEmitter(Emitter emitter) {
		this.emitter = emitter;
	}
	private SemanticAnalyzer semanticAnalyzer;
    public SemanticAnalyzer getSemanticAnalyzer() {
		return semanticAnalyzer;
	}

	public void setSemanticAnalyzer(SemanticAnalyzer semanticAnalyzer) {
		this.semanticAnalyzer = semanticAnalyzer;
	}

	/**
     * Gets the compiler's lexer component
     * @return The lexer
     */
    public Lexer getLexer() {
        return this.lexer;
    }
    private Lexer lexer; // Lexical analyzer instance

    /**
     * Gets the compiler's parser component
     * @return The parser
     */
    public Parser getParser() {
        return this.parser;
    }
    private Parser parser; // Parser instance

    /**
     * Gets the compiler's error handler component
     * @return The error handler
     */
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }
    private ErrorHandler errorHandler; // Error handler instance

    /**
     * Creates new compiler object
     * @param inputFilePath Path of file to begin with
     * @throws SyntaxErrorException 
     * @throws IOException 
     */
    public Compiler(String inputFilePath, String outputFilePath, boolean printAST) throws SyntaxErrorException, IOException {
        // Initialize components
        setErrorHandler(new ErrorHandler(this)); // Initialize first because lexer might need to call missing file error
        setLexer(new Lexer(inputFilePath, this));
        setParser(new Parser(this));
        setSemanticAnalyzer(new SemanticAnalyzer(this));
        setEmitter(new Emitter(this, outputFilePath));
        setPrintAST(printAST);
        
    }
    
    public void setLexer(Lexer lexer) {
		this.lexer = lexer;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public Program compileProgram() throws Exception {
    	try {
    		Program astRootNode = getParser().parseProgram();
    		if(isPrintAST())
    			astRootNode.print();
			getEmitter().emitProgram(astRootNode);
		} catch (SyntaxErrorException e) {
			getErrorHandler().syntaxError(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public static void debugOutput(String msg) {
    	////System.out.println(msg);
    }
}
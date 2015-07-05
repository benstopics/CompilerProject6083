package com.benjamindward.compiler.tests;

import java.io.IOException;

import org.omg.CORBA.Environment;

import com.benjamindward.compiler.Compiler;
import com.benjamindward.compiler.emitter.Emitter;
import com.benjamindward.compiler.errorhandler.ErrorHandler.SyntaxErrorException;
import com.benjamindward.compiler.errorhandler.ErrorHandler.WarningException;
import com.benjamindward.compiler.lexer.Token;

/**
 * At a certain point, it was decided that TokenNode was no necessary and that abstract classes should be used.
 * This voided all the test methods in this class. Procedure calls which printed AST's from parser were
 * commented out to allow TestsAlpha class to remain in the project without preventing compilation.
 * @author ben
 *
 */
public class TestsAlpha {

	/**
	 * Run all compiler tests
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		try {
			// Lexer tests
			//LexerDebug();
			
			// Parser tests
			//TestParserAST();
			//TestParserDeclarations();
			//TestParserStatements();
			TestParserExpression();
			//TestParserStatementsWithExpressions();
			//TestParserUnreachableStatements();
			
			// Semantic Analyzer tests
			//TestSemanticAnalyzerScope();
			//TestSemanticAnalyzerDuplicateProcedureHeaders();
			
			// Code gen tests
			//TestCodeGenGetClangVersion();
		} catch (SyntaxErrorException e) {
			System.out.println(e.getMessage());
		} catch (WarningException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests semantic analyzer against semantically correct test file. Development process: rinse and repeat, place System.out.println()'s for debugging or use Eclipse debugger
	 * @throws SyntaxErrorException
	 * @throws IOException
	 * @throws WarningException
	 */
    private static void TestSemanticAnalyzerScope() throws SyntaxErrorException, IOException, WarningException {
    	System.out.println("\n-:| TEST SEMANTIC ANALYZER >>> ALL |:-\n");
    	
    	Compiler compiler = new Compiler("test_programs/semanticanalyzer/semanticanalyzer-general-test-file.src", "a.ll");
    	//compiler.compileProgram();
	}
    
    private static void TestSemanticAnalyzerDuplicateProcedureHeaders() throws SyntaxErrorException, IOException, WarningException {
    	System.out.println("\n-:| TEST PARSER >>> PROCEDURE HEADER |:-\n");
    	
    	Compiler compiler = new Compiler("test_programs/semanticanalyzer/semanticanalyzer-duplicate-procedure-headers.src", "a.ll");
    	//compiler.compileProgram();
	}

	private static void TestCodeGenGetClangVersion() throws IOException {
    	if(System.getProperty("os.name").startsWith("Windows")) {
	    	String versionLine = Emitter.execCmd("\"%ProgramFiles(x86)%\\LLVM\\bin\\clang.exe\" --version").split("\n")[0];
	    	if(versionLine.startsWith("clang version ")) {
	    		String[] words = versionLine.split(" ");
	    		String version = words[2];
	    		System.out.println("Installation of Clang version " + version + " found.");
	    	} else {
	    		System.out.println("An installation of Clang was not found. If Clang is installed, change the DIR field in config.properties to the directory containing \"clang.exe\".");
	    	}
    	}
	}

	/**
     * Tests lexical analysis valid and invalid tokens by outputting token stream and analyzing error reporting
     * @throws SyntaxErrorException 
     * @throws IOException 
     */
    public static void LexerDebug() throws SyntaxErrorException, IOException {
		System.out.println("\n-:| TEST LEXER >>> VALID/INVALID TOKENS |:-\n");
		
        Compiler compiler = new Compiler("test_programs/lexer/lexer-debug.src", "a.ll");

        while (true) { // While not end of file
            Token token;
            try {
                token = compiler.getLexer().getNextToken();
                //if(token.TokenType != TokenTypes.NULL)
                System.out.println("<" + token.getType().toString() + "," + token.getStr() + ">");

                if (token.getType() == Token.TokenTypes.EOF) // End of file
                    break;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Development checkpoint: Debug framework and parser skeleton tested. Statement placeholder is "teststmt" and
     * declaration placeholder is "testdecl".
     * @throws SyntaxErrorException 
     * @throws IOException 
     * @throws WarningException 
     */
    public static void TestParserAST() throws SyntaxErrorException, IOException, WarningException {
    	System.out.println("\n-:| TEST PARSER >>> AST PRINTOUT |:-\n");
    	
    	Compiler compiler = new Compiler("test_programs/parser/parser-ast.src", "a.ll");
    	//compiler.getParser().parseProgram().print();
    }
    
    /**
     * Development checkpoint: procedure and variable declarations tested. Statement placeholder remains "teststmt".
     * @throws SyntaxErrorException 
     * @throws IOException 
     * @throws WarningException 
     */
    public static void TestParserDeclarations() throws SyntaxErrorException, IOException, WarningException {
    	System.out.println("\n-:| TEST PARSER >>> PROCEDURE AND VARIABLE DECLARATION |:-\n");
    	
    	Compiler compiler = new Compiler("test_programs/parser/parser-declarations.src", "a.ll");
    	//compiler.getParser().parseProgram().print();
    }
    
    /**
     * Development checkpoint: all five statements tested.  Expression placeholder is "testexpr"
     * @throws SyntaxErrorException 
     * @throws IOException 
     * @throws WarningException 
     */
    public static void TestParserStatements() throws SyntaxErrorException, IOException, WarningException {
    	System.out.println("\n-:| TEST PARSER >>> STATEMENT |:-\n");
    	
    	Compiler compiler = new Compiler("test_programs/parser/parser-statements.src", "a.ll");
    	//compiler.getParser().parseProgram().print();
    }
    
    /**
     * Development checkpoint: expressions tested.
     * @throws SyntaxErrorException 
     * @throws IOException 
     * @throws WarningException 
     */
    public static void TestParserExpression() throws SyntaxErrorException, IOException, WarningException {
    	System.out.println("\n-:| TEST PARSER >>> EXPRESSION |:-\n");
    	
    	Compiler compiler = new Compiler("test_programs/parser/parser-expressions.src", "a.ll");
    	//compiler.compileProgram().print();
    }
    
    /**
     * Development checkpoint: statements with "testexpr" placeholder replaced with test expressions
     * @throws SyntaxErrorException 
     * @throws IOException 
     * @throws WarningException 
     */
    public static void TestParserStatementsWithExpressions() throws SyntaxErrorException, IOException, WarningException {
    	System.out.println("\n-:| TEST PARSER >>> ALL ELEMENTS |:-\n");
    	
    	Compiler compiler = new Compiler("test_programs/parser/parser-statements-with-expressions.src", "a.ll");
    	//compiler.getParser().parseProgram().print();
    }
    
    /**
     * Development checkpoint: parser detects unreachable statements and gives warning, but continues parsing
     * @throws SyntaxErrorException 
     * @throws IOException 
     * @throws WarningException 
     */
    public static void TestParserUnreachableStatements() throws SyntaxErrorException, IOException, WarningException {
    	System.out.println("\n-:| TEST ERROR HANDLER >>> UNREACHABLE CODE |:-\n");
    	
    	Compiler compiler = new Compiler("test_programs/parser/errorhandler-unreachable.src", "a.ll");
    	//compiler.getParser().parseProgram().print();
    }
}
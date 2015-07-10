package com.benjamindward.compiler.tests;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import com.benjamindward.compiler.Compiler;
import com.benjamindward.compiler.errorhandler.ErrorHandler.SyntaxErrorException;

public class TestsBeta {

	public static void main(String[] args) {
		try {
			//TestCompilerMinimum();
			//TestCompilerBasic();
			//TestCompilerProvidedTests();
			TestEmitter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void TestCompilerMinimum() throws Exception {
    	//System.out.println("\n-:| TEST COMPILER >>> MINIMUM PROGRAM |:-\n");
    	
    	Compiler compiler = new Compiler("test_programs/minimum_program.src", "a.ll", true);
    	compiler.compileProgram().print();
    }

	public static void TestCompilerBasic() throws Exception {
    	//System.out.println("\n-:| TEST COMPILER >>> BASIC PROGRAM |:-\n");
    	
    	Compiler compiler = new Compiler("test_programs/basic_program.src", "a.ll", true);
    	compiler.compileProgram().print();
    }
	
	public static void TestCompilerProvidedTests() throws Exception {
    	//System.out.println("\n-:| TEST COMPILER >>> PROVIDED TEST PROGRAMS |:-\n");
    	
    	Compiler compiler = new Compiler("provided_test_programs/correct/fromJake.src", "a.ll", true);
    	compiler.compileProgram().print();
    	
    	//System.out.println();
    	
    	compiler = new Compiler("provided_test_programs/correct/test_heap.src", "a.ll", true);
    	compiler.compileProgram().print();
    	
    	//System.out.println();
    	
    	compiler = new Compiler("provided_test_programs/correct/test_program.src", "a.ll", true);
    	compiler.compileProgram().print();
    	
    	//System.out.println();
    	
    	compiler = new Compiler("provided_test_programs/correct/test_program_array.src", "a.ll", true);
    	compiler.compileProgram().print();
    	
    	//System.out.println();
    	
    	compiler = new Compiler("provided_test_programs/correct/test_program_minimal.src", "a.ll", true);
    	compiler.compileProgram().print();
    	
    	//System.out.println();
    	
    	compiler = new Compiler("provided_test_programs/correct/test_program_with_errors.src", "a.ll", true);
    	compiler.compileProgram().print();
    }
	
	public static void TestEmitter() throws Exception {
		//Scanner reader = new Scanner(System.in);
		//System.out.print("Enter local or full input file path: ");
		//String inputFilePath = reader.nextLine();
		//reader.close();
		
		//Compiler compiler = new Compiler(inputFilePath, "a.ll", true);
		Compiler compiler = new Compiler("test_programs/legacy/parser/parser-expressions.src", "a.ll", true);
		compiler.compileProgram();
	}
	
}

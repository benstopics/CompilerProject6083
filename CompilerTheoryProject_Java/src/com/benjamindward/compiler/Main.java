package com.benjamindward.compiler;

public class Main {
	public static void main(String[] args) {
		try {
			if(args.length == 1 && args[0].equals("--help"))
				printHelp();
			else {
				String inputFile = null;
				String outputFile = "a.ll";
				boolean showAST = false;
				if(args.length <= 0)
					throw new Exception("Not enough arguments. Use the \"--help\" flag for details.");
				for(int i=0; i<args.length; i++) {
					if(args[i].equals("-s"))
						inputFile = args[++i];
					else if(args[i].equals("-o"))
						outputFile = args[++i];
					else if(args[i].equals("-show-ast"))
						showAST = true;
				}
				if(inputFile == null)
					throw new Exception("Input file path not specified. Use the \"--help\" flag for details.");
				new Compiler(inputFile, outputFile, showAST).compileProgram();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void printHelp() {
		System.out.println("  -s <file>\tSpecify source file path"
				+ "\n  -o <file>\tSpecify output file path"
				+ "\n  -show-ast\tDisplay parsed AST in console");
	}
}

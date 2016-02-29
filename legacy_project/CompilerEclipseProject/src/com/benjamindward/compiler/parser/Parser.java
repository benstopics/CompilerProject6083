package com.benjamindward.compiler.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.jws.soap.SOAPBinding.ParameterStyle;

import org.omg.CosNaming.IstringHelper;

import com.benjamindward.compiler.Compiler;
import com.benjamindward.compiler.CompilerComponent;
import com.benjamindward.compiler.emitter.Emitter;
import com.benjamindward.compiler.errorhandler.ErrorHandler;
import com.benjamindward.compiler.errorhandler.ErrorHandler.SyntaxErrorException;
import com.benjamindward.compiler.errorhandler.ErrorHandler.WarningException;
import com.benjamindward.compiler.errorhandler.ErrorHandler.MissingExpressionException;
import com.benjamindward.compiler.errorhandler.ErrorHandler.MissingFactorException;
import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;
import com.benjamindward.compiler.semantic.ProcedureKey;
import com.benjamindward.compiler.semantic.SemanticAnalyzer;
import com.benjamindward.compiler.semantic.VariableKey;
import com.benjamindward.compiler.semantic.abstractclasses.AssignmentStatement;
import com.benjamindward.compiler.semantic.abstractclasses.Declaration;
import com.benjamindward.compiler.semantic.abstractclasses.Destination;
import com.benjamindward.compiler.semantic.abstractclasses.Expression;
import com.benjamindward.compiler.semantic.abstractclasses.ExpressionNode;
import com.benjamindward.compiler.semantic.abstractclasses.ExpressionNode;
import com.benjamindward.compiler.semantic.abstractclasses.Factor;
import com.benjamindward.compiler.semantic.abstractclasses.IfStatement;
import com.benjamindward.compiler.semantic.abstractclasses.LoopStatement;
import com.benjamindward.compiler.semantic.abstractclasses.Parameter;
import com.benjamindward.compiler.semantic.abstractclasses.Procedure;
import com.benjamindward.compiler.semantic.abstractclasses.ProcedureCall;
import com.benjamindward.compiler.semantic.abstractclasses.Program;
import com.benjamindward.compiler.semantic.abstractclasses.ProgramBody;
import com.benjamindward.compiler.semantic.abstractclasses.ReturnStatement;
import com.benjamindward.compiler.semantic.abstractclasses.Statement;
import com.benjamindward.compiler.semantic.abstractclasses.VariableDeclaration;
import com.benjamindward.legacy.ast.TokenAST;

/**
 * 
 * @author ben
 * 
 */
public class Parser extends CompilerComponent {

	// Reserved keywords will disallow overwriting of symbol table by ID declaration
	public static final String[] Keywords = new String[] {
		"string",
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
		"end"
	};
	
	public static enum InternalNodeLabels {
		NULL,
		DECLARATION_LIST,
		RETURN, STATEMENT_LIST,
		PROCEDURE_DECLARATION,
		PARAMETER_LIST,
		PARAMETER,
		VARIABLE_DECLARATION,
		PROCEDURE_CALL,
		ASSIGNMENT_STATEMENT,
		IF_STATEMENT,
		LOOP_STATEMENT,
		AND,
		OR,
		ADD,
		SUB,
		GE,
		LE,
		EQ,
		NEQ,
		MUL,
		DIV,
		FACTOR,
		NEGATE,
		NOT,
		EXPRESSION,
		ROOT,
	}

	public Parser(Compiler compiler)
	{
		super(compiler);
	}

	private Token nextToken() throws SyntaxErrorException, IOException {
		return getCompiler().getLexer().getNextToken(); // Return next token, updating lookahead token
	}

	private Token getLookaheadToken() {
		return getCompiler().getLexer().getLookaheadToken();
	}
	
	private int getLineNumber() {
		return getCompiler().getLexer().getLineNumber();
	}
	
	/**
	 * Convenience method
	 */
	private void enterScope(boolean codeBlock) {
		getCompiler().getSemanticAnalyzer().enterScope(codeBlock);
	}
	
	/**
	 * Convenience method
	 */
	private void exitScope() {
		getCompiler().getSemanticAnalyzer().exitScope();
	}
	
	/**
	 * Convenience method to get semantic analyzer of compiler
	 * @return Compiler's semantic analyzer instance
	 */
	public SemanticAnalyzer getSemanticAnalyzer() {
		return getCompiler().getSemanticAnalyzer();
	}

	/**
	 * Parse single token by token string with customized error message
	 * @param str String of token to parse
	 * @param optional FALSE will trigger compiler error if not parsed, TRUE will pass over
	 * @return if token parsed, returns Token object, if not, returns null
	 * @throws SyntaxErrorException 
	 * @throws IOException 
	 */
	private Token parseTokenByReservedWord(String str, boolean optional, String message) throws SyntaxErrorException, IOException {
		Token result = null; // Assume not parsed
		////System.out.println("Attempting to match " + getLookaheadToken() + " with " + str);
		if(getLookaheadToken().getStr().equals(str)) { // Check token type against comparison type
			result = nextToken(); // Continue, returning parsed token
			////System.out.println("matched " + result + " while looking for " + str);
		} else if(!optional) { // If not optional
			if(message != null)
				// Error handling, missing token
				throw new SyntaxErrorException(message);
			else if(str.length() > 1)
				throw new SyntaxErrorException("Expected '" + str + "' keyword.");
			else
				throw new SyntaxErrorException("Expected '" + str + "'.");
		}
		return result;
	}
	
	/**
	 * Parse single token by token string
	 * @param str String of token to parse
	 * @param optional FALSE will trigger compiler error if not parsed, TRUE will pass over
	 * @return if token parsed, returns Token object, if not, returns null
	 * @throws SyntaxErrorException 
	 * @throws IOException 
	 */
	private Token parseTokenByReservedWord(String str, boolean optional) throws SyntaxErrorException, IOException {
		return parseTokenByReservedWord(str, optional, null);
	}

	/**
	 * Parse single token by token type with customized error message
	 * @param type Type of token to parse
	 * @param optional FALSE will trigger compiler error if not parsed, TRUE will pass over
	 * @param message Customized error message
	 * @return if token parsed, returns Token object, if not, returns null
	 * @throws SyntaxErrorException 
	 * @throws IOException 
	 */
	private Token parseTokenByType(TokenTypes type, boolean optional, String message) throws SyntaxErrorException, IOException {
		Token result = null; // Assume not parsed
		if(type == TokenTypes.ID && getLookaheadToken().getType() == TokenTypes.RESERVED)
			throw new SyntaxErrorException("'" + getLookaheadToken() + "' is a reserved word and cannot be used as an " + Token.typeToStr(type) + ".");
		else if(getLookaheadToken().getType() == type) { // Check token type against comparison type
			result = nextToken(); // Continue, returning parsed token
			////System.out.println("matched " + result);
		} else if(!optional) // If not optional
			// Error handling, missing token
			if(message != null)
				throw new SyntaxErrorException(message);
			else
				throw new SyntaxErrorException("Expected " + Token.typeToStr(type) + ".");
		return result;
	}
	
	/**
	 * Parse single token by token type
	 * @param type Type of token to parse
	 * @param optional FALSE will trigger compiler error if not parsed, TRUE will pass over
	 * @param message Customized error message
	 * @return if token parsed, returns Token object, if not, returns null
	 * @throws SyntaxErrorException 
	 * @throws IOException 
	 */
	private Token parseTokenByType(TokenTypes type, boolean optional) throws SyntaxErrorException, IOException {
		return parseTokenByType(type, optional, null);
	}

	/**
	 * Parse the entire program. <br/>
	 * <br/>
	 * {@literal <program> ::= <program_header> <program_body>}
	 * @return 
	 * @throws Exception 
	 */
	public Program parseProgram() throws Exception {
		TokenAST tokenAST = new TokenAST();
		
		String name = parseProgramHeader(); // Returns string which is program name
		
		// ==== LIBRARY FUNCTIONS ====
		// getBool()
		getSemanticAnalyzer().declareProcedureKey(
				new ProcedureKey("getbool", new ArrayList<Parameter>() {
					private static final long serialVersionUID = 1L;
				{
					add(new Parameter(new VariableKey("input_bool", true, 0, TokenTypes.BOOL), true));
				}}));
		// getInteger()
		getSemanticAnalyzer().declareProcedureKey(
				new ProcedureKey("getinteger", new ArrayList<Parameter>() {
					private static final long serialVersionUID = 1L;
				{
					add(new Parameter(new VariableKey("input_int", true, 0, TokenTypes.INTEGER), true));
				}}));
		// getFloat()
		getSemanticAnalyzer().declareProcedureKey(
				new ProcedureKey("getfloat", new ArrayList<Parameter>() {
					private static final long serialVersionUID = 1L;
				{
					add(new Parameter(new VariableKey("input_float", true, 0, TokenTypes.FLOAT), true));
				}}));
		// getString()
		getSemanticAnalyzer().declareProcedureKey(
				new ProcedureKey("getstring", new ArrayList<Parameter>() {
					private static final long serialVersionUID = 1L;
				{
					add(new Parameter(new VariableKey("input_str", true, 0, TokenTypes.STR), true));
				}}));
		// putBool()
		getSemanticAnalyzer().declareProcedureKey(
				new ProcedureKey("putbool", new ArrayList<Parameter>() {
					private static final long serialVersionUID = 1L;
				{
					add(new Parameter(new VariableKey("output_bool", true, 0, TokenTypes.BOOL), true));
				}}));
		// putInteger()
		getSemanticAnalyzer().declareProcedureKey(
				new ProcedureKey("putinteger", new ArrayList<Parameter>() {
					private static final long serialVersionUID = 1L;
				{
					add(new Parameter(new VariableKey("output_int", true, 0, TokenTypes.INTEGER), true));
				}}));
		// putFloat()
		getSemanticAnalyzer().declareProcedureKey(
				new ProcedureKey("putfloat", new ArrayList<Parameter>() {
					private static final long serialVersionUID = 1L;
				{
					add(new Parameter(new VariableKey("output_float", true, 0, TokenTypes.FLOAT), true));
				}}));
		// putString()
		getSemanticAnalyzer().declareProcedureKey(
				new ProcedureKey("putstring", new ArrayList<Parameter>() {
					private static final long serialVersionUID = 1L;
				{
					add(new Parameter(new VariableKey("output_str", true, 0, TokenTypes.STR), true));
				}}));
		
		ProgramBody body = parseProgramBody();
		
		return new Program(name, body);
	}

	/**
	 * Parse program header. <br/>
	 * <br/>
	 * {@literal <program_header> ::= 'program' <identifier> 'is'}
	 * @throws SyntaxErrorException 
	 * @throws IOException 
	 */
	private String parseProgramHeader() throws SyntaxErrorException, IOException {
		parseTokenByReservedWord("program", false);
		Token programNameToken = parseTokenByType(TokenTypes.ID, false, "Invalid program header. Missing program name.");
		parseTokenByReservedWord("is", false);
		return programNameToken.getStr();
	}
	
	/**
	 * Parse program body. <br/>
	 * <br/>
	 * {@literal <program_body> ::=} <br/>
	 * {@literal ( <declaration> ';' )*} <br/>
	 * {@literal 'begin'} <br/>
	 * {@literal ( <statement> ';' )*} <br/>
	 * {@literal 'end' 'program'}
	 * @throws Exception 
	 
	 */
	private ProgramBody parseProgramBody() throws Exception {
		
		ArrayList<Declaration> declarations = parseDeclarationsList();
		parseTokenByReservedWord("begin", false);
		ArrayList<Statement> statements = parseStatementsList(false); // Parse statement list
		parseTokenByReservedWord("end", false);
		parseTokenByReservedWord("program", false);
		
		return new ProgramBody(declarations, statements);
	}
	
	/**
	 * Parse a list of declarations. <br/>
	 * <br/>
	 * {@literal ( <declaration> ';' )*}
	 * @param landmarkStr String to search for if semicolon cannot be found.
	 * @throws Exception 
	 
	 */
	private ArrayList<Declaration> parseDeclarationsList() throws Exception {
		////System.out.println("Declaration list");
		ArrayList<Declaration> declarations = new ArrayList<Declaration>();
		
		while(true) { // Parse statements, if any
			Declaration declaration = parseDeclaration();
			////System.out.println("Looking for declaration");
			if(declaration != null) { // If statement parsed
				////System.out.println("Declaration");
				declarations.add(declaration);
				parseTokenByType(TokenTypes.SEMICOL, false); // Find ending semicolon
			} else
				break; // End of list
		}
		return declarations; // Return collected declaration nodes
	}

	/**
	 * Parse declaration. <br/>
	 * <br/>
	 * {@literal <declaration> ::=} <br/>
	 * {@literal [ 'global' ] <procedure_declaration>} <br/>
	 * {@literal | [ 'global' ] <variable_declaration>} <br/>
	 * @return 
	 * @throws Exception 
	 
	 */
	private Declaration parseDeclaration() throws Exception {
		// Optional global keyword
		Token globalToken = parseTokenByReservedWord("global", true);
		if(globalToken != null) { // Not optional since global was found
			if(!getSemanticAnalyzer().inOutMostScope()) {
				////System.out.println(getSemanticAnalyzer().getRootSymbolTable().getChildSymbolTable());
				throw new SyntaxErrorException("Only declarations in the outer-most scope can be declared as global.");
			} else if(getLookaheadToken().getStr().equals("procedure"))
				return parseProcedureDeclaration();
			else
				return parseVariableDeclaration(false);
		} else if(getLookaheadToken().getStr().equals("procedure") || getLookaheadToken().isTypeMarkReservedWordToken()) {
			////System.out.println("procedure or typemark");
			if(getSemanticAnalyzer().inOutMostScope()) {
				throw new SyntaxErrorException("Declarations in the outer-most scope must be declared as global.");
			} else if(getLookaheadToken().getStr().equals("procedure")) {
				////System.out.println("procedure");
				return parseProcedureDeclaration();
			} else if(getLookaheadToken().isTypeMarkReservedWordToken()) {
				////System.out.println("typemark");
				return parseVariableDeclaration(false);
			} else {
				////System.out.println("no declaration ahead");
				return null;
			}
		} else {
			////System.out.println("no declaration ahead");
			return null;
		}
	}

	/**
	 * Parse a list of statements. <br/>
	 * <br/>
	 * {@literal ( <statement> ';' )*}
	 * @throws Exception 
	 
	 */
	private ArrayList<Statement> parseStatementsList(boolean atLeastOne) throws Exception {
		
		ArrayList<Statement> statements = new ArrayList<Statement>();
		
		boolean foundAtLeastOne = false;
		boolean returnCalled = false;
		boolean warningGiven = false;
		while(true) { // Parse statements, if any
			Statement statement = parseStatement();
			if(statement != null) { // If statement parsed
				parseTokenByType(TokenTypes.SEMICOL, false); // Find ending semicolon
				statements.add(statement);
				foundAtLeastOne = true; // We have met our quota
				
				if(!warningGiven && returnCalled) { // Check to see if return statement called before return statement check
					warningGiven = true; // Set to true to prevent multiple warning messages
					getCompiler().getErrorHandler().warning("Unreachable statement(s).");
				}
				
				if(statement instanceof ReturnStatement)
					returnCalled = true; // Return has been seen for the first time
			} else if(atLeastOne && !foundAtLeastOne) // Needed to find at least one and didn't
				throw new SyntaxErrorException("Must have at least one statement.");
			else
				break; // End of list
		}
		return statements; // Return parsed statement nodes
	}
	
	/**
	 * Parse statement.<br/>
	 * <br/>
	 * {@literal <statement> ::=}<br/>
	 * {@literal <identifier_statement>}<br/>
	 * {@literal | <if_statement>}<br/>
	 * {@literal | <loop_statement>}<br/>
	 * {@literal | <return_statement>}<br/>
	 * {@literal <identifier_statement> ::=}<br/>
	 * {@literal <procedure_call>}<br/>
	 * {@literal | <assignment_statement>}<br/>
	 * @return
	 * @throws Exception 
	 
	 */
	private Statement parseStatement() throws Exception {
		// If statement
		if(getLookaheadToken().getStr().equals("if"))
			return parseIfStatement();
		// Loop statement
		else if(getLookaheadToken().getStr().equals("for"))
			return parseLoopStatement();
		// Identifier in first-set
		else if(getLookaheadToken().getType() == TokenTypes.ID) {
			Token idToken = parseTokenByType(TokenTypes.ID, false); // Identifier of...
			// Assignment statement
			if(getLookaheadToken().getType() == TokenTypes.ASSIGN || // ':='
					getLookaheadToken().getType() == TokenTypes.O_BRK) // '[' <expression> ']'
				return parseAssignmentStatement(idToken);
			// Procedure statement
			else if(getLookaheadToken().getType() == TokenTypes.O_PAR) {
				return parseProcedureCall(idToken);
			} else {
				throw new SyntaxErrorException("Expected ':=' for assignment statement or '(' for procedure call after '" + idToken.getStr() + "'.");
			}
		}
		// Return statement
		else if(parseTokenByReservedWord("return", true) != null) // Parse lookahead, but will use a label instead
			return new ReturnStatement(); // Use label instead
		// No statement parsed
		else
			return null;
	}

	/**
	 * Parse procedure declaration. <br/>
	 * {@literal <procedure_declaration> ::= <procedure_header> <procedure_body>}<br/>
	 * @return
	 * @throws Exception 
	 
	 */
	private Procedure parseProcedureDeclaration() throws Exception {
		
		// Procedure header
		parseTokenByReservedWord("procedure", false);
		String name = parseTokenByType(TokenTypes.ID, false, "Invalid procedure header. Missing procedure name.").getStr(); // Parse procedure name
		parseTokenByType(TokenTypes.O_PAR, false);
		ArrayList<Parameter> paramList = parseParameterList(); // Parse parameter list
		parseTokenByType(TokenTypes.C_PAR, false);
		
		ProcedureKey key = getSemanticAnalyzer().declareProcedureKey(new ProcedureKey(name, paramList));
		
		enterScope(true);
		
		// Procedure body
		//System.out.println("BEFORE PROCEDURE DECLARATIONS");
		ArrayList<Declaration> declarations = parseDeclarationsList();
		//System.out.println("AFTER PROCEDURE DECLARATIONS");
		
		parseTokenByReservedWord("begin", false);
		ArrayList<Statement> statements = parseStatementsList(false);
		parseTokenByReservedWord("end", false);
		parseTokenByReservedWord("procedure", false);
		
		exitScope();
		
		return new Procedure(key, declarations, statements);
	}

	/**
	 * Parse parameter list.<br/>
	 * <br/>
	 * {@literal <parameter_list> ::=}<br/>
	 * {@literal <parameter> , <parameter_list>}<br/>
	 * {@literal | <parameter>}<br/>
	 * @return
	 * @throws SyntaxErrorException 
	 * @throws IOException 
	 */
	private ArrayList<Parameter> parseParameterList() throws SyntaxErrorException, IOException {
		
		ArrayList<Parameter> paramList = new ArrayList<Parameter>();
		
		while(true) { // Consume all parameters in list
			Parameter parsedParam = parseParameter();
			if(parsedParam != null) { // Check for parameter
				paramList.add(parsedParam);
				if(parseTokenByType(TokenTypes.COMMA, true) == null) // Additional parameters optional
					break;
			} else // Else, continue
				break;
		}
		
		return paramList; // Return parameter list
	}

	/**
	 * Parse parameter.<br/>
	 * <br/>
	 * {@literal <parameter> ::= <variable_declaration> (in | out)}
	 * @return
	 * @throws SyntaxErrorException 
	 * @throws IOException 
	 */
	private Parameter parseParameter() throws SyntaxErrorException, IOException {
		
		if(!getLookaheadToken().isTypeMarkReservedWordToken()) {
			return null;
		} else {
			VariableDeclaration variableDeclaration = parseVariableDeclaration(true); // Check variable declaration
			//System.out.println("Parameter variable array size: " + variableDeclaration.getArraySize() + ", metaID: " + variableDeclaration.getInfo().getMetaKeyID());
			
			// Check for in or out
			Token inOut;
			if(getLookaheadToken().toString().equals("in") || getLookaheadToken().toString().equals("out")) {
				inOut = nextToken();
			} else
				throw new SyntaxErrorException("Parameter must be defined as 'in' or 'out'.");
			
			return new Parameter(variableDeclaration.getInfo(), inOut.getStr().equals("in") ? true : false);
		}
	}

	/**
	 * Parse variable declaration.<br/>
	 * <br/>
	 * {@literal <variable_declaration> ::= <type_mark> <identifier> [ '[' <array_size> ']' ]}
	 * @return
	 */
	private VariableDeclaration parseVariableDeclaration(boolean isParam) throws SyntaxErrorException, IOException {
		
		TokenTypes typeMark = parseTypeMark(); // Parse typemark
		String name = parseTokenByType(TokenTypes.ID, false, "Invalid variable declaration. Missing variable name.").getStr(); // Parse variable name
		
		// Optional array size specification
		int arraySize = -1;
		if(parseTokenByType(TokenTypes.O_BRK, true) != null) {
			arraySize = Integer.parseInt(parseTokenByType(TokenTypes.INTEGER, false, "Invalid array size. Expected integer value.").toString());
			if(arraySize < 0)
				throw new SyntaxErrorException("Array size must be 0 or greater.");
			parseTokenByType(TokenTypes.C_BRK, false);
		}
		
		//System.out.println(name + " array size is " + arraySize);
		VariableDeclaration varDeclaration = new VariableDeclaration(name, isParam, typeMark, arraySize);
		//System.out.println(name + " array size is " + varDeclaration.getArraySize());
		
		return getSemanticAnalyzer().declareVariableKey(varDeclaration);
	}

	/**
	 * Parse type mark.<br/>
	 * <br/>
	 * {@literal <type_mark> ::=}<br/>
	 * {@literal integer}<br/>
	 * {@literal | float}<br/>
	 * {@literal | bool}<br/>
	 * {@literal | string}<br/>
	 * @return
	 * @throws SyntaxErrorException 
	 * @throws IOException 
	 */
	private TokenTypes parseTypeMark() throws SyntaxErrorException, IOException {
		if(getLookaheadToken().isTypeMarkReservedWordToken())
			return Token.strToTypemark(nextToken().getStr());
		else
			throw new SyntaxErrorException("Expected type-mark.");
	}

	/**
	 * Parse procedure call.<br/>
	 * <br/>
	 * {@literal <procedure_call> ::= <identifier> '(' [ <argument_list> ] ')'}<br/>
	 * {@literal <argument_list> ::=}<br/>
	 * {@literal <expression> , <argument_list>}<br/>
	 * {@literal | <expression>}<br/>
	 * @return
	 * @throws Exception 
	 */
	private ProcedureCall parseProcedureCall(Token idToken) throws Exception {
		
		//System.out.println("Procedure call");
		
		parseTokenByType(TokenTypes.O_PAR, false); // Begin argument list
		
		ProcedureKey procedure = getSemanticAnalyzer().lookupProcedureKey(idToken.getStr());
		if(procedure != null) {
			ArrayList<Parameter> parameters = procedure.getParameters();
			ArrayList<ExpressionNode> arguments = new ArrayList<ExpressionNode>();
			
			// Optional argument list (required according to defined procedure header)
			for(Parameter parameter : parameters) { // For each of the found procedure key's parameters
				ExpressionNode parsedArg;
				if(parameter.isIn()) { // Can be any valid expression that matches the procedure key parameter type
					parsedArg = parseExpression(false);
					if(parsedArg.getEvaluatedType() == parameter.getTypemark())
						arguments.add(parsedArg);
					else
						throw new SyntaxErrorException("Expression must evaluated to " + Token.typeToStr(parameter.getTypemark()) + " for this 'in' parameter.");
				} else { // Out parameter, must be a variable
					parsedArg = parseExpression(true);
					if(parsedArg instanceof Factor && // Factor
						 ((Factor) parsedArg).getValue().getType() == TokenTypes.ID) { // And ID
						if(!((Factor) parsedArg).isNegate()) { // Negation implies value, not destination
							VariableKey varKey = getSemanticAnalyzer().lookupVariableKey(((Factor) parsedArg).getValue().getStr()); // Lookup variable in symbol table
							if(varKey != null && varKey.getTypemark() == parameter.getTypemark()) { // Matching evaluated typemark
								if(parameter.isArray() == varKey.isArray())
									arguments.add(parsedArg);
								else
									throw new SyntaxErrorException("Check to make sure out parameter is or is not an array."); 
							} else
								throw new SyntaxErrorException("Expression must evaluated to " + Token.typeToStr(parameter.getTypemark()) + " for this 'out' parameter.");
						} else
							throw new SyntaxErrorException("Negation implies value, not destination.");
					} else if(parsedArg instanceof Destination && parsedArg.getEvaluatedType() == parameter.getTypemark()) { // Destination instance that matches evaluated typemark
						Destination dest = (Destination) parsedArg;
						if(!dest.isNegate()) { // Negation implies value, not destination
							boolean matchArrayItem = dest.ptrToArrayItem() && !parameter.isArray();
							boolean matchArrayPtr = !dest.ptrToArrayItem() && dest.isArray() && parameter.isArray();
							boolean matchNotArray = !dest.isArray() && !parameter.isArray();
							//System.out.println("isArray: " + dest.isArray() + ", Param.isArray: " + parameter.isArray());
							//System.out.println(matchArrayItem + " " + matchArrayPtr + " " + matchNotArray + " " + parameter.getKeyName() + ":" + parameter.getArraySize() + ":" + parameter.isArray());
							if(matchArrayItem || matchArrayPtr || matchNotArray) { // Match compatibility concerning arrays
								arguments.add(parsedArg);
							} else {
								throw new SyntaxErrorException("Argument '" + dest.getVariableName() + "' incompatible with procedure header.");
							}
						} else {
							throw new SyntaxErrorException("Negation implies value, not destination.");
						}
					} else {
						//System.out.println(parsedArg instanceof Destination);
						throw new SyntaxErrorException("Expected variable name of type " + Token.typeToStr(parameter.getTypemark()) + " for 'out' argument.");
					}
				}
				
				if(parseTokenByType(TokenTypes.COMMA, true) == null) // Not additional arguments
					break;
				// Else, parse next argument
			}
			
			parseTokenByType(TokenTypes.C_PAR, false); // End argument list
			
			return new ProcedureCall(procedure, arguments);
		} else {
			throw new SyntaxErrorException("Procedure not defined.");
		}
	}

	/**
	 * Parse assignment statement.<br/>
	 * <br/>
	 * {@literal <assignment_statement> ::= <identifier> [ '[' <expression> ']' ] := <expression>}
	 * @return
	 * @throws Exception 
	 */
	private AssignmentStatement parseAssignmentStatement(Token idToken) throws Exception {
		//System.out.println("Assignment statement");
		Destination destination = parseDestination(false, idToken, false);
		
		//System.out.println("Got here 1");
		
		parseTokenByType(TokenTypes.ASSIGN, false); // Parse ':='
		// Assignment expression operand
		
		// Parse operand expression and semantically analyze
		ExpressionNode operandExpression = parseExpression(false);
		
		//System.out.println("Got here 2");
		
		if(!Token.typesAreCompatible(operandExpression.getEvaluatedType(), destination.getTypemark())) // Not compatible
			throw new SyntaxErrorException("Cannot explicitly cast " + Token.typeToStr(operandExpression.getEvaluatedType()) + " to " +  Token.typeToStr(destination.getTypemark()) + ".");
		
		//System.out.println("End Assignment statement");
			
		return new AssignmentStatement(destination, operandExpression); // Operand expression
	}
	
	/**
	 * Parses declaration or name being that they are identical, however, destination is in the assignment statement production,
	 * while name is in the expression and/or argument list
	 * @param negate
	 * @param idToken
	 * @param name TRUE if name, FALSE if not. Causes function to react to array symbols differently.
	 * @return
	 * @throws Exception
	 */
	private Destination parseDestination(boolean negate, Token idToken, boolean name) throws Exception {
		//System.out.println("parseDestination negation: " + negate);
		VariableKey idKey = getSemanticAnalyzer().lookupVariableKey(idToken.getStr());
		//System.out.println("LOOK UP VARIABLEKEY " + idToken.getStr() + " ISPARAM " + idKey.isArray());
		if(idKey != null) { // ID in symbol table
			if(idKey.isArray()) { // Lookedup symbol is array
				//System.out.println("isArray = true");
				Destination destination;
				if(name) {
					//System.out.println(idKey.getKeyName() + " is NAME");
					if(parseTokenByType(TokenTypes.O_BRK, true) != null) { // Brackets imply index / array item
						// Integer index expression
						ExpressionNode indexExpression = parseExpression(false); // Integer index expression parsed
						
						parseTokenByType(TokenTypes.C_BRK, false);
						// Set destination type other than NULL to imply array item
						destination = new Destination(negate, idKey, indexExpression);
						//System.out.println("factor " + destination.getVariableName() + " array index expression: " + destination.getArrayIndexExpression());
					} else { // No brackets imply array ptr
						// Save negation for later, set index expression other than NULL to set isArray to true but token type to NULL to imply array ptr
						destination = new Destination(negate, idKey);
						//System.out.println("destination pass array by pointer");
					}
				} else {
					//System.out.println(idKey.getKeyName() + " is DESTINATION");
					parseTokenByType(TokenTypes.O_BRK, false);
					// Integer index expression
					ExpressionNode indexExpression = parseExpression(false); // Integer index expression parsed
					
					parseTokenByType(TokenTypes.C_BRK, false);
					// Set destination type
					destination = new Destination(negate, idKey, indexExpression);
					//System.out.println("factor " + destination.getVariableName() + " array index expression: " + destination.getArrayIndexExpression());
				}
				//System.out.println("DEST before analysis: " + (destination.getArrayIndexExpression() == null));
				destination = getSemanticAnalyzer().lookupDestination(destination);
				//System.out.println("DEST after analysis: " + (destination.getArrayIndexExpression() == null));
				return destination;
			} else if(getLookaheadToken().getType() == TokenTypes.O_BRK) {
				throw new SyntaxErrorException("'" + idKey.getKeyName() + "' is not an array.  Remove array index.");
			} else { // Array item and no brackets: valid non-array destination definition
				//System.out.println("isArray = false");
				//System.out.println(idToken.getStr() + " no array index");
				return getSemanticAnalyzer().lookupDestination(new Destination(negate, idKey)); // ID mistaken for an array
			}
		} else {
			throw new SyntaxErrorException("Variable is not defined.");
		}
	}

	/**
	 * Parse if statement.<br/>
	 * <br/>
	 * {@literal <if_statement> ::=}<br/>
	 * {@literal if '(' <expression> ')' then ( <statement> ; )+}<br/>
	 * {@literal [ else ( <statement> ; )+ ]}<br/>
	 * {@literal end if}
	 * @return
	 * @throws Exception 
	 
	 */
	private IfStatement parseIfStatement() throws Exception {
		
		parseTokenByReservedWord("if", false);
		parseTokenByType(TokenTypes.O_PAR, false); 
		ExpressionNode conditional = null;
		try {
			conditional = parseExpression(false); // Conditional statement expression found
			if(conditional.getEvaluatedType() != TokenTypes.BOOL)
				throw new MissingExpressionException("");
		} catch (MissingExpressionException e) {
			throw new SyntaxErrorException("Expected boolean expression.");
		}
		parseTokenByType(TokenTypes.C_PAR, false);
		
		enterScope(false); // Enter first if scope
		
		parseTokenByReservedWord("then", false);
		ArrayList<Statement> ifBody = parseStatementsList(true);
		
		exitScope(); // Exit first if scope
		
		// Optional else's
		ArrayList<Statement> elseBody = null;
		if(parseTokenByReservedWord("else", true) != null) { // If else statement found
			
			enterScope(false);
			
			elseBody = parseStatementsList(true);
			
			exitScope();
		}
		
		parseTokenByReservedWord("end", false);
		parseTokenByReservedWord("if", false);
		
		return new IfStatement(conditional, ifBody, elseBody);
	}

	/**
	 * Parse loop statement.<br/>
	 * <br/>
	 * {@literal <loop_statement> ::=}<br/>
	 * {@literal for '(' <assignment_statement> ;}<br/>
	 * {@literal <expression> ')'}<br/>
	 * {@literal ( <statement> ; )*}<br/>
	 * {@literal end for}
	 * @return
	 * @throws Exception 
	 
	 */
	private LoopStatement parseLoopStatement() throws Exception {
		
		parseTokenByReservedWord("for", false);
		parseTokenByType(TokenTypes.O_PAR, false);
		// Assignment statement
		AssignmentStatement assignmentStatement = parseAssignmentStatement(parseTokenByType(TokenTypes.ID, false)); 
		parseTokenByType(TokenTypes.SEMICOL, false);
		// Conditional statement
		ExpressionNode conditional = null;
		try {
			conditional = parseExpression(false); // Conditional statement expression parsed
			if(conditional.getEvaluatedType() != TokenTypes.BOOL)
				throw new MissingExpressionException("");
		} catch (MissingExpressionException e) {
			throw new SyntaxErrorException("Expected boolean expression.");
		}
		parseTokenByType(TokenTypes.C_PAR, false);
		
		enterScope(false);
		
		// Loop body
		ArrayList<Statement> statements = parseStatementsList(false);
		
		parseTokenByReservedWord("end", false);
		parseTokenByReservedWord("for", false);
		
		exitScope();
		
		return new LoopStatement(assignmentStatement, conditional, statements);
	}
	
	/**
	 * Parse expression.
	 * 
	 * @return
	 * @throws Exception 
	 */
	private ExpressionNode parseExpression(boolean isOutArg) throws Exception {
		
		boolean notFound = false;
		if(parseTokenByReservedWord("not", true) != null) // First is 'not'
			notFound = true;
		
		try {
			ExpressionNode operandANode = parseArithOp(isOutArg);
			if(notFound) {
				return getSemanticAnalyzer().evaluateExpression(new Expression(true, operandANode));
			} else {
				// Evaluates & and | operators
				TokenTypes operator = null;
				if(getLookaheadToken().getType() == TokenTypes.AND ||
						getLookaheadToken().getType() == TokenTypes.OR)
					operator = nextToken().getType();
				
				if(operator != null) { // Operator found
					return getSemanticAnalyzer().evaluateExpression(new ExpressionNode(operandANode, parseArithOp(isOutArg), operator));
				} else { // No operator found
					return operandANode;
				}
			}
		} catch (MissingFactorException e) {
			throw new SyntaxErrorException("Expected expression.");
		}
	}
	
	private ExpressionNode parseArithOp(boolean isOutArg) throws Exception {
		
		ExpressionNode operandANode = parseRelation(isOutArg); // <relation>
		// Evaluates + and - operators
		TokenTypes operator = null;
		if(getLookaheadToken().getType() == TokenTypes.ADD ||
				getLookaheadToken().getType() == TokenTypes.SUB)
			operator = nextToken().getType();
		
		if(operator != null) {
			try {
				return getSemanticAnalyzer().evaluateExpression(new ExpressionNode(operandANode, parseRelation(isOutArg), operator));
			} catch (MissingFactorException e) {
				throw new SyntaxErrorException("Expected expression.");
			}
		} else { // Arith op evaluates to first operand
			return operandANode;
		}
	}
	
	private ExpressionNode parseRelation(boolean isOutArg) throws Exception {

		ExpressionNode operandANode = parseTerm(isOutArg); // <term>
		// Evaluates >=, <=, >, <, ==, and != operators
		TokenTypes operator = null;
		if(getLookaheadToken().getType() == TokenTypes.GT ||
				getLookaheadToken().getType() == TokenTypes.LT ||
				getLookaheadToken().getType() == TokenTypes.GTEQ ||
				getLookaheadToken().getType() == TokenTypes.LTEQ ||
				getLookaheadToken().getType() == TokenTypes.EQ ||
				getLookaheadToken().getType() == TokenTypes.NEQ)
			operator = nextToken().getType();
		
		if(operator != null) {
			try {
				return getSemanticAnalyzer().evaluateExpression(new ExpressionNode(operandANode, parseTerm(isOutArg), operator));
			} catch (MissingFactorException e) {
				throw new SyntaxErrorException("Expected expression.");
			}
		} else { // Relation evaluates to first operand
			return operandANode;
		}
	}
	
	private ExpressionNode parseTerm(boolean isOutArg) throws Exception {

		ExpressionNode operandANode = parseFactor(isOutArg); // <term>
		// Evaluates * and / operator
		TokenTypes operator = null;
		if(getLookaheadToken().getType() == TokenTypes.MUL ||
				getLookaheadToken().getType() == TokenTypes.DIV)
			operator = nextToken().getType();
		
		if(operator != null) {
			try {
				return getSemanticAnalyzer().evaluateExpression(new ExpressionNode(operandANode, parseFactor(isOutArg), operator));
			} catch (MissingFactorException e) {
				throw new SyntaxErrorException("Expected expression.");
			}
		} else { // Term evaluates to first operand
			return operandANode;
		}
	}
	
	private ExpressionNode parseFactor(boolean isOutArg) throws Exception {
		if(parseTokenByType(TokenTypes.O_PAR, true) != null) { // '(' <expression> ')'
			try {
				ExpressionNode expressionNode = parseExpression(isOutArg); // Expression parsed
				
				parseTokenByType(TokenTypes.C_PAR, false); // Skip ')' ending
				return getSemanticAnalyzer().evaluateExpression(expressionNode);
			} catch (MissingExpressionException e) {
				throw new MissingFactorException("Expected expression.");
			}
		} else if(parseTokenByType(TokenTypes.SUB, true) != null) { // '-' <name> OR '-' <number>
			if(getLookaheadToken().getType() == TokenTypes.INTEGER ||
					getLookaheadToken().getType() == TokenTypes.FLOAT) {
				
				return getSemanticAnalyzer().evaluateExpression(new Factor(nextToken(), true));
			} else if(getLookaheadToken().getType() == TokenTypes.ID) {
				if(!isOutArg) { // Pass by value
					Token idToken = nextToken();
					Destination destination = parseDestination(true, idToken, false); // Will never be name because
					
					return getSemanticAnalyzer().evaluateExpression(destination);
				} else { // Out argument (pointer) cannot be negated
					throw new SyntaxErrorException("Out argument cannot be negated.");
				}
			} else {
				throw new SyntaxErrorException("Expected identifier/name, integer, or float.");
			}
		} else if(getLookaheadToken().getType() == TokenTypes.ID) {
			Token idToken = nextToken();
			Destination destination = parseDestination(false, idToken, isOutArg ? true : false);
			//System.out.println("parseFactor(), destination=" + destination);
			
			return getSemanticAnalyzer().evaluateExpression(destination);
		} else if(getLookaheadToken().getType() == TokenTypes.INTEGER ||
				getLookaheadToken().getType() == TokenTypes.FLOAT ||
				getLookaheadToken().getType() == TokenTypes.STR ||
				getLookaheadToken().getType() == TokenTypes.BOOL) {
			return getSemanticAnalyzer().evaluateExpression(new Factor(nextToken(), false));
		} else {
			throw new MissingFactorException("Expected identifier/name, integer, float, or boolean.");
		}
	}
}

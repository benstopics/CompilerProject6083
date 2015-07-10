package com.benjamindward.compiler.emitter;

import java.util.ArrayList;

import com.benjamindward.compiler.errorhandler.ErrorHandler.SyntaxErrorException;
import com.benjamindward.compiler.semantic.abstractclasses.AssignmentStatement;
import com.benjamindward.compiler.semantic.abstractclasses.Destination;
import com.benjamindward.compiler.semantic.abstractclasses.Expression;
import com.benjamindward.compiler.semantic.abstractclasses.ExpressionNode;
import com.benjamindward.compiler.semantic.abstractclasses.Factor;
import com.benjamindward.compiler.semantic.abstractclasses.IfStatement;
import com.benjamindward.compiler.semantic.abstractclasses.LoopStatement;
import com.benjamindward.compiler.semantic.abstractclasses.Parameter;
import com.benjamindward.compiler.semantic.abstractclasses.ProcedureCall;
import com.benjamindward.compiler.semantic.abstractclasses.ReturnStatement;
import com.benjamindward.compiler.semantic.abstractclasses.Statement;

public class EmitterStatementList {
	private ArrayList<Statement> statements = new ArrayList<Statement>();
	private Emitter emitter;
	private EmitterStatementList parentList = null;
	private ArrayList<Parameter> procedureParams = new ArrayList<Parameter>();

	public ArrayList<Parameter> getProcedureParams() {
		if(parentList != null)
			return parentList.getProcedureParams();
		else
			return this.procedureParams;
	}

	public void setProcedureParams(ArrayList<Parameter> procedureParams) {
		this.procedureParams = procedureParams;
	}

	public EmitterStatementList getParentList() {
		return parentList;
	}

	public void setParentList(EmitterStatementList parentList) {
		this.parentList = parentList;
	}

	private int exprTempIndex = 0;
	
	public int getExprTempIndex() {
		if(parentList == null)
			return exprTempIndex;
		else
			return parentList.getExprTempIndex();
	}

	public void setExprTempIndex(int exprTempIndex) {
		if(parentList == null)
			this.exprTempIndex = exprTempIndex;
		else
			parentList.setExprTempIndex(exprTempIndex);
	}

	public int nextExprTempIndex() {
		if(parentList == null)
			return exprTempIndex++;
		else
			return parentList.nextExprTempIndex();
	}
	
	public Emitter getEmitter() {
		if(parentList == null)
			return emitter;
		else
			return parentList.getEmitter();
	}

	public void setEmitter(Emitter emitter) {
		if(parentList == null)
			this.emitter = emitter;
		else
			parentList.setEmitter(emitter);
	}

	private int tempIndex = 0;
	
	public int getTempIndex() {
		if(parentList == null)
			return tempIndex;
		else
			return parentList.getTempIndex();
	}

	public void setTempIndex(int tempIndex) {
		if(parentList == null)
			this.tempIndex = tempIndex;
		else
			parentList.setTempIndex(tempIndex);
	}

	public int nextTempIndex() {
		if(parentList == null) {
			return tempIndex++;
		} else
			return parentList.nextTempIndex();
	}
	
	public ArrayList<Statement> getStatements() {
		return statements;
	}

	public void setStatements(ArrayList<Statement> statements) {
		this.statements = statements;
	}
	
	public EmitterStatementList(Emitter emitter, ArrayList<Statement> statements) {
		setStatements(statements);
		setEmitter(emitter);
		setProcedureParams(new ArrayList<Parameter>());
	}

	/**
	 * Default constructor
	 * @param emitter
	 * @param statements
	 */
	public EmitterStatementList(Emitter emitter, ArrayList<Statement> statements, ArrayList<Parameter> params) {
		setStatements(statements);
		setEmitter(emitter);
		setProcedureParams(params);
	}
	
	/**
	 * Allows continuation of unnamed index count
	 * @param emitter
	 * @param statements
	 * @param lastTempIndex
	 */
	public EmitterStatementList(EmitterStatementList parentList, ArrayList<Statement> statements) {
		setStatements(statements);
		setParentList(parentList);
	}
	
	public ArrayList<String> emit() throws Exception {
		ArrayList<String> statementsLines = new ArrayList<String>();
		for(Statement statement : statements)
			statementsLines.add(emitStatement(statement));
		statementsLines.add("");
		return statementsLines;
	}
	
	private String emitStatement(Statement statement) throws Exception {
		if(statement instanceof AssignmentStatement) {
			// Cast assignment statement
			AssignmentStatement assignmentStatement = (AssignmentStatement) statement;
			
			return (new EmitterAssignmentStatement(this, assignmentStatement)).emit();
		} else if(statement instanceof ProcedureCall) {
			// Cast procedure call
			ProcedureCall procedureCall = (ProcedureCall) statement;
			
			return (new EmitterProcedureCall(this, procedureCall)).emit();
		} else if(statement instanceof IfStatement) {
			// Cast if statement
			IfStatement ifStatement = (IfStatement) statement;
			
			return (new EmitterIfStatement(this, ifStatement, getProcedureParams())).emit();
		} else if(statement instanceof LoopStatement) {
			// Cast loop statement
			LoopStatement loopStatement = (LoopStatement) statement;
			
			return (new EmitterLoopStatement(this, loopStatement, getProcedureParams())).emit();
		} else if(statement instanceof ReturnStatement) {
			// Add return statement goto line
			return Emitter.tab + "br label %return.stmt";
		} else {
			getEmitter().getCompiler().getErrorHandler().compilerError("Compiler error: instance is not an instance of a Statement subclass.");
			return null;
		}
	}
}

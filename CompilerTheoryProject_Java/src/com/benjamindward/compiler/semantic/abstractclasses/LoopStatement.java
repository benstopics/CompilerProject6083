package com.benjamindward.compiler.semantic.abstractclasses;

import java.util.ArrayList;

public class LoopStatement extends Statement {
	
	private AssignmentStatement assignmentStatement;
	private ExpressionNode conditional;
	private ArrayList<Statement> statements;
	
	public AssignmentStatement getAssignmentStatement() {
		return assignmentStatement;
	}

	public void setAssignmentStatement(AssignmentStatement assignmentStatement) {
		this.assignmentStatement = assignmentStatement;
	}

	public ExpressionNode getConditional() {
		return conditional;
	}

	public void setConditional(ExpressionNode conditional) {
		this.conditional = conditional;
	}

	public ArrayList<Statement> getStatements() {
		return statements;
	}

	public void setStatements(ArrayList<Statement> statements) {
		this.statements = statements;
	}

	public LoopStatement(AssignmentStatement assignmentStatement, ExpressionNode conditional, ArrayList<Statement> statements) {
		setAssignmentStatement(assignmentStatement);
		setConditional(conditional);
		setStatements(statements);
	}
}

package com.benjamindward.compiler.semantic.abstractclasses;

import java.util.ArrayList;

import com.benjamindward.compiler.semantic.ast.IRAbstractNode;

public class IfStatement extends Statement {
	private ExpressionNode conditional;
	private ArrayList<Statement> ifBody;
	private ArrayList<Statement> elseBody;

	public IfStatement(ExpressionNode conditional, ArrayList<Statement> ifBody, ArrayList<Statement> elseBody) {
		setConditional(conditional);
		setIfBody(ifBody);
		setElseBody(elseBody);
		
		getChildren().add(conditional);
		IRAbstractNode ifBodyNode = new IRAbstractNode("if body");
		ifBodyNode.getChildren().addAll(ifBody);
		getChildren().add(ifBodyNode);
		if(elseBody != null) {
			IRAbstractNode elseBodyNode = new IRAbstractNode("else body");
			elseBodyNode.getChildren().addAll(elseBody);
			getChildren().add(elseBodyNode);
		}
	}

	public ArrayList<Statement> getElseBody() {
		return elseBody;
	}

	public void setElseBody(ArrayList<Statement> elseBody) {
		this.elseBody = elseBody;
	}

	public ExpressionNode getConditional() {
		return conditional;
	}

	public void setConditional(ExpressionNode conditional) {
		this.conditional = conditional;
	}

	public ArrayList<Statement> getIfBody() {
		return ifBody;
	}

	public void setIfBody(ArrayList<Statement> ifBody) {
		this.ifBody = ifBody;
	}
	
	public String toString() {
		return "if ( ... ) then" + (getElseBody() != null ? ", else" : "");
	}
}

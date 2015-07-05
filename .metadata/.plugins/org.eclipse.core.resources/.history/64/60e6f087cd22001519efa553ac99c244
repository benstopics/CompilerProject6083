package com.benjamindward.compiler.semantic.abstractclasses;

import java.util.ArrayList;

import com.benjamindward.compiler.semantic.ast.IRAbstractNode;

public class AssignmentStatement extends Statement {
	private Destination destination;
	private ExpressionNode operand;
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public ExpressionNode getOperand() {
		return operand;
	}
	public void setOperand(ExpressionNode operand) {
		this.operand = operand;
	}
	
	public AssignmentStatement(Destination destination, ExpressionNode expressionNode) {
		setDestination(destination);
		setOperand(expressionNode);
		
		getChildren().add(destination);
		//System.out.println("Assignment statement expressionNode: " + expressionNode);
		getChildren().add(expressionNode);
	}
	
	public String toString() {
		return getDestination().getVariableName() + " :=";
	}
}

package com.benjamindward.compiler.semantic.abstractclasses;

public class Expression extends ExpressionNode {
	
	private boolean not = false;
	private ExpressionNode expressionNode;
	
	public boolean isNot() {
		return not;
	}

	public void setNot(boolean not) {
		this.not = not;
	}

	public ExpressionNode getExpressionNode() {
		return expressionNode;
	}

	public void setExpressionNode(ExpressionNode expressionNode) {
		this.expressionNode = expressionNode;
	}
	
	public String toString() {
		return isNot() ? "positive" : "negated";
	}
	
	public Expression(boolean not) {
		super();
		
		setNot(not);
	}

	public Expression(boolean not, ExpressionNode expressionNode) throws Exception {
		super(expressionNode);
		
		setNot(not);
		setExpressionNode(expressionNode);
		setEvaluatedType(expressionNode.getEvaluatedType());
	}
}

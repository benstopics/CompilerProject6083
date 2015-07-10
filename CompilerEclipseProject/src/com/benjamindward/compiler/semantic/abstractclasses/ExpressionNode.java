package com.benjamindward.compiler.semantic.abstractclasses;

import java.util.ArrayList;

import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;
import com.benjamindward.compiler.semantic.ast.IRAbstractNode;

public class ExpressionNode extends IRAbstractNode {

	private ExpressionNode operandA = null;
	private ExpressionNode operandB = null;
	private TokenTypes operator = TokenTypes.NULL;
	private TokenTypes evaluatedType = TokenTypes.NULL;

	public TokenTypes getEvaluatedType() {
		return evaluatedType;
	}

	public void setEvaluatedType(TokenTypes evaluatedType) {
		this.evaluatedType = evaluatedType;
	}

	public ExpressionNode() {
		super();
		
	}
	
	public ExpressionNode(ExpressionNode operandA) throws Exception {
		super();
		
		if(operandA instanceof Factor || operandA instanceof Expression)
			setOperandA(operandA);
		else
			throw new Exception("Compiler error: ExpressionNode(ExpressionNode operandA) constructor called, but operandA is not an instance of Factor.");
	}
	
	public ExpressionNode(ExpressionNode operandA, ExpressionNode operandB, TokenTypes operator) {
		super();
		
		setOperandA(operandA);
		setOperandB(operandB);
		setOperator(operator);
		
		getChildren().add(getOperandA());
		getChildren().add(getOperandB());
	}
	
	public ExpressionNode(ExpressionNode operandA, ExpressionNode operandB, TokenTypes operator, TokenTypes evaluatedType) {
		super();
		
		setOperandA(operandA);
		setOperandB(operandB);
		setOperator(operator);
		setEvaluatedType(evaluatedType);
		
		getChildren().add(getOperandA());
		getChildren().add(getOperandB());
	}
	
	public String toString() {
		return Token.typeToStr(getOperator());
	}
	
	public boolean isEvaluatedTypeNumber() {
		if(getEvaluatedType() == TokenTypes.INTEGER || getEvaluatedType() == TokenTypes.FLOAT)
			return true;
		else
			return false;
	}
	
	public boolean isTypeRelational() {
		if(getEvaluatedType() == TokenTypes.INTEGER || getEvaluatedType() == TokenTypes.BOOL)
			return true;
		else
			return false;
	}

	public ExpressionNode getOperandA() {
		return operandA;
	}

	public void setOperandA(ExpressionNode operandA) {
		this.operandA = operandA;
	}

	public ExpressionNode getOperandB() {
		return operandB;
	}

	public void setOperandB(ExpressionNode operandB) {
		this.operandB = operandB;
	}

	public TokenTypes getOperator() {
		return operator;
	}

	public void setOperator(TokenTypes operator) {
		this.operator = operator;
	}
	
}

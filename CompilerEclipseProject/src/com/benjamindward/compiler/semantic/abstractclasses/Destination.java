package com.benjamindward.compiler.semantic.abstractclasses;

import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;
import com.benjamindward.compiler.semantic.VariableKey;
import com.benjamindward.compiler.semantic.ast.IRAbstractNode;

public class Destination extends ExpressionNode {
	private ExpressionNode arrayIndexExpression = null;
	private boolean negate;
	private VariableKey varKey;
	
	public int getMetaKeyID() {
		return this.varKey.getMetaKeyID();
	}
	public void setMetaKeyID(int metaKeyID) {
		this.varKey.setMetaKeyId(metaKeyID);
	}
	public boolean isArray() {
		return this.varKey.isArray();
	}
	public int getArraySize() {
		return this.varKey.getArraySize();
	}
	public boolean isNegate() {
		return negate;
	}
	public void setNegate(boolean negate) {
		this.negate = negate;
	}
	public TokenTypes getTypemark() {
		return this.varKey.getTypemark();
	}
	public void setTypemark(TokenTypes typemark) {
		setEvaluatedType(typemark);
		this.varKey.setTypemark(typemark);
	}
	public String getVariableName() {
		return this.varKey.getKeyName();
	}
	public void setVariableName(String variableName) {
		this.varKey.setKeyName(variableName);
	}
	public ExpressionNode getArrayIndexExpression() {
		return arrayIndexExpression;
	}
	
	public boolean isParam() {
		return varKey.isParam();
	}
	
	public void setArrayIndexExpression(ExpressionNode arrayIndexExpression) {
		this.arrayIndexExpression = arrayIndexExpression;
	}
	
	public boolean ptrToArrayItem() {
		if(isArray() && getArrayIndexExpression() != null) // Destination is array, but expression is null typemark
			return true;
		else
			return false;
	}
	
	public Destination(boolean negate, VariableKey variableKey) {
		super(); // Leaf node
		
		setNegate(negate);
		this.varKey = variableKey;
		
		////System.out.println("Destination: isArray: " + isArray());
		
		setTypemark(variableKey.getTypemark());
		
		////System.out.println("class Destination: constructor -> " + toString());
	}
	
	public Destination(boolean negate, VariableKey variableKey, ExpressionNode arrayExpressionNode) {
		super(); // Leaf node
		
		setNegate(negate);
		setArrayIndexExpression(arrayExpressionNode);
		this.varKey = variableKey;
		
		////System.out.println("Destination constructor: " + getVariableName() + " " + (getArrayIndexExpression() == null ? "null" : "has expression") + " " + isNegate()
				//+ " varIsArray=" + isArray() + " ptrToArrayItem=" + ptrToArrayItem());
		
		if(getArrayIndexExpression() != null)
			getChildren().add(getArrayIndexExpression());
		
		setTypemark(variableKey.getTypemark());
	}
	
	public String toString() {
		return Token.typeToStr(getTypemark()) + " " + getVariableName()
				+ (getArrayIndexExpression() == null ? // Based on whether index was assigned or not
						(isArray() ? "[]" : "") // If never assigned, till signify that it is an array or not (error probably occurred)
						: "[ ... ]"); // If assigned
	}
}

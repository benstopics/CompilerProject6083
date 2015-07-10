package com.benjamindward.compiler.semantic.abstractclasses;

import java.util.ArrayList;

import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;

public class Factor extends ExpressionNode {
	private Token value;
	private boolean negate = false;
	
	public boolean isNegate() {
		return negate;
	}

	public void setNegate(boolean negate) {
		this.negate = negate;
	}

	public Factor(Token value, boolean negate) {
		super(); // Leaf node
		
		setValue(value);
		setNegate(negate);
		
		setEvaluatedType(getType());
	}
	
	/**
	 * If Token is an identifier, depend on constructor caller to provide type of identifier from a symbol table
	 * @param value
	 * @param negate
	 * @param evaluatedType
	 */
	public Factor(Token value, boolean negate, TokenTypes evaluatedType) {
		super(); // Leaf node
		
		setValue(value);
		setNegate(negate);
		
		setEvaluatedType(evaluatedType);
	}
	
	public TokenTypes getType() {
		return getValue().getType();
	}

	public Token getValue() {
		return this.value;
	}

	public void setValue(Token value) {
		this.value = value;
	}
	
	public String toString() {
		return getValue().getStr();
	}
	
	public boolean isExpressionOperator() {
    	return getValue().isExpressionOperator();
    }
    
    public boolean isArithOpOperator() {
    	return getValue().isArithOpOperator();
    }
    
    public boolean isRelationOperator() {
    	return getValue().isRelationOperator();
    }
    
    public boolean isTermOperator() {
    	return getValue().isTermOperator();
    }
}

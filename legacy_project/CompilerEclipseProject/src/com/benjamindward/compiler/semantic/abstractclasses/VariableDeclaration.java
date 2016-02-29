package com.benjamindward.compiler.semantic.abstractclasses;

import com.benjamindward.compiler.errorhandler.ErrorHandler.SyntaxErrorException;
import com.benjamindward.compiler.lexer.Token.TokenTypes;
import com.benjamindward.compiler.semantic.VariableKey;

public class VariableDeclaration extends Declaration {
	
	private VariableKey info;
	
	public VariableKey getInfo() {
		return info;
	}

	public void setInfo(VariableKey info) {
		this.info = info;
	}
	
	public VariableDeclaration(String name, boolean isParam, TokenTypes typeMark, int arraySize) throws SyntaxErrorException {
		setInfo(new VariableKey(name, isParam, 0, typeMark, arraySize));
	}

	public VariableDeclaration(String name, boolean isParam, int metaKeyID, TokenTypes typeMark, int arraySize) throws SyntaxErrorException {
		setInfo(new VariableKey(name, isParam, metaKeyID, typeMark, arraySize));
	}
	
	public TokenTypes getTypeMark() {
		return getInfo().getTypemark();
	}
	
	public int getArraySize() {
		return getInfo().getArraySize();
	}
	
	public boolean isArray() {
		return getInfo().isArray();
	}
	
	public String getVariableName() {
		return getInfo().getKeyName();
	}
	
	public String toString() {
		return getInfo().toString();
	}
}

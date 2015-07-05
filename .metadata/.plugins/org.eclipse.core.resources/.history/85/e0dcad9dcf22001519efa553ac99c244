package com.benjamindward.compiler.semantic;

import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;
import com.benjamindward.compiler.errorhandler.ErrorHandler.SyntaxErrorException;

public class VariableKey extends SymbolTableKey {
	private TokenTypes typeMark;
	private int arraySize = -1;
	private boolean param;
	
	public boolean isParam() {
		return param;
	}

	public void setParam(boolean param) {
		this.param = param;
	}

	public int getArraySize() {
		return arraySize;
	}

	public void setArraySize(int arraySize) {
		if(getKeyName().equals("result"))
			//System.out.println("VariableKey: RESULT ARRAY SIZE BEING ALTERED TO " + arraySize);
		this.arraySize = arraySize;
	}

	public TokenTypes getTypemark() {
		return typeMark;
	}

	public void setTypemark(TokenTypes typemark) {
		this.typeMark = typemark;
	}

	public VariableKey(String keyName, boolean isParam, int metaKeyID, TokenTypes typemark) {
		super(keyName, metaKeyID);
		
		setTypemark(typemark);
		setParam(isParam);
	}
	
	public VariableKey(String keyName, boolean isParam, int metaKeyID, TokenTypes typemark, int arraySize) throws SyntaxErrorException {
		super(keyName, metaKeyID);
		
		setTypemark(typemark);
		setArraySize(arraySize);
		setParam(isParam);
	}
	
	public VariableKey(VariableKey varKey) {
		super(varKey.getKeyName(), 0);
		
		setTypemark(varKey.getTypemark());
		setArraySize(varKey.getArraySize());
		setMetaKeyId(varKey.getMetaKeyID());
	}

	public boolean isArray() {
		if(getArraySize() < 0)
			return false;
		else
			return true;
	}
	
	public boolean equals(VariableKey key) {
		if(key.getKeyName().equals(getKeyName()) &&
				getTypemark() == key.getTypemark() &&
				getArraySize() == getArraySize()) {
			////System.out.println(key.getKeyName() + " equals " + getKeyName() + " and " + key.getTypemark() + " equals " + getTypemark());
			return true;
		} else {
			////System.out.println(key.getKeyName() + " not equal to " + getKeyName() + " or " + key.getTypemark() + " not equal to " + getTypemark());
			return false;
		}
	}
	
	public String toString() {
		return Token.typeToStr(getTypemark()) + " " + getKeyName() + (getArraySize() < 0 ? "" : "[" + getArraySize() + "]");
	}
}

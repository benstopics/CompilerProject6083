package com.benjamindward.compiler.emitter;

import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;

public class Temporary {
	private String tempName;
	private TokenTypes tempType;

	public TokenTypes getType() {
		return tempType;
	}

	public void setType(TokenTypes tempType) {
		this.tempType = tempType;
	}

	public String getName() {
		return tempName;
	}

	public void setName(String tempName) {
		this.tempName = tempName;
	}
	
	public Temporary(String tempName, TokenTypes tempType) {
		setName(tempName);
		setType(tempType);
	}
	
	public String toString() {
		return getName();
	}
}

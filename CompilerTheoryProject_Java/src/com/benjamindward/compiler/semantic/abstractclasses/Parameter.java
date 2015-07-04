package com.benjamindward.compiler.semantic.abstractclasses;

import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;
import com.benjamindward.compiler.semantic.VariableKey;

public class Parameter extends VariableKey {
	
	private boolean in;
	
	public boolean isIn() {
		return in;
	}

	public void setIn(boolean in) {
		this.in = in;
	}
	
	public Parameter(VariableKey varKey, boolean in) {
		super(varKey);
		
		setIn(in);
	}
	
	public boolean equals(Parameter parameter) {
		if(parameter.isIn() == this.isIn() &&
				parameter.getKeyName().equals(this.getKeyName()) &&
				parameter.getTypemark() == this.getTypemark())
			return true;
		else
			return false;
	}
	
	public String toString() {
		return Token.typeToStr(getTypemark()) + " " + getKeyName() + " " + (isIn() ? "in" : "out");
	}
}

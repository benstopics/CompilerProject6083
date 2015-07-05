package com.benjamindward.compiler.emitter;

public class StringConstant {
	private String value;
	private int charCount;
	private int index;
	
	public String getValue() {
		return value + "\\00";
	}

	public int getCharCount() {
		return charCount + 1;
	}

	public int getIndex() {
		return index;
	}

	public StringConstant(String value, int index) {
		System.out.println("StringConstant: " + value);
		this.value = value;
		this.index = index;
		this.charCount = value.length();
	}
	
	public String toString() {
		return "@.str" + getIndex();
	}
}

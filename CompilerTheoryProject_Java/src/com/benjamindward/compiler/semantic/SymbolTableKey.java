package com.benjamindward.compiler.semantic;

public class SymbolTableKey {
	private String keyName;
	private int metaKeyID = 0;
	
	public int getMetaKeyID() {
		return metaKeyID;
	}

	public void setMetaKeyId(int metaKeyID) {
		this.metaKeyID = metaKeyID;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	public SymbolTableKey(String keyName, int metaKeyID) {
		setKeyName(keyName);
		setMetaKeyId(metaKeyID);
	}
}

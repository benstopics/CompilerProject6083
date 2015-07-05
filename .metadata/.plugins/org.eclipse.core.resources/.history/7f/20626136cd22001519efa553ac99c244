package com.benjamindward.compiler.semantic;

import java.util.ArrayList;

public class SymbolTable {
	private ArrayList<SymbolTableKey> keys = new ArrayList<SymbolTableKey>();
	private SymbolTable parentSymbolTable;
	private SymbolTable childSymbolTable = null;
	private boolean codeBlock;
	
	public boolean isCodeBlock() {
		return codeBlock;
	}

	public void setCodeBlock(boolean codeBlock) {
		this.codeBlock = codeBlock;
	}

	public SymbolTable getChildSymbolTable() {
		return childSymbolTable;
	}

	public void setChildSymbolTable(SymbolTable childSymbolTable) {
		this.childSymbolTable = childSymbolTable;
	}

	public SymbolTable getParentSymbolTable() {
		return parentSymbolTable;
	}

	public void setParentSymbolTable(SymbolTable parentSymbolTable) {
		this.parentSymbolTable = parentSymbolTable;
	}
	
	public ArrayList<SymbolTableKey> getKeys() {
		return keys;
	}

	public void setKeys(ArrayList<SymbolTableKey> keys) {
		this.keys = keys;
	}

	public SymbolTable() {
		setParentSymbolTable(null);
		setChildSymbolTable(null);
	}
	
	public SymbolTable(SymbolTable parent, boolean codeBlock) {
		setParentSymbolTable(parent);
		setCodeBlock(codeBlock);
	}
	
	public void enterScope(boolean codeBlock) {
		if(getChildSymbolTable() == null)
			setChildSymbolTable(new SymbolTable(this, codeBlock)); // If main scope, enter new scope by creating child table
		else
			getChildSymbolTable().enterScope(codeBlock); // If not main scope, pass responsibility down to child
	}
	
	public void exitScope() {
		if(getChildSymbolTable() == null) {
			getParentSymbolTable().exitScope(); // If main scope, tell parent to disown them
		} else if(getChildSymbolTable().getChildSymbolTable() == null) { // Child has no children
			setChildSymbolTable(null);
		} else {
			getChildSymbolTable().exitScope(); // If child has children, pass responsibility down
		}
	}
	
	public SymbolTableKey lookupInCodeBlock(String keyName) {
		return getMostInnerScope().lookupInCodeBlockBottomUp(keyName);
	}
	
	public SymbolTableKey lookupInCodeBlockBottomUp(String keyName) {
		//System.out.println("# of children: " + getKeys().size());
		// Attempt to find key in all scopes
		SymbolTableKey foundKey = lookupMyScope(keyName); // Attempt to find inner most key match
		if(foundKey == null) {
			if(getParentSymbolTable() == null) // Outer most scope
				return null;
			else if(isCodeBlock())
				return null;
			else
				return getParentSymbolTable().lookupBottomUp(keyName);
		} else
			return foundKey; // Found, return it
	}
	
	public SymbolTableKey lookupAllScopes(String keyName) {
		return getMostInnerScope().lookupBottomUp(keyName);
	}
	
	public SymbolTableKey lookupBottomUp(String keyName) {
		//System.out.println("# of children: " + getKeys().size());
		// Attempt to find key in all scopes
		SymbolTableKey foundKey = lookupMyScope(keyName); // Attempt to find inner most key match
		if(foundKey == null) {
			if(getParentSymbolTable() == null) // Outer most scope
				return null;
			else
				return getParentSymbolTable().lookupBottomUp(keyName);
		} else
			return foundKey; // Found, return it
	}
	
	public SymbolTableKey lookupMyScope(String keyName) {
		SymbolTableKey foundKey = null;
		// Attempt to find key in local scope
		for(SymbolTableKey key : getKeys()) {
			if(key.getKeyName().equals(keyName)) {
				foundKey = key;
			}
		}
		// If key was found, will return the found key, if not, will return null
		return foundKey;
	}
	
	public SymbolTableKey lookupMostInnerScope(String keyName) {
		return getMostInnerScope().lookupMyScope(keyName);
	}
	
	public SymbolTable getMostInnerScope() {
		if(getChildSymbolTable() == null) // This is most inner scope
			return this;
		// If not most inner scope, find most inner scope amongst descendants
		SymbolTable nextInnerTable = getChildSymbolTable();
		while(true) {
			if(nextInnerTable.getChildSymbolTable() == null) // Found most inner scope
				return nextInnerTable;
			else
				nextInnerTable = nextInnerTable.getChildSymbolTable();
		}
	}
	
	public void addKey(SymbolTableKey key) {
		getKeys().add(key);
	}
	
	public void addKeyToCurrentScope(SymbolTableKey key) {
		getMostInnerScope().addKey(key);
	}
}

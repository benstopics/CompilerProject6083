package com.benjamindward.compiler.semantic.abstractclasses;

import java.util.ArrayList;

import com.benjamindward.compiler.semantic.ast.IRAbstractNode;

public class Program extends IRAbstractNode {
	private String name;
	private ProgramBody body;
	
	public Program(String name, ProgramBody body) {
		setName(name);
		setBody(body);
		
		ArrayList<IRAbstractNode> children = new ArrayList<IRAbstractNode>();
		children.add(body);
		setChildren(children);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProgramBody getBody() {
		return body;
	}

	public void setBody(ProgramBody body) {
		this.body = body;
	}
	
	public String toString() {
		return getName();
	}
	
	public void print() {
		super.print();
		//System.out.println(); // Make sure cursor is not at end of last printed line (due to nature of printing algorithm)
	}
}

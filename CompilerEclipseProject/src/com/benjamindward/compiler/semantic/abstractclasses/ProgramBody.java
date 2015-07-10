package com.benjamindward.compiler.semantic.abstractclasses;

import java.util.ArrayList;

import com.benjamindward.compiler.semantic.ast.IRAbstractNode;

public class ProgramBody extends IRAbstractNode {
	private ArrayList<Declaration> declarations;
	private ArrayList<Statement> statements;
	
	public ProgramBody(ArrayList<Declaration> declarations, ArrayList<Statement> statements) {
		setDeclarations(declarations);
		setStatements(statements);
		
		ArrayList<IRAbstractNode> children = new ArrayList<IRAbstractNode>();
		children.addAll(declarations);
		children.addAll(statements);
		setChildren(children);
	}

	public ArrayList<Declaration> getDeclarations() {
		return declarations;
	}

	public void setDeclarations(ArrayList<Declaration> declarations) {
		this.declarations = declarations;
	}

	public ArrayList<Statement> getStatements() {
		return statements;
	}

	public void setStatements(ArrayList<Statement> statements) {
		this.statements = statements;
	}
	
	public String toString() {
		return "program_body";
	}
}

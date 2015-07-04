package com.benjamindward.compiler.semantic.abstractclasses;

import java.util.ArrayList;

import com.benjamindward.compiler.semantic.ProcedureKey;
import com.benjamindward.compiler.semantic.ast.IRAbstractNode;

public class Procedure extends Declaration {
	
	private ProcedureKey procedureKey;
	private ArrayList<Declaration> declarations;
	private ArrayList<Statement> statements;
	
	public ProcedureKey getProcedureKey() {
		return procedureKey;
	}
	public void setProcedureKey(ProcedureKey procedureKey) {
		this.procedureKey = procedureKey;
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
	
	public Procedure(ProcedureKey procedureKey, ArrayList<Declaration> declarations, ArrayList<Statement> statements) {
		setProcedureKey(procedureKey);
		setDeclarations(declarations);
		setStatements(statements);
		
		getChildren().addAll(declarations);
		getChildren().addAll(statements);
	}
	
	public int getMetaKeyID() {
		return getProcedureKey().getMetaKeyID();
	}
	
	public String toString() {
		return getProcedureKey().toString();
	}
}

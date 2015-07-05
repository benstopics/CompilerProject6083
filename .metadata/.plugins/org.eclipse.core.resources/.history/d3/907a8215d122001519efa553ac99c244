package com.benjamindward.compiler.semantic.abstractclasses;

import java.util.ArrayList;

import com.benjamindward.compiler.semantic.ProcedureKey;

public class ProcedureCall extends Statement {
	private ProcedureKey procedureKey;
	public ProcedureKey getProcedureKey() {
		return procedureKey;
	}

	public void setProcedureKey(ProcedureKey procedureKey) {
		this.procedureKey = procedureKey;
	}

	private ArrayList<ExpressionNode> arguments;
	
	public ProcedureCall(ProcedureKey procedureKey, ArrayList<ExpressionNode> arguments) {
		
		setProcedureKey(procedureKey);
		setArguments(arguments);
	}

	public String getProcedureName() {
		return getProcedureKey().getKeyName();
	}

	public void setProcedureName(String procedureName) {
		setProcedureKey(procedureKey);
	}

	public ArrayList<ExpressionNode> getArguments() {
		return arguments;
	}

	public void setArguments(ArrayList<ExpressionNode> arguments) {
		this.arguments = arguments;
	}
}

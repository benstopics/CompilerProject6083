package com.benjamindward.compiler.semantic;

import java.util.ArrayList;

import com.benjamindward.compiler.semantic.abstractclasses.Parameter;

public class ProcedureKey extends SymbolTableKey {
	
	ArrayList<Parameter> parameters;
	ArrayList<VariableKey> inArgs;
	ArrayList<VariableKey> outArgs;
	
	public ArrayList<VariableKey> getInArgs() {
		return inArgs;
	}

	public void setInArgs(ArrayList<VariableKey> inArgs) {
		System.out.println("ProcedureKey: get in parameters");
		this.inArgs = inArgs;
	}

	public ArrayList<VariableKey> getOutArgs() {
		return outArgs;
	}

	public void setOutArgs(ArrayList<VariableKey> outArgs) {
		System.out.println("ProcedureKey: get out parameters");
		this.outArgs = outArgs;
	}

	public ArrayList<Parameter> getParameters() {
		System.out.println("ProcedureKey: get parameters");
		return parameters;
	}

	public void setParameters(ArrayList<Parameter> parameters) {
		System.out.println("ProcedureKey: set parameters");
		this.parameters = parameters;
		
		ArrayList<VariableKey> inArgs = new ArrayList<VariableKey>();
		ArrayList<VariableKey> outArgs = new ArrayList<VariableKey>();
		for(Parameter parameter : parameters) {
			if(parameter.isIn())
				inArgs.add(parameter);
			else
				outArgs.add(parameter);
		}
		
		setInArgs(inArgs);
		setOutArgs(outArgs);
	}
	
	/**
	 * Compares a list of arguments to the instance's argument list
	 * @param matchArgs List of arguments to compare to
	 * @return TRUE if parameters, FALSE if not
	 */
	public boolean equalArgumentList(ArrayList<Parameter> parameters) {
		if(getParameters().size() != parameters.size())
			return false;
		else {
			for(int i = 0; i < parameters.size(); i++)
				if(!parameters.get(i).equals(getParameters().get(i))) {
					System.out.println(parameters.get(i).getKeyName() + " not equal to " + getParameters().get(i).getKeyName());
					return false; // Return false if discrepancy found
				} else
					System.out.println(parameters.get(i).getKeyName() + " equal to " + getParameters().get(i).getKeyName());
			return true; // If no discrepancy found, return true
		}
	}
	
	public String toString() {
		String strParamList = "";
		for(int i = 0; i < getParameters().size(); i++) {
			if(i < getParameters().size() - 1)
				strParamList += getParameters().get(i);
		}
		return getKeyName() + "(" + strParamList + ")";
	}
	
	private void init(String name, int metaKeyID, ArrayList<Parameter> parameters) {
		setParameters(parameters);
	}
	
	public ProcedureKey(String name, ArrayList<Parameter> parameters) {
		super(name, 0);
		
		init(name, 0, parameters);
	}

	public ProcedureKey(String name, int metaKeyID, ArrayList<Parameter> parameters) {
		super(name, metaKeyID);
		
		init(name, metaKeyID, parameters);
	}
}

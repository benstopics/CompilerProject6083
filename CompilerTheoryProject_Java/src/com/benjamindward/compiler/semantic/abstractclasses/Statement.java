package com.benjamindward.compiler.semantic.abstractclasses;

import java.util.ArrayList;

import com.benjamindward.compiler.emitter.Emitter;
import com.benjamindward.compiler.emitter.EmitterStatementList;
import com.benjamindward.compiler.semantic.ast.IRAbstractNode;

public class Statement extends IRAbstractNode {
	public Statement() {
		
	}
	
	private EmitterStatementList statementList;
	private ArrayList<String> lines = new ArrayList<String>();
	
	public void addLine(boolean tab, String line) {
		if(tab) {
			getLines().add(Emitter.tab + line);
		} else {
			getLines().add(line);
		}
	}
	
	protected EmitterStatementList getStatementList() {
		return statementList;
	}

	protected void setStatementList(EmitterStatementList statementList) {
		this.statementList = statementList;
	}

	protected ArrayList<String> getLines() {
		return lines;
	}

	protected void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}
}

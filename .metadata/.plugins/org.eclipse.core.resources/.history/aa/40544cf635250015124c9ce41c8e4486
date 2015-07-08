package com.benjamindward.compiler.emitter;

import java.util.ArrayList;

import com.benjamindward.compiler.semantic.abstractclasses.IfStatement;
import com.benjamindward.compiler.semantic.abstractclasses.Parameter;

public class EmitterIfStatement extends EmitterStatement {

	public EmitterIfStatement(EmitterStatementList statementList, IfStatement ifStatement, ArrayList<Parameter> params) throws Exception {
		super(statementList);
		
		//System.out.println(getStatementList().getTempIndex());
		
		String conditionalTemp = getExprTempString(ifStatement.getConditional()).getName();
		
		int ifIdx = getStatementList().nextExprTempIndex();
		String cmpTemp = "%" + getStatementList().nextTempIndex();
		addLine(true, cmpTemp + " = load i1* " + conditionalTemp);
		addLine(true, "br i1 " + cmpTemp + ", label %if.then" + ifIdx + ", label %if.else" + ifIdx);
		addLine(true, "");
		addLine(false, "if.then" + ifIdx + ":");
		getLines().addAll(new EmitterStatementList(getStatementList(), ifStatement.getIfBody()).emit());
		addLine(true, "br label %if.end" + ifIdx);
		addLine(true, "");
		if(ifStatement.getElseBody() != null) {
			addLine(false, "if.else" + ifIdx + ":");
			getLines().addAll(new EmitterStatementList(getStatementList(), ifStatement.getElseBody()).emit());
			addLine(true, "br label %if.end" + ifIdx);
			addLine(true, "");
		}
		addLine(false, "if.end" + ifIdx + ":");
	}
}

package com.benjamindward.compiler.emitter;

import java.util.ArrayList;

import com.benjamindward.compiler.semantic.abstractclasses.LoopStatement;
import com.benjamindward.compiler.semantic.abstractclasses.Parameter;

public class EmitterLoopStatement extends EmitterStatement {
	public EmitterLoopStatement(EmitterStatementList statementList, LoopStatement loopStatement, ArrayList<Parameter> params) throws Exception {
		super(statementList);
		
		addLine(false, new EmitterAssignmentStatement(getStatementList(), loopStatement.getAssignmentStatement()).emit());
		int loopIdx = getStatementList().nextExprTempIndex();
		addLine(true, "br label %while.cond" + loopIdx);
		addLine(true, "");
		addLine(false, "while.cond" + loopIdx + ":");
		
		String conditionalTemp = getExprTempString(loopStatement.getConditional()).getName();
		addLine(true, "br i1 " + conditionalTemp + ", label %while.body" +  + loopIdx + ", label %while.end" + loopIdx);
		addLine(true, "");
		addLine(false, "while.body" + loopIdx + ":");
		//System.out.println(getStatementList().getTempIndex());
		getLines().addAll(new EmitterStatementList(getStatementList(), loopStatement.getStatements()).emit());
		//System.out.println(getStatementList().getTempIndex());
		addLine(true, "br label %while.cond" + loopIdx);
		addLine(true, "");
		addLine(false, "while.end" + loopIdx + ":");
		addLine(true, ";" + getStatementList().getTempIndex());
	}
}

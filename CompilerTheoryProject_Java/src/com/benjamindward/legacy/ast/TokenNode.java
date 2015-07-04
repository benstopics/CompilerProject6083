package com.benjamindward.legacy.ast;

import java.util.ArrayList;

import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;
import com.benjamindward.compiler.parser.Parser;
import com.benjamindward.compiler.parser.Parser.InternalNodeLabels;
import com.benjamindward.legacy.ast.AbstractSyntaxTree.IAbstractParseTreeNode;

public class TokenNode extends AbstractSyntaxTreeNode<Token> {
	
	private InternalNodeLabels label = InternalNodeLabels.NULL;
	private int lineNumber = 0;
	
	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public InternalNodeLabels getLabel() {
		return label;
	}

	public void setLabel(InternalNodeLabels label) {
		this.label = label;
	}

	public TokenNode() {
		setData(new Token());
		setChildren(new ArrayList<TokenNode>());
	}
	
	public ArrayList<TokenNode> getChildren() {
		ArrayList<TokenNode> children = new ArrayList<TokenNode>();
		for(IAbstractParseTreeNode<Token> child : super.getChildren()) {
			children.add((TokenNode)child);
		}
		return children;
	}
	
	/**
	 * Basic TokenNode constructor
	 * @param data Payload token
	 * @param parent Parent token node
	 */
	public TokenNode(Token data, TokenNode parent, int lineNumber) {
		super(data, parent);
		
		setLineNumber(lineNumber);
	}
	
	public TokenNode(InternalNodeLabels nodeLabel, TokenNode parent) {
		super(new Token(TokenTypes.INTERNAL, ""), parent);
		
		setLabel(nodeLabel);
	}
	
	@Override
	public String toString() {
		if(getLabel() != InternalNodeLabels.NULL)
			return getLabel().toString();
		else
			return super.toString();
	}
}

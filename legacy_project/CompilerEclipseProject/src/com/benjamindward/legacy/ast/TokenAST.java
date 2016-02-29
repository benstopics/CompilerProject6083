package com.benjamindward.legacy.ast;

import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;
import com.benjamindward.compiler.parser.Parser.InternalNodeLabels;

public class TokenAST extends AbstractSyntaxTree<Token, TokenNode> {
	public TokenAST(TokenNode root) {
		super(root);
	}
	
	public TokenAST() {
		super(new TokenNode(InternalNodeLabels.ROOT, new TokenNode()));
	}
	
	public void print() {
		getRoot().print();
	}
}

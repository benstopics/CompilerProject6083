package com.benjamindward.compiler.semantic.ast;

import com.benjamindward.compiler.lexer.Token;
import com.benjamindward.compiler.lexer.Token.TokenTypes;
import com.benjamindward.compiler.parser.Parser.InternalNodeLabels;
import com.benjamindward.legacy.ast.AbstractSyntaxTree;

public class IRAbstractAST extends AbstractSyntaxTree<Token, IRAbstractNode> {
	public IRAbstractAST(IRAbstractNode root) {
		super(root);
	}
	
	public IRAbstractAST() {
		super(new IRAbstractNode());
	}
}

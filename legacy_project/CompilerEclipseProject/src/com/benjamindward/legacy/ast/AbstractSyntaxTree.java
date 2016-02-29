package com.benjamindward.legacy.ast;

import java.util.ArrayList;

/**
 * 
 * @author Benjamin Ward 2015
 *
 * @param <D> Data class
 * @param <N> Node class
 */
public class AbstractSyntaxTree<D, N> {
    private N root;
    
    public N getRoot() {
		return root;
	}
	public void setRoot(N root) {
		this.root = root;
	}
    public AbstractSyntaxTree(N rootNode) {
        setRoot(rootNode);
    }
    
    /**
     * 
     * @author Benjamin Ward 2015
     *
     * @param <D> Data class
     */
    public interface IAbstractParseTreeNode<D> {
        public D getData();
    	public void setData(D data);
    	
    	public AbstractSyntaxTreeNode<D> getParent();
    	public void setParent(AbstractSyntaxTreeNode<D> parent);
    	
    	public ArrayList<? extends IAbstractParseTreeNode<D>> getChildren();
    	public void setChildren(ArrayList<? extends IAbstractParseTreeNode<D>> children);
    	public void addChild(AbstractSyntaxTreeNode<D> child);
    	public AbstractSyntaxTreeNode<D> getChild(int index);
    	public void addChildren(ArrayList<? extends IAbstractParseTreeNode<D>> children);
    	
    	public void print(ArrayList<Boolean> edgeColumns);
    	public String toString();
    }
}
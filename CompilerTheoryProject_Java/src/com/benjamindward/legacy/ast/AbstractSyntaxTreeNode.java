package com.benjamindward.legacy.ast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import com.benjamindward.legacy.ast.AbstractSyntaxTree.IAbstractParseTreeNode;

public class AbstractSyntaxTreeNode<T> implements IAbstractParseTreeNode<T> {
	private T data;
    private AbstractSyntaxTreeNode<T> parent;
    private ArrayList<AbstractSyntaxTreeNode<T>> children;
    
    public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public AbstractSyntaxTreeNode<T> getParent() {
		return parent;
	}
	public void setParent(AbstractSyntaxTreeNode<T> parent) {
		this.parent = parent;
	}
	
	public ArrayList<? extends IAbstractParseTreeNode<T>> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<? extends IAbstractParseTreeNode<T>> children) {
		this.children = (ArrayList<AbstractSyntaxTreeNode<T>>) children;
	}
	public void setChildrenParent(AbstractSyntaxTreeNode<T> parent) {
		for(int i=0;i<children.size();i++)
			children.get(i).setParent(parent);
	}
	public void addChild(AbstractSyntaxTreeNode<T> child) {
		children.add(child);
	}
	public void addChild(int index, AbstractSyntaxTreeNode<T> child) {
		children.add(index, child);
	}
	public AbstractSyntaxTreeNode<T> getChild(int index) {
		return (AbstractSyntaxTreeNode<T>) getChildren().get(index);
	}
	public void addChildren(ArrayList<? extends IAbstractParseTreeNode<T>> children) {
		for(IAbstractParseTreeNode<T> child : children)
			addChild((AbstractSyntaxTreeNode<T>) child);
	}
	
	public AbstractSyntaxTreeNode() {
	}
	public AbstractSyntaxTreeNode(T data, AbstractSyntaxTreeNode<T> parent) {
		setData(data);
		setParent(parent);
		setChildren(new ArrayList<AbstractSyntaxTreeNode<T>>());
	}
	
	public void print() {
		print(new ArrayList<Boolean>());
	}
	
	public void print(ArrayList<Boolean> edgeColumns) {
		// Print parent node
		System.out.print(toString() + " <- " + getParent().toString());
		
		// Click ENTER to continue...
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    //try {br.readLine();} catch (IOException e) {e.printStackTrace();}
		
		// Generate column string prefix
		String columnsPrefixStr = "";
		for(int i=0; i<edgeColumns.size(); i++) {
			if(edgeColumns.get(i))
				columnsPrefixStr += " |    ";
			else
				columnsPrefixStr += "      ";
		}
		
		// Print children
		if(getChildren().size() > 0) { // Stem
			
			edgeColumns.add(new Boolean(false)); // Add column before printing descendants
			
			for(int i=0;i<getChildren().size();i++) {
				String outputStr = "";
				boolean rightMostNode = false;
				if(i < getChildren().size()-1) {
					outputStr += columnsPrefixStr + " |--- "; // Still more to go, continue stem
				} else {
					outputStr += columnsPrefixStr + " `--- "; // Cut off stem
					rightMostNode = true;
				}
				System.out.print("\n" + outputStr); // Display node in console
				
				edgeColumns.set(edgeColumns.size()-1, !rightMostNode); // NOT rightMostNode, meaning: if not right-most node, print edge
				getChild(i).print(edgeColumns); // Manage edges
			}
			
			edgeColumns.remove(edgeColumns.size()-1); // Remove column now that descendants have been printed
		}
	}
	
	public String toString() {
		if(data == null)
			return "";
		else
			return data.toString();
	}
}
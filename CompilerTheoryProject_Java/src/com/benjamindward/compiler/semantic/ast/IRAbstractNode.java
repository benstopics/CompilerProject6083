package com.benjamindward.compiler.semantic.ast;

import java.util.ArrayList;


public class IRAbstractNode {
	
	private ArrayList<IRAbstractNode> children;
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<IRAbstractNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<IRAbstractNode> children) {
		this.children = children;
	}
	
	public IRAbstractNode() {
		setChildren(new ArrayList<IRAbstractNode>());
	}
	
	public IRAbstractNode(String label) {
		setChildren(new ArrayList<IRAbstractNode>());
		setLabel(label);
	}

	public IRAbstractNode(ArrayList<IRAbstractNode> children) {
		setChildren(children);
	}
	
	public String toString() {
		return label;
	}
	
	public void print() {
		print(new ArrayList<Boolean>());
	}
	
	public void print(ArrayList<Boolean> edgeColumns) {
		// Print parent node
		System.out.print(toString());
		
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
				getChildren().get(i).print(edgeColumns); // Manage edges
			}
			
			edgeColumns.remove(edgeColumns.size()-1); // Remove column now that descendants have been printed
		}
	}
}

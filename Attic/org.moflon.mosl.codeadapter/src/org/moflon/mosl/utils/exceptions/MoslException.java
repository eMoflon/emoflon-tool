package org.moflon.mosl.utils.exceptions;

import java.util.LinkedList;
import java.util.List;

import MocaTree.Attribute;
import MocaTree.Node;

public abstract class MoslException extends RuntimeException {

	private static final long serialVersionUID = -3449133203669863579L;
	
	private Node errorNode;
	
	protected String referencePath;
	
	private String fileName;
	
	protected MoslException(String message, String referencePath, Node errorNode, Throwable arg) {
		super(message,arg);
		this.setErrorNode(errorNode);
		this.referencePath = referencePath;
	}
	
	protected MoslException(String message, String path){
		this(message,path,null,null);
	}
	
	protected MoslException(String message, String path, Node errorNode){
		this(message, path, errorNode, null);
	}

	protected MoslException(String message, String path, Throwable arg){
		this(message, path, null, arg);
	} 
	
	public Node getErrorNode() {
		return errorNode;
	}

	public String getPath() {
		return referencePath;
	}
	
	protected void setErrorNode(Node errorNode){
		this.errorNode = errorNode;
		findFileName(errorNode);
	}
	
	private void findFileName(Node node){
		if(node == null)
			fileName = "";
		else{				
			if(node.getAttribute("fileName").size() > 0){
				fileName = "/" +node.getName();
				getFilePath(node.getParentNode());
			}
			else
				findFileName(node.getParentNode());				
		}
	}
	
	private void getFilePath(Node node){
		if(node==null)
			return;
		// is Mosl?
		List<Attribute> tmpAttributes=new LinkedList<>(node.getAttribute("name"));
		if(tmpAttributes.size() >0){
			String name = tmpAttributes.get(0).getValue();
			fileName="/" +name +fileName;
			if(name.compareTo("MOSL")!=0){
				getFilePath(node.getParentNode());
			}
			
				
		}
	}
	
	public String getFileName(){
		return fileName;
	}
}

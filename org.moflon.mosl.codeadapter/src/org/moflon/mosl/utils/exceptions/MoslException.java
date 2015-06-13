package org.moflon.mosl.utils.exceptions;

import MocaTree.Node;

public abstract class MoslException extends RuntimeException {

	private static final long serialVersionUID = -3449133203669863579L;
	
	private Node errorNode;
	
	private String path;
	
	protected MoslException(String message){
		super(message);
	}

	protected MoslException(String message, Throwable arg) {
		super(message,arg);
	}
	
	protected MoslException(String message, String path){
		super(message);
		this.path=path;
	}
	
	protected MoslException(String message, Node errorNode){
		super(message);
		this.errorNode=errorNode;
	}
	
	protected MoslException(String message, String path, Node errorNode){
		super(message);
		this.path=path;
		this.errorNode=errorNode;
	}

	protected MoslException(String message, Node errorNode, Throwable arg) {
		super(message,arg);
		this.errorNode=errorNode;
	}

	public Node getErrorNode() {
		return errorNode;
	}

	public String getPath() {
		return path;
	}
	
	
}

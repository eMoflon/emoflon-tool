package org.moflon.mosl.utils.exceptions;

import MocaTree.Node;

public class CanNotResolveCategoryException extends MoslException {

	private static final long serialVersionUID = 5729271481794352545L;
	
	public CanNotResolveCategoryException(String message, String path){
		super(message, path);
	}
	
	
	public CanNotResolveCategoryException(String message, String path, Node errorNode, Throwable arg){
		super(message, path, errorNode, arg);
	}
	
	
}

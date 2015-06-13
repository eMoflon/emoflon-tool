package org.moflon.mosl.utils;

import MocaTree.TreeElement;

public class CanNotAddTreeElementException extends Exception {


	private static final long serialVersionUID = -3668550433608591650L;
	
	public CanNotAddTreeElementException(TreeElement rootElement, TreeElement toAddElement){
		super(toAddElement.getClass().getName() + " cannot be Added to " + rootElement.getClass().getName() );
	}

}

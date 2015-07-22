package org.moflon.mosl.utils.exceptions;

public class MoslLoadException extends MoslException {
	
	private static final long serialVersionUID = -1398669230849833829L;
	
	public MoslLoadException(String message){
		super(message,null);
	}
	
	public MoslLoadException(String message, Throwable arg){
		super(message, null, arg);
	}
}

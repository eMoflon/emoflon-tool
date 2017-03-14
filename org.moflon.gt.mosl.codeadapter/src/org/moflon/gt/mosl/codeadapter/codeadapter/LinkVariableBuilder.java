package org.moflon.gt.mosl.codeadapter.codeadapter;

import org.gervarro.democles.specification.emf.Pattern;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;

public class LinkVariableBuilder {

	private static LinkVariableBuilder instance;
	
	private LinkVariableBuilder(){
		
	}
	
	public static LinkVariableBuilder getInstance(){
		if(instance == null)
			instance = new LinkVariableBuilder();
		return instance;
	}
	
	public void transformLinkVariable(LinkVariablePattern linkVar, Pattern pattern){
		
	}
}

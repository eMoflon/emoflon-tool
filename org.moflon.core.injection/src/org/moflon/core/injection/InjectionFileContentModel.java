package org.moflon.core.injection;

import java.util.List;

public class InjectionFileContentModel {
	
	private String className;
	private List<String> imports;
	private String members;
	private List<MethodModel> methods;
	
	public InjectionFileContentModel(String className, List<String> imports, String members, List<MethodModel> methods)
	{
		this.className = className;
		this.imports = imports;
		this.members = members;
		this.methods = methods;
	}
	
	public String getClassName()
	{
		return className;
	}
	
	public List<String> getImports()
	{
		return imports;
	}
	
	public String getMembers()
	{
		return members;
	}
	
	public List<MethodModel> getMethods() 
	{
		return methods;
	}
	
	public void setMethods(List<MethodModel> methods) 
	{
		this.methods = methods;
	}
}

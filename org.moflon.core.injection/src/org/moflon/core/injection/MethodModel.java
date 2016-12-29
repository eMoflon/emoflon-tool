package org.moflon.core.injection;

import java.util.List;

public class MethodModel
{
   private String name;
   private String parameters = "";
   private String body;
   
   private List<String> paramTypes;
   private List<String> paramNames;
   
   public MethodModel(String name, String body, List<String> paramTypes, List<String> paramNames) 
   {
      this.name = name;
      this.body = body;
      this.paramNames = paramNames;
      this.paramTypes = paramTypes;
      createMethodSignature();
   }
   
   @Override
   public boolean equals(Object obj)
   {
	   if (obj == null)
		   return false;
	   
	   MethodModel method = (MethodModel) obj;
	   
	   if (!getName().equals(method.getName()))
		   return false;	   
	   if (!getParamTypes().equals(method.getParamTypes()))
		   return false;
	   
	   return true;
   }
   
   public void createMethodSignature()
   {
	  final StringBuilder signature = new StringBuilder();
	   
	  boolean firstRun = true;
	  for (int i = 0; i < paramNames.size(); i++)
	  	{
		if(!firstRun)
			  signature.append(", "); 
		 firstRun = false;
		 
	     signature.append(getParamTypes().get(i));
	     signature.append(" ");
	     signature.append(getParamNames().get(i));
	  	}
	  
	  parameters = signature.toString();
   }
   
   public String getName() 
   {
      return name;
   }
   
   public String getParameters() 
   {
      return parameters;
   }
   
   public String getBody() 
   {
      return body;
   }
   
   public List<String> getParamTypes() 
   {
      return paramTypes;
   }
   
   public List<String> getParamNames() 
   {
      return paramNames;
   }
}
// erweitern für komplette injectionfile
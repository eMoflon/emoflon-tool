package org.moflon.maave.tool.smt.solverutil;

import org.stringtemplate.v4.STGroup;

public class TemplateHelper
{
  
   
   private static final String WSLOC=System.getenv("CurrentWSLoc");
   private static final String PREDICATE_GROUP_LOC="/org.moflon.maave.tool.smt.solverutil/templates/predicates.stg"; 
   
   public static STGroup getPredicateTemplateGroup()
   {
      STGroup stg = new STGroup('<', '>');
      stg.loadGroupFile("/","file:/"+WSLOC+PREDICATE_GROUP_LOC);
      return stg;
   }
}

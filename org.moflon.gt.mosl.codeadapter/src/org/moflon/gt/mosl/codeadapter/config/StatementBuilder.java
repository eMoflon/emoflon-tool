package org.moflon.gt.mosl.codeadapter.config;

import java.util.ArrayList;
import java.util.List;

import org.moflon.gt.mosl.codeadapter.statementrules.IStatementRule;
import org.moflon.gt.mosl.moslgt.MethodDec;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.Scope;

public class StatementBuilder
{   
   private final List<IStatementRule> transformationRules;

   private MethodDec currentMethod;

   public StatementBuilder()
   {
      transformationRules = new ArrayList<>();
   }

   public void registerTransformationRule(final IStatementRule rule)
   {
      this.transformationRules.add(rule);
   }

   public void transformStatement(final Statement stmnt, Scope scope, CFNode previosCFNode)
   {
      for (final IStatementRule rule : this.transformationRules)
      {
         if (rule.canHandle(stmnt))
         {
            rule.invoke(stmnt, scope, previosCFNode);
            break;
         }
      }
   }
   
   //TODO@rkluge: Such comments are dangerous because they are critical but nobody reads them
   /*
    * load the currentMethod before using transformStatement !!!!!!
    */
   public void loadCurrentMethod(MethodDec method)
   {
      currentMethod = method;
   }

   public MethodDec getCurrentMethod()
   {
      return currentMethod;
   }
}

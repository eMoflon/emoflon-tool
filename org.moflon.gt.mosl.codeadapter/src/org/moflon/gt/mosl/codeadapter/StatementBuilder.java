package org.moflon.gt.mosl.codeadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.moflon.gt.mosl.codeadapter.statementrules.IStatementRule;
import org.moflon.gt.mosl.moslgt.MethodDec;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.Scope;

public class StatementBuilder
{

   private static Map<Class<? extends Statement>, Function<Statement, Function<Scope, Consumer<CFNode>>>> statementRuleCache = new HashMap<>();
   private final List<IStatementRule> transformationRules;

   private static StatementBuilder instance;

   private MethodDec currentMethod;

   private StatementBuilder()
   {
      transformationRules = new ArrayList<>();
      statementRuleCache.clear();
   }

   public static StatementBuilder getInstance()
   {
      if (instance == null)
         instance = new StatementBuilder();
      return instance;
   }

   public static void setStatementRule(Class<? extends Statement> stmtClass, Function<Statement, Function<Scope, Consumer<CFNode>>> transformerRule)
   {
      statementRuleCache.put(stmtClass, transformerRule);
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

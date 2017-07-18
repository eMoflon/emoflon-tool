package org.moflon.gt.mosl.codeadapter.config;

import org.moflon.gt.mosl.codeadapter.VariableTransformer;

public class TransformationConfiguration
{
   private final PatternMatchingController patternMatchingController;
   
   private final PatternBuilder patternCreationController;
   
   private final StatementBuilder statementCreationController;
   
   private final ContextController contextController;
   
   private final ECoreAdapterController eCoreAdapterController;
   
   private final VariableTransformer variableTransformer;

   public TransformationConfiguration()
   {
      this.patternMatchingController = new PatternMatchingController();
      this.patternCreationController = new PatternBuilder();
      this.statementCreationController = new StatementBuilder();
      this.contextController = new ContextController();
      this.eCoreAdapterController = new ECoreAdapterController();
      this.variableTransformer = new VariableTransformer();
   }

   public PatternMatchingController getPatternMatchingController()
   {
      return patternMatchingController;
   }

   public PatternBuilder getPatternCreationController()
   {
      return this.patternCreationController;
   }
   
   public StatementBuilder getStatementCreationController()
   {
      return statementCreationController;
   }

   public ContextController getContextController()
   {
      return contextController;
   }

   public ECoreAdapterController getECoreAdapterController()
   {
      return this.eCoreAdapterController;
   }

   public VariableTransformer getVariableTransformer()
   {
      return this.variableTransformer;
   }
}

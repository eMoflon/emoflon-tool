package org.moflon.gt.mosl.codeadapter.config;

import org.moflon.gt.mosl.codeadapter.StatementBuilder;

public class TransformationConfiguration
{
   private final PatternMatchingController patternMatchingController;
   
   private final PatternCreationController patternCreationController;
   
   private final StatementBuilder statementCreationController;
   
   private final ContextController contextController;

   public TransformationConfiguration()
   {
      this.patternMatchingController = new PatternMatchingController();
      this.patternCreationController = new PatternCreationController();
      this.statementCreationController = new StatementBuilder();
      this.contextController = new ContextController();
   }

   public PatternMatchingController getPatternMatchingController()
   {
      return patternMatchingController;
   }

   public PatternCreationController getPatternCreationController()
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
}

package org.moflon.gt.mosl.codeadapter.config;

import java.util.Arrays;

import org.moflon.gt.mosl.codeadapter.CodeadapterTrafo;
import org.moflon.gt.mosl.codeadapter.VariableTransformer;
import org.moflon.gt.mosl.codeadapter.statementrules.ConditionStatementRule;
import org.moflon.gt.mosl.codeadapter.statementrules.DoLoopStatementRule;
import org.moflon.gt.mosl.codeadapter.statementrules.ForLoopStatementRule;
import org.moflon.gt.mosl.codeadapter.statementrules.ObjectVariableDefinitionRule;
import org.moflon.gt.mosl.codeadapter.statementrules.PatternStatementRule;
import org.moflon.gt.mosl.codeadapter.statementrules.ReturnStatementRule;
import org.moflon.gt.mosl.codeadapter.statementrules.WhileLoopStatementRule;
import org.moflon.gt.mosl.codeadapter.transformplanrules.BlackTransformPlanRule;
import org.moflon.gt.mosl.codeadapter.transformplanrules.GreenTransformPlanRule;
import org.moflon.gt.mosl.codeadapter.transformplanrules.RedTransformPlanRule;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;

public class TransformationConfiguration
{
   private final PatternMatchingController patternMatchingController;
   
   private final PatternBuilder patternCreationController;
   
   private final StatementBuilder statementCreationController;
   
   private final ContextController contextController;
   
   private final ECoreAdapterController eCoreAdapterController;
   
   private final VariableTransformer variableTransformer;
   
   private final CodeadapterTrafo codeadapterTransformator;

   public TransformationConfiguration()
   {
      this.patternMatchingController = new PatternMatchingController();
      this.patternCreationController = new PatternBuilder(this);
      this.statementCreationController = new StatementBuilder();
      this.contextController = new ContextController();
      this.eCoreAdapterController = new ECoreAdapterController();
      this.variableTransformer = new VariableTransformer();
      this.codeadapterTransformator = new CodeadapterTrafo(this);
      registerTransformationRules();
      register();
   }
   private void registerTransformationRules()
   {
      patternCreationController.addTransformPlanRule(PatternKind.BLACK, new BlackTransformPlanRule());
      patternCreationController.addTransformPlanRule(PatternKind.GREEN, new GreenTransformPlanRule());
      patternCreationController.addTransformPlanRule(PatternKind.RED, new RedTransformPlanRule());


   }
   private void register(){
      //@formatter:off
      Arrays.asList(
            new ReturnStatementRule(this),
            new PatternStatementRule(this),
            new ConditionStatementRule(this),
            new WhileLoopStatementRule(this),
            new ForLoopStatementRule(this),
            new DoLoopStatementRule(this),
            new ObjectVariableDefinitionRule(this)
            ).stream().forEach(rule -> this.statementCreationController.registerTransformationRule(rule));
      //@formatter:on
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
   
   public CodeadapterTrafo getCodeadapterTransformator(){
      return this.codeadapterTransformator;
   }
}

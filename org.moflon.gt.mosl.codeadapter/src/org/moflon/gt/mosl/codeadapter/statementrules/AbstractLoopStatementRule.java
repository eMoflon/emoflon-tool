package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.moslgt.LoopStatement;
import org.moflon.sdm.compiler.democles.validation.result.ResultFactory;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CompoundNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public abstract class AbstractLoopStatementRule<S extends LoopStatement> extends AbstractConditionStatementRule<S>
{
   @Override
   protected ValidationReport transformStatement(S stmnt, Scope scope, CFNode previosCFNode, final TransformationConfiguration transformationConfiguration)
   {
      Scope innerScope = DemoclesFactory.eINSTANCE.createScope();
      CompoundNode parent = this.updateCurrentNode(createCurrentCompoundNode());
      handlePattern(parent);
      innerScope.setParent(parent);
      parent.setScope(scope);
      transformationConfiguration.getStatementCreationController().transformStatement(stmnt.getLoopStartStatement(), innerScope, null, transformationConfiguration);
      return ResultFactory.eINSTANCE.createValidationReport();
   }

   protected abstract CompoundNode createCurrentCompoundNode();
}
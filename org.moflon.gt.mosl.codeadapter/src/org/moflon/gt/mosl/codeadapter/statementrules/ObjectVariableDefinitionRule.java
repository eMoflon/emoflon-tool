package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.moslgt.ObjectVariableStatement;
import org.moflon.sdm.compiler.democles.validation.result.ResultFactory;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.Scope;

public class ObjectVariableDefinitionRule extends AbstractNextStatementRule<ObjectVariableStatement>
{

   public ObjectVariableDefinitionRule(TransformationConfiguration trafoConfig)
   {
      super(trafoConfig);
   }

   @Override
   protected Class<ObjectVariableStatement> getStatementClass()
   {
      return ObjectVariableStatement.class;
   }

   @Override
   protected ValidationReport transformStatement(ObjectVariableStatement stmnt, Scope scope, CFNode previosCFNode)
   {
      this.getOrCreateVariable(scope, stmnt.getName(), stmnt.getType());
      return ResultFactory.eINSTANCE.createValidationReport();
   }

}

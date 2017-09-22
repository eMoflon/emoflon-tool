package org.moflon.gt.mosl.codeadapter.statementrules;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.codeadapter.utils.MOSLUtil;
import org.moflon.gt.mosl.codeadapter.utils.VariableVisibility;
import org.moflon.gt.mosl.moslgt.ObjectVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableStatement;
import org.moflon.gt.mosl.moslgt.ReturnStatement;
import org.moflon.sdm.compiler.democles.validation.result.ResultFactory;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.runtime.democles.Action;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.Scope;

public class ReturnStatementRule extends AbstractStatementRule<ReturnStatement>
{

   public ReturnStatementRule(TransformationConfiguration trafoConfig)
   {
      super(trafoConfig);
   }

   @Override
   protected Class<ReturnStatement> getStatementClass()
   {
      return ReturnStatement.class;
   }

   @Override
   protected ValidationReport transformStatement(ReturnStatement stmnt, Scope scope, CFNode previosCFNode)
   {
      org.moflon.sdm.runtime.democles.ReturnStatement rs = DemoclesFactory.eINSTANCE.createReturnStatement();
      if (previosCFNode != null)
      {
         previosCFNode.setNext(rs);
         rs.setId(previosCFNode.getId() + 1);
      } else
         rs.setId(1);
      scope.getContents().add(rs);
      
      ObjectVariableStatement returnValueObjectStmnt = stmnt.getReturnObject();
      
      Action action = null;
      if(returnValueObjectStmnt == null)
         action = DemoclesFactory.eINSTANCE.createAction();
      else{
         ObjectVariablePattern returnValueObject = MOSLUtil.convertOVStatementToOVPattern(returnValueObjectStmnt);
         Map<String, CFVariable> environment = getEnviroment(Arrays.asList(returnValueObject), scope);
         Map<String, VariableVisibility> visibility = getVisibility(Arrays.asList(returnValueObject), null);
         EClass eClass = getEClass();
         PatternInvocation invocation= this.transformationConfiguration.getPatternCreationController().createResultPatternInvocation(environment, visibility, eClass, returnValueObject);
         
         action = invocation;
      }
      
      rs.getActions().add(action);
      rs.setMainAction(action);
      return ResultFactory.eINSTANCE.createValidationReport();
   }

   @Override
   protected void invokeNextRule(ReturnStatement stmnt, Scope scope, CFNode previosCFNode)
   {
      // Nothing to do here since returns statement terminate the control flow.
   }
}

package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.Scope;

public class ObjectVariableDefinitionRule extends AbstractNextStatementRule<ObjectVariableDefinition> implements IHandleCFVariable
{

   @Override
   protected Class<ObjectVariableDefinition> getStatementClass()
   {
      return ObjectVariableDefinition.class;
   }

   @Override
   protected void transformStatement(ObjectVariableDefinition stmnt, Scope scope, CFNode previosCFNode)
   {
      this.getOrCreateVariable(scope, stmnt.getName(), stmnt.getType());
   }

}

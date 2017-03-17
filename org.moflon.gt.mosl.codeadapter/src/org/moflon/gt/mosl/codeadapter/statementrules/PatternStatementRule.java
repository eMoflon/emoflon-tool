package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.PatternStatement;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public class PatternStatementRule extends AbstractNextStatementRule<PatternStatement> implements IHandlePatternsInStatement
{

   @Override
   protected Class<PatternStatement> getStatementClass()
   {
      return PatternStatement.class;
   }

   @Override
   protected void transformStatement(PatternStatement stmnt, Scope scope, CFNode previosCFNode)
   {
      CFNode cfNode = this.updateCurrentNode(DemoclesFactory.eINSTANCE.createCFNode());
      cfNode.setScope(scope);
      this.handlePattern(stmnt.getParameters(), stmnt.getPattern(), cfNode, scope);

   }

}

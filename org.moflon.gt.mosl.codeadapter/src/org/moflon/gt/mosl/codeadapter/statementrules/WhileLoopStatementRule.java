package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.WhileLoopStatement;
import org.moflon.sdm.runtime.democles.CompoundNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;

public class WhileLoopStatementRule extends AbstractLoopStatementRule<WhileLoopStatement>
{

   @Override
   protected Class<WhileLoopStatement> getStatementClass()
   {
      return WhileLoopStatement.class;
   }

   @Override
   protected CompoundNode createCurrentCompoundNode()
   {
      return DemoclesFactory.eINSTANCE.createHeadControlledLoop();
   }

}

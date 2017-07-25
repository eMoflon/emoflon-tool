package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.moslgt.DoLoopStatement;
import org.moflon.sdm.runtime.democles.CompoundNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;

public class DoLoopStatementRule extends AbstractLoopStatementRule<DoLoopStatement>
{

   public DoLoopStatementRule(TransformationConfiguration trafoConfig)
   {
      super(trafoConfig);
   }

   @Override
   protected Class<DoLoopStatement> getStatementClass()
   {
      return DoLoopStatement.class;
   }
   
   @Override
   protected CompoundNode createCurrentCompoundNode()
   {
      return DemoclesFactory.eINSTANCE.createTailControlledLoop();
   }

}

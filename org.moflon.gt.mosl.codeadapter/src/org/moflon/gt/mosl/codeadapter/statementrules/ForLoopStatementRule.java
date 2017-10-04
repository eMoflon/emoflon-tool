package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.moslgt.ForLoopStatement;
import org.moflon.sdm.runtime.democles.CompoundNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;

public class ForLoopStatementRule extends AbstractLoopStatementRule<ForLoopStatement>
{

   public ForLoopStatementRule(TransformationConfiguration trafoConfig)
   {
      super(trafoConfig);
   }

   @Override
   protected Class<ForLoopStatement> getStatementClass()
   {
      return ForLoopStatement.class;
   }

   @Override
   protected CompoundNode createCurrentCompoundNode()
   {
      return DemoclesFactory.eINSTANCE.createForEach();
   }

}

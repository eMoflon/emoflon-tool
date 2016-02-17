package org.moflon.tie;

import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;


import org.moflon.tgg.algorithm.modelgenerator.ModelGenerator;
import org.moflon.tgg.algorithm.modelgenerator.controller.*;

import org.moflon.ide.visualization.dot.tgg.TggPackage;


public class TggModelGen extends SynchronizationHelper {

   public TggModelGen()
   {
      super(TggPackage.eINSTANCE, ".");
   }
	
	public static void main(String[] args) throws IOException {
		// Set up logging
		BasicConfigurator.configure();

		AbstractModelGenerationController controller = new DefaultModelGenController();
		
		/* 
		 Decide how to control model generation, stop either after a number of rule performs (better control over model size) 
		 or just a timeout (also corresponds roughly to model size)
		*/ 
		
		//controller.addContinuationController(new MaxRulePerformCounterController(1000));
      controller.addContinuationController(new TimeoutController(100));
      
      
      /* Add limits for rules if you wish to either speed up model generation, or influence how generated models look like */
      
      controller.setRuleSelector(new LimitedRandomRuleSelector()
            .addRuleLimit("FileToTGGRuleRule", 1)
            .addRuleLimit("LabelEntryToConstraint", 0)
            .addRuleLimit("RecordEntryToInlineConstraint", 0)
            .addRuleLimit("RecordEntryToAttrAssignment", 0)
            );

		ModelGenerator gen = new ModelGenerator(TggPackage.eINSTANCE, controller);
		gen.generate();
	}
}
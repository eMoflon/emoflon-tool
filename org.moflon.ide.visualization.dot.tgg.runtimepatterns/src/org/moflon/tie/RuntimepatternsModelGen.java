package org.moflon.tie;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.moflon.ide.visualization.dot.tgg.runtimepatterns.RuntimepatternsPackage;
import org.moflon.tgg.algorithm.modelgenerator.ModelGenerator;
import org.moflon.tgg.algorithm.modelgenerator.controller.AbstractModelGenerationController;
import org.moflon.tgg.algorithm.modelgenerator.controller.DefaultModelGenController;
import org.moflon.tgg.algorithm.modelgenerator.controller.LimitedRandomRuleSelector;
import org.moflon.tgg.algorithm.modelgenerator.controller.MaxRulePerformCounterController;
import org.moflon.tgg.algorithm.modelgenerator.controller.TimeoutController;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;


public class RuntimepatternsModelGen extends SynchronizationHelper{

   public RuntimepatternsModelGen()
   {
      super(RuntimepatternsPackage.eINSTANCE, ".");
   }
	
	public static void main(String[] args) throws IOException {
		// Set up logging
		BasicConfigurator.configure();

		AbstractModelGenerationController controller = new DefaultModelGenController();
		controller.addContinuationController(new MaxRulePerformCounterController(20));
      	controller.addContinuationController(new TimeoutController(5000));
      	controller.setRuleSelector(new LimitedRandomRuleSelector().addRuleLimit("<enter rule name>", 1));

		ModelGenerator gen = new ModelGenerator(RuntimepatternsPackage.eINSTANCE, controller);
		gen.generate();
	}
}
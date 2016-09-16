package org.moflon.tie;

import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;


import org.moflon.ide.visualization.dot.tgg.runtimepatterns.RuntimepatternsPackage;


public class RuntimepatternsConsistencyCheck extends SynchronizationHelper{

   public RuntimepatternsConsistencyCheck()
   {
      super(RuntimepatternsPackage.eINSTANCE, ".");
   }
	public static void main(String[] args) throws IOException {
		// Set up logging
        BasicConfigurator.configure();

		// Consistency Check
        RuntimepatternsConsistencyCheck helper = new RuntimepatternsConsistencyCheck();
        helper.loadSrc("instances/src.xmi");
		helper.loadTrg("instances/trg.xmi");
		helper.createCorrespondences();
		helper.saveCorr("instances/corr.xmi");
		helper.saveConsistencyCheckProtocol("instances/protocol.xmi");
	}
}
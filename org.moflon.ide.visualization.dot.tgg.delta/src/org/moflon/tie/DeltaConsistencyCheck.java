package org.moflon.tie;

import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;


import org.moflon.ide.visualization.dot.tgg.delta.DeltaPackage;


public class DeltaConsistencyCheck extends SynchronizationHelper{

   public DeltaConsistencyCheck()
   {
      super(DeltaPackage.eINSTANCE, ".");
   }
	public static void main(String[] args) throws IOException {
		// Set up logging
        BasicConfigurator.configure();

		// Consistency Check
        DeltaConsistencyCheck helper = new DeltaConsistencyCheck();
        helper.loadSrc("instances/src.xmi");
		helper.loadTrg("instances/trg.xmi");
		helper.createCorrespondences();
		helper.saveCorr("instances/corr.xmi");
	}
}
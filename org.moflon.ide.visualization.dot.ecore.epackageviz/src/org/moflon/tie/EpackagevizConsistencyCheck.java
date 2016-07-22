package org.moflon.tie;

import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;


import org.moflon.ide.visualization.dot.ecore.epackageviz.EpackagevizPackage;


public class EpackagevizConsistencyCheck extends SynchronizationHelper{

   public EpackagevizConsistencyCheck()
   {
      super(EpackagevizPackage.eINSTANCE, ".");
   }
	public static void main(String[] args) throws IOException {
		// Set up logging
        BasicConfigurator.configure();

		// Consistency Check
        EpackagevizConsistencyCheck helper = new EpackagevizConsistencyCheck();
        helper.loadSrc("instances/src.xmi");
		helper.loadTrg("instances/trg.xmi");
		helper.createCorrespondences();
		helper.saveCorr("instances/corr.xmi");
	}
}
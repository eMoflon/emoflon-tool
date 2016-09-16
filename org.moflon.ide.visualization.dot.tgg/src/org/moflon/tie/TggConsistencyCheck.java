package org.moflon.tie;

import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;


import org.moflon.ide.visualization.dot.tgg.TggPackage;


public class TggConsistencyCheck extends SynchronizationHelper{

   public TggConsistencyCheck()
   {
      super(TggPackage.eINSTANCE, ".");
   }
	public static void main(String[] args) throws IOException {
		// Set up logging
        BasicConfigurator.configure();

		// Consistency Check
        TggConsistencyCheck helper = new TggConsistencyCheck();
        helper.loadSrc("instances/src.xmi");
		helper.loadTrg("instances/trg.xmi");
		helper.createCorrespondences();
		helper.saveCorr("instances/corr.xmi");
		helper.saveConsistencyCheckProtocol("instances/protocol.xmi");
	}
}
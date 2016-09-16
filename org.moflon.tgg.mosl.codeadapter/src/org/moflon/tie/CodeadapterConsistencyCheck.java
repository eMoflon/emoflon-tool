package org.moflon.tie;

import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;


import org.moflon.tgg.mosl.codeadapter.CodeadapterPackage;


public class CodeadapterConsistencyCheck extends SynchronizationHelper{

   public CodeadapterConsistencyCheck()
   {
      super(CodeadapterPackage.eINSTANCE, ".");
   }
	public static void main(String[] args) throws IOException {
		// Set up logging
        BasicConfigurator.configure();

		// Consistency Check
        CodeadapterConsistencyCheck helper = new CodeadapterConsistencyCheck();
        helper.loadSrc("instances/src.xmi");
		helper.loadTrg("instances/trg.xmi");
		helper.createCorrespondences();
		helper.saveCorr("instances/corr.xmi");
		helper.saveConsistencyCheckProtocol("instances/protocol.xmi");
	}
}
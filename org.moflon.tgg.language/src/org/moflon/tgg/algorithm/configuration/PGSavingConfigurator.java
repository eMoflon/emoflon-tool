package org.moflon.tgg.algorithm.configuration;

import org.moflon.tgg.algorithm.datastructures.PrecedenceInputGraph;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;

public class PGSavingConfigurator implements Configurator {
	private SynchronizationHelper helper;
	private String path;

	public PGSavingConfigurator(SynchronizationHelper helper, String path) {
		this.helper = helper;
		this.path = path;
	}

	public void inspectPG(PrecedenceInputGraph pg) {
		helper.savePrecedenceGraph(pg, path);
	}
}

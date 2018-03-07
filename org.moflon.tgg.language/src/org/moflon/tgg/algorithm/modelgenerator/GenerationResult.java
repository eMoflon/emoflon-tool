package org.moflon.tgg.algorithm.modelgenerator;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;

public class GenerationResult {

	private Logger logger = Logger.getLogger(ModelGenerator.class);

	private ModelgenStats modelgenStats;

	private EObject srcModel, trgModel, corrModel;

	protected GenerationResult() {
	}

	protected void init(DataContainer dataContainer) {
		this.modelgenStats = dataContainer.getModelgenStats();

		if (dataContainer.getSrcTempOutputContainer().getPotentialRoots().size() == 1) {
			this.srcModel = dataContainer.getSrcTempOutputContainer().getPotentialRoots().get(0);
		} else if (dataContainer.getSrcTempOutputContainer().getPotentialRoots().size() > 1) {
			this.srcModel = dataContainer.getSrcTempOutputContainer();
		}
		if (dataContainer.getTrgTempOutputContainer().getPotentialRoots().size() == 1) {
			this.trgModel = dataContainer.getTrgTempOutputContainer().getPotentialRoots().get(0);
		} else if (dataContainer.getTrgTempOutputContainer().getPotentialRoots().size() > 1) {
			this.trgModel = dataContainer.getTrgTempOutputContainer();
		}
		this.corrModel = dataContainer.getCorrespondenceModel();

	}

	public void logResult() {

		logger.info("");
		logger.info("--- Model Generation Log ---");

		logger.info("performs: " + modelgenStats.getTotalRulePerformCount() + " / duration "
				+ modelgenStats.getTotalDuration() + "ms");
		logger.info("failures: " + modelgenStats.getTotalFailureCount() + " / failure duration: "
				+ modelgenStats.getTotalFailureDuration() + "ms");
		logger.info(modelgenStats.getSrcElementCount() + "/" + modelgenStats.getSrcConnectorCount()
				+ " nodes/edges created for source");
		logger.info(modelgenStats.getTrgElementCount() + "/" + modelgenStats.getTrgConnectorCount()
				+ " nodes/edges created for target");
		logger.info(modelgenStats.getCorrElementCount() + "/" + modelgenStats.getCorrConnectorCount()
				+ " nodes/edges created for correspondence");
		logger.info("");
		for (RulePerformStats stats : modelgenStats.getRulePerformStats().values()) {
			logger.info(stats.getRuleName());
			logger.info("   performs: " + stats.getPerformCount() + " / duration: " + stats.getTotalDuration() + "ms");
			logger.info("   failures: " + stats.getFailures() + " / failure duration: "
					+ stats.getTotalFailureDuration() + "ms");
		}
		logger.info("--- Model Generation Log ---");

	}

	public EObject getSrcModel() {
		return srcModel;
	}

	public void setSrcModel(EObject srcModel) {
		this.srcModel = srcModel;
	}

	public EObject getTrgModel() {
		return trgModel;
	}

	public void setTrgModel(EObject trgModel) {
		this.trgModel = trgModel;
	}

	public EObject getCorrModel() {
		return corrModel;
	}

	public void setCorrModel(EObject corrModel) {
		this.corrModel = corrModel;
	}

	public ModelgenStats getModelgenStats() {
		return this.modelgenStats;
	}

}

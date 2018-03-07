package org.moflon.tgg.algorithm.modelgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.algorithm.invocation.InvokeAxiomGenerateModel;
import org.moflon.tgg.algorithm.invocation.InvokeGenerateModel;
import org.moflon.tgg.algorithm.modelgenerator.controller.AbstractModelGenerationController;
import org.moflon.tgg.language.LanguagePackage;
import org.moflon.tgg.language.TGGRule;
import org.moflon.tgg.language.TripleGraphGrammar;
import org.moflon.tgg.language.modelgenerator.ModelgeneratorFactory;
import org.moflon.tgg.language.modelgenerator.RuleEntryContainer;
import org.moflon.tgg.language.modelgenerator.RuleEntryList;
import org.moflon.tgg.runtime.ModelgeneratorRuleResult;

public class ModelGenerator {

	private DataContainer dataContainer;

	private InvokeAxiomGenerateModel invokeAxiomGenerateModel;

	private InvokeGenerateModel invokeGenerateModel;

	private AbstractModelGenerationController controller;

	private Extractor extractor;

	private Logger logger = Logger.getLogger(ModelGenerator.class);

	private Boolean shouldPersist = false;

	List<EOperation> genModelOperations = new ArrayList<EOperation>();

	public static boolean writeLog;

	private Random random = new Random();

	public ModelGenerator(final EPackage tggPackage,
			final AbstractModelGenerationController abstractTGGModelGenController, final boolean persistModels,
			final boolean writeLog) {
		ModelGenerator.writeLog = writeLog;
		this.shouldPersist = persistModels;
		dataContainer = new DataContainer();
		invokeGenerateModel = new InvokeGenerateModel();
		invokeAxiomGenerateModel = new InvokeAxiomGenerateModel();
		extractor = new Extractor();

		this.controller = abstractTGGModelGenController;

		for (EOperation eOperation : extractor.extractGenerateModelMethods(tggPackage)) {
			addGenModelOperation(eOperation);
		}

		if (genModelOperations.size() == 0) {
			logger.warn("No model generator code has been found for the project: " + tggPackage.getName()
					+ " use TGG BuildMode.ALL or BuildMode.SIMULTANEOUS to create model generator code.");
		}

		try {
			String pathToXMIFile = "../" + tggPackage.getName() + "/model/" + tggPackage.getName() + ".tgg.xmi";
			eMoflonEMFUtil.init(LanguagePackage.eINSTANCE);
			TripleGraphGrammar tggXmi = (TripleGraphGrammar) eMoflonEMFUtil.loadModel(pathToXMIFile);

			for (TGGRule rule : tggXmi.getTggRule()) {
				dataContainer.createRuleAnalysis(rule);
			}
		} catch (Exception e) {
			logger.warn("The corresponding tgg model file (" + tggPackage.getName()
					+ ".tgg.xmi) cannot be found - model size statistics can not be collected");
		}

	}

	public ModelGenerator(final EPackage tggPackage,
			final AbstractModelGenerationController abstractTGGModelGenController) {
		this(tggPackage, abstractTGGModelGenController, true, true);
	}

	public GenerationResult generate() {

		List<EOperation> rulesToBeApplied = new ArrayList<EOperation>();
		rulesToBeApplied.addAll(genModelOperations);

		ModelgeneratorRuleResult lastResult = null;
		while (controller.continueGeneration(lastResult, dataContainer.getModelgenStats())) {

			EOperation genModelOperation = controller.getNextTggRule(rulesToBeApplied,
					dataContainer.getModelgenStats());
			if (genModelOperation == null) {
				logger.info("no next rule could be found to be used for model generation");
				break;
			}

			RulePerformData data = new RulePerformData();

			data.setCurrentGenModelOperation(genModelOperation);

			long duration = System.currentTimeMillis();

			if (genModelOperation.getEParameters().size() == 1) {
				lastResult = invokeAxiomGenerateModel.apply(data);
				duration = System.currentTimeMillis() - duration;
			} else if (genModelOperation.getEParameters().size() > 0) {
				data.setCurrentContainerParameter(computeContainerForMethod(genModelOperation));
				lastResult = invokeGenerateModel.apply(data);
				duration = System.currentTimeMillis() - duration;
			}
			if (lastResult != null && lastResult.isSuccess()) {
				lastResult.setRule(genModelOperation.getEContainingClass().getName());
				dataContainer.extractGeneratedObjects(lastResult, (int) duration);
			} else if (lastResult != null && !lastResult.isSuccess()) {
				dataContainer.getModelgenStats().addFailure(genModelOperation.getEContainingClass().getName(),
						duration);
			}
		}

		GenerationResult result = new GenerationResult();
		result.init(dataContainer);

		if (shouldPersist) {
			Persist.persist(dataContainer);
		}

		if (writeLog) {
			result.logResult();
		}

		return result;
	}

	private RuleEntryContainer computeContainerForMethod(final EOperation genModelOperation) {
		RuleEntryContainer container = org.moflon.tgg.language.modelgenerator.ModelgeneratorFactory.eINSTANCE
				.createRuleEntryContainer();
		for (int i = 1; i < genModelOperation.getEParameters().size(); i++) {
			List<EObject> typedEObjects = dataContainer
					.getTypedEObjects(genModelOperation.getEParameters().get(i).getEType());
			if (typedEObjects != null) {

				if (i == 1) {
					EObject randomObject = null;
					if (typedEObjects.size() == 1) {
						randomObject = typedEObjects.get(0);
					} else {
						randomObject = typedEObjects.get(random.nextInt(typedEObjects.size()));
					}

					RuleEntryList entryList = ModelgeneratorFactory.eINSTANCE.createRuleEntryList();
					entryList.getEntryObjects().add(randomObject);
					container.getRuleEntryList().add(entryList);
				} else {
					RuleEntryList entryList = ModelgeneratorFactory.eINSTANCE.createRuleEntryList();
					entryList.getEntryObjects().addAll(typedEObjects);
					container.getRuleEntryList().add(entryList);
				}
			}
		}
		return container;
	}

	public AbstractModelGenerationController getController() {
		return controller;
	}

	public void setController(final AbstractModelGenerationController controller) {
		this.controller = controller;
	}

	public Boolean getShouldPersist() {
		return shouldPersist;
	}

	public void setShouldPersist(final Boolean shouldPersist) {
		this.shouldPersist = shouldPersist;
	}

	public void addGenModelOperation(final EOperation generateModelOperation) {
		genModelOperations.add(generateModelOperation);
	}

}

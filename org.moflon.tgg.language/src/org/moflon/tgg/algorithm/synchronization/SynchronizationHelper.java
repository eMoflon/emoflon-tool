package org.moflon.tgg.algorithm.synchronization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.cardygan.ilp.api.Result;
import org.cardygan.ilp.api.solver.Solver;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.core.utilities.MoflonConventions;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.algorithm.ccutils.UserDefinedILPStrategy;
import org.moflon.tgg.algorithm.configuration.Configurator;
import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.PrecedenceInputGraph;
import org.moflon.tgg.algorithm.datastructures.SynchronizationProtocol;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.algorithm.delta.OnlineChangeDetector;
import org.moflon.tgg.algorithm.exceptions.LocalCompletenessException;
import org.moflon.tgg.language.algorithm.AlgorithmFactory;
import org.moflon.tgg.language.algorithm.TempOutputContainer;
import org.moflon.tgg.language.analysis.StaticAnalysis;
import org.moflon.tgg.runtime.AttributeDelta;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.DeltaSpecification;
import org.moflon.tgg.runtime.EMoflonEdge;
import org.moflon.tgg.runtime.RuntimeFactory;


/**
 * Responsible for checking if input data makes sense, inducing default deltas
 * for the batch case, and establishing all required components if necessary
 * with useful default values. This helper also invokes the synchronizer and
 * provides a simple interface to the user, hiding as much details as possible.
 * The generated stubs in a TGG project subclass this helper.
 *
 *
 */
public class SynchronizationHelper {
	// Members

	private Logger logger = Logger.getLogger(SynchronizationHelper.class);

	protected ResourceSet set;

	protected EObject src;

	protected EObject trg;

	protected CorrespondenceModel corr;

	protected StaticAnalysis rules;

	protected String projectName;

	protected Resource corrPackageResource;

	protected Configurator configurator;

	protected int ilpProblemVariablesCount;
	
	protected int ilpProblemChosenVariablesCount;

	protected int ilpProblemConstraintsCount;

	protected DeltaSpecification deltaSpec;

	protected DeltaSpecification sourceInconsistency;

	protected DeltaSpecification targetInconsistency;

	protected Delta delta;

	protected SynchronizationProtocol protocol;

	protected ConsistencyCheckPrecedenceGraph ccProtocol;

	protected Consumer<EObject> changeSrc;

	protected Consumer<EObject> changeTrg;

	protected TempOutputContainer tempOutputContainer;

	protected boolean batchMode = false;

	protected boolean verbose = false;
	protected boolean mute = false;

	private double runtimeOfCorrespondenceCreation;

	private double runtimeOfILPSolving;

	private double runtimeOfRemovingDeselectedCorrespondences;
	
	private Solver ilpSolver;
	
	private UserDefinedILPStrategy userDefinedILPstrategy;
	
	private Result ilpResult;

	// Setters

	private boolean isInTheCorrectResourceSet(final EObject model) {
		if (model.eIsProxy())
			throw new IllegalStateException("Model must not be a proxy: '" + model.toString() + "'.");
		return set == model.eResource().getResourceSet();
	}

	public void setSrc(final EObject sourceModel) {
		if (!isInTheCorrectResourceSet(sourceModel)) {
			throw new IllegalStateException("Source model must be in the resource set of your synchronization helper");
		}
		this.src = sourceModel;
	}

	public void setTrg(final EObject targetModel) {
		if (!isInTheCorrectResourceSet(targetModel)) {
			throw new IllegalStateException("Target model must be in the resource set of your synchronization helper");
		}
		this.trg = targetModel;
	}

	public void setCorr(final EObject corrModell) {
		if (!isInTheCorrectResourceSet(corrModell)) {
			throw new IllegalStateException(
					"Correspondence model must be in the resource set of your synchronization helper");
		}
		this.corr = (CorrespondenceModel) corrModell;
	}

	protected void setCorrPackage(final EPackage corrPackage) {
		projectName = corrPackage.getName();

		// Set mapping for correspondence package to ecore file
		corrPackageResource = corrPackage.eResource();
	}

	public void setRules(final StaticAnalysis rulesModel) {
		this.rules = rulesModel;
	}

	public void setConfigurator(final Configurator configurator) {
		this.configurator = configurator;
	}

	public Configurator getConfigurator() {
		return configurator;
	}

	public void setDelta(final Delta delta) {
		this.delta = delta;
	}

	public void setSynchronizationProtocol(final SynchronizationProtocol protocol) {
		this.protocol = protocol;
	}

	public void setChangeSrc(final Consumer<EObject> changeSrc) {
		this.changeSrc = changeSrc;
	}

	public void setChangeTrg(final Consumer<EObject> changeTrg) {
		this.changeTrg = changeTrg;
	}

	public void setVerbose(final boolean state) {
		verbose = state;
	}

	/**
	 * Sets the 'mute' state of this helper.
	 *
	 * If true, no log messages are printed, e.g., in case of errors.
	 *
	 * @param isMute
	 */
	public void setMute(final boolean isMute) {
		mute = isMute;
	}

	// Getters

	public EObject getSrc() {
		return src;
	}

	public EObject getTrg() {
		return trg;
	}

	public CorrespondenceModel getCorr() {
		return corr;
	}

	public ResourceSet getResourceSet() {
		return set;
	}

	public Resource getResourceFor(final EObject obj, final URI uri) {
		if (obj.eResource() == null) {
			Resource resource = set.createResource(uri);
			resource.getContents().add(obj);
		}

		return obj.eResource();
	}

	public SynchronizationProtocol getSynchronizationProtocol() {
		return protocol;
	}

	public Consumer<EObject> getChangeSrc() {
		return changeSrc;
	}

	public Consumer<EObject> getChangeTrg() {
		return changeTrg;
	}

	public Delta getDelta() {
		return delta;
	}

	// Defaults
	public SynchronizationHelper(final EPackage corrPackage, final String pathToProject) {
		this(corrPackage, pathToProject, eMoflonEMFUtil.createDefaultResourceSet());
	}

	public SynchronizationHelper(final EPackage corrPackage, final String pathToProject,
			final ResourceSet resourceSet) {
		this.set = resourceSet;
		setCorrPackage(corrPackage);
		loadRulesFromProject(pathToProject);

		configurator = new Configurator() {
		};
		changeSrc = (root -> {
		});
		changeTrg = (root -> {
		});
	}

	/**
	 * Use this constructor only if you prefer to prepare your own synchronization
	 * helper (e.g., via inheritance) and set the protected fields by yourself
	 */
	public SynchronizationHelper() {

	}

	// Integration
	protected void establishTranslationProtocol() {
		if (batchMode)
			protocol = new SynchronizationProtocol();
		else if (protocol == null)
			throw new IllegalStateException(
					"You cannot synchronize your delta without providing a translation protocol");
	}

	protected void establishGraphTriple() {
		if (corr == null) {
			corr = RuntimeFactory.eINSTANCE.createCorrespondenceModel();
			corr.setSource(src);
			corr.setTarget(trg);
			set.createResource(eMoflonEMFUtil.createFileURI(projectName + "/corr.xmi", false)).getContents().add(corr);
		}

		tempOutputContainer = AlgorithmFactory.eINSTANCE.createTempOutputContainer();
		final URI uri = eMoflonEMFUtil.createFileURI(projectName + "/tempOutputContainer.xmi", false);
		Resource resource = set.getResource(uri, false);
		if (resource == null)
			resource = set.createResource(uri);
		resource.getContents().add(tempOutputContainer);
	}

	protected void establishForwardDelta() {
		establishDelta(src, changeSrc);
	}

	protected void establishBackwardDelta() {
		establishDelta(trg, changeTrg);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Consumer<EObject> executeDeltaSpec(String pathToDelta) {
		DeltaSpecification localDeltaSpec = (DeltaSpecification) loadModel(pathToDelta);
		EcoreUtil.resolveAll(localDeltaSpec);
		deltaSpec = EcoreUtil.copy(localDeltaSpec);

		return (input) -> {
			// Added edges (nodes are indirectly added)
			for (EMoflonEdge ae : localDeltaSpec.getAddedEdges())
				performActionOnFeature(ae, (f, o) -> ((EList) ae.getSrc().eGet(f)).add(o), ae.getSrc()::eSet);

			// Edge deletion
			for (EMoflonEdge de : localDeltaSpec.getDeletedEdges())
				performActionOnFeature(de, (f, o) -> ((EList) de.getSrc().eGet(f)).remove(o),
						(f, o) -> de.getSrc().eUnset(f));

			// Node deletion
			for (EObject delObj : localDeltaSpec.getDeletedNodes())
				EcoreUtil.delete(delObj);

			// Attribute deltas
			for (AttributeDelta ac : localDeltaSpec.getAttributeChanges())
				ac.getAffectedNode().eSet(ac.getAffectedAttribute(),
						EcoreUtil.createFromString(ac.getAffectedAttribute().getEAttributeType(), ac.getNewValue()));
		};
	}

	private void performActionOnFeature(EMoflonEdge e, BiConsumer<EStructuralFeature, EObject> actionMany,
			BiConsumer<EStructuralFeature, EObject> actionOne) {
		EStructuralFeature feature = e.getSrc().eClass().getEStructuralFeature(e.getName());
		if (!feature.isDerived()) {
			if (feature.isMany()) {
				actionMany.accept(feature, e.getTrg());
			} else
				actionOne.accept(feature, e.getTrg());
		}

	}

	protected void establishDelta(final EObject input, final Consumer<EObject> change) {
		delta = new Delta();

		if (input == null)
			throw new IllegalStateException("Why are you trying to backward synchronize an empty input graph?");

		new OnlineChangeDetector(delta, input);
		change.accept(input);
		OnlineChangeDetector.removeDeltaListeners(input);

		sanityCheckDelta();

		if (noChangesWereMade() && protocol == null) {
			induceCreateDeltaForBatchTrafo(input);
			batchMode = true;
		} else {
			batchMode = false;
		}

		if (verbose)
			logger.info("\nI am going to propagate this as delta: \n" + delta
					+ "\nPlease check and ensure that it is as expected!");
	}

	protected void sanityCheckDelta() {
		if (delta != null && deltaSpec != null) {
			if (deltaSpec.getAddedNodes().size() != delta.getAddedNodes().size()) {
				logger.warn(
						"The added nodes in your delta specification do not match the added nodes in the resulting delta!");
			} else if (deltaSpec.getAddedEdges().size() > delta.getAddedEdges().size()) {
				logger.warn(
						"The added edges in your delta specification do not match the added edges in the resulting delta!");
			} else if (deltaSpec.getDeletedEdges().size() > delta.getDeletedEdges().size()) {
				logger.warn(
						"The deleted edges in your delta specification do not match the deleted edges in the resulting delta!");
			} else if (deltaSpec.getDeletedNodes().size() != delta.getDeletedNodes().size()) {
				logger.warn(
						"The deleted nodes in your delta specification do not match the deleted nodes in the resulting delta!");
			}
		}
	}

	protected boolean noChangesWereMade() {
		return delta.getAllAddedElements().size() == 0 && delta.getAllDeletedElements().size() == 0;
	}

	protected void prepare() {
		if (corrPackageResource == null)
			throw new IllegalArgumentException("Correspondence package must be set.");

		if (rules == null)
			throw new IllegalArgumentException("Rules must be set.");
	}

	protected void induceCreateDeltaForBatchTrafo(final EObject inputRoot) {
		this.delta = new Delta();

		inputRoot.eAllContents().forEachRemaining(node -> addNodeAndOutgoingEdges(node));
		addNodeAndOutgoingEdges(inputRoot);
	}

	protected StaticAnalysis determineLookupMethods() {
		StaticAnalysis sma = EcoreUtil.copy(rules);
		return sma;
	}

	@SuppressWarnings("unchecked")
	protected void addNodeAndOutgoingEdges(final EObject node) {
		delta.addNode(node);

		// Retrieve the edge to the container if there is one
		if (node.eContainmentFeature() != null && node.eContainmentFeature().getEOpposite() != null) {
			EMoflonEdge edge = RuntimeFactory.eINSTANCE.createEMoflonEdge();
			edge.setName(node.eContainmentFeature().getEOpposite().getName());
			edge.setSrc(node);
			edge.setTrg(node.eContainer());
			delta.addEdge(edge);
		}

		// Retrieve all EReference instances
		Set<EStructuralFeature> references = eMoflonEMFUtil.getAllReferences(node);

		// Iterate through all references
		for (EStructuralFeature reference : references) {
			if (!reference.isDerived()) {
				// Check if the reference to be handled is a containment edge
				// (i.e.,
				// node contains s.th.)
				if (reference.getUpperBound() != 1)
					// Edge is n-ary: edge exists only once, but points to many
					// contained EObjects
					for (EObject containedObject : (EList<EObject>) node.eGet(reference, true)) {
						// Create the wrapper and set the appropriate values
						EMoflonEdge edge = RuntimeFactory.eINSTANCE.createEMoflonEdge();
						edge.setName(reference.getName());
						edge.setSrc(node);
						edge.setTrg(containedObject);

						// Save edge as unprocessed
						delta.addEdge(edge);
					}
				// else a standard reference was found
				else {
					// Create the wrapper and set the appropriate values
					EMoflonEdge edge = RuntimeFactory.eINSTANCE.createEMoflonEdge();
					edge.setName(reference.getName());
					edge.setSrc(node);
					edge.setTrg((EObject) node.eGet(reference, true));

					// Save edge as unprocessed
					delta.addEdge(edge);
				}
			}

		}
	}

	public void integrateForward() {
		if (src == null)
			throw new IllegalArgumentException("Source model must be set");

		init();
		establishForwardDelta();
		establishTranslationProtocol();

		performSynchronization(new ForwardSynchronizer(corr, delta, protocol, configurator, determineLookupMethods(),
				tempOutputContainer));

		if (trg == null)
			trg = corr.getTarget();
	}

	public void createCorrespondences(boolean prepareDeltas) {
		
		if (ilpSolver == null)
			throw new RuntimeException(
					"You have not provided an ILP solver yet. Please see TODO in the generated main class for consistency checking");
		
		init();

		establishForwardDelta();
		Delta srcDelta = delta;
		establishBackwardDelta();
		Delta trgDelta = delta;

		establishGraphTriple();

		ccProtocol = new ConsistencyCheckPrecedenceGraph();

		ConsistencySynchronizer cs = new ConsistencySynchronizer(srcDelta, trgDelta, determineLookupMethods(), corr,
				ccProtocol);
		cs.setILPSolver(ilpSolver);
		cs.setUserDefinedILPStrategy(userDefinedILPstrategy);
		cs.createCorrespondences();

		int inconsistentElementCount = 0;
		inconsistentElementCount += cs.getInconsistentSourceElements().size();
		inconsistentElementCount += cs.getInconsistentTargetElements().size();

		if (inconsistentElementCount != 0)
			logger.info("Your models are inconsistent! Please save and check the source and target deltas.");
		else
			logger.info("Your models are consistent!");

		if (prepareDeltas) {
			sourceInconsistency = prepareDelta(cs.getInconsistentSourceElements());
			sourceInconsistency.setTargetModel(src);

			set.createResource(URI.createURI(src.eResource().getURI().toString() + ".delta.xmi")).getContents()
					.add(sourceInconsistency);

			targetInconsistency = prepareDelta(cs.getInconsistentTargetElements());
			targetInconsistency.setTargetModel(trg);

			set.createResource(URI.createURI(trg.eResource().getURI().toString() + ".delta.xmi")).getContents()
					.add(targetInconsistency);
		}

		ilpProblemVariablesCount = cs.getVariableCount();
		ilpProblemChosenVariablesCount = cs.getChosenVariableCount();
		ilpProblemConstraintsCount = cs.getConstraintCount();
		
		runtimeOfCorrespondenceCreation = cs.getRuntimeOfCorrespondenceCreation();
		runtimeOfILPSolving = cs.getRuntimeOfILPSolving();
		runtimeOfRemovingDeselectedCorrespondences = cs.getRuntimeOfRemovingDeselectedCorrespondences();
		
		ilpResult = cs.getResult();
	}

	protected void performSynchronization(final Synchronizer synchronizer) {
		try {
			synchronizer.synchronize();
		} catch (LocalCompletenessException e) {
			if (!mute)
				logger.error(e.getMessage(projectName, verbose));
		} finally {
			if (!mute)
				synchronizer.handleElementsNotTranslatedWarning(verbose);
		}
	}

	protected void init() {
		prepare();

		establishGraphTriple();
	}

	public void integrateBackward() {
		if (trg == null)
			throw new IllegalArgumentException("Target model must be set");

		init();
		establishBackwardDelta();
		establishTranslationProtocol();

		performSynchronization(new BackwardSynchronizer(corr, delta, protocol, configurator, determineLookupMethods(),
				tempOutputContainer));

		if (src == null)
			src = corr.getSource();
	}

	/* Persistency */

	private EObject loadModel(final String path) {
		Resource r = set.getResource(eMoflonEMFUtil.createFileURI(path, true), true);
		return r.getContents().get(0);
	}

	private EObject loadModelFromJar(final String pathToJar, final String pathToResource) {
		return set.getResource(eMoflonEMFUtil.createInJarURI(pathToJar, pathToResource), true).getContents().get(0);
	}

	public void loadTrg(final String path) {
		setTrg(loadModel(path));
	}

	public void loadSrc(final String path) {
		setSrc(loadModel(path));
	}

	public void loadCorr(final String path) {
		setCorr(loadModel(path));

		// Resolve to make sure that there are no proxies
		EcoreUtil.resolveAll(corr);
	}

	/**
	 * Loads the rules for the given project, located at the given path.
	 *
	 * The rules are loaded from the following location:
	 * '[pathToProject]/model/[rulesFileBaseName].sma.xmi' .
	 *
	 * @param pathToProject
	 *            path that contains the project
	 * @param rulesFileBaseName
	 *            the basename of the file containing the rules
	 */
	private void loadRulesFromProject(final String pathToProject, final String rulesFileBaseName) {
		setRules((StaticAnalysis) loadModel(pathToProject + "/model/"
				+ MoflonConventions.getDefaultNameOfFileInProjectWithoutExtension(rulesFileBaseName) + ".sma.xmi"));
	}

	/**
	 * Loads TGG rules from inside a Jar archive.
	 *
	 * @param pathToJarArchive
	 *            the absolute or relative path to the Jar file (e.g.,
	 *            "./libraries/Rules.jar")
	 * @param pathToResourceInJar
	 *            the absolute path of the rules package inside the Jar file (e.g.,
	 *            "/model/MyIntegration.sma.xmi")
	 */
	public void loadRulesFromJarArchive(final String pathToJarArchive, final String pathToResourceInJar) {
		setRules((StaticAnalysis) loadModelFromJar(pathToJarArchive, pathToResourceInJar));
	}

	/**
	 * Loads the rules for the given project, located at the given path.
	 *
	 * The rules are loaded from the following location:
	 * '[pathToProject]/model/[CorrespondencePackageName].sma.xmi' .
	 *
	 * @param pathToProject
	 *            path that contains the project
	 */
	private void loadRulesFromProject(final String pathToProject) {
		loadRulesFromProject(pathToProject, projectName);
	}

	public void loadSynchronizationProtocol(final String path) {
		org.moflon.tgg.runtime.PrecedenceStructure ps = (org.moflon.tgg.runtime.PrecedenceStructure) loadModel(path);

		// Resolve to make sure that there are no proxies
		EcoreUtil.resolveAll(ps);

		protocol = new SynchronizationProtocol();
		protocol.load(ps);
	}

	public void saveSrc(final String path) {
		if (src == null) {
			logger.error("Sorry, I don't have any source model to save.");
			return;
		}

		src.eResource().setURI(eMoflonEMFUtil.createFileURI(path, false));
		eMoflonEMFUtil.saveModel(src.eResource().getResourceSet(), src, path);
	}

	public void saveTrg(final String path) {
		if (trg == null) {
			logger.error("Sorry, I don't have any target model to save.");
			return;
		}

		trg.eResource().setURI(eMoflonEMFUtil.createFileURI(path, false));
		eMoflonEMFUtil.saveModel(trg.eResource().getResourceSet(), trg, path);
	}

	public void saveCorr(final String path) {
		if (corr == null) {
			logger.error("Sorry, I don't have any correspondence model to save.");
			return;
		}

		corr.eResource().setURI(eMoflonEMFUtil.createFileURI(path, false));
		eMoflonEMFUtil.saveModel(corr.eResource().getResourceSet(), corr, path);
	}

	public void saveSynchronizationProtocol(final String path) {
		if (protocol == null) {
			logger.error("Sorry, I don't have a synchronization protocol to save.");
			return;
		}

		org.moflon.tgg.runtime.PrecedenceStructure ps = protocol.save();
		set.createResource(eMoflonEMFUtil.createFileURI(path, false)).getContents().add(ps);
		eMoflonEMFUtil.saveModel(ps.eResource().getResourceSet(), ps, path);
	}

	public void saveConsistencyCheckProtocol(final String path) {
		if (ccProtocol == null) {
			logger.error("Sorry, I don't have a consistency check protocol to save.");
			return;
		}

		org.moflon.tgg.runtime.PrecedenceStructure ps = ccProtocol.save();
		set.createResource(eMoflonEMFUtil.createFileURI(path, false)).getContents().add(ps);
		eMoflonEMFUtil.saveModel(ps.eResource().getResourceSet(), ps, path);
	}

	public void savePrecedenceGraph(final PrecedenceInputGraph pg, final String path) {
		org.moflon.tgg.runtime.PrecedenceStructure pgAsPSs = pg.save();
		for (int i = 0; i < pgAsPSs.getTripleMatches().size(); i++) {
			pgAsPSs.getTripleMatches().get(i).setNumber(i);
		}

		set.createResource(eMoflonEMFUtil.createFileURI(path, false)).getContents().add(pgAsPSs);
		eMoflonEMFUtil.saveModel(pgAsPSs.eResource().getResourceSet(), pgAsPSs, path);
	}

	/**
	 * @deprecated Use {@link #saveInconsistentSourceDelta()}
	 */
	@Deprecated
	public void saveInconsistentSourceDelta(final String path) {
		saveInconsistentSourceDelta();
	}

	public void saveInconsistentSourceDelta() {
		try {
			sourceInconsistency.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

   /**
    * @deprecated Use {@link #saveInconsistentTargetDelta()}
    */
	@Deprecated
	public void saveInconsistentTargetDelta(final String path) {

		saveInconsistentTargetDelta();
	}

	public void saveInconsistentTargetDelta() {
		try {
			targetInconsistency.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private DeltaSpecification prepareDelta(Collection<EObject> elements) {
		DeltaSpecification ds = RuntimeFactory.eINSTANCE.createDeltaSpecification();
		ArrayList<EObject> nodes = new ArrayList<>();
		for (EObject elt : elements) {
			if (elt instanceof EMoflonEdge) {
				EMoflonEdge edge = (EMoflonEdge) elt;
				ds.getAddedEdges().add(edge);
				try {
					performActionOnFeature(edge, (f, o) -> ((EList<?>) edge.getSrc().eGet(f)).remove(o),
							(f, o) -> edge.getSrc().eUnset(f));
				} catch (Exception e) {
				}

			}

			else
				nodes.add(elt);
		}
		ds.getAddedNodes().addAll(nodes);
		return ds;
	}

	public int getIlpProblemVariablesCount() {
		return ilpProblemVariablesCount;
	}
	
	public int getIlpProblemChosenVariablesCount() {
		return ilpProblemChosenVariablesCount;
	}

	public int getIlpProblemConstraintsCount() {
		return ilpProblemConstraintsCount;
	}

	public double getRuntimeOfCorrespondenceCreation() {
		return runtimeOfCorrespondenceCreation;
	}

	public double getRuntimeOfILPSolving() {
		return runtimeOfILPSolving;
	}

	public double getRuntimeOfRemovingDeselectedCorrespondences() {
		return runtimeOfRemovingDeselectedCorrespondences;
	}
	
	public void setILPSolver(Solver ilpSolver) {
		this.ilpSolver = ilpSolver;
	}
	
	public void setUserDefinedILPStrategy(UserDefinedILPStrategy userDefinedILPstrategy) {
		this.userDefinedILPstrategy = userDefinedILPstrategy;
	}
	
	public Result getILPResult() {
		return ilpResult;
	}

}

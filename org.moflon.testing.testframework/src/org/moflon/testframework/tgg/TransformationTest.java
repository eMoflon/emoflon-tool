package org.moflon.testframework.tgg;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Assert;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.algorithm.configuration.Configurator;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;
import org.moflon.tgg.language.algorithm.ApplicationTypes;

/**
 * A common base class for integration tests.
 * 
 * With the name of the given correspondence package, for example
 * "BlockCodeAdapter", test data is located: input models
 * (/resources/tggLanguageTestData/in/BlockCodeAdapter) and expected models
 * (/resources/tggLanguageTestData/in/BlockCodeAdapter).
 * 
 * The created model is compared to the expected model and for each test, the
 * output model, the translation protocol as well as the correspondence model
 * are stored to /resources/tggLanguageTestData/out/BlockCodeAdapter.
 * 
 */
public abstract class TransformationTest {
	private static final Logger logger = Logger.getLogger(TransformationTest.class);

	protected String expectedPath = "resources/tggLanguageTestData/expected/";

	protected String inPath = "resources/tggLanguageTestData/in/";

	protected String outPath = "resources/tggLanguageTestData/out/";

	protected String integrationName;

	protected EPackage sourcePackage;

	protected EPackage corrPackage;

	protected EPackage targetPackage;

	protected SynchronizationHelper helper;

	public TransformationTest(final EPackage sourcePackage, final EPackage corrPackage, final EPackage targetPackage) {
		this.integrationName = corrPackage.getName();
		this.sourcePackage = sourcePackage;
		this.corrPackage = corrPackage;
		this.targetPackage = targetPackage;
		helper = new SynchronizationHelper(corrPackage, "../" + corrPackage.getName());
	}

	public void setConfigurator(final Configurator configurator) {
		helper.setConfigurator(configurator);
	}

	public String getExpectedPath() {
		return expectedPath + integrationName + "/";
	}

	public String getFullInpath() {
		return inPath + integrationName + "/";
	}

	public String getFullOutpath() {
		return outPath + integrationName + "/";
	}

	public String getOutPath() {
		return outPath + integrationName + "/";
	}

	public void integrate(final String testCaseName) {
		ApplicationTypes direction = determineDirection(testCaseName);
		integrate(testCaseName, direction);
	}

	/**
	 * Transforms the model corresponding to the given test case in the given
	 * direction.
	 */
	public void integrate(final String testCaseName, final ApplicationTypes direction) {
		try {
			// Load input model
			setInputModel(direction, testCaseName);
			reportInputModelLoaded();

			// Perform transformation
			EObject created = performTransformation(direction);
			reportTransformationCompleted();

			// Postprocess
			postProcessing(created);
			reportPostProcessingCompleted();

			// Save ouput model
			if (created != null) {
				saveOutput(testCaseName, created);
				reportOutputModelSaved();
			}

			compareWithExpected(testCaseName, direction, created);

		} catch (IOException e) {
			Assert.fail("IOException during Integration!");
			LogUtils.error(logger, e);
		} catch (InterruptedException e) {
			Assert.fail("IOException during compare of created and expected!");
			LogUtils.error(logger, e);
		}
	}

	/**
	 * Post processing for the created model, override in subclasses if necessary.
	 * 
	 * @param created
	 */
	public void postProcessing(final EObject created) {

	}

	/**
	 * Returns the input root package, depending on the given direction.
	 * 
	 * For FWD, this is the source root package; for BWD, this is the target root
	 * package.
	 * 
	 */
	public EPackage getInputPackage(final ApplicationTypes direction) {
		return direction == ApplicationTypes.FORWARD ? sourcePackage : targetPackage;
	}

	/**
	 * Returns the output root package, depending on the given direction.
	 * 
	 * For FWD, this is the target root package; for BWD, this is the source root
	 * package.
	 * 
	 */
	public EPackage getOutputPackage(final ApplicationTypes direction) {
		return direction == ApplicationTypes.FORWARD ? targetPackage : sourcePackage;
	}

	public String getExpectedpath() {
		return expectedPath;
	}

	public String getInpath() {
		return inPath;
	}

	public String getOutpath() {
		return outPath;
	}

	public String getIntegrationName() {
		return integrationName;
	}

	public EPackage getSourcePackage() {
		return sourcePackage;
	}

	public EPackage getCorrPackage() {
		return corrPackage;
	}

	public EPackage getTargetPackage() {
		return targetPackage;
	}

	public SynchronizationHelper getHelper() {
		return helper;
	}

	public void setHelper(final SynchronizationHelper helper) {
		this.helper = helper;
	}

	public abstract void compare(EObject expected, EObject created) throws InterruptedException;

	public abstract EObject performTransformation(ApplicationTypes direction) throws IOException;

	public abstract void wrapUp();

	protected void reset() {
		helper = new SynchronizationHelper(corrPackage, "../");
	}

	/**
	 * Compares the created model with the expected model for the given
	 * transformation direction.
	 *
	 * The expected model is loaded from the conventional folder for expected models
	 * based on the test case name (see
	 * {@link #loadExpected(String, ApplicationTypes)}).
	 */
	protected void compareWithExpected(final String testCaseName, final ApplicationTypes direction,
			final EObject created) throws InterruptedException {

		// Test with expected model
		EObject expected = loadExpected(testCaseName, direction);
		if (expected == null) {
			Assert.fail("no expected model");
		} else if (created == null) {
			Assert.fail("no created model");
		} else {
			compare(expected, created);
			wrapUp();
		}

	}

	protected void reportOutputModelSaved() {

	}

	protected void reportPostProcessingCompleted() {

	}

	protected void reportTransformationCompleted() {

	}

	protected void reportInputModelLoaded() {

	}

	protected void saveOutput(final String testCaseName, final EObject created) {
		eMoflonEMFUtil.saveModel(eMoflonEMFUtil.createDefaultResourceSet(), created,
				getFullOutpath() + testCaseName + ".xmi");
	}

	protected void setInputModel(final ApplicationTypes direction, final String testCaseName) {
		if (direction == ApplicationTypes.FORWARD) {
			helper.setSrc(eMoflonEMFUtil.loadModel(getFullInpath() + testCaseName + ".xmi", helper.getResourceSet()));
		} else {
			helper.setTrg(eMoflonEMFUtil.loadModel(getFullInpath() + testCaseName + ".xmi", helper.getResourceSet()));
		}
	}

	protected void performIntegration(final ApplicationTypes direction) {
		if (direction == ApplicationTypes.FORWARD) {
			helper.integrateForward();
		} else {
			helper.integrateBackward();
		}
	}

	/**
	 * Determines the transformation direction from the test case name.
	 * 
	 * If the test case name contains 'FWD' ('BWD') the direction is assumed to be
	 * forward (backward).
	 */
	protected ApplicationTypes determineDirection(final String testCaseName) {
		if (testCaseName.contains("FWD")) {
			return ApplicationTypes.FORWARD;
		} else if (testCaseName.contains("BWD")) {
			return ApplicationTypes.BACKWARD;
		} else {
			throw new RuntimeException(
					"Test Case name \"" + testCaseName + "\" is invalid, it does not contain \"FWD\" or \"BWD\"!");
		}
	}

	/**
	 * Loads the expected output for the given test case.
	 * 
	 * The given direction determines the meta-model to be used for loading.
	 */
	protected EObject loadExpected(final String testCaseName, final ApplicationTypes direction) {
		try {
			Resource resource = helper.getResourceSet()
					.getResource(eMoflonEMFUtil.createFileURI(getExpectedPath() + testCaseName + ".xmi", true), true);
			return resource.getContents().get(0);
		} catch (Exception e) {
			return null;
		}

	}
}

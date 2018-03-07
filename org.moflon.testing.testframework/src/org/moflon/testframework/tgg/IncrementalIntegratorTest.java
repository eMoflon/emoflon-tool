package org.moflon.testframework.tgg;

import java.io.IOException;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.tgg.algorithm.delta.Delta;

public class IncrementalIntegratorTest extends IntegratorTest {
	public IncrementalIntegratorTest(EPackage sourcePackage, EPackage corrPackage, EPackage targetPackage) {
		super(sourcePackage, corrPackage, targetPackage);

		expectedPath = "resources/tggLanguageTestDataIncremental/expected/";
		inPath = "resources/tggLanguageTestDataIncremental/in/";
		outPath = "resources/tggLanguageTestDataIncremental/out/";
	}

	public void init() throws IOException {

	}

	public void setInputModelDelta(Delta delta) {
		helper.setDelta(delta);
	}

	/**
	 * Stores the source, correspondence, and target model in the 'full output path'
	 * using the testCaseName as common prefix.
	 * 
	 * @see #getFullInpath()
	 */
	protected void saveOutput(String testCaseName) {
		helper.saveSrc(getFullOutpath() + testCaseName + "_source.xmi");
		helper.saveTrg(getFullOutpath() + testCaseName + "_target.xmi");
		helper.saveCorr(getFullOutpath() + testCaseName + "_corr.xmi");
		helper.saveSynchronizationProtocol(getFullOutpath() + testCaseName + "_protocol.xmi");
	}
}

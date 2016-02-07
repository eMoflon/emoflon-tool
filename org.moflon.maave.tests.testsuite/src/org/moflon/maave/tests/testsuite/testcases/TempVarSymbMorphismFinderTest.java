package org.moflon.maave.tests.testsuite.testcases;

import org.junit.Before;
import org.junit.Test;

import org.moflon.maave.tests.testgen.diachase.DiaChaseTestGen;
import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;

public class TempVarSymbMorphismFinderTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		System.out.println("Starting TempVarSymbMorphismFinderTest/Test" );
		DiaChaseTestGen testgen=DiachaseFactory.eINSTANCE.createDiaChaseTestGen();
		testgen.chasingTestTempVar();
		
	}

}

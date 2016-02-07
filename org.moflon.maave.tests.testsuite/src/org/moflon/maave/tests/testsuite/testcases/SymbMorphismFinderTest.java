package org.moflon.maave.tests.testsuite.testcases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;

import org.moflon.maave.tests.testgen.diachase.DiaChaseTestGen;
import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;

public class SymbMorphismFinderTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		System.out.println("Starting SymbMorphismFinderTest/Test" );
		DiaChaseTestGen testgen=DiachaseFactory.eINSTANCE.createDiaChaseTestGen();
		SymbolicGraphMorphism mor=testgen.chasingTest2();
		Assert.assertTrue("Not a Valid symbolic morphism",mor!=null);
		SymbolicGraphMorphism mor2=testgen.chasingTest3();
		Assert.assertTrue("Not a Valid symbolic morphism",mor2==null);
	}

}

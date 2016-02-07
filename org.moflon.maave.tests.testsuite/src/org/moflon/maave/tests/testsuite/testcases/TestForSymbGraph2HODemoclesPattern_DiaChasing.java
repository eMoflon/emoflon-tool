/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;

import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

/**
 * @author fdeckwerth
 *
 */
public class TestForSymbGraph2HODemoclesPattern_DiaChasing {

	private EPackage pack;	
	
	@Before 
	public void init(){
		MnoqPackage.eINSTANCE.getClass();
		DiachasePackage.eINSTANCE.getClass();
		pack=TestRunner.loadTestMM("DiagramChasingTestGen", "DiagramChasingTestGen");

	}
	
	@Test
	public  void test1(){
		System.out.println("Starting TestForSymbGraph2HODemoclesPattern_DiaChasing/Test1" );
		EClass cls=(EClass) pack.getEClassifier("DiaChaseTestGen");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPatternLarger4")).findFirst().get();
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
//		TestRunner.saveTestResult(symbGTRule,"testPatternLarger4");

		
	}
	@Test
	public  void test2(){
		System.out.println("Starting TestForSymbGraph2HODemoclesPattern_DiaChasing/Test2" );
		EClass cls=(EClass) pack.getEClassifier("DiaChaseTestGen");
		
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPatternAdd")).findFirst().get();
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
//		TestRunner.saveTestResult(symbGTRule,"testPatterAdd");

		
	}
	
	@Test
	public  void test3(){
		System.out.println("Starting TestForSymbGraph2HODemoclesPattern_DiaChasing/Test3" );
		EClass cls=(EClass) pack.getEClassifier("DiaChaseTestGen");
		
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPatternTempVar")).findFirst().get();
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
//		TestRunner.saveTestResult(symbGTRule,"testPatternTempVar");

		
	}
}

/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Constant;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tests.testsuite.helper.TestHelper;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

import org.moflon.maave.tests.lang.abc.AbcPackage;
/**
 * @author fdeckwerth
 *
 */
public class TestForStoryPatternToSymbGraTraRules_Basic {

	private EPackage pack;	
	
	@Before 
	public void init(){
		DiachasePackage.eINSTANCE.getClass();
		AbcPackage.eINSTANCE.getClass();
		pack=TestRunner.loadTestMM("DiagramChasingTestGen", "DiagramChasingTestGen");

	}
	
	@Test
	public  void test7(){
		System.out.println("Starting TestForSymbGraph2HODemoclesPattern_Basic/Test7" );
		EClass cls=(EClass) pack.getEClassifier("PatternGenerator");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperation(6);
		Assert.assertTrue("FailedAssert: 0",operation.getName().equals("testPattern7"));
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
		TestRunner.saveTestResult(symbGTRule,"test7");

		SymbolicGraphMorphism left=symbGTRule.getLeft();
		SymbolicGraphMorphism right=symbGTRule.getRight();
		SymbolicGraph K=left.getDom();
		SymbolicGraph L=left.getCodom();
		SymbolicGraph R=right.getCodom();
		TestHelper.checkMorphismsInRule(symbGTRule);

		Assert.assertTrue("FailedAssert: 1",L.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 2",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));


		Assert.assertTrue("FailedAssert: 3",L.getGraphEdges().size()==0);


		Assert.assertTrue("FailedAssert: 4",L.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 5",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 6",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("c_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 7",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("c_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 8",L.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 9",L.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 10",L.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 11",L.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 12",L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 13", L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("=")
				&& x.getParameters().size()==2
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("c_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("a_x")));

		/////////
		Assert.assertTrue("FailedAssert: 14",K.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 15",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));


		Assert.assertTrue("FailedAssert: 16",K.getGraphEdges().size()==0);


		Assert.assertTrue("FailedAssert: 17",K.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 18",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 19",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("c_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 20",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("c_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 21",K.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 22",K.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 23",K.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 24",K.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 25",K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 26", K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("=")
				&& x.getParameters().size()==2
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("c_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("a_x")));

		/////////
		Assert.assertTrue("FailedAssert: 27",R.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 28",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 29",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c") && x.getType().getName().equals("C")));

		Assert.assertTrue("FailedAssert: 30",R.getGraphEdges().size()==1);
		Assert.assertTrue("FailedAssert: 31",R.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("c") && x.getType().getName().equals("c")));


		Assert.assertTrue("FailedAssert: 32",R.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 33",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 34",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("c_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 35",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("c_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 36",R.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 37",R.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));
		Assert.assertTrue("FailedAssert: 38",R.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("c") && x.getTarget().getLabel().equals("c_x_prime") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 39",R.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 40",R.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 41",R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 42", R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("=")
				&& x.getParameters().size()==2
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("c_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("a_x")));
		Assert.assertTrue(TestHelper.areLabelNodesAndEdgesCorrect(symbGTRule));

	}
	@Test
	public  void test6(){
		System.out.println("Starting TestForSymbGraph2HODemoclesPattern_Basic/Test6" );
		EClass cls=(EClass) pack.getEClassifier("PatternGenerator");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperation(5);
		Assert.assertTrue("FailedAssert: 43",operation.getName().equals("testPattern6"));
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
		TestRunner.saveTestResult(symbGTRule,"test6");

		SymbolicGraphMorphism left=symbGTRule.getLeft();
		SymbolicGraphMorphism right=symbGTRule.getRight();
		SymbolicGraph K=left.getDom();
		SymbolicGraph L=left.getCodom();
		SymbolicGraph R=right.getCodom();
		TestHelper.checkMorphismsInRule(symbGTRule);

		Assert.assertTrue("FailedAssert: 44",L.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 45",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 46",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c") && x.getType().getName().equals("C")));

		Assert.assertTrue("FailedAssert: 47",L.getGraphEdges().size()==1);
		Assert.assertTrue("FailedAssert: 48",L.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("c") && x.getType().getName().equals("c")));

		Assert.assertTrue("FailedAssert: 49",L.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 50",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 51",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 52",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("c_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 53",L.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 54",L.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));
		Assert.assertTrue("FailedAssert: 55",L.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("c") && x.getTarget().getLabel().equals("c_x") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 56",L.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 57",L.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 58",L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 59", L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("=")
				&& x.getParameters().size()==2
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("c_x")));
		/////////
		Assert.assertTrue("FailedAssert: 60",K.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 61",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));


		Assert.assertTrue("FailedAssert: 62",K.getGraphEdges().size()==0);


		Assert.assertTrue("FailedAssert: 63",K.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 64",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 65",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 66",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("c_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 67",K.getLabelEdges().size()==0);

		Assert.assertTrue("FailedAssert: 68",K.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 69",K.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 70",K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 71", K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("=")
				&& x.getParameters().size()==2
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("c_x")));


		/////////
		Assert.assertTrue("FailedAssert: 72",R.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 73",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));


		Assert.assertTrue("FailedAssert: 74",R.getGraphEdges().size()==0);


		Assert.assertTrue("FailedAssert: 75",R.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 76",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 77",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 78",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("c_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 79",R.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 80",R.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x_prime") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 81",R.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 82",R.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 83",R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 84", R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("=")
				&& x.getParameters().size()==2
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("c_x")));
		Assert.assertTrue(TestHelper.areLabelNodesAndEdgesCorrect(symbGTRule));

		
	}
	@Test
	public  void test5(){
		System.out.println("Starting TestForSymbGraph2HODemoclesPattern_Basic/Test5" );
		EClass cls=(EClass) pack.getEClassifier("PatternGenerator");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperation(4);
		Assert.assertTrue("FailedAssert: 85",operation.getName().equals("testPattern5"));
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
		TestRunner.saveTestResult(symbGTRule,"test5");

		SymbolicGraphMorphism left=symbGTRule.getLeft();
		SymbolicGraphMorphism right=symbGTRule.getRight();
		SymbolicGraph K=left.getDom();
		SymbolicGraph L=left.getCodom();
		SymbolicGraph R=right.getCodom();
		TestHelper.checkMorphismsInRule(symbGTRule);

		Assert.assertTrue("FailedAssert: 86",L.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 87",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 88",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("b") && x.getType().getName().equals("B")));

		Assert.assertTrue("FailedAssert: 89",L.getGraphEdges().size()==2);
		Assert.assertTrue("FailedAssert: 90",L.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getDebugId().equals("a") && x.getType().getName().equals("a")));
		Assert.assertTrue("FailedAssert: 91",L.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("b") && x.getType().getName().equals("b")));

		Assert.assertTrue("FailedAssert: 92",L.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 93",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 94",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 95",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("b_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 96",L.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 97",L.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));
		Assert.assertTrue("FailedAssert: 98",L.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getLabel().equals("b_x") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 99",L.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 100",L.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 101",L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 102",L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("=")
				&& x.getParameters().size()==2
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("b_x")));

		/////////
		Assert.assertTrue("FailedAssert: 103",K.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 104",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 105",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("b") && x.getType().getName().equals("B")));

		Assert.assertTrue("FailedAssert: 106",K.getGraphEdges().size()==2);
		Assert.assertTrue("FailedAssert: 107",K.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getDebugId().equals("a") && x.getType().getName().equals("a")));
		Assert.assertTrue("FailedAssert: 108",K.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("b") && x.getType().getName().equals("b")));

		Assert.assertTrue("FailedAssert: 109",K.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 110",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 111",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 112",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("b_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 113",K.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 114",K.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getLabel().equals("b_x") && x.getType().getName().equals("x")));	

		Assert.assertTrue("FailedAssert: 115",K.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 116",K.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 117",K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 118",K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("=")
				&& x.getParameters().size()==2
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("b_x")));

		/////////
		Assert.assertTrue("FailedAssert: 119",R.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 120",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 121",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("b") && x.getType().getName().equals("B")));

		Assert.assertTrue("FailedAssert: 122",R.getGraphEdges().size()==2);
		Assert.assertTrue("FailedAssert: 123",R.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getDebugId().equals("a") && x.getType().getName().equals("a")));
		Assert.assertTrue("FailedAssert: 124",R.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("b") && x.getType().getName().equals("b")));

		Assert.assertTrue("FailedAssert: 125",R.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 126",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 127",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 128",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("b_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 129",R.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 130",R.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getLabel().equals("b_x") && x.getType().getName().equals("x")));	
		Assert.assertTrue("FailedAssert: 131",R.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x_prime") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 132",R.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 133",R.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 134",R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 135", R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("=")
				&& x.getParameters().size()==2
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("b_x")));
		Assert.assertTrue(TestHelper.areLabelNodesAndEdgesCorrect(symbGTRule));
	}

	@Test
	public  void test4(){
		System.out.println("Starting TestForSymbGraph2HODemoclesPattern_Basic/Test4" );
		EClass cls=(EClass) pack.getEClassifier("PatternGenerator");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperation(3);
		Assert.assertTrue("FailedAssert: 136",operation.getName().equals("testPattern4"));
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
		TestRunner.saveTestResult(symbGTRule,"test4");

		SymbolicGraphMorphism left=symbGTRule.getLeft();
		SymbolicGraphMorphism right=symbGTRule.getRight();
		SymbolicGraph K=left.getDom();
		SymbolicGraph L=left.getCodom();
		SymbolicGraph R=right.getCodom();
		TestHelper.checkMorphismsInRule(symbGTRule);
		Assert.assertTrue("FailedAssert: 137",L.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 138",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));


		Assert.assertTrue("FailedAssert: 139",L.getGraphEdges().size()==0);

		Assert.assertTrue("FailedAssert: 140",L.getLabelNodes().size()==2);
		Assert.assertTrue("FailedAssert: 141",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 142",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		


		Assert.assertTrue("FailedAssert: 144",L.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 145",L.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));
		
		Assert.assertTrue("FailedAssert: 146A",L.getFormula().getConstants().size()==1);
		Assert.assertTrue("FailedAssert: 146B",L.getFormula().getConstants().stream().anyMatch(x->x.getInterpretation().equals("1")&&x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 146",L.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 147",L.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 148",L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 149", L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("+")
				&& x.getParameters().size()==3
				&& ((LabelNode)x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&&((LabelNode)x.getParameters().get(1)).getLabel().equals("a_x")
				&& ((Constant)x.getParameters().get(2)).getInterpretation().equals("1")));

		/////////
		Assert.assertTrue("FailedAssert: 150",K.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 151",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));


		Assert.assertTrue("FailedAssert: 152",K.getGraphEdges().size()==0);

		Assert.assertTrue("FailedAssert: 153",K.getLabelNodes().size()==2);
		Assert.assertTrue("FailedAssert: 154",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 155",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		

		Assert.assertTrue("FailedAssert: 157",K.getLabelEdges().size()==0);

		Assert.assertTrue("FailedAssert: 158A",K.getFormula().getConstants().size()==1);
		Assert.assertTrue("FailedAssert: 158B",K.getFormula().getConstants().stream().anyMatch(x->x.getInterpretation().equals("1")&&x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 158",K.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 159",K.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 160",K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 161", K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("+")
				&& x.getParameters().size()==3
				&& ((LabelNode)x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode)x.getParameters().get(1)).getLabel().equals("a_x")
				&& ((Constant)x.getParameters().get(2)).getInterpretation().equals("1")));

		/////////
		Assert.assertTrue("FailedAssert: 162",R.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 163",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));


		Assert.assertTrue("FailedAssert: 164",R.getGraphEdges().size()==0);

		Assert.assertTrue("FailedAssert: 165",R.getLabelNodes().size()==2);
		Assert.assertTrue("FailedAssert: 166",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 167",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		

		Assert.assertTrue("FailedAssert: 169",R.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 170",R.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x_prime") && x.getType().getName().equals("x")));

		
		Assert.assertTrue("FailedAssert: 171A",R.getFormula().getConstants().size()==1);
		Assert.assertTrue("FailedAssert: 171B",R.getFormula().getConstants().stream().anyMatch(x->x.getInterpretation().equals("1")&&x.getType().getName().equals("EInt")));		
		Assert.assertTrue("FailedAssert: 171",R.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 172",R.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 173",R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 174",R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("+")
				&& x.getParameters().size()==3
				&& ((LabelNode)x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode)x.getParameters().get(1)).getLabel().equals("a_x")
				&& ((Constant)x.getParameters().get(2)).getInterpretation().equals("1")));
		Assert.assertTrue(TestHelper.areLabelNodesAndEdgesCorrect(symbGTRule));
	}
	@Test
	public  void test3(){
		System.out.println("Starting TestForSymbGraph2HODemoclesPattern_Basic/Test3" );
		EClass cls=(EClass) pack.getEClassifier("PatternGenerator");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperation(2);
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
		TestRunner.saveTestResult(symbGTRule,"test3");

		SymbolicGraphMorphism left=symbGTRule.getLeft();
		SymbolicGraphMorphism right=symbGTRule.getRight();
		SymbolicGraph K=left.getDom();
		SymbolicGraph L=left.getCodom();
		SymbolicGraph R=right.getCodom();
		TestHelper.checkMorphismsInRule(symbGTRule);
		Assert.assertTrue("FailedAssert: 175",L.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 176",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 177",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("b") && x.getType().getName().equals("B")));

		Assert.assertTrue("FailedAssert: 178",L.getGraphEdges().size()==2);
		Assert.assertTrue("FailedAssert: 179",L.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getDebugId().equals("a") && x.getType().getName().equals("a")));
		Assert.assertTrue("FailedAssert: 180",L.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("b") && x.getType().getName().equals("b")));

		Assert.assertTrue("FailedAssert: 181",L.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 182",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 183",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 184",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("b_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 185",L.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 186",L.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));
		Assert.assertTrue("FailedAssert: 187",L.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getLabel().equals("b_x") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 188",L.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 189",L.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 190",L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 191", L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("+")
				&& x.getParameters().size()==3
				&& ((LabelNode)x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode)x.getParameters().get(1)).getLabel().equals("b_x")
				&& ((LabelNode)x.getParameters().get(2)).getLabel().equals("a_x")));

		/////////
		Assert.assertTrue("FailedAssert: 192",K.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 193",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 194",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("b") && x.getType().getName().equals("B")));

		Assert.assertTrue("FailedAssert: 195",K.getGraphEdges().size()==2);
		Assert.assertTrue("FailedAssert: 196",K.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getDebugId().equals("a") && x.getType().getName().equals("a")));
		Assert.assertTrue("FailedAssert: 197",K.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("b") && x.getType().getName().equals("b")));

		Assert.assertTrue("FailedAssert: 198",K.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 199",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 200",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 201",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("b_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 202",K.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 203",K.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getLabel().equals("b_x") && x.getType().getName().equals("x")));	

		Assert.assertTrue("FailedAssert: 204",K.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 205",K.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 206",K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 207", K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("+")
				&& x.getParameters().size()==3
				&& ((LabelNode)x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode)x.getParameters().get(1)).getLabel().equals("b_x")
				&& ((LabelNode)x.getParameters().get(2)).getLabel().equals("a_x")));

		/////////
		Assert.assertTrue("FailedAssert: 208",R.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 209",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 210",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("b") && x.getType().getName().equals("B")));

		Assert.assertTrue("FailedAssert: 211",R.getGraphEdges().size()==2);
		Assert.assertTrue("FailedAssert: 212",R.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getDebugId().equals("a") && x.getType().getName().equals("a")));
		Assert.assertTrue("FailedAssert: 213",R.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("b") && x.getType().getName().equals("b")));

		Assert.assertTrue("FailedAssert: 214",R.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 215",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 216",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 217",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("b_x") && x.getType().getName().equals("EInt")));

		Assert.assertTrue("FailedAssert: 218",R.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 219",R.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getLabel().equals("b_x") && x.getType().getName().equals("x")));	
		Assert.assertTrue("FailedAssert: 220",R.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x_prime") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 221",R.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 222",R.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 223",R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 224",R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("+")
				&& x.getParameters().size()==3
				&& ((LabelNode)x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode)x.getParameters().get(1)).getLabel().equals("b_x")
				&& ((LabelNode)x.getParameters().get(2)).getLabel().equals("a_x")));
		Assert.assertTrue(TestHelper.areLabelNodesAndEdgesCorrect(symbGTRule));
	}
	@Test
	public  void test2(){
		System.out.println("Starting TestForSymbGraph2HODemoclesPattern_Basic/Test2" );
		EClass cls=(EClass) pack.getEClassifier("PatternGenerator");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperation(1);
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
		TestRunner.saveTestResult(symbGTRule,"test2");
		SymbolicGraphMorphism left=symbGTRule.getLeft();
		SymbolicGraphMorphism right=symbGTRule.getRight();
		SymbolicGraph K=left.getDom();
		SymbolicGraph L=left.getCodom();
		SymbolicGraph R=right.getCodom();
		TestHelper.checkMorphismsInRule(symbGTRule);

		Assert.assertTrue("FailedAssert: 225201",L.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 226202",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));

		Assert.assertTrue("FailedAssert: 227203",L.getGraphEdges().size()==0);

		Assert.assertTrue("FailedAssert: 228204",L.getLabelNodes().size()==1);
		Assert.assertTrue("FailedAssert: 229205",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		

		Assert.assertTrue("FailedAssert: 231207",L.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 232208",L.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 233209A",L.getFormula().getConstants().size()==1);
		Assert.assertTrue("FailedAssert: 233209B",L.getFormula().getConstants().stream().anyMatch(x->x.getInterpretation().equals("5")&&x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 233209",L.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 234210",L.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 235211",L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 236212", L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("<")
				&& x.getParameters().size()==2
				&& ((LabelNode)x.getParameters().get(0)).getLabel().equals("a_x")
				&& ((Constant)x.getParameters().get(1)).getInterpretation().equals("5")));
		///////
		Assert.assertTrue("FailedAssert: 237213",K.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 238214",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));

		Assert.assertTrue("FailedAssert: 239215",K.getGraphEdges().size()==0);

		Assert.assertTrue("FailedAssert: 240216",K.getLabelNodes().size()==1);
		Assert.assertTrue("FailedAssert: 241217",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		

		Assert.assertTrue("FailedAssert: 243219",K.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 244220",K.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));
		
		Assert.assertTrue("FailedAssert: 245221A",K.getFormula().getConstants().size()==1);
		Assert.assertTrue("FailedAssert: 245221B",K.getFormula().getConstants().stream().anyMatch(x->x.getInterpretation().equals("5")&&x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 245221",K.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 246222",K.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 247223",K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 248224", K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("<")
				&& x.getParameters().size()==2
				&& ((LabelNode)x.getParameters().get(0)).getLabel().equals("a_x")
				&& ((Constant)x.getParameters().get(1)).getInterpretation().equals("5")));
		///////		
		Assert.assertTrue("FailedAssert: 249",R.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 250",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));

		Assert.assertTrue("FailedAssert: 251",R.getGraphEdges().size()==0);

		Assert.assertTrue("FailedAssert: 252",R.getLabelNodes().size()==1);
		Assert.assertTrue("FailedAssert: 254",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));

		
		Assert.assertTrue("FailedAssert: 255",R.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 256",R.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));

		Assert.assertTrue("FailedAssert: 257A",R.getFormula().getConstants().size()==1);
		Assert.assertTrue("FailedAssert: 257B",R.getFormula().getConstants().stream().anyMatch(x->x.getInterpretation().equals("5")&&x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 257",R.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 258",R.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 259",R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 260", R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("<")
				&& x.getParameters().size()==2
				&& ((LabelNode)x.getParameters().get(0)).getLabel().equals("a_x")
				&& ((Constant)x.getParameters().get(1)).getInterpretation().equals("5")));
		Assert.assertTrue(TestHelper.areLabelNodesAndEdgesCorrect(symbGTRule));

	}

	@Test
	public void test1(){
		System.out.println("Starting TestForSymbGraph2HODemoclesPattern_Basic/Test1" );

		EClass cls=(EClass) pack.getEClassifier("PatternGenerator");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperation(0);
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
		SymbolicGraphMorphism left=symbGTRule.getLeft();
		SymbolicGraphMorphism right=symbGTRule.getRight();
		SymbolicGraph K=left.getDom();
		SymbolicGraph L=left.getCodom();
		SymbolicGraph R=right.getCodom();
		TestRunner.saveTestResult(symbGTRule,"test1");

		Assert.assertTrue("FailedAssert: 261",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 262",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("bCheck") && x.getType().getName().equals("B")));
		Assert.assertTrue("FailedAssert: 263",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("bDestroy") && x.getType().getName().equals("B")));
		Assert.assertTrue("FailedAssert: 264",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c") && x.getType().getName().equals("C")));

		Assert.assertTrue("FailedAssert: 265",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 266",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("bCheck") && x.getType().getName().equals("B")));
		Assert.assertTrue("FailedAssert: 267",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c") && x.getType().getName().equals("C")));

		Assert.assertTrue("FailedAssert: 268",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 269",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("bCheck") && x.getType().getName().equals("B")));
		Assert.assertTrue("FailedAssert: 270",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c") && x.getType().getName().equals("C")));
		Assert.assertTrue("FailedAssert: 271",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("bCreate") && x.getType().getName().equals("B")));


		TestHelper.checkMorphismsInRule(symbGTRule);
		Assert.assertTrue(TestHelper.areLabelNodesAndEdgesCorrect(symbGTRule));

		
	}
	

}

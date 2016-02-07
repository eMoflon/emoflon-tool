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
public class TestForStoryPatternToSymbGraTraRules_NAC {

	private EPackage pack;	
	
	@Before 
	public void init(){
		DiachasePackage.eINSTANCE.getClass();
		AbcPackage.eINSTANCE.getClass();
		pack=TestRunner.loadTestMM("DiagramChasingTestGen", "DiagramChasingTestGen");

	}
	@Test
	public void test3(){

		EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperation(2);
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Assert.assertTrue("FailedAssert: 0",operation.getName().equals("nacTestPattern3"));
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
		SymbolicGraphMorphism left=symbGTRule.getLeft();
		SymbolicGraphMorphism right=symbGTRule.getRight();
		SymbolicGraph K=left.getDom();
		SymbolicGraph L=left.getCodom();
		SymbolicGraph R=right.getCodom();
//		TestRunner.saveTestResult(symbGTRule,"nacTest3");

		Assert.assertTrue("FailedAssert: 1",L.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 2",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 3",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c") && x.getType().getName().equals("C")));
		
		Assert.assertTrue("FailedAssert: 4",L.getGraphEdges().size()==1);
		Assert.assertTrue("FailedAssert: 5",L.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("c") && x.getType().getName().equals("c")));
		
		Assert.assertTrue("FailedAssert: 6",L.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 7",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 8",L.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		
		
		Assert.assertTrue("FailedAssert: 10",L.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 11",L.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));
		
		Assert.assertTrue("FailedAssert: 12A",L.getFormula().getConstants().size()==1);
		Assert.assertTrue("FailedAssert: 12B",L.getFormula().getConstants().stream().anyMatch(x->x.getInterpretation().equals("1")&&x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 12",L.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 13",L.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 14",L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 15", L.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("+")
				&& x.getParameters().size()==3
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("a_x")
				&& ((Constant) x.getParameters().get(2)).getInterpretation().equals("1")));
		
		
		////////////////////////////////
		Assert.assertTrue("FailedAssert: 16",K.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 17",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 18",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c") && x.getType().getName().equals("C")));
		
		Assert.assertTrue("FailedAssert: 19",K.getGraphEdges().size()==1);
		Assert.assertTrue("FailedAssert: 20",K.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("c") && x.getType().getName().equals("c")));
		
		Assert.assertTrue("FailedAssert: 21",K.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 22",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 23",K.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
	
		
		Assert.assertTrue("FailedAssert: 25",K.getLabelEdges().size()==1);
		
		Assert.assertTrue("FailedAssert: 26A",K.getFormula().getConstants().size()==1);
		Assert.assertTrue("FailedAssert: 26B",K.getFormula().getConstants().stream().anyMatch(x->x.getInterpretation().equals("1")&&x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 26",K.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 27",K.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 28",K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 29", K.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("+")
				&& x.getParameters().size()==3
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("a_x")
				&& ((Constant) x.getParameters().get(2)).getInterpretation().equals("1")));
		
		
		////////////////////////////////		
		Assert.assertTrue("FailedAssert: 30",R.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 31",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 32",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c") && x.getType().getName().equals("C")));
		
		Assert.assertTrue("FailedAssert: 33",R.getGraphEdges().size()==1);
		Assert.assertTrue("FailedAssert: 34",R.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("c") && x.getType().getName().equals("c")));
		
		Assert.assertTrue("FailedAssert: 35",R.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 36",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 37",R.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
	
		
		Assert.assertTrue("FailedAssert: 39",R.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 40",R.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x_prime") && x.getType().getName().equals("x")));
		
		Assert.assertTrue("FailedAssert: 41A",R.getFormula().getConstants().size()==1);
		Assert.assertTrue("FailedAssert: 41B",R.getFormula().getConstants().stream().anyMatch(x->x.getInterpretation().equals("1")&&x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 41",R.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 42",R.getFormula().getOf().get(0).getOf().size()==2);
		Assert.assertTrue("FailedAssert: 43",R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 44", R.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("+")
				&& x.getParameters().size()==3
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("a_x")
				&& ((Constant) x.getParameters().get(2)).getInterpretation().equals("1")));
		
		
		//////////////////////////////////
		Assert.assertTrue("FailedAssert: 45",symbGTRule.getApplicationConditions().size()==1);
		Assert.assertTrue("FailedAssert: 46",symbGTRule.getApplicationConditions().get(0).getX().getDom()==L);
		SymbolicGraph X=symbGTRule.getApplicationConditions().get(0).getX().getCodom();
		
		Assert.assertTrue("FailedAssert: 47",X.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 48",X.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 49",X.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c") && x.getType().getName().equals("C")));
		
		Assert.assertTrue("FailedAssert: 50",X.getGraphEdges().size()==1);
		Assert.assertTrue("FailedAssert: 51",X.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("c") && x.getType().getName().equals("c")));
		
		Assert.assertTrue("FailedAssert: 52",X.getLabelNodes().size()==3);
		Assert.assertTrue("FailedAssert: 53",X.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 54",X.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("a_x_prime") && x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 55",X.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("c_x") && x.getType().getName().equals("EInt")));
		
		
		Assert.assertTrue("FailedAssert: 57",X.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 58",X.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getLabel().equals("a_x") && x.getType().getName().equals("x")));
		Assert.assertTrue("FailedAssert: 59",X.getLabelEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("c") && x.getTarget().getLabel().equals("c_x") && x.getType().getName().equals("x")));
		
		Assert.assertTrue("FailedAssert: 60A",X.getFormula().getConstants().size()==1);
		Assert.assertTrue("FailedAssert: 60B",X.getFormula().getConstants().stream().anyMatch(x->x.getInterpretation().equals("1")&&x.getType().getName().equals("EInt")));
		Assert.assertTrue("FailedAssert: 60",X.getFormula().getOf().size()==1);
		Assert.assertTrue("FailedAssert: 61",X.getFormula().getOf().get(0).getOf().size()==3);
		Assert.assertTrue("FailedAssert: 62",X.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("#T")));
		Assert.assertTrue("FailedAssert: 63",X.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals("+")
				&& x.getParameters().size()==3
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("a_x")
				&& ((Constant) x.getParameters().get(2)).getInterpretation().equals("1")));
		Assert.assertTrue("FailedAssert: 64", X.getFormula().getOf().get(0).getOf().stream().anyMatch(x->x.getSymbol().equals(">")
				&& x.getParameters().size()==2
				&& ((LabelNode) x.getParameters().get(0)).getLabel().equals("a_x_prime")
				&& ((LabelNode) x.getParameters().get(1)).getLabel().equals("c_x")));
		
		
		Assert.assertTrue("FailedAssert: 65",TestHelper.checkMorphism(symbGTRule.getApplicationConditions().get(0).getX()));
		
		
		Assert.assertTrue("FailedAssert: 66",TestHelper.checkMorphismsInRule(symbGTRule));


		
	}
	@Test
	public void test2(){

		EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperation(1);
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Assert.assertTrue("FailedAssert: 67",operation.getName().equals("nacTestPattern2"));
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
//		TestRunner.saveTestResult(symbGTRule,"nacTest2");
		SymbolicGraphMorphism left=symbGTRule.getLeft();
		SymbolicGraphMorphism right=symbGTRule.getRight();
		SymbolicGraph K=left.getDom();
		SymbolicGraph L=left.getCodom();
		SymbolicGraph R=right.getCodom();

		Assert.assertTrue("FailedAssert: 68",L.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 69",L.getGraphEdges().size()==0);
		Assert.assertTrue("FailedAssert: 70",L.getLabelNodes().size()==2);
		Assert.assertTrue("FailedAssert: 71",L.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 72",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 73",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c1") && x.getType().getName().equals("C")));
		
		Assert.assertTrue("FailedAssert: 74",K.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 75",K.getGraphEdges().size()==0);
		Assert.assertTrue("FailedAssert: 76",K.getLabelNodes().size()==2);
		Assert.assertTrue("FailedAssert: 77",K.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 78",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 79",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c1") && x.getType().getName().equals("C")));
		
		Assert.assertTrue("FailedAssert: 80",R.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 81",R.getGraphEdges().size()==0);
		Assert.assertTrue("FailedAssert: 82",R.getLabelNodes().size()==2);
		Assert.assertTrue("FailedAssert: 83",R.getLabelEdges().size()==2);
		Assert.assertTrue("FailedAssert: 84",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 85",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c1") && x.getType().getName().equals("C")));
		
		Assert.assertTrue("FailedAssert: 86",symbGTRule.getApplicationConditions().size()==2);
		Assert.assertTrue("FailedAssert: 87",symbGTRule.getApplicationConditions().get(1).getX().getDom()==L);
		SymbolicGraph X=(SymbolicGraph) symbGTRule.getApplicationConditions().stream().filter(x->x.getId().equals("1")).findFirst().get().getX().getCodom();
		
		Assert.assertTrue("FailedAssert: 88",X.getGraphNodes().size()==4);
		Assert.assertTrue("FailedAssert: 89",X.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 90",X.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c1") && x.getType().getName().equals("C")));
		Assert.assertTrue("FailedAssert: 91",X.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c2") && x.getType().getName().equals("C")));
		Assert.assertTrue("FailedAssert: 92",X.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("B") && x.getType().getName().equals("B")));
		
		Assert.assertTrue("FailedAssert: 93",X.getGraphEdges().size()==4);
		
		Assert.assertTrue("FailedAssert: 94",X.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("B") && x.getTarget().getDebugId().equals("a") && x.getType().getName().equals("a")));
		Assert.assertTrue("FailedAssert: 95",X.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("B") && x.getType().getName().equals("b")));
		Assert.assertTrue("FailedAssert: 96",X.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("c2") && x.getTarget().getDebugId().equals("B") && x.getType().getName().equals("b")));
		Assert.assertTrue("FailedAssert: 97",X.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("c2") && x.getType().getName().equals("c")));
		
		Assert.assertTrue("FailedAssert: 82",X.getLabelNodes().size()==2);
		Assert.assertTrue("FailedAssert: 83",X.getLabelEdges().size()==2);
		
		Assert.assertTrue("FailedAssert: 98",symbGTRule.getApplicationConditions().get(0).getX().getDom()==L);
		SymbolicGraph X2=(SymbolicGraph) symbGTRule.getApplicationConditions().stream().filter(x->x.getId().equals("2")).findFirst().get().getX().getCodom();
		
		Assert.assertTrue("FailedAssert: 99",X2.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 100",X2.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 101",X2.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("c1") && x.getType().getName().equals("C")));
		
		Assert.assertTrue("FailedAssert: 102",X2.getGraphEdges().size()==1);
		Assert.assertTrue("FailedAssert: 103",X2.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("c1") && x.getType().getName().equals("c")));
		
		Assert.assertTrue("FailedAssert: 104",TestHelper.checkMorphism(symbGTRule.getApplicationConditions().get(1).getX()));
		Assert.assertTrue("FailedAssert: 105",TestHelper.checkMorphism(symbGTRule.getApplicationConditions().get(0).getX()));
		Assert.assertTrue("FailedAssert: 82",X2.getLabelNodes().size()==2);
		Assert.assertTrue("FailedAssert: 83",X2.getLabelEdges().size()==2);
		

		Assert.assertTrue("FailedAssert: 106",TestHelper.checkMorphismsInRule(symbGTRule));
		Assert.assertTrue(TestHelper.areLabelNodesAndEdgesCorrect(symbGTRule));

		
	}
	@Test
	public void test1(){

		EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
		MoflonEOperation operation=(MoflonEOperation) cls.getEOperation(0);
		StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
		Assert.assertTrue("FailedAssert: 107",operation.getName().equals("nacTestPattern1"));
		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule symbGTRule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
//		TestRunner.saveTestResult(symbGTRule,"nacTest1");
		SymbolicGraphMorphism left=symbGTRule.getLeft();
		SymbolicGraphMorphism right=symbGTRule.getRight();
		SymbolicGraph K=left.getDom();
		SymbolicGraph L=left.getCodom();
		SymbolicGraph R=right.getCodom();

		Assert.assertTrue("FailedAssert: 108",L.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 109",L.getGraphEdges().size()==0);
		Assert.assertTrue("FailedAssert: 110",L.getLabelNodes().size()==1);
		Assert.assertTrue("FailedAssert: 111",L.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 112",L.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		
		Assert.assertTrue("FailedAssert: 113",K.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 114",K.getGraphEdges().size()==0);
		Assert.assertTrue("FailedAssert: 115",K.getLabelNodes().size()==1);
		Assert.assertTrue("FailedAssert: 116",K.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 117",K.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		
		Assert.assertTrue("FailedAssert: 118",R.getGraphNodes().size()==1);
		Assert.assertTrue("FailedAssert: 119",R.getGraphEdges().size()==0);
		Assert.assertTrue("FailedAssert: 120",R.getLabelNodes().size()==1);
		Assert.assertTrue("FailedAssert: 121",R.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 122",R.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		
		Assert.assertTrue("FailedAssert: 123",symbGTRule.getApplicationConditions().size()==1);
		Assert.assertTrue("FailedAssert: 124",symbGTRule.getApplicationConditions().get(0).getX().getDom()==L);
		SymbolicGraph X=symbGTRule.getApplicationConditions().get(0).getX().getCodom();
		
		Assert.assertTrue("FailedAssert: 125",X.getGraphNodes().size()==2);
		Assert.assertTrue("FailedAssert: 126",X.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("a") && x.getType().getName().equals("A")));
		Assert.assertTrue("FailedAssert: 127",X.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("b") && x.getType().getName().equals("B")));
		
		Assert.assertTrue("FailedAssert: 128",X.getGraphEdges().size()==2);
		Assert.assertTrue("FailedAssert: 129",X.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("b") && x.getTarget().getDebugId().equals("a") && x.getType().getName().equals("a")));
		Assert.assertTrue("FailedAssert: 130",X.getGraphEdges().stream().anyMatch(x->x.getSource().getDebugId().equals("a") && x.getTarget().getDebugId().equals("b") && x.getType().getName().equals("b")));
		
		Assert.assertTrue("FailedAssert: 120",X.getLabelNodes().size()==1);
		Assert.assertTrue("FailedAssert: 121",X.getLabelEdges().size()==1);
		Assert.assertTrue("FailedAssert: 131",TestHelper.checkMorphism(symbGTRule.getApplicationConditions().get(0).getX()));
		
		
		Assert.assertTrue("FailedAssert: 132",TestHelper.checkMorphismsInRule(symbGTRule));
		Assert.assertTrue(TestHelper.areLabelNodesAndEdgesCorrect(symbGTRule));

		
	}

}

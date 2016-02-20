package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.CriticalPairBuilder;
import org.moflon.maave.tool.analysis.DirectDerivationBuilder;
import org.moflon.maave.tool.analysis.ImprovedJointlyEpiSetBuilder;
import org.moflon.maave.tool.analysis.JointlyEpiSetBuilder;
import org.moflon.maave.tool.analysis.NonEmptySemanticJointlyEpiSetBuilder;
import org.moflon.maave.tool.analysis.ProjectiveDirectDerivationBuilder;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.NormalFormBuilder;
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.category.SymbolicPullback;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.DirectDerivationPairSet;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MorphismPairSet;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.SpanSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.EGraphElement;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraphsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.ConfigurableMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.GenericMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismFinderFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtil;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtilsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfigurableMorphismClass;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.MorphismClass;
import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tests.testgen.diachase.PaperExampleTest;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;

import org.moflon.maave.tests.lang.abc.AbcPackage;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;

public class NormalFormEquivalenceTest {
     

   @Test
   public void test1() {
      System.out.println("Starting NormalFormEquivalenceTest/Test1" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("NormalFormEquivalenceTestPatterns");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPattern1")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPattern2")).findFirst().get();
      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());

      SymbolicGraph graphP3=rule1.getRight().getCodom();
      graphP3.setName("P3");
      SymbolicGraph graphP4=rule2.getRight().getCodom();
      graphP4.setName("P4");
      
      NormalFormBuilder normalFormBuilder=ConfluenceFactory.eINSTANCE.createNormalFormBuilder();
      SpanSet equNormalFormSpanSet=normalFormBuilder.getIsomorphicNormalForms(graphP3, graphP4);
      
      assertTrue(equNormalFormSpanSet.getSpans().size()==1);
      SymbolicGraphMorphism morZ_P3=equNormalFormSpanSet.getSpans().get(0).getG();
      SymbolicGraphMorphism morZ_P4=equNormalFormSpanSet.getSpans().get(0).getH();
      
      ConfigurableMorphismClassFactory morClassFac=CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismClass morClass=morClassFac.createMorphismClass("B", "I", "B", "B", "=>");
      assertTrue(morClass.isMember(morZ_P3).isValid());
      assertTrue(morClass.isMember(morZ_P4).isValid());
      assertTrue(!morZ_P3.getDom().getLabelNodes().stream().anyMatch(x->x.getLabelEdge().isEmpty()));
      assertTrue(!morZ_P4.getDom().getLabelNodes().stream().anyMatch(x->x.getLabelEdge().isEmpty()));
      
      
      
     



   }
   @Test
   public void test2() {
      System.out.println("Starting NormalFormEquivalenceTest/Test2" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("NormalFormEquivalenceTestPatterns");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPattern3")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPattern4")).findFirst().get();
      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());

      SymbolicGraph graphP3=rule1.getRight().getCodom();
      graphP3.setName("P3");
      SymbolicGraph graphP4=rule2.getRight().getCodom();
      graphP4.setName("P4");
      
      NormalFormBuilder normalFormBuilder=ConfluenceFactory.eINSTANCE.createNormalFormBuilder();
      SpanSet equNormalFormSpanSet=normalFormBuilder.getIsomorphicNormalForms(graphP3, graphP4);
      ConfigurableMorphismClassFactory morClassFac=CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismClass morClass=morClassFac.createMorphismClass("B", "I", "B", "B", "=>");
      
      assertTrue(equNormalFormSpanSet.getSpans().size()==2);
      
      SymbolicGraphMorphism morZ_P3=equNormalFormSpanSet.getSpans().get(0).getG();
      SymbolicGraphMorphism morZ_P4=equNormalFormSpanSet.getSpans().get(0).getH();
      assertTrue(morClass.isMember(morZ_P3).isValid());
      assertTrue(morClass.isMember(morZ_P4).isValid());
      assertTrue(!morZ_P3.getDom().getLabelNodes().stream().anyMatch(x->x.getLabelEdge().isEmpty()));
      assertTrue(!morZ_P4.getDom().getLabelNodes().stream().anyMatch(x->x.getLabelEdge().isEmpty()));
      
      morZ_P3=equNormalFormSpanSet.getSpans().get(1).getG();
      morZ_P4=equNormalFormSpanSet.getSpans().get(1).getH();
      assertTrue(morClass.isMember(morZ_P3).isValid());
      assertTrue(morClass.isMember(morZ_P4).isValid());
      assertTrue(!morZ_P3.getDom().getLabelNodes().stream().anyMatch(x->x.getLabelEdge().isEmpty()));
      assertTrue(!morZ_P4.getDom().getLabelNodes().stream().anyMatch(x->x.getLabelEdge().isEmpty()));
      
     



   }
   
   


}

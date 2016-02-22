package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.ImprovedJointlyEpiSetBuilder;
import org.moflon.maave.tool.analysis.NonEmptySemanticJointlyEpiSetBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MorphismPairSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

public class JointlyEpiTest {
     

   @Test
   public void test1() {
      System.out.println("Starting JointlyEpiTest.java/Test1" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
      MoflonEOperation opL1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddOne")).findFirst().get();
      MoflonEOperation opL2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleLarger4")).findFirst().get();
      Assert.assertTrue("FailedAssert: 0",opL1!=null && opL2!=null);
      StoryNode stnL1=(StoryNode) opL1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stnL2=(StoryNode) opL2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule ruleL1=transformer.transformStpToProjGTRule(stnL1.getStoryPattern());
      SymbGTRule ruleL2=transformer.transformStpToProjGTRule(stnL2.getStoryPattern());

      SymbolicGraph L1=ruleL1.getLeft().getCodom();
      SymbolicGraph L2=ruleL2.getLeft().getCodom();
      L1.setName("L1");
      L2.setName("L2");

      NonEmptySemanticJointlyEpiSetBuilder epiSetBuilder=AnalysisFactory.eINSTANCE.createNonEmptySemanticJointlyEpiSetBuilder();

      MorphismPairSet jointliEpiPairSet= epiSetBuilder.getAllMinimalContexts(L1, L2);


      ImprovedJointlyEpiSetBuilder epiSetBuilder2=AnalysisFactory.eINSTANCE.createImprovedJointlyEpiSetBuilder();

      MorphismPairSet jointliEpiPairSet2= epiSetBuilder.getAllMinimalContexts(L1, L2);
      assertTrue(true);



   }
   @Test
   public void test2() {
      System.out.println("Starting JointlyEpiTest.java/Test2" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PullbackTest");
      MoflonEOperation opL1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPatternD")).findFirst().get();
      MoflonEOperation opL2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPatternD")).findFirst().get();
      Assert.assertTrue("FailedAssert: 0",opL1!=null && opL2!=null);
      StoryNode stnL1=(StoryNode) opL1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stnL2=(StoryNode) opL2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule ruleL1=transformer.transformStpToProjGTRule(stnL1.getStoryPattern());
      SymbGTRule ruleL2=transformer.transformStpToProjGTRule(stnL2.getStoryPattern());

      SymbolicGraph L1=ruleL1.getLeft().getCodom();
      SymbolicGraph L2=ruleL2.getLeft().getCodom();
      L1.setName("L1");
      L2.setName("L2");

      NonEmptySemanticJointlyEpiSetBuilder epiSetBuilder=AnalysisFactory.eINSTANCE.createNonEmptySemanticJointlyEpiSetBuilder();
      long time1start=System.currentTimeMillis();
      MorphismPairSet jointliEpiPairSet= epiSetBuilder.getAllMinimalContexts(L1, L2);
      long time1end=System.currentTimeMillis();
      
      
      ImprovedJointlyEpiSetBuilder epiSetBuilder2=AnalysisFactory.eINSTANCE.createImprovedJointlyEpiSetBuilder();
      long time2start=System.currentTimeMillis();
      MorphismPairSet jointliEpiPairSet2= epiSetBuilder2.getAllMinimalContexts(L1, L2);
      long time2end=System.currentTimeMillis();
      
      System.out.println("number of jointlyEpiPairs1: "+jointliEpiPairSet.getMorphismPairs().size()+";;;; calculated in "+(time1end-time1start)+"ms");
      System.out.println("number of jointlyEpiPairs2: "+jointliEpiPairSet2.getMorphismPairs().size()+";;;; calculated in "+(time2end-time2start)+"ms");
      assertTrue(true);



   }




}

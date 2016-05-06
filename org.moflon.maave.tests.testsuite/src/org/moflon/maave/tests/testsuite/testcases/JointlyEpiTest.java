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
import org.moflon.maave.tool.analysis.StandartJointlyEpiSetBuilder;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.MorphismPairSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

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
      
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      
      StandartJointlyEpiSetBuilder epiSetBuilder=AnalysisFactory.eINSTANCE.createStandartJointlyEpiSetBuilder();

      MorphismPairSet jointliEpiPairSet= epiSetBuilder.getAllMinimalContexts(L1, L2,gts);


      ImprovedJointlyEpiSetBuilder epiSetBuilder2=AnalysisFactory.eINSTANCE.createImprovedJointlyEpiSetBuilder();

      MorphismPairSet jointliEpiPairSet2= epiSetBuilder2.getAllMinimalContexts(L1, L2,gts);
      assertTrue(jointliEpiPairSet.getMorphismPairs().size()>=jointliEpiPairSet2.getMorphismPairs().size());



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

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
            
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      
      StandartJointlyEpiSetBuilder epiSetBuilder=AnalysisFactory.eINSTANCE.createStandartJointlyEpiSetBuilder();
      long time1start=System.currentTimeMillis();
      MorphismPairSet jointliEpiPairSet= epiSetBuilder.getAllMinimalContexts(L1, L2,gts);
      long time1end=System.currentTimeMillis();
      
      
      ImprovedJointlyEpiSetBuilder epiSetBuilder2=AnalysisFactory.eINSTANCE.createImprovedJointlyEpiSetBuilder();
      long time2start=System.currentTimeMillis();
      MorphismPairSet jointliEpiPairSet2= epiSetBuilder2.getAllMinimalContexts(L1, L2,gts);
      long time2end=System.currentTimeMillis();
      
      System.out.println("number of jointlyEpiPairs1: "+jointliEpiPairSet.getMorphismPairs().size()+";;;; calculated in "+(time1end-time1start)+"ms");
      System.out.println("number of jointlyEpiPairs2: "+jointliEpiPairSet2.getMorphismPairs().size()+";;;; calculated in "+(time2end-time2start)+"ms");
      assertTrue(true);



   }




}

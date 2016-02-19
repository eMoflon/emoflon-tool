package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

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
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.category.SymbolicPullback;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.DirectDerivationPairSet;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MorphismPairSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.EGraphElement;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
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
import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tests.testgen.diachase.PaperExampleTest;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;

import org.moflon.maave.tests.lang.abc.AbcPackage;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;

public class CriticalPairTestProj {
     

   @Test
   public void test1() {
      System.out.println("Starting CriticalPairTestProj/Test1" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddOne")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleLarger4")).findFirst().get();
      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());

      DirectDerivationBuilder derBuilder=AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
      CriticalPairBuilder cpBuilder=AnalysisFactory.eINSTANCE.createBasicSymbolicCriticalPairBuilder();
      JointlyEpiSetBuilder jointlyEpiSetBuilder=AnalysisFactory.eINSTANCE.createNonEmptySemanticJointlyEpiSetBuilder();
      
      DirectDerivationPairSet criticalPairs=cpBuilder.getAllCriticalPairs(rule1, rule2, derBuilder,jointlyEpiSetBuilder);
      assertTrue(criticalPairs.getPairsOfDirectDerivations().size()==1);



   }
   
   @Test
   public  void test2(){
      System.out.println("Starting CriticalPairTestProj/Test2" );
      
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("CPATest");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("noAttrRule1")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("noAttrRule2")).findFirst().get();
      MoflonEOperation op3=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("noAttrRule3")).findFirst().get();
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn3=(StoryNode) op3.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
            
      Transformer transformer = StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1= transformer.transformStpToProjGTRule(stn1.getStoryPattern());
      SymbGTRule rule2= transformer.transformStpToProjGTRule(stn2.getStoryPattern());
      SymbGTRule rule3= transformer.transformStpToProjGTRule(stn3.getStoryPattern());
      
      CriticalPairBuilder cpBuilder=AnalysisFactory.eINSTANCE.createBasicSymbolicCriticalPairBuilder();
      DirectDerivationBuilder derBuilder=AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
      JointlyEpiSetBuilder jointlyEpiSetBuilder=AnalysisFactory.eINSTANCE.createNonEmptySemanticJointlyEpiSetBuilder();
      DirectDerivationPairSet setNotEmpty=cpBuilder.getAllCriticalPairs(rule1, rule2,derBuilder,jointlyEpiSetBuilder);
      DirectDerivationPairSet setEmpty=cpBuilder.getAllCriticalPairs(rule1, rule3,derBuilder,jointlyEpiSetBuilder);

      Assert.assertTrue(setNotEmpty.getPairsOfDirectDerivations().size()>0);
      Assert.assertTrue(setEmpty.getPairsOfDirectDerivations().size()==0);

   }
   @Test
   public  void test3(){
      System.out.println("Starting CriticalPairTestProj/Test3" );
          

      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddOne")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddTwo")).findFirst().get();
      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
      
      CriticalPairBuilder cpBuilder=AnalysisFactory.eINSTANCE.createBasicSymbolicCriticalPairBuilder();
      DirectDerivationBuilder derBuilder=AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
      JointlyEpiSetBuilder jointlyEpiSetBuilder=AnalysisFactory.eINSTANCE.createNonEmptySemanticJointlyEpiSetBuilder();
      DirectDerivationPairSet setNotEmpty=cpBuilder.getAllCriticalPairs(rule1 , rule2,derBuilder,jointlyEpiSetBuilder);
      Assert.assertTrue(setNotEmpty.getPairsOfDirectDerivations().size()>0);
   }



}

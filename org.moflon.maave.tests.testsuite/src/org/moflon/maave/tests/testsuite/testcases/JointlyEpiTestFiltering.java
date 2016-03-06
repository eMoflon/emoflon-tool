package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.maave.tests.lang.cms.CmsPackage;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.ImprovedJointlyEpiSetBuilder;
import org.moflon.maave.tool.analysis.NonEmptySemanticJointlyEpiSetBuilder;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.graphtransformation.conditions.ConditionsFactory;
import org.moflon.maave.tool.graphtransformation.conditions.NegativeConstraint;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.MorphismPairSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

public class JointlyEpiTestFiltering {
     

   @Test
   public void test1() {
      System.out.println("Starting JointlyEpiTestFiltering.java/Test1" );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      
      EClass cls=(EClass) pack.getEClassifier("CMS");
      
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("unregisterStudentForExam")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerStudentForExamDeadline")).findFirst().get();
      
      Assert.assertTrue(op1!=null && op2!=null);
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());

      SymbolicGraph L1=rule1.getLeft().getCodom();
      SymbolicGraph L2=rule2.getLeft().getCodom();
      L1.setName("L1");
      L2.setName("L2");
      
      //get negativeConstraint
      EClass metamodelConstraints=(EClass) pack.getEClassifier("MetamodelConstraints");
      MoflonEOperation opNC=(MoflonEOperation) metamodelConstraints.getEOperations().stream().filter(x->x.getName().equals("nowTwoCMS")).findFirst().get();
      Assert.assertTrue(opNC!=null);
      StoryNode stnNC=(StoryNode) opNC.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      SymbGTRule ruleNC=transformer.transformStpToProjGTRule(stnNC.getStoryPattern());
      SymbolicGraph atomicNC=ruleNC.getLeft().getCodom();
      atomicNC.setName("NC");
      NegativeConstraint nc=ConditionsFactory.eINSTANCE.createNegativeConstraint();
      nc.getAtomicNegativeConstraints().add(atomicNC);
      
      
      
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      NonEmptySemanticJointlyEpiSetBuilder epiSetBuilder=AnalysisFactory.eINSTANCE.createNonEmptySemanticJointlyEpiSetBuilder();

      long time1start=System.currentTimeMillis();
      MorphismPairSet jointliEpiPairSetA= epiSetBuilder.getAllMinimalContexts(L1, L2,gts);
      long time1end=System.currentTimeMillis();
      
      gts.getConstraints().add(nc);
     
      long time2start=System.currentTimeMillis();
      MorphismPairSet jointliEpiPairSetB= epiSetBuilder.getAllMinimalContexts(L1, L2,gts);
      long time2end=System.currentTimeMillis();
      
      System.out.println("number of jointlyEpiPairs1: "+jointliEpiPairSetA.getMorphismPairs().size()+";;;; calculated in "+(time1end-time1start)+"ms");
      System.out.println("number of jointlyEpiPairs2: "+jointliEpiPairSetB.getMorphismPairs().size()+";;;; calculated in "+(time2end-time2start)+"ms");


   }
  



}

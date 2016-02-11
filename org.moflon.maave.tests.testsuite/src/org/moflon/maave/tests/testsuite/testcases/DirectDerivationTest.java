package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.DirectDerivationBuilder;
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.category.SymbolicPullback;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.DirectDerivation;
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
import org.moflon.maave.tool.symbolicgraphs.secondorder.util.MorphismClassUtil;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import org.moflon.maave.tests.lang.abc.AbcPackage;
import org.moflon.maave.tests.lang.mnoq.M;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import org.moflon.maave.tests.lang.mnoq.N;
import org.moflon.maave.tests.lang.mnoq.O;

public class DirectDerivationTest {
   private EPackage pack;  
   @Before
   public void setUp() throws Exception {
	   DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
   }

   @Test
   public void test1() {
	   System.out.println("Starting DirectDerivationTest/Test1" );
      
         EClass cls=(EClass) pack.getEClassifier("DirectDerivationTestCases");
         
         MoflonEOperation opRule=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testRule1")).findFirst().get();
         MoflonEOperation opG=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testModel1")).findFirst().get();
         Assert.assertTrue("FailedAssert: 0",opRule!=null && opG!=null);
         
         StoryNode stnRule=(StoryNode) opRule.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         StoryNode stnG=(StoryNode) opG.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         
         Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
         
         SymbGTRule rule=transformer.transformStpToProjGTRule(stnRule.getStoryPattern());
         SymbGTRule ruleForG=transformer.transformStpToProjGTRule(stnG.getStoryPattern());
         
         
         SymbolicGraph G=ruleForG.getLeft().getCodom();
         G.setName("G");
         
         SymbolicGraph L=rule.getLeft().getCodom();
         
         
         // Collect matches using new morphism finder
                
         ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
         
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
         ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(L, morclass);
                   
         SymbolicGraphMorphism emptyL_G =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyL_G.setDom(L);
         emptyL_G.setCodom(G);
         

         MorphismsSet morListL_G=morFinderForL.getAllMorphisms(emptyL_G);
        
         SymbolicGraphMorphism morL_G=morListL_G.getMorphisms().get(0);
         // build direct derivation
         DirectDerivationBuilder derBuilder=AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
         DirectDerivation der=derBuilder.deriveDirectDerivation(rule, morL_G);
         
         SymbolicGraph H=der.getComatch().getCodom();
         assertTrue(H.getGraphNodes().size()==3);
         assertTrue(H.getGraphNodes().stream().anyMatch(x->x.getType().getName().equals("N")));
         assertTrue(H.getGraphNodes().stream().anyMatch(x->x.getType().getName().equals("M")));  
         assertTrue(H.getGraphNodes().stream().anyMatch(x->x.getType().getName().equals("O")));
         
         assertTrue(H.getGraphEdges().size()==3);
         assertTrue(H.getGraphEdges().stream().anyMatch(x->x.getSource().getType().getName().equals("N")&&x.getTarget().getType().getName().equals("M")));
         assertTrue(H.getGraphEdges().stream().anyMatch(x->x.getSource().getType().getName().equals("M")&&x.getTarget().getType().getName().equals("N")));
         assertTrue(H.getGraphEdges().stream().anyMatch(x->x.getSource().getType().getName().equals("M")&&x.getTarget().getType().getName().equals("O")));
         
         assertTrue(H.getLabelNodes().size()==4);
         
         assertTrue(H.getLabelEdges().size()==3);
         assertTrue(H.getLabelEdges().stream().anyMatch(x->x.getSource().getType().getName().equals("N")&&x.getTarget().getType().getName().equals("EInt")));
         assertTrue(H.getLabelEdges().stream().anyMatch(x->x.getSource().getType().getName().equals("O")&&x.getTarget().getType().getName().equals("EInt")));
         assertTrue(H.getLabelEdges().stream().anyMatch(x->x.getSource().getType().getName().equals("M")&&x.getTarget().getType().getName().equals("EInt")));
         
         
         ConfigurableMorphismClass leftRuleMorclass=morClassFac.createMorphismClass("I", "B", "I", "I", "<=>");
         ConfigurableMorphismClass rightRuleMorclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
         
         assertTrue(rightRuleMorclass.isMember(rule.getRight()).isValid());
         assertTrue(rightRuleMorclass.isMember(der.getSpan().getH()).isValid());
         assertTrue(leftRuleMorclass.isMember(rule.getLeft()).isValid());
         assertTrue(leftRuleMorclass.isMember(der.getSpan().getG()).isValid());
         assertTrue(rightRuleMorclass.isMember(der.getMatch()).isValid());
         assertTrue(rightRuleMorclass.isMember(der.getComatch()).isValid());
         assertTrue(MorphismClassUtil.isCorrectlyTyped(der.getSpan().getG()));
         assertTrue(MorphismClassUtil.isCorrectlyTyped(der.getSpan().getH()));
         assertTrue(MorphismClassUtil.isCorrectlyTyped(rule.getRight()));
         assertTrue(MorphismClassUtil.isCorrectlyTyped(rule.getLeft()));
         
         
      


   }
   @Test
   public void test2() {
      System.out.println("Starting DirectDerivationTest/Test2" );
      
         EClass cls=(EClass) pack.getEClassifier("DirectDerivationTestCases");
         
         MoflonEOperation opRule=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testRule1")).findFirst().get();
         MoflonEOperation opG=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testModel2")).findFirst().get();
         Assert.assertTrue("FailedAssert: 0",opRule!=null && opG!=null);
         
         StoryNode stnRule=(StoryNode) opRule.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         StoryNode stnG=(StoryNode) opG.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         
         Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
         
         SymbGTRule rule=transformer.transformStpToProjGTRule(stnRule.getStoryPattern());
         SymbGTRule ruleForG=transformer.transformStpToProjGTRule(stnG.getStoryPattern());
         
         
         SymbolicGraph G=ruleForG.getLeft().getCodom();
         G.setName("G");
         
         SymbolicGraph L=rule.getLeft().getCodom();
         
         
         // Collect matches using new morphism finder
                
         ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
         
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
         ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(L, morclass);
                   
         SymbolicGraphMorphism emptyL_G =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyL_G.setDom(L);
         emptyL_G.setCodom(G);
         

         MorphismsSet morListL_G=morFinderForL.getAllMorphisms(emptyL_G);
         assertTrue(morListL_G.getMorphisms().size()==0);
   }
  
   @Test
   public void test3() {
      System.out.println("Starting DirectDerivationTest/Test3" );
      
         EClass cls=(EClass) pack.getEClassifier("DirectDerivationTestCases");
         
         MoflonEOperation opRule=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testRule1")).findFirst().get();
         MoflonEOperation opG=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testModel3")).findFirst().get();
         Assert.assertTrue("FailedAssert: 0",opRule!=null && opG!=null);
         
         StoryNode stnRule=(StoryNode) opRule.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         StoryNode stnG=(StoryNode) opG.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         
         Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
         
         SymbGTRule rule=transformer.transformStpToProjGTRule(stnRule.getStoryPattern());
         SymbGTRule ruleForG=transformer.transformStpToProjGTRule(stnG.getStoryPattern());
         
         
         SymbolicGraph G=ruleForG.getLeft().getCodom();
         G.setName("G");
         
         SymbolicGraph L=rule.getLeft().getCodom();
         
         
         // Collect matches using new morphism finder
                
         ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
         
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
         ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(L, morclass);
                   
         SymbolicGraphMorphism emptyL_G =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyL_G.setDom(L);
         emptyL_G.setCodom(G);
         

         MorphismsSet morListL_G=morFinderForL.getAllMorphisms(emptyL_G);
        
         SymbolicGraphMorphism morL_G=morListL_G.getMorphisms().get(0);
         // build direct derivation
         DirectDerivationBuilder derBuilder=AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
         DirectDerivation der=derBuilder.deriveDirectDerivation(rule, morL_G);
         assertTrue(der==null);
         
         

   }
   
   
}

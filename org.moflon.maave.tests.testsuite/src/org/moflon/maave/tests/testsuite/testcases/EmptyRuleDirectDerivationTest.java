package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tests.lang.cms.Sys;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tool.graphtransformation.DirectDerivation;
import org.moflon.maave.tool.graphtransformation.DirectDerivationBuilder;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.ConfigurableMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismFinderFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClass;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.util.MorphismClassUtil;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

public class EmptyRuleDirectDerivationTest {
   private EPackage pack;  
   @Before
   public void setUp() throws Exception {
	   DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
   }

   @Test
   public void test1() {
	   System.out.println("Starting EmptyRuleDirectDerivationTest/Test1" );
      
         EClass cls=(EClass) pack.getEClassifier("DirectDerivationTestCases");
         
         MoflonEOperation opRule=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("emptyRule")).findFirst().get();
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
                
         ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
         
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
         ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(L, morclass);
                   
         SymbolicGraphMorphism emptyL_G =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyL_G.setDom(L);
         emptyL_G.setCodom(G);
         

         MorphismsSet morListL_G=morFinderForL.getAllMorphisms(emptyL_G);
        
         SymbolicGraphMorphism morL_G=morListL_G.getMorphisms().get(0);
         // build direct derivation
         DirectDerivationBuilder derBuilder=GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
         DirectDerivation der=derBuilder.deriveDirectDerivation(rule, morL_G);
         
         
         ConfigurableMorphismClass isoMorclass=morClassFac.createMorphismClass("B", "B", "B", "B", "<=>");
         assertTrue(isoMorclass.isMember(der.getLeft()).isValid());
         assertTrue(isoMorclass.isMember(der.getRight()).isValid());
      


   }
  
   
   
}

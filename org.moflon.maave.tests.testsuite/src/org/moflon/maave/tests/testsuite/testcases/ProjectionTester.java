/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.InitialPushout;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MorphismPair;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.ConfigurableMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismFinderFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtil;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtilsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfigurableMorphismClass;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfigurableMorphismClassFactory;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

/**
 * @author fdeckwerth
 *
 */
public class ProjectionTester {



   private EPackage pack;  
   @Before
   public void setUp() throws Exception {
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
   }

   @Test
   public void test1() {
      System.out.println("Starting ProjectionTester/Test1" );

      EClass cls=(EClass) pack.getEClassifier("InitialPushoutTest");
      MoflonEOperation opL=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testModel6")).findFirst().get();
      MoflonEOperation opG=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testModel4")).findFirst().get();
      Assert.assertTrue("FailedAssert: 0",opL!=null && opG!=null);
      StoryNode stnL=(StoryNode) opL.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stnG=(StoryNode) opG.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule ruleL=transformer.transformStpToSymbGTRule(stnL.getStoryPattern());
      SymbGTRule ruleG=transformer.transformStpToSymbGTRule(stnG.getStoryPattern()); 
      SymbolicGraph L=ruleL.getLeft().getCodom();
      SymbolicGraph G=ruleG.getLeft().getCodom();
      L.setName("L");
      G.setName("G");
      // Collect matches using new morphism finder
      ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();

      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "*");
      ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(L, morclass);

      SymbolicGraphMorphism emptyL_G =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyL_G.setDom(L);
      emptyL_G.setCodom(G);

      MorphismsSet morListL_G=morFinderForL.getAllMorphisms(emptyL_G);
      assertTrue(morListL_G.getMorphisms().size()==1);
      SymbolicGraphMorphism morL_G=morListL_G.getMorphisms().get(0);
      SymbolicGraphCat cat=CategoryFactory.eINSTANCE.createSymbolicGraphCat();
      SymbolicGraphMorphism projMorL_G=cat.Project(morL_G);
      SymbolicGraph Lp=projMorL_G.getDom();
      assertTrue(Lp.getGraphNodes().size()==4);
      assertTrue(Lp.getLabelNodes().size()==G.getLabelNodes().size()-1);
      assertTrue(Lp.getFormula().getQuantifier().getLabelNodes().size()==1);
      assertTrue(Lp.getFormula().getQuantifier().getLabelNodes().get(0).getLabel().equals("mmm_x"));


   }

}

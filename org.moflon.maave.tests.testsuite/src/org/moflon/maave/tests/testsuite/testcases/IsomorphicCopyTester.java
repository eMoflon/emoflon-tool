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
public class IsomorphicCopyTester {

   

   private EPackage pack;  
   @Before
   public void setUp() throws Exception {
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
   }

   @Test
   public void test1() {
      System.out.println("Starting IsomorphicCopyTester/Test1" );
      
         EClass cls=(EClass) pack.getEClassifier("InitialPushoutTest");

         MoflonEOperation opG=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testModel4")).findFirst().get();
         Assert.assertTrue(opG!=null);
    
         StoryNode stnG=(StoryNode) opG.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      
         SymbGTRule ruleG=transformer.transformStpToSymbGTRule(stnG.getStoryPattern()); 
     
         SymbolicGraph G=ruleG.getLeft().getCodom();
         G.setName("G");
         
         CategoryUtil util=CategoryUtilsFactory.eINSTANCE.createCategoryUtil();
         
         SymbolicGraphMorphism isoMorH_G=util.copyGraph(G);
         MorphismPair isoMorPair=util.createIsomorpicCopy(G);
         
         ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("B", "B", "B", "B", "<=>");
         assertTrue(morclass.isMember(isoMorH_G).isValid());
         assertTrue(morclass.isMember(isoMorPair.getFirst()).isValid());
         assertTrue(morclass.isMember(isoMorPair.getSecond()).isValid());
     

   }

}

package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

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
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import org.moflon.maave.tests.lang.abc.AbcPackage;

public class MorphismFinderTest {
   private EPackage pack;  
   @Before
   public void setUp() throws Exception {
	   DiachasePackage.eINSTANCE.getClass();
      AbcPackage.eINSTANCE.getClass();
      pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
   }

   @Test
   public void test() {
	   System.out.println("Starting MorphismFinderTest/Test" );
      for (int i=1; i<=7 ;i++) {
         EClass cls=(EClass) pack.getEClassifier("PatternGenerator");
         int n=i;
         MoflonEOperation operation=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPattern"+n)).findFirst().get();
         Assert.assertTrue("FailedAssert: 0",operation!=null);
         StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
         SymbGTRule rule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
        
         
         SymbolicGraph L=rule.getLeft().getCodom();
         SymbolicGraph K=rule.getLeft().getDom();
         SymbolicGraph R=rule.getRight().getCodom();
         SymbolicGraphMorphism left=rule.getLeft();
         SymbolicGraphMorphism right=rule.getRight();


         GenericMorphismFinder morFinder = MatchingFactory.eINSTANCE.createInjectiveEGraphMorphismFinder();
         MorphismsSet morListLeft=morFinder.getAllMorphisms(K, L);
         MorphismsSet morListRight=morFinder.getAllMorphisms(K, R);
         assertTrue("Failed testModel"+i+" assert 1", containsMorphism(left, morListLeft));
         assertTrue("Failed testModel"+i+" assert 2", containsMorphism(right, morListRight));
         
         
            
         
         
         
         
      }


   }

   @Test
   public void nacTest() {
	   System.out.println("Starting MorphismFinderTest/nacTest" );
      for (int i=1; i<=3 ;i++) {
         EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
         int n=i;
         MoflonEOperation operation=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("nacTestPattern"+n)).findFirst().get();
         Assert.assertTrue("FailedAssert: 0",operation!=null);
         StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
         SymbGTRule rule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
        
         SymbolicGraph L=rule.getLeft().getCodom();
         SymbolicGraph K=rule.getLeft().getDom();
         SymbolicGraph R=rule.getRight().getCodom();
         SymbolicGraphMorphism left=rule.getLeft();
         SymbolicGraphMorphism right=rule.getRight();


         GenericMorphismFinder morFinder = MatchingFactory.eINSTANCE.createInjectiveEGraphMorphismFinder();
         MorphismsSet morListLeft=morFinder.getAllMorphisms(K, L);
         MorphismsSet morListRight=morFinder.getAllMorphisms(K, R);
         assertTrue("Failed testModel"+i+" assert 1", containsMorphism(left, morListLeft));
         assertTrue("Failed testModel"+i+" assert 2", containsMorphism(right, morListRight));
      }


   }

   public boolean containsMorphism(SymbolicGraphMorphism mor, MorphismsSet morList){
      CategoryUtil helper=CategoryUtilsFactory.eINSTANCE.createCategoryUtil();
      for (SymbolicGraphMorphism mor2 : morList.getMorphisms()) {
         if(helper.areSimilarEGraphMorphisms(mor, mor2)){
            return true;
         }
      }
      return false;
   }
   
   
   
}

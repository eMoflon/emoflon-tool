package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
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
import org.moflon.maave.tool.symbolicgraphs.secondorder.util.MorphismClassUtil;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import org.moflon.maave.tests.lang.abc.AbcPackage;

public class ConfigurableMorphismFinderTestEGraph {
   private EPackage pack;  
   @Before
   public void setUp() throws Exception {
	   DiachasePackage.eINSTANCE.getClass();
      AbcPackage.eINSTANCE.getClass();
      pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
   }

   @Test
   public void test() {
	   System.out.println("Starting ConfigurableMorphismFinderTestEGraph/Test" );
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

         // Collect matches using old morphism finder
         GenericMorphismFinder morFinder = MatchingFactory.eINSTANCE.createInjectiveEGraphMorphismFinder();
         MorphismsSet morListLeft=morFinder.getAllMorphisms(K, L);
         MorphismsSet morListRight=morFinder.getAllMorphisms(K, R);
         assertTrue("Failed testModel"+i+" assert 1", containsMorphism(left, morListLeft));
         assertTrue("Failed testModel"+i+" assert 2", containsMorphism(right, morListRight));
         
         
         // Collect matches using new morphism finder
         ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
         
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "*", "I", "I", "*");
         ConfigurableMorphismFinder confMorFinder = mofFindFac.createMorphismFinder(K, morclass);
        
         SymbolicGraphMorphism emptyLeft =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyLeft.setDom(K);
         emptyLeft.setCodom(L);
         SymbolicGraphMorphism emptyRight =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyRight.setDom(K);
         emptyRight.setCodom(R);
         
         
         MorphismsSet morListLeft2=confMorFinder.getAllMorphisms(emptyLeft);
         MorphismsSet morListRight2=confMorFinder.getAllMorphisms(emptyRight);
         assertTrue("Failed testModel"+i+" assert 3", containsMorphism(left, morListLeft2));
         assertTrue("Failed testModel"+i+" assert 4", containsMorphism(right, morListRight2));
         
         for (SymbolicGraphMorphism mor : morListLeft2.getMorphisms()) {
        	 assertTrue("Failed testModel"+i+" assert 5", containsMorphism(mor, morListLeft));
         }
         for (SymbolicGraphMorphism mor : morListLeft.getMorphisms()) {
        	 assertTrue("Failed testModel"+i+" assert 6", containsMorphism(mor, morListLeft2));
         }

         for (SymbolicGraphMorphism mor : morListRight2.getMorphisms()) {
        	 assertTrue("Failed testModel"+i+" assert 7", containsMorphism(mor, morListRight));
         }
         for (SymbolicGraphMorphism mor : morListRight.getMorphisms()) {
        	 assertTrue("Failed testModel"+i+" assert 8", containsMorphism(mor, morListRight2));
         }  

      }


   }
   public void test2() {
	   System.out.println("Starting ConfigurableMorphismFinderTestEGraph/Test2" );
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

                  
         
         // Collect matches using new morphism finder
         ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
         
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "*");
         ConfigurableMorphismFinder confMorFinder = mofFindFac.createMorphismFinder(K, morclass);
        
         SymbolicGraphMorphism emptyLeft =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyLeft.setDom(K);
         emptyLeft.setCodom(L);
         SymbolicGraphMorphism emptyRight =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyRight.setDom(K);
         emptyRight.setCodom(R);
         
         
         MorphismsSet morListLeft2=confMorFinder.getAllMorphisms(emptyLeft);
         MorphismsSet morListRight2=confMorFinder.getAllMorphisms(emptyRight);
         assertTrue("Failed testModel"+i+" assert 3", containsMorphism(left, morListLeft2));
         assertTrue("Failed testModel"+i+" assert 4", containsMorphism(right, morListRight2));
         
         for (SymbolicGraphMorphism mor : morListLeft2.getMorphisms()) {
        	 assertTrue("Failed testModel"+i+" assert 5a",MorphismClassUtil.isCorrectlyTyped(mor));
        	 assertTrue("Failed testModel"+i+" assert 6a",isInjective(mor));
		}
         for (SymbolicGraphMorphism mor : morListRight2.getMorphisms()) {
        	 assertTrue("Failed testModel"+i+" assert 5b",MorphismClassUtil.isCorrectlyTyped(mor));
        	 assertTrue("Failed testModel"+i+" assert 6b",isInjective(mor));
		}
         
         
         
      }


   }
//   @Test
//   public void nacTest() {
//	   System.out.println("Starting ConfigurableMorphismFinderTest/nacTest" );
//      for (int i=1; i<=3 ;i++) {
//         EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
//         int n=i;
//         MoflonEOperation operation=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("nacTestPattern"+n)).findFirst().get();
//         Assert.assertTrue("FailedAssert: 0",operation!=null);
//         StoryNode stn=(StoryNode) operation.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//         Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//         SymbGTRule rule=transformer.transformStpToSymbGTRule(stn.getStoryPattern());
//        
//         SymbolicGraph L=rule.getLeft().getCodom();
//         SymbolicGraph K=rule.getLeft().getDom();
//         SymbolicGraph R=rule.getRight().getCodom();
//         SymbolicGraphMorphism left=rule.getLeft();
//         SymbolicGraphMorphism right=rule.getRight();
//
//
//         GenericMorphismFinder morFinder = MatchingFactory.eINSTANCE.createInjectiveEGraphMorphismFinder();
//         MorphismsSet morListLeft=morFinder.getAllMorphisms(K, L);
//         MorphismsSet morListRight=morFinder.getAllMorphisms(K, R);
//         assertTrue("Failed testModel"+i+" assert 1", containsMorphism(left, morListLeft));
//         assertTrue("Failed testModel"+i+" assert 2", containsMorphism(right, morListRight));
//      }
//
//
//   }

   public boolean containsMorphism(SymbolicGraphMorphism mor, MorphismsSet morList){
      CategoryUtil helper=CategoryUtilsFactory.eINSTANCE.createCategoryUtil();
      for (SymbolicGraphMorphism mor2 : morList.getMorphisms()) {
         if(helper.areSimilarEGraphMorphisms(mor, mor2)){
            return true;
         }
      }
      return false;
   }
   private boolean isInjective(SymbolicGraphMorphism mor){
	   for (Object obj : mor.getDom().getAllElements()) {
			if (mor.getDom().getAllElements().stream()
					.anyMatch(x -> x != obj && mor.imageOf((EGraphElement)x) == mor.imageOf((EGraphElement)obj))) {
				return false;
			}

		}
		return true;
	   
   }
   
   
}

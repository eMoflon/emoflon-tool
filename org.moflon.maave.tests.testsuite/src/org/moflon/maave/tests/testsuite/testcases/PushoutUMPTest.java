package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.category.SymbolicPullback;
import org.moflon.maave.tool.category.SymbolicPushout;
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
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;

public class PushoutUMPTest {
   private EPackage pack;  
   @Before
   public void setUp() throws Exception {
	   DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
   }

   @Test
   public void test() {
	   System.out.println("Starting PullbackTest/Test" );
      
         EClass cls=(EClass) pack.getEClassifier("PullbackTest");
         MoflonEOperation opB=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPatternB")).findFirst().get();
         MoflonEOperation opC=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPatternC")).findFirst().get();
         MoflonEOperation opD=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPatternD")).findFirst().get();
         Assert.assertTrue("FailedAssert: 0",opB!=null && opC!=null && opD!=null);
         StoryNode stnB=(StoryNode) opB.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         StoryNode stnC=(StoryNode) opC.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         StoryNode stnD=(StoryNode) opD.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
         SymbGTRule ruleB=transformer.transformStpToSymbGTRule(stnB.getStoryPattern());
         SymbGTRule ruleC=transformer.transformStpToSymbGTRule(stnC.getStoryPattern());
         SymbGTRule ruleD=transformer.transformStpToSymbGTRule(stnD.getStoryPattern());
         SymbolicGraph B=ruleB.getLeft().getCodom();
         SymbolicGraph C=ruleC.getLeft().getCodom();
         SymbolicGraph X=ruleD.getLeft().getCodom();
         B.setName("B");
         C.setName("C");
         X.setName("X");
         
         // Collect matches using new morphism finder
         ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
         
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
         ConfigurableMorphismFinder morFinderForB = mofFindFac.createMorphismFinder(B, morclass);
         ConfigurableMorphismFinder morFinderForC = mofFindFac.createMorphismFinder(C, morclass);
           
         SymbolicGraphMorphism emptyB_D =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyB_D.setDom(B);
         emptyB_D.setCodom(X);
         SymbolicGraphMorphism emptyC_D =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyC_D.setDom(C);
         emptyC_D.setCodom(X);

         MorphismsSet morListB_X=morFinderForB.getAllMorphisms(emptyB_D);
         MorphismsSet morListC_X=morFinderForC.getAllMorphisms(emptyC_D);
         
         SymbolicGraphMorphism b_x=morListB_X.getMorphisms().get(0);
         SymbolicGraphMorphism c_x=morListC_X.getMorphisms().get(0);
         
         SymbolicGraphCat cat=CategoryFactory.eINSTANCE.createSymbolicGraphCat();
         
         
         //construct pullback
         SymbolicPullback pullback=cat.pullback(b_x, c_x);
         SymbolicGraph A=pullback.getPullbackObject();
         SymbolicGraphMorphism a_b=null;
         SymbolicGraphMorphism a_c=null;
         for (SymbolicGraphMorphism mor : pullback.getMorphisms())
         {
            if(mor.getCodom()==B)
            {
               a_b=mor;
            }
            if(mor.getCodom()==C)
            {
               a_c=mor;
            }
         }
         assertTrue(a_b!=null && a_c!=null);
         
         //construct pushout with pullback object
         SymbolicPushout pushout=cat.pushout(a_b, a_c);
         SymbolicGraph D=pushout.getPushoutObject();
         SymbolicGraphMorphism b_d=null;
         SymbolicGraphMorphism c_d=null;
         for (SymbolicGraphMorphism mor : pushout.getMorphism())
         {
            if(mor.getDom()==B)
            {
               b_d=mor;
            }
            if(mor.getDom()==C)
            {
               c_d=mor;
            }
         }
         assertTrue(b_d!=null && c_d!=null);
         
         //check commutation
         for (Object obj : A.getAllElements())
         {
            EGraphElement elem=(EGraphElement) obj;
            assertTrue((b_x.imageOf(a_b.imageOf(elem))).equals(c_x.imageOf(a_c.imageOf(elem))));
         }
         SymbolicGraphMorphism d_x=pushout.uMP(c_x, b_x);
         
         
         ConfigurableMorphismFinder morFinderForD = mofFindFac.createMorphismFinder(D, morclass);
         
         SymbolicGraphMorphism emptyD_X =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyD_X.setCodom(X);
         emptyD_X.setDom(D);
         MorphismsSet morListD_X=morFinderForD.getAllMorphisms(emptyD_X);
         assertTrue(morListD_X.getMorphisms().size()==1);
         SymbolicGraphMorphism d_x_prime=morListD_X.getMorphisms().get(0);
         CategoryUtil catUtil=CategoryUtilsFactory.eINSTANCE.createCategoryUtil();
         assertTrue(catUtil.areSimilarEGraphMorphisms(d_x ,d_x_prime));
         
         
         
         
      


   }

  
   
   
   
}

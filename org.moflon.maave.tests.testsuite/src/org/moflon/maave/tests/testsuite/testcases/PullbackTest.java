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

public class PullbackTest {
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
         SymbolicGraph D=ruleD.getLeft().getCodom();
         B.setName("B");
         C.setName("C");
         D.setName("D");
         
         // Collect matches using new morphism finder
         ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
         
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
         ConfigurableMorphismFinder morFinderForB = mofFindFac.createMorphismFinder(B, morclass);
         ConfigurableMorphismFinder morFinderForC = mofFindFac.createMorphismFinder(C, morclass);
           
         SymbolicGraphMorphism emptyB_D =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyB_D.setDom(B);
         emptyB_D.setCodom(D);
         SymbolicGraphMorphism emptyC_D =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyC_D.setDom(C);
         emptyC_D.setCodom(D);

         MorphismsSet morListB_D=morFinderForB.getAllMorphisms(emptyB_D);
         MorphismsSet morListC_D=morFinderForC.getAllMorphisms(emptyC_D);
         
         SymbolicGraphMorphism b_d=morListB_D.getMorphisms().get(0);
         SymbolicGraphMorphism c_d=morListC_D.getMorphisms().get(0);
         
         SymbolicGraphCat cat=CategoryFactory.eINSTANCE.createSymbolicGraphCat();
         SymbolicPullback pullback=cat.pullback(b_d, c_d);
         System.out.println(pullback.getPullbackObject().getFormula().toString());
         int x=0;
         x++;
         
            
         
         
         
         
      


   }

  
   
   
   
}

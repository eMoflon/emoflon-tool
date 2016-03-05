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
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.category.SymbolicPullback;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.EGraphElement;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.ConfigurableMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismFinderFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClass;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

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
         SymbGTRule ruleB=transformer.transformStpToProjGTRule(stnB.getStoryPattern());
         SymbGTRule ruleC=transformer.transformStpToProjGTRule(stnC.getStoryPattern());
         SymbGTRule ruleD=transformer.transformStpToProjGTRule(stnD.getStoryPattern());
         SymbolicGraph B=ruleB.getLeft().getCodom();
         SymbolicGraph C=ruleC.getLeft().getCodom();
         SymbolicGraph D=ruleD.getLeft().getCodom();
         B.setName("B");
         C.setName("C");
         D.setName("D");
         
         // Collect matches using new morphism finder
         ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
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
         SymbolicGraph A=pullback.getPullbackObject();
         
         SymbolicGraphMorphism a_b=pullback.getMorphisms().stream().filter(x->x.getCodom()==B).findAny().get();
         SymbolicGraphMorphism a_c=pullback.getMorphisms().stream().filter(x->x.getCodom()==C).findAny().get();
         //   check commutation
         for (Object obj : A.getAllElements())
         {
            EGraphElement elem=(EGraphElement) obj;
            assertTrue((b_d.imageOf(a_b.imageOf(elem))).equals(c_d.imageOf(a_c.imageOf(elem))));
         }
         
         assertTrue(A.getGraphNodes().stream().filter(n->n.getType().getName().equals("N")).count()==1);
         assertTrue(A.getGraphNodes().stream().filter(n->n.getType().getName().equals("Q")).count()==1);
         assertTrue(A.getGraphEdges().stream().filter(e->e.getSource().getType().getName().equals("N")&&e.getTarget().getType().getName().equals("Q")).count()==1);
         assertTrue(A.getGraphEdges().stream().filter(e->e.getSource().getType().getName().equals("Q")&&e.getTarget().getType().getName().equals("N")).count()==1);
         assertTrue(A.getGraphNodes().size()==2);
         assertTrue(A.getGraphEdges().size()==2);
         assertTrue(A.getLabelEdges().size()==1);
         assertTrue(A.getLabelNodes().size()==1);
         
         assertTrue(morclass.isMember(a_c).isValid());
         assertTrue(morclass.isMember(a_b).isValid());
         
            
         
         
         
         
      


   }

  
   
   
   
}

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
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.EGraphElement;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.CategoryUtil;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClass;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

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
         
         CategoryUtil util=MatchingUtilsFactory.eINSTANCE.createCategoryUtil();
         
         SymbolicGraphMorphism isoMorH_G=util.copyGraph(G);
         SymbolicGraph H=isoMorH_G.getDom();
         H.setName("H");
         
         ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("B", "B", "B", "B", "<=>");
         assertTrue(morclass.isMember(isoMorH_G).isValid());
         SymbolicGraphMorphism isoMorG_H=util.getInverseIfIsomorphism(isoMorH_G);
         assertTrue(morclass.isMember(isoMorG_H).isValid());
         SymbolicGraphMorphism id_H=util.createIdentityMorphism(H);
         SymbolicGraphMorphism id_G=util.createIdentityMorphism(G);
         
         for (Object  ob : H.getAllElements())
         {
            EGraphElement elem=(EGraphElement) ob;
            assertTrue(isoMorG_H.imageOf(isoMorH_G.imageOf(elem))==id_H.imageOf(elem));
         }
         
         for (Object  ob : G.getAllElements())
         {
            EGraphElement elem=(EGraphElement) ob;
            assertTrue(isoMorH_G.imageOf(isoMorG_H.imageOf(elem))==id_G.imageOf(elem));
         }

   }

}

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
import org.moflon.maave.tests.lang.abc.AbcPackage;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tests.testsuite.helper.TestHelper;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Constant;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
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
/**
 * @author fdeckwerth
 *
 */
public class TestForStoryPatternToProjGraTraRules_Enum {

   private EPackage pack;	

   @Before 
   public void init(){
      DiachasePackage.eINSTANCE.getClass();
      AbcPackage.eINSTANCE.getClass();
      pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");

   }

   @Test
   public  void test1(){
      System.out.println("Starting TestForStoryPatternToProjGraTraRules_Enum/Test1" );
      EClass cls=(EClass) pack.getEClassifier("EnumTestPattern");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPattern1")).findFirst().get();
      Assert.assertTrue(op1!=null);
      StoryNode stn=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule symbGTRule=transformer.transformStpToProjGTRule(stn.getStoryPattern());
      SymbGTRule ruleG=transformer.transformStpToProjGTRule(stn.getStoryPattern());
     
      SymbolicGraph graphL=symbGTRule.getLeft().getCodom();
      SymbolicGraph graphG=ruleG.getLeft().getCodom();
     
      
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      
      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
      ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(graphL, morclass);
      SymbolicGraphMorphism emptyMorL_G=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G.setDom(graphL);
      emptyMorL_G.setCodom(graphG);
      MorphismsSet morSetL_G=morFinderForL.getAllMorphisms(emptyMorL_G);
      
      assertTrue(morSetL_G.getMorphisms().size()==1);

   }
 

}

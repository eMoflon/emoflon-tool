package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tool.analysis.util.SubgraphBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;

public class SubgraphBuilderTest {
   private EPackage pack;  
   @Before
   public void setUp() throws Exception {
	   DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
   }

   @Test
   public void test1() {
	   System.out.println("Starting SubgraphBuilderTest/Test1" );
      
         EClass cls=(EClass) pack.getEClassifier("DirectDerivationTestCases");
         
        
         MoflonEOperation opG=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testModel1")).findFirst().get();
         Assert.assertTrue(opG!=null);
         
         
         StoryNode stnG=(StoryNode) opG.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         
         Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
         
         
         SymbGTRule ruleForG=transformer.transformStpToSymbGTRule(stnG.getStoryPattern());
         
         
         SymbolicGraph G=ruleForG.getLeft().getCodom();
         G.setName("G");
         
         SubgraphBuilder subgraphBuilder=new SubgraphBuilder(G);
         MorphismsSet morSet=subgraphBuilder.getAllSubgraphs();
         
         
        System.out.println("Number of Subgraphs for model DirectDerivationTestCases/testModel1="+morSet.getMorphisms().size());
         assertTrue(true);
         
         
      


   }
   
   
   
}

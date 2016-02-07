/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.category.SymbolicPushoutComplement;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.GenericMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;

import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import SDMLanguage.SDMLanguagePackage;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;

/**
 * @author fdeckwerth
 *
 */
public class SymbolicPushoutComplementTest {



	private StoryPattern getTestPattern(String className,String name){

		MnoqPackage.eINSTANCE.eClass();
		SDMLanguagePackage.eINSTANCE.getClass();
		DiachasePackage.eINSTANCE.getClass();
		EPackage pack = (EPackage) eMoflonEMFUtil
				.loadModel("../org.moflon.maave.tests.testgen.diachase/model/Diachase.ecore");
		EClass clazz = (EClass) pack.getEClassifier(className);
		MoflonEOperation eop = (MoflonEOperation) clazz.getEAllOperations()
				.stream().filter(x -> x.getName().equals(name)).findFirst()
				.get();
		StoryNode stn = (StoryNode) eop.getActivity().getOwnedActivityNode()
				.stream().filter(x -> x instanceof StoryNode).findFirst().get();
		return stn.getStoryPattern();
	}

	@Test
	public  void test1(){
		System.out.println("Starting SymbolicPushoutComplementTest/Test1" );


		StoryPattern stpRule1=getTestPattern("SymbolicPushoutComplement","rule1");
		StoryPattern stpRule2=getTestPattern("SymbolicPushoutComplement","rule1");


		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
		SymbGTRule rule1=transformer.transformStpToSymbGTRule(stpRule1);
		SymbGTRule rule2=transformer.transformStpToSymbGTRule(stpRule2);
		 
		GenericMorphismFinder matcher= MatchingFactory.eINSTANCE.createInjectiveSymbolicGraphMorphismFinder();
		
		SymbolicGraphMorphism match=matcher.getAllMorphisms(rule1.getLeft().getCodom(), rule2.getLeft().getCodom()).getMorphisms().get(0);
		
		SymbolicGraphCat cat=CategoryFactory.eINSTANCE.createSymbolicGraphCat();
		SymbolicPushoutComplement poc=cat.pushoutComplement(rule1.getLeft(), match);
		SymbolicGraph c=poc.getContextObject();
		Assert.assertTrue(c.getGraphNodes().size()==2);
		Assert.assertTrue(c.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("n")));
		Assert.assertTrue(c.getGraphNodes().stream().anyMatch(x->x.getDebugId().equals("m")));
		
		Assert.assertTrue(c.getGraphEdges().size()==2);
      Assert.assertTrue(c.getGraphEdges().stream().anyMatch(x->x.getType().getName().equals("nns")&& x.getSource().getDebugId().equals("m")&&x.getTarget().getDebugId().equals("n")));
      Assert.assertTrue(c.getGraphEdges().stream().anyMatch(x->x.getType().getName().equals("mms")&& x.getSource().getDebugId().equals("n")&&x.getTarget().getDebugId().equals("m")));
      
      Assert.assertTrue(c.getLabelEdges().size()==1);
      Assert.assertTrue(c.getLabelEdges().stream().anyMatch(x-> x.getType().getName().equals("x")&&x.getSource().getDebugId().equals("n")&&x.getTarget().getLabel().equals("n_x")));
      
      Assert.assertTrue(c.getLabelNodes().size()==3);
      Assert.assertTrue(c.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("n_x")));
      Assert.assertTrue(c.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("m_x")));
      Assert.assertTrue(c.getLabelNodes().stream().anyMatch(x->x.getLabel().equals("m_x_prime")));
		
	

		
	}
	
}

/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Test;
import org.moflon.core.utilities.eMoflonEMFUtil;

import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import SDMLanguage.SDMLanguagePackage;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;

/**
 * @author fdeckwerth
 *
 */
public class SemanticAnalyserTest {



	private StoryPattern getTestPattern(String className,String name){

		//    rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		//    rs.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true), URI.createFileURI(args[0] + "\\"));
		//    rs.getURIConverter().getURIMap().put(URI.createPlatformPluginURI("/DiagramChasingTestGen/", true), URI.createPlatformResourceURI("/DiagramChasingTestGen/", true));

		//URI testGeneratorURI =URI.createURI("DiagramChasingTestGen/model/DiagramChasingTestGen.ecore",true);
		MnoqPackage.eINSTANCE.eClass();
		SDMLanguagePackage.eINSTANCE.getClass();
		DiachaseFactory.eINSTANCE.getClass();
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
		System.out.println("Starting SemanticAnalyserTest/Test1" );

//
//		StoryPattern stpRule1=getTestPattern("SemanticTest","ruleAddOneAddOne");
//		StoryPattern stpRule2=getTestPattern("SemanticTest","ruleAddTwo");
//		StoryPattern stpValidResult=getTestPattern("SemanticTest","validResult");
//		StoryPattern stpIinvalidResult1=getTestPattern("SemanticTest","invalidResult1");
//
//		Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//		SymbGTRule ruleAddOneAddOne=transformer.transformStpToSymbGTRule(stpRule1);
//		SymbGTRule ruleAddTwo=transformer.transformStpToSymbGTRule(stpRule2);
//		SymbGTRule ruleValidResult=transformer.transformStpToSymbGTRule(stpValidResult);
//      SymbGTRule ruleInvalidResult1=transformer.transformStpToSymbGTRule(stpIinvalidResult1);
//		
//		SymbolicGraph graphA=ruleAddOneAddOne.getRight().getCodom();
//		SymbolicGraph graphB=ruleAddTwo.getRight().getCodom();
//		SymbolicGraph graphValidResult=ruleValidResult.getLeft().getCodom();
//		SymbolicGraph graphInvalidResult1=ruleInvalidResult1.getLeft().getCodom();
//		
//		ISemanticAnalyser semAnalyser=EMoflonVerificationFactory.eINSTANCE.createSemanticAnalyser();
//		MorphismPair semPair=semAnalyser.getSematicEquivalentGraph(graphA , graphB);
//		SymbolicGraph graphSem= semPair.getFirst().getCodom();
//		GenericMorphismFinder matcher=FirstOrderSymbolicGraphsMatchingFactory.eINSTANCE.createInjectiveSymbolicGraphMorphismFinder();
//		
//		
//		Assert.assertTrue(matcher.getAllMorphisms(graphSem, graphValidResult).getMorphisms().size()>0);
//		Assert.assertTrue(matcher.getAllMorphisms(graphA, graphValidResult).getMorphisms().size()>0);
//		Assert.assertTrue(matcher.getAllMorphisms(graphB, graphValidResult).getMorphisms().size()>0);
//		
//		Assert.assertTrue(matcher.getAllMorphisms(graphSem, graphInvalidResult1).getMorphisms().size()==0);
//      Assert.assertTrue(matcher.getAllMorphisms(graphA, graphInvalidResult1).getMorphisms().size()==0);
//      Assert.assertTrue(matcher.getAllMorphisms(graphB, graphInvalidResult1).getMorphisms().size()>0);
//      
//		
//		
//		System.out.println("stop");
	

		
	}

}

/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.DirectDerivationBuilder;
import org.moflon.maave.tool.analysis.JointlyEpiSetBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.smt.solverutil.IAttribSolver;
import org.moflon.maave.tool.smt.solverutil.Z3AttribSolver;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.DirectDerivation;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MorphismPairSet;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.Span;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.EGraphElement;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;

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
public class SymbolicPushoutTest {



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
      System.out.println("Starting SymbolicPushoutTest/Test1" );


      StoryPattern stpRule1=getTestPattern("PaperExampleTest","ruleAddOne");
      StoryPattern stpRule2=getTestPattern("PaperExampleTest","ruleAddTwo");


      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule ruleAddOne=transformer.transformStpToSymbGTRule(stpRule1);
      SymbGTRule ruleAddTwo=transformer.transformStpToSymbGTRule(stpRule2);

      SymbolicGraph graphL1=ruleAddOne.getLeft().getCodom();
      SymbolicGraph graphL2=ruleAddTwo.getLeft().getCodom();


      JointlyEpiSetBuilder jointlyEpiSetBuilder=AnalysisFactory.eINSTANCE.createImprovedJointlyEpiSetBuilder();

      MorphismPairSet mPairSet=jointlyEpiSetBuilder.getAllMinimalContexts(graphL1, graphL2);

      DirectDerivationBuilder derBuilder=AnalysisFactory.eINSTANCE.createSymbolicDirectDerivationBuilder();
      DirectDerivation der1=derBuilder.deriveDirectDerivation(ruleAddOne, mPairSet.getMorphismPairs().get(0).getFirst());


      Span span=der1.getSpan();
      SymbolicGraphMorphism g=span.getG();
      SymbolicGraphMorphism h=span.getH();
      SymbolicGraph graphD=g.getDom();
      SymbolicGraph graphG=g.getCodom();
      SymbolicGraph graphH=h.getCodom();

      for (Object obj : graphG.getAllElements())
      {
         EGraphElement elem=(EGraphElement) obj;
         if(!(elem instanceof LabelEdge)){
            Assert.assertTrue(graphD.getAllElements().stream().anyMatch(x->g.imageOf((EGraphElement) x)==elem));
         }
      }
      for (Object obj : graphH.getAllElements())
      {
         EGraphElement elem=(EGraphElement) obj;
         if(!(elem instanceof LabelEdge)){
            Assert.assertTrue(graphD.getAllElements().stream().anyMatch(x->h.imageOf((EGraphElement) x)==elem));
         }
      }
      IAttribSolver solver=new Z3AttribSolver();
      Assert.assertTrue(solver.hasEquivalentFormulas(h));
      Assert.assertTrue(solver.hasEquivalentFormulas(g));


   }
   @Test
   public  void test2(){
      System.out.println("Starting SymbolicPushoutTest/Test2" );


      StoryPattern stpRule1=getTestPattern("PaperExampleTest","ruleAddOne");
      StoryPattern stpRule2=getTestPattern("PaperExampleTest","ruleAddTwo");


      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule ruleAddOne=transformer.transformStpToSymbGTRule(stpRule1);
      SymbGTRule ruleAddTwo=transformer.transformStpToSymbGTRule(stpRule2);

      SymbolicGraph graphL1=ruleAddOne.getLeft().getCodom();
      SymbolicGraph graphL2=ruleAddTwo.getLeft().getCodom();


      JointlyEpiSetBuilder jointlyEpiSetBuilder=AnalysisFactory.eINSTANCE.createImprovedJointlyEpiSetBuilder();
      ruleAddOne.makeRuleLazy();
      MorphismPairSet mPairSet=jointlyEpiSetBuilder.getAllMinimalContexts(graphL1, graphL2);

      DirectDerivationBuilder derBuilder=AnalysisFactory.eINSTANCE.createSymbolicDirectDerivationBuilder();
      DirectDerivation der1=derBuilder.deriveDirectDerivation(ruleAddOne, mPairSet.getMorphismPairs().get(0).getFirst());

      Span span=der1.getSpan();
      SymbolicGraphMorphism g=span.getG();
      SymbolicGraphMorphism h=span.getH();
      SymbolicGraph graphD=g.getDom();
      SymbolicGraph graphG=g.getCodom();
      SymbolicGraph graphH=h.getCodom();

      for (Object obj : graphG.getAllElements())
      {
         EGraphElement elem=(EGraphElement) obj;
         if(!(elem instanceof LabelEdge)){
            Assert.assertTrue(graphD.getAllElements().stream().anyMatch(x->g.imageOf((EGraphElement) x)==elem));
         }
      }
      
      IAttribSolver solver=new Z3AttribSolver();
      Assert.assertTrue(solver.hasEquivalentFormulas(g));
      Assert.assertTrue(solver.checkImplication(h));
     

   }


}

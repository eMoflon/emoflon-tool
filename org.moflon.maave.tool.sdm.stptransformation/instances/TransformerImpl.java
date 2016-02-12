/**
 */
package org.moflon.maave.tool.sdm.stptransformation.impl;

import SDMLanguage.patterns.StoryPattern;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.moflon.maave.tool.sdm.stptransformation.AttributeConstraintTransformer;
import org.moflon.maave.tool.sdm.stptransformation.NACTransformer;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationPackage;
import org.moflon.maave.tool.sdm.stptransformation.StructureTransformer;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;

import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbolicGTRuleFactory;

import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;

import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Conjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Predicate;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraphsFactory;
// <-- [user defined imports]
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelEdge;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.eclipse.emf.ecore.util.EcoreUtil;
import java.util.LinkedList;
import java.util.List;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Quantifier;
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class TransformerImpl extends EObjectImpl implements Transformer
{
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected TransformerImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass()
   {
      return StptransformationPackage.Literals.TRANSFORMER;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public SymbGTRule transformStpToSymbGTRule(StoryPattern storyPattern)
   {
      // ActivityNode1
      Object[] result1_black = TransformerImpl.pattern_Transformer_0_1_ActivityNode1_blackB(this);
      if (result1_black == null)
      {
         throw new RuntimeException("Pattern matching in node [ActivityNode1] failed." + " Variables: " + "[this] = " + this + ".");
      }
      Object[] result1_green = TransformerImpl.pattern_Transformer_0_1_ActivityNode1_greenFFFFFFFFFFFFFFFFFF();
      SymbGTRule symbGTRule = (SymbGTRule) result1_green[0];
      // SymbolicGraph L = (SymbolicGraph) result1_green[1];
      // SymbolicGraph K = (SymbolicGraph) result1_green[2];
      StructureTransformer structureTransformer = (StructureTransformer) result1_green[3];
      // SymbolicGraph R = (SymbolicGraph) result1_green[4];
      // SymbolicGraphMorphism right = (SymbolicGraphMorphism) result1_green[5];
      // SymbolicGraphMorphism left = (SymbolicGraphMorphism) result1_green[6];
      AttributeConstraintTransformer attributeConstraintTransformer = (AttributeConstraintTransformer) result1_green[7];
      NACTransformer nACTransformer = (NACTransformer) result1_green[8];
      // Disjunction phi_L = (Disjunction) result1_green[9];
      // Disjunction phi_K = (Disjunction) result1_green[10];
      // Disjunction phi_R = (Disjunction) result1_green[11];
      // Conjunction con_L = (Conjunction) result1_green[12];
      // Conjunction con_K = (Conjunction) result1_green[13];
      // Conjunction con_R = (Conjunction) result1_green[14];
      // Predicate pred_L = (Predicate) result1_green[15];
      // Predicate pred_K = (Predicate) result1_green[16];
      // Predicate pred_R = (Predicate) result1_green[17];

      // ActivityNode84
      TransformerImpl.pattern_Transformer_0_2_ActivityNode84_expressionFBBB(structureTransformer, symbGTRule, storyPattern);
      // ActivityNode85
      TransformerImpl.pattern_Transformer_0_3_ActivityNode85_expressionBBB(attributeConstraintTransformer, storyPattern, symbGTRule);
      // ActivityNode88
      TransformerImpl.pattern_Transformer_0_4_ActivityNode88_expressionFBBB(nACTransformer, symbGTRule, storyPattern);
      return TransformerImpl.pattern_Transformer_0_5_expressionFB(symbGTRule);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public SymbGTRule transformStpToProjGTRule(StoryPattern storyPattern)
   {
      // [user code injected with eMoflon]
      SymbGTRule rule = this.transformStpToSymbGTRule(storyPattern);
      SymbolicGraphMorphism morK_L = rule.getLeft();
      SymbolicGraphMorphism morK_R = rule.getRight();

      SymbolicGraph L = morK_L.getCodom();
      Quantifier exitsL = SymbolicGraphsFactory.eINSTANCE.createExists();
      SymbolicGraph K = morK_L.getDom();
      Quantifier exitsK = SymbolicGraphsFactory.eINSTANCE.createExists();
      SymbolicGraph R = morK_R.getCodom();

      List<LabelNode> labelNodeList = new LinkedList<LabelNode>(K.getLabelNodes());
      for (LabelNode n : labelNodeList)
      {
         if (n.getPredicates().isEmpty())
         {

            EcoreUtil.delete(morK_L.imageOf(n));
            morK_L.removeMapping(n);
            EcoreUtil.delete(morK_R.imageOf(n));
            morK_R.removeMapping(n);
            EcoreUtil.delete(n);

         }

      }

      List<LabelEdge> labelEdgeList = new LinkedList<LabelEdge>(L.getLabelEdges());
      for (LabelEdge e : labelEdgeList)
      {

         if (e.getTarget() == null)
         {
            EcoreUtil.delete(e);
         }
      }
      labelEdgeList = new LinkedList<LabelEdge>(R.getLabelEdges());
      for (LabelEdge e : labelEdgeList)
      {

         if (e.getTarget() == null)
         {
            EcoreUtil.delete(e);
         }
      }
      labelNodeList = new LinkedList<LabelNode>(K.getLabelNodes());
      for (LabelNode n : labelNodeList)
      {
         if (morK_L.imageOf(n).getLabelEdge().isEmpty())
         {
            exitsL.getLabelNodes().add(morK_L.imageOf(n));
            exitsK.getLabelNodes().add(n);
            morK_L.removeMapping(n);
            morK_R.removeMapping(n);
         }
      }

      if (exitsL.getLabelNodes().isEmpty() == false)
      {
         L.getFormula().setQuantifier(exitsL);
      }
      if (exitsK.getLabelNodes().isEmpty() == false)
      {
         K.getFormula().setQuantifier(exitsK);
      }

      return rule;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException
   {
      switch (operationID)
      {
      case StptransformationPackage.TRANSFORMER___TRANSFORM_STP_TO_SYMB_GT_RULE__STORYPATTERN:
         return transformStpToSymbGTRule((StoryPattern) arguments.get(0));
      case StptransformationPackage.TRANSFORMER___TRANSFORM_STP_TO_PROJ_GT_RULE__STORYPATTERN:
         return transformStpToProjGTRule((StoryPattern) arguments.get(0));
      }
      return super.eInvoke(operationID, arguments);
   }

   public static final Object[] pattern_Transformer_0_1_ActivityNode1_blackB(Transformer _this)
   {
      return new Object[] { _this };
   }

   public static final Object[] pattern_Transformer_0_1_ActivityNode1_greenFFFFFFFFFFFFFFFFFF()
   {
      SymbGTRule symbGTRule = SymbolicGTRuleFactory.eINSTANCE.createSymbGTRule();
      SymbolicGraph l = SymbolicGraphsFactory.eINSTANCE.createSymbolicGraph();
      SymbolicGraph k = SymbolicGraphsFactory.eINSTANCE.createSymbolicGraph();
      StructureTransformer structureTransformer = StptransformationFactory.eINSTANCE.createStructureTransformer();
      SymbolicGraph r = SymbolicGraphsFactory.eINSTANCE.createSymbolicGraph();
      SymbolicGraphMorphism right = SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      SymbolicGraphMorphism left = SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      AttributeConstraintTransformer attributeConstraintTransformer = StptransformationFactory.eINSTANCE.createAttributeConstraintTransformer();
      NACTransformer nACTransformer = StptransformationFactory.eINSTANCE.createNACTransformer();
      Disjunction phi_L = SymbolicGraphsFactory.eINSTANCE.createDisjunction();
      Disjunction phi_K = SymbolicGraphsFactory.eINSTANCE.createDisjunction();
      Disjunction phi_R = SymbolicGraphsFactory.eINSTANCE.createDisjunction();
      Conjunction con_L = SymbolicGraphsFactory.eINSTANCE.createConjunction();
      Conjunction con_K = SymbolicGraphsFactory.eINSTANCE.createConjunction();
      Conjunction con_R = SymbolicGraphsFactory.eINSTANCE.createConjunction();
      Predicate pred_L = SymbolicGraphsFactory.eINSTANCE.createPredicate();
      Predicate pred_K = SymbolicGraphsFactory.eINSTANCE.createPredicate();
      Predicate pred_R = SymbolicGraphsFactory.eINSTANCE.createPredicate();
      String l_name_prime = "L";
      String k_name_prime = "K";
      String r_name_prime = "R";
      String pred_L_Symbol_prime = "#T";
      String pred_K_Symbol_prime = "#T";
      String pred_R_Symbol_prime = "#T";
      symbGTRule.getContainedSymbolicGraphs().add(l);
      symbGTRule.getContainedSymbolicGraphs().add(k);
      symbGTRule.getContainedSymbolicGraphs().add(r);
      symbGTRule.setRight(right);
      symbGTRule.getContainedGraphMorphism().add(right);
      right.setDom(k);
      right.setCodom(r);
      symbGTRule.setLeft(left);
      symbGTRule.getContainedGraphMorphism().add(left);
      left.setDom(k);
      left.setCodom(l);
      l.setFormula(phi_L);
      k.setFormula(phi_K);
      r.setFormula(phi_R);
      phi_L.getOf().add(con_L);
      phi_K.getOf().add(con_K);
      phi_R.getOf().add(con_R);
      con_L.getOf().add(pred_L);
      con_K.getOf().add(pred_K);
      con_R.getOf().add(pred_R);
      l.setName(l_name_prime);
      k.setName(k_name_prime);
      r.setName(r_name_prime);
      pred_L.setSymbol(pred_L_Symbol_prime);
      pred_K.setSymbol(pred_K_Symbol_prime);
      pred_R.setSymbol(pred_R_Symbol_prime);
      return new Object[] { symbGTRule, l, k, structureTransformer, r, right, left, attributeConstraintTransformer, nACTransformer, phi_L, phi_K, phi_R, con_L,
            con_K, con_R, pred_L, pred_K, pred_R };
   }

   public static final SymbGTRule pattern_Transformer_0_2_ActivityNode84_expressionFBBB(StructureTransformer structureTransformer, SymbGTRule symbGTRule,
         StoryPattern storyPattern)
   {
      SymbGTRule _localVariable_0 = structureTransformer.transform(symbGTRule, storyPattern);
      SymbGTRule _result = _localVariable_0;
      return _result;
   }

   public static final void pattern_Transformer_0_3_ActivityNode85_expressionBBB(AttributeConstraintTransformer attributeConstraintTransformer,
         StoryPattern storyPattern, SymbGTRule symbGTRule)
   {
      attributeConstraintTransformer.transform(storyPattern, symbGTRule);

   }

   public static final SymbGTRule pattern_Transformer_0_4_ActivityNode88_expressionFBBB(NACTransformer nACTransformer, SymbGTRule symbGTRule,
         StoryPattern storyPattern)
   {
      SymbGTRule _localVariable_0 = nACTransformer.transformNAC(symbGTRule, storyPattern);
      SymbGTRule _result = _localVariable_0;
      return _result;
   }

   public static final SymbGTRule pattern_Transformer_0_5_expressionFB(SymbGTRule symbGTRule)
   {
      SymbGTRule _result = symbGTRule;
      return _result;
   }

   // <-- [user code injected with eMoflon]

   // [user code injected with eMoflon] -->
} //TransformerImpl

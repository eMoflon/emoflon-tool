package org.moflon.maave.tool.symbolicgraphs.secondorder.util;

import java.security.InvalidParameterException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Conjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Constant;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Exists;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Parameter;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Predicate;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Quantifier;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraphsFactory;

public class FormulaUtil
{

   public static void conjunctCodomFormulawithDomFormula(SymbolicGraphMorphism mor)
   {
      SymbolicGraph to = mor.getCodom();
      SymbolicGraph from = mor.getDom();
      Disjunction phi_to = to.getFormula();
      Disjunction phi_from = from.getFormula();
      if (phi_to == null || phi_from == null)
      {
         throw new InvalidParameterException("Codomain and domain formulas must be valid");
      }
      Disjunction phi_new = SymbolicGraphsFactory.eINSTANCE.createDisjunction();
      Quantifier quantifier_from = phi_from.getQuantifier();
      Quantifier quantifier_to = phi_to.getQuantifier();
      Quantifier quantifier_from_new = copyQuantifierWithLabelNodes(quantifier_from);
      Quantifier quantifier_to_new = copyQuantifierWithLabelNodes(quantifier_to);

      for (Conjunction conj_from : from.getFormula().getOf())
      {
         for (Conjunction conj_to : phi_to.getOf())
         {
            Conjunction conj_new = SymbolicGraphsFactory.eINSTANCE.createConjunction();
            phi_new.getOf().add(conj_new);

            for (Predicate pred_from : conj_from.getOf())
            {
               Predicate pred_new = SymbolicGraphsFactory.eINSTANCE.createPredicate();
               conj_new.getOf().add(pred_new);
               pred_new.setSymbol(pred_from.getSymbol());

               for (Parameter param_from : pred_from.getParameters())
               {
                  if (param_from instanceof LabelNode)
                  {
                     LabelNode ln_from = (LabelNode) param_from;
                     LabelNode targetLn;
                     if (ln_from.eContainer() == quantifier_from)
                     {
                        targetLn = quantifier_from_new.getLabelNodes().get(quantifier_from.getLabelNodes().indexOf(param_from));
                     } else
                     {
                        targetLn = mor.imageOf(ln_from);
                     }
                     pred_new.getParameters().add(targetLn);
                  }
                  else if(param_from instanceof Constant)
                  {
                     Constant const_from =(Constant) param_from;
                     Constant const_new = EcoreUtil.copy(const_from);
                     pred_new.getParameters().add(const_new);
                  }
               }
            }
            for (Predicate pred_to : conj_to.getOf())
            {
               Predicate pred_new = SymbolicGraphsFactory.eINSTANCE.createPredicate();
               conj_new.getOf().add(pred_new);
               pred_new.setSymbol(pred_to.getSymbol());
               for (Parameter param_to : pred_to.getParameters())
               {
                  if (param_to instanceof LabelNode)
                  {
                     LabelNode ln_to = (LabelNode) param_to;
                     LabelNode targetLn;
                     if (ln_to.eContainer() == quantifier_to)
                     {
                        targetLn = quantifier_to_new.getLabelNodes().get(quantifier_to.getLabelNodes().indexOf(param_to));
                     } else
                     {
                        targetLn = ln_to;
                     }
                     pred_new.getParameters().add(targetLn);
                  }
                  else if(param_to instanceof Constant)
                  {
                     Constant const_to =(Constant) param_to;
                     Constant const_new = EcoreUtil.copy(const_to);
                     pred_new.getParameters().add(const_new);
                  }
               }
            }
         }
      }
      quantifier_to_new.getLabelNodes().addAll(quantifier_from_new.getLabelNodes());
      if (quantifier_to_new.getLabelNodes().isEmpty() == false)
      {
         phi_new.setQuantifier(quantifier_to_new);
      }
      to.setFormula(phi_new);
   }

   private static Quantifier copyQuantifierWithLabelNodes(Quantifier quantifier)
   {

      Quantifier newQuantifier = SymbolicGraphsFactory.eINSTANCE.createExists();
      if (quantifier != null)
      {
         for (LabelNode ln : quantifier.getLabelNodes())
         {
            LabelNode newLn = EcoreUtil.copy(ln);
            newLn.setType(ln.getType());
            newQuantifier.getLabelNodes().add(newLn);
         }
      }
      return newQuantifier;
   }

   public static void disjunctDomFormulawithCodomFormula(SymbolicGraphMorphism mor)
   {
      SymbolicGraph from = mor.getCodom();
      SymbolicGraph to = mor.getDom();
      Disjunction phi_To = to.getFormula();
      if (phi_To == null)
      {
         throw new InvalidParameterException("Domain formula must not to be empty");
      }

      Quantifier quantifierTo = (Exists) phi_To.getQuantifier();
      if (quantifierTo == null)
      {
         quantifierTo = SymbolicGraphsFactory.eINSTANCE.createExists();

      }

      for (Conjunction conj_From : from.getFormula().getOf())
      {
         Conjunction conj_To = SymbolicGraphsFactory.eINSTANCE.createConjunction();
         phi_To.getOf().add(conj_To);
         for (Predicate pred_From : conj_From.getOf())
         {
            Predicate pred_To = SymbolicGraphsFactory.eINSTANCE.createPredicate();
            conj_To.getOf().add(pred_To);
            pred_To.setSymbol(pred_From.getSymbol());
            for (Parameter param_From : pred_From.getParameters())
            {
               if (param_From instanceof LabelNode)
               {
                  LabelNode target = to.getLabelNodes().stream().filter(ln -> mor.imageOf(ln) == param_From).findAny().orElse(null);
                  if (target == null)
                  {
                     target = SymbolicGraphsFactory.eINSTANCE.createLabelNode();
                     target.setType(param_From.getType());
                     target.setLabel(((LabelNode) param_From).getLabel());
                     quantifierTo.getLabelNodes().add(target);
                  }
                  pred_To.getParameters().add(target);
               } else
               {
                  Constant const_d = phi_To.getConstant(((Constant) param_From).getInterpretation(), param_From.getType());
                  pred_To.getParameters().add(const_d);
               }
            }
         }
      }
      if (quantifierTo.getLabelNodes().isEmpty() == false)
      {
         phi_To.setQuantifier(quantifierTo);
      }
   }

   public static void copyFormulaFromCodomToDom(SymbolicGraphMorphism mor)
   {
      SymbolicGraph from = mor.getCodom();
      SymbolicGraph to = mor.getDom();
      Disjunction phi_To = SymbolicGraphsFactory.eINSTANCE.createDisjunction();
      to.setFormula(phi_To);
      Quantifier quantifier = SymbolicGraphsFactory.eINSTANCE.createExists();
      for (Conjunction conj_From : from.getFormula().getOf())
      {
         Conjunction conj_To = SymbolicGraphsFactory.eINSTANCE.createConjunction();
         phi_To.getOf().add(conj_To);
         for (Predicate pred_From : conj_From.getOf())
         {
            Predicate pred_To = SymbolicGraphsFactory.eINSTANCE.createPredicate();
            conj_To.getOf().add(pred_To);
            pred_To.setSymbol(pred_From.getSymbol());
            for (Parameter param_From : pred_From.getParameters())
            {
               if (param_From instanceof LabelNode)
               {
                  LabelNode target = to.getLabelNodes().stream().filter(ln -> mor.imageOf(ln) == param_From).findAny().orElse(null);
                  if (target == null)
                  {
                     target = SymbolicGraphsFactory.eINSTANCE.createLabelNode();
                     target.setType(param_From.getType());
                     target.setLabel(((LabelNode) param_From).getLabel());
                     quantifier.getLabelNodes().add(target);
                  }
                  pred_To.getParameters().add(target);
               } else
               {
                  Constant const_d = phi_To.getConstant(((Constant) param_From).getInterpretation(), param_From.getType());
                  pred_To.getParameters().add(const_d);
               }
            }
         }
      }
      if (quantifier.getLabelNodes().isEmpty() == false)
      {
         phi_To.setQuantifier(quantifier);
      }
   }

}
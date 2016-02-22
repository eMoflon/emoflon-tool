package org.moflon.maave.tool.symbolicgraphs.secondorder.util;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
      syntacticallySimplifyFormula(phi_new);
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
      SymbolicGraph codom = mor.getCodom();
      SymbolicGraph dom = mor.getDom();
      Disjunction phi_dom = dom.getFormula();
      Disjunction phi_codom = codom.getFormula();
      if (phi_dom == null||phi_codom==null)
      {
         throw new InvalidParameterException("Domain and Codomain formulas must not to be empty");
      }

      Quantifier quantifier_dom = (Exists) phi_dom.getQuantifier();
      if (quantifier_dom == null)
      {
         quantifier_dom = SymbolicGraphsFactory.eINSTANCE.createExists();

      }
      Map<LabelNode, LabelNode> quantLnMapDom_Codom=new HashMap<LabelNode, LabelNode>();
      //add quantified Label nodes in codomain to domain quantifier
      if(phi_codom.getQuantifier()!=null)
      {
         for (LabelNode quantLn_codom : phi_codom.getQuantifier().getLabelNodes())
         {

            LabelNode quantLN_dom=SymbolicGraphsFactory.eINSTANCE.createLabelNode();
            quantLN_dom.setType(quantLn_codom.getType());
            quantLN_dom.setLabel(quantLn_codom.getLabel());
            quantifier_dom.getLabelNodes().add(quantLN_dom);
            quantLnMapDom_Codom.put(quantLN_dom, quantLn_codom);
         }
      }
      //add quantified Label nodes to domain quantifier for each removed label node
      List<LabelNode> removedLnList_codom = mor.getCodom().getLabelNodes().stream().filter(ln->(mor.isInImage(ln)==false)).collect(Collectors.toList());
      for (LabelNode removedLn_codom : removedLnList_codom)
      {
         LabelNode quantLN_dom=SymbolicGraphsFactory.eINSTANCE.createLabelNode();
         quantLN_dom.setType(removedLn_codom.getType());
         quantLN_dom.setLabel(removedLn_codom.getLabel());
         quantifier_dom.getLabelNodes().add(quantLN_dom);
         quantLnMapDom_Codom.put(quantLN_dom, removedLn_codom);
      }
      //conjunct Domain Formula with codomain Formula
      for (Conjunction conj_codom : codom.getFormula().getOf())
      {
         Conjunction conj_dom = SymbolicGraphsFactory.eINSTANCE.createConjunction();
         phi_dom.getOf().add(conj_dom);
         for (Predicate pred_codom : conj_codom.getOf())
         {
            Predicate pred_dom = SymbolicGraphsFactory.eINSTANCE.createPredicate();
            conj_dom.getOf().add(pred_dom);
            pred_dom.setSymbol(pred_codom.getSymbol());
            for (Parameter param_codom : pred_codom.getParameters())
            {
               if (param_codom instanceof LabelNode)
               {
                  LabelNode ln_codom=(LabelNode) param_codom;
                  LabelNode ln_dom = dom.getLabelNodes().stream().filter(ln -> mor.imageOf(ln) == ln_codom).findAny().orElse(null);
                  if (ln_dom == null)
                  {
                     ln_dom = quantifier_dom.getLabelNodes().stream().filter(ln -> quantLnMapDom_Codom.get(ln) == param_codom).findAny().orElse(null);
                  }
                  pred_dom.getParameters().add(ln_dom);
               } else if(param_codom instanceof Constant)
               {
                  Constant const_codom=(Constant) param_codom;
                  Constant const_d = phi_dom.getConstant(const_codom.getInterpretation(), param_codom.getType());
                  pred_dom.getParameters().add(const_d);
               }
            }
         }
      }
      if (quantifier_dom.getLabelNodes().isEmpty() == false)
      {
         phi_dom.setQuantifier(quantifier_dom);
      }
      syntacticallySimplifyFormula(phi_dom);
   }

   public static void copyFormulaFromCodomToDom(SymbolicGraphMorphism mor)
   {
      SymbolicGraph to = mor.getDom();
      Disjunction phi_To = SymbolicGraphsFactory.eINSTANCE.createDisjunction();
      to.setFormula(phi_To);
      Conjunction con_to=SymbolicGraphsFactory.eINSTANCE.createConjunction();
      phi_To.getOf().add(con_to);
      Predicate pred_false=SymbolicGraphsFactory.eINSTANCE.createPredicate();
      pred_false.setSymbol("#F");
      con_to.getOf().add(pred_false);
      disjunctDomFormulawithCodomFormula(mor);


   }
   public static Disjunction createFalseFormula()
   {

      return createFormula(false);
   }

   private static Disjunction createFormula(boolean predicate)
   {
      Disjunction phi=SymbolicGraphsFactory.eINSTANCE.createDisjunction();
      Conjunction conj=SymbolicGraphsFactory.eINSTANCE.createConjunction();
      phi.getOf().add(conj);
      Predicate pred=SymbolicGraphsFactory.eINSTANCE.createPredicate();
      conj.getOf().add(pred);
      if(predicate)
      {
         pred.setSymbol("#T");
      }
      else
      {
         pred.setSymbol("#F");
      }
      return phi;
   }


   private static void syntacticallySimplifyFormula(Disjunction  phi){

      List<Conjunction>conjunctions=new LinkedList<Conjunction>(phi.getOf());
      for (Conjunction conj : conjunctions)
      {
         if(conj.getOf().stream().anyMatch(pred->pred.getSymbol().equals("#F")))
         {
            if(phi.getOf().size()>1)
            {
               phi.getOf().remove(conj);
            }
         }
         else
         {

            List<Predicate> predList=conj.getOf().stream().filter(pred->pred.getSymbol().equals("#T")).collect(Collectors.toList());
            for (Predicate predicate : predList)
            {
               if(conj.getOf().size()>1)
               {
                  conj.getOf().remove(predicate);
               }
            }

         }
      }





   }

   public static Disjunction createTrueFormula()
   {
      return createFormula(true);
   }

}
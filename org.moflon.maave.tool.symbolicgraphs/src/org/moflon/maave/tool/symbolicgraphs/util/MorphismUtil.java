package org.moflon.maave.tool.symbolicgraphs.util;

import org.moflon.maave.tool.symbolicgraphs.Datastructures.Mapping;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Conjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Constant;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Disjunction;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.LabelNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Parameter;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Predicate;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraphsFactory;

public class MorphismUtil
{
   public static void copyFormula(SymbolicGraph from , SymbolicGraph to, Mapping<LabelNode>fromTolabelNodeMapping)
   {
      Disjunction phi_To = SymbolicGraphsFactory.eINSTANCE.createDisjunction();
      to.setFormula(phi_To);
      for (Conjunction conj_from : from.getFormula().getOf())
      {
         Conjunction conj_To = SymbolicGraphsFactory.eINSTANCE.createConjunction();
         phi_To.getOf().add(conj_To);
         for (Predicate pred_from : conj_from.getOf())
         {
            Predicate pred_to = SymbolicGraphsFactory.eINSTANCE.createPredicate();
            conj_To.getOf().add(pred_to);
            pred_to.setSymbol(pred_from.getSymbol());
            for (Parameter param_from : pred_from.getParameters())
            {
               if (param_from instanceof LabelNode)
               {
                  LabelNode target = fromTolabelNodeMapping.imageOf((LabelNode) param_from);
                  pred_to.getParameters().add(target);
               } else
               {
                  Constant const_d = phi_To.getConstant(((Constant) param_from).getInterpretation(), param_from.getType());
                  pred_to.getParameters().add(const_d);
               }
            }
         }
      }
   }
}

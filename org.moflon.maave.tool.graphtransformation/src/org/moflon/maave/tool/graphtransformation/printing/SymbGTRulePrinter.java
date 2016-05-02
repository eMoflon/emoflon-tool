package org.moflon.maave.tool.graphtransformation.printing;

import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.graphtransformation.conditions.AtomicCond;
import org.moflon.maave.tool.graphtransformation.conditions.NegCond;
import org.moflon.maave.tool.graphtransformation.conditions.TrueCond;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Condition;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.printing.GraphAndMorphismPrinter;


public class SymbGTRulePrinter
{
   public static String print(SymbGTRule rule){
      
      SymbolicGraphMorphism l=rule.getLeft();
      SymbolicGraphMorphism r=rule.getRight();
     
      
      return rule.getName()+"\n"+GraphAndMorphismPrinter.internalPrintSpan(l, r);
   
      
   }
   public String printNac(SymbolicGraph graphL)
   {
      for (Condition cond : graphL.getConditions())
      {
         SymbolicGraphMorphism morP_C=getMorP_C(graphL, cond);
         if(morP_C!=null)
         {
            morP_C.getDom().setName("NotExists(a,true)");
            return GraphAndMorphismPrinter.print(morP_C);
         }
      }
      return "";
   }
   private SymbolicGraphMorphism getMorP_C(SymbolicGraph graphL, Condition cond)
   {
      if(cond instanceof NegCond)
      {
         NegCond negcond=(NegCond) cond;
         if(negcond.getNegCondition() instanceof AtomicCond)
         {
            AtomicCond atomicCond=(AtomicCond) negcond.getNegCondition();
            if(atomicCond.getNestedCondition() instanceof TrueCond)
            {
               if(atomicCond.getMorP_C().getDom()==graphL)
               {
                  return atomicCond.getMorP_C();
               }
            }
         }
      }
    return null;     
   }
}

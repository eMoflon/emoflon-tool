package org.moflon.maave.tool.analysis.confluence.prettyprinter;

import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisResult;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.printing.GraphAndMorphismPrinter;

public class ConfluenceAnalysisResultPrinter
{
   public static String printConfluencStatus(ConfluenceAnalysisResult result)
   {
//      SymbolicGraphMorphism first=status.getNonConfluentCriticalPair().getDer1().getSpan().getG();
//      SymbolicGraphMorphism second=status.getNonConfluentCriticalPair().getDer2().getSpan().getG();
      StringBuilder sb=new StringBuilder("\n"+result.getMessage()+"\n");
      sb.append("#critical pairs="+result.getNrOfCriticalpairs()+"\n");
      sb.append("Time for CPA="+((float)result.getTimeForCPA()/1000)+"sec\n");
      sb.append("Time for confluence="+((float)result.getTimeForConfluence()/1000)+"sec\n");
      sb.append("CONFLUENT="+result.isValid()+"\n");
      if(result.getNonConfluentCriticalPair()!=null)
      {
         sb.append("REASON:\n");
         SymbolicGraphMorphism first=result.getNonConfluentCriticalPair().getDer1().getMatch();
         SymbolicGraphMorphism second=result.getNonConfluentCriticalPair().getDer2().getMatch();
         sb.append(GraphAndMorphismPrinter.internalPrintPair(first, second));
      }
      return sb.toString();
   }
}

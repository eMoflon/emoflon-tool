package org.moflon.maave.tool.analysis.confluence.prettyprinter;

import org.moflon.maave.tool.analysis.confluence.ConfluenceStatus;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.printing.GraphAndMorphismPrinter;

public class ConfluenceStatusPrinter
{
   public static String printConfluencStatus(ConfluenceStatus status)
   {
//      SymbolicGraphMorphism first=status.getNonConfluentCriticalPair().getDer1().getSpan().getG();
//      SymbolicGraphMorphism second=status.getNonConfluentCriticalPair().getDer2().getSpan().getG();
      if(status.getNonConfluentCriticalPair()==null&&status.isValid())
      {
         return "CONFLUENT";
      }
      SymbolicGraphMorphism first=status.getNonConfluentCriticalPair().getDer1().getMatch();
      SymbolicGraphMorphism second=status.getNonConfluentCriticalPair().getDer2().getMatch();
      return GraphAndMorphismPrinter.internalPrintPair(first, second);
   }
}

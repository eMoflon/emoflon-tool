package org.moflon.maave.tool.analysis.confluence.prettyprinter;

import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisReport;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisResult;
import org.moflon.maave.tool.graphtransformation.DirectDerivationPair;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.printing.GraphAndMorphismPrinter;

public class ConfluenceAnalysisResultPrinter
{
   public static String printConfluenceStatusExample(ConfluenceAnalysisResult result)
   {
//      SymbolicGraphMorphism first=status.getNonConfluentCriticalPair().getDer1().getSpan().getG();
//      SymbolicGraphMorphism second=status.getNonConfluentCriticalPair().getDer2().getSpan().getG();
      StringBuilder sb=new StringBuilder("\n"+result.getMessage()+"\n");
      sb.append("#critical pairs="+result.getNrOfCriticalpairs()+"\n");
      sb.append("Time for CPA="+((float)result.getTimeForCPA()/1000)+"sec\n");
      sb.append("Time for confluence="+((float)result.getTimeForConfluence()/1000)+"sec\n");
      sb.append("CONFLUENT="+result.isValid()+"\n");
      if(result.getNonConfluentCriticalPairs().isEmpty()==false)
      {
         sb.append("-------------------------------------------------------- REASON  ------------------------------------------------------\n");
         for (DirectDerivationPair  criticalPair : result.getNonConfluentCriticalPairs())
         {
         sb.append("-----------------------------------------------NON CONFLUENT CRITICAL PAIR "+result.getNonConfluentCriticalPairs().indexOf(criticalPair)+"-------------------------------------\n");
         
         SymbolicGraphMorphism first=criticalPair.getDer1().getMatch();
         SymbolicGraphMorphism second=criticalPair.getDer2().getMatch();
         sb.append(GraphAndMorphismPrinter.internalPrintPair(first, second));
         
         
         }
         sb.append("===========================================================================");
      }
      return sb.toString();
   }
   public static String printConfluenceStatus(ConfluenceAnalysisResult result)
   {
//      SymbolicGraphMorphism first=status.getNonConfluentCriticalPair().getDer1().getSpan().getG();
//      SymbolicGraphMorphism second=status.getNonConfluentCriticalPair().getDer2().getSpan().getG();
      StringBuilder sb=new StringBuilder("\n"+result.getMessage()+"\n");
      sb.append("#critical pairs="+result.getNrOfCriticalpairs()+"\n");
      sb.append("Time for CPA="+((float)result.getTimeForCPA()/1000)+"sec\n");
      sb.append("Time for confluence="+((float)result.getTimeForConfluence()/1000)+"sec\n");
      sb.append("CONFLUENT="+result.isValid()+"\n");
      return sb.toString();
   }
   
   public static String printConfluenceReport(ConfluenceAnalysisReport report, boolean printStatistics, boolean printConfluent, boolean printNonConfluent, boolean printReason)
   {
      StringBuilder sb = new StringBuilder();
      for (ConfluenceAnalysisResult result : report.getConfluenceStates())
      {
         if (printNonConfluent&&result.isValid() == false)
         {
            sb.append("========================================================================================");
            sb.append(printConfluenceStatus(result, printStatistics, printReason));
         }
         if (printConfluent&&result.isValid() == true)
         {
            sb.append("========================================================================================");
            sb.append(printConfluenceStatus(result, printStatistics, printReason));
         }
      }
      return sb.toString();
   }
   public static String printConfluenceStatus(ConfluenceAnalysisResult result, boolean printStatistics, boolean printReason)
   {
//      SymbolicGraphMorphism first=status.getNonConfluentCriticalPair().getDer1().getSpan().getG();
//      SymbolicGraphMorphism second=status.getNonConfluentCriticalPair().getDer2().getSpan().getG();
      StringBuilder sb=new StringBuilder("\n"+result.getMessage()+"\n");
      if(printStatistics)
      {
      sb.append("#critical pairs="+result.getNrOfCriticalpairs()+"\n");
      sb.append("Time for CPA="+((float)result.getTimeForCPA()/1000)+"sec\n");
      sb.append("Time for confluence="+((float)result.getTimeForConfluence()/1000)+"sec\n");
      }
      sb.append("CONFLUENT="+result.isValid()+"\n");
      
      if(printReason && result.getNonConfluentCriticalPairs().isEmpty()==false)
      {
         sb.append("-------------------------------------------------------- REASON  ------------------------------------------------------\n");
         for (DirectDerivationPair  criticalPair : result.getNonConfluentCriticalPairs())
         {
         sb.append("-----------------------------------------------NON CONFLUENT CRITICAL PAIR "+result.getNonConfluentCriticalPairs().indexOf(criticalPair)+"-------------------------------------\n");
         
         SymbolicGraphMorphism first=criticalPair.getDer1().getMatch();
         SymbolicGraphMorphism second=criticalPair.getDer2().getMatch();
         sb.append(GraphAndMorphismPrinter.internalPrintPair(first, second));
         
         
         }
         sb.append("===========================================================================");
      }
      return sb.toString();
   }
}

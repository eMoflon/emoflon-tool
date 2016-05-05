package org.moflon.maave.tool.analysis.confluence.prettyprinter;

import java.util.Arrays;
import java.util.HashMap;

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
   
   public static String confluenceReportToTable(ConfluenceAnalysisReport report)
   {
//      ArrayList<ArrayList<String>> table=new ArrayList<ArrayList<String>>();
      HashMap<String, Integer> nameToIndex=new HashMap<String, Integer>();
      int i=0;
      for (ConfluenceAnalysisResult result : report.getConfluenceStates())
      {
         if(nameToIndex.containsKey(result.getRuleA())==false)
         {
            nameToIndex.put(result.getRuleA(), i);
            i++;
         }
      }
      String[][] table = new String[nameToIndex.keySet().size()+1][nameToIndex.keySet().size()+1];
      
      for (String st1 : nameToIndex.keySet())
      {
         String st=st1.replace("_", "\\_");
         table[0][nameToIndex.get(st1)+1]=st;
         
         table[nameToIndex.get(st1)+1][0]=st;
      }
      for (ConfluenceAnalysisResult result : report.getConfluenceStates())
      {
         int iy=nameToIndex.get(result.getRuleA())+1;
         int ix=nameToIndex.get(result.getRuleB())+1;
         if(result.isValid())
         {
//            if(result.getNrOfCriticalpairs()==0)
//            {
//               table[ix][iy]=" ";
//            }
//            else
//            {
//               table[ix][iy]=" ";
//            }
//            table[ix][iy]=String.valueOf(result.getNrOfCriticalpairs());
            table[ix][iy]=" ";
         }
         else
         {
            table[ix][iy]="X";
         }
         if(ix!=iy)
         {
            table[iy][ix]="o";
         }
      }
      StringBuilder tb=new StringBuilder();
      tb.append("\\begin{tabular}{|");
      for (int x = 0; x < table.length; x++)
      {
         tb.append("c|");
      }
      tb.append("|}\n");
      tb.append("\\hline \n");
      
      tb.append(Arrays.asList(table[0]).stream().map(x->"\\rot{"+x+"}").reduce((a, b) -> a+"&"+b).get());
      tb.append("\\\\ \\hline \n");
      for (int x = 1; x < table.length; x++)
      {
         if(x==0)
         {
            
         }
         tb.append(Arrays.asList(table[x]).stream().reduce((a, b) -> a+"&"+b).get());
         tb.append("\\\\ \\hline \n");
         
      }
      tb.append("\\end{tabular}");
      System.out.println(tb);
      
      return null;   
      
      
      
   }
}

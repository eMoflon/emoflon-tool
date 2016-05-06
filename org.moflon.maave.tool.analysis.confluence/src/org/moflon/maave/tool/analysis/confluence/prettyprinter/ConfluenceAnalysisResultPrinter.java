package org.moflon.maave.tool.analysis.confluence.prettyprinter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisReport;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisResult;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.CpaResult;

public class ConfluenceAnalysisResultPrinter
{
     
   private static String toCSVTable(String[][] table)
   {
     
      StringBuilder tb=new StringBuilder();
     
      for (int x = 0; x < table.length; x++)
      {
         tb.append(Arrays.asList(table[x]).stream().reduce((a, b) -> a+";"+b).get());
         tb.append("\n");

      }
      
      return tb.toString();
   }
   
   private static String toLatexTable(String[][] table)
   {
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
      return tb.toString();
   }

   public static String confluenceReportToCSVTable(ConfluenceAnalysisReport report, Function<ConfluenceAnalysisResult, String> function)
   {
      return toCSVTable(confluenceReportToTable(report, function)); 
   }
   public static String confluenceReportToLatexTable(ConfluenceAnalysisReport report, Function<ConfluenceAnalysisResult, String> function)
   {
      return toLatexTable(confluenceReportToTable(report, function)); 
   }
   public static String[][] confluenceReportToTable(ConfluenceAnalysisReport report, Function<ConfluenceAnalysisResult, String> function)
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

         String st=formatNames(st1);
         table[0][nameToIndex.get(st1)+1]=st;

         table[nameToIndex.get(st1)+1][0]=st;
      }
      for (ConfluenceAnalysisResult result : report.getConfluenceStates())
      {
         int iy=nameToIndex.get(result.getRuleA())+1;
         int ix=nameToIndex.get(result.getRuleB())+1;
         table[ix][iy]=function.apply(result);
         if(ix!=iy)
         {
            table[iy][ix]="o";
         }
      }


      return table;   



   }
   public static String formatNames(String st1)
   {
      int index1=st1.indexOf("_");
      int index2=st1.lastIndexOf("_");
      if(index1>=0&& index2>=0)
      {
         String name=st1.subSequence(index1+1,index2).toString();
         String version=st1.subSequence(index2+1,st1.length()).toString();
         if(version.equals("v1"))
         {
            name=name+"'";
         }
         return name;
      }
      return st1;
    
   }
   public static ConfluenceAnalysisReport getAverageReport(List<ConfluenceAnalysisReport> reports)
   {
      ConfluenceAnalysisReport averageReport=ConfluenceFactory.eINSTANCE.createConfluenceAnalysisReport();
      //init First
      for (ConfluenceAnalysisResult result : reports.get(0).getConfluenceStates())
      {
         ConfluenceAnalysisResult averageResult=ConfluenceFactory.eINSTANCE.createConfluenceAnalysisResult();
         averageResult.setRuleA(result.getRuleA());
         averageResult.setRuleB(result.getRuleB());
         averageReport.getConfluenceStates().add(averageResult);
      }
      
      if(reports.size()>5)
      {
         reports=reports.subList(5, reports.size());
      }
      for (int i=0;i<averageReport.getConfluenceStates().size();i++)
      {
         ConfluenceAnalysisResult averageResult=averageReport.getConfluenceStates().get(i);
         CpaResult aCpa=ConfluenceFactory.eINSTANCE.createCpaResult();
         averageResult.setCpaResult(aCpa);
         for (ConfluenceAnalysisResult matchingResult : getMatchingResults(averageResult, reports))
         {
            averageResult.setTimeForConfluence(averageResult.getTimeForConfluence()+matchingResult.getTimeForConfluence());
            averageResult.setTimeOverall(averageResult.getTimeOverall()+matchingResult.getTimeOverall());
            averageResult.setNrOfNonConfluentCriticalPairs(averageResult.getNrOfNonConfluentCriticalPairs()+matchingResult.getNrOfNonConfluentCriticalPairs());
            CpaResult mCpa=matchingResult.getCpaResult();
            aCpa.setNrOfConsistentMinContexts(aCpa.getNrOfConsistentMinContexts()+mCpa.getNrOfConsistentMinContexts());
            aCpa.setNrOfCritPairs(aCpa.getNrOfCritPairs()+mCpa.getNrOfCritPairs());
            aCpa.setNrOfMinContexts(aCpa.getNrOfMinContexts()+mCpa.getNrOfMinContexts());
            aCpa.setTimeCalcMinContexts(aCpa.getTimeCalcMinContexts()+mCpa.getTimeCalcMinContexts());
            aCpa.setTimeFilteringConsistent(aCpa.getTimeFilteringConsistent()+mCpa.getTimeFilteringConsistent());
            aCpa.setTimeForCalcCritPairs(aCpa.getTimeForCalcCritPairs()+mCpa.getTimeForCalcCritPairs());
         }
         
         averageResult.setTimeForConfluence(averageResult.getTimeForConfluence()/reports.size());
         averageResult.setTimeOverall(averageResult.getTimeOverall()/reports.size());
         averageResult.setNrOfNonConfluentCriticalPairs(averageResult.getNrOfNonConfluentCriticalPairs()/reports.size());
         aCpa.setNrOfConsistentMinContexts(aCpa.getNrOfConsistentMinContexts()/reports.size());
         aCpa.setNrOfCritPairs(aCpa.getNrOfCritPairs()/reports.size());
         aCpa.setNrOfMinContexts(aCpa.getNrOfMinContexts()/reports.size());
         aCpa.setTimeCalcMinContexts(aCpa.getTimeCalcMinContexts()/reports.size());
         aCpa.setTimeFilteringConsistent(aCpa.getTimeFilteringConsistent()/reports.size());
         aCpa.setTimeForCalcCritPairs(aCpa.getTimeForCalcCritPairs()/reports.size());
      }
      return averageReport;
  
   }
   private static List<ConfluenceAnalysisResult> getMatchingResults(ConfluenceAnalysisResult newResult,List<ConfluenceAnalysisReport> reports)
   {
      List<ConfluenceAnalysisResult> matchingResults=new LinkedList<ConfluenceAnalysisResult>();
      for (ConfluenceAnalysisReport report : reports)
      {
         for (ConfluenceAnalysisResult result : report.getConfluenceStates())
         {
            if(newResult.getRuleA().equals(result.getRuleA())&&newResult.getRuleB().equals(result.getRuleB()))
            {
               matchingResults.add(result);
               break;
            }
         }
      }
      return matchingResults;
   }
}

package org.moflon.maave.tests.testsuite.helper;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.moflon.maave.tool.analysis.acenforcment.ACEnforcementResult;
import org.moflon.maave.tool.analysis.acenforcment.ACEnforcmentReport;
import org.moflon.maave.tool.analysis.acenforcment.AcenforcmentFactory;
import org.moflon.maave.tool.analysis.acenforcment.impl.ACEnforcmentReportImpl;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisReport;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisResult;
import org.moflon.maave.tool.analysis.confluence.prettyprinter.ConfluenceAnalysisResultPrinter;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;

public class EvalHelper
{
   public static ACEnforcmentReport mergeReports(List<ACEnforcmentReport> reports)
   {

      Collection<SymbGTRule> rules=((ACEnforcmentReportImpl)reports.get(0)).ruleToResultMap.keySet();
      ACEnforcmentReportImpl newReport=(ACEnforcmentReportImpl) AcenforcmentFactory.eINSTANCE.createACEnforcmentReport();
      newReport.setNrOfConstraints(0);
      newReport.setNrOfRules(0);
      newReport.setTimeMinimize(0);
      newReport.setTimeOverall(0);
      if(reports.size()>5){
         for (int i = 0; i < 5; i++)
         {
            reports.remove(0);  
         }
      }
      for (ACEnforcmentReport acEnforcmentReport : reports)
      {
         newReport.setNrOfConstraints(newReport.getNrOfConstraints()+acEnforcmentReport.getNrOfConstraints());
         newReport.setNrOfRules(newReport.getNrOfRules()+acEnforcmentReport.getNrOfRules());
         newReport.setTimeMinimize(newReport.getTimeMinimize()+acEnforcmentReport.getTimeMinimize());
         newReport.setTimeOverall(newReport.getTimeOverall()+acEnforcmentReport.getTimeOverall());
      }
      
      newReport.setNrOfConstraints(newReport.getNrOfConstraints()/reports.size());
      newReport.setNrOfRules(newReport.getNrOfRules()/reports.size());
      newReport.setTimeMinimize(newReport.getTimeMinimize()/reports.size());
      newReport.setTimeOverall(newReport.getTimeOverall()/reports.size());
      
      for (SymbGTRule rule : rules)
      {
         ACEnforcementResult newResult = newReport.lookupResult(rule);
         newResult.setRuleName(rule.getName());
         newResult.setNrOfPreAC(0);
         newResult.setNrOfPreACRaw(0);
         newResult.setNrOfPreAcRemGuaranteeingAC(0);
         newResult.setTimeConstToPrecond(0);
         newResult.setTimeMinimizeRedAC(0);
         newResult.setTimeRemoveGuaranteeingAC(0);
         newResult.setNrOfPostNACs(0);
         newResult.setTimeForPostNacs(0);
         newResult.setTimePostToPre(0);
         

         for (ACEnforcmentReport acEnforcmentReport : reports)
         {
            ACEnforcmentReportImpl report=(ACEnforcmentReportImpl) acEnforcmentReport;
            SymbGTRule currentRule=report.ruleToResultMap.keySet().stream().filter(x->x.getName().equals(rule.getName())).findAny().get();
            newResult.setNrOfPreAC(acEnforcmentReport.lookupResult(currentRule).getNrOfPreAC()+newResult.getNrOfPreAC());
            newResult.setNrOfPreACRaw(acEnforcmentReport.lookupResult(currentRule).getNrOfPreACRaw()+newResult.getNrOfPreACRaw());
            newResult.setNrOfPreAcRemGuaranteeingAC(acEnforcmentReport.lookupResult(currentRule).getNrOfPreAcRemGuaranteeingAC()+newResult.getNrOfPreAcRemGuaranteeingAC());
            newResult.setTimeConstToPrecond(acEnforcmentReport.lookupResult(currentRule).getTimeConstToPrecond()+newResult.getTimeConstToPrecond());
            newResult.setTimeMinimizeRedAC(acEnforcmentReport.lookupResult(currentRule).getTimeMinimizeRedAC()+newResult.getTimeMinimizeRedAC());
            newResult.setTimeRemoveGuaranteeingAC(acEnforcmentReport.lookupResult(currentRule).getTimeRemoveGuaranteeingAC()+newResult.getTimeRemoveGuaranteeingAC());
            newResult.setNrOfPostNACs(acEnforcmentReport.lookupResult(currentRule).getNrOfPostNACs()+newResult.getNrOfPostNACs());
            newResult.setTimeForPostNacs(acEnforcmentReport.lookupResult(currentRule).getTimeForPostNacs()+newResult.getTimeForPostNacs());
            newResult.setTimePostToPre(acEnforcmentReport.lookupResult(currentRule).getTimePostToPre()+newResult.getTimePostToPre());
         }
         newResult.setNrOfPreAC(newResult.getNrOfPreAC()/reports.size());
         newResult.setNrOfPreACRaw(newResult.getNrOfPreACRaw()/reports.size());
         newResult.setNrOfPreAcRemGuaranteeingAC(newResult.getNrOfPreAcRemGuaranteeingAC()/reports.size());
         newResult.setTimeConstToPrecond(newResult.getTimeConstToPrecond()/reports.size());
         newResult.setTimeMinimizeRedAC(newResult.getTimeMinimizeRedAC()/reports.size());
         newResult.setTimeRemoveGuaranteeingAC(newResult.getTimeRemoveGuaranteeingAC()/reports.size());
         newResult.setNrOfPostNACs(newResult.getNrOfPostNACs()/reports.size());
         newResult.setTimeForPostNacs(+newResult.getTimeForPostNacs()/reports.size());
         newResult.setTimePostToPre(+newResult.getTimePostToPre()/reports.size());
      }
      return newReport;
      
   }
   public static void reportToFile(ConfluenceAnalysisReport report)
   {
      Function<ConfluenceAnalysisResult, String> fTimeOverall = x ->
      {  
         return   String.valueOf(x.getTimeOverall());

      };
      Function<ConfluenceAnalysisResult, String> fTimeConfluence = x ->
      {  
         return   String.valueOf(x.getTimeForConfluence());

      };
      Function<ConfluenceAnalysisResult, String> fTimeCalcMinContexts = x ->
      {  
         return   String.valueOf(x.getCpaResult().getTimeCalcMinContexts());

      };
      Function<ConfluenceAnalysisResult, String> fTimeFilteringConsistent = x ->
      {  
         return   String.valueOf(x.getCpaResult().getTimeFilteringConsistent());

      };
      Function<ConfluenceAnalysisResult, String> fTimeCalcCritpairs = x ->
      {  
         return   String.valueOf(x.getCpaResult().getTimeForCalcCritPairs());

      };
      ////
      Function<ConfluenceAnalysisResult, String> fNrNonConfluentPairs = x ->
      {  
         return   String.valueOf(x.getNrOfNonConfluentCriticalPairs());

      };

      Function<ConfluenceAnalysisResult, String> fNrMincontexts = x ->
      {  
         return   String.valueOf(x.getCpaResult().getNrOfMinContexts());

      };
      Function<ConfluenceAnalysisResult, String> fNrOfConsistentMinContexts = x ->
      {  
         return   String.valueOf(x.getCpaResult().getNrOfConsistentMinContexts());

      };
      Function<ConfluenceAnalysisResult, String> fNrOfCritPairs = x ->
      {  
         return   String.valueOf(x.getCpaResult().getNrOfCritPairs());

      };


      Function<ConfluenceAnalysisResult, String> fTimeCPAvsConfluenceL = x ->
      {  
         return   "\\makecell{"+(x.getTimeOverall()-x.getTimeForConfluence())+" | "+x.getTimeForConfluence()+"}";

      };
      Function<ConfluenceAnalysisResult, String> fNumbersL = x ->
      {  
         return   "\\makecell{"+ x.getCpaResult().getNrOfMinContexts()+" | "+
               x.getCpaResult().getNrOfConsistentMinContexts()+" | "+
               x.getCpaResult().getNrOfCritPairs()+" | "+
               x.getNrOfNonConfluentCriticalPairs()+"}";


      };
      Function<ConfluenceAnalysisResult, String> fTimesL = x ->
      {  
         return   "\\makecell{"+ x.getCpaResult().getTimeCalcMinContexts()+" | "+
               x.getCpaResult().getTimeFilteringConsistent()+" | "+
               x.getCpaResult().getTimeForCalcCritPairs()+" | "+
               x.getTimeForConfluence()+"}";


      };

      Function<ConfluenceAnalysisResult, String> fNumberVSTimesL = x ->
      {  
         return   "\\makecell{"+x.getCpaResult().getNrOfMinContexts()+" | "+
               x.getCpaResult().getNrOfConsistentMinContexts()+" | "+
               x.getCpaResult().getNrOfCritPairs()+" | "+
               x.getNrOfNonConfluentCriticalPairs() +"\\\\"+
               x.getCpaResult().getTimeCalcMinContexts()+" | "+
               x.getCpaResult().getTimeFilteringConsistent()+" | "+
               x.getCpaResult().getTimeForCalcCritPairs()+" | "+
               x.getTimeForConfluence()+"}";


      };
      try
      {
     
      FileUtils.writeStringToFile(new File("instances/TimeOverall.csv"), ConfluenceAnalysisResultPrinter.confluenceReportToCSVTable(report,fTimeOverall));
      
      
      FileUtils.writeStringToFile(new File("instances/TimeConfluence.csv"), ConfluenceAnalysisResultPrinter.confluenceReportToCSVTable(report,fTimeConfluence));

      
      FileUtils.writeStringToFile(new File("instances/TimeCalcMinContexts.csv"), ConfluenceAnalysisResultPrinter.confluenceReportToCSVTable(report,fTimeCalcMinContexts));

     
      FileUtils.writeStringToFile(new File("instances/TimeFilteringConsistent.csv"), ConfluenceAnalysisResultPrinter.confluenceReportToCSVTable(report,fTimeFilteringConsistent));

      
      FileUtils.writeStringToFile(new File("instances/TimeCalcCritpairs.csv"),ConfluenceAnalysisResultPrinter.confluenceReportToCSVTable(report,fTimeCalcCritpairs));

     
      FileUtils.writeStringToFile(new File("instances/NrMincontexts.csv"), ConfluenceAnalysisResultPrinter.confluenceReportToCSVTable(report,fNrMincontexts));

      
      FileUtils.writeStringToFile(new File("instances/NrOfConsistentMinContexts.csv"), ConfluenceAnalysisResultPrinter.confluenceReportToCSVTable(report,fNrOfConsistentMinContexts));

    
      FileUtils.writeStringToFile(new File("instances/NrOfCritPairs.csv"),ConfluenceAnalysisResultPrinter.confluenceReportToCSVTable(report,fNrOfCritPairs));

     
      FileUtils.writeStringToFile(new File("instances/NrNonConfluentPairs.csv"),ConfluenceAnalysisResultPrinter.confluenceReportToCSVTable(report,fNrNonConfluentPairs));

      
      FileUtils.writeStringToFile(new File("instances/TimeOverall.tex"), ConfluenceAnalysisResultPrinter.confluenceReportToLatexTable(report,fTimeOverall));

    
      FileUtils.writeStringToFile(new File("instances/TimeCPAvsConfluence.tex"), ConfluenceAnalysisResultPrinter.confluenceReportToLatexTable(report,fTimeCPAvsConfluenceL));

      
      FileUtils.writeStringToFile(new File("instances/Numbers.tex"), ConfluenceAnalysisResultPrinter.confluenceReportToLatexTable(report,fNumbersL));

     
      FileUtils.writeStringToFile(new File("instances/Times.tex"), ConfluenceAnalysisResultPrinter.confluenceReportToLatexTable(report,fTimesL));
      
      
      FileUtils.writeStringToFile(new File("instances/NumberVSTimes.tex"), ConfluenceAnalysisResultPrinter.confluenceReportToLatexTable(report,fNumberVSTimesL));
     
     
      StringBuilder sb=new StringBuilder();
      sb.append("OverallNrOfMinCtx;OverallNrOfConsistMinCtx;OverallNrOfCritPairs;OverallNrOfNonConfluentCritPairs\n");
      sb.append(report.getOverallNrOfMinCtx()+";"+report.getOverallNrOfConsistMinCtx()+";"+report.getOverallNrOfCritPairs()+";"+report.getOverallNrOfNonConfluentCritPairs()+"\n");
      FileUtils.writeStringToFile(new File("instances/Overall Numbers.tex"), sb.toString());
      
      } catch (IOException e)
      {
         e.printStackTrace();
      }
   }
}

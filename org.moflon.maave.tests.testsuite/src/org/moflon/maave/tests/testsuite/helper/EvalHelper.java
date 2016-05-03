package org.moflon.maave.tests.testsuite.helper;

import java.util.Collection;
import java.util.List;

import org.moflon.maave.tool.analysis.acenforcment.ACEnforcementResult;
import org.moflon.maave.tool.analysis.acenforcment.ACEnforcmentReport;
import org.moflon.maave.tool.analysis.acenforcment.AcenforcmentFactory;
import org.moflon.maave.tool.analysis.acenforcment.impl.ACEnforcmentReportImpl;
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
}

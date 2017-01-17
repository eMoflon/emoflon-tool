using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Util;
using EAEcoreAddin.Consistency.Rules;

namespace EAEcoreAddin.Consistency.RuleHandling
{
    public partial class NewErrorOutput : UserControl
    {
        SQLRepository sqlRepository;
        RuleControl ruleControl;
        RuleQuickfixDialogue quickFixDialogue;

        List<RuleResult> shownResults = new List<RuleResult>();

        public String ErrorSummary { get; set; }
        public Boolean ErrorsExisting { get; set; }
        
        public NewErrorOutput()
        {
            InitializeComponent();
            consistencyDataGridView1.initializeComponent(this);
            setSummaryLabel(0 + " Message(s), " + 0 + " Warning(s), " + 0 + " Error(s), " + 0 + " Fatal Error(s), " + 0 + " Eclipse Error(s)");
        }


        public void setClassMembers(SQLRepository repository, RuleControl ruleControl)
        {
            this.sqlRepository = repository;
            this.ruleControl = ruleControl;  
        }

        public void clearOutput()
        {
            if (InvokeRequired)
            {
                Invoke(new MethodInvoker(() => { clearOutput(); }));
            }
            else
            {
                this.consistencyDataGridView1.clearEntries();
                this.labelSummary.Text = "";
            }
        }



        public void writeOutput()
        {
            if (ruleControl != null)
            {
                this.shownResults.Clear();
                ruleControl.sortRuleResults();

                //clear output so our own output can be displayed
                this.consistencyDataGridView1.clearEntries();
                int countWarning = 0;
                int countError = 0;
                int countInformation = 0;
                int countErrorCritical = 0;
                int countEclipseErrors = 0;
                //pairOfRuleAndElementIds contains the ids of all rules which have been failed and the ids of the checked objetcs
                for (int i = 0; i < ruleControl.getRuleResults().Count; i++)
                {
                    RuleResult ruleResult = ruleControl.getRuleResults()[i];
                    Boolean writeResult = false;
                    switch (ruleResult.ErrorLevel)
                    {
                        case RuleErrorLevel.Information:
                                countInformation++;
                                writeResult = true;                              
                            break;
                        case RuleErrorLevel.Warning:
                                countWarning++;
                                writeResult = true;                                                 
                            break;
                        case RuleErrorLevel.Error:
                                countError++;
                                writeResult = true;                           
                            break;
                        case RuleErrorLevel.Fatal:
                                countErrorCritical++;
                                writeResult = true;                       
                            break;
                        case RuleErrorLevel.Eclipse:
                                countEclipseErrors++;
                                writeResult = true;
                            break;
                    }
                    if (writeResult)
                    {
                        this.consistencyDataGridView1.addRow(ruleResult.RuleID, ruleResult.NameOfObject + "(" + ruleResult.TypeOfObject + ")", ruleResult.ErrorOutput, ruleResult.ErrorLevel.ToString());
                        this.shownResults.Add(ruleResult);
                    }
                }

                this.consistencyDataGridView1.setCellSizes();

                //run export after validation if there are only informations and warnings
                if (countError > 0 || countErrorCritical > 0 || countEclipseErrors > 0)
                {
                    ruleControl.ConsistencyModule.LastCheckSuccessful = false;
                }
                else
                {
                    ruleControl.ConsistencyModule.LastCheckSuccessful = true;
                }

                String outputSummary = countInformation + " Message(s), " + countWarning + " Warning(s), " + countError + " Error(s), " + countErrorCritical + " Fatal Error(s), " + countEclipseErrors + " Eclipse Error(s)";

                setSummaryLabel(outputSummary);

                this.ErrorSummary = outputSummary;
                this.ErrorsExisting = (countEclipseErrors > 0 || countError > 0 || countErrorCritical > 0 || countInformation > 0 || countWarning > 0);

            }
        }

        private void setSummaryLabel(String labelText)
        {
            if(labelSummary.InvokeRequired)
            {
                Invoke(new MethodInvoker(() => { setSummaryLabel(labelText); }));
            }
            else
            {
                labelSummary.Text = labelText;
            }
                
        }
     
        public void errorSelected(int index)
        {
            RuleResult ruleResult = this.shownResults[index];
            Object eaObject = ruleResult.EaObject;
            Rule rule = ruleControl.getRule(ruleResult.RuleID);
            //try to perform onClick action
            try
            {
                //"check" if eaObject still existing. otherwise throw exception -> result outdated
                Object eaObject2 = EAUtil.sqlEAObjectToOriginalObject(sqlRepository, ruleResult.EaObject);


                EAUtil.markObjectInProjectBrowser(eaObject, sqlRepository);
                EAUtil.markObjectOnDiagram(eaObject, sqlRepository);
            }
            //error during action. rule is outdated and should be removed from the output window
            catch
            {
                ruleControl.deleteRuleResult(rule.getRuleID(), ruleResult.ObjectID);
                writeOutput();
            }         
        }

        public void errorDoubleClicked(int index)
        {
            RuleResult ruleResult = this.shownResults[index];
            EAEcoreAddin.Consistency.Rules.Rule rule = ruleControl.getRule(ruleResult.RuleID);

            try
            {
                quickFixDialogue = new RuleQuickfixDialogue(ruleResult, sqlRepository);
                //delete the current rule and recheck 
                ruleControl.deleteRuleResult(rule.getRuleID(), ruleResult.ObjectID);

                if (rule is GlobalRule<SQLElement> || rule is GlobalRule<SQLConnector>)
                {
                    ruleControl.doGlobalRules(sqlRepository);
                }


                if (ruleResult.ObjectType == EA.ObjectType.otAttribute)
                {
                    ruleResult.EaObject = sqlRepository.GetAttributeByID(ruleResult.ObjectID);
                }
                else if (ruleResult.ObjectType == EA.ObjectType.otElement)
                {
                    ruleResult.EaObject = sqlRepository.GetElementByID(ruleResult.ObjectID);
                }
                else if (ruleResult.ObjectType == EA.ObjectType.otMethod)
                {
                    ruleResult.EaObject = sqlRepository.GetMethodByID(ruleResult.ObjectID);
                }
                else if (ruleResult.ObjectType == EA.ObjectType.otPackage)
                {
                    ruleResult.EaObject = sqlRepository.GetPackageByID(ruleResult.ObjectID);
                }
                else if (ruleResult.ObjectType == EA.ObjectType.otDiagram)
                {
                    ruleResult.EaObject = sqlRepository.GetDiagramByID(ruleResult.ObjectID);
                }

                ruleControl.doSingleRule(ruleResult.EaObject, sqlRepository, rule);
                writeOutput();
            }
            //error during action rule is outdated and should be removed from the output window
            catch(Exception)
            {
                if (rule != null)
                    ruleControl.deleteRuleResult(rule.getRuleID(), ruleResult.ObjectID);
                writeOutput();
            }
            
        }

        private void smoothTableLayoutPanel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void consistencyDataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void buttonDeleteResults_Click(object sender, EventArgs e)
        {
            ruleControl.clearRuleResults();
            writeOutput();
        }


       
    }
}

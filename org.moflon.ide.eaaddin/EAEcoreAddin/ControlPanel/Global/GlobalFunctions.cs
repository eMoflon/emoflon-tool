using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.TGGModeling;
using System.Threading;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Persistency;
using EAEcoreAddin.Modeling.SDMModeling.Util;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.Consistency.PropertyFile;
using EAEcoreAddin.Consistency.RuleHandling;
using EAEcoreAddin.Import;
using EAEcoreAddin.Import.Gui;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.ControlPanel.Navigation;
using EAEcoreAddin.ControlPanel.Global;

namespace EAEcoreAddin.ControlPanel.Global
{
    public partial class GlobalFunctions : UserControl
    {

        public BackgroundWorker worker { get; set; }

        SQLRepository repository;

        private Boolean run = true;

        public GlobalFunctions()
        {
            InitializeComponent();

           Consistency.ConsistencyModule.RuleErrorOutputControls.Add(this.newErrorOutput1);
        }

        public void stopFunctions()
        {
            this.run = false;
            worker.CancelAsync();
        }
        
        public void resetFunctions(EA.Repository repository)
        {

            if (worker != null)
            {
                worker.CancelAsync();
            }


            initializeFunctions(repository);

            ContextMenu = new GlobalFunctionsContextMenu(repository);
        }

        private void initializeFunctions(EA.Repository repository)
        {
            this.repository = new SQLRepository(repository, false);
            this.run = true;
        }

        private void worker_DoWork(object sender, DoWorkEventArgs e)
        {
            while (run)
            {

                
            }
        }

        private void worker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {

        }

        private void worker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {

        }


        private void buttonExportSelection_Click(object sender, EventArgs e)
        {
            SQLRepository sqlRepository = new SQLRepository(repository.GetOriginalRepository(), true);
            Export exporter = new Export(sqlRepository, false);
            exporter.doExport();
        }

        private void buttonExportAll_Click(object sender, EventArgs e)
        {
            SQLRepository sqlRepository = new SQLRepository(repository.GetOriginalRepository(), true);
            Export exporter = new Export(sqlRepository, true);
            exporter.doExport();
        }

        private void buttonValidateSelection_Click(object sender, EventArgs e)
        {
            SQLRepository sqlRepository = new SQLRepository(repository.GetOriginalRepository(), false);
            Main.consistencyModule.dispatchSelectionRulecheck(ref sqlRepository);
        }


        private void buttonValidatePackage_Click(object sender, EventArgs e)
        {
            doValidationAction(false);
        }

        private void doValidationAction(Boolean validateEverything)
        {
            SQLRepository sqlRepository = new SQLRepository(repository.GetOriginalRepository(), false);
            Main.consistencyModule.dispatchFullRulecheck(ref sqlRepository, validateEverything);
            if (Main.consistencyModule.LastCheckSuccessful && Main.consistencyModule.RunExportAfter && !Main.consistencyModule.Canceled)
            {
                Export exporter = new Export(sqlRepository, validateEverything);
                exporter.doExport();
            }
            else if (!Main.consistencyModule.LastCheckSuccessful && Main.consistencyModule.RunExportAfter)
            {
                Form unableToExportDialog = new UnableToExportDialog();
                unableToExportDialog.ShowDialog();
            }
        }

        private void buttonValidateAll_Click(object sender, EventArgs e)
        {
            doValidationAction(true);
        }

        private void buttonExtractSDM_Click(object sender, EventArgs e)
        {
            ExtractSDM exctractSDMForm = new ExtractSDM(repository);
        }

        private void buttonDeriveNewTGGRule_Click(object sender, EventArgs e)
        {
            DeriveNewTGGRule deriveDialog = new DeriveNewTGGRule(repository);
        }

        private void buttonSDMVerbose_Click(object sender, EventArgs e)
        {
            EA.Diagram currentDiagram = repository.GetCurrentDiagram();
            if(currentDiagram.Notes == "Moflon::Verbose=true")
                SDMUtil.setAllVerboseTags(false, repository, currentDiagram);
            else if(currentDiagram.Notes == "Moflon::Verbose=false")
                SDMUtil.setAllVerboseTags(true, repository, currentDiagram);
            else
                SDMUtil.setAllVerboseTags(true, repository, currentDiagram);
        }

        private void buttonTGGVerbose_Click(object sender, EventArgs e)
        {
            buttonSDMVerbose_Click(sender, e);
        }


        private void button2_Click(object sender, EventArgs e)
        {
            ImportDialog importDialog = new ImportDialog(repository);   
        }

        private void tableLayoutPanel1_SizeChanged(object sender, EventArgs e)
        {

        }

        private void tableLayoutPanel1_Paint(object sender, PaintEventArgs e)
        {

        }

        

        private void buttonAnchor_Click(object sender, EventArgs e)
        {
            EA.Diagram currentDiagram = repository.GetCurrentDiagram();

            if (currentDiagram != null)
            {
                Navigator.jumpToAnchorPredecessor(repository.GetOriginalRepository(), currentDiagram.DiagramID);
            }
        }

        private void buttonJump_Click(object sender, EventArgs e)
        {
            EA.Diagram currentDiagram = repository.GetCurrentDiagram();

            Boolean handled = false;

            if (currentDiagram != null && currentDiagram.SelectedObjects.Count == 1)
            {
                EA.DiagramObject selectedDiagramObject = currentDiagram.SelectedObjects.GetAt(0) as EA.DiagramObject;
                SQLElement selectedElement = repository.GetElementByID(selectedDiagramObject.ElementID);

                EA.Diagram calledDiagram = null;

                Expression callExpression = null;

                if (selectedElement.Stereotype == SDMModelingMain.StatementNodeStereotype)
                {
                    StatementNode statementNode = new StatementNode(repository, selectedElement);
                    statementNode.loadTreeFromTaggedValue();
                    callExpression = statementNode.StatementExpression;                 
                }
                else if (selectedElement.Stereotype == SDMModelingMain.ObjectVariableStereotype)
                {
                    ObjectVariable ov = new ObjectVariable(selectedElement, repository);
                    ov.loadTreeFromTaggedValue();
                    callExpression = ov.BindingExpression;         
                    
                }
                else if (selectedElement.Stereotype == SDMModelingMain.StopNodeStereotype)
                {
                    StopNode stopNode = new StopNode(repository, selectedElement);
                    stopNode.loadTreeFromTaggedValue();
                    callExpression = stopNode.ReturnValue;
                    
                }

                if (callExpression is MethodCallExpression)
                {
                    MethodCallExpression mcE = callExpression as MethodCallExpression;
                    SQLMethod calledMethod = repository.GetMethodByGuid(mcE.MethodGuid);
                    if (calledMethod != null)
                    {
                        int sdmDiagramId = SDMUtil.getSDMDiagramID(repository, calledMethod);
                        if (sdmDiagramId != SDMUtil.UNKNOWN_SDM_DIAGRAM_ID)
                        {
                            calledDiagram = repository.GetOriginalRepository().GetDiagramByID(sdmDiagramId);
                        }
                    }
                }

                if (calledDiagram != null)
                {
                    Navigator.addAnchorEntry(calledDiagram.DiagramID, currentDiagram.DiagramID);                   
                    repository.OpenDiagram(calledDiagram.DiagramID);
                    handled = true;
                }
            }

            if (!handled)
            {
                if(currentDiagram != null)
                    Navigator.jumpToAnchorSuccessor(repository.GetOriginalRepository(), currentDiagram.DiagramID);        
            }
                
        }



    }
}

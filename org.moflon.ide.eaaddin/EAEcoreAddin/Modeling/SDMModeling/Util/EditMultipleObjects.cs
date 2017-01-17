using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.TGGModeling.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.Util
{
    public partial class EditMultipleObjects : Form
    {
        SQLRepository repository;
        public BackgroundWorker worker { get; set; }
        List<int> selectedObjectVariableIDs = new List<int>();
        List<EA.Element> selectedObjectVariables = new List<EA.Element>();
        List<EA.Connector> selectedLinkVariables = new List<EA.Connector>();

        EA.Diagram currentDiag;

        public EditMultipleObjects(SQLRepository repository)
        {
            InitializeComponent();

            worker = new BackgroundWorker();
            worker.WorkerReportsProgress = true;
            worker.WorkerSupportsCancellation = true;
            worker.DoWork += new DoWorkEventHandler(worker_DoWork);
            worker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(worker_RunWorkerCompleted);
            worker.ProgressChanged += new ProgressChangedEventHandler(worker_ProgressChanged);

            this.repository = repository;
            this.StartPosition = FormStartPosition.CenterScreen;
            //gather selected objectvariables
            EA.Diagram currentDiagram = repository.GetCurrentDiagram();
            this.currentDiag = currentDiagram;
            if (currentDiagram != null)
            {
                foreach (EA.DiagramObject diagramObject in currentDiagram.SelectedObjects)
                {
                    EA.Element actElement = repository.GetOriginalRepository().GetElementByID(diagramObject.ElementID);
                    if (actElement.MetaType == SDMModelingMain.ObjectVariableStereotype)
                    {
                        selectedObjectVariables.Add(actElement);
                        selectedObjectVariableIDs.Add(actElement.ElementID);
                    }
                }
            }

            //gather link variables between selected objectvariables
            foreach (EA.Element selectedObject in this.selectedObjectVariables)
            {
                foreach (EA.Connector con in selectedObject.Connectors)
                {
                    if (con.ClientID == selectedObject.ElementID && selectedObjectVariableIDs.Contains(con.SupplierID) && con.MetaType == SDMModelingMain.LinkVariableStereotype)
                    {
                        selectedLinkVariables.Add(con);
                    }
                }
            }
            if(currentDiag != null)
                ShowDialog();
        }

        private void buttonOk_Click(object sender, EventArgs e)
        {
            worker.RunWorkerAsync();
            Close();
        }

        private void worker_DoWork(object sender, DoWorkEventArgs e)
        {
            BindingSemantics bindingSemantics = BindingSemantics.MANDATORY;
            BindingOperator bindingOperator = BindingOperator.CHECK_ONLY;
            if (radioButtonMandatory.Checked)
                bindingSemantics = BindingSemantics.MANDATORY;
            else if (radioButtonNegative.Checked)
                bindingSemantics = BindingSemantics.NEGATIVE;
            if (radioButtonDestroy.Checked)
                bindingOperator = BindingOperator.DESTROY;
            else if (radioButtonCreate.Checked)
                bindingOperator = BindingOperator.CREATE;
            else if (radioButtonCheckOnly.Checked)
                bindingOperator = BindingOperator.CHECK_ONLY;


            foreach (EA.Element selectedOv in this.selectedObjectVariables)
            {
                ObjectVariable currentOV = ObjectVariable.createCorrectOvType(repository.GetElementByID(selectedOv.ElementID), repository);
                currentOV.loadTreeFromTaggedValue();

                currentOV.BindingSemantics = bindingSemantics;
                currentOV.BindingOperator = bindingOperator;

                if (bindingOperator == BindingOperator.CHECK_ONLY)
                    TGGModelingUtil.assignmentsToConstraints(currentOV, repository);
                else if (bindingOperator == BindingOperator.CREATE)
                    TGGModelingUtil.constraintsToAssignments(currentOV, repository);

                currentOV.saveTreeToEATaggedValue(true);

                

            }

            if (checkBoxLinksToo.Checked)
            {
                foreach (EA.Connector selectedLink in this.selectedLinkVariables)
                {
                    LinkVariable lv = null;
                    if (selectedLink.Stereotype == SDMModelingMain.LinkVariableStereotype)
                        lv = new LinkVariable(repository.GetConnectorByID(selectedLink.ConnectorID), repository);
                    else if (selectedLink.Stereotype == TGGModelingMain.TggLinkVariableStereotype)
                        lv = new TGGLinkVariable(repository.GetConnectorByID(selectedLink.ConnectorID), repository);

                    lv.loadTreeFromTaggedValue();
                    lv.BindingOperator = bindingOperator;
                    lv.BindingSemantics = bindingSemantics;

                    lv.saveTreeToEATaggedValue(true);
                }
            }
            repository.ReloadDiagram(repository.GetCurrentDiagram().DiagramID);
            Close();    
        }

        private void worker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {

        }

        private void worker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {

        }


        private void buttonCancel_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void radioButtonCreate_CheckedChanged(object sender, EventArgs e)
        {
            if (radioButtonCreate.Checked)
            {
                checkBoxLinksToo.Checked = true;
            }
        }
    }
}

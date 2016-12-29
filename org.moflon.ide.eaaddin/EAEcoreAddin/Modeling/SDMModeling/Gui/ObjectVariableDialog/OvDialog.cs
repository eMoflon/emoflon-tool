using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.Gui;
using EAEcoreAddin.Modeling.SDMModeling.Gui.ObjectVariableDialog;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.TGGModeling.Gui;

namespace EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog
{
    public partial class OvDialog : Form, IOVDialog
    {
        SQLRepository repository;
        public ObjectVariable objectVariable;
        IExpressionProvider ovBindingProvider;

        public static Boolean DialogClosed;

        public OvDialog(SQLRepository repository,ObjectVariable objectVariable)
        {
            InitializeComponent();

            this.objectVariable = objectVariable;
            this.repository = repository;

            DialogClosed = false;

            if (objectVariable is TGGObjectVariable || objectVariable is TGGCorrespondence)
            {
                this.ovBindingProvider = new TggOvExpressionProvider(objectVariable as TGGObjectVariable, repository);
            }
            else
            {
                this.ovBindingProvider = new OvExpressionProvider(objectVariable, repository);
            }

            

            //try to load ObjectVariable data from moca tree.
            this.objectVariable.loadTreeFromTaggedValue();

            this.ovPropertiesControl.initializeUserControl(repository, objectVariable, this);
            this.bindingControl.initializeInformation(ovBindingProvider, ovBindingProvider.getProviderExpression(), repository);

            this.StartPosition = FormStartPosition.CenterScreen;

            this.ovPropertiesControl.checkBoxBound.Checked = !this.ovPropertiesControl.checkBoxBound.Checked;
            this.ovPropertiesControl.checkBoxBound.Checked = !this.ovPropertiesControl.checkBoxBound.Checked;

        }

        private void tabIndexChanged(object sender, EventArgs e)
        {

            if (this.tabControl.SelectedIndex == 0)
            {
                this.ovPropertiesControl.comboNames.Select();
            }
            else if (this.tabControl.SelectedIndex == 1)
            {
                this.ovConstraintControl.expressionControl.cmbExpressions.Select();
            }
            else if (this.tabControl.SelectedIndex == 2)
            {
                this.bindingControl.cmbExpressions.Select();
            }

   

        }

        bool forceClose = false;

        private void btnOKClick(object sender, EventArgs e)
        {
            DialogClosed = true;
            Boolean valid = false;
            valid = ovPropertiesControl.btnOK_Click();
            valid &= ovConstraintControl.handleCloseAction();
            if (valid)
            {
                forceClose = true;
                if (objectVariable.BindingState == BindingState.BOUND)
                {
                    objectVariable.BindingExpression = ovBindingProvider.getExpression();
                }
                else
                {
                    objectVariable.BindingExpression = null;
                }
                objectVariable.saveTreeToEATaggedValue(true);
                Close();

                objectVariable.loadTreeFromTaggedValue();
                objectVariable.saveTreeToEATaggedValue(true);
            }
            
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void ObjectVariableTabsForm_Load(object sender, EventArgs e)
        {

        }

        public void showTab(int tabIndex)
        {
            if (tabControl.TabCount > tabIndex)
            {
                tabControl.SelectedIndex = tabIndex;
            }
        }

        private void OvDialog_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (!forceClose && !this.ovConstraintControl.handleCloseAction())
            {
                e.Cancel = true;
            }
        }



        public void initializeConstraintTab()
        {
            this.ovConstraintControl.initializeUserControl(ref objectVariable, repository, this.ovBindingProvider);
        }
    }
}

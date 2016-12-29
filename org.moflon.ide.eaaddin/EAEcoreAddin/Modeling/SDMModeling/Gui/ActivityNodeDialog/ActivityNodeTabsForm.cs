using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.Gui;
using EAEcoreAddin.Modeling.SDMModeling.Gui.ActivityNodeDialog;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;

namespace EAEcoreAddin.Modeling.SDMModeling.ActivityNodeDialog
{
    public partial class ActivityNodeTabsForm : Form
    {

        SQLRepository repository;
        public ActivityNode ActivityNode { get; set; }
        public IExpressionProvider provider;

        public ActivityNodeTabsForm(ActivityNode activityNode, SQLRepository repository)
        {
            InitializeComponent();
            this.repository = repository;
            this.ActivityNode = activityNode;
            this.ActivityNode.loadTreeFromTaggedValue();

            this.StartPosition = FormStartPosition.CenterScreen;
            this.activityNodePropertiesUserControl1.initializeUserControl(repository, this);
            
            this.ShowDialog();
        }

         public void initializeStatementTab()
        {
            this.provider = new StatementNodeExpressionProvider(this.ActivityNode as StatementNode, repository);
            this.statementExpressionControl.initializeInformation(provider, provider.getProviderExpression(), repository);
        }
       

        private void btnCancel_Click(object sender, EventArgs e)
        {
            Close();
        }

        public void disableStatementTab()
        {
            this.tabControl.TabPages[1].Controls[0].Enabled = false;
        }

        public void enableStatementTab()
        {
            this.tabControl.TabPages[1].Controls[0].Enabled = true;
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            this.activityNodePropertiesUserControl1.bttOK_Click(sender, e);

            if (ActivityNode is StatementNode)
            {
                Expression sExpression =provider.getExpression();
                if(sExpression == null && statementExpressionControl.getSelectedExpressionText() == "MethodCallExpression")
                    return;
                (ActivityNode as StatementNode).StatementExpression = sExpression;
            }
            Close();
            ActivityNode.saveTreeToEATaggedValue(true);
        }

        private void tabControl_TabIndexChanged(object sender, EventArgs e)
        {
            if (tabControl.SelectedIndex == 0)
            {
                activityNodePropertiesUserControl1.txtName.Select();
            }
            else if (tabControl.SelectedIndex == 1)
            {
                statementExpressionControl.cmbExpressions.Select();
            }
        }

 

    }
}

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

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog
{
    public partial class StopNodeForm : Form
    {
        StopNode stopNode;
        SQLRepository repository;

        IExpressionProvider provider;

        public StopNodeForm(StopNode stopNode, SQLRepository repository)
        {
            InitializeComponent();

            stopNode.loadTreeFromTaggedValue();

            this.provider = new StopNodeExpressionProvider(stopNode, repository);
            this.expressionControl1.initializeInformation(provider, stopNode.ReturnValue, repository);

            if (stopNode.ReturnValue == null)
            {
                expressionControl1.cmbExpressions.SelectedIndex = 0;
            }

            this.stopNode = stopNode;
            this.repository = repository;
            this.StartPosition = FormStartPosition.CenterScreen;

            ShowDialog();
        }

        private void bttOK_Click(object sender, EventArgs e)
        {
            this.stopNode.ReturnValue = provider.getExpression();
            if (stopNode.ReturnValue != null)
                stopNode.Name = stopNode.ReturnValue.ToString();
            else
                stopNode.Name = "";
            this.stopNode.saveTreeToEATaggedValue(true);

            Close();
        }

        private void bttCancel_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}

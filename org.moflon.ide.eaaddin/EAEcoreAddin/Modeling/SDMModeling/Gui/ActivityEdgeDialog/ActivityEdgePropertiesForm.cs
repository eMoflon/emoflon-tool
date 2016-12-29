using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;


namespace EAEcoreAddin.Modeling.SDMModeling.ActivityEdgeDialog
{
    public partial class ActivityEdgePropertiesForm : Form
    {
        EA.Repository repository;
        ActivityEdge activityEdge;
        /// <summary>
        /// Initialization of the Form
        /// </summary>
        /// <param name="transition"></param>
        /// <param name="Repository"></param>
        public ActivityEdgePropertiesForm(ActivityEdge activityEdge, EA.Repository Repository)
        {
            InitializeComponent();
            activityEdge.loadTreeFromTaggedValue();
            this.repository = Repository;
            this.activityEdge = activityEdge;
            
            this.StartPosition = FormStartPosition.CenterScreen;
            this.cmbGuard.comboBoxFromEdgeGuard(activityEdge.GuardType);
            this.ShowDialog();

        }
        
        

        /// <summary>
        /// executes if the user clicks the OK button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnOk_Click(object sender, EventArgs e)
        {
            this.activityEdge.GuardType = cmbGuard.edgeGuardFromComboBox();
            this.activityEdge.saveTreeToEATaggedValue(true);
            Close();
        }
        private void btnCancel_Click(object sender, EventArgs e)
        {
            Close(); 
        }
    }
}

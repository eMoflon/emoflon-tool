using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using EAEcoreAddin.Consistency.Rules;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Consistency
{
    public partial class RuleQuickfixDialogue : Form
    {
        public RuleQuickfixDialogue(RuleResult ruleResult, SQLRepository repository)
        {
            InitializeComponent();

            quickfixControl.initializeControl(ruleResult, repository);

            this.Width = quickfixControl.Width + 6;
            this.Height = quickfixControl.Height + 28;
            this.AcceptButton = quickfixControl.bttOk;
            this.CancelButton = quickfixControl.buttonCancel;
            this.StartPosition = FormStartPosition.CenterScreen;
            this.ShowDialog();
        }

        private void quickfixControl_Load(object sender, EventArgs e)
        {

        }
       
    }
}

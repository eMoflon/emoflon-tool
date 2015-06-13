using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Persistency;
using System.Threading;

namespace EAEcoreAddin.SQLWrapperClasses
{
    public partial class ExtractingDatabaseProgessBar : Form
    {
        public ExtractingDatabaseProgessBar(int max)
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;
            this.TopMost = true;
            this.computeBar(10);
        }


        private void computeBar(int maximum)
        {
            this.progressBar.Maximum = maximum;   
            this.labelCount.Text = "0 / " + maximum;
        }

        public void performStep(int newValue)
        {
            labelCount.Text = newValue + " / " + progressBar.Maximum;
            progressBar.PerformStep();
            labelOutput.Text = "extracting table: " + newValue;
        }

        protected override CreateParams CreateParams
        {
            get
            {   CreateParams p = base.CreateParams;
                p.ClassStyle = 0x200;
                return p;
            }
        }

    }
}

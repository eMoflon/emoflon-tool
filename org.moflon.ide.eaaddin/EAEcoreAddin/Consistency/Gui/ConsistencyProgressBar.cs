using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;

namespace EAEcoreAddin.Consistency
{
    public partial class ConsistencyProgressBar : Form
    {

        int i = 1;

        public Boolean Canceled { get; set; }

        public ConsistencyProgressBar()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;
            
        }

        public void prepareBar()
        {
            txtOutput.Text = "Gathering objects to perform Rulechecks";
        }

        public void invokeProgressBarAndPrepare()
        {

            if (this.InvokeRequired)
            {
                this.Invoke(new Action(() => invokeProgressBarAndPrepare()));
            }
            else
            {
                this.prepareBar();
            }
        }

        public void computeBar(int count)
        {
            i = 1;
            progressBar.Maximum = count;
            progressBar.Step = 1;
        }

        public void performNext(EA.ObjectType objectType)
        {
            txtOutput.Text = i + " / " + progressBar.Maximum;
            if (objectType == EA.ObjectType.otElement)
            {
                txtOutput.Text += " checking Rules for Elements";
            }
            if (objectType == EA.ObjectType.otConnector)
            {
                txtOutput.Text += " checking Rules for Connectors";
            }
            if (objectType == EA.ObjectType.otMethod)
            {
                txtOutput.Text += " checking Rules for Methods";
            }
            if (objectType == EA.ObjectType.otAttribute)
            {
                txtOutput.Text += " checking Rules for Attributes";
            }
            if (objectType == EA.ObjectType.otPackage)
            {
                txtOutput.Text += " checking Rules for Package";
            }

            progressBar.PerformStep();
            
            i++;
        }

        public void invokeProgressBarAndPerformNext(EA.ObjectType objectType)
        {

            if (this.InvokeRequired)
            {
                this.Invoke(new Action(() => invokeProgressBarAndPerformNext(objectType)));
            }
            else
            {
                this.performNext(objectType);
            }
        }

        public void invokeProgressBarSetBarSize(int count)
        {

            if (this.InvokeRequired)
            {
                this.Invoke(new Action(() => invokeProgressBarSetBarSize(count)));
            }
            else
            {
                this.computeBar(count);
            }
        }

        public void invokeProgressBarClose()
        {

            if (this.InvokeRequired)
            {
                this.Invoke(new Action(() => invokeProgressBarClose()));
            }
            else
            {
                try
                {
                    this.Close();
                }
                catch (InvalidOperationException)
                {
                }
            }
        }


        protected override CreateParams CreateParams
        {
            get
            {
                CreateParams p = base.CreateParams;
                p.ClassStyle = 0x200;
                return p;
            }
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            Canceled = true;
        }
        

    }
}

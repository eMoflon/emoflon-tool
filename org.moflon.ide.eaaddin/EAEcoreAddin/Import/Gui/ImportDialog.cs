using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Import.Util;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using System.Xml;
using System.IO;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using System.Timers;

namespace EAEcoreAddin.Import.Gui
{
    /*
     * GUI component for importing Ecore models to EA
     */
    public partial class ImportDialog : Form
    {
        SQLRepository repository;
        BackgroundWorker importWorker;
        List<String> checkedMetamodelsToImport;
        MocaNode mocaTree;
        int defaultHeight;
        System.Timers.Timer durationTimer;

        public ImportDialog(SQLRepository repository)
        {
            InitializeComponent();

            defaultHeight = this.Width;

            durationTimer = new System.Timers.Timer();
            durationTimer.Elapsed += new ElapsedEventHandler(OnTimedEvent);
            // Set the Interval to 5 seconds.
            durationTimer.Interval = 1000;
            durationTimer.Enabled = false ;


            StartPosition = FormStartPosition.CenterScreen;
            labelComplete.Text = "";
            labelCurrent.Text = "";
            labelTimer.Text = "";

            checkedMetamodelsToImport = new List<string>();

            buttonImport.Enabled = false;
            this.repository = repository;

            pBarCurrent.Step = 1;
            pBarComplete.Step = 1;

            importWorker = new BackgroundWorker();
            importWorker.WorkerReportsProgress = true;
            importWorker.DoWork += new DoWorkEventHandler(worker_DoWork);
            importWorker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(worker_RunWorkerCompleted);
            importWorker.ProgressChanged += new ProgressChangedEventHandler(worker_ProgressChanged);

            ShowDialog();
        }




        private void buttonSelectFile_Click_1(object sender, EventArgs e)
        {
            String chosenFileName = ImportUtil.openMocaTreeChooseDialog(repository.GetOriginalRepository());
            textBoxFile.Text = chosenFileName;
        }


        private void worker_DoWork(object sender, DoWorkEventArgs e)
        {
            BackgroundWorker worker = sender as BackgroundWorker;

            MainImport import = MainImport.getInstance(repository, worker);
            import.startImport(mocaTree, checkedMetamodelsToImport, true);
        }

        int oldPercentage = -1;

        private void worker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            ProgressObject pObject = e.UserState as ProgressObject;
            
            if (pObject.Type == ProgressBarType.Current)
            {
                if (oldPercentage != e.ProgressPercentage)
                {
                    pBarCurrent.Maximum = pObject.Maximum;
                    pBarCurrent.Value = 0;
                    oldPercentage = e.ProgressPercentage;
                }
                pBarCurrent.PerformStep();
                labelCurrent.Text = pBarCurrent.Value + "/" + pBarCurrent.Maximum + " " + pObject.Text;

            }
            else if (pObject.Type == ProgressBarType.Complete)
            {
                if (pObject.Maximum != -1)
                {
                    pBarComplete.Maximum = pObject.Maximum;
                } 
                pBarComplete.PerformStep();
                labelComplete.Text = pObject.Text;    
            
            }
            
        }

        private void worker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            buttonCancel.Enabled = true;
            buttonImport.Enabled = true;
            groupBoxCheckboxes.Enabled = true;
            durationTimer.Enabled = false;
            groupBoxChooseFile.Enabled = true;

            labelComplete.Text = "Import Complete!";
            labelCurrent.Text = "Import Complete!";
        }

        private void addCheckbox(MocaNode node)
        {
            int count = groupBoxCheckboxes.Controls.Count;

            CheckBox newBox = new CheckBox();
            newBox.Checked = true;
            newBox.Location = new Point(9, 19 + count * 23);
            newBox.Width = groupBoxCheckboxes.Width;

            String name = node.getAttributeOrCreate("name").Value;
            if (name == "")
            {
                name = node.getAttributeOrCreate("Moflon::Name").Value;
            }

            newBox.Text = node.Name + ": " + name;
            newBox.Enabled = false;
            groupBoxCheckboxes.Controls.Add(newBox);
             
        }

        private void textBoxFile_TextChanged(object sender, EventArgs e)
        {
            groupBoxCheckboxes.Controls.Clear();
            checkedMetamodelsToImport.Clear();

            String fileName = textBoxFile.Text;
            if (File.Exists(fileName))
            {
                buttonImport.Enabled = true;
                MocaNode mocaTree = new MocaNode();
                String readText = File.ReadAllText(fileName);
                XmlDocument mocaXmlDocument = XmlUtil.stringToXmlDocument(readText);
                mocaTree.deserializeFromXmlTree(mocaXmlDocument.DocumentElement.FirstChild as XmlElement);

                this.mocaTree = mocaTree;

                foreach (MocaNode modelPackageNode in mocaTree.Children)
                {
                    if (modelPackageNode.Name == ECOREModelingMain.EPackageStereotype)
                    {
                        addCheckbox(modelPackageNode);
                    }
                    else if (modelPackageNode.Name == "TGG")
                    {
                        addCheckbox(modelPackageNode);
                    }
                }
            }
            else
            {
                buttonImport.Enabled = false;
            }
            int newHeight = 310 + groupBoxCheckboxes.Controls.Count * 23;
            if (newHeight < 352)
                Height = 352;
            else
                Height = newHeight;

            MinimumSize = new Size(492, Height);

        }

        private void buttonImport_Click(object sender, EventArgs e)
        {
            durationTimer.Enabled = true;
            buttonImport.Enabled = false;
            buttonCancel.Enabled = false;
            pBarComplete.Value = 0;
            pBarCurrent.Value = 0;
            groupBoxCheckboxes.Enabled = false;
            groupBoxChooseFile.Enabled = false;

            foreach (CheckBox cb in groupBoxCheckboxes.Controls)
            {
                String name = cb.Text.Split(':')[1].Replace(" ","");
                if (cb.Checked)
                {
                    checkedMetamodelsToImport.Add(name);
                }
            }

            importWorker.RunWorkerAsync();    
        }

        int seconds = 0;
        int minutes = 0;
        private void OnTimedEvent(object source, ElapsedEventArgs e)
        {
            seconds++;
            if (seconds == 60)
            {
                minutes++;
                seconds = 0;
            }
            
            String minutesString = "" + minutes;
            if(minutes < 10)
                minutesString = "0" + minutesString;

            String secondsString = "" + seconds;
            if(seconds < 10)
                secondsString = "0" + secondsString;

            updateLabelTimer(minutesString + ":" + secondsString);          
        }

        public void updateLabelTimer(String text)
        {
            if (labelTimer.InvokeRequired)
            {
                labelTimer.Invoke(new MethodInvoker(() => { updateLabelTimer(text); }));
            }
            else
            {
                labelTimer.Text = text;
            }
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            Close();
        }

       
    }
}

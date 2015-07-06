using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Serialization.MocaTree;
using System.Threading;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;

namespace EAEcoreAddin.Persistency
{
    public partial class ExportProgressBar : Form
    {
        BackgroundWorker worker;
        List<SQLPackage> packagesToExport;
        int progress = 1;

        private AutoResetEvent resetEvent = new AutoResetEvent(false);

        public ExportProgressBar()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;

            worker = new BackgroundWorker();
            worker.WorkerReportsProgress = true;

            worker.DoWork += new DoWorkEventHandler(worker_DoWork);
            worker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(worker_RunWorkerCompleted);
            worker.ProgressChanged += new ProgressChangedEventHandler(worker_ProgressChanged);
        }

        public void computeSize()
        {
            int count = packagesToExport.Count;

            List<SQLPackage> listOfPackages = new List<SQLPackage>();
            listOfPackages.AddRange(packagesToExport);
            for (int i = 0; i < listOfPackages.Count; i++)
            {
                SQLPackage pkg = listOfPackages[i];
                
                foreach(SQLPackage actPkg in pkg.Packages)
                {
                    listOfPackages.Add(actPkg);
                    count++;
                }
            }

            progressBar.Maximum = count;
        }

        private void ExportProgressBar_Load(object sender, EventArgs e)
        {

        }

        internal void startExport(Export exporter)
        {
            CreateControl();

            exporter.setWorker(worker);

            packagesToExport = new List<SQLPackage>();
            foreach (SQLPackage model in exporter.Repository.Models)
            {
                foreach (SQLPackage projectPackage in model.Packages)
                {
                    packagesToExport.Add(projectPackage);
                }
            }

            computeSize();

            if (exporter.showStatusBar)
            {
                worker.RunWorkerAsync(exporter);
                ShowDialog();
            }
            else
            {
                runExport(exporter);
            }
        }

        private void worker_DoWork(object sender, DoWorkEventArgs e)
        {
            BackgroundWorker worker = sender as BackgroundWorker;
            Export exporter = e.Argument as Export;

            runExport(exporter);

            

            
        }

        private void runExport(Export exporter)
        {
            try
            {
                foreach (SQLPackage projectPackage in packagesToExport)
                {
                    exporter.CurrentMetamodelName = projectPackage.Name;
                    exporter.exportPackage(projectPackage);
                }

                exporter.createDependencyTree();

                //write property file
                foreach (SQLPackage projectPackage in packagesToExport)
                {
                    MocaNode metamodelNode = null;
                    foreach (MocaNode node in exporter.MocaTree.Children)
                    {
                        if (node.getAttributeOrCreate(EPackageHelper.MoflonCustomNameTaggedValueName).Value == projectPackage.Name)
                        {
                            metamodelNode = node;
                            break;
                        }
                    }
                    if (metamodelNode != null)
                    {
                        exporter.enhanceMetamodelNodeWithDependencies(projectPackage, metamodelNode);
                    }

                    // refactor
                    /*MocaNode refactorNode = null;
                    foreach (MocaNode node in exporter.RefactorTree.Children)
                    {
                        if (node.getAttributeOrCreate(EPackageHelper.MoflonCustomNameTaggedValueName).Value == projectPackage.Name)
                        {
                            refactorNode = node;
                            break;
                        }
                    }
                    if (refactorNode != null)
                    {
                        exporter.enhanceMetamodelNodeWithDependencies(projectPackage, refactorNode);
                    }*/
                }

                exporter.finalize();
            }
            catch
            {
                worker.ReportProgress(0, "Error");
                MessageBox.Show("Something has gone wrong. Try the validation tool and contact the eMoflon team if necessary");
            }
        }

        private void worker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            if (e.UserState is String && (e.UserState as String) == "Error")
            {
                this.Close();
            }
            else
            {
                this.progressBar.PerformStep();
                this.labelCount.Text = progress + "/" + this.progressBar.Maximum;
                this.labelOutputText.Text = "Package: " + e.UserState as string;
                progress++;
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

        private void worker_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            Close();
            resetEvent.Set();
        }
    }
}



            
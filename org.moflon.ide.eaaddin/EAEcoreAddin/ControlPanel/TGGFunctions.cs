using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.TGGModeling;
using System.Threading;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Persistency;
using EAEcoreAddin.Modeling.SDMModeling.Util;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.Consistency.PropertyFile;
using EAEcoreAddin.Modeling.TGGModeling.Gui;
using System.IO;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Modeling.CSP.ExportWrapper;

namespace EAEcoreAddin.ControlPanel
{
    public partial class TGGFunctions : UserControl
    {
        SQLRepository repository;

        Boolean activateTGGDeriveButton = false;
        Boolean activateTGGVerboseButton = false;
        Boolean activateMultipleButton = false;
        Boolean activatePostProcessButton = false;
        private bool activateExportImportCSPButton;
        

        public TGGFunctions()
        {
            InitializeComponent();

        }

        public void resetFunctions(EA.Repository repository)
        {
            initializeFunctions(repository);
        }

        private void initializeFunctions(EA.Repository repository)
        {           
            this.repository = new SQLRepository(repository, false);
        }

        private void checkForFunctions()
        {
            EA.Diagram currentDiag = repository.GetCurrentDiagram();
            activateTGGDeriveButton = false;
            activateMultipleButton = false;
            activateTGGVerboseButton = false;
            activateExportImportCSPButton = false;
            if (currentDiag != null && TGGModelingMain.TggSchemaDiagramMetatype.Contains(currentDiag.MetaType))
            {
                if (currentDiag.SelectedObjects.Count > 0)
                    activateTGGDeriveButton = true;
            }                   
            if (currentDiag != null && TGGModelingMain.TggRuleDiagramMetatype.Contains(currentDiag.MetaType))
            {
                if (currentDiag.SelectedObjects.Count > 0)
                {
                    activateTGGDeriveButton = true;
                    activateMultipleButton = true;
                    activatePostProcessButton = true;
                }
                activateTGGVerboseButton = true;
            }
            EA.Package selectedPackage = repository.GetTreeSelectedPackage() as EA.Package;
            if (selectedPackage != null && selectedPackage.Element != null && selectedPackage.Element.Stereotype == TGGModelingMain.TggSchemaPackageStereotype)
            {
                activateExportImportCSPButton = true;
            }
        }

        private void buttonDeriveNewTGGRule_Click(object sender, EventArgs e)
        {
            checkForFunctions();
            if (activateTGGDeriveButton)
            {
                DeriveNewTGGRule deriveDialog = new DeriveNewTGGRule(repository);
            }
            else
                MessageBox.Show("To use this function select TGG ObjectVariables or TGG Schemas");
        }

        private void buttonSDMVerbose_Click(object sender, EventArgs e)
        {
            
            EA.Diagram currentDiagram = repository.GetCurrentDiagram();
            if(currentDiagram.Notes == "Moflon::Verbose=true")
                SDMUtil.setAllVerboseTags(false, repository, currentDiagram);
            else if(currentDiagram.Notes == "Moflon::Verbose=false")
                SDMUtil.setAllVerboseTags(true, repository, currentDiagram);
            else
                SDMUtil.setAllVerboseTags(true, repository, currentDiagram);
        }

        private void buttonTGGVerbose_Click(object sender, EventArgs e)
        {
            checkForFunctions();
            if(activateTGGVerboseButton)
                buttonSDMVerbose_Click(sender, e);
            else
                MessageBox.Show("This function can only be used on TGG rule diagrams");
        }


        private void buttonEditMultiple_Click(object sender, EventArgs e)
        {
            checkForFunctions();
            if (activateMultipleButton)
            {
                EditMultipleObjects edit = new EditMultipleObjects(repository);
            }
            else
                MessageBox.Show("This function can only be used on selected TGG ObjectVariables and Correspondence Objects");
        }

        private void buttonPostProcess_Click(object sender, EventArgs e)
        {
            checkForFunctions();
            if (activatePostProcessButton)
            {
                AddPostProcessForm pProcess = new AddPostProcessForm(this.repository);
            }
        }

        private void buttonExportCsp_Click(object sender, EventArgs e)
        {
            checkForFunctions();
            if (activateExportImportCSPButton)
            {

                String cspXmlString = "";

                String pathToWrite = Path.GetDirectoryName(repository.ConnectionString);

                SaveFileDialog dialog = new SaveFileDialog();
                dialog.AddExtension = true;
                dialog.DefaultExt = "txt";
                dialog.InitialDirectory = pathToWrite;
                dialog.Filter = "Text files (*.txt)|*.txt|All files (*.*)|*.*";
                dialog.FileName = "csptemp.txt";



                if (dialog.ShowDialog() == DialogResult.OK)
                {
                    TGG tggPackage = new TGG(repository, repository.GetTreeSelectedPackage());
                    tggPackage.loadTreeFromTaggedValue();
                    MocaNode dummyNode = new MocaNode();

                    foreach (CSPConstraint constraint in tggPackage.Constraints)
                    {
                        if (constraint.UserDefined)
                        {
                            dummyNode.Children.Add(constraint.serializeToMocaTree(new MocaNode("CSPConstraint")));
                        }
                    }
                    cspXmlString = MocaTreeUtil.mocaNodeToString(dummyNode);

                    pathToWrite = dialog.FileName;
                    File.WriteAllText(pathToWrite, cspXmlString);
                    MessageBox.Show("User defined CSPs are exported to path: " + pathToWrite);
                }
                
            }
        }

        private void buttonImportCSP_Click(object sender, EventArgs e)
        {
            checkForFunctions();
            if (activateExportImportCSPButton)
            {
                String pathToWrite = Path.GetDirectoryName(repository.ConnectionString);

                OpenFileDialog dialog = new OpenFileDialog();
                dialog.AddExtension = true;
                dialog.DefaultExt = "txt";
                dialog.InitialDirectory = pathToWrite;
                dialog.Filter = "Text files (*.txt)|*.txt|All files (*.*)|*.*";
                dialog.FileName = "csptemp.txt";

                if (dialog.ShowDialog() == DialogResult.OK)
                {
                    TGG tggPackage = new TGG(repository, repository.GetTreeSelectedPackage());
                    tggPackage.loadTreeFromTaggedValue();

                    String fileContent = File.ReadAllText(dialog.FileName);
                    MocaNode dummyParent = MocaTreeUtil.mocaNodeFromXmlString(fileContent);
                    foreach (MocaNode cspNode in dummyParent.Children)
                    {
                        CSPConstraint constraint = new CSPConstraint();
                        constraint.deserializeFromMocaTree(cspNode);
                        Boolean alreadyIn = false;
                        foreach (CSPConstraint existingCSP in tggPackage.Constraints)
                        {
                            if (existingCSP.Name == constraint.Name)
                            {
                                alreadyIn = true;
                                break;
                            }
                        }

                        if (!alreadyIn)
                        {
                            tggPackage.Constraints.Add(constraint);
                        }
                    }

                    tggPackage.saveTreeToEATaggedValue(false);
                }
            }
        }
    }
}

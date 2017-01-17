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
using EAEcoreAddin.Util;
using EAEcoreAddin.ControlPanel.SDM;
using System.Text.RegularExpressions;

namespace EAEcoreAddin.ControlPanel
{
    public partial class SDMFunctions : UserControl
    {

        SQLRepository repository;

        public SDMFunctions()
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

        Boolean activateSDMVerboseButton = false;
        Boolean activateExtractSDMButton = false;
        Boolean activateEditMultiple = false;

        private void checkFunctions()
        {
            EA.Diagram currentDiag = repository.GetCurrentDiagram();

            activateSDMVerboseButton = false;
            activateExtractSDMButton = false;
            activateEditMultiple = false;

            if (currentDiag != null && SDMModelingMain.SdmDiagramMetatype.Contains(currentDiag.MetaType))
            {
                if (currentDiag.SelectedObjects.Count > 0)
                {
                    activateExtractSDMButton = true;
                    activateEditMultiple = true;
                }
                activateSDMVerboseButton = true;
            }

        }


        private void buttonExtractSDM_Click(object sender, EventArgs e)
        {
            checkFunctions();
            if (activateExtractSDMButton)
            {
                ExtractSDM exctractSDMForm = new ExtractSDM(repository);
            }
            else
                MessageBox.Show("To use this function select some ActivityNodes");
        }


        private void buttonSDMVerbose_Click(object sender, EventArgs e)
        {
            checkFunctions();
            if (activateSDMVerboseButton)
            {
                EA.Diagram currentDiagram = repository.GetCurrentDiagram();
                if (currentDiagram.Notes.Contains("Moflon::Verbose=true"))
                    SDMUtil.setAllVerboseTags(false, repository, currentDiagram);
                else if (currentDiagram.Notes.Contains("Moflon::Verbose=false"))
                    SDMUtil.setAllVerboseTags(true, repository, currentDiagram);
                else
                    SDMUtil.setAllVerboseTags(true, repository, currentDiagram);
            }
            else
                MessageBox.Show("To use this function open a SDM Diagram");
        }

        private void buttonVerboseGlobal_Click(object sender, EventArgs e)
        {
            String sqlQuery = "select * from t_diagram";
            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(repository.SQLQuery(sqlQuery), "Row"))
            {
                if (row != "")
                {
                    SQLDiagram diagram = new SQLDiagram(repository, row);
                    if (SDMModelingMain.SdmDiagramMetatype.Contains(diagram.MetaType))
                    {
                        EA.Diagram rdiagram = repository.GetOriginalRepository().GetDiagramByID(diagram.DiagramID);
                        toggleDiagramVerboseStatus(rdiagram);
                    }
                }
            }
        }

        private void toggleDiagramVerboseStatus(EA.Diagram currentDiagram)
        {
            if (currentDiagram != null)
            {
                if (currentDiagram.Notes.Contains("Moflon::Verbose=true"))
                    SDMUtil.setAllVerboseTags(false, repository, currentDiagram);
                else if (currentDiagram.Notes.Contains("Moflon::Verbose=false"))
                    SDMUtil.setAllVerboseTags(true, repository, currentDiagram);
                else
                    SDMUtil.setAllVerboseTags(true, repository, currentDiagram);
            }
        }


        private void buttonEditMultiple_Click(object sender, EventArgs e)
        {
            checkFunctions();
            if (activateEditMultiple)
            {
                EditMultipleObjects edit = new EditMultipleObjects(repository);
            }
            else
                MessageBox.Show("To use this function select some ObjectVariables");
        }

        private void buttonDeleteSdms_Click(object sender, EventArgs e)
        {
            ConfirmDeleteSDMs confirmDialog = new ConfirmDeleteSDMs();
            if (confirmDialog.ShowDialog() == DialogResult.OK)
            {

                SQLPackage selectedPackage = repository.GetTreeSelectedPackage();
                SQLPackage outermostPackage = EAUtil.getOutermostPackage(selectedPackage, repository);

                String sqlResult = repository.SQLQuery("SELECT * FROM t_object WHERE Stereotype = '" + SDMModelingMain.SdmContainerStereotype + "'");
                foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(sqlResult ,"Row"))
                {
                    if (row != "")
                    {
                        SQLElement element = new SQLElement(repository, row);
                        if (EAUtil.getOutermostPackage(element, repository).PackageGUID == outermostPackage.PackageGUID)
                        {
                            EAUtil.deleteElement(element.getRealElement(), repository);
                        }
                    }
                }


            }
        }

        private void buttonToggleMceVis_Click(object sender, EventArgs e)
        {
            checkFunctions();
            if (activateSDMVerboseButton)
            {
                EA.Diagram currentDiagram = repository.GetCurrentDiagram();
                if (currentDiagram.Notes.Contains("Moflon::MceVisualization=true"))
                {
                    SDMUtil.setAllMceVisTags(false, repository, currentDiagram);
                    currentDiagram.Notes = currentDiagram.Notes.Replace("Moflon::MceVisualization=true", "Moflon::MceVisualization=false");
                }
                else if (currentDiagram.Notes.Contains("Moflon::MceVisualization=false"))
                {
                    SDMUtil.setAllMceVisTags(true, repository, currentDiagram);
                    currentDiagram.Notes = currentDiagram.Notes.Replace("Moflon::MceVisualization=false", "Moflon::MceVisualization=true");
                }
                else
                {
                    SDMUtil.setAllMceVisTags(false, repository, currentDiagram);
                    currentDiagram.Notes = currentDiagram.Notes += "Moflon::MceVisualization=false";
                }
                currentDiagram.Update();
                repository.ReloadDiagram(currentDiagram.DiagramID);
            }
            else
                MessageBox.Show("To use this function open a SDM Diagram");
        }

        

    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.SQLWrapperClasses;
using System.Text.RegularExpressions;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Util;
using EAEcoreAddin.Serialization;
using System.Diagnostics;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.Modeling.CSP.ExportWrapper;

namespace EAEcoreAddin.Modeling.CSP.Gui
{
    public partial class CSPInstanceDialog : Form
    {


        SQLElement constraintElement;
        SQLRepository repository;

        CSPInstance constraintInstance;

        private CSPController controller;

        public CSPInstanceDialog(SQLRepository repository, SQLElement constraintElement, CSPController controller)
        {
            InitializeComponent();
            this.controller = controller;
            this.labelInformation.Text = "";
            this.CSPConstraintDataGridView1.initializeComponent(repository, controller);
            this.StartPosition = FormStartPosition.CenterScreen;
            this.constraintElement = constraintElement;
            this.repository = repository;

            this.constraintInstance = new CSPInstance(repository, constraintElement);
            this.constraintInstance.loadTreeFromTaggedValue();

            this.setAppearance();
            if(comboBoxConstraints.SelectedIndex == -1)
                this.comboBoxConstraints.SelectedIndex = 0;

            if (listBoxConstraints.Items.Count > 0)
            {
                listBoxConstraints.SelectedIndex = 0;

            }  

        }


        private void setAppearance()
        {
           
            this.comboBoxConstraints.Items.Clear();
            this.listBoxConstraints.Items.Clear();

            controller.getConstraints().Sort(new CSPConstraintComparer());
           

            foreach (CSPConstraint constr in controller.getConstraints())
            {
                this.comboBoxConstraints.Items.Add(comboBoxConstraints.Items.Count + ". " + constr.Name);
            }

            //get raw data from notes field of ea element

            foreach (CSPInstanceEntry entry in constraintInstance.createdEntries)
            {
                listBoxConstraints.Items.Add(entry.ToString());

            }

                     
        }

        private void comboBoxConstraints_SelectedIndexChanged(object sender, EventArgs e)
        {
            labelInformation.Text = "";
            String selectedText = comboBoxConstraints.Text;
            this.CSPConstraintDataGridView1.clearRows();
            if (comboBoxConstraints.SelectedIndex != -1)
            {
                CSPConstraint selectedConstraint = controller.getConstraints()[comboBoxConstraints.SelectedIndex];
                labelInformation.Text = selectedConstraint.getConstraintInformationSummary();

                CSPConstraintDataGridView1.resetTypedExpressions();

                for (int i = 0; i < selectedConstraint.Signature.Count; i++)
                {
                    this.CSPConstraintDataGridView1.addRow("", selectedConstraint.Signature[i] + " // " + selectedConstraint.SignatureInformation[i]);
                }


                if (selectedConstraint.UserDefined)
                {
                    this.buttonDeleteConstraint.Enabled = true;
                    this.buttonEditUserDefinedConstraint.Enabled = true;
                }
                else
                {
                    this.buttonDeleteConstraint.Enabled = false;
                    this.buttonEditUserDefinedConstraint.Enabled = false;
                }
            }



            

        }

        private String computeConstraintString(CSPInstanceEntry currentEntry)
        {
            if (currentEntry != null)
                return currentEntry.ToString();


            String constraintString = comboBoxConstraints.Text + "(";
            foreach (String typedInValue in this.CSPConstraintDataGridView1.getTypedInValues())
            {
                constraintString += typedInValue + ",";
            }
            constraintString = constraintString.Remove(constraintString.Length - 1, 1);
            constraintString = constraintString + ")";
            return constraintString;
        }

        private void buttonAdd_Click(object sender, EventArgs e)
        {
            if (!this.CSPConstraintDataGridView1.getTypedInValues().Contains(""))
            {

                CSPInstanceEntry currentEntry = new CSPInstanceEntry(repository);
                foreach (Expression expr in CSPConstraintDataGridView1.getTypedExpressions())
                {
                    currentEntry.typedInExpressions.Add(expr);
                }

                currentEntry.constraintName = controller.getConstraints()[comboBoxConstraints.SelectedIndex].Name;
                constraintInstance.createdEntries.Add(currentEntry);

                this.listBoxConstraints.Items.Add(computeConstraintString(currentEntry));
                listBoxConstraints.SelectedIndex = listBoxConstraints.Items.Count - 1;
                    
            }
        }

        private void listBoxConstraints_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listBoxConstraints.SelectedIndex != -1)
            {
                if (constraintInstance.createdEntries.Count == listBoxConstraints.Items.Count)
                {
                    CSPInstanceEntry currentEntry = constraintInstance.createdEntries[listBoxConstraints.SelectedIndex];
                    int constraintIndex = 0;
                    foreach (CSPConstraint constraint in controller.getConstraints())
                    {
                        if (constraint.Name == currentEntry.constraintName)
                        {
                            break;
                        }
                        constraintIndex++;
                    }

                    if (comboBoxConstraints.Items.Count > constraintIndex)
                    {
                        this.comboBoxConstraints.SelectedIndex = constraintIndex;
                        int i = 0;
                        CSPConstraintDataGridView1.resetTypedExpressions();
                        foreach (Expression currentExpression in currentEntry.typedInExpressions)
                        {
                            CSPConstraintDataGridView1.addTypedExpression(currentExpression);
                            CSPConstraintDataGridView1.setCellValue(currentExpression.ToString(), i);
                            i++;
                        }
                    }
                }
            }
        }

        private void buttonOk_Click(object sender, EventArgs e)
        {   

            List<String> constraintEntries = new List<string>();
            foreach (CSPInstanceEntry entry in constraintInstance.createdEntries)
            {
                constraintEntries.Add(entry.ToString());
            }

            //save existing connectors
            List<SQLConnector> existingConnectors = new List<SQLConnector>();
            foreach(SQLConnector actCon in constraintElement.Connectors)
            {
                existingConnectors.Add(actCon);
            }

            Dictionary<SQLElement, EA.DiagramObject> elementsToDiagramObjects = new Dictionary<SQLElement, EA.DiagramObject>();

            List<SQLConnector> validConnectors = new List<SQLConnector>();

            EA.Diagram currentDiagram = repository.GetCurrentDiagram();
            if (currentDiagram != null)
            {
                repository.SaveDiagram(currentDiagram.DiagramID);
                foreach (EA.DiagramObject diagObject in currentDiagram.DiagramObjects)
                {
                    SQLElement currentElement = repository.GetElementByID(diagObject.ElementID);
                    elementsToDiagramObjects.Add(currentElement, diagObject);
                }
                //check for all constrant entries
                foreach (String consString in constraintEntries)
                {
                    MatchCollection matches = Regex.Matches(consString, @"[a-z0-9_]+\.[a-z0-9_]+", RegexOptions.IgnoreCase);
                    foreach (Match match in matches)
                    {
                        Boolean connectionAlreadyExisting = false;

                        String[] values = match.Value.Split('.');

                        String objectName = values[0];
                        String attributeName = values[1];

                        //if connection already existing
                        connectionAlreadyExisting = checkForConnectionExistence(existingConnectors,validConnectors, objectName, attributeName);
                        //otherwise create new connection to object for current constraint entry
                        if (!connectionAlreadyExisting)
                        {
                            EA.Connector newConnection = createNewConnection(elementsToDiagramObjects, currentDiagram, objectName, attributeName);
                            if (newConnection != null)
                                validConnectors.Add(repository.GetConnectorByID(newConnection.ConnectorID));
                        }
                    }
                }
                //find all invalid connectors
                List<SQLConnector> consToDelete = new List<SQLConnector>();

                constraintElement.Connectors.Refresh();
                foreach (SQLConnector con in constraintElement.Connectors)
                {
                    Boolean conValid = false;
                    foreach (SQLConnector validCon in validConnectors)
                    {
                        if (con.ConnectorID == validCon.ConnectorID)
                            conValid = true;
                    }
                    if (!conValid)
                        consToDelete.Add(con);
                }
                //delete all invalid connectors
                foreach (SQLConnector toDelete in consToDelete)
                {
                    EAUtil.deleteConnector(toDelete.getRealConnector(), repository.GetOriginalRepository());
                }


                repository.ReloadDiagram(currentDiagram.DiagramID);
            }

            constraintInstance.saveTreeToEATaggedValue(true);

            Close();
        }

        private EA.Connector createNewConnection(Dictionary<SQLElement, EA.DiagramObject> elementsToDiagramObjects, EA.Diagram currentDiagram, String objectName, String attributeName)
        {
            foreach (SQLElement diagramElement in elementsToDiagramObjects.Keys)
            {
                if (diagramElement.Name == objectName)
                {
                    EA.Connector connector = constraintElement.getRealElement().Connectors.AddNew(attributeName, Main.EADependencyType) as EA.Connector;
                    connector.SupplierID = diagramElement.ElementID;
                    connector.StereotypeEx = "ConstraintLink";
                    connector.Update();

                    EA.DiagramLink diagramLink = currentDiagram.DiagramLinks.AddNew("", Main.EADependencyType) as EA.DiagramLink;
                    diagramLink.ConnectorID = connector.ConnectorID;
                    diagramLink.Update();
                    return connector;
                }
            }
            return null;
        }

        private Boolean checkForConnectionExistence(List<SQLConnector> existingConnectors,List<SQLConnector> validConnectors, String objectName, String attributeName)
        {
            foreach (SQLConnector actCon in existingConnectors)
            {
                if (actCon.Name == attributeName)
                {
                    SQLElement supplierElement = repository.GetElementByID(actCon.SupplierID);
                    if (supplierElement.Name == objectName)
                    {
                        validConnectors.Add(actCon);
                        return true;
                    }
                }
            }
            return false;
        }

        private void deleteAllConnectors()
        {
            
            for (short i = 0; i < constraintElement.getRealElement().Connectors.Count; i++)
            {
                constraintElement.getRealElement().Connectors.Delete(i);
            }
            constraintElement.getRealElement().Update();
        }

        private List<String> updateConstraintElementNotes()
        {
            List<String> constraintEntries = new List<string>();

            foreach (String constraintString in this.listBoxConstraints.Items)
            {
                this.constraintElement.getRealElement().Notes += constraintString + "\r\n";
                constraintEntries.Add(constraintString);
            }
            this.constraintElement.getRealElement().Update();
            return constraintEntries;
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void buttonModify_Click(object sender, EventArgs e)
        {
            if (listBoxConstraints.SelectedIndex != -1)
            {
                int selectedIndex = listBoxConstraints.SelectedIndex;


                CSPInstanceEntry currentEntry = constraintInstance.createdEntries[selectedIndex];

                currentEntry.constraintName = controller.getConstraints()[comboBoxConstraints.SelectedIndex].Name;
                currentEntry.typedInExpressions.Clear();
                foreach (Expression typedExpression in CSPConstraintDataGridView1.getTypedExpressions())
                {
                    currentEntry.typedInExpressions.Add(typedExpression);
                }

                if (constraintInstance.createdEntries.Count <= selectedIndex)
                    constraintInstance.createdEntries.Insert(selectedIndex, currentEntry);

                constraintInstance.createdEntries[selectedIndex] = currentEntry;
                
                listBoxConstraints.Items.RemoveAt(selectedIndex);
                listBoxConstraints.Items.Insert(selectedIndex, computeConstraintString(currentEntry));
                listBoxConstraints.SelectedIndex = selectedIndex;
            }
        }

        private void buttonRemove_Click(object sender, EventArgs e)
        {
            if (listBoxConstraints.SelectedIndex != -1)
            {
                int selectedIndex = listBoxConstraints.SelectedIndex;
                listBoxConstraints.Items.RemoveAt(selectedIndex);

                constraintInstance.createdEntries.RemoveAt(selectedIndex);

                if (listBoxConstraints.Items.Count > selectedIndex)
                    listBoxConstraints.SelectedIndex = selectedIndex;
                else if (listBoxConstraints.Items.Count > 0)
                    listBoxConstraints.SelectedIndex = 0;
            }
        }

        private void buttonAddConstraint_Click(object sender, EventArgs e)
        {
            NewCSPDefinitionDialog newConstraintDialog = new NewCSPDefinitionDialog();
            if (newConstraintDialog.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                newConstraintDialog.computeNewCSPConstraint();
                controller.getConstraints().Insert(0,newConstraintDialog.CreatedCSPConstraint);
                controller.recomputeConstraintIndizes();
                controller.saveConstraintContainer();
            }
            setAppearance();
            this.comboBoxConstraints.SelectedIndex = 0;
           
        }

        private void buttonDeleteConstraint_Click(object sender, EventArgs e)
        {
            controller.getConstraints().RemoveAt(comboBoxConstraints.SelectedIndex);
            this.comboBoxConstraints.Items.RemoveAt(comboBoxConstraints.SelectedIndex);
            comboBoxConstraints.SelectedIndex = 0;
            controller.saveConstraintContainer();
        }

        private void buttonEditUserDefinedConstraint_Click(object sender, EventArgs e)
        {
            int selectedIndex = comboBoxConstraints.SelectedIndex;

            CSPConstraint selectedConstraint = controller.getConstraints()[selectedIndex];
          
            NewCSPDefinitionDialog newConstraintDialog = new NewCSPDefinitionDialog(selectedConstraint);
            if (newConstraintDialog.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                newConstraintDialog.computeNewCSPConstraint();
                controller.saveConstraintContainer();
            }
            setAppearance();
            comboBoxConstraints.SelectedIndex = selectedIndex;
        }

        private void buttonMoveConstraintUp_Click(object sender, EventArgs e)
        {
            moveConstraintPosition(true);
        }

        private void buttonMoveConstraintDown_Click(object sender, EventArgs e)
        {
            moveConstraintPosition(false);
        }


        private void moveConstraintPosition(Boolean up)
        {
            int constraintIndex = comboBoxConstraints.SelectedIndex;
            int constraintIndexNew = -1;
            CSPConstraint constraintToMove = controller.getConstraints()[constraintIndex];

                
                if (!up)
                {
                    if (constraintIndex < controller.getConstraints().Count)
                    {
                        constraintIndexNew = constraintIndex + 1;
                    }
                }
                else
                {
                    if (constraintIndex > 0)
                    {
                        constraintIndexNew = constraintIndex - 1;
                    }
                }
                if (constraintIndexNew > -1 && constraintIndexNew < controller.getConstraints().Count)
                {
                    controller.getConstraints().RemoveAt(constraintIndex);
                    controller.getConstraints().Insert(constraintIndexNew, constraintToMove);
                    controller.recomputeConstraintIndizes();
                    setAppearance();
                    comboBoxConstraints.SelectedIndex = constraintIndexNew;
                }
            
        }


    }
}

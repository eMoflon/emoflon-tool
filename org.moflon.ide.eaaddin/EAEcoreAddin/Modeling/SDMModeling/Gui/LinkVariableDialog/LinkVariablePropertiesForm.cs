using System;
using System.Collections.Generic;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Util;


namespace EAEcoreAddin.Modeling.SDMModeling.LinkVariableDialog
{
    public partial class LinkVariablePropertiesForm : Form
    {
        private bool edit;
        private LinkVariable linkVariable;
        private List<LinkDialogueEntry> linkDialogueEntries;
        private SQLRepository repository;
        private SQLElement lvClient;
        private SQLElement lvSupplier;

        /// <summary>
        /// Initialization of the form, searching for the connected associations
        /// </summary>
        /// <param name="link"></param>
        /// <param name="repository"></param>
        /// <param name="editExistingLink"></param>
        public LinkVariablePropertiesForm(LinkVariable linkVariable, SQLRepository repository, bool editExistingLink)
        {
            InitializeComponent();

            linkVariable.loadTreeFromTaggedValue();

            this.linkDialogueEntries = new List<LinkDialogueEntry>();
            this.linkVariable = linkVariable;
            this.edit = editExistingLink;
            this.repository = repository;
            this.lvClient = repository.GetElementByID(linkVariable.LinkVariableEA.ClientID);
            this.lvSupplier = repository.GetElementByID(linkVariable.LinkVariableEA.SupplierID);
            
            

            addPossibleLinks();
            
            initializeDialog();

            this.StartPosition = FormStartPosition.CenterScreen;
            this.ShowDialog();
        }

        private void initializeDialog()
        {
            if (listboxLinks.Items.Count != 0 ) listboxLinks.SelectedIndex = 0;

            String guidOfRef = "";
            String linkName = "";
            String linkNameOpposite = "";

            if (this.linkVariable.linkDialogueEntry != null)
            {
                guidOfRef = this.linkVariable.linkDialogueEntry.CorrespondingConnectorGuid;
                linkName = this.linkVariable.linkDialogueEntry.output;
                linkNameOpposite = this.linkVariable.linkDialogueEntry.OutputOpposite;
            }

            linkName = Regex.Replace(linkName,"&gt;", ">");
            linkName = Regex.Replace(linkName, "&lt;", "<");

            linkNameOpposite = Regex.Replace(linkNameOpposite, "&gt;", ">");
            linkNameOpposite = Regex.Replace(linkNameOpposite, "&lt;", "<");
            
            int i = 0;
            foreach (String entry in listboxLinks.Items)
            {
                if ((entry == linkName || entry == linkNameOpposite) && (linkDialogueEntries[i].CorrespondingConnectorGuid == guidOfRef))
                    break;
                i++;
            }
            if(i < listboxLinks.Items.Count)
                listboxLinks.SelectedIndex = i;

            if (linkVariable.BindingOperator == BindingOperator.CHECK_ONLY)
                radioButtonCheckOnly.Checked = true;
            else if (linkVariable.BindingOperator == BindingOperator.CREATE)
                radioButtonCreate.Checked = true;
            else if (linkVariable.BindingOperator == BindingOperator.DESTROY)
                radioButtonDestroy.Checked = true;
            if (linkVariable.BindingSemantics == BindingSemantics.MANDATORY)
                radioButtonMandatory.Checked = true;
            else if (linkVariable.BindingSemantics == BindingSemantics.NEGATIVE)
                radioButtonNegative.Checked = true;
            
            if (linkVariable.NacIndex != -1)
                complexNAC1.setNacIndexValue(linkVariable.NacIndex.ToString());
        }

       



        private void addPossibleLinks()
        {
            SQLElement clientClassifier = EAUtil.getClassifierElement(repository, lvClient.ClassifierID);
            SQLElement supplierClassifier = EAUtil.getClassifierElement(repository, lvSupplier.ClassifierID);
            if (clientClassifier != null && supplierClassifier != null)
            {
                List<SQLConnector> clientToSupplierAssociations = new List<SQLConnector>();
                List<SQLConnector> supplierToClientAssociations = new List<SQLConnector>();

                collectPossibleAssociations(ref clientClassifier, ref supplierClassifier, ref clientToSupplierAssociations, ref supplierToClientAssociations, ref this.repository);

                foreach (SQLConnector possibleAssociation in clientToSupplierAssociations)
                {
                    LinkDialogueEntry linkDialogueEntry = new LinkDialogueEntry();
                    linkDialogueEntry.CorrespondingConnectorGuid = possibleAssociation.ConnectorGUID;

                    EA.Element associationClient = repository.GetOriginalRepository().GetElementByID(possibleAssociation.ClientID);
                    EA.Element associationSupplier = repository.GetOriginalRepository().GetElementByID(possibleAssociation.SupplierID);
                    if (possibleAssociation.Direction == "Bi-Directional")
                    {

                        linkDialogueEntry.output = (lvClient.Name + ":" + associationClient.Name + " <--(" + possibleAssociation.ClientEnd.Role + ") - (" + possibleAssociation.SupplierEnd.Role + ")-->  " + lvSupplier.Name + ":" + associationSupplier.Name);
                        linkDialogueEntry.direction = LinkDialogueEntryDirection.BI;
                        linkDialogueEntry.clientRoleName = possibleAssociation.ClientEnd.Role;
                        linkDialogueEntry.supplierRoleName = possibleAssociation.SupplierEnd.Role;
                    }
                    else if (possibleAssociation.Direction == Main.EASourceTargetDirection)
                    {
                        linkDialogueEntry.output = (lvClient.Name + ":" + associationClient.Name + " --(" + possibleAssociation.SupplierEnd.Role + ")--> " + lvSupplier.Name + ":" + associationSupplier.Name);
                        linkDialogueEntry.direction = LinkDialogueEntryDirection.RIGHT;
                        linkDialogueEntry.supplierRoleName = possibleAssociation.SupplierEnd.Role;
                    }
                    else if (possibleAssociation.Direction == Main.EATargetSourceDirection)
                    {
                        linkDialogueEntry.output = (lvClient.Name + ":" + associationClient.Name + " <--(" + possibleAssociation.ClientEnd.Role + ")-- " + lvSupplier.Name + ":" + associationSupplier.Name);
                        linkDialogueEntry.OutputOpposite = (lvSupplier.Name + ":" + associationSupplier.Name + " --(" + possibleAssociation.ClientEnd.Role + ")--> " + lvClient.Name + ":" + associationClient.Name);

                        linkDialogueEntry.direction = LinkDialogueEntryDirection.LEFT;
                        linkDialogueEntry.supplierRoleName = possibleAssociation.ClientEnd.Role;
                    }

                    if (linkDialogueEntry.output != null)
                    {
                        linkDialogueEntries.Add(linkDialogueEntry);
                        listboxLinks.Items.Add(linkDialogueEntry.output);
                    }


                }

                foreach (SQLConnector possibleAssociation in supplierToClientAssociations)
                {
                    LinkDialogueEntry linkDialogueEntry = new LinkDialogueEntry();
                    linkDialogueEntry.CorrespondingConnectorGuid = possibleAssociation.ConnectorGUID;

                    EA.Element associationClient = repository.GetOriginalRepository().GetElementByID(possibleAssociation.ClientID);
                    EA.Element associationSupplier = repository.GetOriginalRepository().GetElementByID(possibleAssociation.SupplierID);
                    if (possibleAssociation.Direction == "Bi-Directional")
                    {
                        linkDialogueEntry.output = (lvClient.Name + ":" + associationSupplier.Name + " <--(" + possibleAssociation.SupplierEnd.Role + ") - (" + possibleAssociation.ClientEnd.Role + ")-->  " + lvSupplier.Name + ":" + associationClient.Name);
                        linkDialogueEntry.direction = LinkDialogueEntryDirection.BI;
                        linkDialogueEntry.clientRoleName = possibleAssociation.SupplierEnd.Role;
                        linkDialogueEntry.supplierRoleName = possibleAssociation.ClientEnd.Role;
                    }
                    else if (possibleAssociation.Direction == Main.EASourceTargetDirection)
                    {
                        linkDialogueEntry.output = (lvClient.Name + ":" + associationSupplier.Name + " <--(" + possibleAssociation.SupplierEnd.Role + ")-- " + lvSupplier.Name + ":" + associationClient.Name);
                        linkDialogueEntry.OutputOpposite = (lvSupplier.Name + ":" + associationClient.Name + " --(" + possibleAssociation.SupplierEnd.Role + ")--> " + lvClient.Name + ":" + associationSupplier.Name);
                        linkDialogueEntry.direction = LinkDialogueEntryDirection.LEFT;
                        linkDialogueEntry.supplierRoleName = possibleAssociation.SupplierEnd.Role;
                    }
                    else if (possibleAssociation.Direction == Main.EATargetSourceDirection)
                    {
                        linkDialogueEntry.output = (lvClient.Name + ":" + associationSupplier.Name + " --(" + possibleAssociation.ClientEnd.Role + ")--> " + lvSupplier.Name + ":" + associationClient.Name);
                        linkDialogueEntry.direction = LinkDialogueEntryDirection.RIGHT;
                        linkDialogueEntry.supplierRoleName = possibleAssociation.ClientEnd.Role;
                    }
                    if (linkDialogueEntry.output != null)
                    {
                        linkDialogueEntries.Add(linkDialogueEntry);
                        listboxLinks.Items.Add(linkDialogueEntry.output);
                    }
                }
                
                setListBoxSize();


            }
        }

        public static void collectPossibleAssociations(ref SQLElement clientClassifier, ref SQLElement supplierClassifier, ref List<SQLConnector> clientToSupplierAssociations, ref List<SQLConnector> supplierToClientAssociations, ref SQLRepository repository)
        {
            List<SQLElement> clientBaseClasses = EAUtil.getBaseClasses(clientClassifier);
            List<SQLElement> supplierBaseClasses = EAUtil.getBaseClasses(supplierClassifier);

            String sqlStub = "select * from t_connector where ";


            foreach (SQLElement actClient in clientBaseClasses)
            {
                String sqlClientToSupplier = "";
                String sqlSupplierToClient = "";

                foreach (SQLElement actSupplier in supplierBaseClasses)
                {
                    sqlClientToSupplier = sqlClientToSupplier + " Start_Object_ID = " + actClient.ElementID + " and End_Object_ID = " + actSupplier.ElementID + " and Connector_Type = 'Association' or ";
                    sqlSupplierToClient = sqlSupplierToClient + " End_Object_ID = " + actClient.ElementID + " and Start_Object_ID = " + actSupplier.ElementID + " and Connector_Type = 'Association' or ";
                }
                sqlSupplierToClient = sqlSupplierToClient.Substring(0, sqlSupplierToClient.Length - 3);
                sqlClientToSupplier = sqlClientToSupplier.Substring(0, sqlClientToSupplier.Length - 3);

                String sqlStringClientToSupplier = sqlStub + sqlClientToSupplier;
                String sqlStringSupplierToClient = sqlStub + sqlSupplierToClient;

                String sqlResultClientToSupplier = repository.SQLQuery(sqlStringClientToSupplier);
                String sqlResultSupplierToClient = repository.SQLQuery(sqlStringSupplierToClient);


                foreach (String row in EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlResultClientToSupplier, "Row"))
                {
                    if (row != "")
                    {
                        SQLConnector actCon = new SQLConnector(repository, row);
                        clientToSupplierAssociations.Add(actCon);
                    }
                }

                foreach (String row in EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlResultSupplierToClient, "Row"))
                {
                    if (row != "")
                    {
                        SQLConnector actCon = new SQLConnector(repository, row);
                        supplierToClientAssociations.Add(actCon);
                    }
                }
            }

            //only if there is no valid association
            if (supplierToClientAssociations.Count == 0 && clientToSupplierAssociations.Count == 0)
            {
                foreach (SQLElement actClient in clientBaseClasses)
                {
                    String sqlString = @"select a.* from t_connector a, t_object b where " +
                                        "a.Start_Object_ID = " + actClient.ElementID + " AND a.End_Object_ID = b.Object_ID AND " +
                                        "b.Name = 'EObject' AND a.Connector_Type = 'Association'";

                    String result = repository.SQLQuery(sqlString);
                    foreach (String row in EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
                    {
                        if (row != "")
                        {
                            SQLConnector actCon = new SQLConnector(repository, row);
                            clientToSupplierAssociations.Add(actCon);
                        }
                    }

                    sqlString = @"select a.* from t_connector a, t_object b where " +
                                        "a.End_Object_ID = " + actClient.ElementID + " AND a.Start_Object_ID = b.Object_ID AND " +
                                        "b.Name = 'EObject' AND a.Connector_Type = 'Association'";

                    result = repository.SQLQuery(sqlString);
                    foreach (String row in EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
                    {
                        if (row != "")
                        {
                            SQLConnector actCon = new SQLConnector(repository, row);
                            supplierToClientAssociations.Add(actCon);
                        }
                    }
                }

                foreach (SQLElement actClient in supplierBaseClasses)
                {
                    String sqlString = @"select a.* from t_connector a, t_object b where " +
                                        "a.End_Object_ID = " + actClient.ElementID + " AND a.Start_Object_ID = b.Object_ID AND " +
                                        "b.Name = 'EObject' AND a.Connector_Type = 'Association'";

                    String result = repository.SQLQuery(sqlString);
                    foreach (String row in EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
                    {
                        if (row != "")
                        {
                            SQLConnector actCon = new SQLConnector(repository, row);

                            clientToSupplierAssociations.Add(actCon);

                        }
                    }

                    sqlString = @"select a.* from t_connector a, t_object b where " +
                                        "a.Start_Object_ID = " + actClient.ElementID + " AND a.End_Object_ID = b.Object_ID AND " +
                                        "b.Name = 'EObject' AND a.Connector_Type = 'Association'";

                    result = repository.SQLQuery(sqlString);
                    foreach (String row in EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
                    {
                        if (row != "")
                        {
                            SQLConnector actCon = new SQLConnector(repository, row);
                            supplierToClientAssociations.Add(actCon);
                        }
                    }

                }


            }
        }


        private void setListBoxSize()
        {
            int charsMax = 0;
            foreach (String item in this.listboxLinks.Items)
            {
                if(item.Length > charsMax)
                    charsMax = item.Length;
            }

            int valuedCharSize = charsMax - 45;
            this.Size = new Size(290 + valuedCharSize * 5, 296);
        }



        /// <summary>
        /// Pressing the OK Button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnOK_Click(Object sender, EventArgs e)
        {
            EA.Diagram openDiag = repository.GetCurrentDiagram();
            //No Association between the objects classifiers has been found
            if (listboxLinks.Items.Count == 0 ) 
            {
                System.Windows.Forms.MessageBox.Show("There is no Association between these two elements");
                Close();
                return;
            }
            if (listboxLinks.SelectedItem != null)
            {      
                BindingSemantics bindingSemantics = BindingSemantics.MANDATORY;
                BindingOperator bindingOperator = BindingOperator.CHECK_ONLY;
                if (radioButtonMandatory.Checked)
                    bindingSemantics = BindingSemantics.MANDATORY;
                else if (radioButtonNegative.Checked)
                    bindingSemantics = BindingSemantics.NEGATIVE;
                if (radioButtonDestroy.Checked)
                    bindingOperator = BindingOperator.DESTROY;
                else if (radioButtonCreate.Checked)
                    bindingOperator = BindingOperator.CREATE;
                else if (radioButtonCheckOnly.Checked)
                    bindingOperator = BindingOperator.CHECK_ONLY;

                if (complexNAC1.getNacIndexValue() != "" && radioButtonNegative.Checked)
                    linkVariable.NacIndex = int.Parse(complexNAC1.getNacIndexValue());
                else
                    linkVariable.NacIndex = -1;

                linkVariable.linkDialogueEntry = this.linkDialogueEntries[listboxLinks.SelectedIndex];
                                
                //new linkvariable handling
                linkVariable.BindingSemantics = bindingSemantics;
                linkVariable.BindingOperator = bindingOperator;

                linkVariable.saveTreeToEATaggedValue(true);
                EA.Diagram currentDiag = repository.GetCurrentDiagram();
                if (currentDiag != null)
                    repository.ReloadDiagram(currentDiag.DiagramID);
                Close();
            }            
        }
        
        private void listboxLinks_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            btnOK_Click(sender, e);
        }

        private void radioButtonNegative_CheckedChanged(object sender, EventArgs e)
        {
            updateRadioButtonsOnChange();
        }

        private void radioButtonCreate_CheckedChanged(object sender, EventArgs e)
        {
            updateRadioButtonsOnChange();
        }

        private void radioButtonDestroy_CheckedChanged(object sender, EventArgs e)
        {
            updateRadioButtonsOnChange();
        }


        private void updateRadioButtonsOnChange()
        {
            if (radioButtonNegative.Checked)
            {
                radioButtonCheckOnly.Checked = true;
                radioButtonCreate.Enabled = false;
                radioButtonDestroy.Enabled = false;
                complexNAC1.Enabled = true;               
            }
            else
            {
                radioButtonCreate.Enabled = true;
                radioButtonDestroy.Enabled = true;
                complexNAC1.Enabled = false;
            }

            if (radioButtonCreate.Checked || radioButtonDestroy.Checked)
            {
                radioButtonNegative.Enabled = false;
            }
            else
            {
                radioButtonNegative.Enabled = true;
            }

        }


    }
}
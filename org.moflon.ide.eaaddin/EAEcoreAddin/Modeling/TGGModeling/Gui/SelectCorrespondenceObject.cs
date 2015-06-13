using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.ECOREModeling;
using EA;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGActions
{
    public partial class SelectCorrespondenceObject : Form
    {
        Dictionary<int, List<SQLConnector>> possibleCorrespondencesLinkWithConnectors;
        SQLElement corrObject;
        SQLRepository sqlRepository;
        EA.Repository repository;
        BindingOperator bo;

        SQLElement clientClassifier;
        SQLElement supplierClassifier;

        SQLElement clientOv;
        SQLElement supplierOv;

        public int selectedCorrespondenceLinkId {get;set;}

        public SelectCorrespondenceObject(Dictionary<int, List<SQLConnector>> possibleCorrespondencesLinkWithConnectors, SQLElement corrObject, SQLRepository repository, BindingOperator bO, SQLElement clientClassifier, SQLElement supplierClassifier, SQLElement clientOv, SQLElement supplierOv)
        {
            InitializeComponent();
            this.sqlRepository = repository;
            this.repository = sqlRepository.GetOriginalRepository();
            this.StartPosition = FormStartPosition.CenterScreen;
            this.possibleCorrespondencesLinkWithConnectors = possibleCorrespondencesLinkWithConnectors;
            this.corrObject = corrObject;
            this.clientOv = clientOv;
            this.supplierOv = supplierOv;

            DomainType clientDomain = TGGModelingUtil.getDomainOfEClass(sqlRepository, clientClassifier);
            DomainType supplierDomain = TGGModelingUtil.getDomainOfEClass(sqlRepository, supplierClassifier);

            if (clientDomain == DomainType.SOURCE && supplierDomain == DomainType.TARGET)
            {

                this.clientClassifier = clientClassifier;
                this.supplierClassifier = supplierClassifier;
            }
            else if (clientDomain == DomainType.TARGET && supplierDomain == DomainType.SOURCE)
            {
                this.clientClassifier = supplierClassifier;
                this.supplierClassifier = clientClassifier;
            }

            else
            {
                this.clientClassifier = clientClassifier;
                this.supplierClassifier = supplierClassifier;
            }
            
            this.bo = bO;
            foreach (int possibleCorrClassID in this.possibleCorrespondencesLinkWithConnectors.Keys)
            {
                SQLElement possibleCorrClass = sqlRepository.GetElementByID(possibleCorrClassID);
                this.comboBoxCorrClasses.Items.Add(possibleCorrClass.Name); 
            }

            checkBoxCreateNew.Checked = false;
            if (this.clientClassifier != null && this.supplierClassifier != null)
            {
                textBoxObjectName.Text = this.clientOv.Name + "To" + this.supplierOv.Name.Substring(0, 1).ToUpper() + this.supplierOv.Name.Substring(1, this.supplierOv.Name.Length - 1);
            }
            textBoxLinkName.Text = this.clientClassifier.Name + "To" + this.supplierClassifier.Name;


            if (this.comboBoxCorrClasses.Items.Count > 0)
                this.comboBoxCorrClasses.SelectedIndex = 0;

            ShowDialog();
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            EAUtil.deleteElement(this.corrObject.getRealElement(), this.sqlRepository);
            Close();
        }

        private void buttonOk_Click(object sender, EventArgs e)
        {
            if (!checkBoxCreateNew.Checked)
            {
                if (this.comboBoxCorrClasses.SelectedIndex != -1)
                {
                    int i = 0;
                    foreach (int corId in this.possibleCorrespondencesLinkWithConnectors.Keys)
                    {
                        if (i++ == this.comboBoxCorrClasses.SelectedIndex)
                            this.selectedCorrespondenceLinkId = corId;
                    }

                    EA.Element selectedLink = sqlRepository.GetOriginalRepository().GetElementByID(selectedCorrespondenceLinkId);
                    corrObject.getRealElement().ClassifierID = selectedLink.ElementID;
                    corrObject.getRealElement().Name = textBoxObjectName.Text;
                    corrObject.getRealElement().Update();

                    TGGCorrespondence tggCorrOv = new TGGCorrespondence(sqlRepository.GetElementByID(corrObject.ElementID), sqlRepository);
                    tggCorrOv.BindingOperator = this.bo;
                    tggCorrOv.saveTreeToEATaggedValue(true);
                    Close();
                }

            }
            else if (checkBoxCreateNew.Checked)
            {
                EA.Package tggOutermostPackage = EAUtil.sqlEAObjectToOriginalObject(sqlRepository, EAUtil.getOutermostPackage(corrObject, sqlRepository)) as EA.Package;
                EA.Connector connectorToSource;
                EA.Connector connectorToTarget;

                EA.Element selectedLink = createNewCorrespondenceLink(tggOutermostPackage, out connectorToSource, out connectorToTarget); 
                this.corrObject.getRealElement().ClassifierID = selectedLink.ElementID;
                this.corrObject.getRealElement().Name = textBoxObjectName.Text;
                this.corrObject.getRealElement().Update();



                TGGCorrespondence tggCorrOv = new TGGCorrespondence(sqlRepository.GetElementByID(corrObject.ElementID), sqlRepository);
                tggCorrOv.BindingOperator = this.bo;
                tggCorrOv.saveTreeToEATaggedValue(true);

                List<SQLConnector> references = new List<SQLConnector>();
                references.Add(sqlRepository.GetConnectorByID(connectorToSource.ConnectorID));
                references.Add(sqlRepository.GetConnectorByID(connectorToTarget.ConnectorID));

                possibleCorrespondencesLinkWithConnectors.Add(selectedLink.ElementID, references);

                Close();

                
            }
        }

        private EA.Element createNewCorrespondenceLink(EA.Package tggOutermostePackage, out EA.Connector connectorToSource, out EA.Connector connectorToTarget)
        {
            EA.Element tggLink = tggOutermostePackage.Elements.AddNew(textBoxLinkName.Text, Main.EAClassType) as EA.Element;
            tggLink.StereotypeEx = TGGModelingMain.TggCorrespondenceTypeStereotype;
            tggLink.Update();

            TGGCorrespondenceType correspondenceType = new TGGCorrespondenceType(sqlRepository.GetElementByID(tggLink.ElementID), sqlRepository);
            correspondenceType.saveTreeToEATaggedValue(false);

            connectorToSource = tggLink.Connectors.AddNew("", ECOREModelingMain.EReferenceConnectorType) as EA.Connector;
            connectorToSource.SupplierID = clientClassifier.ElementID;
            connectorToSource.Direction = Main.EASourceTargetDirection;
            connectorToSource.SupplierEnd.Role = "source";
            connectorToSource.SupplierEnd.Navigable = "Navigable";
            connectorToSource.SupplierEnd.Cardinality = "1";
            connectorToSource.SupplierEnd.IsNavigable = true;

            connectorToSource.Update();

            EReference refToSource = new EReference(sqlRepository.GetConnectorByID(connectorToSource.ConnectorID), sqlRepository);
            refToSource.saveTreeToEATaggedValue(true);


            tggLink.Connectors.Refresh();

            connectorToTarget = tggLink.Connectors.AddNew("", ECOREModelingMain.EReferenceConnectorType) as EA.Connector;
            connectorToTarget.SupplierID = supplierClassifier.ElementID;
            connectorToTarget.Direction = Main.EASourceTargetDirection;
            connectorToTarget.SupplierEnd.Role = "target";
            connectorToTarget.SupplierEnd.Cardinality = "1";
            connectorToTarget.SupplierEnd.Navigable = "Navigable";
            connectorToTarget.SupplierEnd.IsNavigable = true;

            connectorToTarget.Update();

            EReference refToTarget = new EReference(sqlRepository.GetConnectorByID(connectorToTarget.ConnectorID), sqlRepository);
            refToTarget.saveTreeToEATaggedValue(true);

            selectedCorrespondenceLinkId = tggLink.ElementID;

            EA.Diagram curDiagram = null;
            EA.DiagramObject sourceDiagObj = null;
            EA.DiagramObject targetDiagObj = null;
            foreach (EA.Diagram diag in tggOutermostePackage.Diagrams)
            {
             
                foreach (EA.DiagramObject diagObj in diag.DiagramObjects)
                {
                    if (diagObj.ElementID == clientClassifier.ElementID)
                        sourceDiagObj = diagObj;
                    else if (diagObj.ElementID == supplierClassifier.ElementID)
                        targetDiagObj = diagObj;

                    if (sourceDiagObj != null && targetDiagObj != null)
                    {
                        curDiagram = diag;
                    }
                }
                
            }

            if (curDiagram != null)
            {
                sqlRepository.SaveDiagram(curDiagram.DiagramID);
                int sourceLeft = (sourceDiagObj == null) ? 0 : sourceDiagObj.left;
                int sourceRight = (sourceDiagObj == null) ? 0 : sourceDiagObj.right;
                int sourceBottom = (sourceDiagObj == null) ? 0 : sourceDiagObj.bottom;
                int sourceTop = (sourceDiagObj == null) ? 0 : sourceDiagObj.top;

                int targetLeft = (targetDiagObj == null) ? 0 : targetDiagObj.left;
                int targetRight = (targetDiagObj == null) ? 0 : targetDiagObj.right;
                int targetBottom = (targetDiagObj == null) ? 0 : targetDiagObj.bottom;
                int targetTop = (targetDiagObj == null) ? 0 : targetDiagObj.top;

                EA.DiagramObject tggLinkDiagObj = curDiagram.DiagramObjects.AddNew("", "") as EA.DiagramObject;
                tggLinkDiagObj.ElementID = tggLink.ElementID;
                tggLinkDiagObj.Update();
                tggLinkDiagObj.left = (sourceLeft + targetLeft) / 2;
                tggLinkDiagObj.right = (sourceRight + targetRight) / 2;
                tggLinkDiagObj.bottom = (sourceBottom + targetBottom) / 2;
                tggLinkDiagObj.top = (sourceTop + targetTop) / 2;
                tggLinkDiagObj.Update();
                curDiagram.DiagramObjects.Refresh();

                sqlRepository.ReloadDiagram(curDiagram.DiagramID);

                tggOutermostePackage.Elements.Refresh();
            }
            return tggLink;
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBoxCreateNew.Checked)
            {
                groupBoxSelectLink.Enabled = false;
                groupBoxCreateNewLink.Enabled = true;
            }
            else
            {
                groupBoxSelectLink.Enabled = true;
                groupBoxCreateNewLink.Enabled = false;
            }
        }
    }
}

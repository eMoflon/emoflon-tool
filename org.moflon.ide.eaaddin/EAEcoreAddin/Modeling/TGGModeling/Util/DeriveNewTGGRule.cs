using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling.LinkVariableDialog;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.CSP.ExportWrapper;

namespace EAEcoreAddin.Modeling.TGGModeling.Util
{
    public partial class DeriveNewTGGRule : Form
    {
        SQLRepository repository;

        Dictionary<int, int> oldElementIdToNewElementId = new Dictionary<int, int>();
        Dictionary<int, EA.Element> newElementIdToNewElement = new Dictionary<int, EA.Element>();


        List<EA.Connector> selectedConnectors = new List<EA.Connector>();
        List<EA.Element> selectedElements = new List<EA.Element>();
        List<EA.DiagramObject> selectedDiagramObject = new List<EA.DiagramObject>();
        EA.Package rulesPackage;

        EA.Element newRuleElement;
        EA.Diagram newRuleDiagram;
        private SQLElement originalRuleElement;

        public DeriveNewTGGRule(SQLRepository repository)
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;
            this.repository = repository;
            this.textBoxRuleName.Select();

            EA.Diagram oldDiagram = repository.GetCurrentDiagram();
            this.originalRuleElement = repository.GetElementByID(oldDiagram.ParentID);

            if (oldDiagram.MetaType == TGGModelingMain.TggSchemaDiagramMetatype[0])
            {
                checkBoxExactCopy.Enabled = false;
            }

            findSelectedElements(repository, oldDiagram);

            findRulesPackage(repository, oldDiagram);

            this.ShowDialog();
        }

        private void findRulesPackage(SQLRepository repository, EA.Diagram oldDiagram)
        {
            this.rulesPackage = repository.GetOriginalRepository().GetPackageByID(oldDiagram.PackageID);
            if (rulesPackage.Element.Stereotype != TGGModelingMain.TggRulePackageStereotype)
            {
                foreach (EA.Package childPkg in rulesPackage.Packages)
                {
                    if (childPkg.Element.Stereotype == TGGModelingMain.TggRulePackageStereotype
                        || childPkg.Element.Stereotype.ToLower() == ECOREModelingMain.EPackageStereotype.ToLower())
                        this.rulesPackage = childPkg;
                }
            }
        }

        private void findSelectedElements(SQLRepository repository, EA.Diagram oldDiagram)
        {
            foreach (EA.DiagramObject selectedDiagramObject in oldDiagram.SelectedObjects)
            {
                EA.Element selectedElement = repository.GetOriginalRepository().GetElementByID(selectedDiagramObject.ElementID);
                if (selectedElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype
                    || selectedElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype
                    || selectedElement.Stereotype == TGGModelingMain.CSPConstraintStereotype
                    || selectedElement.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype
                    || selectedElement.Stereotype == ECOREModeling.ECOREModelingMain.EClassStereotype)
                {
                    this.selectedElements.Add(selectedElement);
                    this.selectedDiagramObject.Add(selectedDiagramObject);
                }
            }
        }

        private void buttonOk_Click(object sender, EventArgs e)
        {

            createRuleElementAndRuleDiagram();

            createNewElementsInRule();

            findSelectedConnectors();

            createConnectorsInRule();

            repository.OpenDiagram(newRuleDiagram.DiagramID);
            Close();

            Main.consistencyModule.dispatchSingleObject(repository.GetOriginalRepository(), newRuleElement.ElementGUID, newRuleElement.ObjectType, true);

        }

        private void addInheritanceLinkToOriginalRule()
        {
            EA.Element baseRule = this.newRuleElement;
            EA.Element parentRule = this.originalRuleElement.getRealElement();

            EA.Connector refinementConnector = baseRule.Connectors.AddNew("", "TGGCustomGeneralization") as EA.Connector;
            refinementConnector.SupplierID = parentRule.ElementID;
            refinementConnector.Update();
            baseRule.Update();
            parentRule.Update();
        }

        private void createConnectorsInRule()
        {
            for (int i = 0; i < selectedConnectors.Count; i++)
            {
                EA.Connector currentConnector = selectedConnectors[i];
                //stereotype is ereference if derive is called on a tgg schema instead of another tgg rule
                if (currentConnector.Stereotype == TGGModelingMain.TggLinkVariableStereotype || currentConnector.Stereotype == "ereference")
                {
                    addTGGLinkVariableCopyToRule(currentConnector);
                }
                //handle tgg csp connectors
                else if (currentConnector.Stereotype == TGGModelingMain.CSPConstraintLinkStereotype)
                {
                    addCSPConstraintLinkCopy(currentConnector);
                }
            }
        }



        private void findSelectedConnectors()
        {
            foreach (EA.Element selElem in selectedElements)
            {
                foreach (EA.Connector con in selElem.Connectors)
                {
                    //connection is between selected elements and is outgoing
                    if (oldElementIdToNewElementId.ContainsKey(con.ClientID)
                        && oldElementIdToNewElementId.ContainsKey(con.SupplierID)
                        && con.ClientID == selElem.ElementID
                        && con.ClientID != con.SupplierID)
                    {
                        this.selectedConnectors.Add(con);
                    }

                }
            }
        }

        private void createNewElementsInRule()
        {
            for (int i = 0; i < selectedElements.Count; i++)
            {
                EA.Element selectedElement = selectedElements[i];
                if (selectedElements[i].Type == "Constraint")
                {
                    addConstraintCopyToRule(selectedElements[i], selectedDiagramObject[i]);
                }
                else if (selectedElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype
                     || selectedElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype
                     || selectedElement.Stereotype == TGGModelingMain.CSPConstraintStereotype)
                {
                    addElementCopyToRule(selectedElements[i], selectedDiagramObject[i]);
                }
                else if (selectedElement.Stereotype == ECOREModeling.ECOREModelingMain.EClassStereotype
                     || selectedElement.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype)
                {
                    addTGGElementToRuleFromSchema(selectedElement, selectedDiagramObject[i]);
                }
            }
        }


        private void addCSPConstraintLinkCopy(EA.Connector connectorToBeCopied)
        {
            EA.Element newSource = newElementIdToNewElement[oldElementIdToNewElementId[connectorToBeCopied.ClientID]];
            EA.Element newTarget = newElementIdToNewElement[oldElementIdToNewElementId[connectorToBeCopied.SupplierID]];

            EA.Connector newConnector = newSource.Connectors.AddNew("", connectorToBeCopied.Type) as EA.Connector;
            newConnector.Stereotype = connectorToBeCopied.Stereotype;
            newConnector.ClientEnd.Role = connectorToBeCopied.ClientEnd.Role;
            newConnector.SupplierEnd.Role = connectorToBeCopied.SupplierEnd.Role;
            newConnector.Name = connectorToBeCopied.Name;
            newConnector.SupplierID = newTarget.ElementID;
            newConnector.ClientID = newSource.ElementID;
            newConnector.Update();
        }

        private void addTGGLinkVariableCopyToRule(EA.Connector connectorToBeCopied)
        {
            EA.Element newSource = newElementIdToNewElement[oldElementIdToNewElementId[connectorToBeCopied.ClientID]];
            EA.Element newTarget = newElementIdToNewElement[oldElementIdToNewElementId[connectorToBeCopied.SupplierID]];

            EA.Connector newConnector = newSource.Connectors.AddNew("", connectorToBeCopied.Type) as EA.Connector;
            newConnector.Stereotype = TGGModelingMain.TggLinkVariableStereotype;
            newConnector.ClientEnd.Role = connectorToBeCopied.ClientEnd.Role;
            newConnector.SupplierEnd.Role = connectorToBeCopied.SupplierEnd.Role;
            newConnector.SupplierID = newTarget.ElementID;
            newConnector.ClientID = newSource.ElementID;
            newConnector.Update();
            if (connectorToBeCopied.Stereotype == TGGModelingMain.TggLinkVariableStereotype)
            {
                foreach (EA.ConnectorTag tag in connectorToBeCopied.TaggedValues)
                {
                    EA.ConnectorTag newTag = newConnector.TaggedValues.AddNew(tag.Name, "") as EA.ConnectorTag;
                    if (!checkBoxExactCopy.Checked)
                    {
                        newTag.Value = tag.Value.Replace("create", "check_only");
                        newTag.Notes = tag.Notes.Replace("\"bindingOperator\" value=\"create", "\"bindingOperator\" value=\"check_only");
                    }
                    else
                    {
                        newTag.Value = tag.Value;
                        newTag.Notes = tag.Notes;
                    }
                    newTag.Update();
                }
            }
            EA.DiagramLink newDiagramLink = newRuleDiagram.DiagramLinks.AddNew("", newConnector.Type) as EA.DiagramLink;
            newDiagramLink.ConnectorID = newConnector.ConnectorID;
            newDiagramLink.Update();
            if (connectorToBeCopied.Stereotype != TGGModelingMain.TggLinkVariableStereotype)
            {
                LinkDialogueEntry linkEntry = new LinkDialogueEntry();
                linkEntry.direction = LinkDialogueEntryDirection.RIGHT;
                linkEntry.CorrespondingConnectorGuid = connectorToBeCopied.ConnectorGUID;
                if (newConnector.ClientEnd.Role != "")
                    linkEntry.supplierRoleName = newConnector.ClientEnd.Role;
                else if (newConnector.SupplierEnd.Role != "")
                    linkEntry.supplierRoleName = newConnector.SupplierEnd.Role;
                TGGLinkVariable tggLv = new TGGLinkVariable(repository.GetConnectorByID(newConnector.ConnectorID), repository);
                tggLv.linkDialogueEntry = linkEntry;
                if (!checkBoxExactCopy.Checked)
                {
                    tggLv.BindingOperator = SDMModeling.SDMExportWrapper.patterns.BindingOperator.CREATE;
                }
                tggLv.saveTreeToEATaggedValue(true);
            }
        }

        private void addConstraintCopyToRule(EA.Element elementToBeCopied, EA.DiagramObject diagramObjectToBeCopied)
        {

            EA.Element newConstraint = newRuleElement.EmbeddedElements.AddNew(elementToBeCopied.Name, "Note") as EA.Element;
            newConstraint.Type = "Constraint";
            newConstraint.Notes = elementToBeCopied.Notes;
            newConstraint.Update();


            EA.DiagramObject newDiagramObject = newRuleDiagram.DiagramObjects.AddNew("", newConstraint.Type) as EA.DiagramObject;
            newDiagramObject.ElementID = newConstraint.ElementID;
            newDiagramObject.left = diagramObjectToBeCopied.left;
            newDiagramObject.right = diagramObjectToBeCopied.right;
            newDiagramObject.top = diagramObjectToBeCopied.top;
            newDiagramObject.bottom = diagramObjectToBeCopied.bottom;
            newDiagramObject.Update();


        }

        private void addTGGElementToRuleFromSchema(EA.Element elementToBeCopied, EA.DiagramObject diagramObjectToBeCopied)
        {

            EA.Element newElement = newRuleElement.Elements.AddNew(elementToBeCopied.Name.Substring(0, 1).ToLower() + elementToBeCopied.Name.Substring(1, elementToBeCopied.Name.Length - 1), Main.EAObjectType) as EA.Element;
            newElement.ClassifierID = elementToBeCopied.ElementID;
            newElement.Update();

            newElementIdToNewElement.Add(newElement.ElementID, newElement);
            oldElementIdToNewElementId.Add(elementToBeCopied.ElementID, newElement.ElementID);

            EA.DiagramObject newDiagramObject = newRuleDiagram.DiagramObjects.AddNew("", newElement.Type) as EA.DiagramObject;
            newDiagramObject.ElementID = newElement.ElementID;
            newDiagramObject.left = diagramObjectToBeCopied.left;
            newDiagramObject.right = diagramObjectToBeCopied.right;
            newDiagramObject.top = diagramObjectToBeCopied.top;
            newDiagramObject.bottom = diagramObjectToBeCopied.bottom;
            newDiagramObject.Update();

            SQLElement sqlElement = repository.GetElementByID(newElement.ElementID);

            if (elementToBeCopied.Stereotype == ECOREModeling.ECOREModelingMain.EClassStereotype)
            {
                TGGObjectVariable tggOv = new TGGObjectVariable(sqlElement, repository);
                tggOv.loadTreeFromTaggedValue();
                tggOv.BindingOperator = SDMModeling.SDMExportWrapper.patterns.BindingOperator.CREATE;
                tggOv.saveTreeToEATaggedValue(true);
            }
            else if (elementToBeCopied.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype)
            {
                TGGCorrespondence tggCorr = new TGGCorrespondence(sqlElement, repository);
                tggCorr.loadTreeFromTaggedValue();
                tggCorr.BindingOperator = SDMModeling.SDMExportWrapper.patterns.BindingOperator.CREATE;
                tggCorr.saveTreeToEATaggedValue(true);
            }

        }

        private void addElementCopyToRule(EA.Element elementToBeCopied, EA.DiagramObject diagramObjectToBeCopied)
        {

            EA.Element newElement = newRuleElement.Elements.AddNew(elementToBeCopied.Name.Substring(0, 1).ToLower() + elementToBeCopied.Name.Substring(1, elementToBeCopied.Name.Length - 1), elementToBeCopied.Type) as EA.Element;
            newElement.Stereotype = elementToBeCopied.Stereotype;
            newElement.ClassifierID = elementToBeCopied.ClassifierID;
            newElement.Notes = elementToBeCopied.Notes;
            newElement.Update();

            newElementIdToNewElement.Add(newElement.ElementID, newElement);
            oldElementIdToNewElementId.Add(elementToBeCopied.ElementID, newElement.ElementID);

            foreach (EA.TaggedValue tags in elementToBeCopied.TaggedValues)
            {
                EA.TaggedValue newTag = newElement.TaggedValues.AddNew(tags.Name, "") as EA.TaggedValue;
                if (!checkBoxExactCopy.Checked)
                {
                    newTag.Value = tags.Value.Replace(elementToBeCopied.ElementGUID, newElement.ElementGUID).Replace("create", "check_only");
                    newTag.Notes = tags.Notes.Replace(elementToBeCopied.ElementGUID, newElement.ElementGUID).Replace("\"bindingOperator\" value=\"create", "\"bindingOperator\" value=\"check_only");
                }
                else
                {
                    newTag.Value = tags.Value;
                    newTag.Notes = tags.Notes;
                }
                newTag.Update();
            }

            EA.DiagramObject newDiagramObject = newRuleDiagram.DiagramObjects.AddNew("", newElement.Type) as EA.DiagramObject;
            newDiagramObject.ElementID = newElement.ElementID;
            newDiagramObject.left = diagramObjectToBeCopied.left;
            newDiagramObject.right = diagramObjectToBeCopied.right;
            newDiagramObject.top = diagramObjectToBeCopied.top;
            newDiagramObject.bottom = diagramObjectToBeCopied.bottom;
            newDiagramObject.Update();


            if (newElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype)
            {
                TGGObjectVariable tggOv = new TGGObjectVariable(repository.GetElementByID(newElement.ElementID), repository);
                tggOv.loadTreeFromTaggedValue();
                if (!checkBoxExactCopy.Checked)
                {
                    tggOv.BindingOperator = SDMModeling.SDMExportWrapper.patterns.BindingOperator.CHECK_ONLY;
                    TGGModelingUtil.assignmentsToConstraints(tggOv, repository);
                }
                tggOv.saveTreeToEATaggedValue(true);
            }
            else if (newElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
            {
                TGGCorrespondence tggCorr = new TGGCorrespondence(repository.GetElementByID(newElement.ElementID), repository);
                tggCorr.loadTreeFromTaggedValue();
                if (!checkBoxExactCopy.Checked)
                {
                    tggCorr.BindingOperator = SDMModeling.SDMExportWrapper.patterns.BindingOperator.CHECK_ONLY;
                    TGGModelingUtil.assignmentsToConstraints(tggCorr, repository);
                }
                tggCorr.saveTreeToEATaggedValue(true);
            }
            else if (newElement.Stereotype == TGGModelingMain.CSPConstraintStereotype)
            {
                CSPInstance instance = new CSPInstance(repository, repository.GetElementByID(newElement.ElementID));
                instance.loadTreeFromTaggedValue();
                instance.saveTreeToEATaggedValue(true);
            }

        }

        private void createRuleElementAndRuleDiagram()
        {
            this.newRuleElement = rulesPackage.Elements.AddNew(this.textBoxRuleName.Text, "Class") as EA.Element;
            this.newRuleElement.Stereotype = TGGModelingMain.TggRuleStereotype;
            this.newRuleElement.Update();

            EA.Diagram rulesDiagram = rulesPackage.Diagrams.GetAt(0) as EA.Diagram;

            EA.DiagramObject newRuleDiagramObject = rulesDiagram.DiagramObjects.AddNew("", this.newRuleElement.Type) as EA.DiagramObject;
            if (this.addInheritanceLinkCheckBox.Checked)
            {
                EA.DiagramObject originalRuleDiagramObject = null;
                foreach (EA.DiagramObject diagramObject in rulesDiagram.DiagramObjects)
                {
                    if (diagramObject.ElementID == this.originalRuleElement.ElementID)
                    {
                        originalRuleDiagramObject = diagramObject;
                        break;
                    }
                }
                if (originalRuleDiagramObject != null)
                {
                    int verticalOffset = -(originalRuleDiagramObject.top - originalRuleDiagramObject.bottom + 40);
                    newRuleDiagramObject.left = originalRuleDiagramObject.left - 10;
                    newRuleDiagramObject.right = originalRuleDiagramObject.right + 10;
                    newRuleDiagramObject.top = originalRuleDiagramObject.top + verticalOffset;
                    newRuleDiagramObject.bottom = originalRuleDiagramObject.bottom + verticalOffset;
                }
            }
            else
            {
                newRuleDiagramObject.left = 10;
                newRuleDiagramObject.right = 110;
                newRuleDiagramObject.top = -10;
                newRuleDiagramObject.bottom = -80;
            }
            newRuleDiagramObject.ElementID = this.newRuleElement.ElementID;
            newRuleDiagramObject.Update();

            if (this.addInheritanceLinkCheckBox.Checked)
            {
                addInheritanceLinkToOriginalRule();
            }

            repository.ReloadDiagram(rulesDiagram.DiagramID);

            newRuleDiagram = newRuleElement.Diagrams.AddNew(this.textBoxRuleName.Text, TGGModelingMain.TggRuleDiagramMetatype[0]) as EA.Diagram;
            newRuleDiagram.Update();


            TGGRule rule = new TGGRule(repository, repository.GetElementByID(newRuleElement.ElementID));
            rule.saveTreeToEATaggedValue(true);
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void DeriveNewTGGRule_Load(object sender, EventArgs e)
        {
            this.textBoxRuleName.Text = originalRuleElement.Name + "_Copy";
        }

    }
}

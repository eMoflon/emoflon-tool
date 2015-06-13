using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.TGGModeling.TGGActions;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling.LinkVariableDialog;
using EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.Util;
using EAEcoreAddin.ControlPanel.Navigation;
using EAEcoreAddin.Modeling.Util;
using EAEcoreAddin.Modeling.SDMModeling.Gui.CreateSDMDialog;
using EAEcoreAddin.Modeling.TGGModeling.Gui;
using EAEcoreAddin.Modeling.CSP.Gui;
using EAEcoreAddin.Modeling.CSP;

namespace EAEcoreAddin.Modeling.TGGModeling
{
    class TGGModelingMain
    {
        public static readonly String TGGkernelMorphismStereotype = "Kernel Morphism";
        public static readonly String TggCustomGeneralizationStereotype = "TGGCustomGeneralization";
        public static readonly String TggLinkVariableStereotype = "TGGLinkVariable";
        public static readonly String TggLinkStereotype = "TGGLink";
        public static readonly String CSPConstraintLinkStereotype = "ConstraintLink";

        
        public static readonly String TggSchemaPackageStereotype = "TGGSchemaPackage";
        public static readonly String TggRuleStereotype = "Rule";
        public static readonly String TggRuleSetStereotype = "Rule-Set";

        public static readonly String CSPConstraintStereotype = SDMModelingMain.CSPInstanceStereotype;

        public static readonly String TggRulePackageStereotype = "RulePackage";
        public static readonly String TggLinkInstanceStereotype = "TGGLink_instance";

        public static readonly String TggObjectVariableStereotype = "TGGObjectVariable";
        public static readonly String TggCorrespondenceStereotype = "Correspondence";

        public static readonly String TggCorrespondenceTypeStereotype = "CorrespondenceType";

        public static readonly String TggBindingExpressionLinkMethodGuidTaggedValue = "BindingMethodGuid";
        

        public static readonly String[] TggSchemaDiagramMetatype = { "eMoflon TGG Diagrams::TGG Schema",
                                                                     "TGGDiagram::TGG Schema" };

        public static readonly String[] TggRulesDiagramMetatype = { "eMoflon TGG Diagrams::TGG Rules",
                                                                    "TGGDiagram::TGG Rules" };

        public static readonly String[] TggRuleDiagramMetatype = { "eMoflon TGG Diagrams::TGG Rule",
                                                                    "TGGDiagram::TGG Rule" };

        public static readonly String[] TggPatternsDiagramMetatype = {  "eMoflon TGG Diagrams::TGG Patterns", 
                                                                        "TGGDiagram::TGG Patterns" };

        OvDialog ObjectVariableDialog;
        LinkVariablePropertiesForm LinkVariableDialog;

        ECOREModelingMain ecoreModeling;

        public TGGModelingMain(ECOREModelingMain eModeling)
        {
            this.ecoreModeling = eModeling;
        }
        


        public void EA_OnNotifyContextItemModified(EA.Repository Repository, String GUID, EA.ObjectType ot)
        {

            SQLRepository sqlRepository = new SQLRepository(Repository, false);
            EA.Diagram actDiag = Repository.GetCurrentDiagram();

            if (ot == EA.ObjectType.otElement)
            {
                EA.Element element = Repository.GetElementByGuid(GUID);
                if (element.Stereotype.ToLower() == TGGModelingMain.TggRuleStereotype.ToLower())
                {
                    onNotifyTggRuleModified(sqlRepository, element);
                }
                else if (element.Stereotype.ToLower() == TGGModelingMain.TggCorrespondenceTypeStereotype.ToLower())
                {
                    onNotifyCorrespondenceTypeModified(sqlRepository, element);
                }

            }
            if (ot == EA.ObjectType.otPackage)
            {
                EA.Package pkg = Repository.GetPackageByGuid(GUID);
                if (pkg.Element != null && pkg.Element.Stereotype == TGGModelingMain.TggSchemaPackageStereotype)
                {
                    onNotifyTGGOutermostPackageModified(sqlRepository, pkg);
                }
                else if (pkg.Element.Stereotype == TGGModelingMain.TggRulePackageStereotype)
                {
                    onNotifyTGGRulePackageModified(sqlRepository, pkg);
                }
            }

            if (ot == EA.ObjectType.otConnector)
            {
                EA.Connector con = Repository.GetConnectorByGuid(GUID);
                if (actDiag != null)
                {
                    if (con.Type == ECOREModelingMain.EReferenceConnectorType && con.Stereotype == "" && actDiag.MetaType == TGGModelingMain.TggSchemaDiagramMetatype[0])
                    {
                        onNotifyEReferenceModified(sqlRepository, con);
                    }
                }
            }

        }

        #region on notify modification methods

        private static void onNotifyEReferenceModified(SQLRepository sqlRepository, EA.Connector con)
        {
            EReference eRef = new EReference(sqlRepository.GetConnectorByID(con.ConnectorID), sqlRepository);
            eRef.saveTreeToEATaggedValue(true);
        }


        private static void onNotifyTGGRulePackageModified(SQLRepository sqlRepository, EA.Package pkg)
        {
            TGGRulePackage rulePkg = new TGGRulePackage(sqlRepository.GetPackageByID(pkg.PackageID), sqlRepository);
            rulePkg.saveTreeToEATaggedValue(true);
        }

        private static void onNotifyTGGOutermostPackageModified(SQLRepository sqlRepository, EA.Package pkg)
        {
            TGG tgg = new TGG(sqlRepository, sqlRepository.GetPackageByID(pkg.PackageID));
            tgg.loadTreeFromTaggedValue();
            tgg.saveTreeToEATaggedValue(true);
        }

        public Boolean EA_OnPreDeleteMethod(EA.Repository Repository, EA.Method methodToDelete)
        {
            return true;
        }

        private static void onNotifyCorrespondenceTypeModified(SQLRepository sqlRepository, EA.Element element)
        {
            TGGCorrespondenceType correspondenceType = new TGGCorrespondenceType(sqlRepository.GetElementByID(element.ElementID), sqlRepository);
            correspondenceType.saveTreeToEATaggedValue(false);
        }

        private static void onNotifyTggRuleModified(SQLRepository sqlRepository, EA.Element element)
        {
            TGGRule tggRule = new TGGRule(sqlRepository, sqlRepository.GetElementByID(element.ElementID));
            //tggRule.loadTreeFromTaggedValue();
            tggRule.saveTreeToEATaggedValue(false);
            Main.addToTreeQueue(element.ElementGUID, tggRule);
        }


        #endregion






        public bool EA_OnContextItemDoubleClicked(EA.Repository Repository, String GUID, EA.ObjectType ot)
        {
            SQLRepository sqlRepository = new SQLRepository(Repository, false);
            EA.Diagram currentDiagram = Repository.GetCurrentDiagram();
            if (currentDiagram != null)
            {
                Repository.SaveDiagram(currentDiagram.DiagramID);
            }

            if (ot == EA.ObjectType.otPackage)
            {
                EA.Package doubleClickedPackage = Repository.GetPackageByGuid(GUID);
                if (doubleClickedPackage.Element.Stereotype == TGGModelingMain.TggSchemaPackageStereotype)
                {
                    return onTggSchemaPackageDoubleClicked(sqlRepository, doubleClickedPackage);
                }
            }

            if (ot == EA.ObjectType.otElement)
            {

                EA.Element doubleClickedElement = Repository.GetElementByGuid(GUID);
                if (doubleClickedElement.Stereotype == TGGModelingMain.TggLinkStereotype)
                {
                    if (doubleClickedElement.Diagrams.Count > 0)
                    {
                        onTggLinkDoubleClicked(Repository, doubleClickedElement);
                        return true;
                    }
                }

                else if (doubleClickedElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype)
                {
                    onTggOvDoubleClicked(sqlRepository, doubleClickedElement);
                    return true;
                }
                else if (doubleClickedElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
                {
                    onTggCorrespondenceDoubleClicked(sqlRepository, doubleClickedElement);
                    return true;
                }

                else if (doubleClickedElement.Stereotype == CSPConstraintStereotype || doubleClickedElement.Stereotype == "TGGCsp")
                {
                    if (currentDiagram != null && TggRuleDiagramMetatype.Contains(currentDiagram.MetaType))
                    {
                        onCSPConstraintDoubleClicked(sqlRepository, doubleClickedElement);
                        return true;
                    }
                }
                else if (doubleClickedElement.Stereotype == TGGModelingMain.TggRuleStereotype)
                {
                    if (doubleClickedElement.Diagrams.Count > 0)
                    {
                        onTggRuleDoubleClicked(Repository, doubleClickedElement);
                        return true;
                    }
                }

            }
            if (ot == EA.ObjectType.otConnector)
            {
                if (currentDiagram != null && TGGModelingMain.TggRuleDiagramMetatype.Contains(currentDiagram.MetaType))
                {
                    EA.Connector doubleClickedConnector = Repository.GetConnectorByGuid(GUID);
                    if (doubleClickedConnector.Stereotype == TGGModelingMain.TggLinkVariableStereotype || doubleClickedConnector.Stereotype == SDMModelingMain.LinkVariableStereotype)
                    {
                        onTggLvDoubleClicked(sqlRepository, sqlRepository.GetConnectorByID(doubleClickedConnector.ConnectorID));
                        return true;
                    }
                }
            }
            return false;
        }

        

        #region on double click methods



        private void onTggLvDoubleClicked(SQLRepository sqlRepository, SQLConnector doubleClickedConnector)
        {
            TGGLinkVariable tggLv = new TGGLinkVariable(doubleClickedConnector, sqlRepository);
            this.LinkVariableDialog = new LinkVariablePropertiesForm(tggLv, sqlRepository, true);

        }

        private static void onTggRuleDoubleClicked(EA.Repository Repository, EA.Element doubleClickedElement)
        {
            EA.Diagram ruleDiag = doubleClickedElement.Diagrams.GetAt(0) as EA.Diagram;
            EA.Diagram currentDiag = Repository.GetCurrentDiagram();
            if (currentDiag != null)
            {
                Navigator.addAnchorEntry(ruleDiag.DiagramID, Repository.GetCurrentDiagram().DiagramID);
                Repository.OpenDiagram(ruleDiag.DiagramID);
            }
        }

        private static void onCSPConstraintDoubleClicked(SQLRepository sqlRepository, EA.Element doubleClickedElement)
        {
            TGG tggPackage = new TGG(sqlRepository, EAUtil.getOutermostPackage(doubleClickedElement, sqlRepository));
            tggPackage.loadTreeFromTaggedValue();
            CSPInstanceDialog CSPConstraintDialog = new CSPInstanceDialog(sqlRepository, sqlRepository.GetElementByID(doubleClickedElement.ElementID), new TGGCSPController(tggPackage));
            CSPConstraintDialog.ShowDialog();
        }

        private void onTggCorrespondenceDoubleClicked(SQLRepository sqlRepository, EA.Element doubleClickedElement)
        {
            TGGCorrespondence tggCorrespondence = new TGGCorrespondence(sqlRepository.GetElementByID(doubleClickedElement.ElementID), sqlRepository);
            this.ObjectVariableDialog = new OvDialog(sqlRepository, tggCorrespondence);
            ObjectVariableDialog.ShowDialog();
        }

        private void onTggOvDoubleClicked(SQLRepository sqlRepository, EA.Element doubleClickedElement)
        {

            TGGObjectVariable tggObjectVariable = new TGGObjectVariable(sqlRepository.GetElementByID(doubleClickedElement.ElementID), sqlRepository);
            this.ObjectVariableDialog = new OvDialog(sqlRepository, tggObjectVariable);
            ObjectVariableDialog.ShowDialog();
        }

        private static void onTggLinkDoubleClicked(EA.Repository Repository, EA.Element doubleClickedElement)
        {
            EA.Diagram tggRuleDiagram = doubleClickedElement.Diagrams.GetAt(0) as EA.Diagram;
            Repository.OpenDiagram(tggRuleDiagram.DiagramID);
        }

        private static bool onTggSchemaPackageDoubleClicked(SQLRepository sqlRepository, EA.Package doubleClickedPackage)
        {
            NewTGGProjectDialog tggDialog = new NewTGGProjectDialog(sqlRepository, sqlRepository.GetPackageByID(doubleClickedPackage.PackageID));
            tggDialog.ShowDialog();
            return true;
        }

        #endregion

        private Dictionary<int, List<SQLConnector>> computeCorrespondencesWithLinks(SQLElement clientClassifier, SQLElement targetClassifier, SQLRepository Repository, int tggPackageId)
        {
            DateTime time1 = DateTime.Now;

            Dictionary<int, List<SQLConnector>> possibleCorrespondencesLinkWithConnectors = new Dictionary<int, List<SQLConnector>>();

            foreach (SQLElement clientAndBases in EAUtil.getBaseClasses(clientClassifier))
            {
                foreach (SQLConnector conOfClientClassifier in clientAndBases.Connectors)
                {
                    SQLElement elementOnOtherSide = null;
                    if (conOfClientClassifier.ClientID == clientAndBases.ElementID)
                    {
                        elementOnOtherSide = Repository.GetElementByID(conOfClientClassifier.SupplierID);
                    }
                    else if (conOfClientClassifier.SupplierID == clientAndBases.ElementID)
                    {
                        elementOnOtherSide = Repository.GetElementByID(conOfClientClassifier.ClientID);
                    }
                    if (elementOnOtherSide.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype && EAUtil.getOutermostPackage(elementOnOtherSide, Repository).PackageID == tggPackageId)
                    {
                        foreach (SQLConnector conOfTGGCorrType in elementOnOtherSide.Connectors)
                        {
                            foreach (SQLElement targetAndBases in EAUtil.getBaseClasses(targetClassifier))
                            {
                                if (conOfTGGCorrType.ClientID == targetAndBases.ElementID || conOfTGGCorrType.SupplierID == targetAndBases.ElementID)
                                {
                                    foreach (SQLElement subClass in EAUtil.findSubclasses(elementOnOtherSide, Repository))
                                    {
                                        List<SQLConnector> refs = new List<SQLConnector>();
                                        refs.Add(conOfTGGCorrType);
                                        refs.Add(conOfClientClassifier);
                                        if (!possibleCorrespondencesLinkWithConnectors.ContainsKey(subClass.ElementID))
                                        {
                                            possibleCorrespondencesLinkWithConnectors.Add(subClass.ElementID, refs);
                                        }
                                    }


                                   
                                }
                            }
                        }
                    }
                }
            }
            return possibleCorrespondencesLinkWithConnectors;
        }

        

        private void createCorrespondenceDiagramObject(EA.Diagram currentDiagram, EA.Repository Repository, EA.Element clientOv, EA.Element supplierOv, EA.Element tggCorrespondenceObject)
        {
            //create a new diagram object
            Repository.SaveDiagram(currentDiagram.DiagramID);
            EA.DiagramObject sourceDiagObj = null;
            EA.DiagramObject targetDiagObj = null;

            currentDiagram.DiagramObjects.Refresh();
            foreach (EA.DiagramObject diagObj in currentDiagram.DiagramObjects)
            {
                if (diagObj.ElementID == clientOv.ElementID)
                    sourceDiagObj = diagObj;
                else if (diagObj.ElementID == supplierOv.ElementID)
                    targetDiagObj = diagObj;

                if (sourceDiagObj != null && targetDiagObj != null)
                    continue;
            }

            int sourceLeft = (sourceDiagObj == null) ? 0 : sourceDiagObj.left;
            int sourceRight = (sourceDiagObj == null) ? 0 : sourceDiagObj.right;
            int sourceBottom = (sourceDiagObj == null) ? 0 : sourceDiagObj.bottom;
            int sourceTop = (sourceDiagObj == null) ? 0 : sourceDiagObj.top;

            int targetLeft = (targetDiagObj == null) ? 0 : targetDiagObj.left;
            int targetRight = (targetDiagObj == null) ? 0 : targetDiagObj.right;
            int targetBottom = (targetDiagObj == null) ? 0 : targetDiagObj.bottom;
            int targetTop = (targetDiagObj == null) ? 0 : targetDiagObj.top;

            EA.DiagramObject tggLinkDiagObj = currentDiagram.DiagramObjects.AddNew("", "") as EA.DiagramObject;
            tggLinkDiagObj.ElementID = tggCorrespondenceObject.ElementID;
            tggLinkDiagObj.Update();
            tggLinkDiagObj.left = (sourceLeft + targetLeft) / 2;
            tggLinkDiagObj.right = (sourceRight + targetRight) / 2;
            tggLinkDiagObj.bottom = (sourceBottom + targetBottom) / 2;
            tggLinkDiagObj.top = (sourceTop + targetTop) / 2;
            tggLinkDiagObj.Update();
            currentDiagram.DiagramObjects.Refresh();
        }

        private Boolean createCorrespondenceObjectWithLinks(EA.Repository Repositorya, EA.EventProperties Info)
        {
            DateTime t1 = DateTime.Now;
            SQLRepository Repository = new SQLRepository(Repositorya, false);
            EA.Diagram curDiagram = Repository.GetCurrentDiagram();

            int tggPackageId = EAUtil.getOutermostPackage(curDiagram, Repository).PackageID;

            BindingOperator newBindingOperator = BindingOperator.CHECK_ONLY;

            SQLElement clientOv = Repository.GetElementByID(int.Parse(Info.Get("ClientID").Value.ToString()));
            SQLElement supplierOv = Repository.GetElementByID(int.Parse(Info.Get("SupplierID").Value.ToString()));

            EA.Element ruleClass = Repository.GetOriginalRepository().GetElementByID(clientOv.ParentID);

            SQLElement clientClassifier = Repository.GetElementByID(clientOv.ClassifierID);
            SQLElement supplierClassifier = Repository.GetElementByID(supplierOv.ClassifierID);

            //compute domains of source and target classifiers
            DomainType clientDomain = TGGModelingUtil.getDomainOfObjectVariable(Repository, new TGGObjectVariable(clientOv, Repository));
            DomainType supplierDomain = TGGModelingUtil.getDomainOfObjectVariable(Repository, new TGGObjectVariable(supplierOv, Repository));

            //get possible correspondence classes with connectors
            Dictionary<int, List<SQLConnector>> possibleCorrespondencesLinkWithConnectors = computeCorrespondencesWithLinks(clientClassifier, supplierClassifier, Repository, tggPackageId);

            //check for existing bindingOperator
            SQLTaggedValue bindingOperatorTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(clientOv, ObjectVariable.BindingOperatorTaggedValueName);
            if (bindingOperatorTag != null)
                newBindingOperator = (BindingOperator)Enum.Parse(typeof(BindingOperator), bindingOperatorTag.Value.ToUpper());

            //create new correspondence ov
            EA.Element tggCorrespondenceObject = ruleClass.Elements.AddNew("", Main.EAObjectType) as EA.Element;
            tggCorrespondenceObject.Update();

            //dont know what this line does :)
            EA_OnNotifyContextItemModified(Repository.GetOriginalRepository(), tggCorrespondenceObject.ElementGUID, EA.ObjectType.otElement);

            //create connector from correspondence object to client ov
            EA.Connector connectorToClient = tggCorrespondenceObject.Connectors.AddNew("", ECOREModelingMain.EReferenceConnectorType) as EA.Connector;
            connectorToClient.SupplierID = clientOv.ElementID;

            connectorToClient.Direction = "Unspecified";
            connectorToClient.Update();
            tggCorrespondenceObject.Connectors.Refresh();

            //create connector from correspondence object to supplier ov
            EA.Connector connectorToTarget = tggCorrespondenceObject.Connectors.AddNew("", ECOREModelingMain.EReferenceConnectorType) as EA.Connector;
            connectorToTarget.SupplierID = supplierOv.ElementID;
            connectorToTarget.Direction = "Unspecified";
            connectorToTarget.Update();
            tggCorrespondenceObject.Connectors.Refresh();

            createCorrespondenceDiagramObject(curDiagram, Repository.GetOriginalRepository(), clientOv.getRealElement(), supplierOv.getRealElement(), tggCorrespondenceObject);

            SelectCorrespondenceObject selectCorr = new SelectCorrespondenceObject(possibleCorrespondencesLinkWithConnectors, Repository.GetElementByID(tggCorrespondenceObject.ElementID), Repository, newBindingOperator, clientClassifier, supplierClassifier, clientOv, supplierOv);
            if (selectCorr.DialogResult == DialogResult.Cancel)
                return true;

            //compute correct linkDialogueEntries so the rolenames can be set correctly

            LinkDialogueEntry sourceEntry = computeLinkDialogEntry("source", possibleCorrespondencesLinkWithConnectors[selectCorr.selectedCorrespondenceLinkId]);
            LinkDialogueEntry targetEntry = computeLinkDialogEntry("target", possibleCorrespondencesLinkWithConnectors[selectCorr.selectedCorrespondenceLinkId]);

            TGGLinkVariable sourceLv = new TGGLinkVariable(Repository.GetConnectorByID(connectorToClient.ConnectorID), Repository);
            sourceLv.BindingOperator = newBindingOperator;

            TGGLinkVariable targetLv = new TGGLinkVariable(Repository.GetConnectorByID(connectorToTarget.ConnectorID), Repository);
            targetLv.BindingOperator = newBindingOperator;

            if (clientDomain == DomainType.SOURCE && supplierDomain == DomainType.TARGET)
            {
                sourceLv.linkDialogueEntry = sourceEntry;
                targetLv.linkDialogueEntry = targetEntry;
            }
            else if (clientDomain == DomainType.TARGET && supplierDomain == DomainType.SOURCE)
            {
                sourceLv.linkDialogueEntry = targetEntry;
                targetLv.linkDialogueEntry = sourceEntry;
            }
            else
            {
                sourceLv.linkDialogueEntry = sourceEntry;
                targetLv.linkDialogueEntry = targetEntry;
            }
            sourceLv.saveTreeToEATaggedValue(true);
            targetLv.saveTreeToEATaggedValue(true);
            

            Repository.ReloadDiagram(curDiagram.DiagramID);





            return true;
        }

        private LinkDialogueEntry computeLinkDialogEntry(String sourceOrTarget, List<SQLConnector> refs)
        {
            SQLConnector foundRef = null;
            foreach (SQLConnector con in refs)
            {
                if (con.ClientEnd.Role == sourceOrTarget || con.SupplierEnd.Role == sourceOrTarget)
                    foundRef = con;
            }
            LinkDialogueEntry newEntry = new LinkDialogueEntry();
            newEntry.direction = LinkDialogueEntryDirection.RIGHT;
            newEntry.supplierRoleName = sourceOrTarget;
            newEntry.CorrespondingConnectorGuid = foundRef.ConnectorGUID;
            return newEntry;
        }

        private static Boolean createTGGLink(EA.Repository Repository, EA.EventProperties Info, TGGModelingMain modelingMain)
        {
            SQLRepository sqlRep = new SQLRepository(Repository, false);

            int clientID = int.Parse(Info.Get("ClientID").Value.ToString());
            int supplierID = int.Parse(Info.Get("SupplierID").Value.ToString());

            SQLElement client = sqlRep.GetElementByID(clientID);
            SQLElement supplier = sqlRep.GetElementByID(supplierID);

            DomainType clientDomain = TGGModelingUtil.getDomainOfEClass(sqlRep, client);
            DomainType supplierDomain = TGGModelingUtil.getDomainOfEClass(sqlRep, supplier);

            SQLElement source = null;
            SQLElement target = null;

            if (clientDomain == DomainType.SOURCE || supplierDomain == DomainType.TARGET)
            {
                source = client;
                target = supplier;
            }
            else if (clientDomain == DomainType.TARGET || supplierDomain == DomainType.SOURCE)
            {
                source = supplier;
                target = client;
            }
            else
            {
                //could not define the domains, this is an arbitrary choice
                source = client;
                target = supplier;
            }


            EA.Diagram curDiagram = Repository.GetCurrentDiagram();
            EA.Package tggPackage = Repository.GetPackageByID(curDiagram.PackageID);

            EA.Element tggLink = tggPackage.Elements.AddNew(source.Name + "To" + target.Name, Main.EAClassType) as EA.Element;
            tggLink.StereotypeEx = TGGModelingMain.TggCorrespondenceTypeStereotype;
            tggLink.Update();
            modelingMain.EA_OnNotifyContextItemModified(Repository, tggLink.ElementGUID, EA.ObjectType.otElement);

            EA.Connector connectorToSource = tggLink.Connectors.AddNew("", ECOREModelingMain.EReferenceConnectorType) as EA.Connector;
            connectorToSource.SupplierID = source.ElementID;
            connectorToSource.Direction = Main.EASourceTargetDirection;
            connectorToSource.SupplierEnd.Role = "source";
            connectorToSource.SupplierEnd.Navigable = "Navigable";
            connectorToSource.SupplierEnd.Cardinality = "1";
            connectorToSource.SupplierEnd.IsNavigable = true;

            connectorToSource.Update();
            tggLink.Connectors.Refresh();
            modelingMain.EA_OnNotifyContextItemModified(Repository, connectorToSource.ConnectorGUID, EA.ObjectType.otConnector);

            EA.Connector connectorToTarget = tggLink.Connectors.AddNew("", ECOREModelingMain.EReferenceConnectorType) as EA.Connector;
            connectorToTarget.SupplierID = target.ElementID;
            connectorToTarget.Direction = Main.EASourceTargetDirection;
            connectorToTarget.SupplierEnd.Role = "target";
            connectorToTarget.SupplierEnd.Cardinality = "1";
            connectorToTarget.SupplierEnd.Navigable = "Navigable";
            connectorToTarget.SupplierEnd.IsNavigable = true;

            connectorToTarget.Update();
            tggLink.Connectors.Refresh();
            modelingMain.EA_OnNotifyContextItemModified(Repository, connectorToTarget.ConnectorGUID, EA.ObjectType.otConnector);

            //create a new diagram object
            Repository.SaveDiagram(curDiagram.DiagramID);
            EA.DiagramObject sourceDiagObj = null;
            EA.DiagramObject targetDiagObj = null;

            curDiagram.DiagramObjects.Refresh();
            foreach (EA.DiagramObject diagObj in curDiagram.DiagramObjects)
            {
                if (diagObj.ElementID == source.ElementID)
                    sourceDiagObj = diagObj;
                else if (diagObj.ElementID == target.ElementID)
                    targetDiagObj = diagObj;

                if (sourceDiagObj != null && targetDiagObj != null)
                    continue;
            }

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

            Repository.ReloadDiagram(curDiagram.DiagramID);

            tggPackage.Elements.Refresh();


            return true;
        }


        public bool EA_OnPreNewConnector(EA.Repository Repository, EA.EventProperties Info)
        {
            String type = Info.Get("Type").Value.ToString();
            String stereoType = Info.Get("Stereotype").Value.ToString();
            EA.Diagram curDiag = Repository.GetCurrentDiagram();
            int clientId = int.Parse(Info.Get("ClientID").Value.ToString());
            int supplierId = int.Parse(Info.Get("SupplierID").Value.ToString());

            if (TGGModelingMain.TggSchemaDiagramMetatype.Contains(curDiag.MetaType))
            {
                if (type == "Dependency" && stereoType == "tggLink")
                {
                    createTGGLink(Repository, Info, this);
                    return false;
                }
                else if (type == Main.EAAssociationType)
                {
                    //EA.Element client = Repository.GetElementByID(clientId);
                    EA.Element supplier = Repository.GetElementByID(supplierId);
                    if (supplier.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype)
                        return false;
                }
            }
            else if (TGGModelingMain.TggRuleDiagramMetatype.Contains(curDiag.MetaType))
            {
                if (type == Main.EAAssociationType && stereoType == "tggCorrLink")
                {
                    createCorrespondenceObjectWithLinks(Repository, Info);
                    return false;
                }
            }

            return true;
        }

        public bool EA_OnPostNewConnector(SQLRepository sqlRepository, EA.Connector actCon, EA.Diagram currentDiagram)
        {
            try
            {
                if (TGGModelingMain.TggSchemaDiagramMetatype.Contains(currentDiagram.MetaType))
                {
                    if (actCon.Type == ECOREModelingMain.EReferenceConnectorType)
                    {
                        onPostEReferenceConnector(sqlRepository, actCon);
                    }
                }
                else if (TGGModelingMain.TggRuleDiagramMetatype.Contains(currentDiagram.MetaType))
                {
                    if (actCon.Stereotype == TGGModelingMain.TggLinkVariableStereotype || actCon.Stereotype == SDMModelingMain.LinkVariableStereotype)
                    {
                        onPostLinkVariableConnector(sqlRepository, actCon);
                    }
                }
                else if (TGGModelingMain.TggRulesDiagramMetatype.Contains(currentDiagram.MetaType))
                {
                    if (actCon.Stereotype == TGGModelingMain.TGGkernelMorphismStereotype)
                    {
                        onPostKernelMorphismConnector(sqlRepository, actCon);
                    }
                }
            }
            catch
            {

            }
            return true;
        }

        #region on post connector methods

        private void onPostKernelMorphismConnector(SQLRepository sqlRepository, EA.Connector actCon)
        {
            //set the arrow as this somehow does not work wtih the stereotype in the UML-Profile
            actCon.Direction = "Source -> Destination";
            actCon.Update();
        }
        private void onPostEReferenceConnector(SQLRepository sqlRepository, EA.Connector actCon)
        {
            ecoreModeling.EA_OnPostNewConnector(sqlRepository, actCon, sqlRepository.GetCurrentDiagram());
        }

        private void onPostLinkVariableConnector(SQLRepository sqlRepository, EA.Connector actCon2)
        {
            //save initial data for linkVariable
            SQLConnector actCon = sqlRepository.GetConnectorByID(actCon2.ConnectorID);
            TGGLinkVariable linkVariable = new TGGLinkVariable(actCon, sqlRepository);
            SQLElement newClient = sqlRepository.GetElementByID(actCon.ClientID);
            SQLElement newSupplier = sqlRepository.GetElementByID(actCon.SupplierID);
            String clientObjMod = EAEcoreAddin.Util.EAUtil.findTaggedValue(newClient, ObjectVariable.BindingOperatorTaggedValueName).Value;
            String supplierObjMod = EAEcoreAddin.Util.EAUtil.findTaggedValue(newSupplier, ObjectVariable.BindingOperatorTaggedValueName).Value;
            BindingSemantics bindingSemantics = BindingSemantics.MANDATORY;
            BindingOperator bindingOperator = BindingOperator.CHECK_ONLY;

            if (supplierObjMod == "create" || clientObjMod == "create")
            {
                bindingSemantics = BindingSemantics.MANDATORY;
                bindingOperator = BindingOperator.CREATE;
            }
            else if (supplierObjMod == "destroy" || clientObjMod == "destroy")
            {
                bindingSemantics = BindingSemantics.MANDATORY;
                bindingOperator = BindingOperator.DESTROY;
            }
            else if (supplierObjMod == "check_only" && clientObjMod == "check_only")
            {
                bindingSemantics = BindingSemantics.MANDATORY;
                bindingOperator = BindingOperator.CHECK_ONLY;
            }
            else if (supplierObjMod == "create" && clientObjMod == "destroy" || supplierObjMod == "destroy" && clientObjMod == "create")
            {
                bindingSemantics = BindingSemantics.MANDATORY;
                bindingOperator = BindingOperator.CHECK_ONLY;
            }

            linkVariable.BindingOperator = bindingOperator;
            linkVariable.BindingSemantics = bindingSemantics;

            this.LinkVariableDialog = new LinkVariablePropertiesForm(linkVariable, sqlRepository, false);
            EAEcoreAddin.Util.EAUtil.setTaggedValue(sqlRepository, actCon.getRealConnector(), Main.MoflonVerboseTaggedValueName, Main.FalseValue);

        }

      
        #endregion

        public void EA_OnPostNewDiagram(EA.Repository Repository, EA.EventProperties Info)
        {
            int diagramID = int.Parse(Info.Get(0).Value.ToString());
            SQLRepository sqlRep = new SQLRepository(Repository, false);
            EA.Diagram diagram = Repository.GetDiagramByID(diagramID);
            String diagramMetatype = diagram.MetaType;
            if (TGGModelingMain.TggSchemaDiagramMetatype.Contains(diagram.MetaType))
            {
                SQLPackage newTGGProject = sqlRep.GetPackageByID(diagram.PackageID);
                if (EAUtil.packageIsModel(newTGGProject, sqlRep) && newTGGProject.Diagrams.Count == 1 && newTGGProject.getRealPackage().StereotypeEx == "")
                {
                    NewTGGProjectDialog tggDialog = new NewTGGProjectDialog(sqlRep, newTGGProject);
                    tggDialog.ShowDialog();
                }
            }
        }



        public void EA_OnPostNewDiagramObject(EA.Repository Repository, EA.EventProperties Info)
        {
            EA.Diagram currentDiagram = Repository.GetCurrentDiagram();
            Repository.SaveDiagram(currentDiagram.DiagramID);

            int objectID = int.Parse(Info.Get(0).Value.ToString());
           // EA.Element newElement = Repository.GetElementByID(objectID);
            

        }

        public void EA_OnPostOpenDiagram(EA.Repository repository, EA.Diagram openedDiagram)
        {
            if (openedDiagram.MetaType == TGGModelingMain.TggRuleDiagramMetatype[0])
            {
                SDMModelingMain.createAnchorElementIfNecessary(repository, openedDiagram, true);
            }

            else if (openedDiagram.MetaType == TGGModelingMain.TggRulesDiagramMetatype[0])
            {
                SDMModelingMain.createAnchorElementIfNecessary(repository, openedDiagram, true);
            }
        }

        public bool EA_OnPreNewElement(EA.Repository Repository, EA.EventProperties Info)
        {
        	return true;

        }

        public bool EA_OnPostNewElement(SQLRepository sqlRepository, EA.Element newElement, EA.Diagram currentDiagram)
        {
            EA.Repository repository = sqlRepository.GetOriginalRepository();

            try
            {

                //a correspondence is created as an instance of CorrespondenceType
                //Therefore its stereotype is at first time "CorrespondenceType"
                //but we want to change it to "Correspondence" 

                if (TGGModelingMain.TggRuleDiagramMetatype.Contains(currentDiagram.MetaType))
                {
                    sqlRepository.SuppressEADialogs = true;

                    if (newElement.Type == Main.EAObjectType)
                    {
                        TGGObjectVariable tggOv = null;

                        //drag & drop is used
                        if (newElement.ClassifierID != 0)
                        {
                            SQLElement classifier = sqlRepository.GetElementByID(newElement.ClassifierID);
                            if (classifier.Stereotype == ECOREModelingMain.EClassStereotype)
                            {
                                tggOv = new TGGObjectVariable(sqlRepository.GetElementByID(newElement.ElementID), sqlRepository);
                                tggOv.Name = classifier.Name.ToLower().Substring(0, 1) + classifier.Name.Substring(1, classifier.Name.Length - 1);
                                tggOv.BindingOperator = BindingOperator.CREATE;
                                this.ObjectVariableDialog = new OvDialog(sqlRepository, tggOv);
                                ObjectVariableDialog.ShowDialog();

                            }
                            else if (classifier.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype)
                            {
                                tggOv = new TGGCorrespondence(sqlRepository.GetElementByID(newElement.ElementID), sqlRepository);
                                tggOv.Name = classifier.Name.ToLower().Substring(0, 1) + classifier.Name.Substring(1, classifier.Name.Length - 1);
                                tggOv.BindingOperator = BindingOperator.CREATE;
                                this.ObjectVariableDialog = new OvDialog(sqlRepository, tggOv);
                                ObjectVariableDialog.ShowDialog();
                            }
                        }
                        else
                        {
                            //toolbox is used
                            if (newElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype)
                            {
                                tggOv = new TGGObjectVariable(sqlRepository.GetElementByID(newElement.ElementID), sqlRepository);
                                this.ObjectVariableDialog = new OvDialog(sqlRepository, tggOv);
                                ObjectVariableDialog.ShowDialog();
                            }
                            else if (newElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
                            {
                                tggOv = new TGGCorrespondence(sqlRepository.GetElementByID(newElement.ElementID), sqlRepository);
                                this.ObjectVariableDialog = new OvDialog(sqlRepository, tggOv);
                                this.ObjectVariableDialog.ShowDialog();
                            }
                            else if (newElement.Stereotype == SDMModelingMain.CSPInstanceStereotype)
                            {
                                TGG tggPackage = new TGG(sqlRepository, EAUtil.getOutermostPackage(newElement, sqlRepository));
                                tggPackage.loadTreeFromTaggedValue();

                                CSPInstanceDialog cspInstanceDialog = new CSPInstanceDialog(sqlRepository, sqlRepository.GetElementByID(newElement.ElementID), new TGGCSPController(tggPackage));
                                cspInstanceDialog.ShowDialog();
                            }
                        }


                        
                    }
                }
                else if (TGGModelingMain.TggRulesDiagramMetatype.Contains(currentDiagram.MetaType))
                {
                    sqlRepository.SuppressEADialogs = true;
                    if (newElement.Type == Main.EAClassType && ( newElement.Stereotype == TGGModelingMain.TggRuleStereotype))
                    {
                        newElement = EAUtil.sqlEAObjectToOriginalObject(sqlRepository, newElement) as EA.Element;

                        EA.Diagram ruleDiagram = newElement.Diagrams.AddNew(newElement.Name, TGGModelingMain.TggRuleDiagramMetatype[0]) as EA.Diagram;
                        ruleDiagram.Update();

                        EA_OnNotifyContextItemModified(sqlRepository.GetOriginalRepository(), newElement.ElementGUID, EA.ObjectType.otElement);
                    }
                }
                else if (TGGModelingMain.TggPatternsDiagramMetatype.Contains(currentDiagram.MetaType))
                {
                    sqlRepository.SuppressEADialogs = true;
                }

            }
            catch
            {
            }
            return true;
        }




    }
}

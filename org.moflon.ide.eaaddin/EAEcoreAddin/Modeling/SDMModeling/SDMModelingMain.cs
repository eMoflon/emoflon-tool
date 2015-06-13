using System;
using System.Windows.Forms;
using System.IO;
using System.Reflection;
using System.Resources;
using System.Linq;
using System.Runtime.InteropServices;
using System.Collections;
using System.Threading;
using System.Collections.Generic;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;

using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.LinkVariableDialog;
using EAEcoreAddin.Modeling.SDMModeling.ActivityNodeDialog;
using EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog;
using EAEcoreAddin.Modeling.SDMModeling.ActivityEdgeDialog;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog;
using EAEcoreAddin.Modeling.SDMModeling.Gui;
using EAEcoreAddin.ControlPanel.Navigation;
using EAEcoreAddin.Modeling.SDMModeling.Gui.CreateSDMDialog;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Modeling.CSP.Gui;
using EAEcoreAddin.Modeling.CSP;



namespace EAEcoreAddin.Modeling.SDMModeling
{
    public class SDMModelingMain
    {


        public static readonly String[] SdmDiagramMetatype = { "eMoflon SDM Diagrams::SDM Diagram",
                                                                 "ESDM_Diagram::ESDM Diagram" };

        public static readonly String ObjectVariableStereotype = "ObjectVariable";
        public static readonly String ObjectVariableQuickCreateStereotype = "ObjectVariableQuickCreate";
        public static readonly String LinkVariableStereotype = "LinkVariable";
        public static readonly String BindingExpressionLinkStereotype = "BindingExpressionLink";
        public static readonly String ActivityNodeMetatype = "ActivityNode";
        public static readonly String ActivityEdgeStereotype = "";
        public static readonly String ActivityType = "Activity";
        public static readonly String SDMAnchorStereotype = "SDMAnchor";
        
        public static readonly String StoryNodeStereotype = "StoryNode";
        public static readonly String StatementNodeStereotype = "StatementNode";
        public static readonly String StartNodeStereotype = "StartNode";
        public static readonly String StopNodeStereotype = "StopNode";
        public static readonly String SdmContainerStereotype = "SDM Activity";



        public static readonly String SDMAnchorDiagramIDTaggedValueName = "AnchorDiagramID";
        public static readonly String SDMContainerAssociatedMethodTaggedValueName = "associatedMethod";
        public static readonly String EvacuatedTaggedValueName = "evacuated";
        public static readonly string CSPInstanceStereotype = "CSPInstance";
        
        
        
        public bool EA_OnPreNewElement(EA.Repository Repository, EA.EventProperties Info)
        {

            return true;    
        }

        public void EA_OnNotifyContextItemModified(EA.Repository Repository, String GUID, EA.ObjectType ot)
        {
            SQLRepository sqlRepository = new SQLRepository(Repository, false);



            if (ot == EA.ObjectType.otElement)
            {
                EA.Element eaElement2 = Repository.GetElementByGuid(GUID);
                SQLElement sqlElement = sqlRepository.GetElementByID(eaElement2.ElementID);
                if (sqlElement.Stereotype.ToLower() == SDMModelingMain.SdmContainerStereotype.ToLower())
                {
                    Activity activity = new Activity(sqlElement, sqlRepository);
                    activity.saveTreeToEATaggedValue(true);
                }
                else if (sqlElement.MetaType == SDMModelingMain.ActivityNodeMetatype)
                {
                    ActivityNode aNode = SDMUtil.createCorrectActivityNode(sqlRepository, sqlElement);
                    if (aNode != null)
                    {
                        aNode.loadTreeFromTaggedValue();
                        aNode.Name = sqlElement.Name;
                        aNode.saveTreeToEATaggedValue(true);
                    }
                }
            }
        }
        
        /// <summary>
        /// Defines what happens if the user doubleclicks on SDM related Elements.
        /// </summary>
        /// <param name="repository"></param>
        /// <param name="GUID"></param>
        /// <param name="ot"></param>
        /// <returns>True if the EA Dialogs should not be openend. False if the standard EA Dialogs should be opened.
        /// </returns>
        public bool EA_OnContextItemDoubleClicked(EA.Repository repository, String GUID, EA.ObjectType ot)
        {
            SQLRepository sqlRepository = new SQLRepository(repository, false);
            EA.Diagram actDiag = repository.GetCurrentDiagram();
            SQLMethod meth = sqlRepository.GetMethodByGuid(GUID);
            SQLElement elem = sqlRepository.GetElementByGuid(GUID);
            SQLConnector con = sqlRepository.GetConnectorByGuid(GUID);
            
            if (actDiag != null)
            {
                if (ECOREModelingMain.EcoreDiagramMetatype.Contains(actDiag.MetaType) && ot == EA.ObjectType.otMethod)
                {
                    SQLElement methParent;
                    SQLElement container;

                    if (!openSelectedMethodSDM(actDiag, meth, sqlRepository, out methParent, out container))
                    {

                        if (methParent.Stereotype == ECOREModelingMain.EClassStereotype)
                        {

                            CreateSDMDialog createSdmDialog = new CreateSDMDialog();
                            if (createSdmDialog.ShowDialog() == DialogResult.OK)
                            {
                                createStoryDiagram(meth, repository, actDiag);
                            }
                            return true;
                        }
                    }
                    else
                    {
                        return true;
                    }
                }
                else if (SdmDiagramMetatype.Contains(actDiag.MetaType))
                {
                    repository.SaveDiagram(actDiag.DiagramID);
                    if (elem != null)
                    {
                        if (elem.Stereotype == SDMModelingMain.ObjectVariableStereotype)
                        {
                            ObjectVariable ov = new ObjectVariable(elem, sqlRepository);
                            OvDialog objectVariableDialogue = new OvDialog(sqlRepository, ov);
                            objectVariableDialogue.ShowDialog();
                            return true;
                        }
                             
                        else if (elem.Stereotype == SDMModelingMain.StoryNodeStereotype || elem.Stereotype == SDMModelingMain.StatementNodeStereotype)
                        {
                            
                            SQLTaggedValue extractedTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(elem, Main.MoflonExtractedStoryPatternTaggedValueName);
                            if (extractedTag == null)
                            {
                                SQLTaggedValue oldExtractedTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(elem, "evacuated");
                                if (oldExtractedTag != null)
                                    EAEcoreAddin.Util.EAUtil.setTaggedValue(sqlRepository, elem.getRealElement(), Main.MoflonExtractedStoryPatternTaggedValueName, oldExtractedTag.Value);
                                else
                                    EAEcoreAddin.Util.EAUtil.setTaggedValue(sqlRepository, elem.getRealElement(), Main.MoflonExtractedStoryPatternTaggedValueName, "false");
                                extractedTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(elem, Main.MoflonExtractedStoryPatternTaggedValueName);                         
                            }

                            if (extractedTag != null && extractedTag.Value == Main.TrueValue)
                            {
                                SQLDiagram diagram = (SQLDiagram)elem.Diagrams.GetAt(0);
                                Navigator.addAnchorEntry(diagram.DiagramID, actDiag.DiagramID);
                                repository.OpenDiagram(diagram.DiagramID);
                            }
                            else
                            {                                
                                if (elem.Stereotype == SDMModelingMain.StoryNodeStereotype)
                                {
                                    ActivityNodeTabsForm activityNodeDialog = new ActivityNodeTabsForm(new StoryNode(sqlRepository, elem), sqlRepository);
                                }
                                else if (elem.Stereotype == SDMModelingMain.StatementNodeStereotype)
                                {
                                    ActivityNodeTabsForm activityNodeDialog = new ActivityNodeTabsForm(new StatementNode(sqlRepository, elem), sqlRepository);
                                }                                                    
                            }
                            return true;
                        }
                            
                        else if (elem.Stereotype == SDMModelingMain.StopNodeStereotype)       
                        {
                            StopNode stopNode = new StopNode(sqlRepository, elem);
                            StopNodeForm sNodeForm = new StopNodeForm(stopNode, sqlRepository);
                            return true;
                        }

                        else if (elem.Stereotype == TGGModelingMain.CSPConstraintStereotype)
                        {
                            SDMCSPDialog sdmCspDialog = new SDMCSPDialog(elem, sqlRepository);
                            sdmCspDialog.ShowDialog();

                            return true;
                        }
                    }
                    if (con != null)
                    {
                        
                        if (con.Stereotype == SDMModelingMain.LinkVariableStereotype)
                        {                         
                            LinkVariablePropertiesForm linkVariableDialogue = new LinkVariablePropertiesForm(new LinkVariable(con,sqlRepository), sqlRepository, true);
                            return true;   
                        }
                        else if (con.Type == Main.EAControlFlowType)
                        {
                            ActivityEdge activityEdge = new ActivityEdge(sqlRepository, con);
                            ActivityEdgePropertiesForm activityEdgeDialog = new ActivityEdgePropertiesForm(activityEdge, repository);
                            return true;
                       }
                        else if(con.Stereotype == SDMModelingMain.BindingExpressionLinkStereotype)
                        {
                            SQLElement targetElement = sqlRepository.GetElementByID(con.SupplierID);
                            ObjectVariable ov = new ObjectVariable(targetElement, sqlRepository);

                            OvDialog ovDialog = new OvDialog(sqlRepository, ov);
                            ovDialog.showTab(2);
                            ovDialog.ShowDialog();
                            return true;
                        }
                    }
                    if (ot == EA.ObjectType.otDiagram)
                    {
                        EA.Diagram diag = (EA.Diagram)repository.GetDiagramByGuid(GUID);
                        EA.Element activity = null;
                        SQLTaggedValue evacuatedTag = null;
                        if (diag.ParentID != 0)
                        {
                            activity = repository.GetElementByID(diag.ParentID);
                            evacuatedTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(sqlRepository.GetElementByID(activity.ElementID), Main.MoflonExtractedStoryPatternTaggedValueName);
                        }
                        
                        if (evacuatedTag != null && evacuatedTag.Value == Main.TrueValue)
                        {
                            StoryNode activityNode = new StoryNode(sqlRepository, sqlRepository.GetElementByID(activity.ElementID));
                            ActivityNodeTabsForm activityNodeDialog = new ActivityNodeTabsForm(activityNode, sqlRepository);
                            return true;
                        }
                        
                    }
                    //sqlRepository.ReloadDiagram(actDiag.DiagramID);
                }
                else if (TGGModelingMain.TggRulesDiagramMetatype.Contains(actDiag.MetaType) && ot == EA.ObjectType.otMethod)
                {
                    EA.TaggedValue aTag = null;
                    EA.Element methParent = repository.GetElementByID(meth.ParentID); 
                    EA.Element container = null;
                    foreach (EA.Element sdmContainer in methParent.Elements)
                    {
                        if (sdmContainer.Stereotype == SDMModelingMain.SdmContainerStereotype)
                        {
                            aTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(sdmContainer, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName);
                            if (aTag.Value == meth.MethodGUID.ToString())
                            {
                                container = sdmContainer;
                                break;
                            }
                        }
                    }
                    EA.Diagram sdmDiagg = null;
                    if (container != null)
                    {
                        foreach (EA.Diagram sdmDiag in container.Diagrams)
                        {
                            sdmDiagg = sdmDiag;
                            repository.OpenDiagram(sdmDiag.DiagramID);
                            return true;
                        }
                    }
                    if (sdmDiagg == null && container == null)  // no SDM Diagram exists
                    {
                        if (methParent.Stereotype == TGGModelingMain.TggRuleStereotype )
                        {
                            createStoryDiagram(meth, repository, actDiag);
                            return true;
                        }   
                    }                      
                }
            }
             
            return false;
        }

        public static bool openSelectedMethodSDM(EA.Diagram actDiag, SQLMethod meth, SQLRepository sqlRepository, out SQLElement methParent, out SQLElement container)
        {
            SQLTaggedValue aTag = null;
            methParent = sqlRepository.GetElementByID(meth.ParentID);
            container = null;
            foreach (SQLElement sdmContainer in methParent.Elements)
            {
                aTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(sdmContainer, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName);
                if (aTag != null)
                {
                    if (aTag.Value == meth.MethodGUID.ToString())
                    {
                        container = sdmContainer;
                        break;
                    }
                }
            }
            SQLDiagram sdmDiagg = null;
            if (container != null)
            {
                foreach (SQLDiagram sdmDiag in container.Diagrams)
                {
                    sdmDiagg = sdmDiag;
                    Navigator.addAnchorEntry(sdmDiag.DiagramID, actDiag.DiagramID);
                    sqlRepository.OpenDiagram(sdmDiag.DiagramID);

                    setAnchorElementTags(sqlRepository, actDiag, container);

                    return true;
                }
            }
            return false;
        }

        /// <summary>
        /// Checks if the new created Element is an SDM related Element.
        /// Opens the custom User Dialogues or sets default values if it is necessary.
        /// </summary>
        /// <param name="sqlRep"></param>
        /// <param name="Info"></param>
        /// <returns>if newElement is still valid</returns>
        public bool EA_OnPostNewElement(SQLRepository sqlRep, EA.Element newElement, EA.Diagram currentDiagram)
        {
            if (SdmDiagramMetatype.Contains(currentDiagram.MetaType))
            {
                sqlRep.SuppressEADialogs = true;
                if (newElement.Stereotype == SDMModelingMain.ObjectVariableQuickCreateStereotype)
                {
                    newElement.Stereotype = SDMModelingMain.ObjectVariableStereotype;
                    ObjectVariable ov = new ObjectVariable(sqlRep.GetElementByID(newElement.ElementID), sqlRep);
                    OvDialog objectVariableDialogue = new OvDialog(sqlRep, ov);

                    List<SQLElement> validEClassesTemp = new List<SQLElement>();
                    List<EClass> validEClasses = new List<EClass>();
                    SQLElement sourceObjectVariable = sqlRep.GetElementByGuid(Main.lastContextItemGUID);
                    SQLElement classifier = sqlRep.GetElementByID(sourceObjectVariable.ClassifierID);

                    foreach (SQLElement baseclass in EAUtil.getBaseClasses(classifier))
                    {
                        foreach (SQLConnector connector in baseclass.Connectors)
                        {
                            if (connector.Type == ECOREModelingMain.EReferenceConnectorType)
                            {
                                EReference eRef = new EReference(connector, sqlRep);
                                eRef.loadTreeFromTaggedValue();
                                SQLElement otherEnd = null;
                                if (connector.ClientID == baseclass.ElementID && eRef.SupplierEnd.Navigable)
                                {
                                    otherEnd = sqlRep.GetElementByID(connector.SupplierID);
                                }
                                else if(connector.SupplierID == baseclass.ElementID && eRef.ClientEnd.Navigable)
                                {
                                    otherEnd = sqlRep.GetElementByID(connector.ClientID);
                                }
                                
                                if (otherEnd != null && otherEnd.Stereotype == ECOREModelingMain.EClassStereotype)
                                {
                                    validEClassesTemp.Add(otherEnd);
                                }
                            }
                        }
                    }


                    objectVariableDialogue.ovPropertiesControl.comboTypes.clearClassifiers();
                    foreach (SQLElement tempClassifier in validEClassesTemp)
                    {
                        objectVariableDialogue.ovPropertiesControl.comboTypes.addClassifier(new EClass(tempClassifier,sqlRep));
                    }
                    objectVariableDialogue.ovPropertiesControl.comboTypes.printClassifiers();

                    objectVariableDialogue.ShowDialog();
                }

                else if (newElement.Stereotype == SDMModelingMain.ObjectVariableStereotype || (newElement.Type == Main.EAObjectType && newElement.Stereotype == ""))
                {
                    Boolean dragAndDropUsed = newElement.ClassifierID != 0;

                    if (dragAndDropUsed && !classifierIsValid(sqlRep, newElement))
                    {
                        Main.addToDeleteGUIDQueue(newElement.ElementGUID);
                        return false;
                    }
                    EA.DiagramObject diagObj = EAEcoreAddin.Util.EAUtil.findDiagramObject(sqlRep, newElement, currentDiagram);

                    foreach (EA.DiagramObject diagObjs in currentDiagram.DiagramObjects)
                    {
                        if (diagObj.left > (diagObjs.left - 45) && diagObj.right < (diagObjs.right + 45) && diagObj.top < (diagObjs.top + 30) && diagObj.bottom > (diagObjs.bottom - 30))
                        {
                            if (newElement.ElementID != diagObjs.ElementID)
                            {
                                EA.Element realElement = sqlRep.GetOriginalRepository().GetElementByID(newElement.ElementID);
                                realElement.ParentID = diagObjs.ElementID;
                                realElement.Update();
                                break;
                            }
                        }
                    }

                    //recompute newElement because of drag and drop changes to the element
                    if(dragAndDropUsed)
                        newElement = sqlRep.GetOriginalRepository().GetElementByID(newElement.ElementID);
                    newElement.Stereotype = SDMModelingMain.ObjectVariableStereotype;
                    ObjectVariable ov = new ObjectVariable(sqlRep.GetElementByID(newElement.ElementID), sqlRep);
                    OvDialog objectVariableDialogue = new OvDialog(sqlRep, ov);
                    objectVariableDialogue.ShowDialog();
  
                }
                

                else if (newElement.Stereotype == SDMModelingMain.StoryNodeStereotype)
                {
                    //save initial data for storyNode
                    EAEcoreAddin.Util.EAUtil.setTaggedValue(sqlRep, newElement, Main.MoflonExtractedStoryPatternTaggedValueName, "false");
                    StoryNode storyNode = new StoryNode(sqlRep, sqlRep.GetElementByID(newElement.ElementID));
                    storyNode.ForEach = false;
                    storyNode.saveTreeToEATaggedValue(true);
                }

                else if (newElement.Stereotype == SDMModelingMain.StopNodeStereotype)
                {
                    //save initial data for stopNode
                    StopNode stopNode = new StopNode(sqlRep, sqlRep.GetElementByID(newElement.ElementID));
                    stopNode.saveTreeToEATaggedValue(true);
                }
                else if (newElement.Stereotype == SDMModelingMain.StartNodeStereotype)
                {
                    //save initial data for startNode
                    StartNode startNode = new StartNode(sqlRep, sqlRep.GetElementByID(newElement.ElementID));
                    startNode.saveTreeToEATaggedValue(false);
                }
                else if (newElement.Stereotype == SDMModelingMain.CSPInstanceStereotype)
                {
                    EA.DiagramObject instanceObject = EAUtil.findDiagramObject(sqlRep, newElement, sqlRep.GetCurrentDiagram());
                    instanceObject.right = instanceObject.left + 225;
                    instanceObject.bottom = instanceObject.top - 125;
                    instanceObject.Update();

                    SDMCSPDialog sdmCspDialog = new SDMCSPDialog(sqlRep.GetElementByID(newElement.ElementID), sqlRep);
                    sdmCspDialog.ShowDialog();
                   
                }

            }
            return true;       
       }

        private static bool classifierIsValid(SQLRepository sqlRep, EA.Element newElement)
        {
            SQLElement newElementClassifier = sqlRep.GetElementByID(newElement.ClassifierID);
            if (newElementClassifier.Stereotype == ECOREModelingMain.EDatatypeStereotype)
            {
                MessageBox.Show("Object variables of type EDatatype cannot be created");
                return false;
            }
            return true;
        }

        /// informed that the deletion of the Method will delete the SDM Diagram as well.
        /// </summary>
        /// <param name="Repository"></param>
        /// <param name="Info"></param>
        /// <returns></returns>
        public Boolean EA_OnPreDeleteMethod(EA.Repository Repository, EA.Method method)
        {
            EA.Method meth = method;
            EA.Element methParent = Repository.GetElementByID(meth.ParentID);
            EA.TaggedValue aTag = null;

            SQLRepository sqlRep = new SQLRepository(Repository, false);

            foreach( EA.Element actEle in methParent.Elements ) 
            {
                aTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(actEle, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName);
                if (aTag != null)
                {
                    if (aTag.Value.ToString() == meth.MethodGUID.ToString())
                    {
                        Boolean delete = false;
                        DeleteMethod deleteMethod = new DeleteMethod(delete);
                        deleteMethod.StartPosition = FormStartPosition.CenterScreen;
                        deleteMethod.ShowDialog();
                        delete = deleteMethod.setVal();
                        if (delete)
                        {
                            EAUtil.deleteElement(actEle, sqlRep);
                            return true;
                        }
                        return false;
                    }
                }
                
            }
            return true;
        }

        public void EA_OnPostOpenDiagram(EA.Repository repository, EA.Diagram openedDiagram)
        {
            if (openedDiagram.MetaType == SDMModelingMain.SdmDiagramMetatype[0])
            {
                createAnchorElementIfNecessary(repository, openedDiagram, true);          
            }
        }

        public static EA.Element createAnchorElementIfNecessary(EA.Repository repository, EA.Diagram openedDiagram, Boolean reloadDiagramAfter)
        {

            //checks if the current SDM Diagram already contains an Anchor element.
            //String sqlResult = repository.SQLQuery(@"SELECT Count(*) as Count FROM t_object WHERE ParentID = " + openedDiagram.ParentID +
             //                                       " AND Stereotype = '" + SDMModelingMain.SDMAnchorStereotype + "'");

            String sqlResult = repository.SQLQuery(@"select Count(*) as Count from t_diagramobjects d, t_object o where d.Object_ID = o.Object_ID 
                                                    and o.Stereotype = '" + SDMModelingMain.SDMAnchorStereotype +"' and d.Diagram_ID = " + openedDiagram.DiagramID);
            String count = EAUtil.getXMLNodeContentFromSQLQueryString(sqlResult, "Count")[0];

            if (count == "0")
            {
                Boolean locked = false;

                EA.Element newAnchorElement = null;

                if (openedDiagram.ParentID != 0)
                {
                    EA.Element parentElement = repository.GetElementByID(openedDiagram.ParentID);
                    locked = parentElement.Locked;
                    newAnchorElement = parentElement.Elements.AddNew("", Main.EAClassType) as EA.Element;
                }
                else
                {
                    EA.Package parentPackage = repository.GetPackageByID(openedDiagram.PackageID);
                    locked = parentPackage.Element.Locked;
                    newAnchorElement = parentPackage.Elements.AddNew("", Main.EAClassType) as EA.Element;
                }

                if (!locked)
                {
                    
                    newAnchorElement.Stereotype = SDMModelingMain.SDMAnchorStereotype;
                    newAnchorElement.Update();

                    EA.DiagramObject newAnchorDiagramObject = openedDiagram.DiagramObjects.AddNew("", newAnchorElement.Type) as EA.DiagramObject;
                    newAnchorDiagramObject.left = 5;
                    newAnchorDiagramObject.top = -5;
                    newAnchorDiagramObject.right = 25;
                    newAnchorDiagramObject.bottom = -25;
                    newAnchorDiagramObject.ElementID = newAnchorElement.ElementID;

                    newAnchorDiagramObject.Update();

                    if (reloadDiagramAfter)
                        repository.ReloadDiagram(openedDiagram.DiagramID);
                    return newAnchorElement;
                }
            }
            return null;
            
        }

        public Boolean EA_OnPreNewConnector(EA.Repository repository, EA.EventProperties info) {
            if (info.Get("Stereotype").Value.Equals(SDMModelingMain.LinkVariableStereotype))
            {
                EA.Element client = repository.GetElementByID(Int32.Parse(info.Get("ClientID").Value as String));
                EA.Element supplier = repository.GetElementByID(Int32.Parse(info.Get("SupplierID").Value as String));
                if (client != null && supplier != null)
                {
                    List<SQLConnector> clientToSupplierAssociations = new List<SQLConnector>();
                    List<SQLConnector> supplierToClientAssociations = new List<SQLConnector>();

                    SQLRepository sqlRepository = new SQLRepository(repository, false);
                    SQLElement clientClassifier = sqlRepository.GetElementByID(client.ClassfierID);
                    SQLElement supplierClassifier = sqlRepository.GetElementByID(supplier.ClassfierID);

                    LinkVariablePropertiesForm.collectPossibleAssociations(ref clientClassifier, ref supplierClassifier, ref clientToSupplierAssociations, ref supplierToClientAssociations, ref sqlRepository);
                    if (clientToSupplierAssociations.Count == 0 && supplierToClientAssociations.Count == 0)
                    {
                        System.Windows.Forms.MessageBox.Show("There is no Association between these two elements");
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
            return true;
        }

        public Boolean EA_OnPostNewConnector(SQLRepository sqlRep, EA.Connector actCon, EA.Diagram currentDiagram)
        {
            if (SdmDiagramMetatype.Contains(currentDiagram.MetaType))
            {
                sqlRep.SaveDiagram(currentDiagram.DiagramID);
                if (actCon.Stereotype == SDMModelingMain.LinkVariableStereotype)
                {                    
                    
                    //save initial data for linkVariable
                    LinkVariable linkVariable = new LinkVariable(sqlRep.GetConnectorByID(actCon.ConnectorID), sqlRep);
                    ObjectVariable newClient = new ObjectVariable(sqlRep.GetElementByID(actCon.ClientID), sqlRep);
                    ObjectVariable newSupplier = new ObjectVariable(sqlRep.GetElementByID(actCon.SupplierID), sqlRep);
                    newClient.loadTreeFromTaggedValue();
                    newSupplier.loadTreeFromTaggedValue();
                    String clientBindingOperator = newClient.BindingOperator.ToString().ToLower();
                    String supplierBindingOperator = newSupplier.BindingOperator.ToString().ToLower();
                    BindingSemantics bindingSemantics = BindingSemantics.MANDATORY;
                    BindingOperator bindingOperator = BindingOperator.CHECK_ONLY;

                    if (supplierBindingOperator == BindingOperator.CREATE.ToString().ToLower() || clientBindingOperator == BindingOperator.CREATE.ToString().ToLower())
                    {
                        bindingSemantics = BindingSemantics.MANDATORY;
                        bindingOperator = BindingOperator.CREATE;
                    }
                    else if (supplierBindingOperator == BindingOperator.DESTROY.ToString().ToLower() || clientBindingOperator == BindingOperator.DESTROY.ToString().ToLower())
                    {
                        bindingSemantics = BindingSemantics.MANDATORY;
                        bindingOperator = BindingOperator.DESTROY;
                    }
                    else if (supplierBindingOperator == BindingOperator.CHECK_ONLY.ToString().ToLower() && clientBindingOperator == BindingOperator.CHECK_ONLY.ToString().ToLower())
                    {
                        bindingSemantics = BindingSemantics.MANDATORY;
                        bindingOperator = BindingOperator.CHECK_ONLY;
                    }
                    else if (supplierBindingOperator == BindingOperator.CHECK_ONLY.ToString().ToLower() && clientBindingOperator == BindingOperator.DESTROY.ToString().ToLower() || supplierBindingOperator == BindingOperator.DESTROY.ToString().ToLower() && clientBindingOperator == BindingOperator.CREATE.ToString().ToLower())
                    {
                        bindingSemantics = BindingSemantics.MANDATORY;
                        bindingOperator = BindingOperator.CHECK_ONLY;
                    }
                    linkVariable.BindingOperator = bindingOperator;
                    linkVariable.BindingSemantics = bindingSemantics;
                    LinkVariablePropertiesForm linkVariableDialogue = new LinkVariablePropertiesForm(linkVariable, sqlRep, false);
                    
                }
                else if (actCon.Type.Equals(Main.EAControlFlowType))
                {
                    ActivityEdge activityEdge = new ActivityEdge(sqlRep, sqlRep.GetConnectorByID(actCon.ConnectorID));

                    //if there is already Success / Failure / ForEach / End guarded edge set new Edge respectively
                    foreach (SQLConnector outgoingEdge in activityEdge.Source.Connectors)
                    {
                        if (outgoingEdge.ClientID == activityEdge.Source.ElementID && outgoingEdge.ConnectorID != activityEdge.EaConnector.ConnectorID)
                        {
                            if (outgoingEdge.TransitionGuard == ActivityEdge.EdgeGuardFailureGui)
                            {
                                activityEdge.GuardType = EdgeGuard.SUCCESS;
                                break;
                            }
                            else if (outgoingEdge.TransitionGuard == ActivityEdge.EdgeGuardSuccessGui)
                            {
                                activityEdge.GuardType = EdgeGuard.FAILURE;
                                break;
                            }
                            else if (outgoingEdge.TransitionGuard == ActivityEdge.EdgeGuardEachTimeGui)
                            {
                                activityEdge.GuardType = EdgeGuard.END;
                                break;
                            }
                            else if (outgoingEdge.TransitionGuard == ActivityEdge.EdgeGuardEndGui)
                            {
                                activityEdge.GuardType = EdgeGuard.EACH_TIME;
                                break;
                            }
                        }
                    }

                    if (activityEdge.GuardType == EdgeGuard.NONE)
                    {
                        SQLElement sourceNode = sqlRep.GetElementByID(actCon.ClientID);
                        if (sourceNode.Stereotype == SDMModelingMain.StoryNodeStereotype)
                        {
                            StoryNode storyNode = new StoryNode(sqlRep, sourceNode);
                            storyNode.loadTreeFromTaggedValue();
                            if (storyNode.ForEach)
                            {
                                activityEdge.GuardType = EdgeGuard.END;
                            }
                        }
                    }

                    activityEdge.saveTreeToEATaggedValue(true);
                }
            }
            return true;
        }


        /// <summary>
        /// creates an empty story diagram related to the given Method 
        /// </summary>
        /// <param name="method"></param>
        /// <param name="repository"></param>
        /// <param name="currentDiagram"></param> The currently open diagram. An anchor will be added to this diagram.
        public static void createStoryDiagram(SQLMethod method, EA.Repository repository, EA.Diagram currentDiagram)
        {
            EA.Element containingEClass = repository.GetElementByID(method.ParentID);
            SQLRepository sqlRepository = new SQLRepository(repository, false);

            EA.Element sdmContainer = (EA.Element)(containingEClass.Elements.AddNew(method.Name, Main.EAClassType));
            sdmContainer.ParentID = containingEClass.ElementID;

            

            EA.Diagram sdmDiagram = (EA.Diagram)(sdmContainer.Diagrams.AddNew(method.Name + " Story Diagram",SdmDiagramMetatype[0]));
            sdmDiagram.ParentID = sdmContainer.ElementID;
            sdmDiagram.ExtendedStyle = "HideRel=0;ShowTags=0;ShowReqs=0;ShowCons=0;OpParams=1;ShowSN=0;ScalePI=0;PPgs.cx=1;PPgs.cy=1;PSize=9;ShowIcons=1;SuppCN=0;HideProps=0;HideParents=0;UseAlias=0;HideAtts=0;HideOps=0;HideStereo=0;HideEStereo=1;FormName=;";
            sdmDiagram.StyleEx = sdmDiagram.StyleEx + "HideConnStereotype=0;";
            repository.SaveDiagram(sdmDiagram.DiagramID);
            sdmDiagram.Update();
            EA.Element startNode = (EA.Element)sdmContainer.Elements.AddNew(" Start", Main.EAStateNodeType);
            startNode.Subtype = Main.EAStartNodeSubtype;
            startNode.ParentID = sdmContainer.ElementID;
            
            startNode.Name = SDMUtil.computeStartNodeName(sqlRepository.GetMethodByID(method.MethodID), sqlRepository.GetElementByID(containingEClass.ElementID));
                    
            startNode.Update();
            EA.DiagramObject startNodeDiagramObject = (EA.DiagramObject)sdmDiagram.DiagramObjects.AddNew("l=50;r=70;t=50;b=70;", Main.EAStateNodeType);
            startNodeDiagramObject.ElementID = startNode.ElementID;
            startNodeDiagramObject.Update();


            method.getRealMethod().Update();
            sdmContainer.Update();

            Navigator.addAnchorEntry(sdmDiagram.DiagramID, currentDiagram.DiagramID);

            String objectStyleString = "StateNodeLBL=CX=437:CY=13:OX=-9:OY=-18:HDN=0:BLD=0:ITA=0:UND=0:CLR=-1:ALN=0:ALT=0:ROT=0;DUID=2B547EF9;LBL=CX=437:CY=13:OX=-6:OY=-17:HDN=0:BLD=0:ITA=0:UND=0:CLR=-1:ALN=0:ALT=0:ROT=0;";
            repository.Execute("UPDATE t_diagramobjects SET ObjectStyle = '" + objectStyleString + "' WHERE Object_ID = " + startNodeDiagramObject.ElementID);

            repository.OpenDiagram(sdmDiagram.DiagramID);

            sdmContainer.Elements.Refresh();

            setAnchorElementTags(sqlRepository, currentDiagram, sqlRepository.GetElementByID(sdmContainer.ElementID));
            Activity activity = new Activity(sqlRepository.GetElementByID(sdmContainer.ElementID), sqlRepository);
            activity.OwningOperation = new ECOREModeling.ECOREExportWrapper.EOperation(sqlRepository.GetMethodByID(method.MethodID), sqlRepository);
            activity.saveTreeToEATaggedValue(true);

            StartNode startNodeObject = new StartNode(sqlRepository, sqlRepository.GetElementByID(startNode.ElementID));
            startNodeObject.saveTreeToEATaggedValue(true);
        }

        public static void setAnchorElementTags(SQLRepository sqlRepository, EA.Diagram callDiagram, SQLElement sdmContainer)
        {
            String anchorElementId = EAUtil.getXMLNodeContentFromSQLQueryString(sqlRepository.SQLQuery("select Object_ID from t_object where ParentID = " + sdmContainer.ElementID + " AND Stereotype = '" + SDMModelingMain.SDMAnchorStereotype + "'"), "Object_ID")[0];
            if (anchorElementId != "")
            {
                SQLElement anchorElement = sqlRepository.GetElementByID(int.Parse(anchorElementId));
                if(EAUtil.findTaggedValue(anchorElement, SDMAnchorDiagramIDTaggedValueName) == null)
                    EAUtil.setTaggedValue(sqlRepository, anchorElement.getRealElement(), SDMAnchorDiagramIDTaggedValueName, callDiagram.DiagramID.ToString());
            }
        }



        
    }
}

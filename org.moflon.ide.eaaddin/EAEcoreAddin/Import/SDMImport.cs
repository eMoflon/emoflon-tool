using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.SDMModeling.LinkVariableDialog;
using System.Web;
using System.Drawing;
using System.Collections.Specialized;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Import
{
    public class SDMImport
    {
        
        public EA.Repository repository;
        public SQLRepository sqlRep;

        public SDMImport(SQLRepository sqlRep)
        {
            this.sqlRep = sqlRep;
            this.repository = sqlRep.GetOriginalRepository();   
        }



        public void importSDMActivity(SQLElement parentEClass, SQLMethod owningOperation, MocaNode activityNode)
        {
            EA.Element activityElement = MainImport.getInstance().EcoreImport.getOrCreateElement(parentEClass, activityNode, Main.EAClassType);

            appendSdmDiagram(owningOperation.Name, activityElement);

            Activity sdmActivity = new Activity(sqlRep.GetElementByID(activityElement.ElementID), sqlRep);
            sdmActivity.OwningOperation = new Modeling.ECOREModeling.ECOREExportWrapper.EOperation(sqlRep.GetMethodByID(owningOperation.MethodID),sqlRep);

            MainImport.getInstance().MocaTaggableElements.Add(sdmActivity);

            foreach (MocaNode activityNodeNode in activityNode.Children)
            {
                if (activityNodeNode.Name == "ActivityNode")
                {
                    importActivityNode(sqlRep.GetElementByID(activityElement.ElementID), activityNodeNode);
                }
            }

        }
        
        private static void appendSdmDiagram(String name, EA.Element parentElement)
        {
            if (parentElement.Diagrams.Count == 0)
            {
                EA.Diagram sdmDiagram = parentElement.Diagrams.AddNew(name, SDMModelingMain.SdmDiagramMetatype[0]) as EA.Diagram;
                sdmDiagram.Update();
                parentElement.Diagrams.Refresh();
                MainImport.getInstance().DiagramsToBeFilled.Add(sdmDiagram);
            }
        }

        private void importActivityNode(SQLElement sdmContainer, MocaNode activityNodeNode)
        {
            EA.Element activityNodeElement = null;
            ActivityNode aNode = null;
            if (activityNodeNode.getAttributeOrCreate("type").Value == "start")
            {
                activityNodeElement = MainImport.getInstance().EcoreImport.getOrCreateElement(sdmContainer, activityNodeNode, Main.EAStateNodeType, Main.EAStartNodeSubtype);
                
                aNode = new StartNode(sqlRep, sqlRep.GetElementByID(activityNodeElement.ElementID));
            }
            else if (activityNodeNode.getAttributeOrCreate("type").Value == "stop")
            {
                activityNodeElement = MainImport.getInstance().EcoreImport.getOrCreateElement(sdmContainer, activityNodeNode, Main.EAStateNodeType, Main.EAStopNodeSubtype);
                
                aNode = new StopNode(sqlRep, sqlRep.GetElementByID(activityNodeElement.ElementID));
            }
            else if (activityNodeNode.getAttributeOrCreate("type").Value == "statement")
            {
                activityNodeElement = MainImport.getInstance().EcoreImport.getOrCreateElement(sdmContainer, activityNodeNode, Main.EAActivityType);
                
                aNode = new StatementNode(sqlRep, sqlRep.GetElementByID(activityNodeElement.ElementID));
            }
            else if (activityNodeNode.getAttributeOrCreate("type").Value == "story")
            {
                activityNodeElement = MainImport.getInstance().EcoreImport.getOrCreateElement(sdmContainer, activityNodeNode, Main.EAActivityType);
                appendSdmDiagram(activityNodeElement.Name, activityNodeElement);
                aNode = new StoryNode(sqlRep, sqlRep.GetElementByID(activityNodeElement.ElementID));
            }
            
            MocaNode outgoingEdgesNode = activityNodeNode.getChildNodeWithName(ActivityNode.OutgoingTransitionsNodeName);
            if (outgoingEdgesNode != null)
            {
                foreach (MocaNode outgoingEdgeNode in outgoingEdgesNode.Children)
                {
                    MainImport.getInstance().ConnectorNodeToParent.Add(outgoingEdgeNode, activityNodeElement);        
                }
            }

            MocaNode objectVariablesNode = activityNodeNode.getChildNodeWithName(StoryPattern.ObjectVariablesChildNodeName);
            if (objectVariablesNode != null)
            {
                foreach (MocaNode ovNode in objectVariablesNode.Children)
                {
                    importObjectVariable(sqlRep.GetElementByID(activityNodeElement.ElementID), ovNode);
                }
            }

            aNode.deserializeFromMocaTree(activityNodeNode);

            MainImport.getInstance().ElementGuidToElement.Add(activityNodeElement.ElementGUID, activityNodeElement);
            MainImport.getInstance().OldGuidToNewGuid.Add(aNode.EaGuid, activityNodeElement.ElementGUID);
            MainImport.getInstance().MocaTaggableElements.Add(aNode);
        }

        public ObjectVariable importObjectVariable(SQLElement storyNode, MocaNode ovNode)
        {
            EA.Element ovElement = MainImport.getInstance().EcoreImport.getOrCreateElement(storyNode, ovNode, Main.EAObjectType);

            MocaNode outgoingLinksNode = ovNode.getChildNodeWithName(ObjectVariable.OutgoingLinksNodeName);
            if (outgoingLinksNode != null)
            {
                foreach (MocaNode outgoingLinkNode in outgoingLinksNode.Children)
                {
                    MainImport.getInstance().ConnectorNodeToParent.Add(outgoingLinkNode, ovElement);
                }
            }

            ObjectVariable ov = new ObjectVariable(sqlRep.GetElementByID(ovElement.ElementID), sqlRep);
            ov.deserializeFromMocaTree(ovNode);

            MainImport.getInstance().MocaTaggableElements.Add(ov);
            MainImport.getInstance().ElementGuidToElement.Add(ovElement.ElementGUID, ovElement);
            MainImport.getInstance().OldGuidToNewGuid.Add(ov.Guid, ovElement.ElementGUID);
            MainImport.getInstance().ObjectToTypeGuid.Add(ovElement, ov.TypeGuid);

            return ov;
        }


        public void importActivityEdge(SQLElement activityNodeElement, MocaNode activityEdgeNode)
        {
            String targetgGuid = MainImport.getInstance().OldGuidToNewGuid[activityEdgeNode.getAttributeOrCreate("targetGuid").Value];
            EA.Element targetElement = MainImport.getInstance().ElementGuidToElement[targetgGuid];
            
            EA.Connector activityEdgeConnector = MainImport.getInstance().EcoreImport.getOrCreateConnector(activityNodeElement, sqlRep.GetElementByID(targetElement.ElementID),activityEdgeNode.getAttributeOrCreate(Main.GuidStringName).Value, Main.EAControlFlowType);

            ActivityEdge actEdge = new ActivityEdge(sqlRep, sqlRep.GetConnectorByID(activityEdgeConnector.ConnectorID));
            actEdge.deserializeFromMocaTree(activityEdgeNode);
            MainImport.getInstance().MocaTaggableElements.Add(actEdge);

        }

        public LinkVariable importLinkVariable(SQLElement ovElement, MocaNode lvNode)
        {
            String oldTargetGuid = lvNode.getAttributeOrCreate("targetGuid").Value;
            String newTargetGuid = MainImport.getInstance().OldGuidToNewGuid[oldTargetGuid];
            
            EA.Element targetElement = MainImport.getInstance().ElementGuidToElement[newTargetGuid];
            
            EA.Connector newLinkConnector = MainImport.getInstance().EcoreImport.getOrCreateConnector(ovElement, sqlRep.GetElementByID(targetElement.ElementID), lvNode.getAttributeOrCreate(Main.GuidStringName).Value, Main.EAAssociationType);


            LinkVariable lv = new LinkVariable(sqlRep.GetConnectorByID(newLinkConnector.ConnectorID), sqlRep);
            lv.deserializeFromMocaTree(lvNode);
            if (lv.linkDialogueEntry.clientRoleName != "" && lv.linkDialogueEntry.supplierRoleName != "")
                lv.linkDialogueEntry.direction = LinkDialogueEntryDirection.BI;
            MainImport.getInstance().MocaTaggableElements.Add(lv);

            return lv;

        }

        internal void fillSDMDiagram(EA.Diagram diagram)
        {
            EA.Element parentElement = repository.GetElementByID(diagram.ParentID);

            if (parentElement.Stereotype == SDMModelingMain.SdmContainerStereotype)
            {
                SDMUtil.fillSDMActivityNodeDiagram(repository, diagram, parentElement);
            }
            else if (parentElement.Stereotype == SDMModelingMain.StoryNodeStereotype)
            {
                SDMUtil.fillAllStoryNodeDiagrams(sqlRep, diagram, parentElement);
            }
        }
    }
}

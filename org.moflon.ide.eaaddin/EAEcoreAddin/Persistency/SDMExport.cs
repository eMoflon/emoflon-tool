using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Persistency
{
    public class SDMExport
    {
        SQLRepository repository;
        Export Export { get; set; }
        MocaNode currentNode;

        public ExportProgressBar exportProgressBar { get; set; }

        public SDMExport(SQLRepository repository, Export export)
        {
            this.Export = export;
            this.repository = repository;
        }

        public MocaNode processActivity(SQLElement sdmContainerClass)
        {
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(sdmContainerClass, Main.MoflonExportTreeTaggedValueName);
            
            Activity activity = new Activity(sdmContainerClass, repository);
            Boolean valid = activity.loadTreeFromTaggedValue();
            MocaNode activityMocaNode = new MocaNode();
            activityMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);
            this.currentNode = activityMocaNode;

            foreach (SQLElement activityNode in sdmContainerClass.Elements)
            {
                if (activityNode.Type == Main.EAActivityType || activityNode.Type == Main.EAStateNodeType)
                {
                    this.currentNode = activityMocaNode;
                    processActivityNode(activityNode);
                }
            }

            return activityMocaNode;
        }

        public void processActivityNode(SQLElement activityNodeEAElement)
        {
            if (Serialization.MocaTaggableElement.isIgnored(activityNodeEAElement))
                return;

            ActivityNode actNode = null;
            if (activityNodeEAElement.Stereotype == "StoryNode" || activityNodeEAElement.Elements.Count > 0 || (activityNodeEAElement.Elements.Count == 0 && activityNodeEAElement.Notes == "" && activityNodeEAElement.Subtype != 100 && activityNodeEAElement.Subtype != 101))
                actNode = new StoryNode(repository, activityNodeEAElement);
            else if (activityNodeEAElement.Stereotype == "StopNode" || activityNodeEAElement.Subtype == 101)
                actNode = new StopNode(repository, activityNodeEAElement);
            else if (activityNodeEAElement.Stereotype == "StartNode" || activityNodeEAElement.Subtype == 100)
                actNode = new StartNode(repository, activityNodeEAElement);
            else if (activityNodeEAElement.Stereotype == "StatementNode" || (activityNodeEAElement.Elements.Count == 0 && activityNodeEAElement.Notes != ""))
                actNode = new StatementNode(repository, activityNodeEAElement);

            Boolean valid = actNode.loadTreeFromTaggedValue();
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(activityNodeEAElement, Main.MoflonExportTreeTaggedValueName);
            
            MocaNode activityNodeMocaNode = new MocaNode();
            activityNodeMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);
            this.currentNode.appendChildNode(activityNodeMocaNode); 

            foreach (SQLConnector activityEdge in activityNodeEAElement.Connectors)
            {
                if ((activityEdge.Stereotype == SDMModelingMain.ActivityEdgeStereotype || activityEdge.Stereotype == "") && activityEdge.Type == "ControlFlow" && activityEdge.ClientID == activityNodeEAElement.ElementID)
                {
                    this.currentNode = activityNodeMocaNode.getChildNodeWithName(ActivityNode.OutgoingTransitionsNodeName);
                    processActivityEdge(activityEdge);
                }
            }
            foreach (SQLElement storyNodeChild in activityNodeEAElement.Elements)
            {
                if (storyNodeChild.Stereotype == SDMModelingMain.ObjectVariableStereotype || storyNodeChild.Stereotype == "SDM_Object")
                {
                    this.currentNode = activityNodeMocaNode.getChildNodeWithName(StoryPattern.ObjectVariablesChildNodeName);
                    processObjectVariable(storyNodeChild);
                }
                else if (storyNodeChild.Stereotype == SDMModelingMain.CSPInstanceStereotype)
                {
                    this.currentNode = activityNodeMocaNode.getChildNodeWithName("AttributeConstraints");
                    if (currentNode == null)
                    {
                        this.currentNode = activityNodeMocaNode.appendChildNode("AttributeConstraints");
                    }

                    MocaNode cspNode = currentNode.appendChildNode("AttributeConstraint");
                    cspNode.appendChildAttribute("constraintSpec", storyNodeChild.Notes);
                    SQLTaggedValue nacIndexTag = EAUtil.findTaggedValue(storyNodeChild, "nacIndex");
                    if(nacIndexTag != null) {
                        String nacIndex = nacIndexTag.Value; 
                        cspNode.appendChildAttribute("nacIndex", nacIndex != "" ? nacIndex : "-1");
                    }   
                }
            }

        }


        public void processActivityEdge(SQLConnector activityEdgeEAElement)
        {
            SQLConnectorTag mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(activityEdgeEAElement, Main.MoflonExportTreeTaggedValueName);

            ActivityEdge aEdge = new ActivityEdge(repository, activityEdgeEAElement);
            Boolean valid = aEdge.loadTreeFromTaggedValue();
            MocaNode activityEdgeMocaNode = new MocaNode();
            activityEdgeMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);

            aEdge.addAttributesDuringExport(activityEdgeMocaNode);

            this.currentNode.appendChildNode(activityEdgeMocaNode);
        }

        public void processLinkVariable(SQLConnector linkVariable)
        {
            SQLConnectorTag mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(linkVariable, Main.MoflonExportTreeTaggedValueName);

            LinkVariable lv = new LinkVariable(linkVariable, repository);
            lv.loadTreeFromTaggedValue();
            MocaNode linkVariableMocaNode = new MocaNode();
            linkVariableMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);

            lv.addAttributesDuringExport(linkVariableMocaNode);

            this.currentNode.appendChildNode(linkVariableMocaNode);
        }

        public void processObjectVariable(SQLElement objectVariable)
        {
            SQLTaggedValue mocaTreeTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(objectVariable, Main.MoflonExportTreeTaggedValueName);

            ObjectVariable ov = new ObjectVariable(objectVariable, repository);
            ov.loadTreeFromTaggedValue();

            SQLElement classifier = EAUtil.getClassifierElement(repository, objectVariable.ClassifierID);
            if (classifier != null)
            {
                Export.computeAndAddToDependencies(repository, classifier);
            }

            MocaNode ovMocaNode = new MocaNode();
            
            ovMocaNode.deserializeFromXmlTree(MocaTreeUtil.stringToXmlDocument(mocaTreeTag.Notes).DocumentElement.FirstChild as XmlElement);

            ov.addAttributesDuringExport(ovMocaNode);
            
            this.currentNode.appendChildNode(ovMocaNode);

            foreach (SQLConnector linkVariable in objectVariable.Connectors)
            {
                if ((linkVariable.Stereotype == SDMModelingMain.LinkVariableStereotype || linkVariable.Stereotype == "SDM_Association") && linkVariable.ClientID == objectVariable.ElementID)
                {
                    this.currentNode = ovMocaNode.getChildNodeWithName(ObjectVariable.OutgoingLinksNodeName);
                    processLinkVariable(linkVariable);
                }
            }   
        }
    }
}

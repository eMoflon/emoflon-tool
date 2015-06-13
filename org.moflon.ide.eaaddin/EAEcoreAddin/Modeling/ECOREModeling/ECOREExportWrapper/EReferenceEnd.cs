using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using System.Text.RegularExpressions;
using EAEcoreAddin.Modeling.ECOREModeling.Util;

namespace EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper
{
    class EReferenceEnd : MocaSerializableElement
    {

        public String typeGUID = "";
        public String refGUID = "";
        public String oppositeGUID = "";
        public String containment = "false";
        public String lowerBound = "0";
        public String upperBound = "1";

        public Boolean Navigable = false;

        public SQLConnectorEnd ConnectorEnd;
        SQLConnector EaConnector;

        public EReferenceEnd(SQLConnectorEnd eaConnectorEnd, SQLConnector connector, SQLRepository repository)
        {
            this.Repository = repository;
            this.EaConnector = connector;
            this.ConnectorEnd = eaConnectorEnd;
            this.Navigable = eaConnectorEnd.IsNavigable;
        }


        

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode referencesNode)
        {
            MocaNode ereferenceNode = referencesNode;

            ereferenceNode = extractFromReferencesNode(ereferenceNode);


            //alternative way
            if (ereferenceNode == null || ereferenceNode.Name != "EReference")
            {

                if (ConnectorEnd.End == "Client")
                {
                    ereferenceNode = referencesNode.getChildNodeWithName("ClientReference");
                }
                else if (ConnectorEnd.End == "Supplier")
                {
                    ereferenceNode = referencesNode.getChildNodeWithName("SupplierReference");
                }
                if(ereferenceNode != null)
                    ereferenceNode = ereferenceNode.getChildNodeWithName("EReference");
            }

            if (ereferenceNode != null)
            {
                this.Navigable = true;
                MocaAttribute typeAttr = ereferenceNode.getAttributeOrCreate("typeGuid");
                if (typeAttr != null)
                    this.typeGUID = typeAttr.Value;
                MocaAttribute nameAttr = ereferenceNode.getAttributeOrCreate("name");
                if (nameAttr != null)
                    this.Name = nameAttr.Value;
                MocaAttribute guidAttr = ereferenceNode.getAttributeOrCreate(Main.GuidStringName);
                if (guidAttr != null)
                    this.refGUID = guidAttr.Value;
                MocaAttribute lbAttr = ereferenceNode.getAttributeOrCreate("lowerBound");
                if (lbAttr != null)
                    this.lowerBound = lbAttr.Value;
                MocaAttribute ubAttr = ereferenceNode.getAttributeOrCreate("upperBound");
                if (ubAttr != null)
                    this.upperBound = ubAttr.Value;
                MocaAttribute cont = ereferenceNode.getAttributeOrCreate("containment");
                if (cont != null)
                    this.containment = cont.Value;
                MocaAttribute oppo = ereferenceNode.getAttributeOrCreate("oppositeGuid");
                if (oppo != null)
                    this.oppositeGUID = oppo.Value;
            }
        }

        private MocaNode extractFromReferencesNode(MocaNode ereferenceNode)
        {
            MocaNode erefNode = null;
            if (ereferenceNode.Name == EClass.ReferencesChildNodeName)
            {
                foreach (MocaNode childNode in ereferenceNode.Children)
                {
                    if (ereferenceNode.Children.Count == 2)
                    {
                        //String a = "";
                    }



                    if (childNode.getAttributeOrCreate(Main.GuidStringName).Value.Contains("Client") && ConnectorEnd.End == "Client")
                    {
                        erefNode = childNode;
                        break;
                    }
                    else if (childNode.getAttributeOrCreate(Main.GuidStringName).Value.Contains("Supplier") && ConnectorEnd.End == "Supplier")
                    {
                        erefNode = childNode;
                        break;
                    }
                }
            }
            return erefNode;
        }

        public override MocaNode serializeToMocaTree(MocaNode actNode)
        {
            MocaNode dummyNode = null;

            EcoreUtil.computeLowerUpperBound(ConnectorEnd.Cardinality, ref lowerBound, ref upperBound);

            if (ConnectorEnd.End == "Client")
            {
                dummyNode = actNode.appendChildNode("ClientReference");
                
                SQLElement clientEClass = Repository.GetElementByID(this.EaConnector.ClientID);
                typeGUID = clientEClass.ElementGUID;
                refGUID = this.EaConnector.ConnectorGUID + "Client";
                if (this.EaConnector.SupplierEnd.Role != "")
                {
                    oppositeGUID = this.EaConnector.ConnectorGUID + "Supplier";
                }

                if (this.EaConnector.SupplierEnd.Aggregation == 2)
                    containment = "true";

            }

            else if (ConnectorEnd.End == "Supplier")
            {
                dummyNode = actNode.appendChildNode("SupplierReference");
                SQLElement supplierEClass = Repository.GetElementByID(this.EaConnector.SupplierID);
                typeGUID = supplierEClass.ElementGUID;
                refGUID = this.EaConnector.ConnectorGUID + "Supplier";
                if (this.EaConnector.ClientEnd.Role != "")
                {
                    oppositeGUID = this.EaConnector.ConnectorGUID + "Client";
                }

                if (this.EaConnector.ClientEnd.Aggregation == 2)
                    containment = "true";
            }

            if (Navigable)
            {
                MocaNode ereferenceNode = dummyNode.appendChildNode("EReference");
                ereferenceNode.appendChildAttribute("typeGuid", typeGUID);
                ereferenceNode.appendChildAttribute("name", ConnectorEnd.Role);
                ereferenceNode.appendChildAttribute(Main.GuidStringName, refGUID);
                ereferenceNode.appendChildAttribute("lowerBound", lowerBound);
                ereferenceNode.appendChildAttribute("upperBound", upperBound);
                ereferenceNode.appendChildAttribute("containment", containment);
                if (oppositeGUID != "")
                {
                    ereferenceNode.appendChildAttribute("oppositeGuid", oppositeGUID);
                }

            }
            return dummyNode;
        }
    }
}

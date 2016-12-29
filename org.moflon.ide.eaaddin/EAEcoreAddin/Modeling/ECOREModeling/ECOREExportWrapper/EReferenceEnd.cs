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
        public bool containment = false;
        public String lowerBound = "0";
        public String upperBound = "1";

        public Boolean Navigable = false;
        public Boolean aggregationSet = false;

        public SQLConnectorEnd ConnectorEnd;
        SQLConnector EaConnector;

        public EReferenceEnd(SQLConnectorEnd eaConnectorEnd, SQLConnector connector, SQLRepository repository)
        {
            this.Repository = repository;
            this.EaConnector = connector;
            this.ConnectorEnd = eaConnectorEnd;
            this.Navigable = eaConnectorEnd.IsNavigable;
        }

        public void Update()
        {
            String cardinality = ConnectorEnd.getRealConnectorEnd().Cardinality;
            if(cardinality != null){
                if (cardinality == "")
                {
                    lowerBound = "0";
                    upperBound = "1";
                }
                else
                {
                    String[] parts = cardinality.Split('.');
                    if (parts.Length > 0)
                    {
                        if (parts.Length == 1)
                        {
                            if (parts[0] == "*")
                            {
                                lowerBound = "0";
                                upperBound = "-1";
                            }
                            else
                            {
                                lowerBound = getCardinality(parts[0]);
                                upperBound = getCardinality(parts[0]);
                            }
                        }

                        else
                        {
                            lowerBound = getCardinality(parts[0]);
                            upperBound = getCardinality(parts[parts.Length - 1]);
                        }
                    }
                }
            }
        }

        private String getCardinality(String cad)
        {
            if (cad == "*")
                return "-1";
            else
                return cad;
        }

        public void setEndString(String endString)
        {
            ConnectorEnd.setClientOrTarget(endString);
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
               MocaAttribute cont = ereferenceNode.getAttribute("containment");
                if (cont != null)
                {
                    this.containment = cont.Value == "true";
                }
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

            EcoreUtil.computeLowerUpperBound(ConnectorEnd.getRealConnectorEnd().Cardinality, ref lowerBound, ref upperBound);

            if (ConnectorEnd.getRealConnectorEnd().End == "Client")
            {
                dummyNode = actNode.appendChildNode("ClientReference");
                
                SQLElement clientEClass = Repository.GetElementByID(this.EaConnector.getRealConnector().ClientID);
                typeGUID = clientEClass.ElementGUID;
                refGUID = this.EaConnector.getRealConnector().ConnectorGUID + "Client";
                if (this.EaConnector.getRealConnector().SupplierEnd.Role != "")
                {
                    oppositeGUID = this.EaConnector.getRealConnector().ConnectorGUID + "Supplier";
                }


                containment = this.EaConnector.getRealConnector().SupplierEnd.Aggregation == 2;

            }

            else if (ConnectorEnd.getRealConnectorEnd().End == "Supplier")
            {
                dummyNode = actNode.appendChildNode("SupplierReference");
                SQLElement supplierEClass = Repository.GetElementByID(this.EaConnector.getRealConnector().SupplierID);
                typeGUID = supplierEClass.ElementGUID;
                refGUID = this.EaConnector.getRealConnector().ConnectorGUID + "Supplier";
                if (this.EaConnector.getRealConnector().ClientEnd.Role != "")
                {
                    oppositeGUID = this.EaConnector.getRealConnector().ConnectorGUID + "Client";
                }


                containment = this.EaConnector.getRealConnector().ClientEnd.Aggregation == 2;
            }

            if (Navigable)
            {
                MocaNode ereferenceNode = dummyNode.appendChildNode("EReference");
                ereferenceNode.appendChildAttribute("typeGuid", typeGUID);
                if((ConnectorEnd.Role == "" || ConnectorEnd.getRealConnectorEnd().Role == null))
                    ereferenceNode.appendChildAttribute("name", this.Name);
                else
                    ereferenceNode.appendChildAttribute("name", ConnectorEnd.getRealConnectorEnd().Role);
                ereferenceNode.appendChildAttribute(Main.GuidStringName, refGUID);
                ereferenceNode.appendChildAttribute("lowerBound", lowerBound);
                ereferenceNode.appendChildAttribute("upperBound", upperBound);
                ereferenceNode.appendChildAttribute("containment", (containment+"").ToLower());
                if (oppositeGUID != "")
                {
                    ereferenceNode.appendChildAttribute("oppositeGuid", oppositeGUID);
                }

            }
            return dummyNode;
        }

    }
}

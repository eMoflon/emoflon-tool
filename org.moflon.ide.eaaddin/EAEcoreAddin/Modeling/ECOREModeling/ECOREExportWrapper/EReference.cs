using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using System.Text.RegularExpressions;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper
{
    class EReference : MocaTaggableElement
    {

        public SQLConnector EaConnector { get; set; }
        public static readonly String ClientReferenceChildNodeName = "ClientReference";
        public static readonly String SupplierReferenceChildNodeName = "SupplierReference";

        public EReferenceEnd ClientEnd;
        public EReferenceEnd SupplierEnd;

        public EReference(SQLConnector eaConnector, SQLRepository repository)
        {
            this.Repository = repository;
            this.EaConnector = eaConnector;
            this.ClientEnd = new EReferenceEnd(eaConnector.ClientEnd, eaConnector, repository);
            this.SupplierEnd = new EReferenceEnd(eaConnector.SupplierEnd, eaConnector, repository);
        }


        public override object getObjectToBeTagged()
        {
            return this.EaConnector;
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode ereferenceNode = new MocaNode();
            ereferenceNode.Name = "EAReferences";

            this.ClientEnd.serializeToMocaTree(ereferenceNode);
            this.SupplierEnd.serializeToMocaTree(ereferenceNode);

            return ereferenceNode;
        }


        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.ClientEnd.deserializeFromMocaTree(actNode);
            this.SupplierEnd.deserializeFromMocaTree(actNode);
            
        }

        public override void doEaGuiStuff()
        {

            setConnectorEnd(ClientEnd);
            setConnectorEnd(SupplierEnd);

            EaConnector.getRealConnector().Update();
        }

        public override void refreshSQLObject()
        {
            this.EaConnector = Repository.GetConnectorByID(EaConnector.ConnectorID);
        }

        private void setConnectorEnd(EReferenceEnd end)
        {
            EReferenceEnd opposite = null;
            if (ClientEnd == end)
                opposite = SupplierEnd;
            else
                opposite = ClientEnd;

            if (end.Navigable)
            {
                if (end.containment == "true")
                {
                    opposite.ConnectorEnd.getRealConnectorEnd().Aggregation = 2;
                }
                if (end.lowerBound != ClientEnd.upperBound)
                {
                    end.ConnectorEnd.getRealConnectorEnd().Cardinality = end.lowerBound.Replace("-1", "*") + ".." + end.upperBound.Replace("-1", "*");
                }
                else
                {
                    end.ConnectorEnd.getRealConnectorEnd().Cardinality = end.lowerBound.Replace("-1", "*");
                }
                if (end.Name != "" && end.Name != null)
                {
                    end.ConnectorEnd.getRealConnectorEnd().Role = end.Name;
                    end.ConnectorEnd.getRealConnectorEnd().Update();
                }

               
            }
        }
    }
}

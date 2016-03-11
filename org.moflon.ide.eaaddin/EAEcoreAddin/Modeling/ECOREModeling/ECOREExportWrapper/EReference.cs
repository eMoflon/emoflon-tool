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

        private EClass sourceClass;
        private EClass targetClass;

        public EReference(SQLConnector eaConnector, SQLRepository repository)
        {
            this.Repository = repository;
            this.EaConnector = eaConnector;
            this.ClientEnd = new EReferenceEnd(eaConnector.ClientEnd, eaConnector, repository);
            this.SupplierEnd = new EReferenceEnd(eaConnector.SupplierEnd, eaConnector, repository);
        }

        public EReference(SQLConnector eaConnector, SQLRepository repository, EClass source, EClass target)
        {
            this.Repository = repository;
            this.EaConnector = eaConnector;
            this.ClientEnd = new EReferenceEnd(eaConnector.ClientEnd, eaConnector, repository);
            this.SupplierEnd = new EReferenceEnd(eaConnector.SupplierEnd, eaConnector, repository);
            this.sourceClass = source;
            this.targetClass = target;
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
            EaConnector.Update();
            
        }

        public override void refreshSQLObject()
        {
            this.EaConnector = Repository.GetConnectorByID(EaConnector.ConnectorID);
        }

        private void setConnectorEnd(EReferenceEnd end)
        {
            
            EReferenceEnd opposite = null;
            int oldAggregation;
            bool isEnd = ClientEnd == end;
            bool containment = false;
            string name = end.Name;
            
            if (isEnd)
                opposite = SupplierEnd;
            else
                opposite = ClientEnd;

            if(opposite!=null && !end.aggregationSet)
                containment = opposite.containment;
            if (end.Navigable)
            {
                                
              if (containment)
                {
                    oldAggregation = end.ConnectorEnd.getAggregation();
                    end.ConnectorEnd.setAggregation(2);
                   end.aggregationSet = true;
                    end.ConnectorEnd.Update();
                    EaConnector.Update();
                    oldAggregation = end.ConnectorEnd.getAggregation(); 
                }
                if (end.lowerBound != ClientEnd.upperBound)
                {
                    end.ConnectorEnd.getRealConnectorEnd().Cardinality = end.lowerBound.Replace("-1", "*") + ".." + end.upperBound.Replace("-1", "*");
                   // Update();
                }
                else
                {
                    end.ConnectorEnd.getRealConnectorEnd().Cardinality = end.lowerBound.Replace("-1", "*");
                   // Update();
                }
                if (name != "" && name != null)
                {
                    end.ConnectorEnd.getRealConnectorEnd().Role = end.Name;
                    end.ConnectorEnd.Update();
                    end.ConnectorEnd.getRealConnectorEnd().Update();
                }

               
            }
        }
        public void Update()
        {
            this.SupplierEnd.Update();
            this.ClientEnd.Update();
        }

        public void switchEnds()
        {
            EReferenceEnd tmp = this.SupplierEnd;
            this.SupplierEnd = this.ClientEnd;
            SupplierEnd.setEndString("Supplier");
            this.ClientEnd = tmp;
            ClientEnd.setEndString("Client");
        }

        public void setRealConnector(EA.Connector con)
        {
            EaConnector.setRealConnector(con);
        }
    }
}

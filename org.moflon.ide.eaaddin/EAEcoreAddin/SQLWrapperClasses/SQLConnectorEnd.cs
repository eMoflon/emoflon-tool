using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Web;
using EAEcoreAddin.Util;
using EAEcoreAddin.Serialization.MocaTree.Util;

namespace EAEcoreAddin.SQLWrapperClasses
{
    /*
     *Created by Alexander Schleich
     *for EMoflon:
     *http://www.moflon.org/emoflon/
     */
    public class SQLConnectorEnd
    {
        String xmlConnectorString, clientOrTarget;
        SQLRepository repository;
        public SQLConnectorEnd(SQLRepository repository, String xmlConnectorString, String clientOrTarget)
        {
            this.repository = repository;
            this.xmlConnectorString = xmlConnectorString;
            this.clientOrTarget = clientOrTarget;
        }

      // private int Aggregation = -1;
        
       public int getAggregation()
            {
        /*        if (Aggregation == -1)
                {
                    if (clientOrTarget == "client")
                        Aggregation = int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlConnectorString, "SourceIsAggregate")[0]);

                    else if (clientOrTarget == "target")
                        Aggregation = int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlConnectorString, "DestIsAggregate")[0]);
                }
                return Aggregation;*/
                return getRealConnectorEnd().Aggregation;
            }

       public void setAggregation(int value)
            {
               // Aggregation = value;
                getRealConnectorEnd().Aggregation = value;
                if (clientOrTarget == "client")
                    repository.Execute("update t_connector set SourceIsAggregate = '" + value + "' where ea_guid = '" + getGuid() + "'");
                else
                    repository.Execute("update t_connector set DestIsAggregate = '" + value + "' where ea_guid = '" + getGuid() + "'");
           // repository.Execute("update t_connector set ea_guid = '" + oldGuid + "' where ea_guid = '" + connector.ConnectorGUID + "'");
                realConnectorEnd.Update();
               /* if (clientOrTarget == "client")
                  xmlConnectorString = EAEcoreAddin.Util.EAUtil.setValueInXMLNodeContentFromSQLQueryString(xmlConnectorString, "SourceIsAggregate", value+"");

                else if (clientOrTarget == "target")
                    xmlConnectorString = EAEcoreAddin.Util.EAUtil.setValueInXMLNodeContentFromSQLQueryString(xmlConnectorString, "DestIsAggregate", value + "");*/
            }


       private String getGuid()
       { 
                    return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlConnectorString, "ea_guid")[0];
       }

        public void setOppositeAggregation(int value)
        {
            getOppositeConnectorEnd().Aggregation = value;
            oppositeConnectorEnd.Update();
            if (clientOrTarget == "target")
                xmlConnectorString = EAEcoreAddin.Util.EAUtil.setValueInXMLNodeContentFromSQLQueryString(xmlConnectorString, "SourceIsAggregate", value + "");

            else if (clientOrTarget == "client")
                xmlConnectorString = EAEcoreAddin.Util.EAUtil.setValueInXMLNodeContentFromSQLQueryString(xmlConnectorString, "DestIsAggregate", value + "");
        }


        public string Cardinality
        {
            get
            {
                if (clientOrTarget == "client")
                {
                    return EAUtil.getXMLNodeContentFromSQLQueryString(xmlConnectorString, "SourceCard")[0];
                }
                else if (clientOrTarget == "target")
                {
                    return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlConnectorString, "DestCard")[0];
                }
                return "";
            }
        }


        private EA.ConnectorEnd realConnectorEnd;

        public EA.ConnectorEnd getRealConnectorEnd()
        {
            if (realConnectorEnd == null)
            {
                if (clientOrTarget == "client")
                {
                    realConnectorEnd = OwningConnector.getRealConnector().ClientEnd;
                }
                else if (clientOrTarget == "target")
                {
                    realConnectorEnd = OwningConnector.getRealConnector().SupplierEnd;
                }
            }
            return realConnectorEnd;
        }

        private EA.ConnectorEnd oppositeConnectorEnd;

        public EA.ConnectorEnd getOppositeConnectorEnd()
        {
            if (oppositeConnectorEnd == null)
            {
                if (clientOrTarget == "client")
                {
                    oppositeConnectorEnd = OwningConnector.getRealConnector().SupplierEnd;
                }
                else if (clientOrTarget == "target")
                {
                    oppositeConnectorEnd = OwningConnector.getRealConnector().ClientEnd;
                }
            }
            return oppositeConnectorEnd;
        }








        public string End
        {
            get 
            {
                if (clientOrTarget == "client")
                    return "Client";
                else if (clientOrTarget == "target")
                    return "Supplier";
                return "";

            }
        }

        public void setClientOrTarget(String end)
        {
            if (end == "Client")
                clientOrTarget = "client";
            else if (end == "Supplier")
                clientOrTarget = "target";
        }


        public bool IsNavigable
        {
            get
            {
                return Navigable == "Navigable";
            }
        }

        public string Navigable
        {
            get
            {

                String direction = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlConnectorString, "Direction")[0];
                String directionDecoded = XmlUtil.XmlDecode(direction);

                if (clientOrTarget == "client")
                {
                    String navigable = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlConnectorString, "SourceIsNavigable")[0];
                    
                    //further checks because of possible inconsistent database where SourceIsNavigable is set to false even if
                    //the connectorEnd in EA is definitely set to navigable...
                    if(directionDecoded == Main.EATargetSourceDirection || directionDecoded == "Bi-Directional")
                        return "Navigable";

                    if (navigable == "TRUE")
                        return "Navigable";
                    if (navigable == "FALSE")
                        return "Non-Navigable";
                    else
                        return "Unspecified";
                }
                else if (clientOrTarget == "target")
                {
                    String navigable = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlConnectorString, "DestIsNavigable")[0];

                    //further checks because of possible inconsistent database where SourceIsNavigable is set to false even if
                    //the connectorEnd in EA is definitely set to navigable...
                    if (directionDecoded == Main.EASourceTargetDirection || directionDecoded == "Bi-Directional")
                        return "Navigable";
                    
                    if (navigable == "TRUE")
                        return "Navigable";
                    if (navigable == "FALSE")
                        return "Non-Navigable";
                    else 
                        return "Unspecified";
                }
                return "";
            }
        }






        public SQLConnector OwningConnector
        {
            get
            {
                return new SQLConnector(this.repository, this.xmlConnectorString);
            }
        }



        public string Role
        {
            get
            {
                if (clientOrTarget == "client")
                {
                    return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlConnectorString, "SourceRole")[0];
                }
                else if (clientOrTarget == "target")
                {
                    return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(xmlConnectorString, "DestRole")[0];
                }
                return "";
            }
        }

        public bool Update()
        {
            return getRealConnectorEnd().Update() && getOppositeConnectorEnd().Update();
        }



    }
}

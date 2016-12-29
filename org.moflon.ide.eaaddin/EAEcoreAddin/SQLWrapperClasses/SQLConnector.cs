using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Web;
using EAEcoreAddin.Serialization.MocaTree.Util;

namespace EAEcoreAddin.SQLWrapperClasses
{
    /*
     *Created by Alexander Schleich
     *for EMoflon:
     *http://www.moflon.org/emoflon/
     */
    public class SQLConnector
    {
        SQLRepository repository;
        String sqlString;

        public SQLConnector(SQLRepository repository, String sqlString)
        {
            this.repository = repository;
            this.sqlString = sqlString;
        }

        EA.Connector realConnector;
        public EA.Connector getRealConnector()
        {
            if (realConnector == null)
            {
                realConnector = repository.GetOriginalRepository().GetConnectorByID(ConnectorID);
            }
            return realConnector;
        }

        public void setRealConnector(EA.Connector con)
        {
            realConnector = con;
        }

        private SQLConnectorEnd clientEnd;
        public SQLConnectorEnd ClientEnd
        {
            get 
            {
                if (clientEnd != null)
                {
                    return clientEnd;
                }
                else
                {
                    clientEnd = new SQLConnectorEnd(repository as SQLRepository, sqlString, "client");
                    return clientEnd;
                }
            }
        }

        private int clientID = -1;
        public int ClientID
        {
            get
            {
                if (clientID != -1)
                {
                    return clientID;
                }
                else
                {
                   clientID = int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlString, "Start_Object_ID")[0]);
                   return clientID;
                }
            }
        }




        private String connectorGuid;
        public string ConnectorGUID
        {
            get 
            {
                if (connectorGuid == null)
                {
                    connectorGuid = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlString, "ea_guid")[0];
                }
                return connectorGuid;
            }
        }

        private int connectorID = -1;
        public int ConnectorID
        {
            get
            {
                if (connectorID == -1)
                {
                    connectorID = int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlString, "Connector_ID")[0]);
                }
                return connectorID;
            }
        }





        private String direction;
        public string Direction
        {
            get
            {
                if (direction == null)
                {
                    String directionUndecoded = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlString, "Direction")[0];
                    direction = XmlUtil.XmlDecode(directionUndecoded);
                }
                return direction;

            }

        }


        private string name;
        public string Name
        {
            get
            {
                if (name == null)
                {
                   name = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlString, "Name")[0];
                }
                return name;
            }
        }











        private string stereotype;
        public string Stereotype
        {
            get
            {
                if (stereotype == null)
                {
                   stereotype = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlString, "Stereotype")[0];
                }
                return stereotype;
            }
        }







        private SQLConnectorEnd supplierEnd;
        public SQLConnectorEnd SupplierEnd
        {
            get 
            {
                if (supplierEnd == null)
                {
                    supplierEnd = new SQLConnectorEnd(repository as SQLRepository, sqlString, "target");
                } 
                return supplierEnd;
            }
        }

        private int supplierID = -1;
        public int SupplierID
        {
            get
            {
                if (supplierID == -1)
                {
                   supplierID = int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlString, "End_Object_ID")[0]);
                }
                return supplierID;
            }
        }

        SQLCollection<SQLConnectorTag> taggedValues;
        public SQLCollection<SQLConnectorTag> TaggedValues
        {
            get 
            {
                if (taggedValues != null)
                    return taggedValues;
                else
                {
                    taggedValues = new SQLCollection<SQLConnectorTag>();

                    String[] conTags = null;
                    if (repository.FullDatabaseCheckout)
                    {
                        conTags = repository.t_connectortagElementID.GetValues(ConnectorID.ToString());
                    }
                    else
                    {
                        String contagQry = repository.SQLQuery("select * from t_connectortag where ElementID = " + ConnectorID);
                        conTags = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(contagQry, "Row").ToArray();
                    }
                    if (conTags == null)
                        return taggedValues;

                    foreach (String actTag in conTags)
                    {
                        if (actTag != "")
                        {
                            SQLConnectorTag newTag = new SQLConnectorTag(repository, actTag);
                            taggedValues.AddNew(newTag);
                        }
                    }
                    return taggedValues;
                }
            }
        }

        public SQLCollection<SQLTemplate> Templates
        {
            get
            {
                SQLCollection<SQLTemplate> templates = new SQLCollection<SQLTemplate>();

                String[] templatesArray = null;
                if (repository.FullDatabaseCheckout)
                {
                    templatesArray = repository.t_xrefElementGUID.GetValues(ConnectorGUID);
                }
                else
                {
                    String taggedQry = repository.SQLQuery("select * from t_xref where Client = " + "'" + ConnectorGUID + "'");
                    templatesArray = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(taggedQry, "Row").ToArray();
                }
                if (templatesArray == null)
                    return templates;
                foreach (String templateEntry in templatesArray)
                {
                    if (templateEntry != "")
                    {
                        SQLTemplate newTemplate = new SQLTemplate(repository, templateEntry);
                        templates.AddNew(newTemplate);
                    }
                }
                return templates;
            }
        }




        private string transitionGuard;
        public string TransitionGuard
        {
            get
            {
                if (transitionGuard == null)
                {
                   transitionGuard = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlString, "PDATA2")[0];
                }
                return transitionGuard;
            }
        }
        private string type;
        public string Type
        {
            get
            {
                if (type == null)
                {
                   type = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlString, "Connector_Type")[0];
                }
                return type;
            }
        }

        public void Update()
        {
            this.clientEnd.Update();
            this.supplierEnd.Update();
            this.repository.Resources.Refresh();
        }
    }
}

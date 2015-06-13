using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Web;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Util;
using System.Diagnostics;

namespace EAEcoreAddin.SQLWrapperClasses
{
    /*
     *Created by Alexander Schleich
     *for EMoflon:
     *http://www.moflon.org/emoflon/
     */
    public class SQLElement
    {
        EA.Element realElem;
        String sqlStringOfElement;
        SQLRepository repository;


        
        public SQLElement(SQLRepository repository, String sqlStringOfElement)
        {
            this.sqlStringOfElement = sqlStringOfElement;
            this.repository = repository as SQLRepository;
        }


        #region IDualElement Members

        String abstractStr = "";
        public string Abstract
        {
            get
            {
                if (abstractStr != "")
                    return abstractStr;
                else
                {
                    abstractStr = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "Abstract")[0];
                    return abstractStr;
                }
            }
        }


        String alias = "";
        public string Alias
        {
            get
            {
                if (alias != "")
                    return alias;
                else
                {
                    alias = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "Alias")[0];
                    return alias;
                }
            }
        }

        public bool ApplyGroupLock(string aGroupName)
        {
            throw new NotImplementedException();
        }

        public bool ApplyUserLock()
        {
            throw new NotImplementedException();
        }

        public int AssociationClassConnectorID
        {
            get { throw new NotImplementedException(); }
        }


        SQLCollection<SQLAttribute> attributes = null;
        public SQLCollection<SQLAttribute> Attributes
        {
            get 
            {
                if (attributes != null)
                    return attributes;
                else
                {
                    attributes = new SQLCollection<SQLAttribute>();
                    String[] attrs = null;
                    if (repository.FullDatabaseCheckout)
                    {
                        attrs = repository.t_attributeParentID.GetValues(ElementID.ToString());
                    }
                    else
                    {
                        String attributeQry = repository.SQLQuery("select * from t_attribute where Object_ID = " + ElementID);
                        attrs = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(attributeQry, "Row").ToArray();
                    }
                    if (attrs == null)
                        return attributes;

                    foreach (String attribute in attrs)
                    {
                        if (attribute != "")
                        {
                            SQLAttribute newAttribute = new SQLAttribute(attribute, repository);
                            attributes.AddNew(newAttribute);
                        }
                    }
                    return attributes;
                }
            }
        }

        public EA.Collection AttributesEx
        {
            get { throw new NotImplementedException(); }
        }


        private SQLCollection<SQLElement> baseClasses;
        public SQLCollection<SQLElement> BaseClasses
        {
            get 
            {
                if (baseClasses == null)
                {
                    baseClasses = new SQLCollection<SQLElement>();
                    foreach (SQLConnector actCon in Connectors)
                    {
                        if (actCon.Type == "Generalization")
                        {
                            if (actCon.ClientID == ElementID)
                            {
                                String targetSQLString = "";
                                if (repository.FullDatabaseCheckout)
                                {
                                    targetSQLString = repository.t_objectObjectID[actCon.SupplierID.ToString()];
                                }
                                else
                                {
                                    String qryString = repository.SQLQuery("select * from t_object where Object_ID = " + actCon.SupplierID);
                                    targetSQLString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(qryString, "Row")[0];
                                }
                                SQLElement baseElement = new SQLElement(repository, targetSQLString);
                                baseClasses.AddNew(baseElement);
                            }
                        }
                    }
                }
                return baseClasses;
            }
        }

        public int ClassfierID
        {
            get
            {
                throw new NotImplementedException();
            }
        }

        private int classifierID = -1;
        public int ClassifierID
        {
            get
            {
                if (classifierID == -1)
                {
                    String result = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "Classifier")[0];
                    if (result != "")
                    {
                        classifierID = int.Parse(result);
                    }
                }
                return classifierID;
            }
        }



        SQLCollection<SQLConnector> connectors;
        public SQLCollection<SQLConnector> Connectors
        {
            get 
            {
                if (connectors != null)
                    return connectors;
                else
                {
                    connectors = new SQLCollection<SQLConnector>();
                    String[] outgoingLinks = null;
                    String[] incomingLinks = null;
                    if (repository.FullDatabaseCheckout)
                    {
                        outgoingLinks = repository.t_connectorStart_Object_ID.GetValues(ElementID.ToString());
                        incomingLinks = repository.t_connectorEnd_Object_ID.GetValues(ElementID.ToString());
                    }
                    else
                    {
                        String outgoingQry = repository.SQLQuery("select * from t_connector where Start_Object_ID = " + ElementID);
                        outgoingLinks = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(outgoingQry, "Row").ToArray();
                        String incomingQry = repository.SQLQuery("select * from t_connector where End_Object_ID = " + ElementID);
                        incomingLinks = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(incomingQry, "Row").ToArray();
                    }
                    if (incomingLinks != null)
                    {
                        foreach (String actIncomingLink in incomingLinks)
                        {
                            if (actIncomingLink != "")
                            {
                                SQLConnector connector = new SQLConnector(repository, actIncomingLink);
                                //connector = repository.GetConnectorByID(connector.ConnectorID);
                                if (connector.SupplierID != connector.ClientID)
                                    connectors.AddNew(connector);
                            }
                        }
                    }
                    if (outgoingLinks != null)
                    {
                        foreach (String actOutgoingLink in outgoingLinks)
                        {
                            if (actOutgoingLink != "")
                            {
                                SQLConnector connector = new SQLConnector(repository, actOutgoingLink);
                                //connector = repository.GetConnectorByID(connector.ConnectorID);
                                connectors.AddNew(connector);
                            }
                        }
                    }
                    return connectors;
                }
            }
        }


        public SQLCollection<SQLDiagram> Diagrams
        {
            get 
            {
                SQLCollection<SQLDiagram> diagrams = new SQLCollection<SQLDiagram>();

                String[] diags = null;
                if (repository.FullDatabaseCheckout)
                {
                    diags = repository.t_diagramParentID.GetValues(ElementID.ToString());
                }
                else
                {
                    String diagsQry = repository.SQLQuery("select * from t_diagram where ParentID = " + ElementID);
                    diags = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(diagsQry, "Row").ToArray();
                }
                if (diags == null)
                    return diagrams;
                foreach (String diag in diags)
                {
                    if (diag != "")
                    {
                        SQLDiagram childDiag = new SQLDiagram(repository, diag);
                        diagrams.AddNew(childDiag);
                    }
                }
                return diagrams; 
            }
        }

        public string ElementGUID
        {
            get
            {
                return EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "ea_guid")[0]; 
            }
        }


        private int elementID = -1;
        public int ElementID
        {
            get
            {
                if (elementID != -1)
                {
                    return elementID;
                }
                else
                {
                    if (sqlStringOfElement == "")
                        return -1;
                    else
                    {
                        elementID = int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "Object_ID")[0] as String);
                        return elementID;
                    }
                }
            }
        }

        SQLCollection<SQLElement> elements = null;
        public SQLCollection<SQLElement> Elements
        {
            get 
            {
                if (elements != null)
                {
                    return elements;
                }
                else
                {
                    elements = new SQLCollection<SQLElement>();

                    String[] childs = null;

                    if (repository.FullDatabaseCheckout)
                    {
                        childs = repository.t_objectParentID.GetValues(ElementID.ToString());
                    }
                    else
                    {
                        String childsQry = repository.SQLQuery("select * from t_object where ParentID = " + ElementID);
                        childs = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(childsQry, "Row").ToArray();
                    }
                    if (childs == null)
                        childs = new String[0];
                    foreach (String child in childs)
                    {
                        if (child != "")
                        {
                            SQLElement childElement = new SQLElement(repository, child);
                            elements.AddNew(childElement);
                        }
                    }

                    //this.elements = elements;

                    return elements;
                }
                
            }
        }


        String genLinks = "";
        public string Genlinks
        {
            get
            {
                if (genLinks != "")
                    return genLinks;
                else
                {
                    genLinks = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "Genlinks")[0];
                    return genLinks;
                }
            }
        }




        public bool IsSpec
        {
            get
            {
                 return Boolean.Parse(EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "IsSpec")[0]);
            }
        }

        public bool Locked
        {
            get
            {
                if(this.realElem == null)
                    realElem = this.repository.GetOriginalRepository().GetElementByID(this.ElementID);
                return realElem.Locked;
            }
        }

        private string metatype = "";

        public string MetaType
        {
            get
            {
                if (metatype != "")
                {
                    return metatype;
                }
                if (this.realElem == null)
                    realElem = this.repository.GetOriginalRepository().GetElementByID(this.ElementID);
                return realElem.MetaType;
            }
        }

        SQLCollection<SQLMethod> localMethods = null;
        public SQLCollection<SQLMethod> Methods
        {
            get 
            {
                if (localMethods != null)
                    return localMethods;
                else
                {
                    localMethods = new SQLCollection<SQLMethod>();

                    String[] ops = null;
                    if (repository.FullDatabaseCheckout)
                    {
                        ops = repository.t_operationParentID.GetValues(ElementID.ToString());
                    }
                    else
                    {
                        String opsQry = repository.SQLQuery("select * from t_operation where Object_ID = " + ElementID);
                        ops = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(opsQry, "Row").ToArray();
                    }

                    if (ops == null)
                        return localMethods;

                    foreach (String op in ops)
                    {
                        if (op != "")
                        {
                            SQLMethod newMethod = new SQLMethod(op, repository);
                            localMethods.AddNew(newMethod);
                        }
                    }
                    return localMethods;
                }
            }
        }


        String name = "";
        public string Name
        {
            get
            {
                if (name != "")
                    return name;
                else
                {
                    name = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "Name")[0] as String;
                    return name;
                }
            } 
        }

        [DebuggerHidden]
        public string Notes
        {
            get
            {
                return XmlUtil.XmlDecode(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "Note")[0]);
            }
        }

        public EA.ObjectType ObjectType
        {
            get { return EA.ObjectType.otElement; }
        }

        private int packageID = -1;
        public int PackageID
        {
            get
            {
                if (packageID != -1)
                {
                    return packageID;
                }
                String packageIDString = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "Package_ID")[0];
                if (packageIDString != "")
                {
                    packageID = int.Parse(packageIDString);
                    return packageID;
                }
                else
                    return 0;
            }
        }
        int parentID = -1;
        public int ParentID
        {
            get
            {
                if (parentID != -1)
                {
                    return parentID;
                }
                else
                {
                    parentID = int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "ParentID")[0]);
                   
                }
                return parentID;
                
            }
        }

         String stereotype = "";
        public string Stereotype
        {
            get
            {
                if (stereotype != "")
                    return stereotype;
                else
                {   
                    stereotype = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "Stereotype")[0];
                    return stereotype;
                }
            }
        }


        public int Subtype
        {
            get
            {
                return int.Parse(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "NType")[0]);
            }
        }

        SQLCollection<SQLTaggedValue> taggedValues = null;
        public SQLCollection<SQLTaggedValue> TaggedValues
        {
            get 
            {
                if (taggedValues != null)
                    return taggedValues;
                else
                {
                    taggedValues = new SQLCollection<SQLTaggedValue>();

                    String[] tags = null;
                    if (repository.FullDatabaseCheckout)
                    {
                       tags = repository.t_objectpropertiesObject_ID.GetValues(ElementID.ToString());
                    }
                    else
                    {
                        String taggedQry = repository.SQLQuery("select * from t_objectproperties where Object_ID = " + ElementID);
                        tags = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(taggedQry, "Row").ToArray();
                    }
                    if (tags == null)
                        return taggedValues;
                    foreach (String tag in tags)
                    {
                        if (tag != "")
                        {
                            SQLTaggedValue newTag = new SQLTaggedValue(repository, tag);
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
                    templatesArray = repository.t_xrefElementGUID.GetValues(ElementGUID);
                }
                else
                {
                    String taggedQry = repository.SQLQuery("select * from t_xref where Client = '" + ElementGUID+"'");
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

        private string type;
        public string Type
        {
            get
            {
                if (type != null)
                {
                    return type;
                }
                else
                {
                    type =  EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlStringOfElement, "Object_Type")[0];
                    return type;
                }
            }
        }

        public EA.Element getRealElement()
        {
            if (realElem == null)
            {
                realElem = EAUtil.sqlEAObjectToOriginalObject(repository, this) as EA.Element;
            }
            return realElem;
        }


        #endregion
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;

using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;
 

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions
{
    class AttributeValueExpression : Expression
    {
        public const String DESCRIPTION = "AttributeValueExpression";

        public String ObjectVariableNameOld { get; set; }
        public String ObjectVariableName { get; set; }
        public String ObjectVariableGUID { get; set; }
        private SQLElement ObjectVariable { get; set; }
        private SQLAttribute Attribute { get; set; }
        
        public String AttributeName { get; set; }
        public String AttributeNameOld { get; set; }
        public String AttributeGUID { get; set; }
        

        public AttributeValueExpression(SQLRepository repository)
        {
            this.Repository = repository;
            this.Type = DESCRIPTION;
        }

        public AttributeValueExpression(SQLRepository repository, SQLAttribute attribute, SQLElement objectVariable)
        {
            this.Attribute = attribute;
            this.AttributeGUID = attribute.AttributeGUID;
            this.AttributeName = attribute.Name;
            this.Type = DESCRIPTION;
            this.Repository = repository;
            this.ObjectVariableGUID = objectVariable.ElementGUID;
            this.ObjectVariable = objectVariable;
            this.ObjectVariableName = objectVariable.Name;
        }

        public override void expressionFromXmlElement(XmlElement attributeValueExpressionElement)
        {
            XmlAttribute attributeAttr = XmlUtil.getXMLAttributeWithName(attributeValueExpressionElement, "attribute");
            XmlAttribute ovAttr = XmlUtil.getXMLAttributeWithName(attributeValueExpressionElement, "object");
            XmlAttribute ovNameAttr = XmlUtil.getXMLAttributeWithName(attributeValueExpressionElement, "objectName");
            XmlAttribute attributeGuidAttr = XmlUtil.getXMLAttributeWithName(attributeValueExpressionElement, "attributeGUID");
            this.AttributeName = SDMUtil.extractNameFromURIString(attributeAttr.InnerXml);
            this.ObjectVariableGUID = ovAttr.InnerXml;
            this.ObjectVariableName = ovNameAttr.InnerXml;
            this.AttributeGUID = getGuidOfAttributeByURIString(attributeAttr.InnerXml);


        }

        public String getGuidOfAttributeByURIString(String uriString)
        {
            List<String> values = new List<String>();
            String[] splitted = uriString.Split('/');
            foreach (String entry in splitted)
            {
                if (entry != "")
                    values.Add(entry.Replace("#", ""));
            }

            String result = Repository.SQLQuery("select * from t_attribute where name = '" + this.AttributeName + "'");
            foreach (String row in EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
            {
                if (row != "")
                {
                    SQLAttribute attribute = new SQLAttribute(row, Repository);
                    SQLElement parentElement = Repository.GetElementByID(attribute.ParentID);
                    SQLPackage parentPackage = Repository.GetPackageByID(parentElement.PackageID);
                    if (parentElement.Name == values[values.Count - 2] && parentPackage.Name == values[values.Count - 3])
                    {
                        return attribute.AttributeGUID;
                    }
                }
            }
            return "";
        }


        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            base.serializeToMocaTree(actNode);
            actNode.appendChildAttribute("attributeGuid",this.AttributeGUID);
            actNode.appendChildAttribute("attributeName", this.AttributeName);
            actNode.appendChildAttribute("objectVariableGuid", this.ObjectVariableGUID);
            actNode.appendChildAttribute("objectVariableName", this.ObjectVariableName);
            return actNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.AttributeGUID = actNode.getAttributeOrCreate("attributeGuid").Value;
            this.AttributeNameOld = actNode.getAttributeOrCreate("attributeName").Value;
            this.AttributeName = this.AttributeNameOld;
            this.ObjectVariableNameOld = actNode.getAttributeOrCreate("objectVariableName").Value;
            this.ObjectVariableName = this.ObjectVariableNameOld;
            this.ObjectVariableGUID = actNode.getAttributeOrCreate("objectVariableGuid").Value;
            SQLAttribute at = Repository.GetAttributeByGuid(this.AttributeGUID);
            SQLElement ob = Repository.GetElementByGuid(this.ObjectVariableGUID);
            if(at != null)
                this.AttributeName = at.Name;
            if (ob != null)
                this.ObjectVariableName = ob.Name;
        }

        public override string ToString()
        {
            return this.ObjectVariableName + "." + this.AttributeName;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using System.Xml;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions
{
    class ObjectVariableExpression : Expression
    {
        public const String DESCRIPTION = "ObjectVariableExpression";

        public String ObjectVariableGUID { get; set; }
        public String ObjectVariableName { get; set; }
        public String ObjectVariableNameOld { get; set; }

        public ObjectVariableExpression(SQLElement objectVariableEAElement, SQLRepository repository) 
        {
            this.Repository = repository;
            this.ObjectVariableGUID = objectVariableEAElement.ElementGUID;
            this.ObjectVariableName = objectVariableEAElement.Name;
            this.Type = DESCRIPTION;
        }

        public ObjectVariableExpression(SQLRepository repository)
        {
            this.Repository = repository;
            this.Type = DESCRIPTION;
        }

        public override void expressionFromXmlElement(System.Xml.XmlElement expressionElement)
        {
            base.expressionFromXmlElement(expressionElement);
            XmlAttribute ovAttribute = XmlUtil.getXMLAttributeWithName(expressionElement, "object");
            XmlAttribute ovNameAttribute = XmlUtil.getXMLAttributeWithName(expressionElement, "objectName");
            this.ObjectVariableName = ovNameAttribute.InnerXml;
            this.ObjectVariableGUID = ovAttribute.InnerXml;
        }

      

        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            MocaNode node = base.serializeToMocaTree(actNode);
            node.appendChildAttribute("objectVariableGuid", this.ObjectVariableGUID);
            node.appendChildAttribute("objectVariableName", this.ObjectVariableName);
            return node;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.ObjectVariableGUID = actNode.getAttributeOrCreate("objectVariableGuid").Value;
            this.ObjectVariableNameOld = actNode.getAttributeOrCreate("objectVariableName").Value;
            this.ObjectVariableName = this.ObjectVariableNameOld;
            SQLElement ob = Repository.GetElementByGuid(this.ObjectVariableGUID);
            if (ob != null)
                this.ObjectVariableName = ob.Name;
        }

        public override string ToString()
        {
            return this.ObjectVariableName;
        }
    }
}

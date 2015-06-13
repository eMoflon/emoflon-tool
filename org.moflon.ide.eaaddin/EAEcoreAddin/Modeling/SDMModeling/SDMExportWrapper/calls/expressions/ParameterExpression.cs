using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.SQLWrapperClasses;

using System.Xml;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions
{
    class ParameterExpression : Expression
    {
        public const String DESCRIPTION = "ParameterExpression";

        public String ParameterGUID { get; set; }
        public String ParameterName { get; set; }
        public String ParameterNameOld { get; set; }
        public SQLMethod ParentMethod { get; set; }
        public SQLParameter Parameter { get; set; }
        
        public ParameterExpression(SQLRepository repository, SQLParameter parameter)
        {
            this.Repository = repository;
            this.Parameter = parameter;
            this.ParameterGUID = parameter.ParameterGUID;
            this.ParameterName = parameter.Name;
            this.Type = DESCRIPTION;
        }

        public ParameterExpression(SQLRepository repository)
        {
            this.Repository = repository;
            this.Type = DESCRIPTION;
        }

        #region old xml handling

        public override void expressionFromXmlElement(System.Xml.XmlElement parameterExpressionElement)
        {
            base.expressionFromXmlElement(parameterExpressionElement);
            XmlAttribute parameterAttribute = XmlUtil.getXMLAttributeWithName(parameterExpressionElement, "parameter");
            XmlAttribute parameterGUIDAttribute = XmlUtil.getXMLAttributeWithName(parameterExpressionElement, "parameterGUID");
            
            this.ParameterName = SDMUtil.extractNameFromURIString(parameterAttribute.InnerXml);
            this.ParameterGUID = parameterGUIDAttribute.InnerXml;
        }


        #endregion


        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            MocaNode node = base.serializeToMocaTree(actNode);
            node.appendChildAttribute("parameterGuid", this.ParameterGUID);
            node.appendChildAttribute("parameterName", this.ParameterName);
            return node;

        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.ParameterGUID = actNode.getAttributeOrCreate("parameterGuid").Value;
            this.ParameterNameOld = actNode.getAttributeOrCreate("parameterName").Value;
            this.ParameterName = this.ParameterNameOld;
            SQLParameter param = Repository.GetParameterByGuid(this.ParameterGUID);
            if (param != null)
                this.ParameterName = param.Name;
        }

        public override string ToString()
        {
            return this.ParameterName;
        }
    }
}

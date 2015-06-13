using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;

using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns
{
    public class AttributeAssignment : MocaSerializableElement
    {

        public SQLAttribute Attribute { get; set; }
        //public String AttributeString { get; set; }
        public String AttributeGUID { get; set; }
        public String AttributeName { get; set; }
        public Expression ValueExpression { get; set; }
        public String AttributeNameOld { get; set; }
        public String OvName { get; set; }
        public ObjectVariable Ov { get; set; }

        public AttributeAssignment(SQLRepository repository,ObjectVariable ov, SQLAttribute attribute)
        {
            this.Attribute = attribute;
            this.AttributeGUID = attribute.AttributeGUID;
            this.AttributeName = attribute.Name;
            this.Repository = repository;
            this.OvName = ov.Name;
        }

        public AttributeAssignment(SQLRepository repository)
        {
            this.Repository = repository;
        }


        public override String ToString()
        {
            return this.OvName + "." + this.AttributeName + " := " + ValueExpression.ToString();
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            actNode.appendChildAttribute("attributeName",this.AttributeName);
            actNode.appendChildAttribute("attributeGuid", this.AttributeGUID);
            MocaNode valueExpNode = actNode.appendChildNode("valueExpression");
            this.ValueExpression.serializeToMocaTree(valueExpNode);
            return actNode;
        }

        protected virtual void setOvName(MocaNode actNode)
        {
            MocaNode tempNode = actNode.Parent;
            if (tempNode != null)
            {
                while (tempNode != null && tempNode.Name != typeof(ObjectVariable).Name && tempNode.Name != typeof(TGGObjectVariable).Name && tempNode.Name != typeof(TGGCorrespondence).Name)
                {
                    tempNode = tempNode.Parent;
                }
                if (tempNode != null)
                {
                    MocaAttribute nameAttr = tempNode.getAttributeOrCreate("name");
                    if (nameAttr != null)
                        this.OvName = nameAttr.Value;
                }
            }
        }

        private void setOvGuid(MocaNode actNode)
        {
            MocaNode tempNode = actNode.Parent;
            if (tempNode != null)
            {
                while (tempNode != null && tempNode.Name != typeof(ObjectVariable).Name && tempNode.Name != typeof(TGGObjectVariable).Name && tempNode.Name != typeof(TGGCorrespondence).Name)
                {
                    tempNode = tempNode.Parent;
                }
                if (tempNode != null)
                {
                    MocaAttribute guidAttr = tempNode.getAttributeOrCreate(Main.GuidStringName);
                    if (guidAttr != null)
                        this.OvGuid = guidAttr.Value;
                }
            }
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            setOvName(actNode);
            setOvGuid(actNode);
            this.AttributeGUID = actNode.getAttributeOrCreate("attributeGuid").Value;
            this.AttributeNameOld = actNode.getAttributeOrCreate("attributeName").Value;
            this.AttributeName = this.AttributeNameOld;
            MocaNode valueExpressionNode = actNode.getChildNodeWithName("valueExpression");
            this.ValueExpression = Expression.createExpression(valueExpressionNode.getAttributeOrCreate("type").Value, Repository);
            this.ValueExpression.deserializeFromMocaTree(valueExpressionNode);
            SQLAttribute attribute = Repository.GetAttributeByGuid(this.AttributeGUID);
            if (attribute != null)
                this.AttributeName = attribute.Name;
        }



        public string OvGuid { get; set; }
    }
}

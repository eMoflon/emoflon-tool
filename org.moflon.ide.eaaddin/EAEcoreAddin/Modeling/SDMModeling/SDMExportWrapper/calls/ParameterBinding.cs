using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;

using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls

{
    public class ParameterBinding : MocaSerializableElement
    {
        public Expression ValueExpression { get; set; }
        public SQLParameter Parameter { get; set; }
        public String ParameterName { get; set; }
        public String ParameterGuid { get; set; }

        public String ParameterType { get; set; }


        public ParameterBinding(SQLRepository repository ,  SQLParameter eaParameter)
        {
            this.Name = eaParameter.Name;
            this.Repository = repository;
            this.Parameter = eaParameter;
            this.ParameterGuid = eaParameter.ParameterGUID;
            this.ParameterType = eaParameter.Type;
        }

        public ParameterBinding(SQLRepository repository) 
        {
            this.Repository = repository;
        }




        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            MocaNode valueExpressionNode = actNode.appendChildNode("valueExpression");
            if(this.ValueExpression != null)
                this.ValueExpression.serializeToMocaTree(valueExpressionNode);

            actNode.appendChildAttribute("parameterGuid", this.ParameterGuid);
            actNode.appendChildAttribute("parameterType", this.ParameterType);

            return actNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            MocaNode valueExpressionNode = actNode.getChildNodeWithName("valueExpression");
            this.ValueExpression = Expression.createExpression(valueExpressionNode.getAttributeOrCreate("type").Value, Repository);
            this.ValueExpression.deserializeFromMocaTree(valueExpressionNode);
            this.ParameterGuid = actNode.getAttributeOrCreate("parameterGuid").Value;
            this.ParameterType = actNode.getAttributeOrCreate("parameterType").Value;
        }

        public String parameterBindingToString()
        {
            return this.ValueExpression.ToString();
        }
    }
}

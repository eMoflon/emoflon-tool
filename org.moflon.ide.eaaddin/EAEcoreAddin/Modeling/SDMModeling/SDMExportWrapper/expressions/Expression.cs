using System;
using System.Collections;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;

using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions
{
    public abstract class Expression : MocaSerializableElement
    {
       



        public static Expression createExpression(String type, SQLRepository repository) 
        {
            Expression newExpression = null;
            if (type.ToLower() == "attributevalueexpression")
                newExpression = new AttributeValueExpression(repository);
            else if (type.ToLower() == "objectvariableexpression")
                newExpression = new ObjectVariableExpression(repository);
            else if (type.ToLower() == "parameterexpression")
                newExpression = new ParameterExpression(repository);
            else if (type.ToLower() == "comparisonexpression")
                newExpression = new ComparisonExpression(repository);
            else if (type.ToLower() == "methodcallexpression")
                newExpression = new MethodCallExpression(repository);
            else if (type.ToLower() == "literalexpression")
                newExpression = new LiteralExpression();
            else if (type.ToLower() == "textualexpression")
                newExpression = new TextualExpression();

            return newExpression;
        }

        public virtual void expressionFromXmlElement(XmlElement expressionElement)
        {
        }

        
        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            MocaNode exp = actNode;
            exp.appendChildAttribute("type",this.Type);
            return exp;
        }

    }
}

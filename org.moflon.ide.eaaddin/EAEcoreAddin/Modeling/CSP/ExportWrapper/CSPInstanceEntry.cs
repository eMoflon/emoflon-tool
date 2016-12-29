using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Modeling.CSP.ExportWrapper
{
    class CSPInstanceEntry : MocaSerializableElement
    {
        public List<Expression> typedInExpressions { get; set; }
        public String constraintName { get; set; }

        public CSPInstanceEntry(SQLRepository repository)
        {
            this.Repository = repository;
            constraintName = "";
            typedInExpressions = new List<Expression>();
        }

        public override string ToString()
        {
            String value = constraintName + "(";
            foreach (Expression typedExpression in typedInExpressions)
            {
                value += typedExpression.ToString() + ", ";
            }
            value = value.Substring(0, value.Length - 2);
            value += ")";
            return value;
        }
    
        public override Serialization.MocaTree.MocaNode  serializeToMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            actNode.appendChildAttribute("constraintName", constraintName);

            MocaNode exprNode = actNode.appendChildNode("expressions");

            foreach (Expression expr in typedInExpressions)
            {
                MocaNode expressionNode = exprNode.appendChildNode("Expression");
                expr.serializeToMocaTree(expressionNode);
            }

            return actNode;
        }

        public override void  deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            constraintName = actNode.getAttributeOrCreate("constraintName").Value;
            
            foreach (MocaNode exprNode in actNode.getChildNodeWithName("expressions").Children)
            {
                Expression expression = Expression.createExpression(exprNode.getAttributeOrCreate("type").Value, Repository);
                expression.deserializeFromMocaTree(exprNode);
                typedInExpressions.Add(expression);
            }
        }
}
}

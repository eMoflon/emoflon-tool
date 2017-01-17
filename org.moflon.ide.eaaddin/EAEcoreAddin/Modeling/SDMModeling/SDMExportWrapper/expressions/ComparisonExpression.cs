using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using System.Xml;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree.Util;


namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions
{
    public class ComparisonExpression : Expression
    {

        public const String DESCRIPTION = "ComparisonExpression";

        public ComparingOperator Operator { get; set; }
        public Expression LeftExpression { get; set; }
        public Expression RightExpression { get; set; }

        public ComparisonExpression(SQLRepository repository)
        {
            this.Type = DESCRIPTION;
            this.Repository = repository;
        }
            
        #region old xml handling

        public override void expressionFromXmlElement(XmlElement expressionElement)
        {
            base.expressionFromXmlElement(expressionElement);
            XmlAttribute operatorAttribute = XmlUtil.getXMLAttributeWithName(expressionElement, "operator");
            this.Operator = (ComparingOperator)Enum.Parse(typeof(ComparingOperator), operatorAttribute.InnerXml);
            foreach (XmlElement childElement in expressionElement.ChildNodes)
            {
                if (childElement.Name == "leftExpression")
                {
                    XmlElement leftExpressionElement = childElement;
                    String leftExprTypeName = XmlUtil.getXsiTypeName(leftExpressionElement);
                    this.LeftExpression = Expression.createExpression(leftExprTypeName, this.Repository);
                    this.LeftExpression.expressionFromXmlElement(leftExpressionElement);
                }
                else if (childElement.Name == "rightExpression")
                {
                    XmlElement rightExpressionElement = childElement;
                    String rightExprTypeName = XmlUtil.getXsiTypeName(rightExpressionElement);
                    this.RightExpression = Expression.createExpression(rightExprTypeName, this.Repository);
                    this.RightExpression.expressionFromXmlElement(rightExpressionElement);
                }
            }
        }

        #endregion


        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            MocaNode compExp = base.serializeToMocaTree(actNode);
            compExp.appendChildAttribute("operator", this.Operator.ToString().ToLower());
            MocaNode leftNode = compExp.appendChildNode("leftExpression");
            MocaNode rightNode = compExp.appendChildNode("rightExpression");
            this.LeftExpression.serializeToMocaTree(leftNode);
            this.RightExpression.serializeToMocaTree(rightNode);
            return compExp;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.Operator = (ComparingOperator)Enum.Parse(typeof(ComparingOperator), actNode.getAttributeOrCreate("operator").Value.ToUpper());
            MocaNode leftNode = actNode.getChildNodeWithName("leftExpression");
            this.LeftExpression = Expression.createExpression(leftNode.getAttributeOrCreate("type").Value,Repository);
            this.LeftExpression.deserializeFromMocaTree(leftNode);
            MocaNode rightNode = actNode.getChildNodeWithName("rightExpression");
            this.RightExpression = Expression.createExpression(rightNode.getAttributeOrCreate("type").Value, Repository);
            this.RightExpression.deserializeFromMocaTree(rightNode);
        }

        public String operatorToString(ComparingOperator compOperator)
        {
            String operatorString = "";
            if (compOperator == ComparingOperator.EQUAL)
                operatorString = " == ";
            else if (compOperator == ComparingOperator.GREATER)
                operatorString = " > ";
            else if (compOperator == ComparingOperator.GREATER_OR_EQUAL)
                operatorString = " >= ";
            else if (compOperator == ComparingOperator.LESS)
                operatorString = " < ";
            else if (compOperator == ComparingOperator.LESS_OR_EQUAL)
                operatorString = " <= ";
            else if (compOperator == ComparingOperator.UNEQUAL)
                operatorString = " != ";
            return operatorString;
        }

        public String StringToOperator(String value)
        {
            throw new Exception();
        }

        public override string ToString()
        {
            
            return LeftExpression.ToString() + this.operatorToString(this.Operator) + RightExpression.ToString();
        }
    }
}

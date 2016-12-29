using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;

using System.Xml;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions
{
    class TextualExpression : Expression
    {
        public const String DESCRIPTION = "TextualExpression";

        public String ExpressionText { get; set; }

        public TextualExpression(String expressionText)
        {
            this.ExpressionText = expressionText;
            this.Type = DESCRIPTION;
        }

        public TextualExpression()
        {
            this.Type = DESCRIPTION;
        }

        #region new xml handling

        public override void expressionFromXmlElement(System.Xml.XmlElement expressionElement)
        {
            base.expressionFromXmlElement(expressionElement);
            XmlAttribute expressionTextAttribute = XmlUtil.getXMLAttributeWithName(expressionElement, "expressionText");
            this.ExpressionText = expressionTextAttribute.InnerXml;
        }


        #endregion


        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            MocaNode node = base.serializeToMocaTree(actNode);
            node.appendChildAttribute("expressionText", this.ExpressionText);
            return node;

        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.ExpressionText = actNode.getAttributeOrCreate("expressionText").Value;
        }

        public override string ToString()
        {
            return this.ExpressionText;
        }
    }
}

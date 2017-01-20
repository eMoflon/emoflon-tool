using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using System.Xml;

using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions
{
    public class LiteralExpression : Expression
    {
        public const String DESCRIPTION = "LiteralExpression";


        public String Value { get; set; }
        public String Datatype { get; set; }

        public LiteralExpression(String value)
        {
            this.Value = value;
            this.Type = DESCRIPTION;
            setDatatype();
        }

        public LiteralExpression()
        {
            this.Type = DESCRIPTION;
        }

        public void setDatatype()
        {
            int a;
            bool b;
            double c;
            float d;
            char e;

            if (int.TryParse(Value, out a))
            {
                Datatype = "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EInt";
            }
            else if (bool.TryParse(Value, out b))
            {
                Datatype = "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EBoolean";
            }
            else if (double.TryParse(Value, out c))
            {
                Datatype = "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EDouble";
            }
            else if (float.TryParse(Value, out d))
            {
                Datatype = "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EFloat";
            }
            else if (char.TryParse(Value, out e))
            {
                Datatype = "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EChar";
            }
            //string which will be simply taken
            else
            {  
                Datatype = "";
            }
        }

        #region new xml handling

        public override void expressionFromXmlElement(XmlElement expressionElement)
        {
            base.expressionFromXmlElement(expressionElement);
            XmlAttribute typeAttribute = XmlUtil.getXMLAttributeWithName(expressionElement, "type");
            XmlAttribute valueAttribute = XmlUtil.getXMLAttributeWithName(expressionElement, "value");
            this.Value = valueAttribute.InnerXml;
            this.Datatype = typeAttribute.InnerXml;
        }

        #endregion


        public override Serialization.MocaTree.MocaNode serializeToMocaTree(MocaNode actNode)
        {
            MocaNode literalExpressionNode = base.serializeToMocaTree(actNode);
            literalExpressionNode.appendChildAttribute("value",this.Value);
            literalExpressionNode.appendChildAttribute("datatype", this.Datatype);

            return literalExpressionNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            MocaAttribute valueAttribute = actNode.getAttributeOrCreate("value");
            MocaAttribute datatypeAttribute = actNode.getAttributeOrCreate("datatype");
            this.Value = valueAttribute.Value;
            this.Datatype = datatypeAttribute.Value;
        }

        public override string ToString()
        {
            return this.Value;
        }
    }
}

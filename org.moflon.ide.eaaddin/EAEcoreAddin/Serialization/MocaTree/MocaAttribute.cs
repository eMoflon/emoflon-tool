using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

using EAEcoreAddin.Serialization.MocaTree.Util;
using System.Web;
using System.Diagnostics;

namespace EAEcoreAddin.Serialization.MocaTree
{
    public class MocaAttribute : MocaTreeElement
    {
        public String Value { get; set; }
        public MocaNode Parent { get; set; }

        private MocaAttribute(String attributeName, String attributeValue)
        {
            this.Name = attributeName;
            this.Value = attributeValue;
            this.Index = this.Parent.Attributes.IndexOf(this);
            if (this.Index == -1)
                this.Index = 0;
        }

        public MocaAttribute() 
        {
            Value = "";
            Name = "";
            Index = 0;
        }

        [DebuggerHidden]
        public override XmlElement serializeToXmlTree(XmlDocument xmlDocument)
        {
            XmlElement attributeNode = xmlDocument.CreateElement(MocaTreeUtil.AttributeNodeDefaultName);
            XmlUtil.appendNewSimpleAttribute(MocaTreeUtil.NameAttributeName, this.Name, attributeNode, xmlDocument);
            XmlUtil.appendNewSimpleAttribute(MocaTreeUtil.ValueAttributeName, this.Value, attributeNode, xmlDocument);
            XmlUtil.appendNewSimpleAttribute(MocaTreeUtil.IndexAttributeName, this.Index.ToString(), attributeNode, xmlDocument);
            
            return attributeNode;
        }

        //[DebuggerHidden]

        public override void deserializeFromXmlTree(XmlElement mainNode)
        {
            this.Name = mainNode.Attributes[MocaTreeUtil.NameAttributeName].InnerXml;
            
            XmlAttribute valueAttribute = XmlUtil.getXMLAttributeWithName(mainNode, MocaTreeUtil.ValueAttributeName);
            if (valueAttribute != null)
            {
                Value = XmlUtil.XmlDecode(valueAttribute.InnerXml);
            }
            XmlAttribute indexAttribute = XmlUtil.getXMLAttributeWithName(mainNode, MocaTreeUtil.IndexAttributeName);
            if (indexAttribute != null)
            {
                Index = int.Parse(indexAttribute.InnerXml) | 0;
            }

        }
    }
}

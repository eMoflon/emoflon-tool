using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

using EAEcoreAddin.Serialization.MocaTree.Util;
using System.Diagnostics;

namespace EAEcoreAddin.Serialization.MocaTree
{
    public class MocaNode : MocaText
    {

        public static readonly String XsiType = "MocaTree:Node";
        public static readonly String ChildNodeDefaultName = "children";
        
        
        public List<MocaAttribute> Attributes { get; set; }
        public List<MocaNode> Children { get; set; }
        public MocaFile Files { get; set; }
        public MocaNode Parent { get; set; }

        public MocaNode(String name)
        {
            this.Attributes = new List<MocaAttribute>();
            this.Children = new List<MocaNode>();
            this.Name = name;
        }

        public MocaNode() 
        {
            this.Attributes = new List<MocaAttribute>();
            this.Children = new List<MocaNode>();
            this.Name = "";
        }

      //  [DebuggerHidden]
        public override XmlElement serializeToXmlTree(XmlDocument xmlDocument)
        {
            XmlElement nodeElement = xmlDocument.CreateElement(ChildNodeDefaultName);
            XmlUtil.appendNewSimpleAttribute(MocaTreeUtil.NameAttributeName, this.Name, nodeElement, xmlDocument);
            XmlUtil.appendNewSimpleAttribute(MocaTreeUtil.IndexAttributeName, this.Index.ToString(), nodeElement, xmlDocument);
            XmlUtil.appendNewXsiTypeAttribute(XsiType, nodeElement, xmlDocument);
            foreach (MocaNode children in this.Children)
            {
                XmlElement childNode = children.serializeToXmlTree(xmlDocument);
                nodeElement.AppendChild(childNode);
            }

            foreach (MocaAttribute attribute in this.Attributes)
            {
                XmlElement attributeNode = attribute.serializeToXmlTree(xmlDocument);
                nodeElement.AppendChild(attributeNode);
            }

            return nodeElement;
        }

       // [DebuggerHidden]

        public override void deserializeFromXmlTree(XmlElement nodeNode)
        {
            this.Name = nodeNode.Attributes[MocaTreeUtil.NameAttributeName].InnerXml;
            XmlAttribute indexAttribute = XmlUtil.getXMLAttributeWithName(nodeNode, MocaTreeUtil.IndexAttributeName);
            if (indexAttribute != null)
            {
                Index = int.Parse(indexAttribute.InnerXml) | 0;
            }

            foreach (XmlElement child in nodeNode.ChildNodes)
            {
                if (child.Name == MocaTreeUtil.AttributeNodeDefaultName)
                {
                    MocaAttribute newAttribute = new MocaAttribute();
                    newAttribute.deserializeFromXmlTree(child);
                    this.Attributes.Add(newAttribute);
                    newAttribute.Parent = this;
                }
                else if (child.Name == MocaNode.ChildNodeDefaultName)
                {
                    MocaNode newChild = new MocaNode();
                    newChild.deserializeFromXmlTree(child);
                    this.Children.Add(newChild);
                    newChild.Parent = this;
                }
            }
        }


        public MocaAttribute getAttribute(String attributeName)
        {
            MocaAttribute attribute = null;
            foreach (MocaAttribute mocaAttribute in this.Attributes)
            {
                if (mocaAttribute.Name == attributeName)
                {
                    attribute = mocaAttribute;
                }
            }
            return attribute;
        }

        public Boolean hasAllAttributes(List<String> expectedAttributeNames)
        {
            foreach (String expectedAttributeName in expectedAttributeNames)
            {
                Boolean found = false;
                foreach (MocaAttribute mocaAttribute in this.Attributes)
                {
                    if (expectedAttributeName == mocaAttribute.Name)
                    {
                        found = true;
                        break;
                    }
                }
                if (!found)
                    return false;
            }
            return true;
        }

        public MocaAttribute getAttributeOrCreate(String attributeName)
        {
            MocaAttribute attribute = getAttribute(attributeName);

            if (attribute == null)
            {
                attribute = this.appendChildAttribute(attributeName, "");
            }

            return attribute;
        
        }

        public MocaNode getChildNodeWithName(String childName)
        {
            MocaNode childToFind = null;
            foreach (MocaNode mocaChildNode in this.Children)
            {   
                if (mocaChildNode.Name == childName)
                    childToFind = mocaChildNode;
            }
            return childToFind;
        }

        public MocaAttribute appendChildAttribute(MocaAttribute attribute)
        {
            return appendChildAttribute(attribute.Name, attribute.Value);
        }

        public MocaAttribute appendChildAttribute(String attributeName, String attributeValue)
        {
            MocaAttribute mocaAttribute = null;

            if (attributeValue == null)
            {
                attributeValue = "";
            }

            MocaAttribute existingAttribute = this.getAttribute(attributeName);
            if (existingAttribute != null)
            {
                mocaAttribute = existingAttribute;
                mocaAttribute.Value = attributeValue;                
            }
            else
            {
                mocaAttribute = new MocaAttribute();
                mocaAttribute.Name = attributeName;
                mocaAttribute.Parent = this;
                mocaAttribute.Value = attributeValue;
                this.Attributes.Add(mocaAttribute);
                mocaAttribute.Index = this.Attributes.IndexOf(mocaAttribute);
            }
            return mocaAttribute;
        }
        public MocaNode appendChildNode(String attributeName)
        {
            MocaNode mocaNode = new MocaNode();
            mocaNode.Name = attributeName;
            this.Children.Add(mocaNode);
            mocaNode.Parent = this;
            mocaNode.Index = this.Children.IndexOf(mocaNode);
            return mocaNode;
         }
        public MocaNode appendChildNode(MocaNode nodeToAppend)
        {
            MocaNode mocaNode = nodeToAppend;
            this.Children.Add(mocaNode);
            mocaNode.Parent = this;
            mocaNode.Index = this.Children.IndexOf(mocaNode);
            return mocaNode;
        }

    }
}

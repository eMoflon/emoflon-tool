using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.IO;
using EAEcoreAddin.Modeling.SDMModeling;


namespace EAEcoreAddin.Serialization.MocaTree.Util
{
    public class MocaTreeUtil
    {
        
        public static readonly String NodeDefaultName = "node";
        public static readonly String AttributeNodeDefaultName = "attribute";
        public static readonly String NameAttributeName = "name";
        public static readonly String ValueAttributeName = "value";
        public static readonly String IndexAttributeName = "index";
        public static readonly String TopLevelNodePrefix = "MocaTree";
        public static readonly String TopLevelNodeNamespaceURI = "platform:/plugin/MocaTree/model/MocaTree.ecore";
        public static readonly String TopLevelNodeName = "Node";
        public static readonly String XsiName = "xsi";
        public static readonly String XsiDeclarationUrl = "http://www.w3.org/2001/XMLSchema-instance";
        public static readonly String XmlnsName = "xmlns";
        public static readonly String XmlnsDeclarationUrl = "http://www.w3.org/2000/xmlns/";
        public static readonly String XmiNameSpace = "http://www.omg.org/XMI";


        private static void appendNewSimpleAttribute(String attrName, String attrValue, XmlElement xmlElement, XmlDocument xmlDocument)
        {
            XmlAttribute newAtt = xmlDocument.CreateAttribute(attrName);
            newAtt.InnerXml = attrValue;
            xmlElement.Attributes.Append(newAtt);
        }


        public static XmlDocument createMocaXMLDoc()
        {   
            Dictionary<String, String> xmlnsDeclarations = new Dictionary<String, String>();
            Dictionary<String, String> xsiInfos = new Dictionary<String, String>();
           
            xmlnsDeclarations.Add("xmi", XmiNameSpace);
            xmlnsDeclarations.Add("xsi", XsiDeclarationUrl);

            XmlDocument newDocument = new XmlDocument();
            XmlDeclaration xmlDecl = newDocument.CreateXmlDeclaration("1.0", "ASCII", "");

            XmlElement topLevelNode = newDocument.CreateElement(TopLevelNodePrefix, TopLevelNodeName, TopLevelNodeNamespaceURI);

            foreach (String xmlnsDeclarationName in xmlnsDeclarations.Keys)
            {
                String xmlnsDeclarationURI = xmlnsDeclarations[xmlnsDeclarationName];
                XmlAttribute xmlnsDecleration = newDocument.CreateAttribute(XmlnsName, xmlnsDeclarationName, XmlnsDeclarationUrl);
                xmlnsDecleration.InnerXml = xmlnsDeclarationURI;
                topLevelNode.SetAttributeNode(xmlnsDecleration);
            }

            foreach (String xsiInfoName in xsiInfos.Keys)
            {
                String xsiInfoValue = xsiInfos[xsiInfoName];
                XmlAttribute xsiInfo = newDocument.CreateAttribute(XsiName, xsiInfoName, XsiDeclarationUrl);
                xsiInfo.InnerXml = xsiInfoValue;
                topLevelNode.SetAttributeNode(xsiInfo);
            }
            newDocument.AppendChild(topLevelNode);
            return newDocument;

        }


        public static String xmlDocumentToString(XmlDocument xmlDoc)
        {
            StringWriter sw = new StringWriter();
            XmlTextWriter xw = new XmlTextWriter(sw);
            xw.Formatting = Formatting.Indented;
            xmlDoc.WriteTo(xw);
            return sw.ToString();
        }

        public static XmlDocument stringToXmlDocument(String xmlString)
        {
            XmlDocument domDoc = new XmlDocument();
            StringReader sw = new StringReader(xmlString);
            XmlTextReader xw = new XmlTextReader(sw);
            domDoc.Load(xw);
            return domDoc;
        }

        public static MocaNode mocaNodeFromXmlString(String xmlString)
        {
            XmlDocument xmlDoc = MocaTreeUtil.stringToXmlDocument(xmlString);
            MocaNode mocaNode = new MocaNode();
            mocaNode.deserializeFromXmlTree(xmlDoc.DocumentElement.FirstChild as XmlElement);
            return mocaNode;
        }

        public static String mocaNodeToString(MocaNode mocaNode)
        {
            //create xml stub with a single root node
            XmlDocument xmlDocStub = MocaTreeUtil.createMocaXMLDoc();
            //parse this object to moca element tree
            MocaNode treeElement = mocaNode;
            //parse moca element to xmlElement tree
            XmlElement serializedMocaRoot = treeElement.serializeToXmlTree(xmlDocStub);

            //append new xmlElement to xmlDocument
            xmlDocStub.FirstChild.AppendChild(serializedMocaRoot);
            return MocaTreeUtil.xmlDocumentToString(xmlDocStub);
        }

    }
}

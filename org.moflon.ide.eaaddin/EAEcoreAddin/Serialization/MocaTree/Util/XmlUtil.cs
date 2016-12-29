using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.IO;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Web;
using System.Diagnostics;


namespace EAEcoreAddin.Serialization.MocaTree.Util
{
    public class XmlUtil
    {
        
       
        public static XmlAttribute getXMLAttributeWithName(XmlNode xmlNode, String name)
        {
            XmlAttribute atttribute = null;
            foreach (XmlAttribute actAttribute in xmlNode.Attributes)
            {
                if (actAttribute.Name == name)
                    atttribute = actAttribute;
            }
            return atttribute;
        }   

        public static void appendNewXsiTypeAttribute(String attrValue, XmlElement xmlElement, XmlDocument xmlDocument)
        {
            XmlAttribute typeAttr = xmlDocument.CreateAttribute("xsi", "type", "http://www.w3.org/2001/XMLSchema-instance");
            typeAttr.InnerXml = attrValue;
            xmlElement.Attributes.Append(typeAttr);
        }

        [DebuggerHidden]
        public static void appendNewSimpleAttribute(String attrName, String attrValue, XmlElement xmlElement, XmlDocument xmlDocument)
        {
            
            XmlAttribute newAtt = xmlDocument.CreateAttribute(attrName);
            newAtt.InnerXml = XmlUtil.XmlEncode(attrValue);
            xmlElement.Attributes.Append(newAtt);
        }

        public static string XmlEncode(string value)
        {
            return HttpUtility.HtmlEncode(value);

        }

        public static string XmlDecode(string value)
        {
            return HttpUtility.HtmlDecode(value);
        }

        public void addValueToSimpleAttributesInnerXML(String attrName, String attrValue, XmlElement xmlElement)
        {
            XmlAttribute editAtt = getXMLAttributeWithName(xmlElement, attrName);
            editAtt.InnerXml = editAtt.InnerXml + " " + attrValue;
        }

        
        public void setExistingSimpleAttribute(String attName, String attValue, XmlElement xmlElement)
        {
            XmlAttribute attribute = getXMLAttributeWithName(xmlElement, attName);
            attribute.Value = attValue;
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

        public static String getXsiTypeName(XmlNode xmlElement)
        {
            XmlAttribute xmlAttribute = getXMLAttributeWithName(xmlElement, "xsi:type");
            String name = xmlAttribute.InnerXml.Split(':')[1];
            return name;
        }
        

    }
}

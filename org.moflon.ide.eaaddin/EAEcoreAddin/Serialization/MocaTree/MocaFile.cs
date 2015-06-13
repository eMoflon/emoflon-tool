using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;


namespace EAEcoreAddin.Serialization.MocaTree
{
    public class MocaFile : MocaTreeElement
    {
        public static readonly String FolderDefaultName = "folder";

        public MocaFolder Folder {get;set;}
        public MocaNode RootNode { get; set; }

        public override System.Xml.XmlElement serializeToXmlTree(System.Xml.XmlDocument xmlDocument)
        {
            XmlElement fileElement = xmlDocument.CreateElement(FolderDefaultName);
            XmlUtil.appendNewSimpleAttribute(MocaTreeUtil.NameAttributeName, this.Name, fileElement, xmlDocument);
            XmlElement rootNodeElement = RootNode.serializeToXmlTree(xmlDocument);
            fileElement.AppendChild(rootNodeElement);
            return fileElement;
        }

        public override void deserializeFromXmlTree(System.Xml.XmlElement mainNode)
        {
            throw new NotImplementedException();
        }
    }
}

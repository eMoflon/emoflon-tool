using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;


namespace EAEcoreAddin.Serialization.MocaTree
{
    public class MocaFolder : MocaTreeElement
    {
        List<MocaFolder> subFolder { get; set; }
        MocaFolder parentFolder { get; set; }
        List<MocaFile> File { get; set; }

        public override System.Xml.XmlElement serializeToXmlTree(System.Xml.XmlDocument xmlDocument)
        {
            XmlElement folderElement = xmlDocument.CreateElement("folder");
            XmlUtil.appendNewSimpleAttribute("name", this.Name, folderElement, xmlDocument);

            foreach(MocaFile file in this.File) 
            {
                XmlElement fileElement = file.serializeToXmlTree(xmlDocument);
                folderElement.AppendChild(fileElement);
            }
            return folderElement;
        }

        public override void deserializeFromXmlTree(System.Xml.XmlElement mainNode)
        {
            throw new NotImplementedException();
        }
    }
}

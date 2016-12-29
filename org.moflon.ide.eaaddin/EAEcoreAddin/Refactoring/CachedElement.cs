using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Refactoring
{
    public abstract class CachedElement
    {
        public SQLElement element;
        public SQLRepository sqlRepository;

        public abstract MocaNode serializeToMocaTree();

        public void getElement(String GUID, SQLRepository repository)
        {
            this.sqlRepository = repository;
            this.element = this.sqlRepository.GetElementByGuid(GUID);
        }

        public String getXMLDocumentString()
        {
            //create xml stub with a single root node
            XmlDocument xmlDocStub = MocaTreeUtil.createMocaXMLDoc();
            //parse this object to moca element tree
            MocaNode treeElement = this.serializeToMocaTree();

            //parse moca element to xmlElement tree
            XmlElement serializedMocaRoot = treeElement.serializeToXmlTree(xmlDocStub);

            //append new xmlElement to xmlDocument
            xmlDocStub.FirstChild.AppendChild(serializedMocaRoot);

            String documentString = MocaTreeUtil.xmlDocumentToString(xmlDocStub);

            return documentString;
        }

        public void saveElementToEATaggedValue(Boolean updateEaGui)
        {
            EAEcoreAddin.Util.EAUtil.setTaggedValueNotes(this.sqlRepository, this.element, Main.MoflonChangesTreeTaggedValueName, getXMLDocumentString());
        }
    }
}

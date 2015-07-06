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
        public SQLRepository repository;

        public abstract MocaNode serializeToMocaTree();
        public abstract void cache();

        public void getElement(String GUID, EA.Repository Repository)
        {
            this.repository = new SQLRepository(Repository, false);
            this.element = this.repository.GetElementByGuid(GUID);
        }

        public void saveElementToEATaggedValue()
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

            EAEcoreAddin.Util.EAUtil.setTaggedValueNotes(this.repository, this.element, Main.MoflonRefactorTreeTaggedValueName, documentString);

        }
    }
}

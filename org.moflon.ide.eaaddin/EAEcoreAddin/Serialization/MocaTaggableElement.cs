using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Util;
using EAEcoreAddin.Persistency;

namespace EAEcoreAddin.Serialization
{
    public abstract class MocaTaggableElement : MocaSerializableElement
    {

        private const String IGNORED_TAG_NAME = "Moflon::Ignored";

        /// <summary>
        /// The EA Object the TaggedValue should be saved to
        /// </summary>
        /// <returns></returns>
        public abstract Object getObjectToBeTagged();

        public abstract void refreshSQLObject();


        /// <summary>
        /// Serializes the current Object 1. to MocaTree and 2. to XmlDocument and saves the XmlDocument 
        /// represented as String to a TaggedValue. Last some EA GUI stuff is done which is used to make sure that
        /// the visualization in EA works correctly.
        /// </summary>
        public void saveTreeToEATaggedValue(Boolean updateEaGui)
        {
            String documentString = "";
            if (!this.isIgnored())
            {

                //create xml stub with a single root node
                XmlDocument xmlDocStub = MocaTreeUtil.createMocaXMLDoc();
                //parse this object to moca element tree
                MocaNode treeElement = this.serializeToMocaTree();

                //parse moca element to xmlElement tree
                XmlElement serializedMocaRoot = treeElement.serializeToXmlTree(xmlDocStub);

                //append new xmlElement to xmlDocument
                xmlDocStub.FirstChild.AppendChild(serializedMocaRoot);

                documentString = MocaTreeUtil.xmlDocumentToString(xmlDocStub);
            }

            if (Import.MainImport.ImportBusy)
            {
                foreach (String oldGuid in Import.MainImport.getInstance(Repository, null).OldGuidToNewGuid.Keys)
                {
                    documentString = documentString.Replace(oldGuid, Import.MainImport.getInstance(Repository, null).OldGuidToNewGuid[oldGuid]);
                }
            }
            if (!isLocked())
            {
                EAEcoreAddin.Util.EAUtil.setTaggedValueNotes(Repository, getObjectToBeTagged(), Main.MoflonExportTreeTaggedValueName, documentString);
                if (updateEaGui)
                    doEaGuiStuff();
            }

            refreshSQLObject();

        }

        public Boolean isIgnored()
        {
            Object taggedObject = getObjectToBeTagged();
            if (taggedObject is EA.Element)
            {
                EA.TaggedValue taggedValue = EAUtil.findTaggedValue(taggedObject as EA.Element, IGNORED_TAG_NAME);
                if (taggedValue != null && "true".Equals(taggedValue.Value))
                {
                    return true;
                }
            }
            else if (taggedObject is SQLElement)
            {
                return isIgnored(taggedObject as SQLElement);
            }
            return false;
        }

        public static bool isIgnored(SQLElement taggedObject)
        {
            SQLTaggedValue taggedValue = EAUtil.findTaggedValue(taggedObject, IGNORED_TAG_NAME);
            if (taggedValue != null && "true".Equals(taggedValue.Value))
            {
                return true;
            }
            return false;
        }

        private Boolean isLocked()
        {
            //checks if ObjectToBeTagged is locked and adds outermost package name to Export
            Boolean locked = false;
            SQLElement lockedElement = null;
            if (getObjectToBeTagged() is SQLElement)
            {
                SQLElement elemn = getObjectToBeTagged() as SQLElement;
                locked = elemn.Locked;
                lockedElement = elemn;
            }
            else if (getObjectToBeTagged() is SQLPackage)
            {
                SQLPackage elemn = getObjectToBeTagged() as SQLPackage;
                if (elemn.Element != null)
                    locked = elemn.Element.Locked;
                lockedElement = elemn.Element;
            }
            else if (getObjectToBeTagged() is SQLConnector)
            {
                SQLConnector elemn = getObjectToBeTagged() as SQLConnector;
                SQLElement source = Repository.GetElementByID(elemn.ClientID);
                locked = source.Locked;
                lockedElement = source;
            }
            if (locked)
            {
                SQLPackage outermostPkg = EAUtil.getOutermostPackage(lockedElement, Repository);

                if (outermostPkg != null && !Export.AutoUpdatePackages.Contains(outermostPkg.Name))
                    Export.AutoUpdatePackages.Add(outermostPkg.Name);
            }
            return locked;
        }


        /// <summary>
        /// Deserializes the TaggedValue from the tagged Object 
        /// </summary>
        /// <returns></returns>
        public Boolean loadTreeFromTaggedValue()
        {



            String tagNotes = "";

            Object mocaTag = null;
            Object objectToBeTagged = getObjectToBeTagged();
            if (objectToBeTagged is SQLElement)
            {
                mocaTag = EAUtil.findTaggedValue(objectToBeTagged as SQLElement, Main.MoflonExportTreeTaggedValueName);
            }
            else if (objectToBeTagged is SQLConnector)
            {
                mocaTag = EAUtil.findTaggedValue(objectToBeTagged as SQLConnector, Main.MoflonExportTreeTaggedValueName);
            }
            else if (objectToBeTagged is SQLPackage)
            {
                mocaTag = EAUtil.findTaggedValue(objectToBeTagged as SQLPackage, Main.MoflonExportTreeTaggedValueName);
            }
            else if (objectToBeTagged is SQLMethod)
            {
                mocaTag = EAUtil.findTaggedValue(Repository.GetOriginalRepository().GetMethodByID((objectToBeTagged as SQLMethod).MethodID), Main.MoflonExportTreeTaggedValueName);
            }
            if (mocaTag is SQLTaggedValue)
                tagNotes = (mocaTag as SQLTaggedValue).Notes;
            else if (mocaTag is SQLConnectorTag)
                tagNotes = (mocaTag as SQLConnectorTag).Notes;
            else if (mocaTag is EA.MethodTag)
                tagNotes = (mocaTag as EA.MethodTag).Notes;

            if (tagNotes != "")
            {
                XmlDocument xmlDocument = MocaTreeUtil.stringToXmlDocument(tagNotes);
                MocaNode emptyMocaNode = new MocaNode();

                //fill mocanode from xmlElement
                emptyMocaNode.deserializeFromXmlTree(xmlDocument.FirstChild.FirstChild as XmlElement);

                //fill this from mocanode

                try
                {

                    deserializeFromMocaTree(emptyMocaNode);
                }
                catch
                {
                    return false;
                }
                return true;
            }
            return false;
        }

        public override MocaNode serializeToMocaTree(MocaNode actNode)
        {
            return serializeToMocaTree();
        }

        public abstract MocaNode serializeToMocaTree();


        /// <summary>
        /// necessary for the visual output in EA. 
        /// TaggedValues for shapescripts etc.
        /// </summary>
        public virtual void doEaGuiStuff()
        {
        }

    }
}

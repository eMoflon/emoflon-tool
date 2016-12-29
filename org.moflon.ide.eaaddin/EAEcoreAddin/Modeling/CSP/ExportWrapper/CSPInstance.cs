using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.CSP.ExportWrapper
{
    class CSPInstance : MocaTaggableElement
    {

        public List<CSPInstanceEntry> createdEntries { get; set; }
        public string CspStringValueFromImport { get; set; }
        public SQLElement constraintElement { get; set; }

        public CSPInstance(SQLRepository repository, SQLElement constraintElement)
        {
            this.Repository = repository;
            this.constraintElement = constraintElement;
            this.createdEntries = new List<CSPInstanceEntry>();
        }


        public override object getObjectToBeTagged()
        {
            return this.constraintElement;
        }

        public override void doEaGuiStuff()
        {

            EA.Element realElement = constraintElement.getRealElement();
            realElement.StereotypeEx = TGGModelingMain.CSPConstraintStereotype;
            realElement.Notes = "";

            foreach (CSPInstanceEntry entry in this.createdEntries)
            {
                realElement.Notes += entry.ToString() + Environment.NewLine;
            }

            realElement.Update();
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode constraintNode = new MocaNode("ConstraintInstance");
            
            MocaNode entries = constraintNode.appendChildNode("entries");
            foreach (CSPInstanceEntry createdEntry in createdEntries)
            {
                entries.appendChildNode(createdEntry.serializeToMocaTree(new MocaNode("Entry")));
            }


            return constraintNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            MocaNode entriesNode = actNode.getChildNodeWithName("entries");
            if(entriesNode != null)
            {
                foreach (MocaNode entryNode in entriesNode.Children)
                {
                    CSPInstanceEntry entry = new CSPInstanceEntry(Repository);
                    entry.deserializeFromMocaTree(entryNode);
                    createdEntries.Add(entry);
                }
            }
        }




        public override void refreshSQLObject()
        {
            this.constraintElement = Repository.GetElementByID(constraintElement.ElementID);
        }
    }
}

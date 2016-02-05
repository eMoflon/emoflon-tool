using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper
{
    class EEnum : MocaTaggableElement
    {

        SQLElement eaEEnumElement;
        public String Guid { get; set; }
        public List<EEnumLiteral> Literals { get; set; }

        public EEnum(SQLElement eaEEnumElement, SQLRepository repository)
        {
            this.Repository = repository;
            this.Literals = new List<EEnumLiteral>();
            this.eaEEnumElement = eaEEnumElement;
            this.Name = eaEEnumElement.Name;
            computeAttributes();
        }

        public void computeAttributes()
        {

            foreach (SQLAttribute eLiteral in this.eaEEnumElement.Attributes)
            {
                EEnumLiteral newLiteral = new EEnumLiteral(eLiteral, Repository);
                this.Literals.Add(newLiteral);
            }

        }

        public override object getObjectToBeTagged()
        {
            return this.eaEEnumElement;
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode eEnumNode = new MocaNode("EEnum");
            eEnumNode.appendChildAttribute("name", this.eaEEnumElement.Name);
            eEnumNode.appendChildAttribute(Main.GuidStringName, this.eaEEnumElement.ElementGUID);
            
            MocaNode eLiteralsNode = eEnumNode.appendChildNode("literals");
            foreach (EEnumLiteral literal in this.Literals)
            {
                MocaNode eLiteralNode = eLiteralsNode.appendChildNode("EEnumLiteral");
                literal.serializeToMocaTree(eLiteralNode);
            }
           
            return eEnumNode;
        }

        private void removeExisting(string literalAttr)
        {
            int max=this.Literals.Count;
            for (int i = 0; i < max; i++)
            {
                EEnumLiteral literal = this.Literals.ElementAt(i);
                if (literal.Literal == literalAttr)
                {
                    this.Literals.RemoveAt(i);
                    return;
                }
                    
            }            
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.Name = actNode.getAttributeOrCreate("name").Value;
            this.Guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;
            MocaNode literalsNode = actNode.getChildNodeWithName("literals");
            foreach (MocaNode literalNode in literalsNode.Children)
            {
                foreach (SQLAttribute litAttr in this.eaEEnumElement.Attributes)
                {
                    if (litAttr.Name == literalNode.getAttributeOrCreate("name").Value)
                    {
                        EEnumLiteral eenumLit = new EEnumLiteral(litAttr, Repository);
                        eenumLit.deserializeFromMocaTree(literalNode);
                        removeExisting(litAttr.Name);
                        this.Literals.Add(eenumLit);
                    }
                }
            }
        }

        public override void doEaGuiStuff()
        {
            EA.Element realElement = eaEEnumElement.getRealElement();
            realElement.Stereotype = ECOREModelingMain.EEnumStereotype;
            realElement.Name = this.Name;
            realElement.Update();

            foreach (EEnumLiteral eLit in this.Literals)
            {
                eLit.updateEaGui();
            }
            
        }

        public override void refreshSQLObject()
        {
            this.eaEEnumElement = Repository.GetElementByID(eaEEnumElement.ElementID);
        }

    }
}

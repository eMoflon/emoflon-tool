using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper
{
    class EEnumLiteral : MocaSerializableElement
    {
        SQLAttribute enumLiteral;

        public String Value { get; set; }
        public String Literal { get; set; }

        public EEnumLiteral(SQLAttribute attribute, SQLRepository sqlRep)
        {
            this.Repository = sqlRep;
            this.enumLiteral = attribute;
            this.Name = attribute.Name;
            this.Value = attribute.Default;
            this.Literal = attribute.getRealAttribute().Alias;
            if (Literal == "")
            {
                Literal = Name;
            }
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree(Serialization.MocaTree.MocaNode actNode)
        {           
            actNode.appendChildAttribute("name", this.Name);
            actNode.appendChildAttribute("value", this.Value);
            actNode.appendChildAttribute("literal", this.Literal);
            return actNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.Name = actNode.getAttributeOrCreate("name").Value;
            this.Value = actNode.getAttributeOrCreate("value").Value;
            this.Literal = actNode.getAttributeOrCreate("literal").Value;
        }

        public void updateEaGui()
        {
            EA.Attribute realElement = enumLiteral.getRealAttribute();
            realElement.Name = this.Name;
            realElement.Default = this.Value;
            realElement.Alias = this.Literal;
            realElement.Update();
        }   
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper
{
    public class Domain : MocaSerializableElement
    {
        internal DomainType DomainType { get; set; }

        public String MetamodelGuid { get; set; }

        public Domain(Metamodel metamodel, DomainType type)
        {
            this.DomainType = type;
            this.MetamodelGuid = metamodel.Guid;
        }

        public Domain()
        {
        }


        public override Serialization.MocaTree.MocaNode serializeToMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            actNode.appendChildAttribute("domainType", this.DomainType.ToString().ToLower());
            actNode.appendChildAttribute("metamodelGuid", this.MetamodelGuid);
            return actNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.DomainType = (DomainType) Enum.Parse(typeof(DomainType), actNode.getAttributeOrCreate("domainType").Value.ToUpper());
            this.MetamodelGuid = actNode.getAttributeOrCreate("metamodelGuid").Value;
            //(BindingOperator)Enum.Parse(typeof(BindingOperator), bindingOperatorAttribute.InnerXml);
        }
    }
}

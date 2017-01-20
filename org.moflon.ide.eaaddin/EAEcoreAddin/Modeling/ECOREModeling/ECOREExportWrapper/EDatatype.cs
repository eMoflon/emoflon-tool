using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper
{
    class EDatatype : MocaTaggableElement
    {
        SQLElement EAEDataType { get; set; }
        public String Guid { get; set; }
        public String InstanceTypeName { get; set; }

        public EDatatype(SQLElement EAEDataType, SQLRepository repository)
        {
            this.EAEDataType = EAEDataType;
            this.Repository = repository;
            this.Name = EAEDataType.Name;
            this.InstanceTypeName = EAEDataType.Alias;
            this.Guid = EAEDataType.ElementGUID;
        }

        public override object getObjectToBeTagged()
        {
            return this.EAEDataType;
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode eDataTypeNode = new MocaNode();
            eDataTypeNode.Name = "EDatatype";
            eDataTypeNode.appendChildAttribute("name", this.Name);
            eDataTypeNode.appendChildAttribute(Main.GuidStringName, this.Guid);
            eDataTypeNode.appendChildAttribute("instanceTypeName", this.InstanceTypeName);
            return eDataTypeNode;
        }
         
        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.Name = actNode.getAttributeOrCreate("name").Value;
            this.Guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;
            this.InstanceTypeName = actNode.getAttributeOrCreate("instanceTypeName").Value;
        }

        public override void doEaGuiStuff()
        {
            if (this.EAEDataType.Stereotype != ECOREModelingMain.EDatatypeStereotype)
            {
                EA.Element realDatatype = Repository.GetOriginalRepository().GetElementByGuid(this.EAEDataType.ElementGUID);
                realDatatype.StereotypeEx = ECOREModelingMain.EDatatypeStereotype;
                realDatatype.Alias = this.InstanceTypeName;
                realDatatype.Name = this.Name;
                realDatatype.Update();
            }
        }

        public override void refreshSQLObject()
        {
            this.EAEDataType = Repository.GetElementByID(EAEDataType.ElementID);
        }
    }
}

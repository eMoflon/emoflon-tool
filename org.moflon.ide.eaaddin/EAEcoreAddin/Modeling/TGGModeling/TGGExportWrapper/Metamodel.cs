using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper
{
    public class Metamodel : MocaSerializableElement
    {
        internal String name;
        public String Guid { get; set; }
        SQLRepository repository;

        public Metamodel(SQLRepository repository, SQLPackage package)
        {
            this.repository = repository;
            this.name = package.Name;
            this.Guid = package.PackageGUID;
        }

        public Metamodel()
        {
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            actNode.appendChildAttribute(Main.GuidStringName, this.Guid);
            return actNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.Guid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;
        }
    }
}

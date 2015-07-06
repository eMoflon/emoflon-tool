using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Refactoring
{
    class CachedPackage : CachedElement
    {
        public string oldName;
        public string name;

        public override void cache()
        {
            if (this.element != null)
            {
                this.oldName = this.element.Name;
            }
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            //TODO: "Übernehmen"
            MocaNode eclassNode = new MocaNode("EPackageRefactored");
            eclassNode.appendChildAttribute("name", this.name);
            eclassNode.appendChildAttribute("oldName", this.oldName);
            return eclassNode;
        }
    }
}

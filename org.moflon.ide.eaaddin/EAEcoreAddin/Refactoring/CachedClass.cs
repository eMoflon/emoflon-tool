using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Refactoring
{
    class CachedClass : CachedElement
    {
        public string previousName;
        public string name;
        public string packageName;

        public override void cache()
        {
            if (this.element != null)
            {
                this.previousName = this.element.Name;
            }
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            //TODO: "Übernehmen"
            MocaNode eclassNode = new MocaNode("EClass");     
            eclassNode.appendChildAttribute("name", this.name);
            eclassNode.appendChildAttribute("previousName", this.previousName);
            eclassNode.appendChildAttribute("packageName", this.packageName);
            return eclassNode;
        }
    }
}

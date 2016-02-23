using EAEcoreAddin.Serialization.MocaTree;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Refactoring
{
    class CachedNamedElement : CachedElement
    {
        public string type;
        public string previousName;
        public string name;
        public string packageName;
        public string projectName;

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode ePackageNode = new MocaNode(type);
            ePackageNode.appendChildAttribute("name", this.name);
            ePackageNode.appendChildAttribute("previousName", this.previousName);
            ePackageNode.appendChildAttribute("packageName", this.packageName);
            ePackageNode.appendChildAttribute("projectName", this.projectName);
            return ePackageNode;
        }
    }
}

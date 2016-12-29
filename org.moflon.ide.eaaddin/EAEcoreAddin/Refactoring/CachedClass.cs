using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.ECOREModeling;

namespace EAEcoreAddin.Refactoring
{
    class CachedClass : CachedElement
    {
        //public string type;
        public string previousName;
        public string name;
        public string packageName;
        public string projectName;

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode ePackageNode = new MocaNode(ECOREModelingMain.EClassStereotype);
            ePackageNode.appendChildAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_NAME, this.name);
            ePackageNode.appendChildAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PREVIOUS_NAME, this.previousName);
            ePackageNode.appendChildAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PACKAGE_NAME, this.packageName);
            ePackageNode.appendChildAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PROJECT_NAME, this.projectName);
            return ePackageNode;
        }
    }
}

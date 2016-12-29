using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Modeling.ECOREModeling;

namespace EAEcoreAddin.Refactoring
{   
    class CachedPackage : CachedElement
    {
        public SQLPackage package;
        //public string type;
        public string previousName;
        public string name;
        public string packageName;
        public string projectName;

        public void getPackage(String GUID, SQLRepository repository)
        {
            this.sqlRepository = repository;
            this.package = this.sqlRepository.GetPackageByGuid(GUID);
        }

        public void savePackageToEATaggedValue(Boolean updateEaGui)
        {
            EAEcoreAddin.Util.EAUtil.setTaggedValueNotes(this.sqlRepository, this.package, Main.MoflonChangesTreeTaggedValueName, getXMLDocumentString());
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode ePackageNode = new MocaNode(ECOREModelingMain.EPackageStereotype);
            ePackageNode.appendChildAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_NAME, this.name);
            ePackageNode.appendChildAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PREVIOUS_NAME, this.previousName);
            ePackageNode.appendChildAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PACKAGE_NAME, this.packageName);
            ePackageNode.appendChildAttribute(ChangesTreeConstants.ATTRIBUTE_KEY_PROJECT_NAME, this.projectName);
            return ePackageNode;
        }
    }
}

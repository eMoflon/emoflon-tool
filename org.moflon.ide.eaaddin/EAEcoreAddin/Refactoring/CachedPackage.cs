using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Refactoring
{
    class CachedPackage : CachedElement
    {
        public SQLPackage package;
        public string previousName;
        public string name;
        public string isTLP;

        public void getPackage(String GUID, SQLRepository repository)
        {
            this.sqlRepository = repository;
            this.package = this.sqlRepository.GetPackageByGuid(GUID);
        }
        /*public override void cache()
        {
            if (this.element != null)
            {
                if (!(this.element.Name.Equals(this.oldName)))
                {
                    this.oldName = this.element.Name;
                }
            }

        }*/

        public void savePackageToEATaggedValue(Boolean updateEaGui)
        {
            EAEcoreAddin.Util.EAUtil.setTaggedValueNotes(this.sqlRepository, this.package, Main.MoflonChangesTreeTaggedValueName, getXMLDocumentString());
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode eclassNode = new MocaNode("EPackage");
            eclassNode.appendChildAttribute("name", this.name);
            eclassNode.appendChildAttribute("previousName", this.previousName);
            eclassNode.appendChildAttribute("isTLP", this.isTLP);
            return eclassNode;
        }
    }
}

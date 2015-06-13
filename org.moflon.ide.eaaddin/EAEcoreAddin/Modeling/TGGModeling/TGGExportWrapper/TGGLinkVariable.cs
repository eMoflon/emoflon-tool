using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.TGGModeling.Util;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper
{

    class TGGLinkVariable : EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.LinkVariable
    {

        DomainType domain;

        public TGGLinkVariable(SQLConnector linkVariable, SQLRepository repository) :
            base(linkVariable, repository)
        {
           
        }

        public override void addAttributesDuringExport(MocaNode linkVariableMocaNode)
        {
            base.addAttributesDuringExport(linkVariableMocaNode);
            linkVariableMocaNode.appendChildAttribute("domain", TGGModelingUtil.getDomainOfLinkVariable(Repository, this.LinkVariableEA).ToString().ToLower());           
        }

        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();
            this.LinkVariableEA.getRealConnector().StereotypeEx = TGGModelingMain.TggLinkVariableStereotype;
            this.LinkVariableEA.getRealConnector().Update();
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            this.domain = TGGModelingUtil.getDomainOfLinkVariable(Repository, this.LinkVariableEA);
            MocaNode lvNode = base.serializeToMocaTree();
            lvNode.appendChildAttribute("domain", this.domain.ToString().ToLower());
            lvNode.Name = TGGModelingMain.TggLinkVariableStereotype;
            return lvNode;
        }
    }
}

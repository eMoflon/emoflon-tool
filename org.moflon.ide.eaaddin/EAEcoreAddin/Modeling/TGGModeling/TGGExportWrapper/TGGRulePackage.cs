using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper
{
    class TGGRulePackage : EPackage
    {
        public static readonly String RulesChildNodeName = "rules";

        public TGGRulePackage(SQLPackage eaPackage, SQLRepository repository) : base (eaPackage, repository)
        {
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode ePackageNode = base.serializeToMocaTree();
            ePackageNode.Name = "RulePackage";
            ePackageNode.appendChildNode(TGGRulePackage.RulesChildNodeName);
            return ePackageNode;
        }

        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();
            EA.Package realPackage = EaPackage.getRealPackage();
            if (realPackage.Element.Stereotype != TGGModelingMain.TggRulePackageStereotype)
            {
                realPackage.Element.StereotypeEx = TGGModelingMain.TggRulePackageStereotype;
                realPackage.Update();
            }
            

        }
    }
}

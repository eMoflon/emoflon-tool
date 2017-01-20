using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper
{
    class TGGCorrespondenceType : EClass
    {
        public TGGCorrespondenceType(SQLElement eaElement, SQLRepository repository)
            : base(eaElement, repository)
        {

        }

        public override void doEaGuiStuff()
        {

            if (this.EaElement.Stereotype != TGGModelingMain.TggCorrespondenceTypeStereotype)
            {
                EA.Element realElement = Repository.GetOriginalRepository().GetElementByGuid(this.EaElement.ElementGUID);
                realElement.StereotypeEx = TGGModelingMain.TggCorrespondenceTypeStereotype;
                realElement.Update();
            }
            
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode eClassNode = base.serializeToMocaTree();
            eClassNode.Name = "CorrespondenceType";
            return eClassNode;
        }



    }
}

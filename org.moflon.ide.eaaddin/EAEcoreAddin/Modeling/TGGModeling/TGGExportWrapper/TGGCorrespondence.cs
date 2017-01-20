using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper
{
    class TGGCorrespondence : TGGObjectVariable
    {
        public static readonly String CorrespondenceNodeName = "TGGCorrespondence";

        public TGGCorrespondence(SQLElement sdmObject, SQLRepository repository) :
            base(sdmObject, repository)
        {
        }


        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();
            EA.Element realElement = sqlElement.getRealElement();
            realElement.StereotypeEx = TGGModelingMain.TggCorrespondenceStereotype;
            realElement.Update();
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode ovNode = base.serializeToMocaTree();
            ovNode.Name = TGGCorrespondence.CorrespondenceNodeName;
            return ovNode;
        }


    }
}

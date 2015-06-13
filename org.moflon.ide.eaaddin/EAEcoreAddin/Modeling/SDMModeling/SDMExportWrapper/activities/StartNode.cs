using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities
{
    public class StartNode : ActivityNode
    {
        public StartNode(SQLRepository repository, SQLElement activityNode)
            : base(repository, activityNode)
        {
        }

        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();

            EA.Element sdmContainer = ActivityNodeEAElement.getRealElement();
            if (sdmContainer.Stereotype == SDMModelingMain.SdmContainerStereotype)
            {
                try
                {
                    SQLMethod associatedMethod = Repository.GetMethodByGuid(EAUtil.findTaggedValue(ActivityNodeEAElement, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName).Value);
                    SQLElement containingEClass = Repository.GetElementByID(associatedMethod.ParentID);
                    ActivityNodeEAElement.getRealElement().Name = SDMUtil.computeStartNodeName(associatedMethod, containingEClass);
                }
                catch (Exception)
                {
                }
            }

            sdmContainer.StereotypeEx = SDMModelingMain.StartNodeStereotype;
            sdmContainer.Update();
        }


        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            Name = this.ActivityNodeEAElement.Name;
            MocaNode startNodeNode = base.serializeToMocaTree();
            startNodeNode.appendChildAttribute("type", "start");
            return startNodeNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            base.deserializeFromMocaTree(actNode);  
        }



    }
}

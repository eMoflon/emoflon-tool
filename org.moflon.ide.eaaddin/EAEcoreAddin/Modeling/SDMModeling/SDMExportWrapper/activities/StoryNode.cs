using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.SQLWrapperClasses;
using System.Xml;

using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization.MocaTree.Util;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities
{
    public class StoryNode : ActivityNode
    {
        public Boolean ForEach { get; set; }
        public StoryPattern OwnedRule { get; set; }

        public static readonly int DefaultWidth = 100;
        public static readonly int DefaultHeight = 75;

        public StoryNode(SQLRepository repository, SQLElement activityNode)
            : base(repository, activityNode)
        {
        }





        public override void doEaGuiStuff()
        {
            base.doEaGuiStuff();
            EA.Element realElement = EAUtil.sqlEAObjectToOriginalObject(Repository, ActivityNodeEAElement) as EA.Element;
            realElement.StereotypeEx = SDMModelingMain.StoryNodeStereotype;
            if (this.ForEach)
            {
                EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realElement, "foreach", "true");
            }
            else
            {
                EAEcoreAddin.Util.EAUtil.setTaggedValue(Repository, realElement, "foreach", "false"); 
            }
            realElement.Update();
        }






        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            MocaNode actNode = base.serializeToMocaTree();
            actNode.appendChildAttribute("forEach", this.ForEach.ToString().ToLower());
            actNode.appendChildAttribute("type", "story");
            actNode.appendChildNode(StoryPattern.ObjectVariablesChildNodeName);
            return actNode;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            base.deserializeFromMocaTree(actNode);
            this.ForEach = actNode.getAttributeOrCreate("forEach").Value == Main.TrueValue;
        }
    }
}

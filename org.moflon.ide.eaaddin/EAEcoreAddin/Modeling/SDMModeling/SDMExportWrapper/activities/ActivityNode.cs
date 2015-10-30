using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.SQLWrapperClasses;

using System.Xml;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Serialization.MocaTree.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities
{
    public abstract class ActivityNode : MocaTaggableElement
    {
        public String EaGuid { get; set; }
        public SQLElement ActivityNodeEAElement { get; set; }
        public static readonly String OutgoingTransitionsNodeName = "outgoingTransitions";

        public ActivityNode(SQLRepository repository, SQLElement activityNode)
        {
            this.Repository = repository;
            this.ActivityNodeEAElement = activityNode;
            this.Name = this.ActivityNodeEAElement.Name;
            this.EaGuid = this.ActivityNodeEAElement.ElementGUID;
        }


        private Activity activity;
        public Activity OwningActivity 
        { 
            get 
            {
                if(activity == null)
                    activity = new Activity(Repository.GetElementByID(ActivityNodeEAElement.ParentID) ,Repository);
                return activity;
            } 
        }



        public override object getObjectToBeTagged()
        {
            return this.ActivityNodeEAElement;
        }

        public override void doEaGuiStuff()
        {
            EA.Element realElement = ActivityNodeEAElement.getRealElement();
            realElement.Name = this.Name;
            realElement.Update();
        }

        public override MocaNode serializeToMocaTree()
        {
            MocaNode node = new MocaNode("ActivityNode");
            node.appendChildAttribute("name", this.Name);
            node.appendChildAttribute(Main.GuidStringName, this.ActivityNodeEAElement.ElementGUID);
            node.appendChildNode(ActivityNode.OutgoingTransitionsNodeName);
            return node;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            this.EaGuid = actNode.getAttributeOrCreate(Main.GuidStringName).Value;
            this.Name = actNode.getAttributeOrCreate("name").Value;
        }

        public override void refreshSQLObject()
        {
            this.ActivityNodeEAElement = Repository.GetElementByID(ActivityNodeEAElement.ElementID);
        }

    }
}

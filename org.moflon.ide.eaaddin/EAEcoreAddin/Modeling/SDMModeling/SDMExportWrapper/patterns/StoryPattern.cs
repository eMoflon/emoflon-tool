using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Serialization.MocaTree.Util;using EAEcoreAddin.Serialization;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns
{
    public class StoryPattern : MocaTaggableElement
    {
        protected ArrayList LinkVariable { get; set; }
        private ArrayList ObjectVariable { get; set; }
        public SQLElement StoryPatternElement { get; set; }
        public String Guid { get; set; }

        public static readonly String ObjectVariablesChildNodeName = "objectVariables";

        public StoryPattern(SQLRepository repository, SQLElement storyPattern)
        {
            this.StoryPatternElement = storyPattern;
            this.Repository = repository;
            this.LinkVariable = new ArrayList();
            this.ObjectVariable = new ArrayList();
        }


        protected StoryNode parentStoryNode;

        public StoryNode ParentStoryNode
        {
            get
            {
                if(parentStoryNode == null)
                    parentStoryNode = new StoryNode(Repository, this.StoryPatternElement);
                return parentStoryNode;
            }
            
        }

        public override void refreshSQLObject()
        {
            this.StoryPatternElement = Repository.GetElementByID(StoryPatternElement.ElementID);
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {
            throw new NotImplementedException();
        }

        public override object getObjectToBeTagged()
        {
            throw new NotImplementedException(); 
        }

        public override Serialization.MocaTree.MocaNode serializeToMocaTree()
        {
            throw new NotImplementedException();
        }
    }
}

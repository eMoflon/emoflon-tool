using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;

namespace EAEcoreAddin.Modeling.SDMModeling.ActivityNodeDialog
{
    public class ActivityNodeTypeComboBox : ComboBox
    {

        public String ActivityNodeTypeFromComboBox()
        {
            String actType = "";
            if (this.Text == "StatementNode")
                actType = "call";
            else if (this.Text == "ForEach")
                actType = "foreach";
            else if(this.Text == "StoryNode")
                actType = "activity";
            return actType;
        }

        public void comboBoxFromActivityNodeType(ActivityNode activityNode) 
        {
            if (activityNode is StatementNode)
                this.Text = "StatementNode";
            else if (activityNode is StoryNode)
            {
                StoryNode storyNode = activityNode as StoryNode;
                if (storyNode.ForEach)
                    this.Text = "ForEach";
                else
                    this.Text = "StoryNode";
            }
        }


    }
}

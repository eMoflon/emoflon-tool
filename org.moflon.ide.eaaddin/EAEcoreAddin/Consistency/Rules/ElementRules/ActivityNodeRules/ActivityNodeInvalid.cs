using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ActivityNodeRules
{
    class ActivityNodeInvalid : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            if (Serialization.MocaTaggableElement.isIgnored(eaElement))
                return results;

            if (eaElement.Stereotype == SDMModelingMain.StoryNodeStereotype)
            {
                StoryNode storyNode = new StoryNode(repository, eaElement);
                if (!storyNode.loadTreeFromTaggedValue())
                    results.Add("StoryNode is invalid and has to be updated manually"); 
            }
            else if (eaElement.Stereotype == SDMModelingMain.StatementNodeStereotype)
            {
                StatementNode stNode = new StatementNode(repository, eaElement);
                if (!stNode.loadTreeFromTaggedValue())
                    results.Add("StatementNode is invalid and has to be updated manually");
            }
            else if (eaElement.Stereotype == SDMModelingMain.StopNodeStereotype)
            {
                StopNode stopNode = new StopNode(repository, eaElement);
                if (!stopNode.loadTreeFromTaggedValue())
                    results.Add("StopNode is invalid and has to be updated manually");
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

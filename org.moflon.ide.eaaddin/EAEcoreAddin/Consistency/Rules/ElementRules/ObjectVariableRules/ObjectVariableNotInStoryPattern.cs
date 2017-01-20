using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class ObjectVariableNotInStoryPattern : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype)
            {
                SQLElement containingElement = repository.GetElementByID(eaElement.ParentID);
                if(containingElement.Stereotype != SDMModelingMain.StoryNodeStereotype)
                    results.Add("Every ObjectVariable must be contained in a story pattern");
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

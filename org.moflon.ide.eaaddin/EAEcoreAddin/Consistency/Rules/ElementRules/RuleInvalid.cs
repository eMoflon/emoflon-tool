using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class RuleInvalid : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == TGGModelingMain.TggRuleStereotype)
            {
                TGGRule tggRule = new TGGRule(repository, eaElement);
                tggRule.loadTreeFromTaggedValue();
                if (tggRule.Guid != eaElement.ElementGUID)
                    results.Add("TGGRule is invalid and has to be updated");
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            if (i == 0)
            {
                if (eaElement.Stereotype == TGGModelingMain.TggRuleStereotype)
                {
                    TGGRule tggRule = new TGGRule(repository, eaElement);
                    tggRule.saveTreeToEATaggedValue(true);
                }
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            ArrayList msgs = new ArrayList();
            msgs.Add("Perform update");
            return msgs;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class BlanksInElementNamesNotAllowed : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == ECOREModelingMain.EClassStereotype)
                if(eaElement.Name.Contains(" "))
                    results.Add("Blanks are not allowed in element names");
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLRepository repository, int i, String errorMessage)
        {
            if (i == 0)
            {
                EA.Element realElement = eaElement.getRealElement();
                realElement.Name = Regex.Replace(realElement.Name, " ", "");
                realElement.Update();
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.Immediately;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList messages = new ArrayList();
            messages.Add("Remove blanks from attribute name");
            return messages;
        }
    }
}

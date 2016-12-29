using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Text.RegularExpressions;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.AttributeRules
{
    class BlanksInAttributeNamesNotAllowed :  AttributeRule
    {
        public override List<String> doRule(SQLAttribute eaAttribute, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if(eaAttribute.Name.Contains(" "))
                results.Add("Blanks are not allowed in attribute names");
            return results;
        }

        public override void doRuleQuickFix(SQLAttribute eaAttribute, SQLRepository repository, int i, String errorMessage)
        {
            if (i == 0)
            {
                eaAttribute.getRealAttribute().Name = Regex.Replace(eaAttribute.Name, " ", "");
                eaAttribute.getRealAttribute().Update();
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

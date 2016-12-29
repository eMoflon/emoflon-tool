using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.MethodRules
{
    class BlanksInMethodNamesNotAllowed : MethodRule
    {
        public override List<String> doRule(SQLMethod eaMethod, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if(eaMethod.Name.Contains(" "))
                results.Add("Blanks are not allowed in method names");
            return results;
        }

        public override void doRuleQuickFix(SQLMethod eaMethod, SQLRepository repository, int i)
        {
            if (i == 0)
            {
                eaMethod.getRealMethod().Name = Regex.Replace(eaMethod.Name, " ", "");
                eaMethod.getRealMethod().Update();
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
            messages.Add("Remove blanks from method name");
            return messages;
        }
    }
}

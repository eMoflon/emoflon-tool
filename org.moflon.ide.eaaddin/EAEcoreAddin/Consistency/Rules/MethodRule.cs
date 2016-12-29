using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Consistency.Util;

namespace EAEcoreAddin.Consistency.Rules
{
    public abstract class MethodRule : Rule
    {
        public override List<RuleResult> getRuleResult(Object eaObject, SQLRepository repository)
        {
            List<RuleResult> results = new List<RuleResult>();
            foreach (String ruleOutput in doRule(eaObject as SQLMethod, repository))
            {
                results.Add(ConsistencyUtil.computeRuleResult(this, eaObject as SQLMethod, ruleOutput));
            }
            return results;
            
        }

        public override List<String> doRule(object eaObject, SQLRepository repository)
        {
            SQLMethod method = eaObject as SQLMethod;
            return doRule(method, repository);
        }

        public override void doRuleQuickFix(object eaObject,SQLRepository repository, int i, String errorMessage)
        {
            doRuleQuickFix(eaObject as SQLMethod,repository, i);
        }

        public abstract List<String> doRule(SQLMethod eaMethod, SQLRepository repository);
        public abstract void doRuleQuickFix(SQLMethod eaMethod,SQLRepository repository, int i);

    }
}

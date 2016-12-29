using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Consistency.Util;

namespace EAEcoreAddin.Consistency.Rules
{
    public abstract class AttributeRule : Rule
    {

        public override List<RuleResult> getRuleResult(Object eaObject, SQLRepository repository)
        {
            List<RuleResult> results = new List<RuleResult>();
            foreach (String result in doRule(eaObject, repository))
            {
                results.Add(ConsistencyUtil.computeRuleResult(this, eaObject as SQLAttribute, result));
            }
            return results;
        }

        public override List<String> doRule(object eaObject, SQLRepository repository)
        {
            return doRule(eaObject as SQLAttribute, repository);
        }

        

        public override void doRuleQuickFix(object eaObject,SQLRepository repository, int i, String errorMessage)
        {
            doRuleQuickFix(eaObject as SQLAttribute,repository, i, errorMessage);
        }

        public abstract List<String> doRule(SQLAttribute eaAttribute, SQLRepository repository);
        public abstract void doRuleQuickFix(SQLAttribute eaAttribute,SQLRepository repository, int i, String errorMessage);

    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Consistency.Util;

namespace EAEcoreAddin.Consistency.Rules
{
    public abstract class ElementRule : Rule
    {
        
        

        public override List<RuleResult> getRuleResult(Object eaObject, SQLRepository repository)
        {
            List<RuleResult> results = new List<RuleResult>();
            foreach (String ruleOutput in doRule(eaObject as SQLElement, repository))
            {
                results.Add(ConsistencyUtil.computeRuleResult(this ,eaObject as SQLElement, ruleOutput));
            }
            return results;
        }

        

        public override List<String> doRule(object eaObject, SQLRepository repository)
        {
            SQLElement element = eaObject as SQLElement;            
            return doRule(element, repository);
        }

        public abstract List<String> doRule(SQLElement sqlElement, SQLRepository sqlRepository);

        public override void doRuleQuickFix(Object eaObject,SQLRepository repository,int i, String errorMessage)
        {
            doRuleQuickFix(eaObject as SQLElement,repository,i, errorMessage);
        }

        public abstract void doRuleQuickFix(SQLElement sqlElement, SQLRepository sqlRepository,int i, String errorMessage);

    }
}

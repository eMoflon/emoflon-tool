using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Consistency.Util;

namespace EAEcoreAddin.Consistency.Rules
{
    public abstract class ConnectorRule : Rule
    {

        public override List<RuleResult> getRuleResult(Object eaObject, SQLRepository repository)
        {
            List<RuleResult> results = new List<RuleResult>();
            foreach (String ruleOutput in doRule(eaObject as SQLConnector, repository))
            {
                results.Add(ConsistencyUtil.computeRuleResult(this ,eaObject as SQLConnector, ruleOutput));
            }
            return results;
        }

        

        public override List<String> doRule(object eaObject, SQLRepository repository)
        {
            SQLConnector connector = eaObject as SQLConnector;
            return doRule(connector, repository);
        }

        public abstract List<String> doRule(SQLConnector eaConnector, SQLRepository repository);

        public override void doRuleQuickFix(Object eaObject,SQLRepository repository,int i, String errorMessage)
        {
            doRuleQuickFix(eaObject as SQLConnector,repository,i, errorMessage);
        }

        public abstract void doRuleQuickFix(SQLConnector eaConnector,SQLRepository repository,int i, String errorMessage);
        

    }
}

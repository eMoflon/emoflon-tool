using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules
{
    abstract class GlobalRule<T> : Rule
    {

        public virtual String getCustomName(T eaObject, SQLRepository repository)
        {
            return "";
        }

        public abstract Dictionary<T, String> doGlobalRule(SQLRepository repository);

        public override List<RuleResult> getRuleResult(object eaObject, SQLWrapperClasses.SQLRepository repository)
        {
            throw new NotImplementedException();
        }

        public override List<String> doRule(object eaObject, SQLWrapperClasses.SQLRepository repository)
        {
            throw new NotImplementedException();
        }

        public override void doRuleQuickFix(object eaObject, SQLRepository repository, int i, string errorMessage)
        {
            doRuleQuickFix((T)(eaObject), repository, i, errorMessage);
        }

        public abstract void doRuleQuickFix(T eaObject, SQLRepository repository, int i, string errorMessage);

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.OnRequest;
        }

    }
}

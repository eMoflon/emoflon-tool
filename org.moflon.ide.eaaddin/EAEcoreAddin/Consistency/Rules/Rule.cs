using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules
{
    public abstract class Rule
    {
        public abstract RuleErrorLevel getErrorLevel(Object eaObject, String errorMessage);

        public abstract List<RuleResult> getRuleResult(Object eaObject, SQLRepository repository);

        public virtual RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.Immediately;
        }

        public abstract List<String> doRule(Object eaObject, SQLRepository repository);
        public abstract ArrayList getQuickFixMessages(String errorMessage);
        public abstract void doRuleQuickFix(Object eaObject,SQLRepository repository,int i, String errorMessage);
        public virtual String getRuleID()
        {
            return this.GetType().Name;
        }

        public virtual int getSortIndex()
        {
            return 0;
        }

    }
}

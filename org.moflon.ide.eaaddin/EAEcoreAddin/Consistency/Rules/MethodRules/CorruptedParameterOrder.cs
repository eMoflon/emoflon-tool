using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.MethodRules
{
    class CorruptedParameterOrder : MethodRule
    {
        public override List<String> doRule(SQLMethod eaMethod, SQLRepository repository)
        {
            List<String> results = new List<string>();
            return results;

        }

        public override void doRuleQuickFix(SQLMethod eaMethod, SQLRepository repository, int i)
        {
            if (i == 0)
            {
                
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.Immediately;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {

            return null;
        }

        public override string getRuleID()
        {
            return "CorruptedParameterOrder";
        }
    }
}

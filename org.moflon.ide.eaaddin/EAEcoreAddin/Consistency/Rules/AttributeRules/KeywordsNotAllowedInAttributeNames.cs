using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.AttributeRules
{
    class KeywordsNotAllowedInAttributeNames :  AttributeRule
    {
        public override List<String> doRule(SQLAttribute eaAttribute, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if(Consistency.Util.ConsistencyUtil.nameIsKeyword(eaAttribute.Name))
                results.Add("No keywords should be used for attribute names");
            return results;
        }

        public override void doRuleQuickFix(SQLAttribute eaAttribute, SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
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
            return new System.Collections.ArrayList();
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Text.RegularExpressions;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Consistency.Util;

namespace EAEcoreAddin.Consistency.Rules.PackageRules
{
    class JavaReservedKeywordsNotAllowed : PackageRule
    {
        public override List<String> doRule(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            foreach (string segment in eaPackage.Name.Split('.'))
            {
                if (ConsistencyUtil.nameIsKeyword(segment))
                {
                    results.Add("The segment " + segment + " of package " + eaPackage.Name + " is a Java keyword and may not be used.");
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
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
            return new ArrayList();
        }
    }
}

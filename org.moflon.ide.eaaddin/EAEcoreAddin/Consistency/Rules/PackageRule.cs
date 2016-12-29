using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Consistency.Util;

namespace EAEcoreAddin.Consistency.Rules
{
    public abstract class PackageRule : Rule
    {

        public override List<RuleResult> getRuleResult(Object eaObject, SQLRepository repository)
        {
            List<RuleResult> results = new List<RuleResult>();
            foreach (String ruleOutput in doRule(eaObject as SQLPackage, repository))
            {
                results.Add(ConsistencyUtil.computeRuleResult(this, eaObject as SQLPackage, ruleOutput));
            }

            return results;
        }

        public override List<String> doRule(object eaObject, SQLRepository repository)
        {
            EA.Package package = eaObject as EA.Package;
            return doRule(eaObject as SQLPackage, repository);
        }

        public abstract List<String> doRule(SQLPackage eaPackage, SQLRepository repository);
        public abstract void doRuleQuickFix(SQLPackage eaPackage ,SQLRepository repository, int i, String errorMessage);


        public override void doRuleQuickFix(object eaObject,SQLRepository repository, int i, String errorMessage)
        {
            doRuleQuickFix(eaObject as SQLPackage,repository, i, errorMessage);
        }

        
    }
}

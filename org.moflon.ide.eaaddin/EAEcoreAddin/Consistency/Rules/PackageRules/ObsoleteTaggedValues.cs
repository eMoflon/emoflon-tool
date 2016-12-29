using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.PackageRules
{
    class ObsoleteTaggedValues : PackageRule
    {
        public override List<String> doRule(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();

            handleObsoleteTaggedValue(eaPackage, results, "Moflon::ValidationFilter");
            handleObsoleteTaggedValue(eaPackage, results, "Moflon::CustomNsUri");
            handleObsoleteTaggedValue(eaPackage, results, "validated");
            handleObsoleteTaggedValue(eaPackage, results, "Moflon::CustomNsPrefix");

            return results;
        }

        private static void handleObsoleteTaggedValue(SQLPackage eaPackage, List<String> results, String tag)
        {
            SQLTaggedValue validationFilter = EAUtil.findTaggedValue(eaPackage, tag);
            if (validationFilter != null)
                results.Add("The tagged value " + tag + " is obsolete and should be deleted or renamed appropriately.");
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Warning;
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.Immediately;
        }

        public override void doRuleQuickFix(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            return new ArrayList();
        }
    }
}

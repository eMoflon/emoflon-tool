using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using System.Text.RegularExpressions;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.PackageRules
{
    class BlanksInPackageNamesNotAllowed :  PackageRule
    {
        public override List<String> doRule(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            //not for root packages
            if(eaPackage.ParentID != 0)
                if(eaPackage.Name.Contains(" "))
                    results.Add("Blanks are not allowed in package names");
            return results;
        }

        public override void doRuleQuickFix(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            if (i == 0)
            {
                eaPackage.getRealPackage().Name = Regex.Replace(eaPackage.Name, " ", "");
                eaPackage.getRealPackage().Update();
            }
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
            ArrayList messages = new ArrayList();
            messages.Add("Remove blanks from package name");
            return messages;
        }
    }
}

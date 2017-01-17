using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.PackageRules
{
    class EmptyPackageNotAllowed : PackageRule
    {
        public override List<String> doRule(SQLPackage eaObject, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaObject.Element != null)
            {
                if (eaObject.Element.Stereotype == ECOREModelingMain.EPackageStereotype)
                {
                    if (eaObject.Elements.Count == 0)
                        results.Add("Empty package is not allowed");
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLPackage eaObject, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Warning;
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.OnRequest;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            return new ArrayList();
        }

        
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class TGGCorrespondenceInvalidMetamodel :  ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
            {
                SQLPackage containingPackage =  EAUtil.getOutermostPackage(eaElement, repository);

                SQLElement correspondenceTypeElement = EAUtil.getClassifierElement(repository, eaElement.ClassifierID);
                    SQLPackage corrTypeContainingPackage = EAUtil.getOutermostPackage(correspondenceTypeElement, repository);

                    if (correspondenceTypeElement != null && containingPackage.PackageGUID != corrTypeContainingPackage.PackageGUID)
                        results.Add("the correspondence classifier is not located in the current tgg package");
            }    
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, string errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

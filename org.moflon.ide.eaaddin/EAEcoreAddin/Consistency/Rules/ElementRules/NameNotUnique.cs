using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class NameNotUnique : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype)
            {
                SQLPackage pkg = repository.GetPackageByID(eaElement.PackageID);
                foreach (SQLElement elem in pkg.Elements)
                {
                    if (elem.Name == eaElement.Name && elem.ElementID != eaElement.ElementID)
                        results.Add("name of CorrespondenceType must be unique");
                }
            }

           

            if (eaElement.MetaType == ECOREModelingMain.EClassStereotype)
            {
                SQLPackage pkg = repository.GetPackageByID(eaElement.PackageID);
                foreach (SQLElement elem in pkg.Elements)
                {
                    if (elem.Name == eaElement.Name && elem.ElementID != eaElement.ElementID)
                    {
                        results.Add("name of classes in a package must be unique");
                    }
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

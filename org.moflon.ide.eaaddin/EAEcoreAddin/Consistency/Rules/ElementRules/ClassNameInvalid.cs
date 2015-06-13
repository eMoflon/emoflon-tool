using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class ClassNameInvalid : ElementRule 
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            if (eaElement.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                SQLPackage containingPackage = repository.GetPackageByID(eaElement.PackageID);
                if(containingPackage.Name == eaElement.Name)
                    results.Add("EClasses shouldnt have the same name as their containing package");

                SQLPackage metamodel = EAUtil.getOutermostPackage(eaElement, repository);
                if (metamodel.Name + "Package" == eaElement.Name)
                    results.Add("No EClass should be named \"<NameOfProject>Package\" as this is reserved for EMF by convention.");
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

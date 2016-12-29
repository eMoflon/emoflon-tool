using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class EClassParentInvalid : ElementRule
    {
        public override List<string> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            if (eaElement.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                List<String> results = new List<string>();

                SQLPackage pkg = repository.GetPackageByID(eaElement.PackageID);

                if (eaElement.ParentID != 0 || pkg.Element == null || (pkg.Element.Stereotype != ECOREModelingMain.EPackageStereotype && pkg.Element.Stereotype != TGGModelingMain.TggSchemaPackageStereotype))
                {
                    results.Add("EClasses can only be contained by EPackages");
                }
                return results;

            }
            return new List<string>();
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

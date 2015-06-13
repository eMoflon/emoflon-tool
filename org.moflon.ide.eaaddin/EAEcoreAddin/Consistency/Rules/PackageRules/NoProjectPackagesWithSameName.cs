using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.PackageRules
{
    class NoProjectPackagesWithSameName : PackageRule
    {
        public override List<String> doRule(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaPackage.ParentID != 0)
            {
                SQLPackage parentPackage = repository.GetPackageByID(eaPackage.ParentID);
                if (parentPackage.ParentID == 0)
                {
                    foreach (SQLPackage model in repository.Models)
                    {
                        foreach (SQLPackage project in model.Packages)
                            if (project.Name == eaPackage.Name && project.PackageGUID != eaPackage.PackageGUID)
                            results.Add("No two project packages should have the same name");
                    }
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
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

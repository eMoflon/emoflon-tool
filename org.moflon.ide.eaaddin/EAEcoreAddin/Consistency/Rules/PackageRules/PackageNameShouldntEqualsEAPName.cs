using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.PackageRules
{
    class PackageNameShouldntEqualsEAPName :  PackageRule
    {
        public override List<String> doRule(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaPackage.ParentID != 0)
            {
                SQLPackage parentPackage = repository.GetPackageByID(eaPackage.ParentID);
                if (parentPackage.ParentID == 0)
                {
                    String eapFilename = Path.GetFileNameWithoutExtension(repository.ConnectionString);
                    if(eapFilename == eaPackage.Name)
                        results.Add("No package should be named as the containing eap file");

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

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Consistency.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.PackageRules
{
    class PackageNameInvalid : PackageRule
    {
        public override List<String> doRule(SQLPackage eaPackage, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaPackage.Element.Stereotype == ECOREModelingMain.EPackageStereotype
                || eaPackage.Element.Stereotype == TGGModelingMain.TggSchemaPackageStereotype)
            {
                

                string[] invalidChars = getPackageNameInvalidChars(eaPackage.Name).Select(ch => ch.ToString()).ToArray(); 

                if(invalidChars.Length > 0)
                {
                    results.Add("Package name contains invalid chars: " + string.Join(", ", invalidChars));
                }
                else if (eaPackage.Name.ToLower().Equals("package"))
                {
                    results.Add("Naming your class Package might clash with java.lang.Package.  Please add a prefix or suffix if possible");
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
            return RuleErrorLevel.Warning;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            throw new NotImplementedException();
        }

        public static List<char> getPackageNameInvalidChars(String name)
        {
            return name.ToCharArray().Where(ch => isInvalidChar(ch)).ToList();
        }

        private static bool isInvalidChar(char ch)
        {
            return !(isCapitalOrSmallAlpha(ch) || isNumeric(ch) || isAllowedSpecial(ch));
        }

        private static bool isAllowedSpecial(char currentChar)
        {
            return currentChar == '.' || currentChar == '_';
         }

        private static bool isNumeric(char currentChar)
        {
            return currentChar >= '0' && currentChar <= '9';
        }

        private static bool isCapitalOrSmallAlpha(Char currentChar)
        {
            return (currentChar >= 'A' && currentChar <= 'Z') ||
                   (currentChar >= 'a' && currentChar <= 'z');
        }
    }
}

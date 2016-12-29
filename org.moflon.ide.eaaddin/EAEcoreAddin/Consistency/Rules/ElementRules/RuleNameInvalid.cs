using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using System.Collections;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Consistency.Util;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class RuleNameInvalid : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == TGGModelingMain.TggRuleStereotype)
            {
                SQLPackage rulesPackage = repository.GetPackageByID(eaElement.PackageID);
                SQLPackage tggSchemaPackage = repository.GetPackageByID(rulesPackage.ParentID);
                foreach (SQLElement corrType in tggSchemaPackage.Elements)
                {
                    if (corrType.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype)
                    {
                        if (corrType.Name == eaElement.Name)
                            results.Add("TGG Rule name shouldnt be equal to TGG CorrespondenceType name");
                    }
                }

                if (!ConsistencyUtil.isValidTGGRuleName(eaElement.Name))
                {
                    results.Add("TGG Rule name should consist of the following letters: a-z,A-Z,0-9,_,-");
                }

            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement sqlElement, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            EA.Element realElement = sqlElement.getRealElement();
            if (errorMessage == "TGG Rule name shouldnt be equal to TGG CorrespondenceType name")
            {
                if (i == 0)
                {

                    realElement.Name = "Rule" + realElement.Name;
                    realElement.Update();

                    TGGRule tggRule = new TGGRule(repository, sqlElement);
                    tggRule.saveTreeToEATaggedValue(true);
                }
            }
            else if (errorMessage == "TGG Rule name must not contain spaces")
            {
                if (i == 0)
                {
                    realElement.Name = realElement.Name.Replace(" ", "");
                    realElement.Update();

                    TGGRule tggRule = new TGGRule(repository, sqlElement);
                    tggRule.saveTreeToEATaggedValue(true);
                }
            }
            
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList msg = new ArrayList();
            if (errorMessage == "TGG Rule name shouldnt be equal to TGG CorrespondenceType name")
            {
                msg.Add("add 'Rule' suffix to Rule name");            
            }
            else if (errorMessage == "TGG Rule name must not contain spaces")
            {                
                msg.Add("Remove Spaces");
            }

            return msg;
        }
    }
}

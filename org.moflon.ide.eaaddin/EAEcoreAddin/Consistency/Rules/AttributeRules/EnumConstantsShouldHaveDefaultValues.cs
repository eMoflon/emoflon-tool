using EAEcoreAddin.Consistency.Util;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.SQLWrapperClasses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Consistency.Rules.AttributeRules
{
    /**
     *  This rule verifies that each enum constant has an integer default value.
     */
    class EnumConstantsShouldHaveDefaultValues : AttributeRule
    {
        public override List<String> doRule(SQLAttribute eaAttribute, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            SQLElement parentClass = repository.GetElementByID(eaAttribute.ParentID);
            if (parentClass.Stereotype == ECOREModelingMain.EEnumStereotype)
            {
                if (ConsistencyUtil.isValidConstantName(eaAttribute.Name))
                {
                    try
                    {
                        Int32.Parse(eaAttribute.Default);
                    }
                    catch (FormatException)
                    {
                        results.Add("EEnums constants must provide an integer default value.");
                    }
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLAttribute eaAttribute, SQLRepository repository, int i, String errorMessage)
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

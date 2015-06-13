using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using EAEcoreAddin.Consistency.Util;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    /**
     * This rule verifies that each enum contains at least one constant.
     * 
     * A constant is identified either by having a contentional name (upper case letters, digits, and underscores; e.g., A_CONSTANT, RULE_1) or by having an integer default value.
     */
    class EnumShouldContainConstants : ElementRule 
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            if (eaElement.Stereotype == ECOREModelingMain.EEnumStereotype)
            {
                bool foundEnumConstant = false;
                SQLCollection<SQLAttribute> attributes = eaElement.Attributes;
                foreach (SQLAttribute attribute in attributes) {
                    if (ConsistencyUtil.isValidConstantName(attribute.Name))
                    {
                        foundEnumConstant = true;
                    }
                    else
                    {
                        try
                        {
                            Int32.Parse(attribute.Default);
                            foundEnumConstant = true;
                        }
                        catch (FormatException)
                        {
                            //ignore
                        }
                    }
                }
                if(!foundEnumConstant)
                {
                    results.Add("EEnum should contain at least one enum constant consisting of uppercase letters, digits, and underscores with an integer default value.");
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Warning;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

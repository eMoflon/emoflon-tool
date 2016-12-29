using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class OvWrongBindingSemantics : ElementRule
    {
        public override List<string> doRule(SQLWrapperClasses.SQLElement sqlElement, SQLWrapperClasses.SQLRepository sqlRepository)
        {
            var result = new List<String>();
            if (sqlElement.Stereotype == SDMModelingMain.ObjectVariableStereotype || sqlElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype)
            {
                SQLTaggedValue tag = EAUtil.findTaggedValue(sqlElement, ObjectVariable.BindingSemanticsTaggedValueName);
                if (tag != null)
                {
                    if (tag.Value.ToLower() == "optional")
                    {
                        result.Add("Optional ist not supported anymore");
                    }
                }
            }
            return result;
        }

        public override void doRuleQuickFix(SQLWrapperClasses.SQLElement sqlElement, SQLWrapperClasses.SQLRepository sqlRepository, int i, string errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, string errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

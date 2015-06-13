using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Consistency.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class InvalidBindingExpression : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();

            if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype
                || eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype
                || eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
            {
                ObjectVariable ov = new ObjectVariable(eaElement, repository);
                ov.loadTreeFromTaggedValue();
                if (ov.BindingExpression != null)
                    if (!ConsistencyUtil.checkExpression(eaElement, ov.BindingExpression, repository))
                        results.Add("BindingExpression is Invalid: (" + ov.BindingExpression.ToString() + ")");
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

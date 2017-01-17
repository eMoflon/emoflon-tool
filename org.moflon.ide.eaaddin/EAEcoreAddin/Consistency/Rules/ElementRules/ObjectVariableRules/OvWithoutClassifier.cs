using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class OvWithoutClassifier : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype 
                || eaElement.Stereotype == "SDM_Object" 
                || eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype
                || eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
            {

                SQLElement classifier = EAUtil.getClassifierElement(repository, eaElement.ClassifierID);
                if (classifier == null)
                {
                    results.Add("must have a valid classifier");
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement element,SQLRepository repository,int i, String errorMessage)
        {

        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            return new System.Collections.ArrayList();
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.Immediately;
        }

        public override string getRuleID()
        {
            return "OvWithoutClassifier";
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class TGGObjectVariableUndefinedDomain : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype || eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
            {
                TGGObjectVariable tggOV = new TGGObjectVariable(eaElement, repository);
                DomainType domain = TGGModelingUtil.getDomainOfObjectVariable(repository, tggOV);
                if (domain == DomainType.UNDEFINED)
                    results.Add("Domain of the Object Variable (source or target) cannot be defined. Prefix the name of this object variable with \"src__\" or \"trg__\".");
            }
            return results;
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            return new ArrayList();
        }



    }
}

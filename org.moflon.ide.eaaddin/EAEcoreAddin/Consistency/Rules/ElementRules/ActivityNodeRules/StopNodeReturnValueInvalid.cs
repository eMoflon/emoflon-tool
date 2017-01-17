using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Consistency.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ActivityNodeRules
{
    class StopNodeReturnValueInvalid : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == SDMModelingMain.StopNodeStereotype)
            {
                StopNode stopNode = new StopNode(repository, eaElement);
                stopNode.loadTreeFromTaggedValue();

                if (stopNode.ReturnValue != null)
                {
                    Boolean isValid = ConsistencyUtil.checkExpression(eaElement ,stopNode.ReturnValue, repository);
                    if (!isValid)
                    {
                        String output = "StopNode returnValue is invalid and must be updated manually";
                        output += ConsistencyUtil.getExpressionOutput(stopNode.ReturnValue, repository);
                        results.Add(output);
                    }
                }
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

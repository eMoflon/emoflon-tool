using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Serialization;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ActivityNodeRules
{
    class IncomingEdgesInvalid : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();

            if (MocaTaggableElement.isIgnored(eaElement))
            {
                return results;
            }
            
            if (eaElement.Stereotype == SDMModelingMain.StatementNodeStereotype 
                || eaElement.Stereotype == SDMModelingMain.StoryNodeStereotype
                || eaElement.Stereotype == SDMModelingMain.StopNodeStereotype)
            {
                int incomingCount = 0;
                foreach (SQLConnector edge in eaElement.Connectors)
                {
                    if (edge.SupplierID == eaElement.ElementID)
                    {
                        incomingCount++;
                    }
                }
                if (incomingCount == 0)
                    results.Add("At least one incoming Edge is needed");
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

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.OnRequest;
        }

    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Consistency.Rules;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Consistency.Util;

namespace EAEcoreAddin.Consistency.Rules
{
    abstract class DiagramRule : Rule 
    {
        public override List<RuleResult> getRuleResult(Object eaObject, SQLRepository repository)
        {
            List<RuleResult> results = new List<RuleResult>();
            foreach (String ruleOutput in doRule(eaObject as SQLDiagram, repository))
            {
                results.Add(ConsistencyUtil.computeRuleResult(this, eaObject as SQLDiagram, ruleOutput));
            }
            return results;
            
        }

        public override List<String> doRule(object eaObject, SQLRepository repository)
        {
            SQLDiagram diagram = eaObject as SQLDiagram;
            return doRule(diagram, repository);
        }

        public override void doRuleQuickFix(object eaObject, SQLRepository repository, int i, String errorMessage)
        {
            doRuleQuickFix(eaObject as SQLDiagram, repository, i, errorMessage);
        }

        public abstract List<String> doRule(SQLDiagram eaDiagram, SQLRepository repository);
        public abstract void doRuleQuickFix(SQLDiagram eaDiagram, EA.Repository repository, int i, String errorMessage);

    }
}

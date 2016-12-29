using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ActivityNodeRules
{
    class EvacuatedDiagramNameNotEqual : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == SDMModelingMain.StoryNodeStereotype)
            {
                if (eaElement.Diagrams.Count == 1)
                {
                    SQLDiagram evacDiagram = eaElement.Diagrams.GetAt(0);
                    if(evacDiagram.Name != eaElement.Name)
                        results.Add("Diagram of evacuated storypattern must have the same name as the storypattern itself");
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLRepository repository, int i, String errorMessage)
        {
            if (i == 0)
            {
                if (eaElement.Diagrams.Count == 1)
                {
                    EA.Diagram evacDiag = (eaElement.Diagrams.GetAt(0) as SQLDiagram).getRealDiagram();
                    evacDiag.Name = eaElement.Name;
                    evacDiag.Update();
                }
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Information;
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.Immediately;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList messages = new ArrayList();
            messages.Add("Update name of evacuated Diagram");
            return messages;
        }

        public override string getRuleID()
        {
            return "EvacuatedDiagramNameNotEqual";
        }
    }
}

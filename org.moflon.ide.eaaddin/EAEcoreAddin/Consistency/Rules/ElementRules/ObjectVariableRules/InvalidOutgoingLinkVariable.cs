using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class InvalidOutgoingLinkVariable : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype || eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
            {
                foreach (SQLConnector outgoingLink in eaElement.Connectors)
                {
                    if (outgoingLink.ClientID == eaElement.ElementID)
                    {
                        if (outgoingLink.Stereotype != TGGModelingMain.TggLinkVariableStereotype && outgoingLink.Type == Main.EAAssociationType)
                        {
                            results.Add("At least one outgoing LinkVariable has the wrong stereotype. Delete and recreate it");
                        }
                    }
                }
            }

            if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype)
            {
                foreach (SQLConnector outgoingLink in eaElement.Connectors)
                {
                    if (outgoingLink.ClientID == eaElement.ElementID)
                    {
                        if (outgoingLink.Stereotype != SDMModelingMain.LinkVariableStereotype && outgoingLink.Type == Main.EAAssociationType)
                        {
                            results.Add("At least one outgoing LinkVariable has the wrong stereotype. Delete and recreate it");
                        }
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

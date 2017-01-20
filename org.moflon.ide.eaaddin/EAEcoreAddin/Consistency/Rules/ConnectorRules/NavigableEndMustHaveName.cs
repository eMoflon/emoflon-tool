using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Consistency.RuleHandling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;

namespace EAEcoreAddin.Consistency.Rules.ConnectorRules
{
    class NavigableEndMustHaveName : ConnectorRule
    {
        public override List<String> doRule(SQLConnector eaConnector, SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaConnector.Type == ECOREModelingMain.EReferenceConnectorType)
            {
                SQLElement source = repository.GetElementByID(eaConnector.ClientID);
                SQLElement target = repository.GetElementByID(eaConnector.SupplierID);

                if (source.Stereotype == ECOREModelingMain.EClassStereotype || source.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype
                    ||target.Stereotype == ECOREModelingMain.EClassStereotype || target.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype)
                {
                    SQLConnectorEnd targetEnd = eaConnector.SupplierEnd;
                    SQLConnectorEnd sourceEnd = eaConnector.ClientEnd;
                    if (targetEnd.Navigable == "Navigable" && targetEnd.Role == "")
                        results.Add("EReference: Navigable end must have a role name");
                    if (sourceEnd.Navigable == "Navigable" && sourceEnd.Role == "")
                        results.Add("EReference: Navigable end must have a role name");
                }
            }
            return results;
        }


     
        public override void doRuleQuickFix(SQLConnector eaConnector,SQLRepository repository,int i, String errorMessage)
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
            return "NavigableEndMustHaveName";
        }
    }
}

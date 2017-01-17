using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.Consistency.Util;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ActivityNodeRules
{
    class StatementNodeInvalid : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == SDMModelingMain.StatementNodeStereotype)
            {
                StatementNode statementNode = new StatementNode(repository, eaElement);
                statementNode.loadTreeFromTaggedValue();

                if (statementNode.StatementExpression != null)
                {
                    String result = statementNode.StatementExpression.ToString();
                    if(!ConsistencyUtil.checkExpression(eaElement, statementNode.StatementExpression, repository))
                        results.Add("StatementExpression is invalid: (" + result + ")");
                }
                else
                    results.Add("StatementExpression is missing");

                foreach (SQLConnector outgoingCon in eaElement.Connectors)
                {
                    if (outgoingCon.ClientID == eaElement.ElementID)
                    {
                        ActivityEdge edge = new ActivityEdge(repository, outgoingCon);
                        edge.loadTreeFromTaggedValue();
                        if (edge.GuardType == EdgeGuard.FAILURE || edge.GuardType == EdgeGuard.SUCCESS)
                        {
                            if (statementNode.StatementExpression is MethodCallExpression)
                            {
                                MethodCallExpression mCe = statementNode.StatementExpression as MethodCallExpression;
                                SQLMethod method = repository.GetMethodByGuid(mCe.MethodGuid);

                                if (method != null && method.ReturnType != "EBoolean")
                                    results.Add("Method must be of type EBoolean if StatementNode has success/failure guards");
                            }
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

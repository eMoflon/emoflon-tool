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
    class OutgoingEdgesInvalid : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();

            if (Serialization.MocaTaggableElement.isIgnored(eaElement))
            {
                return results;
            }
            
            if (eaElement.Stereotype == SDMModelingMain.StartNodeStereotype)
            {
                checkStartNode(eaElement, results);
            }

            else if (eaElement.Stereotype == SDMModelingMain.StopNodeStereotype)
            {
                checkStopNode(eaElement, results);
            }

            else if (eaElement.Stereotype == SDMModelingMain.StatementNodeStereotype)
            {
                checkStatementNode(eaElement, results);
            }

            else if (eaElement.Stereotype == SDMModelingMain.StoryNodeStereotype)
            {
                checkStoryNode(eaElement, repository, results);
            }
            return results;
        }

        private static void checkStoryNode(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, List<String> results)
        {
            StoryNode storyNode = new StoryNode(repository, eaElement);
            storyNode.loadTreeFromTaggedValue();
            if (storyNode.ForEach)
            {
                checkForEachStoryNode(eaElement, results);
            }
            else if (!storyNode.ForEach)
            {
                checkNoForEachStoryNode(eaElement, results);
            }
        }

        private static void checkNoForEachStoryNode(SQLElement eaElement, List<String> results)
        {
            int outgoingCount = 0;
            int successEdgeExists = 0;
            int failureEdgeExists = 0;
            int foreachEdgeExists = 0;
            int endForEachEdgeExists = 0;
            foreach (SQLConnector edge in eaElement.Connectors)
            {
                if (edge.ClientID == eaElement.ElementID)
                {
                    if (edge.TransitionGuard.ToLower() == "success")
                        successEdgeExists++;
                    else if (edge.TransitionGuard.ToLower() == "failure")
                        failureEdgeExists++;
                    else if (edge.TransitionGuard.ToLower() == "each time")
                        foreachEdgeExists++;
                    else if (edge.TransitionGuard.ToLower() == "end")
                        endForEachEdgeExists++;
                    outgoingCount++;
                }
            }
            if ((outgoingCount == 1 && (successEdgeExists == 1 || failureEdgeExists == 1))
                || (outgoingCount == 2 && (successEdgeExists != 1 || failureEdgeExists != 1))
                || outgoingCount == 0
                || outgoingCount > 2
                || foreachEdgeExists > 0
                || endForEachEdgeExists > 0)
                results.Add("StoryNode must have one outgoing Edge without Guard or two outgoing Edges with Success and Failure");

        }

        private static void checkForEachStoryNode(SQLElement eaElement, List<String> results)
        {
            int outgoingCount = 0;
            int foreachEdgeExists = 0;
            int endForEachEdgeExists = 0;
            foreach (SQLConnector edge in eaElement.Connectors)
            {
                if (edge.ClientID == eaElement.ElementID)
                {
                    if (edge.TransitionGuard.ToLower() == "each time")
                        foreachEdgeExists++;
                    else if (edge.TransitionGuard.ToLower() == "end")
                        endForEachEdgeExists++;
                    outgoingCount++;
                }
            }
            if (((foreachEdgeExists != 1 || endForEachEdgeExists != 1) && outgoingCount == 2)
               || ((endForEachEdgeExists != 1 || foreachEdgeExists != 0) && outgoingCount == 1)
               || outgoingCount == 0
               || outgoingCount > 2)
                results.Add("ForEachNode must have two outgoing Edges with End and Each Time or one with End");

        }

        private static void checkStatementNode(SQLElement eaElement, List<String> results)
        {
            int outgoingCount = 0;
            int successEdgeCount = 0;
            int failureEdgeCount = 0;
            foreach (SQLConnector edge in eaElement.Connectors)
            {
                if (edge.ClientID == eaElement.ElementID)
                {
                    if (edge.TransitionGuard.ToLower() == "success")
                        successEdgeCount++;
                    else if (edge.TransitionGuard.ToLower() == "failure")
                        failureEdgeCount++;
                    outgoingCount++;
                }
            }
            if ((outgoingCount == 1 && (successEdgeCount == 1 || failureEdgeCount == 1))
                || (outgoingCount == 2 && (successEdgeCount != 1 || failureEdgeCount != 1))
                || outgoingCount > 2
                || outgoingCount < 1)
                results.Add("StatementNode must have one outgoing Edge without Guard or two outgoing Edges with Success and Failure");

        }

        private static void checkStopNode(SQLElement eaElement, List<String> results)
        {
            foreach (SQLConnector con in eaElement.Connectors)
            {
                if (eaElement.ElementID == con.ClientID)
                    results.Add("StopNode must not have outgoing Edges");
            }
        }

        private static void checkStartNode(SQLElement eaElement, List<String> results)
        {
            int outgoingCount = 0;
            foreach (SQLConnector con in eaElement.Connectors)
            {
                if (con.ClientID == eaElement.ElementID && con.Type == Main.EAControlFlowType)
                    outgoingCount++;
                else if (con.ClientID == eaElement.ElementID && con.Type == Main.EAControlFlowType)
                    results.Add("StartNode must have one outgoing Edge without TransitionGuard");
            }
            if (!(outgoingCount == 1))
                results.Add("StartNode must have one outgoing Edge without TransitionGuard");
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.OnRequest;
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

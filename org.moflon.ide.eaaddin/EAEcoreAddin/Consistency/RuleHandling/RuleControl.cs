using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Consistency.Rules.ElementRules;
using EAEcoreAddin.Consistency.Rules.ConnectorRules;
using EAEcoreAddin.Consistency.Rules.MethodRules;
using EAEcoreAddin.Consistency.Rules.AttributeRules;
using EAEcoreAddin.Consistency.Rules.PackageRules;
using EAEcoreAddin.Consistency.Rules;
using EAEcoreAddin.Consistency.Util;
using EAEcoreAddin.SQLWrapperClasses;
using System.Reflection;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Diagnostics;
using System.Windows.Forms;

namespace EAEcoreAddin.Consistency.RuleHandling
{
    public class RuleControl
    {

        private List<Rule> listOfRules = new List<Rule>();
        private static List<RuleResult> ruleResults = new List<RuleResult>();
        public RuleExecutionPoint ruleExecutionPoint;

        public ConsistencyModule ConsistencyModule { get; set; }


        public RuleControl(ConsistencyModule cModule)
        {
            this.ConsistencyModule = cModule;
        }
        
        public void setRuleExecutionPoint(RuleExecutionPoint ruleExecutionPoint)
        {
            this.ruleExecutionPoint = ruleExecutionPoint;
        }


        public Rule getRule(String ruleID)
        {
            foreach (Rule rule in listOfRules)
            {
                if (rule.getRuleID() == ruleID)
                    return rule;
            }
            return null;
        }

        

        public void doSingleRule(Object eaObject, SQLRepository repository, Rule rule)
        {
            try
            {
                if (eaObject is SQLElement)
                    doSingleElementRule(eaObject as SQLElement, repository, rule);
                else if (eaObject is SQLConnector)
                    doSingleConnectorRule(eaObject as SQLConnector, repository, rule);
                else if (eaObject is SQLMethod)
                    doSingleMethodRule(eaObject as SQLMethod, repository, rule);
                else if (eaObject is SQLAttribute)
                    doSingleAttributeRule(eaObject as SQLAttribute, repository, rule);
                else if (eaObject is SQLPackage)
                    doSinglePackageRule(eaObject as SQLPackage, repository, rule);
                else if (eaObject is SQLDiagram)
                    doSingleDiagramRule(eaObject as SQLDiagram, repository, rule);
                else
                {
                    throw new NotImplementedException();
                }
            }
            catch(Exception e)
            {
                MessageBox.Show("Error during validation \n\r " + e.StackTrace);
            }
            

        }

        public void doRules(Object eaObject, SQLRepository repository)
        {
            foreach (Rule rule in listOfRules)
            {
                if (ConsistencyModule.Canceled)
                {
                    return;
                }
                try
                {
                    doSingleRule(eaObject, repository, rule);
                }
                catch (Exception e)
                {
                    MessageBox.Show("Exception while doing validation rules \r\n " + e.StackTrace);
                }
                
            }
        }

        public void doGlobalRules(SQLRepository repository)
        {
            //end validation and write output

            foreach (Rule rule in listOfRules)
            {
                if (rule.getRuleExecutionPoint() == ruleExecutionPoint || ruleExecutionPoint == RuleExecutionPoint.OnRequest)
                {
                    if (rule is GlobalRule<SQLConnector>)
                    {
                        GlobalRule<SQLConnector> connectorRule = rule as GlobalRule<SQLConnector>;
                        Dictionary<SQLConnector, String> results = (rule as GlobalRule<SQLConnector>).doGlobalRule(repository);
                        foreach (SQLConnector con in results.Keys)
                        {
                            RuleResult ruleResult = ConsistencyUtil.computeRuleResult(rule, con, results[con]);
                            if (connectorRule.getCustomName(con, repository) != "")
                                ruleResult.NameOfObject = connectorRule.getCustomName(con, repository);
                            handleRuleResult(ruleResult, repository);
                        }
                    }
                    else if (rule is GlobalRule<SQLElement>)
                    {
                        GlobalRule<SQLElement> elementRule = rule as GlobalRule<SQLElement>;
                        Dictionary<SQLElement, String> results = (rule as GlobalRule<SQLElement>).doGlobalRule(repository);

                        foreach (SQLElement con in results.Keys)
                        {
                            RuleResult ruleResult = ConsistencyUtil.computeRuleResult(rule, con, results[con]);
                            if (elementRule.getCustomName(con, repository) != "")
                                ruleResult.NameOfObject = elementRule.getCustomName(con, repository);
                            handleRuleResult(ruleResult, repository);
                        }

                        
                    }
                }
            }
        }

        #region rule initialize features

        //register your rules here
        public void registerRules()
        {
            //all namespaces defined will be searched for rule classes
            List<String> namespacesToFindRules = new List<String>();
            namespacesToFindRules.Add("EAEcoreAddin.Consistency.Rules.ElementRules");
            namespacesToFindRules.Add("EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules");
            namespacesToFindRules.Add("EAEcoreAddin.Consistency.Rules.ElementRules.ActivityNodeRules");
            namespacesToFindRules.Add("EAEcoreAddin.Consistency.Rules.ConnectorRules");
            namespacesToFindRules.Add("EAEcoreAddin.Consistency.Rules.MethodRules");
            namespacesToFindRules.Add("EAEcoreAddin.Consistency.Rules.AttributeRules");
            namespacesToFindRules.Add("EAEcoreAddin.Consistency.Rules.PackageRules");

            //all rule class names defined here will be ignored from the validation framework
            List<String> ruleclassesToAvoid = new List<String>();
            
            //dynamically create objects for all relevant rule classes
            Assembly asm = Assembly.GetExecutingAssembly();

            foreach (Type type in asm.GetTypes())
            {
                if (type.Namespace != null && type.Namespace.Contains("EAEcoreAddin.Consistency.Rules") && !type.IsAbstract)
                {
                    Object actRuleClass = Activator.CreateInstance(type);
                    if (actRuleClass is Rule)
                    {
                        registerRule(actRuleClass as Rule);
                    }
                }
            }
        }

        private void registerRule(Rule rule)
        {
            listOfRules.Add(rule);
        }


        #endregion

        #region add and remove features for failed rules list

        public List<RuleResult> getRuleResults()
        {
            return ruleResults;
        }


        public void handleRuleResult(RuleResult ruleResult, SQLRepository repository)
        {
            addRuleResult(ruleResult);           
        }

        private void addRuleResult(RuleResult ruleResult)
        {
            if (!ruleResults.Contains(ruleResult) && !resultAlreadyIn(ruleResult))
            {
                ruleResults.Add(ruleResult);
            }
        }

        private Boolean resultAlreadyIn(RuleResult result)
        {
            foreach (RuleResult actResult in ruleResults)
            {
                if (result.resultEqual(actResult))
                    return true;
            }
            return false;
        }

        public void clearRuleResults()
        {
            ruleResults.Clear();
        }
        
        

        public void deleteRuleResults(EA.ObjectType objectType, int elementID)
        {
            List<RuleResult> resultsToDelete = new List<RuleResult>();
            foreach (RuleResult actResult in ruleResults)
            {
                if (actResult.ObjectType == objectType && actResult.ObjectID == elementID)
                    resultsToDelete.Add(actResult);
            }
            foreach (RuleResult actResult in resultsToDelete)
            {
                ruleResults.Remove(actResult);
            }
        }

        public void deleteRuleResult(String ruleID, int elementID)
        {
            RuleResult resultToDelete = null;
            foreach (RuleResult actResult in ruleResults)
            {
                if (actResult.ObjectID == elementID && actResult.RuleID == ruleID)
                {
                    resultToDelete = actResult;
                    break;
                }
            }
            if(resultToDelete != null)
                ruleResults.Remove(resultToDelete);
        }

        public void sortRuleResults()
        {
            ruleResults.Sort(new RuleResultIComparer());
        }

        #endregion

        #region ObjectType doRule Methods
        

        public void doSingleElementRule(SQLElement element, SQLRepository repository, Rule rule)
        {
            if (rule is ElementRule && (ruleExecutionPoint == rule.getRuleExecutionPoint() || ruleExecutionPoint == RuleExecutionPoint.OnRequest))
            {
                foreach (RuleResult result in rule.getRuleResult(element, repository))
                {
                    if (!result.Passed)
                        handleRuleResult(result, repository);
                }
                
            }
        }

        public void doSingleAttributeRule(SQLAttribute attribute, SQLRepository repository, Rule rule)
        {
            if (rule is AttributeRule && (ruleExecutionPoint == rule.getRuleExecutionPoint() || ruleExecutionPoint == RuleExecutionPoint.OnRequest))
            {
                foreach (RuleResult result in rule.getRuleResult(attribute, repository))
                {
                    if (!result.Passed)
                        handleRuleResult(result, repository);
                }
            }
        }

        public void doSingleMethodRule(SQLMethod method, SQLRepository repository, Rule rule)
        {
            if (rule is MethodRule && (ruleExecutionPoint == rule.getRuleExecutionPoint() || ruleExecutionPoint == RuleExecutionPoint.OnRequest))
            {
                foreach (RuleResult result in rule.getRuleResult(method, repository))
                {
                    if (!result.Passed)
                        handleRuleResult(result, repository);
                }
            }
        }

        public void doSingleConnectorRule(SQLConnector connector, SQLRepository repository, Rule rule)
        {
            if (rule is ConnectorRule && (ruleExecutionPoint == rule.getRuleExecutionPoint() || ruleExecutionPoint == RuleExecutionPoint.OnRequest))
            {
                foreach (RuleResult result in rule.getRuleResult(connector, repository))
                {
                    if (!result.Passed)
                        handleRuleResult(result, repository);
                }
            }
        }

        public void doSinglePackageRule(SQLPackage package, SQLRepository repository, Rule rule)
        {
            if (rule is PackageRule && (ruleExecutionPoint == rule.getRuleExecutionPoint() || ruleExecutionPoint == RuleExecutionPoint.OnRequest))
            {
                foreach (RuleResult result in rule.getRuleResult(package, repository))
                {
                    if (!result.Passed)
                        handleRuleResult(result, repository);
                }
            }
        }

        public void doSingleDiagramRule(SQLDiagram diagram, SQLRepository repository, Rule rule)
        {
            if (rule is DiagramRule && (ruleExecutionPoint == rule.getRuleExecutionPoint() || ruleExecutionPoint == RuleExecutionPoint.OnRequest))
            {
                foreach (RuleResult result in rule.getRuleResult(diagram, repository))
                {
                    if (!result.Passed)
                        handleRuleResult(result, repository);
                }
            }
        }

        #endregion




    }
}

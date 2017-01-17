using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using System.Collections;
using EAEcoreAddin.Serialization.MocaTree;
using EAEcoreAddin.Serialization;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using EAEcoreAddin.Util;
using EAEcoreAddin.Consistency.Rules.ElementRules;

namespace EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper
{
    public class TGGRule : StoryPattern
    {
        internal Domain source;
        internal Domain target;
        internal Domain correspondence;
        public SQLElement rule;
        public TGG TGGPackage { get; set; }



        public static readonly String CspsChildNodeName = "csps";
        public static readonly String CspSpecAttributeName = "cspSpec";


        public static readonly String MainMethodGuid = "MainMethodGuid";

        public TGGRule(SQLRepository repository, SQLElement ruleElement)
            : base(repository, ruleElement)
        {
            this.Name = ruleElement.Name;
            this.rule = ruleElement;
            this.Guid = ruleElement.ElementGUID;
        }

        public void additionalAttributesDuringExport(MocaNode currentNode)
        {
            currentNode.getAttributeOrCreate(TGGRule.CspSpecAttributeName).Value = "";

            String refinesString = computeRefinementString();
            currentNode.appendChildAttribute("refines", refinesString);

            String kernelString = computeKernelString();
            currentNode.appendChildAttribute("kernel", kernelString);
        }

        



        public List<SQLElement> getAllBaseRules()
        {
            List<SQLElement> rules = getDirectBaseRules(rule);
            for (int i = 0; i < rules.Count; i++)
            {
                rules.AddRange(getDirectBaseRules(rules[i]));
            }

            List<String> guids = new List<string>();
            List<SQLElement> toDelete = new List<SQLElement>();
            foreach (SQLElement element in rules)
            {
                if(guids.Contains(element.ElementGUID))
                {
                    toDelete.Add(element);
                }
                guids.Add(element.ElementGUID);
            }
            foreach(SQLElement deleteRule in toDelete)
            {
                rules.Remove(deleteRule);
            }

            return rules;
        }

        public List<SQLElement> getDirectBaseRules(SQLElement rule)
        {
            List<SQLElement> list = new List<SQLElement>();

            //if rule is contained in Rule-Set
            if (rule.ParentID != 0)
            {
                SQLElement parentElement = Repository.GetElementByID(rule.ParentID);
                if (parentElement.Stereotype == TGGModelingMain.TggRuleSetStereotype)
                {
                    list.AddRange(getDirectBaseRules(parentElement));
                }
            }
            
            foreach (SQLElement baseClass in rule.BaseClasses)
            {
                list.Add(baseClass);
            }



            foreach (SQLConnector con in rule.Connectors)
            {
                if (con.Stereotype == TGGModelingMain.TggCustomGeneralizationStereotype && con.ClientID == rule.ElementID)
                {

                    SQLElement targetElement = Repository.GetElementByID(con.SupplierID);
                    if (targetElement.Stereotype == TGGModelingMain.TggRuleSetStereotype)
                    {
                        foreach (SQLElement ruleInsideBoundary in targetElement.Elements)
                        {
                            list.Add(ruleInsideBoundary);
                        }
                    }
                    else if (targetElement.Stereotype == TGGModelingMain.TggRuleStereotype)
                    {
                        list.Add(targetElement);
                    }
                }

            }
            return list;
        }

        public String computeRefinementString()
        {
            String refinesString = "";

            foreach (SQLElement element in getDirectBaseRules( rule))
            {
                refinesString += element.ElementGUID + " ";
            }

            return refinesString;
        }

        private string computeKernelString()
        {
            foreach (SQLConnector connector in rule.Connectors)
            {
                if (connector.SupplierID == rule.ElementID && connector.Stereotype == TGGModelingMain.TGGkernelMorphismStereotype)
                    return Repository.GetElementByID(connector.ClientID).ElementGUID;
            }
            return "";
        }

        

        public override object getObjectToBeTagged()
        {
            return this.StoryPatternElement;
        }

        public override void deserializeFromMocaTree(Serialization.MocaTree.MocaNode actNode)
        {

        }

        public override MocaNode serializeToMocaTree()
        {
            EClass dummyEClass = new EClass(this.rule, Repository);
            dummyEClass.computeFeatures();
            MocaNode ruleNode = dummyEClass.serializeToMocaTree();
            ruleNode.Name = "Rule";

            MocaNode operationsNode = ruleNode.getChildNodeWithName(EClass.OperationsChildNodeName);
            MocaNode toDelete = null;
            if (operationsNode != null)
            {
                foreach (MocaNode node in operationsNode.Children)
                {
                    MocaAttribute nameAttr = node.getAttributeOrCreate("name");
                    if (nameAttr != null && nameAttr.Value == this.Name)
                        toDelete = node;
                }
            }
            if (toDelete != null)
                operationsNode.Children.Remove(toDelete);

            ruleNode.appendChildAttribute(TGGRule.CspSpecAttributeName, "");
            ruleNode.appendChildAttribute("refines", "");
            ruleNode.appendChildAttribute("baseClasses", "");
            ruleNode.appendChildNode(StoryPattern.ObjectVariablesChildNodeName);

            return ruleNode;

        }
        public override void doEaGuiStuff()
        {
            EA.Element realelement = StoryPatternElement.getRealElement();
            realelement.StereotypeEx = TGGModelingMain.TggRuleStereotype;
            realelement.Update();

            if (realelement.Diagrams.Count > 0)
            {
                EA.Diagram ruleDiagram = (realelement.Diagrams.GetAt(0) as EA.Diagram);
                ruleDiagram.Name = realelement.Name;
                ruleDiagram.Update();
            }
        }
        

        public void setDomains(Domain sourceDomain, Domain targetDomain, Domain correspondenceDomain)
        {
            this.source = sourceDomain;
            this.target = targetDomain;
            this.correspondence = correspondenceDomain;
        }

    }
}

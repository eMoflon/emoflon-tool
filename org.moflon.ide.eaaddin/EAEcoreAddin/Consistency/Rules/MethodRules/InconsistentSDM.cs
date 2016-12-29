using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class InconsistentSDM :  MethodRule
    {

        public override List<String> doRule(SQLMethod eaMethod, SQLRepository repository)
        {
            SQLElement parent = repository.GetElementByID(eaMethod.ParentID);
            List<String> results = new List<string>();
            
            if (parent.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                foreach (SQLElement sdmContainer in parent.Elements)
                {
                    if (sdmContainer.Stereotype == SDMModelingMain.SdmContainerStereotype)
                    {
                        SQLTaggedValue associatedTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(sdmContainer, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName);
                        if(associatedTag !=  null)
                        {
                            String associatedTagValue = associatedTag.Value;
                            if (associatedTagValue == eaMethod.MethodGUID)
                            {
                                foreach (SQLElement aNode in sdmContainer.Elements)
                                {
                                    if (aNode.Stereotype == SDMModelingMain.StartNodeStereotype)
                                    {
                                        String parameters = "";
                                        foreach (SQLParameter param in eaMethod.Parameters)
                                        {
                                            parameters += param.Name + ": " + param.Type + ", ";
                                        }
                                        if (parameters.Length > 0)
                                            parameters = parameters.Substring(0, parameters.Length - 2);
                                        String nameShouldBe = parent.Name + "::" + eaMethod.Name + " (" + parameters + "): " + eaMethod.ReturnType.ToString();
                                        bool equal = nameShouldBe.StartsWith(aNode.Name);
                                        if (!equal)
                                        {
                                            results.Add("SDM Container, StartNode, and Diagram name should correspond to Method name");
                                        }
                                    }
                                }                               
                            }
                        }
                    }
                }
            }


            return results;
        }

        public override void doRuleQuickFix(SQLMethod eaMethod, SQLRepository repository, int i)
        {
            if (i == 0)
            {
                EA.Element parent = repository.GetOriginalRepository().GetElementByID(eaMethod.ParentID);

                if (parent.Stereotype == ECOREModelingMain.EClassStereotype)
                {
                    foreach (EA.Element sdmContainer in parent.Elements)
                    {
                        if (sdmContainer.Stereotype == SDMModelingMain.SdmContainerStereotype)
                        {
                            EA.TaggedValue associatedTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(sdmContainer, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName);
                            if (associatedTag != null)
                            {
                                String associatedTagValue = associatedTag.Value;
                                if (associatedTagValue == eaMethod.MethodGUID)
                                {
                                    foreach (EA.Element aNode in sdmContainer.Elements)
                                    {
                                        if (aNode.Stereotype == SDMModelingMain.StartNodeStereotype)
                                        {
                                            String parameters = "";
                                            foreach (SQLParameter param in eaMethod.Parameters)
                                            {
                                                parameters += param.Name + ": " + param.Type + ", ";
                                            }
                                            if (parameters.Length > 0)
                                                parameters = parameters.Substring(0, parameters.Length - 2);
                                            String nameShouldBe = parent.Name + "::" + eaMethod.Name + " (" + parameters + "): " + eaMethod.ReturnType.ToString();
                                            aNode.Name = nameShouldBe;
                                            aNode.Update();
                                            sdmContainer.Name = eaMethod.Name + " SDM";
                                            sdmContainer.Update();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }    
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Warning;
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.Immediately;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList messages = new ArrayList();
            messages.Add("Update name of StartNode, SDM Diagram and SDM Container");
            return messages;
        }

        public override string getRuleID()
        {
            return "InconsistentSDM";
        }
    }
}

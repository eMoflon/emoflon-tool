using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class InvalidMocaFragment : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                EClass eclass = new EClass(eaElement, repository);
                eclass.loadTreeFromTaggedValue();

                if (eclass.Name != eaElement.Name)
                    results.Add("EClass name is not equal to name of the saved EClass fragment");

                if (eclass.Guid != eaElement.ElementGUID)
                    results.Add("EClass GUID is not equal to saved fragment GUID");

            }

            else if (eaElement.Stereotype == SDMModelingMain.StartNodeStereotype || eaElement.Stereotype == SDMModelingMain.StopNodeStereotype 
                  || eaElement.Stereotype == SDMModelingMain.StoryNodeStereotype || eaElement.Stereotype == SDMModelingMain.StatementNodeStereotype)
            {
                ActivityNode aNode = SDMUtil.createCorrectActivityNode(repository, eaElement);
                if (aNode == null)
                    return new List<string>();
                aNode.loadTreeFromTaggedValue();

                if(aNode.EaGuid != eaElement.ElementGUID)
                    results.Add("ActivityNode GUID is not equal to saved fragment GUID");

                if (eaElement.Stereotype != SDMModelingMain.StopNodeStereotype && aNode.Name != eaElement.Name && eaElement.Stereotype != SDMModelingMain.StartNodeStereotype)
                    results.Add("ActivityNode name is not equal to name of the saved fragment");

            }

            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            if (i == 0)
            {
                if (eaElement.Stereotype == ECOREModelingMain.EClassStereotype)
                {
                    EClass eclass = new EClass(eaElement, repository);
                    eclass.saveTreeToEATaggedValue(true);

                }

                else if (eaElement.Stereotype == SDMModelingMain.StartNodeStereotype || eaElement.Stereotype == SDMModelingMain.StopNodeStereotype
                      || eaElement.Stereotype == SDMModelingMain.StoryNodeStereotype || eaElement.Stereotype == SDMModelingMain.StatementNodeStereotype)
                {
                    ActivityNode aNode = SDMUtil.createCorrectActivityNode(repository, eaElement);
                    aNode.loadTreeFromTaggedValue();
                    aNode.saveTreeToEATaggedValue(true);
                }
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            if (errorMessage == "ActivityNode name is not equal to name of the saved fragment")
            {
                return RuleErrorLevel.Information;
            }
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList msg = new ArrayList();
            msg.Add("Update the EClass fragment");
            return msg;
        }
    }
}

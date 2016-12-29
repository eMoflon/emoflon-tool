using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class EmptyObjectVariableNameForbidden : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype
                || eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype
                || eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
            {
                if(eaElement.Name == "")
                    results.Add("ObjectVariable must have a name");
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            if (i == 0)
            {
                EA.Element realElement = eaElement.getRealElement();
                SQLElement classifier = repository.GetElementByID(eaElement.ClassifierID);
                realElement.Name = classifier.Name.Substring(0,1).ToLower() + classifier.Name.Substring(1, classifier.Name.Length - 1);
                realElement.Update();
            }
            else if (i == 1)
            {
                ObjectVariable ov = ObjectVariable.createCorrectOvType(eaElement, repository);
                OvDialog ovDialog = new OvDialog(repository, ov);
                ovDialog.ShowDialog();

            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList messages = new ArrayList();
            messages.Add("Change name to classifier name beginning with lowercase");
            messages.Add("Open Object Dialog");
            return messages;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Consistency.Util;
using System.Collections;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling.ObjectVariableDialog;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class InvalidAssignmentConstraint : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();

            if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype
                || eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype
                || eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
            {
                ObjectVariable ov = new ObjectVariable(eaElement, repository);
                ov.loadTreeFromTaggedValue();

                String output = "";
                
                foreach (Constraint constraint in ov.Constraints)
                {
                    if (!ConsistencyUtil.checkConstraint(eaElement, constraint, repository))
                        output += " - (" + constraint.ToString() + ")";
                }

                foreach (AttributeAssignment attrAssignment in ov.AttributeAssignments)
                {
                    if (!ConsistencyUtil.checkAttributeAssignment(eaElement, attrAssignment, repository))
                        output += " - (" + attrAssignment.ToString() + ")";
                }

                if (output != "")
                {
                    output = "ObjectVariable contains invalid Constraints / Assignments: " + output;
                    results.Add(output);
                }

            }

            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            if (i == 0)
            {
                ObjectVariable ov = ObjectVariable.createCorrectOvType(eaElement, repository);
                OvDialog ovDialog = new OvDialog(repository, ov);
                ovDialog.ShowDialog();
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList msgs = new ArrayList();
            msgs.Add("Update ObjectVariable manually");
            return msgs;
        }
    }
}

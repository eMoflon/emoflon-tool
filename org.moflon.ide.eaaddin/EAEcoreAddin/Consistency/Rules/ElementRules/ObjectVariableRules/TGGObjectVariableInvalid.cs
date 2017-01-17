using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.TGGModeling.Util;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class TGGObjectVariableInvalid : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype || eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
            {
                ObjectVariable tggOv = ObjectVariable.createCorrectOvType(eaElement, repository);
                tggOv.loadTreeFromTaggedValue();
                if (tggOv.BindingOperator == Modeling.SDMModeling.SDMExportWrapper.patterns.BindingOperator.CREATE && tggOv.Constraints.Count > 0)
                    results.Add("Constraints and BindingOperator create is not allowed");
                else if (tggOv.BindingOperator == Modeling.SDMModeling.SDMExportWrapper.patterns.BindingOperator.CHECK_ONLY && tggOv.AttributeAssignments.Count > 0)
                    results.Add("AttributeAssigmnments and BindingOperator check_only is not allowed");

                else if (tggOv.BindingState == Modeling.SDMModeling.SDMExportWrapper.patterns.BindingState.BOUND)
                {
                    Boolean valid = false;
                    foreach (SQLConnector con in eaElement.Connectors)
                    {
                        if (con.SupplierID == eaElement.ElementID && con.Type == Main.EADependencyType)
                            valid = true;
                    }
                    if (!valid)
                    {
                        SQLTaggedValue refinedtag = EAUtil.findTaggedValue(eaElement, TGGObjectVariable.RefinedTaggedValueName);
                        if (refinedtag == null || refinedtag.Value == "false")
                        {
                            results.Add("Bound TGG ObjectVariables must be the target of a TGG BindingExpression");
                        }
                    }
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            if (errorMessage == "AttributeAssignments and BindingOperator check_only is not allowed")
            {
                if (i == 0)
                {
                    TGGObjectVariable tggOv = new TGGObjectVariable(eaElement, repository);
                    tggOv.loadTreeFromTaggedValue();
                    TGGModelingUtil.assignmentsToConstraints(tggOv, repository);
                    tggOv.saveTreeToEATaggedValue(true);
                }
                else if (i == 1)
                {
                    TGGObjectVariable tggOv = new TGGObjectVariable(eaElement, repository);
                    tggOv.loadTreeFromTaggedValue();
                    tggOv.BindingOperator = BindingOperator.CREATE;
                    tggOv.saveTreeToEATaggedValue(true);
                }
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList msg = new ArrayList();
            if (errorMessage == "AttributeAssigmnments and BindingOperator check_only is not allowed")
            {
                msg.Add("Convert assignment to assertion");
                msg.Add("Set bindingOperator to create");
            }
            return msg;
        }


    }
}

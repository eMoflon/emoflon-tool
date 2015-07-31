using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Consistency.Rules.ConnectorRules
{
    class LvWrongBindingSemantics : ConnectorRule
    {
        public override List<string> doRule(SQLWrapperClasses.SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository)
        {
            var result = new List<String>();
            if (eaConnector.Stereotype == SDMModelingMain.LinkVariableStereotype || eaConnector.Stereotype == TGGModelingMain.TggLinkVariableStereotype)
            {
                SQLConnectorTag tag = EAUtil.findTaggedValue(eaConnector, ObjectVariable.BindingSemanticsTaggedValueName);
                if (tag != null && tag.Value.ToLower() == "optional")
                {
                    result.Add("Optional ist not supported anymore");
                }
            }
            return result;
        }

        public override void doRuleQuickFix(SQLWrapperClasses.SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, string errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

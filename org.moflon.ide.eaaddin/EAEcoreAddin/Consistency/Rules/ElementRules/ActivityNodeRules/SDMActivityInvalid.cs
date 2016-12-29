using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Consistency.Util;

namespace EAEcoreAddin.Consistency.Rules
{
    class SDMActivityInvalid : ElementRule
    {
        public override List<String> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaElement.Stereotype == SDMModelingMain.SdmContainerStereotype)
            {
                Activity activity = new Activity(eaElement, repository);
                if (eaElement.ParentID != activity.OwningOperation.EaMethod.ParentID)
                {
                    results.Add("SDM Activity and related EOperation are no children of the same EClass");
                }

                if(!ConsistencyUtil.isValidActivityNodeName(activity.Name))
                {
                    results.Add("SDM Activity names should consist of: a-z, A-Z, 0-9, _, -, whitespaces,");
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            Activity activity = new Activity(eaElement, repository);
            EA.Element realElement = eaElement.getRealElement();
            realElement.ParentID = activity.OwningOperation.EaMethod.ParentID;
            realElement.Update();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList msg = new ArrayList();
            msg.Add("Move SDM Activity to correct EClass");
            return msg;
        }
    }
}

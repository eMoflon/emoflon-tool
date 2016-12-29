using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.ECOREModeling.Util;
using EAEcoreAddin.Modeling.ECOREModeling;
using System.Web;

namespace EAEcoreAddin.Consistency.Rules.MethodRules
{
    class InvalidReturnValue : MethodRule
    {
        public override List<String> doRule(SQLMethod eaMethod, SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            SQLElement parentClass = repository.GetElementByID(eaMethod.ParentID);
            if (parentClass.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                //if return type of method is a ecore built in type
                if (EcoreUtil.ecoreEDataTypes.Contains(eaMethod.ReturnType) || eaMethod.ReturnType == "void")
                    return new List<string>();

                if (eaMethod.ClassifierID == "0")
                {
                    String type = HttpUtility.HtmlDecode(eaMethod.ReturnType);
                    if (type == "EEList<E>" || type == "EMap<K,V>")
                    {
                        results.Add(@"Unsupported EDatatype, This EDatatype is not currently supported, please choose another EDatatype or create a new EDatatype with an appropriate alias (mapping to Java type)");               
                    }
                    else
                    {
                        results.Add( "Return value is invalid - Please choose a model element or use a known Ecore datatype");               
                    }
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLMethod eaMethod, SQLRepository repository, int i)
        {
            return;
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.Immediately;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            return new System.Collections.ArrayList();
        }

        public override string getRuleID()
        {
            return "InvalidReturnValue";
        }
    }
}

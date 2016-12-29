using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.ECOREModeling.Util;
using System.Web;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.MethodRules
{
    class InvalidParameterTypes : MethodRule
    {
        public override List<String> doRule(SQLMethod eaMethod, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            SQLElement parentClass = repository.GetElementByID(eaMethod.ParentID);
            if (parentClass.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                foreach (SQLParameter param in eaMethod.Parameters)
                {
                    Boolean isClassifier = false;
                    Boolean validDatatype = false;
                    if (EcoreUtil.ecoreEDataTypes.Contains(param.Type))
                    {
                        validDatatype = true;
                    }
                    if (param.ClassifierID != "0" && param.ClassifierID != "")
                    {
                        try
                        {
                            SQLElement classifier = repository.GetElementByID(int.Parse(param.ClassifierID));
                            isClassifier = true;
                        }
                        catch
                        {
                        }
                    }

                    if (!validDatatype && !isClassifier)
                    {
                        String type = HttpUtility.HtmlDecode(param.Type);
                        if (type == "EEList<E>" || type == "EMap<K,V>")
                        {
                            results.Add(@"Unsupported EDatatype, This EDatatype is not currently supported, please choose another EDatatype or create a new EDatatype with an appropriate alias (mapping to Java type) - " + param.Name + ":" + param.Type);
                        }
                        else
                        {
                            results.Add(@"Type is invalid - Please choose a model element or use a known Ecore datatype - " + param.Name + ":" + param.Type);
                        }
                    }
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLMethod eaMethod, SQLWrapperClasses.SQLRepository repository, int i)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

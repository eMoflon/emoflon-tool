using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.ECOREModeling.Util;
using System.Web;

namespace EAEcoreAddin.Consistency.Rules.AttributeRules
{
    class InvalidAttributeType : AttributeRule
    {
        public override List<String> doRule(SQLAttribute eaAttribute, SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            SQLElement parentClass = repository.GetElementByID(eaAttribute.ParentID);
            if (parentClass.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                //if return type of method is a ecore built in type
                if (EcoreUtil.ecoreEDataTypes.Contains(eaAttribute.Type))
                    return new List<string>();
                //if the attribute is a part of an enumeration there is no type needed         
                if (eaAttribute.Stereotype == "enum")
                    return new List<string>();

                foreach (SQLTemplate tmpl in (parentClass as SQLElement).Templates)
                {
                    if (tmpl.Name == eaAttribute.Type)
                        return new List<string>();
                }


                if (eaAttribute.ClassifierID == 0)
                {
                    String type = HttpUtility.HtmlDecode(eaAttribute.Type);
                    if (type == "EEList<E>" || type == "EMap<K,V>")
                    {
                        results.Add(@"Unsupported EDatatype, This EDatatype is not currently supported, please choose another EDatatype or create a new EDatatype with an appropriate alias (mapping to Java type)");
                    }
                    else
                    {
                        results.Add("Attribute type is invalid - Please choose a model element or use a known Ecore datatype");
                    }
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLAttribute eaAttribute, SQLRepository repository, int i, String errorMessage)
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
            return "InvalidAttributeType";
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;

namespace EAEcoreAddin.Consistency.Rules.AttributeRules
{
    class AttributeAndReferenceNamesMustBeExplicit : AttributeRule
    {
        public override List<String> doRule(SQLAttribute eaAttribute, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            SQLElement parentClass = repository.GetElementByID(eaAttribute.ParentID);
            if (parentClass.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                List<String> attRefNames = new List<String>();

                foreach (SQLAttribute attribute in parentClass.Attributes)
                {
                    attRefNames.Add(attribute.Name);
                }

                foreach (SQLConnector reference in parentClass.Connectors)
                {
                    if (reference.Type == ECOREModelingMain.EReferenceConnectorType)
                    {
                        if (reference.ClientID == parentClass.ElementID)
                        {
                            if (reference.SupplierEnd.Navigable == "Navigable")
                            {
                                attRefNames.Add(reference.SupplierEnd.Role);
                            }
                        }
                        if (reference.SupplierID == parentClass.ElementID)
                        {
                            if (reference.ClientEnd.Navigable == "Navigable")
                            {
                               attRefNames.Add(reference.ClientEnd.Role);
                            }
                        }
                    }
                }
                if (attRefNames.LastIndexOf(eaAttribute.Name) != attRefNames.IndexOf(eaAttribute.Name))
                {
                    results.Add("All attribute and reference names must be explicit");
                }

            }
            return results;
        }

        public override void doRuleQuickFix(SQLAttribute eaAttribute, SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

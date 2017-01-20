using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ConnectorRules
{
    class RoleNameInvalid : ConnectorRule
    {
        public override List<String> doRule(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            SQLElement clientClass = repository.GetElementByID(eaConnector.ClientID);
            SQLElement suppClass = repository.GetElementByID(eaConnector.SupplierID);
            if (clientClass.Stereotype == ECOREModelingMain.EClassStereotype && eaConnector.Type == ECOREModelingMain.EReferenceConnectorType)
            {
                if (eaConnector.SupplierEnd.Role != "")
                {
                    Char first = eaConnector.SupplierEnd.Role[0];
                    if (Char.IsUpper(first))
                        results.Add("Supplier Rolename should not start with uppercase");
                }
                if (eaConnector.ClientEnd.Role != "")
                {
                    Char first = eaConnector.ClientEnd.Role[0];
                    if (Char.IsUpper(first))
                        results.Add("Client Rolename should not start with uppercase");
                }
                if (eaConnector.SupplierEnd.Role == clientClass.Name)
                    results.Add("Rolename must not be equal to EClass name [" +clientClass.Name+ "]");
                
                if (eaConnector.ClientEnd.Role == suppClass.Name)
                    results.Add("Rolename must not be equal to EClass name [" +suppClass.Name+ "]");

            }
            return results;
        }

        public override void doRuleQuickFix(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            if (errorMessage == "Supplier Rolename should not start with uppercase")
                return RuleErrorLevel.Warning;
            else if (errorMessage == "Rolename must not be equal to EClass name")
                return RuleErrorLevel.Fatal;
            return RuleErrorLevel.Information;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

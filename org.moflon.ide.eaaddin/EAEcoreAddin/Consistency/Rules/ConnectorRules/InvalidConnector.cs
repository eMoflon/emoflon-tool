using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ConnectorRules
{
    class InvalidConnector : ConnectorRule
    {
        public override List<string> doRule(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            try
            {
                repository.GetElementByID(eaConnector.ClientID);
            }
            catch
            {
                results.Add("Connector client is not existing anymore. Connector should be deleted");
            }
            try
            {
                repository.GetElementByID(eaConnector.SupplierID);
            }
            catch
            {
                results.Add("Connector supplier is not existing anymore. Connector should be deleted");
            }

            return results;
        }

        public override void doRuleQuickFix(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            if (i == 0)
            {
                EA.Element ownerElement = null;
                try
                {
                    ownerElement = repository.GetOriginalRepository().GetElementByID(eaConnector.ClientID);
                }
                catch
                {
                    try
                    {
                        ownerElement = repository.GetOriginalRepository().GetElementByID(eaConnector.SupplierID);
                    }
                    catch
                    {
                    }
                }
                if (ownerElement != null)
                {
                    short z = 0;
                    foreach (EA.Connector con in ownerElement.Connectors)
                    {
                        if (con.ConnectorGUID == eaConnector.ConnectorGUID)
                        {
                            ownerElement.Connectors.DeleteAt(z, true);
                            break;
                        }
                        z++;
                    }
                }
            }

        }

        public override RuleErrorLevel getErrorLevel(object eaObject, string errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            ArrayList msgs = new ArrayList();
            msgs.Add("Delete invalid connector");
            return msgs;
        }
    }
}

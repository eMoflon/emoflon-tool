using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;
using System.Collections;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ConnectorRules
{
    class InvalidEReferenceFragment : ConnectorRule
    {
        public override List<String> doRule(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            SQLElement clientClass = repository.GetElementByID(eaConnector.ClientID);
            if (clientClass.Stereotype == ECOREModelingMain.EClassStereotype && eaConnector.Type == ECOREModelingMain.EReferenceConnectorType)
            {
                SQLConnector con = eaConnector;

                SQLElement supplierClass = repository.GetElementByID(con.SupplierID);

                EReference eReference = new EReference(con, repository);
                eReference.loadTreeFromTaggedValue();

                if ( (con.ClientEnd.Navigable == "Navigable" && eReference.ClientEnd.typeGUID != clientClass.ElementGUID) 
                  || (con.SupplierEnd.Navigable == "Navigable" && eReference.SupplierEnd.typeGUID != supplierClass.ElementGUID))
                {
                    results.Add("Moca fragment of EReference is invalid and must be refreshed");
                }

            }
            return results;
        }

        public override void doRuleQuickFix(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            if (i == 0)
            {
                EReference eref = new EReference(eaConnector, repository);
                eref.saveTreeToEATaggedValue(true);
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            ArrayList msg = new ArrayList();
            msg.Add("Refresh EReference");
            return msg;
        }

    }
}

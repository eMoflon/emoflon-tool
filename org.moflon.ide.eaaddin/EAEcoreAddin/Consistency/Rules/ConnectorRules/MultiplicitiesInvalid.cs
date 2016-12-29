using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.ECOREModeling.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ConnectorRules
{
    class MultiplicitiesInvalid : ConnectorRule
    {


        public override List<String> doRule(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaConnector.Type == ECOREModelingMain.EReferenceConnectorType)
            {
                SQLElement sourceElement = repository.GetElementByID(eaConnector.ClientID);
                SQLElement targetElement = repository.GetElementByID(eaConnector.SupplierID);

                if (sourceElement.Stereotype == ECOREModelingMain.EClassStereotype)
                {
                    String clientResult = checkCardinality(eaConnector.ClientEnd.Cardinality);
                    String supplierResult = checkCardinality(eaConnector.SupplierEnd.Cardinality);
                    if(clientResult != "")
                    {
                        results.Add( clientResult);
                    }
                    if (supplierResult != "")
                    {
                        results.Add( supplierResult);
                    }
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            throw new NotImplementedException();
        }


        private String checkCardinality(String cardinality)
        {
            if (cardinality != "")
            {
                string lowerBound = "";
                string upperBound = "";
                int lb = int.MinValue;
                int ub = int.MinValue;
                EcoreUtil.computeLowerUpperBound(cardinality, ref lowerBound, ref upperBound);

                try
                {
                    lb = int.Parse(lowerBound);
                    if (lb < 0)
                    {
                        throw new Exception();
                    }
                }
                catch
                {
                    return "multiplicity is invalid: " + cardinality;
                }

                if (upperBound == "*")
                    return "";

                try
                {
                    ub = int.Parse(upperBound);
                    if ((ub < 0 || lb > ub ) && ub != -1 )
                    {
                        throw new Exception();
                    }
                }
                catch
                {
                    return "multiplicity is invalid: " + cardinality;
                }
            }
            return "";
        }

    }
}

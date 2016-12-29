using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;

namespace EAEcoreAddin.Consistency.Rules.ConnectorRules
{
    class MultiplicitiesMustBeSpecified : ConnectorRule
    {
        public override List<String> doRule(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            if (eaConnector.Type == ECOREModelingMain.EReferenceConnectorType && eaConnector.Stereotype == "")
            {

                SQLElement source = repository.GetElementByID(eaConnector.ClientID);
                SQLElement target = repository.GetElementByID(eaConnector.SupplierID);

                if (source.Stereotype == ECOREModelingMain.EClassStereotype || source.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype
                    || target.Stereotype == ECOREModelingMain.EClassStereotype || target.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype)
                {

                    if (eaConnector.ClientEnd.Navigable == "Navigable")
                    {
                        if (eaConnector.ClientEnd.getAggregation() == 2 && (eaConnector.ClientEnd.Cardinality != "1" && eaConnector.ClientEnd.Cardinality != "0..1"))
                        {
                            results.Add( "Containment end must have multiplicity 1 or 0..1");
                        }
                        if (eaConnector.ClientEnd.Cardinality == "")
                        {
                            results.Add("Multiplicities must be defined for navigable ends");
                        }
                    }
                    else if (eaConnector.SupplierEnd.Navigable == "Navigable")
                    {
                        if (eaConnector.SupplierEnd.getAggregation() == 2 && (eaConnector.SupplierEnd.Cardinality != "1" && eaConnector.SupplierEnd.Cardinality != "0..1"))
                        {
                            results.Add("Containment end must have multiplicity 1 or 0..1");
                        }
                        if (eaConnector.SupplierEnd.Cardinality == "")
                        {
                            results.Add("Multiplicities must be defined for navigable ends");
                        }
                    }
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLConnector eaConnector, SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.Immediately;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            return new System.Collections.ArrayList();
        }
    }
}

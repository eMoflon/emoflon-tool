using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.ECOREModeling;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.ECOREModeling.ECOREExportWrapper;

namespace EAEcoreAddin.Consistency.Rules.ConnectorRules
{
    class EndWithRoleNameMustBeNavigable : ConnectorRule
    {
        public override List<String> doRule(SQLConnector eaConnector, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            
            SQLElement sourceElement = repository.GetElementByID(eaConnector.ClientID);

            if (sourceElement.Stereotype == ECOREModelingMain.EClassStereotype && eaConnector.Type == ECOREModelingMain.EReferenceConnectorType)
            {
                if (eaConnector.ClientEnd.Role != "" && eaConnector.ClientEnd.Navigable != "Navigable")
                    results.Add("Every named end must be set to navigable");
                if (eaConnector.SupplierEnd.Role != "" && eaConnector.SupplierEnd.Navigable != "Navigable")
                    results.Add("Every named end must be set to navigable");
            }
            return results;
        }


        public override void doRuleQuickFix(SQLConnector eaConnector2, SQLRepository repository, int i, String errorMessage)
        {

            EA.Connector eaConnector = eaConnector2.getRealConnector();

            if (i == 0)
            {
                if (eaConnector.ClientEnd.Role != "" && eaConnector.SupplierEnd.Role != "")
                {
                    eaConnector.Direction = "Bi-Directional";
                }
                else if (eaConnector.ClientEnd.Role != "" && eaConnector.ClientEnd.Navigable != "Navigable")
                {
                    eaConnector.ClientEnd.Navigable = "Navigable";
                    eaConnector.ClientEnd.IsNavigable = true;
                    eaConnector.ClientEnd.Update();
                    //eaConnector.Direction = Main.EATargetSourceDirection;
                    //eaConnector.Update();
                }
                else if (eaConnector.SupplierEnd.Role != "" && eaConnector.SupplierEnd.Navigable != "Navigable")
                {
                    eaConnector.SupplierEnd.Navigable = "Navigable";
                    eaConnector.SupplierEnd.IsNavigable = true;
                    eaConnector.SupplierEnd.Update();
                    //eaConnector.Direction = "Source -> Destination";
                    //eaConnector.Update();
                }
                eaConnector.Update();

                EA.Diagram currentDiagram = repository.GetCurrentDiagram();
                if (currentDiagram != null)
                    repository.ReloadDiagram(currentDiagram.DiagramID);

                EReference eRef = new EReference(repository.GetConnectorByID(eaConnector.ConnectorID), repository);
                eRef.saveTreeToEATaggedValue(false);
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Warning;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList messages = new ArrayList();
            messages.Add("Change end to navigable");
            return messages;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.ECOREModeling;
using System.Collections;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class DuplicateEReference : ElementRule
    {
        public override List<string> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> results = new List<string>();
            if (eaElement.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                List<String> referenceNames = new List<string>();
                foreach(SQLConnector con in eaElement.Connectors) 
                {
                    if (con.Type == ECOREModelingMain.EReferenceConnectorType)
                    {
                        SQLConnectorEnd otherEnd = null;
                        if (con.ClientID == eaElement.ElementID)
                        {
                            otherEnd = con.SupplierEnd;
                        }
                        else
                        {
                            otherEnd = con.ClientEnd;
                        }
                        if (otherEnd.Role != "")
                        {
                            if (referenceNames.Contains(otherEnd.Role))
                            {
                                results.Add("Two outgoing EReferences must not have the same");
                            }
                            else
                            {
                                referenceNames.Add(otherEnd.Role);
                            }
                        }
                    }
                }
            }


            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement2, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            EA.Element eaElement = eaElement2.getRealElement();
            i = 1;
            if (eaElement.Stereotype == ECOREModelingMain.EClassStereotype)
            {
                List<String> referenceNames = new List<string>();
                foreach (EA.Connector con in eaElement.Connectors)
                {
                    if (con.Type == ECOREModelingMain.EReferenceConnectorType)
                    {
                        EA.ConnectorEnd otherEnd = null;
                        if (con.ClientID == eaElement.ElementID)
                        {
                            otherEnd = con.SupplierEnd;
                        }
                        else
                        {
                            otherEnd = con.ClientEnd;
                        }
                        if (otherEnd.Role != "")
                        {
                            if (referenceNames.Contains(otherEnd.Role))
                            {
                                otherEnd.Role = otherEnd.Role + i++;
                                otherEnd.Update();
                            }
                            else
                            {
                                referenceNames.Add(otherEnd.Role);
                            }
                        }
                    }
                    con.Update();
                }
            }
            EA.Diagram currentDiag = repository.GetCurrentDiagram();
            if (currentDiag != null)
            {
                repository.SaveDiagram(currentDiag.DiagramID);
                repository.ReloadDiagram(currentDiag.DiagramID);
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, string errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            ArrayList msgs = new ArrayList();
            msgs.Add("Add suffix number to EReference name");
            return msgs;
        }
    }
 
    
}

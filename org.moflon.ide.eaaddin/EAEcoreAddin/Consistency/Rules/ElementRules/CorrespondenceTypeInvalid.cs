using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Util;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class CorrespondenceClassInvalid : ElementRule
    {
        public override List<string> doRule(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository)
        {
            List<String> result = new List<string>();

            if (eaElement.Stereotype == TGGModelingMain.TggCorrespondenceTypeStereotype)
            {
                List<SQLElement> baseClasses = EAUtil.getBaseClasses(eaElement);
                if (baseClasses.Count > 1)
                {
                    foreach (SQLConnector con in eaElement.Connectors)
                    {
                        if (con.Type != Main.EAGeneralizationConType)
                        {
                            result.Add("Only the top level correspondence type in inheritance hierarchy should have connectors besides inheritance ");
                            break;
                        }
                    }
                }
                else
                {

                    int count = 0;
                    bool valid = true;
                    foreach (SQLConnector con in eaElement.Connectors)
                    {
                            
                        if (con.Type == Main.EAAssociationType)
                        {
                            count++;
                            SQLConnectorEnd rightEnd = null;
                            SQLConnectorEnd wrongEnd = null;
                            if (con.ClientID == eaElement.ElementID)
                            {
                                wrongEnd = con.ClientEnd;
                                rightEnd = con.SupplierEnd;
                            }
                            else if (con.SupplierID == eaElement.ElementID)
                            {
                                wrongEnd = con.SupplierEnd;
                                rightEnd = con.ClientEnd;
                            }

                            valid &= wrongEnd.Role == "";
                            valid &= !wrongEnd.IsNavigable;

                            valid &= (rightEnd.Role == "source" || rightEnd.Role == "target");
                            valid &= rightEnd.IsNavigable;
                            valid &= rightEnd.Cardinality == "1";
                        }
                        
                    }

                        if (count != 2 || !valid)
                        {
                            result.Add("Correspondence type should only have source and target EReference with multiplicity 1");
                        }
                    
                }
            }
            return result;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            if (i == 0)
            {
                List<SQLConnector> toDelte = new List<SQLConnector>();
                List<SQLElement> baseClasses = EAUtil.getBaseClasses(eaElement);
                if (baseClasses.Count > 1)
                {
                    
                    foreach (SQLConnector con in eaElement.Connectors)
                    {
                        if (con.Type != Main.EAGeneralizationConType)
                            toDelte.Add(con);
                    }
                }

                else
                {
                    foreach (SQLConnector con in eaElement.Connectors)
                    {
                        if (con.Type == Main.EAAssociationType)
                        {
                            bool valid = true;
                            SQLConnectorEnd rightEnd = null;
                            SQLConnectorEnd wrongEnd = null;
                            if (con.ClientID == eaElement.ElementID)
                            {
                                wrongEnd = con.ClientEnd;
                                rightEnd = con.SupplierEnd;
                            }
                            else if (con.SupplierID == eaElement.ElementID)
                            {
                                wrongEnd = con.SupplierEnd;
                                rightEnd = con.ClientEnd;
                            }

                            valid &= wrongEnd.Role == "";
                            valid &= !wrongEnd.IsNavigable;

                            valid &= (rightEnd.Role == "source" || rightEnd.Role == "target");
                            valid &= rightEnd.IsNavigable;
                            valid &= rightEnd.Cardinality == "1";
                            if (!valid)
                            {
                                toDelte.Add(con);
                            }
                        }
                    }
                }
                foreach (EA.Connector toDeleteCon in toDelte)
                {
                    EAUtil.deleteConnector(toDeleteCon, repository.GetOriginalRepository());
                }
            }
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, string errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            ArrayList msgs = new ArrayList();
            msgs.Add("Delete invalid connectors");
            return msgs;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class InvalidRuleDiagramElements : GlobalRule<SQLElement>
    {
        public override Dictionary<SQLElement, string> doGlobalRule(SQLWrapperClasses.SQLRepository repository)
        {
            Dictionary<SQLElement, string> results = new Dictionary<SQLElement, string>();

            String ruleElements = repository.SQLQuery("select * from t_object where Stereotype = '" + TGGModelingMain.TggRuleStereotype + "'");

            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(ruleElements, "Row"))
            {
                if (row != "")
                {
                    SQLElement ruleElement = new SQLElement(repository, row);
                    SQLDiagram ruleDiagram = ruleElement.Diagrams.GetAt(0) as SQLDiagram;
                    EA.Diagram rruleDiagram = EAUtil.sqlEAObjectToOriginalObject(repository, ruleDiagram) as EA.Diagram;

                    foreach (EA.DiagramObject diagObject in rruleDiagram.DiagramObjects)
                    {
                        SQLElement diagElement = repository.GetElementByID(diagObject.ElementID);
                        if (diagElement.ParentID != ruleElement.ElementID &&
                           (diagElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype
                         || diagElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype))
                        {
                            results.Add(diagElement, "Element is no child of Rule: " + ruleElement.Name + " but is contained on its Rule Diagram");
                        }
                    }
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaObject, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, string errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

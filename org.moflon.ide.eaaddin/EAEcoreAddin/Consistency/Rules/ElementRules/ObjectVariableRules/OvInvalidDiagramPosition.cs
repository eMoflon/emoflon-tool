using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Modeling.TGGModeling;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class OvInvalidDiagramPosition : GlobalRule<SQLElement>
    {
        public override Dictionary<SQLElement, string> doGlobalRule(SQLWrapperClasses.SQLRepository repository)
        {
            Dictionary<SQLElement, string> results = new Dictionary<SQLElement, string>();

            String sqlString = "select o.* FROM t_object o, t_diagramobjects a, t_diagramobjects b "+
                                "WHERE a.Object_ID = o.Object_ID "+
                                "AND b.Object_ID <> a.Object_ID "+
                                "AND b.RectRight > a.RectRight "+
                                "AND b.RectLeft < a.RectLeft "+
                                "AND b.RectTop > a.RectTop "+ 
                                "AND b.RectBottom < a.RectBottom "+
                                "AND a.Diagram_ID = b.Diagram_ID "+
                                "AND o.ParentID <> b.Object_ID";

            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(repository.SQLQuery(sqlString), "Row"))
            {
                if (row != "")
                {
                    SQLElement element = new SQLElement(repository, row);
                    if (element.Stereotype == SDMModelingMain.ObjectVariableStereotype || element.Stereotype == TGGModelingMain.TggObjectVariableStereotype)
                    {
                        results.Add(element, "Elements diagram bounds are contained by another diagram element which is not its parent");
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
            return RuleErrorLevel.Warning;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            throw new NotImplementedException();
        }
    }
}

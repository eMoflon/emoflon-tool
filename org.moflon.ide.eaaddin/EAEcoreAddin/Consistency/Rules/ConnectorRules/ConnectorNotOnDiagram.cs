using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;
using System.Collections;

namespace EAEcoreAddin.Consistency.Rules.ConnectorRules
{
    class ConnectorNotOnDiagram : GlobalRule<SQLConnector>
    {
        public override Dictionary<SQLConnector, string> doGlobalRule(SQLWrapperClasses.SQLRepository repository)
        {
            Dictionary<SQLConnector, String> results = new Dictionary<SQLConnector, string>();
            //select all connectors where are hidden diagram links and NO visible diagram links
            String sql = "select * from t_connector where Connector_ID in  ( select ConnectorID from t_diagramlinks link where HIDDEN = TRUE ) AND Connector_ID NOT IN ( select ConnectorID from t_diagramlinks link where HIDDEN = FALSE )";
            String result = repository.SQLQuery(sql);

            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
            {
                if (row != "")
                {
                    SQLConnector con = new SQLConnector(repository, row);
                    results.Add(con, "Connector is not visible on any diagram");
                }
            }
            return results;
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Warning;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            ArrayList msgs = new ArrayList();
            msgs.Add("add Connector to possible diagram");
            return msgs;
        }

        public override void doRuleQuickFix(SQLConnector eaObject, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            if (i == 0)
            {
                String sql = "select DiagramID from t_diagramlinks where ConnectorID = " + eaObject.ConnectorID;
                String result = repository.SQLQuery(sql);
                EA.Diagram diagram = repository.GetOriginalRepository().GetDiagramByID(int.Parse(EAUtil.getXMLNodeContentFromSQLQueryString(result, "DiagramID")[0]));
                foreach (EA.DiagramLink link in diagram.DiagramLinks)
                {
                    if (link.ConnectorID == eaObject.ConnectorID)
                    {
                        link.IsHidden = false;
                        link.Update();
                    }
                }
                repository.OpenDiagram(diagram.DiagramID);
                repository.ReloadDiagram(diagram.DiagramID);
            }
        }
    }
}

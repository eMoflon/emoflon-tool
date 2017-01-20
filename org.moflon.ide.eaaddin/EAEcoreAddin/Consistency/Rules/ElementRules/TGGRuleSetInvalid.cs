using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.TGGModeling;
using System.Collections;

namespace EAEcoreAddin.Consistency.Rules
{
    class TGGRuleSetInvalid : GlobalRule<SQLElement>
    {

        public override Dictionary<SQLElement, string> doGlobalRule(SQLWrapperClasses.SQLRepository repository)
        {
            Dictionary<SQLElement, string> results = new Dictionary<SQLElement, string>();

            string sqlString = @"SELECT x.* from t_diagram d, t_diagramobjects a, t_diagramobjects b, t_object o,t_object x 
                                                              WHERE d.Diagram_ID = a.Diagram_ID 
                                                              AND o.Object_ID = a.Object_ID 
                                                              AND b.Diagram_ID = a.Diagram_ID 
                                                              AND b.RectTop <= a.RectTop 
                                                              AND b.RectBottom >= a.RectBottom 
                                                              AND b.RectLeft >= a.RectLeft 
                                                              AND b.RectRight <= a.RectRight 
                                                              AND b.Object_ID <> a.Object_ID 
                                                              AND x.Object_ID = b.Object_ID 
                                                              AND x.ParentID  <> o.Object_ID
                                                              AND o.Stereotype = '" + TGGModelingMain.TggRuleSetStereotype + @"' 
                                                              AND x.Stereotype = '" + TGGModelingMain.TggRuleStereotype + "'";

            String result = repository.SQLQuery(sqlString);
            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
            {
                if (row != "")
                {
                    SQLElement element = new SQLElement(repository, row);
                    results.Add(element, "The Rule should be a children of the containing Rule-Set");
                }
            }
            return results;
        }

        public override void doRuleQuickFix(SQLElement eaObject, SQLWrapperClasses.SQLRepository repository, int i, string errorMessage)
        {
            string sqlString = @"SELECT o.Object_ID from t_diagram d, t_diagramobjects a, t_diagramobjects b, t_object o,t_object x 
                                                              WHERE d.Diagram_ID = a.Diagram_ID 
                                                              AND o.Object_ID = a.Object_ID 
                                                              AND b.Diagram_ID = a.Diagram_ID 
                                                              AND b.RectTop <= a.RectTop 
                                                              AND b.RectBottom >= a.RectBottom 
                                                              AND b.RectLeft >= a.RectLeft 
                                                              AND b.RectRight <= a.RectRight 
                                                              AND b.Object_ID <> a.Object_ID 
                                                              AND x.Object_ID = b.Object_ID 
                                                              AND x.ParentID  <> o.Object_ID
                                                              AND o.Stereotype = '" + TGGModelingMain.TggRuleSetStereotype + @"' 
                                                              AND x.Stereotype = '" + TGGModelingMain.TggRuleStereotype + @"' 
                                                              AND x.Object_ID = " + eaObject.ElementID;
            String result = repository.SQLQuery(sqlString);
            String id = EAUtil.getXMLNodeContentFromSQLQueryString(result, "Object_ID")[0];
            if(id != "")
            {
                int parentId = int.Parse(id);
                eaObject.getRealElement().ParentID = parentId;
                eaObject.getRealElement().Update();
            }
           

        }

        public override RuleErrorLevel getErrorLevel(object eaObject, string errorMessage)
        {
            return RuleErrorLevel.Error;
        }

        public override System.Collections.ArrayList getQuickFixMessages(string errorMessage)
        {
            ArrayList msgs = new ArrayList();
            msgs.Add("Set the correct ParentID");
            return msgs;
        }



    }
}

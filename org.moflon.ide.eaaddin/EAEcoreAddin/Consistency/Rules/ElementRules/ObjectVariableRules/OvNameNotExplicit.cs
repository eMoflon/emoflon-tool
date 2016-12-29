using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.Util;
using EAEcoreAddin.SQLWrapperClasses;
using System.Collections.Specialized;

namespace EAEcoreAddin.Consistency.Rules.ElementRules.ObjectVariableRules
{
    class OvNameNotExplicit : GlobalRule<SQLElement>
    {

        public override Dictionary<SQLElement, string> doGlobalRule(SQLWrapperClasses.SQLRepository repository)
        {
            NameValueCollection parentIdToName = new NameValueCollection();
            Dictionary<SQLElement, String> results = new Dictionary<SQLElement,string>();

            //SDM ObjectVariables
            string result = repository.SQLQuery(@"select a.* from t_object a, t_object b, t_object c 
                                WHERE a.Name = b.Name 
                                AND a.ea_guid <> b.ea_guid 
                                AND a.ParentID = c.Object_ID
                                AND b.parentID = c.Object_ID
                                AND a.Stereotype = '" + SDMModelingMain.ObjectVariableStereotype + @"'
                                AND b.Stereotype = '" + SDMModelingMain.ObjectVariableStereotype + @"'
                                AND c.Stereotype = '" + SDMModelingMain.StoryNodeStereotype + "'");

            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
            {
                if (row != "")
                {
                    SQLElement element = new SQLElement(repository, row);
                    String[] names = parentIdToName.GetValues(element.ParentID + "");
                    if (names == null || !names.Contains(element.Name))
                    {
                        results.Add(element, "ObjectVariable name in a single StoryPattern must be explicit");
                        parentIdToName.Add(element.ParentID + "", element.Name);
                    }
                }
            }

            //TGGObjectVariables
            result = repository.SQLQuery(@"select a.* from t_object a, t_object b, t_object c 
                                WHERE a.Name = b.Name 
                                AND a.ea_guid <> b.ea_guid 
                                AND a.ParentID = c.Object_ID
                                AND b.parentID = c.Object_ID
                                AND (a.Stereotype = '" + TGGModelingMain.TggObjectVariableStereotype + @"' OR a.Stereotype = '" + TGGModelingMain.TggCorrespondenceStereotype + @"')
                                AND (b.Stereotype = '" + TGGModelingMain.TggObjectVariableStereotype + @"' OR b.Stereotype = '" + TGGModelingMain.TggCorrespondenceStereotype + @"')
                                AND c.Stereotype = '" + TGGModelingMain.TggRuleStereotype + "'");

            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
            {
                if (row != "")
                {
                    SQLElement element = new SQLElement(repository, row);
                    String[] names = parentIdToName.GetValues(element.ParentID + "");
                    if (names == null || !names.Contains(element.Name))
                    {
                        results.Add(element, "ObjectVariable name in a single Rule must be explicit");
                        parentIdToName.Add(element.ParentID + "", element.Name);
                    }
                }
            }


            return results;
        }

        public override void doRuleQuickFix(SQLElement eaElement, SQLWrapperClasses.SQLRepository repository, int i, String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            return RuleErrorLevel.Fatal;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            throw new NotImplementedException();
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.Immediately;
        }
        
    }
}

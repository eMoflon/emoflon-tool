using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Modeling.SDMModeling;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.ECOREModeling;

namespace EAEcoreAddin.Consistency.Rules.ElementRules
{
    class ElementNotOnDiagram : GlobalRule<SQLElement>
    {

        public override Dictionary<SQLElement, string> doGlobalRule(SQLRepository repository)
        {
            Dictionary<SQLElement, string> results = new Dictionary<SQLElement, string>();
            String sql = "select * from t_object a where Object_ID not in ( select Object_ID from t_diagramobjects where Object_ID = a.Object_ID)";
            String result = repository.SQLQuery(sql);
            foreach (String row in EAUtil.getXMLNodeContentFromSQLQueryString(result, "Row"))
            {
                if (row != "")
                {
                    SQLElement eaElement = new SQLElement(repository, row);
                    if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype
                         || eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype
                         || eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype
                         || eaElement.Stereotype == SDMModelingMain.StoryNodeStereotype
                         || eaElement.Stereotype == SDMModelingMain.StatementNodeStereotype
                         || eaElement.Stereotype == SDMModelingMain.StartNodeStereotype
                         || eaElement.Stereotype == SDMModelingMain.StopNodeStereotype
                         || eaElement.Stereotype == ECOREModelingMain.EClassStereotype
                         || eaElement.Stereotype == ECOREModelingMain.EEnumStereotype
                         || eaElement.Stereotype == ECOREModelingMain.EDatatypeStereotype
                         || eaElement.Stereotype == TGGModelingMain.TggRuleStereotype)
                    {
                        results.Add(eaElement, "Element is not on any diagram");
                    }
                }
            }
            return results;
        }
        
        public override void doRuleQuickFix(SQLElement eaElement, SQLRepository repository, int i, String errorMessage)
        {
            if (i == 0)
            {
                EAUtil.deleteElement(eaElement.getRealElement(), repository);
            }
            else if (i == 1)
            {
                EA.Diagram possibleDiagram = null;
                SQLElement parent = eaElement;
                SQLPackage parentPackage = null;
                while (possibleDiagram == null)
                {
                    if (parent.ParentID != 0)
                    {
                        parent = repository.GetElementByID(parent.ParentID);
                        if (parent.Diagrams.Count > 0)
                        {
                            possibleDiagram = parent.Diagrams.GetAt(0) as EA.Diagram;
                        }
                    }
                    else
                    {
                        if (parentPackage == null)
                        {
                            parentPackage = repository.GetPackageByID(parent.PackageID);
                        }
                        else
                        {
                            parentPackage = repository.GetPackageByID(parentPackage.ParentID);
                        }
                        if(parentPackage.Diagrams.Count > 0)
                        {
                            possibleDiagram = parentPackage.Diagrams.GetAt(0) as EA.Diagram;
                        }
                    }
                    
                }
                possibleDiagram = EAUtil.sqlEAObjectToOriginalObject(repository, possibleDiagram) as EA.Diagram;
                EA.DiagramObject diagObject = possibleDiagram.DiagramObjects.AddNew("", eaElement.Type) as EA.DiagramObject;
                diagObject.left = 20;
                diagObject.right = 120;
                diagObject.top = -20;
                diagObject.bottom = -70;
                diagObject.ElementID = eaElement.ElementID;
                diagObject.Update();
                repository.OpenDiagram(possibleDiagram.DiagramID);
                EAUtil.clearSelectedDiagramObjects(repository);
                possibleDiagram.SelectedObjects.AddNew(diagObject.ElementID.ToString(), "");
                repository.ReloadDiagram(possibleDiagram.DiagramID);

            }
        }

       
        public override RuleErrorLevel getErrorLevel(object eaObject, String errorMessage)
        {
            SQLElement eaElement = eaObject as SQLElement;
            if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype)
            {
                return RuleErrorLevel.Warning;
            }
            else if (eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype
                 || eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype)
            {
                return RuleErrorLevel.Error;
            }
            else
                return RuleErrorLevel.Information;
        }

        public override RuleExecutionPoint getRuleExecutionPoint()
        {
            return RuleExecutionPoint.OnRequest;
        }

        public override System.Collections.ArrayList getQuickFixMessages(String errorMessage)
        {
            ArrayList returnList = new ArrayList();
            returnList.Add("Delete Element from Model");
            returnList.Add("Add Element to possible Diagram");
            return returnList;
        }

        public override string getCustomName(SQLElement eaObject, SQLRepository repository)
        {
            String customName = "";
            if (eaObject.Stereotype == SDMModelingMain.ObjectVariableStereotype)
            {
               
                String classifierSql = repository.SQLQuery("select * from t_object where t_object.Object_ID = " + eaObject.ClassifierID);
                String classifierName = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(classifierSql, "Name")[0];
                String OVParentID = eaObject.ParentID.ToString();
                String activityNodeParentSQL = repository.SQLQuery("select * from t_object where t_object.Object_ID = " + OVParentID);
                String containerID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(activityNodeParentSQL, "ParentID")[0];
                String methodGUIDSQL = repository.SQLQuery("select t_objectproperties.VALUE FROM t_objectproperties where t_objectproperties.Property = '" + SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName + "' and t_objectproperties.Object_ID = " + containerID);
                String methodGUID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(methodGUIDSQL, "VALUE")[0];
                String methodNamesql = repository.SQLQuery("select t_operation.Name from t_operation where t_operation.ea_guid = '" + methodGUID + "'");
                String methodName = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(methodNamesql, "Name")[0];
                customName = classifierName + "/" + methodName + "/" + eaObject.Name;
            }
            else
                customName = (eaObject as SQLElement).Name;
            return customName;
        }

        

        

        #region deprecated

        /*
        public override List<String> doRule(EA.Element eaElement, SQLRepository repository)
        {
            if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype
             || eaElement.Stereotype == TGGModelingMain.TggObjectVariableStereotype
             || eaElement.Stereotype == TGGModelingMain.TggCorrespondenceStereotype
             || eaElement.Stereotype == SDMModelingMain.StoryNodeStereotype
             || eaElement.Stereotype == SDMModelingMain.StatementNodeStereotype
             || eaElement.Stereotype == SDMModelingMain.StartNodeStereotype
             || eaElement.Stereotype == SDMModelingMain.StopNodeStereotype
             || eaElement.Stereotype == ECOREModelingMain.EClassStereotype
             || eaElement.Stereotype == ECOREModelingMain.EEnumStereotype
             || eaElement.Stereotype == ECOREModelingMain.EDatatypeStereotype
             || eaElement.Stereotype == TGGModelingMain.TggRuleStereotype)
            {
                String checkIfElementStillExists = repository.SQLQuery("select t_object.Object_ID from t_object where t_object.Object_ID = " + eaElement.ElementID);
                String objectID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(checkIfElementStillExists, "Object_ID")[0];
                if (objectID == "")
                    return "";

                String sqlResult = repository.SQLQuery("select t_diagramobjects.Diagram_ID from t_diagramobjects where t_diagramobjects.Object_ID = " + eaElement.ElementID);
                String diagramID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlResult, "Diagram_ID")[0];
                if (diagramID == "")
                    return "Element is not on any diagram perhaps it should be deleted";
            }
            return "";

        }

        public override List<RuleResult> getRuleResult(Object eaObject, SQLRepository repository)
        {
            RuleResult ruleResult = base.getRuleResult(eaObject, repository);
            EA.Element eaElement = eaObject as EA.Element;
            if (!ruleResult.Passed)
            {
                if (eaElement.Stereotype == SDMModelingMain.ObjectVariableStereotype)
                {
                    String classifierSql = repository.SQLQuery("select * from t_object where t_object.Object_ID = " + eaElement.ClassifierID);
                    String classifierName = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(classifierSql, "Name")[0];
                    String OVParentID = eaElement.ParentID.ToString();
                    String activityNodeParentSQL = repository.SQLQuery("select * from t_object where t_object.Object_ID = " + OVParentID);
                    String containerID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(activityNodeParentSQL, "ParentID")[0];
                    String methodGUIDSQL = repository.SQLQuery("select t_objectproperties.VALUE FROM t_objectproperties where t_objectproperties.Property = '" + SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName + "' and t_objectproperties.Object_ID = " + containerID);
                    String methodGUID = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(methodGUIDSQL, "VALUE")[0];
                    String methodNamesql = repository.SQLQuery("select t_operation.Name from t_operation where t_operation.ea_guid = '" + methodGUID + "'");
                    String methodName = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(methodNamesql, "Name")[0];
                    ruleResult.NameOfObject = classifierName + "/" + methodName + "/" + eaElement.Name;
                }
                else
                    ruleResult.NameOfObject = (eaObject as EA.Element).Name;
            }


            return ruleResult;
        }

        */
        #endregion

    }
}

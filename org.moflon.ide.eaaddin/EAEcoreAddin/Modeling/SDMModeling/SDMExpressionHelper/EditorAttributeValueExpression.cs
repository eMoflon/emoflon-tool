using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper
{
    class EditorAttributeValueExpression : EditorExpression
    {
        public override List<FirstObject> getFirstObjects(SQLElement elementToSearch, SQLRepository repository)
        {
            List<FirstObject> targetObjectVariables = new List<FirstObject>();

            String sdmOvQuery = repository.SQLQuery("select a.Name, min(a.ea_guid) as ea_guid, max(a.Classifier) as Classifier from t_object a, t_object b, t_object c where (a.Stereotype = '"+SDMModelingMain.ObjectVariableStereotype+"') and a.ParentID = b.Object_ID and b.ParentID = c.Object_ID and c.Object_ID = " + elementToSearch.ElementID + "and (c.Stereotype = '"+SDMModelingMain.SdmContainerStereotype+"') group by a.Name");
            String tggOvQuery = repository.SQLQuery("select a.Name, min(a.ea_guid) as ea_guid, max(a.Classifier) as Classifier from t_object a, t_object b where a.Stereotype = '"+TGGModelingMain.TggObjectVariableStereotype+"' and a.ParentID = " + elementToSearch.ElementID + "and b.Stereotype = '"+TGGModelingMain.TggRuleStereotype+"' group by a.Name");           
            List<String> ovList = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sdmOvQuery, "Row");
            ovList.AddRange(EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(tggOvQuery, "Row"));
            foreach (String targetEntry in ovList)
            {
                if (targetEntry != "")
                {
                    SQLElement actTarget = new SQLElement(repository, targetEntry);
                    FirstObject fObject = new FirstObject(actTarget);
                    targetObjectVariables.Add(fObject);
                }
            }

            return targetObjectVariables;
        }

        public override List<SecondObject> getSecondObjects(Object targetObject, SQLRepository repository)
        {
            Object targetElement = targetObject;
            List<SecondObject> sourceAttributes = new List<SecondObject>();
            SQLElement realClassifier = null;
            List<SQLElement> allClasses = new List<SQLElement>();
            if (targetObject is SQLElement)
            {
                SQLElement tempElement = targetObject as SQLElement;
                realClassifier = EAUtil.getClassifierElement(repository, tempElement.ClassifierID);
            }
            else if (targetElement is SQLParameter)
            {
                SQLParameter targetParameter = targetObject as SQLParameter;
                if(targetParameter.ClassifierID != "0" && targetParameter.ClassifierID != "")
                    realClassifier = repository.GetElementByID(int.Parse((targetObject as EA.Parameter).ClassifierID));
            }

            if (realClassifier != null)
            {
                allClasses = EAUtil.getBaseClasses(realClassifier);
                foreach (SQLElement actClass in allClasses)
                {
                    foreach (SQLAttribute attribute in actClass.Attributes)
                    {
                        SecondObject sObject = new SecondObject(attribute);
                        sourceAttributes.Add(sObject);
                    }
                }
                sourceAttributes.Sort(new EAObjectIComparer());
            }
            return sourceAttributes;
        }
    }
}

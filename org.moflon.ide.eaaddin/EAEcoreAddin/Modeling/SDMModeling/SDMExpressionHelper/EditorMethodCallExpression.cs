using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using System.Windows.Forms;
using EAEcoreAddin.Modeling.TGGModeling;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper
{
    public class EditorMethodCallExpression : EditorExpression
    {
        public override List<FirstObject> getFirstObjects(SQLElement elementToSearch, SQLRepository repository)
        {
            EditorAttributeValueExpression eAvExpression = new EditorAttributeValueExpression();
            List<FirstObject> targetObjectVariables = eAvExpression.getFirstObjects(elementToSearch, repository);
            
            //add parameters of associatedMethod
            SQLMethod associatedMethod = null;
            if(elementToSearch.Stereotype == SDMModelingMain.SdmContainerStereotype) 
            {
                SQLTaggedValue assoMethodTag = EAEcoreAddin.Util.EAUtil.findTaggedValue(elementToSearch, SDMModelingMain.SDMContainerAssociatedMethodTaggedValueName);
                associatedMethod = repository.GetMethodByGuid(assoMethodTag.Value);
            }
            else if (elementToSearch.Stereotype == TGGModelingMain.TggRuleStereotype)
            {
                associatedMethod = elementToSearch.Methods.GetAt(0);
            }
            if (associatedMethod != null)
            {
                foreach (SQLParameter parameter in associatedMethod.Parameters)
                {

                    if (parameter.ClassifierID != "0")
                    {
                        Boolean alreadyIn = false;
                        foreach (Object searchTarget in targetObjectVariables)
                        {
                            if (searchTarget is SQLElement)
                            {
                                if ((searchTarget as SQLElement).Name == parameter.Name)
                                    alreadyIn = true;
                            }
                        }
                        if (!alreadyIn)
                        {
                            targetObjectVariables.Add(new FirstObject(parameter));
                        }
                    }
                }
            }
            
            return targetObjectVariables;
        }

        public override List<SecondObject> getSecondObjects(Object targetObject, SQLRepository repository)
        {
            Object targetElement = targetObject;
            List<SecondObject> sourceMethods = new List<SecondObject>();
            SQLElement realClassifier = null;
            List<SQLElement> allClasses = new List<SQLElement>();
            if (targetObject is SQLElement)
            {
                realClassifier = EAUtil.getClassifierElement(repository, (targetObject as SQLElement).ClassifierID);
            }
            else if (targetElement is SQLParameter)
            {
                if ((targetObject as SQLParameter).ClassifierID != "0" && (targetObject as SQLParameter).ClassifierID != "")
                {
                    try
                    {
                        realClassifier = repository.GetElementByID(int.Parse((targetObject as SQLParameter).ClassifierID));
                    }
                    catch
                    {
                        realClassifier = null;
                    }
                }
            }
            if (realClassifier != null)
            {
                allClasses = EAUtil.getBaseClasses(realClassifier);
                foreach (SQLElement actClass in allClasses)
                {
                    foreach (SQLMethod method in actClass.Methods)
                    {
                        SecondObject sObject = new SecondObject(method);
                        sourceMethods.Add(sObject);
                    }
                }
                sourceMethods.Sort(new EAObjectIComparer());
            }
            
            return sourceMethods;    
        }

        public ArrayList getParameters(EA.Method method, SQLRepository repository) 
        {
            ArrayList parameters = new ArrayList();
            foreach(EA.Parameter actParameter in method.Parameters) 
            {
                parameters.Add(actParameter);
            }
            return parameters;
        }

        public static String getTargetName(MethodCallExpression methodCallExpression)
        {
            string targetName = "";
            if(methodCallExpression.Target is ObjectVariableExpression) 
            {
                ObjectVariableExpression ovExp = methodCallExpression.Target as ObjectVariableExpression;
                targetName = ovExp.ObjectVariableName;
            }
            else if (methodCallExpression.Target is ParameterExpression)
            {
                ParameterExpression pExp = methodCallExpression.Target as ParameterExpression;
                targetName = pExp.ParameterName;
            }
            return targetName;
        }

        public static String getOperationName(SQLRepository rep ,MethodCallExpression methodCallExpression)
        {
            SQLMethod method =  rep.GetMethodByGuid(methodCallExpression.MethodGuid);
            return EAUtil.getMethodSignature(method);
        }

        public static MethodCallExpression generateMethodCallExpression(SQLRepository sqlRepository ,Object target, SQLMethod method)
        {
            MethodCallExpression methodCallExpression = new MethodCallExpression(method, sqlRepository);
            Expression targetExpression = null;
            foreach (SQLParameter parameter in method.Parameters)
            {
                ParameterBinding parameterBinding = new ParameterBinding(sqlRepository, parameter);
                Expression expression = new LiteralExpression("null");
                parameterBinding.ValueExpression = expression;
                methodCallExpression.OwnedParameterBinding.Add(parameterBinding);
            }


            //check if the target is an ObjectVariable or a Parameter
                
            if (target is SQLParameter)
            {
                SQLParameter parameter = target as SQLParameter;
                ParameterExpression parameterExpression = new ParameterExpression(sqlRepository, parameter);
                targetExpression = parameterExpression;
            }
            else if (target is SQLElement)
            {
                SQLElement element = target as SQLElement;
                ObjectVariableExpression ovExp = new ObjectVariableExpression(element, sqlRepository);
                targetExpression = ovExp;
            }
            methodCallExpression.Target = targetExpression;
            return methodCallExpression;
        }
    }
}

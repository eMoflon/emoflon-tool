using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.activities;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper;
using EAEcoreAddin.Util;
using EAEcoreAddin.Modeling.SDMModeling.Util;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using System.Runtime.InteropServices;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.StopNodeDialog
{
    /// <summary>
    /// This class contains the default implementation of IExpressionProvider. 
    /// </summary>
    abstract class CommonExpressionProvider : IExpressionProvider
    {
        public SQLRepository repository;
        List<String> expressionStringList;
        List<FirstObject> firstObjects;
        List<SecondObject> secondObjects;

        String selectedExpressionString;
        int selectedSecondObjectIndex;
        int selectedFirstObjectIndex;
        private Expression expression;
        
               

        public CommonExpressionProvider(SQLRepository repository)
        {
            this.firstObjects = new List<FirstObject>();
            this.secondObjects = new List<SecondObject>();
            this.expressionStringList = new List<string>();
            this.methodParameterClassifierIdFilter = 0;
            this.repository = repository;
        }

        public virtual List<string> getExpressionStringList()
        {
            expressionStringList.Clear();
            expressionStringList.Add("ObjectVariableExpression");
            expressionStringList.Add("LiteralExpression");
            expressionStringList.Add("AttributeValueExpression");
            expressionStringList.Add("MethodCallExpression");
            expressionStringList.Add("ParameterExpression");            
            expressionStringList.Add("Void");
            return this.expressionStringList;
        }

        public List<string> getFirstStringList(String selectedExpressionString)
        {
            this.selectedExpressionString = selectedExpressionString;
            this.expression = null;

            EditorExpression editorExpression = EditorExpression.createEditorExpression(selectedExpressionString);
            this.firstObjects.Clear();
            this.secondObjects.Clear();

            List<String> objectStringList = new List<string>();
            List<FirstObject> objectListTemp = new List<FirstObject>();

            //if == null than Void has been selected from the dropdown menu
            if (editorExpression != null)
            {

                objectListTemp = editorExpression.getFirstObjects(getContainerElement(), repository);

                foreach (FirstObject actTarget in objectListTemp)
                {
                    String name = actTarget.Name;
                    int classifierId = actTarget.ClassifierId;

                    bool valid = false;
                    valid = validateFirstObject(selectedExpressionString, classifierId);
                    if (valid)
                    {
                        objectStringList.Add(name);
                        firstObjects.Add(actTarget);
                    }
                }
            }
            this.expression = computeExpression();

            //objectStringList.Sort();
            objectStringList = objectStringList.Distinct().ToList();

            return objectStringList;
        }

        private bool validateFirstObject(String selectedExpressionString, int firtObjectClassifierId)
        {
            bool valid = false;
            if (methodParameterClassifierIdFilter != -1 && methodParameterClassifierIdFilter != 0)
            {

                if (selectedExpressionString == "ObjectVariableExpression" || selectedExpressionString == "ParameterExpression")
                {

                    try
                    {
                        //is object classifier == method parameter classifier, we can skip the base class detection
                        if (firtObjectClassifierId == methodParameterClassifierIdFilter)
                        {
                            valid = true;
                        }
                        //is the object classifier contained in the method parameter classifier type hierarchy?
                        else
                        {
                            SQLElement methodClassifier = EAUtil.getClassifierElement(repository, methodParameterClassifierIdFilter);
                            if (methodClassifier != null)
                            {
                                //all targets are valid for EObject parameters
                                if (methodClassifier.Name == "EObject")
                                {
                                    valid = true;
                                }

                            }

                            if (!valid)
                            {

                                SQLElement classifierFilter = repository.GetElementByID(firtObjectClassifierId);

                                List<SQLElement> baseClasses = EAUtil.getBaseClasses(classifierFilter);
                                foreach (SQLElement baseClass in baseClasses)
                                {
                                    if (baseClass.ElementID == methodParameterClassifierIdFilter)
                                    {
                                        valid = true;
                                    }
                                }
                            }
                        }

                    }
                    catch (COMException)
                    {
                    }
                }
                //other expression is used. do not filter it
                else
                {
                    valid = true;
                }
            }
            else
            {
                valid = true;
            }
            return valid;
        }

        

        public List<string> getSecondStringList(int selectedFirstObjectIndex)
        {
            this.secondObjects = new List<SecondObject>();
            this.selectedFirstObjectIndex = selectedFirstObjectIndex;
            this.selectedSecondObjectIndex = -1;

            List<String> sourceStringList = new List<string>();

            Object targetElement = this.firstObjects[selectedFirstObjectIndex].EaObject;
            EditorExpression editorExpression = EditorExpression.createEditorExpression(selectedExpressionString);
            List<SecondObject> sourceList = editorExpression.getSecondObjects(targetElement, repository);

            foreach (SecondObject actSecondObject in sourceList)
            {
                String name = actSecondObject.Name;
                int classifierId = actSecondObject.ClassifierId;

                if (actSecondObject.EaObject is SQLMethod)
                {
                    SQLMethod method = actSecondObject.EaObject as SQLMethod;
                    if (methodParameterClassifierIdFilter != 0)
                    {
                        if (classifierId != 0)
                        {
                            SQLElement methodReturnTypeClass = repository.GetElementByID(classifierId);
                            SQLElement wantedParameterTypeClasss = repository.GetElementByID(methodParameterClassifierIdFilter);

                            foreach (SQLElement baseClass in EAUtil.getBaseClasses(methodReturnTypeClass))
                            {
                                if (baseClass.ElementID == methodParameterClassifierIdFilter)
                                {
                                    name = EAUtil.getMethodSignature(method);
                                    break;
                                }
                            }
                        }

                    }
                    else
                    {
                        name = EAUtil.getMethodSignature(method);
                    }
                }
                
                if (name != "" && !sourceStringList.Contains(name))
                {                  
                    sourceStringList.Add(name);
                    this.secondObjects.Add(actSecondObject);   
                }
            }

            this.expression = computeExpression();

            //sourceStringList.Sort();
            sourceStringList = sourceStringList.Distinct().ToList();

            return sourceStringList;
        }

        public abstract SQLElement getContainerElement();


        public abstract SDMExportWrapper.expressions.Expression getProviderExpression();



        private Expression computeExpression()
        {
            Expression expression = null;
            try
            {
                //output will be computed as the name of the stopNode
                
                //AttributeValueExpression with target(cmbTarget) and attribute(cmbSources)
                if (selectedExpressionString == "AttributeValueExpression" && secondObjects.Count > selectedSecondObjectIndex && firstObjects.Count > selectedFirstObjectIndex && selectedSecondObjectIndex != -1 && selectedFirstObjectIndex != -1)
                {
                    //get OV and Attribute
                    SQLAttribute attribute = this.secondObjects[selectedSecondObjectIndex].EaObject as SQLAttribute;
                    SQLElement targetObjectVariable = this.firstObjects[selectedFirstObjectIndex].EaObject as SQLElement;
                    AttributeValueExpression avExp = new AttributeValueExpression(repository, attribute, targetObjectVariable);
                    expression = avExp;
                }
                //MethodCallExpression with target(cmbTarget) and Method(cmbSources)
                else if (selectedExpressionString == "MethodCallExpression" && secondObjects.Count > selectedSecondObjectIndex && firstObjects.Count > selectedFirstObjectIndex && selectedSecondObjectIndex != -1 && selectedFirstObjectIndex != -1)
                {
                    SQLMethod method = this.secondObjects[selectedSecondObjectIndex].EaObject as SQLMethod;
                    String name = method.Name;
                    Object target = this.firstObjects[selectedFirstObjectIndex].EaObject;
                    MethodCallExpression methodCallExpression = EditorMethodCallExpression.generateMethodCallExpression(repository, target, method);
                    expression = methodCallExpression;
                }
                //parameterExpression
                else if (selectedExpressionString == "ParameterExpression" && firstObjects.Count > selectedFirstObjectIndex && selectedFirstObjectIndex != -1)
                {
                    SQLParameter parameter = this.firstObjects[selectedFirstObjectIndex].EaObject as SQLParameter;
                    ParameterExpression pExp = new ParameterExpression(repository, parameter);
                    expression = pExp;
                }
                //literalExpression
                else if (selectedExpressionString == "LiteralExpression")
                {
                    LiteralExpression literalExpression = new LiteralExpression("null");
                    expression = literalExpression;
                }
                //objectVariableExpression
                else if (selectedExpressionString == "ObjectVariableExpression" && firstObjects.Count > selectedFirstObjectIndex)
                {
                    SQLElement sdmObject = this.firstObjects[selectedFirstObjectIndex].EaObject as SQLElement;
                    ObjectVariableExpression ovExp = new ObjectVariableExpression(sdmObject, repository);
                    expression = ovExp;
                }
            }
            catch (Exception)
            {

            }
            return expression;
        }

        public Expression getExpression()
        {
            return this.expression;
        }



        public void setSelectedSecondString(int selectedSecondIndex)
        {
            if (secondObjects.Count > selectedSecondIndex)
            {
                this.selectedSecondObjectIndex = selectedSecondIndex;
            }
            this.expression = computeExpression();
        }

        public abstract IExpressionProvider getMainProvider();



        public void setParameterTypeFilter(int parameterId)
        {
            this.methodParameterClassifierIdFilter = parameterId;
        }

        public int methodParameterClassifierIdFilter { get; set; }



        public SecondObject getSelectedSecond()
        {
            if (secondObjects.Count > selectedSecondObjectIndex && selectedSecondObjectIndex != -1)
            {
                return secondObjects[selectedSecondObjectIndex];
            }
            return null;
        }


        public void setExpression(Expression expression)
        {
            this.expression = expression;
        }
    }
}

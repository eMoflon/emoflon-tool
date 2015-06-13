using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.SDMModeling.Util;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.patterns.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls.expressions;
using EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper;
using EAEcoreAddin.Modeling.SDMModeling.SDMExportWrapper.calls;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Modeling.SDMModeling.Gui.Util
{
    class GUIUtil
    {


        public static ExpressionControlData getExpressionStringRepresentation(IExpressionProvider provider, Expression expression, SQLRepository repository)
        {
            ExpressionControlData data = new ExpressionControlData();
            Expression providerExpression = expression;
            if (providerExpression == null)
                providerExpression = provider.getProviderExpression();

            if (providerExpression != null)
            {
                data.Expression = providerExpression.GetType().Name;
                if (data.Expression == "AttributeValueExpression")
                {
                    AttributeValueExpression attributeValueExpression = providerExpression as AttributeValueExpression;
                    data.First = attributeValueExpression.ObjectVariableName;
                    data.Second = attributeValueExpression.AttributeName;
                }
                else if (data.Expression == "MethodCallExpression")
                {
                    MethodCallExpression methodCallExpression = providerExpression as MethodCallExpression;
                    if (methodCallExpression.Target is ObjectVariableExpression)
                    {
                        ObjectVariableExpression ovExp = methodCallExpression.Target as ObjectVariableExpression;
                        data.First = ovExp.ObjectVariableName;
                    }
                    else if (methodCallExpression.Target is ParameterExpression)
                    {
                        ParameterExpression pExp = methodCallExpression.Target as ParameterExpression;
                        data.First = pExp.ParameterName;
                    }
                    data.Second = EditorMethodCallExpression.getOperationName(repository, methodCallExpression);
                    foreach (ParameterBinding parameterBinding in methodCallExpression.OwnedParameterBinding)
                    {
                        data.Parameters.Add((parameterBinding.ValueExpression.ToString()));
                    }
                }

                else if (data.Expression == "ParameterExpression")
                {
                    ParameterExpression parameterExpression = providerExpression as ParameterExpression;
                    data.First = parameterExpression.ParameterName;
                }
                else if (data.Expression == "LiteralExpression")
                {
                    LiteralExpression literalExpression = providerExpression as LiteralExpression;
                    data.Parameters.Add(literalExpression.Value);
                }
                else if (data.Expression == "ObjectVariableExpression")
                {
                    ObjectVariableExpression objectVariableExpression = providerExpression as ObjectVariableExpression;
                    data.First = objectVariableExpression.ObjectVariableName;
                }
                else if (data.Expression == "TextualExpression")
                {
                    TextualExpression textualExpression = providerExpression as TextualExpression;
                    data.Expression = "LiteralExpression";
                    data.Parameters.Add(textualExpression.ExpressionText);
                }


            }
            return data;
        }
    }
}

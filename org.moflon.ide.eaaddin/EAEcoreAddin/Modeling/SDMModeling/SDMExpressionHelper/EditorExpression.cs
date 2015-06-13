using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper
{
    public abstract class EditorExpression
    {

        public abstract List<FirstObject> getFirstObjects(SQLElement elementToSearch, SQLRepository repository);
        public abstract List<SecondObject> getSecondObjects(Object targetObject, SQLRepository repository);
        public static EditorExpression createEditorExpression(String name)
        {
            if (name == "AttributeValueExpression")
            {
                return new EditorAttributeValueExpression();
            }
            else if (name == "MethodCallExpression")
            {
                return new EditorMethodCallExpression();
            }
            else if (name == "ObjectVariableExpression")
            {
                return new EditorObjectVariableExpression();
            }
            else if (name == "LiteralExpression")
            {
                return new EditorLiteralExpression();
            }
            else if (name == "ParameterExpression")
            {
                return new EditorParameterExpression();
            }
            return null;
        }
    }
}

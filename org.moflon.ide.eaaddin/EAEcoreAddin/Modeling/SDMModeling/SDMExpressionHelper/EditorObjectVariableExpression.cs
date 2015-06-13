using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;
using System.Collections;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper
{
    class EditorObjectVariableExpression : EditorExpression
    {
        public override List<FirstObject> getFirstObjects(SQLElement elementToSearch, SQLRepository repository)
        {
            EditorAttributeValueExpression eavExp = new EditorAttributeValueExpression();
            List<FirstObject> targetObjects = eavExp.getFirstObjects(elementToSearch, repository);
            return targetObjects;
        }

        public override List<SecondObject> getSecondObjects(object targetObject, SQLRepository repository)
        {
            return new List<SecondObject>();
        }
    }
}

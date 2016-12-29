using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.SQLWrapperClasses;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper
{
    public class EditorLiteralExpression : EditorExpression
    {
        public override List<FirstObject> getFirstObjects(SQLElement elementToSearch, SQLRepository repository)
        {
            return new List<FirstObject>();
        }



        public override List<SecondObject> getSecondObjects(Object sdmObject, SQLRepository repository)
        {
            throw new NotImplementedException();
        }
    }
}

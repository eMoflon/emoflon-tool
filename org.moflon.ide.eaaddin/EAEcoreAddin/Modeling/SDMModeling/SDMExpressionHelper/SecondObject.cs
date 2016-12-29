using EAEcoreAddin.SQLWrapperClasses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper
{
    public class SecondObject
    {
        public readonly Object eaObject;

        public SecondObject(Object eaObject)
        {
            this.eaObject = eaObject;
        }

        public string Name
        {
            get
            {
                if (eaObject is SQLAttribute)
                {
                    return (eaObject as SQLAttribute).Name;
                }
                else if (eaObject is SQLMethod)
                {
                    return (eaObject as SQLMethod).Name;
                }
                return "";
            }
        }


        public int ClassifierId
        {
            get
            {
                if (eaObject is SQLMethod)
                {
                    int result;
                    int.TryParse((eaObject as SQLMethod).ClassifierID, out result);
                    return result;
                }
                return 0;
            }
        }

        public Object EaObject
        {
            get
            {
                return eaObject;
            }
        }
    }
}

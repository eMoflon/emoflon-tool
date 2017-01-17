using EAEcoreAddin.SQLWrapperClasses;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper
{
    public class FirstObject
    {

        private Object eaObject;



        public FirstObject(Object eaObject)
        {
            this.eaObject = eaObject;
        }


        public String Name
        {
            get
            {
                if (eaObject is SQLElement)
                {
                    return (eaObject as SQLElement).Name;
                }
                else if (eaObject is SQLParameter)
                {
                    return (eaObject as SQLParameter).Name;
                }
                return "";
            }
        }

        public int ClassifierId
        {
            get
            {
                if (eaObject is SQLElement)
                {
                    return (eaObject as SQLElement).ClassifierID;
                }
                else if (eaObject is SQLParameter)
                {
                    int result = 0;
                    int.TryParse((eaObject as SQLParameter).ClassifierID, out result);
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

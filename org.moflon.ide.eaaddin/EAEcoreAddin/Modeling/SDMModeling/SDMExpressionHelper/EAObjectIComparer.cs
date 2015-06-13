using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper
{
    class EAObjectIComparer : IComparer<Object>
    {
        #region IComparer Members

        public int Compare(object x, object y)
        {
            if (x is EA.Element && y is EA.Element)
            {
                return String.Compare((x as EA.Element).Name, (y as EA.Element).Name);
            }
            else if (x is EA.Element && y is EA.Parameter)
            {
                return String.Compare((x as EA.Element).Name, (y as EA.Parameter).Name);
            }
            else if (x is EA.Parameter && y is EA.Element)
            {
                return String.Compare((x as EA.Parameter).Name, (y as EA.Element).Name);
            }
            else if (x is EA.Parameter && y is EA.Element)
            {
                return String.Compare((x as EA.Parameter).Name, (y as EA.Parameter).Name);
            }
            else if (x is EA.Attribute && y is EA.Attribute)
            {
                return String.Compare((x as EA.Attribute).Name, (y as EA.Attribute).Name);
            }
            else if (x is EA.Method && y is EA.Method)
            {
                return String.Compare((x as EA.Method).Name, (y as EA.Method).Name);
            }
            else
                return 0;
        }

        #endregion
    }
}

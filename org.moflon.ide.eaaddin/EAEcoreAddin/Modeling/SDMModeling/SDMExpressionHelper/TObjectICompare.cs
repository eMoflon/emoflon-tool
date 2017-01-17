using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Util;

namespace EAEcoreAddin.Modeling.SDMModeling.SDMExpressionHelper
{
    class TObjectICompare : IComparer<String>
    {
        #region IComparer Members

        public int Compare(String x, String y)
        {
            String xName = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(x, "Name")[0];
            String yName = EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(y, "Name")[0];
            return String.Compare(xName, yName);
        }

        #endregion

        #region IComparer<string> Members


        #endregion
    }
}

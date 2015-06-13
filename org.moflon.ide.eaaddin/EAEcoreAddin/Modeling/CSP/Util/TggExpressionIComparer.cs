using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.CSP.Util
{
    class TggCspExpressionIComparer : IComparer<string>
    {
        public int Compare(string x, string y)
        {
            if (x == "AttributeValueExpression")
                return -1;
            if (y == "AttributeValueExpression")
                return 1;
            if(x == "LiteralExpression")
                return -1;
            if(y == "LiteralExpression")
                return 1;

            return 0;
        }
    }
}

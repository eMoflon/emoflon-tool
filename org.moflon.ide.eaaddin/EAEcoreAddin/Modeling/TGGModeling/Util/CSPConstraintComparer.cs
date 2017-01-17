using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EAEcoreAddin.Modeling.TGGModeling.TGGExportWrapper;
using EAEcoreAddin.Modeling.CSP.ExportWrapper;

namespace EAEcoreAddin.Modeling.TGGModeling.Util
{
    class CSPConstraintComparer : IComparer<CSPConstraint>
    {
        #region IComparer Members

        String[] frequentlyUsedConstraints = new String[] { "eq"};

        public int Compare(CSPConstraint x, CSPConstraint y)
        {

            if (x.Index < y.Index)
            {
                return -1;
            }
            else if (x.Index > y.Index)
            {
                return 1;
            }
            

            if (x.UserDefined && !y.UserDefined)
                return -1;
            else if (!x.UserDefined && y.UserDefined)
                return 1;


            //else if (x.UserDefined && y.UserDefined)
            //    return String.Compare(x.Name, y.Name);
            else if (!x.UserDefined && !y.UserDefined)
            {
                
                if (frequentlyUsedConstraints.Contains(x.Name) && frequentlyUsedConstraints.Contains(y.Name))
                    return String.Compare(x.Name, y.Name);
                else if (frequentlyUsedConstraints.Contains(x.Name))
                    return -1;
                else if (frequentlyUsedConstraints.Contains(y.Name))
                    return 1;
                
            }

            return string.Compare(x.Name, y.Name);
        }

        #endregion
    }
}

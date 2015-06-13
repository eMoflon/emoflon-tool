using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.Consistency.Rules;

namespace EAEcoreAddin.Consistency.Util
{
    public class RuleResultIComparer : IComparer<RuleResult>
    {

        #region IComparer<RuleResult> Members

        public int Compare(RuleResult x, RuleResult y)
        {
            if (x.Rule == null || y.Rule == null)
                return 0;

            int compareRuleID = String.Compare(x.RuleID, y.RuleID);

            int compareNames = String.Compare(x.NameOfObject, y.NameOfObject);

            int compareErrorOutput = String.Compare(x.ErrorOutput, y.ErrorOutput);

            
            if (x.Rule.getSortIndex() < y.Rule.getSortIndex())
            {
                return 1;
            }
            else if (x.Rule.getSortIndex() > y.Rule.getSortIndex())
            {
                return -1;
            }

            if (x.ErrorLevel != y.ErrorLevel)
            {
                if (x.ErrorLevel == RuleErrorLevel.Fatal)
                {
                    return -1;
                }
                if (y.ErrorLevel == RuleErrorLevel.Fatal)
                {
                    return 1;
                }
                if (x.ErrorLevel == RuleErrorLevel.Error)
                {
                    return -1;
                }
                if (y.ErrorLevel == RuleErrorLevel.Error)
                {
                    return 1;
                }
                if (x.ErrorLevel == RuleErrorLevel.Warning)
                {
                    return -1;
                }
                if (y.ErrorLevel == RuleErrorLevel.Warning)
                {
                    return 1;
                }
                if (x.ErrorLevel == RuleErrorLevel.Information)
                {
                    return -1;
                }
                if (y.ErrorLevel == RuleErrorLevel.Information)
                {
                    return 1;
                }
                if (x.ErrorLevel == RuleErrorLevel.Eclipse)
                {
                    return -1;
                }
                if (y.ErrorLevel == RuleErrorLevel.Eclipse)
                {
                    return 1;
                }
                return 0;
            }

            else if (x.ErrorLevel == y.ErrorLevel)
            {
                if (compareRuleID == 0)
                {
                    if (compareNames == 0)
                    {
                        return compareErrorOutput;
                    }
                    else
                    {
                        return compareNames;
                    }
                }
                else
                {
                    return compareRuleID;
                }
            }
            return 0;
        }
        #endregion
    }
}

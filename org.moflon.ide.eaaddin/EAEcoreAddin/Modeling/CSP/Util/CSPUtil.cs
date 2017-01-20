using EAEcoreAddin.Modeling.CSP.ExportWrapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace EAEcoreAddin.Modeling.CSP.Util
{
    class CSPUtil
    {
        internal static void recomputeConstraintIndizes(List<ExportWrapper.CSPConstraint> Constraints)
        {
            int i = 0;
            foreach (CSPConstraint constraint in Constraints)
            {
                constraint.Index = i++;
            }
        }


        /// <summary>
        /// default built-in constraints are defined here
        /// </summary>
        public static void addDefaultConstraints(List<CSPConstraint> constraints)
        {
            CSPConstraint concatConstraint = new CSPConstraint("concat", new String[] { "BBBB", "BBBF", "BBFB", "BFFB", "BFBB" }, new String[] { "EString", "EString", "EString", "EString" });
            concatConstraint.setConstraintInformation("Semantics: a separator b = c");
            concatConstraint.SignatureInformation.Add("separator");
            concatConstraint.SignatureInformation.Add("a");
            concatConstraint.SignatureInformation.Add("b");
            concatConstraint.SignatureInformation.Add("c");
            concatConstraint.modelgenAdornments.AddRange(new String[] { "BBBB", "BBBF", "BBFB", "BFFB", "BFBB", "BFFF", "BFBF", "BBFF" });


            CSPConstraint addPrefixConstraint = new CSPConstraint("addPrefix", new String[] { "BBB", "BBF", "BFB", "FBB" }, new String[] { "EString", "EString", "EString" });
            addPrefixConstraint.setConstraintInformation("Semantics: prefix a = b");
            addPrefixConstraint.SignatureInformation.Add("prefix");
            addPrefixConstraint.SignatureInformation.Add("a");
            addPrefixConstraint.SignatureInformation.Add("b");
            addPrefixConstraint.modelgenAdornments.AddRange(new String[] { "BBB", "BBF", "BFB", "FBB", "BFF","FBF" });

            CSPConstraint addSuffixConstraint = new CSPConstraint("addSuffix", new String[] { "BBB", "BBF", "BFB", "FBB" }, new String[] { "EString", "EString", "EString" });
            addSuffixConstraint.setConstraintInformation("Semantics: a suffix = b");
            addSuffixConstraint.SignatureInformation.Add("a");
            addSuffixConstraint.SignatureInformation.Add("suffix");
            addSuffixConstraint.SignatureInformation.Add("b");
            addSuffixConstraint.modelgenAdornments.AddRange(new String[] { "BBB","BBF","BFB","FBB","BFF","FFF","FBF" });

            CSPConstraint multiplyConstraint = new CSPConstraint("multiply", new String[] { "BBB", "BBF", "BFB", "FBB" }, new String[] { "Number", "Number", "Number" });
            multiplyConstraint.setConstraintInformation("Semantics: a * b = c");
            multiplyConstraint.SignatureInformation.Add("a");
            multiplyConstraint.SignatureInformation.Add("b");
            multiplyConstraint.SignatureInformation.Add("c");
            multiplyConstraint.modelgenAdornments.AddRange(new String[] { "BBB", "BBF", "BFB", "FBB" });

            CSPConstraint divideConstraint = new CSPConstraint("divide", new String[] { "BBB", "BBF", "BFB", "FBB" }, new String[] { "Number", "Number", "Number" });
            divideConstraint.setConstraintInformation("Semantics: a / b = c");
            divideConstraint.SignatureInformation.Add("a");
            divideConstraint.SignatureInformation.Add("b");
            divideConstraint.SignatureInformation.Add("c");
            divideConstraint.modelgenAdornments.AddRange(new String[] { "BBB", "BBF", "BFB", "FBB" });

            CSPConstraint addConstraint = new CSPConstraint("add", new String[] { "BBB", "BBF", "BFB", "FBB" }, new String[] { "Number", "Number", "Number" });
            addConstraint.setConstraintInformation("Semantics: a + b = c");
            addConstraint.SignatureInformation.Add("a");
            addConstraint.SignatureInformation.Add("b");
            addConstraint.SignatureInformation.Add("c");
            addConstraint.modelgenAdornments.AddRange(new String[] { "BBB", "BBF", "BFB", "FBB", "FFB", "FBF", "BFF" });

            CSPConstraint subConstraint = new CSPConstraint("sub", new String[] { "BBB", "BBF", "BFB", "FBB" }, new String[] { "Number", "Number", "Number" });
            subConstraint.setConstraintInformation("Semantics: a - b = c");
            subConstraint.SignatureInformation.Add("a");
            subConstraint.SignatureInformation.Add("b");
            subConstraint.SignatureInformation.Add("c");
            subConstraint.modelgenAdornments.AddRange(new String[] { "BBB", "BBF", "BFB", "FBB", "FFB", "BFF", "FBF", "FFF" });

            CSPConstraint maxConstraint = new CSPConstraint("max", new String[] { "BBB", "BBF", "BFB", "FBB" }, new String[] { "Number", "Number", "Number" });
            maxConstraint.setConstraintInformation("Semantics: max(a,b) = c");
            maxConstraint.SignatureInformation.Add("a");
            maxConstraint.SignatureInformation.Add("b");
            maxConstraint.SignatureInformation.Add("c");
            maxConstraint.modelgenAdornments.AddRange(new String[] { "BBB", "BBF", "BFB", "FBB" });

            CSPConstraint stringToDoubleConstraint = new CSPConstraint("stringToDouble", new String[] { "BB", "BF", "FB" }, new String[] { "EString", "EDouble" });
            stringToDoubleConstraint.setConstraintInformation("Semantics: string = number.toString()");
            stringToDoubleConstraint.SignatureInformation.Add("string");
            stringToDoubleConstraint.SignatureInformation.Add("double");
            stringToDoubleConstraint.modelgenAdornments.AddRange(new String[] { "BB", "BF", "FB", "FF" });

            CSPConstraint stringToIntConstraint = new CSPConstraint("stringToInt", new String[] { "BB", "BF", "FB" }, new String[] { "EString", "EInt" });
            stringToIntConstraint.setConstraintInformation("Semantics: string = number.toString()");
            stringToIntConstraint.SignatureInformation.Add("string");
            stringToIntConstraint.SignatureInformation.Add("int");
            stringToIntConstraint.modelgenAdornments.AddRange(new String[] { "BB", "BF", "FB", "FF" });

            CSPConstraint smallerOrEqualConstraint = new CSPConstraint("smallerOrEqual", new String[] { "BB", "BF", "FB" }, new String[] { "Number", "Number" });
            smallerOrEqualConstraint.setConstraintInformation("Semantics: a <= b");
            smallerOrEqualConstraint.SignatureInformation.Add("a");
            smallerOrEqualConstraint.SignatureInformation.Add("b");
            smallerOrEqualConstraint.modelgenAdornments.AddRange(new String[] { "BB", "BF", "FB", "FF" });

            CSPConstraint eqConstraint = new CSPConstraint("eq", new String[] { "BB", "BF", "FB" }, new String[] { "", "" });
            eqConstraint.setConstraintInformation("Semantics: a = b");
            eqConstraint.SignatureInformation.Add("a");
            eqConstraint.SignatureInformation.Add("b");
            eqConstraint.modelgenAdornments.AddRange(new String[] { "BB", "BF", "FB", "FF" });

            CSPConstraint setDefaultNumberConstraint = new CSPConstraint("setDefaultNumber", new String[] { "BB", "FB" }, new String[] { "Number", "Number" });
            setDefaultNumberConstraint.setConstraintInformation("Semantics: set number = default only if number is free");
            setDefaultNumberConstraint.SignatureInformation.Add("number");
            setDefaultNumberConstraint.SignatureInformation.Add("default");
            setDefaultNumberConstraint.modelgenAdornments.AddRange(new String[] { "BB", "FB", "FF" });

            CSPConstraint setDefaultStringConstraint = new CSPConstraint("setDefaultString", new String[] { "BB", "FB" }, new String[] { "EString", "EString" });
            setDefaultStringConstraint.setConstraintInformation("Semantics: set string = default only if string is free");
            setDefaultStringConstraint.SignatureInformation.Add("string");
            setDefaultStringConstraint.SignatureInformation.Add("default");
            setDefaultStringConstraint.modelgenAdornments.AddRange(new String[] { "BB", "FB", "FF" });

            constraints.Add(eqConstraint);
            constraints.Add(addPrefixConstraint);
            constraints.Add(addSuffixConstraint);
            constraints.Add(concatConstraint);
            constraints.Add(setDefaultStringConstraint);
            constraints.Add(setDefaultNumberConstraint);
            constraints.Add(stringToDoubleConstraint);
            constraints.Add(stringToIntConstraint);
            constraints.Add(multiplyConstraint);
            constraints.Add(divideConstraint);
            constraints.Add(addConstraint);
            constraints.Add(subConstraint);
            constraints.Add(maxConstraint);
            constraints.Add(smallerOrEqualConstraint);
            
            CSPUtil.recomputeConstraintIndizes(constraints);

        }
    }
}

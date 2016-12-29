using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace EAEcoreAddin.Modeling.ECOREModeling.Util
{
    public class EcoreUtil
    {
        public static readonly String ARBITRARY_MANY_MULTIPLICITY_PLACEHOLDER = "-1";

        public static readonly String[] ecoreEClasses = {"EAnnotation", "EAttribute", "EClass", 
                                                          "EClassifier", "EcoreFactory", "EDataType",
                                                          "EEnum", "EEnumLiteral", "EFactory",
                                                          "EGenericType", "EModelElement", "ENamedElement",
                                                          "EObject", "EOperation", "EPackage",
                                                          "EParameter", "EReference", "EStructuralFeature",
                                                          "ETypedElement", "ETypeParameter", "EValidator"};

        public static readonly String[] ecoreEDataTypes = {  "EEnumerator", "EBigDecimal","EBigInteger", "EBoolean", "EBooleanObject",
                                                             "EByte", "EByteArray", "EByteObject", "EChar", "ECharacterObject",
                                                             "EDate", "EDiagnosticChain", "EDouble", "EDoubleObject",
                                                             "EFeatureMap", "EFeatureMapEntry", "EFloat", "EFloatObject", 
                                                             "EInt", "EIntegerObject", "EJavaClass", "EJavaObject", "ELong", "ELongObject", 
                                                             "EResource", "EResourceSet", "EShort", "EShortObject", "EString", 
                                                             "ETreeIterator"  };

        /**
         * Calculates integral boundaries from the given string description
         * 
         * For example: 
         * "1..2" -> lower="1", upper="2"
         * "0..*" -> lower="0", upper="-1"=ARBITRARY_MANY_MULTIPLICITY_PLACEHOLDER
         * "1"    -> lower="1", upper="1"
         */
        public static void computeLowerUpperBound(String multiplicity, ref String lowerBound, ref String upperBound)
        {
            String[] multiplicitySplitted = Regex.Split(multiplicity, "\\.\\.");
            //n..m is defined
            if (multiplicitySplitted.Length > 1)
            {
                if (multiplicitySplitted[0] == "*")
                {
                    lowerBound = ARBITRARY_MANY_MULTIPLICITY_PLACEHOLDER;
                }
                else if (multiplicitySplitted[0] == "")
                {
                    lowerBound = "1";
                }
                else
                {
                    lowerBound = multiplicitySplitted[0];
                }

                if (multiplicitySplitted[1] == "*")
                {
                    upperBound = ARBITRARY_MANY_MULTIPLICITY_PLACEHOLDER;
                }
                else if (multiplicitySplitted[1] == "")
                {
                    upperBound = "1";
                }
                else
                {
                    upperBound = multiplicitySplitted[1];
                }
            }
            //only n is defined
            else if (multiplicitySplitted.Length == 1)
            {
                if (multiplicitySplitted[0] == "*")
                {
                    lowerBound = "0";
                    upperBound = ARBITRARY_MANY_MULTIPLICITY_PLACEHOLDER;
                    
                }
                else if (multiplicitySplitted[0] == "")
                {
                    upperBound = "1";
                }
                else
                {
                    upperBound = multiplicitySplitted[0];
                    lowerBound = multiplicitySplitted[0];
                }
            }

        }

    }
}

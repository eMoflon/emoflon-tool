using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using EAEcoreAddin.SQLWrapperClasses;
using EAEcoreAddin.Modeling.SDMModeling;


namespace EAEcoreAddin.Persistency.Util
{
    class PersistencyUtil
    {
        #region Ecore spesific addressing of model elements

        

        public static readonly String[] ecoreEClasses = {"EAnnotation", "EAttribute", "EClass", 
                                                          "EClassifier", "EcoreFactory", "EDataType",
                                                          "EEnum", "EEnumLiteral", "EFactory",
                                                          "EGenericType", "EModelElement", "ENamedElement",
                                                          "EObject", "EOperation", "EPackage",
                                                          "EParameter", "EReference", "EStructuralFeature",
                                                          "ETypedElement", "ETypeParameter", "EValidator"};

        public static readonly String[] ecoreEDataTypes = {  "EEnumerator", "EBigDecimal","EBigInteger", "EBoolean", "EBooleanObject",
                                                             "EByte", "EByteArray", "EByteObject", "EChar", "ECharacterObject",
                                                             "EDate", "EDiagnosticChain", "EDouble", "EDoubleObject", "EElist<E>",
                                                             "EFeatureMap", "EFeatureMapEntry", "EFloat", "EFloatObject", 
                                                             "EInt", "EIntegerObject", "EJavaClass", "EJavaObject", "ELong", "ELongObject", 
                                                             "EMap<K,V>", "EResource", "EResourceSet", "EShort", "EShortObject", "EString", 
                                                             "ETreeIterator"  };

        public static String getReferenceToBuiltInEcoreType(String ecoreType)
        {
            if (ecoreEClasses.Contains(ecoreType))
                return "ecore:EClass http://www.eclipse.org/emf/2002/Ecore#//" + ecoreType;
            else if (ecoreEDataTypes.Contains(ecoreType))
                return "ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//" + ecoreType;

            return "";
        }

        public static String getReferenceToBuiltInEcoreTypeWithoutPrefix(String ecoreType)
        {
            if (ecoreEClasses.Contains(ecoreType))
                return "http://www.eclipse.org/emf/2002/Ecore#//" + ecoreType;
            else if (ecoreEDataTypes.Contains(ecoreType))
                return "http://www.eclipse.org/emf/2002/Ecore#//" + ecoreType;

            return "";
        }

                
        #endregion

        public static String computePackageUri(SQLPackage pkg, SQLRepository rep)
        {
            String val = pkg.Name;
            SQLPackage currentPkg = pkg;
            while (!currentPkg.IsModel)
            {
                currentPkg = rep.GetPackageByID(currentPkg.ParentID);
                if(!currentPkg.IsModel)
                    val = currentPkg.Name + "/" + val;
            }
            return val;
        }


        public static Boolean updateEapFileNecessary(EA.Repository Repository)
        {
            UpdateEap updateEapForm = new UpdateEap();
            Boolean updateNecessary = true;
            Boolean updateWished = true;

            String sqlresult = Repository.SQLQuery("select Property from t_objectproperties where Property = 'Moflon::ExportTree'");

            if (EAEcoreAddin.Util.EAUtil.getXMLNodeContentFromSQLQueryString(sqlresult, "Row").Count > 10)
            {
                updateNecessary = false;
            }

            if (updateNecessary)
            {
                updateEapForm.ShowDialog();
                updateWished = updateEapForm.getOkPressed();
            }
            return updateWished;
        }

    }
}

package org.moflon.sdm.compiler.democles.derivedfeatures;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenOperation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;

import SDMLanguage.activities.Activity;
import SDMLanguage.activities.impl.MoflonEOperationImpl;

public class DerivedFeatureExtractor {
    
    /**
     * Extracts the {@link Activity} from a calculation method that corresponds
     * to the {@link GenFeature} that represents a derived attribute.
     * @param genFeature
     *       {@link GenFeature} that represents a derived attribute.
     * @return
     *       The corresponding {@link Activity}.
     */
    public static Activity getActivity(final GenFeature genFeature, String methodName) {
        Activity activity = null;
        
        String returnType = DerivedFeatureExtractor.getAttributeType(genFeature);
        EOperation eOperation = DerivedFeatureExtractor.getEOperation(genFeature, methodName, returnType, true);
        
        if (eOperation != null && eOperation instanceof MoflonEOperationImpl) {
            MoflonEOperationImpl eOperationImpl = (MoflonEOperationImpl) eOperation;
            activity = eOperationImpl.getActivity();
        }

        return activity;
    }
    
    /**
     * Extracts the {@link EOperation} with a specific
     * name and return type from a {@link GenFeature}.
     * @param genFeature
     *      The GenFeature that contains the EOperation to extract. 
     * @param name
     *      The name of the EOperation to extract.
     * @param returnType
     *      The return type of the EOperation to extract.
     * @param allowDerivedType
     *      Set true, if a derived type is allowed as return type.
     * @return
     *      The matching EOperation or null of there is no matching method. 
     */
    public static EOperation getEOperation(final GenFeature genFeature, String name, String returnType, boolean allowDerivedType) {
        EOperation eOperation = null;
        
        if (genFeature.eContainer() instanceof GenClass) {
            GenClass genClass = (GenClass) genFeature.eContainer();
            for (GenOperation genOperation : genClass.getGenOperations()) {
                EOperation currentEOperation = genOperation.getEcoreOperation();

                if (currentEOperation.getName().equals(name)) {
                    EClassifier operationReturnType = currentEOperation.getEType();
                    
                    if (operationReturnType.getName().equals(returnType)) {
                        // return type matches exactly
                        eOperation = currentEOperation;
                        break;
                    } else if (allowDerivedType && operationReturnType instanceof EClass) {
                        EClass opRetTypeClass = (EClass)operationReturnType;
                        
                        for (EClass superType : opRetTypeClass.getESuperTypes()) {
                            if (superType.getName().equals(returnType)) {
                                // super type of return type matches
                                eOperation = currentEOperation;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return eOperation;
    }
    
    /**
     * Identifies the type of a derived attribute represented by a {@link GenFeature}.
     * @param genFeature A derived attribute representation.
     * @return The return type of the derived attribute. 
     */
    public static String getAttributeType(final GenFeature genFeature) {
        String returnType = null;
        if (genFeature.getTypeGenDataType() != null) {
            returnType = genFeature.getTypeGenDataType().getName();
        } else {
            returnType = genFeature.getImportedType(genFeature.getGenClass());
        }
        return returnType;
    }
}

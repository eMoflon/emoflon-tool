package org.moflon.sdm.compiler.democles.derivedfeatures;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.democles.specification.emf.Constraint;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Attribute;
import org.gervarro.democles.specification.emf.constraint.emf.emf.Reference;
import org.moflon.compiler.sdm.democles.DemoclesMethodBodyHandler;
import org.moflon.compiler.sdm.democles.eclipse.AdapterResource;
import org.moflon.sdm.constraints.democles.AttributeValueConstraint;
import org.moflon.sdm.runtime.democles.Action;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.RegularPatternInvocation;
import org.moflon.sdm.runtime.democles.ReturnStatement;
import org.moflon.sdm.runtime.democles.Scope;
import org.moflon.sdm.runtime.democles.SingleResultPatternInvocation;

public class DerivedFeatureSdmAnalyzer
{
   /**
    * Creates a set of variables on which the derived attribute depends.
    * 
    * @param genFeature
    *           The derived attribute that should be analyzed.
    * @return A set of variables on which the derived attribute depends.
    */
    public static Set<EStructuralFeature> analyzeSDM(final GenFeature genFeature, String calcMethodName) {
        Set<EStructuralFeature> dependentFeatures = new HashSet<EStructuralFeature>();
        String returnType = DerivedFeatureExtractor.getAttributeType(genFeature);
        EOperation eOperation = DerivedFeatureExtractor.getEOperation(genFeature, calcMethodName, returnType, true);

        AdapterResource cfResource = (AdapterResource) EcoreUtil.getRegisteredAdapter(eOperation,
                DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION);

        if (cfResource != null && cfResource.getContents().size() > 0
                && cfResource.getContents().get(0) instanceof Scope) {

            final Scope rootScope = (Scope) cfResource.getContents().get(0);

            for (EObject content : rootScope.eContents()) {
                if (content instanceof ReturnStatement) {
                    ReturnStatement returnStatement = (ReturnStatement) content;
                    analyzeNode(dependentFeatures, returnStatement);
                } else if (content instanceof CFNode) {
                    CFNode cfNode = (CFNode) content;
                    analyzeNode(dependentFeatures, cfNode);
                }
            }
        }

        return dependentFeatures;
    }

   /**
    * Analyzes a {@link CFNode} and adds dependent features to the list.  
    * @param dependentFeatures
    *           The list of dependent features.
    * @param returnStatement
    *           The {@link CFNode} to analyze.
    */
    private static void analyzeNode(Set<EStructuralFeature> dependentFeatures, CFNode cfNode) {
        for (Action action : cfNode.getActions()) {

            if (action instanceof RegularPatternInvocation) {
                RegularPatternInvocation regPatInv = (RegularPatternInvocation) action;
                Pattern pattern = regPatInv.getPattern();
                
                if (pattern != null) {
                    for (PatternBody patternBody : pattern.getBodies()) {

                        for (Constraint constraint : patternBody.getConstraints()) {

                            if (constraint instanceof Attribute) {
                                Attribute attr = (Attribute) constraint;
                                dependentFeatures.add(attr.getEModelElement());
                            }
                            
                            // finds dependent features in CSPs
                            // TODO find also the corresponding StructuralFeatures 
                            if (constraint instanceof AttributeValueConstraint) {
                        // AttributeValueConstraint attrValConstr = (AttributeValueConstraint) constraint;
                        // for (ConstraintParameter constrParam : attrValConstr.getParameters()) {
                        // }
                            }
                            
                            // finds references to dependent features
                            if (constraint instanceof Reference) {
                                Reference ref = (Reference) constraint;
                                addSdmReference(dependentFeatures, ref);
                            }
                        }
                    }
                }
            }
        }
    }

   /**
    * Analyzes a {@link ReturnStatement} and adds dependent features to the list.  
    * @param dependentFeatures
    *           The list of dependent features.
    * @param returnStatement
    *           The {@link ReturnStatement} to analyze.
    */
    private static void analyzeNode(Set<EStructuralFeature> dependentFeatures, ReturnStatement returnStatement) {
        for (Action action : returnStatement.getActions()) {
            
            if (action instanceof SingleResultPatternInvocation) {
                SingleResultPatternInvocation inv = (SingleResultPatternInvocation) action;
                
                for (CFVariable var : inv.getConstructedVariables()) {
                    if (var instanceof CFVariable) {
                        CFVariable cfVar = (CFVariable) var;
                        
                        if (cfVar.getConstructor() instanceof SingleResultPatternInvocation) {
                            SingleResultPatternInvocation constVar = (SingleResultPatternInvocation) cfVar.getConstructor();
                            Pattern pattern = constVar.getPattern();
                            
                            if (pattern != null) {
                                for (PatternBody patternBody : pattern.getBodies()) {
                                    for (Constraint constraint : patternBody.getConstraints()) {
                                        if (constraint instanceof Attribute) {
                                            Attribute attribute = (Attribute) constraint;
                                            dependentFeatures.add(attribute.getEModelElement());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

   /**
    * Adds both ends of a {@link Reference} to the list of dependent features. 
    * @param dependentFeatures
    *   The list of dependent features.
    * @param ref
    *   The reference to add.
    */
    private static void addSdmReference(Set<EStructuralFeature> dependentFeatures, Reference ref) {
        if (ref != null) {

            // TODO check which end of the reference is actually the dependency and which one is not
            
            EReference modelRef = ref.getEModelElement();
            EReference modelRefOpp = modelRef.getEOpposite();

            if (!modelRef.getName().equals("this")) {
                dependentFeatures.add(modelRef);
            }

            if (!modelRefOpp.getName().equals("this")) {
                dependentFeatures.add(modelRefOpp);
            }
        }
    }
}

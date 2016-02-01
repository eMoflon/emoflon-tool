package org.moflon.eclipse.genmodel;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;

import SDMLanguage.activities.Activity;
import SDMLanguage.activities.ActivityNode;
import SDMLanguage.activities.StopNode;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.calls.callExpressions.MethodCallExpression;
import SDMLanguage.calls.callExpressions.ParameterExpression;
import SDMLanguage.expressions.Expression;
import SDMLanguage.patterns.StoryPattern;
import SDMLanguage.patterns.AttributeConstraints.AssignmentConstraint;
import SDMLanguage.patterns.AttributeConstraints.AttributeConstraintExpression;
import SDMLanguage.patterns.AttributeConstraints.AttributeConstraintVariable;
import SDMLanguage.patterns.AttributeConstraints.AttributeLookupConstraint;
import SDMLanguage.patterns.AttributeConstraints.AttributeValueConstraint;
import SDMLanguage.patterns.AttributeConstraints.CspConstraint;
import SDMLanguage.patterns.AttributeConstraints.PrimitiveVariable;
import SDMLanguage.patterns.patternExpressions.AttributeValueExpression;
import SDMLanguage.patterns.patternExpressions.ObjectVariableExpression;

public class DerivedFeatureSdmAnalyzer {
    
    /**
     * Creates a set of variables on which the derived attribute depends.
     * @param genFeature
     *      The derived attribute that should be analyzed.
     * @return 
     *      A set of variables on which the derived attribute depends.
     */
    public static Set<EStructuralFeature> analyzeSDM(final GenFeature genFeature, String calcMethodName) {
        Set<EStructuralFeature> dependentVariables = new HashSet<EStructuralFeature>();
        Activity activity = DerivedFeatureExtractor.getActivity(genFeature, calcMethodName);

        if (activity != null) {
            EList<ActivityNode> nodes = activity.getOwnedActivityNode();

            for (ActivityNode node : nodes) {
                if (node instanceof StopNode) {
                    Set<EStructuralFeature> stopNodeVariables = analyzeStopNode((StopNode)node);
                    dependentVariables.addAll(stopNodeVariables);
                } else if (node instanceof StoryNode) {
                    Set<EStructuralFeature> storyNodeVariables = analyzeStoryNode((StoryNode)node);
                    dependentVariables.addAll(storyNodeVariables);
                }
            }
        }
        
        return dependentVariables;
    }
    
    /**
     * Creates a set of variables contained in a {@link StoryNode}}
     * on which the derived attribute depends.
     * @param node
     *      The story node that should be analyzed.
     * @return
     *      A set of variables on which the derived attribute depends.
     */
    private static Set<EStructuralFeature> analyzeStoryNode(StoryNode node) {
        Set<EStructuralFeature> dependentVariables = new HashSet<EStructuralFeature>();
        StoryPattern storyPattern = node.getStoryPattern();          

        if (storyPattern != null) {
            for (AttributeConstraintVariable variable : storyPattern.getVariables()) {
                if (variable instanceof AttributeConstraintExpression) {
                    //AttributeConstraintExpression attributeConstraintExpression = (AttributeConstraintExpression)variable;
                } else if (variable instanceof CspConstraint) {
                    //CspConstraint cspConstraint = (CspConstraint)variable;
                } else if (variable instanceof PrimitiveVariable) {
                    findDependencies((PrimitiveVariable)variable);
                }
            }
        }
        
        /*
        for (ObjectVariable variable : storyPattern.getObjectVariable()) {
            if (!variable.getName().equals("this")) {
                //dependentVariables.add(variable.getName());
                dependentVariables.add(null);
            }
        }
        */
        
        return dependentVariables;
    }
    
    /**
     * Creates a set of derived features contained in an {@link PrimitiveVariable}}
     * @param primitiveVariable
     *      The variable that should be analyzed.
     * @return
     *      A set of derived features that occurs in the PrimitiveVariable.
     */
    private static Set<EStructuralFeature> findDependencies(PrimitiveVariable primitiveVariable) {
        Set<EStructuralFeature> dependentVariables = new HashSet<EStructuralFeature>();
        
        for (AttributeValueConstraint attributeValueConstraint : primitiveVariable.getAttributeValueConstraints()) {
            if (attributeValueConstraint != null) {
                if (attributeValueConstraint instanceof AssignmentConstraint) {
                    AssignmentConstraint assignmentConstraint = (AssignmentConstraint)attributeValueConstraint;
                    //dependentVariables.add(assignmentConstraint.getObjectVariable().getName());
                    dependentVariables.add(assignmentConstraint.getType());
                } else if (attributeValueConstraint instanceof AttributeLookupConstraint) {
                    AttributeLookupConstraint assignmentLookupConstraint = (AttributeLookupConstraint)attributeValueConstraint;
                    if (!assignmentLookupConstraint.getObjectVariable().getName().equals("this")) {
                        //dependentVariables.add(assignmentLookupConstraint.getObjectVariable());
                        dependentVariables.add(null);
                    } else {
                        if (assignmentLookupConstraint.getType() instanceof EAttribute) {
                            dependentVariables.add(assignmentLookupConstraint.getType());
                        }
                    }
                }
            }
        }
        
        return dependentVariables;
    }
    
    /**
     * Creates a set of variables contained in a {@link StopNode}}
     * on which the derived attribute depends.
     * @param node
     *      The stop node that should be analyzed.
     * @return
     *      A set of variables on which the derived attribute depends.
     */
    private static Set<EStructuralFeature> analyzeStopNode(StopNode node) {
        return analyzeExpression(node.getReturnValue());
    }
    
    /**
     * Creates a set of variables contained in an {@link Expression}}
     * on which the derived attribute depends.
     * @param expression
     *      The expression that should be analyzed.
     * @return
     *      A set of variables on which the expression depends.
     */
    private static Set<EStructuralFeature> analyzeExpression(Expression expression) {
        Set<EStructuralFeature> dependentVariables = new HashSet<EStructuralFeature>();
        
        if (expression instanceof AttributeValueExpression) {
            //AttributeValueExpression attributeValueExpression = (AttributeValueExpression)expression;
        } else if (expression instanceof ObjectVariableExpression) {
            //ObjectVariableExpression objectVariableExpression = (ObjectVariableExpression)expression;
        } else if (expression instanceof MethodCallExpression) {
            //MethodCallExpression methodCallExpression = (MethodCallExpression)expression;
        } else if (expression instanceof ParameterExpression) {
            //ParameterExpression parameterExpression = (ParameterExpression)expression;
        }
        
        return dependentVariables;
    }
}

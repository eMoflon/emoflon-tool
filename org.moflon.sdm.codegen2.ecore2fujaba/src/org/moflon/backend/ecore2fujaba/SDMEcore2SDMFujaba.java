package org.moflon.backend.ecore2fujaba;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.moflon.compiler.sdm.MethodVisitor;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.properties.MoflonPropertiesContainerHelper;
import org.moflon.util.eMoflonSDMUtil;

import MoflonPropertyContainer.MoflonPropertiesContainer;
import SDMLanguage.activities.ActivitiesPackage;
import SDMLanguage.activities.Activity;
import SDMLanguage.activities.ActivityEdge;
import SDMLanguage.activities.ActivityNode;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StartNode;
import SDMLanguage.activities.StatementNode;
import SDMLanguage.activities.StopNode;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.calls.callExpressions.MethodCallExpression;
import SDMLanguage.calls.callExpressions.ParameterExpression;
import SDMLanguage.expressions.ComparisonExpression;
import SDMLanguage.expressions.Expression;
import SDMLanguage.expressions.LiteralExpression;
import SDMLanguage.expressions.TextualExpression;
import SDMLanguage.patterns.AttributeAssignment;
import SDMLanguage.patterns.BindingState;
import SDMLanguage.patterns.Constraint;
import SDMLanguage.patterns.LinkVariable;
import SDMLanguage.patterns.ObjectVariable;
import SDMLanguage.patterns.StoryPattern;
import SDMLanguage.patterns.patternExpressions.AttributeValueExpression;
import SDMLanguage.patterns.patternExpressions.ObjectVariableExpression;
import SDMLanguage.sdmUtil.SDMPrecompiler;
import SDMLanguage.sdmUtil.SdmUtilFactory;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivityDiagram;
import de.uni_paderborn.fujaba.uml.behavior.UMLAttrExprPair;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.behavior.UMLStatementActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStopActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryPattern;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransition;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransitionGuard;
import de.uni_paderborn.fujaba.uml.structure.UMLAssoc;
import de.uni_paderborn.fujaba.uml.structure.UMLClass;
import de.uni_paderborn.fujaba.uml.structure.UMLMethod;

/**
 * Responsible for converting SDMs, that are conform to the current SDM metamodel in Ecore, to SDMs that are conform to
 * the old SDM metamodel for Fujaba. This is necessary for SDM code generation using CodeGen2.
 * 
 * The transformation process is quite simple and consists of two clear steps: (1) all elements (nodes) are transformed
 * to their corresponding counterparts and (2) all relationships between elements are created.
 * 
 * @author anjorin
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */
public class SDMEcore2SDMFujaba
{
   private static final String FUJABA_SUCCESS = "fujaba__Success";

   /*
    * Constants used for convention how SDMs are embedded as annotations in eOperations
    */

   public static final String SDM_ANNOTATION_SOURCE = "SDM";

   public static final String SDM_KEY = "XMI";

   public static final String GENMODEL_ANNOTATION_SOURCE = "http://www.eclipse.org/emf/2002/GenModel";

   public static final String GENMODEL_KEY = "body";

   // :~

   private Resource ecoreResource;

   private FProject project;

   private MoflonPropertiesContainer properties;

   private HashMap<String, String> sdms;

   /* Maps used for transformation */

   private HashMap<ActivityNode, UMLActivity> activityNodeToUMLActivity;

   private HashMap<ActivityEdge, UMLTransition> activityEdgeToUMLTransition;

   private HashMap<ObjectVariable, UMLObject> objectVariableToUmlObject;

   private HashMap<LinkVariable, UMLLink> abstractLinkVariableToUmlLink;

   // :~

   private static final Logger logger = Logger.getLogger(SDMEcore2SDMFujaba.class);

   public SDMEcore2SDMFujaba(final Resource ecoreResource, final FProject project, final MoflonPropertiesContainer properties)
   {
      /* Set data members */
      this.ecoreResource = ecoreResource;
      this.project = project;
      sdms = new HashMap<String, String>();
      this.properties = properties;

      // Initialize Maps
      activityEdgeToUMLTransition = new HashMap<ActivityEdge, UMLTransition>();
      activityNodeToUMLActivity = new HashMap<ActivityNode, UMLActivity>();
      objectVariableToUmlObject = new HashMap<ObjectVariable, UMLObject>();
      abstractLinkVariableToUmlLink = new HashMap<LinkVariable, UMLLink>();
   }

   private Activity loadActivityFromEAnnoation(final EOperation eOperation)
   {

      EAnnotation annotation = eOperation.getEAnnotation(SDM_ANNOTATION_SOURCE);

      if (annotation != null)
      {
         logger.debug("Got annotation: " + annotation);

         String sdmXMISpec = annotation.getDetails().get(SDM_KEY);
         if (sdmXMISpec == null || sdmXMISpec.equals(""))
            return null;

         String fileName = MethodVisitor.signatureFor(eOperation) + ".xmi";
         sdms.put(fileName, sdmXMISpec);

         // Use same ResourceSet as for ecore file to create Resource
         ResourceSet resourceSet = ecoreResource.getResourceSet();
         Resource resource = resourceSet.createResource(URI.createURI(fileName));

         logger.debug("Created resource using resourceSet with mappings: " + resourceSet.getURIConverter().getURIMap().keySet() + "->"
               + resourceSet.getURIConverter().getURIMap().values());

         // Load SDM model
         InputStream in = new URIConverter.ReadableInputStream(sdmXMISpec);
         try
         {
            resource.load(in, null);
            in.close();
         } catch (IOException e)
         {
            e.printStackTrace();
         }

         logger.debug("Loaded sdm successfully");
         logger.warn("USE OF DEPRECATED METHOD: SDM for eOperation " + eOperation.getName() + " in "
               + eOperation.getEContainingClass().getEPackage().getNsURI()
               + " was loaded from EAnnotation. It is recommended to use MoflonEOperation, as container for the SDM Activity");
         return (Activity) resource.getContents().get(0);
      } else
      {

         return null;
      }

   }

   private Activity loadActivityMoflonEOperation(final EOperation eOperation)
   {

      if (ActivitiesPackage.eINSTANCE.getMoflonEOperation().isInstance(eOperation))
      {
         MoflonEOperation moflonEOperation = (MoflonEOperation) eOperation;
         return moflonEOperation.getActivity();
      }
      return null;
   }

   /**
    * Transforms the SDM annotated to the eOperation to an SDM contained in the UMLMethod. Used by {@link Ecore2Fujaba}
    * in the Ecore->Fujaba transformation process.
    * 
    * @param method
    *           UMLMethod where the new (Fujaba) SDM should be created.
    * @param eOperation
    *           EOperation containing (Ecore) SDM for transformation.
    * @param eClassifierToUMLClassMap
    *           Map from previous transformation of class diagram containing correspondences.
    * @param eReferenceToUMLAssocMap
    *           Map from previous transformation of class diagram containing correspondences.
    * @throws CoreException
    */
   void processSDM(final UMLMethod method, final EOperation eOperation, final HashMap<EClassifier, UMLClass> eClassifierToUMLClassMap,
         final HashMap<EReference, UMLAssoc> eReferenceToUMLAssocMap, final HashMap<String, UMLAssoc> uniqueNameToUMLAssoc) throws CoreException
   {
      Activity activity;
      // try to fetch activity if eOperation is MoflonEoperation
      activity = loadActivityMoflonEOperation(eOperation);

      // Fallback to deprecated method: try to fetch activity from eAnnotation
      if (activity == null)
      {
         activity = loadActivityFromEAnnoation(eOperation);
      }

      // Process SDM model (ecore) and transform to SDM model (fujaba)
      if (activity != null)
      {
         if (properties.getStrictSDMConditionalBranching().isBool())
         {
            SDMPrecompiler sdmPrecompiler = SdmUtilFactory.eINSTANCE.createSDMPrecompiler();
            sdmPrecompiler.processActivity(activity);
         }

         UMLActivityDiagram umlActivityDiagram = method.createStoryDiagram();

         logger.debug("ActivityNodes to process: " + activity.getOwnedActivityNode());
         for (ActivityNode activityNode : activity.getOwnedActivityNode())
         {
            logger.debug("Processing: " + activityNode);
            processActivityNode(activityNode, umlActivityDiagram, eClassifierToUMLClassMap, eReferenceToUMLAssocMap, uniqueNameToUMLAssoc);
         }

         for (ActivityEdge activityEdge : activity.getOwnedActivityEdge())
         {
            processActivityEdge(activityEdge, umlActivityDiagram);
         }

         postProcessElements();

         logger.debug("Activities in Fujaba: " + activityNodeToUMLActivity.values());
      }
      // catch (Exception e)
      // {
      // throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to process SDM: " +
      // eOperation.getEContainingClass().getName() + "::"
      // + eOperation.getName() + ": " + e.toString() + ", " + Arrays.toString(e.getStackTrace())));
      // }

   }

   private void postProcessElements()
   {
      // Connect transitions and activities
      for (ActivityEdge activityEdge : activityEdgeToUMLTransition.keySet())
      {
         UMLTransition umlTransition = activityEdgeToUMLTransition.get(activityEdge);

         ActivityNode source = activityEdge.getSource();
         umlTransition.setSource(activityNodeToUMLActivity.get(source));

         ActivityNode target = activityEdge.getTarget();
         umlTransition.setTarget(activityNodeToUMLActivity.get(target));
      }

      // Connect links and objects
      for (LinkVariable abstractLinkVariable : abstractLinkVariableToUmlLink.keySet())
      {
         UMLLink umlLink = abstractLinkVariableToUmlLink.get(abstractLinkVariable);

         ObjectVariable source = abstractLinkVariable.getSource();
         umlLink.setSource(objectVariableToUmlObject.get(source));

         ObjectVariable target = abstractLinkVariable.getTarget();
         umlLink.setTarget(objectVariableToUmlObject.get(target));

         // Handle direction of Assoc
         String nameOfLink = abstractLinkVariable.getName();
         String nameOfAssoc = umlLink.getInstanceOf().getName();
         if (!nameOfLink.equals(nameOfAssoc))
         {
            umlLink.setSource(objectVariableToUmlObject.get(target));
            umlLink.setTarget(objectVariableToUmlObject.get(source));
         }
      }

      logger.debug("Completed post processing!");
   }

   private void processActivityEdge(final ActivityEdge activityEdge, final UMLActivityDiagram umlActivityDiagram)
   {
      UMLTransition transition = project.getFromFactories(UMLTransition.class).create();
      umlActivityDiagram.addToElements(transition);

      UMLTransitionGuard guard = project.getFromFactories(UMLTransitionGuard.class).create();
      transition.setGuard(guard);

      switch (activityEdge.getGuard())
      {
      case SUCCESS:
         guard.setType(UMLTransitionGuard.SUCCESS);
         break;
      case FAILURE:
         guard.setType(UMLTransitionGuard.FAILURE);
         break;
      case NONE:
         guard.setType(UMLTransitionGuard.NONE);
         break;
      case END:
         guard.setType(UMLTransitionGuard.TERMINATION);
         break;
      case EACH_TIME:
         guard.setType(UMLTransitionGuard.EVERYTIMES);
         break;
      default:
         throw new UnsupportedOperationException("Type of guard is not implemented");
      }

      activityEdgeToUMLTransition.put(activityEdge, transition);
   }

   private void processActivityNode(final ActivityNode activityNode, final UMLActivityDiagram umlActivityDiagram,
         final HashMap<EClassifier, UMLClass> eClassifierToUMLClassMap, final HashMap<EReference, UMLAssoc> eReferenceToUMLAssocMap,
         final HashMap<String, UMLAssoc> uniqueNameToUMLAssoc) throws CoreException
   {
      UMLActivity umlActivity = null;

      if (activityNode instanceof StartNode)
      {
         umlActivity = umlActivityDiagram.getStartActivity();
         activityNodeToUMLActivity.put(activityNode, umlActivity);
      } else if (activityNode instanceof StopNode)
      {
         umlActivity = project.getFromFactories(UMLStopActivity.class).create();
         umlActivityDiagram.addToElements(umlActivity);
         activityNodeToUMLActivity.put(activityNode, umlActivity);

         Expression returnValue = ((StopNode) activityNode).getReturnValue();
         if (returnValue instanceof TextualExpression)
         {
            UMLStopActivity stopActivity = (UMLStopActivity) umlActivity;
            TextualExpression textualExpression = ((TextualExpression) returnValue);
            stopActivity.setReturnValue((textualExpression.getExpressionText()));
            stopActivity.setGenerateCode(true);
         } else if (returnValue instanceof LiteralExpression)
         {
            UMLStopActivity stopActivity = (UMLStopActivity) umlActivity;
            LiteralExpression literalExpression = (LiteralExpression) returnValue;

            stopActivity.setReturnValue(eMoflonSDMUtil.extractValueFromLiteralExpression(literalExpression));
            stopActivity.setGenerateCode(true);
         }

         else if (returnValue == null)

         {
            // No return value == void
            UMLStopActivity stopActivity = (UMLStopActivity) umlActivity;
            stopActivity.setGenerateCode(true);

         } else if (returnValue instanceof ObjectVariableExpression)
         {
            UMLStopActivity stopActivity = (UMLStopActivity) umlActivity;
            stopActivity.setReturnValue(((ObjectVariableExpression) returnValue).getObject().getName());
            stopActivity.setGenerateCode(true);
         }

         else if (returnValue instanceof MethodCallExpression)
         {
            UMLStopActivity stopActivity = (UMLStopActivity) umlActivity;
            MethodCallExpression methodCall = (MethodCallExpression) returnValue;
            stopActivity.setReturnValue(eMoflonSDMUtil.extractMethodCall(methodCall, project.getName()));
            stopActivity.setGenerateCode(true);
         }

         else if (returnValue instanceof ParameterExpression)
         {
            UMLStopActivity stopActivity = (UMLStopActivity) umlActivity;
            ParameterExpression parameterExp = (ParameterExpression) returnValue;

            EParameter parameter = parameterExp.getParameter();

            stopActivity.setReturnValue(parameter.getName());
            stopActivity.setGenerateCode(true);
         }

         else if (returnValue instanceof AttributeValueExpression)
         {
            UMLStopActivity stopActivity = (UMLStopActivity) umlActivity;
            AttributeValueExpression attributeValueExpression = (AttributeValueExpression) returnValue;
            stopActivity.setReturnValue(eMoflonSDMUtil.extractAttributeValueExpression(attributeValueExpression, project.getName()));
            stopActivity.setGenerateCode(true);
         }

         else
            throw new UnsupportedOperationException("Expression handling not implemented for: " + returnValue);
      } else if (activityNode instanceof StoryNode)
      {
         umlActivity = project.getFromFactories(UMLStoryActivity.class).create();
         umlActivityDiagram.addToElements(umlActivity);
         activityNodeToUMLActivity.put(activityNode, umlActivity);
         processStoryNode((StoryNode) activityNode, (UMLStoryActivity) umlActivity, eClassifierToUMLClassMap, eReferenceToUMLAssocMap, uniqueNameToUMLAssoc);

      } else if (activityNode instanceof StatementNode)
      {
         StatementNode statementNode = (StatementNode) activityNode;

         umlActivity = project.getFromFactories(UMLStatementActivity.class).create();
         umlActivityDiagram.addToElements(umlActivity);
         activityNodeToUMLActivity.put(activityNode, umlActivity);

         // Handle statement node
         Expression statementExp = statementNode.getStatementExpression();
         UMLStatementActivity umlStatement = (UMLStatementActivity) umlActivity;
         if (statementExp instanceof MethodCallExpression)
         {
            MethodCallExpression methodCall = (MethodCallExpression) statementExp;
            String statement = eMoflonSDMUtil.extractMethodCall(methodCall, project.getName());
            umlStatement.setStatement(expandToJavaStatement(statement, statementNode, methodCall.getCallee()));
         } else if (statementExp instanceof LiteralExpression)
         {
            LiteralExpression literalExp = (LiteralExpression) statementExp;
            umlStatement.setStatement(expandToJavaStatement(literalExp.getValue(), statementNode));
         } else
            throw new UnsupportedOperationException("Expression handling not implemented for: " + statementExp);
      } else
      {
         throw new UnsupportedOperationException("Unsupported type of ActivityNode: " + activityNode);
      }

      // Set name
      umlActivity.setName(activityNode.getName());
   }

   private String expandToJavaStatement(final String statement, final StatementNode statementNode)
   {
      if (statementNode.getOutgoing().size() > 1)
         return FUJABA_SUCCESS + " = " + statement;

      return statement;
   }

   private String expandToJavaStatement(final String statement, final StatementNode statementNode, final EOperation callee)
   {
      if (statementNode.getOutgoing().size() > 1)
      {
         // Check if method is not void
         if (callee.getEType() != null)
         {
            // Check if method returns bool
            boolean methodReturnsBoolean = callee.getEType().equals(EcorePackage.eINSTANCE.getEBoolean());

            if (methodReturnsBoolean)
               return FUJABA_SUCCESS + " = " + statement + ";";
            else
               // If method has a non-bool return value, branch with
               // null-check
               return FUJABA_SUCCESS + " = " + statement + " != null;";
         } else
            // Trouble - no way to branch!
            throw new IllegalStateException(callee + " used in " + statement + " in statement node: " + statementNode + ","
                  + " must not be void when branching!");
      }

      return statement + ";";
   }

   private void processStoryNode(final StoryNode activityNode, final UMLStoryActivity umlStoryActivity,
         final HashMap<EClassifier, UMLClass> eClassifierToUMLClassMap, final HashMap<EReference, UMLAssoc> eReferenceToUMLAssocMap,
         final HashMap<String, UMLAssoc> uniqueNameToUMLAssoc) throws CoreException
   {
      if (activityNode instanceof StoryNode)
      {
         StoryPattern matchingPattern = activityNode.getStoryPattern();
         UMLStoryPattern umlStoryPattern = project.getFromFactories(UMLStoryPattern.class).create();
         umlStoryPattern.setType(UMLStoryPattern.STORYPATTERN);
         umlStoryPattern.setName(activityNode.getName());
         umlStoryActivity.setForEach(activityNode.isForEach());

         umlStoryActivity.setStoryPattern(umlStoryPattern);

         // Process object variables
         for (ObjectVariable objectVariable : matchingPattern.getObjectVariable())
         {
            processObjectVariable(eClassifierToUMLClassMap, umlStoryPattern, objectVariable);
         }

         for (LinkVariable abstractLinkVariable : matchingPattern.getLinkVariable())
         {
            processLinkVariable(uniqueNameToUMLAssoc, matchingPattern, umlStoryPattern, abstractLinkVariable);
         }
      } else
      {
         throw new IllegalArgumentException("Don't know how to handle: " + activityNode.getClass());
      }
   }

   private void processLinkVariable(final HashMap<String, UMLAssoc> uniqueNameToUMLAssoc, final StoryPattern matchingPattern,
         final UMLStoryPattern umlStoryPattern, final LinkVariable abstractLinkVariable) throws CoreException
   {
      UMLLink umlLink = project.getFromFactories(UMLLink.class).create();
      umlStoryPattern.addToElements(umlLink);

      // Use name, target and source of linkVariable to set name and type of umlLink
      String nameOfLink = abstractLinkVariable.getName();
      umlLink.setName(nameOfLink);

      // Determine assoc (type of link)
      ObjectVariable source = abstractLinkVariable.getSource();
      ObjectVariable target = abstractLinkVariable.getTarget();
      if (source == null || target == null)
      {
         throw new IllegalArgumentException("The link variable: " + source + "-" + nameOfLink + "->" + target + " in "
               + eMoflonEMFUtil.getName(matchingPattern) + " is invalid!");
      }
      FAssoc typeOfLink = determineTypeOfLink(uniqueNameToUMLAssoc, nameOfLink, source.getType(), target.getType());
      umlLink.setInstanceOf(typeOfLink);

      // Handle binding operator
      switch (abstractLinkVariable.getBindingOperator())
      {
      case CREATE:
         umlLink.setModifier(UMLLink.CREATE);
         break;
      case DESTROY:
         umlLink.setModifier(UMLLink.DELETE);
         break;
      case CHECK_ONLY:
         umlLink.setModifier(UMLLink.NONE);
         break;
      }

      // Handle binding semantic
      switch (abstractLinkVariable.getBindingSemantics())
      {
      case MANDATORY:
         umlLink.setType(UMLLink.NONE);
         break;
      case NEGATIVE:
         umlLink.setType(UMLLink.NEGATIVE);
         break;
      case OPTIONAL:
         umlLink.setType(UMLLink.OPTIONAL);

      }

      abstractLinkVariableToUmlLink.put(abstractLinkVariable, umlLink);
   }

   private void processObjectVariable(final HashMap<EClassifier, UMLClass> eClassifierToUMLClassMap, final UMLStoryPattern umlStoryPattern,
         final ObjectVariable objectVariable)
   {
      UMLObject umlObject = project.getFromFactories(UMLObject.class).create();
      umlStoryPattern.addToElements(umlObject);

      umlObject.setObjectName(objectVariable.getName());
      umlObject.setBound(objectVariable.getBindingState().equals(BindingState.BOUND));

      switch (objectVariable.getBindingOperator())
      {
      case CREATE:
         umlObject.setModifier(UMLObject.CREATE);
         break;
      case DESTROY:
         umlObject.setModifier(UMLObject.DELETE);
         break;
      case CHECK_ONLY:
         umlObject.setModifier(UMLObject.NONE);
         break;
      default:
         throw new UnsupportedOperationException("Handling of modifier is not implemented");
      }

      switch (objectVariable.getBindingSemantics())
      {
      case NEGATIVE:
         umlObject.setType(UMLObject.NEGATIVE);
         break;
      case OPTIONAL:
         umlObject.setType(UMLObject.OPTIONAL);
         break;
      case MANDATORY:
         umlObject.setType(UMLObject.NORM);
         break;
      default:
         throw new UnsupportedOperationException("Handling of Binding Type is not implemented");
      }

      // Use classifier of ObjectVariable to set type of umlObject
      EClass typeOfObjectVariable = (EClass) objectVariable.getType();
      UMLClass fujabaType = determineFujabaType(typeOfObjectVariable, eClassifierToUMLClassMap);

      if (fujabaType == null)
         throw new UnsupportedOperationException("Unable to determine type of " + objectVariable + " with Ecore type: " + objectVariable.getType());

      umlObject.setInstanceOf(fujabaType);

      // Handle constraints
      EList<Constraint> constraints = objectVariable.getConstraint();
      for (Constraint constraint : constraints)
      {
         UMLAttrExprPair umlAttrExprPair = project.getFromFactories(UMLAttrExprPair.class).create();
         umlObject.addToAttrs(umlAttrExprPair);

         Expression expression = constraint.getConstraintExpression();
         if (expression instanceof ComparisonExpression)
         {
            ComparisonExpression compExpr = (ComparisonExpression) expression;

            // A comparison corresponds to a pre-conditional comparison
            umlAttrExprPair.setQualifier(UMLAttrExprPair.PRE);

            // Get operator and translate
            switch (compExpr.getOperator())
            {
            case EQUAL:
               umlAttrExprPair.setOperation(UMLAttrExprPair.EQUAL);
               break;
            case UNEQUAL:
               umlAttrExprPair.setOperation(UMLAttrExprPair.NOTEQUAL);
               break;
            case LESS_OR_EQUAL:
               umlAttrExprPair.setOperation(UMLAttrExprPair.LESSEQUAL);
               break;
            case LESS:
               umlAttrExprPair.setOperation(UMLAttrExprPair.LESS);
               break;
            case GREATER:
               umlAttrExprPair.setOperation(UMLAttrExprPair.GREATER);
               break;
            case GREATER_OR_EQUAL:
               umlAttrExprPair.setOperation(UMLAttrExprPair.GREATEREQUAL);
               break;
            default:
               throw new UnsupportedOperationException("Operator translation is not implemented");
            }

            // Translate right hand side (= expression)
            if (compExpr.getRightExpression() instanceof LiteralExpression)
            {
               LiteralExpression literalExpression = (LiteralExpression) compExpr.getRightExpression();
               umlAttrExprPair.setExpression(eMoflonSDMUtil.extractValueFromLiteralExpression(literalExpression));
            } else if (compExpr.getRightExpression() instanceof ParameterExpression)
            {
               ParameterExpression parameterExp = (ParameterExpression) compExpr.getRightExpression();
               EParameter parameter = parameterExp.getParameter();
               umlAttrExprPair.setExpression(parameter.getName());
            } else if (compExpr.getRightExpression() instanceof MethodCallExpression)
            {
               MethodCallExpression methodCall = (MethodCallExpression) compExpr.getRightExpression();
               if (compExpr.getLeftExpression() instanceof AttributeValueExpression)
               {
                  EAttribute attr = ((AttributeValueExpression) compExpr.getLeftExpression()).getAttribute();
                  umlAttrExprPair.setExpression("(" + getTypeCast(attr) + ")" + eMoflonSDMUtil.extractMethodCall(methodCall, project.getName()));
               }
            }

            else if (compExpr.getRightExpression() instanceof AttributeValueExpression)
            {
               AttributeValueExpression attributeValueExpression = (AttributeValueExpression) compExpr.getRightExpression();
               umlAttrExprPair.setExpression(eMoflonSDMUtil.extractAttributeValueExpression(attributeValueExpression, project.getName()));
            }

            else
            {
               throw new UnsupportedOperationException("Expression type of RHS is not implemented");
            }

            // Translate left hand side (= attribute)
            if (compExpr.getLeftExpression() instanceof AttributeValueExpression)
            {
               AttributeValueExpression attrValExpr = (AttributeValueExpression) compExpr.getLeftExpression();
               EAttribute attr = attrValExpr.getAttribute();
               FAttr instanceOf = umlObject.getInstanceOf().getFromAllAttrs(attr.getName());

               if (instanceOf == null)
               {
                  throw new UnsupportedOperationException("Unable to resolve: " + attr);
               }

               umlAttrExprPair.setInstanceOf(instanceOf);
               umlAttrExprPair.setName(instanceOf.getName());
            } else
            {
               throw new UnsupportedOperationException("Expression type of LHS is not implemented");
            }
         } else
         {
            throw new UnsupportedOperationException("Expression handling of constraint not implemented");
         }

      }

      // Handle attribute assignments
      EList<AttributeAssignment> attributeAssignments = objectVariable.getAttributeAssignment();
      for (AttributeAssignment attributeAssignment : attributeAssignments)
      {
         UMLAttrExprPair umlAttrExprPair = project.getFromFactories(UMLAttrExprPair.class).create();
         umlObject.addToAttrs(umlAttrExprPair);
         umlAttrExprPair.setQualifier(UMLAttrExprPair.POST);
         umlAttrExprPair.setOperation(UMLAttrExprPair.EQUAL);

         // Defines which attribute should be assigned
         EAttribute attr = attributeAssignment.getAttribute();
         FAttr instanceOf = umlObject.getInstanceOf().getFromAllAttrs(attr.getName());

         if (instanceOf == null)
         {
            throw new UnsupportedOperationException("Unable to resolve: " + attr);
         }

         umlAttrExprPair.setInstanceOf(instanceOf);
         umlAttrExprPair.setName(instanceOf.getName());
         Expression expression = attributeAssignment.getValueExpression();
         if (expression instanceof TextualExpression)
         {
            TextualExpression textualExpression = (TextualExpression) expression;
            umlAttrExprPair.setExpression(textualExpression.getExpressionText());
         } else if (expression instanceof AttributeValueExpression)
         {
            AttributeValueExpression attributeValueExpression = (AttributeValueExpression) expression;
            umlAttrExprPair.setExpression(eMoflonSDMUtil.extractAttributeValueExpression(attributeValueExpression, project.getName()));

         } else if (expression instanceof ParameterExpression)
         {
            ParameterExpression parameterExp = (ParameterExpression) expression;
            EParameter parameter = parameterExp.getParameter();
            umlAttrExprPair.setExpression(parameter.getName());
         } else if (expression instanceof MethodCallExpression)
         {
            MethodCallExpression methodCall = (MethodCallExpression) expression;
            umlAttrExprPair.setExpression("(" + getTypeCast(attr) + ")" + eMoflonSDMUtil.extractMethodCall(methodCall, project.getName()));
         } else if (expression instanceof LiteralExpression)
         {
            LiteralExpression literalExpression = (LiteralExpression) expression;
            umlAttrExprPair.setExpression(eMoflonSDMUtil.extractValueFromLiteralExpression(literalExpression));
         } else
         {
            throw new UnsupportedOperationException("Expression type:" + expression + " of ValueExpression is not implemented");
         }
      }

      // Handle Binding, but only if ov is bound
      Expression binding = objectVariable.getBindingExpression();
      if (binding != null && objectVariable.getBindingState().equals(BindingState.BOUND))
      {
         String typeCastSource = "";

         if (binding instanceof ParameterExpression)
         {
            ParameterExpression paramExp = (ParameterExpression) binding;
            typeCastSource = paramExp.getParameter().getName();
         } else if (binding instanceof ObjectVariableExpression)
         {
            ObjectVariableExpression objVarExp = (ObjectVariableExpression) binding;
            typeCastSource = objVarExp.getObject().getName();
         } else if (binding instanceof MethodCallExpression)
         {
            typeCastSource = "(" + eMoflonSDMUtil.extractMethodCall((MethodCallExpression) binding, project.getName()) + ")";
         } else if (binding instanceof LiteralExpression)
         {
            typeCastSource = "(" + eMoflonSDMUtil.extractValueFromLiteralExpression((LiteralExpression) binding) + ")";
         } else
         {
            throw new UnsupportedOperationException("Binding expression:" + binding + " is not supported");
         }

         // Create typecast for fujaba
         umlObject.setCheckTypeCast(true);
         umlObject.setTypeCastSource(typeCastSource);
      }

      objectVariableToUmlObject.put(objectVariable, umlObject);
   }

   private String getTypeCast(final EAttribute attribute)
   {
      String instanceTypeName = attribute.getEType().getInstanceTypeName();
      if (instanceTypeName == null)
         instanceTypeName = MoflonUtil.getFQN(attribute.getEType());

      // Correct instanceTypeName with import mappings
      Map<String, String> importMappings = MoflonPropertiesContainerHelper.mappingsToMap(properties.getImportMappings());
      instanceTypeName = MoflonUtil.transformPackageNameUsingImportMapping(instanceTypeName, importMappings);

      return instanceTypeName;
   }

   private UMLClass determineFujabaType(final EClass typeOfObjectVariable, final HashMap<EClassifier, UMLClass> eClassifierToUMLClassMap)
   {
      UMLClass fujabaType = eClassifierToUMLClassMap.get(typeOfObjectVariable);
      if (fujabaType == null)
      {
         String uriFragment = typeOfObjectVariable.getEPackage().getNsURI() + typeOfObjectVariable.eResource().getURIFragment(typeOfObjectVariable);
         for (EClassifier eClass : eClassifierToUMLClassMap.keySet())
         {
            String uriFragmentCandidate = eClass.getEPackage().getNsURI() + eClass.eResource().getURIFragment(eClass);
            if (uriFragment.equals(uriFragmentCandidate))
               fujabaType = eClassifierToUMLClassMap.get(eClass);
         }
      }

      return fujabaType;
   }

   private FAssoc determineTypeOfLink(final HashMap<String, UMLAssoc> uniqueNameToUMLAssoc, final String nameOfLink, final EClassifier source,
         final EClassifier target) throws CoreException
   {
      if (source instanceof EClass && target instanceof EClass)
      {
         EClass sourceClass = (EClass) source;
         List<EClass> sources = new ArrayList<EClass>();
         sources.add(sourceClass);
         sources.addAll(sourceClass.getEAllSuperTypes());
         sources.add(EcorePackage.eINSTANCE.getEObject());

         EClass targetClass = (EClass) target;
         List<EClass> targets = new ArrayList<EClass>();
         targets.add(targetClass);
         targets.addAll(targetClass.getEAllSuperTypes());
         targets.add(EcorePackage.eINSTANCE.getEObject());

         FAssoc typeOfLink = null;
         for (EClassifier s : sources)
         {
            for (EClassifier t : targets)
            {
               typeOfLink = uniqueNameToUMLAssoc.get(getFullyQualName(nameOfLink, s, t));
               if (typeOfLink != null)
                  return typeOfLink;
            }
         }
      }

      throw new UnsupportedOperationException("Unable to determine type of: " + nameOfLink);
   }

   public static String getFullyQualName(final String nameOfLink, final EClassifier source, final EClassifier target) throws CoreException
   {
      if (source == null || target == null || nameOfLink == null || nameOfLink.length() == 0)
         MoflonUtil.throwCoreExceptionAsError("The eReference: " + source + "-" + nameOfLink + "->" + target + " is not valid!",
               MoflonUtilitiesActivator.PLUGIN_ID, null);

      String fullyQualSource = source.getEPackage().getNsURI() + "_" + source.getName();
      String fullyQualTarget = target.getEPackage().getNsURI() + "_" + target.getName();
      return nameOfLink + "_" + fullyQualSource + "_" + fullyQualTarget;
   }

   /**
    * For debug purposes return a list of all loaded SDMs as Strings.
    * 
    * @return
    */
   public HashMap<String, String> getSDMs()
   {
      return sdms;
   }
}

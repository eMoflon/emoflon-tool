/*
 * Copyright (c) 2010-2012 Gergely Varro
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   Gergely Varro - Initial API and implementation
 */
package org.moflon.eclipse.genmodel;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenOperation;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.jet.JETEmitter;
import org.eclipse.emf.codegen.util.ImportManager;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.gervarro.democles.emoflon.templates.JavaClassGenerator;
import org.moflon.codegen.InjectionHandlingImportManager;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.ui.LoggingSTErrorListener;
import org.moflon.moca.inject.InjectionManager;
import org.moflon.moca.inject.util.InjectionRegions;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import SDMLanguage.activities.Activity;
import SDMLanguage.activities.ActivityNode;
import SDMLanguage.activities.StopNode;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.activities.impl.MoflonEOperationImpl;
import SDMLanguage.calls.callExpressions.MethodCallExpression;
import SDMLanguage.calls.callExpressions.ParameterExpression;
import SDMLanguage.expressions.Expression;
import SDMLanguage.patterns.ObjectVariable;
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

abstract public class MoflonClassGeneratorAdapter extends org.eclipse.emf.codegen.ecore.genmodel.generator.GenClassGeneratorAdapter
{
   private static final String DERIVED_ATTRIBUTES_TEMPLATE_GROUP = "derivedAttributes";

   private Logger logger = Logger.getLogger(MoflonClassGeneratorAdapter.class);

   private JETEmitterDescriptor[] emitterDescriptors;

   private STGroup derivedAttributesGroup;

   /**
    * Registers {@link JavaClassGenerator} as code-generating class for EMF classes.
    * 
    * @param generatorAdapterFactory
    */
   public MoflonClassGeneratorAdapter(final GeneratorAdapterFactory generatorAdapterFactory)
   {
      super(generatorAdapterFactory);
      emitterDescriptors = new JETEmitterDescriptor[] {
            new JETEmitterDescriptor("model/Class.javajet", "org.gervarro.democles.emoflon.templates.JavaClassGenerator") };
   }

   public boolean hasGeneratedMethodBody(final EOperation eOperation)
   {
      return getAdapterFactory().getInjectionManager().hasModelCode(eOperation);
   }

   /**
    * Generates the content of the method, described by the given EOperation.
    */
   abstract public String getGeneratedMethodBody(EOperation eOperation);

   /**
    * Returns the code that should be inserted at the beginning of the getter of the given {@link GenFeature}
    * 
    * @param genFeature
    *         the affected {@link GenFeature}
    * @return the code to be inserted, starting and ending with a new-line, or <code>null</code> if no code should be
    *         added.
    */
   public String getPreGetGenFeatureCode(final GenFeature genFeature)
   {
        if (genFeature.isDerived()) {
            initializeStringTemplatesForDerivedAttributesLazily();

            Set<String> dependentVariables = analyzeSDM(genFeature);
            
            String genFeatureTemplateName = "";
            if (!calculationMethodExists(genFeature)) {
                genFeatureTemplateName = "/preGetGenFeatureNoOperation";
            } else if (genFeature.isPrimitiveType()) {
                genFeatureTemplateName = "/preGetGenFeaturePrimitiveType";
            } else if (genFeature.isReferenceType() || genFeature.isStringType() || isUserDefinedType(genFeature)) {
                genFeatureTemplateName = "/preGetGenFeatureReferenceType";
            } else {
                genFeatureTemplateName = "/preGetGenFeatureUnknownType";
            }

            ST preGetGenFeatureTemplate = derivedAttributesGroup
                    .getInstanceOf("/" + DERIVED_ATTRIBUTES_TEMPLATE_GROUP + genFeatureTemplateName);
            preGetGenFeatureTemplate.add("genFeature", genFeature);
            preGetGenFeatureTemplate.add("genFeatureType", genFeature.getImportedType(genFeature.getGenClass()));
            String preGetGenFeatureTypeCode = preGetGenFeatureTemplate.render();

            return preGetGenFeatureTypeCode;
        } else {
            return null;
        }
   }
   
    /**
     * Creates a set of variables on which the derived attribute depends.
     * @param genFeature
     *      The derived attribute that should be analyzed.
     * @return 
     *      A set of variables on which the derived attribute depends.
     */
    private Set<String> analyzeSDM(final GenFeature genFeature) {

        Set<String> dependentVariables = new HashSet<String>();

        Activity activity = getActivity(genFeature);

        if (activity != null) {
            EList<ActivityNode> nodes = activity.getOwnedActivityNode();

            for (ActivityNode node : nodes) {
                if (node instanceof StoryNode) {
                    Set<String> storyNodeVariables = analyzeStoryNode((StoryNode)node);
                    dependentVariables.addAll(storyNodeVariables);
                } else if (node instanceof StopNode) {
                    Set<String> stooNodeVariables = analyzeStopNode((StopNode)node);
                    dependentVariables.addAll(stooNodeVariables);
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
    private Set<String> analyzeStoryNode(StoryNode node) {
        return analyzeStoryPattern(node.getStoryPattern());
    }
    
    /**
     * Creates a set of variables contained in a {@link StopNode}}
     * on which the derived attribute depends.
     * @param node
     *      The stop node that should be analyzed.
     * @return
     *      A set of variables on which the derived attribute depends.
     */
    private Set<String> analyzeStopNode(StopNode node) {
        return analyzeExpression(node.getReturnValue());
    }
    
    /**
     * Creates a set of variables contained in a {@link StoryPattern}}
     * on which the derived attribute depends.
     * @param storyPattern
     *      The story pattern that should be analyzed.
     * @return
     *      A set of variables on which the derived attribute depends.
     */
    private Set<String> analyzeStoryPattern(StoryPattern storyPattern) {

        Set<String> dependentVariables = new HashSet<String>();
        
        for (AttributeConstraintVariable variable : storyPattern.getVariables()) {
            if (variable instanceof AttributeConstraintExpression) {
                //AttributeConstraintExpression attributeConstraintExpression = (AttributeConstraintExpression)variable;
            } else if (variable instanceof CspConstraint) {
                //CspConstraint cspConstraint = (CspConstraint)variable;
            } else if (variable instanceof PrimitiveVariable) {
                PrimitiveVariable primitiveVariable = (PrimitiveVariable)variable;
                for (AttributeValueConstraint attributeValueConstraint : primitiveVariable.getAttributeValueConstraints()) {
                    if (attributeValueConstraint != null) {
                        if (attributeValueConstraint instanceof AssignmentConstraint) {
                            AssignmentConstraint assignmentConstraint = (AssignmentConstraint)attributeValueConstraint;
                            dependentVariables.add(assignmentConstraint.getObjectVariable().getName());
                        } else if (attributeValueConstraint instanceof AttributeLookupConstraint) {
                            AttributeLookupConstraint assignmentLookupConstraint = (AttributeLookupConstraint)attributeValueConstraint;
                            if (!assignmentLookupConstraint.getObjectVariable().getName().equals("this")) {
                                dependentVariables.add(assignmentLookupConstraint.getObjectVariable().getName());
                            } else {
                                dependentVariables.add(assignmentLookupConstraint.getType().getName());
                            }
                            
                        }
                    }
                }
            }
        }
        
        for (ObjectVariable variable : storyPattern.getObjectVariable()) {
            if (!variable.getName().equals("this")) {
                dependentVariables.add(variable.getName());
            }
        }
        
        return dependentVariables;
    }
    
    /**
     * Creates a set of variables contained in an {@link Expression}}
     * on which the derived attribute depends.
     * @param expression
     *      The expression that should be analyzed.
     * @return
     *      A set of variables on which the expression depends.
     */
    private Set<String> analyzeExpression(Expression expression) {

        Set<String> dependentVariables = new HashSet<String>();
        
        if (expression instanceof AttributeValueExpression) {
            AttributeValueExpression attributeValueExpression = (AttributeValueExpression)expression;
            dependentVariables.add(attributeValueExpression.getObject().getName());
        } else if (expression instanceof ObjectVariableExpression) {
            //ObjectVariableExpression objectVariableExpression = (ObjectVariableExpression)expression;
        } else if (expression instanceof MethodCallExpression) {
            //MethodCallExpression methodCallExpression = (MethodCallExpression)expression;
        } else if (expression instanceof ParameterExpression) {
            //ParameterExpression parameterExpression = (ParameterExpression)expression;
        }
        
        return dependentVariables;
    }

   /**
    * Extracts the {@link Activity} from a calculation method that corresponds
    * to the {@link GenFeature} that represents a derived attribute.
    * @param genFeature
    *       {@link GenFeature} that represents a derived attribute.
    * @return
    *       The corresponding {@link Activity}.
    */
   private Activity getActivity(final GenFeature genFeature) {
       Activity activity = null;

       String methodName = "_get" + genFeature.getCapName();
       String returnType = getAttributeType(genFeature);
       EOperation eOperation = getEOperation(genFeature, methodName, returnType);
       
       if (eOperation != null && eOperation instanceof MoflonEOperationImpl) {
           MoflonEOperationImpl eOperationImpl = (MoflonEOperationImpl) eOperation;
           activity = eOperationImpl.getActivity();
       }

       return activity;
   }

    /**
     * Checks if the type of the genFeature is defined in the model created by the user. 
     * @param genFeature
     *      The GenFeature to check. 
     * @return
     *      True, if the type is defined by the user, otherwise false.
     */
    private boolean isUserDefinedType(final GenFeature genFeature) {
        boolean isUserDefinedType = false;
        
        if (genFeature.eContainer() instanceof GenClass) {
            GenClass featureGenClass = (GenClass)genFeature.eContainer();
            
            if (featureGenClass.eContainer() instanceof GenPackage) {
                GenPackage genPackage = (GenPackage)featureGenClass.eContainer();
                
                for (GenClass genClass : genPackage.getAllSwitchGenClasses()) {
                    if (genClass instanceof MoflonGenClass
                            && genClass.getEcoreModelElement() instanceof EClassImpl) {
                        
                        EClassImpl eClassImpl = (EClassImpl)genClass.getEcoreModelElement();
                        String userDefinedClass = eClassImpl.getName();
                        String genFeatureClass = genFeature.getObjectType(featureGenClass);
                        
                        if (genFeatureClass.equals(userDefinedClass)) {
                            isUserDefinedType = true;
                            break;
                        }
                    }
                }
            }
        }
        
        return isUserDefinedType;
    }
    
    /**
     * Checks if there is a corresponding calculation method
     * to a {@link GenFeature} representing a derived attribute.
     * @param genFeature
     *      Representation of a derived attribute.
     * @return
     *      True, if a calculation method exists, otherwise false.
     */
    private boolean calculationMethodExists(final GenFeature genFeature) {
        boolean methodExists = false;
        String methodName = "_get" + genFeature.getCapName();
        String returnType = getAttributeType(genFeature);
        
        EOperation eOperation = getEOperation(genFeature, methodName, returnType);

        if (eOperation != null) {
            methodExists = true;
        }
        
        return methodExists;
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
     * @return
     *      The matching EOperation or null of there is no matching method. 
     */
    private EOperation getEOperation(final GenFeature genFeature, String name, String returnType) {
        EOperation eOperation = null;
        
        if (genFeature.eContainer() instanceof GenClass) {
            GenClass genClass = (GenClass) genFeature.eContainer();
            for (GenOperation genOperation : genClass.getGenOperations()) {
                EOperation currentEOperation = genOperation.getEcoreOperation();
                
                if (currentEOperation.getName().equals(name) 
                        && currentEOperation.getEType().getName().equals(returnType)) {
                    eOperation = currentEOperation;
                    break;
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
    private String getAttributeType(final GenFeature genFeature) {
        String returnType = null;
        if (genFeature.getTypeGenDataType() != null) {
            returnType = genFeature.getTypeGenDataType().getName();
        } else {
            returnType = genFeature.getImportedType(genFeature.getGenClass());
        }
        return returnType;
    }

   private void initializeStringTemplatesForDerivedAttributesLazily()
   {
      if (this.derivedAttributesGroup == null)
      {
         derivedAttributesGroup = new STGroup();
         derivedAttributesGroup.setListener(new LoggingSTErrorListener(logger));
         derivedAttributesGroup.loadGroupFile("/" + DERIVED_ATTRIBUTES_TEMPLATE_GROUP + "/",
               "platform:/plugin/" + CodeGeneratorPlugin.getModuleID() + "/templates/stringtemplate/derivedAttributes.stg");
      }
   }

   /**
    * Returns the code that should be inserted at the beginning of the setter of the given {@link GenFeature}
    * 
    * @param genFeature
    *           the affected {@link GenFeature}
    * @return the code to be inserted, starting and ending with a new-line, or <code>null</code> if no code should be
    *         added.
    */
   public String getPreSetGenFeatureCode(final GenFeature genFeature)
   {
      if (genFeature.isDerived())
      {
         return null;
      } else
      {
         return null;
      }
   }

   /**
    * Returns the members code for the given EClass, depending on whether we currently generate the interface or the
    * implementation of the EClass.
    */
   public String getInjectedCode(final boolean isImplementation)
   {
      String code = "";
      final InjectionManager injectionManager = getAdapterFactory().getInjectionManager();
      if (injectionManager != null)
      {
         final GenClass genClass = (GenClass) generatingObject;
         final String fullyQualifiedClassName = isImplementation ? CodeGeneratorPlugin.getClassName(genClass) : CodeGeneratorPlugin.getInterfaceName(genClass);

         final String retrievedMembersCode = injectionManager.getMembersCode(fullyQualifiedClassName);
         if (retrievedMembersCode != null)
         {
            code = retrievedMembersCode;
         }
      }
      return InjectionRegions.buildMembersBlock(code);
   }

   public void handleImports(final boolean isImplementation)
   {
      final GenClass genClass = ((GenClass) generatingObject);
      final InjectionManager injectionManager = getAdapterFactory().getInjectionManager();
      if (injectionManager != null)
      {
         final ImportManager importManager = genClass.getGenModel().getImportManager();
         final String fullyQualifiedClassName = isImplementation ? CodeGeneratorPlugin.getClassName(genClass) : CodeGeneratorPlugin.getInterfaceName(genClass);
         for (final String imp : injectionManager.getImports(fullyQualifiedClassName))
         {
            if (importManager instanceof InjectionHandlingImportManager)
            {
               ((InjectionHandlingImportManager) importManager).injectedImports.add(imp);
            } else
            {
               importManager.addImport(imp);
            }
         }
      }
   }

   @Override
   public GeneratorAdapterFactory getAdapterFactory()
   {
      return (GeneratorAdapterFactory) adapterFactory;
   }

   @Override
   protected void ensureContainerExists(final URI workspacePath, final Monitor monitor)
   {
      try
      {
         if (EMFPlugin.IS_ECLIPSE_RUNNING)
         {
            super.ensureContainerExists(workspacePath, monitor);
         } else
         {
            URI platformResourceURI = URI.createPlatformResourceURI(workspacePath.toString(), true);
            URI normalizedURI = getURIConverter().normalize(platformResourceURI);
            if (normalizedURI.isFile())
            {
               File file = new File(normalizedURI.toString());
               if (!file.exists())
               {
                  file.mkdirs();
               }
            }
         }
      } finally
      {
         monitor.done();
      }
   }

   @Override
   protected JETEmitter getJETEmitter(final JETEmitterDescriptor[] jetEmitterDescriptors, final int id)
   {
      JETEmitter jetEmitter = super.getJETEmitter(jetEmitterDescriptors, id);
      if (!EMFPlugin.IS_ECLIPSE_RUNNING)
      {
         try
         {
            Class<?> clazz = getClass().getClassLoader().loadClass(jetEmitterDescriptors[id].className);
            Method method = clazz.getMethod("generate", Object.class);
            jetEmitter.setMethod(method);
         } catch (ClassNotFoundException e)
         {
            // Do nothing
         } catch (NoSuchMethodException e)
         {
            // Do nothing
         } catch (SecurityException e)
         {
            // Do nothing
         }
      }
      return jetEmitter;
   }

   @Override
   protected JETEmitterDescriptor[] getJETEmitterDescriptors()
   {
      return emitterDescriptors;
   }

   @Override
   protected void ensureProjectExists(final String workspacePath, final Object object, final Object projectType, final boolean force, final Monitor monitor)
   {
      monitor.done();
   }

   @Override
   protected void createImportManager(final String packageName, final String className)
   {
      importManager = new InjectionHandlingImportManager(packageName, true);
      importManager.addMasterImport(packageName, className);
      updateImportManager();
   }

   @Override
   protected void clearImportManager()
   {
      importManager = null;
      updateImportManager();
   }

   protected void updateImportManager()
   {
      if (generatingObject != null)
      {
         ((GenBase) generatingObject).getGenModel().setImportManager(importManager);
      }
   }

   @Override
   protected Diagnostic doPreGenerate(final Object object, final Object projectType)
   {
      // if (object instanceof MoflonGenClass && projectType == GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE) {
      // MoflonGenClass genClass = (MoflonGenClass) object;
      // genClass.setCodeGenerator(getAdapterFactory().getCodeGenerator());
      // }
      return Diagnostic.OK_INSTANCE;
   }

   @Override
   protected Diagnostic doPostGenerate(final Object object, final Object projectType)
   {
      // if (object instanceof MoflonGenClass && projectType == GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE) {
      // MoflonGenClass genClass = (MoflonGenClass) object;
      // genClass.setCodeGenerator(null);
      // }
      return Diagnostic.OK_INSTANCE;
   }

   @Override
   protected void generateJava(final String targetPath, final String packageName, final String className, final JETEmitter jetEmitter, final Object[] arguments,
         final Monitor monitor)
   {
      Object argument = arguments[0];
      if (argument instanceof Object[])
      {
         Object[] argumentArray = (Object[]) argument;
         Object[] newArgumentArray = new Object[argumentArray.length + 1];
         System.arraycopy(argumentArray, 0, newArgumentArray, 0, argumentArray.length);
         newArgumentArray[argumentArray.length] = this;
         super.generateJava(targetPath, packageName, className, jetEmitter, new Object[] { newArgumentArray }, monitor);
      } else
      {
         super.generateJava(targetPath, packageName, className, jetEmitter, arguments, monitor);
      }
   }

}
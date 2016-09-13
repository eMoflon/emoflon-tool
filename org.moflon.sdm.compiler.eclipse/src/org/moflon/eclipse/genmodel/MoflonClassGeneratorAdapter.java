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

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.jet.JETEmitter;
import org.eclipse.emf.codegen.util.ImportManager;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EOperation;
import org.gervarro.democles.emoflon.templates.JavaClassGenerator;
import org.moflon.codegen.InjectionHandlingImportManager;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.moca.inject.CodeInjectionPlugin;
import org.moflon.moca.inject.InjectionManager;
import org.moflon.moca.inject.util.InjectionRegions;

/**
 * This implementation base class is invoked during the code generation of a Java class
 * 
 * @author Gergely Varr�
 * @author Roland Kluge
 * 
 * @see JavaClassGenerator
 */
abstract public class MoflonClassGeneratorAdapter extends org.eclipse.emf.codegen.ecore.genmodel.generator.GenClassGeneratorAdapter
{
   private static final String DERIVED_FEATURES_CODE_CONTRIBUTOR = "org.moflon.sdm.compiler.democles.derivedfeatures.DerivedFeaturesCodeContributor";

   private static final Logger logger = Logger.getLogger(MoflonClassGeneratorAdapter.class);

   private JETEmitterDescriptor[] emitterDescriptors;

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
    * Returns a string that is injected into the generated constructor.
    * 
    * @param genClass
    * @return
    */
   public String getConstructorInjectionCode(GenClass genClass)
   {
      MoflonClassGeneratorCodeContributor codeContributor = (MoflonClassGeneratorCodeContributor) Platform.getAdapterManager().loadAdapter(genClass,
            DERIVED_FEATURES_CODE_CONTRIBUTOR);
      if (codeContributor != null)
      {
         try
         {
            return codeContributor.getConstructorInjectionCode(genClass);
         } catch (final Exception e)
         {
            logger.error(String
                  .format("Problem while getting code contribution " + DERIVED_FEATURES_CODE_CONTRIBUTOR + ": " + MoflonUtil.displayExceptionAsString(e)));
            return null;
         }
      }
      return null;
   }

   /**
    * Returns the code that should be inserted at the beginning of the getter of the given {@link GenFeature}
    * 
    * @param genFeature
    *           the affected {@link GenFeature}
    * @return the code to be inserted, starting and ending with a new-line, or <code>null</code> if no code should be
    *         added.
    */
   public String getPreGetGenFeatureCode(final GenFeature genFeature)
   {

      MoflonClassGeneratorCodeContributor codeContributor = (MoflonClassGeneratorCodeContributor) Platform.getAdapterManager().loadAdapter(genFeature,
            DERIVED_FEATURES_CODE_CONTRIBUTOR);
      if (codeContributor != null)
      {
         try
         {
            return codeContributor.getPreGetGenFeatureCode(genFeature);
         } catch (final Exception e)
         {
            logger.error(String
                  .format("Problem while getting code contribution " + DERIVED_FEATURES_CODE_CONTRIBUTOR + ": " + MoflonUtil.displayExceptionAsString(e)));
            return null;
         }
      }

      return null;
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
      MoflonClassGeneratorCodeContributor codeContributor = (MoflonClassGeneratorCodeContributor) Platform.getAdapterManager().loadAdapter(genFeature,
            DERIVED_FEATURES_CODE_CONTRIBUTOR);
      if (codeContributor != null)
      {
         try
         {
            return codeContributor.getPreSetGenFeatureCode(genFeature);
         } catch (final Exception e)
         {
            logger.error(String
                  .format("Problem while getting code contribution " + DERIVED_FEATURES_CODE_CONTRIBUTOR + ": " + MoflonUtil.displayExceptionAsString(e)));
            return null;
         }
      }

      return null;
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
         final String fullyQualifiedClassName = isImplementation ? CodeInjectionPlugin.getClassName(genClass) : CodeInjectionPlugin.getInterfaceName(genClass);

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
         final String fullyQualifiedClassName = isImplementation ? CodeInjectionPlugin.getClassName(genClass) : CodeInjectionPlugin.getInterfaceName(genClass);
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
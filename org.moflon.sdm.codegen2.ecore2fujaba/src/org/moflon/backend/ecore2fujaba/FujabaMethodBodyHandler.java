package org.moflon.backend.ecore2fujaba;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory.Descriptor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.codegen.CodeGenerator;
import org.moflon.codegen.MethodBodyHandler;
import org.moflon.codegen.MoflonOperationIterator;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.codegen.eclipse.NoOperationTask;
import org.moflon.eclipse.genmodel.GeneratorAdapterFactory;
import org.moflon.eclipse.job.IMonitoredJob;
import org.moflon.moca.inject.CodeInjector;
import org.moflon.moca.inject.CodeInjectorImpl;

import MoflonPropertyContainer.MoflonPropertiesContainer;

public class FujabaMethodBodyHandler extends GeneratorAdapterFactory implements MethodBodyHandler, IMonitoredJob, Descriptor
{
   public static final String GENMODEL_BODY_KEY = "body";

   public static final String TASK_NAME = "Code generation";

   private final ResourceSet resourceSet;

   private final List<Resource> resources;

   private final IFile ecoreFile;

   private final MoflonPropertiesContainer moflonProperties;

   // Possible configuration options
   private final boolean useMethodBodyAnnotationInGenModel = true;

   private GenModel genModel;

   private Map<String, String> methodBodies;

   private CodeGen2Adapter fujabaCodegen;

   public FujabaMethodBodyHandler(final ResourceSet resourceSet, final List<Resource> resources, final IFile ecoreFile,
         final MoflonPropertiesContainer moflonProperties)
   {
      this.resourceSet = resourceSet;
      this.resources = resources;
      this.ecoreFile = ecoreFile;
      this.moflonProperties = moflonProperties;
   }

   @Override
   public IMonitoredJob createValidator(final EPackage ePackage)
   {
      return new NoOperationTask("Validation");
   }

   @Override
   public IMonitoredJob createGenModelProcessor(final MoflonCodeGenerator codeGenerator, final Resource resource)
   {
      this.genModel = codeGenerator.getGenModel();
      this.injectionManager = codeGenerator.getInjectorManager();
      return this;
   }

   @Override
   public Descriptor createCodeGenerationEngine(final MoflonCodeGenerator codeGenerator, final Resource resource)
   {
      return this;
   }

   @Override
   public GeneratorAdapterFactory createAdapterFactory()
   {
      return this;
   }

   @Override
   public IStatus run(final IProgressMonitor monitor)
   {
      try
      {
         monitor.beginTask("Generating method body", 10 + genModel.getGenPackages().size());
         final URI ecoreFileURI = URI.createPlatformResourceURI(ecoreFile.getFullPath().toString(), true);
         final Resource ecoreResource = resourceSet.getResource(ecoreFileURI, true);

         this.fujabaCodegen = new CodeGen2Adapter(ecoreFile, resources, ecoreResource, moflonProperties);
         methodBodies = fujabaCodegen.generateCode(genModel);
         fujabaCodegen.release();
         monitor.worked(10);

         final CodeInjector injector = new CodeInjectorImpl(ecoreFile.getProject().getLocation().toOSString());
         injectionManager.setCodeInjector(injector);

         for (final GenPackage genPackage : genModel.getGenPackages())
         {
            final EPackage ePackage = genPackage.getEcorePackage();
            for (final MoflonOperationIterator iterator = new MoflonOperationIterator(ePackage); iterator.hasNext();)
            {
               final EOperation eOperation = iterator.next();
               handleMethodBodyAnnotation(eOperation);
            }
            monitor.worked(1);
         }

      } catch (final Exception e)
      {
         throw new RuntimeException(e.getMessage(), e);
      } finally
      {
         monitor.done();
      }
      return Status.OK_STATUS;
   }

   @Override
   public String getTaskName()
   {
      return "Processing GenModel";
   }

   public String getGeneratedMethodBody(final EOperation eOperation)
   {
      final String injectionCode = injectionManager.getModelCode(eOperation);
      if (injectionCode != null)
      {
         return injectionCode;
      }
      return methodBodies.get(CodeGenerator.getOperationID(eOperation));
   }

   public void handleImports(final EClass eClass, final boolean isImplementation)
   {
      for (final GenPackage genPackage : genModel.getGenPackages())
      {
         final EPackage ePackage = genPackage.getEcorePackage();
         for (final MoflonOperationIterator iterator = new MoflonOperationIterator(ePackage); iterator.hasNext();)
         {
            final EOperation eOperation = iterator.next();
            fujabaCodegen.addImportsToGenModel(genModel, eOperation);
         }
      }
   }

   protected final void handleMethodBodyAnnotation(final EOperation eOperation)
   {
      if (useMethodBodyAnnotationInGenModel)
      {
         // Embed implementation as annotation for standard code generator
         final String codeForMethod = getGeneratedMethodBody(eOperation);
         if (codeForMethod != null)
         {
            // Embed SDM code as annotation (override even if there is currently no annotation).
            EAnnotation annotation = eOperation.getEAnnotation(GenModelPackage.eNS_URI);
            if (annotation == null)
            {
               annotation = EcoreFactory.eINSTANCE.createEAnnotation();
               annotation.setSource(GenModelPackage.eNS_URI);
               eOperation.getEAnnotations().add(annotation);
            }
            annotation.getDetails().put(GENMODEL_BODY_KEY, codeForMethod);
         }
      }
   }

   @Override
   public FujabaClassGeneratorAdapter createGenClassAdapter()
   {
      if (genClassGeneratorAdapter == null)
      {
         genClassGeneratorAdapter = new FujabaClassGeneratorAdapter(this);
      }
      return (FujabaClassGeneratorAdapter) genClassGeneratorAdapter;
   }
}

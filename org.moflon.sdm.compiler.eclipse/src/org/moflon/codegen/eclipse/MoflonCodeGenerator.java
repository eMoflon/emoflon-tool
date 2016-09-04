package org.moflon.codegen.eclipse;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.JobGroup;
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory.Descriptor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.codegen.CodeGenerator;
import org.moflon.codegen.MethodBodyHandler;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.eclipse.job.IMonitoredJob;
import org.moflon.moca.inject.CodeInjector;
import org.moflon.moca.inject.CodeInjectorImpl;
import org.moflon.moca.inject.InjectionManager;
import org.moflon.moca.inject.extractors.CompilerInjectionExtractorImpl;
import org.moflon.moca.inject.extractors.UserInjectionExtractorImpl;

public class MoflonCodeGenerator extends GenericMoflonProcess
{
   private int timeoutForValidationTaskInMillis = 0;

   private static final Logger logger = Logger.getLogger(MoflonCodeGenerator.class);

   private InjectionManager injectionManager;

   private GenModel genModel;

   public MoflonCodeGenerator(final IFile ecoreFile, final ResourceSet resourceSet)
   {
      super(ecoreFile, resourceSet);
   }
   
   public void setValidationTimeout(final int timeoutInMillis) {
      this.timeoutForValidationTaskInMillis = timeoutInMillis;
   }

   @Override
   public String getTaskName()
   {
      return "Generating code";
   }

   @SuppressWarnings("deprecation")
   @Override
   public IStatus processResource(final IProgressMonitor monitor)
   {
      try
      {
         SubMonitor subMon = SubMonitor.convert(monitor, "Code generation task", 100);
         final MoflonPropertiesContainer moflonProperties = getMoflonProperties();
         logger.info("Generating code for: " + moflonProperties.getMetaModelProject().getMetaModelProjectName() + "::" + moflonProperties.getProjectName());

         long toc = System.nanoTime();

         final Resource resource = getEcoreResource();
         final EPackage ePackage = (EPackage) resource.getContents().get(0);

         // (1) Instantiate code generation engine
         final String engineID = CodeGeneratorPlugin.getMethodBodyHandler(getMoflonProperties());
         final MethodBodyHandler methodBodyHandler = (MethodBodyHandler) Platform.getAdapterManager().loadAdapter(this, engineID);
         subMon.worked(5);
         if (methodBodyHandler == null)
         {
            return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), "Unknown method body handler: " + engineID + ". Code generation aborted.");
         }
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }

         // (2) Validate metamodel (including SDMs)
         final IMonitoredJob validator = methodBodyHandler.createValidator(ePackage);
         final WorkspaceJob validationJob = new WorkspaceJob(engineID) {
            @Override
            public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException
            {
               final SubMonitor subMon = SubMonitor.convert(monitor, "Validation job", 100);
               return validator.run(subMon.newChild(100));
            }
         };
         JobGroup jobGroup = new JobGroup("Validation job group", 1, 1);
         validationJob.setJobGroup(jobGroup);
         validationJob.schedule();
         jobGroup.join(timeoutForValidationTaskInMillis, subMon.newChild(10));

         final IStatus validatorStatus = validationJob.getResult();

         if (validatorStatus == null)
         {
            //TODO@rkluge: This is a really ugly hack that should be removed as soon as a more elegant solution is available
            validationJob.getThread().stop();
            throw new OperationCanceledException("Validation took longer than " + (timeoutForValidationTaskInMillis / 1000)
                  + "seconds. This could(!) mean that some of your patterns have no valid search plan. You may increase the timeout value using the eMoflon property page");
         } else if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         if (validatorStatus.matches(IStatus.ERROR))
         {
            return validatorStatus;
         }

         // (3) Build or load GenModel
         final MonitoredGenModelBuilder genModelBuilderJob = new MonitoredGenModelBuilder(getResourceSet(), getAllResources(), getEcoreFile(), true,
               getMoflonProperties());
         final IStatus genModelBuilderStatus = genModelBuilderJob.run(subMon.newChild(15));
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         if (genModelBuilderStatus.matches(IStatus.ERROR))
         {
            return genModelBuilderStatus;
         }
         this.genModel = genModelBuilderJob.getGenModel();

         // (4) Load injections
         final IProject project = getEcoreFile().getProject();

         final IStatus injectionStatus = createInjections(project, genModel);
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         if (injectionStatus.matches(IStatus.ERROR))
         {
            return injectionStatus;
         }

         // (5) Process GenModel
         subMon.subTask("Processing SDMs for project " + project.getName());
         final IMonitoredJob genModelProcessor = methodBodyHandler.createGenModelProcessor(this, resource);
         final IStatus genModelProcessorStatus = genModelProcessor.run(subMon.newChild(35));
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         if (genModelProcessorStatus.matches(IStatus.ERROR))
         {
            return genModelProcessorStatus;
         }

         // (6) Generate code
         subMon.subTask("Generating code for project " + project.getName());
         final Descriptor codeGenerationEngine = methodBodyHandler.createCodeGenerationEngine(this, resource);
         final CodeGenerator codeGenerator = new CodeGenerator(codeGenerationEngine);
         final IStatus codeGenerationStatus = codeGenerator.generateCode(genModel, new BasicMonitor.EclipseSubProgress(subMon, 30));
         if (subMon.isCanceled())
         {
            return Status.CANCEL_STATUS;
         }
         if (codeGenerationStatus.matches(IStatus.ERROR))
         {
            return codeGenerationStatus;
         }
         subMon.worked(5);

         long tic = System.nanoTime();

         logger.info("Completed in " + (tic - toc) / 1e9 + "s");

         return validatorStatus.isOK() && injectionStatus.isOK() ? new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), "Code generation succeeded")
               : new MultiStatus(CodeGeneratorPlugin.getModuleID(), validatorStatus.getCode(), new IStatus[] { validatorStatus, injectionStatus },
                     "Code generation warnings/errors", null);
      } catch (final Exception e)
      {
         return new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), IStatus.ERROR, e.getMessage(), e);
      } 
   }

   /**
    * Loads the injections from the /injection folder
    */
   private IStatus createInjections(final IProject project, final GenModel genModel) throws CoreException
   {
      IFolder injectionFolder = WorkspaceHelper.addFolder(project, WorkspaceHelper.INJECTION_FOLDER, new NullProgressMonitor());
      CodeInjector injector = new CodeInjectorImpl(project.getLocation().toOSString());

      UserInjectionExtractorImpl injectionExtractor = new UserInjectionExtractorImpl(injectionFolder.getLocation().toString(), genModel);
      CompilerInjectionExtractorImpl compilerInjectionExtractor = new CompilerInjectionExtractorImpl(project, genModel);

      injectionManager = new InjectionManager(injectionExtractor, compilerInjectionExtractor, injector);
      return injectionManager.extractInjections();
   }

   public final GenModel getGenModel()
   {
      return genModel;
   }

   public final InjectionManager getInjectorManager()
   {
      return injectionManager;
   }
}

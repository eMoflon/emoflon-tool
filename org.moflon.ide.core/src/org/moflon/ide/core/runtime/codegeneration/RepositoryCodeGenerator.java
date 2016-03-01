package org.moflon.ide.core.runtime.codegeneration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.osgi.service.resolver.BundleSpecification;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.PluginModelManager;
import org.moflon.codegen.ErrorReporter;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.MoflonCodeGenerator;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.preferences.EMoflonPreferencesStorage;
import org.moflon.ide.core.util.BuilderHelper;
import org.moflon.util.plugins.manifest.ExportedPackagesInManifestUpdater;
import org.moflon.util.plugins.manifest.PluginXmlUpdater;

@SuppressWarnings("restriction")
public class RepositoryCodeGenerator
{
   private static final Logger logger = Logger.getLogger(RepositoryCodeGenerator.class);

   protected IProject project;

   public RepositoryCodeGenerator(final IProject project)
   {
      this.project = project;
   }

   public boolean generateCode(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Generating code for project " + this.project.getName(), 9);

         final IFile ecoreFile = getEcoreFileAndHandleMissingFile();
         if (!ecoreFile.exists())
         {
            logger.warn("Unable to generate code for " + project.getName()
                  + ",  as no Ecore file according to naming convention (capitalizeFirstLetter.lastSegmentOf.projectName) was found!");
            return false;
         }

         final ResourceSet resourceSet = CodeGeneratorPlugin.createDefaultResourceSet();
         eMoflonEMFUtil.installCrossReferencers(resourceSet);
         monitor.worked(1);

         final MoflonCodeGenerator codeGenerationTask = new MoflonCodeGenerator(ecoreFile, resourceSet);
         codeGenerationTask.setValidationTimeout(EMoflonPreferencesStorage.getInstance().getValidationTimeout());

         generateDependenciesIfNecessary(WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         final IStatus status = codeGenerationTask.run(WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         handleErrorsAndWarnings(status);
         monitor.worked(3);

         final GenModel genModel = codeGenerationTask.getGenModel();
         if (genModel != null)
         {

            ExportedPackagesInManifestUpdater exportedPackagesUpdater = new ExportedPackagesInManifestUpdater(project, genModel);
            exportedPackagesUpdater.run(WorkspaceHelper.createSubmonitorWith1Tick(monitor));

            new PluginXmlUpdater().updatePluginXml(project, genModel, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         }
         ecoreFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         CoreActivator.addMappingForProject(project);

         CoreActivator.getDefault().setDirty(project, false);

      } finally
      {
         monitor.done();
      }

      return true;
   }

   /**
    * Handles errors and warning produced by the code generation task
    * 
    * @param status
    */
   private void handleErrorsAndWarnings(final IStatus status) throws CoreException
   {
      if (indicatesThatValidationCrashed(status))
      {
         throw new CoreException(new Status(IStatus.ERROR, CodeGeneratorPlugin.getModuleID(), status.getMessage(), status.getException().getCause()));
      }
      if (status.matches(IStatus.ERROR))
      {
         handleErrorsInEA(status);
         handleErrorsInEclipse(status);
      }
      if (status.matches(IStatus.WARNING))
      {
         handleInjectionWarningsAndErrors(status);
      }
   }

   private boolean indicatesThatValidationCrashed(IStatus status)
   {
      return status.getException() != null;
   }

   private void handleInjectionWarningsAndErrors(final IStatus status)
   {
      final String reporterClass = "org.moflon.moca.inject.validation.InjectionErrorReporter";
      final ErrorReporter errorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(project, reporterClass);
      if (errorReporter != null)
      {
         errorReporter.report(status);
      } else
      {
         logger.debug("Could not load error reporter '" + reporterClass + "'");
      }
   }

   public void handleErrorsInEclipse(final IStatus status)
   {
      final String reporterClass = "org.moflon.compiler.sdm.democles.eclipse.EclipseErrorReporter";
      final ErrorReporter eclipseErrorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(getEcoreFile(), reporterClass);
      if (eclipseErrorReporter != null)
      {
         eclipseErrorReporter.report(status);
      } else
      {
         logger.debug("Could not load error reporter '" + reporterClass + "'");
      }
   }

   public void handleErrorsInEA(final IStatus status)
   {
      final String reporterClass = "org.moflon.validation.EnterpriseArchitectValidationHelper";
      final ErrorReporter errorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(project, reporterClass);
      if (errorReporter != null)
      {
         errorReporter.report(status);
      } else
      {
         logger.debug("Could not load error reporter '" + reporterClass + "'");
      }
   }

   private void generateDependenciesIfNecessary(final IProgressMonitor monitor) throws OperationCanceledException, CoreException
   {
      try
      {
         IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

         monitor.beginTask("Generating dependencies", 2 * projects.length);
         List<IProject> requiredProjects = new ArrayList<>();
         PluginModelManager modelManager = PDECore.getDefault().getModelManager();
         final IPluginModelBase model = modelManager.findModel(project);

         if (model == null || model.getBundleDescription() == null)
         {
            throw new CoreException(
                  new Status(IStatus.ERROR, CoreActivator.getModuleID(), "Code generation requires project " + project.getName() + " to be a plugin project."));
         }

         List<BundleSpecification> requirements = Arrays.asList(model.getBundleDescription().getRequiredBundles());

         Predicate<BundleSpecification> isValidModel = r -> r.getSupplier() != null && r.getSupplier().getSupplier() != null
               && r.getSupplier().getSupplier().getSymbolicName() != null;

         Function<BundleSpecification, String> getName = r -> r.getSupplier().getSupplier().getSymbolicName();

         List<String> reqsAsIDs = requirements.stream().filter(isValidModel).map(getName).collect(Collectors.toList());

         for (IProject p : projects)
         {
            IPluginModelBase pm = modelManager.findModel(p);

            if (pm != null && reqsAsIDs.contains(pm.getBundleDescription().getSymbolicName()))
               requiredProjects.add(p);

            monitor.worked(1);
         }

         Collection<IProject> projectsWithoutGenmodel = requiredProjects.stream().filter(p -> !getGenmodelFile(p).exists()).collect(Collectors.toList());

         BuilderHelper.generateCodeInOrder(WorkspaceHelper.createSubMonitor(monitor, projects.length), projectsWithoutGenmodel);
      } finally
      {
         monitor.done();
      }
   }

   private static IResource getGenmodelFile(final IProject p)
   {
      String genModelFileName = MoflonUtil.lastCapitalizedSegmentOf(p.getName());
      return p.getFolder(WorkspaceHelper.MODEL_FOLDER).getFile(genModelFileName + WorkspaceHelper.GEN_MODEL_EXT);
   }

   private boolean doesEcoreFileExist()
   {
      return getEcoreFile().exists();
   }

   protected IFile getEcoreFile()
   {
      return getEcoreFile(project);
   }

   public static IFile getEcoreFile(final IProject p)
   {
      String ecoreFileName = MoflonUtil.getDefaultNameOfFileInProjectWithoutExtension(p.getName());
      return p.getFolder(WorkspaceHelper.MODEL_FOLDER).getFile(ecoreFileName + WorkspaceHelper.ECORE_FILE_EXTENSION);
   }

   protected IFile getEcoreFileAndHandleMissingFile() throws CoreException
   {
      if (!doesEcoreFileExist())
         createMarkersForMissingEcoreFile();

      return getEcoreFile();
   }

   private void createMarkersForMissingEcoreFile() throws CoreException
   {
      IFile ecoreFile = getEcoreFile();
      logger.error("Unable to generate code: " + ecoreFile + " does not exist in project!");

      // Create marker
      final IMarker marker = project.createMarker(IMarker.PROBLEM);
      marker.setAttribute(IMarker.MESSAGE, "Cannot find: " + ecoreFile.getProjectRelativePath().toString());
      marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
      marker.setAttribute(IMarker.LOCATION, ecoreFile.getProjectRelativePath().toString());
   }

   public static boolean isEcoreFileOfProject(final IResource resource, final IProject p)
   {
      return resource.exists() && resource.getProjectRelativePath().equals(getEcoreFile(p).getProjectRelativePath());
   }

}

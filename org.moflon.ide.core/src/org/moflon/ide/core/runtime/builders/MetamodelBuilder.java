package org.moflon.ide.core.runtime.builders;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gervarro.eclipse.task.ITask;
import org.gervarro.eclipse.task.ProgressMonitoringJob;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.codegen.eclipse.ValidationStatus;
import org.moflon.core.mocatomoflon.Exporter;
import org.moflon.core.utilities.ErrorReporter;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.eclipse.resource.SDMEnhancedEcoreResource;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.properties.MocaTreeEAPropertiesReader;
import org.moflon.ide.core.runtime.CleanMocaToMoflonTransformation;
import org.moflon.ide.core.runtime.MoflonProjectCreator;
import org.moflon.ide.core.runtime.ProjectDependencyAnalyzer;
import org.moflon.ide.core.runtime.ResourceFillingMocaToMoflonTransformation;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHook;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHookDTO;
import org.moflon.ide.core.runtime.builders.hooks.PreMetamodelBuilderHook;
import org.moflon.ide.core.runtime.builders.hooks.PreMetamodelBuilderHookDTO;
import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
import org.moflon.util.plugins.MetamodelProperties;

import MocaTree.Node;

/**
 * A metamodel builder that produces plugin projects
 */
public class MetamodelBuilder extends AbstractVisitorBuilder
{

   public static final Logger logger = Logger.getLogger(MetamodelBuilder.class);

   public MetamodelBuilder()
   {
      super(new AntPatternCondition(new String[] { ".temp/*.moca.xmi" }));
   }

   public void clean(final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Cleaning " + getProject(), 2);

      deleteProblemMarkers();
      subMon.worked(1);

      final IFolder tempFolder = getProject().getFolder(WorkspaceHelper.TEMP_FOLDER);
      final IFile mocaFile = tempFolder.getFile(getProject().getName() + WorkspaceHelper.MOCA_XMI_FILE_EXTENSION);
      if (mocaFile.isAccessible())
      {
         final URI workspaceURI = URI.createPlatformResourceURI("/", true);
         final URI projectURI = URI.createURI(getProject().getName() + "/", true).resolve(workspaceURI);

         final ResourceSet set = CodeGeneratorPlugin.createDefaultResourceSet();
         final URI mocaFileURI = URI.createURI(mocaFile.getProjectRelativePath().toString(), true).resolve(projectURI);
         final Resource mocaTreeResource = set.getResource(mocaFileURI, true);
         final Node mocaTree = (Node) mocaTreeResource.getContents().get(0);

         final CleanMocaToMoflonTransformation transformation = new CleanMocaToMoflonTransformation(set, this, getProject());
         transformation.mocaToEcore(mocaTree);

         mocaFile.touch(subMon.split(1));
      }
      subMon.setWorkRemaining(0);
   }

   @Override
   protected void processResource(IResource mocaFile, int kind, Map<String, String> args, IProgressMonitor monitor)
   {
      final MultiStatus mocaToMoflonStatus = new MultiStatus(WorkspaceHelper.getPluginId(getClass()), 0, getClass().getName() + " failed", null);

      final String mocaFilePath = WorkspaceHelper.TEMP_FOLDER + "/" + getProject().getName() + WorkspaceHelper.MOCA_XMI_FILE_EXTENSION;
      if (mocaFile instanceof IFile && mocaFilePath.equals(mocaFile.getProjectRelativePath().toString()) && mocaFile.isAccessible())
      {
         logger.debug("Start processing .temp folder");
         final SubMonitor subMon = SubMonitor.convert(monitor, "Building " + getProject().getName(), 21);

         try
         {
            deleteProblemMarkers();
            
            MoflonProjectCreator.addGitignoreFileForMetamodelProject(getProject(), subMon.split(1));

            final URI workspaceURI = URI.createPlatformResourceURI("/", true);
            final URI projectURI = URI.createURI(getProject().getName() + "/", true).resolve(workspaceURI);

            // Create and initialize resource set
            final ResourceSet set = CodeGeneratorPlugin.createDefaultResourceSet();
            // eMoflonEMFUtil.installCrossReferencers(set);

            // Load Moca tree in read-only mode
            final URI mocaFileURI = URI.createURI(mocaFilePath, true).resolve(projectURI);
            final Resource mocaTreeResource = set.getResource(mocaFileURI, true);
            final Node mocaTree = (Node) mocaTreeResource.getContents().get(0);

            final MocaTreeEAPropertiesReader mocaTreeReader = new MocaTreeEAPropertiesReader();
            final Map<String, MetamodelProperties> properties = mocaTreeReader.getProperties(getProject());

            createInfoFile(properties, mocaTree);
            callPreBuildHooks(properties, mocaTreeReader);

            // Create and run exporter on Moca tree
            final SubMonitor exporterSubMonitor = SubMonitor.convert(subMon.split(10), "Running MOCA-to-eMoflon transformation", properties.keySet().size());
            final ResourceFillingMocaToMoflonTransformation exporter = new ResourceFillingMocaToMoflonTransformation(set, this, getProject(), properties,
                  exporterSubMonitor);
            try
            {
               exporter.mocaToEcore(mocaTree);
            } catch (final OperationCanceledException e)
            {
               throw e;
            } catch (final Exception e)
            {
               throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Exception during export: " + e.toString(), e));
            } 

            for (final ErrorMessage message : exporter.getMocaToMoflonReport().getErrorMessages())
            {
               mocaToMoflonStatus.add(ValidationStatus.createValidationStatus(message));
            }

            if (exporter.getEpackages().isEmpty())
            {
               final String errorMessage = "Unable to transform exported files to Ecore models";
               CoreActivator.createProblemMarker(mocaFile, errorMessage, IMarker.SEVERITY_ERROR, mocaFile.getProjectRelativePath().toString());
               logger.error(errorMessage);
               return;
            }

            // Remove mocaTreeResource
            set.getResources().remove(mocaTreeResource);

            // Enforce resource change notifications to update workspace plugin information
            ResourcesPlugin.getWorkspace().checkpoint(false);

            // Load resources (metamodels and tgg files)
            triggerProjects.clear();
            ITask[] taskArray = new ITask[exporter.getMetamodelLoaderTasks().size()];
            taskArray = exporter.getMetamodelLoaderTasks().toArray(taskArray);
            final IStatus metamodelLoaderStatus = ProgressMonitoringJob.executeSyncSubTasks(taskArray,
                  new MultiStatus(WorkspaceHelper.getPluginId(getClass()), 0, "Resource loading failed", null), subMon.split(5));
            CoreActivator.checkCancellation(subMon);
            if (!metamodelLoaderStatus.isOK())
            {
               if (kind == IncrementalProjectBuilder.FULL_BUILD && !triggerProjects.isEmpty())
               {
                  needRebuild();
               }
               processProblemStatus(metamodelLoaderStatus, mocaFile);
               return;
            }

            // Analyze project dependencies
            ProjectDependencyAnalyzer[] dependencyAnalyzers = new ProjectDependencyAnalyzer[exporter.getProjectDependencyAnalyzerTasks().size()];
            dependencyAnalyzers = exporter.getProjectDependencyAnalyzerTasks().toArray(dependencyAnalyzers);
            for (ProjectDependencyAnalyzer analyzer : dependencyAnalyzers)
            {
               analyzer.setInterestingProjects(triggerProjects);
            }
            triggerProjects.clear();
            final IStatus projectDependencyAnalyzerStatus = ProgressMonitoringJob.executeSyncSubTasks(dependencyAnalyzers,
                  new MultiStatus(WorkspaceHelper.getPluginId(getClass()), 0, "Dependency analysis failed", null), subMon.split(5));
            CoreActivator.checkCancellation(subMon);
            if (!projectDependencyAnalyzerStatus.isOK())
            {
               processProblemStatus(projectDependencyAnalyzerStatus, mocaFile);
               return;
            }

            // Prepare save options
            Map<Object, Object> saveOnlyIfChangedOption = new HashMap<Object, Object>();
            saveOnlyIfChangedOption.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
            saveOnlyIfChangedOption.put(SDMEnhancedEcoreResource.SAVE_GENERATED_PACKAGE_CROSSREF_URIS, true);

            // Persist resources (metamodels, tgg files and moflon.properties files)
            for (Resource resource : set.getResources())
            {
               try
               {
                  resource.save(saveOnlyIfChangedOption);
               } catch (IOException e)
               {
                  logger.debug(e.getMessage(), e);
               }
            }

            callPostBuildHooks(mocaToMoflonStatus, mocaTreeReader, exporter);

            handleErrorsInEclipse(mocaToMoflonStatus);
         } catch (CoreException e)
         {
            LogUtils.error(logger, e, "Unable to update created projects.");
         } 
      }
   }

   @Override
   protected final AntPatternCondition getTriggerCondition(final IProject project)
   {
      try
      {
         if (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) || project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID))
         {
            return new AntPatternCondition(new String[] { "model/*.ecore" });
         }
      } catch (final CoreException e)
      {
         // Do nothing
      }
      return new AntPatternCondition(new String[0]);
   }

   /**
    * Creates the file projectInformation.txt in the .temp folder.
    */
   private void createInfoFile(final Map<String, MetamodelProperties> properties, final Node mocaTree)
   {
      IProject metamodelProject = getProject();
      IFile file = metamodelProject.getFile(WorkspaceHelper.TEMP_FOLDER + "/projectInformation.txt");
      StringBuilder sb = new StringBuilder();
      ArrayList<String> projectNames = new ArrayList<>(properties.keySet());
      Collections.sort(projectNames);
      for (final String projectName : projectNames)
      {
         final MetamodelProperties metamodelProperties = properties.get(projectName);
         final String projectType = metamodelProperties.getType().substring(0, 1);
         final String isExported = Boolean.toString(metamodelProperties.isExported());
         sb.append(String.format("%s [type=%s, exported=%s, nsUri=%s]\n", projectName, projectType, isExported, metamodelProperties.getNsUri()));
         List<String> dependencies = new ArrayList<>(metamodelProperties.getDependencies());
         Collections.sort(dependencies);
         int d = 0;
         for (final String dependency : dependencies)
         {
            sb.append(String.format("\t\tdependency%d=%s\n", d, dependency));
            d++;
         }
      }

      try
      {
         final ByteArrayInputStream source = new ByteArrayInputStream(sb.toString().getBytes());
         if (!file.exists())
         {
            file.create(source, true, new NullProgressMonitor());
         } else
         {
            file.setContents(source, true, false, new NullProgressMonitor());
         }
      } catch (final CoreException e)
      {
         logger.warn("Failed to create project info file " + file, e);
      }
   }

   /**
    * This method delegates to the registered extensions of the "Pre-MetamodelBuilder" extension points
    */
   private final void callPreBuildHooks(final Map<String, MetamodelProperties> properties, final MocaTreeEAPropertiesReader mocaTreeReader)
   {
      final IConfigurationElement[] extensions = Platform.getExtensionRegistry().getConfigurationElementsFor(PreMetamodelBuilderHook.PRE_BUILD_EXTENSION_ID);
      for (final IConfigurationElement extension : extensions)
      {
         try
         {
            PreMetamodelBuilderHook metamodelBuilderHook = (PreMetamodelBuilderHook) extension.createExecutableExtension("class");
            metamodelBuilderHook.run(new PreMetamodelBuilderHookDTO(mocaTreeReader, getProject()));
         } catch (final CoreException e)
         {
            logger.error("Problem during pre-build hook: " + e.getMessage());
         }
      }
   }

   /**
    * This method delegates to the registered extensions of the "Post-MetamodelBuilder" extension points
    */
   private final void callPostBuildHooks(final IStatus mocaToMoflonStatus, final MocaTreeEAPropertiesReader mocaTreeReader, final Exporter exporter)
   {
      final IConfigurationElement[] extensions = Platform.getExtensionRegistry().getConfigurationElementsFor(PostMetamodelBuilderHook.POST_BUILD_EXTENSION_ID);
      for (final IConfigurationElement extension : extensions)
      {
         try
         {
            PostMetamodelBuilderHook metamodelBuilderHook = (PostMetamodelBuilderHook) extension.createExecutableExtension("class");
            metamodelBuilderHook.run(new PostMetamodelBuilderHookDTO(mocaToMoflonStatus, mocaTreeReader, exporter, getProject()));
         } catch (final CoreException e)
         {
            logger.error("Problem during post-build hook: " + e.getMessage());
         }
      }
   }

   private void handleErrorsInEclipse(final IStatus validationStatus)
   {
      IProject metamodelProject = getProject();
      IFile eapFile = metamodelProject.getFile(metamodelProject.getName() + ".eap");

      if (eapFile.exists())
      {
         ErrorReporter eclipseErrorReporter = (ErrorReporter) Platform.getAdapterManager().loadAdapter(eapFile,
               "org.moflon.compiler.sdm.democles.eclipse.EclipseErrorReporter");
         if (eclipseErrorReporter != null)
         {
            try
            {
               eapFile.deleteMarkers(WorkspaceHelper.MOFLON_PROBLEM_MARKER_ID, true, IResource.DEPTH_INFINITE);
            } catch (CoreException e)
            {
               LogUtils.error(logger, e);
            }
            if (!validationStatus.isOK())
            {
               eclipseErrorReporter.report(validationStatus);
            }
         }
      }
   }
}

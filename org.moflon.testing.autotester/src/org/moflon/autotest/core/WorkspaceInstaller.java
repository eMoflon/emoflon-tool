package org.moflon.autotest.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.JavaModelManager;
import org.eclipse.team.internal.ui.wizards.ImportProjectSetOperation;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.gervarro.eclipse.workspace.util.WorkspaceTaskJob;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.ProgressMonitorUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.tasks.ProjectBuilderTask;
import org.moflon.ide.core.tasks.TaskUtilities;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;
import org.moflon.ide.workspaceinstaller.psf.PsfFileUtils;

@SuppressWarnings("restriction")
public class WorkspaceInstaller
{
   private static final String EMOFLON_WORKSPACE_NAME_PREFIX = "eMoflon ";

   private static final String JUNIT_TEST_LAUNCHER_FILE_NAME_PATTERN = "^.*[Test|TestSuite].*[.]launch$";

   private static final Logger logger = Logger.getLogger(WorkspaceInstaller.class);

   private static final String MASTER_BRANCH = "master";

   private static final class ExploreEMoflonProjectsJob extends Job
   {
      private final List<Job> jobs;

      private final String label;

      private ExploreEMoflonProjectsJob(String name, List<Job> jobs, String label)
      {
         super(name);
         this.jobs = jobs;
         this.label = label;
      }

      @Override
      protected final IStatus run(final IProgressMonitor monitor)
      {
         final IProject[] moflonProjects = CoreActivator.getRepositoryAndIntegrationProjects(ResourcesPlugin.getWorkspace().getRoot().getProjects());
         final IProject[] graphicalMoflonProjects = CoreActivator.getProjectsWithGraphicalSyntax(moflonProjects);
         final List<IProject> mweProjects = getProjectsWithMweWorkflows();
         if (mweProjects.size() > 0)
         {
            try
            {
               final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
               final ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.emf.mwe2.launch.Mwe2LaunchConfigurationType");
               final ILaunchConfiguration[] configurations = manager.getLaunchConfigurations(type);
               if (configurations.length > 0)
               {
                  // (1) Closing projects with textual syntax (without workspace lock)
                  jobs.add(new CloseProjectsJob("Closing projects", mweProjects));
                  // (2) Building projects with graphical syntax (with workspace lock)
                  prepareIncrementalProjectBuilderJob(jobs, graphicalMoflonProjects);
                  // (3) Launching MWE2 workflows to generate Xtext metamodels (without workspace lock)
                  final Job mweWorkflowLauncher = new LaunchMwe2WorkflowsJob("Launching MWE2 workflows", configurations);
                  mweWorkflowLauncher.setRule(ResourcesPlugin.getWorkspace().getRoot());
                  jobs.add(mweWorkflowLauncher);

                  // (4) Opening projects with textual syntax (without workspace lock)
                  jobs.add(new OpenProjectsJob("Opening projects", mweProjects));
                  // (5) Building projects with textual syntax (with workspace lock)
                  prepareIncrementalProjectBuilderJob(jobs, mweProjects);
               } else
               {
                  // Building projects (with workspace lock)
                  prepareIncrementalProjectBuilderJob(jobs, graphicalMoflonProjects);
                  prepareIncrementalProjectBuilderJob(jobs, mweProjects);
               }
            } catch (final CoreException e)
            {
               // Building projects with graphical syntax (with workspace lock)
               prepareIncrementalProjectBuilderJob(jobs, graphicalMoflonProjects);
            }
         } else
         {
            // Building projects with graphical syntax (with workspace lock)
            prepareIncrementalProjectBuilderJob(jobs, graphicalMoflonProjects);
         }

         if (isRunningJUnitTestsRequired(label))
         {
            enqueueJUnitTestJob(jobs);
         }
         return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), "eMoflon projects successfully explored");
      }

      /**
       * Searches for all projects that contain JUnit test configurations and enqueues a job that invokes all of these configurations into the given list of jobs
       * @param jobs the job list
       */
      private void enqueueJUnitTestJob(final List<Job> jobs)
      {
         final List<IFile> launchConfigurations = new LinkedList<IFile>();
         for (final IProject testProjectCandidate : getAllOpenProjectsInWorkspace())
         {
            try
            {
               final List<IFile> selectedLaunchConfigurations = Arrays.asList(testProjectCandidate.members()).stream()//
                     .filter(m -> m instanceof IFile) //
                     .map(m -> (IFile) m.getAdapter(IFile.class))//
                     .filter(f -> f.getName().matches(JUNIT_TEST_LAUNCHER_FILE_NAME_PATTERN))//
                     .collect(Collectors.toList());
               launchConfigurations.addAll(selectedLaunchConfigurations);
            } catch (final CoreException e)
            {
               LogUtils.error(logger, e);
            }
         }
         if (!launchConfigurations.isEmpty())
         {
            final Job testConfigurationJob = new Job("Launching test configurations") {

               @Override
               protected IStatus run(final IProgressMonitor monitor)
               {
                  final MultiStatus result = new MultiStatus(WorkspaceHelper.getPluginId(getClass()), IStatus.OK, "Test configurations executed succesfully",
                        null);
                  final ILaunchManager mgr = DebugPlugin.getDefault().getLaunchManager();
                  final SubMonitor subMonitor = SubMonitor.convert(monitor, launchConfigurations.size());
                  for (final IFile file : launchConfigurations)
                  {
                     final ILaunchConfiguration config = mgr.getLaunchConfiguration(file);
                     final LaunchInvocationTask launchInvocationTask = new LaunchInvocationTask(config);
                     result.add(launchInvocationTask.run(subMonitor.split(1)));
                     ProgressMonitorUtil.checkCancellation(subMonitor);
                  }
                  return result;
               }
            };
            testConfigurationJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
            jobs.add(testConfigurationJob);
         }
      }

      private List<IProject> getAllOpenProjectsInWorkspace()
      {
         return WorkspaceHelper.getAllProjectsInWorkspace().stream().filter(p -> p.isAccessible()).collect(Collectors.toList());
      }

      private List<IProject> getProjectsWithMweWorkflows()
      {
         return getAllOpenProjectsInWorkspace().stream().filter(p -> containsMwe2Files(p)).collect(Collectors.toList());
      }

      private boolean containsMwe2Files(final IProject project)
      {
         final List<IResource> mwe2Resources = new ArrayList<>();
         try
         {
            project.accept(new IResourceVisitor() {

               @Override
               public boolean visit(IResource resource) throws CoreException
               {
                  // Quit after first identified resource
                  if (!mwe2Resources.isEmpty())
                     return false;

                  if (resource.getName().endsWith("." + WorkspaceHelper.MWE2_FILE_EXTENSION))
                     mwe2Resources.add(resource);

                  return true;
               }
            });
            return !mwe2Resources.isEmpty();
         } catch (final CoreException e)
         {
            return false;
         }
      }

      private final void prepareIncrementalProjectBuilderJob(final List<Job> jobs, final Collection<IProject> projects)
      {
         projects.toArray(new IProject[projects.size()]);
      }

      private final void prepareIncrementalProjectBuilderJob(final List<Job> jobs, final IProject[] projects)
      {
         final IBuildConfiguration[] buildConfigurations = getDefaultBuildConfigurations(projects);
         if (buildConfigurations.length > 0)
         {
            final ProjectBuilderTask builder = new ProjectBuilderTask(buildConfigurations);
            jobs.add(new WorkspaceTaskJob(builder));
         }
      }

      /**
       * Returns true if the JUnit tests in the workspace shall be executed, based on the provided displayed name
       */
      private boolean isRunningJUnitTestsRequired(String displayName)
      {
         // When installing the 'handbook' workspace, only latex sources are downloaded.
         if (EMoflonStandardWorkspaces.MODULE_DOCUMENTATION.equals(displayName))
            return false;

         return true;
      }
   }

   private static final class CloseProjectsJob extends Job
   {
      private final List<IProject> mweProjects;

      private CloseProjectsJob(String name, List<IProject> mweProjects)
      {
         super(name);
         this.mweProjects = mweProjects;
      }

      @Override
      protected IStatus run(IProgressMonitor monitor)
      {
         final SubMonitor closingMonitor = SubMonitor.convert(monitor, "Closing projects", mweProjects.size());
         try
         {
            for (final IProject mweProject : mweProjects)
            {
               mweProject.close(closingMonitor.split(1));
               ProgressMonitorUtil.checkCancellation(closingMonitor);
            }
         } catch (final CoreException e)
         {
            return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), e.getMessage(), e);
         } finally
         {
            SubMonitor.done(monitor);
         }
         return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), "eMoflon projects with textual syntax have been successfully closed");
      }
   }

   private static final class LaunchMwe2WorkflowsJob extends Job
   {
      private final ILaunchConfiguration[] configurations;

      private LaunchMwe2WorkflowsJob(String name, ILaunchConfiguration[] configurations)
      {
         super(name);
         this.configurations = configurations;
      }

      @Override
      public IStatus run(final IProgressMonitor monitor)
      {
         final SubMonitor mweWorkflowExecutionMonitor = SubMonitor.convert(monitor, "Executing MWE2 workflows", configurations.length);
         try
         {
            for (int i = 0; i < configurations.length; i++)
            {
               final ILaunchConfiguration config = configurations[i];
               final LaunchInvocationTask launchInvocationTask = new LaunchInvocationTask(config);
               launchInvocationTask.run(mweWorkflowExecutionMonitor.split(1));
               ProgressMonitorUtil.checkCancellation(mweWorkflowExecutionMonitor);
            }
         } finally
         {
            SubMonitor.done(monitor);
         }
         return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), "MWE2 workflows have been successfully executed");
      }
   }

   private static final class OpenProjectsJob extends Job
   {
      private final List<IProject> mweProjects;

      private OpenProjectsJob(String name, List<IProject> mweProjects)
      {
         super(name);
         this.mweProjects = mweProjects;
      }

      @Override
      protected IStatus run(IProgressMonitor monitor)
      {
         final SubMonitor openingMonitor = SubMonitor.convert(monitor, "Opening projects", mweProjects.size());
         try
         {
            for (final IProject mweProject : mweProjects)
            {
               mweProject.open(openingMonitor.split(1));
               ProgressMonitorUtil.checkCancellation(openingMonitor);
            }
         } catch (final CoreException e)
         {
            return new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), e.getMessage(), e);
         } finally
         {
            SubMonitor.done(monitor);
         }
         return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), "eMoflon projects with textual syntax have been successfully opened");
      }
   }

   public void installWorkspaceByName(final String workspaceName)
   {
      final List<String> path = EMoflonStandardWorkspaces.getPathToPsfFileForWorkspace(workspaceName);
      final String branchName = EMoflonStandardWorkspaces.extractCustomBranchName(workspaceName);
      if (!path.isEmpty())
      {
         this.installPluginRelativePsfFiles(path, workspaceName, branchName);
      } else
      {
         logger.debug("Not a recognized workspace: " + workspaceName);
      }
   }

   public void installPsfFiles(final List<File> psfFiles)
   {
      final String label = joinBasenames(psfFiles);
      installPsfFiles(psfFiles, label);
   }

   private void installPluginRelativePsfFiles(final Collection<String> pluginRelativePsfFiles, final String label, final String branchName)
   {
      prepareWorkspace();

      installPsfFiles(mapToAbsoluteFiles(pluginRelativePsfFiles), label, branchName);
   }

   private static String mapToAbsolutePath(final String pluginRelativePathToPSF)
   {
      final String pluginIdOfPsfFilesProject = getPluginIdOfPsfFilesProject();
      final IProject workspaceProject = WorkspaceHelper.getProjectByPluginId(pluginIdOfPsfFilesProject);
      if (workspaceProject != null)
      {
         logger.debug("Using PSF files from workspace project with plugin ID " + pluginIdOfPsfFilesProject + ".");
         final File fullPath = new File(workspaceProject.getLocation().toOSString(), pluginRelativePathToPSF);
         return fullPath.getAbsolutePath();
      } else
      {
         logger.debug("Using PSF files in installed plugin " + pluginIdOfPsfFilesProject + ".");
         final URL fullPathURL = MoflonUtilitiesActivator.getPathRelToPlugIn(pluginRelativePathToPSF, pluginIdOfPsfFilesProject);
         logger.debug("Retrieved URL: " + fullPathURL);
         final String absolutePathToPSF = new File(fullPathURL.getPath()).getAbsolutePath();
         return absolutePathToPSF;
      }
   }

   private static String getPluginIdOfPsfFilesProject()
   {
      return WorkspaceHelper.getPluginId(EMoflonStandardWorkspaces.class);
   }

   public void installPsfFiles(final List<File> psfFiles, final String label)
   {
      installPsfFiles(psfFiles, label, MASTER_BRANCH);
   }

   public void installPsfFiles(final List<File> psfFiles, final String label, final String customBranch)
   {
      try
      {
         // We extract the contents beforehand because the following action may delete them if we load PSF files
         // directly from the workspace
         final String psfContent;
         if (customBranch == null || MASTER_BRANCH.equals(customBranch))
         {
            psfContent = PsfFileUtils.joinPsfFile(psfFiles);
         } else
         {
            psfContent = PsfFileUtils.joinPsfFile(psfFiles).replaceAll(Pattern.quote("," + MASTER_BRANCH + ","), "," + customBranch + ",");
         }

         final int numberOfProjects = StringUtils.countMatches(psfContent, "<project ");
         final int numberOfWorkingSets = StringUtils.countMatches(psfContent, "<workingSets ");
         final String joinedPaths = joinBasenames(psfFiles);
         logger.info(String.format("Checking out %d projects and %d working sets in %s.", numberOfProjects, numberOfWorkingSets, joinedPaths));

         final ImportProjectSetOperation importProjectSetOperation = new ImportProjectSetOperation(null, psfContent,
               psfFiles.size() > 1 ? null : psfFiles.get(0).getAbsolutePath(), new IWorkingSet[0]) {

            @Override
            public String getJobName()
            {
               return "Checking out projects";
            }

            @Override
            public ISchedulingRule getSchedulingRule()
            {
               return ResourcesPlugin.getWorkspace().getRoot();
            }

            @Override
            public void done(final IJobChangeEvent event)
            {
               if (event.getResult().isOK())
               {
                  final WorkspaceTask buildConfiguratorTask = new WorkspaceTask() {

                     @Override
                     public void run(IProgressMonitor monitor) throws CoreException
                     {
                        performBuildAndTest(label);
                     }

                     @Override
                     public String getTaskName()
                     {
                        return "Configuring build and test process";
                     }

                     @Override
                     public ISchedulingRule getRule()
                     {
                        return ResourcesPlugin.getWorkspace().getRoot();
                     }
                  };
                  new WorkspaceTaskJob(buildConfiguratorTask).schedule();
               }
            }

         };
         importProjectSetOperation.run();
      } catch (final InterruptedException e)
      {
         // Operation cancelled by the user on the GUI
         throw new OperationCanceledException();
      } catch (final InvocationTargetException | IOException e)
      {
         LogUtils.error(logger, e);
         return;
      } catch (final CoreException e)
      {
         final String message = "Sorry, I was unable to check out the projects in the PSF file.\n"//
               + "  If you did not explicitly cancel then please check the following (most probable first):\n"//
               + "      (1) Git: Ensure that the Git repositories appearing in the error message below are clean or do not exist (Window/Perspective/Open Perspective/Other.../Git)\n" //
               + "      (2) If possible, start with an empty, fresh workspace. Although the PSF import offers to delete the projects this may fail, especially on Windows.\n"//
               + "      (3) Are you sure you have access to all the projects (if they do not support anonymous access)?\n"//
               + "      (4) The PSF file might be outdated - please check for an update of the test plugin\n"//
               + "      (5) If it's quite late in the night, our server might be down performing a back-up - try again in a few hours.\n"//
               + "      (6) If none of these helped, write us a mail to contact@emoflon.org :)\n" //
               + "\n" //
               + "Exception of type " + e.getClass().getName() + ", Message: " + MoflonUtil.displayExceptionAsString(e);
         logger.error(message);
         return;
      }
   }

   private final void performBuildAndTest(final String label)
   {
      final List<Job> jobs = new ArrayList<Job>();
      if (exportingEapFilesRequired(label))
      {
         final IProject[] metamodelProjects = CoreActivator.getMetamodelProjects(ResourcesPlugin.getWorkspace().getRoot().getProjects());
         if (metamodelProjects.length > 0)
         {
            final EnterpriseArchitectModelExporterTask eaModelExporter = new EnterpriseArchitectModelExporterTask(metamodelProjects, false);
            jobs.add(new WorkspaceTaskJob(eaModelExporter));
         }
         final IBuildConfiguration[] buildConfigurations = getDefaultBuildConfigurations(metamodelProjects);
         if (buildConfigurations.length > 0)
         {
            final ProjectBuilderTask metamodelBuilder = new ProjectBuilderTask(buildConfigurations);
            jobs.add(new WorkspaceTaskJob(metamodelBuilder));
         }
      }
      final Job moflonProjectExplorerJob = new ExploreEMoflonProjectsJob("Exploring eMoflon projects", jobs, label);
      moflonProjectExplorerJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
      jobs.add(moflonProjectExplorerJob);
      jobs.add(new Job("Good bye.") {

         @Override
         protected IStatus run(IProgressMonitor monitor)
         {
            logger.info("Code generation jobs completed successfully.");
            return Status.OK_STATUS;
         }

      });

      try
      {
         TaskUtilities.processJobQueueInBackground(jobs);
         logger.info("Code generation jobs scheduled.");
      } catch (final CoreException e)
      {
         LogUtils.error(logger, e);
      }
      logger.info("End of automatic workspace configuration reached. Please wait for the code generation jobs to finish. Bye bye.");
   }

   private static final IBuildConfiguration[] getDefaultBuildConfigurations(final IProject[] projects)
   {
      return CoreActivator.getDefaultBuildConfigurations(Arrays.asList(projects));
   }

   public Collection<IProject> collectTestProjects()
   {
      return Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects()).stream().filter(project -> isTestProjectAccordingToConvention(project))
            .collect(Collectors.toList());
   }

   /**
    * Returns true if EAP files shall be exported, based on the provided displayed name
    */
   private boolean exportingEapFilesRequired(String displayName)
   {
      // When installing the 'handbook' workspace, only latex sources are downloaded.
      if (EMoflonStandardWorkspaces.MODULE_DOCUMENTATION.equals(displayName))
         return false;

      return true;
   }

   // This is required to avoid NPEs when checking out plugin projects (a problem with JDT)
   private static void prepareWorkspace()
   {
      try
      {
         JavaModelManager.getExternalManager().createExternalFoldersProject(new NullProgressMonitor());
      } catch (final CoreException e)
      {
         LogUtils.error(logger, e);
      }

   }

   private static boolean isTestProjectAccordingToConvention(final IProject project)
   {
      try
      {
         return project.getName().toUpperCase().contains("TESTSUITE") && project.getNature(JavaCore.NATURE_ID) != null;
      } catch (CoreException e)
      {
         return false;
      }
   }

   /**
    * This method removes all working sets containing no elements whose name starts with "org.moflon"
    */
   public static void removeEmptyMoflonWorkingSets()
   {
      IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
      for (IWorkingSet ws : workingSetManager.getAllWorkingSets())
      {
         if (ws.getName().startsWith(EMOFLON_WORKSPACE_NAME_PREFIX) && ws.getElements().length == 0)
         {
            workingSetManager.removeWorkingSet(ws);
         }
      }
   }

   private static List<File> mapToAbsoluteFiles(final Collection<String> pluginRelativePsfFiles)
   {
      return pluginRelativePsfFiles.stream().filter(s -> s != null).map(WorkspaceInstaller::mapToAbsolutePath).map(s -> new File(s))
            .collect(Collectors.toList());
   }

   private static String joinBasenames(final List<File> files)
   {
      return files.stream().map(f -> f.getName()).collect(Collectors.joining(", "));
   }
}

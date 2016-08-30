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
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
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
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.NatureMigrator;
import org.moflon.ide.core.runtime.ProjectNatureAndBuilderConfiguratorTask;
import org.moflon.ide.core.tasks.ProjectBuilderTask;
import org.moflon.ide.core.tasks.TaskUtilities;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;
import org.moflon.ide.workspaceinstaller.psf.PSFPlugin;
import org.moflon.ide.workspaceinstaller.psf.PsfFileUtils;
import org.osgi.framework.FrameworkUtil;

@SuppressWarnings("restriction")
public class WorkspaceInstaller
{
   private static final String JUNIT_TEST_LAUNCHER_FILE_NAME_PATTERN = "^.*[Test|TestSuite].*[.]launch$";

   private static final Logger logger = Logger.getLogger(WorkspaceInstaller.class);

   public void installWorkspaceByName(final String workspaceName)
   {
      final List<String> path = EMoflonStandardWorkspaces.getPathToPsfFileForWorkspace(workspaceName);
      if (!path.isEmpty())
      {
         this.installPluginRelativePsfFiles(path, workspaceName);
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

   private void installPluginRelativePsfFiles(final Collection<String> pluginRelativePsfFiles, final String label)
   {
      prepareWorkspace();

      installPsfFiles(mapToAbsoluteFiles(pluginRelativePsfFiles), label);
   }

   private static String mapToAbsolutePath(final String pluginRelativePathToPSF)
   {
      final IProject workspaceProject = WorkspaceHelper.getProjectByPluginId(PSFPlugin.getDefault().getPluginId());
      if (workspaceProject != null)
      {
         logger.debug("Using PSF files from workspace project with plugin ID " + PSFPlugin.getDefault().getPluginId() + ".");
         final File fullPath = new File(workspaceProject.getLocation().toOSString(), pluginRelativePathToPSF);
         return fullPath.getAbsolutePath();
      } else
      {
         logger.debug("Using PSF files in installed plugin " + PSFPlugin.getDefault().getPluginId() + ".");
         final URL fullPathURL = MoflonUtilitiesActivator.getPathRelToPlugIn(pluginRelativePathToPSF, PSFPlugin.getDefault().getPluginId());
         logger.debug("Retrieved URL: " + fullPathURL);
         final String absolutePathToPSF = new File(fullPathURL.getPath()).getAbsolutePath();
         return absolutePathToPSF;
      }
   }

   public void installPsfFiles(final List<File> psfFiles, final String label)
   {
      try
      {
         final String joinedPaths = joinBasenames(psfFiles);

         // We extract the contents beforehand because the following action may delete them if we load PSF files
         // directly from the workspace
         final String psfContent = PsfFileUtils.joinPsfFile(psfFiles);

         final int numberOfProjects = StringUtils.countMatches(psfContent, "<project ");
         final int numberOfWorkingSets = StringUtils.countMatches(psfContent, "<workingSets ");
         logger.info(String.format("Checking out %d projects and %d working sets in %s.", numberOfProjects, numberOfWorkingSets, joinedPaths));

         final ImportProjectSetOperation importProjectSetOperation = new ImportProjectSetOperation(null, psfContent,
               psfFiles.size() > 1 ? null : psfFiles.get(0).getAbsolutePath(), new IWorkingSet[0]) {
            @Override
            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
            {
               super.run(monitor);

               // TODO@rkluge: Remove this later
               // Enforce nature migration
               final NatureMigrator natureMigrator = new NatureMigrator();
               for (final IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects())
               {
                  try
                  {
                     final ProjectNatureAndBuilderConfiguratorTask task = new ProjectNatureAndBuilderConfiguratorTask(project, false);
                     task.updateNatureIDs(natureMigrator, true);
                     task.updateBuildSpecs(natureMigrator, true);
                     WorkspaceTask.execute(task, false);
                  } catch (final CoreException e)
                  {
                     e.printStackTrace();
                  }
               }
            }

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
               + "      (1) SVN: Ensure you have switched to SVNKit (Window/Preferences/Team/SVN) or make sure JavaHL is working.\n"//
               + "      (2) Git: Ensure that the Git repositories appearing in the error message below are clean or do not exist (Window/Perspective/Open Perspective/Other.../Git)\n" //
               + "      (3) If possible, start with an empty, fresh workspace. Although the PSF import offers to delete the projects this may fail, especially on Windows.\n"//
               + "      (4) Are you sure you have access to all the projects (if they do not support anonymous access)?\n"//
               + "      (5) The PSF file might be outdated - please check for an update of the test plugin\n"//
               + "      (6) If it's quite late in the night, our server might be down performing a back-up - try again in a few hours.\n"//
               + "      (7) If none of these helped, write us a mail to contact@emoflon.org :)\n" //
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
         final IBuildConfiguration[] buildConfigurations = CoreActivator.getDefaultBuildConfigurations(metamodelProjects);
         if (buildConfigurations.length > 0)
         {
            final ProjectBuilderTask metamodelBuilder = new ProjectBuilderTask(buildConfigurations);
            jobs.add(new WorkspaceTaskJob(metamodelBuilder));
         }
      }
      final Job moflonProjectExplorerJob = new Job("Exploring eMoflon projects") {

         @Override
         protected final IStatus run(final IProgressMonitor monitor)
         {
            final IProject[] moflonProjects = CoreActivator.getRepositoryAndIntegrationProjects(ResourcesPlugin.getWorkspace().getRoot().getProjects());
            final IProject[] graphicalMoflonProjects = CoreActivator.getProjectsWithGraphicalSyntax(moflonProjects);
            final IProject[] textualMoflonProjects = CoreActivator.getProjectsWithTextualSyntax(moflonProjects);
            if (textualMoflonProjects.length > 0)
            {
               try
               {
                  final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
                  final ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.emf.mwe2.launch.Mwe2LaunchConfigurationType");
                  final ILaunchConfiguration[] configurations = manager.getLaunchConfigurations(type);
                  if (configurations.length > 0)
                  {
                     // (1) Closing projects with textual syntax (without workspace lock)
                     jobs.add(new Job("Closing projects") {

                        @Override
                        protected IStatus run(IProgressMonitor monitor)
                        {
                           final SubMonitor closingMonitor = SubMonitor.convert(monitor, "Closing projects", textualMoflonProjects.length);
                           try
                           {
                              for (int i = 0; i < textualMoflonProjects.length; i++)
                              {
                                 textualMoflonProjects[i].close(closingMonitor.newChild(1));
                                 CoreActivator.checkCancellation(closingMonitor);
                              }
                           } catch (final CoreException e)
                           {
                              return new Status(IStatus.ERROR, FrameworkUtil.getBundle(getClass()).getSymbolicName(), e.getMessage(), e);
                           } finally
                           {
                              SubMonitor.done(monitor);
                           }
                           return new Status(IStatus.OK, FrameworkUtil.getBundle(getClass()).getSymbolicName(),
                                 "eMoflon projects with textual syntax have been successfully closed");
                        }
                     });
                     // (2) Building projects with graphical syntax (with workspace lock)
                     prepareIncrementalProjectBuilderJob(jobs, graphicalMoflonProjects);
                     // (3) Launching MWE2 workflows to generate Xtext metamodels (without workspace lock)
                     final Job mweWorkflowLauncher = new Job("Launching MWE2 workflows") {

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
                                 launchInvocationTask.run(mweWorkflowExecutionMonitor.newChild(1));
                                 CoreActivator.checkCancellation(mweWorkflowExecutionMonitor);
                              }
                           } finally
                           {
                              SubMonitor.done(monitor);
                           }
                           return new Status(IStatus.OK, FrameworkUtil.getBundle(getClass()).getSymbolicName(),
                                 "MWE2 workflows have been successfully executed");
                        }
                     };
                     mweWorkflowLauncher.setRule(ResourcesPlugin.getWorkspace().getRoot());
                     jobs.add(mweWorkflowLauncher);

                     // (4) Opening projects with textual syntax (without workspace lock)
                     jobs.add(new Job("Opening projects") {

                        @Override
                        protected IStatus run(IProgressMonitor monitor)
                        {
                           final SubMonitor openingMonitor = SubMonitor.convert(monitor, "Opening projects", textualMoflonProjects.length);
                           try
                           {
                              for (int i = 0; i < textualMoflonProjects.length; i++)
                              {
                                 textualMoflonProjects[i].open(openingMonitor.newChild(1));
                                 CoreActivator.checkCancellation(openingMonitor);
                              }
                           } catch (final CoreException e)
                           {
                              return new Status(IStatus.ERROR, FrameworkUtil.getBundle(getClass()).getSymbolicName(), e.getMessage(), e);
                           } finally
                           {
                              SubMonitor.done(monitor);
                           }
                           return new Status(IStatus.OK, FrameworkUtil.getBundle(getClass()).getSymbolicName(),
                                 "eMoflon projects with textual syntax have been successfully opened");
                        }
                     });
                     // (5) Building projects with textual syntax (with workspace lock)
                     prepareIncrementalProjectBuilderJob(jobs, textualMoflonProjects);
                  } else
                  {
                     // Building projects (with workspace lock)
                     prepareIncrementalProjectBuilderJob(jobs, graphicalMoflonProjects);
                     prepareIncrementalProjectBuilderJob(jobs, textualMoflonProjects);
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
               final List<IFile> launchConfigurations = new LinkedList<IFile>();
               for (final IProject testProject : WorkspaceHelper.getAllProjectsInWorkspace())
               {
                  try
                  {
                     final List<IFile> selectedLaunchConfigurations = Arrays.asList(testProject.members()).stream()//
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
                        final MultiStatus result = new MultiStatus(FrameworkUtil.getBundle(getClass()).getSymbolicName(), IStatus.OK,
                              "Test configurations executed succesfully", null);
                        final ILaunchManager mgr = DebugPlugin.getDefault().getLaunchManager();
                        final SubMonitor subMonitor = SubMonitor.convert(monitor, launchConfigurations.size());
                        for (final IFile file : launchConfigurations)
                        {
                           final ILaunchConfiguration config = mgr.getLaunchConfiguration(file);
                           final LaunchInvocationTask launchInvocationTask = new LaunchInvocationTask(config);
                           result.add(launchInvocationTask.run(subMonitor.newChild(1)));
                           CoreActivator.checkCancellation(subMonitor);
                        }
                        return result;
                     }
                  };
                  testConfigurationJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
                  jobs.add(testConfigurationJob);
               }
            }
            return new Status(IStatus.OK, FrameworkUtil.getBundle(getClass()).getSymbolicName(), "eMoflon projects successfully explored");
         }
      };
      moflonProjectExplorerJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
      jobs.add(moflonProjectExplorerJob);

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

   private final void prepareIncrementalProjectBuilderJob(final List<Job> jobs, final IProject[] projects)
   {
      final IBuildConfiguration[] buildConfigurations = CoreActivator.getDefaultBuildConfigurations(projects);
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
         if (ws.getName().startsWith("org.moflon") && ws.getElements().length == 0)
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

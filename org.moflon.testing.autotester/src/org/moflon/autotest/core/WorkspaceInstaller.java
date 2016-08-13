package org.moflon.autotest.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.ui.DebugUIMessages;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.JavaModelManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.internal.ui.wizards.ImportProjectSetOperation;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.moflon.autotest.AutoTestActivator;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.ea.EnterpriseArchitectHelper;
import org.moflon.ide.core.util.BuilderHelper;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;
import org.moflon.ide.workspaceinstaller.psf.PSFPlugin;
import org.moflon.ide.workspaceinstaller.psf.PsfFileUtils;

@SuppressWarnings("restriction")
public class WorkspaceInstaller
{
   private static final String JUNIT_TEST_LAUNCHER_FILE_NAME_PATTERN = "^.*[Test|TestSuite].*[.]launch$";

   private static final Logger logger = Logger.getLogger(WorkspaceInstaller.class);

   private static final long TIMEOUT = 5000; // 5s

   private static String getMweLaunchJob(String name)
   {
      return MessageFormat.format(DebugUIMessages.DebugUIPlugin_25, new Object[] { name });
   }

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
      final Job job = new Job("Installing " + label + "...") {

         @Override
         protected IStatus run(final IProgressMonitor monitor)
         {
            try
            {
               logger.info("Installing " + label + "...");
               SubMonitor subMon = SubMonitor.convert(monitor, "Installing " + label, 500);
               if (BuilderHelper.turnOffAutoBuild())
               {
                  logger.info("Ok - I was able to switch off auto build ...");

                  checkOutProjectsWithPsfFiles(psfFiles, label, subMon.split(100));

                  logger.info("Invoking MWE2 workflows...");

                  Collection<Job> mweJobs = invokeMwe2Workflows(subMon.split(100));

                  if (exportingEapFilesRequired(label))
                  {
                     logger.info("Exporting EAP files...");

                     exportModelsFromEAPFilesInWorkspace(subMon.split(100));

                     logger.info("Great! All model (.ecore) files have been exported ...");
                  }

                  // Wait a bit for MWE jobs to be scheduled, then join them with a timeout
                  if (!mweJobs.isEmpty())
                  {
                     logger.info("Waiting a bit before trying to join all MWE jobs ...");
                     Thread.sleep(TIMEOUT);
                  }

                  mweJobs.forEach(j -> {
                     try
                     {
                        logger.info("Joining " + j.getName());
                        j.join(TIMEOUT, subMon.split(100));
                     } catch (Exception e)
                     {
                        LogUtils.error(logger, e);
                     }
                  });

                  logger.info("Now refreshing and turning auto build back on to invoke normal code generation (build) process ...");
                  refreshAndBuildWorkspace(subMon.split(100));

                  logger.info("Finished building workspace...");

                  if (isRunningJUnitTestsRequired(label))
                  {
                     logger.info("Running tests if any test projects according to our naming convention exist (*TestSuite*)");
                     // Without this time of waiting, a NPE is thrown
                     // when launching JUnit.
                     Thread.sleep(TIMEOUT);

                     runJUnitTests(subMon.split(100));

                     logger.info("Finished auto-test process");
                  }
                  logger.info("Workspace installation completed succesfully. Bye bye.");
               }
               return Status.OK_STATUS;
            } catch (final InterruptedException e)
            {
               return new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Failed to install workspace", e);
            } catch (final CoreException e)
            {
               final String message = "Sorry, I was unable to check out the projects in the PSF file.\n"//
                     + "  If you did not explicitly cancel then please check the following (most probable first):\n"//
                     + "      (1) SVN: Ensure you have switched to SVNKit (Window/Preferences/Team/SVN) or make sure JavaHL is working.\n"//
                     + "      (2) Git: Ensure that the Git repositories appearing in the error message below are clean or do not exist (Window/Perspective/Open Perspective/Other.../Git)\n" //
                     + "      (3) If possible, start with an empty, fresh workspace. Although the PSF import offers to delete the projects this may fail, especially on Windows.\n"//
                     + "      (4) Are you sure you have acess to all the projects (if they do not support anonymous access)?\n"//
                     + "      (5) The PSF file might be outdated - please check for an update of the test plugin\n"//
                     + "      (6) If it's quite late in the night, our server might be down performing a back-up - try again in a few hours.\n"//
                     + "      (7) If none of these helped, write us a mail to contact@emoflon.org :)\n" //
                     + "\n" //
                     + "Exception of type " + e.getClass().getName() + ", Message: " + MoflonUtil.displayExceptionAsString(e);
               logger.error(message);
               return new Status(IStatus.ERROR, AutoTestActivator.getModuleID(),
                     "Installing workspace failed. Please consult the eMoflon console for further information.", e);
            }
         }
      };
      job.setUser(true);
      job.schedule();

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

   /**
    * This method invokes all tests via launchers with an appropriate name (see
    * {@link #JUNIT_TEST_LAUNCHER_FILE_NAME_PATTERN}).
    */
   private void runJUnitTests(final IProgressMonitor monitor) throws InterruptedException, CoreException
   {
      try
      {
         final Collection<IProject> testProjects = collectTestProjects();
         monitor.beginTask("Running JUnit tests", testProjects.size());

         for (final IProject testProject : testProjects)
         {
            final List<IFile> selectedLaunchConfigurations = Arrays.asList(testProject.members()).stream()//
                  .filter(m -> m instanceof IFile) //
                  .map(m -> (IFile) m.getAdapter(IFile.class))//
                  .filter(f -> f.getName().matches(JUNIT_TEST_LAUNCHER_FILE_NAME_PATTERN))//
                  .collect(Collectors.toList());
            logger.info(
                  "Launching the following launch configurations: " + selectedLaunchConfigurations.stream().map(IFile::getName).collect(Collectors.toList()));
            selectedLaunchConfigurations.forEach(file -> {
               ILaunchManager mgr = DebugPlugin.getDefault().getLaunchManager();
               ILaunchConfiguration launchConfiguration = mgr.getLaunchConfiguration(file);
               try
               {
                  launchConfiguration.launch(ILaunchManager.RUN_MODE, new NullProgressMonitor());
               } catch (Exception e)
               {
                  logger.error(
                        "Failed to run launch configuration " + file.getName() + " in" + file.getProject().getName() + ". Reason: " + e.getMessage() + " ");
               }
            });

            monitor.worked(1);
            WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);
         }
      } finally
      {
         monitor.done();
      }
   }

   public Collection<IProject> collectTestProjects()
   {
      return Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects()).stream().filter(project -> isTestProjectAccordingToConvention(project))
            .collect(Collectors.toList());
   }

   private void checkOutProjectsWithPsfFiles(final List<File> psfFiles, String label, final IProgressMonitor monitor) throws CoreException, InterruptedException
   {
      try
      {
         final String joinedPaths = joinBasenames(psfFiles);
         final SubMonitor subMon = SubMonitor.convert(monitor, "Checking out " + joinedPaths, 1 + 20);

         // We extract the contents beforehand because the following action may delete them if we load PSF files
         // directly from the workspace
         final String psfContent = PsfFileUtils.joinPsfFile(psfFiles);

         final int numberOfProjects = StringUtils.countMatches(psfContent, "<project ");
         final int numberOfWorkingSets = StringUtils.countMatches(psfContent, "<workingSets ");
         logger.info(String.format("Checking out %d projects and %d working sets in %s.", numberOfProjects, numberOfWorkingSets, joinedPaths));

         removeProjectsIfDesired(subMon.split(1));

         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);

         final ImportProjectSetOperation op = new ImportProjectSetOperation(null, psfContent, psfFiles.size() > 1 ? null : psfFiles.get(0).getAbsolutePath(),
               new IWorkingSet[0]);
         op.run(subMon.split(20));

      } catch (final IOException | InvocationTargetException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Importing projects failed", e));
      }

   }

   /**
    * Executes relevant MWE2 workflows in the workspace
    * 
    * @see #collectMwe2Workflows()
    */
   private Collection<Job> invokeMwe2Workflows(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
         final ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.emf.mwe2.launch.Mwe2LaunchConfigurationType");
         ILaunchConfiguration[] configurations = manager.getLaunchConfigurations(type);

         Collection<Job> launchJobs = new ArrayList<>();
         for (int i = 0; i < configurations.length; i++)
            launchJobs.add(runLaunchConfiguration(configurations[i], ILaunchManager.RUN_MODE, monitor));

         launchJobs.removeIf(job -> job == null);
         return launchJobs;
      } catch (final CoreException e)
      {
         LogUtils.error(logger, e);
      } finally
      {
         monitor.done();
      }

      return Collections.<Job> emptyList();
   }

   private Job runLaunchConfiguration(ILaunchConfiguration configuration, String runMode, IProgressMonitor monitor)
   {
      DebugUITools.launch(configuration, runMode);
      final IJobManager jobManager = Job.getJobManager();
      try
      {
         Job[] allJobs = jobManager.find(null);
         for (Job job : allJobs)
         {
            if (job.getName().equals(getMweLaunchJob(configuration.getName())))
               return job;
         }
      } catch (OperationCanceledException e)
      {
         LogUtils.error(logger, e);
      }

      return null;
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

   public void removeProjectsIfDesired(final IProgressMonitor monitor)
   {
      final List<IProject> projects = WorkspaceHelper.getAllProjectsInWorkspace();
      final SubMonitor subMon = SubMonitor.convert(monitor, "Deleting projects (if desired)", projects.size());
      if (projects.size() > 0)
      {
         Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run()
            {
               if (MessageDialog.openQuestion(null, "There are projects in the workspace!", "Should I delete them for you?"))
               {
                  for (final IProject project : projects)
                  {
                     try
                     {
                        logger.info("Deleting " + project.getName() + "...");
                        project.delete(true, subMon.split(1));
                     } catch (CoreException e)
                     {
                        logger.error("Sorry - I was unable to clean up your workspace. I'll continue anyway...");
                     }
                  }
               } else
               {
                  monitor.worked(projects.size());
               }
            }
         });
      }
   }

   private void exportModelsFromEAPFilesInWorkspace(final IProgressMonitor monitor) throws CoreException, InterruptedException
   {
      final List<IProject> metamodelProjects = Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects()).stream().filter(p -> p.isOpen())
            .filter(WorkspaceHelper::isMetamodelProjectNoThrow).filter(WorkspaceInstaller::hasNoTempDirectoryOrOutdatedMocaTree).collect(Collectors.toList());
      monitor.beginTask("Exporting models from EAP files", metamodelProjects.size());

      for (IProject project : metamodelProjects)
      {
         logger.info("Exporting EAP file in project " + project.getName() + "...");

         EnterpriseArchitectHelper.exportEcoreFilesFromEAP(project, monitor);
         monitor.worked(1);
         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);
      }
      monitor.done();
   }

   private static boolean hasNoTempDirectoryOrOutdatedMocaTree(final IProject metamodelProject)
   {
      if (!metamodelProject.getFolder(WorkspaceHelper.TEMP_FOLDER).exists())
         return true;

      final IFile metamodelFile = WorkspaceHelper.getEapFileFromMetamodelProject(metamodelProject);
      if (!metamodelFile.exists())
      {
         return false;
      }

      final IFile xmiTree = WorkspaceHelper.getExportedMocaTree(metamodelProject);
      final boolean isDirty = !xmiTree.exists() || (xmiTree.exists() && xmiTree.getLocalTimeStamp() < metamodelFile.getLocalTimeStamp());
      return isDirty;
   }

   private void refreshAndBuildWorkspace(final IProgressMonitor monitor) throws InterruptedException
   {
      try
      {
         final IWorkspace ws = ResourcesPlugin.getWorkspace();
         final List<IProject> projects = Arrays.asList(ws.getRoot().getProjects());
         final int projectCount = projects.size();

         final SubMonitor subMon = SubMonitor.convert(monitor, "Refreshing workspace", 6 * projectCount);

         ws.getRoot().refreshLocal(IResource.DEPTH_INFINITE, subMon.split(projectCount));
         Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_REFRESH, subMon.split(projectCount));
         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);

         BuilderHelper.turnOnAutoBuild();
         Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, subMon.split(projectCount));
         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);

         ws.getRoot().refreshLocal(IResource.DEPTH_INFINITE, subMon.split(projectCount));
         Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_REFRESH, subMon.split(projectCount));
         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);

         // TODO@rkluge: Remove
         // BuilderHelper.generateCodeInOrder(subMon.split(projectCount), Arrays.asList(ws.getRoot().getProjects()));
         // WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);
      } catch (CoreException | OperationCanceledException e)
      {
         logger.error("I'm having problems refreshing the workspace.");
         logger.error(MoflonUtil.displayExceptionAsString(e));
      } finally
      {
         monitor.done();
      }

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

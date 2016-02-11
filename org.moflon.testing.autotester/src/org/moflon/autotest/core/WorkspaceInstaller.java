package org.moflon.autotest.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.JavaModelManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.internal.ui.wizards.ImportProjectSetOperation;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.moflon.autotest.AutoTestActivator;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.ea.EnterpriseArchitectHelper;
import org.moflon.ide.core.util.BuilderHelper;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;
import org.moflon.ide.workspaceinstaller.psf.PSFPlugin;

@SuppressWarnings("restriction")
public class WorkspaceInstaller
{
   private static final Logger logger = Logger.getLogger(WorkspaceInstaller.class);

   public void installWorkspacesByName(final List<String> workspaceNames, final String displayName)
   {
      final List<String> psfPaths = workspaceNames.stream().map(EMoflonStandardWorkspaces::getPathToPsfFileForWorkspace).collect(Collectors.toList());
      installWorkspaceWithPluginRelativePsfPath(psfPaths, displayName);
   }

   public void installWorkspaceByName(final String workspaceName)
   {
      final String path = EMoflonStandardWorkspaces.getPathToPsfFileForWorkspace(workspaceName);
      if (path != null)
      {
         this.installWorkspaceWithPluginRelativePsfPath(Arrays.asList(path), workspaceName);
      } else
      {
         logger.debug("Not a recognized workspace: " + workspaceName);
      }
   }

   public void installWorkspaceWithPSF(final String absolutePathToPSF)
   {
      installWorkspaceExternal(absolutePathToPSF, absolutePathToPSF);
   }

   public void installWorkspacesWithPSF(List<File> absolutePaths, final String displayName)
   {
      this.installWorkspacesExternal(absolutePaths.stream().map(File::getAbsolutePath).collect(Collectors.toList()), displayName);
   }

   private void installWorkspaceWithPluginRelativePsfPath(final List<String> pluginRelativePathToPSF, final String displayName)
   {
      prepareWorkspace();

      final List<String> absolutePathsToPSF = pluginRelativePathToPSF.stream().filter(s -> s != null).map(WorkspaceInstaller::mapToAbsolutePath)
            .collect(Collectors.toList());

      installWorkspacesExternal(absolutePathsToPSF, displayName);
   }

   private static String mapToAbsolutePath(final String pluginRelativePathToPSF)
   {

      final IProject workspaceProject = WorkspaceHelper.getProjectByPluginId(PSFPlugin.getDefault().getPluginId());
      if (workspaceProject != null)
      {
         logger.debug("Using project with id " + PSFPlugin.getDefault().getPluginId() + " in workspace to retrieve PSF files.");
         final File fullPath = new File(workspaceProject.getLocation().toOSString(), pluginRelativePathToPSF);
         return fullPath.getAbsolutePath();
      } else
      {
         logger.debug("Using installed plugin to retrieve PSF files.");
         final URL fullPathURL = MoflonUtilitiesActivator.getPathRelToPlugIn(pluginRelativePathToPSF, PSFPlugin.getDefault().getPluginId());
         final String absolutePathToPSF = new File(fullPathURL.getPath()).getAbsolutePath();
         return absolutePathToPSF;
      }
   }

   private void prepareWorkspace()
   {
      // This is required to avoid NPEs when checking out plugin projects (a problem with JDT)
      try
      {
         JavaModelManager.getExternalManager().createExternalFoldersProject(new NullProgressMonitor());
      } catch (final CoreException ex)
      {
         ex.printStackTrace();
      }

   }

   private void installWorkspaceExternal(final String absolutePathToPSF, final String displayName)
   {
      this.installWorkspacesExternal(Arrays.asList(absolutePathToPSF), displayName);
   }

   private void installWorkspacesExternal(final List<String> absolutePathsToPSF, final String displayName)
   {

      IRunnableWithProgress runnable = new IRunnableWithProgress() {

         @Override
         public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
         {
            logger.info("Installing " + displayName + "...");
            try
            {
               monitor.beginTask("Installing " + displayName, 400);
               if (BuilderHelper.turnOffAutoBuild())
               {
                  logger.info("Ok - I was able to switch off auto build ...");

                  checkOutProjectsWithPsfFiles(absolutePathsToPSF, WorkspaceHelper.createSubMonitor(monitor, 100));

                  logger.info("All projects have now been checked out ...");

                  exportModelsFromEAPFilesInWorkspace(WorkspaceHelper.createSubMonitor(monitor, 100));

                  logger.info("Great! All model (.ecore) files have been exported ...");

                  logger.info("Now refreshing and turning auto build back on to invoke normal code generation (build) process ...");

                  refreshAndBuildWorkspace(WorkspaceHelper.createSubMonitor(monitor, 100));

                  logger.info("Finished building workspace...  Running tests if any test projects according to our naming convention exist (*TestSuite*)");

                  // Without this time of waiting, a NPE is thrown
                  // when launching JUnit.
                  Thread.sleep(5000);

                  runJUnitTests(WorkspaceHelper.createSubMonitor(monitor, 100));

                  logger.info("Finished auto-test process - Good bye!");
               }
            } catch (CoreException e)
            {
               logger.error("Sorry, I was unable to check out the projects in the PSF file.\n"//
                     + "  If you did not explicitly cancel then please check the following (most probable first):\n"//
                     + "      (1) Ensure you have switched to SVNKit (Window/Preferences/Team/SVN) or make sure JavaHL is working.\n"//
                     + "      (2) If possible, start with a clean Workspace without any projects. Although the PSF import offers to delete the projects this does not always work, especially on Windows.\n"//
                     + "      (3) Are you sure you have acess to all the projects (if they do not support anonymous access)?\n"//
                     + "      (4) The PSF file might be outdated - please check for an update of the test plugin\n"//
                     + "      (5) If it's quite late in the night, our server might be down performing a back-up - try again in a few hours.\n"//
                     + "      (6) What nothing helped?!  Please send us an email at contact@emoflon.org :)\n" //
                     + "\n" //
                     + "Exception of type " + e.getClass().getName() + ", Message: " + MoflonUtil.displayExceptionAsString(e));
            } finally
            {
               monitor.done();
            }

         }
      };
      
      // This code blocks the UI!
      // ProgressMonitorDialog dialog = new ProgressMonitorDialog(window.getShell());
      // dialog.run(true, true, runnable);

      final WorkspaceJob job = new WorkspaceJob("Installing " + displayName + "...") {
         
         @Override
         public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException
         {
            try
            {
               runnable.run(monitor);
            } catch (InvocationTargetException | InterruptedException e)
            {
               return new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Failed to install workspace", e);
            }
            return Status.OK_STATUS;
         }
      };
      job.setUser(true);
      job.schedule();
      
      // final IMonitoredJob job = new IMonitoredJob() {
      //
      // @Override
      // public IStatus run(IProgressMonitor monitor)
      // {
      // try
      // {
      // runnable.run(monitor);
      // return Status.OK_STATUS;
      // } catch (InvocationTargetException | InterruptedException e)
      // {
      // return new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Failed to install workspace", e);
      // }
      // }
      //
      // @Override
      // public String getTaskName()
      // {
      // return "Installing " + displayName + "...";
      // }
      // };
      // ProgressMonitoringJob monitoringJob = new ProgressMonitoringJob(AutoTestActivator.getModuleID(), job);
      // monitoringJob.schedule();
   }

   private void runJUnitTests(final IProgressMonitor monitor) throws InterruptedException, CoreException
   {
      final Collection<IProject> testProjects = collectTestProjects();
      monitor.beginTask("Running JUnit tests", testProjects.size());

      for (final IProject testProject : testProjects)
      {
         List<IFile> selectedLaunchConfigurations = Arrays.asList(testProject.members()).stream().filter(m -> m instanceof IFile)
               .map(m -> (IFile) m.getAdapter(IFile.class)).filter(f -> f.getName().matches("^.*[Test|TestSuite].*[.]launch$")).collect(Collectors.toList());
         logger.info(
               "Launching the following launch configurations: " + selectedLaunchConfigurations.stream().map(f -> f.getName()).collect(Collectors.toList()));
         selectedLaunchConfigurations.forEach(file -> {
            ILaunchManager mgr = DebugPlugin.getDefault().getLaunchManager();
            ILaunchConfiguration launchConfiguration = mgr.getLaunchConfiguration(file);
            try
            {
               launchConfiguration.launch(ILaunchManager.RUN_MODE, new NullProgressMonitor());
            } catch (Exception e)
            {
               logger.error("Failed to run launch configuration " + file.getName() + " in" + file.getProject().getName() + ". Reason: " + e.getMessage() + " ");
            }
         });

         monitor.worked(1);
         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);
      }
      monitor.done();
   }

   public Collection<IProject> collectTestProjects()
   {
      return Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects()).stream().filter(project -> isTestProjectAccordingToConvention(project))
            .collect(Collectors.toList());
   }

   private void checkOutProjectsWithPsfFiles(final List<String> absolutePathsToPSF, final IProgressMonitor monitor) throws CoreException, InterruptedException
   {
      try
      {
         monitor.beginTask("Checking out projects", 20 * absolutePathsToPSF.size() + 1);

         // We extract the contents beforehand because the following action may delete them if we load PSF files
         // directly from the workspace
         final List<String> psfContents = extractPsfFileContents(absolutePathsToPSF);

         removeProjectsIfDesired(WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);

         importProjectSets(WorkspaceHelper.createSubMonitor(monitor, 20 * absolutePathsToPSF.size()), absolutePathsToPSF, psfContents);
      } catch (IOException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Importing projects failed", e));
      } finally
      {
         monitor.done();
      }

   }

   private List<String> extractPsfFileContents(final List<String> absolutePathsToPSF) throws IOException
   {
      final List<String> psfContents = new ArrayList<>();
      for (final String absolutePathToPSF : absolutePathsToPSF)
      {
         psfContents.add(FileUtils.readFileToString(new File(absolutePathToPSF)));
      }
      return psfContents;
   }

   public void removeProjectsIfDesired(final IProgressMonitor monitor)
   {
      final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      monitor.beginTask("Deleting projects (if desired)", projects.length);
      if (projects.length > 0)
      {
         Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run()
            {
               if (MessageDialog.openQuestion(null, "There are projects in the workspace!", "Should I delete them for you?"))
               {
                  for (IProject project : projects)
                  {
                     try
                     {
                        logger.info("Deleting " + project.getName() + "...");
                        project.delete(true, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
                     } catch (CoreException e)
                     {
                        logger.error("Sorry - I was unable to clean up your workspace. I'll continue anyway...");
                     }
                  }
               } else
               {
                  monitor.worked(projects.length);
               }
            }
         });
      }
      monitor.done();
   }

   private void importProjectSets(final IProgressMonitor monitor, final List<String> absolutePathsToPSF, List<String> psfContents)
         throws CoreException, InterruptedException
   {
      try
      {
         monitor.beginTask("Importing projects", absolutePathsToPSF.size());

         for (int i = 0; i < psfContents.size(); ++i)
         {
            final String absolutePathToPSFFile = absolutePathsToPSF.get(i);
            final String psfContent = psfContents.get(i);
            logger.info("Checking out projects using PSF file: " + absolutePathToPSFFile);
            final ImportProjectSetOperation op = new ImportProjectSetOperation(null, psfContent, absolutePathToPSFFile, new IWorkingSet[0]);
            op.run(WorkspaceHelper.createSubmonitorWith1Tick(monitor));
            WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);
         }
      } catch (InvocationTargetException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, AutoTestActivator.getModuleID(), "Importing projects failed", e));
      } finally
      {
         monitor.done();
      }
   }

   private void exportModelsFromEAPFilesInWorkspace(final IProgressMonitor monitor) throws CoreException, InterruptedException
   {
      final List<IProject> metamodelProjects = Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects()).stream().filter(p -> p.isOpen())
            .filter(WorkspaceHelper::isMetamodelProjectNoThrow).collect(Collectors.toList());
      monitor.beginTask("Exporting models from EAP files", metamodelProjects.size());

      for (IProject project : metamodelProjects)
      {
         logger.info("Exporting EAP file in project " + project.getName() + "...");

         EnterpriseArchitectHelper.exportEcoreFilesFromEAP(project);
         monitor.worked(1);
         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);
      }
      monitor.done();
   }

   private void refreshAndBuildWorkspace(final IProgressMonitor monitor) throws InterruptedException
   {
      try
      {
         final IWorkspace ws = ResourcesPlugin.getWorkspace();
         final List<IProject> projects = Arrays.asList(ws.getRoot().getProjects());
         final int projectCount = projects.size();

         monitor.beginTask("Refreshing workspace", 6 * projectCount);

         ws.getRoot().refreshLocal(IResource.DEPTH_INFINITE, WorkspaceHelper.createSubMonitor(monitor, projectCount));
         Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_REFRESH, WorkspaceHelper.createSubMonitor(monitor, projectCount));
         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);

         BuilderHelper.turnOnAutoBuild();
         Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, WorkspaceHelper.createSubMonitor(monitor, projectCount));
         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);

         ws.getRoot().refreshLocal(IResource.DEPTH_INFINITE, WorkspaceHelper.createSubMonitor(monitor, projectCount));
         Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_REFRESH, WorkspaceHelper.createSubMonitor(monitor, projectCount));
         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);

         BuilderHelper.generateCodeInOrder(WorkspaceHelper.createSubMonitor(monitor, projectCount), Arrays.asList(ws.getRoot().getProjects()));
         WorkspaceHelper.checkCanceledAndThrowInterruptedException(monitor);
      } catch (CoreException | OperationCanceledException e)
      {
         logger.error("I'm having problems refreshing the workspace.");
         logger.error(MoflonUtil.displayExceptionAsString(e));
      } finally
      {
         monitor.done();
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

}

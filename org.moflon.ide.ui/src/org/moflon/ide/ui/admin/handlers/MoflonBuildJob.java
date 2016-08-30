package org.moflon.ide.ui.admin.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.gervarro.eclipse.workspace.util.WorkspaceTaskJob;
import org.moflon.core.utilities.LogUtils;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.preferences.EMoflonPreferencesStorage;
import org.moflon.ide.core.tasks.ProjectBuilderTask;
import org.moflon.ide.core.tasks.TaskUtilities;
import org.moflon.ide.ui.UIActivator;
import org.moflon.ide.ui.preferences.EMoflonPreferenceInitializer;
import org.osgi.framework.FrameworkUtil;

public class MoflonBuildJob extends WorkspaceJob
{
   private static final String JOB_NAME = "eMoflon Manual Build";
   
   private static final Logger logger = Logger.getLogger(MoflonBuildJob.class);

   private final List<IProject> projects;

   private int buildType;

   /**
    * Constructor.
    * 
    * @param name the name to be handed to {@link WorkspaceJob} 
    * @param projects the projects to be built
    * @param buildType the build type (as specified in {@link IncrementalProjectBuilder})
    */
   public MoflonBuildJob(final List<IProject> projects, int buildType)
   {
      super(JOB_NAME);
      this.projects = projects;
      this.buildType = buildType;
   }

   @Override
   public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException
   {
      LogUtils.info(logger, "Manual build triggered (mode=%s, projects=%s)", CoreActivator.mapBuildKindToName(this.buildType), this.projects);
      final MultiStatus resultStatus = new MultiStatus(UIActivator.getModuleID(), 0, "eMoflon Build Job failed", null);
      final List<Job> jobs = new ArrayList<>();
      final IBuildConfiguration[] buildConfigurations = CoreActivator.getDefaultBuildConfigurations(projects);
      if (buildConfigurations.length > 0)
      {
         final ProjectBuilderTask metamodelBuilder = new ProjectBuilderTask(buildConfigurations, this.buildType);
         jobs.add(new WorkspaceTaskJob(metamodelBuilder));
      }

      try
      {
         TaskUtilities.processJobQueueAsUser(jobs);
      } catch (final CoreException e)
      {
         resultStatus.add(new Status(IStatus.ERROR, FrameworkUtil.getBundle(getClass()).getSymbolicName(), IStatus.ERROR, getName() + "failed.", e));
      }

      updateUserSelectedTimeoutForValidation();

      return resultStatus.matches(Status.ERROR) ? resultStatus : Status.OK_STATUS;
   }

   private void updateUserSelectedTimeoutForValidation()
   {
      EMoflonPreferencesStorage.getInstance().setValidationTimeout(EMoflonPreferenceInitializer.getValidationTimeoutMillis());
   }
}
package org.moflon.ide.core.util;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspace.ProjectOrder;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.codegeneration.IntegrationCodeGenerator;
import org.moflon.ide.core.runtime.codegeneration.RepositoryCodeGenerator;

public class BuilderHelper
{
   private static final Logger logger = Logger.getLogger(BuilderHelper.class);

   public static boolean turnOffAutoBuild()
   {
      try
      {
         switchAutoBuilding(false);
      } catch (CoreException e)
      {
         logger.error("Unable to turn off auto build.");
         logger.error(MoflonUtil.displayExceptionAsString(e));

         return false;
      }

      return true;
   }

   public static void turnOnAutoBuild()
   {
      try
      {
         switchAutoBuilding(true);
      } catch (CoreException e)
      {
         logger.error("Unable to turn on auto build.");
         logger.error(MoflonUtil.displayExceptionAsString(e));
      }
   }

   private static void switchAutoBuilding(final boolean isAutoBuildingOn) throws CoreException
   {
      IWorkspace workspace = ResourcesPlugin.getWorkspace();
      IWorkspaceDescription description = workspace.getDescription();

      if (isAutoBuildingOn)
      {
         if (!workspace.isAutoBuilding())
         {
            description.setAutoBuilding(true);
         }
      } else
      {
         if (workspace.isAutoBuilding())
            description.setAutoBuilding(false);
      }

      workspace.setDescription(description);
   }

   public static void generateCodeInOrder(final IProgressMonitor monitor, final Collection<IProject> projects) throws CoreException
   {
      monitor.beginTask("Generating code in order", 100 * projects.size());
      try
      {
         Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, monitor);

         ProjectOrder order = ResourcesPlugin.getWorkspace().computeProjectOrder(projects.stream().toArray(IProject[]::new));
         for (IProject project : order.projects)
         {
            if (monitor.isCanceled())
               throw new OperationCanceledException();

            RepositoryCodeGenerator codeGenerator = null;
            if (project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID))
            {
               codeGenerator = new IntegrationCodeGenerator(project);
            } else if (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID))
            {
               codeGenerator = new RepositoryCodeGenerator(project);
            }
            
            if (codeGenerator != null)
            {
               codeGenerator.generateCode(WorkspaceHelper.createSubMonitor(monitor, 100));
            }

         }
      } catch (OperationCanceledException | InterruptedException e)
      {
         e.printStackTrace();
      } finally
      {
         monitor.done();
      }
   }
}

package org.moflon.ide.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspace.ProjectOrder;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
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

   // TODO@rkluge
   /**
    * @deprecated Should be replaced by the new automatic build process (rkluge, 2016-08-11)
    */
   @Deprecated
   public static IStatus generateCodeInOrder(final IProgressMonitor monitor, final Collection<IProject> projects)
   {
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Generating code in order", 1 + 100 * projects.size());
         
         Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, subMon.newChild(1));

         final List<Status> collectedErrorStatus = new ArrayList<>();
         final ProjectOrder order = ResourcesPlugin.getWorkspace().computeProjectOrder(projects.stream().toArray(IProject[]::new));
         
         for (IProject project : order.projects)
         {
            
            try
            {
               if (subMon.isCanceled())
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
                  codeGenerator.generateCode(subMon.newChild(100));
               }
               
            } catch (final CoreException e)
            {
               collectedErrorStatus.add(new Status(IStatus.ERROR, CoreActivator.getModuleID(), "Problem during code generation of " + project.getName(), e));
            }
         }

         if (collectedErrorStatus.isEmpty())
         {
            return Status.OK_STATUS;
         }
         else {
            return new MultiStatus(CoreActivator.getModuleID(), 0, collectedErrorStatus.toArray(new IStatus[collectedErrorStatus.size()]), "Problems during code generation", null);
         }
         
      } catch (final InterruptedException e)
      {
         return new Status(IStatus.ERROR, CoreActivator.getModuleID(), "Code generation interrupted", e);
      }
   }
}

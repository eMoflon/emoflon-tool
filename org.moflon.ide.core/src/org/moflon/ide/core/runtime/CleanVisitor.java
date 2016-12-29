package org.moflon.ide.core.runtime;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.gervarro.eclipse.workspace.util.VisitorCondition;

/**
 * A pattern-based 
 * @author Gergely Varro - Initial implementation
 * @author Roland Kluge - Exclusion condition
 */
public final class CleanVisitor implements IResourceVisitor
{
   private final IProject project;

   private final VisitorCondition inclusionCondition;

   private final VisitorCondition exclusionCondition;

   public CleanVisitor(IProject project, VisitorCondition inclusionCondition)
   {
      this(project, inclusionCondition, new NullVisitorCondition());
   }

   public CleanVisitor(IProject project, VisitorCondition inclusionCondition, VisitorCondition exclusionCondition)
   {
      this.project = project;
      this.inclusionCondition = inclusionCondition;
      this.exclusionCondition = exclusionCondition;
   }

   @Override
   public boolean visit(final IResource resource)
   {
      final int resourceType = resource.getType();
      if (resourceType == IResource.PROJECT)
      {
         return resource.isAccessible() && resource.getProject() == project;
      } else if (resourceType != IResource.ROOT)
      {
         final String path = resource.getProjectRelativePath().toString();

         if (this.exclusionCondition.isExactMatch(path))
            return false;

         final boolean exactInclusionMatchFound = inclusionCondition.isExactMatch(path);
         if (exactInclusionMatchFound)
         {
            if (resource.isAccessible())
            {
               try
               {
                  resource.delete(true, new NullProgressMonitor());
               } catch (CoreException e)
               {
                  // Do nothing
               }
            }
            return false;
         } else
         {
            return inclusionCondition.isPrefixMatch(path);
         }
      }
      return false;
   }

   /**
    * Null implementation of {@link VisitorCondition} that always returns false
    */
   private static final class NullVisitorCondition implements VisitorCondition
   {
      @Override
      public boolean isPrefixMatch(String path)
      {
         return false;
      }

      @Override
      public boolean isExactMatch(String path)
      {
         return false;
      }
   }
}

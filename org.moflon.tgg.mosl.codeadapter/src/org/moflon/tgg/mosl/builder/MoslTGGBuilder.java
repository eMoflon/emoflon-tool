package org.moflon.tgg.mosl.builder;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

public class MoslTGGBuilder extends IncrementalProjectBuilder
{
   public static final String BUILDER_ID = "org.moflon.tgg.mosl.codeadapter.mosltggbuilder";

   class MoslTGGVisitor implements IResourceDeltaVisitor, IResourceVisitor
   {
      @Override
      public boolean visit(IResourceDelta delta) throws CoreException
      {
         IResource resource = delta.getResource();

         if (isMOSLFolder(resource))
         {
            switch (delta.getKind())
            {
            case IResourceDelta.ADDED:
            case IResourceDelta.CHANGED:
               new MOSLTGGConversionHelper().generateTGGModel(resource);
               return false;
            case IResourceDelta.REMOVED:
               // handle removed resource
               return false;
            }
         }
         
         if(isGeneratedEcore(resource)){
            switch (delta.getKind())
            {
            case IResourceDelta.ADDED:
               removeXtextMarkers(resource);
               return false;
            }
         }

         // return true to continue visiting children.
         return true;
      }

      private void removeXtextMarkers(IResource resource)
      {
         try
         {
            resource.deleteMarkers(org.eclipse.xtext.ui.MarkerTypes.FAST_VALIDATION, false, IResource.DEPTH_ZERO);
         } catch (CoreException e)
         {
            e.printStackTrace();
         }
      }

      private boolean isGeneratedEcore(IResource resource)
      {
         return "ecore".equals(resource.getProjectRelativePath().getFileExtension())
             && "model".equals(resource.getProjectRelativePath().segment(0));
      }

      private boolean isMOSLFolder(IResource resource)
      {
         IProject project = resource.getProject();
         IPath pathToMOSLFolder = project.getProjectRelativePath().append("/src/org/moflon/tgg/mosl");
         return resource.getProjectRelativePath().equals(pathToMOSLFolder);
      }

      @Override
      public boolean visit(IResource resource) throws CoreException
      {
         if (isMOSLFolder(resource))
         {
            new MOSLTGGConversionHelper().generateTGGModel(resource);
            return false;
         } else if(isGeneratedEcore(resource)){
        	removeXtextMarkers(resource);
        	return false;
         }

         return true;
      }
   }

   @Override
   protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException
   {
      if (kind == FULL_BUILD)
      {
         fullBuild(monitor);
      } else
      {
         IResourceDelta delta = getDelta(getProject());
         if (delta == null)
         {
            fullBuild(monitor);
         } else
         {
            incrementalBuild(delta, monitor);
         }
      }
      return null;
   }

   @Override
   protected void clean(IProgressMonitor monitor) throws CoreException
   {

   }

   protected void fullBuild(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         getProject().accept(new MoslTGGVisitor());
      } catch (CoreException e)
      {
         e.printStackTrace();
      }
   }

   protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException
   {
      // the visitor does the work.
      delta.accept(new MoslTGGVisitor());
   }
}

package org.moflon.ide.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.core.utilities.MoflonConventions;
import org.moflon.core.utilities.ProblemMarkerUtil;
import org.moflon.core.utilities.UncheckedCoreException;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.natures.IntegrationNature;
import org.moflon.ide.core.runtime.natures.MetamodelNature;
import org.moflon.ide.core.runtime.natures.RepositoryNature;
import org.osgi.framework.BundleContext;

/**
 * The Activator controls the plug-in life cycle and contains state and functionality that can be used throughout the
 * plugin. Constants used in various places in the plugin should also be defined in the Activator.
 *
 * Core (non gui) functionality that can be useful for other Moflon eclipse plugins should be implemented here.
 */
public class CoreActivator extends Plugin
{
   private static CoreActivator plugin;

   @Override
   public void start(final BundleContext context) throws Exception
   {
      super.start(context);
      plugin = this;
   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      plugin = null;
      super.stop(context);
   }

   public static CoreActivator getDefault()
   {
      return plugin;
   }

   /**
    * Used when the plugin has to store resources on the client machine and eclipse installation + current workspace.
    * This location reserved for the plugin is called the "state location" and is usually in
    * pathToWorkspace/.metadata/pluginName
    *
    * @param filename
    *           Appended to the state location. This is the name of the resource to be saved.
    * @return path to location reserved for the plugin which can be used to store resources
    */
   public IPath getPathInStateLocation(final String filename)
   {
      return getStateLocation().append(filename);
   }

   /**
    * @deprecated Use {@link ProblemMarkerUtil#createProblemMarker(IResource, String, int, String)}
    */
   @Deprecated
   public static void createProblemMarker(final IResource resource, final String message, final int severity, final String location)
   {
      try
      {
         ProblemMarkerUtil.createProblemMarker(resource, message, severity, location);
      } catch (final CoreException e)
      {
         throw new UncheckedCoreException(e);
      }
   }

   public static final void deleteMarkers(final IResource resource, final String type, final boolean includeSubtypes, final int depth)
   {
      try
      {
         resource.deleteMarkers(type, includeSubtypes, depth);
      } catch (CoreException e)
      {
         throw new UncheckedCoreException(e);
      }
   }

   /**
    * @deprecated Use {@link ProblemMarkerUtil#convertStatusSeverityToMarkerSeverity(int)} instead
    */
   public static final int convertStatusSeverityToMarkerSeverity(final int severity) throws CoreException
   {
      return ProblemMarkerUtil.convertStatusSeverityToMarkerSeverity(severity);
   }

   public static final IFile getEcoreFile(final IProject project)
   {
      final String ecoreFileName = MoflonConventions.getDefaultNameOfFileInProjectWithoutExtension(project.getName());
      return project.getFolder(WorkspaceHelper.MODEL_FOLDER).getFile(ecoreFileName + WorkspaceHelper.ECORE_FILE_EXTENSION);
   }

   public static final IProject[] getMetamodelProjects(final IProject[] projects)
   {
      final List<IProject> result = new ArrayList<IProject>(projects.length);
      for (final IProject project : projects)
      {
         if (project.isAccessible() && MetamodelNature.isMetamodelProjectNoThrow(project))
         {
            result.add(project);
         }
      }
      return result.toArray(new IProject[result.size()]);
   }

   public static final void setEPackageURI(final EPackage ePackage)
   {
      URI uri = EcoreUtil.getURI(ePackage);
      if (ePackage instanceof InternalEObject && ((InternalEObject) ePackage).eDirectResource() != null)
      {
         uri = uri.trimFragment();
      }
      ePackage.setNsURI(uri.toString());
      for (final EPackage subPackage : ePackage.getESubpackages())
      {
         setEPackageURI(subPackage);
      }
   }

   /**
    * Returns whether the given project is (1) a repository project or (2) an integration project or (3) a MOSL-GT project
    */
   public static boolean isMoflonProject(final IProject project) throws CoreException
   {
      return RepositoryNature.isRepositoryProject(project) || IntegrationNature.isIntegrationProject(project);
   }

   /**
    * Returns whether the given project is (1) a repository project or (2) an integration project.
    *
    * Returns also false if an exception would be thrown.
    */
   public static boolean isMoflonProjectNoThrow(final IProject project)
   {
      return RepositoryNature.isRepositoryProjectNoThrow(project) || IntegrationNature.isIntegrationProjectNoThrow(project);
   }
}

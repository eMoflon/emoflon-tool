package org.moflon.tutorial.helper;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.tutorial.Common;
import org.osgi.framework.Bundle;

/**
 * This class contains several methods to check or manipulate the workspace or the resources that come with this
 * plug-in. The methods were created when needed, so it might appear as a random collection of helper methods.
 * 
 * @author Matthias
 * 
 */
public class MyWorkspaceHelper
{

   /**
    * bundle for this plug-in, to get access to the resources
    */
   static Bundle bundle;

   static
   {
      if (bundle == null)
      {
         bundle = Platform.getBundle(Common.MAIN_PLUGIN_ID);
      }
   }

   /**
    * @param resource
    *           a path relative to this plug-in e.g. "resources/somefile.xy"
    * @return a URL to the specified resource
    */
   public static URL getResourceURL(final String resource)
   {
      return MoflonUtilitiesActivator.getPathRelToPlugIn(resource, Common.MAIN_PLUGIN_ID);
   }

   /**
    * Use this to get a (absolute) URI for a file in the workspace. It can easily be converted into a URL or you can
    * create a File Object with it.
    * 
    * @param fileName
    *           relative name of a file (or directory) in the workspace
    * @return a URI containing the absolute location of the given file.
    */
   public static File getWorkspaceFile(final String fileName)
   {
      IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
      File rootFile = new File(root.getLocationURI());

      return new File(rootFile, fileName);
   }

   /**
    * converts a String to IFile. The existence of the file is not checked!
    * 
    * @param fileName
    *           the relative name (with path) of a file in the workspace e.g. "project/folder/file"
    * @return IFile instance for the file
    */
   public static IFile getWorkspaceIFile(final String fileName)
   {
      String[] components = fileName.split("/");
      if (components.length < 2)
         throw new RuntimeException("Invalid Path Length! Path must have at least 2 Parts, seperated with a \"/\"");

      IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
      IProject project = root.getProject(components[0]);

      if (components.length == 2)
      {
         return project.getFile(components[1]);
      } else
      {
         IFolder folder = project.getFolder(components[1]);
         for (int i = 2; i <= components.length - 2; i++)
         {
            folder = folder.getFolder(components[i]);
         }
         return folder.getFile(components[components.length - 1]);
      }
   }

   /**
    * @param resource
    *           path of a file as string relative to this plug-in.
    * @return InputStream for the file. Use it to write into the file.
    */
   public static InputStream getResourceInputStream(final String resource)
   {
      InputStream result = null;
      try
      {
         result = getResourceURL(resource).openStream();
      } catch (IOException e)
      {
         System.err.println("ERROR while creating InputStream for resource: " + resource);
         e.printStackTrace();
      }
      return result;
   }

   /**
    * @param name
    *           name of the project
    * @return true, if a project with that name exists in the current workspace
    */
   public static boolean existsProject(final String name)
   {
      IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
      IProject project = root.getProject(name);
      return project.exists();
   }

   /**
    * Creates a project with the given name in the workspace.
    * 
    * @param deleteExisting
    *           if true and a if a project with the specified name already exists in the workspace, that project and all
    *           of its content will be deleted!
    */
   public static IProject createEmptyProject(final String name, final boolean deleteExisting)
   {
      IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
      IProject project = root.getProject(name);

      try
      {
         if (project.exists())
         {
            if (deleteExisting)
            {
               try
               {
                  project.delete(true, null);
               } catch (CoreException e)
               {
                  e.printStackTrace();
                  return null;
               }
            }
         }

         if (!project.exists())
            project.create(null);

         project.open(null);
         return project;

      } catch (CoreException e)
      {
         e.printStackTrace();
         return null;
      }

   }

   /**
    * copies a file or a directory into a specified project or folder.
    * 
    * @param from
    *           path to a resource (file or directory) relative to this plug-in.
    * @param to
    *           path of a directory relative to the current workspace (a project or a directory inside a project)
    * @return true, if the copying was successful. false if an error occurred.
    */
   public static boolean copy(final String from, final String to)
   {
      try
      {
         MoflonUtil.copyDirToDir(getResourceURL(from), getWorkspaceFile(to), new FileFilter() {
            @Override
            public boolean accept(final File pathname)
            {
               return true;
            }
         });
      } catch (IOException e1)
      {
         System.err.println("error while copying files!");
         e1.printStackTrace();
         return false;
      }

      return true;

   }

   /**
    * @param name
    *           the name of the project that will be built (if it exists - otherwise nothing will happen)
    */
   public static void buildProject(final String name)
   {
      IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
      if (project.exists())
         buildProject(project);
   }

   /**
    * @param project
    *           the project to build
    */
   public static void buildProject(final IProject project)
   {
      try
      {
         project.build(IncrementalProjectBuilder.FULL_BUILD, null);
      } catch (CoreException e)
      {
         System.err.println("Build failed!");
         ;
      }
   }

   /**
    * adds a project to the workingSet with the given Name. Only works if the working set already exists
    * 
    * @param project
    * @param workingSetName
    */
   public static void addProjectToWorkingSet(final IProject project, final String workingSetName)
   {
      IWorkingSetManager wsManager = PlatformUI.getWorkbench().getWorkingSetManager();
      IWorkingSet workingSet = wsManager.getWorkingSet(workingSetName);

      // though getWorkingSet should return null, if the workingSet does not exist, it
      // does not do so...
      if (workingSet != null)
      {
         IAdaptable[] elements = workingSet.getElements();
         IAdaptable[] newElements = new IAdaptable[elements.length + 1];
         System.arraycopy(elements, 0, newElements, 0, elements.length);
         newElements[elements.length] = project;
         workingSet.setElements(newElements);
      }
   }

}

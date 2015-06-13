/*
 * The FUJABA ToolSuite project:
 *
 *   FUJABA is the acronym for 'From Uml to Java And Back Again'
 *   and originally aims to provide an environment for round-trip
 *   engineering using UML as visual programming language. During
 *   the last years, the environment has become a base for several
 *   research activities, e.g. distributed software, database
 *   systems, modelling mechanical and electrical systems and
 *   their simulation. Thus, the environment has become a project,
 *   where this source code is part of. Further details are avail-
 *   able via http://www.fujaba.de
 *
 *      Copyright (C) Fujaba Development Group
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *   MA 02111-1307, USA or download the license under
 *   http://www.gnu.org/copyleft/lesser.html
 *
 * WARRANTY:
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 * Contact address:
 *
 *   Fujaba Management Board
 *   Software Engineering Group
 *   University of Paderborn
 *   Warburgerstr. 100
 *   D-33098 Paderborn
 *   Germany
 *
 *   URL  : http://www.fujaba.de
 *   email: info@fujaba.de
 *
 */
package de.uni_paderborn.fujaba.project;

import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.versioning.Versioning;

import java.io.*;
import java.util.zip.GZIPInputStream;


/**
 * Superclass for strategies loading FProjects.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public abstract class ProjectLoader
{
   public final static String LOAD_TASK = "Loading Project";

   /**
    * true to indicate some strategy is currently loading a project.
    */
   private static transient Thread loading;

   /**
    * Strategy for accessing plugin and class loader information during loading and saving.
    */
   private static PersistencySupport persistencySupport;


   /**
    * Get the persistencySupport attribute of the ProjectLoader class
    *
    * @return   The persistencySupport value
    */
   public static PersistencySupport getPersistencySupport()
   {
      return persistencySupport;
   }


   /**
    * Sets the persistencySupport attribute of the ProjectLoader class
    *
    * @param value  The new persistencySupport value
    */
   public static void setPersistencySupport (PersistencySupport value)
   {
      persistencySupport = value;
   }


   /**
    * True, if a project is currently loading
    *
    * @return   The loading value
    * @deprecated use ProjectManager.get().isLoading() instead, to be removed in 5.2
    */
   public static synchronized boolean isLoading()
   {
      return ProjectManager.get().isLoading();
   }


   /**
    * Sets the loading attribute of the UMLProject class
    *
    * @throws InterruptedException if thread is interrupted
    */
   private static synchronized void startLoading() throws InterruptedException
   {
      Thread currentThread = Thread.currentThread();
      while (loading != currentThread && loading != null)
      {
         ProjectLoader.class.wait(1000);
      }
      loading = currentThread;
      ProjectManager.get().setLoading(true);
   }


   private static synchronized void stopLoading()
   {
      loading = null;
      ProjectManager.get().setLoading(false);
      ProjectLoader.class.notify();
   }


   /**
    * Load a project from file. (Delegates to {@link #loadProject(InputStream, File, ProgressHandler)})
    *
    * @param inputFile               File that denotes the location of the project to load.
    * @param progress                progress handler to track loading progress
    * @return                        An instance of FProject that was found in the file, null if no project could be read
    * @throws FileNotFoundException  if file is not found
    * @throws IOException            if loading fails for another reason
    */
   public final FProject loadProject (File inputFile, ProgressHandler progress) throws IOException
   {

      try
      {
         setLoadingProject (null);
         startLoading();
      }
      catch (InterruptedException e)
      {
         throw new IOException (e.getMessage());
      }

      InputStream stream = createNewInputStreamFromFile(inputFile);

      FProject fProject;
      try
      {
         if (progress != null)
         {
            progress.setTask (LOAD_TASK);
            progress.setTotalWork (getTotalWork());
            progress.start();
         }

         fProject = load (stream, inputFile, progress);
         if (fProject != null)
         {
            if (!Versioning.get().isInWorkspace (inputFile))
            {
               fProject.setFile (inputFile);
            }
            ProjectManager.get().addToProjects (fProject);
         }
      }
      finally
      {
         stopLoading();
         try
         {
            stream.close();
         }
         catch (IOException e)
         {
            //closing failed we can ignore this as this is most likely already indicated by another exception
         }
         if (progress != null)
         {
            progress.stop();
         }
      }

      return fProject;
   }


   /**
    * Creates a new input stream from the given file. 
    * @param inputFile
    * @return
    * @throws FileNotFoundException
    */
   protected InputStream createNewInputStreamFromFile(File inputFile)
         throws FileNotFoundException
   {
      if(inputFile == null)
      {
         return null;
      }
      InputStream stream;
      try
      {
         stream = new GZIPInputStream (new FileInputStream (inputFile));
      }
      catch (IOException e)
      {
         stream = new FileInputStream (inputFile);
      }
      return stream;
   }


   protected int getTotalWork()
   {
      return ProgressHandler.UNKNOWN;
   }


   /**
    * Load a project from stream. The loaded project is added to the ProjectManager.
    *
    * @param stream        stream to read the project data
    * @param file          name of the file the stream stems from, used e.g. for the save dialog
    *               (but not used to read any data from file)
    * @param progress      progress handler to track loading progress
    * @return              An instance of FProject that was found in the stream, null if no project could be read
    * @throws IOException  if an IO error occures while loading
    */
   public final FProject loadProject (InputStream stream, File file, ProgressHandler progress) throws IOException
   {
      try
      {
         setLoadingProject (null);
         startLoading();
      }
      catch (InterruptedException ex)
      {
         throw new IOException (ex.getMessage());
      }

      if (progress != null)
      {
         progress.setTask (LOAD_TASK);
         progress.setTotalWork (getTotalWork());
         progress.start();
      }

      try
      {
         InputStream inputStream;
         try
         {
            inputStream = new GZIPInputStream (stream);
         }
         catch (IOException e)
         {
            inputStream = stream;
         }

         FProject project = load (inputStream, file, progress);
         if (project != null)
         {
            ProjectManager.get().addToProjects (project);
         }
         return project;
      }
      finally
      {
         stopLoading();
         if (progress != null)
         {
            progress.stop();
         }
      }
   }


   /**
    * Load a project from stream. This method <b>MUST NOT</b> be called from outside
    * {@link #loadProject(File, ProgressHandler)} and {@link #loadProject(InputStream, File, ProgressHandler)}.
    *
    * @param stream        stream to read the project data
    * @param file          name of the file the stream stems from, used e.g. for the save dialog
    *               (but not used to read any data from file)
    * @param progress      progress handler to track loading progress
    * @return              An instance of FProject that was found in the stream, null if no project could be read
    * @throws IOException  if an IO error occures while loading
    */
   protected abstract FProject load (InputStream stream, File file, ProgressHandler progress) throws IOException;


   /**
    * getter for field loadingProject
    *
    * @return   current value of field loadingProject
    */
   public FProject getLoadingProject()
   {
      return this.loadingProject;
   }


   /**
    * store the value for field loadingProject
    */
   private FProject loadingProject;

   /**
    * Property name for firing property change events of field loadingProject.
    */
   public final static String PROPERTY_LOADING_PROJECT = "loadingProject";


   /**
    * setter for field loadingProject
    *
    * @param value  new value
    */
   protected void setLoadingProject (final FProject value)
   {
      final FProject oldValue = this.loadingProject;
      if (oldValue != value)
      {
         this.loadingProject = value;
//         firePropertyChange(PROPERTY_LOADING_PROJECT, oldValue, value);
      }
   }

   /**
    * @param file a project file
    * @return estimated complete file size in bytes
    */
   public long estimateProjectLoadSize(File file)
   {
      return -1;
   }
}

/*
 * $Log$
 * Revision 1.15  2007/02/07 13:48:14  cschneid
 * Jars can be opened as projects for dependencies, Classpath respects project dependencies
 *
 */

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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import de.uni_kassel.coobra.errors.HandledErrorException;
import de.uni_kassel.coobra.identifiers.RequiredRepositoryMissingException;
import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.basic.FujabaPropertyChangeSupport;
import de.uni_paderborn.fujaba.basic.SchemaFilter;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FProjectFactory;
import de.uni_paderborn.fujaba.preferences.FujabaPreferencesManager;
import de.uni_paderborn.fujaba.versioning.Versioning;
import de.upb.tools.fca.FHashSet;
import de.upb.tools.fca.FPropHashSet;
import de.upb.tools.pcs.PropertyChangeClient;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *                         currentProject        0..1
 *  ProjectManager ----------------------------------&gt; FProject
 *                                     currentProject
 * 
 *                  0..1   projects   0..n
 *  ProjectManager ------------------------ FProject
 *                  manager       projects
 *                  
 *                 -------           projectFactories      0..1 
 *  ProjectManager | key |-------------------------------------&gt; FProjectFactory&lt;? extends FProject&gt;
 *                 -------                     projectFactories 
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class ProjectManager implements PropertyChangeClient
{
   /**
    * log4j logging.
    */
   private final static transient Logger log = Logger
         .getLogger(ProjectManager.class);

   /**
    * The singleton instance.
    */
   private static ProjectManager theProjectManager;
   private boolean loading;
   private Set<FProject> projectsReadOnly;


   /**
    * The private constructor. Use get() to get the singleton instance.
    */
   private ProjectManager()
   {
      addPropertyChangeListener(PROJECTS_PROPERTY, new ProjectsListener());
   }


   /**
    * @return the singleton instance
    */
   public static ProjectManager get()
   {
      if (theProjectManager == null)
      {
         theProjectManager = new ProjectManager();
      }

      return theProjectManager;
   }


   /**
    * Get the currently selected project.
    * 
    * @return the current project
    * @deprecated (gets deleted in 5.1) very error prone - use getProject() of a model element that
    *             is known to be in the same project
    */
   public FProject getCurrentProject()
   {
      throw new UnsupportedOperationException("use getProject() of a model element that is known " +
            "to be in the same project instead");
   }

   /**
    * <pre>
    *                  0..1   projects   0..n
    *  ProjectManager ------------------------ FProject
    *                  manager       projects
    * </pre>
    */
   private Set<FProject> projects;

   /**
    * Constant for property name in property change support.
    */
   public final static String PROJECTS_PROPERTY = "projects";


   public boolean addToProjects(FProject value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.projects == null)
         {
            this.projects = new FPropHashSet(this, PROJECTS_PROPERTY);
         }
         changed = this.projects.add(value);
         if (changed)
         {
            value.setManager(this);
         }
      }
      return changed;
   }


   public boolean hasInProjects(FProject value)
   {
      return ((this.projects != null) && (value != null) && this.projects
            .contains(value));
   }


   public Iterator<? extends FProject> iteratorOfProjects()
   {
      if (this.projects == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.projects.iterator();
      }
   }


   public FProject getFromProjects(String name)
   {
      for (Iterator iter = this.iteratorOfProjects(); iter.hasNext();)
      {
         FProject aProject = (FProject) iter.next();
         if (name.equals(aProject.getName()))
         {
            return aProject;
         }

      }
      return null;
   }


   public void removeAllFromProjects()
   {
      Iterator iter = this.iteratorOfProjects();
      while (iter.hasNext())
      {
         FProject tmpValue = (FProject) iter.next();
         this.removeFromProjects(tmpValue);
      }
   }


   public boolean removeFromProjects(FProject value)
   {
      boolean changed = false;
      if ((this.projects != null) && (value != null))
      {
         changed = this.projects.remove(value);
         if (changed)
         {
            value.setManager(null);
         }
      }
      return changed;
   }


   public int sizeOfProjects()
   {
      return ((this.projects == null) ? 0 : this.projects.size());
   }

   /**
    * <pre>
    *                 -------           projectFactories      0..1
    *  ProjectManager | key |-------------------------------------&gt; FProjectFactory
    *                 -------                     projectFactories
    * </pre>
    */
   private transient Map<Class<? extends FProject>, FProjectFactory<? extends FProject>> projectFactories;


   public <I extends FProject> boolean addToProjectFactories(Class<I> key,
         FProjectFactory<I> value)
   {
      boolean changed = false;
      if (key != null)
      {
         if (this.projectFactories == null)
         {
            // no need to fire property change events
            this.projectFactories = new ConcurrentHashMap<Class<? extends FProject>, FProjectFactory<? extends FProject>>();
         }
         FProjectFactory oldValue = this.projectFactories.put(key, value);
         if (oldValue != value)
         {
            changed = true;
         }
      }
      return changed;
   }


   /**
    * Retrieve a project factory by project class.
    * 
    * @param key the project class that should be instantiated
    * @return a factory for creating a project of specified type
    */
   @SuppressWarnings( { "unchecked" })
   public <I extends FProject> FProjectFactory<I> getFromProjectFactories(
         Class<I> key)
   {
      if (this.projectFactories == null || key == null)
      {
         return null;
      }
      else
      {
         return (FProjectFactory<I>) this.projectFactories.get(key);
      }
   }


   /**
    * Retrieve a project factory by classname. Preferably use
    * {@link #getFromProjectFactories(Class)}. This method should be used for loading only.
    * 
    * @param className name of the class to instantiate
    * @return a factory for creating a project of specified type
    */
   public FProjectFactory<? extends FProject> getFromProjectFactories(
         String className)
   {
      if (this.projectFactories == null || className == null)
      {
         return null;
      }
      else
      {
         for (Map.Entry<Class<? extends FProject>, FProjectFactory<? extends FProject>> entry : this.projectFactories
               .entrySet())
         {
            if (className.equals(entry.getKey().getName()))
            {
               return entry.getValue();
            }
         }
         return null;
      }
   }


   public boolean hasInProjectFactories(
         FProjectFactory<? extends FProject> value)
   {
      return ((this.projectFactories != null) && this.projectFactories
            .containsValue(value));
   }


   public Iterator<FProjectFactory<? extends FProject>> iteratorOfProjectFactories()
   {
      if (this.projectFactories == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.projectFactories.values().iterator();
      }
   }


   public void removeAllFromProjectFactories()
   {
      Iterator<Map.Entry<Class<? extends FProject>, FProjectFactory<? extends FProject>>> iter = entriesOfProjectFactories();
      while (iter.hasNext())
      {
         Map.Entry<Class<? extends FProject>, ? extends FProjectFactory<? extends FProject>> entry = iter
               .next();
         removeFromProjectFactories(entry.getKey(), entry.getValue());
      }
   }


   public <I extends FProject> boolean removeFromProjectFactories(
         FProjectFactory<I> value)
   {
      boolean changed = false;
      if (this.projectFactories != null)
      {
         Iterator<Map.Entry<Class<? extends FProject>, FProjectFactory<? extends FProject>>> iter = entriesOfProjectFactories();
         while (iter.hasNext())
         {
            Map.Entry<Class<? extends FProject>, ? extends FProjectFactory<? extends FProject>> entry = iter
                  .next();
            if (entry.getValue() == value)
            {
               if (this.removeFromProjectFactories(entry.getKey(), value))
               {
                  changed = true;
               }
            }
         }
      }
      return changed;
   }


   private Iterator<Map.Entry<Class<? extends FProject>, FProjectFactory<? extends FProject>>> entriesOfProjectFactories()
   {
      if (this.projectFactories == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.projectFactories.entrySet().iterator();
      }
   }


   private boolean removeFromProjectFactories(Class<? extends FProject> key,
         FProjectFactory<? extends FProject> value)
   {
      boolean changed = false;
      if ((this.projectFactories != null) && (key != null))
      {
         FProjectFactory oldValue = this.projectFactories.get(key);
         if (oldValue == value
               && (oldValue != null || this.projectFactories.containsKey(key)))
         {
            this.projectFactories.remove(key);
            changed = true;
         }
      }
      return changed;
   }

   /**
    * <pre>
    *                         projectInitializers      0..n
    *  ProjectManager -------------------------------------&gt; FProjectInitializer
    *                                   projectInitializers
    * </pre>
    */
   private Set<FProjectInitializer> projectInitializers;


   public boolean addToProjectInitializers(FProjectInitializer value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.projectInitializers == null)
         {
            this.projectInitializers = new FHashSet<FProjectInitializer>();
         }
         changed = this.projectInitializers.add(value);
      }
      return changed;
   }


   public boolean hasInProjectInitializers(FProjectInitializer value)
   {
      return ((this.projectInitializers != null) && (value != null) && this.projectInitializers
            .contains(value));
   }


   public Iterator<? extends FProjectInitializer> iteratorOfProjectInitializers()
   {
      if (this.projectInitializers == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.projectInitializers.iterator();
      }
   }


   public boolean removeFromProjectInitializers(FProjectInitializer value)
   {
      boolean changed = false;
      if ((this.projectInitializers != null) && (value != null))
      {
         changed = this.projectInitializers.remove(value);
      }
      return changed;
   }


   public void removeAllFromProjectInitializers()
   {
      if (this.projectInitializers != null
            && this.projectInitializers.size() > 0)
      {
         this.projectInitializers.clear();
      }
   }

   // --------------------------------- interface PropertyChangeClient
   // --------------------------

   /**
    * Property change support.
    */
   private transient FujabaPropertyChangeSupport propertyChangeSupport = new FujabaPropertyChangeSupport(
         this);


   /**
    * @see de.upb.tools.pcs.PropertyChangeInterface#getPropertyChangeSupport()
    */
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return this.propertyChangeSupport;
   }


   /**
    * @see de.upb.tools.pcs.PropertyChangeClient#addPropertyChangeListener(java.beans.PropertyChangeListener)
    */
   public void addPropertyChangeListener(PropertyChangeListener listener)
   {
      getPropertyChangeSupport().addPropertyChangeListener(listener);
   }


   /**
    * @see de.upb.tools.pcs.PropertyChangeClient#addPropertyChangeListener(java.lang.String,
    *      java.beans.PropertyChangeListener)
    */
   public void addPropertyChangeListener(String propertyName,
         PropertyChangeListener listener)
   {
      getPropertyChangeSupport().addPropertyChangeListener(propertyName,
            listener);
   }


   /**
    * @see de.upb.tools.pcs.PropertyChangeClient#removePropertyChangeListener(java.beans.PropertyChangeListener)
    */
   public void removePropertyChangeListener(PropertyChangeListener listener)
   {
      getPropertyChangeSupport().removePropertyChangeListener(listener);
   }


   /**
    * @see de.upb.tools.pcs.PropertyChangeClient#removePropertyChangeListener(java.lang.String,
    *      java.beans.PropertyChangeListener)
    */
   public void removePropertyChangeListener(String propertyName,
         PropertyChangeListener listener)
   {
      getPropertyChangeSupport().removePropertyChangeListener(propertyName,
            listener);
   }

   /**
    * Map from file extension (lowercase, without leading ".") to ProjectLoader instance in charge.
    */
   private Map<String, ProjectLoader> loaders;


   /**
    * Find a loader for a specified format description.
    * 
    * The format description is either a file extension or a specialized format description
    * 
    * If it is a file extension, the format is lowercase, without leading ".", examples: "ctr",
    * "fpr.gz"
    * 
    * For any other format description, the format is
    * <code>&lt;description-type&gt;<b>:</b>&lt;description-key&gt;</code> where
    * <code>&lt;description-type&gt;</code> is a non-empty, lowercase string of the form
    * [a-z_][a-z0-9_\-]* and <code>&lt;description-key&gt;</code> is a non-empty, lowercase
    * string, whose format is specific for the description-type. Examples: "file:the.project" -
    * description of type "file" with key "the.project" "content-type:application/xml" - description
    * of type "content-type" with key "application/xml"
    * 
    * @param format format description
    * @return project loader capable of loading the specified format
    * @see #registerProjectLoader(String, ProjectLoader)
    */
   public ProjectLoader getProjectLoader(String format)
   {
      return loaders != null ? loaders.get(format.toLowerCase()) : null;
   }


   /**
    * List supported file formats (file extensions) for loading.
    * 
    * @return iterator through extensions (in lower case, without leading ".")
    */
   public Iterator<String> iteratorOfProjectLoaderFormats()
   {
      if (loaders != null)
      {
         return loaders.keySet().iterator();
      }
      else
      {
         return EmptyIterator.get();
      }
   }


   /**
    * Registers a ProjectLoader for a specified file format (file extension). Any previously
    * registration for the same format is ignored.
    * 
    * @param format format description, see {@link #getProjectLoader(String)}
    * @param loader ProjectLoader in charge
    * @see #getProjectLoader(String)
    * @see #registerProjectWriter(String, ProjectWriter)
    */
   public void registerProjectLoader(String format, ProjectLoader loader)
   {
      if (loader == null || format == null)
      {
         throw new IllegalArgumentException("Arguments may not be null!");
      }
      if (loaders == null)
      {
         loaders = new ConcurrentHashMap<String, ProjectLoader>();
      }
      format = format.toLowerCase();
      if (format.startsWith("."))
      {
         format = format.substring(1);
      }
      loaders.put(format, loader);
   }

   /**
    * Map from file extension (lowercase, without leading ".") to ProjectWriter instance in charge.
    */
   private Map<String, ProjectWriter> writers;


   /**
    * Find a writer for a specified file extension.
    * 
    * @param format format description, see {@link #getProjectLoader(String)}
    * @return project writer capable of loading the specified format
    * @see #getProjectLoader(String)
    * @see #registerProjectWriter(String, ProjectWriter)
    */
   public ProjectWriter getProjectWriter(String format)
   {
      return writers != null ? writers.get(format.toLowerCase()) : null;
   }


   /**
    * List supported file formats (file extensions) for loading.
    * 
    * @return iterator through extensions (in lower case, without leading ".")
    */
   public Iterator<String> iteratorOfProjectWriterFormats()
   {
      if (writers != null)
      {
         return writers.keySet().iterator();
      }
      else
      {
         return EmptyIterator.get();
      }
   }


   /**
    * Registers a ProjectWriter for a specified file format (file extension). Any previously
    * registration for the same format is ignored.
    * 
    * @param format format description, see {@link #getProjectLoader(String)}
    * @param writer ProjectWriter in charge
    * @see #getProjectLoader(String)
    * @see #registerProjectLoader(String, ProjectLoader)
    */
   public void registerProjectWriter(String format, ProjectWriter writer)
   {
      if (writer == null || format == null)
      {
         throw new IllegalArgumentException("Arguments may not be null!");
      }
      if (writers == null)
      {
         writers = new ConcurrentHashMap<String, ProjectWriter>();
      }
      format = format.toLowerCase();
      if (format.startsWith("."))
      {
         format = format.substring(1);
      }
      writers.put(format, writer);
   }


   /**
    * @return ProjectWriter that should be used by default
    * @param project for which project
    */
   public ProjectWriter getDefaultProjectWriter(FProject project)
   {
      return getProjectWriter(getDefaultFormat(project));
   }

   Map<Class<? extends FProject>, String> defaultFormats = new ConcurrentHashMap<Class<? extends FProject>, String>();


   public void addToDefaultFormats(Class<? extends FProject> projectClass,
         String format)
   {
      defaultFormats.put(projectClass, format);
   }

   private String defaultFormat;


   public void setDefaultFormat(String format)
   {
      defaultFormat = format;
   }


   /**
    * @return format that is used by default (file extension)
    * @param project for which project
    */
   public String getDefaultFormat(FProject project)
   {
      if (project != null)
      {
         if (defaultFormats.containsKey(project.getClass()))
         {
            return defaultFormats.get(project.getClass());
         }
      }
      return defaultFormat;
   }

   private Map<Class<? extends FProject>, SchemaFilter> schemaFilters = new ConcurrentHashMap<Class<? extends FProject>, SchemaFilter>();


   public void addToSchemaFilters(Class<? extends FProject> projectClass,
         SchemaFilter filter)
   {
      schemaFilters.put(projectClass, filter);
   }


   public Iterator<SchemaFilter> iteratorOfSchemaFilters()
   {
      return schemaFilters.values().iterator();
   }


   public SchemaFilter getFromSchemaFilters(
         Class<? extends FProject> projectClass)
   {
      return schemaFilters.get(projectClass);
   }

   private SchemaFilter defaultSchemaFilter;


   public void setDefaultSchemaFilter(SchemaFilter filter)
   {
      defaultSchemaFilter = filter;
   }


   public SchemaFilter getDefaultSchemaFilter()
   {
      return defaultSchemaFilter;
   }


   /**
    * Close all projects.
    * 
    * @param renameWorkspaceFiles true to rename workspace files (to disable automatic reopening of
    *           projects)
    */
   public void closeAllProjects(boolean renameWorkspaceFiles)
   {
      for (Iterator it = iteratorOfProjects(); it.hasNext();)
      {
         FProject project = (FProject) it.next();
         closeProject(project, renameWorkspaceFiles);
      }
   }


   /**
    * Close a project (make garbage collection possible and workspace files).
    * 
    * @param project which project to be closed
    * @param renameWorkspaceFile true to rename workspace files (to disable automatic reopening of
    *           projects)
    */
   public void closeProject(FProject project, boolean renameWorkspaceFile)
   {
      if (hasInProjects(project))
      {
         for (Iterator<? extends FProject> it = project.iteratorOfRequiredBy(); it
               .hasNext();)
         {
            FProject requiredBy = it.next();
            closeProject(requiredBy, renameWorkspaceFile);
         }
         try
         {
            Versioning.get().close(project, renameWorkspaceFile);
         }
         finally
         {
            removeFromProjects(project);
            project.removeYou();
         }
         FujabaPreferencesManager.deleteProjectPreferenceStore(project);
      }
   }

   public static final String LOADING_PROPERTY = "loading";


   /**
    * Load a project from file. Uses the ProjectLoader that is in charge for the file's extension.
    * 
    * @param file file to be loaded.
    * @param progress progress handler to track loading progress
    * @param keepWorkspaceFileOnFailure true to keep the workspace even if the load operation failed
    * @return the new project, or null if the file does not exist
    * @throws IllegalArgumentException if no ProjectLoader was found for the file extension
    * @throws IOException if an IOException is thrown by the ProjectLoader
    * @throws InvocationTargetException if any other exception is thrown by the ProjectLoader, that
    *            exception is wrapped by an InvocationTargetException
    */
   public FProject loadProject(File file, ProgressHandler progress, boolean keepWorkspaceFileOnFailure)
         throws IllegalArgumentException,
            IOException,
            InvocationTargetException
   {
      // distinguish between fpr and ctr files
      final ProjectLoader projectLoader = findLoader(file);
      return loadProject(projectLoader, file, progress, keepWorkspaceFileOnFailure);
   }

   /**
    * Load a project from file. Use the passed ProjectLoader.
    * 
    * @param projectLoader project loader that is in charge for the file's extension.
    * @param file file to be loaded.
    * @param progress progress handler to track loading progress
    * @param keepWorkspaceFileOnFailure true to keep the workspace even if the load operation failed
    * @return the new project, or null if the file does not exist
    * @throws IllegalArgumentException if no ProjectLoader was found for the file extension
    * @throws IOException if an IOException is thrown by the ProjectLoader
    * @throws InvocationTargetException if any other exception is thrown by the ProjectLoader, that
    *            exception is wrapped by an InvocationTargetException
    */
   public FProject loadProject(ProjectLoader projectLoader, File file, ProgressHandler progress, boolean keepWorkspaceFileOnFailure)
         throws IllegalArgumentException,
            IOException,
            InvocationTargetException
   {
      Versioning.get().startInplaceEditing();
      if (file.exists() && file.isFile())
      {
         setLoading(true);

         try
         {
            FProject newProject = projectLoader.loadProject(file, progress);

            if (newProject != null)
               {
                  log.debug("ProjectManager: " + projectLoader.getClass().getSimpleName() + " loaded project '" + newProject + "' of type '" + newProject.getClass().getName() + "'");
               }

            return newProject;
         }
         catch (Exception except)
         {
            if ( !(except instanceof HandledErrorException) )
            {
               log.error(except.getMessage(), except);
            }
            FProject newProject = projectLoader.getLoadingProject();
            String projectName = null;
            
            // do not throw new exceptions while error handling, otherwise this would cancel handling the above exception
            try
            {
               if (newProject != null)
               {
                  projectName = newProject.getName();
               } else
               {
                  projectName = null;
               }
               Versioning.get().abortCurrentAction();
               if (newProject != null)
               {
                  closeProject(newProject, !keepWorkspaceFileOnFailure);
               }
            }
            catch (Exception e)
            {
               log.error("The ProjectManager's error handling has caused the following exception:", e);
            }
            
            if(except instanceof RequiredRepositoryMissingException)
            {
               throw (RequiredRepositoryMissingException) except;
            }
            if (except instanceof InvocationTargetException)
            {
               throw (InvocationTargetException) except;
            }
            if (except instanceof IOException)
            {
               throw (IOException) except;
            }

            if ( except instanceof HandledErrorException)
            {
               return null;
            }
            else
            {
               throw new LoadProjectException(except, projectName);
            }
         }
         finally
         {
            setLoading(false);
         }
      }
      else
      {
         return null;
      }
   }

   private ProjectLoader findLoader(File file)
   {
      String fileName = file.getName();
      String extension = SchemaFilter.extractFileExtension(fileName);
      if (extension.length() == 0)
      {
         extension = getDefaultFormat(null);
      }
      final ProjectLoader projectLoader = getProjectLoader(extension);

      if (projectLoader == null)
      {
         throw new IllegalArgumentException("Unknown file format: "
               + extension);
      }
      return projectLoader;
   }

   /**
    * @param file a project file
    * @return estimated complete file size in bytes
    */
   public long estimateProjectLoadSize(File file)
   {
      final ProjectLoader projectLoader = findLoader(file);
      return projectLoader.estimateProjectLoadSize(file);
   }


   /**
    * Use with care, consider using
    * {@link de.uni_paderborn.fujaba.versioning.Versioning#isInOperationalization(de.uni_paderborn.fujaba.metamodel.common.FElement) Versioning.isInOperationalization()} instead.
    * @return true while loading project data from file/stream
    */
   public boolean isLoading()
   {
      return loading;
   }

   public void setLoading(boolean state)
   {
      try
      {
         loading = state;
         this.propertyChangeSupport.firePropertyChange(LOADING_PROPERTY,
               !state, state);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * @return unmodifiable set of projects
    */
   public Set<FProject> getProjects()
   {
      if ( projectsReadOnly == null )
      {
         projectsReadOnly = Collections.unmodifiableSet(projects);
      }
      return projectsReadOnly;
   }

   public static final String PROPERTY_LAST_FILE_HEADER_ENTRIES = "lastFileHeaderEntries";

   /**
    * Contains the entries (key-value pairs) of the last file header.
    * May be set by a ProjectLoader instance while loading a project.
    * The entries depend on the ProjectWriter instance that has been used to serialize the project.
    */
   private Map<String, String> lastFileHeaderEntries;

   public boolean setLastFileHeaderEntries (Map<String, String> value)
   {
      boolean changed = false;

      if (this.lastFileHeaderEntries != value)
      {
         Map<String, String> oldValue = this.lastFileHeaderEntries;
         this.lastFileHeaderEntries = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_LAST_FILE_HEADER_ENTRIES, oldValue, value);
         changed = true;
      }

      return changed;
   }

   public Map<String, String> getLastFileHeaderEntries ()
   {
      return this.lastFileHeaderEntries;
   }

   class ProjectsListener implements PropertyChangeListener
   {
      private final ProjectDependencyListener listener = new ProjectDependencyListener();


      public void propertyChange(PropertyChangeEvent evt)
      {
         FProject oldProject = ((FProject) evt.getOldValue());
         if (oldProject != null)
         {
            oldProject.removePropertyChangeListener(FProject.REQUIRES_PROPERTY,
                  listener);
            oldProject.removePropertyChangeListener(
                  FProject.REQUIRED_BY_PROPERTY, listener);
         }
         FProject newProject = ((FProject) evt.getNewValue());
         if (newProject != null)
         {
            newProject.addPropertyChangeListener(FProject.REQUIRES_PROPERTY,
                  listener);
            newProject.addPropertyChangeListener(FProject.REQUIRED_BY_PROPERTY,
                  listener);
         }
      }
   }

   class ProjectDependencyListener implements PropertyChangeListener
   {
      public void propertyChange(PropertyChangeEvent evt)
      {
         if (evt.getNewValue() != null) // don't write order if dependencies are only removed -
         // esp. on close
         {
            updateProjectLoadOrder();
         }
      }
   }

   private List<FProject> determineProjectLoadOrder() throws ProjectDependencyCycleException
   {
      List<FProject> sortedProjects = new ArrayList<FProject>(projects.size());
      boolean missingProject = true;
      while (missingProject)
      {
         missingProject = false;
         boolean addedProject = false;
         projectLoop: for (FProject project : projects)
         {
            if (!sortedProjects.contains(project))
            {
               for (Iterator<? extends FProject> it = project
                     .iteratorOfRequires(); it.hasNext();)
               {
                  FProject requiredProject = it.next();
                  if (!sortedProjects.contains(requiredProject))
                  {
                     missingProject = true;
                     continue projectLoop;
                  }
               }
               addedProject = sortedProjects.add(project);
            }
         }
         if (missingProject && !addedProject)
         {
            // handle cycle: throw Exception
            
            List<FProject> missingProjects = new ArrayList<FProject>();
            for (FProject project : projects)
            {
               if (!sortedProjects.contains(project))
                  missingProjects.add(project);
            }
            
            String text = "A cyclic dependency was detected "
               + "within project dependencies: "
               + missingProjects
               + " Please correct the dependencies to allow proper operation again.";
            
            throw new ProjectDependencyCycleException(text, missingProjects);
         }
      }
      return sortedProjects;
   }
   
   public final static String PROJECT_DEPENDENCY_PROPERTY = "project_dependency";
   
   /**
    * Store the order for loading projects into projects workspace.
    */
   public void updateProjectLoadOrder()
   {
      // notify about re-calculation of dependencies
      getPropertyChangeSupport().firePropertyChange(
            new PropertyChangeEvent(
                  this,
                  PROJECT_DEPENDENCY_PROPERTY,
                  this, // dummy value to ensure that the event is fired
                  null) );
      
      List<FProject> sortedProjects;
      try
      {
         sortedProjects = determineProjectLoadOrder();
      }
      catch (ProjectDependencyCycleException e)
      {
         // notify about detected project dependency cycle
         getPropertyChangeSupport().firePropertyChange(
               PROJECT_DEPENDENCY_PROPERTY,
               null,
               e.getAffectedProjects());
         
         return; // do not change file load order if there is a cyclic dependency
      }
      
      Versioning.get().changeWorkspaceFileLoadOrder(sortedProjects);
   }
}

/*
 * $Log$
 * Revision 1.55  2007/04/23 11:15:37  cschneid
 * store code style (fire event); don't update tree while closing
 *
 * Revision 1.54  2007/03/15 16:36:25  cschneid
 * - Generating code is possible again
 * - projects failing to load entirely are removed from workspace
 *
 * Revision 1.53  2007/03/13 13:40:20  weisemoeller
 * Restored deleted classes from last commit. Those classes have been marked as deprecated now and will probably be deleted in a few weeks (we can discuss the point of time for this on the developer days, I think.)
 *
 * The new classes have been moved back to de.fujaba for now.
 *
 * Moreover, the usage of some workspace settings has been replaced with the corresponding project specific settings.
 *
 * Revision 1.52  2007/03/13 10:46:18  weisemoeller
 * Replaced old dialogs with xml based ones.
 *
 * I have replaced the preferences dialogs in Fujaba (and MOFLON) with this commit. If you want to change or create dialogs, take a look at the file dialogs.xsd in the DTDs directory and the existing dialog.xml in the properties directory. Since the properties backend has been changed as well, I needed to modify a large number of classes to use the new backend. If you notice unusual behaviour and suppose it to be related to this, please let me (ingo,weisemoeller@es.tu-darmstadt.de) know.
 *
 * Revision 1.51  2007/03/12 08:29:28  cschneid
 * deprecated FrameMain.getCurrentProject, removed ProjectManager.getCurrentProject (calls to ProjectManager.setCurrentProject can be omitted) and FProject.getCurrentModelRootNode (can be replaced with FrameMain.getCurrentDiagram in most cases)
 *
 * Revision 1.50  2007/02/27 10:31:34  lowende
 * Removed tons of compile warnings.
 * Revision 1.49 2007/02/23 13:31:48 cschneid speed up update and
 * checkout, new libs
 * 
 * Revision 1.48 2007/02/16 15:39:00 cschneid write project order on save (reopen)
 * 
 * Revision 1.47 2007/02/16 14:37:00 cschneid don't write project order on app close
 * 
 * Revision 1.46 2007/02/13 14:37:59 weisemoeller current state of xml based dialogs - added a map
 * containing the preferenceStore for each plugin to the ProjectManager - all MOFLON plugins inherit
 * from AbstractPlugin - specification of MOFLON/Editor preferences dialog - XML merger - smaller
 * bugfixes
 * 
 * Revision 1.45 2007/02/12 16:33:33 cschneid close depending projects, fixed fpr progrss bar,
 * Fujaba 4 Bend/Arrow import fixed
 * 
 * Revision 1.44 2007/02/08 13:43:27 cschneid recover and project dependencies improved
 * 
 * Revision 1.43 2007/02/07 13:48:14 cschneid Jars can be opened as projects for dependencies,
 * Classpath respects project dependencies
 * 
 * Revision 1.42 2006/08/17 08:57:56 zuendorf Some enhancement of the Link Editor ported from Fujaba
 * 4 Some utilities added for class retrival Some virtual access methods added for convinience
 * Revision 1.41 2006/06/13 11:27:39 cschneid local versioning support (CVS/SVN)
 * 
 * Revision 1.40 2006/05/24 08:44:42 cschneid use Project factories in Versioning, selection manager
 * can select not-displayed elements, Project tree displays packages, more dummy code for access
 * sytles
 * 
 * Revision 1.39 2006/05/23 13:46:51 fklar adjusted generic-type of Iterator in return-values of
 * some methods in class ProjectManager
 * 
 * Revision 1.38 2006/05/23 09:33:08 mm [applied patch by travkin]: removed compile errors in
 * ASGElement and Message - ProjectLoader can handle InputStreams now - corrected some javadoc
 * comments - added library to plugin.xml - added some plugins to cvsignore
 * 
 * Revision 1.37 2006/04/25 11:58:24 cschneid added deprecation expiration note, work on versioning
 * 
 * Revision 1.36 2006/04/12 13:52:21 lowende Project factories introduced. Interface for plugins
 * introduced to add factories to newly-created project factories.
 * 
 */

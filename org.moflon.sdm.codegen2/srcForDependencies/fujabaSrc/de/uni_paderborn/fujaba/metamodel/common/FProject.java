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
package de.uni_paderborn.fujaba.metamodel.common;


import de.uni_kassel.coobra.Repository;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.uni_paderborn.fujaba.versioning.properties.Local;
import de.upb.lib.plugins.PluginProperty;

import java.io.File;
import java.util.Iterator;
import java.util.Map;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *           0..1   modelRootNodes   0..n
 * FProject ------------------------------ FModelRootNode
 *           project       modelRootNodes
 *
 *                                  0..1
 * FProject -----------------------------> FModelRootNode
 *                  currentModelRootNode
 *
 *          --------------- 0..1   factories   0..1
 * FProject | factoryKind |------------------------- FFactory
 *          --------------- project       factories
 *
 *           0..n   projects   0..1
 * FProject ------------------------ ProjectManager
 *           projects       manager
 *
 *          ------- 0..1   properties   0..1
 * FProject | key |-------------------------- ProjectProperty
 *          ------- project       properties
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public interface FProject extends FElement
{
   /**
    * Property name for firing property change events of field $name.
    */
   String PROPERTY_REPOSITORY_SERVER = "repositoryServer";
   /**
    * Property name for firing property change events of field $name.
    */
   String PROPERTY_REPOSITORY_SERVER_USERNAME = "repositoryServerUsername";

   /**
    * For persistency and versioning support.
    * 
    * @return the repository of the whole project (including all contents).
    */
   public Repository getRepository();


   /**
    * <pre>
    *           0..1   modelRootNodes   0..n
    * FProject ------------------------------ FModelRootNode
    *           project       modelRootNodes
    * </pre>
    */
   public final static String MODEL_ROOT_NODES_PROPERTY = "modelRootNodes";


   @Property(name = MODEL_ROOT_NODES_PROPERTY )
   public boolean addToModelRootNodes(FModelRootNode value);


   @Property(name = MODEL_ROOT_NODES_PROPERTY )
   public boolean hasInModelRootNodes(FModelRootNode value);


   @Property(name = MODEL_ROOT_NODES_PROPERTY, accessFragment = AccessFragment.ITERATOR,
         kind = ReferenceHandler.ReferenceKind.TO_MANY, adornment = ReferenceHandler.Adornment.COMPOSITION)
   public Iterator<? extends FModelRootNode> iteratorOfModelRootNodes();


   /**
    * Retrieve the modelRootNode by the specified name.
    * 
    * @param key The name of the modelRootNode to search for.
    * @return Return the modelRootNode or null, if no such modelRootNode exists.
    */
   @Property(name = MODEL_ROOT_NODES_PROPERTY )
   public FModelRootNode getFromModelRootNodes(String key);


   @Property(name = MODEL_ROOT_NODES_PROPERTY )
   public void removeAllFromModelRootNodes();


   @Property(name = MODEL_ROOT_NODES_PROPERTY )
   public boolean removeFromModelRootNodes(FModelRootNode value);


   @Property(name = MODEL_ROOT_NODES_PROPERTY )
   public int sizeOfModelRootNodes();


   /**
    * <pre>
    *                                  0..1
    * FProject -----------------------------> FModelRootNode
    *                  currentModelRootNode
    * </pre>
    */
   public final static String CURRENT_MODEL_ROOT_NODE_PROPERTY = "currentModelRootNode";


   /**
    * <pre>
    *          ---------------        factories   0..1
    * FProject | factoryKind |-------------------------> FFactory<? extends FElement>
    *          ---------------               factories
    * </pre>
    */
   public <I extends FElement> boolean addToFactories(FFactory<I> value);


   /**
    * Obtain a factory for specified interface or implementation.
    * @param key class or interface
    * @return a factory, not null
    */
   public <I extends FElement> FFactory<I> getFromFactories(Class<I> key);

   public <I extends FElement> I createFromFactories(Class<I> key, boolean persistent);
   
   public <I extends FElement> I createFromFactories(Class<I> key);

   public <I extends FElement> boolean hasInFactories(Class<I> key,
         FFactory<I> value);


   public <I extends FElement> boolean hasInFactories(FFactory<I> value);


   public <I extends FElement> boolean hasKeyInFactories(Class<I> key);


   public Iterator<FFactory<? extends FElement>> iteratorOfFactories();


   public boolean removeFromFactories(FFactory<? extends FElement> value);


   public <I extends FElement> boolean removeKeyFromFactories(Class<I> key);


   public void removeAllFromFactories();


   /**
    * If possible use {@link #getFromFactories(Class)} instead. 
    */
   public FFactory getFromFactories(String key);


   /**
    * <pre>
    *           0..n   projects   0..1
    * FProject ------------------------ ProjectManager
    *           projects       manager
    * </pre>
    */
   public final static String MANAGER_PROPERTY = "manager";


   public ProjectManager getManager();


   public boolean setManager(ProjectManager value);


   /**
    * @see #addToProperties
    */
   public final static String PROPERTIES_PROPERTY = "properties";

   /**
    * Store a project wide property. Plugins are encouraged to set property defaults when creating new projects.
    * @param key property name, ususally qualified with plugin ID
    * @param value value for the specified key, null to remove key
    */
   public void addToProperties(String key, String value);

   public final static String SAVED_PROPERTY = "saved";


   public void setSaved(boolean value);


   public boolean isSaved();


   public final static String FILE_PROPERTY = "file";


   public void setFile(File file);


   public File getFile();
   

   public static final String REQUIRES_PROPERTY = "requires";


   /**
    * Add a project to the set of required projects. Introduces a project dependency.
    * 
    * @param value project that will be required for loading this project from now on
    * @return false if the project was already a required one
    * @see #addToRequiredBy(FProject)
    */
   public boolean addToRequires(FProject value);


   /**
    * @param value second project
    * @return true if the second project is required by this project
    * @see #addToRequires(FProject)
    * @see #addToRequiredBy(FProject)
    */
   public boolean hasInRequires(FProject value);


   /**
    * @return iterator through set of required projects.
    */
   public Iterator<? extends FProject> iteratorOfRequires();


   /**
    * clear set of required projects.
    */
   public void removeAllFromRequires();


   /**
    * Remove project from set of required projects.
    * 
    * @param value project removed
    * @return false if value was not in set
    */
   public boolean removeFromRequires(FProject value);


   /**
    * @return number of required projects
    */
   public int sizeOfRequires();


   public static final String REQUIRED_BY_PROPERTY = "requiredBy";


   /**
    * Add a project to the list of projects that require this project for loading.
    * 
    * @param value dependent project
    * @return false if the value was already required by this project
    * @see #addToRequires(FProject)
    */
   public boolean addToRequiredBy(FProject value);


   /**
    * @param value second project
    * @return true if the list of projects depending on this project contains the second project.
    */
   public boolean hasInRequiredBy(FProject value);


   /**
    * @return iterator through list of projects depending on this project.
    */
   public Iterator<? extends FProject> iteratorOfRequiredBy();


   /**
    * clear list of projects depending on this project.
    */
   public void removeAllFromRequiredBy();


   /**
    * Remove project from list of projects depending on this project.
    * 
    * @param value second project
    * @return false if value was in the list
    */
   public boolean removeFromRequiredBy(FProject value);


   /**
    * @return number of projects depending on this project.
    */
   public int sizeOfRequiredBy();


   public static final String REQUIRED_PLUGINS_PROPERTY = "requiredPlugins";


   /**
    * Add a required plugin.
    * 
    * @param plugin the plugin that is required by this project
    * @return true if plugin was not already required
    */
   public boolean addToRequiredPlugins(PluginProperty plugin);


   /**
    * Remove a plugin from the required plugins list.
    * 
    * @param plugin which plugin is no longer needed
    * @return true if plugin was required before
    */
   public boolean removeFromRequiredPlugins(PluginProperty plugin);


   /**
    * @return iterator through all PluginProperties that specify plugins required by this project
    */
   public Iterator<PluginProperty> iteratorOfRequiredPlugins();


   public final static String ROOT_PACKAGE_PROPERTY = "rootPackage";


   @Property(name = ROOT_PACKAGE_PROPERTY, accessFragment = AccessFragment.GETTER,
         kind = ReferenceHandler.ReferenceKind.TO_ONE, adornment = ReferenceHandler.Adornment.COMPOSITION)
   public FPackage getRootPackage();


   /**
    * getter for field repositoryServer
    *
    * @return current value of field repositoryServer
    */
   @Property(name = PROPERTY_REPOSITORY_SERVER, accessFragment = AccessFragment.GETTER,
         kind = ReferenceHandler.ReferenceKind.TO_ONE, adornment = ReferenceHandler.Adornment.NONE)
   String getRepositoryServer();

   /**
    * setter for field repositoryServer
    *
    * @param value new value
    */
   @Property(name = PROPERTY_REPOSITORY_SERVER, accessFragment = AccessFragment.SETTER )
   void setRepositoryServer(String value);

   /**
    * getter for field repositoryServerUsername
    *
    * @return current value of field repositoryServerUsername
    */
   @Local @Property(name = PROPERTY_REPOSITORY_SERVER_USERNAME, accessFragment = AccessFragment.GETTER,
         kind = ReferenceHandler.ReferenceKind.ATTRIBUTE)
   String getRepositoryServerUsername();

   /**
    * setter for field repositoryServerUsername
    *
    * @param value new value
    */
   @Local @Property(name = PROPERTY_REPOSITORY_SERVER_USERNAME, accessFragment = AccessFragment.SETTER )
   void setRepositoryServerUsername(String value);

   /**
    * Read property specified with {@link #addToProperties}.
    * @param key qualified property name
    * @return value of the property, null if not set
    */
   String getFromProperties( String key );

   /**
    * @see #addToProperties
    * @return iterator through all property keys
    */
   Iterator<String> keysOfProperties();

   /**
    * @see #addToProperties
    * @return iterator through all properties (key-value-pairs)
    */
   Iterator<Map.Entry<String,String>> iteratorOfProperties();
   
   public abstract boolean hasInProperties (String key);

   /**
    * @see #addToProperties
    * @param key key to be removed
    */
   void removeKeyFromProperties( String key );

   /**
    * Should be called only upon creation of the project.
    *
    * @param rootPackage root package for this project
    */
   void setRootPackage(FPackage rootPackage);

   /**
    * @return true if {@link de.uni_kassel.features.FeatureAccessModule} may be reused.
    * @see de.uni_paderborn.fujaba.versioning.Versioning
    */
   boolean allowsReuseOfFeatureAccessModule();
}

/*
 * $Log$
 * Revision 1.39  2007/04/04 10:49:24  zuendorf
 * Added a create story board command. This command allows to create JUnit tests (soon). Added stereotype support for activities. Added a DiagramCreationHelper for comfortable creation of diagram templates.
 *
 * Revision 1.38  2007/03/26 12:17:34  cschneid
 * deprecation text corrected/improved
 *
 * Revision 1.37  2007/03/15 16:36:27  cschneid
 * - Generating code is possible again
 * - projects failing to load entirely are removed from workspace
 *
 * Revision 1.36  2007/03/15 16:07:09  weisemoeller
 * project specific export folder in the preferences dialog
 *
 * Revision 1.35  2007/03/12 08:29:35  cschneid
 * deprecated FrameMain.getCurrentProject, removed ProjectManager.getCurrentProject (calls to ProjectManager.setCurrentProject can be omitted) and FProject.getCurrentModelRootNode (can be replaced with FrameMain.getCurrentDiagram in most cases)
 *
 * Revision 1.34  2007/02/15 10:00:32  cschneid
 * do not return null in gerFromFactories, load projects with same FeatureAccessModule to speed up loading many projects, new libs
 *
 * Revision 1.33  2007/02/09 15:29:31  cschneid
 * allow loading multiple projects in filechooser, prepend project dependencies instead of appending
 *
 * Revision 1.32  2006/12/19 14:04:14  cschneid
 * annotation completed
 *
 * Revision 1.31  2006/12/06 11:34:41  cschneid
 * ProjectProperty replaced by FProject.properties map
 *
 * Revision 1.30  2006/11/28 14:01:53  cschneid
 * new lib version
 *
 * Revision 1.29  2006/11/23 14:34:43  cschneid
 * updated features.jar, Property moved into Features, some NPE fix, setup updated
 *
 * Revision 1.28  2006/09/30 09:46:00  l3_g5
 * added createFromFactories method
 *
 * Revision 1.27  2006/05/18 12:39:46  lowende
 * Removed some compile warnings.
 *
 * Revision 1.26  2006/05/18 06:44:21  cschneid
 * added @interfaces for annotating properties, work on multi-user features
 *
 * Revision 1.25  2006/05/17 17:44:48  fklar
 * using java 1.5 generics:
 * * adjusted return values of some methods in FProject so they return a parameterized type
 *
 * Revision 1.24  2006/05/05 11:45:47  lowende
 * ASGVisitor returns result now.
 * FProject.getFromModelRootNodes(String key) added.
 * FModelRootNode.getName() added.
 *
 * Revision 1.23  2006/04/25 11:58:24  cschneid
 * added deprecation expiration note, work on versioning
 *
 * Revision 1.22  2006/03/28 18:52:50  fklar
 * introduced constant 'ROOT_PACKAGE_PROPERTY:String' in interface 'FProject'
 *
 * Revision 1.21  2006/03/28 15:20:47  cschneid
 * several fixes for storing/restoring
 *
 * Revision 1.20  2006/03/23 17:22:31  lowende
 * FrameMain.getCurrentProject and getTreeSelection introduced.
 * FProject.getCurrentModelRootNode and setCurrentModelRootNode set to deprecated.
 *
 * Revision 1.19  2006/03/09 10:53:14  lowende
 * Removed FProject.findType.
 *
 */

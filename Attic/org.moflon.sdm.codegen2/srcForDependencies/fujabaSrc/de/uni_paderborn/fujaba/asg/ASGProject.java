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
package de.uni_paderborn.fujaba.asg;


import de.uni_kassel.coobra.Repository;
import de.uni_kassel.features.FeatureAccessModule;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_kassel.util.ConcurrentHashSet;
import de.uni_kassel.util.EmptyIterator;
import de.uni_kassel.util.IteratorWithoutRemove;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FModelRootNode;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.util.FProjectUtility;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.project.FProjectInitializer;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.uni_paderborn.fujaba.versioning.Versioning;
import de.uni_paderborn.fujaba.versioning.properties.Local;
import de.upb.lib.plugins.PluginProperty;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FPropHashSet;
import de.upb.tools.pcs.CollectionChangeEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *           0..1   modelRootNodes   0..n
 * FProject ------------------------------ FModelRootNode
 *           project       modelRootNodes
 *
 *                                       0..1
 * FProject ----------------------------------> FModelRootNode
 *                       currentModelRootNode
 *
 *           0..n   projects   0..1
 * FProject ------------------------ ProjectManager
 *           projects       manager
 *
 *          ---------------        factories   0..1
 * FProject | factoryKind |-------------------------> FFactory<? extends FElement>
 *          ---------------               factories
 *
 *          ------- 0..1   properties   0..1
 * FProject | key |-------------------------- ProjectProperty
 *          ------- project       properties
 * </pre>
 * 
 * @author lowende
 * @author Last Editor: $Author$
 * @version $Revision$ $Date$
 */
public abstract class ASGProject extends ASGElement implements FProject
{
   protected ASGProject()
   {
      this(null);
   }


   /**
    * Create a project while loading.
    * 
    * @param repository repository that is loading the project.
    */
   protected ASGProject(Repository repository)
   {
      this(repository, true);
   }


   protected ASGProject(Repository repository, boolean persistent)
   {
      super(null, persistent);

      if (repository == null && persistent)
      {
         repository = Versioning.get().setupRepositoryForNewProject(true, allowReuseOfFeatureAccessModule());
      }
      this.repository = repository;
      if (persistent)
      {
         Versioning.get().registerPersistentObject(this, this, null);
      }

      addPropertyChangeListener(NAME_PROPERTY, new PropertyChangeListener()
      {
         /**
          * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
          */
         public void propertyChange(PropertyChangeEvent evt)
         {
            if (NAME_PROPERTY.equals(evt.getPropertyName()))
            {
               if (ASGProject.this.repository != null)
               {
                  ASGProject.this.repository.setDescription((String) evt
                        .getNewValue());
               }
            }
         }
      });

      initFactories(persistent);
      
      ProjectManager.get().addToProjects( this );
   }

   private void initFactories(boolean persistent)
   {
      initOwnFactories();
      initForeignFactories();
      createInitialProducts(persistent);
   }

   /**
    * Template method that should be overridden by subclasses.
    * Called in the constructor of ASGProject.
    */
   protected void initOwnFactories()
   {
      // init own factories
   }

   private void initForeignFactories()
   {
      // pass newly-created project to all project initializers
      Iterator<? extends FProjectInitializer> iter = ProjectManager.get().iteratorOfProjectInitializers();
      while(iter.hasNext())
      {
         FProjectInitializer initializer = iter.next();
         initializer.initializeProject(this);
      }
   }

   private void createInitialProducts(boolean persistent)
   {
      // let all factories create their initial products
      Iterator<FFactory<? extends FElement>> factoriesIter = this.iteratorOfFactories();
      while (factoriesIter.hasNext())
      {
         FFactory<? extends FElement> factory = factoriesIter.next();
         factory.createInitialProducts(persistent);
      }
   }

   /**
    * Overridden by subclasses to deny reuse of {@link FeatureAccessModule}.
    * @return true, by default
    */
   protected abstract boolean allowReuseOfFeatureAccessModule();

   public final boolean allowsReuseOfFeatureAccessModule()
   {
      return allowReuseOfFeatureAccessModule();
   }


   /**
    * the repository of the whole project (including all contents).
    */
   final Repository repository;


   /**
    * For persistency and versioning support.
    * 
    * @return the repository of the whole project (including all contents).
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#getRepository()
    */
   public Repository getRepository()
   {
      return repository;
   }


   /**
    * <pre>
    *           0..1   modelRootNodes   0..n
    * FProject ------------------------------ FModelRootNode
    *           project       modelRootNodes
    * </pre>
    */
   private FPropHashSet modelRootNodes;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#addToModelRootNodes(de.uni_paderborn.fujaba.metamodel.common.FModelRootNode)
    */
   public boolean addToModelRootNodes(FModelRootNode value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (value.getProject() != this)
         {
            throw new IllegalArgumentException(
                  "Cannot add root node that is not in this project!");
         }
         if (this.modelRootNodes == null)
         {
            this.modelRootNodes = new FPropHashSet(this,
                  FProject.MODEL_ROOT_NODES_PROPERTY);
         }
         changed = this.modelRootNodes.add(value);
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#hasInModelRootNodes(de.uni_paderborn.fujaba.metamodel.common.FModelRootNode)
    */
   public boolean hasInModelRootNodes(FModelRootNode value)
   {
      return ((this.modelRootNodes != null) && (value != null) && this.modelRootNodes
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#iteratorOfModelRootNodes()
    */
   public Iterator iteratorOfModelRootNodes()
   {
      return ((this.modelRootNodes == null) ? FEmptyIterator.get()
            : this.modelRootNodes.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#getFromModelRootNodes(java.lang.String)
    */
   public FModelRootNode getFromModelRootNodes(String key)
   {
      return FProjectUtility.getFromModelRootNodes(this, key);
   }
   
   
   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#removeAllFromModelRootNodes()
    */
   public void removeAllFromModelRootNodes()
   {
      Iterator iter = this.iteratorOfModelRootNodes();
      while (iter.hasNext())
      {
         FModelRootNode tmpValue = (FModelRootNode) iter.next();
         this.removeFromModelRootNodes(tmpValue);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#removeFromModelRootNodes(de.uni_paderborn.fujaba.metamodel.common.FModelRootNode)
    */
   public boolean removeFromModelRootNodes(FModelRootNode value)
   {
      boolean changed = false;
      if ((this.modelRootNodes != null) && (value != null))
      {
         changed = this.modelRootNodes.remove(value);
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#sizeOfModelRootNodes()
    */
   public int sizeOfModelRootNodes()
   {
      return ((this.modelRootNodes == null) ? 0 : this.modelRootNodes.size());
   }


   /**
    * <pre>
    *          ---------------        factories   0..1
    * FProject | factoryKind |-------------------------> FFactory<? extends FElement>
    *          ---------------               factories
    * </pre>
    */
   private transient Map<String, FFactory<? extends FElement>> factories;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#addToFactories(de.uni_paderborn.fujaba.metamodel.factories.FFactory)
    */
   public <I extends FElement> boolean addToFactories(FFactory<I> value)
   {
      if (value == null)
      {
         throw new IllegalArgumentException("Argument must not be null.");
      }

      Class<I> interfaceClass = value.getInterfaceClass();
      Class<? extends I> concreteClass = value.getConcreteClass();

      if (hasKeyInFactories(interfaceClass) || hasKeyInFactories(concreteClass))
      {
         throw new IllegalStateException(
               "There is already a factory registered for '" + interfaceClass
                     + "' and/or '" + concreteClass + "'.");
      }

      if (this.factories == null)
      {
         // no need to fire events
         this.factories = new ConcurrentHashMap<String, FFactory<? extends FElement>>();
      }

      boolean changed = false;

      if (interfaceClass != null)
      {
         this.factories.put(interfaceClass.getName(), value);
         changed = true;
      }
      if (concreteClass != null)
      {
         if (concreteClass != interfaceClass)
         {
            this.factories.put(concreteClass.getName(), value);
            changed = true;
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#getFromFactories(java.lang.Class)
    */
   @SuppressWarnings( { "unchecked" })
   public <I extends FElement> FFactory<I> getFromFactories(Class<I> key)
   {
      if ((this.factories == null) || (key == null))
      {
         return new EmptyFactory<I>(this, key);
      }
      else
      {
         FFactory<I> factory = (FFactory<I>) this.factories.get(key.getName());
         if (factory != null)
         {
            return factory;
         } else
         {
            return new EmptyFactory<I>(this, key);
         }
      }
   }


   public <I extends FElement> I createFromFactories(Class<I> key, boolean persistent)
   {
      FFactory<I> factory = getFromFactories(key);
      return factory.create (persistent);
   }


   public <I extends FElement> I createFromFactories(Class<I> key)
   {
   	return createFromFactories(key, true);
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#hasInFactories
    */
   public <I extends FElement> boolean hasInFactories(Class<I> key,
         FFactory<I> value)
   {
      return ((this.factories != null)
            && (value != null || this.factories.containsKey(key.getName()))
            && (key != null) && (this.factories.get(key.getName()) == value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#hasInFactories(de.uni_paderborn.fujaba.metamodel.factories.FFactory)
    */
   public <I extends FElement> boolean hasInFactories(FFactory<I> value)
   {
      return ((this.factories != null) && this.factories.containsValue(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#hasKeyInFactories
    */
   public <I extends FElement> boolean hasKeyInFactories(Class<I> key)
   {
      return ((this.factories != null) && (key != null) && this.factories
            .containsKey(key.getName()));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#iteratorOfFactories()
    */
   public Iterator<FFactory<? extends FElement>> iteratorOfFactories()
   {
      if (this.factories == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.factories.values().iterator();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#removeFromFactories(de.uni_paderborn.fujaba.metamodel.factories.FFactory)
    */
   public boolean removeFromFactories(FFactory<? extends FElement> value)
   {
      boolean changed = false;
      if (this.factories != null)
      {
         Iterator<Map.Entry<String, FFactory<? extends FElement>>> iter = entriesOfFactories();
         while (iter.hasNext())
         {
            Map.Entry<String, FFactory<? extends FElement>> entry = iter.next();

            FFactory<? extends FElement> factory = entry.getValue();
            if (factory == value)
            {
               if (this.removeFromFactories(entry.getKey(), factory))
               {
                  changed = true;

                  if (!hasInFactories(factory))
                  {
                     factory.removeYou();
                  }
               }
            }
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#removeKeyFromFactories
    */
   @SuppressWarnings( { "unchecked" })
   public <I extends FElement> boolean removeKeyFromFactories(Class<I> key)
   {
      boolean changed = false;
      if ((this.factories != null) && (key != null))
      {
         changed = this.factories.containsKey(key.getName());
         if (changed)
         {
            FFactory<I> factory = (FFactory<I>) this.factories.remove(key
                  .getName());

            if (!hasInFactories(factory))
            {
               factory.removeYou();
            }
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#removeAllFromFactories()
    */
   public void removeAllFromFactories()
   {
      Iterator<Map.Entry<String, FFactory<? extends FElement>>> iter = entriesOfFactories();
      while (iter.hasNext())
      {
         Map.Entry<String, FFactory<? extends FElement>> entry = iter.next();
         removeFromFactories(entry.getKey(), entry.getValue());
         entry.getValue().removeYou();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#getFromFactories(java.lang.String)
    * 
    * @deprecated (gets deleted in 5.1)
    */
   public FFactory getFromFactories(String key)
   {
      if ((this.factories == null) || (key == null))
      {
         return null;
      }
      else
      {
         return this.factories.get(key);
      }
   }


   private Iterator<Map.Entry<String, FFactory<? extends FElement>>> entriesOfFactories()
   {
      if (this.factories == null)
      {
         return FEmptyIterator.get();
      }
      else
      {
         return this.factories.entrySet().iterator();
      }
   }


   private <I extends FElement> boolean removeFromFactories(String key,
         FFactory<I> value)
   {
      boolean changed = false;
      if ((this.factories != null) && (key != null))
      {
         FFactory oldValue = this.factories.get(key);
         if (oldValue == value
               && (oldValue != null || this.factories.containsKey(key)))
         {
            this.factories.remove(key);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * <pre>
    *           0..n   projects   0..1
    * FProject ------------------------ ProjectManager
    *           projects       manager
    * </pre>
    */
   private transient ProjectManager manager;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#getManager()
    */
   public ProjectManager getManager()
   {
      return this.manager;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#setManager(de.uni_paderborn.fujaba.project.ProjectManager)
    */
   public boolean setManager(ProjectManager value)
   {
      boolean changed = false;
      if (this.manager != value)
      {
         ProjectManager oldValue = this.manager;

         if (this.manager != null)
         {
            this.manager = null;
            oldValue.removeFromProjects(this);
         }
         this.manager = value;
         if (value != null)
         {
            value.addToProjects(this);
         }
         changed = true;
         firePropertyChange(FProject.MANAGER_PROPERTY, oldValue, value);
      }
      return changed;
   }

   private Map<String, String> properties;

   public void addToProperties(String key, String value)
   {
      if (key != null)
      {
         if (value != null)
         {
            if (properties == null)
            {
               properties = new ConcurrentHashMap<String, String>();
            }
            String oldValue = properties.put(key, value);
            firePropertyChange( CollectionChangeEvent.get( this, PROPERTIES_PROPERTY, properties, oldValue, value, key,
                  CollectionChangeEvent.CHANGED ) );
         } else if (properties != null)
         {
            String oldValue = properties.remove(key);
            firePropertyChange( CollectionChangeEvent.get( this, PROPERTIES_PROPERTY, properties, oldValue, null, key,
                  CollectionChangeEvent.REMOVED ) );
         }
      }
   }

   public void removeKeyFromProperties( String key ) {
      addToProperties( key, null );
   }

   public String getFromProperties( String key ) {
      return properties != null ? properties.get( key ) : null;
   }

   public Iterator<String> keysOfProperties() {
      if ( properties != null ) {
         return new IteratorWithoutRemove<String>( properties.keySet().iterator() );
      } else {
         return EmptyIterator.get();
      }
   }


   public Iterator<Map.Entry<String,String>> iteratorOfProperties() {
      if ( properties != null ) {
         return new IteratorWithoutRemove<Map.Entry<String,String>>( properties.entrySet().iterator() );
      } else {
         return EmptyIterator.get();
      }
   }

   public boolean hasInProperties (String key) {
      return properties != null && properties.containsKey(key);
   }

   /**
    * Required projects.
    */
   private Set<FProject> requires;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#addToRequires(de.uni_paderborn.fujaba.metamodel.common.FProject)
    */
   public boolean addToRequires(FProject value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.requires == null)
         {
            this.requires = new ConcurrentHashSet<FProject>();
         }
         changed = this.requires.add(value);
         if (changed)
         {
            firePropertyChange(REQUIRES_PROPERTY, null, value);
            value.addToRequiredBy(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#hasInRequires(de.uni_paderborn.fujaba.metamodel.common.FProject)
    */
   public boolean hasInRequires(FProject value)
   {
      return ((this.requires != null) && (value != null) && this.requires
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#iteratorOfRequires()
    */
   public Iterator<FProject> iteratorOfRequires()
   {
      if (this.requires == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.requires.iterator();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#removeAllFromRequires()
    */
   public void removeAllFromRequires()
   {
      FProject tmpValue;
      Iterator<FProject> iter = this.iteratorOfRequires();
      while (iter.hasNext())
      {
         tmpValue = iter.next();
         this.removeFromRequires(tmpValue);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#removeFromRequires(de.uni_paderborn.fujaba.metamodel.common.FProject)
    */
   public boolean removeFromRequires(FProject value)
   {
      boolean changed = false;
      if ((this.requires != null) && (value != null))
      {
         changed = this.requires.remove(value);
         if (changed)
         {
            // todo: check if there still are references to the other project
         }
         if (changed)
         {
            firePropertyChange(REQUIRES_PROPERTY, value, null);
            value.removeFromRequiredBy(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#sizeOfRequires()
    */
   public int sizeOfRequires()
   {
      return ((this.requires == null) ? 0 : this.requires.size());
   }

   /**
    * list of projects depending on this project.
    */
   private transient Set<FProject> requiredBy;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#addToRequiredBy(de.uni_paderborn.fujaba.metamodel.common.FProject)
    */
   public boolean addToRequiredBy(FProject value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.requiredBy == null)
         {
            this.requiredBy = new ConcurrentHashSet<FProject>();
         }
         changed = this.requiredBy.add(value);
         if (changed)
         {
            firePropertyChange(REQUIRED_BY_PROPERTY, null, value);
            value.addToRequires(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#hasInRequiredBy(de.uni_paderborn.fujaba.metamodel.common.FProject)
    */
   public boolean hasInRequiredBy(FProject value)
   {
      return ((this.requiredBy != null) && (value != null) && this.requiredBy
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#iteratorOfRequiredBy()
    */
   public Iterator<FProject> iteratorOfRequiredBy()
   {
      if (this.requiredBy == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.requiredBy.iterator();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#removeAllFromRequiredBy()
    */
   public void removeAllFromRequiredBy()
   {
      FProject tmpValue;
      Iterator<FProject> iter = this.iteratorOfRequiredBy();
      while (iter.hasNext())
      {
         tmpValue = iter.next();
         this.removeFromRequiredBy(tmpValue);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#removeFromRequiredBy(de.uni_paderborn.fujaba.metamodel.common.FProject)
    */
   public boolean removeFromRequiredBy(FProject value)
   {
      boolean changed = false;
      if ((this.requiredBy != null) && (value != null))
      {
         changed = this.requiredBy.remove(value);
         if (changed)
         {
            firePropertyChange(REQUIRED_BY_PROPERTY, value, null);
            value.removeFromRequires(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#sizeOfRequiredBy()
    */
   public int sizeOfRequiredBy()
   {
      return ((this.requiredBy == null) ? 0 : this.requiredBy.size());
   }


   /**
    * store required plugins.
    */
   private Map<String, PluginProperty> requiredPlugins = new HashMap<String, PluginProperty>();


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#addToRequiredPlugins(de.upb.lib.plugins.PluginProperty)
    */
   public boolean addToRequiredPlugins(PluginProperty plugin)
   {
      boolean changed = requiredPlugins.put(plugin.getPluginID(), plugin) == null;
      if (changed)
      {
         firePropertyChange(REQUIRED_PLUGINS_PROPERTY, null, plugin);
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#removeFromRequiredPlugins(de.upb.lib.plugins.PluginProperty)
    */
   public boolean removeFromRequiredPlugins(PluginProperty plugin)
   {
      boolean changed = requiredPlugins.remove(plugin.getPluginID()) != null;
      if (changed)
      {
         firePropertyChange(REQUIRED_PLUGINS_PROPERTY, plugin, null);
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FProject#iteratorOfRequiredPlugins()
    */
   public Iterator<PluginProperty> iteratorOfRequiredPlugins()
   {
      if (requiredPlugins == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return requiredPlugins.values().iterator();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      return null;
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      ProjectManager tmpManager = getManager();
      if (tmpManager != null)
      {
         setManager(null);
      }

      removeAllFromModelRootNodes();
      removeAllFromFactories();
      removeAllFromRequiredBy();
      removeAllFromRequires();

      super.removeYou();
   }



   /**
    * store the value for field repositoryServer
    */
   @Property(name = PROPERTY_REPOSITORY_SERVER, accessFragment = AccessFragment.FIELD_STORAGE )
   private String repositoryServer;

   public String getRepositoryServer() {
        return this.repositoryServer;
    }

   public void setRepositoryServer(final String value) {
        final String oldValue = this.repositoryServer;
        if (oldValue != null ? (!oldValue.equals(value)) : value != null) {
            this.repositoryServer = value;
            firePropertyChange(PROPERTY_REPOSITORY_SERVER, oldValue, value);
        }
    }


   /**
    * store the value for field repositoryServerUsername
    */
   @Local @Property(name = PROPERTY_REPOSITORY_SERVER_USERNAME, accessFragment = AccessFragment.FIELD_STORAGE )
   private String repositoryServerUsername;

   public String getRepositoryServerUsername() {
        return this.repositoryServerUsername;
    }

   public void setRepositoryServerUsername(final String value) {
        final String oldValue = this.repositoryServerUsername;
        if (oldValue != null ? (!oldValue.equals(value)) : value != null) {
            this.repositoryServerUsername = value;
            firePropertyChange(PROPERTY_REPOSITORY_SERVER_USERNAME, oldValue, value);
        }
    }

   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      return getName();
   }


   @Override
   public String toString()
  {
     return getName();
  }

}

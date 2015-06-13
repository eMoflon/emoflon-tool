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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_kassel.properties.ObjectInspector;
import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.basic.BasicIncrement;
import de.uni_paderborn.fujaba.basic.FujabaPropertyChangeSupport;
import de.uni_paderborn.fujaba.metamodel.common.FAnnotation;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FElementRef;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FCardinality;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.metamodel.structure.util.FPackageUtility;
import de.uni_paderborn.fujaba.project.FPRCommon;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.uni_paderborn.fujaba.uml.factories.UMLFlyweightFactory;
import de.uni_paderborn.fujaba.uml.factories.UMLHeavyweightFactory;
import de.uni_paderborn.fujaba.versioning.Versioning;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashMap;
import de.upb.tools.fca.FHashSet;
import de.upb.tools.fca.FPropHashMap;
import de.upb.tools.fca.FPropLinkedList;
import de.upb.tools.fca.FTreeSet;


/**
 * This is the base class for all model elements contained in the abstract syntax graph.
 * 
 * <h2>Associations</h2>
 * 
 * <pre>
 *                    project    0..1
 * ASGElement -----------------------> ASGProject
 *                            project
 *
 *             0..n    hasElements    0..n
 * ASGElement ----------------------------- ASGDiagram
 *             elements           diagrams
 *
 *            -------------- 0..1     hasElementReferences    0..1
 * ASGElement | getClass() |--------------------------------------- ASGElementRef
 *            -------------- element             elementReferences
 *
 *               ------- 0..n   annotations   0..n
 * ASGAnnotation | key |--------------------------- ASGElement
 *               ------- annotations      elements
 *
 *             0..1                        0..1
 * ASGElement ---------------------------------- OOGenToken
 *             asgElement       firstOOGenToken
 *
 *             0..1                           0..1
 * ASGElement ------------------------------------- OOGenToken
 *             lastUmlIncrement     lastOOGenToken
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$
 */
public abstract class ASGElement extends BasicIncrement implements FElement
{
   /**
    * set to false of object is not stored.
    */
   private final transient boolean persistent;

   /**
    * If this attribute is set, every created ASGElement is created transient.
    */
   private static boolean inTransientMode = false;

   private final String factoryKey;


   /**
    * Constructor for class ASGElement Does not use a factoryKey.
    *
    * @param project
    * @param persistent true to store the object (default)
    * @see #ASGElement(de.uni_paderborn.fujaba.metamodel.common.FProject,boolean,String)
    */
   protected ASGElement(FProject project, boolean persistent)
   {
      this(project, persistent, null);
   }


   /**
    * Constructor for class ASGElement
    *
    * @param project
    * @param persistent true to store the object (default)
    * @param factoryKey the key used to retrieve the created instance from the factory
    */
   protected ASGElement(FProject project, boolean persistent, String factoryKey)
   {
      this.factoryKey = factoryKey;
      this.persistent = persistent;

      if (project == null)
      {
         if (!(this instanceof FProject))
         {
            throw new IllegalArgumentException("Project must not be null!");
         }
         else
         {
            project = (FProject) this;
         }
      }

      this.elementProject = project;

      if (!persistent)
      {
         // ignore object
         addToTransientElements();
      }
      else
      {
         Versioning.get().registerPersistentObject(this.getProject(), this,
               factoryKey);

         // when repository is deactivated we need the listener to change the
         // {@link FProject#setSaved} property
         if ( !Versioning.get().isEnabled() ) {
            getPropertyChangeSupport().addPropertyChangeListener( SET_SAVED_ELEMENT_LISTENER );
         }
      }

      if (additionalListeners != null)
      {
         getPropertyChangeSupport();
      }
   }


   private final FProject elementProject;


   /**
    * Query the project this element belongs to. It is vital to the versioning mechanism that the
    * project is set and does not change. That's why this method used to be final. Final does not
    * prevent project from being changed. But plugins might want to restrict the return type. So I
    * removed final [tr].
    *
    * @return project this element belongs to
    */
   public FProject getProject()
   {
      return this.elementProject;
   }


   /**
    * Query if this object is stored or just a temporary one.
    *
    * @return true if the object is stored (default for most objects)
    */
   public boolean isPersistent()
   {
      return this.persistent;
   }

   /**
    * @see de.uni_paderborn.fujaba.basic.UniqueIdentifier#getID()
    * @throws UnsupportedOperationException If versioning is activated, but no ID is found for this element 
    */
   @Override
   @NoProperty
   public String getID() throws UnsupportedOperationException
   {
      String iD = FPRCommon.getID(this);
      if (iD != null)
      {
         return iD;
      } else
      {
         Repository repository = getProject().getRepository();
         if (repository != null)
         {
            IdentifierModule identifierModule = repository.getIdentifierModule();
            Object id = (identifierModule == null) ? null : identifierModule.getID(this);
            if (id == null) 
            {
              if (isPersistent())
              {
                 throw new UnsupportedOperationException("Versioning is activated but no ID was found for this object!");
              }
              else
              {
                 id = super.getID();
              }
            }
            return id.toString();
         }
         return super.getID();
      }
   }

   /**
    * <pre>
    *            0..n    hasElements    0..n
    * ASGElement --------------------------- ASGDiagram
    *            elements           diagrams
    * </pre>
    */
   @Property( name = DIAGRAMS_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         partner = FDiagram.ELEMENTS_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private List<FDiagram> diagrams = null;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#sizeOfDiagrams()
    */
   public int sizeOfDiagrams()
   {
      return ((diagrams == null) ? 0 : diagrams.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#hasInDiagrams(de.uni_paderborn.fujaba.metamodel.common.FDiagram)
    */
   public boolean hasInDiagrams(FDiagram diagram)
   {
      return (this.diagrams != null && this.diagrams.contains(diagram));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#iteratorOfDiagrams()
    */
   public Iterator<? extends FDiagram> iteratorOfDiagrams()
   {
      if (this.diagrams == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.diagrams.iterator();
      }
   }
   
   public FDiagram getFirstFromDiagrams()
   {
   	Iterator<? extends FDiagram> iter = this.iteratorOfDiagrams();
   	if (iter.hasNext())
   	{
   		return iter.next();
   	}
   	else
   	{
   		return null;
   	}
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#addToDiagrams(de.uni_paderborn.fujaba.metamodel.common.FDiagram)
    */
   public void addToDiagrams(FDiagram diagram)
   {
      if (diagram != null && !hasInDiagrams(diagram))
      {
         if (this.diagrams == null)
         {
            this.diagrams = new FPropLinkedList(this, DIAGRAMS_PROPERTY);
         }
         diagrams.add(diagram);
         diagram.addToElements(this);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#removeFromDiagrams(de.uni_paderborn.fujaba.metamodel.common.FDiagram)
    */
   public void removeFromDiagrams(FDiagram diagram)
   {
      if (this.hasInDiagrams(diagram))
      {
         this.diagrams.remove(diagram);
         diagram.removeFromElements(this);
         ASGDiagram asgDiagram = (ASGDiagram) diagram;
         ASGUnparseInformation unparseInformation = getFromUnparseInformations(asgDiagram);
         if (unparseInformation != null)
         {
            unparseInformation.removeYou();
         }
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#removeAllFromDiagrams()
    */
   public void removeAllFromDiagrams()
   {
      Iterator iter = iteratorOfDiagrams();

      while (iter.hasNext())
      {
         ASGDiagram item = (ASGDiagram) iter.next();
         removeFromDiagrams(item);
      }
   }


   /**
    * <pre>
    *            -------------- 0..1      hasReferences       0..1
    * ASGElement | getClass() |------------------------------------ ASGElementRef
    *            -------------- element          elementReferences
    * </pre>
    */
   private transient Map<String, FElementRef> elementReferences;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#hasInElementReferences(de.uni_paderborn.fujaba.metamodel.common.FElementRef)
    */
   public boolean hasInElementReferences(FElementRef value)
   {
      return ((this.elementReferences != null) && (value != null) && this.elementReferences
            .containsValue(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#hasInElementReferences(java.lang.String,
    *      de.uni_paderborn.fujaba.metamodel.common.FElementRef)
    */
   public boolean hasInElementReferences(String key, FElementRef value)
   {
      return ((this.elementReferences != null) && (value != null)
            && (key != null) && (this.elementReferences.get(key) == value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#hasKeyInElementReferences(java.lang.String)
    */
   public boolean hasKeyInElementReferences(String key)
   {
      return ((this.elementReferences != null) && (key != null) && this.elementReferences
            .containsKey(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#iteratorOfElementReferences()
    */
   public Iterator<? extends FElementRef> iteratorOfElementReferences()
   {
      if (this.elementReferences == null)
      {
         return EmptyIterator.get();
      } else
      {
         return this.elementReferences.values().iterator();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#keysOfElementReferences()
    */
   public Iterator keysOfElementReferences()
   {
      return ((this.elementReferences == null) ? FEmptyIterator.get()
            : this.elementReferences.keySet().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#entriesOfElementReferences()
    */
   public Iterator entriesOfElementReferences()
   {
      return ((this.elementReferences == null) ? FEmptyIterator.get()
            : this.elementReferences.entrySet().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#sizeOfElementReferences()
    */
   public int sizeOfElementReferences()
   {
      return ((this.elementReferences == null) ? 0 : this.elementReferences
            .size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getFromElementReferences(java.lang.String)
    */
   public ASGElementRef getFromElementReferences(String key)
   {
      return (((this.elementReferences == null) || (key == null)) ? null
            : (ASGElementRef) this.elementReferences.get(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#addToElementReferences(java.lang.String,
    *      de.uni_paderborn.fujaba.metamodel.common.FElementRef)
    */
   
   public boolean addToElementReferences(String key, FElementRef value)
   {
      boolean changed = false;
      if ((value != null) && (key != null))
      {
         if (this.elementReferences == null)
         {
            this.elementReferences = new FPropHashMap(this,
                  ELEMENT_REFERENCES_PROPERTY);
         }
         ASGElementRef oldValue = (ASGElementRef) this.elementReferences.put(
               key, value);
         if (oldValue != value)
         {
            if (oldValue != null)
            {
               oldValue.setElementWithKey(null, null);
            }
            ((ASGElementRef) value).setElementWithKey(key, this);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#addToElementReferences(java.util.Map.Entry)
    */
   public boolean addToElementReferences(java.util.Map.Entry entry)
   {
      return addToElementReferences((String) entry.getKey(),
            (ASGElementRef) entry.getValue());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#removeFromElementReferences(de.uni_paderborn.fujaba.metamodel.common.FElementRef)
    */
   public boolean removeFromElementReferences(FElementRef value)
   {
      boolean changed = false;
      if ((this.elementReferences != null) && (value != null))
      {
         Iterator iter = this.entriesOfElementReferences();
         while (iter.hasNext())
         {
            Map.Entry entry = (Map.Entry) iter.next();
            if (entry.getValue() == value)
            {
               changed = changed
                     || this.removeFromElementReferences((String) entry
                           .getKey(), value);
            }
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#removeFromElementReferences(java.lang.String,
    *      de.uni_paderborn.fujaba.metamodel.common.FElementRef)
    */
   public boolean removeFromElementReferences(String key, FElementRef value)
   {
      boolean changed = false;
      if ((this.elementReferences != null) && (value != null) && (key != null))
      {
         ASGElementRef oldValue = (ASGElementRef) this.elementReferences
               .get(key);
         if (oldValue == value)
         {
            this.elementReferences.remove(key);
            value.removeYou();
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#removeKeyFromElementReferences(java.lang.String)
    */
   public boolean removeKeyFromElementReferences(String key)
   {
      boolean changed = false;
      if ((this.elementReferences != null) && (key != null))
      {
         ASGElementRef tmpValue = (ASGElementRef) this.elementReferences
               .get(key);
         if (tmpValue != null)
         {
            this.elementReferences.remove(key);
            tmpValue.setElementWithKey(null, null);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#removeAllFromElementReferences()
    */
   public void removeAllFromElementReferences()
   {
      Iterator iter = entriesOfElementReferences();
      while (iter.hasNext())
      {
         Map.Entry entry = (Map.Entry) iter.next();
         removeFromElementReferences((String) entry.getKey(),
               (ASGElementRef) entry.getValue());
      }
   }


   /**
    * <pre>
    *               ------- 0..n   Annotations   0..n
    * ASGAnnotation | key |--------------------------- ASGElement
    *               ------- annotations      elements
    * </pre>
    */
   private Set<FAnnotation> annotations;


   /**
    * Access method for an one to n association.
    *
    * @param key The object added.
    * @param value The object added.
    * @return No description provided
    */
   public boolean addToAnnotations(String key, FAnnotation value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.annotations == null)
         {
            this.annotations = new FHashSet();
         }
         changed = this.annotations.add(value);
         if (changed)
         {
            value.addToElements(key, this);
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return No description provided
    */
   public Iterator<? extends FAnnotation> iteratorOfAnnotations()
   {
      if (this.annotations == null)
      {
         return EmptyIterator.get();
      } else
      {
         return this.annotations.iterator();
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value No description provided
    * @return No description provided
    */
   public boolean hasInAnnotations(FAnnotation value)
   {
      return ((this.annotations != null) && (value != null) && this.annotations
            .contains(value));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return No description provided
    */
   public int sizeOfAnnotations()
   {
      return ((this.annotations == null) ? 0 : this.annotations.size());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param key No description provided
    * @param value No description provided
    * @return No description provided
    */
   public boolean removeFromAnnotations(String key, FAnnotation value)
   {
      boolean changed = false;
      if ((this.annotations != null) && (value != null))
      {
         changed = this.annotations.remove(value);
         if (changed)
         {
            value.removeFromElements(key, this);
         }
      }
      return changed;
   }


   public void removeAllFromAnnotations()
   {
      Iterator iter = this.iteratorOfAnnotations();
      while (iter.hasNext())
      {
         ASGAnnotation tmpValue = (ASGAnnotation) iter.next();
         tmpValue.removeFromElements(this);
      }
   }


   /**
    * map from ASGElement (parent key) to ASGUnparseInformation
    */
   public final static String UNPARSE_INFORMATIONS_PROPERTY = "unparseInformations";


   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   private FHashMap unparseInformations;


   /**
    * @param key to be removed
    * @param value information
    * @return true when something was changed
    * @see #addToUnparseInformations(ASGElement, ASGUnparseInformation)
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY, partner=ASGUnparseInformation.ASGELEMENT_PROPERTY, kind=ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean removeFromUnparseInformations(ASGElement key,
         ASGUnparseInformation value)
   {
      boolean changed = false;
      if ((this.unparseInformations != null) && (value != null)
            && (key != null))
      {

         ASGUnparseInformation oldValue = (ASGUnparseInformation) this.unparseInformations
               .remove(key);
         if (oldValue != null)
         {
            value.setASGElement(null, null);
         }
         changed = true;
      }
      return changed;
   }


   /**
    * @param key key to be removed
    * @return true when something was changed
    * @see #addToUnparseInformations(ASGElement, ASGUnparseInformation)
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   public boolean removeKeyFromUnparseInformations(ASGElement key)
   {
      boolean changed = false;
      if ((this.unparseInformations != null) && (key != null))
      {
         ASGUnparseInformation oldValue = (ASGUnparseInformation) this.unparseInformations
               .remove(key);
         if (oldValue != null)
         {
            oldValue.setASGElement(null, null);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @param value information to be removed
    * @return true when something was changed
    * @see #addToUnparseInformations(ASGElement, ASGUnparseInformation)
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   public boolean removeFromUnparseInformations(ASGUnparseInformation value)
   {
      boolean changed = false;
      if ((this.unparseInformations != null) && (value != null))
      {
         Iterator iter = this.entriesOfUnparseInformations();
         while (iter.hasNext())
         {
            Map.Entry entry = (Map.Entry) iter.next();
            if (entry.getValue() == value)
            {
               changed = changed
                     || this.removeFromUnparseInformations((ASGElement) entry
                           .getKey(), value);
            }
         }
      }
      return changed;
   }


   /**
    * remove all
    *
    * @see #addToUnparseInformations(ASGElement, ASGUnparseInformation)
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   public void removeAllFromUnparseInformations()
   {
      Iterator iter = entriesOfUnparseInformations();
      while (iter.hasNext())
      {
         Map.Entry entry = (Map.Entry) iter.next();
         ASGUnparseInformation unparseInfo = (ASGUnparseInformation) entry
               .getValue();
         removeFromUnparseInformations((ASGElement) entry.getKey(), unparseInfo);
         unparseInfo.removeYou();
      }
   }


   /**
    * @return No description provided
    * @see #addToUnparseInformations(ASGElement, ASGUnparseInformation)
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   public Iterator keysOfUnparseInformations()
   {
      return ((this.unparseInformations == null) ? FEmptyIterator.get()
            : this.unparseInformations.keySet().iterator());
   }


   /**
    * @return No description provided
    * @see #addToUnparseInformations(ASGElement, ASGUnparseInformation)
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   public Iterator entriesOfUnparseInformations()
   {
      return ((this.unparseInformations == null) ? FEmptyIterator.get()
            : this.unparseInformations.entrySet().iterator());
   }


   /**
    * add an information about unparsing of this ASGElement
    *
    * @param key parent of the ASGElement regarding this unparse information
    * @param value information
    * @return true when information was added
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   public boolean addToUnparseInformations(ASGElement key,
         ASGUnparseInformation value)
   {
      boolean changed = false;
      if ((value != null) && (key != null))
      {
         if (this.unparseInformations == null)
         {
            this.unparseInformations = new FPropHashMap(this,
                  "unparseInformations");
         }
         ASGUnparseInformation oldValue = (ASGUnparseInformation) this.unparseInformations
               .get(key);
         if (oldValue != value)
         {
            this.unparseInformations.put(key, value);

            if (oldValue != null)
            {
               oldValue.setASGElement(null, null);
               // nobody will miss this
               oldValue.removeYou();
            }
            value.setASGElement(key, this);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @param entry what to add
    * @return true when entry was added
    * @see #addToUnparseInformations(ASGElement, ASGUnparseInformation)
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   public boolean addToUnparseInformations(java.util.Map.Entry entry)
   {
      return addToUnparseInformations((ASGElement) entry.getKey(),
            (ASGUnparseInformation) entry.getValue());
   }


   /**
    * @return iterator through all keys (parents) in uparseInformations
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   public Iterator iteratorOfKeyFromUnparseInformations()
   {
      if (unparseInformations != null)
      {
         return unparseInformations.keySet().iterator();
      }
      else
      {
         return FEmptyIterator.get();
      }
   }


   /**
    * @return iterator through all entries in uparseInformations
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   public Iterator iteratorOfUnparseInformations()
   {
      if (unparseInformations != null)
      {
         return unparseInformations.entrySet().iterator();
      }
      else
      {
         return FEmptyIterator.get();
      }
   }


   /**
    * @param key parent
    * @return unparse information
    */
   @Property(name=UNPARSE_INFORMATIONS_PROPERTY)
   public ASGUnparseInformation getFromUnparseInformations(ASGElement key)
   {
      if (unparseInformations != null)
      {
         return (ASGUnparseInformation) unparseInformations.get(key);
      }
      else
      {
         return null;
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeObsoleteUnparseInformation()
   {
      Iterator iter = iteratorOfKeyFromUnparseInformations();
      while (iter.hasNext())
      {
         // remove unparse information for diagrams that are not in the project
         ASGElement asgElement = (ASGElement) iter.next();
         if (asgElement instanceof ASGDiagram)
         {
            ASGDiagram asgDiagram = (ASGDiagram) asgElement;
            if (!getProject().hasInModelRootNodes(asgDiagram))
            {
               ASGUnparseInformation unparseInformation = getFromUnparseInformations(asgDiagram);
               if (unparseInformation != null)
               {
                  unparseInformation.removeYou();
               }
               continue;
            }
         }

         // remove empty ASGUnparseInformation (for old projects)
         ASGUnparseInformation unparseInformation = getFromUnparseInformations(asgElement);
         if (unparseInformation.sizeOfASGInformation() == 0)
         {
            unparseInformation.removeYou();
         }
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private transient FujabaPropertyChangeSupport propertyChangeSupport = null;


   /**
    * Get the propertyChangeSupport attribute of the ASGElement object
    *
    * @return The propertyChangeSupport value
    */
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      if (propertyChangeSupport == null)
      {
         propertyChangeSupport = new FujabaPropertyChangeSupport(this);

         if (additionalListeners != null)
         {

            for (Object additionalListener : additionalListeners)
            {
               PropertyChangeListener listener = (PropertyChangeListener) additionalListener;
               addPropertyChangeListener(listener);
            }

         }
      }
      return propertyChangeSupport;
   }


   /**
    * Access method for an one to n association.
    *
    * @param listener The object added.
    */
   public void addToPropertyChangeListeners(PropertyChangeListener listener)
   {
      addPropertyChangeListener(listener);
   }


   /**
    * Access method for an one to n association.
    *
    * @param propertyName The object added.
    * @param listener The object added.
    */
   public void addToPropertyChangeListeners(String propertyName,
         PropertyChangeListener listener)
   {
      addPropertyChangeListener(propertyName, listener);
   }


   /**
    * Access method for an one to n association.
    *
    * @param listener The object added.
    */
   public void addPropertyChangeListener(PropertyChangeListener listener)
   {
      getPropertyChangeSupport().addPropertyChangeListener(listener);
   }


   /**
    * Access method for an one to n association.
    *
    * @param propertyName The object added.
    * @param listener The object added.
    */
   public void addPropertyChangeListener(String propertyName,
         PropertyChangeListener listener)
   {
      getPropertyChangeSupport().addPropertyChangeListener(propertyName,
            listener);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param listener No description provided
    */
   public void removeFromPropertyChangeListeners(PropertyChangeListener listener)
   {
      removePropertyChangeListener(listener);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param propertyName No description provided
    * @param listener No description provided
    */
   public void removeFromPropertyChangeListeners(String propertyName,
         PropertyChangeListener listener)
   {
      removePropertyChangeListener(propertyName, listener);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param listener No description provided
    */
   public void removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (propertyChangeSupport != null)
      {
         propertyChangeSupport.removePropertyChangeListener(listener);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param propertyName No description provided
    * @param listener No description provided
    */
   public void removePropertyChangeListener(String propertyName,
         PropertyChangeListener listener)
   {
      if (propertyChangeSupport != null)
      {
         propertyChangeSupport.removePropertyChangeListener(propertyName,
               listener);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param event No description provided
    */
   public void fireEvent(PropertyChangeEvent event)
   {
      firePropertyChange(event);
   }


   /**
    * fires property change events via the property change support.
    *
    * @param e what to be fired
    */
   protected void firePropertyChange(PropertyChangeEvent e)
   {
      if (propertyChangeSupport != null)
      {
         propertyChangeSupport.firePropertyChange(e);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param name No description provided
    * @param oldValue No description provided
    * @param newValue No description provided
    */
   protected void firePropertyChange(String name, Object oldValue,
         Object newValue)
   {
      if (oldValue == newValue)
      {
         return;
      }

      firePropertyChange(new PropertyChangeEvent(this, name, oldValue, newValue));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param name No description provided
    * @param oldValue No description provided
    * @param newValue No description provided
    */
   protected void firePropertyChange(String name, boolean oldValue,
         boolean newValue)
   {
      if (oldValue == newValue || propertyChangeSupport == null)
      {
         return;
      }

      firePropertyChange(name, Boolean.valueOf(oldValue), Boolean
            .valueOf(newValue));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param name No description provided
    * @param oldValue No description provided
    * @param newValue No description provided
    */
   protected void firePropertyChange(String name, int oldValue, int newValue)
   {
      if (oldValue == newValue)
      {
         return;
      }
      firePropertyChange(name, new Integer(oldValue), new Integer(newValue));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param name No description provided
    * @param oldValue No description provided
    * @param newValue No description provided
    */
   protected void firePropertyChange(String name, double oldValue,
         double newValue)
   {
      if (oldValue == newValue || propertyChangeSupport == null)
      {
         return;
      }
      firePropertyChange(name, new Double(oldValue), new Double(newValue));
   }
   
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param name No description provided
    * @param oldValue No description provided
    * @param newValue No description provided
    */
   protected void firePropertyChange(String name, long oldValue,
         long newValue)
   {
      if (oldValue == newValue || propertyChangeSupport == null)
      {
         return;
      }
      firePropertyChange(name, new Long(oldValue), new Long(newValue));
   }

   // --------------------------------------------------------------------
   // Additional property change support for plugins.
   // --------------------------------------------------------------------

   /**
    * Set of additional listeners.
    */
   private static final Set<PropertyChangeListener> additionalListeners = new HashSet<PropertyChangeListener>();


   /**
    * Adds an additional property change listener. If, e.g. a plugin wants to be notified about
    * changes in the ASG, it has to register a listener at startup time. Each time a new
    * <code>ASGElement</code> is created, all registered listeners are added to the
    * propertyChangeSupport of this element.
    *
    * @param listener
    */
   public static void addAdditionalListener(PropertyChangeListener listener)
   {
      if (listener != null)
      {
         // just add once!
         if (!additionalListeners.contains(listener))
         {
            additionalListeners.add(listener);
         }
      }
   }


   /**
    * Removes an additional property change listener.
    *
    * @param listener
    */
   public static void removeAdditionalListener(PropertyChangeListener listener)
   {
      if (listener != null)
      {
         if (additionalListeners != null)
         {
            additionalListeners.remove(listener);
         }
      }
   }


   /**
    * Instance that listens on all changes of ASGElement objects. Only one listener will be created
    * for the environment.
    */
   private static final ASGElementSetSavedListener SET_SAVED_ELEMENT_LISTENER = new ASGElementSetSavedListener();

   /**
    * This method must be overridden by any class that supports being referenced as an external reference of a
    * copy&paste action.
    * @param context context the ID is valid in (e.g. a UML Project, Package or UML Class)
    * @return an identifier for this object unique in the given context
    * @see de.uni_paderborn.fujaba.versioning.NamespaceManager
    */
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      throw new UnsupportedOperationException("Elements of " + getClass() + " do not support being referenced by " +
            "context ID! (getContextIdentifier is not implemented)");
   }


   /**
    * Listener that evaluates property changes of ASGElement objects. If a property changes, the
    * corresponding project must be saved.
    *
    * @author $Author$
    * @version $Revision$ $Date$
    */
   static class ASGElementSetSavedListener implements PropertyChangeListener
   {

      /**
       * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
       */
      public void propertyChange(PropertyChangeEvent evt)
      {
         Object source = evt.getSource();
         if (!(source instanceof ASGElement))
         {
            return;
         }

         // at this moment we only check if the property realy changes
         // and if so, we inform the project in which the element is placed
         // to set its state to 'unchanged'.
         Object oldValue = evt.getOldValue();
         Object newValue = evt.getNewValue();

         if (oldValue == newValue)
         {
            return;
         }

         FProject project = ((ASGElement) source).getProject();

         // if the project is currently loading, we do not inform the project
         // FIXME: the ProjectLoader must be extended, so it is able to return
         // the FProject that is currently loaded!
         // then we must check, if 'project' equals the project that
         // is currently loaded.
         // NOTE: the method that returns the project that is currently
         // loaded should be 'synchronized' to be thread safe
         if (Versioning.get().isInOperationalization(project))
         {
            return;
         }

         String propertyName = evt.getPropertyName();

         if (project == null)
         {
            return;
         }

         if (project == source)
         {
            if (FProject.SAVED_PROPERTY.equals(propertyName))
            {
               return;
            }
            else if (FProject.FILE_PROPERTY.equals(propertyName))
            {
               return;
            }
         }

         // TODO: check some more cases, in which the project does not need
         // to be saved, i.e., "well known" transient properties or properties
         // that don't make sense to be saved

         // now we can be sure that the project has been changed
         // in such a way, that it needs to be saved
         project.setSaved(false);
      }
   }


   /**
    * Get the name attribute of the ASGElement object
    *
    * @return The name value
    */
   public String getName()
   {
      return "no Name";
   }


   /**
    * Sets the name attribute of the ASGElement object
    *
    * @param newName The new name value
    */
   public void setName(String newName)
   {
      if (newName == null ? getName() != null : !newName.equals(getName()))
      throw new UnsupportedOperationException(
            "'ASGElement.setName ("
                  + newName
                  + ")' was called.\n"
                  + "It is not supported by ASGElement and has to be overridden in a derived class!");
   }


   /**
    * Get the text attribute of the ASGElement object
    *
    * @return The text value
    */
   public String getText()
   {
      return "";
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    *
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @SuppressWarnings({"unchecked"}) // to allow generic removal of objects
   @Override
   public void removeYou()
   {
      removeAllFromElementReferences();
      removeAllFromDiagrams();
      removeAllFromAnnotations();
      removeAllFromUnparseInformations();

      if (!isPersistent())
      {
         removeFromTransientElements();
      }

      // remove this object from products, if possible
      FFactory<?> factory = getProject().getFromFactories(getClass());
      if (factory instanceof UMLHeavyweightFactory)
      {
         UMLHeavyweightFactory heavyweightFactory = (UMLHeavyweightFactory) factory;
         heavyweightFactory.removeFromProducts(this);
      }
      else if (factory instanceof UMLFlyweightFactory)
      {
         UMLFlyweightFactory flyweightFactory = (UMLFlyweightFactory) factory;
         flyweightFactory.removeFromProducts(this);
      }

      if (propertyChangeSupport != null)
      {
         firePropertyChange(REMOVE_YOU_PROPERTY, this, null);
         propertyChangeSupport.removeYou();
         propertyChangeSupport = null;
      } // end of if ()

      super.removeYou();
   }


   /**
    * Query key for creation of this object by a factory.
    *
    * @return factory key, null if not applicable
    * @see #ASGElement(de.uni_paderborn.fujaba.metamodel.common.FProject,boolean,String)
    */
   public final String getFactoryKey()
   {
      return factoryKey;
   }


   /**
    * Get the elementKey attribute of the ASGElement object
    *
    * @return The elementKey value
    */
   public String getElementKey()
   {
      return getName();
   }

   public String getQualifiedDisplayName()
   {
      return getName() + " in " + getProject().getName();
   }

   /**
    * getter for field codeStyle
    *
    * @return current value of field codeStyle
    */
   public FCodeStyle getCodeStyle()
   {
      return this.codeStyle;
   }

   /**
    * store the value for field codeStyle
    */
   private FCodeStyle codeStyle;

   /**
    * Property name for firing property change events of field codeStyle.
    */
   public static final String PROPERTY_CODE_STYLE = "codeStyle";

   /**
    * setter for field codeStyle
    *
    * @param value new value
    */
   public void setCodeStyle(final FCodeStyle value)
   {
      final FCodeStyle oldValue = this.codeStyle;
      if (oldValue != value)
      {
         this.codeStyle = value;
         firePropertyChange(PROPERTY_CODE_STYLE, oldValue, value);
      }
   }

   public FCodeStyle getInheritedCodeStyle ()
   {
      final FCodeStyle codeStyle = getCodeStyle();
      if (codeStyle!=null)
      {
         return codeStyle;
      }
      final FElement parentElement = getParentElement();
      if (parentElement!=null)
      {
         return parentElement.getInheritedCodeStyle();
      }
      return null;
   }

   private static final Set<String> uneditableProperties = new FTreeSet();
   static {
      uneditableProperties.add("id");
      uneditableProperties.add("iD");
      uneditableProperties.add("generated");
   }


   /**
    * Should return true when an inspection of the named field is senseful. (for property editor)
    *
    * @param fieldName the name of the field
    * @return false when the field should not be inspected.
    */
   public boolean isInspectableField(String fieldName)
   {
      // allow only writable fields that are primitives or for which values are proposed
      if (ObjectInspector.get().hasSetter(this.getClass(), fieldName))
      {
         Class cls = ObjectInspector.get().getFieldClass(this.getClass(),
               fieldName);
         if (cls.isPrimitive() || cls.equals(String.class))
         {
            return !uneditableProperties.contains(fieldName);
         }
         else
         {
            return proposeFieldValues(fieldName, cls) != null;
         }
      }
      else
      {
         return "lastModified".equals(fieldName) || PROJECT_PROPERTY.equals(fieldName);
      }
   }


   /**
    * Returns proposals for values that could be inserted into the field (for property editor)
    *
    * @param fieldName name of the field that should get the values
    * @param fieldClass class of the field for which to propose values
    * @return an Iterator through value proposals, may return null when no proposals are available
    */
   public Iterator proposeFieldValues(String fieldName, Class fieldClass)
   {
      Class[] interfaces = fieldClass.getInterfaces();
      FProject project = getProject();

      if (project != null)
      {
         if (fieldClass.equals(FClass.class))
         {
            FFactory<FClass> factory = project.getFromFactories(FClass.class);
            return factory.iteratorOfProducts();
         } else if (fieldClass.equals(FType.class))
         {
            FFactory<FType> factory = project.getFromFactories(FType.class);
            return factory.iteratorOfProducts();
         } else if (fieldClass.equals(FCardinality.class))
         {
            FFactory<FCardinality> factory = project.getFromFactories(FCardinality.class);
            return factory.iteratorOfProducts();
         } else if (fieldClass.equals(FPackage.class))
         {
            return FPackageUtility.iteratorOfAllSubPackages( project.getRootPackage() );
         }
      }
      if (fieldClass.equals(FProject.class))
      {
         return ProjectManager.get().iteratorOfProjects();
      }

      if ( !fieldClass.isInterface() ) {
         for (Class iface : interfaces)
         {
            Iterator proposal = proposeFieldValues( fieldName, iface );
            if ( proposal != null ) return proposal;
         }
      }
      if ( FCodeStyle.class.isAssignableFrom(fieldClass) )
      {
         // FIXME the Codegen plug-in should provide the code styles and actually code styles shouldn't be model elements
         FFactory<FCodeStyle> factory = project.getFromFactories(FCodeStyle.class);
         Iterator<FCodeStyle> stylesIter = factory.iteratorOfProducts();
         if (!stylesIter.hasNext())
         {
            try
            {
               for (String codeStyleName : FCodeStyle.DEFAULT_CODESTYLE_NAMES)
               {
                  if(factory.getFromProducts(codeStyleName) == null)
                  {
                     FCodeStyle style = factory.create(this.isPersistent());
                     style.setName(codeStyleName);
                  }
               }
            }
            catch (UnsupportedOperationException e)
            {
               // ok, no access styles
            }
         }
         return factory.iteratorOfProducts();
      }
      return null;
   }

   
   /**
    * @return Returns the inTransientMode.
    */
   public static boolean isInTransientMode()
   {
      return inTransientMode;
   }


   /**
    * @param inTransientMode  The inTransientMode to set.
    * @deprecated (gets deleted in 5.1)             ARG!!! Use _at_least_ something thread dependent!
    */
   public static void setInTransientMode(boolean inTransientMode)
   {
      ASGElement.inTransientMode = inTransientMode;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private final static Map<ASGElement,Object> transientElements = new WeakHashMap<ASGElement, Object>();


   /**
    * Access method for a To N-association.
    */
   private void addToTransientElements()
   {
      transientElements.put(this, null);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private void removeFromTransientElements()
   {
      transientElements.remove(this);
   }


   /**
    * Get the transientElements attribute of the ASGElement class
    *
    * @return The transientElements value
    */
   public static Set getTransientElements()
   {
      return Collections.unmodifiableSet(transientElements.keySet());
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object). This
    * method allows to navigate in direction of the model root (project) from any element within a
    * project.
    *
    * @return the logical parent of this element, may not return null unless this is the top level
    *         node (project) or is not contained in any parent yet
    */
   public FElement getParentElement()
   {
      throw new UnsupportedOperationException(getClass()
            + " has no implementation for getParentElement()");
   }


   /**
    * Comparator for sorting FElements by name
    *
    * @author $Author$
    * @version $Revision$ $Date$
    */
   public static class SortByNameComparator implements Comparator
   {
      /**
       * Compares its two FElements for ordering by name.
       * 
       * @param o1 the first object to be compared.
       * @param o2 the second object to be compared.
       * @return a negative integer, zero, or a positive integer as the first argument is less than,
       *         equal to, or greater than the second.
       * @throws ClassCastException if the arguments' types prevent them from being compared by this
       *            Comparator.
       */
      public int compare(Object o1, Object o2)
      {
         FElement element1 = (FElement) o1;
         FElement element2 = (FElement) o2;
         if (element1.getName() != null)
         {
            return element1.getName().compareTo(element2.getName());
         }
         else if (element2.getName() != null)
         {
            return -element2.getName().compareTo(element1.getName());
         }
         else
         {
            return 0;
         }
      }
   }

   @Override
   public String toString()
   {
      try
      {
         return getClass().getName() + "[name='"+getName()+"';ID='" + getID()+"']";
      }
      catch (UnsupportedOperationException e)
      {
         // handle exception that might be thrown by call to "getID()"
         return getClass().getName() + "[name='"+getName()+"';ID=???]";
      }
   }
}
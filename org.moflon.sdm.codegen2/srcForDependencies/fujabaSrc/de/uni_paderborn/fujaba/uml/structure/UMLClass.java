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
package de.uni_paderborn.fujaba.uml.structure;


import de.fujaba.text.FTextReferenceUtility;
import de.fujaba.text.TextNode;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.metamodel.common.*;
import de.uni_paderborn.fujaba.metamodel.common.util.FProjectUtility;
import de.uni_paderborn.fujaba.metamodel.structure.*;
import de.uni_paderborn.fujaba.metamodel.structure.util.FClassUtility;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.behavior.UMLStartActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStatechart;
import de.uni_paderborn.fujaba.uml.common.UMLFile;
import de.uni_paderborn.fujaba.versioning.Versioning;
import de.upb.tools.fca.*;
import de.upb.tools.pcs.CollectionChangeEvent;

import java.util.*;


/**
 * UMLClass is the core element of UMLClass-Diagrams.
 *
 * <h2>Associations</h2>
 *
 * <pre>
 *          +--------------------+ 1                1
 * UMLClass | getFullClassName() +-------------------- UMLMethod
 *          +--------------------+ parent     methods
 *
 *          +-----------+ 1              1
 * UMLClass | getName() +------------------ UMLAttr
 *          +-----------+ parent     attrs
 *
 *         +-----------+ 1                   1
 * UMLFile | getName() +----------------------- UMLClass
 *         +-----------+ file         contains
 *
 *          +-----------+ 1                         1
 * UMLClass | getName() +----------------------------- UMLMethod
 *          +-----------+ declares   declaredInMethod
 *
 *          +-----------+ 1                        1
 * UMLClass | getName() +---------------------------- UMLClass
 *          +-----------+ declares   declaredInClass
 *
 *          +-----------+ 1                          1
 * UMLClass | getName() +------------------------------ UMLPackage
 *          +-----------+ declares   declaredInPackage
 *
 *            +---------+ 1                                   1
 * UMLProject + name    +--------------------------------------- UMLClass
 *            +---------+ declaredInReferences       references
 *
 *                 parsedMembers          0..n
 * UMLClass -----------------------------------> UMLDeclaration
 *                               parsedMembers
 * </pre>
 *
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLClass extends UMLDeclaration implements UMLType, FClass
{
   public final static String PROPERTY_NAME = FElement.NAME_PROPERTY;


   protected UMLClass(FProject project, boolean persistent)
   {
      super(project, persistent);
      this.initDeclaredInPackage(project);
   }


   /**
    * Returns a full qualified key including full package name and name of this class.
    *
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getElementKey()
    */
   @Override
   public String getElementKey()
   {
      return FClassUtility.getFullClassName(this);
   }


   private String name;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getName()
    */
   @Override
   public String getName()
   {
      return this.name;
   }

   
   /**
    * Sets the name attribute of the UMLClass object.
    *
    * <pre>
    *
    * The new name of the UMLClass can either be a short name,
    * a full qualified name or an inner class definition in Java-style.
    * Note that the intention of this method is only
    * to set the SHORT name of the class!
    * To specify the full qualified name, use method 'setDeclaredInPackage'.
    * To define an inner class, use method 'setDeclaredInClass'.
    *
    * Example:
    *   Map (short)
    *   java.util.Map (full qualified)
    *   java.util.Map$Entry (inner class)
    *   java.util.Map.Entry (inner class, alternative)
    * </pre>
    *
    * @param name The new name value
    */
   @Override
   public void setName(String name)
   {
      if (name == null)
      {
         return;
      }

      String oldName = getName();
      String shortName = name;

      // extract package prefix from name
      String packageName = FProjectUtility.getPackageOfFullQualifiedType(name);

      // extract short name if the name describes an inner class
      String parentClassFullName = FProjectUtility
            .getOuterClassOfFullQualifiedType(name);
      UMLClass parentClass = null;
      if (!"".equals(parentClassFullName))
      {
         shortName = name.substring(parentClassFullName.length() + 1);
         parentClass = (UMLClass) getProject().getRootPackage().findClass(
               parentClassFullName);

         if (parentClass == null)
         {
            throw new IllegalArgumentException("Parent class of '" + name
                  + "' is not existing!");
         }
      }
      else
      {
         if (packageName.length() > 0)
         {
            shortName = name.substring(packageName.length() + 1);
         }
      }

      // we have to make sure that some other objects
      // change the key used to identify this UMLClass,
      // because the name (which is part of the key) is
      // about to change
      UMLPackage oldPack = this.getDeclaredInPackage();
      this.setDeclaredInPackage(null);

      UMLClass oldClass = this.getDeclaredInClass();
      this.setDeclaredInClass(null);

      UMLMethod oldMethod = this.getDeclaredInMethod();
      this.setDeclaredInMethod(null);

      UMLFile oldFile = this.getFile();
      this.setFile(null);

      // TODO: what if this UMLClass declares other UMLClasses
      // (i.e., has inner classes)?
      // do we have to inform them about the name-change?


      // BEGIN change the (full qualified) name
      if (this.name == null || !this.name.equals(shortName))
      {
         this.name = shortName;
         firePropertyChange(FElement.NAME_PROPERTY, oldName, shortName);
      }

      this.setDeclaredInMethod(oldMethod);

      // this class should be an inner class
      if (!parentClassFullName.equals(""))
      {
         parentClass.removeKeyFromStereotypes("reference");
         parentClass.getFile();
         setDeclaredInClass(parentClass);
         // the package of an inner class is defined by its outer class
         setDeclaredInPackage(null);
      }
      // this class should be contained in a package
      else if (!packageName.equals(""))
      {
         UMLPackage tmpPkg = (UMLPackage) FProjectUtility.findPackage(getProject(), packageName,
               true, true);
         setDeclaredInPackage(tmpPkg);
      }
      else
      {
         this.setDeclaredInPackage(oldPack);
         this.setDeclaredInClass(oldClass);
      }

      // END change the (full qualified) name
      // ////////

      this.setFile(oldFile);

      // TODO: why do we fire another property-change-event?
      if (this.name == null || !this.name.equals(shortName))
      {
         firePropertyChange(FElement.NAME_PROPERTY, oldName, shortName);
      }
   } // setName


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      return this.getName();
   }


   private String defaultIcon;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getDefaultIcon()
    */
   public String getDefaultIcon()
   {
      return this.defaultIcon;
   }


   public void setDefaultIcon(String newVal)
   {
      if (newVal == null || !newVal.equals(this.defaultIcon))
      {
         String oldVal = this.defaultIcon;
         this.defaultIcon = newVal;
         firePropertyChange(UMLClass.DEFAULT_ICON_PROPERTY, oldVal, newVal);
      }
   }


   @Property (name=FClass.ABSTRACT_PROPERTY)
   private boolean umlAbstract = false;


   /**
    * @deprecated use {@link #setAbstract(boolean)} instead (gets deleted in 5.1)
    */
   @NoProperty
   public boolean setUmlAbstract(boolean umlAbstract)
   {
      throw new UnsupportedOperationException("deprecated");
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#setAbstract(boolean)
    */
   public boolean setAbstract(boolean value)
   {
      boolean changed = false;
      if (this.umlAbstract != value)
      {
         this.umlAbstract = value;
         changed = true;
         firePropertyChange(ABSTRACT_PROPERTY, !this.umlAbstract,
               this.umlAbstract);
      }
      return changed;
   }


   /**
    * @deprecated use {@link #isAbstract()} instead (gets deleted in 5.1)
    */
   @NoProperty
   public boolean isUmlAbstract()
   {
      throw new UnsupportedOperationException("deprecated");
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#isAbstract()
    */
   public boolean isAbstract()
   {
      return this.umlAbstract;
   }


   public FAccessStyle getAccessStyle()
   {
      final FCodeStyle style = getCodeStyle();
      if (style == null || style instanceof FAccessStyle)
      {
         return (FAccessStyle) style;
      }
      return null;
   }

   public void setAccessStyle(final FAccessStyle accessStyle)
   {
      final FCodeStyle oldStyle = getCodeStyle();
      if (oldStyle != accessStyle)
      {
         setCodeStyle(accessStyle);
         firePropertyChange(ACCESS_STYLE_PROPERTY, oldStyle, accessStyle);
      }
   }

   public FAccessStyle getInheritedAccessStyle()
   {
      final FCodeStyle style = getInheritedCodeStyle();
      if (style == null || style instanceof FAccessStyle)
      {
         return (FAccessStyle) style;
      }
      return null;
   }

   /**
    * <pre>
    *         +-----------+ 1                   1
    * UMLFile | getName() +----------------------- UMLClass
    *         +-----------+ file         contains
    * </pre>
    */
   private UMLFile file;


   public boolean setFile(FFile obj)
   {
      boolean changed = false;

      if (this.file != obj)
      {
         UMLFile oldValue = this.file;
         if (oldValue != null)
         {
            this.file = null;
            oldValue.removeFromContains(this);
         }
         this.file = (UMLFile) obj;
         if (obj != null)
         {
            this.file.addToContains(this);
         }
         changed = true;

         firePropertyChange(FILE_PROPERTY, oldValue, this.file);
      }

      return changed;
   }


   public UMLFile getFile()
   {
      if ((file == null) && (this.getDeclaredInPackage() != null))
      {
         FFile tmpFile = getProject().getFromFactories(FFile.class).create(
               isPersistent());

         tmpFile.addToContains(this);
         tmpFile.setName(this.getName());
      }

      return this.file;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#removeFromDiagrams(de.uni_paderborn.fujaba.metamodel.common.FDiagram)
    */
   @Override
   public void removeFromDiagrams(FDiagram obj)
   {
      if (obj != null)
      {
         super.removeFromDiagrams(obj);
         Iterator iter;

         // ----- remove all assocs
         UMLAssoc assoc;
         UMLRole role;
         iter = iteratorOfRoles();
         while (iter.hasNext())
         {
            role = (UMLRole) iter.next();
            assoc = role.getAssoc();
            obj.removeFromElements(assoc);
         }

         // ----- remove all inheritance
         UMLGeneralization gen;
         iter = iteratorOfRevSubclass();
         while (iter.hasNext())
         {
            gen = (UMLGeneralization) iter.next();
            obj.removeFromElements(gen);
         }
         iter = iteratorOfRevSuperclass();
         while (iter.hasNext())
         {
            gen = (UMLGeneralization) iter.next();
            obj.removeFromElements(gen);
         }
      }
   }

   private FHashSet revImportedClasses = new FHashSet();


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasInRevImportedClasses(de.uni_paderborn.fujaba.metamodel.common.FFile)
    */
   public boolean hasInRevImportedClasses(FFile elem)
   {
      return this.revImportedClasses.contains(elem);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfRevImportedClasses()
    */
   public Iterator iteratorOfRevImportedClasses()
   {
      return revImportedClasses.iterator();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#addToRevImportedClasses(de.uni_paderborn.fujaba.metamodel.common.FFile)
    */
   public void addToRevImportedClasses(FFile elem)
   {
      if (elem != null && !this.hasInRevImportedClasses(elem))
      {
         this.revImportedClasses.add(elem);
         elem.addToImportedClasses(this);
         firePropertyChange(CollectionChangeEvent.get(this,
               "revImportedClasses", this.revImportedClasses, null, elem,
               CollectionChangeEvent.ADDED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeFromRevImportedClasses(de.uni_paderborn.fujaba.metamodel.common.FFile)
    */
   public void removeFromRevImportedClasses(FFile elem)
   {
      if (this.hasInRevImportedClasses(elem))
      {
         this.revImportedClasses.remove(elem);
         elem.removeFromImportedClasses(this);
         firePropertyChange(CollectionChangeEvent.get(this,
               "revImportedClasses", this.revImportedClasses, elem, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   private final void removeAllFromRevImportedClasses()
   {
      UMLFile item;
      Iterator iter = iteratorOfRevImportedClasses();

      while (iter.hasNext())
      {
         item = (UMLFile) iter.next();
         item.removeFromImportedClasses(this);
         firePropertyChange(CollectionChangeEvent.get(this,
               "revImportedClasses", this.revImportedClasses, item, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   /**
    * Implements the association of FType.
    */
   private transient FPropHashSet<UMLAttr> revAttrType = null;


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#hasInRevAttrType(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
    */
   public boolean hasInRevAttrType(FAttr value)
   {
      return ((this.revAttrType != null) && (value != null) && this.revAttrType
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#iteratorOfRevAttrType()
    */
   public Iterator<UMLAttr> iteratorOfRevAttrType()
   {
      return ((this.revAttrType == null) ? FEmptyIterator.<UMLAttr>get()
            : this.revAttrType.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#sizeOfRevAttrType()
    */
   public int sizeOfRevAttrType()
   {
      return ((this.revAttrType == null) ? 0 : this.revAttrType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#addToRevAttrType(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
    */
   public boolean addToRevAttrType(FAttr value)
   {
      boolean changed = false;
      if (value instanceof UMLAttr || value == null)
      {
         changed = this.addToRevAttrType((UMLAttr) value);
      }
      else
      {
         throw new IllegalArgumentException("Expected " + UMLAttr.class.getName()
               + " as argument instead of " + value.getClass().getName());
      }
      return changed;
   }

   /**
    * @param value the UMLAttr to be added
    * @return <code>true</code>, if successfully added the given value, <code>false</code> otherwise.
    */
   public boolean addToRevAttrType(UMLAttr value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revAttrType == null)
         {
            this.revAttrType = new FPropHashSet<UMLAttr>(this, "revAttrType");
         }
         changed = this.revAttrType.add(value);
         if (changed)
         {
            value.setAttrType(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeFromRevAttrType(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
    */
   public boolean removeFromRevAttrType(FAttr value)
   {
      boolean changed = false;
      if ((this.revAttrType != null) && (value != null))
      {
         changed = this.revAttrType.remove(value);
         if (changed)
         {
            value.setAttrType(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeAllFromRevAttrType()
    */
   public void removeAllFromRevAttrType()
   {
      UMLAttr tmpValue;
      Iterator<UMLAttr> iter = this.iteratorOfRevAttrType();
      while (iter.hasNext())
      {
         tmpValue = iter.next();
         this.removeFromRevAttrType(tmpValue);
      }
   }


   /**
    * Used for keeping the order of the parsed attributes, methods and inner classes.
    *
    * <pre>
    *                 parsedMembers          0..n
    * UMLClass -----------------------------------> UMLDeclaration
    *                               parsedMembers
    * </pre>
    */
   private FLinkedList parsedMembers;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#addToParsedMembers(de.uni_paderborn.fujaba.metamodel.structure.FDeclaration)
    */
   public boolean addToParsedMembers(FDeclaration value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.parsedMembers == null)
         {
            this.parsedMembers = new FLinkedList();
         }
         changed = this.parsedMembers.add(value);
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasInParsedMembers(de.uni_paderborn.fujaba.metamodel.structure.FDeclaration)
    */
   public boolean hasInParsedMembers(FDeclaration value)
   {
      return ((this.parsedMembers != null) && (value != null) && this.parsedMembers
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfParsedMembers()
    */
   public Iterator iteratorOfParsedMembers()
   {
      return ((this.parsedMembers == null) ? FEmptyIterator.get()
            : this.parsedMembers.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeAllFromParsedMembers()
    */
   public void removeAllFromParsedMembers()
   {
      FDeclaration tmpValue;
      Iterator iter = this.iteratorOfParsedMembers();
      while (iter.hasNext())
      {
         tmpValue = (FDeclaration) iter.next();
         this.removeFromParsedMembers(tmpValue);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeFromParsedMembers(de.uni_paderborn.fujaba.metamodel.structure.FDeclaration)
    */
   public boolean removeFromParsedMembers(FDeclaration value)
   {
      boolean changed = false;
      if ((this.parsedMembers != null) && (value != null))
      {
         changed = this.parsedMembers.remove(value);
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#sizeOfParsedMembers()
    */
   public int sizeOfParsedMembers()
   {
      return ((this.parsedMembers == null) ? 0 : this.parsedMembers.size());
   }


   /**
    * <pre>
    *          +-----------+ 1              1
    * UMLClass | getName() +------------------ UMLAttr
    *          +-----------+ parent     attrs
    * </pre>
    */
   private Set<UMLAttr> attrs;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasInAttrs(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
    */
   public boolean hasInAttrs(FAttr obj)
   {
      return this.attrs != null && this.attrs.contains(obj);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasKeyInAttrs(java.lang.String)
    */
   public boolean hasKeyInAttrs(String key)
   {
      return getFromAttrs(key) != null;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfAttrs()
    */
   public Iterator<UMLAttr> iteratorOfAttrs()
   {
      if (this.attrs == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.attrs.iterator();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#keysOfAttrs()
    */
   public Iterator<String> keysOfAttrs()
   {
      if (this.attrs == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         ArrayList<String> namesList = new ArrayList<String>(attrs.size());
         for (FAttr attr : this.attrs)
         {
            namesList.add(attr.getName());
         }
         return namesList.iterator();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#sizeOfAttrs()
    */
   public int sizeOfAttrs()
   {
      return ((this.attrs == null) ? 0 : this.attrs.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getFromAttrs(java.lang.String)
    */
   public UMLAttr getFromAttrs(String key)
   {

      if (key == null || attrs == null)
      {
         return null;
      }
      for (UMLAttr attr : attrs)
      {
         if (key.equals(attr.getName()))
         {
            return attr;
         }
      }
      return null;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#addToAttrs(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
    */
   public boolean addToAttrs(FAttr obj)
   {
      boolean changed = false;
      if (obj instanceof UMLAttr || obj == null)
      {
         changed = this.addToAttrs((UMLAttr) obj);
      }
      else
      {
         throw new IllegalArgumentException("Expected " + UMLAttr.class.getName()
               + " as argument instead of " + obj.getClass().getName());
      }
      return changed;
   }


   /**
    * @param obj the UMLAttr to be added
    * @return <code>true</code>, if successfully added the given value, <code>false</code> otherwise.
    */
   public boolean addToAttrs(UMLAttr obj)
   {
      boolean changed = false;

      if ((obj != null))
      {
         if (this.attrs == null)
         {
            this.attrs = new FPropHashSet<UMLAttr>(this, ATTRS_PROPERTY);
         }
         changed = this.attrs.add(obj);
         if (changed)
         {
            obj.setParent(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeFromAttrs(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
    */
   public boolean removeFromAttrs(FAttr obj)
   {
      boolean changed = false;

      if ((this.attrs != null) && (obj != null))
      {
         changed = attrs.remove(obj);
         if (changed)
         {
            obj.setParent(null);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeKeyFromAttrs(java.lang.String)
    */
   public boolean removeKeyFromAttrs(String key)
   {
      return removeFromAttrs(getFromAttrs(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeAllFromAttrs()
    */
   public void removeAllFromAttrs()
   {
      Iterator<UMLAttr> iter = this.iteratorOfAttrs();
      while (iter.hasNext())
      {
         iter.next().removeYou();
      }
   }


   private FHashSet<UMLRole> roles = new FHashSet<UMLRole>();


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#sizeOfRoles()
    */
   public int sizeOfRoles()
   {
      return roles.size();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasInRoles(de.uni_paderborn.fujaba.metamodel.structure.FRole)
    */
   public boolean hasInRoles(FRole elem)
   {
      return this.roles.contains(elem);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfRoles()
    */
   public Iterator<UMLRole> iteratorOfRoles()
   {
      return roles.iterator();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#addToRoles(de.uni_paderborn.fujaba.metamodel.structure.FRole)
    */
   public void addToRoles(FRole elem)
   {
      if (elem instanceof UMLRole || elem == null)
      {
         this.addToRoles((UMLRole) elem);
      }
      else
      {
         throw new IllegalArgumentException("Expected " + UMLRole.class.getName()
               + " as argument instead of " + elem.getClass().getName());
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#addToRoles(de.uni_paderborn.fujaba.metamodel.structure.FRole)
    */
   public void addToRoles(UMLRole elem)
   {
      if (elem != null && !this.hasInRoles(elem))
      {
         this.roles.add(elem);
         elem.setTarget(this);

         firePropertyChange(CollectionChangeEvent.get(this, ROLES_PROPERTY,
               this.roles, null, elem, CollectionChangeEvent.ADDED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeFromRoles(de.uni_paderborn.fujaba.metamodel.structure.FRole)
    */
   public void removeFromRoles(FRole elem)
   {
      if (this.hasInRoles(elem))
      {
         this.roles.remove(elem);
         elem.setTarget(null);

         firePropertyChange(CollectionChangeEvent.get(this, ROLES_PROPERTY,
               this.roles, elem, null, CollectionChangeEvent.REMOVED));
      }
   }


   private final void removeAllFromRoles()
   {
      Iterator<UMLRole> iter = iteratorOfRoles();
      while (iter.hasNext())
      {
         UMLRole item = iter.next();
         item.setTarget(null);
         item.removeYou();
         firePropertyChange(CollectionChangeEvent.get(this, ROLES_PROPERTY,
               this.roles, item, null, CollectionChangeEvent.REMOVED));
      }
   }


   /**
    * <pre>
    *            0..n     instanceOf     0..1
    * UMLObject ------------------------------ UMLClass
    *            instances         instanceOf
    * </pre>
    */
   @Property(name=INSTANCES_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY,
         adornment=ReferenceHandler.Adornment.NONE, partner = UMLObject.INSTANCE_OF_PROPERTY)
   private FHashSet instances = new FHashSet();


   public boolean hasInInstances(UMLObject elem)
   {
      return this.instances.contains(elem);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfInstances()
    */
   public Iterator iteratorOfInstances()
   {
      return instances.iterator();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#addToInstances(de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public void addToInstances(FElement elem)
   {
      UMLObject umlObject = (UMLObject) elem;
      if (umlObject != null && !this.hasInInstances(umlObject))
      {
         this.instances.add(umlObject);
         umlObject.setInstanceOf(this);

         firePropertyChange(CollectionChangeEvent.get(this, INSTANCES_PROPERTY,
               this.instances, null, umlObject, CollectionChangeEvent.ADDED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeFromInstances(de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public void removeFromInstances(FElement elem)
   {
      UMLObject umlObject = (UMLObject) elem;
      if (this.hasInInstances(umlObject))
      {
         this.instances.remove(umlObject);
         umlObject.setInstanceOf(null);
         firePropertyChange(CollectionChangeEvent.get(this, INSTANCES_PROPERTY,
               this.instances, umlObject, null, CollectionChangeEvent.REMOVED));
      }
   }


   private final void removeAllFromInstances()
   {
      Iterator iter = iteratorOfInstances();
      while (iter.hasNext())
      {
         UMLObject item = (UMLObject) iter.next();
         item.setInstanceOf(null);
         firePropertyChange(CollectionChangeEvent.get(this, INSTANCES_PROPERTY,
               this.instances, item, null, CollectionChangeEvent.REMOVED));
      }
   }


   /**
    * <pre>
    *          +---------------------+ 0..1              0..1
    * UMLClass | getFullMethodName() +------------------------ UMLMethod
    *          +---------------------+ parent         methods
    * </pre>
    */
   private Set<UMLMethod> methods = null;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasInMethods(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean hasInMethods(FMethod obj)
   {
      return methods != null ? methods.contains(obj) : false;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasKeyInMethods(java.lang.String)
    */
   public boolean hasKeyInMethods(String key)
   {
      return getFromMethods(key) != null;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfMethods()
    */
   public Iterator<UMLMethod> iteratorOfMethods()
   {
      return ((this.methods == null) ? FEmptyIterator.<UMLMethod>get() : this.methods
            .iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#sizeOfMethods()
    */
   public int sizeOfMethods()
   {
      return ((this.methods == null) ? 0 : this.methods.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getFromMethods(java.lang.String)
    */
   public UMLMethod getFromMethods(String key)
   {
      if (key != null)
      {
         for (Iterator<UMLMethod> it = iteratorOfMethods(); it.hasNext();)
         {
            UMLMethod method = it.next();
            if (key.equals(method.getFullMethodName()))
            {
               return method;
            }
         }
      }
      return null;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#addToMethods(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean addToMethods(FMethod obj)
   {
      boolean changed = false;
      if (obj instanceof UMLMethod || obj == null)
      {
         changed = this.addToMethods((UMLMethod) obj);
      }
      else
      {
         throw new IllegalArgumentException("Expected " + UMLMethod.class.getName()
               + " as argument instead of " + obj.getClass().getName());
      }
      return changed;
   }

   
   /**
    * @param obj the UMLMethod to be added
    * @return <code>true</code>, if successfully added the given value, <code>false</code> otherwise.
    */
   public boolean addToMethods(UMLMethod obj)
   {
      boolean changed = false;

      if ((obj != null) && (obj.getFullMethodName() != null))
      {
         if (this.methods == null)
         {
            this.methods = new FPropHashSet<UMLMethod>(this, METHODS_PROPERTY);
         }

         changed = this.methods.add(obj);
         if (changed)
         {
            obj.setParent(this);
            if (obj.hasKeyInStereotypes(FStereotype.SIGNAL))
            {
               firePropertyChange(CollectionChangeEvent.get(this, "signals",
                     methods, null, obj, obj.getFullMethodName(),
                     CollectionChangeEvent.ADDED));
            }
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeFromMethods(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean removeFromMethods(FMethod obj)
   {
      boolean changed = false;

      if ((this.methods != null) && (obj != null))
      {
         changed = methods.remove(obj);
         if (changed)
         {
            obj.setParent(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeKeyFromMethods(java.lang.String)
    */
   public boolean removeKeyFromMethods(String key)
   {
      boolean changed = false;
      FMethod method;
      while ((method = getFromMethods(key)) != null)
      {
         removeFromMethods(method);
         changed = true;
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeAllFromMethods()
    */
   public void removeAllFromMethods()
   {
      Iterator<UMLMethod> iter = this.iteratorOfMethods();
      while (iter.hasNext())
      {
         iter.next().removeYou();
      }
   }


   public final static String SIGNALS_PROPERTY = "signals";


   @Property(name=SIGNALS_PROPERTY)
   public boolean hasInSignals(UMLMethod obj)
   {
      return (obj != null && obj.hasKeyInStereotypes(FStereotype.SIGNAL) && hasInMethods(obj));
   }


   @Property(name=SIGNALS_PROPERTY)
   public boolean hasKeyInSignals(String key)
   {
      FMethod signal = getFromMethods(key);
      return (signal != null && signal.hasKeyInStereotypes(FStereotype.SIGNAL));
   }


   @Property(name=SIGNALS_PROPERTY)
   public Iterator iteratorOfSignals()
   {
      Iterator<UMLMethod> methods = iteratorOfMethods();
      return new FFilterIterator(SignalFilter.get(), methods);
   }


   @Property(name=SIGNALS_PROPERTY)
   public int sizeOfSignals()
   {
      int size = 0;
      Iterator iter = iteratorOfSignals();
      while (iter.hasNext())
      {
         iter.next();
         size++;
      }
      return size;
   }


   @Property(name=SIGNALS_PROPERTY)
   public UMLMethod getFromSignals(String key)
   {
      UMLMethod method = getFromMethods(key);
      return (method.hasKeyInStereotypes(FStereotype.SIGNAL) ? method : null);
   }


   @Property(name=SIGNALS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE, derived=true)
   public boolean addToSignals(UMLMethod obj)
   {
      boolean changed = false;

      if ((obj != null) && (obj.getFullMethodName() != null))
      {
         FStereotype stereotype = obj.getProject().getFromFactories(
               FStereotype.class).getFromProducts(FStereotype.SIGNAL);
         obj.addToStereotypes(stereotype);
         changed = addToMethods(obj);
      }
      return changed;
   }


   @Property(name=SIGNALS_PROPERTY)
   public boolean removeFromSignals(UMLMethod obj)
   {
      boolean changed = false;

      if ((obj != null) && (obj.getFullMethodName() != null)
            && (obj.hasKeyInStereotypes(FStereotype.SIGNAL)))
      {
         changed = removeFromMethods(obj);
      }
      return changed;
   }


   @Property(name=SIGNALS_PROPERTY)
   public boolean removeKeyFromSignals(String key)
   {
      boolean changed = false;

      if (key != null)
      {
         UMLMethod tmpObj = getFromMethods(key);
         if (tmpObj != null && tmpObj.hasKeyInStereotypes(FStereotype.SIGNAL))
         {
            changed = removeKeyFromMethods(key);
         }
      }
      return changed;
   }


   @Property(name=SIGNALS_PROPERTY)
   public void removeAllFromSignals()
   {
      Iterator iter = this.iteratorOfSignals();
      while (iter.hasNext())
      {
         ((UMLMethod) iter.next()).removeYou();
      }
   }


   /**
    * <pre>
    *          +-----------+ 1                          1
    * UMLClass | getName() +------------------------------ UMLPackage
    *          +-----------+ declares   declaredInPackage
    * </pre>
    */
   private UMLPackage declaredInPackage;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#setDeclaredInPackage(de.uni_paderborn.fujaba.metamodel.structure.FPackage)
    */
   public boolean setDeclaredInPackage(FPackage obj)
   {
      boolean changed = false;
      if (obj instanceof UMLPackage || obj == null)
      {
         changed = this.setDeclaredInPackage((UMLPackage) obj);
      }
      else
      {
         throw new IllegalArgumentException("Expected " + UMLPackage.class.getName()
               + " as argument instead of " + obj.getClass().getName());
      }
      return changed;
   }


   /**
    * @param obj the package in which this class is declared
    * @return <code>true</code>, if successfully set the given package, <code>false</code> otherwise.
    */
   public boolean setDeclaredInPackage(UMLPackage obj)
   {
      boolean changed = false;

      if (this.declaredInPackage != obj)
      {
         UMLPackage oldValue = this.declaredInPackage;
         if (this.declaredInPackage != null)
         {
            this.declaredInPackage = null;
            oldValue.removeFromDeclares(this);
         }
         this.declaredInPackage = obj;
         firePropertyChange(DECLARED_IN_PACKAGE_PROPERTY, oldValue, obj);
         if (obj != null)
         {
            obj.addToDeclares(this);
         }
         changed = true;
         // side effects
         if (obj != null)
         {
            this.setDeclaredInClass(null);
            this.setDeclaredInMethod(null);
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getDeclaredInPackage()
    */
   public UMLPackage getDeclaredInPackage()
   {
      return this.declaredInPackage;
   }


   /**
    * <pre>
    *          +-----------+ 1                          1
    * UMLClass | getName() +------------------------------ UMLMethod
    *          +-----------+ declares    declaredInMethod
    * </pre>
    */
   private UMLMethod declaredInMethod;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#setDeclaredInMethod(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean setDeclaredInMethod(FMethod obj)
   {
      boolean changed = false;
      if (obj instanceof UMLMethod || obj == null)
      {
         changed = this.setDeclaredInMethod((UMLMethod) obj);
      }
      else
      {
         throw new IllegalArgumentException("Expected " + UMLMethod.class.getName()
               + " as argument instead of " + obj.getClass().getName());
      }
      return changed;
   }
   
   
   /**
    * @param obj the method that this class is declared in
    * @return <code>true</code>, if successfully set the given method, <code>false</code> otherwise.
    */
   public boolean setDeclaredInMethod(UMLMethod obj)
   {
      boolean changed = false;

      if (this.declaredInMethod != obj)
      {
         UMLMethod oldValue = this.declaredInMethod;
         if (this.declaredInMethod != null)
         {
            this.declaredInMethod = null;
            oldValue.removeFromDeclares(this);
         }
         this.declaredInMethod = (UMLMethod) obj;
         firePropertyChange(DECLARED_IN_METHOD_PROPERTY, oldValue, obj);
         if (obj != null)
         {
            this.declaredInMethod.addToDeclares(this);
         }
         changed = true;

         // side effects
         if (obj != null)
         {
            this.setDeclaredInClass(null);
            this.setDeclaredInPackage(null);
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getDeclaredInMethod()
    */
   public UMLMethod getDeclaredInMethod()
   {
      return this.declaredInMethod;
   }


   /**
    * <pre>
    *          +-----------+ 1                        1
    * UMLClass | getName() +---------------------------- UMLClass
    *          +-----------+ declares   declaredInClass
    * </pre>
    */
   private UMLClass declaredInClass;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#setDeclaredInClass(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean setDeclaredInClass(FClass obj)
   {
      boolean changed = false;
      if (obj instanceof UMLClass || obj == null)
      {
         changed = this.setDeclaredInClass((UMLClass) obj);
      }
      else
      {
         throw new IllegalArgumentException("Expected " + UMLClass.class.getName()
               + " as argument instead of " + obj.getClass().getName());
      }
      return changed;
   }
   

   /**
    * @param obj the class that this class is declared in
    * @return <code>true</code>, if successfully set the given class, <code>false</code> otherwise.
    */
   public boolean setDeclaredInClass(UMLClass obj)
   {
      boolean changed = false;

      if (this.declaredInClass != obj)
      {
         UMLClass oldValue = this.declaredInClass;
         if (this.declaredInClass != null)
         {
            this.declaredInClass = null;
            oldValue.removeFromDeclares(this);
         }
         this.declaredInClass = obj;
         if (obj != null)
         {
            obj.addToDeclares(this);
         }
         changed = true;

         // side effects
         if (obj != null)
         {
            this.setDeclaredInMethod(null);
            this.setDeclaredInPackage(null);
         }
         firePropertyChange(DECLARED_IN_CLASS_PROPERTY, oldValue, obj);
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getDeclaredInClass()
    */
   public UMLClass getDeclaredInClass()
   {
      return this.declaredInClass;
   }


   /**
    * <pre>
    *          +-----------+ 1                        1
    * UMLClass | getName() +---------------------------- UMLClass
    *          +-----------+ declares   declaredInClass
    * </pre>
    */
   private transient FPropTreeMap declares;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasInDeclares(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean hasInDeclares(FClass obj)
   {
      return ((this.declares != null) && (obj != null)
            && (obj.getName() != null) && (this.declares.get(obj.getName()) == obj));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasKeyInDeclares(java.lang.String)
    */
   public boolean hasKeyInDeclares(String key)
   {
      return ((this.declares != null) && (key != null) && this.declares
            .containsKey(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfDeclares()
    */
   public Iterator iteratorOfDeclares()
   {
      return ((this.declares == null) ? FEmptyIterator.get() : this.declares
            .values().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#keysOfDeclares()
    */
   public Iterator keysOfDeclares()
   {
      return ((this.declares == null) ? FEmptyIterator.get() : this.declares
            .keySet().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#entriesOfDeclares()
    */
   public Iterator entriesOfDeclares()
   {
      return ((this.declares == null) ? FEmptyIterator.get() : this.declares
            .entrySet().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#sizeOfDeclares()
    */
   public int sizeOfDeclares()
   {
      return ((this.declares == null) ? 0 : this.declares.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getFromDeclares(java.lang.String)
    */
   public UMLClass getFromDeclares(String key)
   {
      return (((this.declares == null) || (key == null)) ? null
            : (UMLClass) this.declares.get(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#addToDeclares(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean addToDeclares(FClass obj)
   {
      boolean changed = false;

      if ((obj != null) && (obj.getName() != null))
      {
         if (this.declares == null)
         {
            this.declares = new FPropTreeMap(this, DECLARES_PROPERTY);
         }

         UMLClass oldValue = (UMLClass) this.declares.put(obj.getName(), obj);
         if (oldValue != obj)
         {
            if (oldValue != null)
            {
               oldValue.setDeclaredInClass(null);
            }
            obj.setDeclaredInClass(this);
            changed = true;
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeFromDeclares(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean removeFromDeclares(FClass obj)
   {
      boolean changed = false;

      if ((this.declares != null) && (obj != null) && (obj.getName() != null))
      {
         UMLClass oldValue = (UMLClass) this.declares.get(obj.getName());
         if (oldValue == obj)
         {
            this.declares.remove(obj.getName());
            obj.setDeclaredInClass(null);
            changed = true;
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeKeyFromDeclares(java.lang.String)
    */
   public boolean removeKeyFromDeclares(String key)
   {
      boolean changed = false;

      if ((this.declares != null) && (key != null))
      {
         UMLClass tmpObj = (UMLClass) this.declares.get(key);
         if (tmpObj != null)
         {
            this.declares.remove(key);
            tmpObj.setDeclaredInClass(null);
            changed = true;
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeAllFromDeclares()
    */
   public void removeAllFromDeclares()
   {
      UMLClass tmpObj;
      Iterator iter = this.iteratorOfDeclares();

      while (iter.hasNext())
      {
         tmpObj = (UMLClass) iter.next();
         this.removeFromDeclares(tmpObj);
      }
   }


   private FHashSet revSubclass = new FHashSet();


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasInRevSubclass(de.uni_paderborn.fujaba.metamodel.structure.FGeneralization)
    */
   public boolean hasInRevSubclass(FGeneralization elem)
   {
      return this.revSubclass.contains(elem);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#sizeOfRevSubclass()
    */
   public int sizeOfRevSubclass()
   {
      return this.revSubclass.size();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfRevSubclass()
    */
   public Iterator iteratorOfRevSubclass()
   {
      return this.revSubclass.iterator();
   }


   /**
    * Get a Path object containing all superclasses of this UMLClass.
    *
    * @return A Path object containing all superclasses of this UMLClass.
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfSuperClasses()
    */
   public Iterator<? extends FClass> iteratorOfSuperClasses()
   {
      return FClassUtility.iteratorOfSuperClasses(this);
   }

   /**
    * Get a Path object containing all subclasses of this UMLClass.
    *
    * @return A Path object containing all subclasses of this UMLClass.
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfSubClasses()
    */
   public Iterator<? extends FClass> iteratorOfSubClasses()
   {
      return FClassUtility.iteratorOfSubClasses(this);
   }


   /**
    * This method returns the super class of this class. This method treats no
    * implements-generalizations.
    *
    * @return the extended class
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getSuperClass()
    */
   public UMLClass getSuperClass()
   {
      boolean isInterface = hasKeyInStereotypes(FStereotype.INTERFACE);

      Iterator path = iteratorOfSuperClasses();
      while (path.hasNext())
      {
         UMLClass tmpClass = (UMLClass) path.next();

         if (tmpClass.hasKeyInStereotypes(FStereotype.INTERFACE) == isInterface)
         {
            return tmpClass;
         }
      }

      return null;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#addToRevSubclass(de.uni_paderborn.fujaba.metamodel.structure.FGeneralization)
    */
   public void addToRevSubclass(FGeneralization elem)
   {
      if (elem != null && !this.hasInRevSubclass(elem))
      {
         this.revSubclass.add(elem);
         elem.setSubclass(this);

         firePropertyChange(CollectionChangeEvent.get(this,
               REV_SUBCLASS_PROPERTY, this.revSubclass, null, elem,
               CollectionChangeEvent.ADDED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeFromRevSubclass(de.uni_paderborn.fujaba.metamodel.structure.FGeneralization)
    */
   public void removeFromRevSubclass(FGeneralization elem)
   {
      if (this.hasInRevSubclass(elem))
      {
         this.revSubclass.remove(elem);
         elem.setSubclass(null);

         firePropertyChange(CollectionChangeEvent.get(this,
               REV_SUBCLASS_PROPERTY, this.revSubclass, elem, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeAllFromRevSubclass()
    */
   public final void removeAllFromRevSubclass()
   {
      Iterator iter = iteratorOfRevSubclass();
      while (iter.hasNext())
      {
         UMLGeneralization incr = (UMLGeneralization) iter.next();
         removeFromRevSubclass(incr);
         incr.removeYou();
      }
   }


   private transient FHashSet revSuperclass = new FHashSet();


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#hasInRevSuperclass(de.uni_paderborn.fujaba.metamodel.structure.FGeneralization)
    */
   public boolean hasInRevSuperclass(FGeneralization elem)
   {
      return this.revSuperclass.contains(elem);
   }


   public int sizeOfRevSuperclass()
   {
      return this.revSuperclass.size();
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfRevSuperclass()
    */
   public Iterator iteratorOfRevSuperclass()
   {
      return revSuperclass.iterator();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#addToRevSuperclass(de.uni_paderborn.fujaba.metamodel.structure.FGeneralization)
    */
   public void addToRevSuperclass(FGeneralization elem)
   {
      if (elem != null && !this.hasInRevSuperclass(elem))
      {
         this.revSuperclass.add(elem);
         elem.setSuperclass(this);

         firePropertyChange(CollectionChangeEvent.get(this,
               REV_SUPERCLASS_PROPERTY, this.revSuperclass, null, elem,
               CollectionChangeEvent.ADDED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeFromRevSuperclass(de.uni_paderborn.fujaba.metamodel.structure.FGeneralization)
    */
   public void removeFromRevSuperclass(FGeneralization elem)
   {
      if (this.hasInRevSuperclass(elem))
      {
         this.revSuperclass.remove(elem);
         elem.setSuperclass(null);

         firePropertyChange(CollectionChangeEvent.get(this,
               REV_SUPERCLASS_PROPERTY, this.revSuperclass, elem, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#removeAllFromRevSuperclass()
    */
   public final void removeAllFromRevSuperclass()
   {
      UMLGeneralization item;
      Iterator iter = iteratorOfRevSuperclass();

      while (iter.hasNext())
      {
         item = (UMLGeneralization) iter.next();
         item.removeYou();
         firePropertyChange(CollectionChangeEvent.get(this,
               REV_SUPERCLASS_PROPERTY, this.revSuperclass, item, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   /**
    * interface implementation remember to initialize the variables
    * 
    * @return The progLangType value
    */
   public String getProgLangType()
   {
      String tmpString = getName();

      if (tmpString != null)
      {
         if (tmpString.trim().equals(FBaseType.BOOLEAN))
         {
            tmpString = "boolean";
         }
         else if (tmpString.trim().equals("Short"))
         {
            tmpString = "short";
         }
         else if (tmpString.trim().equals(FBaseType.INTEGER))
         {
            tmpString = "int";
         }
         else if (tmpString.trim().equals(FBaseType.FLOAT))
         {
            tmpString = "float";
         }
         else if (tmpString.trim().equals(FBaseType.DOUBLE))
         {
            tmpString = "double";
         }
      }
      return tmpString;
   }


   /**
    * <pre>
    *           0..1                     0..n
    * UMLCLass ------------------------------- UMLMethod
    *           resultType       revResultType
    * </pre>
    */
   private transient FPropHashSet<UMLMethod> revResultType;


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#hasInRevResultType(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean hasInRevResultType(FMethod value)
   {
      return ((this.revResultType != null) && (value != null) && this.revResultType
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#iteratorOfRevResultType()
    */
   public Iterator<UMLMethod> iteratorOfRevResultType()
   {
      return ((this.revResultType == null) ? FEmptyIterator.<UMLMethod>get()
            : this.revResultType.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#sizeOfRevResultType()
    */
   public int sizeOfRevResultType()
   {
      return ((this.revResultType == null) ? 0 : this.revResultType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#addToRevResultType(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean addToRevResultType(FMethod value)
   {
      boolean changed = false;
      if (value instanceof UMLMethod || value == null)
      {
         changed = this.addToRevResultType((UMLMethod) value);
      }
      else
      {
         throw new IllegalArgumentException("Expected " + UMLMethod.class.getName()
               + " as argument instead of " + value.getClass().getName());
      }
      return changed;
   }
   
   
   /**
    * @param value the method to be set
    * @return <code>true</code>, if successfully set the given method, <code>false</code> otherwise.
    */
   public boolean addToRevResultType(UMLMethod value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revResultType == null)
         {
            this.revResultType = new FPropHashSet<UMLMethod>(this,
                  REV_RESULT_TYPE_PROPERTY);
         }
         changed = this.revResultType.add(value);
         if (changed)
         {
            value.setResultType(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeFromRevResultType(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean removeFromRevResultType(FMethod value)
   {
      boolean changed = false;
      if ((this.revResultType != null) && (value != null))
      {
         changed = this.revResultType.remove(value);
         if (changed)
         {
            value.setResultType(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeAllFromRevResultType()
    */
   public void removeAllFromRevResultType()
   {
      UMLMethod tmpValue;
      Iterator<UMLMethod> iter = this.iteratorOfRevResultType();
      while (iter.hasNext())
      {
         tmpValue = iter.next();
         this.removeFromRevResultType(tmpValue);
      }
   }


   /**
    * <pre>
    *           0..1                   0..n
    * UMLClass ----------------------------- UMLParam
    *           paramTyp        revParamTyp
    * </pre>
    */
   private transient FPropHashSet<UMLParam> revParamType;


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#hasInRevParamType(de.uni_paderborn.fujaba.metamodel.structure.FParam)
    */
   public boolean hasInRevParamType(FParam value)
   {
      return ((this.revParamType != null) && (value != null) && this.revParamType
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#iteratorOfRevParamType()
    */
   public Iterator<UMLParam> iteratorOfRevParamType()
   {
      return ((this.revParamType == null) ? FEmptyIterator.<UMLParam>get()
            : this.revParamType.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#sizeOfRevParamType()
    */
   public int sizeOfRevParamType()
   {
      return ((this.revParamType == null) ? 0 : this.revParamType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#addToRevParamType(de.uni_paderborn.fujaba.metamodel.structure.FParam)
    */
   public boolean addToRevParamType(FParam value)
   {
      boolean changed = false;
      if (value instanceof UMLParam || value == null)
      {
         changed = this.addToRevParamType((UMLParam) value);
      }
      else
      {
         throw new IllegalArgumentException("Expected " + UMLParam.class.getName()
               + " as argument instead of " + value.getClass().getName());
      }
      return changed;
   }
   
   
   /**
    * @param value the parameter to be set
    * @return <code>true</code>, if successfully set the given parameter, <code>false</code> otherwise.
    */
   public boolean addToRevParamType(UMLParam value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revParamType == null)
         {
            this.revParamType = new FPropHashSet<UMLParam>(this, REV_PARAM_TYPE_PROPERTY);
         }
         changed = this.revParamType.add(value);
         if (changed)
         {
            value.setParamType(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeFromRevParamType(de.uni_paderborn.fujaba.metamodel.structure.FParam)
    */
   public boolean removeFromRevParamType(FParam value)
   {
      boolean changed = false;
      if ((this.revParamType != null) && (value != null))
      {
         changed = this.revParamType.remove(value);
         if (changed)
         {
            value.setParamType(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeAllFromRevParamType()
    */
   public void removeAllFromRevParamType()
   {
      UMLParam tmpValue;
      Iterator<UMLParam> iter = this.iteratorOfRevParamType();
      while (iter.hasNext())
      {
         tmpValue = iter.next();
         this.removeFromRevParamType(tmpValue);
      }
   }


   private UMLArray revArrayType = null;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#getRevArrayType()
    */
   public UMLArray getRevArrayType()
   {
      return this.revArrayType;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#setRevArrayType(de.uni_paderborn.fujaba.metamodel.structure.FArray)
    */
   public void setRevArrayType(FArray revArrayType)
   {
      if (this.revArrayType != revArrayType)
      {
         UMLArray oldRevArrayType = this.revArrayType;
         // new partner
         if (this.revArrayType != null)
         {
            // inform old partner
            this.revArrayType = null;
            oldRevArrayType.setArrayType(this);
         }
         this.revArrayType = (UMLArray) revArrayType;
         if (this.revArrayType != null)
         {
            // inform new partner
            this.revArrayType.setArrayType(this);
         }
         firePropertyChange(REV_ARRAY_TYPE_PROPERTY, oldRevArrayType,
               revArrayType);
      }
   }

   private UMLStatechart statechart;
   
   /**
    * @return the statechart for this class, null if not applicable
    */
   public UMLStatechart getStatechart()
   {
      return statechart;
   }


   public void setStatechart (UMLStatechart newStatechart)
   {
      if (this.statechart != newStatechart)
      {
         // newPartner
         UMLStatechart oldStatechart = this.statechart;
         if (this.statechart != null)
         {
            // inform old partner
            this.statechart = null;

            oldStatechart.setOwner (null);
         }

         this.statechart = newStatechart;
         if (statechart != null)
         {
            // inform new partner
            newStatechart.setOwner(this);
         }
         firePropertyChange(STATECHART_PROPERTY, oldStatechart,
               newStatechart);
      }
   }
   

   /**
    * <pre>
    *           0..1   startOfStateChart   0..1
    * UMLClass --------------------------------- UMLStartActivity
    *           +                            +
    * </pre>
    * 
    * @deprecated  (gets deleted in 5.1, use FClass.statechart instead)
    */
   private transient UMLStartActivity startOfStateChart;


   /**
    * @deprecated  (gets deleted in 5.1, use FClass.statechart instead)
    */
   public UMLStartActivity getStartOfStateChart()
   {
      // FIX for deprication: since startOfStateChart is transient it is
      // not stored in .ctr files. Thus, we will look up the FClass.statechart attribute.
      // Note, codegen2 still uses this getter to find statecharts. Thus, do not delete this method 
      // without changing codegen2. AZ.
      
      UMLStartActivity startAct = startOfStateChart;
      
//      if (startAct == null)
//      {
//         UMLStatechart statechart = getStatechart();
//         if (statechart != null)
//         {
//            startAct = statechart.getStartActivity();
//         }
//      }
      
      return startAct;
   }

   /**
    * @deprecated  (gets deleted in 5.1, use FClass.statechart instead)
    */
   public void setStartOfStateChart(UMLStartActivity startOfStateChart)
   {
      if (this.startOfStateChart != startOfStateChart)
      {
         // newPartner
         UMLStartActivity oldStartOfStateChart = this.startOfStateChart;
         if (this.startOfStateChart != null)
         {
            // inform old partner
            this.startOfStateChart = null;

            oldStartOfStateChart.setRevStartOfStateChart(null);
         }

         this.startOfStateChart = startOfStateChart;
         if (startOfStateChart != null)
         {
            // inform new partner
            startOfStateChart.setRevStartOfStateChart(this);
         }
         firePropertyChange("startOfStateChart", oldStartOfStateChart,
               startOfStateChart);
      }
   }


   private transient FPropHashSet revType;


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#hasInRevType(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean hasInRevType(FQualifier value)
   {
      return ((this.revType != null) && (value != null) && this.revType
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#iteratorOfRevType()
    */
   public Iterator iteratorOfRevType()
   {
      return ((this.revType == null) ? FEmptyIterator.get() : this.revType
            .iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#sizeOfRevType()
    */
   public int sizeOfRevType()
   {
      return ((this.revType == null) ? 0 : this.revType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#addToRevType(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean addToRevType(FQualifier value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revType == null)
         {
            this.revType = new FPropHashSet(this, REV_TYPE_PROPERTY);
         }
         changed = this.revType.add(value);
         if (changed)
         {
            value.setType(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeFromRevType(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean removeFromRevType(FQualifier value)
   {
      boolean changed = false;
      if ((this.revType != null) && (value != null))
      {
         changed = this.revType.remove(value);
         if (changed)
         {
            value.setType(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeAllFromRevType()
    */
   public void removeAllFromRevType()
   {
      UMLQualifier tmpValue;
      Iterator iter = iteratorOfRevType();
      while (iter.hasNext())
      {
         tmpValue = (UMLQualifier) iter.next();
         this.removeFromRevType(tmpValue);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getFromMethodsByShortNameIncludingInherited(java.lang.String)
    */
   public FMethod getFromMethodsByShortNameIncludingInherited(String key)
   {
      return FClassUtility.getFromMethodsByShortNameIncludingInherited(this,
            key);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#findClass(java.lang.String)
    */
   public FClass findClass(String name)
   {
      return FClassUtility.findClass(this, name);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#findPackage()
    */
   public FPackage findPackage()
   {
      return FClassUtility.findPackage(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getAllAssocs()
    */
   public Set<? extends FAssoc> getAllAssocs()
   {
      return FClassUtility.getAllAssocs(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getAllUsedRoles()
    */
   public Collection getAllUsedRoles()
   {
      return FClassUtility.getAllUsedRoles(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getFromAllAttrs(java.lang.String)
    */
   public FAttr getFromAllAttrs(String key)
   {
      return FClassUtility.getFromAllAttrs(this, key);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getFromAllPartnerRoles(java.lang.String)
    */
   public FRole getFromAllPartnerRoles(String key)
   {
      return FClassUtility.getFromAllPartnerRoles(this, key);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#isChildOf(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean isChildOf(FClass clazz)
   {
      return FClassUtility.isChildOf(this, clazz);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getFullClassName()
    */
   public String getFullClassName()
   {
      return FClassUtility.getFullClassName(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfAllRoles()
    */
   public Iterator iteratorOfAllRoles()
   {
      return FClassUtility.iteratorOfAllRoles(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      if (getDeclaredInClass() != null)
      {
         return getDeclaredInClass();
      }
      else if (getDeclaredInMethod() != null)
      {
         return getDeclaredInMethod();
      }
      else if (getDeclaredInPackage() != null)
      {
         return getDeclaredInPackage();
      }
      else
      {
         return getProject();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getAllRoles()
    */
   public Set getAllRoles()
   {
      return FClassUtility.getAllRoles(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getAllAttrs()
    */
   public Set getAllAttrs()
   {
      return FClassUtility.getAllAttrs(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#findMethodsWithSignatureInSuperclasses(java.lang.String)
    */
   public Set<? extends FMethod> findMethodsWithSignatureInSuperclasses(String signature)
   {
      return FClassUtility.findMethodsWithSignatureInSuperclasses(this,
            signature);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#findMethodsWithSignatureInSubclasses(java.lang.String)
    */
   public Set<? extends FMethod> findMethodsWithSignatureInSubclasses(String signature)
   {
      return FClassUtility
            .findMethodsWithSignatureInSubclasses(this, signature);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getFromMethodsByShortName(java.lang.String)
    */
   public FMethod getFromMethodsByShortName(String string)
   {
      return FClassUtility.getFromMethodsByShortName(this, string);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfAllAttrs()
    */
   public Iterator<? extends FAttr> iteratorOfAllAttrs()
   {
      return FClassUtility.iteratorOfAllAttrs(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfAllAccessibleMethods()
    */
   public Iterator<? extends FMethod> iteratorOfAllAccessibleMethods()
   {
      return FClassUtility.iteratorOfAllAccessibleMethods(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#getFromAllMethods(java.lang.String)
    */
   public FMethod getFromAllMethods(String key)
   {
      return FClassUtility.getFromAllMethods(this, key);
   }


   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return this.getName();
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      this.removeAllFromStereotypes();
      this.setFile(null);
      this.removeAllFromRevImportedClasses();
      this.removeAllFromRevAttrType();
      this.removeAllFromParsedMembers();
      this.removeAllFromAttrs();
      this.removeAllFromRoles();
      this.removeAllFromInstances();
      this.removeAllFromMethods();
      this.setDeclaredInPackage(null);
      this.setDeclaredInMethod(null);
      this.setDeclaredInClass(null);
      this.removeAllFromDeclares();
      this.removeAllFromRevSubclass();
      this.removeAllFromRevSuperclass();
      this.removeAllFromRevResultType();
      this.removeAllFromRevParamType();
      this.setRevArrayType(null);
      this.setStartOfStateChart(null);
      this.removeAllFromUnparseInformations();
      setName("deleted {"+getName()+"}");

      super.removeYou();
   }


   /**
    * @author $Author$
    * @version $Revision$ $Date$
    */
   private final static class SignalFilter implements FFilterIterator.Filter,
         FFilterIterator.Translator
   {
      private static SignalFilter singleton = null;


      public static SignalFilter get()
      {
         if (singleton == null)
         {
            singleton = new SignalFilter();
         }
         return singleton;
      }


      /**
       * @see de.upb.tools.fca.FFilterIterator.Filter#include(java.lang.Object)
       */
      public boolean include(Object arg)
      {
         if (arg instanceof Map.Entry)
         {
            arg = ((Map.Entry) arg).getValue();
         }
         return ((arg instanceof UMLMethod) && ((UMLMethod) arg)
               .hasKeyInStereotypes(FStereotype.SIGNAL));
      }


      /**
       * @see de.upb.tools.fca.FFilterIterator.Translator#translate(java.lang.Object)
       */
      public Object translate(Object arg)
      {
         return ((Map.Entry) arg).getKey();
      }
   }

   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      return getFullClassName();
   }

   /**
    * Implementation of the 'referencedElement' association between FTextReference and
    * UMLTextNode. Since this association must be implemented by numerous classes, all
    * operations are forwarded to the FTextReferenceUtility class.
    * 
    * <pre>
    *           0..1     referencedElement     0..n
    * FTextReference ------------------------- UMLTextNode
    *      referencedElement               textUsages
    * </pre>
    * 
    * @see de.fujaba.text.FTextReference
    * @see de.fujaba.text.FTextReferenceUtility
    */
   private FTextReferenceUtility textRefUtil;

   /**
    * Accessor for textRefUtil field featuring lazy instantiation.
    * 
    * @return the FTextReferenceUtility instance for this instance.
    */
   private FTextReferenceUtility getTextRefUtil()
   {
      if(this.textRefUtil == null)
      {
         this.textRefUtil = new FTextReferenceUtility(this);
      }
      return this.textRefUtil;
   }

   /**
    * Adds the given UMLTextNode instance to the set of
    * text usages. (Forwarded to FTextReferenceUtility)
    * 
    * @return true if the set was changed.
    */
   public boolean addToTextUsages(TextNode value)
   {
      return getTextRefUtil().addToTextUsages(value);
   }

   /**
    * Determines if this instance's set of text usages contains
    * the given UMLTextNode instance. (Forwarded to FTextReferenceUtility)
    */
   public boolean hasInTextUsages(TextNode value)
   {
      return getTextRefUtil().hasInTextUsages(value);
   }

   /**
    * @return an Iterator of this instance's set of text usages.
    * (Forwarded to FTextReferenceUtility)
    */
   public Iterator<TextNode> iteratorOfTextUsages()
   {
      return getTextRefUtil().iteratorOfTextUsages();
   }

   /**
    * Removes all elements from this instance's set of text usages.
    * (Forwarded to FTextReferenceUtility)
    */
   public void removeAllFromTextUsages()
   {
      getTextRefUtil().removeAllFromTextUsages();
   }

   /**
    * Removes the given element from this instance's set of text usages
    * if it is in there. (Forwarded to FTextReferenceUtility)
    * 
    * @return true if the set was changed.
    */
   public boolean removeFromTextUsages(TextNode value)
   {
      return getTextRefUtil().removeFromTextUsages(value);
   }

   /**
    * @return the number of UMLTextNode instances in this instance's set
    * of text usages. (Forwarded to FTextReferenceUtility)
    */
   public int sizeOfTextUsages()
   {
      return getTextRefUtil().sizeOfTextUsages();
   }

   @Override
   public String getQualifiedDisplayName()
   {
      return getFullClassName() + " in " + getProject().getName();
   }
   
   /**
    * Originating from the UMLClassFactory which was deleted.
    * Will be deleted soon.
    * 
    * @param project
    */
   @Deprecated
   public void initDeclaredInPackage(FProject project)
   {
      if (!Versioning.get().isInOperationalization(project))
      {
         this.setDeclaredInPackage(project.getRootPackage());

         // This call creates a FFile object for this class.
         // We do this, for backwards compatibility reasons.
         // Otherwise CodeGen1 won't generate code for this class,
         // if the whole project is exported, because in this case, the
         // project is looked up for FFile objects for which code will be generated.
         // However no FFile objects will exist and so no code will be generated.
         // see: de.uni_paderborn.fujaba.codegen.UMLProjectOOHandler#generateSourceCode(FElement, OOGenToken, Object)
         // TODO: maybe the CodeGen1 should be reviewed and all FClass objects
         // should be exported, or the RootPackage... (FK)
         this.getFile();
      }
   }
}

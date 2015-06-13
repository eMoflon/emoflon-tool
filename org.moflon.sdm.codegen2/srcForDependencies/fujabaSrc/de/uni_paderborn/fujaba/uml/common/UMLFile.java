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
package de.uni_paderborn.fujaba.uml.common;


import java.util.Collection;
import java.util.Iterator;

import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FFile;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.util.FFileUtility;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.preferences.FujabaPreferencesManager;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashSet;
import de.upb.tools.fca.FLinkedList;
import de.upb.tools.fca.FPropLinkedList;
import de.upb.tools.pcs.CollectionChangeEvent;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *         +-----------+ 1                   1
 * UMLFile | getName() +----------------------- FClass
 *         +-----------+ file         contains
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLFile extends UMLIncrement implements FFile
{
   public final static String DEFAULT_FILE_NAME = "default";


   public UMLFile(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   /**
    * The name of the the file specified by the object.
    */
   private String name;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getName()
    */
   @Override
   public String getName()
   {
      // ----- FIXME: what about, if there are more than one class in a file
      // check if the filename is the same as the first class-name

      // ----- search for a class with the current filename
      String myName = null;
      if (this.name != null)
      {
         Iterator iter = this.iteratorOfContains();
         while (iter.hasNext())
         {
            myName = ((FClass) iter.next()).getName();
            if (this.name.equals(myName))
            {
               break;
            }
            myName = null;
         }
      }

      if (myName == null)
      {
         // ----- there is no class with the current filename or
         // the class has not a name yet
         Iterator iter = this.iteratorOfContains();
         if (iter.hasNext())
         {
            this.setName(((FClass) iter.next()).getName());
         }
         else
         {
            this.setName("_@" + getID());
         }
      }
      return this.name;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#setName(java.lang.String)
    */
   @Override
   public void setName(String newName)
   {
      if (newName != null && !newName.equals(this.name))
      {
         String oldValue = this.name;

         // FIXME !!! Remove this nasty side effect and call of getExportFolder()
         String rootDir = getProject() != null ? FujabaPreferencesManager.getExportFolder(getProject()) : "";
         if (rootDir != null && newName.startsWith(rootDir))
         {
            // adjust the file name. if it begins with the inputDir, which is
            // specified in OptionParser
            newName = newName.substring(rootDir.length());
         }
         this.name = newName;
         firePropertyChange(NAME_PROPERTY, oldValue, newName);
      }
   }


   /**
    * <pre>
    *           n      hasClasses       0..1
    * FClass ------------------------------ UMLFile
    *           contains   {ordered,}   file
    * </pre>
    */
   private FLinkedList contains;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#hasInContains(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean hasInContains(FClass value)
   {
      return ((this.contains != null) && (value != null) && this.contains
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#iteratorOfContains()
    */
   public Iterator iteratorOfContains()
   {
      return ((this.contains == null) ? FEmptyIterator.get() : this.contains
            .iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#sizeOfContains()
    */
   public int sizeOfContains()
   {
      return ((this.contains == null) ? 0 : this.contains.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#addToContains(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean addToContains(FClass value)
   {
      boolean changed = false;
      if (value != null && !hasInContains(value))
      {
         if (this.contains == null)
         {
            this.contains = new FPropLinkedList(this, CONTAINS_PROPERTY);
         }
         changed = this.contains.add(value);
         if (changed)
         {
            value.setFile(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#removeFromContains(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean removeFromContains(FClass value)
   {
      boolean changed = false;
      if ((this.contains != null) && (value != null))
      {
         changed = this.contains.remove(value);
         if (changed)
         {
            value.setFile(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#removeAllFromContains()
    */
   public void removeAllFromContains()
   {
      FClass tmpValue;
      Iterator iter = this.iteratorOfContains();
      while (iter.hasNext())
      {
         tmpValue = (FClass) iter.next();
         this.removeFromContains(tmpValue);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#getContainsAt(int)
    */
   public FClass getContainsAt(int index)
   {
      if (index >= 0 && index < sizeOfContains())
      {
         return (FClass) this.contains.get(index);
      }
      else
      {
         throw new IllegalArgumentException("getContainsAt(" + index + ")");
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#indexOfContains(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public int indexOfContains(FClass elem)
   {
      return ((this.contains == null) ? -1 : this.contains.indexOf(elem));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#lastIndexOfContains(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public int lastIndexOfContains(FClass elem)
   {
      return ((this.contains == null) ? -1 : this.contains.lastIndexOf(elem));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#isBeforeOfContains(de.uni_paderborn.fujaba.metamodel.structure.FClass,
    *      de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean isBeforeOfContains(FClass leftObject, FClass rightObject)
   {
      if (contains == null)
      {
         return false;
      }
      else
      {
         return contains.isBefore(leftObject, rightObject);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#isAfterOfContains(de.uni_paderborn.fujaba.metamodel.structure.FClass,
    *      de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean isAfterOfContains(FClass leftObject, FClass rightObject)
   {
      if (contains == null)
      {
         return false;
      }
      else
      {
         return contains.isAfter(leftObject, rightObject);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#getFirstOfContains()
    */
   public FClass getFirstOfContains()
   {
      if (contains == null || contains.isEmpty())
      {
         return null;
      }
      else
      {
         return (FClass) contains.getFirst();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#getLastOfContains()
    */
   public FClass getLastOfContains()
   {
      if (contains == null || contains.isEmpty())
      {
         return null;
      }
      else
      {
         return (FClass) contains.getLast();
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#getNextOfContains(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public FClass getNextOfContains(FClass object)
   {
      if (contains == null)
      {
         return null;
      }
      else
      {
         return (FClass) contains.getNextOf(object);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#getNextIndexOfContains(de.uni_paderborn.fujaba.metamodel.structure.FClass,
    *      int)
    */
   public FClass getNextIndexOfContains(FClass object, int index)
   {
      if (contains == null)
      {
         return null;
      }
      else
      {
         return (FClass) contains.getNextOf(object, index);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#getPreviousOfContains(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public FClass getPreviousOfContains(FClass object)
   {
      if (contains == null)
      {
         return null;
      }
      else
      {
         return (FClass) contains.getPreviousOf(object);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#getPreviousIndexOfContains(de.uni_paderborn.fujaba.metamodel.structure.FClass,
    *      int)
    */
   public FClass getPreviousIndexOfContains(FClass object, int index)
   {
      if (contains == null)
      {
         return null;
      }
      else
      {
         return (FClass) contains.getPreviousOf(object, index);
      }
   }


   private StringBuffer footer;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#getFooter()
    */
   public StringBuffer getFooter()
   {
      return this.footer;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#setFooter(java.lang.StringBuffer)
    */
   public void setFooter(StringBuffer footer)
   {
      if (this.footer != footer)
      {
         this.footer = footer;
      }
   }


   private FHashSet importedClasses = new FHashSet();


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#hasInImportedClasses(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean hasInImportedClasses(FClass elem)
   {
      return this.importedClasses.contains(elem);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#sizeOfImportedClasses()
    */
   public int sizeOfImportedClasses()
   {
      return ((this.importedClasses == null) ? 0 : this.importedClasses.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#iteratorOfImportedClasses()
    */
   public Iterator iteratorOfImportedClasses()
   {
      return importedClasses.iterator();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#addToImportedClasses(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public void addToImportedClasses(FClass elem)
   {
      if (elem != null && !this.hasInImportedClasses(elem))
      {
         this.importedClasses.add(elem);
         elem.addToRevImportedClasses(this);
         firePropertyChange(CollectionChangeEvent.get(this,
               IMPORTED_CLASSES_PROPERTY, this.importedClasses, null, elem,
               CollectionChangeEvent.ADDED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#removeFromImportedClasses(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public void removeFromImportedClasses(FClass elem)
   {
      if (this.hasInImportedClasses(elem))
      {
         this.importedClasses.remove(elem);
         elem.removeFromRevImportedClasses(this);
         firePropertyChange(CollectionChangeEvent.get(this,
               IMPORTED_CLASSES_PROPERTY, this.importedClasses, elem, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   private final void removeAllFromImportedClasses()
   {
      FClass item;
      Iterator iter = iteratorOfImportedClasses();

      while (iter.hasNext())
      {
         item = (FClass) iter.next();
         item.removeFromRevImportedClasses(this);
         firePropertyChange(CollectionChangeEvent.get(this,
               IMPORTED_CLASSES_PROPERTY, this.importedClasses, item, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   /**
    * UMLFile <----------------------------------------> FPackage
    */
   private FHashSet importedPackages = new FHashSet();


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#hasInImportedPackages(de.uni_paderborn.fujaba.metamodel.structure.FPackage)
    */
   public boolean hasInImportedPackages(FPackage elem)
   {
      return this.importedPackages.contains(elem);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#sizeOfImportedPackages()
    */
   public int sizeOfImportedPackages()
   {
      return ((this.importedPackages == null) ? 0 : this.importedPackages
            .size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#iteratorOfImportedPackages()
    */
   public Iterator iteratorOfImportedPackages()
   {
      return this.importedPackages.iterator();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#addToImportedPackages(de.uni_paderborn.fujaba.metamodel.structure.FPackage)
    */
   public void addToImportedPackages(FPackage elem)
   {
      if (elem != null && !this.hasInImportedPackages(elem))
      {
         this.importedPackages.add(elem);
         elem.addToRevImportedPackages(this);

         firePropertyChange(CollectionChangeEvent.get(this,
               IMPORTED_PACKAGES_PROPERTY, this.importedPackages, null, elem,
               CollectionChangeEvent.ADDED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FFile#removeFromImportedPackages(de.uni_paderborn.fujaba.metamodel.structure.FPackage)
    */
   public void removeFromImportedPackages(FPackage elem)
   {
      if (elem != null && this.hasInImportedPackages(elem))
      {
         this.importedPackages.remove(elem);
         elem.removeFromRevImportedPackages(this);

         firePropertyChange(CollectionChangeEvent.get(this,
               IMPORTED_PACKAGES_PROPERTY, this.importedPackages, elem, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   private final void removeAllFromImportedPackages()
   {
      FPackage item;
      Iterator<FPackage> iter = iteratorOfImportedPackages();

      while (iter.hasNext())
      {
         item = iter.next();
         item.removeFromRevImportedPackages(this);

         firePropertyChange(CollectionChangeEvent.get(this,
               IMPORTED_PACKAGES_PROPERTY, this.importedPackages, item, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    * 
    * @return the logical parent of this element;
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      return FFileUtility.getPackage(this);
   }


   public FClass getClassFromImports(String fullName)
   {
      return FFileUtility.getClassFromImports(this, fullName);
   }


   public FPackage getPackage()
   {
      return FFileUtility.getPackage(this);
   }


   public boolean necessaryToCreateFile()
   {
      return FFileUtility.necessaryToCreateFile(this);
   }


   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      final FPackage umlPackage = getPackage();
      if (context != null && context.contains(umlPackage))
      {
         return getName();
      }
      return umlPackage.getFullPackageName() + "." + getName();
   }

   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append("UMLFile[");
      result.append(name);
      result.append("]");

      return result.toString();
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      this.removeAllFromContains();
      this.removeAllFromImportedClasses();
      this.removeAllFromImportedPackages();

      super.removeYou();
   }

}

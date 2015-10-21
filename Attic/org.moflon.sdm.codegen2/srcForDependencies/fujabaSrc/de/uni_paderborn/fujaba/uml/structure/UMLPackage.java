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


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import de.fujaba.text.FTextReferenceUtility;
import de.fujaba.text.TextNode;
import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FFile;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FAccessStyle;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.metamodel.structure.util.FPackageUtility;
import de.uni_paderborn.fujaba.uml.common.UMLFile;
import de.uni_paderborn.fujaba.uml.common.UMLIncrement;
import de.uni_paderborn.fujaba.versioning.Versioning;
import de.upb.tools.fca.FHashSet;
import de.upb.tools.fca.FPropHashSet;
import de.upb.tools.pcs.CollectionChangeEvent;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *          +-----------+ 1                          1
 * UMLClass | getName() +------------------------------ UMLPackage
 *          +-----------+ declares   declaredInPackage
 *
 *            +-----------+ 1                1
 * UMLPackage | getName() +-------------------- UMLPackage
 *            +-----------+ parent    packages
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLPackage extends UMLIncrement implements FPackage
{

   public static final String DEFAULT_PACKAGE_NAME = "default";


   protected UMLPackage(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   /**
    * Returns a full qualified key including the name of all parent packages and the name of this
    * package.
    * 
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getElementKey()
    */
   @Override
   public String getElementKey()
   {
      return FPackageUtility.getFullPackageName(this);
   }


   private String name = "";


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getName()
    */
   @Override
   public String getName()
   {
      return this.name;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#setName(java.lang.String)
    * @throws RuntimeExceptionWithContext If specified packagename is null or packagename is not a
    *            simple packagename.
    */
   @Override
   public void setName(String name) throws RuntimeExceptionWithContext
   {
      if (name == null)
      {
         if (Versioning.get().isInOperationalization(this))
            return;
         else
            throw new RuntimeExceptionWithContext(
                  "Packagename is not allowed to be"
                        + " set to 'null' with this function", this);
      }
      else if (name.indexOf('.') != -1)
      {
         if (Versioning.get().isInOperationalization(this))
            return;
         else
            throw new RuntimeExceptionWithContext("Cannot set packagename to '"
                  + name
                  + "' with this function: only simple names are allowed", this);
      }

      if (this.name == null || !this.name.equals(name))
      {
         UMLPackage myParent = getParent();
         if (this.name != null && myParent != null)
         {
            setParent(null);
         }
         String oldValue = this.name;
         this.name = name;

         // ----- update all qualified assoccs
         if (this.name != null && myParent != null)
         {
            setParent(myParent);
         }
         firePropertyChange(NAME_PROPERTY, oldValue, name);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      return this.getName();
   }


   /**
    * <pre>
    *          +-----------+ 1                          1
    * UMLClass | getName() +------------------------------ UMLPackage
    *          +-----------+ declares   declaredInPackage
    * </pre>
    */
   private transient Set<UMLClass> declares;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#hasInDeclares(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean hasInDeclares(FClass obj)
   {
      if (obj != null && !(obj instanceof UMLClass))
      {
         throw new IllegalArgumentException(
               "Argument must be instance of UMLClass!");
      }

      return (this.declares != null) && (obj != null)
            && this.declares.contains(obj);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#hasKeyInDeclares(java.lang.String)
    */
   public boolean hasKeyInDeclares(String key)
   {
      return getFromDeclares(key) != null;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#iteratorOfDeclares()
    */
   public Iterator<? extends FClass> iteratorOfDeclares()
   {
      if ((this.declares == null))
      {
         return EmptyIterator.get();
      } else
      {
         return (this.declares.iterator());
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#keysOfDeclares()
    * @deprecated gets deleted in 5.1, use {@link #iteratorOfDeclares()} instead
    */
   public Iterator keysOfDeclares()
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#entriesOfDeclares()
    * @deprecated gets deleted in 5.1, use {@link #iteratorOfDeclares()} instead
    */
   public Iterator entriesOfDeclares()
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /*
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#sizeOfDeclares()
    */
   public int sizeOfDeclares()
   {
      return ((this.declares == null) ? 0 : this.declares.size());
   }


   /*
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#getFromDeclares(java.lang.String)
    */
   public UMLClass getFromDeclares(String key)
   {
      if (key != null && declares != null)
      {
         for (UMLClass aClass : declares)
         {
            if (key.equals(aClass.getName()))
            {
               return aClass;
            }
         }
      }
      return null;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#addToDeclares(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean addToDeclares(FClass obj)
   {
      if (obj != null && !(obj instanceof UMLClass))
      {
         throw new IllegalArgumentException(
               "Argument must be instance of UMLClass!");
      }

      boolean changed = false;

      if ((obj != null) && (obj.getName() != null))
      {
         if (this.declares == null)
         {
            this.declares = new FPropHashSet(this, DECLARES_PROPERTY); // should be sorted by gui not here
         }

         if (this.declares.add((UMLClass) obj))
         {
            obj.setDeclaredInPackage(this);
            changed = true;
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#removeFromDeclares(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean removeFromDeclares(FClass obj)
   {
      if (obj != null && !(obj instanceof UMLClass))
      {
         throw new IllegalArgumentException(
               "Argument must be instance of UMLClass!");
      }

      boolean changed = false;

      if ((this.declares != null) && (obj != null) && (obj.getName() != null))
      {
         if (declares.remove(obj))
         {
            obj.setDeclaredInPackage(null);
            changed = true;
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#removeKeyFromDeclares(java.lang.String)
    * @deprecated gets deleted in 5.1, use {@link #removeFromDeclares(FClass)} instead
    */
   public boolean removeKeyFromDeclares(String key)
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#removeAllFromDeclares()
    */
   public void removeAllFromDeclares()
   {
      Iterator iter = this.iteratorOfDeclares();

      while (iter.hasNext())
      {
         UMLClass tmpObj = (UMLClass) iter.next();
         this.removeFromDeclares(tmpObj);
      }
   }


   /**
    * all files which import this package
    * 
    * <pre>
    *             n                                  n
    * UMLPackage -------------------------------------- UMLFile
    *             importedPackage   revImportedPackage
    * </pre>
    */
   private FHashSet revImportedPackages = new FHashSet();


   public boolean hasInRevImportedPackages(FFile elem)
   {
      if (elem != null && !(elem instanceof UMLFile))
      {
         throw new IllegalArgumentException(
               "Argument must be instance of UMLFile!");
      }

      return this.revImportedPackages.contains(elem);
   }


   public Iterator iteratorOfRevImportedPackages()
   {
      return revImportedPackages.iterator();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#sizeOfRevImportedPackages()
    */
   public int sizeOfRevImportedPackages()
   {
      return ((this.revImportedPackages == null) ? 0 : this.revImportedPackages
            .size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#addToRevImportedPackages(de.uni_paderborn.fujaba.metamodel.common.FFile)
    */
   public void addToRevImportedPackages(FFile elem)
   {
      if (elem != null && !(elem instanceof UMLFile))
      {
         throw new IllegalArgumentException(
               "Argument must be instance of UMLFile!");
      }

      if (elem != null && !this.hasInRevImportedPackages(elem))
      {
         this.revImportedPackages.add(elem);
         elem.addToImportedPackages(this);

         firePropertyChange(CollectionChangeEvent.get(this,
               REV_IMPORTED_PACKAGES_PROPERTY, revImportedPackages, null, elem,
               CollectionChangeEvent.ADDED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#removeFromRevImportedPackages(de.uni_paderborn.fujaba.metamodel.common.FFile)
    */
   public void removeFromRevImportedPackages(FFile elem)
   {
      if (!(elem instanceof UMLFile))
      {
         throw new IllegalArgumentException(
               "Argument must be instance of UMLFile!");
      }

      if (this.hasInRevImportedPackages(elem))
      {
         this.revImportedPackages.remove(elem);
         elem.removeFromImportedPackages(this);

         firePropertyChange(CollectionChangeEvent.get(this,
               REV_IMPORTED_PACKAGES_PROPERTY, revImportedPackages, elem, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   public void removeAllFromRevImportedPackages()
   {
      Iterator iter = iteratorOfRevImportedPackages();
      while (iter.hasNext())
      {
         UMLFile item = (UMLFile) iter.next();
         item.removeFromImportedPackages(this);
         firePropertyChange(CollectionChangeEvent.get(this,
               REV_IMPORTED_PACKAGES_PROPERTY, revImportedPackages, item, null,
               CollectionChangeEvent.REMOVED));
      }
   }


   /**
    * To represent the java-package structure it is necessary to put every depth of the package-tree
    * in one UMLPackage.
    * 
    * <pre>
    *            +-----------+ 1                1
    * UMLPackage | getName() +-------------------- UMLPackage
    *            +-----------+ parent    packages
    * </pre>
    * 
    * The key is the short package name (without any extension), e.g. the package
    * de.uni_paderborn.fujaba consists of three packages: the package 'de' contains the package
    * 'uni_paderborn' and the package 'uni_paderborn' contains the package 'fujaba'.
    */
   private Set<FPackage> packages;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#hasInPackages(de.uni_paderborn.fujaba.metamodel.structure.FPackage)
    */
   public boolean hasInPackages(FPackage obj)
   {
      if (obj != null && !(obj instanceof UMLPackage))
      {
         throw new IllegalArgumentException(
               "Argument must be instance of UMLPackage!");
      }

      return (this.packages != null) && (obj != null)
            && this.packages.contains(obj);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#hasKeyInPackages(java.lang.String)
    */
   public boolean hasKeyInPackages(String key)
   {
      return getFromPackages(key) != null;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#iteratorOfPackages()
    */
   public Iterator<FPackage> iteratorOfPackages()
   {
      if ((this.packages == null))
      {
         return EmptyIterator.get();
      } else
      {
         return (this.packages.iterator());
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#keysOfPackages()
    * @deprecated gets deleted in 5.1, use {@link #iteratorOfPackages()} instead
    */
   public Iterator keysOfPackages()
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#entriesOfPackages()
    * @deprecated gets deleted in 5.1, use {@link #iteratorOfPackages()} instead
    */
   public Iterator entriesOfPackages()
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#sizeOfPackages()
    */
   public int sizeOfPackages()
   {
      return ((this.packages == null) ? 0 : this.packages.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#getFromPackages(java.lang.String)
    */
   public UMLPackage getFromPackages(String key)
   {
      if (key != null && packages != null)
      {
         for (FPackage aPackage : packages)
         {
            if (key.equals(aPackage.getName()))
            {
               return (UMLPackage) aPackage;
            }
         }
      }
      return null;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#addToPackages(de.uni_paderborn.fujaba.metamodel.structure.FPackage)
    */
   public boolean addToPackages(FPackage obj)
   {
      if (obj != null && !(obj instanceof UMLPackage))
      {
         throw new IllegalArgumentException(
               "Argument must be instance of UMLPackage!");
      }

      boolean changed = false;

      if ((obj != null) && (obj.getName() != null))
      {
         if (this.packages == null)
         {
            this.packages = new FPropHashSet(this, PACKAGES_PROPERTY); // should be sorted by gui not here
         }
         boolean added = this.packages.add(obj);
         if (added)
         {
            obj.setParent(this);
            changed = true;
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#removeFromPackages(de.uni_paderborn.fujaba.metamodel.structure.FPackage)
    */
   public boolean removeFromPackages(FPackage obj)
   {
      if (obj != null && !(obj instanceof UMLPackage))
      {
         throw new IllegalArgumentException(
               "Argument must be instance of UMLPackage!");
      }

      boolean changed = false;

      if ((this.packages != null) && (obj != null))
      {
         if (this.packages.remove(obj))
         {
            obj.setParent(null);
            changed = true;
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#removeKeyFromPackages(java.lang.String)
    * @deprecated gets deleted in 5.1, use {@link #removeFromPackages(FPackage)} instead
    */
   public boolean removeKeyFromPackages(String key)
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#removeAllFromPackages()
    */
   public void removeAllFromPackages()
   {
      Iterator iter = this.iteratorOfPackages();
      while (iter.hasNext())
      {
         UMLPackage tmpObj = (UMLPackage) iter.next();
         this.removeFromPackages(tmpObj);
      }
   }


   /**
    * <pre>
    *            +-----------+ 1                1
    * UMLPackage | getName() +-------------------- UMLPackage
    *            +-----------+ parent    packages
    * </pre>
    */
   private UMLPackage parent;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#setParent(de.uni_paderborn.fujaba.metamodel.structure.FPackage)
    */
   public boolean setParent(FPackage obj)
   {
      if (obj != null && !(obj instanceof UMLPackage))
      {
         throw new IllegalArgumentException(
               "Argument must be instance of UMLPackage!");
      }

      boolean changed = false;

      if (this.parent != obj)
      {
         UMLPackage oldValue = this.parent;
         if (this.parent != null)
         {
            this.parent = null;
            oldValue.removeFromPackages(this);
         }
         this.parent = (UMLPackage) obj;
         if (obj != null)
         {
            obj.addToPackages(this);
         }
         changed = true;
         firePropertyChange(PARENT_PROPERTY, oldValue, obj);
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#getParent()
    */
   public UMLPackage getParent()
   {
      return this.parent;
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
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#findClass(java.lang.String)
    */
   public UMLClass findClass(String name)
   {
      return (UMLClass) FPackageUtility.findClass(this, name);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#findPackage(java.lang.String)
    */
   public UMLPackage findPackage(String name)
   {
      return (UMLPackage) FPackageUtility.findPackage(this, name);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#providePackage(java.lang.String)
    */
   public UMLPackage providePackage(String name)
   {
      return (UMLPackage) FPackageUtility.providePackage(this, name);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#providePackage(java.lang.String,
    *      boolean)
    */
   public UMLPackage providePackage(String name, boolean persistent)
   {
      return (UMLPackage) FPackageUtility
            .providePackage(this, name, persistent);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#removeEmptyPackages(java.lang.String)
    */
   public void removeEmptyPackages(String name)
   {
      FPackageUtility.removeEmptyPackages(this, name);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#getFullPackageName()
    */
   public String getFullPackageName()
   {
      return FPackageUtility.getFullPackageName(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FPackage#getPackagePath()
    */
   public String getPackagePath()
   {
      return FPackageUtility.getPackagePath(this);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      return getProject() != null && getProject().getRootPackage() == this ? getProject()
            : getParent();
   }


   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      if (this.equals(getProject().getRootPackage()))
      {
         return ROOT_PACKAGE_CONTEXT_IDENTIFIER;
      }
      if (context != null && context.contains(getParent()))
      {
         return getName();
      }
      return getFullPackageName();
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
   
   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return getFullPackageName();
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      this.removeAllFromRevImportedPackages();
      //this.removeAllFromPackages();
      Iterator iter = this.iteratorOfPackages();
      while (iter.hasNext())
      {
         UMLPackage tmpObj = (UMLPackage) iter.next();
         tmpObj.removeYou();
      }
      //this.removeAllFromDeclares();
      iter = this.iteratorOfDeclares();
      while (iter.hasNext())
      {
         UMLClass tmpObj = (UMLClass) iter.next();
         tmpObj.removeYou();
      }

      this.setParent(null);

      super.removeYou();
   }

}

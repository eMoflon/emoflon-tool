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
package de.uni_paderborn.fujaba.metamodel.structure;

import de.fujaba.text.FTextReference;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FFile;
import de.uni_paderborn.fujaba.metamodel.common.FIncrement;

import java.util.Iterator;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FPackage extends FIncrement, FAccessedElement, FTextReference
{

   // --- Property declares ---
   public final static String DECLARES_PROPERTY = "declares";


   @Property(name=DECLARES_PROPERTY)
   public boolean hasInDeclares (FClass obj);


   @Property(name=DECLARES_PROPERTY)
   public boolean hasKeyInDeclares (String key);


   @Property(name=DECLARES_PROPERTY)
   public Iterator<? extends FClass> iteratorOfDeclares();

   /**
    * @deprecated gets deleted in 5.1, use {@link #iteratorOfDeclares()} instead
    */
   @Property(name=DECLARES_PROPERTY)
   public Iterator<String> keysOfDeclares();


   /**
    * @deprecated gets deleted in 5.1, use {@link #iteratorOfDeclares()} instead
    */
   @Property(name=DECLARES_PROPERTY)
   public Iterator entriesOfDeclares();


   @Property(name=DECLARES_PROPERTY)
   public int sizeOfDeclares();

   /**
    * Find a class with given name in this package. Note that this returns the first class found with this name, if
    * there are multiple classes with that name in this package due to temporary inconsistencies
    * (e.g. after copy/paste).
    * @param name unqualified class name
    * @return a class declared in this package with given name
    */
   @Property(name=DECLARES_PROPERTY)
   public FClass getFromDeclares (String name);


   @Property(name=DECLARES_PROPERTY)
   public boolean addToDeclares (FClass obj);


   @Property(name=DECLARES_PROPERTY, partner=FClass.DECLARED_IN_PACKAGE_PROPERTY,
         kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean removeFromDeclares (FClass obj);

   /**
    * @deprecated gets deleted in 5.1, use {@link #removeFromDeclares(FClass)} instead
    */
   @Property(name=DECLARES_PROPERTY)
   public boolean removeKeyFromDeclares (String key);


   @Property(name=DECLARES_PROPERTY)
   public void removeAllFromDeclares();


   // --- Property packages ---
   public final static String PACKAGES_PROPERTY = "packages";


   @Property(name=PACKAGES_PROPERTY)
   public boolean hasInPackages (FPackage obj);


   @Property(name=PACKAGES_PROPERTY)
   public boolean hasKeyInPackages (String key);


   @Property(name=PACKAGES_PROPERTY)
   public Iterator<? extends FPackage> iteratorOfPackages();

   /**
    * @deprecated gets deleted in 5.1, use {@link #iteratorOfPackages()} instead
    */
   @Property(name=PACKAGES_PROPERTY)
   public Iterator<String> keysOfPackages();

   /**
    * @deprecated gets deleted in 5.1, use {@link #iteratorOfPackages()} instead
    */
   @Property(name=PACKAGES_PROPERTY)
   public Iterator entriesOfPackages();


   @Property(name=PACKAGES_PROPERTY)
   public int sizeOfPackages();


   /**
    * Find a subpackage with given name in this package. Note that this returns the first subpackage found with this
    * name, if there are multiple subpackages with that name in this package due to temporary inconsistencies
    * (e.g. after copy/paste).
    * @param name unqualified package name
    * @return a subpackage of this package with given name
    */
   @Property(name=PACKAGES_PROPERTY)
   public FPackage getFromPackages (String name);


   @Property(name=PACKAGES_PROPERTY)
   public boolean addToPackages (FPackage obj);


   @Property(name=PACKAGES_PROPERTY, partner=FPackage.PARENT_PROPERTY,
         kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean removeFromPackages (FPackage obj);

   /**
    * @deprecated gets deleted in 5.1, use {@link #removeFromPackages(FPackage)} instead
    */
   @Property(name=PACKAGES_PROPERTY)
   public boolean removeKeyFromPackages (String key);


   @Property(name=PACKAGES_PROPERTY)
   public void removeAllFromPackages();


   public final static String PARENT_PROPERTY = "parent";


   @Property( name = PARENT_PROPERTY, partner = FPackage.PACKAGES_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.PARENT)
   public boolean setParent (FPackage obj);


   @Property(name=PARENT_PROPERTY)
   public FPackage getParent();


   public final static String REV_IMPORTED_PACKAGES_PROPERTY = "revImportedPackages";


   public void addToRevImportedPackages (FFile file);


   public void removeFromRevImportedPackages (FFile file);


   public int sizeOfRevImportedPackages();


   /**
    * Looks for the class with the given name relatively qualified to this package.
    *
    * @param name  the qualified class name
    * @return      the class with the given name or null
    */
   public FClass findClass (String name);


   /**
    * Looks for the package with the given name relatively qualified to this package.
    *
    * @param name  the qualified class name
    * @return      the package with the given name or null
    */
   public FPackage findPackage (String name);


   /**
    * Provides a package with the given name relatively qualified to this package. If the package
    * and its parent packages don't exist, it creates them. The packages returned are persistent.
    *
    *
    * @param name  the qualified package name
    * @return      The package with the given name.
    */
   public FPackage providePackage (String name);


   /**
    * Provides a package with the given name relatively qualified to this package. If the package
    * and its parent packages don't exist, it creates them.
    *
    *
    * @param name        the qualified package name
    * @param persistent  indicates, if the packages should be persistent
    * @return            The package with the given name.
    */
   public FPackage providePackage (String name, boolean persistent);


   /**
    * Removes empty packages recursively. Example: for the given package hierarchy
    * "de.uni_paderborn.fujaba.uml" the package "uml" will be removed if it contains
    * no more classes and packages. "fujaba" will be removed afterwards if it also
    * contains no more classes and packages, and so on...
    *
    * @param name The qualified package name relative to this package.
    * @deprecated gets removed in 5.1, use
    *             {@link de.uni_paderborn.fujaba.metamodel.structure.util.FPackageUtility#removeEmptyPackages} instead
    */
   public void removeEmptyPackages (String name);


   @Property(name="fullPackageName", kind = ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public String getFullPackageName();


   /**
    * @deprecated gets deleted in 5.1, use {@link de.uni_paderborn.fujaba.metamodel.structure.util.FPackageUtility#getPackagePath} instead
    */
   @Property(name="packagePath", kind = ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public String getPackagePath();


   public static final String ROOT_PACKAGE_CONTEXT_IDENTIFIER = "{ROOT}";
}

/*
 * $Log$
 * Revision 1.15  2007/04/02 09:04:01  cschneid
 * deprecation comments improved, RunAction revamped
 *
 * Revision 1.14  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.13  2006/05/18 19:20:58  fklar
 * using java 1.5 generics:
 * * adjusted return values of some methods in F-interfaces so they return a parameterized type
 *
 * Revision 1.12  2006/05/05 12:21:26  lowende
 * FPackage.providePackage(String name, boolean persistent) added.
 *
 */

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

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;

import java.util.Iterator;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FFile extends FIncrement
{

   // --- Property package ---
   public final static String PACKAGE_PROPERTY = "package";

   // --- Property contains ---
   public final static String CONTAINS_PROPERTY = "contains";


   @Property(name=CONTAINS_PROPERTY)
   public boolean hasInContains (FClass value);


   @Property(name=CONTAINS_PROPERTY)
   public Iterator<? extends FClass> iteratorOfContains();


   @Property(name=CONTAINS_PROPERTY)
   public int sizeOfContains();


   @Property(name=CONTAINS_PROPERTY, partner=FClass.FILE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean addToContains (FClass value);


   @Property(name=CONTAINS_PROPERTY)
   public boolean removeFromContains (FClass value);


   @Property(name=CONTAINS_PROPERTY)
   public void removeAllFromContains();


   public FClass getContainsAt (int index);


   public int indexOfContains (FClass elem);


   public int lastIndexOfContains (FClass elem);


   public boolean isBeforeOfContains (FClass leftObject,
                                      FClass rightObject);


   public boolean isAfterOfContains (FClass leftObject,
                                     FClass rightObject);


   public FClass getFirstOfContains();


   public FClass getLastOfContains();


   public FClass getNextOfContains (FClass object);


   public FClass getNextIndexOfContains (FClass object, int index);


   public FClass getPreviousOfContains (FClass object);


   public FClass getPreviousIndexOfContains (FClass object, int index);

   // --- Property importedClass ---
   public final static String IMPORTED_CLASSES_PROPERTY = "importedClasses";


   @Property(name=IMPORTED_CLASSES_PROPERTY)
   public boolean hasInImportedClasses (FClass elem);


   @Property(name=IMPORTED_CLASSES_PROPERTY)
   public int sizeOfImportedClasses();


   @Property(name=IMPORTED_CLASSES_PROPERTY)
   public Iterator<? extends FClass> iteratorOfImportedClasses();


   @Property(name=IMPORTED_CLASSES_PROPERTY, partner=FClass.REV_IMPORTED_CLASSES_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.USAGE)
   public void addToImportedClasses (FClass elem);


   @Property(name=IMPORTED_CLASSES_PROPERTY)
   public void removeFromImportedClasses (FClass elem);

   // --- Property importedPackages ---
   public final static String IMPORTED_PACKAGES_PROPERTY = "importedPackages";


   @Property(name=IMPORTED_PACKAGES_PROPERTY)
   public boolean hasInImportedPackages (FPackage elem);


   @Property(name=IMPORTED_PACKAGES_PROPERTY)
   public int sizeOfImportedPackages();


   @Property(name=IMPORTED_PACKAGES_PROPERTY)
   public Iterator<? extends FPackage> iteratorOfImportedPackages();


   @Property(name=IMPORTED_PACKAGES_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.USAGE)
   public void addToImportedPackages (FPackage elem);


   @Property(name=IMPORTED_PACKAGES_PROPERTY)
   public void removeFromImportedPackages (FPackage elem);


   // --- Property importedPackages ---
   public final static String FOOTER_PROPERTY = "footer";


   @Property(name=FOOTER_PROPERTY)
   public StringBuffer getFooter();


   @Property(name=FOOTER_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.NONE)
   public void setFooter (StringBuffer footer);


   public FClass getClassFromImports (String fullName);


   @Property(name=PACKAGE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, derived=true)
   public FPackage getPackage();


   /**
    * @return
    * @deprecated (gets deleted in 5.1),
    * use {@link de.uni_paderborn.fujaba.metamodel.common.util.FFileUtility#necessaryToCreateFile} instead
    */
   public boolean necessaryToCreateFile();

}

/*
 * $Log$
 * Revision 1.13  2007/04/02 09:04:02  cschneid
 * deprecation comments improved, RunAction revamped
 *
 * Revision 1.12  2007/03/26 10:49:25  l3_g5
 * prevent exceptions when saving with fpr
 *
 * Revision 1.11  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.10  2006/05/18 18:21:48  fklar
 * using java 1.5 generics:
 * * adjusted return value of method 'iteratorOfElement' so it returns a parameterized iterator
 *
 * Revision 1.9  2006/04/25 11:58:24  cschneid
 * added deprecation expiration note, work on versioning
 *
 */

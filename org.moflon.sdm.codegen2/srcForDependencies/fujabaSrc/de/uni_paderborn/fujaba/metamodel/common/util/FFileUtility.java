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
package de.uni_paderborn.fujaba.metamodel.common.util;


import java.util.Iterator;

import de.uni_paderborn.fujaba.metamodel.common.FFile;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;


/**
 * Utility class for dealing with instances of FFile.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class FFileUtility 
{

   public static boolean necessaryToCreateFile(FFile file)
   {
      boolean create = false;
      Iterator<? extends FClass> iter = file.iteratorOfContains();
      while (!create && iter.hasNext())
      {
         FClass fClass = iter.next();
         create = !fClass.hasKeyInStereotypes(FStereotype.REFERENCE);
      }
      return create;
   }


   public static FClass getClassFromImports(FFile file, String fullName)
   {
      FClass fClass = null;
      String className = fullName;

      // ----- split a qualified classname
      int lastDot = fullName.lastIndexOf('.');
      if (lastDot != -1)
      {
         className = fullName.substring(lastDot + 1);
      }

      // ----- search current package
      if (getPackage(file) != null)
      {
         fClass = getPackage(file).getFromDeclares(className);
      }

      // ----- search imported classes
      Iterator<? extends FClass> iterClasses = file.iteratorOfImportedClasses();
      while (fClass == null && iterClasses.hasNext())
      {
         fClass = iterClasses.next();

         if (!fClass.getName().equals(className))
         {
            fClass = null;
         }
      }

      // ----- search imported packages
      Iterator<? extends FPackage> iterPackages = file.iteratorOfImportedPackages();
      while (fClass == null && iterPackages.hasNext())
      {
         fClass = (iterPackages.next()).getFromDeclares(className);
      }

      // ----- check if the found class is the correct one
      if (lastDot != -1 && fClass != null
            && !fClass.getFullClassName().equals(fullName))
      {
         fClass = null;
      }

      return fClass;
   }


   /**
    * The package to which this file belongs
    * 
    * <pre>
    *         0,1          n          n          0,1
    *  FFile <--------------> FClass <--------------> FPackage
    *  
    * </pre>
    * 
    * @return The package value
    */
   public static FPackage getPackage(FFile file)
   {
      FPackage fPackage = null;
      Iterator<? extends FClass> iter = file.iteratorOfContains();
      FClass fClass = null;

      while (fPackage == null && iter.hasNext())
      {
         fClass = iter.next();
         fPackage = fClass.getDeclaredInPackage();
      }
      
      return fPackage;
   }

}

/*
 * $Log$
 * Revision 1.10  2006/05/18 18:28:42  fklar
 * refactored local variable names
 *
 * Revision 1.9  2006/05/18 18:21:49  fklar
 * using java 1.5 generics:
 * * adjusted return value of method 'iteratorOfElement' so it returns a parameterized iterator
 *
 * Revision 1.8  2006/03/29 09:51:09  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 */
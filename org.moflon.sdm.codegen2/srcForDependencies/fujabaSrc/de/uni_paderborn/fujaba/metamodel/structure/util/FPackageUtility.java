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
package de.uni_paderborn.fujaba.metamodel.structure.util;


import java.io.File;
import java.util.Iterator;

import org.apache.log4j.Logger;

import de.uni_kassel.util.IteratorsIterator;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FFile;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.upb.tools.fca.FHashSet;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class FPackageUtility
{
   private static Logger logger = Logger.getLogger(FPackageUtility.class);
   
   /**
    * Returns a class with the given qualified name. The qualified name is relative to the (client)
    * package. If the client is the root package, the name is fully qualified.
    * 
    * @param name A qualified class name relative to the (client) package.
    * @return The class with the given name if found, null otherwise.
    */
   public static FClass findClass(FPackage thisPackage, String name)
   {
      if (thisPackage == null)
      {
         return null;
      }

      FClass result = null;

      int indexOfFirstDot = name.indexOf('.');
      if (indexOfFirstDot != -1)
      {
         String subPackageName = name.substring(0, indexOfFirstDot);
         FPackage subPackage = thisPackage.getFromPackages(subPackageName);
         if (subPackage != null)
         {
            result = subPackage.findClass(name.substring(indexOfFirstDot + 1));
         }
         else
         {
            // look for inner classes
            // suppose subPackageName to be an outer class
            FClass outerClass = thisPackage.getFromDeclares(subPackageName);
            if (outerClass != null)
            {
               result = outerClass.findClass(name
                     .substring(indexOfFirstDot + 1));
            }
         }
      }
      else
      {
         result = thisPackage.getFromDeclares(name);
      }

      return result;
   }

   public static void getAllClasses (FPackage thePackage, FHashSet<FClass> result)
   {
   	// add classes of this package
   	for (Iterator iter = thePackage.iteratorOfDeclares(); iter.hasNext();)
		{
			FClass element = (FClass) iter.next();
			result.add(element);
		}	
   	
   	// iterate through subpackages
   	for (Iterator iter = thePackage.iteratorOfPackages(); iter.hasNext();)
		{
			FPackage element = (FPackage) iter.next();
			FPackageUtility.getAllClasses(element, result);
		}
   }

   /**
    * @param identifier
    * @return
    */
   public static FFile findFile(FPackage thisPackage, String identifier)
   {
      for (Iterator<? extends FClass> iter = thisPackage.iteratorOfDeclares(); iter.hasNext(); )
      {
         FFile file = iter.next().getFile();
         if (file != null && identifier.equals(file.getName()))
         {
            return file;
         }
      }
      return null;
   }


   /**
    * Looks for the package with the given name relatively qualified to this package.
    * 
    * @param name the qualified class name
    * @return the package with the given name or null
    */
   public static FPackage findPackage(FPackage thisPackage, String name)
   {
      if (thisPackage == null)
      {
         return null;
      }

      FPackage result = null;

      int indexOfFirstDot = name.indexOf('.');
      if (indexOfFirstDot != -1)
      {
         String subPackageName = name.substring(0, indexOfFirstDot);
         FPackage subPackage = thisPackage.getFromPackages(subPackageName);
         if (subPackage != null)
         {
            result = subPackage
                  .findPackage(name.substring(indexOfFirstDot + 1));
         }
      }
      else
      {
         result = thisPackage.getFromPackages(name);
      }

      return result;
   }


   /**
    * Provides a package with the given name relatively qualified to this package. If the package
    * and its parent packages don't exist, it creates them. The packages returned are persistent.
    * 
    * @param thisPackage the package to start from
    * @param name the qualified package name
    * @return The package with the given name.
    */
   public static FPackage providePackage(FPackage thisPackage, String name)
   {
      return providePackage(thisPackage, name, true);
   }


   /**
    * Provides a package with the given name relatively qualified to this package. If the package
    * and its parent packages don't exist, it creates them.
    * 
    * @param thisPackage the package to start from
    * @param name the qualified package name
    * @param persistent indicates, if the packages should be persistent
    * @return The package with the given name.
    */
   public static FPackage providePackage(FPackage thisPackage, String name,
         boolean persistent)
   {
      if (thisPackage == null)
      {
         return null;
      }

      FFactory<FPackage> factory = thisPackage.getProject().getFromFactories(
            FPackage.class);
      FPackage result = null;

      int indexOfFirstDot = name.indexOf('.');
      if (indexOfFirstDot != -1)
      {
         String subPackageName = name.substring(0, indexOfFirstDot);
         FPackage subPackage = thisPackage.getFromPackages(subPackageName);
         if (subPackage == null)
         {
            subPackage = factory.create(persistent);
            subPackage.setName(subPackageName);
            thisPackage.addToPackages(subPackage);
         }
         result = providePackage(subPackage, name
               .substring(indexOfFirstDot + 1), persistent);
      }
      else
      {
         result = thisPackage.getFromPackages(name);
         if (result == null)
         {
            result = factory.create(persistent);
            result.setName(name);
            thisPackage.addToPackages(result);
         }
      }

      return result;
   }


   public static FClass provideClass(FPackage parent, String className)
   {
      return provideClass(parent, className, true);
   }


   public static FClass provideClass(FPackage parent, String className,
         boolean persistent)
   {
      FClass result = parent.findClass(className);

      if (result == null)
      {
         // create a new one
         result = parent.getProject().getFromFactories(FClass.class).create(persistent);
         result.setName(className);
         // usually setName also sets the package.
         // for security reasons check it. AZ.
         if (result.getDeclaredInPackage() == null)
         {
            result.setDeclaredInPackage(parent);
         }
      }
      return result;
   }


   /**
    * Removes empty and unused packages recursively.
    * <p>
    * Example: for the given package hierarchy "de.uni_paderborn.fujaba.uml" the package "uml" will
    * be removed if it contains no more classes and packages and the package is not used as an
    * import declaration in a file. "fujaba" will be removed afterwards if it also contains no more
    * classes and packages, and so on...
    * 
    * @param thisPackage The package in which to search for the package to be removed.
    * @param name The qualified package name relatively to this package.
    */
   public static void removeEmptyPackages(FPackage thisPackage, String name)
   {
      if (thisPackage == null)
      {
         return;
      }

      int indexOfFirstDot = name.indexOf('.');
      if (indexOfFirstDot != -1)
      {
         String subPackageName = name.substring(0, indexOfFirstDot);
         FPackage subPackage = thisPackage.getFromPackages(subPackageName);
         if (subPackage != null)
         {
            subPackage.removeEmptyPackages(name.substring(indexOfFirstDot + 1));

            if (subPackage.sizeOfPackages() == 0
                  && subPackage.sizeOfDeclares() == 0
                  && subPackage.sizeOfRevImportedPackages() == 0)
            {
               subPackage.removeYou();
            }
         }
      }
      else
      {
         FPackage subPackage = thisPackage.getFromPackages(name);
         if (subPackage != null)
         {
            if (subPackage.sizeOfPackages() == 0
                  && subPackage.sizeOfDeclares() == 0
                  && subPackage.sizeOfRevImportedPackages() == 0)
            {
               subPackage.removeYou();
            }
         }
      }
   }


   public static String getPackagePath(FPackage thisPackage)
   {
      if (thisPackage == null)
      {
         return null;
      }

      // check if this is the default package
      if (thisPackage == thisPackage.getProject().getRootPackage())
      {
         // the default
         return ".";
      }
      else
      {
         StringBuffer fullName = new StringBuffer(thisPackage.getName());
         FPackage currentPackage = thisPackage.getParent();
         FPackage rootPackage = thisPackage.getProject().getRootPackage();

         while (currentPackage != rootPackage && currentPackage != null)
         {
            fullName.insert(0, File.separator);
            fullName.insert(0, currentPackage.getName());
            currentPackage = currentPackage.getParent();
         }
         return fullName.toString();
      }
   }


   public static boolean hasParent(FPackage thisPackage)
   {
      if (thisPackage == null)
      {
         return false;
      }

      return ((thisPackage.getParent() != null) && (thisPackage.getParent() != thisPackage
            .getProject().getRootPackage()));
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    * 
    * @return the logical parent of this element, may not return null unless this is the top level
    *         node (project)
    */
   public static FElement getParentElement(FPackage thisPackage)
   {
      if (thisPackage == null)
      {
         return null;
      }

      if (thisPackage.getParent() != null)
      {
         return thisPackage.getParent();
      }
      else
      {
         return thisPackage.getProject();
      }
   }


   /**
    * @return The full qualified name of the given package. Returns the empty string if the package is
    *         the default/ root package. Returns null if the given package is null.
    */
   public static String getFullPackageName(FPackage thisPackage)
   {
      if (thisPackage == null)
      {
         logger.warn("given package is null'");
         return null;
      }
      
      FProject project = thisPackage.getProject();
      FPackage rootPackage = project.getRootPackage();
      if (rootPackage == null)
      {
         logger.error("no root package set in project '" + project + ":" + project.getClass().getName() + "'");
      }
      
      return computeFullPackageName(thisPackage, rootPackage).toString();
   }


   /**
    * Recursively computes the full qualified name of the given package.
    * 
    * @param thisPackage Package for which the name should be computed.
    * @param rootPackage Root package of the project. Might be null.
    * @return Full qualified name of the given package.
    */
   private static StringBuffer computeFullPackageName(FPackage thisPackage, final FPackage rootPackage)
   {
      if (thisPackage == null)
      {
         return new StringBuffer();
      }
      else if (thisPackage == rootPackage)
      {
         // do not return the name of the root package as it might be named "RootPackage" or something like that
         return new StringBuffer();
      }
      else /*if (thisPackage != null && thisPackage != rootPackage)*/
      {
         FPackage parentPackage = thisPackage.getParent();
         if (parentPackage == null)
         {
            // WORKAROUND: there are fpr-Files which are corrupted
            logger.warn("potential corrupt package found: '"
                              + thisPackage.getName()
                              + "' is not attached to another package and is not the root package!");
         }
         else /*if (parentPackage != null)*/
         {
            // check if parent package is a root package but is not the root package of its project
            if (rootPackage != null
                  && parentPackage != rootPackage
                  && parentPackage.getParent() == null)
            {
               logger.info("package '" + thisPackage.getName()
                     + "' is contained in a root package that is not the root package of its project!");
            }
         }

         StringBuffer parentPackageName = computeFullPackageName(parentPackage, rootPackage);
         if (parentPackageName.length() != 0)
         {
            parentPackageName.append(".");
         }
         parentPackageName.append(thisPackage.getName());

         return parentPackageName;
      }
   }


   /**
    * Get an iterator of all subpackages of the given package.
    * @param aPackage A package.
    * @return If successful an iterator containing all subpackages of the given package, null otherwise.
    */
   public static Iterator<FPackage> iteratorOfAllSubPackages(FPackage aPackage)
   {
      if (aPackage == null)
      {
         return null;
      }
      
      IteratorsIterator<FPackage> iterator = new IteratorsIterator<FPackage>(aPackage.iteratorOfPackages());
      for (Iterator<? extends FPackage> it = aPackage.iteratorOfPackages(); it.hasNext();)
      {
         FPackage subPackage = it.next();
         iterator.append(iteratorOfAllSubPackages(subPackage));
      }
      return iterator;
   }
}

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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import de.uni_kassel.features.FeatureAccessModule;
import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.metamodel.common.FModelRootNode;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.metamodel.structure.util.FPackageUtility;
import de.uni_paderborn.fujaba.project.ProjectDependencyCycleException;
import de.uni_paderborn.fujaba.versioning.Versioning;
import de.upb.tools.fca.FHashSet;


/**
 * Utility class for dealing with instances of FProject.
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class FProjectUtility
{

   private static Logger log = Logger.getLogger(FProjectUtility.class);


   public static FPackage findPackage(FProject project, String fullName,
         boolean create, boolean persistent)
   {
      // dont't return if fullName is empty, because
      // this means the default-package
      if (fullName == null)
      { // if (fullName == null || fullName.length() == 0)

         return null;
      }
      FPackage tmpPackage = getFromPackages(project, fullName);
      if (tmpPackage == null && create)
      {
         tmpPackage = project.getFromFactories(FPackage.class).create(
               persistent);
         if (log.isDebugEnabled())
         {
            log.debug("Creating new Package: " + fullName);
         }
         addToPackages(project, tmpPackage, fullName, persistent);
      }
      return tmpPackage;
   }
   

   /**
    * Works like method 'getOuterClassOfFullQualifiedType' but returns the 'outest' class if an
    * inner-class-definition has been passed.
    * 
    * <pre>
    * i.e. type = x.y.z.Outer$Inner1$...$InnerN-1$InnerN
    *      returns x.y.z.Outer
    * </pre>
    * 
    * @param type full qualified class name.
    * @return see method 'getOuterClassOfFullQualifiedType'
    * 
    * @see FProjectUtility#getOuterClassOfFullQualifiedType(String)
    */
   public static String getOutestClassOfFullQualifiedType(String type)
   {
      String result = FProjectUtility.getOuterClassOfFullQualifiedType(type);

      // if we get the empty-string it wasn't an inner class-definition
      if (result == null || "".equals(result))
      {
         return result;
      }
      // if we get a non-empty-string, it is potentialy an inner-class-definition
      // of format 'x.y.z.RootOuterClass$Inner1$...$InnerN-1'
      else
      {
         int firstDollarIndex = result.indexOf("$");

         // if a '$' exists, we return the part before the dollar
         if (firstDollarIndex > -1)
            return result.substring(0, firstDollarIndex);
         // otherwise it was a non-inner-class-definition and we simply return it
         else
            return result;
      }
   }


   /**
    * gives back the outer class name of an inner class. recognizes the dollar format. for example:
    * 
    * <pre>
    *    type = java.lang.String
    *    returns ""
    *
    *    type = java.util.Map$Entry
    *    returns java.util.Map
    *
    *    type = java.util.Map.Entry
    *    returns ""
    *    (because the specified type is not recognized as an innerclass definition)
    *
    *    type = Map$Entry
    *    returns Map
    *    
    *    type = Map.Entry
    *    returns Map
    *    (because the first component is not a valid package name
    *    and so represents an outerclass
    *    (by Java convention the first component should start with a lowercase letter))
    *    
    *    ------
    *    in general:
    *    
    *    type = x.y.z.Outer$Inner1$...$InnerN-1$InnerN
    *    returns x.y.z.Outer$Inner1$...$InnerN-1
    *    
    *    type = name1stCharLowerCase.AdditionalSegments
    *    returns ""
    *    
    *    type = Name1stCharUpperCase.AdditionalSegments
    *    returns "Name1stCharUpperCase"
    * </pre>
    * 
    * Note that java package names may have components, that start with an uppercase letter (like
    * class names do), so one should separate innerclasses from outerclasses by '$' instead of '.'
    * to be sure the outerclass is detected.
    * 
    * @param type A full qualified class name
    * @return If 'type' is an innerclass definition the full qualified outerclass is returned,
    *         otherwise if 'type' is not an innerclass, the empty string is returned.
    */
   public static String getOuterClassOfFullQualifiedType(String type)
   {
      String result = "";
      if (type != null && !type.equals(""))
      {
         int dollar = type.lastIndexOf('$');

         if (dollar > -1)
         {
            result = type.substring(0, dollar);
         }
         else
         {
            // no explizit innerclass definition found:
            // check if the first component starts with an
            // uppercase letter (i.e., is a class and not a package definition)
            int theDot = type.indexOf('.');
            if (theDot > -1)
            {
               // wrong package definition '.x.y.z.Class'
               if (theDot == 0)
               {
                  // print an error-message?
               }
               else
               {
                  String firstSegment = type.substring(0, theDot);
                  String firstChar = firstSegment.substring(0, 1);
                  if (firstChar.equals(firstChar.toUpperCase()))
                  {
                     // first segment represents a class, so
                     // we assume this is the outerclass
                     result = firstSegment;
                  }
                  else
                  {
                     // first segment is a package name.
                     // so the type MUST be a non-inner-class definition!
                     // for that we return the empty-string
                     // result = ""; // is already done at method start
                  }
               }
            }
         }
      }
      return result;
   }


   /**
    * Returns the class name of a full qualified class with the package removed.
    * 
    * Example:
    * 
    * <pre>
    *     type = java.lang.String
    *     returns String
    *
    *     type = java.util.Map.Entry
    *     returns Map.Entry
    * </pre>
    */
   public static String removePackageFromFullQualifiedType(String type)
   {
      String result = getPackageOfFullQualifiedType(type);

      if (result.length() > 0)
      {
         result = type.substring(result.length() + 1);
      }
      else
      {
         result = type;
      }

      return result;
   }


   /**
    * Returns the package name of a full qualified class or type.
    * 
    * Example:
    * 
    * <pre>
    *   type = java.lang.String
    *   returns java.lang
    *   
    *   type = java.util.Map$Entry
    *   returns java.util
    *   
    *   type = java.util.Map.Entry
    *   returns java.util.Map
    * </pre>
    */
   public static String getPackageOfFullQualifiedType(String type)
   {
      String result = "";
      if (type != null && !type.equals(""))
      {
         int theDot = type.lastIndexOf('.');
         if (theDot > 0)
         {
            // we have found a full qualified class
            // of style: x.y.z.Classname
            result = type.substring(0, theDot);
         }
      }
      return result;
   }

   public static FClass provideClassWithoutRequiredProjects (FProject project, String qualName)
   {
      return provideClassWithoutRequiredProjects (project, qualName, true);
   }

   public static FClass provideClassWithoutRequiredProjects (FProject project, String qualName, boolean persistent)
   {
	   FPackage rootPack = project.getRootPackage();
	   FClass result = FPackageUtility.provideClass(rootPack, qualName, persistent);
	   return result;
   }

   public static FClass findClass (FProject project, String qualName)
   {
      return findClass(project, qualName, true);
   }

   public static FClass findClass (FProject project, String qualName, boolean searchInRequiredProjects)
   {
      FPackage rootPack = project.getRootPackage();
      FClass result = FPackageUtility.findClass(rootPack, qualName);
      if (result != null)
      {
         return result;
      }
      if (searchInRequiredProjects)
      {
         Iterator<? extends FProject> iter = project.iteratorOfRequires();
         while (iter.hasNext())
         {
            FProject requiredProject = iter.next();
            result = FProjectUtility.findClass(requiredProject, qualName);
            if (result != null)
            {
               return result;
            }
         }
      }
      return null;
   }
   
   
   public static FClass findClass(FProject project, String fullPackageName, String className)
   {
	   if (fullPackageName == null || className ==null)
	   {
		   return null;
	   }
	   FPackage aPackage = project.getRootPackage();
	   if (fullPackageName.length() != 0)
	   {
		   aPackage = FProjectUtility.findPackage(project, fullPackageName, true, true);		   
	   }
	   return FPackageUtility.findClass(aPackage, className);
   }
   
   
   public static Set<FClass> getAllClasses (FProject project)
   {
   	FHashSet<FClass> allClasses = new FHashSet<FClass>();
   	FPackage rootPack = project.getRootPackage();
      FPackageUtility.getAllClasses (rootPack, allClasses);
   	
   	// collect required projects
      Iterator<? extends FProject> iter = project.iteratorOfRequires();
      while (iter.hasNext())
      {
      	FProject requiredProject = iter.next();
         rootPack = requiredProject.getRootPackage();
         if (rootPack != null)
         {
         	FPackageUtility.getAllClasses (rootPack, allClasses);
         }
      }
   	
   	return allClasses;
   }

   public static FClass provideClass (FProject project, String qualName)
   {
      return provideClass (project, qualName, true);
   }

   public static FClass provideClass (FProject project, String qualName, boolean persistent)
   {
      FClass result = findClass (project, qualName);
      if (result == null)
      {
         return provideClassWithoutRequiredProjects(project, qualName, persistent);
      }
      return result;
   }
   
   public static FClass provideClass(FProject project, String fullPackageName, String className)
   {
	   if (fullPackageName == null || className ==null)
	   {
		   return null;
	   }
	   FPackage aPackage = project.getRootPackage();
	   if (fullPackageName.length() != 0)
	   {
		   aPackage = FProjectUtility.findPackage(project, fullPackageName, true, true);		   
	   }
	   return FPackageUtility.provideClass(aPackage, className);
   }
   

   public static FPackage getFromPackages(FProject project, String key)
   {
      StringTokenizer tokenizer = new StringTokenizer(key, ".");
      FPackage currentPackage = project.getRootPackage();

      while (tokenizer.hasMoreElements() && currentPackage != null)
      {
         String currentKey = tokenizer.nextToken();
         currentPackage = currentPackage.getFromPackages(currentKey);
      }
      return currentPackage;
   }


   /**
    * Adds a feature to the ToPackages attribute of the UMLProject object
    * 
    * @param elem Access method for an one to n association.
    * @param fullName Access method for an one to n association.
    */
   public static void addToPackages(FProject project, FPackage elem,
         String fullName, boolean persistent)
   {
      if (elem == null || fullName == null)
      {
         throw new RuntimeExceptionWithContext(
               "Cannot add an Element with key = null to an OrderedMap", elem);
      }

      if (fullName.length() != 0 && !hasInPackages(project, fullName))
      {
         StringTokenizer tokenizer = new StringTokenizer(fullName, ".");
         FPackage currentPackage = project.getRootPackage();
         FPackage parentOfCurrent = null;
         String currentKey = fullName;

         while (tokenizer.hasMoreElements() && currentPackage != null)
         {
            currentKey = tokenizer.nextToken();
            parentOfCurrent = currentPackage;
            currentPackage = parentOfCurrent.getFromPackages(currentKey);
            if (currentPackage == null && tokenizer.hasMoreElements())
            {
               currentPackage = project.getFromFactories(FPackage.class)
                     .create(persistent);
               currentPackage.setName(currentKey);
               currentPackage.setParent(parentOfCurrent);
            }
         }
         if (currentPackage != null && parentOfCurrent != null)
         {
            // replace the last package with elem;
            parentOfCurrent.removeFromPackages(currentPackage);
         }
         // adjust the name of elem
         elem.setName(currentKey);
         if ( parentOfCurrent != null )
         {
            parentOfCurrent.addToPackages(elem);
         }
      }
   }


   private static boolean hasInPackages(FProject project, String key)
   {
      return key != null && (getFromPackages(project, key) != null);
   }


   /**
    * Retrieve the modelRootNode by the specified name.
    * 
    * @param key The name of the modelRootNode to search for.
    * @return Return the modelRootNode or null, if no such modelRootNode exists.
    */
   public static FModelRootNode getFromModelRootNodes(FProject project,
         String key)
   {
      Iterator<? extends FModelRootNode> iter = project.iteratorOfModelRootNodes();
      while (iter.hasNext())
      {
         FModelRootNode modelRootNode = iter.next();
         if ((modelRootNode.getName() != null)
               && (modelRootNode.getName().equals(key)))
         {
            return modelRootNode;
         }
      }

      return null;
   }
   
   private static FeatureAccessModule noRepositoryFAM = null;

   public static FeatureAccessModule getFeatureAccessModule(FProject project)
   {
      FeatureAccessModule reflect;
      if (project.getRepository() != null)
      {
         reflect = project.getRepository().getFeatureAccessModule();
      }
      else
      {
    	  if (noRepositoryFAM == null)
    	  {
    	    noRepositoryFAM = Versioning.get().createFeatureAccessModule();
    	  }
    	  reflect = noRepositoryFAM;
      }
      return reflect;
   }
   
   /**
    * Checks for two projects whether it is permitted to add one to the 
    * other as a project dependency. Throws a {@link ProjectDependencyCycleException}
    * if a cycle would be created by adding the required project to the dependent one.
    * 
    * @param dependent The project which shall get dependent of the required project.
    * @param required The project which shall be added as a required project to the dependent one. 
    * @return true if no cycle can be determined
    * @throws ProjectDependencyCycleException if cycle is found
    */
   public static boolean isAddingRequiredProjectAllowed(FProject dependent, FProject required) throws ProjectDependencyCycleException
   {
      ArrayList<FProject> pList = new ArrayList<FProject>();
      pList.add(dependent);
      
      checkCircularDependencies(pList, required);
      
      return true;
   }
   
   /**
    * Checks for the given project whether it has circular dependencies.
    * Throws a {@link ProjectDependencyCycleException} if a cycle can be
    * determined.
    * 
    * @param project The project to test.
    * @return true of no cycle can be determined
    * @throws ProjectDependencyCycleException if cycle is found
    */
   public static boolean hasCycleInDependencies(FProject project) throws ProjectDependencyCycleException
   {
      ArrayList<FProject> pList = new ArrayList<FProject>();
      checkCircularDependencies(pList, project);
      return true;
   }
   
   
   
   /**
    * Searches for circular dependencies by iterating over the requires 
    * association of a project and checking whether it was visited before
    * already. 
    * 
    * @param pList A list containing the projects visited so far
    * @param required The next visited project
    * @throws ProjectDependencyCycleException if cycle is found
    */
   private static void checkCircularDependencies(ArrayList<FProject> pList, FProject required) throws ProjectDependencyCycleException
   {
      if(pList == null || required == null)
      {
         throw new IllegalArgumentException("Arguments must not be null!");
      }
      
      if(pList.contains(required))
      {
         throw new ProjectDependencyCycleException("There is a cyclic project dependency. " +
         		"The project \"" + required.getName() == null ? "null" : required.getName() 
         		      + "\" is part of a cycle!");
      }
      
      pList.add(required);
      Iterator<? extends FProject> i = required.iteratorOfRequires();
      while(i.hasNext())
      {
         FProject p = i.next();
         checkCircularDependencies(pList, p);
      }
      pList.remove(required);
   }
}

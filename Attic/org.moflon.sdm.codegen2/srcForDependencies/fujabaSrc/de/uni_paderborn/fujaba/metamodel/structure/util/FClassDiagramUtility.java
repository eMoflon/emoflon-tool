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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.uni_paderborn.fujaba.metamodel.common.FCommentary;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FElementRef;
import de.uni_paderborn.fujaba.metamodel.common.FModelRootNode;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FClassDiagram;
import de.uni_paderborn.fujaba.metamodel.structure.FGeneralization;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.upb.tools.fca.FHashSet;


/**
 * Utility class for reusable UMLClassDiagram methodes.
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class FClassDiagramUtility
{

   /**
    * Only for tests, will be removed.
    * 
    * @param class1 The new ofAssociations value
    * @param class2 The new ofAssociations value
    */
   public static Set<? extends FAssoc> setOfAssociations(FClass class1,
         FClass class2)
   {
      HashSet<FAssoc> set = new HashSet<FAssoc>();

      if (class1 != null && class2 != null)
      {
         Iterator<? extends FRole> roleIter = class1.iteratorOfRoles();
         while (roleIter.hasNext())
         {
            FRole leftRole = roleIter.next();
            FAssoc assoc = leftRole.getAssoc();
            FRole rightRole = assoc.getRightRole();

            if (rightRole.getTarget() == class2)
            {
               set.add(assoc);
            }
         }

         FGeneralization generalization;
         FClass superClass;

         Iterator<? extends FGeneralization> revSubclassIter1 = class1
               .iteratorOfRevSubclass();
         while (revSubclassIter1.hasNext())
         {
            generalization = revSubclassIter1.next();
            superClass = generalization.getSuperclass();
            set.addAll(setOfAssociations(superClass, class2));
         }

         Iterator<? extends FGeneralization> revSubclassIter2 = class2
               .iteratorOfRevSubclass();
         while (revSubclassIter2.hasNext())
         {
            generalization = revSubclassIter2.next();
            superClass = generalization.getSuperclass();
            set.addAll(setOfAssociations(class1, superClass));
         }
      }

      return set;
   }


   /**
    * Calculates all associations between two classes and returns the list of associations.
    * Inherited assocs will be also considered.
    * 
    * @param class1 the first class.
    * @param class2 the second class.
    * @return A list containing all possible associations between the specified classes.
    * @throws IllegalArgumentException If at least one of the parameters is null.
    * @deprecated returned assocs have never been (and really shouldn't be) in any specific order,
    *             so List is misleading here. It's also inefficient implementation-wise. Use
    *             {@link #calculateAssocs(FClass, FClass)} instead.
    */
   public static List<? extends FAssoc> calculatePossibleAssocs(FClass class1,
         FClass class2) throws IllegalArgumentException
   {
      return new ArrayList<FAssoc>(calculateAssocs(class1, class2));
   }


   /**
    * Calculates all associations between two classes and returns the list of associations.
    * Inherited assocs will be also considered.
    * 
    * @param class1 the first class.
    * @param class2 the second class.
    * @return A set containing all possible associations between the specified classes.
    * @throws IllegalArgumentException If at least one of the parameters is null.
    */
   public static Set<? extends FAssoc> calculateAssocs(FClass class1,
         FClass class2) throws IllegalArgumentException
   {
      if ((class1 == null) || (class2 == null))
      {
         return new HashSet<FAssoc>();
      }

      // Calculate the intersection of assocsFromClass1 and
      // assocsFromClass2. The intersection is the set of
      // associations which exist between both class1 and class2.
      Set<FAssoc> assocs = new FHashSet<FAssoc>();
      Set<? extends FRole> rolesFromClass1 = class1.getAllRoles();
      Set<? extends FRole> rolesFromClass2 = class2.getAllRoles();

      for (FRole role : rolesFromClass1)
      {
         FAssoc assoc = role.getAssoc();

         // Check if the assoc is not null to cope with damaged model files.
         if (assoc != null)
         {
            FRole partner = assoc.getLeftRole();
            if (partner == role)
            {
               partner = assoc.getRightRole();
            }
            if (rolesFromClass2.contains(partner))
            {
               assocs.add(assoc);
            }
         }
      }

      return assocs;
   }


   /**
    * Calculates all associations between two classes via ASGElementRef and returns the list of
    * associations.
    */
   public static FHashSet calculatePossibleAssocsViaASGElementRef(
         FClass refClass, FClass elemClass)
   {
      if ((refClass == null) || (elemClass == null) || (refClass == elemClass)
            || (isDerivedfrom(refClass, FElementRef.class.getName())))
      {
         return null;
      }
      if (!isDerivedfrom(elemClass, FElement.class.getName()))
      {
         return null;
      }

      FHashSet assocs = new FHashSet();

      // ---- all associations from the supposed reference class
      Set<? extends FAssoc> assocsFromClass = refClass.getAllAssocs();
      for (FAssoc assoc : assocsFromClass)
      {
         if (isDerivedfrom(assoc.getLeftRole().getTarget(), FElementRef.class
               .getName())
               || isDerivedfrom(assoc.getRightRole().getTarget(),
                     FElementRef.class.getName()))
         {
            assocs.add(assoc);
         }
      }

      if (assocs.isEmpty())
      {
         return null;
      }
      else
      {
         return assocs;
      }

   } // end calculatePossibleAssocsViaASGElementRef


   /**
    * @param cls The class to be checked.
    * @param superClsName The full qualified name of the superclass.
    * @return True if the specified class is derived from the specified superclass.
    */
   public static boolean isDerivedfrom(FClass cls, String superClsName)
   {
      Iterator<? extends FClass> superClsIter = cls.iteratorOfSuperClasses();

      while (superClsIter.hasNext())
      {
         FClass superCls = superClsIter.next();
         if (superCls != null && (superCls.getFullClassName().equals(superClsName)
               || isDerivedfrom(superCls, superClsName)))
         {
            return true;
         }
      }

      return false;
   }


   /**
    * returns the association for a given association name.
    */
   public static FAssoc getAssoc(String assocName, FProject project)
   {
      FClassDiagram clazzDiagram = null;
      FAssoc foundAssoc = null;

      Iterator<? extends FModelRootNode> diagIter = project
            .iteratorOfModelRootNodes();
      while (diagIter.hasNext() && (null == clazzDiagram))
      {
         FModelRootNode modelRootNode = diagIter.next();
         if (modelRootNode instanceof FClassDiagram)
         {
            clazzDiagram = (FClassDiagram) modelRootNode;

            Iterator<? extends FElement> iter = clazzDiagram
                  .iteratorOfElements();
            while ((iter.hasNext()) && (null == foundAssoc))
            {
               FElement element = iter.next();
               if (element instanceof FAssoc)
               {
                  FAssoc assoc = (FAssoc) element;
                  if ((assoc.getName()).equals(assocName))
                  {
                     foundAssoc = assoc;
                  }
               }
            }

            if (null == foundAssoc)
            {
               clazzDiagram = null;
            }
         }
      }

      return foundAssoc;
   }


   public static FAssoc searchForAssoc(FClass class1, FClass class2,
         String assocName)
   {
      FAssoc foundAssoc = null;
      boolean found = false;

      Iterator<? extends FAssoc> iter = calculateAssocs(class1, class2)
            .iterator();
      while (iter.hasNext() && !found)
      {
         FAssoc currentAssoc = iter.next();
         if (currentAssoc.getName().equals(assocName))
         {
            foundAssoc = currentAssoc;
            found = true;
         }
      }

      return foundAssoc;
   }


   public static FAssoc searchForAssocForTargetRoleName(FClass class1,
         FClass class2, String roleName)
   {
      FAssoc foundAssoc = null;
      FRole aRole = null;
      boolean found = false;

      Iterator<? extends FAssoc> iter = calculateAssocs(class1, class2)
            .iterator();
      while (iter.hasNext() && !found)
      {
         FAssoc currentAssoc = iter.next();
         // the role that targets class2 must have name RoleName
         aRole = currentAssoc.getLeftRole();
         if (!aRole.getName().equals(roleName))
         {
            aRole = currentAssoc.getRightRole();
         }
         if (aRole.getName().equals(roleName))
         {
            // does the role target the desired class2
            FClass roleTargetClass = aRole.getTarget();
            if (class2.isChildOf(roleTargetClass))
            {
               foundAssoc = currentAssoc;
               found = true;
            }
         }
      }

      return foundAssoc;
   }


   /**
    * removes tmpMethod with has the same name (not signature!) as FMethod method
    */
   public static void removeMethodBySameName(FClass cls, FMethod method)
   {
      FMethod tmpMethod = null;
      Iterator<? extends FMethod> iter = cls.iteratorOfMethods();
      boolean found = false;

      if (method != null)
      {
         while (!found && iter.hasNext())
         {
            tmpMethod = iter.next();
            found = (tmpMethod.getName().equals(method.getName()));
         }
      }

      if ((method != null) && found)
      {
         tmpMethod.setParent(null);
         tmpMethod.removeYou();
      }
   }


   /**
    * returnes class with name className if this class can be found in the ClassDiagram cDiag
    * returnes null if no class with the name can be found
    * 
    * @param cDiag classDiagram to search in
    * @param className name of class to search for
    * @return No description provided
    */
   public static FClass searchForClass(FClassDiagram cDiag, String className)
   {
      FClass result = null;
      FElement tmpElement;

      Iterator iter = cDiag.iteratorOfElements();
      while (iter.hasNext() && result == null)
      {
         tmpElement = (FElement) iter.next();

         if (tmpElement instanceof FClass
               && tmpElement.getName().equals(className))
         {
            result = (FClass) tmpElement;
         }
      }
      return result;
   }


   /**
    * Add the given class and all its related elements, like generalizations, comments and assocs to
    * the given classdiagram.
    * 
    * @param fClassDiagram The classdiagram, to which the class should be added.
    * @param fClass The class which should be added.
    */
   public static void addToElementsWithContext(FClassDiagram fClassDiagram,
         FClass fClass)
   {
      // make sure the class is contained in the diagram
      fClassDiagram.addToElements(fClass);

      // add the classes comment
      FCommentary comment = fClass.getComment();
      if (comment != null)
      {
         fClassDiagram.addToElements(comment);
      }

      FGeneralization gen;
      Iterator<? extends FGeneralization> generalizationIter = fClass
            .iteratorOfRevSubclass();
      while (generalizationIter.hasNext())
      {
         gen = generalizationIter.next();
         if (fClassDiagram.hasInElements(gen.getSuperclass()))
         {
            fClassDiagram.addToElements(gen);
         }
      }

      generalizationIter = fClass.iteratorOfRevSuperclass();
      while (generalizationIter.hasNext())
      {
         gen = generalizationIter.next();
         if (fClassDiagram.hasInElements(gen.getSubclass()))
         {
            fClassDiagram.addToElements(gen);
         }
      }

      Iterator<? extends FRole> roleIter = fClass.iteratorOfRoles();
      while (roleIter.hasNext())
      {
         FRole role = roleIter.next();
         FAssoc assoc = role.getAssoc();

         if (role.getPartnerRole() != null)
         {
            FClass partner = role.getPartnerRole().getTarget();
            if (fClassDiagram.hasInElements(partner))
            {
               fClassDiagram.addToElements(assoc);
            }
         }
      }
   }


   /**
    * Remove the given class and all its related elements, like generalizations, comments and assocs
    * from the given classdiagram.
    * 
    * @param fClassDiagram The classdiagram, from which the class should be removed.
    * @param fClass The class which should be removed.
    */
   public static void removeFromElementsWithContext(
         FClassDiagram fClassDiagram, FClass fClass)
   {
      // remove the comment associated with clazz
      FCommentary comment = fClass.getComment();
      if (comment != null)
      {
         fClassDiagram.removeFromElements(comment);
         // the dashed line between comment and class will not be removed
         // but if someone is familiar with fsa stuff ...
      }

      // remove generalizations
      Iterator<? extends FGeneralization> genIter = fClass
            .iteratorOfRevSubclass();
      while (genIter.hasNext())
      {
         FGeneralization generalization = genIter.next();
         fClassDiagram.removeFromElements(generalization);
      }
      genIter = fClass.iteratorOfRevSuperclass();
      while (genIter.hasNext())
      {
         FGeneralization generalization = genIter.next();
         fClassDiagram.removeFromElements(generalization);
      }
      // remove assocs
      Iterator<? extends FRole> rolesIter = fClass.iteratorOfRoles();
      while (rolesIter.hasNext())
      {
         FRole role = rolesIter.next();
         fClassDiagram.removeFromElements(role.getAssoc());
      }

      fClassDiagram.removeFromElements(fClass);
   }
}

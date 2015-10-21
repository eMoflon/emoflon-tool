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
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FDeclaration;
import de.uni_paderborn.fujaba.metamodel.structure.FGeneralization;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.upb.tools.fca.EnumerationForAnIterator;
import de.upb.tools.fca.FHashSet;
import de.upb.tools.fca.FTreeMap;


/**
 * @author Last editor: $Author$
 * @version $Revision$ $Date$
 */
public class FClassUtility
{
   private static Logger log = Logger.getLogger(FClassUtility.class);

   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    * 
    * @return the logical parent of this element;
    */
   public static FElement getParentElement(FClass clazz)
   {
      if (clazz.getDeclaredInPackage() != null)
      {
         return clazz.getDeclaredInPackage();
      }
      else if (clazz.getDeclaredInMethod() != null)
      {
         return clazz.getDeclaredInMethod();
      }
      else if (clazz.getDeclaredInClass() != null)
      {
         return clazz.getDeclaredInClass();
      }
      else
      {
         // declared nowhere
         return null;
      }
   }


   /**
    * Add all methods with given signature to the methods set, found in the subclasses and
    * -interfaces.
    * 
    * @param signature signature to match
    * @return set of found method
    */
   public static Set<? extends FMethod> findMethodsWithSignatureInSubclasses(
         FClass clazz, String signature)
   {
      Set<FMethod> result = new HashSet<FMethod>();
      findMethodsWithSignatureInSubclasses(clazz, signature, result);
      return result;
   }


   /**
    * Add all methods with given signature to the methods set, found in the subclasses and
    * -interfaces.
    * 
    * @param signature signature to match
    * @param methods result set
    */
   private static void findMethodsWithSignatureInSubclasses(FClass fClass,
         String signature, Set<FMethod> methods)
   {
      for (Iterator<? extends FGeneralization> it = fClass
            .iteratorOfRevSuperclass(); it.hasNext();)
      {
         FGeneralization gen = it.next();
         FClass superclass = gen.getSubclass();
         FMethod method = superclass.getFromMethods(signature);
         if (method != null)
         {
            if (methods.contains(method))
            {
               throw new IllegalStateException("Cyclic class hierarchy!");
            }
            methods.add(method);
         }
         findMethodsWithSignatureInSubclasses(superclass, signature, methods);
      }
   }


   /**
    * Add all methods with given signature to the methods set, found in the superclasses and
    * -interfaces.
    * 
    * @param signature signature to match
    * @return set of found method
    */
   public static Set<? extends FMethod> findMethodsWithSignatureInSuperclasses(
         FClass clazz, String signature)
   {
      Set<FMethod> result = new HashSet<FMethod>();
      findMethodsWithSignatureInSuperclasses(clazz, signature, result);
      return result;
   }


   /**
    * Add all methods with given signature to the methods set, found in the superclasses and
    * -interfaces.
    * 
    * @param signature signature to match
    * @param methods result set
    */
   private static void findMethodsWithSignatureInSuperclasses(FClass fClass,
         String signature, Set<FMethod> methods)
   {
      for (Iterator<? extends FGeneralization> it = fClass
            .iteratorOfRevSubclass(); it.hasNext();)
      {
         FGeneralization gen = it.next();
         FClass superclass = gen.getSuperclass();
         FMethod method = superclass.getFromMethods(signature);
         if (method != null)
         {
            if (methods.contains(method))
            {
               throw new IllegalStateException("Cyclic class hierarchy!");
            }
            methods.add(method);
         }
         findMethodsWithSignatureInSuperclasses(superclass, signature, methods);
      }
   }


   public static void replaceMethod(FClass clazz, FMethod method)
   {
      FMethod oldMethod = clazz.getFromMethods(method.getFullMethodName());

      if (oldMethod != null)
      {
         oldMethod.removeYou();
      }

      clazz.addToMethods(method);
   }


   public static FMethod getFromMethodsByShortName(FClass clazz, String key)
   {
      Iterator<? extends FMethod> iter = clazz.iteratorOfMethods();
      while (iter.hasNext())
      {
         FMethod tmpMethod = iter.next();
         if (tmpMethod.getName().equals(key))
         {
            return tmpMethod;
         }
      }
      return null;
   }


   /**
    * Searches recursivly the inheritance hierarchy and looks for methods.
    * 
    * @param key No description provided
    * @return The fromMethodsByShortNameIncludingInherited value
    */
   public static FMethod getFromMethodsByShortNameIncludingInherited(
         FClass clazz, String key)
   {
      // get method from this class
      FMethod firstMethodFound = getFromMethodsByShortName(clazz, key);

      if (firstMethodFound == null)
      {
         // get method from super-classes.
         Iterator<? extends FGeneralization> iter = clazz
               .iteratorOfRevSubclass();
         while (iter.hasNext() && firstMethodFound == null)
         {
            FClass tmpClass = iter.next().getSuperclass();
            firstMethodFound = tmpClass
                  .getFromMethodsByShortNameIncludingInherited(key);
         }
      }
      return firstMethodFound;
   }


   public static Set<? extends FAttr> getAllAttrs(FClass clazz)
   {
      Set<FAttr> theSet = new FHashSet();

      Iterator<? extends FAttr> iterAttr = clazz.iteratorOfAttrs();

      while (iterAttr.hasNext())
      {
         FAttr theAttr = iterAttr.next();
         theSet.add(theAttr);
      } // end of while ()

      FGeneralization tmpGen = null;
      FClass tmpClass = null;

      // ----- add all superclasses to classes
      Iterator<? extends FGeneralization> iterGeneralization = clazz
            .iteratorOfRevSubclass();
      while (iterGeneralization.hasNext())
      {
         tmpGen = iterGeneralization.next();
         tmpClass = tmpGen.getSuperclass();
         if (tmpClass != null)
         {
            theSet.addAll(tmpClass.getAllAttrs());
         }
      }

      return theSet;
   }


   public static Set<? extends FRole> getAllRoles(FClass clazz)
   {
      Set<FRole> theSet = new FHashSet();

      Iterator<? extends FRole> iterRole = clazz.iteratorOfRoles();

      while (iterRole.hasNext())
      {
         FRole theRole = iterRole.next();
         theSet.add(theRole);
      } // end of while ()

      FGeneralization tmpGen = null;
      FClass tmpClass = null;

      // ----- add all superclasses to classes
      Iterator<? extends FGeneralization> iterGeneralization = clazz
            .iteratorOfRevSubclass();
      while (iterGeneralization.hasNext())
      {
         tmpGen = iterGeneralization.next();
         tmpClass = tmpGen.getSuperclass();
         if (tmpClass != clazz)
         {
            if (tmpClass != null)
            {
               theSet.addAll(tmpClass.getAllRoles());
            }
            else
            {
               log.error("broken generalization to superclass detected in class '" + clazz + "'");
            }
         }
      }

      return theSet;
   }


   public static Iterator<? extends FAttr> iteratorOfAllAttrs(FClass clazz)
   {
      return getAllAttrs(clazz).iterator();
   }


   public static Enumeration elementsOfAllAttrs(FClass clazz)
   {
      return new EnumerationForAnIterator(iteratorOfAllAttrs(clazz));
   }


   public static Iterator<? extends FRole> iteratorOfAllRoles(FClass clazz)
   {
      return getAllRoles(clazz).iterator();
   }


   /**
    * Get a Path object containing all superclasses of the class.
    * 
    * @param clazz any FClass
    * @return A Path object containing all superclasses of the class.
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfSuperClasses()
    */
   public static Iterator<? extends FClass> iteratorOfSuperClasses(FClass clazz)
   {
      final ArrayList<FClass> list = new ArrayList<FClass>(clazz.sizeOfRevSubclass());
      for (Iterator<? extends FGeneralization> it = clazz.iteratorOfRevSubclass(); it.hasNext();)
      {
         FGeneralization generalization = it.next();
         FClass superClass = generalization.getSuperclass();
         if (superClass != null)
         {
            list.add(superClass);
         }
         else
         {
            log.error("broken generalization to superclass detected in class '" + clazz + "'");
         }
      }
      return list.iterator();
   }


   /**
    * Get a Path object containing all subclasses of the class.
    *
    * @param clazz any FClass
    * @return A Path object containing all subclasses of the class.
    * @see de.uni_paderborn.fujaba.metamodel.structure.FClass#iteratorOfSubClasses()
    */
   public static Iterator<? extends FClass> iteratorOfSubClasses(FClass clazz)
   {
      final ArrayList<FClass> list = new ArrayList<FClass>(clazz.sizeOfRevSuperclass());
      for (Iterator<? extends FGeneralization> it = clazz.iteratorOfRevSuperclass(); it.hasNext();)
      {
         FGeneralization generalization = it.next();
         FClass sublass = generalization.getSubclass();
         if (sublass != null)
         {
            list.add(sublass);
         }
         else
         {
            log.error("broken generalization to subclass detected in class '" + clazz + "'");
         }
      }
      return list.iterator();
   }


   /**
    * Get all Atrributs which could be accessed from inside the class including derived attributs
    * 
    * @return A set whith all Attrs accessible from inside the class
    */
   public static Set<? extends FAttr> getAllAccessibleAttrs(FClass clazz)
   {
      Set<FAttr> attribs = new FHashSet();
      Set<String> attribNames = new FHashSet();

      LinkedList<FClass> classes = new LinkedList<FClass>();

      FClass cls;
      FAttr tmpAttr;
      FGeneralization tmpGen;
      FClass tmpClass;

      // first add all the attrs of this class
      if (clazz.sizeOfAttrs() > 0)
      {
         for (Iterator<? extends FAttr> iterAttrs = clazz.iteratorOfAttrs(); iterAttrs
               .hasNext();)
         {
            attribs.add(iterAttrs.next());
         }
         for (Iterator<String> iterKeys = clazz.keysOfAttrs(); iterKeys
               .hasNext();)
         {
            attribNames.add(iterKeys.next());
         }
      }
      // ----- add superclasses to classes
      Iterator<? extends FGeneralization> iter = clazz.iteratorOfRevSubclass();
      while (iter.hasNext())
      {
         tmpGen = iter.next();
         tmpClass = tmpGen.getSuperclass();
         classes.addLast(tmpClass);
      }

      while (!(classes.isEmpty()))
      {
         cls = classes.removeFirst();

         // ----- get the attrs from class

         Iterator<? extends FAttr> attrIter = cls.iteratorOfAttrs();
         while (attrIter.hasNext())
         {
            tmpAttr = attrIter.next();

            if (tmpAttr.getVisibility() != FDeclaration.PRIVATE)
            {
               if (tmpAttr.getVisibility() != FDeclaration.PACKAGE)
               {
                  if (!(attribNames.contains(tmpAttr.getName())))
                  {
                     attribs.add(tmpAttr);
                  }
               }
               else
               {
                  if (cls.getDeclaredInPackage() == clazz
                        .getDeclaredInPackage())
                  {
                     if (!(attribNames.contains(tmpAttr.getName())))
                     {
                        attribs.add(tmpAttr);
                     }
                  }
               }
            }
            attribNames.add(tmpAttr.getName());
         }

         // ----- add all superclasses to classes
         iter = cls.iteratorOfRevSubclass();
         while (iter.hasNext())
         {
            tmpGen = iter.next();
            tmpClass = tmpGen.getSuperclass();
            classes.addLast(tmpClass);
         }
      }

      return attribs;
   } // getAllAccessibleAttrs


   public static Iterator<? extends FMethod> iteratorOfAllAccessibleMethods(
         FClass clazz)
   {
      return getAllAccessibleMethods(clazz).iterator();
   }


   /**
    * Get all Methods which could be accessed from inside the class including derived methods
    * 
    * @return A set whith all Methods accessible from inside the class
    */
   public static SortedMap<String, ? extends FMethod> getAllAccessibleMethodObjects(
         FClass clazz)
   {
      SortedMap<String, FMethod> accMethods = new FTreeMap();

      LinkedList<FClass> classes = new LinkedList<FClass>();

      FClass cls;
      FMethod tmpMeth;
      FGeneralization tmpGen;
      FClass tmpClass;

      // first add all the methods of this class
      for (Iterator<? extends FMethod> it = clazz.iteratorOfMethods(); it
            .hasNext();)
      {
         FMethod method = it.next();
         accMethods.put(method.getFullMethodName(), method);
      }

      // ----- add superclasses to classes
      Iterator<? extends FGeneralization> iter = clazz.iteratorOfRevSubclass();
      while (iter.hasNext())
      {
         tmpGen = iter.next();
         tmpClass = tmpGen.getSuperclass();
         if (tmpClass != null)
         {
            classes.addLast(tmpClass);
         }
         else
         {
            log.error("broken generalization to superclass detected in class '" + clazz + "'");
         }
      } // while

      while (!(classes.isEmpty()))
      {
         cls = classes.removeFirst();

         // ----- get the methods from class

         Iterator<? extends FMethod> methodIter = cls.iteratorOfMethods();
         while (methodIter.hasNext())
         {
            tmpMeth = methodIter.next();

            if (tmpMeth.getVisibility() != FDeclaration.PRIVATE)
            {
               if (tmpMeth.getVisibility() != FDeclaration.PACKAGE)
               {
                  if (!(accMethods.containsKey(tmpMeth.getFullMethodName())))
                  {
                     accMethods.put(tmpMeth.getFullMethodName(), tmpMeth);
                  }

               }
               else
               {
                  if (cls.getDeclaredInPackage() == clazz
                        .getDeclaredInPackage())
                  {
                     if (!(accMethods.containsKey(tmpMeth.getFullMethodName())))
                     {
                        accMethods.put(tmpMeth.getFullMethodName(), tmpMeth);
                     }
                  }
               }
            }
         } // while

         // ----- add all superclasses to classes
         iter = cls.iteratorOfRevSubclass();
         while (iter.hasNext())
         {
            tmpGen = iter.next();
            tmpClass = tmpGen.getSuperclass();
            classes.addLast(tmpClass);
         } // while

      } // while

      return accMethods;
   } // getAllAccessibleMethods


   /**
    * Get the allAccessibleMethods attribute of the FClass object
    * 
    * @return The allAccessibleMethods value
    */
   public static Collection<? extends FMethod> getAllAccessibleMethods(
         FClass clazz)
   {
      return getAllAccessibleMethodObjects(clazz).values();
   }


   /**
    * Get all roles which are used in this class or in a superclass
    * 
    * @return A set whith all roles use by the class or its superclass
    */
   public static Set<? extends FRole> getAllUsedRoles(FClass clazz)
   {
      Set<FRole> theRoles = new FHashSet();

      LinkedList<FClass> classes = new LinkedList<FClass>();

      FClass cls;
      FGeneralization tmpGen;
      FClass tmpClass;

      // first add all the roles of this class
      if (clazz.sizeOfRoles() > 0)
      {
         for (Iterator<? extends FRole> iterRoles = clazz.iteratorOfRoles(); iterRoles
               .hasNext();)
         {
            theRoles.add(iterRoles.next());
         }
      } // if

      // ----- add superclasses to classes
      Iterator<? extends FGeneralization> iter = clazz.iteratorOfRevSubclass();
      while (iter.hasNext())
      {
         tmpGen = iter.next();
         tmpClass = tmpGen.getSuperclass();
         classes.addLast(tmpClass);
      } // while

      while (!(classes.isEmpty()))
      {
         cls = classes.removeFirst();

         // ----- get the roles from class
         theRoles.addAll(cls.getAllUsedRoles());
      } // while

      return theRoles;
   } // getAllUsedRoles


   /**
    * Get all classes wich are derived direct or indirect
    * 
    * @return A set whith all direct and indirect derived Subclasses
    */
   public static Set<? extends FClass> getAllDerivedClasses(FClass clazz)
   {
      LinkedList<FClass> classes = new LinkedList<FClass>();

      Set<FClass> derived = new FHashSet();

      classes.add(clazz);
      FClass cls;
      FGeneralization tmpGen;
      FClass tmpClass;
      Iterator<? extends FGeneralization> iter;
      while (!classes.isEmpty())
      {
         cls = classes.removeFirst();

         // ----- add all subclasses to derived
         iter = cls.iteratorOfRevSuperclass();
         while (iter.hasNext())
         {
            tmpGen = iter.next();
            tmpClass = tmpGen.getSubclass();
            classes.addLast(tmpClass);
            derived.add(tmpClass);
         } // while

      } // while

      return derived;
   } // getAllDerivedClasses


   /**
    * Check if an class has an public accessible method including derived methods with given (full)
    * name
    * 
    * @param key The (full) name of the method
    * @return True if the class or its superclass has an public method with the given (full) name
    */
   public static boolean hasPubMethWithKey(FClass clazz, String key)
   {
      LinkedList<FClass> classes = new LinkedList<FClass>();

      classes.add(clazz);
      FClass cls;
      FMethod tmpMeth = null;
      FGeneralization tmpGen;
      FClass tmpClass;
      Iterator<? extends FGeneralization> iter;
      boolean found = false;
      while ((!classes.isEmpty()) && (found == false))
      {
         cls = classes.removeFirst();

         // ----- look for the attr
         tmpMeth = cls.getFromMethods(key);
         if (tmpMeth != null)
         {
            found = true;
         } // if
         else
         {
            // ----- add all superclasses to classes
            iter = cls.iteratorOfRevSubclass();
            while (iter.hasNext())
            {
               tmpGen = iter.next();
               tmpClass = tmpGen.getSuperclass();
               classes.addLast(tmpClass);
            }
         } // else

      } // while

      if (found == false)
      {
         return false;
      }

      return (tmpMeth.getVisibility() == FDeclaration.PUBLIC);
   } // hasPubMethWithKey


   /**
    * Check if an class has an public accessible method including derived methods with given (full)
    * name and return it
    * 
    * @param key The (full) name of the method
    * @return The found method, or null if not found
    */
   public static FMethod getPubMethWithKey(FClass clazz, String key)
   {
      LinkedList<FClass> classes = new LinkedList<FClass>();

      classes.add(clazz);
      FClass cls;
      FMethod tmpMeth = null;
      FGeneralization tmpGen;
      FClass tmpClass;
      Iterator<? extends FGeneralization> iter;
      boolean found = false;
      while ((!classes.isEmpty()) && (found == false))
      {
         cls = classes.removeFirst();

         // ----- look for the attr
         tmpMeth = cls.getFromMethods(key);
         if (tmpMeth != null)
         {
            found = true;
         } // if
         else
         {
            // ----- add all superclasses to classes
            iter = cls.iteratorOfRevSubclass();
            while (iter.hasNext())
            {
               tmpGen = iter.next();
               tmpClass = tmpGen.getSuperclass();
               classes.addLast(tmpClass);
            }
         } // else

      } // while

      if ((found == false) || (tmpMeth.getVisibility() != FDeclaration.PUBLIC))
      {
         return null;
      }

      return tmpMeth;
   } // getPubMethWithKey


   /**
    * This method checks if all grabbed roles are references. Only then this class can be marked as
    * TYPE, which means that the reference is hidden and the corresponding attribute is shown.
    * 
    * @return The typeMarkable value
    */
   public static boolean isTypeMarkable(FClass clazz)
   {
      boolean result = true;
      Iterator<? extends FRole> iter = clazz.iteratorOfRoles();
      while (result && iter.hasNext())
      {
         FRole tmpRole = iter.next();
         result = (tmpRole.getAdornment() == FRole.REFERENCE);
      }
      return result;
   }


   /**
    * Returns all associations which can be accessed from this class. Inherited assocs will be also
    * covered.
    * 
    * @return The allAssocs value
    */
   public static Set<? extends FAssoc> getAllAssocs(FClass clazz)
   {
      Set<FAssoc> allAssocs = new FHashSet<FAssoc>();

      // Get all assoEnumeration enumRoles = elementsOfRoles ()
      // which are implemented in this class.
      Iterator<? extends FRole> iter = clazz.iteratorOfRoles();
      while (iter.hasNext())
      {
         FAssoc tmpAssoc = iter.next().getAssoc();
         if (tmpAssoc != null)
         {
            allAssocs.add(tmpAssoc);
         }
      }

      Iterator<? extends FGeneralization> iter2 = clazz.iteratorOfRevSubclass();
      while (iter2.hasNext())
      {
         FGeneralization generalization = iter2.next();
         FClass tmpClass = generalization.getSuperclass();

         if (tmpClass != null)
         {
            // Get all assocs which are implemented in this class.
            allAssocs.addAll(tmpClass.getAllAssocs());
         }
         else
         {
            throw new IllegalStateException("Generalization without superclass!" +
            		" The class " + clazz.getFullClassName() +
            		" has a generalizazion but no superclass.");
         }
      }

      return allAssocs;
   } // getAllAssocs


   /**
    * returns all assocs of this class an his parents
    * 
    * @return The allAssocsNew value
    */
   public static SortedSet<? extends FAssoc> getAllAssocsNew(FClass clazz)
   {
      SortedSet<FAssoc> assocSet = new TreeSet<FAssoc>();
      LinkedList<FClass> classes = new LinkedList<FClass>();

      classes.add(clazz);
      FClass cls;
      FRole tmpRole;
      FAssoc tmpAssoc;
      FGeneralization tmpGen;
      FClass tmpClass;
      Iterator<? extends FRole> iter;
      while (!classes.isEmpty())
      {
         cls = classes.removeFirst();

         // ----- add all assocs
         iter = cls.iteratorOfRoles();
         while (iter.hasNext())
         {
            tmpRole = iter.next();
            tmpAssoc = tmpRole.getAssoc();
            assocSet.add(tmpAssoc);
         }

         // ----- add all superclasses to classes
         Iterator<? extends FGeneralization> iter2 = cls
               .iteratorOfRevSubclass();
         while (iter2.hasNext())
         {
            tmpGen = iter2.next();
            tmpClass = tmpGen.getSuperclass();
            classes.addLast(tmpClass);
         }
      }
      return assocSet;
   } // getAllAssocs


   /**
    * Check if an class has an public accessible Attribut including derived Attributs with given
    * name
    * 
    * @param key The name of the attribut
    * @return True if the class or its superclass has an public attribut with the given name
    */
   public static boolean hasPubAttrWithKey(FClass clazz, String key)
   {
      LinkedList<FClass> classes = new LinkedList<FClass>();

      classes.add(clazz);
      FClass cls;
      FAttr tmpAttr = null;
      FGeneralization tmpGen;
      FClass tmpClass;
      Iterator<? extends FGeneralization> iter;
      boolean found = false;
      while ((!classes.isEmpty()) && (found == false))
      {
         cls = classes.removeFirst();

         // ----- look for the attr
         tmpAttr = cls.getFromAttrs(key);
         if (tmpAttr != null)
         {
            found = true;
         } // if
         else
         {
            // ----- add all superclasses to classes
            iter = cls.iteratorOfRevSubclass();
            while (iter.hasNext())
            {
               tmpGen = iter.next();
               tmpClass = tmpGen.getSuperclass();
               classes.addLast(tmpClass);
            }
         } // else

      } // while

      if (found == false)
      {
         return false;
      }

      return (tmpAttr.getVisibility() == FDeclaration.PUBLIC);
   } // hasPubAttrWithKey


   /**
    * Check if an class has an public accessible Attribut including derived Attributs with given
    * name and deliver it when found
    * 
    * @param key The name of the attribut
    * @return The attribut if found or null
    */
   public static FAttr getPubAttrWithKey(FClass clazz, String key)
   {
      LinkedList<FClass> classes = new LinkedList<FClass>();

      classes.add(clazz);
      FClass cls;
      FAttr tmpAttr = null;
      FGeneralization tmpGen;
      FClass tmpClass;
      Iterator<? extends FGeneralization> iter;
      boolean found = false;
      while ((!classes.isEmpty()) && (found == false))
      {
         cls = classes.removeFirst();

         // ----- look for the attr
         tmpAttr = cls.getFromAttrs(key);
         if (tmpAttr != null)
         {
            found = true;
         }
         else
         {
            // ----- add all superclasses to classes
            iter = cls.iteratorOfRevSubclass();
            while (iter.hasNext())
            {
               tmpGen = iter.next();
               tmpClass = tmpGen.getSuperclass();
               classes.addLast(tmpClass);
            }
         }
      }

      if (found == false)
      {
         return null;
      }

      if (tmpAttr.getVisibility() == FDeclaration.PUBLIC)
      {
         return tmpAttr;
      }

      return null;
   } // getPubAttrWithKey


   public static FAttr getFromAllAttrs(FClass clazz, String key)
   {
      FAttr theAttr = clazz.getFromAttrs(key);

      if (theAttr == null)
      {
         FGeneralization tmpGen = null;
         FClass tmpClass = null;

         // ----- add all superclasses to classes
         Iterator<? extends FGeneralization> iter = clazz
               .iteratorOfRevSubclass();
         while (theAttr == null && iter.hasNext())
         {
            tmpGen = iter.next();
            tmpClass = tmpGen.getSuperclass();
            theAttr = tmpClass.getFromAllAttrs(key);
         }
      }

      return theAttr;
   }


   public static FMethod getFromAllMethods(FClass clazz, String key)
   {
      FMethod method = clazz.getFromMethods(key);

      if (method == null)
      {
         Map<String, ? extends FMethod> methods = getAllAccessibleMethodObjects(clazz);
         method = methods.get(key);
      }

      return method;
   }


   public static FRole getFromAllPartnerRoles(FClass clazz, String key)
   {
      FRole theRole = getFromPartnerRoles(clazz, key);

      if (theRole == null)
      {
         FGeneralization tmpGen = null;
         FClass tmpClass = null;

         // ----- add all superclasses to classes
         Iterator<? extends FGeneralization> iter = clazz
               .iteratorOfRevSubclass();
         while (theRole == null && iter.hasNext())
         {
            tmpGen = iter.next();
            tmpClass = tmpGen.getSuperclass();
            theRole = tmpClass.getFromAllPartnerRoles(key);
         }
      }

      return theRole;
   }


   public static final SortedMap<String, ? extends FRole> getAllOutGoingRoles(
         FClass clazz)
   {
      SortedMap<String, FRole> outGoing = new TreeMap<String, FRole>();

      // ----- search the assoc, which 'leaves' this class
      Iterator<? extends FRole> iter = clazz.iteratorOfRoles();
      while (iter.hasNext())
      {
         FRole role = iter.next();
         FAssoc assoc = role.getAssoc();
         int direction = assoc.getDirection();
         FRole leftRole = assoc.getLeftRole();
         if (((direction == FAssoc.LEFTRIGHT) && (leftRole == role))
               || ((direction == FAssoc.RIGHTLEFT) && (leftRole != role)))
         {
            outGoing.put(assoc.getName(), role);
         }
      }
      return outGoing;
   }


   /**
    * Use this method to determine wether this instance of FClass is a subclass of the given FClass
    * clazz
    * 
    * @param superClass a possible superclass
    * @return true if this is a subclass of clazz, false otherwise. notice: isSubClassOf(this)
    *         returns true
    */
   public static boolean isSubClassOf(FClass subClass, FClass superClass)
   {
      if (subClass != null)
      {
         if (subClass == superClass)
         {
            return true;
         }
         else
         {
            // we might have multiple inheritance
            for (Iterator<? extends FGeneralization> it = subClass.iteratorOfRevSubclass(); it.hasNext();)
            {
               FGeneralization generalization = it.next();
               if (isSubClassOf(generalization.getSuperclass(), superClass))
               {
                  return true;
               }
            }
         }
      }
      return false;
   }


   /**
    * Determines if the class or interface represented by <code>thisClass</code> object is either
    * the same as, or is a superclass or superinterface of, the class or interface represented by
    * <code>otherClass</code>. It returns <code>true</code> if so; otherwise it returns
    * <code>false</code>.
    * 
    * <p>
    * Specifically, this method tests whether the type represented by the specified
    * <code>otherClass</code> parameter can be converted to the type represented by
    * <code>thisClass</code> via an identity conversion or via a widening reference conversion.
    * 
    * @param thisClass the <code>FClass</code> object to be checked against
    * @param otherClass the <code>FClass</code> object to be checked
    * @return the <code>boolean</code> value indicating whether objects of the type
    *         <code>otherClass</code> can be assigned to objects of <code>thisClass</code>
    * 
    * @see java.lang.Class#isAssignableFrom(java.lang.Class)
    */
   public static boolean isAssignableFrom(FClass thisClass, FClass otherClass)
   {
      if (otherClass != null)
      {
         if (thisClass == otherClass)
         {
            return true;
         }
         boolean result = false;
         FClass superClass = null;
         Iterator<? extends FGeneralization> generalizations = otherClass
               .iteratorOfRevSubclass();
         while (generalizations.hasNext() && !result)
         {
            FGeneralization gen = generalizations.next();
            superClass = gen.getSuperclass();
            if (superClass == thisClass)
            {
               return true;
            }
            else
            {
               result = isAssignableFrom(thisClass, superClass);
            }
         }
      }
      return false;
   }


   /**
    * Returns true, if <code>thisClass</code> is subclass of <code>otherClass</code>.
    * <p>
    * <p/> Otherwise the param <code>otherClass</code> isn't a subclass of <code>thisClass</code>.
    * <p>
    * </p>
    * FIXME: there can be a deadlock, if a loop in the generalization exists.
    * 
    * @param otherClass the FClass to be checked.
    * @return returns true if <code>thisClass</code> is a subclass of the param.
    */
   public static boolean isChildOf(FClass thisClass, FClass otherClass)
   {
      if (thisClass.equals(otherClass))
      {
         return true;
      }
      else
      {
         boolean result = false;

         Iterator<? extends FGeneralization> iter = thisClass
               .iteratorOfRevSubclass();
         while (!result && iter.hasNext())
         {
            FGeneralization general = iter.next();
            FClass superClass = general.getSuperclass();
            if (superClass != null)
            {
               result = superClass.isChildOf(otherClass);
            }
         }

         return result;
      }
   }


   /**
    * To get the generalization between this child class and a parent class.
    * 
    * @param parent No description provided
    * @return the generalization between this class and the parent class
    */
   public static FGeneralization getParentGeneralization(FClass clazz,
         FClass parent)
   {
      FGeneralization umlGen = null;
      Iterator<? extends FGeneralization> enumeration = clazz
            .iteratorOfRevSubclass();
      while (umlGen == null && enumeration.hasNext())
      {
         umlGen = enumeration.next();
         if (umlGen.getSuperclass() != parent)
         {
            umlGen = null;
         }
      }

      return umlGen;
   }


   /**
    * If an inner class with the given name exists, this methods returns the inner class, otherwise
    * null. The method works recursively.
    * 
    * @param name the name of the inner class
    * @return the inner class with the given name or null
    */
   public static FClass findClass(FClass clazz, String name)
   {
      FClass result = null;

      int indexOfFirstDot = name.indexOf('.');
      if (indexOfFirstDot != -1)
      {
         String outerClassName = name.substring(0, indexOfFirstDot);
         FClass outerClass = clazz.getFromDeclares(outerClassName);
         if (outerClass != null)
         {
            result = outerClass.findClass(name.substring(indexOfFirstDot + 1));
         }
      }
      else
      {
         result = clazz.getFromDeclares(name);
      }

      return result;
   }


   /**
    * @return package this class is enclosed in - even for inner and anonymous classes
    */
   public static FPackage findPackage(FClass clazz)
   {
      if (clazz.getDeclaredInPackage() != null)
      {
         return clazz.getDeclaredInPackage();
      }
      if (clazz.getDeclaredInClass() != null)
      {
         return clazz.getDeclaredInClass().findPackage();
      }
      if (clazz.getDeclaredInMethod() != null)
      {
         return clazz.getDeclaredInMethod().getParent().findPackage();
      }

      return null;
   }


   /**
    * This function computs the full qualified classname of this class e. g. if the class
    * <code>FooBar</code> is defined in the package <code>upb</code> and <code>upb</code> is
    * in <code>de</code> then this function will return <code>de.upb.FooBar</code>
    * 
    * @return the full qualified class name
    */
   public static String getFullClassName(FClass clazz)
   {
      StringBuffer buffer = new StringBuffer();

      FClass outerClass = clazz.getDeclaredInClass();
      FPackage pack = clazz.getDeclaredInPackage();

      if (outerClass != null)
      {
         buffer.append(outerClass.getFullClassName());
         buffer.append(".");
      }
      else if (pack == null)
      {
         Logger log = Logger.getLogger(FClassUtility.class);
         if (log.isEnabledFor(Level.WARN))
         {
            log.warn("class '" + clazz + "' in project '" + clazz.getProject()
                  + "' is not declared in a package");
         }
      }
      else if (pack != clazz.getProject().getRootPackage())
      {
         buffer.append(pack.getFullPackageName());
         buffer.append(".");
      }
      buffer.append(clazz.getName());

      return buffer.toString();
   }


   public static FRole getFromPartnerRoles(FClass clazz, String key)
   {
      Iterator<? extends FRole> iter = clazz.iteratorOfRoles();
      while (iter.hasNext())
      {
         FRole role = iter.next();
         FRole partnerRole = role.getPartnerRole();
         String name = partnerRole.getName();
         if ((name == null && key == null)
               || (name != null && name.equals(key)))
         {
            return partnerRole;
         }
      }
      return null;
   }


   /**
    * Try to find a role with the given name in the specified class.
    * 
    * @param clazz The class to look up.
    * @param name The role name to be looked up.
    * @return A role with the given name if it exists in the class, null otherwise.
    */
   public static FRole findRole(FClass clazz, String name)
   {
      if (clazz == null)
      {
         return null;
      }

      Iterator<? extends FRole> roleIter = clazz.iteratorOfAllRoles();
      while (roleIter.hasNext())
      {
         FRole role = roleIter.next();
         role = role.getPartnerRole();
         if (role != null && name.equals(role.getName()))
         {
            return role;
         }
      }
      return null;
   }

   public static void createGeneralization(FClass subclass, FClass superclass)
   {
      final FGeneralization generalization = subclass.getProject().getFromFactories(FGeneralization.class).create();
      generalization.setSubclass(subclass);
      generalization.setSuperclass(superclass);
      for (Iterator<? extends FDiagram> it = subclass.iteratorOfDiagrams(); it.hasNext();)
      {
         FDiagram diagram = it.next();
         if ( superclass.hasInDiagrams(diagram) )
         {
            diagram.addToElements(generalization);
         }
      }
   }
   
   public static boolean isInterface(FClass clazz)
   {
      return clazz.hasKeyInStereotypes(FStereotype.INTERFACE);
   }
	

	public static void provideStereotype ( FClass clazz, String stereotypeName, boolean create)
	{
	    FFactory<FStereotype> stereotypeFactory = clazz.getProject().getFromFactories (FStereotype.class);
	    FStereotype stereotype = stereotypeFactory.getFromProducts (stereotypeName);
	    if (stereotype == null && create)
	    {
	    	stereotype = stereotypeFactory.create();
	    	stereotype.setName(stereotypeName);
	    }
	    if (stereotype != null && !clazz.hasInStereotypes(stereotype))
	    {
			clazz.addToStereotypes (stereotype);	    	
	    }
	}
}

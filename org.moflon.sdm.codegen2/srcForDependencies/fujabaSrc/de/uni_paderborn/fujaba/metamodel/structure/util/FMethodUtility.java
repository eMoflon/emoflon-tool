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


import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FDeclaration;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FParam;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivityDiagram;
import de.uni_paderborn.fujaba.uml.behavior.UMLStartActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStatement;
import de.uni_paderborn.fujaba.uml.behavior.UMLStatementActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLStopActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransition;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransitionGuard;
import de.uni_paderborn.fujaba.versioning.Versioning;


/**
 * Utility class for dealing with instances of FMethod.
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class FMethodUtility
{

   private static String getMethodDeclWithoutResultType(FMethod method)
   {
      int count = 0;

      String result = method.getName() + " (";
      Iterator<? extends FParam> iter = method.iteratorOfParam();
      FParam currentParam = null;

      while (iter.hasNext())
      {
         count++;
         if (count > 1)
         {
            result += ", ";
         }
         currentParam = iter.next();
         if ((currentParam != null) && (currentParam.getParamType() != null))
         {
            result += currentParam.getName() + ": "
                  + currentParam.getParamType().getName();
         }
      }

      result += ")";

      return result;
   }


   public static String getMethodDecl(FMethod method)
   {
      String result = getMethodDeclWithoutResultType(method);
      if (method.getResultType() != null)
      {
         result += ": " + method.getResultType().getName();
      }

      return result;
   }


   public static String getQualifiedMethodDecl(FMethod method)
   {
      return method.getParent() + "::" + getMethodDecl(method);
   }


   public static String getJava(FMethod method)
   {
      String result = FDeclaration.VISIBILITY_STRING[method.getVisibility()]
            + " ";
      if (method.getResultType() != null)
      {
         result += method.getResultType().getName() + " ";
      }
      result += method.getName() + " (";
      FParam parameter = null;
      Iterator<? extends FParam> iter = method.iteratorOfParam();

      while (iter.hasNext())
      {
         parameter = iter.next();
         result += parameter.getParamType().getName() + " "
               + parameter.getName();
         if (iter.hasNext())
         {
            result += ", ";
         }
      }
      result += ");";
      return result;
   }


   public static String getText(FMethod method)
   {
      String result = "";
      if (method.getResultType() != null
            && FBaseType.INITIALIZER.equals(method.getResultType().getName()))
      {
         result = FDeclaration.VISIBILITY_CHAR[method.getVisibility()]
               + " Initializer";
      }
      else if (method.getResultType() != null
            && FBaseType.CONSTRUCTOR.equals(method.getResultType().getName()))
      {
         result = FDeclaration.VISIBILITY_CHAR[method.getVisibility()] + " "
               + getMethodDeclWithoutResultType(method);
      }
      else
      {
         result = FDeclaration.VISIBILITY_CHAR[method.getVisibility()] + " "
               + getMethodDecl(method);
      }
      return result;
   }


   public static UMLActivityDiagram createStoryDiagram(FMethod method)
   {
      // create diagram
      FProject project = method.getProject();
      boolean persistent = method.isPersistent();
      UMLActivityDiagram diagram = project.getFromFactories(
            UMLActivityDiagram.class).create(persistent);
      diagram.setStoryMethod(method);
      diagram.setName(getFullMethodName(method));

      // create start activity
      UMLStartActivity start = project.getFromFactories(UMLStartActivity.class)
            .create(persistent);
      start.setSpec(method);
      diagram.addToElements(start);

      // add new diagram to project
      project.addToModelRootNodes(diagram);

      return diagram;
   }


   /**
    * Find all methods in superclasses and -interfaces that have the same signature as method
    * method.
    * 
    * @return iterator through overwritten/implemented methods
    */
   public static Iterator<? extends FMethod> iteratorOfOverriddenMethods(
         FMethod method)
   {
      FClass cls = method.getParent();
      if (cls != null)
      {
         String signature = getFullMethodName(method);
         Set<? extends FMethod> methods = cls
               .findMethodsWithSignatureInSuperclasses(signature);
         return methods.iterator();
      }
      else
      {
         // NOTE: we must store the iterator temporary, because
         // javac 1.5.06 won't compile 'return EmptyIterator.get()' (FK 17.05.2006)
         EmptyIterator<FMethod> emptyIterator = EmptyIterator.get();
         return emptyIterator;
      }
   }


   /**
    * Find all methods in subclasses and -interfaces that have the same signature as method method.
    * 
    * @return iterator through overwriting/implementing methods
    */
   public static Iterator<? extends FMethod> iteratorOfOverridingMethods(
         FMethod method)
   {
      FClass cls = method.getParent();
      if (cls != null)
      {
         String signature = getFullMethodName(method);
         Set<? extends FMethod> methods = cls
               .findMethodsWithSignatureInSubclasses(signature);
         return methods.iterator();
      }
      else
      {
         return EmptyIterator.<FMethod>get();
      }
   }


   /**
    * Creates an activity diagram with one statement activity containing the given method body. The
    * created diagram is linked to the method, an already existing diagram is removed.
    * 
    * @param method the method for which to create the activity diagram
    * @param methodBody the method body as string
    */
   public static void createActivityDiagram(FMethod method, String methodBody)
   {
      createActivityDiagram(method, methodBody, method.isPersistent());
   }


   /**
    * Creates an activity diagram with one statement activity containing the given method body. The
    * created diagram is linked to the method, an already existing diagram is removed.
    * 
    * @param method the method for which to create the activity diagram
    * @param methodBody the method body as string
    * @param persistent should the created activity diagram be persistent?
    */
   public static void createActivityDiagram(FMethod method, String methodBody,
         boolean persistent)
   {
      String parentName = null;
      if (method.getParent() != null)
      {
         parentName = method.getParent().getName();
      }
      else
      {
         parentName = "no name";
      }

      UMLActivityDiagram activityDiagram = (UMLActivityDiagram)method.getStoryDiag();
      if (activityDiagram != null)
      {
         activityDiagram.removeYou();
      }

      FProject project = method.getProject();

      // create a new activity diagram
      activityDiagram = project.getFromFactories(UMLActivityDiagram.class)
            .create(persistent);
      activityDiagram.setName(parentName + "::" + method.getName());
      activityDiagram.setStoryMethod(method);
      method.getProject().addToModelRootNodes(activityDiagram);

      UMLStartActivity start = project.getFromFactories(UMLStartActivity.class)
            .create(persistent);
      activityDiagram.addToElements(start);
      start.setSpec(method);

      UMLStatement statement = project.getFromFactories(UMLStatement.class)
            .create(persistent);
      statement.setStatement(methodBody);

      UMLStatementActivity statementActivity = project.getFromFactories(
            UMLStatementActivity.class).create(persistent);
      statementActivity.setState(statement);
      activityDiagram.addToElements(statementActivity);

      UMLTransitionGuard newGuard = project.getFromFactories(
            UMLTransitionGuard.class).create(persistent);
      newGuard.setType(UMLTransitionGuard.NONE);

      UMLTransition newTransition = project.getFromFactories(
            UMLTransition.class).create(persistent);
      newTransition.setGuard(newGuard);
      newTransition.setSource(start);
      newTransition.setTarget(statementActivity);
      activityDiagram.addToElements(newTransition);

      UMLStopActivity stop = project.getFromFactories(UMLStopActivity.class)
            .create(persistent);
      stop.setGenerateCode(false);
      activityDiagram.addToElements(stop);

      newGuard = project.getFromFactories(UMLTransitionGuard.class).create(
            persistent);
      newGuard.setType(UMLTransitionGuard.NONE);

      newTransition = project.getFromFactories(UMLTransition.class).create(
            persistent);
      newTransition.setGuard(newGuard);
      newTransition.setSource(statementActivity);
      newTransition.setTarget(stop);
      activityDiagram.addToElements(newTransition);
      
      method.setStoryDiag(activityDiagram);
   }


   /**
    * Returns the signature of the method created from its name and its parameters.
    * 
    * @param method method for which the signature should be returned
    * @return methods signature
    */
   public static String getFullMethodName(FMethod method)
   {
      String name = method.getName();
      Iterator<? extends FParam> iteratorOfParam = method.iteratorOfParam();
      return constructFullMethodName(name, iteratorOfParam);
   }


   /**
    * Returns the signature of the method created from its name and its parameters.
    * 
    * 
    * @see #getFullMethodName(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    * @param name name of the method
    * @param iteratorOfParam iterator through parameters
    * @return methods signature
    */
   public static String constructFullMethodName(String name,
         Iterator<? extends FParam> iteratorOfParam)
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(name);
      buffer.append("(");

      boolean setComma = false;
      FType type;
      while (iteratorOfParam.hasNext())
      {
         FParam param = iteratorOfParam.next();
         type = param.getParamType();
         if (type != null && type.getName() != null)
         {
            if (setComma)
            {
               buffer.append(',');
            }
            setComma = true;
            buffer.append(type.getName());
         }
         else if (!Versioning.get().isInOperationalization(param))
         {
            Logger log = Logger.getLogger(FMethodUtility.class);
            if (log.isEnabledFor(Level.WARN))
            {
               log.warn("null type in method parameter (" + name + ")");
            }
         }
         else
         {
            if (setComma)
            {
               buffer.append(',');
            }
            setComma = true;
            buffer.append("<noType>");
         }
      }
      buffer.append(")");

      return buffer.toString();
   }


   /**
    * Returns true, if the method is a constructor. The result type may be null or
    * FBaseType.CONSTRUCTOR, which is an F-Basetype and respresents null. Furthermore the method
    * name is the same as the class name.
    * 
    * @return True, if the method is a constructor
    */
   public static boolean isConstructor(FMethod method)
   {
      return ((method.getResultType() == null) || (method.getResultType()
            .getName().equals(FBaseType.CONSTRUCTOR)))
            && (method.getParent() != null)
            && (method.getName().equals(method.getParent().getName()));
   }

}

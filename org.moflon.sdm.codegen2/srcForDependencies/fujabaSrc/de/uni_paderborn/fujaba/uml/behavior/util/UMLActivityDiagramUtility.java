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
package de.uni_paderborn.fujaba.uml.behavior.util;


import java.util.Iterator;

import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryActivity;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransition;
import de.uni_paderborn.fujaba.uml.behavior.UMLTransitionGuard;


/**
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */
public class UMLActivityDiagramUtility
{
   public static final String DEFAULT_CONDITION_TEXT = "/*TODO:condition*/";

   public static final String DEFAULT_EXCEPTION_TEXT = "/*TODO:exception*/";


   public static int calculateDefaultGuardType(UMLActivity from, UMLActivity to)
   {
      if (from instanceof UMLStoryActivity)
      {
         UMLStoryActivity storyAct = (UMLStoryActivity) from;
         if (storyAct.isForEach())
         {
            // look for existing each_time and end
            boolean hasEachTime = false;
            boolean hasEnd = false;

            for (Iterator<UMLTransition> i = storyAct.iteratorOfExit(); i
                  .hasNext()
                  && !(hasEachTime && hasEnd);)
            {
               UMLTransition trans = i.next();
               int guard = UMLTransitionGuard.getGuardType(trans);
               if (guard == UMLTransitionGuard.EVERYTIMES)
               {
                  hasEachTime = true;
               }
               else if (guard == UMLTransitionGuard.TERMINATION)
               {
                  hasEnd = true;
               }
            }
            if (hasEnd)
            {
               if (hasEachTime)
               {
                  return UMLTransitionGuard.EXCEPTION;
               }
               return UMLTransitionGuard.EVERYTIMES;
            }
            return UMLTransitionGuard.TERMINATION;
         }
      }
      boolean hasCondition = false;
      boolean hasElse = false;
      boolean hasSuccess = false;
      boolean hasFailure = false;
      for (Iterator<UMLTransition> i = from.iteratorOfExit(); i.hasNext()
            && !(hasCondition && hasElse && hasSuccess && hasFailure);)
      {
         UMLTransition trans = i.next();
         int guard = UMLTransitionGuard.getGuardType(trans);
         switch (guard)
         {
            case UMLTransitionGuard.BOOL:
               hasCondition = true;
               break;
            case UMLTransitionGuard.ELSE:
               hasElse = true;
               break;
            case UMLTransitionGuard.SUCCESS:
               hasSuccess = true;
               break;
            case UMLTransitionGuard.FAILURE:
               hasFailure = true;
               break;
         }
      }
      if (hasSuccess && !hasFailure)
      {
         return UMLTransitionGuard.FAILURE;
      }
      if (hasFailure && !hasSuccess)
      {
         return UMLTransitionGuard.SUCCESS;
      }
      if (hasCondition && !hasElse)
      {
         return UMLTransitionGuard.ELSE;
      }
      if (hasElse && !hasCondition)
      {
         return UMLTransitionGuard.BOOL;
      }
      if ((hasSuccess && hasFailure) || (hasCondition && hasElse))
      {
         return UMLTransitionGuard.EXCEPTION;
      }
      return UMLTransitionGuard.NONE;
   }


   public static UMLTransitionGuard createDefaultGuard(FProject project,
         int type)
   {
      UMLTransitionGuard guard = project.getFromFactories(
            UMLTransitionGuard.class).create(true);
      guard.setType(type);
      if (type == UMLTransitionGuard.BOOL)
      {
         guard.setBoolExpr(DEFAULT_CONDITION_TEXT);
      }
      else if (type == UMLTransitionGuard.EXCEPTION)
      {
         guard.setBoolExpr(DEFAULT_EXCEPTION_TEXT);
      }
      return guard;
   }


   public static UMLTransition createDefaultTransition(UMLActivity from,
         UMLActivity to)
   {
      UMLTransition transition = from.getProject().getFromFactories(
            UMLTransition.class).create(true);
      transition.setRevExit(from);
      transition.setRevEntry(to);

      int type = calculateDefaultGuardType(from, to);
      if (type != UMLTransitionGuard.NONE)
      {
         UMLTransitionGuard guard = createDefaultGuard(from.getProject(), type);
         transition.setGuard(guard);
      }
      return transition;
   }


   public static String provideUniqueObjectName(FDiagram diagram, String prefix)
   {
      int counter = 1;
      String name;
      loop: while (counter < 1000)
      {
         name = prefix + counter++;
         for (Iterator iter = diagram.iteratorOfElements(); iter.hasNext();)
         {
            FElement elem = (FElement) iter.next();
            if (elem instanceof UMLObject)
            {
               if (name.equals(((UMLObject) elem).getName()))
               {
                  continue loop;
               }
            }
         }
         return name;
      }
      throw new IllegalStateException();
   }


   public static String addQuotesIfMissing(String expression)
   {
      if (expression != null)
      {
         if (expression.startsWith("\"") && expression.endsWith("\""))
         {
            return expression;
         }
         else
         {
            return "\"" + expression + "\"";
         }
      }
      else
      {
         throw new IllegalArgumentException(
               "Argument of method UMLActivityDiagramUtility.addQuotesIfMissing(String) may not be null.");
      }

   }
}

/*
 * $Log$
 */

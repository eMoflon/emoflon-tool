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
package de.uni_paderborn.fujaba.uml.behavior;

import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.metamodel.common.*;
import de.uni_paderborn.fujaba.uml.structure.UMLRole;

import java.util.HashMap;


/**
 * This class represents path expressions
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLPath extends UMLLink
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int P_PATH = 70;


   /**
    * Constructor for class UMLPath
    *
    * @param project
    * @param persistent
    */
   protected UMLPath (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * Get the reference attribute of the UMLPath object
    *
    * @return   The reference value
    */
   @Override
   public boolean isReference()
   {
      return true;
   }


   /**
    * Get the navigable attribute of the UMLPath object
    *
    * @param source  No description provided
    * @return        The navigable value
    */
   @Override
   public boolean isNavigable (UMLObject source)
   {
      return getSource() == source;
   }


   /**
    * The path expression.
    */
   private String expression;


   /**
    * Sets the expression attribute of the UMLPath object
    *
    * @param newExpression  The new expression value
    */
   public void setExpression (String newExpression)
   {
      if (this.expression != newExpression)
      {
         String oldExpression = this.expression;
         this.expression = newExpression;
         firePropertyChange ("expression", oldExpression, newExpression);
      }
   }


   /**
    * Get the expression attribute of the UMLPath object
    *
    * @return   The expression value
    */
   public String getExpression()
   {
      return this.expression;
   }


   /**
    * Get the correspondingRole attribute of the UMLPath object
    *
    * @param object  No description provided
    * @return        The correspondingRole value
    */
   @Override
   public UMLRole getCorrespondingRole (UMLObject object)
   {
      throw new UnsupportedOperationException ("UMLPath.getCorrespondingRole (UMLObject) is not supported!");
   }


   /**
    * Get the sourceRole attribute of the UMLPath object
    *
    * @return   The sourceRole value
    */
   @Override
   public UMLRole getSourceRole()
   {
      return null;
   }


   /**
    * Get the targetRole attribute of the UMLPath object
    *
    * @return   The targetRole value
    */
   @Override
   public UMLRole getTargetRole()
   {
      return null;
   }


   /**
    * Get the priority attribute of the UMLPath object
    *
    * @param boundObjects        No description provided
    * @param isomorphicBindings  No description provided
    * @return                    The priority value
    */
   @Override
   public int getPriority (HashMap boundObjects, HashMap isomorphicBindings)
   {
      UMLObject sourceObject = getSource();

      if (!boundObjects.containsKey (sourceObject.getID()))
      {
         throw new RuntimeExceptionWithContext ("The source object is not bound", this);
      }

      return P_PATH;
   }


   /**
    * String representation of UMLPath
    *
    * @return   short string representation of current path
    */
   @Override
   public String toString()
   {
      return "UMLPath[name=" + getExpression() + "]";
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Override
   public void removeYou()
   {
      super.removeYou();
   }


   @Override
   public FCodeStyle getInheritedCodeStyle()
   {
      final FCodeStyle codeStyle = getCodeStyle();
      if (codeStyle!=null)
      {
         return codeStyle;
      }
      final FElement instanceOf = getSource();
      if (instanceOf != null)
      {
         return instanceOf.getInheritedCodeStyle();
      }
      return null;
   }
}

/*
 * $Log$
 * Revision 1.4  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.3  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.2  2006/03/01 12:22:48  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

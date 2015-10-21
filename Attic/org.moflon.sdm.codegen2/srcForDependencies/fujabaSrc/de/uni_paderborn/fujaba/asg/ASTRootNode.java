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
package de.uni_paderborn.fujaba.asg;

import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;


/**
 * This class is the Abstract Syntax Tree root node for AST's of method bodies.
 * Every AST has one unique root node.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public abstract class ASTRootNode extends ASTNode
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private FMethod method;


   /**
    *Constructor for class ASTRootNode
    *
    * @param project     No description provided
    * @param persistent  No description provided
    */
   public ASTRootNode (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * Get the method attribute of the ASTRootNode object
    *
    * @return   The method value
    */
   public FMethod getMethod()
   {
      return this.method;
   }


   /**
    * Sets the method attribute of the ASTRootNode object
    *
    * @param value  The new method value
    */
   public void setMethod (FMethod value)
   {
      if (this.method != value)
      {
         FMethod oldMethod = this.method;
         if (this.method != null)
         {
            this.method = null;
            oldMethod.setASTRootNode (null);
         }
         this.method = value;
         if (value != null)
         {
            value.setASTRootNode (this);
         }
      }
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    *
    * @return   the logical parent of this element;
    */
   @Override
   public FElement getParentElement()
   {
      return getMethod();
   }
}

/*
 * $Log$
 * Revision 1.4  2006/04/06 12:04:33  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.3  2006/03/01 12:22:17  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

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
package de.uni_paderborn.fujaba.uml.structure;

import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FGeneralization;
import de.uni_paderborn.fujaba.uml.common.UMLConnection;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLGeneralization extends UMLConnection implements FGeneralization
{

   /**
    * Constructor for class UMLGeneralization
    *
    * @param project
    * @param persistent
    */
   protected UMLGeneralization (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String name;


   /**
    * Get the name attribute of the UMLGeneralization object
    *
    * @return   The name value
    */
   @Override
   public String getName()
   {
      return name;
   }


   /**
    * Sets the name attribute of the UMLGeneralization object
    *
    * @param name  The new name value
    */
   @Override
   public void setName (String name)
   {
      String oldValue = this.name;
      this.name = name;
      firePropertyChange (NAME_PROPERTY, oldValue, name);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLClass subclass;


   /**
    * Get the subclass attribute of the UMLGeneralization object
    *
    * @return   The subclass value
    */
   public UMLClass getSubclass()
   {
      return this.subclass;
   }


   /**
    * Sets the subclass attribute of the UMLGeneralization object
    *
    * @param subclass  The new subclass value
    * @return          No description provided
    */
   public boolean setSubclass (FClass subclass)
   {
      if (this.subclass != subclass)
      { // new partner

         UMLClass oldSubclass = this.subclass;
         if (this.subclass != null)
         { // inform old partner

            this.subclass = null;
            oldSubclass.removeFromRevSubclass (this);
         }
         this.subclass = (UMLClass) subclass;
         if (subclass != null)
         { // inform new partner

            subclass.addToRevSubclass (this);
         }
         firePropertyChange (SUBCLASS_PROPERTY, oldSubclass, subclass);
         return true;
      }
      return false;
   }


   /**
    * Sets the targetConnector attribute of the UMLGeneralization object
    *
    * @param incr  The new targetConnector value
    * @return      No description provided
    */
   @Override
   public boolean setTargetConnector (UMLDiagramItem incr)
   {
      if (! (incr instanceof UMLClass))
      {
         throw new IllegalArgumentException ("Argument must be instance of UMLClass");
      }
      return setSubclass ((UMLClass) incr);
   }


   /**
    * Get the targetConnector attribute of the UMLGeneralization object
    *
    * @return   The targetConnector value
    */
   @Override
   public UMLDiagramItem getTargetConnector()
   {
      return getSubclass();
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLClass superclass;


   /**
    * Get the superclass attribute of the UMLGeneralization object
    *
    * @return   The superclass value
    */
   public UMLClass getSuperclass()
   {
      return this.superclass;
   }


   /**
    * Sets the superclass attribute of the UMLGeneralization object
    *
    * @param superclass  The new superclass value
    * @return            No description provided
    */
   public boolean setSuperclass (FClass superclass)
   {
      if (this.superclass != superclass)
      { // new partner

         UMLClass oldSuperclass = this.superclass;
         if (this.superclass != null)
         { // inform old partner

            this.superclass = null;
            oldSuperclass.removeFromRevSuperclass (this);
         }
         this.superclass = (UMLClass) superclass;
         if (superclass != null)
         { // inform new partner

            superclass.addToRevSuperclass (this);
         }
         firePropertyChange (SUPERCLASS_PROPERTY, oldSuperclass, superclass);
         return true;
      }
      return false;
   }


   /**
    * Sets the sourceConnector attribute of the UMLGeneralization object
    *
    * @param incr  The new sourceConnector value
    * @return      No description provided
    */
   @Override
   public boolean setSourceConnector (UMLDiagramItem incr)
   {
      if (! (incr instanceof UMLClass))
      {
         throw new IllegalArgumentException ("Argument must be instance of UMLClass");
      }
      return setSuperclass ((UMLClass) incr);
   }

   @Override
   public UMLDiagramItem getSourceConnector()
   {
      return getSuperclass();
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    *
    * @return   the logical parent of this element;
    */
   @Override
   public FElement getParentElement()
   {
      return getSubclass();
   }


   /**
    * @return   short string representation of current object
    */
   @Override
   public String toString()
   {
      return "UMLGeneralization[superclass=" + superclass + ",subclass=" + subclass + "]";
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      this.setSubclass (null);
      this.setSuperclass (null);

      super.removeYou();
   }

}

/*
 * $Log$
 * Revision 1.7  2006/04/06 12:06:11  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.6  2006/03/29 09:51:08  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.5  2006/03/01 12:23:01  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

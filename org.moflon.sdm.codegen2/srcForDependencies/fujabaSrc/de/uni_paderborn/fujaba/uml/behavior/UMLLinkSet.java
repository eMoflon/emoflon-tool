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

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FProject;

import java.util.HashMap;
import java.util.TreeMap;



/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 * @deprecated what the heck is this? should be removed in 5.1
 */
public class UMLLinkSet extends UMLLink
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int FIRST = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int LAST = 1;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int NEIGHBOUR = 2;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int NEXT = 3;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int ORDERED = 0;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public static final int SORTED = 1;
   public static final String TO_SET_OBJECT_PROPERTY = "toSetObject";


   /**
    * Constructor for class UMLLinkSet
    *
    * @param project
    * @param persistent
    */
   protected UMLLinkSet (FProject project, boolean persistent)
   {
      super (project, persistent);
      throw new UnsupportedOperationException("Hey! You are using this class? Please tell us what it does :)");
   }


   /**
    * Sets the type attribute of the UMLLinkSet object
    *
    * @param type  The new type value
    */
   @Override
   public void setType (int type)
   {
      if (FIRST <= type && type <= NEXT)
      {
         super.setType (type);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int setType = ORDERED;


   /**
    * Get the setType attribute of the UMLLinkSet object
    *
    * @return   The setType value
    */
   public int getSetType()
   {
      return setType;
   }


   /**
    * Sets the setType attribute of the UMLLinkSet object
    *
    * @param setType  The new setType value
    */
   public void setSetType (int setType)
   {
      int oldValue = this.setType;
      this.setType = setType;
      firePropertyChange ("setType", oldValue, setType);
   }


   @Property( name = TO_SET_OBJECT_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         partner = UMLObject.REV_TO_SET_OBJECT_PROPERTY, adornment = ReferenceHandler.Adornment.USAGE,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private UMLObject toSetObject; // reverse OrderedSet /*UMLLinkSet*/ revToSetObject


   /**
    * Get the toSetObject attribute of the UMLLinkSet object
    *
    * @return   The toSetObject value
    */
   public UMLObject getToSetObject()
   {
      return toSetObject;
   }


   /**
    * Sets the toSetObject attribute of the UMLLinkSet object
    *
    * @param toSetObject  The new toSetObject value
    */
   public void setToSetObject (UMLObject toSetObject)
   {
      if (this.toSetObject != toSetObject)
      { // new partner

         UMLObject oldToSetObject = this.toSetObject;
         if (this.toSetObject != null)
         { // inform old partner

            this.toSetObject = null;
            oldToSetObject.removeFromRevToSetObject (this);
         }
         this.toSetObject = toSetObject;
         if (toSetObject != null)
         { // inform new partner

            toSetObject.addToRevToSetObject (this);
         }
         firePropertyChange (TO_SET_OBJECT_PROPERTY, oldToSetObject, toSetObject);
      }
   }


   /**
    * Get the successorLinkSet attribute of the UMLLinkSet object
    *
    * @param objectsMap  No description provided
    * @return            The successorLinkSet value
    */
   public UMLLinkSet getSuccessorLinkSet (HashMap objectsMap)
   {
      UMLLinkSet succLinkSet = null;
      UMLObject source = (UMLObject) objectsMap.get (getSource());
      UMLObject target = (UMLObject) objectsMap.get (getTarget());
      UMLObject toSet = (UMLObject) objectsMap.get (getToSetObject());

      if (source != null && target != null && getModifier() != DELETE)
      {
         succLinkSet = getProject().getFromFactories (UMLLinkSet.class).create (isPersistent());
         succLinkSet.setName (getName());
         succLinkSet.setType (getType());
         succLinkSet.setModifier (NONE);
         succLinkSet.setRange (getRange());
         succLinkSet.setSource (source);
         succLinkSet.setTarget (target);
         succLinkSet.setInstanceOf (getInstanceOf());
         succLinkSet.setSetType (getSetType());
         succLinkSet.setToSetObject (toSet);
      }

      return succLinkSet;
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      setToSetObject (null);

      super.removeYou();
   }

}

/*
 * $Log$
 * Revision 1.6  2007/02/16 10:27:23  cschneid
 * tests fixed, several wrong/suprtfluous transient markers removed
 *
 * Revision 1.5  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.4  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.3  2006/03/01 12:22:48  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

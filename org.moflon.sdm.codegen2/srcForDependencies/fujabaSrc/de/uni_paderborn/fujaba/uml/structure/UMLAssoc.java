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


import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.common.UMLConnection;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashSet;
import de.upb.tools.pcs.CollectionChangeEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLAssoc extends UMLConnection implements FAssoc
{
   protected UMLAssoc(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   private String name = null;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getName()
    */
   @Override
   public String getName()
   {
      return name;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#setName(java.lang.String)
    */
   @Override
   public void setName(String name)
   {
      if ((this.name == null && name != null)
            || (this.name != null && !this.name.equals(name)))
      {
         String oldValue = this.name;
         this.name = name;
         firePropertyChange(NAME_PROPERTY, oldValue, name);
      }
   }


   private int direction = LEFTRIGHT;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAssoc#getDirection()
    */
   public int getDirection()
   {
      return direction;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAssoc#setDirection(int)
    */
   public void setDirection(int direction)
   {
      if (this.direction != direction)
      {
         int oldValue = this.direction;
         this.direction = direction;
         firePropertyChange(DIRECTION_PROPERTY, oldValue, direction);
      }
   }


   /**
    * Get the sortedComparator attribute of the UMLAssoc object. Looks at the roles in the order
    * given by {@link #getDirection()} to retrieve the comparator from them.
    * 
    * @return The sortedComparator value
    * @deprecated (gets deleted in 5.1) Use {@link de.uni_paderborn.fujaba.uml.structure.UMLRole#getSortedComparator}
    *             instead
    */
   public String getSortedComparator()
   {
      UMLRole firstRole;
      UMLRole secondRole;

      if (this.direction == LEFTRIGHT)
      {
         firstRole = this.rightRole;
         secondRole = this.leftRole;
      }
      else
      {
         firstRole = this.leftRole;
         secondRole = this.rightRole;
      }

      if ( firstRole != null && secondRole != null )
      {
         if (firstRole.getSortedComparator() != null
               && secondRole.getAdornment() != FRole.REFERENCE)
         {
            return firstRole.getSortedComparator();
         }
         if (secondRole.getSortedComparator() != null
               && firstRole.getAdornment() != FRole.REFERENCE)
         {
            return secondRole.getSortedComparator();
         }
      }
      return null;
   }


   /**
    * Sets the sortedComparator attribute of the UMLAssoc object
    * 
    * @param comparator The new sortedComparator value
    * @deprecated (gets deleted in 5.1) Use {@link de.uni_paderborn.fujaba.uml.structure.UMLRole#setSortedComparator}
    *             instead
    */
   public void setSortedComparator(String comparator)
   {
      UMLRole firstRole;
      UMLRole secondRole;

      if (this.direction == LEFTRIGHT)
      {
         firstRole = this.rightRole;
         secondRole = this.leftRole;
      }
      else
      {
         firstRole = this.leftRole;
         secondRole = this.rightRole;
      }

      if (secondRole.getAdornment() != FRole.REFERENCE)
      {
         firstRole.setSortedComparator(comparator);
         if (secondRole.getSortedComparator() != null)
         {
            secondRole.setSortedComparator(null);
         }
      }
      if (firstRole.getAdornment() != FRole.REFERENCE)
      {
         secondRole.setSortedComparator(comparator);
         if (firstRole.getSortedComparator() != null)
         {
            firstRole.setSortedComparator(null);
         }
      }
   }


   /**
    * Reverse UMLAssoc revLeftRole
    */
   private UMLRole leftRole;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAssoc#getLeftRole()
    */
   public UMLRole getLeftRole()
   {
      return leftRole;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAssoc#setLeftRole(de.uni_paderborn.fujaba.metamodel.structure.FRole)
    */
   public boolean setLeftRole(FRole leftRole)
   {
      if ((this.leftRole == null && leftRole != null)
            || (this.leftRole != null && !this.leftRole.equals(leftRole)))
      { // new partner

         UMLRole oldLeftRole = this.leftRole;
         if (this.leftRole != null)
         { // inform old partner

            this.leftRole = null;
            oldLeftRole.setRevLeftRole(null);
         }
         this.leftRole = (UMLRole) leftRole;
         if (leftRole != null)
         { // inform new partner

            this.leftRole.setRevLeftRole(this);
         }
         firePropertyChange(LEFT_ROLE_PROPERTY, oldLeftRole, leftRole);
         return true;
      }
      return false;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.common.UMLConnection#setSourceConnector(de.uni_paderborn.fujaba.uml.common.UMLDiagramItem)
    */
   @Override
   public boolean setSourceConnector(UMLDiagramItem incr)
   {
      throw new UnsupportedOperationException(
            "Cannot set the connector for an UMLAssoc, "
                  + "because it needs an UMLRole in between!");
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.common.UMLConnection#getSourceConnector()
    */
   @Override
   public UMLDiagramItem getSourceConnector()
   {
      UMLRole role;
      if (getDirection() == RIGHTLEFT)
      {
         role = getRightRole();
      }
      else
      {
         role = getLeftRole();
      }
      return (role == null ? null : role.getTarget());
   }


   /**
    * Reverse UMLAssoc revRightRole
    */
   private UMLRole rightRole;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAssoc#getRightRole()
    */
   public UMLRole getRightRole()
   {
      return rightRole;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAssoc#setRightRole(de.uni_paderborn.fujaba.metamodel.structure.FRole)
    */
   public boolean setRightRole(FRole rightRole)
   {
      if ((this.rightRole == null && rightRole != null)
            || (this.rightRole != null && !this.rightRole.equals(rightRole)))
      { // new partner

         UMLRole oldRightRole = this.rightRole;
         if (this.rightRole != null)
         { // inform old partner

            this.rightRole = null;
            oldRightRole.setRevRightRole(null);
         }
         this.rightRole = (UMLRole) rightRole;
         if (rightRole != null)
         { // inform new partner

            this.rightRole.setRevRightRole(this);
         }
         firePropertyChange(RIGHT_ROLE_PROPERTY, oldRightRole, rightRole);
         return true;
      }
      return false;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.common.UMLConnection#setTargetConnector(de.uni_paderborn.fujaba.uml.common.UMLDiagramItem)
    */
   @Override
   public boolean setTargetConnector(UMLDiagramItem incr)
   {
      throw new UnsupportedOperationException(
            "Cannot set the connector for an UMLAssoc, "
                  + "because it needs an UMLRole in between!");
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.common.UMLConnection#getTargetConnector()
    */
   @Override
   public UMLDiagramItem getTargetConnector()
   {
      UMLRole role;
      if (getDirection() == RIGHTLEFT)
      {
         role = getLeftRole();
      }
      else
      {
         role = getRightRole();
      }
      return (role == null ? null : role.getTarget());
   }


   private Set<UMLLink> instances = null;


   public boolean hasInInstances(UMLLink elem)
   {
      if (this.instances == null)
      {
         return false;
      }
      return this.instances.contains(elem);
   }


   public Iterator iteratorOfInstances()
   {
      if (this.instances == null)
      {
         return FEmptyIterator.get();
      }
      return instances.iterator();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAssoc#addToInstances(de.uni_paderborn.fujaba.uml.behavior.UMLLink)
    */
   public void addToInstances(UMLLink elem)
   {
      if (elem != null && !this.hasInInstances(elem))
      {
         if (this.instances == null)
         {
            this.instances = new FHashSet<UMLLink>();
         }
         this.instances.add(elem);
         elem.setInstanceOf(this);
         firePropertyChange(CollectionChangeEvent.get(this, "instances",
               this.instances, null, elem, CollectionChangeEvent.ADDED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAssoc#removeFromInstances(de.uni_paderborn.fujaba.uml.behavior.UMLLink)
    */
   public void removeFromInstances(UMLLink elem)
   {
      if (elem != null && this.hasInInstances(elem))
      {
         this.instances.remove(elem);
         elem.setInstanceOf(null);
         firePropertyChange(CollectionChangeEvent.get(this, "instances",
               this.instances, elem, null, CollectionChangeEvent.REMOVED));
      }
   }


   private void removeAllFromInstances()
   {
      Iterator iter = iteratorOfInstances();
      while (iter.hasNext())
      {
         UMLLink item = (UMLLink) iter.next();
         removeFromInstances(item);
      }
   }


   private transient boolean alreadyRemoved = false;


   /**
    * @deprecated (gets deleted in 5.1)
    */
   public boolean isAlreadyRemoved()
   {
      return alreadyRemoved;
   }

   @Override
   public FCodeStyle getInheritedCodeStyle ()
   {
      final FCodeStyle codeStyle = getCodeStyle();
      if (codeStyle!=null)
      {
         return codeStyle;
      }
      final UMLRole role;
      switch (getDirection())
      {
         case RIGHTLEFT:
            role = getLeftRole();
            break;
         default:
            role = getRightRole();
            break;
      }
      if (role != null)
      {
         return role.getInheritedCodeStyle();
      }
      return null;
   }
   
   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object). An
    * assoc is the child of the left target class!
    * 
    * @return the logical parent of this element;
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      return getProject();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      return getName();
   }


   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append("UMLAssoc[");
      result.append("name=");
      result.append(getName());
      result.append(",leftRole=");
      result.append(getLeftRole());
      result.append(",rightRole=");
      result.append(getRightRole());
      result.append("]");

      return result.toString();
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      UMLRole umlRole = this.leftRole;
      if (umlRole != null)
      {
         this.setLeftRole(null);
         umlRole.removeYou();
      }

      umlRole = this.rightRole;
      if (umlRole != null)
      {
         this.setRightRole(null);
         umlRole.removeYou();
      }

      this.removeAllFromInstances();
      alreadyRemoved = true;

      super.removeYou();
   }

   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      if (getLeftRole() != null && getLeftRole().getName() != null && !"".equals(getLeftRole().getName()))
      {
         return getLeftRole().getContextIdentifier(context);
      } else if (getRightRole() != null)
      {
         return getRightRole().getContextIdentifier(context);
      } else
      {
         throw new IllegalStateException("Invalid role configuration for this assoc!");
      }
   }
}

/*
 * $Log$
 * Revision 1.21  2007/04/24 09:05:50  cschneid
 * deprecation corrected, comment added; color convenience methods added to preferences; some fixes for uml object dialog; use new preferences in activity color updater; some fixed NPEs
 *
 * Revision 1.20  2007/03/21 12:47:48  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 * Revision 1.19  2006/04/25 11:58:26  cschneid
 * added deprecation expiration note, work on versioning
 *
 * Revision 1.18  2006/04/06 12:06:11  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.17  2006/03/06 14:49:43  lowende
 * AGElement.setCutCopyPasteParent removed.
 * FProject.findClass removed again.
 * FProject.elementsOfClasses removed.
 * Some enumerationOf... removed ->use iteratorOf...
 * Some unused methods in UMLAssoc removed.
 *
 */

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
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.metamodel.structure.FQualifier;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.uml.common.UMLIncrement;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLQualifier extends UMLIncrement implements FQualifier
{
   /**
    * Constructor for class UMLQualifier
    *
    * @param project
    * @param persistent
    */
   protected UMLQualifier (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String name = null;


   /**
    * Get the name attribute of the UMLQualifier object
    *
    * @return   The name value
    */
   @Override
   public String getName()
   {
      if (getQualifiedAttr() != null)
      {
         return getQualifiedAttr().getName();
      }
      if (getQualifiedRole() != null)
      {
         return getQualifiedRole().getAttrName();
      }
      if (name == null)
      {
         return " ";
      }
      else
      {
         return name;
      }

   }


   /**
    * Sets the name attribute of the UMLQualifier object
    *
    * @param name  The new name value
    */
   @Override
   public void setName (String name)
   {
      if ( (this.name == null && name != null) ||
          (this.name != null && !this.name.equals (name)))
      {
         String oldValue = this.name;
         this.name = name;
         firePropertyChange (NAME_PROPERTY, oldValue, name);
      }
   }


   /**
    * Get the externalQualifier attribute of the UMLQualifier object
    *
    * @return   The externalQualifier value
    */
   public boolean isExternalQualifier()
   {
      return  (qualifiedAttr == null && qualifiedRole == null);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private transient UMLRole revQualifier; // reverse UMLQualifier qualifier


   /**
    * Get the revQualifier attribute of the UMLQualifier object
    *
    * @return   The revQualifier value
    */
   public UMLRole getRevQualifier()
   {
      return revQualifier;
   }


   /**
    * Sets the revQualifier attribute of the UMLQualifier object
    *
    * @param revQualifier  The new revQualifier value
    */
   public void setRevQualifier (FRole revQualifier)
   {
      if ( (this.revQualifier == null && revQualifier != null) ||
          (this.revQualifier != null && !this.revQualifier.equals (revQualifier)))
      { // new partner

         FRole oldRevQualifier = this.revQualifier;
         if (this.revQualifier != null)
         { // inform old partner
            this.revQualifier = null;
            oldRevQualifier.setQualifier (null);
         }
         this.revQualifier = (UMLRole) revQualifier;
         if (revQualifier != null)
         { // inform new partner
            revQualifier.setQualifier (this);
         }
         firePropertyChange (REV_QUALIFIER_PROPERTY, oldRevQualifier, revQualifier);
      }
   }


   /**
    * <pre>
    *          0..1        qualifiedAttr         n
    * FAttr ------------------------------------- UMLQualifier
    *          qualifiedAttr      revQualifiedAttr
    * </pre>
    */
   private UMLAttr qualifiedAttr;


   /**
    * UMLMethod: '+ setQualifiedAttr (value: FAttr): Boolean'
    *
    * @param value  The new qualifiedAttr value
    * @return       No description provided
    */
   public boolean setQualifiedAttr (FAttr value)
   {
      boolean changed = false;
      if (this.qualifiedAttr != value)
      {
         FAttr oldValue = this.qualifiedAttr;
         boolean oldExternalQualifier = isExternalQualifier();
         if (this.qualifiedAttr != null)
         {
            this.qualifiedAttr = null;
            oldValue.removeFromRevQualifiedAttr (this);
         }
         this.qualifiedAttr = (UMLAttr) value;
         firePropertyChange (QUALIFIED_ATTR_PROPERTY, oldValue, value);
         // firing name change for unparsing
         firePropertyChange (NAME_PROPERTY,  (oldValue == null ? null : oldValue.getName()),  (value == null ? null : value.getName()));
         firePropertyChange (QUALIFIED_ROLE_PROPERTY, oldExternalQualifier, isExternalQualifier());
         if (value != null)
         {
            value.addToRevQualifiedAttr (this);
            setQualifiedRole (null);
         }
         changed = true;
      }
      return changed;
   }


   /**
    * UMLMethod: '+ getQualifiedAttr (): FAttr'
    *
    * @return   The qualifiedAttr value
    */
   public UMLAttr getQualifiedAttr()
   {
      return this.qualifiedAttr;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLRole qualifiedRole;


   /**
    * UMLMethod: '+ setQualifiedAttr (value: FAttr): Boolean'
    *
    * @param value  The new qualifiedAttr value
    * @return       No description provided
    */
   public boolean setQualifiedRole (FRole value)
   {
      boolean changed = false;
      if (this.qualifiedRole != value)
      {
         FRole oldValue = this.qualifiedRole;         
         boolean oldExternalQualifier = isExternalQualifier();
         if (this.qualifiedRole != null)
         {
            this.qualifiedRole = null;
            oldValue.removeFromRevQualifiedRole (this);
         }
         this.qualifiedRole = (UMLRole) value;
         firePropertyChange ("qualifiedRole", oldValue, value);
         // firing name change for unparsing
         firePropertyChange (NAME_PROPERTY,  (oldValue == null ? null : oldValue.getName()),  (value == null ? null : value.getName()));
         firePropertyChange (QUALIFIED_ROLE_PROPERTY, oldExternalQualifier, isExternalQualifier());
         if (value != null)
         {
            value.addToRevQualifiedRole (this);
            setQualifiedAttr (null);
         }
         changed = true;
      }
      return changed;
   }


   /**
    * UMLMethod: '+ getQualifiedAttr (): FAttr'
    *
    * @return   The qualifiedAttr value
    */
   public UMLRole getQualifiedRole()
   {
      return this.qualifiedRole;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private UMLType type = null; // reverse UMLQualifier revType


   /**
    * Get the type attribute of the UMLQualifier object
    *
    * @return   The type value
    */
   public UMLType getType()
   {
      if (getQualifiedAttr() != null)
      {
         return getQualifiedAttr().getAttrType();
      }
      else if (getQualifiedRole() != null)
      {
         return getQualifiedRole().getTarget();
      }
      return type;
   }


   /**
    * Sets the type attribute of the UMLQualifier object
    *
    * @param type  The new type value
    */
   public void setType (FType type)
   {
      if ( (this.type == null && type != null) ||
          (this.type != null && !this.type.equals (type)))
      { // new partner
         UMLType oldType = this.type;
         boolean oldExternalQualifier = isExternalQualifier();
         if (this.type != null)
         { // inform old partner
            this.type = null;
            oldType.removeFromRevType (this);
         }
         this.type = (UMLType) type;
         if (type != null)
         { // inform new partner
             ((UMLType) type).addToRevType (this);
         }
         firePropertyChange (TYPE_PROPERTY, oldType, type);
         firePropertyChange (QUALIFIED_ROLE_PROPERTY, oldExternalQualifier, isExternalQualifier());
      }
   }


   /**
    * Get the umlType attribute of the UMLQualifier object
    *
    * @return   The umlType value
    */
   public UMLType getUmlType()
   {
      if (qualifiedAttr != null)
      {
         return qualifiedAttr.getAttrType();
      }
      if (qualifiedRole != null)
      {
         return qualifiedRole.getTarget();
      }
      return type;
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      setType (null);
      setRevQualifier (null);
      setQualifiedAttr (null);
      setQualifiedRole (null);
      super.removeYou();
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    *
    * @return   the logical parent of this element, may not return null unless this is the top level node (project)
    */
   @Override
   public FElement getParentElement()
   {
      return getType();
   }

   @Override
   public String getQualifiedDisplayName()
   {
      UMLRole role = getRevQualifier();
      if ( role != null )
      {
         try
         {
            return role.getPartnerRole().getTarget().getFullClassName() + "." + role.getName() + "."+ getName();
         } catch (NullPointerException e)
         {
            return super.toString();
         }
      }
      else
      {
         return getQualifiedAttr() + "." + getName();
      }
   }
}

/*
 * $Log$
 * Revision 1.9  2007/03/23 12:45:06  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.8  2006/05/10 11:19:29  joerg
 * integrated free qualified and bound qualified assoc handling and display from Fujaba4; still layout problems remain
 *
 * Revision 1.7  2006/04/06 12:06:11  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.6  2006/03/01 12:23:02  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

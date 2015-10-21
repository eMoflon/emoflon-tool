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

import java.util.Iterator;

import de.fujaba.text.FTextReferenceUtility;
import de.fujaba.text.TextNode;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FParam;
import de.uni_paderborn.fujaba.metamodel.structure.FType;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLParam extends UMLDeclaration implements FParam
{

   protected UMLParam (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   private String name;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getName()
    */
   @Override
   public String getName()
   {
      return  (name == null) ?  ("") : name;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#setName(java.lang.String)
    */
   @Override
   public void setName (String name)
   {
      String oldValue = this.name;
      if (oldValue != null ? !oldValue.equals(name) : name != null)
      {
         this.name = name;
         firePropertyChange (NAME_PROPERTY, oldValue, name);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      String myName = getName() + " : ";

      if (getParamType() == null)
      {
         myName += "<unknown>";
      }
      else
      {
         myName += getParamType().getName();
      }

      return myName;
   }

   /*
    * The following attribute should be transient.
    * The other side is a List and if the link is
    * restored from this side, the order may be corrupted.
    */
   private transient UMLMethod revParam;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FParam#getRevParam()
    */
   public UMLMethod getRevParam()
   {
      return revParam;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FParam#setRevParam(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public void setRevParam (FMethod revParam)
   {
      if (this.revParam != revParam)
      { // new partner

         UMLMethod oldRevParam = this.revParam;
         if (this.revParam != null)
         {
            this.revParam = null;
            oldRevParam.removeFromParam (this);
         }
         this.revParam = (UMLMethod) revParam;
         if (revParam != null)
         { 
            revParam.addToParam (this);
         }
         firePropertyChange (REV_PARAM_PROPERTY, oldRevParam, revParam);
      }
   }


   private void removeRevParam()
   {
      this.setRevParam (null);
   }


   /**
    * @deprecated use stereotype {@link FStereotype#POINTER} instead.
    * @param pointer true to assign stereotype, false to remove it
    */
   public void setPointer (boolean pointer)
   {
      FStereotype stereotype = getProject().getFromFactories (FStereotype.class).getFromProducts (FStereotype.POINTER);
      if (pointer)
      {
         addToStereotypes (stereotype);
      }
      else
      {
         removeFromStereotypes (stereotype);
      }
   }


   private UMLType paramType;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FParam#getParamType()
    */
   public UMLType getParamType()
   {
      return paramType;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FParam#setParamType(de.uni_paderborn.fujaba.metamodel.structure.FType)
    */
   public void setParamType (FType paramType)
   {
      if (this.paramType != paramType)
      {
         // new partner
         UMLType oldParamType = this.paramType;
         if (this.paramType != null)
         {
            // inform old partner
            this.paramType = null;
            oldParamType.removeFromRevParamType (this);
         }
         this.paramType = (UMLType) paramType;
         firePropertyChange (PARAM_TYPE_PROPERTY, oldParamType, paramType);
         if (paramType != null)
         {
            // inform new partner
             ((UMLType) paramType).addToRevParamType (this);
         }
      }
   }


   private void removeParamType()
   {
      this.setParamType (null);
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      this.removeRevParam();
      this.removeParamType();

      super.removeYou();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      return getRevParam();
   }


   /**
    * @return   short string representation of current object
    */
   @Override
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append ("UMLParam[");
      result.append (getParamType());
      result.append (",");
      result.append (getName());
      result.append ("]");

      return result.toString();
   }

   /**
    * Implementation of the 'referencedElement' association between FTextReference and
    * UMLTextNode. Since this association must be implemented by numerous classes, all
    * operations are forwarded to the FTextReferenceUtility class.
    * 
    * <pre>
    *           0..1     referencedElement     0..n
    * FTextReference ------------------------- UMLTextNode
    *      referencedElement               textUsages
    * </pre>
    * 
    * @see de.fujaba.text.FTextReference
    * @see de.fujaba.text.FTextReferenceUtility
    */
   private FTextReferenceUtility textRefUtil;

   /**
    * Accessor for textRefUtil field featuring lazy instantiation.
    * 
    * @return the FTextReferenceUtility instance for this instance.
    */
   private FTextReferenceUtility getTextRefUtil()
   {
      if(this.textRefUtil == null)
      {
         this.textRefUtil = new FTextReferenceUtility(this);
      }
      return this.textRefUtil;
   }

   /**
    * Adds the given UMLTextNode instance to the set of
    * text usages. (Forwarded to FTextReferenceUtility)
    * 
    * @return true if the set was changed.
    */
   public boolean addToTextUsages(TextNode value)
   {
      return getTextRefUtil().addToTextUsages(value);
   }

   /**
    * Determines if this instance's set of text usages contains
    * the given UMLTextNode instance. (Forwarded to FTextReferenceUtility)
    */
   public boolean hasInTextUsages(TextNode value)
   {
      return getTextRefUtil().hasInTextUsages(value);
   }

   /**
    * @return an Iterator of this instance's set of text usages.
    * (Forwarded to FTextReferenceUtility)
    */
   public Iterator<TextNode> iteratorOfTextUsages()
   {
      return getTextRefUtil().iteratorOfTextUsages();
   }

   /**
    * Removes all elements from this instance's set of text usages.
    * (Forwarded to FTextReferenceUtility)
    */
   public void removeAllFromTextUsages()
   {
      getTextRefUtil().removeAllFromTextUsages();
   }

   /**
    * Removes the given element from this instance's set of text usages
    * if it is in there. (Forwarded to FTextReferenceUtility)
    * 
    * @return true if the set was changed.
    */
   public boolean removeFromTextUsages(TextNode value)
   {
      return getTextRefUtil().removeFromTextUsages(value);
   }

   /**
    * @return the number of UMLTextNode instances in this instance's set
    * of text usages. (Forwarded to FTextReferenceUtility)
    */
   public int sizeOfTextUsages()
   {
      return getTextRefUtil().sizeOfTextUsages();
   }

   @Override
   public String getQualifiedDisplayName()
   {
      UMLMethod method = getRevParam();
      if ( method == null )
      {
         return getName();
      }
      else
      {
         return getName() + " from " + (method.getParent() != null ? method.getParent().getName() + "." : "" )
               + method.getName() + "(..)"
               + " in " + getProject().getName();
      }
   }
}

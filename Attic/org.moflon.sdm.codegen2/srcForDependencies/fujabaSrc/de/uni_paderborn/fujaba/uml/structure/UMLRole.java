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


import java.util.Collection;
import java.util.Iterator;

import de.fujaba.text.FTextReferenceUtility;
import de.fujaba.text.TextNode;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FAccessStyle;
import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FCardinality;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FDeclaration;
import de.uni_paderborn.fujaba.metamodel.structure.FQualifier;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.uml.common.UMLIncrement;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashSet;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLRole extends UMLIncrement implements FRole
{

   protected UMLRole(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   private String name;


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
      String oldValue = this.name;
      this.name = name;
      firePropertyChange(NAME_PROPERTY, oldValue, name);
   }


   private int umlVisibility = FDeclaration.PUBLIC;


   /**
    * @deprecated use {@link #getVisibility()} instead (gets deleted in 5.1)
    */
   public int getUmlVisibility()
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @deprecated use {@link #setVisibility(int)} instead (gets deleted in 5.1)
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#setUmlVisibility(int)
    */
   public void setUmlVisibility(int newUmlVisibility)
   {
      throw new UnsupportedOperationException("deprecated");
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#getVisibility()
    */
   public int getVisibility()
   {
      return this.umlVisibility;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#setVisibility(int)
    */
   public void setVisibility(int visibility)
   {
      int oldValue = this.umlVisibility;
      this.umlVisibility = visibility;

      firePropertyChange(FDeclaration.VISIBILITY_PROPERTY, oldValue, visibility);
   }


   /**
    * Value is one of {None, Aggregation, Composition, Reference, Qualified}
    *
    * @see #NONE
    * @see #AGGREGATION
    * @see #COMPOSITION
    * @see #REFERENCE
    * @see #QUALIFIED
    */
   private int adornment;


   /**
    * Get the adornment attribute of the UMLRole object, one of {None, Aggregation, Composition,
    * Reference, Qualified}
    *
    * @return The adornment value
    * @see #NONE
    * @see #AGGREGATION
    * @see #COMPOSITION
    * @see #REFERENCE
    * @see #QUALIFIED
    */
   public int getAdornment()
   {
      return adornment;
   }


   /**
    * Sets the adornment attribute of the UMLRole object, one of {None, Aggregation, Composition,
    * Reference, Qualified}
    *
    * @param adornment The new adornment value
    * @see #NONE
    * @see #AGGREGATION
    * @see #COMPOSITION
    * @see #REFERENCE
    * @see #QUALIFIED
    */
   public void setAdornment(int adornment)
   {
      int oldValue = this.adornment;
      this.adornment = adornment;
      firePropertyChange(ADORNMENT_PROPERTY, oldValue, adornment);
   }


   /**
    * Indicates if the role was generated from parsed source code. If it is true, there will be no
    * code generated for the role, since access methods already exist in the parsed code.
    */
   private boolean parsed = false;


   /**
    * Indicate that the role was generated from parsed source code. If it is set to true, there will
    * be no code generated for the role, since access methods already exist in the parsed code.
    *
    * @param parsed true, if the role was generated from parsed source code
    */
   public void setParsed(boolean parsed)
   {
      if (this.parsed != parsed)
      {
         this.parsed = parsed;
         firePropertyChange("parsed", !parsed, parsed);
      }
   }


   /**
    * Indicates if the role was generated from parsed source code. If it is true, there will be no
    * code generated for the role, since access methods already exist in the parsed code.
    *
    * @return true, if the role was generated from parsed source code
    */
   public boolean isParsed()
   {
      return this.parsed;
   }

   public FAccessStyle getAccessStyle()
   {
      final FCodeStyle style = getCodeStyle();
      if (style == null || style instanceof FAccessStyle)
      {
         return (FAccessStyle) style;
      }
      return null;
   }

   public void setAccessStyle(final FAccessStyle accessStyle)
   {
      final FCodeStyle oldStyle = getCodeStyle();
      if (oldStyle != accessStyle)
      {
         setCodeStyle(accessStyle);
         firePropertyChange(ACCESS_STYLE_PROPERTY, oldStyle, accessStyle);
      }
   }

   public FAccessStyle getInheritedAccessStyle()
   {
      final FCodeStyle style = getInheritedCodeStyle();
      if (style == null || style instanceof FAccessStyle)
      {
         return (FAccessStyle) style;
      }
      return null;
   }

   @Override
   public FCodeStyle getInheritedCodeStyle()
   {
      FCodeStyle ownStyle = getCodeStyle();
      if ( ownStyle != null ) {
         return ownStyle;
      } else {
         if (getRevLeftRole() != null)
         {
            UMLRole rightRole = getRevLeftRole().getRightRole();
            if (rightRole != null)
            {
               UMLClass target = rightRole.getTarget();
               if (target != null)
               {
                  return target.getInheritedCodeStyle();
               }
            }
         } else if (getRevRightRole() != null)
         {
            UMLRole leftRole = getRevRightRole().getLeftRole();
            if (leftRole != null)
            {
               UMLClass target = leftRole.getTarget();
               if (target != null)
               {
                  return target.getInheritedCodeStyle();
               }
            }
         }
         return null;
      }
   }

   private UMLClass target;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#getTarget()
    */
   public UMLClass getTarget()
   {
      return target;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#setTarget(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public void setTarget(FClass target)
   {
      if (this.target != target)
      { // new partner

         UMLClass oldTarget = this.target;
         if (this.target != null)
         { // inform old partner

            this.target = null;
            oldTarget.removeFromRoles(this);
         }
         this.target = (UMLClass) target;
         if (target != null)
         { // inform new partner

            target.addToRoles(this);
         }
         firePropertyChange(TARGET_PROPERTY, oldTarget, target);
      }
   }


   private UMLAssoc revLeftRole = null;


   public UMLAssoc getRevLeftRole()
   {
      return revLeftRole;
   }


   public void setRevLeftRole(FAssoc revLeftRole)
   {
      if ((this.revLeftRole == null && revLeftRole != null)
            || (this.revLeftRole != null && !this.revLeftRole
                  .equals(revLeftRole)))
      { // new partner

         UMLAssoc oldRevLeftRole = this.revLeftRole;
         if (this.revLeftRole != null)
         { // inform old partner

            this.revLeftRole = null;
            oldRevLeftRole.setLeftRole(null);
         }
         this.revLeftRole = (UMLAssoc) revLeftRole;
         if (this.revLeftRole != null)
         { // inform new partner

            this.revLeftRole.setLeftRole(this);
         }
         firePropertyChange(UMLRole.REV_LEFT_ROLE_PROPERTY, oldRevLeftRole, revLeftRole);
         firePropertyChange(FRole.ASSOC_PROPERTY, oldRevLeftRole, revLeftRole);
      }
   }


   private final void removeRevLeftRole()
   {
      UMLAssoc incr = this.revLeftRole;
      if (incr != null)
      {
         this.setRevLeftRole(null);
         incr.removeYou();
      }
   }


   private UMLAssoc revRightRole = null;


   public UMLAssoc getRevRightRole()
   {
      return revRightRole;
   }


   public void setRevRightRole(FAssoc revRightRole)
   {
      if ((this.revRightRole == null && revRightRole != null)
            || (this.revRightRole != null && !this.revRightRole
                  .equals(revRightRole)))
      {
         // new partner
         UMLAssoc oldRevRightRole = this.revRightRole;
         if (this.revRightRole != null)
         {
            // inform old partner
            this.revRightRole = null;
            oldRevRightRole.setRightRole(null);
         }
         this.revRightRole = (UMLAssoc) revRightRole;
         if (this.revRightRole != null)
         {
            // inform new partner
            this.revRightRole.setRightRole(this);
         }
         firePropertyChange(UMLRole.REV_RIGHT_ROLE_PROPERTY, oldRevRightRole, revRightRole);
         firePropertyChange(FRole.ASSOC_PROPERTY, oldRevRightRole, revRightRole);
      }
   }


   private final void removeRevRightRole()
   {
      UMLAssoc incr = this.revRightRole;
      if (incr != null)
      {
         this.setRevRightRole(null);
         incr.removeYou();
      }
   }

   /**
    * store value for field card
    */
   private UMLCardinality card;

   public UMLCardinality getCard()
   {
      return this.card;
   }

   public void setCard(FCardinality fvalue)
   {
      UMLCardinality value = (UMLCardinality) fvalue;
      final UMLCardinality oldValue = this.card;
      if (oldValue != value)
      {
         if (oldValue != null)
         {
            this.card = null;
            oldValue.removeFromRoles(this);
         }
         this.card = value;
         firePropertyChange(CARD_PROPERTY, oldValue, value);
         if (value != null)
         {
            value.addToRoles(this);
         }
      }
   }


   private void removeCard()
   {
      UMLCardinality incr = getCard();
      if (incr != null)
      {
         this.setCard(null);
      }
   }


   private UMLQualifier qualifier = null;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#getQualifier()
    */
   public UMLQualifier getQualifier()
   {
      return qualifier;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#setQualifier(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public void setQualifier(FQualifier qualifier)
   {
      if ((this.qualifier == null && qualifier != null)
            || (this.qualifier != null && !this.qualifier.equals(qualifier)))
      {
         UMLQualifier oldQualifier = this.qualifier;
         if (this.qualifier != null)
         {
            this.qualifier = null;
            oldQualifier.setRevQualifier(null);
         }
         this.qualifier = (UMLQualifier) qualifier;
         if (this.qualifier != null)
         {
            this.qualifier.setRevQualifier(this);
         }
         firePropertyChange(QUALIFIER_PROPERTY, oldQualifier, qualifier);
      }
   }


   private final void removeQualifier()
   {
      UMLQualifier incr = getQualifier();
      if (incr != null)
      {
         this.setQualifier(null);
         incr.removeYou();
      }
   }


   /**
    * <pre>
    *               n        qualifiedRole         0..1
    * UMLQualifier ------------------------------------- UMLRole
    *               revQualifiedRole      qualifiedRole
    * </pre>
    */
   private transient FHashSet revQualifiedRole;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#hasInRevQualifiedRole(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean hasInRevQualifiedRole(FQualifier value)
   {
      return ((this.revQualifiedRole != null) && (value != null) && this.revQualifiedRole
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#iteratorOfRevQualifiedRole()
    */
   public Iterator iteratorOfRevQualifiedRole()
   {
      return ((this.revQualifiedRole == null) ? FEmptyIterator.get()
            : this.revQualifiedRole.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#sizeOfRevQualifiedRole()
    */
   public int sizeOfRevQualifiedRole()
   {
      return ((this.revQualifiedRole == null) ? 0 : this.revQualifiedRole
            .size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#addToRevQualifiedRole(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean addToRevQualifiedRole(FQualifier value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revQualifiedRole == null)
         {
            this.revQualifiedRole = new FHashSet();

         }
         changed = this.revQualifiedRole.add(value);
         if (changed)
         {
            ((UMLQualifier) value).setQualifiedRole(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#removeFromRevQualifiedRole(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean removeFromRevQualifiedRole(FQualifier value)
   {
      boolean changed = false;
      if ((this.revQualifiedRole != null) && (value != null))
      {
         changed = this.revQualifiedRole.remove(value);
         if (changed)
         {
            ((UMLQualifier) value).setQualifiedRole(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#removeAllFromRevQualifiedRole()
    */
   public void removeAllFromRevQualifiedRole()
   {
      UMLQualifier tmpValue;
      Iterator iter = this.iteratorOfRevQualifiedRole();
      while (iter.hasNext())
      {
         tmpValue = (UMLQualifier) iter.next();
         this.removeFromRevQualifiedRole(tmpValue);
      }

   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#getAssoc()
    */
   public UMLAssoc getAssoc()
   {
      return ((revLeftRole != null) ? revLeftRole : revRightRole);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#getPartnerRole()
    */
   public UMLRole getPartnerRole()
   {
      UMLAssoc assoc = getAssoc();
      if (assoc != null)
      {
         return ((assoc.getLeftRole() != this) ? assoc.getLeftRole() : assoc
               .getRightRole());
      }
      else
      {
         return null;
      }
   }


   /**
    * Returns an attribute name which represents this role. If this role has already a name, this
    * name will be returned. If this role does not have a name but has a target class, a default
    * name will be created. If this role does not have a name and no target class, the return value
    * is null.
    * 
    * @return The attribute name as a string.
    */
   public String getAttrName()
   {
      String name = getName();

      if (name == null)
      {
         this.setName("");
      }

      return name;
   }


   private String sortedComparator;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#getSortedComparator()
    */
   public String getSortedComparator()
   {
      return sortedComparator;
   }


   public void setSortedComparator(String comparator)
   {
      if ((this.sortedComparator == null)
            || !this.sortedComparator.equals(comparator))
      {
         String oldValue = this.sortedComparator;
         this.sortedComparator = comparator;
         firePropertyChange(UMLRole.SORTED_COMPARATOR_PROPERTY, oldValue, comparator);
      }
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      this.setTarget(null);
      this.removeRevLeftRole();
      this.removeRevRightRole();
      this.removeCard();
      this.removeQualifier();

      UMLAttr tmpAssociatedAttribute = getAssociatedAttribute();
      if (tmpAssociatedAttribute != null)
      {
         setAssociatedAttribute(null);
         tmpAssociatedAttribute.removeYou();
      }
      
      super.removeYou();
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    * 
    * @return the logical parent of this element, may not return null unless this is the top level
    *         node (project)
    */
   @Override
   public UMLAssoc getParentElement()
   {
      if (getRevLeftRole() != null)
      {
         return getRevLeftRole();
      }
      else if (getRevRightRole() != null)
      {
         return getRevRightRole();
      }
      else
      {
         return null;
      }
   }


   /**
    * @return short string representation of current object
    */
   @Override
   public String toString()
   {
      return "UMLRole[id=" + getID() + ",name=" + getName() + ",target="
            + getTarget() + "]";
   }


   /**
    * <pre>
    *          0..1                                      0..1
    * UMLAttr ------------------------------------------------ UMLRole
    *          associatedAttribute      implementingAssocRole
    * </pre>
    */
   private UMLAttr associatedAttribute;


   public boolean setAssociatedAttribute(UMLAttr value)
   {
      boolean changed = false;

      if (this.associatedAttribute != value)
      {
         if (this.associatedAttribute != null)
         {
            UMLAttr oldValue = this.associatedAttribute;
            this.associatedAttribute = null;
            oldValue.setImplementingAssocRole(null);
         }

         this.associatedAttribute = value;

         if (value != null)
         {
            value.setImplementingAssocRole(this);
         }

         changed = true;
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FRole#getAssociatedAttribute()
    */
   public UMLAttr getAssociatedAttribute()
   {
      return this.associatedAttribute;
   }

   public boolean isReadOnly()
   {
      return this.readOnly;
   }

   /**
    * store the value for field readOnly
    */
   private boolean readOnly;

   public void setReadOnly(final boolean value)
   {
      final boolean oldValue = this.readOnly;
      if (oldValue != value)
      {
         this.readOnly = value;
         firePropertyChange(PROPERTY_READ_ONLY, oldValue, value);
      }
   }

   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      return getParent().getContextIdentifier(context) + "." + getName();
   }

   private UMLClass getParent()
   {
      UMLRole partnerRole = getPartnerRole();
		if (partnerRole != null)
		{
			return partnerRole.getTarget();
		}
		return null;
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

}

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


import de.fujaba.text.FTextReferenceUtility;
import de.fujaba.text.TextNode;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.*;
import de.uni_paderborn.fujaba.uml.behavior.UMLAttrExprPair;
import de.upb.tools.fca.*;
import de.upb.tools.pcs.CollectionChangeEvent;

import java.util.Collection;
import java.util.Iterator;


/**
 * <h2>Associations</h2>
 *
 * <pre>
 *          +-----------+ 1              1
 * UMLClass | getName() +------------------ UMLAttr
 *          +-----------+ parent     attrs
 *
 *          0..1                                      0..1
 * UMLRole ------------------------------------------------ UMLAttr
 *          implementingAssocRole      associatedAttribute
 *
 *            0..*                            0..1
 * UMLMethod -------------------------------------- UMLAttr
 *            accessMethods      accessedAttribute
 *
 * </pre>
 *
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLAttr extends UMLDeclaration implements FAttr
{

   protected UMLAttr(FProject project, boolean persistent)
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
      return this.name;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#setName(java.lang.String)
    */
   @Override
   public void setName(String newName)
   {
      if (this.name == null || !this.name.equals(newName))
      {
         String oldName = this.name;
         this.name = newName;

         firePropertyChange(NAME_PROPERTY, oldName, newName);
      }
   }


   /**
    * Get the value of pointer.
    *
    * @return Value of pointer.
    * @deprecated (gets deleted in 5.1) Use stereotype instead
    */
   public boolean isPointer()
   {
      return hasKeyInStereotypes(FStereotype.POINTER);
   }


   /**
    * Set the value of pointer.
    *
    * @param pointer Value to assign to pointer.
    * @deprecated (gets deleted in 5.1) Use stereotype instead
    */
   public void setPointer(boolean pointer)
   {
      FFactory<FStereotype> stereotypeFactory = getProject().getFromFactories(
            FStereotype.class);
      boolean changed = false;
      if (pointer)
      {
         changed = addToStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.POINTER));
      }
      else
      {
         changed = removeFromStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.POINTER));
      }
      if (changed)
      {
         firePropertyChange("pointer", !pointer, pointer);
      }
   }


   /**
    * @deprecated (gets deleted in 5.1) Use stereotype instead
    */
   public boolean isFinal()
   {
      return hasKeyInStereotypes(FStereotype.FINAL);
   }


   /**
    * @deprecated (gets deleted in 5.1) Use stereotype instead
    */
   public void setFinal(boolean pointer)
   {
      FFactory<FStereotype> stereotypeFactory = getProject().getFromFactories(
            FStereotype.class);
      boolean changed = false;
      if (pointer)
      {
         changed = addToStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.FINAL));
      }
      else
      {
         changed = removeFromStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.FINAL));
      }
      if (changed)
      {
         firePropertyChange("final", !pointer, pointer);
      }
   }


   @Property (name=FAttr.STATIC_PROPERTY)
   private boolean umlStatic;

   /**
    * Just for loading and saving the umlStatic property.
    * @see #isStatic()
    */
   /*package*/ boolean isUmlStatic()
   {
      return isStatic();
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#isStatic()
    */
   public boolean isStatic()
   {
      return this.umlStatic;
   }

   /**
    * Just for loading and saving the umlStatic property.
    * @see #setStatic()
    */
   /*package*/ void setUmlStatic(boolean newValue)
   {
      setStatic(newValue);
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#setStatic(boolean)
    */
   public void setStatic(boolean newValue)
   {
      boolean oldValue = this.umlStatic;
      this.umlStatic = newValue;
      firePropertyChange(STATIC_PROPERTY, oldValue, newValue);
   }

   // --- Property enumConstant --- // TODO move to FAttr
   public final static String ENUMCONSTANT_PROPERTY = "enumConstant";

   
   private boolean enumConstant;

   // TODO add to FAttr
   public boolean isEnumConstant ()
   {
      return this.enumConstant;
   }

   // TODO add to FAttr
   public void setEnumConstant(boolean newValue)
   {
      // TODO don't use a attribute, set a stereotype
      boolean oldValue = this.enumConstant;
      this.enumConstant = newValue;
      firePropertyChange(ENUMCONSTANT_PROPERTY, oldValue, newValue);
  }   
   
   /**
    * @deprecated use {@link #getVisibility()} instead
    */
   @NoProperty
   public int getUmlVisibility()
   {
      throw new UnsupportedOperationException("deprecated");
   } // getUmlVisibility


   /**
    * @deprecated use {@link #setVisibility(int)} instead
    */
   @NoProperty
   public void setUmlVisibility(int newUmlVisibility)
   {
      throw new UnsupportedOperationException("deprecated");
   } // setUmlVisibility


   private String initialValue;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#getInitialValue()
    */
   public String getInitialValue()
   {
      return initialValue;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#setInitialValue(java.lang.String)
    */
   public void setInitialValue(String initialValue)
   {
      String oldValue = this.initialValue;
      this.initialValue = initialValue;
      firePropertyChange(INITIAL_VALUE_PROPERTY, oldValue, initialValue);
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


   /**
    * <pre>
    *          +-----------+ 1              1
    * UMLClass | getName() +------------------ UMLAttr
    *          +-----------+ parent     attrs
    * </pre>
    */
   private UMLClass parent;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#setParent(de.uni_paderborn.fujaba.metamodel.structure.FClass)
    */
   public boolean setParent(FClass obj)
   {
      boolean changed = false;

      if (this.parent != obj)
      {
         UMLClass oldValue = this.parent;
         if (this.parent != null)
         {
            this.parent = null;
            oldValue.removeFromAttrs(this);
         }
         this.parent = (UMLClass) obj;
         if (obj != null)
         {
            obj.addToAttrs(this);
         }
         changed = true;

         // side effects
         firePropertyChange(PARENT_PROPERTY, oldValue, obj);
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#getParent()
    */
   public UMLClass getParent()
   {
      return this.parent;
   }


   /**
    * <pre>
    *               n        qualifiedAttr         0..1
    * UMLQualifier ------------------------------------- UMLAttr
    *               revQualifiedAttr      qualifiedAttr
    * </pre>
    */
   private transient FHashSet revQualifiedAttr;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#hasInRevQualifiedAttr(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean hasInRevQualifiedAttr(FQualifier value)
   {
      return ((this.revQualifiedAttr != null) && (value != null) && this.revQualifiedAttr
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#iteratorOfRevQualifiedAttr()
    */
   public Iterator iteratorOfRevQualifiedAttr()
   {
      return ((this.revQualifiedAttr == null) ? FEmptyIterator.get()
            : this.revQualifiedAttr.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#sizeOfRevQualifiedAttr()
    */
   public int sizeOfRevQualifiedAttr()
   {
      return ((this.revQualifiedAttr == null) ? 0 : this.revQualifiedAttr
            .size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#addToRevQualifiedAttr(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean addToRevQualifiedAttr(FQualifier value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revQualifiedAttr == null)
         {
            this.revQualifiedAttr = new FHashSet();

         }
         changed = this.revQualifiedAttr.add(value);
         if (changed)
         {
            value.setQualifiedAttr(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#removeFromRevQualifiedAttr(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean removeFromRevQualifiedAttr(FQualifier value)
   {
      boolean changed = false;
      if ((this.revQualifiedAttr != null) && (value != null))
      {
         changed = this.revQualifiedAttr.remove(value);
         if (changed)
         {
            value.setQualifiedAttr(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#removeAllFromRevQualifiedAttr()
    */
   public void removeAllFromRevQualifiedAttr()
   {
      UMLQualifier tmpValue;
      Iterator iter = this.iteratorOfRevQualifiedAttr();
      while (iter.hasNext())
      {
         tmpValue = (UMLQualifier) iter.next();
         this.removeFromRevQualifiedAttr(tmpValue);
      }

   }


   private UMLType attrType;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#getAttrType()
    */
   public UMLType getAttrType()
   {
      return this.attrType;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#setAttrType(de.uni_paderborn.fujaba.metamodel.structure.FType)
    */
   public void setAttrType(FType attrType)
   {
      if (this.attrType != attrType)
      { // new partner

         UMLType oldAttrType = this.attrType;
         if (this.attrType != null)
         {
            this.attrType = null;
            oldAttrType.removeFromRevAttrType(this);
         }
         this.attrType = (UMLType) attrType;
         if (attrType != null)
         {
            ((UMLType) attrType).addToRevAttrType(this);
         }
         firePropertyChange(ATTR_TYPE_PROPERTY, oldAttrType, attrType);
      }
   }


   @Property( name = INSTANCES_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         partner = UMLAttrExprPair.INSTANCE_OF_PROPERTY, adornment = ReferenceHandler.Adornment.NONE)
   private FPropHashSet instances = new FPropHashSet(this, INSTANCES_PROPERTY);


   public boolean hasInInstances(UMLAttrExprPair elem)
   {
      return this.instances.contains(elem);
   }


   public Iterator iteratorOfInstances()
   {
      return instances.iterator();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#addToInstances(de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public void addToInstances(FElement elem)
   {
      UMLAttrExprPair umlAttrExprPair = (UMLAttrExprPair) elem;
      if (umlAttrExprPair != null && !this.hasInInstances(umlAttrExprPair))
      {
         this.instances.add(umlAttrExprPair);
         umlAttrExprPair.setInstanceOf(this);
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#removeFromInstances(de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public void removeFromInstances(FElement elem)
   {
      UMLAttrExprPair umlAttrExprPair = (UMLAttrExprPair) elem;
      if (this.hasInInstances(umlAttrExprPair))
      {
         this.instances.remove(umlAttrExprPair);
         umlAttrExprPair.setInstanceOf(null);
         firePropertyChange(CollectionChangeEvent.get(this, "instances",
               this.instances, elem, null, CollectionChangeEvent.REMOVED));
      }
   }


   public void removeAllFromInstances()
   {
      UMLAttrExprPair item;
      Iterator iter = iteratorOfInstances();

      while (iter.hasNext())
      {
         item = (UMLAttrExprPair) iter.next();
         item.setInstanceOf(null);
         firePropertyChange(CollectionChangeEvent.get(this, INSTANCES_PROPERTY,
               this.instances, item, null, CollectionChangeEvent.REMOVED));
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      setParent(null);
      removeAllFromRevQualifiedAttr();
      removeAllFromInstances();

      setAttrType((UMLType) null);

      UMLRole tmpImplementingAssocRole = getImplementingAssocRole();
      if (tmpImplementingAssocRole != null)
      {
         setImplementingAssocRole(null);
      }
      removeAllFromAccessMethods();
      
      TextNode tmpParsetree = getParsetree ();
      if (tmpParsetree != null)
      {
         setParsetree (null);
      }

      super.removeYou();
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    * 
    * @return the logical parent of this element;
    */
   @Override
   public FElement getParentElement()
   {
      return getParent();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(FDeclaration.VISIBILITY_CHAR[getVisibility()]);
      buffer.append(" ");

      buffer.append(getName());
      buffer.append(" : ");
      buffer.append((getAttrType() == null) ? "<unknown>" : getAttrType()
            .getName());

      if ((getInitialValue() != null) && !getInitialValue().equals(""))
      {
         buffer.append(" = ");
         buffer.append(getInitialValue());
      }

      return buffer.toString();
   }


   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append("UMLAttr[");
      result.append(getText());
      result.append("]");

      return result.toString();
   }


   private int createAccessMethods = CREATE_ACCESS_METHODS_DEFAULT;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#getCreateAccessMethods()
    */
   public int getCreateAccessMethods()
   {
      return this.createAccessMethods;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#setCreateAccessMethods(int)
    */
   public void setCreateAccessMethods(int createAccessMethods)
   {
      if (this.createAccessMethods != createAccessMethods)
      {
         int oldValue = this.createAccessMethods;
         boolean oldNoAccessMethods = isNoAccessMethods();

         this.createAccessMethods = createAccessMethods;
         firePropertyChange("createAccessMethods", oldValue,
               createAccessMethods);

         if (isNoAccessMethods() != oldNoAccessMethods)
         {
            firePropertyChange("noAccessMethods", oldNoAccessMethods,
                  !oldNoAccessMethods);
         }
      }
   }


   /**
    * Sets the noAccessMethods attribute of the UMLAttr object
    * 
    * @param noAccessMethods The new noAccessMethods value
    * @deprecated (gets deleted in 5.1) use {@link #getCreateAccessMethods()} instead
    */
   public void setNoAccessMethods(boolean noAccessMethods)
   {
      setCreateAccessMethods((noAccessMethods ? CREATE_ACCESS_METHODS_NO
            : CREATE_ACCESS_METHODS_DEFAULT));
   }


   /**
    * Get the noAccessMethods attribute of the UMLAttr object
    * 
    * @return The noAccessMethods value
    * @deprecated (gets deleted in 5.1) use {@link #getCreateAccessMethods()} instead
    */
   public boolean isNoAccessMethods()
   {
      return (getCreateAccessMethods() == CREATE_ACCESS_METHODS_NO);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#isNeedsAccessMethods()
    * @deprecated (gets deleted in 5.1)
    */
   public boolean isNeedsAccessMethods()
   {
      switch (getCreateAccessMethods())
      {
         case CREATE_ACCESS_METHODS_NO:
            return false;
         case CREATE_ACCESS_METHODS_DEFAULT:
            return !isStatic() || !hasKeyInStereotypes(FStereotype.FINAL);
         default:
            return true;
      }
   }


   /**
    * Sets the transient attribute of the UMLAttr object
    * 
    * @deprecated (gets deleted in 5.1) Use stereotype instead
    * @param javaTransient The new transient value
    */
   public void setTransient(boolean javaTransient)
   {
      FFactory<FStereotype> stereotypeFactory = getProject().getFromFactories(
            FStereotype.class);
      boolean changed = false;
      if (javaTransient)
      {
         changed = addToStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.TRANSIENT));
      }
      else
      {
         changed = removeFromStereotypes(stereotypeFactory
               .getFromProducts(FStereotype.TRANSIENT));
      }
      if (changed)
      {
         firePropertyChange("transient", !javaTransient, javaTransient);
      }
   }


   /**
    * Get the transient attribute of the UMLAttr object
    * 
    * @deprecated (gets deleted in 5.1) Use stereotype instead
    * @return The transient value
    */
   public boolean isTransient()
   {
      return hasKeyInStereotypes(FStereotype.TRANSIENT);
   }


   /**
    * <pre>
    *          0..1                Assoc                 0..1
    * UMLRole ------------------------------------------------ UMLAttr
    *          implementingAssocRole      associatedAttribute
    * </pre>
    */
   private transient UMLRole implementingAssocRole;


   /**
    * UMLMethod: '+ setImplementingAssocRole (value: UMLRole): Boolean'.
    * 
    * @param value The new implementingAssocRole value
    * @return No description provided
    */
   public boolean setImplementingAssocRole(FRole value)
   {
      boolean changed = false;

      if (this.implementingAssocRole != value)
      {
         UMLRole oldValue = this.implementingAssocRole;
         if (this.implementingAssocRole != null)
         {
            this.implementingAssocRole = null;
            oldValue.setAssociatedAttribute(null);
         }

         this.implementingAssocRole = (UMLRole) value;
         firePropertyChange("implementingAssocRole", oldValue, value);

         if (value != null)
         {
            ((UMLRole) value).setAssociatedAttribute(this);
         }
         changed = true;
      }

      return changed;
   }


   /**
    * UMLMethod: '+ getImplementingAssocRole (): UMLRole'.
    * 
    * @return The implementingAssocRole value
    */
   public UMLRole getImplementingAssocRole()
   {
      return this.implementingAssocRole;
   }


   /**
    * <pre>
    *            0..*           Assoc            0..1
    * UMLMethod -------------------------------------- UMLAttr
    *            accessMethods      accessedAttribute
    * </pre>
    */
   private FHashSet accessMethods;


   public boolean hasInAccessMethods(FMethod value)
   {
      return ((this.accessMethods != null) && (value != null) && this.accessMethods
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#iteratorOfAccessMethods()
    * @deprecated
    */
   public Iterator iteratorOfAccessMethods()
   {
      return ((this.accessMethods == null) ? FEmptyIterator.get()
            : this.accessMethods.iterator());
   }


   public int sizeOfAccessMethods()
   {
      return ((this.accessMethods == null) ? 0 : this.accessMethods.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#addToAccessMethods(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean addToAccessMethods(FMethod value)
   {
      boolean changed = false;

      if (value != null)
      {
         if (this.accessMethods == null)
         {
            this.accessMethods = new FPropHashSet(this, "accessMethods");
         }

         changed = this.accessMethods.add(value);

         if (changed)
         {
            value.setAccessedAttribute(this);
         }
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FAttr#removeFromAccessMethods(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean removeFromAccessMethods(FMethod value)
   {
      boolean changed = false;

      if ((this.accessMethods != null) && (value != null))
      {
         changed = this.accessMethods.remove(value);

         if (changed)
         {
            value.setAccessedAttribute(null);
         }
      }

      return changed;
   }


   public void removeAllFromAccessMethods()
   {
      UMLMethod tmpValue;

      Iterator iter = this.iteratorOfAccessMethods();
      while (iter.hasNext())
      {
         tmpValue = (UMLMethod) iter.next();
         this.removeFromAccessMethods(tmpValue);
         tmpValue.removeYou();
      }
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
      return ( getParent() != null ? getParent().getName() + "." : "" ) + this.getName()
            + " in " + getProject().getName();
   }

   /*
    * FParsedElement interface implementation
    */

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getTextPropertyName()
    */
   public String getTextPropertyName()
   {
      return INITIAL_VALUE_PROPERTY;
   }

   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getParsedText()
    */
   public String getParsedText()
   {
      return getInitialValue();
   }
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#setParsedText(java.lang.String)
    */
   public void setParsedText(String value)
   {
      setInitialValue(value);
   }

   /**
    * The parsetree represents the syntax of this element's textual expression,
    * i.e. the 'initialValue' field for the type UMLAttr.
    */
   private TextNode parsetree;
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#getParsetree()
    */
   public TextNode getParsetree()
   {
      return this.parsetree;
   }
   
   /*
    * (non-Javadoc)
    * @see de.fujaba.text.FParsedElement#setParsetree(de.fujaba.text.nodes.UMLTextNode)
    */
   public void setParsetree(TextNode node)
   {
      this.parsetree = node;
   }
}

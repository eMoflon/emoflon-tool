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
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;
import de.uni_paderborn.fujaba.metamodel.structure.FArray;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FParam;
import de.uni_paderborn.fujaba.metamodel.structure.FQualifier;
import de.uni_paderborn.fujaba.uml.common.UMLIncrement;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FPropHashSet;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *             +------+ 1                 1
 * UMLTypeList | name +--------------------- UMLType
 *             +------+ revTypes      types
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class UMLBaseType extends UMLIncrement implements UMLType, FBaseType
{

   /**
    * Don't call directly use appropriate Factory instead.
    * 
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory
    */
   protected UMLBaseType(FProject project, boolean persistent, String factoryKey)
   {
      super(project, persistent, factoryKey);
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
      firePropertyChange(FElement.NAME_PROPERTY, oldValue, name);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getText()
    */
   @Override
   public String getText()
   {
      return getName();
   }


   private String progLangType;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#getProgLangType()
    * @deprecated (gets deleted in 5.1)
    */
   public String getProgLangType()
   {
      return progLangType;
   }


   /**
    * @deprecated (gets deleted in 5.1)
    */
   public void setProgLangType(String progLangType)
   {
      String oldValue = this.progLangType;
      this.progLangType = progLangType;
      firePropertyChange("progLangType", oldValue, progLangType);
   }


   private transient FPropHashSet revAttrType;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#hasInRevAttrType(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
    */
   public boolean hasInRevAttrType(FAttr value)
   {
      return ((this.revAttrType != null) && (value != null) && this.revAttrType
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#iteratorOfRevAttrType()
    */
   public Iterator iteratorOfRevAttrType()
   {
      return ((this.revAttrType == null) ? FEmptyIterator.get()
            : this.revAttrType.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#sizeOfRevAttrType()
    */
   public int sizeOfRevAttrType()
   {
      return ((this.revAttrType == null) ? 0 : this.revAttrType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#addToRevAttrType(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
    */
   public boolean addToRevAttrType(FAttr value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revAttrType == null)
         {
            this.revAttrType = new FPropHashSet(this, REV_ATTR_TYPE_PROPERTY);
         }
         changed = this.revAttrType.add(value);
         if (changed)
         {
            value.setAttrType(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#removeFromRevAttrType(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
    */
   public boolean removeFromRevAttrType(FAttr value)
   {
      boolean changed = false;
      if ((this.revAttrType != null) && (value != null))
      {
         changed = this.revAttrType.remove(value);
         if (changed)
         {
            value.setAttrType((UMLType) null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#removeAllFromRevAttrType()
    */
   public void removeAllFromRevAttrType()
   {
      UMLAttr tmpValue;
      Iterator iter = this.iteratorOfRevAttrType();
      while (iter.hasNext())
      {
         tmpValue = (UMLAttr) iter.next();
         this.removeFromRevAttrType(tmpValue);
      }
   }

   private transient FPropHashSet revType;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#hasInRevType(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean hasInRevType(FQualifier value)
   {
      return ((this.revType != null) && (value != null) && this.revType
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#iteratorOfRevType()
    */
   public Iterator iteratorOfRevType()
   {
      return ((this.revType == null) ? FEmptyIterator.get() : this.revType
            .iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#sizeOfRevType()
    */
   public int sizeOfRevType()
   {
      return ((this.revType == null) ? 0 : this.revType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#addToRevType(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean addToRevType(FQualifier value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revType == null)
         {
            this.revType = new FPropHashSet(this, REV_TYPE_PROPERTY);
         }
         changed = this.revType.add(value);
         if (changed)
         {
            value.setType(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#removeFromRevType(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean removeFromRevType(FQualifier value)
   {
      boolean changed = false;
      if ((this.revType != null) && (value != null))
      {
         changed = this.revType.remove(value);
         if (changed)
         {
            value.setType(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#removeAllFromRevType()
    */
   public void removeAllFromRevType()
   {
      UMLQualifier tmpValue;
      Iterator iter = this.iteratorOfRevType();
      while (iter.hasNext())
      {
         tmpValue = (UMLQualifier) iter.next();
         this.removeFromRevType(tmpValue);
      }
   }


   private transient FPropHashSet revResultType;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#hasInRevResultType(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean hasInRevResultType(FMethod value)
   {
      return ((this.revResultType != null) && (value != null) && this.revResultType
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#iteratorOfRevResultType()
    */
   public Iterator iteratorOfRevResultType()
   {
      return ((this.revResultType == null) ? FEmptyIterator.get()
            : this.revResultType.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#sizeOfRevResultType()
    */
   public int sizeOfRevResultType()
   {
      return ((this.revResultType == null) ? 0 : this.revResultType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#addToRevResultType(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean addToRevResultType(FMethod value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revResultType == null)
         {
            this.revResultType = new FPropHashSet(this,
                  REV_RESULT_TYPE_PROPERTY);
         }
         changed = this.revResultType.add(value);
         if (changed)
         {
            value.setResultType(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#removeFromRevResultType(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
    */
   public boolean removeFromRevResultType(FMethod value)
   {
      boolean changed = false;
      if ((this.revResultType != null) && (value != null))
      {
         changed = this.revResultType.remove(value);
         if (changed)
         {
            value.setResultType(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#removeAllFromRevResultType()
    */
   public void removeAllFromRevResultType()
   {
      UMLMethod tmpValue;
      Iterator iter = this.iteratorOfRevResultType();
      while (iter.hasNext())
      {
         tmpValue = (UMLMethod) iter.next();
         this.removeFromRevResultType(tmpValue);
      }
   }


   private transient FPropHashSet revParamType;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#hasInRevParamType(de.uni_paderborn.fujaba.metamodel.structure.FParam)
    */
   public boolean hasInRevParamType(FParam value)
   {
      return ((this.revParamType != null) && (value != null) && this.revParamType
            .contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#iteratorOfRevParamType()
    */
   public Iterator iteratorOfRevParamType()
   {
      return ((this.revParamType == null) ? FEmptyIterator.get()
            : this.revParamType.iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#sizeOfRevParamType()
    */
   public int sizeOfRevParamType()
   {
      return ((this.revParamType == null) ? 0 : this.revParamType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#addToRevParamType(de.uni_paderborn.fujaba.metamodel.structure.FParam)
    */
   public boolean addToRevParamType(FParam value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.revParamType == null)
         {
            this.revParamType = new FPropHashSet(this, REV_PARAM_TYPE_PROPERTY);
         }
         changed = this.revParamType.add(value);
         if (changed)
         {
            value.setParamType(this);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#removeFromRevParamType(de.uni_paderborn.fujaba.metamodel.structure.FParam)
    */
   public boolean removeFromRevParamType(FParam value)
   {
      boolean changed = false;
      if ((this.revParamType != null) && (value != null))
      {
         changed = this.revParamType.remove(value);
         if (changed)
         {
            value.setParamType(null);
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#removeAllFromRevParamType()
    */
   public void removeAllFromRevParamType()
   {
      UMLParam tmpValue;
      Iterator iter = this.iteratorOfRevParamType();
      while (iter.hasNext())
      {
         tmpValue = (UMLParam) iter.next();
         this.removeFromRevParamType(tmpValue);
      }
   }


   private UMLArray revArrayType;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#getRevArrayType()
    */
   public UMLArray getRevArrayType()
   {
      return this.revArrayType;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FBaseType#setRevArrayType(de.uni_paderborn.fujaba.metamodel.structure.FArray)
    */
   public void setRevArrayType(FArray revArrayType)
   {
      if (this.revArrayType != revArrayType)
      {
         // new partner
         UMLArray oldRevArrayType = this.revArrayType;
         if (this.revArrayType != null)
         {
            // inform old partner
            this.revArrayType = null;
            oldRevArrayType.setArrayType(this);
         }
         this.revArrayType = (UMLArray) revArrayType;
         if (revArrayType != null)
         {
            // inform new partner
            revArrayType.setArrayType(this);
         }
         firePropertyChange(REV_ARRAY_TYPE_PROPERTY, oldRevArrayType,
               revArrayType);
      }
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
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
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return getName();
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      removeAllFromRevAttrType();
      removeAllFromRevType();
      removeAllFromRevResultType();
      removeAllFromRevParamType();

      setRevArrayType(null);
      
      // remove this object from products, if possible
      FFactory<?> factory = getProject().getFromFactories(UMLBaseType.class);
      if (factory instanceof UMLBaseTypeFactory)
      {
         UMLBaseTypeFactory basetypeFactory = (UMLBaseTypeFactory) factory;
         basetypeFactory.removeFromProducts(this);
      }

      super.removeYou();
   }


   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      return getName();
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

/*
 * $Log$
 * Revision 1.16  2007/03/23 12:45:06  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.15  2007/03/21 13:40:40  cschneid
 * - tree displays assocs, roles and qualifiers
 * - objects may be created even if their type is an interface
 * - base types and array clean up their factory on delete
 * - some NPEs prevented
 *
 * Revision 1.14  2006/07/06 12:25:34  rotschke
 * Changed visibility to protected to allow sub classes to call super constructor [tr].
 *
 * Revision 1.13  2006/04/27 11:07:06  creckord
 * From F4: Changed TreeSets to HashSets - sorted property is not needed and breaks with removeYou() of associated objects (because the compareTo() value changes)
 *
 * Revision 1.12  2006/04/25 11:58:26  cschneid
 * added deprecation expiration note, work on versioning
 *
 * Revision 1.11  2006/04/06 12:06:11  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.10  2006/03/16 22:11:57  lowende
 * Property astRootNode readded to FMethod and moved from FMethodUtility back to UMLMethod.
 * Parser interface changed.
 *
 */

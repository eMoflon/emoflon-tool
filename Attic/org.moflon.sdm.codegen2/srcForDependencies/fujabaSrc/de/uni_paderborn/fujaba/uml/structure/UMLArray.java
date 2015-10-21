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

import de.uni_paderborn.fujaba.basic.RuntimeExceptionWithContext;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FArray;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FParam;
import de.uni_paderborn.fujaba.metamodel.structure.FQualifier;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.metamodel.structure.util.FArrayUtility;
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
public class UMLArray extends UMLIncrement implements UMLType, FArray
{
   /**
    * Don't call directly use appropriate Factory instead.
    * 
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory
    */
   protected UMLArray(FProject project, boolean persistent, String factoryKey)
   {
      super(project, persistent, factoryKey);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#getProgLangType()
    */
   public String getProgLangType()
   {
      StringBuffer buf;
      buf = new StringBuffer(this.getArrayType().getProgLangType());
      buf.append("[]");
      return new String(buf);
   }


   public void setProgLangType(String progLangType)
   {
      // this.setName (progLangType);
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
    * Get the full qualified name of this array.
    * 
    * @return A String holding the full qualified name of this array.
    */
   public String getFullArrayName()
   {
      try
      {
         return FArrayUtility.getFullQualifiedName(this);
      } catch (Exception e)
      {
         return null;
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getName()
    */
   @Override
   public String getName()
   {
      StringBuffer buf;
      UMLType arrayType = this.getArrayType();
      if (arrayType == null || arrayType.getName() == null)
      {
         // TODO: what to return, if arrayType isn't set?
         // should we throw an exception?
         buf = new StringBuffer("null");
      }
      else
      {
         buf = new StringBuffer(arrayType.getName());
      }

      buf.append("[]");
      return new String(buf);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#setName(java.lang.String)
    */
   @Override
   public void setName(String name)
   {
   }


   private transient FPropHashSet revResultType;


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#hasInRevResultType(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#sizeOfRevResultType()
    */
   public int sizeOfRevResultType()
   {
      return ((this.revResultType == null) ? 0 : this.revResultType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#addToRevResultType(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeFromRevResultType(de.uni_paderborn.fujaba.metamodel.structure.FMethod)
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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeAllFromRevResultType()
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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#hasInRevParamType(de.uni_paderborn.fujaba.metamodel.structure.FParam)
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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#sizeOfRevParamType()
    */
   public int sizeOfRevParamType()
   {
      return ((this.revParamType == null) ? 0 : this.revParamType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#addToRevParamType(de.uni_paderborn.fujaba.metamodel.structure.FParam)
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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeFromRevParamType(de.uni_paderborn.fujaba.metamodel.structure.FParam)
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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeAllFromRevParamType()
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

   private transient FPropHashSet revAttrType;


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#hasInRevAttrType(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#sizeOfRevAttrType()
    */
   public int sizeOfRevAttrType()
   {
      return ((this.revAttrType == null) ? 0 : this.revAttrType.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#addToRevAttrType(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeFromRevAttrType(de.uni_paderborn.fujaba.metamodel.structure.FAttr)
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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeAllFromRevAttrType()
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

   private UMLArray revArrayType;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FType#getRevArrayType()
    */
   public UMLArray getRevArrayType()
   {
      return this.revArrayType;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#setRevArrayType(de.uni_paderborn.fujaba.metamodel.structure.FArray)
    */
   public void setRevArrayType(FArray revArrayType)
   {
      if (this.revArrayType != revArrayType)
      {
         UMLArray oldRevArrayType = this.revArrayType;
         // new partner
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
    * 0..1 has 0..1 UMLArray ------------------------------------- UMLType + revArrayType +
    * arrayType
    */

   // @@@FIXME: this should be removed, if we could save
   // interfaces, JHM
   // private UMLType arrayType; // reverse attribute array
   private UMLIncrement arrayType; // reverse attribute array


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FArray#getArrayType()
    */
   public UMLType getArrayType()
   {
      return (UMLType) this.arrayType;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FArray#getBaseType()
    */
   public UMLType getBaseType()
   {
      UMLType baseType = null;
      UMLArray current = this;

      while (current != null)
      {
         baseType = current.getArrayType();
         if (baseType instanceof UMLArray)
         {
            current = (UMLArray) baseType;
         }
         else
         {
            current = null;
         }
      }

      return baseType;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.structure.FArray#setArrayType(de.uni_paderborn.fujaba.metamodel.structure.FType)
    */
   public void setArrayType(FType tmpType)
   {
      UMLIncrement type = (UMLIncrement) tmpType;
      if (this.arrayType != type)
      {
         UMLIncrement oldType = this.arrayType;
         // newPartner
         if (this.arrayType != null)
         {
            // inform old partner
            // UMLType oldType = this.arrayType;
            this.arrayType = null;

            ((UMLType) oldType).setRevArrayType(null);
         }

         this.arrayType = type;
         if (this.arrayType != null)
         {
            // inform new partner
            ((UMLType) this.arrayType).setRevArrayType(this);
         }

         firePropertyChange(ARRAY_TYPE_PROPERTY, oldType, type);
      }
   } // setArrayType


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
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#addToRevType(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean addToRevType(FQualifier value)
   {
      throw new RuntimeExceptionWithContext(
            "Use an array as Qualifier?! That seems to be rubbish to me!",
            value);
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#hasInRevType(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean hasInRevType(FQualifier value)
   {
      return false;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#iteratorOfRevType()
    */
   public Iterator iteratorOfRevType()
   {
      return FEmptyIterator.get();
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeAllFromRevType()
    */
   public void removeAllFromRevType()
   {
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#removeFromRevType(de.uni_paderborn.fujaba.metamodel.structure.FQualifier)
    */
   public boolean removeFromRevType(FQualifier value)
   {
      return false;
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.structure.UMLType#sizeOfRevType()
    */
   public int sizeOfRevType()
   {
      return 0;
   }


   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return this.getName();
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      removeAllFromRevParamType();
      removeAllFromRevResultType();
      removeAllFromRevAttrType();

      this.setRevArrayType(null);
      this.setArrayType(null);

      ((UMLArrayFactory)getProject().getFromFactories(UMLArray.class)).removeFromProducts( this );

      super.removeYou();
   }

}

/*
 * $Log$
 * Revision 1.12  2007/03/21 13:40:40  cschneid
 * - tree displays assocs, roles and qualifiers
 * - objects may be created even if their type is an interface
 * - base types and array clean up their factory on delete
 * - some NPEs prevented
 *
 * Revision 1.11  2006/04/06 12:06:11  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.10  2006/03/08 14:57:38  lowende
 * Removed FClass.elementsOfRevSubclasses and FClass.elementsOfRevSuperclasses - use iteratorOf... instead.
 * Moved constants from UMLType to FType.
 * Added FClass.findClass and FClassUtility.findClass.
 * Added some tests and a test suite.
 *
 */

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


import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashMap;
import de.upb.tools.fca.FPropHashMap;

import java.util.Iterator;
import java.util.Map;


/**
 * Stores information about an UnparseInterface of an ASGElement
 * 
 * <h2>Associations</h2>
 * 
 * <pre>
 *                       -------- 0..1   ASGInformation   0..1
 * ASGUnparseInformation | name |------------------------------ ASGInformation
 *                       -------- parent        aSGInformation
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ASGUnparseInformation extends ASGElement
{
   /**
    * Property name of the 'aSGElement' attribute.
    */
   public static final String ASGELEMENT_PROPERTY = "aSGElement";
   public static final String PROPERTY_ASG_INFORMATION = "aSGInformation";

   /**
    * default ctor
    * 
    * @param project
    * @param persistent
    */
   public ASGUnparseInformation(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   /**
    * copy ctor
    * 
    * @param toCopy what to copy
    */
   public ASGUnparseInformation(ASGUnparseInformation toCopy)
   {
      this(toCopy.getProject(), !isInTransientMode());
      for (Iterator it = toCopy.entriesOfASGInformation(); it.hasNext();)
      {
         Map.Entry entry = (Map.Entry) it.next();
         addToASGInformation((String) entry.getKey(), new ASGInformation(
               (ASGInformation) entry.getValue()));
      }
   }


   /**
    * @see #getASGElement
    */
   @Property(name=ASGELEMENT_PROPERTY, partner=ASGElement.UNPARSE_INFORMATIONS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   private transient ASGElement aSGElement;


   /**
    * assign an ASGElement to this information
    * 
    * @param key used to assign this information to the ASGElement
    * @param value ASGElement
    * @return true when value was changed
    */
   public boolean setASGElement(ASGElement key, ASGElement value)
   {
      boolean changed = false;
      if (this.aSGElement != value)
      {
         ASGElement oldValue = this.aSGElement;
         if (oldValue != null)
         {
            this.aSGElement = null;
            oldValue.removeFromUnparseInformations(this.aSGElementKey, this);
         }

         this.aSGElement = value;
         this.aSGElementKey = key;
         firePropertyChange(ASGELEMENT_PROPERTY, oldValue, value);
         if (value != null)
         {
            value.addToUnparseInformations(key, this);
         }
         changed = true;
      }
      return changed;
   }


   /**
    * used for loading/redo (do not use directly)
    * 
    * @param value new assigned ASGElement
    * @return true when something was changed
    */
   public boolean setASGElement(ASGElement value)
   {
      if (aSGElementKey != null)
      {
         return setASGElement(aSGElementKey, value);
      }
      else
      {
         return false;
      }
   }


   /**
    * @return assigned ASGElement
    */
   public ASGElement getASGElement()
   {
      return this.aSGElement;
   }


   /**
    * @see #getASGElementKey
    */
   private transient ASGElement aSGElementKey;


   /**
    * @return the key which is used to assign this information to the ASGElement
    */
   public ASGElement getASGElementKey()
   {
      return aSGElementKey;
   }


   /**
    * <pre>
    *                       -------- 0..1   ASGInformation   0..1
    * ASGUnparseInformation | name |------------------------------ ASGInformation
    *                       -------- parent        aSGInformation
    * </pre>
    */
   @Property( name = PROPERTY_ASG_INFORMATION, kind = ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE,
      adornment = ReferenceHandler.Adornment.COMPOSITION, partner = ASGInformation.PARENT_PROPERTY,
      accessFragment = AccessFragment.FIELD_STORAGE )
   private FHashMap aSGInformation;


   public boolean addToASGInformation(String key, ASGInformation value)
   {
      boolean changed = false;
      if ((value != null) && (key != null))
      {
         if (this.aSGInformation == null)
         {
            this.aSGInformation = new FPropHashMap(this, PROPERTY_ASG_INFORMATION);
         }
         ASGInformation oldValue = (ASGInformation) this.aSGInformation.put(
               key, value);
         if (oldValue != value)
         {
            if (oldValue != null)
            {
               oldValue.setParent(null, null);
            }
            value.setParent(key, this);
            changed = true;
         }
      }
      return changed;
   }


   public boolean addToASGInformation(Map.Entry entry)
   {
      return addToASGInformation((String) entry.getKey(),
            (ASGInformation) entry.getValue());
   }


   public Iterator entriesOfASGInformation()
   {
      return ((this.aSGInformation == null) ? FEmptyIterator.get()
            : this.aSGInformation.entrySet().iterator());
   }


   public ASGInformation getFromASGInformation(String key)
   {
      return (((this.aSGInformation == null) || (key == null)) ? null
            : (ASGInformation) this.aSGInformation.get(key));
   }


   public boolean hasInASGInformation(ASGInformation value)
   {
      return ((this.aSGInformation != null) && (value != null) && this.aSGInformation
            .containsValue(value));
   }


   public boolean hasInASGInformation(String key, ASGInformation value)
   {
      return ((this.aSGInformation != null) && (value != null) && (key != null) && (this.aSGInformation
            .get(key) == value));
   }


   public boolean hasKeyInASGInformation(String key)
   {
      return ((this.aSGInformation != null) && (key != null) && this.aSGInformation
            .containsKey(key));
   }


   public Iterator iteratorOfASGInformation()
   {
      return ((this.aSGInformation == null) ? FEmptyIterator.get()
            : this.aSGInformation.values().iterator());
   }


   public Iterator keysOfASGInformation()
   {
      return ((this.aSGInformation == null) ? FEmptyIterator.get()
            : this.aSGInformation.keySet().iterator());
   }


   /**
    * Remove information from the map without calling removeYou() on the objects.
    */
   public void removeAllFromASGInformation()
   {
      Iterator iter = entriesOfASGInformation();
      Map.Entry entry;
      while (iter.hasNext())
      {
         entry = (Map.Entry) iter.next();
         removeFromASGInformation((String) entry.getKey(),
               (ASGInformation) entry.getValue());
      }
   }


   /**
    * Remove information from the map and call removeYou() on each object.
    */
   public void removeAllFromASGInformationAndRemoveThem()
   {
      Iterator iter = entriesOfASGInformation();
      Map.Entry entry;
      while (iter.hasNext())
      {
         entry = (Map.Entry) iter.next();
         ASGInformation information = (ASGInformation) entry.getValue();
         removeFromASGInformation((String) entry.getKey(), information);
         information.removeYou();
      }
   }


   public boolean removeFromASGInformation(ASGInformation value)
   {
      boolean changed = false;
      if ((this.aSGInformation != null) && (value != null))
      {
         Iterator iter = this.entriesOfASGInformation();
         Map.Entry entry;
         while (iter.hasNext())
         {
            entry = (Map.Entry) iter.next();
            if (entry.getValue() == value)
            {
               changed = changed
                     || this.removeFromASGInformation((String) entry.getKey(),
                           value);
            }
         }
      }
      return changed;
   }


   public boolean removeFromASGInformation(String key, ASGInformation value)
   {
      boolean changed = false;
      if ((this.aSGInformation != null) && (value != null) && (key != null))
      {
         ASGInformation oldValue = (ASGInformation) this.aSGInformation
               .get(key);
         if (oldValue == value)
         {
            this.aSGInformation.remove(key);
            value.setParent(null, null);
            changed = true;
         }
      }
      return changed;
   }


   public boolean removeKeyFromASGInformation(String key)
   {
      boolean changed = false;
      if ((this.aSGInformation != null) && (key != null))
      {
         ASGInformation tmpValue = (ASGInformation) this.aSGInformation
               .get(key);
         if (tmpValue != null)
         {
            this.aSGInformation.remove(key);
            tmpValue.setParent(null, null);
            changed = true;
         }
      }
      return changed;
   }


   public int sizeOfASGInformation()
   {
      return ((this.aSGInformation == null) ? 0 : this.aSGInformation.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      ASGElement tmpASGElement = getASGElement();
      if (tmpASGElement != null)
      {
         setASGElement(null, null);
      } // if

      removeAllFromASGInformationAndRemoveThem();
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
      return getASGElement();
   }
}

/*
 * $Log$
 * Revision 1.22  2007/03/23 16:12:28  l3_g5
 * improved copy paste
 *
 * Revision 1.21  2007/03/23 14:04:05  cschneid
 * some copy improvements
 *
 * Revision 1.20  2006/10/31 17:06:29  cschneid
 * using some generics and unboxing, comments etc.
 *
 * Revision 1.19  2006/04/06 12:04:33  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.18  2006/03/02 09:39:19  lowende
 * Removed some deprecated stuff.
 *
 */

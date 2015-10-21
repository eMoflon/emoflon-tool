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
 * <h2>Associations</h2>
 * 
 * <pre>
 *                -------           information      0..1
 * ASGInformation | key |---------------------------------> String
 *                -------                     information
 *
 *                       -------- 0..1   aSGInformation   0..1
 * ASGUnparseInformation | name |------------------------------ ASGInformation
 *                       -------- parent        aSGinformation
 * </pre>
 * 
 * @author trioptimum
 * @author Last editor: $Author$
 * @version $Revision$ $Date$
 */
public class ASGInformation extends ASGElement
{

   public ASGInformation(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   /**
    * Copies properties of given ASGInformation object to newly created object.
    * 
    * @param toCopy the ASGInformation object to copy from
    */
   public ASGInformation(ASGInformation toCopy)
   {
      this(toCopy.getProject(), toCopy.isPersistent());
      for (Iterator it = toCopy.entriesOfInformation(); it.hasNext();)
      {
         Map.Entry entry = (Map.Entry) it.next();
         addToInformation((String) entry.getKey(), (String) entry.getValue());
      }
   }


   /**
    * <pre>
    *                -------           information      0..1
    * ASGInformation | key |---------------------------------> String
    *                -------                     information
    * </pre>
    */
   @Property( name = INFORMATION_PROPERTY, kind = ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE,
         adornment = ReferenceHandler.Adornment.COMPOSITION,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private FHashMap information;

   public static final String INFORMATION_PROPERTY = "information";


   public boolean addToInformation(String key, String value)
   {
      boolean changed = false;
      if ((value != null) && (key != null))
      {
         if (this.information == null)
         {
            this.information = new FPropHashMap(this, INFORMATION_PROPERTY);
         }
         String oldValue = (String) this.information.put(key, value);
         if (!value.equals(oldValue))
         {
            changed = true;
         }
      }
      return changed;
   }


   public Iterator entriesOfInformation()
   {
      return ((this.information == null) ? FEmptyIterator.get()
            : this.information.entrySet().iterator());
   }


   public String getFromInformation(String key)
   {
      return (((this.information == null) || (key == null)) ? null
            : (String) this.information.get(key));
   }


   public boolean hasInInformation(String key, String value)
   {
      return ((this.information != null) && (value != null) && (key != null) && (value.equals(this.information
            .get(key))));
   }


   public boolean hasInInformation(String value)
   {
      return ((this.information != null) && (value != null) && this.information
            .containsValue(value));
   }


   public boolean hasKeyInInformation(String key)
   {
      return ((this.information != null) && (key != null) && this.information
            .containsKey(key));
   }


   public Iterator iteratorOfInformation()
   {
      return ((this.information == null) ? FEmptyIterator.get()
            : this.information.values().iterator());
   }


   public Iterator keysOfInformation()
   {
      return ((this.information == null) ? FEmptyIterator.get()
            : this.information.keySet().iterator());
   }


   public void removeAllFromInformation()
   {
      Iterator iter = entriesOfInformation();
      Map.Entry entry;
      while (iter.hasNext())
      {
         entry = (Map.Entry) iter.next();
         removeFromInformation((String) entry.getKey(), (String) entry
               .getValue());
      }
   }


   public boolean removeFromInformation(String key, String value)
   {
      boolean changed = false;
      if ((this.information != null) && (value != null) && (key != null))
      {
         String oldValue = (String) this.information.get(key);
         if (value.equals(oldValue))
         {
            this.information.remove(key);
            changed = true;
         }
      }
      return changed;
   }


   public boolean removeFromInformation(String value)
   {
      boolean changed = false;
      if ((this.information != null) && (value != null))
      {
         Iterator iter = this.entriesOfInformation();
         Map.Entry entry;
         while (iter.hasNext())
         {
            entry = (Map.Entry) iter.next();
            if (value.equals(entry.getValue()))
            {
               changed = changed
                     || this.removeFromInformation((String) entry.getKey(),
                           value);
            }
         }
      }
      return changed;
   }


   public boolean removeKeyFromInformation(String key)
   {
      boolean changed = false;
      if ((this.information != null) && (key != null))
      {
         String tmpValue = (String) this.information.get(key);
         if (tmpValue != null)
         {
            this.information.remove(key);
            changed = true;
         }
      }
      return changed;
   }


   public int sizeOfInformation()
   {
      return ((this.information == null) ? 0 : this.information.size());
   }


   /**
    * <pre>
    *                       -------- 0..1   ASGInformation   0..1
    * ASGUnparseInformation | name |------------------------------ ASGInformation
    *                       -------- parent        aSGinformation
    * </pre>
    */
   @Property( name = PARENT_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.PARENT, partner = ASGUnparseInformation.PROPERTY_ASG_INFORMATION,
         accessFragment = AccessFragment.FIELD_STORAGE )
   private transient ASGUnparseInformation parent;


   public ASGUnparseInformation getParent()
   {
      return this.parent;
   }

   /**
    * Property name of attribute 'parent'.
    */
   public static final String PARENT_PROPERTY = "parent";

   public boolean setParent(String key, ASGUnparseInformation value)
   {
      boolean changed = false;
      if (this.parent != value)
      {
         if (this.parent != null)
         {
            ASGUnparseInformation oldValue = this.parent;
            this.parent = null;

            // key is null (removeYou)
            if (key == null)
            {
               oldValue.removeFromASGInformation(this);
            }
         }
         this.parent = value;
         if (value != null)
         {
            value.addToASGInformation(key, this);
         }
         changed = true;
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      return getParent();
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      removeAllFromInformation();

      ASGUnparseInformation tmpParent = getParent();
      if (tmpParent != null)
      {
         setParent(null, null);
      }
      super.removeYou();
   }

}

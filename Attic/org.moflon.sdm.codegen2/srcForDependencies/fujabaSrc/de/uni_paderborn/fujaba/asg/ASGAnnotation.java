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


import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import de.uni_paderborn.fujaba.metamodel.common.FAnnotation;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.upb.tools.fca.FCollections;
import de.upb.tools.fca.FDuplicatedHashMap;
import de.upb.tools.fca.FEmptyIterator;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *               ------- 0..n   Annotations   0..n
 * ASGAnnotation | key |--------------------------- ASGElement
 *               ------- annotations      elements
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$
 */
public abstract class ASGAnnotation extends ASGElement implements FAnnotation
{

   private String name;


   public ASGAnnotation(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   /**
    * @see de.uni_paderborn.fujaba.asg.ASGElement#getName()
    */
   @Override
   public String getName()
   {
      return this.name;
   }


   /**
    * @see de.uni_paderborn.fujaba.asg.ASGElement#setName(java.lang.String)
    */
   @Override
   public void setName(String name)
   {
      if (name == null || !name.equals(this.name))
      {
         String oldValue = this.name;
         this.name = name;
         firePropertyChange(NAME_PROPERTY, oldValue, this.name);
      }
   }


   /**
    * <pre>
    *               ------- 0..n   Annotations   0..n
    * ASGAnnotation | key |--------------------------- ASGElement
    *               ------- annotations      elements
    * </pre>
    */
   private FDuplicatedHashMap elements;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#addToElements(java.lang.String,
    *      de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public boolean addToElements(String key, FElement value)
   {
      boolean changed = false;
      if ((value != null) && (key != null))
      {
         if (this.elements == null)
         {
            this.elements = new FDuplicatedHashMap();
         }
         ASGElement oldValue = (ASGElement) this.elements.put(key, value);
         if (oldValue != value)
         {
            if (oldValue != null)
            {
               oldValue.removeFromAnnotations(key, this);
            }
            value.addToAnnotations(key, this);
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#addToElements(java.util.Map.Entry)
    */
   public boolean addToElements(Map.Entry entry)
   {
      return addToElements((String) entry.getKey(), (ASGElement) entry
            .getValue());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#iteratorOfElements(java.lang.String)
    */
   public Iterator iteratorOfElements(String key)
   {
      return ((this.elements == null) ? FEmptyIterator.get() : FCollections
            .iterator(this.elements.values(key)));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#iteratorOfElements()
    */
   public Iterator iteratorOfElements()
   {
      return ((this.elements == null) ? FEmptyIterator.get() : this.elements
            .values().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#hasKeyInElements(java.lang.String)
    */
   public boolean hasKeyInElements(String key)
   {
      return ((this.elements != null) && (key != null) && this.elements
            .containsKey(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#hasInElements(java.lang.String,
    *      de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public boolean hasInElements(String key, FElement value)
   {
      return ((this.elements != null) && (value != null) && (key != null) && this.elements
            .containsEntry(key, value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#hasInElements(de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public boolean hasInElements(FElement value)
   {
      return ((this.elements != null) && (value != null) && this.elements
            .containsValue(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#keysOfElements()
    */
   public Iterator keysOfElements()
   {
      return ((this.elements == null) ? FEmptyIterator.get() : this.elements
            .keySet().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#entriesOfElements()
    */
   public Iterator entriesOfElements()
   {
      return ((this.elements == null) ? FEmptyIterator.get() : this.elements
            .entrySet().iterator());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#sizeOfElements(java.lang.String)
    */
   public int sizeOfElements(String key)
   {
      return ((this.elements == null) ? 0 : this.elements.size(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#sizeOfElements()
    */
   public int sizeOfElements()
   {
      return ((this.elements == null) ? 0 : this.elements.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#removeFromElements(java.lang.String,
    *      de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public boolean removeFromElements(String key, FElement value)
   {
      boolean changed = false;
      if ((this.elements != null) && (value != null) && (key != null))
      {

         ASGElement oldValue = (ASGElement) this.elements.remove(key, value);
         if (oldValue != null)
         {
            value.removeFromAnnotations(key, this);
         }
         changed = true;
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#removeFromElements(java.lang.String)
    */
   public boolean removeFromElements(String key)
   {
      boolean changed = false;
      if ((this.elements != null) && (key != null))
      {
         Collection tmpCol = (Collection) this.elements.remove(key);
         if (tmpCol != null)
         {
            ASGElement tmpValue;
            Iterator iter = tmpCol.iterator();
            while (iter.hasNext())
            {
               tmpValue = (ASGElement) iter.next();
               tmpValue.removeFromAnnotations(key, this);
            }
            changed = true;
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#removeFromElements(de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public boolean removeFromElements(FElement value)
   {
      boolean changed = false;
      if ((this.elements != null) && (value != null))
      {
         Iterator iter = this.entriesOfElements();
         Map.Entry entry;
         while (iter.hasNext())
         {
            entry = (Map.Entry) iter.next();
            if (entry.getValue() == value)
            {
               changed = changed
                     || this.removeFromElements((String) entry.getKey(), value);
            }
         }
      }
      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FAnnotation#removeAllFromElements()
    */
   public void removeAllFromElements()
   {
      Iterator iter = entriesOfElements();
      Map.Entry entry;
      while (iter.hasNext())
      {
         entry = (Map.Entry) iter.next();
         removeFromElements((String) entry.getKey(), (ASGElement) entry
               .getValue());
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.asg.ASGElement#removeYou()
    */
   @Override
   public void removeYou()
   {
      removeAllFromElements();

      super.removeYou();
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    * 
    * @return the logical parent of this element;
    * @see de.uni_paderborn.fujaba.asg.ASGElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      return this.getProject();
   }

}

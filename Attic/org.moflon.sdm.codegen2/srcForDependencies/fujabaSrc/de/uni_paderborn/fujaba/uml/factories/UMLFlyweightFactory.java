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
package de.uni_paderborn.fujaba.uml.factories;


import java.util.Iterator;
import java.util.Map;

import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FPropHashMap;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *          -------- 0..1   products   0..n
 * FFactory | name |------------------------> FElement
 *          --------               products
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
public abstract class UMLFlyweightFactory<I extends FElement> extends
      UMLAdvancedFactory<I>
{

   public UMLFlyweightFactory(FProject project)
   {
      super(project);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProducts(java.lang.String)
    */
   public I getFromProducts(String key)
   {
      return getFromProducts (key, !ASGElement.isInTransientMode());
   }


   abstract public I getFromProducts(String key, boolean persistent);


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#create(boolean)
    */
   public final I create(boolean persistent)
   {
      throw new UnsupportedOperationException(
            "The client must use getFromProducts(String key) on a flyweight factory.");
   }


   /**
    * <pre>
    *          -------- 0..1   products   0..n
    * FFactory | name |------------------------> FElement
    *          --------               products
    * </pre>
    */
   protected Map<String, I> products;


   public final String PRODUCTS_PROPERTY = "products";


   public final boolean addToProducts(Map.Entry<String, I> entry)
   {
      return addToProducts(entry.getKey(), entry.getValue());
   }


   public final boolean addToProducts(String key, I value)
   {
      boolean changed = false;
      if (key != null)
      {
         if (this.products == null)
         {
            this.products = new FPropHashMap<String, I>(this, PRODUCTS_PROPERTY);
         }
         I oldValue = this.products.put(key, value);
         if (oldValue != value)
         {
            changed = true;
         }
      }
      return changed;
   }


   public final Iterator<Map.Entry<String, I>> entriesOfProducts()
   {
      return ((this.products == null) ? FEmptyIterator.<Map.Entry<String, I>>get() : this.products
            .entrySet().iterator());
   }


   public final boolean hasInProducts(String key, I value)
   {
      return ((this.products != null)
            && (value != null || this.products.containsKey(key))
            && (key != null) && (this.products.get(key) == value));
   }


   public final boolean hasInProducts(I value)
   {
      return ((this.products != null) && this.products.containsValue(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#hasKeyInProducts(java.lang.String)
    */
   public final boolean hasKeyInProducts(String key)
   {
      return ((this.products != null) && (key != null) && this.products
            .containsKey(key));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#iteratorOfProducts()
    */
   public final Iterator<I> iteratorOfProducts()
   {
      if (this.products == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.products.values().iterator();
      }
   }


   public final Iterator<String> keysOfProducts()
   {
      return ((this.products == null) ? FEmptyIterator.<String>get() : this.products
            .keySet().iterator());
   }


   public final void removeAllFromProducts()
   {
      Iterator<Map.Entry<String, I>> iter = entriesOfProducts();
      Map.Entry<String, I> entry;
      while (iter.hasNext())
      {
         entry = iter.next();
         removeFromProducts(entry.getKey(), entry.getValue());
      }
   }


   public final boolean removeFromProducts(String key, I value)
   {
      boolean changed = false;
      if ((this.products != null) && (key != null))
      {
         I oldValue = this.products.get(key);
         if (oldValue == value
               && (oldValue != null || this.products.containsKey(key)))
         {
            this.products.remove(key);
            changed = true;
         }
      }
      return changed;
   }


   public final boolean removeFromProducts(I value)
   {
      boolean changed = false;
      if (this.products != null)
      {
         Iterator<Map.Entry<String, I>> iter = this.entriesOfProducts();
         Map.Entry<String, I> entry;
         while (iter.hasNext())
         {
            entry = iter.next();
            if (entry.getValue() == value)
            {
               if (this.removeFromProducts(entry.getKey(), value))
               {
                  changed = true;
               }
            }
         }
      }
      return changed;
   }


   public final boolean removeKeyFromProducts(String key)
   {
      boolean changed = false;
      if ((this.products != null) && (key != null))
      {
         changed = this.products.containsKey(key);
         if (changed)
         {
            this.products.remove(key);
         }
      }
      return changed;
   }


   public final int sizeOfProducts()
   {
      return ((this.products == null) ? 0 : this.products.size());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProductsOwnProjectOnly(java.lang.String)
    */
   public I getFromProductsOwnProjectOnly(String key)
   {
      return this.getFromProducts(key);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#hasKeyInProductsOwnProjectOnly(java.lang.String)
    */
   public boolean hasKeyInProductsOwnProjectOnly(String key)
   {
      return this.hasKeyInProducts(key);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#iteratorOfProductsOwnProjectOnly()
    */
   public Iterator<I> iteratorOfProductsOwnProjectOnly()
   {
      return this.iteratorOfProducts();
   }
   
   /**
    * @see de.uni_paderborn.fujaba.uml.factories.UMLFactory#removeYou()
    */
   @Override
   public void removeYou()
   {
      Iterator<I> i = this.iteratorOfProductsOwnProjectOnly();
      while(i.hasNext())
      {
         i.next().removeYou();
      }
      super.removeYou();
   }

}

/*
 * $Log$
 * Revision 1.13  2006/12/22 15:05:28  mm
 * changed upper bound of type parameter wildcard to FElement
 *
 * Revision 1.12  2006/12/01 13:02:09  l3_g5
 * improved support for non persistent object creation
 *
 * Revision 1.11  2006/05/08 13:32:48  lowende
 * Some comments added.
 *
 * Revision 1.10  2006/04/20 11:16:35  fklar
 * generalized constructor so it takes a 'FProject' instead of an 'UMLProject'
 *
 * Revision 1.9  2006/02/27 13:46:51  lowende
 * Refactored storing of factories in project.
 *
 */

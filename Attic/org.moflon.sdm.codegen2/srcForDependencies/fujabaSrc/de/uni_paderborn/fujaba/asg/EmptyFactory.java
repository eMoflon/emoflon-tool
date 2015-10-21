/*
 * The FUJABA ToolSuite project:
 *
 * FUJABA is the acronym for 'From Uml to Java And Back Again'
 * and originally aims to provide an environment for round-trip
 * engineering using UML as visual programming language. During
 * the last years, the environment has become a base for several
 * research activities, e.g. distributed software, database
 * systems, modelling mechanical and electrical systems and
 * their simulation. Thus, the environment has become a project,
 * where this source code is part of. Further details are avail-
 * able via http://www.fujaba.de
 *
 *    Copyright (C) Fujaba Development Group
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA 02111-1307, USA or download the license under
 * http://www.gnu.org/copyleft/lesser.html
 *
 *  WARRANTY:
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 *  Contact address:
 *
 * Fujaba Management Board
 * Software Engineering Group
 * University of Paderborn
 * Warburgerstr. 100
 * D-33098 Paderborn
 * Germany
 *
 * URL  : http://www.fujaba.de
 * email: info@fujaba.de
 */
package de.uni_paderborn.fujaba.asg;

import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;

import java.util.Iterator;

class EmptyFactory<I extends FElement> implements FFactory<I>
{
   private Class<I> cls;
   private ASGProject project;

   public EmptyFactory(ASGProject project, Class<I> cls)
   {
      this.project = project;
      this.cls = cls;
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getProject()
    */
   public FProject getProject()
   {
      return project;
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getInterfaceClass()
    */
   public Class<I> getInterfaceClass()
   {
      return cls;
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getConcreteClass()
    */
   public Class<? extends I> getConcreteClass()
   {
      return cls;
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#create()
    */
   public I create()
   {
      return create(true);
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#create(boolean)
    */
   public I create(boolean persistent)
   {
      throw new UnsupportedOperationException(cls + " cannot be instantiated in project '"+getProject()
            +"' - no factory registered!");
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProducts(java.lang.String)
    */
   public I getFromProducts(String key)
   {
      return null;
   }
   
   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProductsOwnProjectOnly(java.lang.String)
    */
   public I getFromProductsOwnProjectOnly(String key)
   {
      return null;
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#hasKeyInProducts(java.lang.String)
    */
   public boolean hasKeyInProducts(String key)
   {
      return false;
   }
   
   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#hasKeyInProductsOwnProjectOnly(java.lang.String)
    */
   public boolean hasKeyInProductsOwnProjectOnly(String key)
   {
      return false;
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#iteratorOfProducts()
    */
   public Iterator<I> iteratorOfProducts()
   {
      return EmptyIterator.get();
   }
   
   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#iteratorOfProductsOwnProjectOnly()
    */
   public Iterator<I> iteratorOfProductsOwnProjectOnly()
   {
      return EmptyIterator.get();
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#createInitialProducts()
    */
   public void createInitialProducts()
   {
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#createInitialProducts(boolean)
    */
   public void createInitialProducts(boolean persistent)
   {
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#removeYou()
    */
   public void removeYou()
   {
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#iteratorOfVisibleProducts(de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public Iterator<I> iteratorOfVisibleProducts(FElement context)
   {
      return EmptyIterator.get();
   }

   /**
    * @see java.lang.Iterable#iterator()
    */
   public Iterator<I> iterator()
   {
      return EmptyIterator.get();
   }
}

/*
 * $Log$
 * Revision 1.1  2007/02/15 10:00:31  cschneid
 * do not return null in gerFromFactories, load projects with same FeatureAccessModule to speed up loading many projects, new libs
 *
 */


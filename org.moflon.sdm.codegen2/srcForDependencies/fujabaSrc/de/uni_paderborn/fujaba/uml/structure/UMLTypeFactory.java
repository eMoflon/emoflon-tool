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

import de.uni_kassel.util.IteratorsIterator;
import de.uni_paderborn.fujaba.metamodel.structure.FArray;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FType;
import de.uni_paderborn.fujaba.uml.common.UMLProject;
import de.uni_paderborn.fujaba.uml.factories.UMLFactory;


/**
 * The UMLTypeFactory is just a facade for UMLBaseTypeFactory, UMLArrayFactory and UMLClassFactory.
 * It does not create own products.
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
class UMLTypeFactory extends UMLFactory<FType>
{

   public UMLTypeFactory(UMLProject project)
   {
      super(project);
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.factories.UMLFactory#getInterfaceClass()
    */
   public Class getInterfaceClass()
   {
      return FType.class;
   }


   /**
    * The UMLTypeFactory does not create its own products.
    * 
    * @return null
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getConcreteClass()
    */
   public Class getConcreteClass()
   {
      return null;
   }


   /**
    * The UMLTypeFactory does not create its own products.
    * 
    * @throws UnsupportedOperationException
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#create(boolean)
    */
   public FType create(boolean persistent)
   {
      throw new UnsupportedOperationException(
            "This factory is an adapter, it creates no products.");
   }


   /**
    * Returns a product of UMLBaseTypeFactory, UMLArrayFactory or UMLClassFactory
    * (includes all required projects).
    * 
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProducts(java.lang.String)
    */
   public FType getFromProducts(String key)
   {
      if (getProject().getFromFactories(FBaseType.class).hasKeyInProducts(key))
      {
         return getProject().getFromFactories(FBaseType.class).getFromProducts(key);
      }
      if (getProject().getFromFactories(FClass.class).hasKeyInProducts(key))
      {
         return getProject().getFromFactories(FClass.class).getFromProducts(key);
      }
      if (getProject().getFromFactories(FArray.class).hasKeyInProducts(key))
      {
    	  return getProject().getFromFactories(FArray.class).getFromProducts(key);
      }
      // if the specified product is not contained in the above factories
      // we don't create it, because we don't know, if the product should
      // be of FBaseType, FClass or FArray
      return null;
   }
   
   /**
    * Returns a product of UMLBaseTypeFactory, UMLArrayFactory or UMLClassFactory
    * (excludes all required projects).
    * 
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProductsOwnProjectOnly(java.lang.String)
    */
   public FType getFromProductsOwnProjectOnly(String key)
   {
      if (getProject().getFromFactories(FBaseType.class).hasKeyInProductsOwnProjectOnly(key))
      {
         return getProject().getFromFactories(FBaseType.class).getFromProductsOwnProjectOnly(key);
      }
      if (getProject().getFromFactories(FClass.class).hasKeyInProductsOwnProjectOnly(key))
      {
         return getProject().getFromFactories(FClass.class).getFromProductsOwnProjectOnly(key);
      }
      if (getProject().getFromFactories(FArray.class).hasKeyInProductsOwnProjectOnly(key))
      {
         return getProject().getFromFactories(FArray.class).getFromProductsOwnProjectOnly(key);
      }
      // if the specified product is not contained in the above factories
      // we don't create it, because we don't know, if the product should
      // be of FBaseType, FClass or FArray
      return null;
   }


   /**
    * Returns true if a product is registered under the key in UMLBaseTypeFactory, UMLArrayFactory
    * or UMLClassFactory (includes all required projects).
    * 
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#hasKeyInProducts(java.lang.String)
    */
   public boolean hasKeyInProducts(String key)
   {
      if (getProject().getFromFactories(FBaseType.class).hasKeyInProducts(key))
      {
         return true;
      }
      if (getProject().getFromFactories(FClass.class).hasKeyInProducts(key))
      {
         return true;
      }
      return getProject().getFromFactories(FArray.class).hasKeyInProducts(key);
   }
   
   /**
    * Returns true if a product is registered under the key in UMLBaseTypeFactory, UMLArrayFactory
    * or UMLClassFactory (excludes all required projects).
    * 
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#hasKeyInProductsOwnProjectOnly(java.lang.String)
    */
   public boolean hasKeyInProductsOwnProjectOnly(String key)
   {
      if (getProject().getFromFactories(FBaseType.class).hasKeyInProductsOwnProjectOnly(key))
      {
         return true;
      }
      if (getProject().getFromFactories(FClass.class).hasKeyInProductsOwnProjectOnly(key))
      {
         return true;
      }
      return getProject().getFromFactories(FArray.class).hasKeyInProductsOwnProjectOnly(key);
   }


   /**
    * Returns all products of UMLBaseTypeFactory, UMLArrayFactory and UMLClassFactory
    * (includes all required projects).
    * 
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#iteratorOfProducts()
    */
   public Iterator<FType> iteratorOfProducts()
   {
      Iterator<FBaseType> baseTypeIterator = getProject().getFromFactories(FBaseType.class).iteratorOfProducts();
      Iterator<FClass> classIterator = getProject().getFromFactories(FClass.class).iteratorOfProducts();
      Iterator<FArray> arrayIterator = getProject().getFromFactories(FArray.class).iteratorOfProducts();
      return new IteratorsIterator<FType>(baseTypeIterator, classIterator, arrayIterator);
   }


   /**
    * Returns all products of UMLBaseTypeFactory, UMLArrayFactory and UMLClassFactory
    * (excludes all required projects).
    * 
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#iteratorOfProductsOwnProjectOnly()
    */
   public Iterator<FType> iteratorOfProductsOwnProjectOnly()
   {
      Iterator<FBaseType> baseTypeIterator = getProject().getFromFactories(FBaseType.class).iteratorOfProductsOwnProjectOnly();
      Iterator<FClass> classIterator = getProject().getFromFactories(FClass.class).iteratorOfProductsOwnProjectOnly();
      Iterator<FArray> arrayIterator = getProject().getFromFactories(FArray.class).iteratorOfProductsOwnProjectOnly();
      return new IteratorsIterator<FType>(baseTypeIterator, classIterator, arrayIterator);
   }

}

/*
 * $Log$
 * Revision 1.2  2006/03/05 20:21:56  fklar
 * bugfixed method 'getFromProducts(String)': if the specified product is not contained it won't be created, because we don't know, if the product should be of FBaseType, FClass or FArray
 *
 * Revision 1.1  2006/02/28 14:45:11  cschneid
 * moved factories near their classes and made factories only package visible
 *
 * Revision 1.10  2006/02/27 13:46:51  lowende
 * Refactored storing of factories in project.
 *
 */

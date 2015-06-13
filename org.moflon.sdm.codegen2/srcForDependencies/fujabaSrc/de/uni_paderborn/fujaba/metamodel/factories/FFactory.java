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
package de.uni_paderborn.fujaba.metamodel.factories;


import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;

import java.util.Iterator;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public interface FFactory<I extends FElement> extends Iterable<I>
{

   public FProject getProject();


   /**
    * Returns the meta model interface class of the factory's product. This class is generally used
    * as the key for the factory in the project.
    * 
    * @return The meta model interface class.
    */
   public Class<I> getInterfaceClass();


   /**
    * Returns the concrete class of the product created by this factory. This class is used as the
    * key for the factory in the project when loading a project.
    * 
    * @return The concrete class
    */
   public Class<? extends I> getConcreteClass();


   /**
    * Let the factory create a product.
    *
    * @throws UnsupportedOperationException if no product may be created.
    * @return If successful, a product of this factory.
    */
   public I create() throws UnsupportedOperationException;


   /**
    * Let the factory create a product.
    *
    * @throws UnsupportedOperationException if no product may be created.
    * @param persistent Should the product be persistent?
    * @return If successful, a product of this factory.
    */
   public I create(boolean persistent) throws UnsupportedOperationException;


   /**
    * Retrieve a product by key (including products from factories in required projects).<br>
    * TODO: some factories create instances if this method is called with an unused key, some do
    * not!
    * 
    * @param key A product key
    * @return The product for the given key
    * 
    * @see FFactory#getFromProductsOwnProjectOnly(String)
    */
   public I getFromProducts(String key);
   
   
   /**
    * Retrieves a product from this FFactory by key (required projects are not considered). 
    * 
    * @param key A key corresponding to a product
    * @return The product to the key
    * 
    * @see FFactory#getFromProducts(String)
    */
   public I getFromProductsOwnProjectOnly(String key);


   /**
    * This method searches for the given key in this FFactory and all factories
    * of the same kind in required projects. 
    * 
    * @param key The key to look for.
    * @return True, if a product was created for this key, false otherwise.
    * 
    * @see FFactory#hasKeyInProductsOwnProjectOnly(String)
    */
   public boolean hasKeyInProducts(String key);
   
   
   /**
    * This method searches for the given key in this FFactory.
    * Does not consider required projects.
    * 
    * @param key The key to look for.
    * @return True, if a product was created for this key, false otherwise.
    * 
    * @see FFactory#hasKeyInProducts(String)
    */
   public boolean hasKeyInProductsOwnProjectOnly(String key);


   /**
    * @return The instances created by this factory type
    * including products from required projects.
    * 
    * @see FFactory#iteratorOfProductsOwnProjectOnly()
    */
   public Iterator<I> iteratorOfProducts();


   /**
    * @return The instances created by this factory only
    * (excluding products from required projects).
    * 
    * @see FFactory#iteratorOfProducts()
    */
   public Iterator<I> iteratorOfProductsOwnProjectOnly();
   
   /**
    * Create products that are common for all projects containing this factory.
    */
   public void createInitialProducts();


   /**
    * Create products that are common for all projects containing this factory.
    * @param persistent true to have the created products persisted
    */
   public void createInitialProducts(boolean persistent);

   
   /**
    * Remove all references, so that garbage collector can take care of this object.
    */
   public void removeYou();

   /**
    * Allows concrete factories to filter products by visibility rules
    * in the context of the element provided as parameter.
    * By default, this method returns FFactory.iteratorOfProducts(),
    * i.e. everything is globally visible.
    * 
    * @param context The context.
    * @return An Iterator containing products that are visible in the specified
    * context.
    */
   public Iterator<I> iteratorOfVisibleProducts(FElement context);
}

/*
 * $Log$
 * Revision 1.21  2007/02/16 14:30:07  cschneid
 * corrected fixes for EmptyFactory
 *
 * Revision 1.20  2007/02/15 16:43:01  fklar
 * improved documentation of method 'create'
 *
 * Revision 1.19  2006/12/01 13:02:10  l3_g5
 * improved support for non persistent object creation
 *
 * Revision 1.18  2006/06/22 15:58:15  fklar
 * added documentation
 *
 * Revision 1.17  2006/06/17 14:43:00  rotschke
 * Added FFactory.iteratorOfVisibleProducts(FElement context). This method allows concrete factories to filter products by visibility rules in the context of the element provided as parameter. By default, this method returns FFactory.iteratorOfProducts(), i.e. everything is globally visible [tr].
 *
 * Revision 1.16  2006/04/12 13:52:20  lowende
 * Project factories introduced.
 * Interface for plugins introduced to add factories to newly-created project factories.
 *
 */

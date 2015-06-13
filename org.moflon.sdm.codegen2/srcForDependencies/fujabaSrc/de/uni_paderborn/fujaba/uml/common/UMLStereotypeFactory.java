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
package de.uni_paderborn.fujaba.uml.common;


import de.uni_paderborn.fujaba.metamodel.common.FStereotype;
import de.uni_paderborn.fujaba.uml.factories.UMLFlyweightFactory;


/**
 * <h2>Associations</h2>
 * 
 * <pre>
 *           0..1   factories   0..1 ---------------
 * FFactory -------------------------| factoryKind | FProject
 *           factories       project ---------------
 *
 *                      +-----------+             stereotypes         0..1
 * UMLStereotypeFactory | getText() |-------------------------------------> UMLStereotype
 *                      +-----------+                          stereotypes
 * </pre>
 * 
 * @author $Author$
 * @version $Revision$ $Date$
 */
class UMLStereotypeFactory extends UMLFlyweightFactory<UMLStereotype>
{

   public UMLStereotypeFactory(UMLProject project)
   {
      super(project);
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.factories.UMLFactory#getInterfaceClass()
    */
   public Class getInterfaceClass()
   {
      return FStereotype.class;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getConcreteClass()
    */
   public Class<UMLStereotype> getConcreteClass()
   {
      return UMLStereotype.class;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProducts(java.lang.String)
    */
   @Override
   public UMLStereotype getFromProducts(String key, boolean persistent)
   {
      if (key == null)
      {
         return null;
      }
      if ((this.products != null) && (this.products.containsKey(key)))
      {
         return this.products.get(key);
      }
      else
      {
         UMLStereotype flyweight = new UMLStereotype(getProject(), persistent, key);
         flyweight.setName(key);
         addToProducts(key, flyweight);
         return flyweight;
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#createInitialProducts()
    */
   @Override
   public void createInitialProducts (boolean persistent)
   {
      getFromProducts(FStereotype.INTERFACE, persistent);
      getFromProducts(FStereotype.ENUM, persistent);
      getFromProducts(FStereotype.REFERENCE, persistent);
      getFromProducts(FStereotype.REFERENCE_WITH_REMOVE_YOU, persistent);
      getFromProducts(FStereotype.TYPE, persistent);
      getFromProducts(FStereotype.IMMUTABLE, persistent);
      getFromProducts(FStereotype.JAVA_BEAN, persistent);
      getFromProducts(FStereotype.FINAL, persistent);
      getFromProducts(FStereotype.NATIVE, persistent);
      getFromProducts(FStereotype.SIGNAL, persistent);
      getFromProducts(FStereotype.VOLATILE, persistent);
      getFromProducts(FStereotype.TRANSIENT, persistent);
      getFromProducts(FStereotype.SYNCHRONIZED, persistent);
      getFromProducts(FStereotype.STATIC, persistent);
      getFromProducts(FStereotype.USAGE, persistent);
      getFromProducts(FStereotype.VIRTUAL_PATH, persistent);
      getFromProducts(FStereotype.VOID, persistent);
   }

}

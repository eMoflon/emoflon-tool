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


import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.uml.common.UMLProject;
import de.uni_paderborn.fujaba.uml.factories.UMLFlyweightFactory;

import java.util.Iterator;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
class UMLBaseTypeFactory extends UMLFlyweightFactory<UMLBaseType>
{

   public UMLBaseTypeFactory(UMLProject project)
   {
      super(project);
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.factories.UMLFactory#getInterfaceClass()
    */
   public Class getInterfaceClass()
   {
      return FBaseType.class;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getConcreteClass()
    */
   public Class<UMLBaseType> getConcreteClass()
   {
      return UMLBaseType.class;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#createInitialProducts()
    */
   @Override
   public void createInitialProducts (boolean persistent)
   {
      getFromProducts(FBaseType.BOOLEAN, "boolean", persistent);
      getFromProducts(FBaseType.STRING, "String", persistent);
      getFromProducts(FBaseType.INTEGER, "int", persistent);
      getFromProducts(FBaseType.BYTE, "byte", persistent);
      getFromProducts(FBaseType.SHORT_INTEGER, "short", persistent);
      getFromProducts(FBaseType.LONG_INTEGER, "long", persistent);
      getFromProducts(FBaseType.FLOAT, "float", persistent);
      getFromProducts(FBaseType.DOUBLE, "double", persistent);
      getFromProducts(FBaseType.CHARACTER, "char", persistent);

      // FIXME Shouldn't we use the UMLArrayFactory here instead of creating UMLBaseTypes?
      // add arrays to basetypes
      Iterator<UMLBaseType> baseTypesIter = iteratorOfProducts();
      while (baseTypesIter.hasNext())
      {
         UMLBaseType baseType = baseTypesIter.next();
         String name = baseType.getName();
         if (!name.endsWith("Array"))
         {
            getFromProducts(name + "Array", baseType.getProgLangType() + "[]", persistent);
         }
      }

      getFromProducts(FBaseType.VOID, "void", persistent);
      getFromProducts(FBaseType.CONSTRUCTOR, "", persistent);
      getFromProducts(FBaseType.INITIALIZER, "", persistent);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProducts(java.lang.String)
    */
   @Override
   public UMLBaseType getFromProducts(String key, boolean persistent)
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
         UMLBaseType flyweight = new UMLBaseType( getProject(), persistent, key );
         flyweight.setName(key);
         flyweight.setProgLangType(key);
         addToProducts(key, flyweight);
         return flyweight;
      }
   }


   private UMLBaseType getFromProducts(String key, String progLangType, boolean persistent)
   {
      UMLBaseType product = getFromProducts(key, persistent);
      product.setProgLangType(progLangType);
      return product;
   }

}

/*
 * $Log$
 * Revision 1.6  2007/01/11 12:44:55  cschneid
 * NPE fixed, 'Character' created as initial product again, pom updated
 *
 * Revision 1.5  2006/12/06 17:47:08  rotschke
 * Removed unnecessary imports [tr].
 *
 * Revision 1.4  2006/12/01 13:02:10  l3_g5
 * improved support for non persistent object creation
 *
 * Revision 1.3  2006/03/08 14:57:38  lowende
 * Removed FClass.elementsOfRevSubclasses and FClass.elementsOfRevSuperclasses - use iteratorOf... instead.
 * Moved constants from UMLType to FType.
 * Added FClass.findClass and FClassUtility.findClass.
 * Added some tests and a test suite.
 *
 * Revision 1.2  2006/03/01 12:23:00  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 * Revision 1.1  2006/02/28 14:45:11  cschneid
 * moved factories near their classes and made factories only package visible
 *
 * Revision 1.7  2006/02/27 13:46:52  lowende
 * Refactored storing of factories in project.
 *
 */

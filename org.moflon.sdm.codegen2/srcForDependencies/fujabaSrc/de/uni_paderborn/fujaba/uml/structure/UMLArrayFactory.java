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


import de.uni_paderborn.fujaba.metamodel.structure.FArray;
import de.uni_paderborn.fujaba.uml.common.UMLProject;
import de.uni_paderborn.fujaba.uml.structure.UMLArray;
import de.uni_paderborn.fujaba.uml.factories.UMLFlyweightFactory;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
class UMLArrayFactory extends UMLFlyweightFactory<UMLArray>
{

   public UMLArrayFactory(UMLProject project)
   {
      super(project);
   }


   /**
    * @see de.uni_paderborn.fujaba.uml.factories.UMLFactory#getInterfaceClass()
    */
   public Class getInterfaceClass()
   {
      return FArray.class;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getConcreteClass()
    */
   public Class<UMLArray> getConcreteClass()
   {
      return UMLArray.class;
   }


   /**
    * The key is of the form "TypeName[]",
    * e.g. an array of UMLClass:
    * de.uni_paderborn.fujaba.uml.structure.UMLClass[];
    * an array of an array of UMLClass:
    * de.uni_paderborn.fujaba.uml.structure.UMLClass[][];
    * an array of integer:
    * integer[]. 
    *
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProducts(java.lang.String)
    */
   @Override
   public UMLArray getFromProducts(String key, boolean persistent)
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
         UMLArray flyweight = new UMLArray(getProject(), persistent, key);
         flyweight.setName(key);
         addToProducts(key, flyweight);
         return flyweight;
      }
   }

}

/*
 * $Log$
 * Revision 1.6  2006/12/06 17:47:08  rotschke
 * Removed unnecessary imports [tr].
 *
 * Revision 1.5  2006/12/01 13:02:10  l3_g5
 * improved support for non persistent object creation
 *
 * Revision 1.4  2006/05/08 13:32:48  lowende
 * Some comments added.
 *
 * Revision 1.3  2006/03/20 16:37:51  lowende
 * All compile errors removed. Not yet tested.
 *
 */

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


import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;

import java.util.Iterator;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public abstract class UMLFactory<I extends FElement> implements FFactory<I>
{

   private FProject project;


   public UMLFactory(FProject project)
   {
      if (project == null)
      {
         throw new IllegalArgumentException("Project must not be null!");
      }
      this.project = project;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getProject()
    */
   public final FProject getProject()
   {
      return this.project;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#create()
    */
   public final I create()
   {
      return create(true);
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#createInitialProducts()
    */
   public void createInitialProducts()
   {
      createInitialProducts(!ASGElement.isInTransientMode());
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#createInitialProducts()
    */
   public void createInitialProducts (boolean persistent)
   {
   }

   /**
    * Returns an iterator over a set of elements of type I.
    * 
    * @see java.lang.Iterable#iterator()
    */
   public final Iterator<I> iterator()
   {
      return iteratorOfProducts();
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#iteratorOfVisibleProducts(de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public Iterator<I> iteratorOfVisibleProducts(FElement context) {
      // Everything is visible in UMLProject
      return iteratorOfProducts();
   }
   
   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#removeYou()
    */
   public void removeYou()
   {
      this.project = null;
   }

}

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


import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FElementRef;
import de.uni_paderborn.fujaba.metamodel.common.FProject;


/**
 * This class implements the stub-mechanism, which allows to establish an association between two
 * distinct meta-model elements.
 * 
 * <h2>Associations</h2>
 * 
 * <pre>
 *            -------------- 0..1     hasReferences    0..1
 * ASGElement | getClass() |-------------------------------- ASGElementRef
 *            -------------- element      		 references
 * </pre>
 * 
 * @author Robert Wagner
 * @author Last Editor: $Author$
 * @version $Revision$ $Date$
 */
public abstract class ASGElementRef extends ASGElement implements FElementRef
{

   public ASGElementRef(FProject project, boolean persistent)
   {
      super(project, persistent);
   }


   /**
    * <pre>
    *            -------------- 0..1  hasReferences    0..1
    * ASGElement | getClass() |----------------------------- ASGElementRef
    *            -------------- element          references
    * </pre>
    */
   private ASGElement element;


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElementRef#setElement(de.uni_paderborn.fujaba.metamodel.common.FElement)
    */
   public boolean setElement(FElement value)
   {
      return setElementWithKey(getClass().getName(), value);
   }


   /* package */boolean setElementWithKey(String key, FElement value)
   {
      boolean changed = false;
      if (this.element != value)
      {
         if (this.element != null)
         {
            FElement oldValue = this.element;
            this.element = null;
            oldValue.removeFromElementReferences(key, this);
         }
         this.element = (ASGElement) value;
         if (value != null)
         {
            value.addToElementReferences(key, this);
         }
         changed = true;
      }

      return changed;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElementRef#getElement()
    */
   public ASGElement getElement()
   {
      return this.element;
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.BasicIncrement#removeYou()
    */
   @Override
   public void removeYou()
   {
      ASGElement tmpElement = getElement();
      if (tmpElement != null)
      {
         setElementWithKey(getClass().getName(), null);
      }
      
      super.removeYou();
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.common.FElement#getParentElement()
    */
   @Override
   public FElement getParentElement()
   {
      return getElement();
   }

}

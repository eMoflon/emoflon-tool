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

import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.metamodel.common.FDiagram;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.upb.tools.fca.FPropHashSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * <h2>Associations</h2>
 * <p/>
 * <pre>
 *          0..n    modelRootNodes   0..1
 * ASGRoot ------------------------------- FProject
 *          modelRootNodes        project
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public abstract class ASGDiagram extends ASGElement implements FDiagram
{
   /**
    * Constructor for class ASGDiagram
    *
    * @param project
    * @param persistent  true to store object (default)
    */
   protected ASGDiagram (FProject project, boolean persistent)
   {
      super (project, persistent);
      getProject().addToModelRootNodes (this);
   }


   /**
    * List of all contained diagram items and its manipulating methods The FHashSet returns
    * the containted items in order of adding.
    */
   private Set<FElement> elements = null;


   /**
    * @return   number of elements contained in this diagram
    */
   public int sizeOfElements()
   {
      return  ( (this.elements == null)
         ? 0
         : this.elements.size());
   }


   /**
    * @param element  ASGElement of interest
    * @return         true when element is in elements attribute
    */
   public boolean hasInElements (FElement element)
   {
      return this.elements != null && elements.contains(element);
   }


   /**
    * @return   iterator through elements (only ASGElements)
    */
   public Iterator<? extends FElement> iteratorOfElements()
   {
      if (this.elements == null)
      {
         return EmptyIterator.get();
      } else
      {
         return this.elements.iterator();
      }
   }


   /**
    * add an ASGEelement to the elements attribute
    *
    * @param element  ASGElement to be added
    * @return         true when element has been newly added
    */
   public boolean addToElements (FElement element)
   {
      boolean changed = false;

      if (element != null && !hasInElements (element))
      {
         if (this.elements == null)
         {
            this.elements = new FPropHashSet (this, ELEMENTS_PROPERTY);
         }
         changed = this.elements.add (element);

         element.addToDiagrams (this);
      }

      return changed;
   }


   /**
    * @param entry  The object added.
    */
   public void addToElements (Map.Entry entry)
   {
      ASGElement element = (ASGElement) entry.getValue();
      addToElements (element);
   }


   /**
    * remove an ASGElement from the elements attribute
    *
    * @param element  what to remove
    * @return         true when element was removed (had in elements)
    */
   public boolean removeFromElements (FElement element)
   {
      boolean changed = false;

      if (element != null && hasInElements (element))
      {
         elements.remove (element);

         //todo: this property change event will be removed
         //firePropertyChange (CollectionChangeEvent.get (this, "items", this.elements, element, null, CollectionChangeEvent.REMOVED));

         element.removeFromDiagrams (this);
         changed = true;
      }

      return changed;
   }


   /**
    * clear elements attribute
    */
   public void removeAllFromElements()
   {
      Iterator iter = iteratorOfElements();
      while (iter.hasNext())
      {
         removeFromElements ((ASGElement) iter.next());
      }
   }


   /**
    * Query the logical parent of this element (e.g. package of a class, diagram of an object).
    *
    * @return   the logical parent of this element;
    */
   @Override
   public FElement getParentElement()
   {
      return getProject();
   }


   /**
    * Deletes the diagram from the project.
    * <b>Delete is not implemented for ASGDiagram, sub classes may override
    * it to enable diagram deletion!</b>
    *
    * @see   de.uni_paderborn.fujaba.app.action.DeleteDiagramAction
    */
   public void delete()
   {
      // This method is not implemented for ASGDiagram, sub classes have to override it to enable diagram deletion!
   }


   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      return getName();
   }


   /**
    * remove all references to other objects
    */
   @Override
   public void removeYou()
   {
      removeAllFromElements();

      getProject().removeFromModelRootNodes(this);

      super.removeYou();
   }

}

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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import de.uni_kassel.util.ConcurrentHashSet;
import de.uni_kassel.util.EmptyIterator;
import de.uni_kassel.util.IteratorsIterator;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.factories.FFactory;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public abstract class UMLHeavyweightFactory<I extends FElement> extends UMLAdvancedFactory<I> implements PropertyChangeListener
{
   /**
    * log4j logging
    */
   private final static transient Logger log = Logger.getLogger (UMLHeavyweightFactory.class);

   public UMLHeavyweightFactory(FProject project)
   {
      super(project);
   }


   protected Set<I> products;


   public final I addToProducts(I value)
   {
      if (value != null)
      {
         if (this.products == null)
         {
            this.products = new ConcurrentHashSet<I>();
         }
         this.products.add(value);
         getPropertyChangeSupport().firePropertyChange(PRODUCTS_PROPERTY, null, value);
         value.addPropertyChangeListener(FElement.REMOVE_YOU_PROPERTY, this);
      }
      return value;
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#hasKeyInProducts(java.lang.String)
    */
   public boolean hasKeyInProducts(String key)
   {
      if (key != null)
      {
         Iterator<I> iter = iteratorOfProducts();
         while (iter.hasNext())
         {
            I element = iter.next();
            String elementKey = element.getElementKey();
            if (key.equals(elementKey))
            {
               return true;
            }
         }
      }
      return false;
   }
   
   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#hasKeyInProductsOwnProjectOnly(java.lang.String)
    */
   public boolean hasKeyInProductsOwnProjectOnly(String key)
   {
      if ((key != null))
      {
         Iterator<I> iter = iteratorOfProductsOwnProjectOnly();
         while (iter.hasNext())
         {
            I element = iter.next();
            String elementKey = element.getElementKey();
            if (key.equals(elementKey))
            {
               return true;
            }
         }
      }
      return false;
   }


   public boolean hasInProducts(I value)
   {
      if(value == null)
      {
         return false;
      }
      
      Iterator<I> i = iteratorOfProducts();
      while (i.hasNext())
      {
         if (value.equals(i.next()))
         {
            return true;
         }
      }
      return false;
   }
   
   public boolean hasInProductsOwnProjectOnly(I value)
   {
      return ((this.products != null) && this.products.contains(value));
   }


   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#iteratorOfProducts()
    */
   public Iterator<I> iteratorOfProducts()
   {      
      IteratorsIterator<I> result = new IteratorsIterator<I>(this.iteratorOfProductsOwnProjectOnly());
      Iterator<FProject> allProjects = getAllRequiredProjects().iterator();
      while(allProjects.hasNext())
      {
         FProject requiredProject = allProjects.next();
         FFactory<I> requiredFactory = requiredProject.getFromFactories(this.getInterfaceClass());
         if (requiredFactory != null)
         {
            if(requiredFactory instanceof UMLHeavyweightFactory)
            {
               result.append(((UMLHeavyweightFactory<I>)requiredFactory).iteratorOfProductsOwnProjectOnly());
            }else
            {
               result.append(requiredFactory.iteratorOfProducts());
            }
         }
      }
      return result;
   }
   

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#iteratorOfProductsOwnProjectOnly()
    */
   public Iterator<I> iteratorOfProductsOwnProjectOnly()
   {
      if (this.products == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.products.iterator();
      }
   }


   public final void removeAllFromProducts()
   {
      Iterator<I> i = this.iteratorOfProductsOwnProjectOnly();
      while(i.hasNext())
      {
         i.next().removeYou();
      }
      if (this.products != null)
      {
         this.products.clear();
      }
   }


   public final boolean removeFromProducts(I value)
   {
      boolean changed = false;
      if ((this.products != null) && (value != null))
      {
         value.removePropertyChangeListener(FElement.REMOVE_YOU_PROPERTY, this);
         changed = products.remove(value);
         getPropertyChangeSupport().firePropertyChange(PRODUCTS_PROPERTY, value, null);
      }
      return changed;
   }


   public int sizeOfProducts()
   {
      int counter = 0;
      Iterator<I> i = iteratorOfProducts();
      while(i.hasNext())
      {
         i.next();
         counter++;
      }
      return counter;
   }
   
   public int sizeOfProductsOwnProjectOnly()
   {
      return ((this.products == null) ? 0 : this.products.size());
   }

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProducts(java.lang.String)
    */
   public I getFromProducts(String key)
   {
      if ((key != null))
      {
         Iterator<I> iter = iteratorOfProducts();
         while (iter.hasNext())
         {
            I elem = iter.next();
            if (key.equals(elem.getElementKey()))
            {
               return elem;
            }
         }
      }
      return null;
   }
   

   /**
    * @see de.uni_paderborn.fujaba.metamodel.factories.FFactory#getFromProducts(java.lang.String)
    */
   public I getFromProductsOwnProjectOnly(String key)
   {
      if ((key != null) && (this.products != null))
      {
         Iterator<I> iter = iteratorOfProductsOwnProjectOnly();
         while (iter.hasNext())
         {
            I elem = iter.next();
            if (key.equals(elem.getElementKey()))
            {
               return elem;
            }
         }
      }
      return null;
   }
   
   /**
    * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
    */
   @SuppressWarnings("unchecked")
   public void propertyChange(PropertyChangeEvent evt)
   {
      if (evt == null)
         return;

      if (FElement.REMOVE_YOU_PROPERTY.equals(evt.getPropertyName()))
      {
         // if an element is deleted, it will be removed from
         // our internal product list as well
         Object oldValue = evt.getOldValue();
         if (oldValue instanceof ASGElement)
         {
            // we make sure, that the passed value is of the correct type 'I'
        	try
        	{
        		I elementToRemove = (I)oldValue;
        		removeFromProducts(elementToRemove);
        	}
        	catch (ClassCastException e)
        	{
        		log.error ("wrong argument type '" + oldValue.getClass().getName() + "'"); 
        	}
         }
      }
   }

   @Override
   public void removeYou()
   {
      removeAllFromProducts();
      super.removeYou();
   }
   
   
   protected List<FProject> getAllRequiredProjects()
   {
      ArrayList<FProject> projects = new ArrayList<FProject>();
      return getRequiredProjects(projects, this.getProject());
   }
   
   private List<FProject> getRequiredProjects(List<FProject> projects, FProject project)
   {
      Iterator<? extends FProject> i = project.iteratorOfRequires();
      while(i.hasNext())
      {
         FProject p = i.next();
         if(!projects.contains(p))
         {
            projects.add(p);
            getRequiredProjects(projects, p);
         }
      }
      return projects;
   }
}

/*
 * $Log$
 * Revision 1.26  2007/03/21 13:40:42  cschneid
 * - tree displays assocs, roles and qualifiers
 * - objects may be created even if their type is an interface
 * - base types and array clean up their factory on delete
 * - some NPEs prevented
 *
 * Revision 1.25  2006/05/24 08:44:44  cschneid
 * use Project factories in Versioning, selection manager can select not-displayed elements, Project tree displays packages, more dummy code for access sytles
 *
 * Revision 1.24  2006/05/09 16:54:02  fklar
 * changed mechanism for removing a FElement from a UMLHeavyWeightFactory: as an element normally shouldn't care about how it has been created, a factory must observe its elements and remove it from any internal list, if an 'FElement.REMOVE_YOU' event arrives
 *
 * Revision 1.23  2006/05/09 14:11:24  lowende
 * ConcurrentModificationException fixed in UMLHeavyweightFactory.
 *
 * Revision 1.22  2006/04/06 12:06:04  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.21  2006/04/06 08:21:56  cschneid
 * added missing super.removeYou in new removeYou method
 *
 * Revision 1.20  2006/04/05 16:34:22  fklar
 * preventing NullPointerException in method 'removeAllFromProducts'
 *
 * Revision 1.19  2006/04/05 16:28:07  fklar
 * added missing 'removeYou()' implementation
 *
 * Revision 1.18  2006/03/28 16:03:16  fklar
 * better debug-support for method 'hasKeyInProducts(String)': storing elementKey in local variable
 *
 * Revision 1.17  2006/03/20 16:37:51  lowende
 * All compile errors removed. Not yet tested.
 *
 */

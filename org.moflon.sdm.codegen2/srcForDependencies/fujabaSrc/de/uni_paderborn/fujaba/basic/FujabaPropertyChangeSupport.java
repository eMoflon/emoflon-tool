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
package de.uni_paderborn.fujaba.basic;


import de.uni_kassel.util.EmptyIterator;
import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.IteratorConcatenation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;


/**
 * This subclass of java.beans.PropertyChangeSupport is identical in functionality -- it sacrifices
 * thread-safety (not a Swing concern) for reduce memory consumption, which helps performance (both
 * big Swing concerns). Most of the overridden methods are only necessary because all of
 * PropertyChangeSupport's instance data is private, without accessor methods. Different to
 * SwingPropertyChangeSupport this class does not register a listener more than once for a property.
 * If the listener is registered for specific properties and shall be added for all properties, the
 * entries in the specific listener lists are deleted first.
 * </p>
 * Additionally this class allows querying for the list of registered listeners according to Fujaba
 * Styleguide.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class FujabaPropertyChangeSupport extends PropertyChangeSupport
{

   /**
    * "listeners" lists all the generic listeners.
    */
   private transient final Vector<PropertyChangeListener> listeners = new Vector<PropertyChangeListener>();

   /**
    * "children" contains FujabaPropertyChangeSupports for individual properties
    */
   private transient Hashtable<String, FujabaPropertyChangeSupport> children;

   private Object source;


   // Serialization version ID
   final static long serialVersionUID = 7162625831330845068L;


   /**
    * Constructs a FujabaPropertyChangeSupport object.
    * 
    * @param sourceBean The bean to be given as the source for any events.
    */
   public FujabaPropertyChangeSupport(Object sourceBean)
   {
      super(sourceBean);
      setSource(sourceBean);
   }


   /**
    * Sets the source attribute of the FujabaPropertyChangeSupport object
    * 
    * @param source The new source value
    */
   public boolean setSource(Object source)
   {
      if (this.source != source)
      {
         this.source = source;
         return true;
      }
      return false;
   }


   /**
    * Get the source attribute of the FujabaPropertyChangeSupport object
    * 
    * @return The source value
    */
   public Object getSource()
   {
      return this.source;
   }


   /**
    * Add a PropertyChangeListener to the listener list. The listener is registered for all
    * properties.
    * 
    * @param listener The PropertyChangeListener to be added
    * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
    */
   @Override
   public void addPropertyChangeListener(PropertyChangeListener listener)
   {
      addToListeners(listener);
   }


   /**
    * if listener is not yet in listener-list it is added. All occurences of listener in child-lists
    * are removed
    * 
    * @param listener The object added.
    */
   public synchronized boolean addToListeners(PropertyChangeListener listener)
   {
      if (listener == null)
      {
         throw new NullPointerException(
               "can't add specified PropertyChangeListener: it is null");
      }
      if (!this.listeners.contains(listener))
      {
         removeFromListeners(null, listener);

         synchronized (this.listeners)
         {
            this.listeners.addElement(listener);
         }
         return true;
      }
      return false;
   }


   /**
    * Remove a PropertyChangeListener from the listener list. This removes a PropertyChangeListener
    * that was registered for all properties.
    * 
    * @param listener The PropertyChangeListener to be removed
    * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
    */
   @Override
   public void removePropertyChangeListener(PropertyChangeListener listener)
   {
      removeFromListeners(listener);
   }


   /**
    * Remove listener both from general listener list and child-lists
    */
   public synchronized boolean removeFromListeners(
         PropertyChangeListener listener)
   {
      if (this.listeners == null)
      {
         return removeFromListeners(null, listener);
      }
      synchronized (this.listeners)
      {
         return (this.listeners.removeElement(listener) || removeFromListeners(
               null, listener));
      }
   }


   /**
    * Add a PropertyChangeListener for a specific property. The listener will be invoked only when a
    * call on firePropertyChange names that specific property.
    * 
    * @param propertyName The name of the property to listen on.
    * @param listener The PropertyChangeListener to be added
    * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
    *      java.beans.PropertyChangeListener)
    */
   @Override
   public void addPropertyChangeListener(String propertyName,
         PropertyChangeListener listener)
   {
      addToListeners(propertyName, listener);
   }


   /**
    * add listener if listsner is not already added as global listener or as listener for that
    * property
    * 
    * @param propertyName The object added.
    * @param listener The object added.
    */
   public synchronized boolean addToListeners(String propertyName,
         PropertyChangeListener listener)
   {
      if (propertyName == null)
      {
         return addToListeners(listener);
      }

      if (!hasInListeners(listener) && !hasInListeners(propertyName, listener))
      {
         if (this.children == null)
         {
            this.children = new Hashtable<String, FujabaPropertyChangeSupport>();
         }
         FujabaPropertyChangeSupport child = this.children.get(propertyName);
         if (child == null)
         {
            child = new FujabaPropertyChangeSupport(this.source);
            this.children.put(propertyName, child);
         }
         child.addPropertyChangeListener(listener);
         return true;
      }
      return false;
   }


   /**
    * Remove a PropertyChangeListener for a specific property.
    * 
    * @param propertyName The name of the property that was listened on.
    * @param listener The PropertyChangeListener to be removed
    * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
    *      java.beans.PropertyChangeListener)
    */
   @Override
   public void removePropertyChangeListener(String propertyName,
         PropertyChangeListener listener)
   {
      removeFromListeners(propertyName, listener);
   }


   /**
    * remove named listener from child-list. if propertyName is null, remove from all child-lists
    * 
    * @param propertyName No description provided
    * @param listener No description provided
    * @return No description provided
    */
   public synchronized boolean removeFromListeners(String propertyName,
         PropertyChangeListener listener)
   {
      if (this.children == null)
      {
         return false;
      }

      if (propertyName != null)
      {
         FujabaPropertyChangeSupport child = this.children.get(propertyName);
         if (child == null)
         {
            return false;
         }
         return child.removeFromListeners(listener);
      }
      else
      {
         boolean changed = false;
         Iterator childIter = iteratorOfChildren();
         while (childIter.hasNext())
         {
            changed = changed
                  | ((FujabaPropertyChangeSupport) childIter.next())
                        .removeFromListeners(listener);
         }
         return changed;
      }
   }


   /**
    * Check if there are any listeners for a specific property.
    * 
    * @param propertyName the property name.
    * @return true if there are ore or more listeners for the given property
    * @see java.beans.PropertyChangeSupport#hasListeners(java.lang.String)
    */
   @Override
   public synchronized boolean hasListeners(String propertyName)
   {
      if (this.listeners != null && !this.listeners.isEmpty())
      {
         // there is a generic listener
         return true;
      }
      if (this.children != null)
      {
         FujabaPropertyChangeSupport child = this.children.get(propertyName);
         if (child != null)
         {
            // The child will always have a listeners Vector.
            return !child.listeners.isEmpty();
         }
      }
      return false;
   }


   public synchronized boolean hasInListeners(PropertyChangeListener listener)
   {
      return (this.listeners != null && this.listeners.contains(listener));
   }


   /**
    * Return true if listener is in child-list for propertyName, or in any child-list if
    * propertyName is null
    */
   public synchronized boolean hasInListeners(String propertyName,
         PropertyChangeListener listener)
   {
      if (listener == null)
      {
         return false;
      }

      if (this.children == null)
      {
         return false;
      }

      if (propertyName != null)
      {
         FujabaPropertyChangeSupport child = this.children.get(propertyName);
         if (child != null)
         {
            // The child will always have a listeners Vector.
            return child.hasInListeners(listener);
         }
         return false;
      }
      else
      {
         Iterator childIter = iteratorOfChildren();
         while (childIter.hasNext())
         {
            if (((FujabaPropertyChangeSupport) childIter.next())
                  .hasInListeners(listener))
            {
               return true;
            }
         }
         return false;
      }
   }


   /**
    * Return true if listener is in global list or in child-list for propertyName, or in any
    * child-list if propertyName is null
    */
   public boolean hasInAllListeners(PropertyChangeListener listener)
   {
      return (hasInListeners(listener) || hasInListeners(null, listener));
   }


   /**
    * Iterator of global listeners
    */
   public synchronized Iterator<? extends Object> iteratorOfListeners()
   {
      if (this.listeners == null)
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.listeners.iterator();
      }
   }


   /**
    * Iterator of all listeners in global list and child-lists
    */
   public synchronized Iterator<? extends Object> iteratorOfAllListeners()
   {
      if (this.listeners == null)
      {
         return iteratorOfListeners(null);
      }
      else
      {
         if (this.children == null)
         {
            return iteratorOfListeners();
         }

         return new IteratorConcatenation(iteratorOfListeners(),
               iteratorOfListeners(null));
      }
   }


   /**
    * Iterator of listeners for propertyName or for all propertyNames if propertyName is null
    */
   public synchronized Iterator<? extends Object> iteratorOfListeners(
         String propertyName)
   {
      if (propertyName != null)
      {
         if (this.children != null)
         {
            FujabaPropertyChangeSupport child = this.children.get(propertyName);
            if (child != null)
            {
               return child.iteratorOfListeners();
            }
         }
         return EmptyIterator.get();
      }
      else
      {
         Iterator<? extends Object> result = null;

         Iterator childIter = iteratorOfChildren();
         while (childIter.hasNext())
         {
            FujabaPropertyChangeSupport child = (FujabaPropertyChangeSupport) childIter
                  .next();
            if (child != null)
            {
               if (child.listeners.size() == 0)
               {
                  continue;
               }

               Iterator<? extends Object> iter = child.iteratorOfListeners();

               if (result == null)
               {
                  result = iter;
               }
               else
               {
                  result = new IteratorConcatenation(result, iter);
               }
            }
         }

         if (result == null)
         {
            result = EmptyIterator.get();
         }

         return result;
      }
   }


   public int sizeOfListeners()
   {
      return (this.listeners == null) ? 0 : this.listeners.size();
   }


   public int sizeOfAllListeners()
   {
      return sizeOfListeners() + sizeOfListeners(null);
   }


   public int sizeOfListeners(String propertyName)
   {
      if (propertyName != null)
      {
         if (this.children != null)
         {
            FujabaPropertyChangeSupport child = this.children.get(propertyName);
            if (child != null)
            {
               return child.sizeOfListeners();
            }
         }
         return 0;
      }
      else
      {
         int result = 0;

         Iterator childIter = iteratorOfChildren();
         while (childIter.hasNext())
         {
            FujabaPropertyChangeSupport child = (FujabaPropertyChangeSupport) childIter
                  .next();
            if (child != null)
            {
               result += child.listeners.size();
            }
         }
         return result;
      }
   }


   public Iterator iteratorOfChildren()
   {
      return (this.children == null) ? FEmptyIterator.get() : this.children
            .values().iterator();
   }


   public Iterator<? extends Object> keysOfChildren()
   {
      if ((this.children == null))
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.children.keySet().iterator();
      }
   }


   public Iterator<? extends Object> entriesOfChildren()
   {
      if ((this.children == null))
      {
         return EmptyIterator.get();
      }
      else
      {
         return this.children.entrySet().iterator();
      }
   }


   /**
    * Get the fromChildren attribute of the FujabaPropertyChangeSupport object
    * 
    * @param propertyName No description provided
    * @return The fromChildren value
    */
   public FujabaPropertyChangeSupport getFromChildren(String propertyName)
   {
      return (this.children == null) ? null
            : (FujabaPropertyChangeSupport) this.children.get(propertyName);
   }


   public int sizeOfChildren()
   {
      return (this.children == null) ? 0 : this.children.size();
   }


   /**
    * Report a bound property update to any registered listeners. No event is fired if old and new
    * are equal and non-null.
    * 
    * @param propertyName The programmatic name of the property that was changed.
    * @param oldValue The old value of the property.
    * @param newValue The new value of the property.
    * @see java.beans.PropertyChangeSupport#firePropertyChange(java.lang.String, java.lang.Object,
    *      java.lang.Object)
    */
   @Override
   public void firePropertyChange(String propertyName, Object oldValue,
         Object newValue)
   {
      if (oldValue == newValue
            || (oldValue != null && oldValue.equals(newValue))
            || (newValue != null && newValue.equals(oldValue)))
      {
         return;
      }

      FujabaPropertyChangeSupport child = null;
      synchronized (this)
      {
         if (this.children != null && propertyName != null)
         {
            child = this.children.get(propertyName);
         }
      }

      if (this.listeners != null || child != null)
      {
         // Only create an event if there's an interested receiver.
         PropertyChangeEvent evt = new PropertyChangeEvent(source,
               propertyName, oldValue, newValue);

         if (this.listeners != null)
         {
            synchronized (this.listeners)
            {
               PropertyChangeListener[] tmpListeners = this.listeners.toArray(
                  new PropertyChangeListener[this.listeners.size()]);
               for (PropertyChangeListener target : tmpListeners)
               {
                  /*
                   * FIXME:Copying and checking the list raises the complexity to n^2. 
                   * Iterate synchronously over the two lists instead.
                   */  
                  if (this.listeners.contains(target))
                  {
                     // decouple property change listeners from model
                     try
                     {
                        target.propertyChange(evt);
                     }
                     catch (Exception e)
                     {
                        e.printStackTrace();
                     }
                  }
               }
            }
         }

         if (child != null)
         {
            child.firePropertyChange(evt);
         }
      }
   }


   /**
    * Fire an existing PropertyChangeEvent to any registered listeners. No event is fired if the
    * given event's old and new values are equal and non-null.
    * 
    * @param evt The PropertyChangeEvent object.
    * @see java.beans.PropertyChangeSupport#firePropertyChange(java.beans.PropertyChangeEvent)
    */
   @Override
   public void firePropertyChange(PropertyChangeEvent evt)
   {
      Object oldValue = evt.getOldValue();
      Object newValue = evt.getNewValue();
      String propertyName = evt.getPropertyName();

      if (oldValue == newValue
            || (oldValue != null && oldValue.equals(newValue)))
      {
         return;
      }

      FujabaPropertyChangeSupport child = null;
      synchronized (this)
      {
         if (this.children != null && propertyName != null)
         {
            child = this.children.get(propertyName);
         }
      }

      if (this.listeners != null)
      {
         synchronized (this.listeners)
         {
            ArrayList<PropertyChangeListener> tmpListeners = new ArrayList<PropertyChangeListener>(
                  this.listeners.size());
            tmpListeners.addAll(this.listeners);
            for (PropertyChangeListener target : tmpListeners)
            {
               if (this.listeners.contains(target))
               {
                  // decouple property change listeners from model
                  try
                  {
                     target.propertyChange(evt);
                  }
                  catch (Exception e)
                  {
                     System.err.println(e.getMessage());
                     e.printStackTrace();
                  }
               }
            }
         }
      }
      if (child != null)
      {
         child.firePropertyChange(evt);
      }
   }


   public void removeYou()
   {
      this.source = null;

      if (this.listeners != null)
      {
         synchronized (this.listeners)
         {
            this.listeners.clear();
         }
      }

      if (this.children != null)
      {
         Iterator iter = iteratorOfChildren();
         while (iter.hasNext())
         {
            FujabaPropertyChangeSupport child = (FujabaPropertyChangeSupport) iter
                  .next();
            child.removeYou();
         } // while

         this.children.clear();
      }
   }

}

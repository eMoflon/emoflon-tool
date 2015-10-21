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

import de.uni_paderborn.fujaba.basic.FujabaPropertyChangeSupport;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.upb.tools.pcs.PropertyChangeInterface;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Iterator;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public abstract class UMLAdvancedFactory<I extends FElement> extends UMLFactory<I> implements PropertyChangeInterface
{

   public UMLAdvancedFactory(FProject project)
   {
      super(project);
   }

   public static final String PRODUCTS_PROPERTY = "products";

   private transient FujabaPropertyChangeSupport propertyChangeSupport = null;


   /**
    * @see de.upb.tools.pcs.PropertyChangeInterface#getPropertyChangeSupport()
    */
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      if (propertyChangeSupport == null)
      {
         propertyChangeSupport = new FujabaPropertyChangeSupport (this);

         if (additionalListeners != null)
         {
            Iterator<PropertyChangeListener> iter = additionalListeners.iterator();
            while (iter.hasNext())
            {
               PropertyChangeListener listener = iter.next();
               addPropertyChangeListener (listener);
            }
         }
      }
      
      return propertyChangeSupport;
   }

   // --------------------------------------------------------------------
   // Additional property change support for plugins.
   // --------------------------------------------------------------------

   /**
    * Set of additional listeners.
    */
   private static volatile HashSet<PropertyChangeListener> additionalListeners;


   /**
    * Adds an additional property change listener. If, e.g. a plugin wants to be notified about
    * changes in the ASG, it has to register a listener at startup time. Each time a new <code>ASGElement</code>
    * is created, all registered listeners are added to the propertyChangeSupport of this element.
    */
   public static void addAdditionalListener (PropertyChangeListener listener)
   {
      if (listener != null)
      {
         if (additionalListeners == null)
         {
            additionalListeners = new HashSet<PropertyChangeListener>();
         }
         additionalListeners.add (listener);
      }
   }


   public void addPropertyChangeListener (PropertyChangeListener listener)
   {
      getPropertyChangeSupport().addPropertyChangeListener (listener);
   }
}

/*
 * $Log$
 * Revision 1.10  2006/05/24 08:44:44  cschneid
 * use Project factories in Versioning, selection manager can select not-displayed elements, Project tree displays packages, more dummy code for access sytles
 *
 * Revision 1.9  2006/03/02 14:03:47  rotschke
 * Used FProject instead of UMLProject to allow other meta model implementations to use BehaviorPackage [tr].
 *
 * Revision 1.8  2006/03/01 14:27:06  lowende
 * Removed a lot of compile warnings.
 *
 * Revision 1.7  2006/03/01 12:22:54  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 * Revision 1.6  2006/02/27 13:46:52  lowende
 * Refactored storing of factories in project.
 *
 */

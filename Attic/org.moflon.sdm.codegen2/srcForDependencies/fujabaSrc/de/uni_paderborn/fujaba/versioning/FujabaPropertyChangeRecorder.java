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
package de.uni_paderborn.fujaba.versioning;


import java.beans.PropertyChangeEvent;
import java.util.List;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.MutableChange;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.adapter.ChangeRecorder;
import de.uni_kassel.coobra.adapter.PropertyChangeRecorder;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.features.annotation.AnnotationUtil;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.messages.MessageManager;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.versioning.properties.Local;
import de.upb.tools.pcs.CollectionChangeEvent;
import de.upb.tools.pcs.ConfigurablePropertyChangeEvent;
import de.upb.tools.pcs.ListChangeEvent;
import de.upb.tools.pcs.MapChangeEvent;


/**
 * @author christian.schneider@uni-kassel.de
 * @version $Revision$ $Date$
 */
class FujabaPropertyChangeRecorder extends PropertyChangeRecorder
{

   /**
    * store disabled state.
    */
   private boolean disabled = false;


   /**
    * Default ctor.
    * 
    * @param repository send recorded changes to this repository
    */
   public FujabaPropertyChangeRecorder(Repository repository)
   {
      super(repository);
   }


   /**
    * Called upon all changes to ASGElements
    * 
    * @param e the change event
    * @throws ClassNotFoundException if the class of the subject is not found by the feature
    *            abstraction module
    * @throws NoSuchFieldException if the property name specified is not found as field of the
    *            subjects class
    */
   @Override
   public void propertyChange(PropertyChangeEvent e)
         throws ClassNotFoundException,
            NoSuchFieldException
   {
      Repository repository = getRepository();
      if (!disabled && !repository.isInOperationalization())
      {
         Object source = e.getSource();
         if (source instanceof ASGElement)
         {
            if (!isPersistencyChange((ASGElement) source, e))
            {
               return;
            }
         }

         Object oldValue = e.getOldValue();
         if (oldValue instanceof ASGElement)
         {
            ASGElement oldAsg = (ASGElement) oldValue;
            if (!oldAsg.isPersistent())
            {
               oldValue = null;
            }
            // change affects object of a requiredBy project which is already closed, so ignore it
            if (oldAsg.getProject().getRepository().getIdentifierModule() == null)
            {
               return;
            }
         }

         Object newValue = e.getNewValue();
         if (newValue instanceof ASGElement)
         {
            ASGElement newAsg = (ASGElement) newValue;
            if (!newAsg.isPersistent())
            {
               newValue = null;
            }
            // change affects object of a requiredBy project which is already closed, so ignore it
            if (newAsg.getProject().getRepository().getIdentifierModule() == null)
            {
               return;
            }
         }

         if (oldValue != newValue)
         {
            Change.Kind changeType = Change.Kind.ALTER_FIELD;
            Object key = null;

            // coobra TODO: updateUndoRedoActions
            // if (!ProjectLoader.isLoading() && umlProject != null)
            // {
            // umlProject.setSaved (false);
            // FujabaChangeManager.updateUndoRedoActions();
            // }

            if (ASGElement.REMOVE_YOU_PROPERTY.equals(e.getPropertyName()))
            {
               // not efficiently handled by garbage collection
               Change change = repository.getChangeFactory().changeDestroyObject(source);
               repository.acknowledgeChange(change);
               markDirty(source);
               return;
            }
            else if (e instanceof CollectionChangeEvent)
            {
               CollectionChangeEvent ce = (CollectionChangeEvent) e;
               if (ce.getType() == CollectionChangeEvent.ADDED
                     || ce.getType() == CollectionChangeEvent.ADDED_BEFORE
                     || ce.getType() == CollectionChangeEvent.ADDED_AFTER)
               {
                  changeType = Change.Kind.ALTER_FIELD;
               }
               else if (ce.getType() == CollectionChangeEvent.REMOVED
                     || ce.getType() == CollectionChangeEvent.REMOVED_AFTER)
               {
                  changeType = Change.Kind.ALTER_FIELD; // coobra TODO: sometimes it's remove_key
               }
               else if (ce.getType() == CollectionChangeEvent.CHANGED)
               {
                  changeType = Change.Kind.ALTER_FIELD;
               }
               else
               {
                  new RuntimeException("Unknown CollectionChangeEvent type: "
                        + ce.getType()).printStackTrace();
               }

               if (ce instanceof MapChangeEvent)
               {
                  key = ((MapChangeEvent) ce).getKey();
               }
               else if (ce instanceof ListChangeEvent)
               {
                  ListChangeEvent listEvent = (ListChangeEvent) e;
                  key = listEvent.getPos();
                  
                  // change the key to be the insertion index to avoid ambiguity
                  if (listEvent.getType() == CollectionChangeEvent.ADDED_BEFORE
                        || listEvent.getType() == CollectionChangeEvent.ADDED_AFTER
                        || listEvent.getType() == CollectionChangeEvent.REMOVED_AFTER)
                  {
                     
                     int index = 0;
                     List list = (List) listEvent.getCollection();
                     if (key != null)
                     {
                        // determine index
                        index = list.indexOf(key); // index of preceding or succeeding list element
                        if (listEvent.getType() == CollectionChangeEvent.ADDED_AFTER
                              || listEvent.getType() == CollectionChangeEvent.REMOVED_AFTER)
                        {
                           index++;
                        }
                        else if (listEvent.getType() == CollectionChangeEvent.ADDED_BEFORE)
                        {
                           index--;
                        }
                     }
                     else // no preceding or succeeding list element
                     {
                        if (listEvent.getType() == CollectionChangeEvent.ADDED_BEFORE)
                        {
                           index = list.size() - 1; // no succeeding element --> added as last element
                        }
                        else // listEvent.getType() == CollectionChangeEvent.ADDED_AFTER || listEvent.getType() == CollectionChangeEvent.REMOVED_AFTER
                        {
                           index = 0; // no preceding element --> added/removed as first element
                        }
                     }
                                          
                     key = Integer.valueOf(index);
                  }
               }
            }

            Change change;
            if (Change.Kind.ALTER_FIELD.equals(changeType))
            {
               if (key != null)
               {
                  change = repository.getChangeFactory().changeAlterField(
                        e.getSource(), e.getPropertyName(), key,
                        e.getOldValue(), e.getNewValue());
               }
               else
               {
                  change = repository.getChangeFactory().changeAlterField(
                        e.getSource(), e.getPropertyName(), e.getOldValue(),
                        e.getNewValue());
               }

            }
            else
            {
               throw new UnsupportedOperationException(
                     "Generate change for change kind " + changeType
                           + " unsupported!");
            }

            boolean knownToBeTransient = change.getField() != null
                  && Boolean.TRUE.equals(change.getField().isTransient());

            // check if the field is only locally stored
            if (change.getField() != null)
            {
               if (AnnotationUtil.getAnnotation(change.getField(), Local.class
                     .getName()) != null)
               {
                  ((MutableChange) change).setModifier(change.getModifier()
                        | TransactionEntry.MODIFIER_LOCAL);
               }
            }

            if (knownToBeTransient)
            {
               return;
            }

            if (change.getField() != null && change.getField().isReadOnly())
            {
               Versioning.log.warn("Omitted change for read-only field: "
                     + change);
               return;
            }

            if ( repository.changeWouldBeAccepted(change) )
            {
               ActionTransactionListener transactionListener = ActionTransactionListener.get();
               transactionListener.checkActiveTransaction(repository);
               repository.acknowledgeChange(change);

               markDirty(source);
            }
         }
      }
   }

   private void markDirty(Object source)
   {
      if (source instanceof ASGElement)
      {
         ASGElement element = (ASGElement) source;
         element.getProject().setSaved(false);
      }
   }


   /**
    * Used to handle transient attributes. Contents of this method should move into feature access
    * implementation.
    * 
    * @param e event that is examined
    * @param element ASGElement that effected the event
    * @return true if persistent
    */
   private boolean isPersistencyChange(ASGElement element, PropertyChangeEvent e)
   {
      if (!element.isPersistent())
      {
         return false;
      }
      
      if (e instanceof ConfigurablePropertyChangeEvent) {
		ConfigurablePropertyChangeEvent event = (ConfigurablePropertyChangeEvent) e;
		if (event.getCoobraIgnore() == ConfigurablePropertyChangeEvent.CoobraIgnore.COOBRA_IGNORE)
			return false;
	  }
      
      String name = e.getPropertyName();
      // FIXME !!! Remove dependency to MessageManager
      if (MessageManager.USER_MESSAGES_PROPERTY.equals(name))
      {
         return false; // ignore userMessages (transient)
      }

      if (element instanceof UMLLink
            && (UMLLink.SOURCE_ROLE_PROPERTY.equals(name) || UMLLink.TARGET_ROLE_PROPERTY.equals(name)))
      {
         return false;
      }

      if (element instanceof FProject)
      {
         if ((FProject.SAVED_PROPERTY.equals(name)
               || "gui".equals(name) || FProject.MANAGER_PROPERTY.equals(name)))
         {
            return false;
         }
         // ignore closing dependent project 
         if ( FProject.REQUIRES_PROPERTY.equals(name) )
         {
            FProject otherProject = (FProject) e.getNewValue();
            if ( otherProject != null && otherProject.getRepository() != null )
            {
               ChangeRecorder recorder = otherProject.getRepository().getDefaultChangeRecorder();
               if (recorder instanceof FujabaPropertyChangeRecorder)
               {
                  FujabaPropertyChangeRecorder changeRecorder = (FujabaPropertyChangeRecorder) recorder;
                  if ( changeRecorder.disabled )
                  {
                     return false;
                  }
               }
            }
         }
      }

      return true; // persist this change
   }


   /**
    * Disable recording of changes.
    */
   public void disable()
   {
      disabled = true;
   }
}

/*
 * $Log$
 * Revision 1.15  2007/03/30 13:57:45  cschneid
 * set 'saved' to false only if change must be stored
 *
 * Revision 1.14  2007/02/27 10:31:35  lowende
 * Removed tons of compile warnings.
 * Revision 1.13 2006/06/13 11:27:41 cschneid local
 * versioning support (CVS/SVN)
 * 
 * Revision 1.12 2006/05/18 06:44:23 cschneid added @interfaces for annotating properties, work on
 * multi-user features
 * 
 * Revision 1.11 2006/05/10 08:40:56 cschneid handle exception for derived attributes - still prints
 * warning
 * 
 * Revision 1.10 2006/05/09 13:18:56 cschneid several versioning fixes
 * 
 * Revision 1.9 2006/04/27 14:35:31 cschneid "uml" prefix removed from abstract, static and
 * visibility; added 'undoable' flag to CompositeTransaction
 * 
 * Revision 1.8 2006/04/06 12:06:24 cschneid added missing @Override everywhere
 * 
 * Revision 1.7 2006/02/15 15:49:12 lowende Visibility for classes fixed. Invalid javadoc flags
 * removed.
 * 
 */

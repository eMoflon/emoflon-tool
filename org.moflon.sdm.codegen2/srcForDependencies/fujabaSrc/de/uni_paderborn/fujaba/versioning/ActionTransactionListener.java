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

import java.awt.event.ActionEvent;

import de.uni_kassel.coobra.Repository;
import de.upb.lib.userinterface.ActionExecutionListenerEx;


/**
 * Wrapps each action in a transaction.
 *
 * @author    christian.schneider@uni-kassel.de
 * @version   $Revision$ $Date$
 * @created   10.06.2005, 15:00:35
 */
public class ActionTransactionListener implements ActionExecutionListenerEx
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static ActionTransactionListener instance;
   /**
    * Command name used if in place editing occurs (no action active).
    */
   public final static String INPLACE_EDITING_COMMAND = "inplace editing";


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public static ActionTransactionListener get()
   {
      if (instance == null)
      {
         instance = new ActionTransactionListener();
      }
      return instance;
   }


   /**
    *Constructor for class ActionTransactionListener
    */
   private ActionTransactionListener() { }


   /**
    * CompositeTransaction that is currently active.
    */
   private CompositeTransaction activeTransaction;


   /**
    * @return   CompositeTransaction that is currently active.
    */
   public synchronized CompositeTransaction getActiveTransaction()
   {
      return this.activeTransaction;
   }


   /**
    * This method will be called before actionPerformed() is executed.
    *
    * @param event  The action-defined event.
    */
   public void preActionNotify (ActionEvent event)
   {
      String actionCommand = event != null ? event.getActionCommand() : INPLACE_EDITING_COMMAND;
      preActionNotify (actionCommand);
   }


   public synchronized void preActionNotify (String name)
   {
      assert name != null;
      CompositeTransaction activeTransaction = this.activeTransaction;
      if (activeTransaction != null)
      {
         if (!INPLACE_EDITING_COMMAND.equals (activeTransaction.getName()))
         {
            throw new IllegalStateException ("Nested actions are not allowed!");
         }

         // perform postActionNotify after the INPLACE_EDITING_COMMAND check
         // completed, because otherwise the activeTransaction will be
         // commited, before it is finished! (FK, 02.05.2006)
         postActionNotify (null);
      }
      this.activeTransaction = new CompositeTransaction (name);
   }


   /**
    * This method will be called after actionPerformed was executed if no exception was thrown.
    *
    * @param event  The action-defined event.
    */
   public synchronized void postActionNotify (ActionEvent event)
   {
      if (this.activeTransaction == null)
      {
         //action failed
      }
      else
      {
         this.activeTransaction.commit();
      }
   }


   /**
    * This method will be called after actionPerformed was executed if an exception/throwable was thrown.
    *
    * @param event      The action-defined event.
    * @param throwable  the exception/error that occured
    * @return           true if the error was handled (though each listener will be notified)
    */
   public synchronized boolean postActionNotify (ActionEvent event, Throwable throwable)
   {
      if (this.activeTransaction == null)
      {
         new IllegalStateException ("No transaction active!").printStackTrace();
      }
      else
      {
         this.activeTransaction.abort();
      }
      return false;
   }


   /**
    * @param activeTransaction  The activeTransaction to set.
    */
   void setActiveTransaction (CompositeTransaction activeTransaction)
   {
      this.activeTransaction = activeTransaction;
   }


   /**
    * Start a transaction with {@link #INPLACE_EDITING_COMMAND} as name.
    *
    * @return   new transaction
    */
   public synchronized CompositeTransaction startInplaceEditing()
   {
      preActionNotify (INPLACE_EDITING_COMMAND);
      return this.activeTransaction;
   }


   public void checkActiveTransaction (Repository repository)
   {
      CompositeTransaction activeTransaction = getActiveTransaction();
      if (activeTransaction == null)
      {
         activeTransaction = startInplaceEditing();
      }
      if (!activeTransaction.hasInTransactions (repository.getActiveTransaction()))
      {
         if (!repository.getPersistencyModule().isOpened())
         {
            repository.getPersistencyModule().open (false);
         }
         activeTransaction.addToTransactions (repository.startTransaction (activeTransaction.getName()));
      }
   }
}

/*
 * $Log$
 * Revision 1.12  2007/04/12 19:00:08  rotschke
 * Changed visibility of checkActiveTransaction to public [tr].
 *
 */

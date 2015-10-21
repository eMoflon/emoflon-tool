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

import java.lang.reflect.InvocationTargetException;


/**
 * A class for running work intensive tasks in different threads and providing feedback on the result in (perhaps) yet another
 * (typically the AWT thread). Most Code taken from the original SwingWorker:
 *
 * This is the 3rd version of SwingWorker (also known as
 * SwingWorker 3), an abstract class that you subclass to
 * perform GUI-related work in a dedicated thread.  For
 * instructions on and examples of using this class, see:
 *
 * <a href="http://java.sun.com/products/jfc/swingdoc-current/threads2.html">
 * Swing Documents: Threads<\a> <br>
 *
 * Note that the API changed slightly in the 3rd version:
 * You must now invoke start() on the SwingWorker after
 * creating it.
 *
 * @author    <a href="mailto:creckord@upb.de">Carsten Reckord</a>
 * @version   $Revision$
 */
public abstract class ThreadWorker
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private Object value;


   /**
    * Get the value produced by the worker thread, or null if it
    * hasn't been constructed yet.
    *
    * @return
    */
   protected synchronized Object getValue()
   {
      return value;
   }


   /**
    * Set the value produced by worker thread
    *
    * @param x
    */
   synchronized void setValue (Object x)
   { //Why does this need to be synchronized?

      value = x;
   }


   /**
    * Class to maintain reference to current worker thread
    * under separate synchronization control.
    *
    * @author    $Author$
    * @version   $Revision$ $Date$
    */
   protected static interface ThreadVar
   {
      public Runnable get();


      public void clear();


      public void watch() throws InterruptedException;
   }


   protected final ThreadVar threadVar;


   /**
    * Create a thread that will, after being started, call the <code>construct</code> method and then exit.
    */
   public ThreadWorker()
   {
      exception = null;

      final Runnable doFinished =
         new Runnable()
         {
            public void run()
            {
               finished();
            }
         };

      Runnable doConstruct =
         new Runnable()
         {
            public void run()
            {
               try
               {
                  setValue (construct());
                  threadVar.clear();
                  scheduleFinished (doFinished);
               }
               catch (final Exception e)
               {
                  threadVar.clear();
                  ThreadWorker.this.exception = e;
                  final Runnable doError =
                     new Runnable()
                     {
                        public void run()
                        {
                           error (e);
                        }
                     };
                  scheduleFinished (doError);
               }
            }
         };

      threadVar = createThreadVar (doConstruct);
   }


   Exception exception;


   protected abstract ThreadVar createThreadVar (Runnable r);


   protected abstract void scheduleConstruct (Runnable r);


   protected abstract void scheduleFinished (Runnable r);


   /**
    * Compute the value to be returned by the <code>get</code> method.
    *
    * @return            No description provided
    * @throws Exception
    */
   public abstract Object construct() throws Exception;


   public void error (Throwable exception)
   {
   }


   /**
    * Called on the event dispatching thread (not on the worker thread) after the <code>construct</code>
    * method has returned.
    */
   public void finished()
   {
   } // finished


   public void start()
   {
      Runnable r = threadVar.get();
      if (r != null)
      {
         scheduleConstruct (r);
      }
   }


   /**
    * A new method that interrupts the worker thread. Call this method to force the worker
    * to abort what it's doing.
    *
    * @throws UnsupportedOperationException  if the worker implementation does not support interruption
    */
   public void interrupt()
   {
      throw new UnsupportedOperationException();
   }


   /**
    * Return the value created by the <code>construct</code> method.
    *
    * @return                            the value created by the <code>{@link #construct()}</code> method
    * @throws InterruptedException       if the worker thread was interrupted before completion
    * @throws InvocationTargetException
    * @throws InvocationTargetException  if the <code>{@link #construct()}</code> method throws an exception
    */
   public Object get() throws InterruptedException, InvocationTargetException
   {
      while (true)
      {
         final Runnable r = threadVar.get();
         if (r == null)
         {
            Object value = getValue(); //synchronize before throwing
            if (this.exception != null)
            {
               throw new InvocationTargetException (this.exception);
            }
            return value;
         }
         try
         {
            threadVar.watch();
         }
         catch (InterruptedException e)
         {
            Thread.currentThread().interrupt(); // propagate
            throw e;
         }
      }
   }
}

/*
 * $Log$
 * Revision 1.2  2007/03/15 16:36:26  cschneid
 * - Generating code is possible again
 * - projects failing to load entirely are removed from workspace
 *
 */

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
 *      Copyright (C) 1997-2004 Fujaba Development Group
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
 * Contact adress:
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
import java.util.LinkedList;
import java.util.Queue;

public final class FujabaWorkQueue
{
   private final Object lock = new Object ();
   private final Queue<Runnable> queue = new LinkedList<Runnable>();
   private Worker worker;
   
   private static final FujabaWorkQueue instance = new FujabaWorkQueue();
   
   protected static FujabaWorkQueue get ()
   {
      return instance;
   }
   
   public static void invokeLater (final Runnable runnable)
   {
      get ().enqueue (runnable);
   }
   
   public static void invokeAndWait (final Runnable runnable) throws InterruptedException, InvocationTargetException
   {
      ensureNotInWorkThread ();
      final Object lock = new Object ();
      final NotifyingRunnable notify = new NotifyingRunnable(runnable, lock);
      synchronized (lock)
      {
         invokeLater(notify);
         lock.wait();
      }
      if (notify.exception != null)
      {
         throw new InvocationTargetException(notify.exception);
      }
   }
   
   protected void enqueue (final Runnable runnable)
   {
      synchronized (lock)
      {
         ensureWorkThread ();
         if (queue.offer(runnable))
         {
            lock.notify();
         }
      }
   }

   public void stop () throws InterruptedException
   {
      stop (-1);
   }
   
   public void stop (long timeout) throws InterruptedException
   {
      synchronized (lock)
      {
         if (this.worker != null)
         {
            this.worker.abort ();
         }
         if (timeout != 0)
         {
            try
            {
               if (timeout < 0)
               {
                  this.worker.join();
               }
               else
               {
                  this.worker.join(timeout);
               }
            }
            finally
            {
               disconnectWorker(worker);
            }
         }
      }
   }
   
   private static void ensureNotInWorkThread ()
   {
      final Thread worker = get ().worker;
      if (worker != null && Thread.currentThread() == worker)
      {
         throw new Error ("Cannot invokeAndWait() from inside the FujabaWorkThread");
      }
   }
   
   private void ensureWorkThread ()
   {
      if (this.worker == null)
      {
         this.worker = new Worker();
         this.worker.start();
      }
   }
   
   Runnable dequeue () throws InterruptedException
   {
      synchronized (lock)
      {
         if (queue.size()==0)
         {
            lock.wait();
         }
         return queue.poll();
      }
   }
   
   void disconnectWorker (Thread thread)
   {
      synchronized (lock)
      {
         if (this.worker == thread)
         {
            this.worker = null;
         }
      }
   }
   
   private static final class NotifyingRunnable implements Runnable
   {
      private final Runnable runnable;
      private final Object lock;
      Throwable exception; 
      
      public NotifyingRunnable (final Runnable runnable, final Object lock)
      {
         this.runnable = runnable;
         this.lock = lock;
      }
      
      public void run ()
      {
         try
         {
            runnable.run();
         }
         catch (Exception e)
         {
            this.exception = e;
         }
         finally
         {
            synchronized (lock)
            {
               lock.notify();
            }
         }
      }
   }
   
   private final class Worker extends Thread
   {
      private boolean aborted = false;
      
      public Worker ()
      {
         super ("FujabaWorkThread");
         setPriority(MIN_PRIORITY);
         setDaemon(true);
      }
      
      @Override
      public void run ()
      {
         try
         {
            while (!aborted)
            {
               final Runnable runnable = dequeue();
               if (runnable != null)
               {
                  runnable.run();
               }
            }
         }
         catch (InterruptedException ie)
         {
         }
         finally
         {
            doAbort ();
         }
      }
      
      public void abort ()
      {
         this.aborted = true;
         this.interrupt();
      }
      
      private void doAbort ()
      {
         disconnectWorker (this);
      }
   }
}

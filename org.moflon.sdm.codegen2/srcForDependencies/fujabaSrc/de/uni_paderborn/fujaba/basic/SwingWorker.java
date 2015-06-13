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

import javax.swing.SwingUtilities;


/**
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
 * Most of the original implementation was moved to {@link de.uni_paderborn.fujaba.basic.ThreadWorker}
 * 
 * @author    $Author$
 * @version   $Revision$
 */
public abstract class SwingWorker extends ThreadWorker
{
   /** 
    * Class to maintain reference to current worker thread
    * under separate synchronization control.
    */
   private static class JThreadVar implements ThreadVar
   {
      private Thread thread;

      JThreadVar (Thread t)
      {
         thread = t;
      }

      public synchronized Thread get ()
      {
         return thread;
      }

      public synchronized void clear ()
      {
         thread = null;
      }
      
      public synchronized void watch () throws InterruptedException
      {
         if (thread != null)
         {
            thread.join();
         }
      }
   }

   @Override
   protected ThreadVar createThreadVar (Runnable r)
   {
      Thread t = new Thread (r);
      return new JThreadVar(t);
   }

   @Override
   protected void scheduleConstruct (Runnable r)
   {
      ((Thread)r).start();
   }

   @Override
   protected void scheduleFinished (Runnable r)
   {
      SwingUtilities.invokeLater(r);
   }

   /**
    * Compute the value to be returned by the <code>get</code> method.
    *
    * @return   No description provided
    */
   @Override
   public abstract Object construct();

   /**
    * A new method that interrupts the worker thread. Call this method to force the worker
    * to abort what it's doing.
    */
   @Override
   public void interrupt ()
   {
      Thread t = (Thread) threadVar.get();
      if (t != null)
      {
         t.interrupt();
      }
      threadVar.clear();
   }
}

/*
 * $Log$
 * Revision 1.14  2006/04/06 12:04:34  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.13  2006/03/15 19:00:06  creckord
 * Load/Save:
 * - ProgressHandler for loading/saving
 * - ProgressBar shown for loading/saving (incl. auto-restore on startup)
 * - changes to ProjectLoader/Writer interface: just 1 method to implement/override now
 *
 * Threads:
 * - introduced FujabaWorkQueue and FujabaWorker to perform non-GUI tasks (esp. long-running, like loading/saving) outside the AWT event thread
 * - updated SwingWorker to current version (SwingWorker 3)
 *
 * Revision 1.12  2004/10/20 17:49:28  schneider
 * Introduction of interfaces for class diagram classes
 *
 */

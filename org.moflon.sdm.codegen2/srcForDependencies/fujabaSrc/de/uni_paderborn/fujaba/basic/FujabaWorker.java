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


import javax.swing.*;


/**
 * Worker similar to the {@link de.uni_paderborn.fujaba.basic.SwingWorker}.
 * 
 * The Method <code>{@link #construct()}</code> is run under the
 * {@link de.uni_paderborn.fujaba.basic.FujabaWorkQueue FujabaWorkThread} and the result
 * notification with either
 * <code>{@link de.uni_paderborn.fujaba.basic.ThreadWorker#finished ()}</code> or
 * <code>{@link ThreadWorker#error(Throwable)}</code> is run under
 * the {@link java.awt.EventQueue AWT event thread}.
 * 
 * This reflects the usual use case that some computationally expensive task (like loading a big
 * project) is triggered by a GUI action, should run outside the AWT Event Dispatch Thread and
 * afterwards provide some sort of GUI feedback (which, again, should happen in the AWT thread).
 * 
 * @author <a href="mailto:creckord@upb.de">Carsten Reckord</a>
 * @version $Revision$
 */
public abstract class FujabaWorker extends ThreadWorker
{
   /**
    * Compute the value to be returned by the <code>get</code> method.
    * 
    * @see de.uni_paderborn.fujaba.basic.ThreadWorker#construct()
    */
   @Override
   public abstract Object construct() throws Exception;

   /**
    * Class to maintain reference to current worker thread under separate synchronization control.
    */
   protected static class FThreadVar implements ThreadVar
   {
      private Runnable worker;


      FThreadVar(Runnable t)
      {
         worker = t;
      }


      /**
       * @see de.uni_paderborn.fujaba.basic.ThreadWorker.ThreadVar#get()
       */
      public synchronized Runnable get()
      {
         return worker;
      }


      /**
       * @see de.uni_paderborn.fujaba.basic.ThreadWorker.ThreadVar#clear()
       */
      public synchronized void clear()
      {
         worker = null;
         notifyAll();
      }


      /**
       * @see de.uni_paderborn.fujaba.basic.ThreadWorker.ThreadVar#watch()
       */
      public synchronized void watch() throws InterruptedException
      {
         if (worker != null)
         {
            this.wait();
         }
      }
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.ThreadWorker#createThreadVar(java.lang.Runnable)
    */
   @Override
   protected ThreadVar createThreadVar(Runnable r)
   {
      return new FThreadVar(r);
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.ThreadWorker#scheduleConstruct(java.lang.Runnable)
    */
   @Override
   protected void scheduleConstruct(Runnable r)
   {
      FujabaWorkQueue.invokeLater(r);
   }


   /**
    * @see de.uni_paderborn.fujaba.basic.ThreadWorker#scheduleFinished(java.lang.Runnable)
    */
   @Override
   protected void scheduleFinished(Runnable r)
   {
      SwingUtilities.invokeLater(r);
   }
   
}

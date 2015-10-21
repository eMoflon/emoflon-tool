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
package de.uni_kassel.fujaba.codegen.sequencer;

import java.util.Iterator;
import java.util.ListIterator;

import de.uni_kassel.fujaba.codegen.rules.NoCodeToken;
import de.upb.tools.fca.FLinkedList;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class Seq extends Flow implements NoCodeToken
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private FLinkedList items = new FLinkedList();


   /**
    * Get the items attribute of the Seq object
    *
    * @return   The items value
    */
   private FLinkedList getItems()
   {
      return items;
   } // getItems


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param flow  No description provided
    */
   public void add (Flow flow)
   {
      if (flow instanceof Seq)
      {
         FLinkedList list =  ((Seq) flow).getItems();

         Object obj;
         Iterator tmpIter = list.iterator();

         while (tmpIter.hasNext())
         {
            obj = tmpIter.next();
            // HACK: AZ
            // iter.add (obj); does not work for an empty iterator iter.
            // however, iter is initialized to the end of items
            // Thus, items.add should do the job, too.
            //Flow flow2 = (Flow) obj;
            //addToChildren (flow2);
            items.add (obj);

            // next line causes trouble and seems not necessary
            // tmpIter.remove ();
         }
      }
      else
      {
         //addToChildren (flow);
         items.add (flow);
      }
   } // add


   /**
    * Get the empty attribute of the Seq object
    *
    * @return   The empty value
    */
   public boolean isEmpty()
   {
      return items.isEmpty();
   } // isEmpty


   /**
    * Get the lastOfItems attribute of the Seq object
    *
    * @return   The lastOfItems value
    */
   public Object getLastOfItems()
   {
      return  ( (this.items.isEmpty() == true)
         ? null
         : this.items.getLast());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param flowActivity  No description provided
    */
   public void removeLastFlowActivity (FlowActivity flowActivity)
   {
      if (!items.isEmpty())
      {
         Flow item = (Flow) getLastOfItems();
         if (item == flowActivity)
         {
            removeFromChildren (item);
            items.removeLast();
         }
         else
         {
            item.removeLastFlowActivity (flowActivity);
         }
      }
   } // removeLastFlowActivity


   /**
    * @return   short string representation of current object
    */
   public String toString()
   {
      StringBuffer result = new StringBuffer();

      result.append ("Seq[]");

      return result.toString();
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String context = "children";


   /**
    * Get the context attribute of the Seq object
    *
    * @return   The context value
    */
   public String getContext()
   {
      return context;
   }


   /**
    * Sets the context attribute of the Seq object
    *
    * @param context  The new context value
    */
   public void setContext (String context)
   {
      this.context = context;
   }


   @Override
   public ListIterator iteratorOfChildren()
   {
      return items.listIterator();
   }

   
}

/*
 * $Log$
 * Revision 1.3  2005/12/16 16:36:40  l3_g5
 * some bugfixing
 *
 * Revision 1.2  2005/12/02 18:19:50  l3_g5
 * bootstrapping works!
 *
 * Revision 1.1  2005/06/11 09:52:27  l3_g5
 * adapted sequencer -> codegen2 now independent from old codegen
 *
 */

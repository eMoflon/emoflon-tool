/*
 * Copyright (c) 2004-2006 Software Engineering Kassel, various authors,
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'CoObRA' nor 'CoObRA2' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.uni_kassel.coobra.transactions;

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.util.ConcurrentLinkedList;
import de.uni_kassel.util.EmptyIterator;
import de.uni_kassel.util.ReverseIterator;

import java.util.Iterator;
import java.util.List;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 02.06.2004, 18:24:12
 */
public class MutableTransaction extends AbstractMutableTransaction
{
   private List<MutableTransactionEntry> entries;


   public MutableTransaction( Repository repository, String name, long timestamp, int modifier )
   {
      super(repository, name, timestamp, modifier);
   }

   /**
    * Construct an iterator though all entries.
    *
    * @return an Iterator which returns TransactionEntry upon call of next().
    */
   public Iterator<? extends MutableTransactionEntry> iterator()
   {
      if ( this.entries != null )
      {
         return this.entries.iterator();
      } else
      {
         return EmptyIterator.get();
      }
   }

   protected boolean addToEntries( MutableTransactionEntry value )
   {
      boolean changed = false;
      if ( value != null )
      {
         List<MutableTransactionEntry> entries = this.entries;
         if ( entries == null )
         {
            entries = new ConcurrentLinkedList<MutableTransactionEntry>();
            this.entries = entries;
         }
         if ( entries.size() == 0 || entries.get( entries.size() - 1 ) != value )
         {
            changed = entries.add( value );
            if ( changed )
            {
               value.setEnclosingTransaction( this );
            }
         }
      }
      return changed;
   }

   public void removeAllFromEntries()
   {
      for ( Iterator<? extends MutableTransactionEntry> it = iterator(); it.hasNext(); )
      {
         MutableTransactionEntry entry = it.next();
         this.removeFromEntries( entry );
      }
   }

   /**
    * deletes all entries in this transaction
    */
   protected void deleteAllEntries()
   {
      if ( entries != null )
      {
         for ( TransactionEntry entry : entries )
         {
            entry.delete();
         }
         entries.clear();
      }
   }

   public boolean removeFromEntries( MutableTransactionEntry value )
   {
      boolean changed = false;
      if ( ( this.entries != null ) && ( value != null ) )
      {
         changed = this.entries.remove( value );
         if ( changed )
         {
            value.setEnclosingTransaction( null );
         }
      }
      return changed;
   }

   public int sizeOfEntries()
   {
      return ( ( this.entries == null )
            ? 0
            : this.entries.size() );
   }

   /**
    * appends the entry at the end of this transaction.
    *
    * @param entry what to append
    */
   public void add( MutableTransactionEntry entry )
   {
      addToEntries( entry );
   }

   /**
    * removes the entry from this transaction.
    *
    * @param entry what to remove
    */
   public void remove( TransactionEntry entry )
   {
      if ( entries != null )
      {
         // to optimize performance we guess that the change is at the end of the list
         int index = entries.lastIndexOf(entry);
         entries.remove( index );
      }
   }

   /**
    * Iterates the transaction entries in reverse order.
    *
    * @return an iterator through the transaction entries, starting at the last entry.
    */
   public Iterator<MutableTransactionEntry> iteratorReverse()
   {
      if ( entries != null )
      {
         return new ReverseIterator<MutableTransactionEntry>( entries );
      } else
      {
         return EmptyIterator.get();
      }
   }

   @Override
   public void delete()
   {
      deleteAllEntries();
      super.delete();
   }
}

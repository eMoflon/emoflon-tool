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

package de.uni_kassel.coobra.persistency;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.MutableChange;
import de.uni_kassel.coobra.transactions.MutableTransaction;
import de.uni_kassel.coobra.transactions.MutableTransactionEntry;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 23.08.2004, 10:36:04
 */
public class MemoryPersistencyModule extends PersistencyModule
{
    protected void delete( MemoryTransactionEntry entry )
    {       
       if ( getFirst() == entry )
       {
          setFirst( entry.getNext() );
       }
       if ( getLast() == entry )
       {
          setLast( entry.getPrevious() );
       }
       entry.delete();
    }

    interface MemoryTransactionEntry extends MutableTransactionEntry
    {
       /**
        * @return current value of the field next
        */
       MemoryTransactionEntry getNext();

       /**
        * @param value new value for field next
        * @return true if next was changed
        */
       boolean setNext( MemoryTransactionEntry value );

       /**
        * @return current value of the field previous
        */
       MemoryTransactionEntry getPrevious();

       /**
        * @param value new value for field previous
        * @return true if previous was changed
        */
       boolean setPrevious( MemoryTransactionEntry value );
    }

   interface MemoryChange extends MemoryTransactionEntry, Change
   {

   }

   protected interface MemoryTransaction extends MemoryTransactionEntry, Transaction
   {

   }

   protected static class MemoryChangeImpl extends MutableChange implements MemoryChange
   {
      /**
       * removes this transaction entry from memory.
       */
      @Override
      public void delete()
      {
         MemoryTransactionEntry previous = getPrevious();
         MemoryTransactionEntry next = getNext();
         if ( next != null )
         {
            next.setPrevious( previous );
         }
         if ( previous != null )
         {
            previous.setNext( next );
         }
         super.delete();
      }

      /**
       * Set the transaction that contains this entry.
       *
       * @param value the transaction where this entry is added, may be null
       */
      public void setEnclosingTransaction( MemoryTransaction value )
      {
         setEnclosingTransaction( ( MutableTransaction ) value );
      }

      public MemoryChangeImpl( Change change )
      {
         super( change );
      }

      public MemoryChangeImpl()
      {
         super();
      }

      /**
       * store value for field next
       */
      private MemoryTransactionEntry next;

      /**
       * @return current value of the field next
       */
      public MemoryTransactionEntry getNext()
      {
         return this.next;
      }

      /**
       * @param value new value for field next
       * @return true if next was changed
       */
      public boolean setNext( final MemoryTransactionEntry value )
      {
         if ( this.getModifier() == MODIFIER_INVALID )
         {
            throw new IllegalStateException("modifier is invalid");
         }
         final MemoryTransactionEntry oldValue = this.next;
         boolean changed = false;
         if ( oldValue != value )
         {
            if ( oldValue != null )
            {
               this.next = null;
               oldValue.setPrevious( null );
            }
            this.next = value;
            if ( value != null )
            {
               value.setPrevious( this );
            }
            changed = true;
         }
         return changed;
      }

      /**
       * store value for field previous
       */
      private MemoryTransactionEntry previous;

      /**
       * @return current value of the field previous
       */
      public MemoryTransactionEntry getPrevious()
      {
         return this.previous;
      }

      /**
       * @param value new value for field previous
       * @return true if previous was changed
       */
      public boolean setPrevious( final MemoryTransactionEntry value )
      {
         if ( this.getModifier() == MODIFIER_INVALID )
         {
            throw new IllegalStateException("modifier is invalid");
         }
         final MemoryTransactionEntry oldValue = this.previous;
         boolean changed = false;
         if ( oldValue != value )
         {
            if ( oldValue != null )
            {
               this.previous = null;
               oldValue.setNext( null );
            }
            this.previous = value;
            if ( value != null )
            {
               value.setNext( this );
            }
            changed = true;
         }
         return changed;
      }
   }

   static class MemoryTransactionImpl extends MutableTransaction implements MemoryTransaction
   {
      public MemoryTransactionImpl( Transaction transaction )
      {
         super( transaction.getRepository(), transaction.getName(), transaction.getTimeStamp(), transaction.getModifier() );
         setStatus( transaction.getStatus() );
         setReference( transaction.getReference() );
      }

      /**
       * removes this transaction entry from memory.
       */
      @Override
      public void delete()
      {
         MemoryTransactionEntry previous = getPrevious();
         MemoryTransactionEntry next = getNext();
         if ( next != null )
         {
            next.setPrevious( previous );
         }
         if ( previous != null )
         {
            previous.setNext( next );
         }
         super.delete();
      }

      /**
       * store value for field next
       */
      private MemoryTransactionEntry next;

      /**
       * @return current value of the field next
       */
      public MemoryTransactionEntry getNext()
      {
         return this.next;
      }

      /**
       * @param value new value for field next
       * @return true if next was changed
       */
      public boolean setNext( final MemoryTransactionEntry value )
      {
         final MemoryTransactionEntry oldValue = this.next;
         boolean changed = false;
         if ( oldValue != value )
         {
            if ( oldValue != null )
            {
               this.next = null;
               oldValue.setPrevious( null );
            }
            this.next = value;
            if ( value != null )
            {
               value.setPrevious( this );
            }
            changed = true;
         }
         return changed;
      }

      /**
       * store value for field previous
       */
      private MemoryTransactionEntry previous;

      /**
       * @return current value of the field previous
       */
      public MemoryTransactionEntry getPrevious()
      {
         return this.previous;
      }

      /**
       * @param value new value for field previous
       * @return true if previous was changed
       */
      public boolean setPrevious( final MemoryTransactionEntry value )
      {
         final MemoryTransactionEntry oldValue = this.previous;
         boolean changed = false;
         if ( oldValue != value )
         {
            if ( oldValue != null )
            {
               this.previous = null;
               oldValue.setNext( null );
            }
            this.previous = value;
            if ( value != null )
            {
               value.setNext( this );
            }
            changed = true;
         }
         return changed;
      }
   }

   public boolean isReadonly()
   {
      return readonly;
   }

   private boolean readonly;

   /**
    * Query if this PersistencyModule is already opened.
    *
    * @return true if opened
    */
   @Override
   public boolean isOpened()
   {
      return true;
   }

   /**
    * does nothing - memory can be used always.
    *
    * @param readonly ignored
    */
   @Override
   public void open( boolean readonly )
   {
      this.readonly = readonly;
   }

   /**
    * does nothing - memory can be used always.
    */
   @Override
   public void close()
   {

   }

   /**
    * Simply returns transaction - transaction info stays in memory.
    *
    * @param transaction       what to store
    * @param activeTransaction
    * @return transaction
    */
   @Override
   public Transaction send( final Transaction transaction, Transaction activeTransaction )
   {
      if ( activeTransaction != null && !( activeTransaction instanceof MemoryTransaction ) )
      {
         throw new ClassCastException( getClass().getName() + ".send must be called with MemoryTransaction as parameter!" );
      }
      MemoryTransaction newEntry = createMemoryTransaction( transaction );
      append( newEntry );
      newEntry.setEnclosingTransaction( ( MemoryTransactionImpl ) activeTransaction );
      return newEntry;
   }

   protected MemoryTransaction createMemoryTransaction( final Transaction transaction )
   {
      return new MemoryTransactionImpl( transaction );
   }

   /**
    * Stores the change as a MemoryChange and returns that.
    *
    * @param change            what to store
    * @param activeTransaction currently active transaction
    * @return memory copy of the change
    */
   @Override
   public Change send( final Change change, Transaction activeTransaction )
   {
      if (Change.Kind.UNDEFINED.equals(change.getKind()))
      {
         throw new IllegalArgumentException("Cannot send UNDEFINED changes");
      }
      MemoryChange newEntry = createMemoryChange(change, (MemoryTransaction) activeTransaction);
      if (!Change.Kind.UNDEFINED.equals(newEntry.getKind()))
      {
         append( newEntry );
      }
      return newEntry;
   }

   protected MemoryChangeImpl createMemoryChange( final Change change, MemoryTransaction activeTransaction )
   {
      final MemoryChangeImpl memoryChange = new MemoryChangeImpl( change );
      memoryChange.setEnclosingTransaction( activeTransaction );
      return memoryChange;
   }

   protected void append( MemoryTransactionEntry newEntry )
   {
      if ( last != null )
      {
         last.setNext( newEntry );
      }
      last = newEntry;
      if ( first == null )
      {
         first = newEntry;
      }
   }

   /**
    * remember first stored change.
    */
   private MemoryTransactionEntry first;

   /**
    * remember last stored change.
    */
   private MemoryTransactionEntry last;

   protected MemoryTransactionEntry getFirst()
   {
      return first;
   }

   protected void setFirst( MemoryTransactionEntry first )
   {
      this.first = first;
   }

   protected MemoryTransactionEntry getLast()
   {
      return last;
   }

   protected void setLast( MemoryTransactionEntry last )
   {
      this.last = last;
   }

   /**
    * Read the change following the specified TransactionEntry. If entry is a Transaction the first enclosed change is
    * returned, if entry is a change the next change is returned. If no changes follow the specified entry null is
    * returned.
    * If affected object is not null only the changes to this object are taken into account.
    * If versionName is not null only changes up to specified version are taken into account (null is returned if entry
    * is the last change in this version or if entry occured after this version).
    *
    * @param entry          preceeding entry
    * @param filter         applied filter
    * @return the next change
    * @throws PersistencyException if the operation fails
    */
   @Override
   public TransactionEntry receiveNext(TransactionEntry entry, EntryFilter filter)
   {
      entry = entry != null ? ( ( MemoryTransactionEntry ) entry ).getNext() : first;

      if ( filter != null && entry != null ) {
         // todo: optimize search for next change to an object with a map?
         while ( entry != null && !filter.accept( entry ) )
         {
            entry = ( ( MemoryTransactionEntry ) entry ).getNext();
         }
      }
      return entry;
   }

   @Override
   public TransactionEntry receivePrevious(TransactionEntry entry, EntryFilter filter)
   {
      if ( filter != null )
      {
         throw new UnsupportedOperationException( "not yet implemented" );
      }
      return ( ( MemoryTransactionEntry ) entry ).getPrevious();
   }

   public Transaction resolveTransaction(TransactionReference reference)
   {
      throw new UnsupportedOperationException("TransactionReferences are not used by memory persistency modules");
   }

   /**
    * clear all changes from memory!
    */
   public void clear() {
       setFirst( null );
       setLast( null );
   }
}

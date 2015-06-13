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

import de.uni_kassel.coobra.AbstractChange;
import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.transactions.AbstractMutableTransaction;
import de.uni_kassel.coobra.transactions.MutableTransactionEntry;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;
import de.uni_kassel.features.FieldHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 01.12.2004, 12:12:19
 */
public class CachedPersistencyModule extends MemoryPersistencyModule
{
   //todo: regard the cacheLength!
   private final int cacheLength;

   public CachedPersistencyModule( PersistencyModule delegate, int cacheLength )
   {
      if ( delegate == null )
      {
         throw new NullPointerException( "null delegate not allowed" );
      }
      this.delegate = delegate;
      this.cacheLength = cacheLength;
   }

   private final PersistencyModule delegate;

   @Override
   public void open( boolean readonly ) throws PersistencyException
   {
      super.open( readonly );
      delegate.open( readonly );
   }

   /**
    * Query if this PersistencyModule is already opened.
    *
    * @return true if opened
    */
   @Override
   public boolean isOpened()
   {
      return delegate.isOpened();
   }

   @Override
   public void close() throws PersistencyException
   {
      super.close();
      delegate.close();
   }

   @Override
   public Transaction send( Transaction transaction, Transaction activeTransaction ) throws PersistencyException
   {
      final Transaction sentTransaction = delegate.send( transaction, activeTransaction != null ? ( ( TransactionProxy ) activeTransaction ).delegate : null );
      final TransactionProxy transactionProxy = wrap( sentTransaction );
      transactionProxy.setEnclosingTransaction( ( TransactionProxy ) activeTransaction );
      return transactionProxy;
   }

   private EntryProxy wrap( final TransactionEntry entry )
   {
      if ( entry instanceof Transaction )
      {
         return wrap( ( Transaction ) entry );
      } else
      {
         return wrap( ( Change ) entry );
      }
   }

   /**
    * @return persistency module behind this module
    */
   public PersistencyModule getDelegate()
   {
      return delegate;
   }

   private interface EntryProxy extends MemoryTransactionEntry
   {
      TransactionEntry getDelegate();
      boolean isNextEndOfCache();
   }

   private TransactionProxy wrap( final Transaction sentTransaction )
   {
      TransactionProxy proxy = transactionProxies.get( sentTransaction );
      if ( proxy == null )
      {
         proxy = new TransactionProxy( sentTransaction );
         transactionProxies.put( sentTransaction, proxy );
         Transaction enclosingTransaction = sentTransaction.getEnclosingTransaction();
         if (enclosingTransaction != null)
         {
            proxy.setEnclosingTransaction(wrap(enclosingTransaction));
         }
      }
      return proxy;
   }

   protected class TransactionProxy extends MemoryTransactionImpl implements EntryProxy
   {
      /**
       * Query auto resolve property. True by default.
       *
       * @return true if getters for affected object, new value, old value and key always return object references.
       * @see #setAutoResolving(boolean)
       */
      @Override
      public boolean isAutoResolving()
      {
         return delegate.isAutoResolving();
      }

      /**
       * Enable/disable automatic resolving of affected object, new value, old value and key.
       *
       * @param enabled if set to false ID are not resolved when getters are called
       * @see #isAutoResolving()
       */
      @Override
      public void setAutoResolving( boolean enabled )
      {
         delegate.setAutoResolving( enabled );
      }

      /**
       * Returns a string representation of the object.
       *
       * @return a string representation of the object.
       */
      @Override
      public String toString()
      {
         return delegate.toString();
      }

      public TransactionProxy( Transaction delegate )
      {
         super( delegate );
         this.delegate = delegate;
         delegate.addPropertyChangeListener( new PropertyChangeListener()
         {
            public void propertyChange( PropertyChangeEvent evt )
            {
               firePropertyChange( evt.getPropertyName(), evt.getOldValue(), evt.getNewValue() );
            }
         } );
      }

      private final Transaction delegate;

      public Transaction getDelegate()
      {
         return delegate;
      }

      public boolean isNextEndOfCache()
      {
         return nextIsEndOfCache;
      }

      /**
       * removes this transaction entry from memory.
       */
      @Override
      public void delete()
      {
         super.delete();
         delegate.delete();
      }

      /**
       * query the name of the transaction.
       *
       * @return a name for this transaction (commonly not unique)
       */
      @Override
      public String getName()
      {
         return delegate.getName();
      }

      /**
       * Query repository this entry belongs to.
       *
       * @return repository this entry belongs to.
       */
      @Override
      public Repository getRepository()
      {
         return delegate.getRepository();
      }

      /**
       * indicates when the transaction was started.
       *
       * @return the return value of {@link System#currentTimeMillis()} upon occurence of the first change
       */
      @Override
      public long getTimeStamp()
      {
         return delegate.getTimeStamp();
      }

      /**
       * status of the Transaction (see Status enum)
       *
       * @return value from Status enum
       */
      @Override
      public Status getStatus()
      {
         return delegate.getStatus();
      }

      /**
       * Setter for field status. Set by rollback and commit only!
       *
       * @param value new value
       */
      @Override
      public void setStatus( final Status value )
      {
         if ( delegate instanceof AbstractMutableTransaction)
         {
            ( (AbstractMutableTransaction) delegate ).setStatus( value );
         } else if ( delegate != null )
         {
            throw new UnsupportedOperationException( "setStatus is only possible if delegate is a MutableTransaction" );
         } else
         {
            //still initializing
         }
      }

      /**
       * Query reference object that is the same for all copies of this transaction. Which especially means that the
       * reference object has the same ID for all instances of this transaction.
       *
       * @return reference object or ID
       */
      @Override
      public TransactionReference getReference()
      {
         return delegate.getReference();
      }



      private boolean nextIsEndOfCache = true;

      /**
       * @return current value of the field next
       */
      public MemoryTransactionEntry getNext()
      {
         if ( !nextIsEndOfCache )
         {
            return super.getNext();
         } else
         {
            throw new UnsupportedOperationException( "end of cache" );
         }
      }

      /**
       * @param value new value for field next
       * @return true if next was changed
       */
      public boolean setNext( final MemoryTransactionEntry value )
      {
         if ( super.setNext(value) )
         {
            nextIsEndOfCache = false;
            return true;
         }
         else
         {
            return false;
         }
      }

      @Override
      public Iterator<? extends MutableTransactionEntry> iterator()
      {
         return new WrappingIterator( super.iterator(), delegate.iterator() );
      }

      @Override
      public Iterator<MutableTransactionEntry> iteratorReverse()
      {
         readCompletely();
         return super.iteratorReverse();
      }

      private void readCompletely()
      {
         for (Iterator<? extends MutableTransactionEntry> it = iterator(); it.hasNext();)
         {
             it.next();
         }
      }

      private class WrappingIterator implements Iterator<MutableTransactionEntry>
      {
         private final Iterator<? extends MutableTransactionEntry> superIterator;
         private final Iterator<? extends TransactionEntry> delegateIterator;
         private int superInAdvanceOfDelegate;
         private EntryProxy nextProxy;
         private boolean fetched;

         public WrappingIterator(Iterator<? extends MutableTransactionEntry> superIterator,
                                 Iterator<? extends TransactionEntry> delegateIterator)
         {
            this.superIterator = superIterator;
            this.delegateIterator = delegateIterator;
         }

         public boolean hasNext()
         {
            fetch();
            return nextProxy != null;
         }

         public MutableTransactionEntry next()
         {
            fetch();
            if ( nextProxy != null )
            {
               fetched = false;
               return nextProxy;
            }
            else
            {
               throw new NoSuchElementException();
            }
         }

         private void fetch()
         {
            if ( ! fetched )
            if ( superInAdvanceOfDelegate >= 0 && superIterator.hasNext() )
            {
               nextProxy = (EntryProxy) superIterator.next();
               superInAdvanceOfDelegate++;
            }
            else
            {
               if ( superInAdvanceOfDelegate < 0 || nextProxy == null )
               {
                  while ( superInAdvanceOfDelegate > 0 )
                  {
                     delegateIterator.next();
                     superInAdvanceOfDelegate--;
                  }
                  if ( delegateIterator.hasNext() )
                  {
                     EntryProxy prevProx = nextProxy;
                     nextProxy = wrap( delegateIterator.next() );
                     if ( prevProx != null && prevProx.isNextEndOfCache() )
                     {
                         append( nextProxy );
                     }
                     superInAdvanceOfDelegate--;
                  }
                  else
                  {
                     nextProxy = null;
                  }
               } else if ( nextProxy.isNextEndOfCache() )
               {
                  nextProxy = (EntryProxy) receiveNextFromTransaction(nextProxy, TransactionProxy.this);
               } else
               {
                  nextProxy = null;
               }
            }
            fetched = true;
         }

         public void remove()
         {
            throw new UnsupportedOperationException();
         }
      }
   }

   protected class ChangeProxy extends AbstractChange implements MemoryChange, EntryProxy
   {
      /**
       * Returns a string representation of the object.
       *
       * @return a string representation of the object.
       */
      @Override
      public String toString()
      {
         return delegate.toString();
      }

      /**
       * Query if this change was rolled back and not recommitted so far. (persistent: after store and load this property
       * is the same)
       *
       * @return true if this change was rolled back and not recommitted so far.
       */
      public boolean isRolledback()
      {
         return delegate.isRolledback();
      }

      public ChangeProxy( Change delegate )
      {
         if ( delegate == null )
         {
            throw new NullPointerException( "delegate cannot be null" );
         }
         this.delegate = delegate;
         Transaction transaction = delegate.getEnclosingTransaction();
         if ( transaction != null )
         {
            setEnclosingTransaction( wrap( transaction ) );
         }
      }

      private final Change delegate;

      public Change getDelegate()
      {
         return delegate;
      }

      public boolean isNextEndOfCache()
      {
         return nextIsEndOfCache;
      }

      public void commit()
      {
         delegate.commit();
      }

      public boolean isManagementEntry()
      {
         return delegate.isManagementEntry();
      }

      public boolean isLocal()
      {
         return delegate.isLocal();
      }

      /**
       * removes this transaction entry from memory.
       */
      public void delete()
      {
         previousIsEndOfCache = true;
         nextIsEndOfCache = true;
         delegate.delete();
         if ( getLast() == this )
         {
            setLast( previous );
         }
         if ( getFirst() == this )
         {
            setFirst( next );
         }
         if ( getNext() != null )
         {
            getNext().setPrevious( getPrevious() );
         } else if ( getPrevious() != null )
         {
            getPrevious().setNext( null );
         }
      }

      public ID getAffectedObjectID()
      {
         return delegate.getAffectedObjectID();
      }

      public void setAutoResolving( boolean enabled )
      {
         delegate.setAutoResolving( enabled );
      }

      public boolean isAutoResolving()
      {
         return delegate.isAutoResolving();
      }

      /**
       * Create a 'copy' of this entry that uses as few memory as possible and holds references as IDs.
       */
      public Change externalize()
      {
         throw new UnsupportedOperationException();
      }

      /**
       * affected object.
       *
       * @return object affected by this change
       */
      public Object getAffectedObject()
      {
         return delegate.getAffectedObject();
      }

      private TransactionProxy transactionProxy;

      /**
       * The transaction that contains this entry.
       *
       * @return the transaction where this entry was added, may be null
       */
      public Transaction getEnclosingTransaction()
      {
         if ( transactionProxy == null )
         {
            Transaction transactionDelegate = delegate.getEnclosingTransaction();
            if ( transactionDelegate != null )
            {
               TransactionProxy transactionProxy = wrap( transactionDelegate );
               transactionProxy.add( this );
            }
         }
         return transactionProxy;
      }

      /**
       * changed field.
       *
       * @return field that was changed (null if NA)
       */
      public FieldHandler getField()
      {
         return delegate.getField();
      }

      /**
       * key in qualified field.
       *
       * @return key for addition/removal to/from qualified field (null if NA)
       */
      public Object getKey()
      {
         return delegate.getKey();
      }

      /**
       * see {@link de.uni_kassel.coobra.AbstractChange.Kind} constants.
       *
       * @return type of this change
       */
      public Kind getKind()
      {
         return delegate.getKind();
      }

      public void setModifier(int value)
      {
         throw new UnsupportedOperationException("Cannot alter delegate.");
      }

      /**
       * see MODIFIER_* constants.
       *
       * @return see MODIFIER_* constants
       */
      public int getModifier()
      {
         return delegate.getModifier();
      }

      /**
       * value after the change.
       *
       * @return value of the field (at key) after this change occured
       */
      public Object getNewValue()
      {
         return delegate.getNewValue();
      }

      /**
       * former value.
       *
       * @return value of the field (at key) before this change occured
       */
      public Object getOldValue()
      {
         return delegate.getOldValue();
      }

      /**
       * Query repository this entry belongs to.
       *
       * @return repository this entry belongs to.
       */
      public Repository getRepository()
      {
         return delegate.getRepository();
      }

      /**
       * Check if this entry is part of a specific transaction (ascends hierarchy).
       *
       * @param transaction transaction that possibly contains this entry (over hierarchy layers)
       * @return true if this entry is contained in the transaction
       */
      public boolean isEnclosedIn( Transaction transaction )
      {
         return delegate.isEnclosedIn( transaction );
      }

      @Override
      public void recommitNotify()
      {
         if (delegate instanceof AbstractChange)
         {
            ((AbstractChange) delegate).recommitNotify();
         }
      }

      @Override
      public void rollbackNotify()
      {
         if (delegate instanceof AbstractChange)
         {
            ((AbstractChange) delegate).rollbackNotify();
         }
      }

      /**
       * Set the transaction that contains this entry.
       *
       * @param transaction the transaction where this entry is added, may be null
       */
      public void setEnclosingTransaction( AbstractMutableTransaction transaction )
      {
         if ( transaction != this.transactionProxy )
         {
            final TransactionProxy transactionProxy = ( TransactionProxy ) transaction;
            this.transactionProxy = transactionProxy;
            transactionProxy.add( this );
            //delegate should already have the transaction
            //delegate.setEnclosingTransaction( transaction.delegate );
         }
      }

      /**
       * store value for field next
       */
      private MemoryTransactionEntry next;

      private boolean nextIsEndOfCache = true;

      /**
       * @return current value of the field next
       */
      public MemoryTransactionEntry getNext()
      {
         if ( !nextIsEndOfCache )
         {
            return this.next;
         } else
         {
            throw new UnsupportedOperationException( "end of cache" );
         }
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
            nextIsEndOfCache = false;
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

      private boolean previousIsEndOfCache = true;

      /**
       * @return current value of the field previous
       */
      public MemoryTransactionEntry getPrevious()
      {
         if ( !previousIsEndOfCache )
         {
            return this.previous;
         } else
         {
            throw new UnsupportedOperationException( "end of cache" );
         }
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
            previousIsEndOfCache = false;
            if ( value != null )
            {
               value.setNext( this );
            }
            changed = true;
         }
         return changed;
      }
   }

   Map<Transaction, TransactionProxy> transactionProxies = new WeakHashMap<Transaction, TransactionProxy>();

   @Override
   public Change send( Change change, Transaction activeTransaction ) throws PersistencyException
   {
      //todo: if delegate contains changes but cache not -> change becomes first - that's wrong!
      Transaction activeTransactionDelegate;
      if ( activeTransaction != null )
      {
         activeTransactionDelegate = ( ( TransactionProxy ) activeTransaction ).delegate;
      } else
      {
         activeTransactionDelegate = null;
      }
      final ChangeProxy proxy = wrap( delegate.send( change, activeTransactionDelegate ) );
      proxy.setEnclosingTransaction( ( TransactionProxy ) activeTransaction );
      return proxy;
   }

   private ChangeProxy wrap( Change sent )
   {
      if ( sent != null )
      {
         final ChangeProxy proxy = new ChangeProxy( sent );
         return proxy;
      } else
      {
         return null;
      }
   }

   public void clear()
   {
      MemoryTransactionEntry change = getLast();
      while ( change != null )
      {
         MemoryTransactionEntry previous = change.getPrevious();
         change.delete();
         change = previous;
      }
      super.clear();
   }

   @Override
   public TransactionEntry receiveNext(TransactionEntry entry, EntryFilter filter) throws PersistencyException
   {
      do
      {
         if (entry == null)
         {
            final MemoryTransactionEntry first = getFirst();
            if (first != null)
            {
               try
               {
                  if (first.getPrevious() == null)
                  {
                     entry = first;
                     continue;
                  }
                  throw new PersistencyException("first should not have a previous change");
               } catch (UnsupportedOperationException e)
               {
                  // ok
               }
            }
            //first not in cache
            clear();
            final MemoryTransactionEntry proxy = wrap(delegate.receiveFirst());
            append( proxy );
            if (proxy instanceof ChangeProxy)
            {
               ((ChangeProxy) proxy).previousIsEndOfCache = false;
            }
            entry = proxy;
         } else if (entry instanceof EntryProxy)
         {
            EntryProxy proxy = (EntryProxy) entry;

            if (!proxy.isNextEndOfCache())
            {
               entry = proxy.getNext();
            }
            else
            {
               proxy = wrap(delegate.receiveNext(proxy.getDelegate(), null));
               if (proxy != null)
               {
                  if (getLast() != entry)
                  {
                     // TODO: non-linear read! (possible due to filters?)
                  }
                  else
                  {
                     append(proxy);
                  }
               }
               entry = proxy;
            }
         }
         else
         {
            throw new UnsupportedOperationException("not implemented");
         }
      }
      while (filter != null && entry != null && !filter.accept(entry));
      
      return entry;
   }

   @Override
   public TransactionEntry receivePrevious(TransactionEntry entry, EntryFilter filter) throws PersistencyException
   {
      do
      {
         if (entry == null)
         {
            //receive first
            final MemoryTransactionEntry first = getFirst();
            if (first != null)
            {
               try
               {
                  if (first.getPrevious() == null)
                  {
                     return first;
                  }
                  throw new PersistencyException("first should not have a prvious change");
               } catch (UnsupportedOperationException e)
               {
                  // ok
               }
            }
            //first not in cache
            clear();
            final TransactionEntry proxy = wrap(delegate.receiveFirst());
            if (proxy instanceof ChangeProxy)
            {
               ((ChangeProxy) proxy).previousIsEndOfCache = false;
            }
            entry = proxy;
         } else if (entry instanceof EntryProxy)
         {
            EntryProxy proxy = (EntryProxy) entry;
            try
            {
               entry = proxy.getPrevious();
            } catch (UnsupportedOperationException e)
            {
               //is end of cache
               entry = wrap(delegate.receivePrevious(proxy.getDelegate(), filter));
            }
         } else
         {
            throw new UnsupportedOperationException("Unsupported entry type: " + (entry != null ? entry.getClass() : null));
         }
      }
      while (filter != null && entry != null && !filter.accept(entry));

      return entry;
   }

   /**
    * Set the repository this persistency module belongs to.
    *
    * @param value repository this persistency module belongs to
    * @return true if repository was changed
    */
   @Override
   public boolean setRepository( Repository value )
   {
      delegate.setRepository( value );
      return super.setRepository( value );
   }

   public Transaction resolveTransaction(TransactionReference reference)
   {
      return wrap(delegate.resolveTransaction(reference));
   }
}

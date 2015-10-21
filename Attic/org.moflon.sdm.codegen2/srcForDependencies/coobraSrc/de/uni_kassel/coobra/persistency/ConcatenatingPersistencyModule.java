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
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;
import de.uni_kassel.util.EmptyIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * PersistencyModule that consists of multiple other PersistencyModules. These are concatenated to form a single one.
 */
public class ConcatenatingPersistencyModule extends PersistencyModule
{
   private ArrayList<PersistencyModule> delegates = new ArrayList<PersistencyModule>();

   /**
    * Append a delegate to read changes from. The last added delegate is used for sending changes.
    * Delegates are used in the order of appendance.
    *
    * @param module next delegate
    */
   public void append( PersistencyModule module )
   {
      module.setRepository( getRepository() );
      delegates.add( module );
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
      for ( PersistencyModule module : delegates )
      {
         module.setRepository( value );
      }
      return super.setRepository( value );
   }

   /**
    * close all delegates.
    */
   @Override
   public void close() throws PersistencyException
   {
      for ( PersistencyModule module : delegates )
      {
         module.close();
      }
   }

   private boolean readonly;

   /**
    * @return true
    */
   @Override
   public boolean isOpened()
   {
      if ( delegates.isEmpty() )
      {
         return true;
      }
      else
      {
         return delegates.get(0).isOpened();
      }
   }

   /**
    * @param readonly if true the module is configured for reading only
    */
   @Override
   public void open( boolean readonly ) throws PersistencyException
   {
      this.readonly = readonly;
      for ( int i = delegates.size() - 1; i >= 0; i-- )
      {
         PersistencyModule module = delegates.get( i );
         module.open( readonly );
         readonly = true; //only last module is written to
      }
   }

   private TransactionEntry receiveFirst( int index )
   {
      TransactionEntry entry = null;
      PersistencyModule delegate = null;
      while ( index < delegates.size() && entry == null )
      {
         delegate = delegates.get( index++ );
         entry = delegate.receiveFirst();
         if ( entry == null && index < delegates.size() )
         {
            lastEntries.put( delegate, entry );
         }
      }
      return register( delegate, entry );
   }

   private Map<Class<? extends TransactionEntry>, PersistencyModule> classDelegates = new HashMap<Class<? extends TransactionEntry>, PersistencyModule>();
   private Map<TransactionEntry, PersistencyModule> entryDelegates = new WeakHashMap<TransactionEntry, PersistencyModule>();
   private Map<PersistencyModule, TransactionEntry> lastEntries = new HashMap<PersistencyModule, TransactionEntry>();

   private <E extends TransactionEntry> E register( PersistencyModule delegate, E transactionEntry )
   {
      if ( transactionEntry != null )
      {
         PersistencyModule classDelegate = classDelegates.get( transactionEntry.getClass() );
         if ( classDelegate == null )
         {
            classDelegates.put( transactionEntry.getClass(), delegate );
         }
         else if ( classDelegate != delegate )
         {
            //multiple delegates that return the same class - distinguish by object reference
            entryDelegates.put( transactionEntry, delegate );
         }
      }
      return transactionEntry;
   }

   private PersistencyModule findDelegateFor( TransactionEntry transactionEntry )
   {
      PersistencyModule entryDelegate = entryDelegates.get( transactionEntry );
      if ( entryDelegate != null )
      {
         return entryDelegate;
      }
      else
      {
         return classDelegates.get( transactionEntry.getClass() );
      }
   }

   @Override
   public TransactionEntry receiveNext(TransactionEntry entry, EntryFilter filter) throws PersistencyException
   {
      PersistencyModule delegate = entry == null ? delegates.get(0) : findDelegateFor( entry );
      if ( delegate == null )
      {
         throw new IllegalArgumentException( "unknown entry" );
      }
      TransactionEntry receivedEntry = delegate.receiveNext( entry, filter);
      while ( receivedEntry == null )
      {
         int index = delegates.indexOf( delegate );
         if ( index < delegates.size() - 1 && filter == null )
         {
            lastEntries.put( delegate, entry );
         }
         receivedEntry = receiveFirst( index + 1 );
         if ( filter == null || filter.accept(receivedEntry) )
         {
            return receivedEntry;
         }
         else
         {
            delegate = findDelegateFor( receivedEntry );
            receivedEntry = delegate.receiveNext( receivedEntry, filter );
         }
      }
      return register( delegate, receivedEntry );
   }

   @Override
   public TransactionEntry receivePrevious(TransactionEntry entry, EntryFilter filter) throws PersistencyException
   {
      PersistencyModule delegate = findDelegateFor( entry );
      if ( delegate == null )
      {
         throw new IllegalArgumentException( "unknown entry" );
      }
      TransactionEntry receivedEntry = delegate.receivePrevious( entry, null);
      if ( receivedEntry == null && delegate != delegates.get( 0 ) )
      {
         int index = delegates.indexOf( delegate );
         while ( index > 0 )
         {
            index--;
            delegate = delegates.get( index );
            TransactionEntry lastEntry = lastEntries.get( delegate );
            if ( lastEntry != null )
            {
               return lastEntry;
            }
            else if ( !lastEntries.containsKey( delegate ) )
            {
               throw new UnsupportedOperationException( "receiving last entry of a persistency module is supported only " +
                     "if the module was entirely read through the ConcatenatingPersistencyModule." );
            }
         }
         return null;
      }
      return register( delegate, receivedEntry );
   }

   @Override
   public Change send( Change change, Transaction activeTransaction ) throws PersistencyException
   {
      checkSend();
      PersistencyModule delegate = getLastPersistencyModule();
      return register( delegate,  delegate.send( change, activeTransaction ) );
   }

   @Override
   public Transaction send( Transaction transaction, Transaction activeTransaction ) throws PersistencyException
   {
      checkSend();
      PersistencyModule delegate = getLastPersistencyModule();
      return register( delegate,  delegate.send( transaction, activeTransaction ) );
   }

   private void checkSend()
   {
      if ( readonly )
      {
         throw new IllegalStateException( "send not allowed in readonly mode" );
      }
      if ( delegates.isEmpty() )
      {
         throw new IllegalStateException( "no delegates have been appended yet" );
      }
   }

   /**
    * @return the persistency module where send requests are forwarded to
    */
   public PersistencyModule getLastPersistencyModule()
   {
      return delegates != null && !delegates.isEmpty() ? delegates.get( delegates.size() - 1 ) : null;
   }

   /**
    * @return the first persistency module
    */
   public PersistencyModule getFirstPersistencyModule()
   {
      return delegates != null && !delegates.isEmpty() ? delegates.get( 0 ) : null;
   }

   public Iterator<PersistencyModule> iteratorOfPersistencyModules()
   {
      if (delegates != null)
      {
         return delegates.iterator();
      } else
      {
         return EmptyIterator.get();
      }
   }

   @Override
   public void flush() throws PersistencyException
   {
      super.flush();
      PersistencyModule delegate = getLastPersistencyModule();
      if (delegate != null)
      {
         delegate.flush();
      }
   }

   public Transaction resolveTransaction(TransactionReference reference)
   {
      for (PersistencyModule delegate : delegates)
      {
         try
         {
            return delegate.resolveTransaction(reference);
         } catch (UnsupportedOperationException e)
         {
            // search on
         }
      }
      throw new UnsupportedOperationException("no delegate was able to resolve the reference");
   }
}

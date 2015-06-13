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


/**
 * @author christian.schneider@uni-kassel.de
 * @created 02.06.2004, 13:37:22
 */
public abstract class PersistencyModule
{
   /**
    * Open the persistency module. If readonly parameter is false tries to open the resource for reading and writing.
    * If this is not possible, for writing only, if still not possible for reading only.
    *
    * @param readonly if true the resource is opened for reading only always
    * @throws PersistencyException if the operation fails
    */
   public abstract void open( boolean readonly ) throws PersistencyException;

   /**
    * Query if this PersistencyModule is already opened.
    *
    * @return true if opened
    */
   public abstract boolean isOpened();

   /**
    * close the persistency module (no more writing/reading allowed). Free resources.
    *
    * @throws PersistencyException if the operation fails
    */
   public abstract void close() throws PersistencyException;

   /**
    * Store info about a transaction at the end of the transaction list. The
    * eventually enclosed transaction entries are <b>not</b> stored. After the
    * transaction was stored the returned transaction object must be used to reference
    * the stored transaction in conjunction with this persistency module.
    *
    * @param transaction       transaction to be stored
    * @param activeTransaction currently active transaction
    * @return the transaction object that must be used from now on to reference
    *         the stored transaction in this persistency module
    * @throws PersistencyException if the operation fails
    */
   public abstract Transaction send( Transaction transaction, Transaction activeTransaction ) throws PersistencyException;

   /**
    * Store info about a change at the end of the change list. The
    * eventually enclosing transaction is <b>not</b> stored. After the
    * change was stored the returned change object must be used to reference
    * the stored change in conjunction with this persistency module.
    *
    * @param change            change to be stored
    * @param activeTransaction currently active transaction
    * @return the change object that must be used from now on to reference
    *         the stored transaction in conjunction with this persistency module
    * @throws PersistencyException if the operation fails
    */
   public abstract Change send( Change change, Transaction activeTransaction ) throws PersistencyException;

   /**
    * Store info about an entry at the end of the change list. After the
    * entry was stored the returned entry object must be used to reference
    * the stored entry in conjunction with this persistency module.
    * The eventually current enclosing transaction is <b>not</b> stored but replaced by
    * activeTransaction.
    *
    * @param entry             entry to be stored
    * @param activeTransaction currently active transaction
    * @return the entry object that must be used from now on to reference
    *         the stored entry in conjunction with this persistency module
    * @throws PersistencyException if the operation fails
    */
   public final TransactionEntry send( TransactionEntry entry, Transaction activeTransaction ) throws PersistencyException
   {
      if ( entry instanceof Transaction )
      {
         return send( ( Transaction ) entry, activeTransaction );
      } else
      {
         return send( ( Change ) entry, activeTransaction );
      }
   }

   /**
    * reads the first change from the list of stored changes.
    *
    * @return null if no change was stored in the module, else the first
    *         change that was stored in the module (for multiple calls on this method
    *         always the same change)
    * @throws PersistencyException
    *          if the operation fails
    */
   public final TransactionEntry receiveFirst() throws PersistencyException
   {
      return receiveNext( null, null);
   }

   /**
    * Virtual version name that always represents the last version knwon to the
    * persistency module.
    */
   public static final String VERSION_LAST = null;

   /**
    * Virtual version name that represents an empty version (no changes).
    */
   public static final String VERSION_NONE = "~none~";

   /**
    * Read the change following the specified TransactionEntry. If entry is a Transaction the first enclosed change is
    * returned, if entry is a change the next change is returned. If no changes follow the specified entry null is
    * returned.
    *
    * @param entry preceeding entry
    * @return the next change
    * @throws PersistencyException if the operation fails
    */
   public final TransactionEntry receiveNext( TransactionEntry entry ) throws PersistencyException
   {
      return receiveNext( entry, null );
   }

   /**
    * Read the change following the specified TransactionEntry. If entry is a Transaction the first enclosed change is
    * returned, if entry is a change the next change is returned. If no changes follow the specified entry null is
    * returned.
    *
    * @param entry          preceeding entry
    * @param filter         entry filter
    * @return the next change
    * @throws PersistencyException if the operation fails
    */
   public abstract TransactionEntry receiveNext(TransactionEntry entry, EntryFilter filter)
         throws PersistencyException;

   /**
    * Read the change preceeding the specified TransactionEntry. If entry is a Transaction the change before the
    * first enclosed change is returned, if entry is a change the previous change is returned.
    * If no changes are before the specified entry, null is returned.
    *
    * @param entry succeeding entry
    * @return the previous change
    * @throws PersistencyException if the operation fails
    */
   public final TransactionEntry receivePrevious( TransactionEntry entry ) throws PersistencyException
   {
      return receivePrevious( entry, null);
   }

   /**
    * Read the change preceeding the specified TransactionEntry. If entry is a Transaction the change before the
    * first enclosed change is returned, if entry is a change the previous change is returned.
    * If no changes are before the specified entry, null is returned.
    *
    * @param entry          succeeding entry
    * @param filter         entry filter
    * @return the previous change
    * @throws PersistencyException if the operation fails
    */
   public abstract TransactionEntry receivePrevious(TransactionEntry entry, EntryFilter filter)
         throws PersistencyException;

   /**
    * store value for field repository.
    */
   private Repository repository;

   /**
    * Query the repository this persistency module belongs to.
    *
    * @return the repository this persistency module belongs to.
    */
   public final Repository getRepository()
   {
      return this.repository;
   }

   /**
    * Set the repository this persistency module belongs to.
    *
    * @param value repository this persistency module belongs to
    * @return true if repository was changed
    */
   public boolean setRepository( Repository value )
   {
      boolean changed = false;
      if ( this.repository != value )
      {
         this.repository = value;
         changed = true;
      }
      return changed;
   }


   /**
    * delete the module from memory
    */
   public void delete()
   {
      close();
      setRepository( null );
   }

    /**
     * Write all entries to underlying persistence layer (if applicable).
     * @throws PersistencyException if flushing fails
     */
    public void flush() throws PersistencyException {}

   /**
    * Receives the next entry which is a direct child of the given transaction.
    * @param entry previous entry
    * @param transaction direct parent of the received entry
    * @return received entry
    */
   public TransactionEntry receiveNextFromTransaction(TransactionEntry entry, Transaction transaction)
   {
      boolean notNullAndNotInThis;
      do
      {
         entry = receiveNext(entry);
         notNullAndNotInThis = entry != null &&
               (entry.getEnclosingTransaction() == null
                     || entry.getEnclosingTransaction().getReference() != transaction.getReference());
      }
      while (notNullAndNotInThis && entry.isEnclosedIn(transaction));
      if (notNullAndNotInThis)
      {
         entry = null;
      }
      return entry;
   }

   /**
    * Resolves a TransactionReference to an actual Transaction for this module.
    * @param reference reference object
    * @return Transaction referenced by parameter
    * @throws UnsupportedOperationException is no Transaction for specified reference is available in this module (yet)
    */
   public abstract Transaction resolveTransaction(TransactionReference reference);
}

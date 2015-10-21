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

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 06.08.2004, 14:04:54
 */
public interface TransactionEntry
{
   /**
    * if returned by {@link #getModifier()} this entry is not processed at all.
    */
   int MODIFIER_INVALID = 0x00;
   /**
    * if returned by {@link #getModifier()} this entry is not stored and not sent anywhere.
    */
   int MODIFIER_TRANSIENT = 0x01;
   /**
    * if returned by {@link #getModifier()} this entry is processed locally only (not sent to server etc).
    */
   int MODIFIER_LOCAL = 0x04;
   /**
    * if returned by {@link #getModifier()} this entry was obtained from the server and thus is not sent to server again.
    */
   int MODIFIER_SERVER = 0x08;
   /**
    * if returned by {@link #getModifier()} this entry is processed normally.
    */
   int MODIFIER_DEFAULT = 0x10;
   /**
    * modifiers greater or equal to this value can be used in application specific filters. CoObRA will
    * treat changes with these modifiers like {@link #MODIFIER_DEFAULT}.
    */
   int MODIFIER_APPLICATION_OFFSET = 0x100;

   /**
    * Query repository this entry belongs to.
    *
    * @return repository this entry belongs to.
    */
   Repository getRepository();

   /**
    * Finish a transaction.
    */
   void commit();

   /**
    * redo the transaction/change.
    *
    * @return the last entry that was recommited (most recently created)
    */
   TransactionEntry recommit();

   /**
    * undo the transaction/change. Abort it if not yet committed.
    *
    * @return the last entry that was rolled back (first regarding creation order)
    */
   TransactionEntry rollback();

   /**
    * The transaction that contains this entry.
    *
    * @return the transaction where this entry was added, may be null
    */
   Transaction getEnclosingTransaction();

   /**
    * removes this transaction entry from memory.
    */
   void delete();

   /**
    * Check if this entry is part of a specific transaction (ascends hierarchy).
    *
    * @param transaction transaction that possibly contains this entry (over hierarchy layers)
    * @return true if this entry is contained in the transaction
    */
   boolean isEnclosedIn( Transaction transaction );

   /**
    * Enable/disable automatic resolving of affected object, new value, old value and key.
    *
    * @param enabled if set to false ID are not resolved when getters are called
    * @see #isAutoResolving()
    */
   void setAutoResolving( boolean enabled );

   /**
    * Query auto resolve property. True by default.
    *
    * @return true if getters for affected object, new value, old value and key always return object references.
    * @see #setAutoResolving(boolean)
    */
   boolean isAutoResolving();

   /**
    * Create a 'copy' of this entry that uses as few memory as possible and holds references as IDs.
    * @return the externalized copy
    */
   TransactionEntry externalize();

   /**
    * @return true iff this is a Change and is of {@link Change.Kind#MANAGE}.
    */
   boolean isManagementEntry();

   /**
    * @return true iff this entry entirely has modifier {@link TransactionEntry#MODIFIER_LOCAL}
    */
   boolean isLocal();

   /**
    * see MODIFIER_* constants.
    *
    * @return see MODIFIER_* constants
    */
   int getModifier();

   /**
    * Query if this change was rolled back and not recommitted so far. Transactions return false.
    * (persistent: after store and load this property is the same)
    *
    * @return true if this is a change and was rolled back and not recommitted so far.
    */
   boolean isRolledback();
}

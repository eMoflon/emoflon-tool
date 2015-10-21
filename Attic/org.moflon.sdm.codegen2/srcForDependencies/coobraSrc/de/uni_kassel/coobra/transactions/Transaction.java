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

import de.uni_kassel.util.PropertyChangeSource;

import java.util.Iterator;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 03.08.2004, 11:05:38
 */
public interface Transaction extends TransactionEntry, PropertyChangeSource
{
   enum Status
   {
      /**
       * This status should never be returned for normal operation, it indicates that a severe error occured
       * while processing the transaction.
       */
      INVALID,

      /**
       * The transaction started and was not yet committed.
       * Changes/transactions can be added to this transaction.
       */
      STARTED,

      /**
       * The transaction was committed.
       * No changes/transactions can be added to this transaction any more.
       * It can possibly be rolled back.
       */
      COMMITTED,

      /**
       * The transaction was never committed but aborted via {@link Transaction#rollback}.
       * No changes/transactions can be added to this transaction any more.
       * It cannnot be committed.
       */
      ABORTED,

      /**
       * The transaction was successfully committed but rolled back afterwards.
       * No changes/transactions can be added to this transaction any more.
       * It can possibly be committed again.
       */
      ROLLED_BACK,
   }

   /**
    * Status of the Transaction (see Status enum).
    * This has to be a bound property, thus all changes to the status of a transaction must
    * be reported by a PropertyChangeEvent.
    *
    * @return value from Status enum
    */
   Status getStatus();

   /**
    * indicates when the transaction was started.
    *
    * @return the return value of {@link System#currentTimeMillis()} upon occurence of the first change
    */
   long getTimeStamp();

   /**
    * query the name of the transaction.
    *
    * @return a name for this transaction (commonly not unique)
    */
   String getName();

   /**
    * Construct an iterator though all entries.
    *
    * @return an Iterator which returns TransactionEntry upon call of next().
    */
   Iterator<? extends TransactionEntry> iterator();

   /**
    * Iterates the transaction entries in reverse order.
    *
    * @return an iterator through the transaction entries, starting at the last entry.
    */
   Iterator<? extends TransactionEntry> iteratorReverse();

   /**
    * Query reference object that is the same for all copies of this transaction. Which especially means that the
    * reference object has the same ID for all instances of this transaction.
    *
    * @return reference object or ID
    */
   TransactionReference getReference();
}

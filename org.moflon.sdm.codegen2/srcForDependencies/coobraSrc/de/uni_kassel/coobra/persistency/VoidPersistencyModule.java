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
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 08.05.2005, 12:10:50
 */
public class VoidPersistencyModule extends PersistencyModule
{
   @Override
   public void close() throws PersistencyException
   {

   }

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

   @Override
   public void open( boolean readonly ) throws PersistencyException
   {

   }

   @Override
   public TransactionEntry receiveNext(TransactionEntry entry, EntryFilter filter) throws PersistencyException
   {
      return null;
   }

   @Override
   public TransactionEntry receivePrevious(TransactionEntry entry, EntryFilter filter) throws PersistencyException
   {
      return null;
   }

   public Transaction resolveTransaction(TransactionReference reference)
   {
      return null;
   }

   @Override
   public Change send( Change change, Transaction activeTransaction ) throws PersistencyException
   {
      return null;
   }

   @Override
   public Transaction send( Transaction transaction, Transaction activeTransaction ) throws PersistencyException
   {
      return null;
   }
}

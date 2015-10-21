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
package de.uni_kassel.coobra.util;

import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;

import java.util.HashMap;
import java.util.Map;

public class TransactionMapper
{
   final Map<TransactionReference, Transaction> sentTransactions = new HashMap<TransactionReference, Transaction>();
   final PersistencyModule persistencyModule;

   public TransactionMapper(PersistencyModule persistencyModule)
   {
      this.persistencyModule = persistencyModule;
   }

   public TransactionEntry sendEntryAndMapEnclosingTransactions(TransactionEntry entry)
   {
      Transaction readEnclosing = entry.getEnclosingTransaction();
      Transaction activeTransaction = readEnclosing != null ? sentTransactions.get(readEnclosing.getReference()) : null;
      if (activeTransaction == null && readEnclosing != null)
      {
         throw new IllegalStateException("Failed to find sent transaction for enclosing transaction!");
      }
      if (entry instanceof Transaction)
      {
         Transaction transaction = (Transaction) entry;
         Transaction sent = persistencyModule.send(transaction, activeTransaction);
         sentTransactions.put(transaction.getReference(), sent);
         return sent;
      } else
      {
         return persistencyModule.send(entry, activeTransaction);
      }
   }
}

/*
 * $Log$
 * Revision 1.2  2008/10/23 14:38:42  cschneid
 * introduced binary persistency modules
 *
 * Revision 1.1  2008/06/30 13:26:29  cschneid
 * improved getObject(String) method; extracted TransactionMapper
 *
 */


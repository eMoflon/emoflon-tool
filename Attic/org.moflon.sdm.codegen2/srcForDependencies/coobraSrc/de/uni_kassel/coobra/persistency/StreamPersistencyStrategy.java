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
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;

import java.io.IOException;
import java.util.Map;

interface StreamPersistencyStrategy
{
   void beforeRead();

   TransactionEntry readEntry(EntryFilter filter) throws IOException;

   AbstractStreamPersistencyModule.StreamChange writeChange(Change change, Transaction activeTransaction) throws IOException;

   void writeEOF() throws IOException;

   Transaction writeTransaction(Transaction transaction, Transaction activeTransaction, final ID transactionID) throws IOException;

   void markRedone(AbstractStreamPersistencyModule.StreamChange change);

   void markUndone(AbstractStreamPersistencyModule.StreamChange change);

   void flush();

   void writeHeader(Map<String, String> values, String modelName) throws IOException;

   Map<String, String> readHeader(String modelName) throws IOException;

   void close();

   /**
    * @return read position in bytes if buffered reading is active, -1 if closed
    */
   long getOpenReadPosition();

   void seekNotify() throws IOException;
}

/*
 * $Log$
 * Revision 1.1  2008/10/23 14:38:23  cschneid
 * introduced binary persistency modules
 *
 */


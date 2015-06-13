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

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.persistency.CompactionModule;
import de.uni_kassel.coobra.persistency.MemoryPersistencyModule;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.transactions.TransactionEntry;

import java.util.Map;
import java.util.Set;

/**
 * @author schneider
 */
public class ProtocollDiff
{
   private ProtocollDiff()
   {
   }

   /**
    * Compare two persisteny modules regarding their contained changes.
    * @param main the first module to read from
    * @param cmp the second module to read from
    * @param additionalChanges changes that are in cmp/second but not in main/first
    * @param missingChanges changes that are in main/first but not in cmp/second
    * @param conflictingChanges map from changes in main/first that are also in cmp/second and pertain
    * to the same object and field(part)
    */
   public static void diff( PersistencyModule main, PersistencyModule cmp,
                            Set<Change> additionalChanges,
                            Set<Change> missingChanges,
                            Map<Change, Change> conflictingChanges ) {
      CompactionModule mainCompact = new CompactionModule( new MemoryPersistencyModule() );
      CompactionModule cmpCompact = new CompactionModule( new MemoryPersistencyModule() );
      copyChanges( main, mainCompact );
      copyChanges( cmp, cmpCompact );

      mainCompact.diff( cmpCompact, additionalChanges, missingChanges, conflictingChanges );
   }

   private static void copyChanges( PersistencyModule src, CompactionModule dst )
   {
      dst.setRepository( src.getRepository() );
      TransactionEntry entry = src.receiveFirst();
      dst.setFlushingOnNewTransaction( false );
      while ( entry != null ) {
         dst.send( entry, null ); // we don't care about transactions
         entry = src.receiveNext( entry );
      }
   }
}

/*
 * $log$
 */


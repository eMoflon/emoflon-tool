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

package de.uni_kassel.coobra.server.messages;

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.persistency.SocketPersistencyModule;
import de.uni_kassel.coobra.server.ClientSession;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 04.04.2005, 11:00:38
 */
public class CheckinRequest extends DefaultRequest
{
   private static final long serialVersionUID = 1;

   private String lastKnownServerVersion;

   public CheckinRequest(String lastKnownServerVersion, String suggestedVersionName)
   {
      this.lastKnownServerVersion = lastKnownServerVersion;
      this.suggestedVersionName = suggestedVersionName;
   }

   public String getLastKnownServerVersion()
   {
      return lastKnownServerVersion;
   }


   /**
    * getter for field suggestedVersionName
    *
    * @return current value of field suggestedVersionName
    */
   public String getSuggestedVersionName()
   {
      return this.suggestedVersionName;
   }

   /**
    * store the value for field suggestedVersionName
    */
   private String suggestedVersionName;

   /**
    * setter for field suggestedVersionName
    *
    * @param value new value
    */
   public void setSuggestedVersionName(final String value)
   {
      this.suggestedVersionName = value;
   }

   @Override
   protected void check( ClientSession session )
   {
      //todo: check lastKnownServerVersion
      session.getServer().checkWritePermission( getUser() );
   }

   @Override
   protected void execute(ClientSession session) throws IOException
   {
      final Repository serverRepository = session.getServer().getRepository();

      String versionProto = getSuggestedVersionName() != null ? getSuggestedVersionName() : "Checkin " + new Date();
      String version = versionProto;
      int i = 1;
      while ( session.getServer().getVersionInfo( version ) != null ) {
         version = versionProto + " (" + ++i + ")";
      }

      ClientSession.LOGGER.info( "Checkin by " + getUserName() + " : " + version );

      final SocketPersistencyModule persistencyModule = session.getPersistencyModule();
      persistencyModule.open(true);
      TransactionEntry entry = persistencyModule.receiveFirst();
      final Transaction checkinTransaction = session.getServer().announceVersion(version, getUserName());
      try {
         while (entry != null)
         {
            //todo: buffer+check and redo
            if ( entry instanceof Transaction || !entry.isLocal() ) {
               entry.setAutoResolving(false);
               serverRepository.acknowledgeUpdate(entry);
            }
            entry = persistencyModule.receiveNext(entry);
         }
         checkinTransaction.commit();
         session.getServer().registerVersion(getUserName(), checkinTransaction);
         persistencyModule.close();
      } catch ( RuntimeException e ) {
         try {
            e.printStackTrace();
            checkinTransaction.rollback();
         } catch ( Exception e2 ) {
            ClientSession.LOGGER.log(Level.SEVERE, "Error rolling back checkin transaction! " +
                  "Data may be corrupted!", e2 );
         }
         try {
            persistencyModule.close();
         } catch ( RuntimeException e2 ) {
            // just ignore it if it can't be closed
         }
         throw e;
      }
      //TODO: read/write locks

      send(new Response( version, getRequestSequenceNumber()), session);
   }

   private void rollbackAndClose(Transaction checkinTransaction) {
      try {
         checkinTransaction.rollback();
      } catch ( Exception e2 ) {
         ClientSession.LOGGER.log(Level.SEVERE, "Error rolling back checkin transaction! " +
               "Data may be corrupted!", e2 );
      }
   }
}

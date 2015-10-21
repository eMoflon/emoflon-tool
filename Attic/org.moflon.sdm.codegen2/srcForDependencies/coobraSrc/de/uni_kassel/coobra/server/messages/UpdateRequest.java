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

import de.uni_kassel.coobra.persistency.CompactionModule;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.persistency.SocketPersistencyModule;
import de.uni_kassel.coobra.server.ClientSession;
import de.uni_kassel.coobra.server.DefaultServer;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.util.TransactionMapper;

import java.io.IOException;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 04.04.2005, 11:00:48
 */
public class UpdateRequest extends DefaultRequest
{
   private static final long serialVersionUID = 1;

   private String lastKnownServerVersion;

   private boolean compacting = false;

   public boolean isCompacting() {
       return compacting;
   }

   public void setCompacting( boolean compacting ) {
      this.compacting = compacting;
   }

    public String getLastKnownServerVersion()
   {
      return lastKnownServerVersion;
   }

   public UpdateRequest(String lastKnownServerVersion)
   {
      this.lastKnownServerVersion = lastKnownServerVersion;
   }

   @Override
   protected void check( ClientSession session )
   {
      session.getServer().checkReadPermission( getUser() );
   }

   @Override
   protected void execute(ClientSession session) throws IOException
   {
      ClientSession.LOGGER.info("Update by " + getUserName() + " from version '" + lastKnownServerVersion + "'");
      sendResponseOk( session );
      SocketPersistencyModule persistencyModule = session.getPersistencyModule();
      sendExistingChanges( persistencyModule, session.getServer() );
      persistencyModule.sendEOF();
      persistencyModule.close();
   }

    protected void sendResponseOk( ClientSession session ) throws IOException {
        DefaultServer.VersionInfo lastVersion = session.getServer().getLastVersion();
        String name = lastVersion != null ? lastVersion.getName() : "";
        send(new Response(name, getRequestSequenceNumber()), session);
    }

    protected void sendExistingChanges( PersistencyModule persistencyModule, DefaultServer server ) throws IOException {
        PersistencyModule repositoryPersistencyModule = server.getRepository().getPersistencyModule();
        if ( compacting )
        {
            persistencyModule = new CompactionModule( persistencyModule );
            persistencyModule.setRepository( server.getRepository() );
            persistencyModule.open( false );
        }
        persistencyModule.open(false);
        TransactionEntry entry;
        if (getLastKnownServerVersion() != null)
        {
           final DefaultServer.VersionInfo versionInfo = server.getVersionInfo(getLastKnownServerVersion());
           if (versionInfo == null)
           {
              //todo: better exception
              throw new RuntimeException("Unknown version: " + getLastKnownServerVersion());
           }
           if (versionInfo.getNext() != null)
           {
              entry = versionInfo.getNext().getTransaction();
           } else
           {
              entry = null; //client is up to date
           }
        } else
        {
           entry = repositoryPersistencyModule.receiveFirst();
        }
       final TransactionMapper mapper = new TransactionMapper(persistencyModule);
       while (entry != null)
       {
          entry.setAutoResolving(false);
          if (!entry.isLocal())
          {
             mapper.sendEntryAndMapEnclosingTransactions(entry);
          }
          entry = repositoryPersistencyModule.receiveNext(entry);
       }
       if (compacting)
       {
          persistencyModule.close();
       }
    }

}

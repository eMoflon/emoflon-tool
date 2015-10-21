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

import de.uni_kassel.coobra.MutableChange;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.persistency.SocketPersistencyModule;
import de.uni_kassel.coobra.server.ClientSession;
import de.uni_kassel.coobra.transactions.TransactionEntry;

import java.io.IOException;

/**
 * 
 */
public class ContinuousUpdateRequest extends UpdateRequest {
    public ContinuousUpdateRequest( String lastKnownServerVersion ) {
        super( lastKnownServerVersion );
    }

    @Override
    protected void execute( ClientSession session ) throws IOException {
        sendResponseOk( session );
        final SocketPersistencyModule persistencyModule = session.getPersistencyModule();
        sendExistingChanges( persistencyModule, session.getServer() );
        Repository.RepositoryListener listener = new Repository.RepositoryListener() {
            public void acknowledgeChange( TransactionEntry change, EventType type ) {
               //FIXME: workaround:
               (( MutableChange)change).setEnclosingTransaction( null );
               //FIXME: /
                persistencyModule.send( change, null ); //todo: transaction
            }
        };
        session.getServer().getRepository().addListener( listener );
        try
        {
            try {
                while ( session.getSocket().isConnected() )
                {
                    Thread.sleep( 100 );
                    if ( session.getSocket().getInputStream().available() != 0 )
                    {
//                        System.out.println( "Continuous update aborted due to next command" );
                        break;
                    }
                }
            } catch ( InterruptedException e ) {
                // thread interrupted - return quickly
            }
        } finally {
            session.getServer().getRepository().removeListener( listener );
        }
        persistencyModule.sendEOF();
        persistencyModule.close();
    }
}

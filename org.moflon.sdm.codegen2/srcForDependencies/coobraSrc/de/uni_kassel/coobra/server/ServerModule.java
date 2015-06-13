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

package de.uni_kassel.coobra.server;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.errors.RepositorySetupException;
import de.uni_kassel.coobra.persistency.PersistencyException;
import de.uni_kassel.coobra.server.errors.RemoteException;
import de.uni_kassel.util.PropertyChangeSourceImpl;

import java.io.IOException;
import java.util.List;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 29.03.2005, 15:33:48
 */
public abstract class ServerModule<ConnectionTarget, Credentials> extends PropertyChangeSourceImpl {
   /**
    * store value for field repository
    */
   private Repository repository;

   /**
    * @return current value of the field repository
    */
   public Repository getRepository()
   {
      return this.repository;
   }

   /**
    * @param value new value for field repository
    * @return true if repository was changed
    * @throws RepositorySetupException if repository already received changes
    */
   public boolean setRepository( final Repository value ) throws RepositorySetupException
   {
      final Repository oldValue = this.repository;
      boolean changed = false;
      if ( oldValue != value )
      {
         if ( value != null && !value.isUntouched() )
         {
            throw new RepositorySetupException( "Server module must be set up right after creating the repository!" );
         }
         if ( oldValue != null )
         {
            this.repository = null;
            oldValue.setServerModule( null );
         }
         this.repository = value;
         if ( value != null )
         {
            value.setServerModule( this );
         }
         changed = true;
      }
      return changed;
   }

    /**
     * Update to current version of the server.
     *
     * @return list of conflicting local changes
     */
    public abstract List<Change> update() throws PersistencyException, RemoteException;

   /**
    * Send local changes to the server. (possible if client is up to date, only)
    */
   public void checkin() throws RemoteException {
      checkin( null );
   }

    /**
     * Connect this server module to specified target. The target is defined subclass dependent.
     * @param target a subclass defined target
     */
    public abstract void connect( ConnectionTarget target ) throws IOException, RemoteException;

   /**
    * @return true if module was connect via {@link #connect}
    */
   public abstract boolean isConnected();

   /**
    * Send local changes to the server. (possible if client is up to date, only)
    * @param suggestedVersionName version name that should be used to identify the new version (may be altered by
    *                             the server - e.g. when not unique)
    */
   public abstract void checkin( String suggestedVersionName ) throws RemoteException;

   public abstract void useAuthentication( Credentials credentials );
}

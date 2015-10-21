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

import de.uni_kassel.coobra.server.AbstractClientSession;
import de.uni_kassel.coobra.server.NameServer;
import de.uni_kassel.coobra.server.RepositoryInfo;

import java.net.InetAddress;

/**
 * This request registers a CoObRA Server Repository 
 * on the Nameservice. If the repository has already 
 * been registered, it resets the announcement-time 
 * via {@link RepositoryInfo#resetAnnouncementTime()}.
 * 
 * @author manuel bork
 */
public class RegisterServerRequest extends Request
{
   private static final long serialVersionUID = 1L;

   /**
    * the name of the repository
    */
   private String repositoryName;

   /**
    * host on which the repository server is running
    */
   private String repositoryHost;

   /**
    * port of the repository
    */
   private int repositoryPort;

   /**
    *
    * @param name of the repository
    * @param repositoryHost host on which the repository is running
    * @param repositoryPort port on which the repository is running
    */
   public RegisterServerRequest( String name, String host, int port )
   {
      this.repositoryName = name;
      this.repositoryHost = host;
      this.repositoryPort = port;
   }


   /* (non-Javadoc)
     * @see de.uni_kassel.coobra.server.messages.Request#check(de.uni_kassel.coobra.server.AbstractClientSession)
     */
   @Override
   protected void check( AbstractClientSession session ) throws Exception
   {
      session.getServer().checkWritePermission( getUser() );
   }

   /**
    * {@inheritDoc}
    * 
    * if the repository host is not set (<code>null</code>), it is determined from the session's socket
    */
   @Override
   protected void execute( AbstractClientSession session ) throws Exception
   {
      if (repositoryHost == null)
      {
         final InetAddress inetAddress = session.getSocket().getInetAddress();
         repositoryHost = inetAddress.getHostAddress();
      }
      NameServer.LOGGER.info( "ANNOUNCE: repository: " + repositoryName + " on server: " + repositoryHost + ":" + repositoryPort );
      session.getServer().addToServices( repositoryName, repositoryHost, repositoryPort );
   }
}

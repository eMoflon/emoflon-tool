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
import de.uni_kassel.coobra.server.DefaultServer;
import de.uni_kassel.coobra.server.DefaultServer.VersionInfo;
import de.uni_kassel.coobra.server.NameServer;

import java.util.LinkedList;

/**
 * @author manuel bork
 */
public class VersionInfoRequest extends Request
{
   private static final long serialVersionUID = 1L;

   /**
    * Lower bound of the list: Causes this request to return all version names that come after the
    * fromVersion (exclusively). If null the list starts with the name of the first version.
    */
   private final String fromVersion;

   /**
    * Upper bound of the list: Causes this request to return all version names that come before the
    * toVersion (exclusively). If null the list ends with the name of the last version.
    */
   private final String toVersion;

   /**
    * Requests a list of {@link VersionInfo} names from the {@link DefaultServer}.
    * 
    * @param fromVersion lower bound of the list: returns all version names that come after the
    *           fromVersion (exclusively). If null the list starts with the name of the first
    *           version.
    * @param toVersion upper bound of the list: returns all version names that come before the
    *           toVersion (exclusively). If null the list ends with the name of the last version.
    */
   public VersionInfoRequest( final String fromVersion, final String toVersion )
   {
      this.fromVersion = fromVersion;
      this.toVersion = toVersion;
   }

   @Override
   protected void check( AbstractClientSession session ) throws Exception
   {
      session.getServer().checkReadPermission( getUser() );
   }

   @Override
   protected void execute( AbstractClientSession session ) throws Exception
   {
      VersionInfoResponse response = new VersionInfoResponse( getRequestSequenceNumber() );

      NameServer server = session.getServer();
      if( server instanceof DefaultServer )
      {
         DefaultServer defaultServer = (DefaultServer) server;
         
         LinkedList<String> versionInfos = new LinkedList<String>();

         boolean lowerBoundPassed = ( fromVersion == null ) ? true : false;
         
         VersionInfo version = defaultServer.getFirstVersion();
         while( version != null )
         {
            if( !lowerBoundPassed )
            {
               // check if the lower bound is reached
               if( fromVersion.equals( version.getName() ) ) lowerBoundPassed = true;
            }
            else
            {
               // check if the upper bound is reached
               if( toVersion != null && toVersion.equals( version.getName() ) )
               {
                  // quit the loop, as the upper bound has been reached
                  break;
               }

               versionInfos.add( version.getName() );
            }
            version = version.getNext();
         }

         response.setVersionInfos( versionInfos );
      }
      else
      {
         IllegalArgumentException ex = new IllegalArgumentException( "This request can only be sent to a DefaultServer." );
         response.setThrowable( ex );
      }

      // send response
      send( response, session );
   }
}

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

import de.uni_kassel.coobra.persistency.PersistencyException;
import de.uni_kassel.coobra.persistency.io.NonClosableInputStream;
import de.uni_kassel.coobra.persistency.io.NonClosableOutputStream;
import de.uni_kassel.coobra.server.errors.RemoteException;
import de.uni_kassel.coobra.server.errors.UnknownResponseException;
import de.uni_kassel.coobra.server.messages.Request;
import de.uni_kassel.coobra.server.messages.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author
 */
public abstract class TCPConnection
{
   protected InetAddress address;
   protected int port;
   private Request lastSentRequest;

   public TCPConnection( String host, int port ) throws UnknownHostException {
      this.address = InetAddress.getByName( host );
      this.port = port;
   }

   public TCPConnection( InetAddress address, int port )
   {
      this.address = address;
      this.port = port;
   }

   protected void open() {
       try
       {
          close();
          final Socket socket = new Socket(address, port);
          //FIXME:soTimeout fails on linux?!?
//          socket.setSoTimeout( 60000 /* 1 Minute */ );
          setSocket(socket);
       } catch ( IOException e )
       {
          throw new PersistencyException( e.getMessage() + ": "+  
             address + ":" + port, e );
       }
   }

   /**
    * Disconnect the module from the server/close file.
    * May be called multiple times, but does not cascade.
    *
    * @throws de.uni_kassel.coobra.persistency.PersistencyException
    *          if the operation fails
    */
   public void close() throws PersistencyException
   {
      if ( getSocket() != null )
      {
         try
         {
            getSocket().close();
            lastSentRequest = null;
         } catch ( IOException e )
         {
            throw new PersistencyException( e );
         }
         setSocket( null );
      }
   }

   protected void send( Request request ) throws IOException
   {
      if ( lastSentRequest != null )
      {
         throw new IllegalStateException( "Last response was not received yet! Pending command: " + lastSentRequest );
      }
      ObjectOutputStream objectOutputStream = new ObjectOutputStream( new NonClosableOutputStream( getSocket().getOutputStream() ) );
      objectOutputStream.writeObject( request );
      objectOutputStream.flush();
      lastSentRequest = request;
   }

   Response readResponse() throws IOException, UnknownResponseException, RemoteException
   {
      if ( lastSentRequest == null )
      {
         throw new IllegalStateException( "No request sent!" );
      }
      try 
      {
         try
         {
            final Response response = ( Response ) new ObjectInputStream( new NonClosableInputStream( getSocket().getInputStream(), true) ).readObject();
            if ( response.getThrowable() != null ) 
            {
               throw new RemoteException( response, response.getThrowable() );
            }
            if ( response.getRequestSequenceNumber() != lastSentRequest.getRequestSequenceNumber() )
            {
               throw new IllegalStateException( "Request sequence corrupted: sent " + lastSentRequest.getRequestSequenceNumber() + ", response " + response.getRequestSequenceNumber() );
            }
            return response;
         } 
         catch ( ClassNotFoundException e )
         {
            throw new UnknownResponseException( "unknown response", e );
         }
      } 
      finally 
      {
         lastSentRequest = null;
      }
   }

   protected abstract Socket getSocket();

   protected abstract void setSocket( Socket socket );

   public boolean isOpened()
   {
      Socket socket = getSocket();
      return socket != null && socket.isConnected() && !socket.isOutputShutdown();
   }
}

/*
 * $log$
 */


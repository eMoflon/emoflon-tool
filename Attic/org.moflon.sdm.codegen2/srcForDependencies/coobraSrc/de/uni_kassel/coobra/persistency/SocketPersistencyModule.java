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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 31.03.2005, 12:05:34
 */
public class SocketPersistencyModule extends AbstractStreamPersistencyModule
{
   public SocketPersistencyModule(boolean binary)
   {
      super(binary);
   }

   public SocketPersistencyModule()
   {
      this(false);
   }

   /**
    * getter for field socket
    *
    * @return current value of field socket
    */
   public Socket getSocket()
   {
      return this.socket;
   }

   /**
    * store the value for field socket
    */
   private Socket socket;

   /**
    * setter for field socket
    *
    * @param value new value
    */
   public void setSocket( final Socket value )
   {
      final Socket oldValue = this.socket;
      if ( oldValue != value )
      {
         this.socket = value;
      }
   }

   /**
    * ignored
    *
    * @param entry
    * @param from  ignored
    * @param to    ignored
    */
   @Override
   protected void modifyByte(StreamEntry entry, int from, int to)
   {
      //ignored
   }

   protected InputStream getInput() throws IOException
   {
      if ( socket == null )
      {
         throw new PersistencyException( "Not opened" );
      }
      return socket.getInputStream();
   }

   protected long getInputPosition() throws IOException
   {
      return 0; // unknown
   }

   protected OutputStream getOutput() throws IOException
   {
      if ( socket == null )
      {
         throw new PersistencyException( "Not opened" );
      }
      return socket.getOutputStream();
   }

   protected long getOutputPosition() throws IOException
   {
      return 0; // unknown
   }

   /**
    * Open the persistency module. If readonly parameter is false tries to open the resource for reading and writing.
    * If this is not possible, for writing only, if still not possible for reading only.
    *
    * @param readonly if true the resource is opened for reading only always
    * @throws de.uni_kassel.coobra.persistency.PersistencyException
    *          if the operation fails
    */
   @Override
   public void open( boolean readonly ) throws PersistencyException
   {
      checkSetup();
      atEOF = false;
      this.inReadOnlyMode = readonly;
   }

   @Override
   public boolean isOpened()
   {
      return true;
   }

   /**
    * Reset state - set last received entries to null to allow reading on in the stream with {@link #receiveFirst}. 
    */
   public void reset()
   {
      beforeLastRead = null;
      lastRead = null;
   }
}

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

/**
 * @author christian.schneider@uni-kassel.de
 * @created 19.05.2005, 13:35:10
 */
public class StreamPersistencyModule extends AbstractStreamPersistencyModule
{
   public StreamPersistencyModule( InputStream inputStream, OutputStream outputStream, boolean binary )
   {
      super( binary );
      this.inputStream = inputStream;
      this.outputStream = outputStream;
      inReadOnlyMode = outputStream == null;
   }

   public StreamPersistencyModule( InputStream inputStream, OutputStream outputStream )
   {
      this( inputStream, outputStream, false );
   }

   /**
    * Unsupported
    *
    * @param entry
    * @param from  ignored
    * @param to    ignored
    */
   @Override
   protected void modifyByte(StreamEntry entry, int from, int to)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * close the persistency module (no more writing/reading allowed). Free resources.
    *
    * @throws PersistencyException if the operation fails
    */
   @Override
   public void close() throws PersistencyException
   {
      flush();
   }

   private InputStream inputStream;

   protected InputStream getInput() throws IOException
   {
      if ( inputStream == null )
      {
         throw new PersistencyException( "Reading in writeonly mode!" );
      }
      return inputStream;
   }

   protected long getInputPosition() throws IOException
   {
      return 0; // unknown
   }

   private OutputStream outputStream;

   protected OutputStream getOutput() throws IOException
   {
      return outputStream;
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
    * @throws PersistencyException if the operation fails
    */
   @Override
   public void open( boolean readonly ) throws PersistencyException
   {
      checkSetup();
      if ( outputStream == null && !readonly )
      {
         throw new PersistencyException( "Cannot open in writing mode if no OutputStream was specified!" );
      }
      if ( inputStream == null && readonly )
      {
         throw new PersistencyException( "Cannot open in reading mode if no InputStream was specified!" );
      }
      inReadOnlyMode = readonly;
      atEOF = false;
   }

   /**
    * Query if this PersistencyModule is already opened.
    *
    * @return true if opened
    */
   @Override
   public boolean isOpened()
   {
      return true;
   }
}

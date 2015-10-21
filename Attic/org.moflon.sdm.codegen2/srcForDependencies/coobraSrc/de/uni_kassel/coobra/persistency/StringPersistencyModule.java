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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Convenience module to allow reading/writing Strings directly.
 *
 * @see #toString()
 * @see #appendInput(String)
 */
public class StringPersistencyModule extends StreamPersistencyModule
{
   private ByteArrayOutputStream byteOutput;
   private PipedOutputStream byteInput;

   private StringPersistencyModule(PipedInputStream in, ByteArrayOutputStream out)
   {
      super(in, out);
      byteOutput = out;
      try
      {
         byteInput = new PipedOutputStream(in);
      } catch (IOException e)
      {
         // does not happen
         throw new RuntimeException(e);
      }
   }

   /**
    * Default ctor.
    */
   public StringPersistencyModule()
   {
      this(new PipedInputStream(), new ByteArrayOutputStream());
   }

   /**
    * Providing buffer size for input stream
    */
   public StringPersistencyModule(int bufSize)
   {
      this(new PipedInputStream(bufSize), new ByteArrayOutputStream());
   }
   
   /**
    * @return all changes which were sent to this module as a single String
    */
   public String toString()
   {
      flush();
      try
      {
         return byteOutput.toString(getCharset().name());
      } catch (UnsupportedEncodingException e)
      {
         // should never happen
         throw new RuntimeException(e);
      }
   }

   /**
    * Append a String to the input for this module. Changes can be read with the receive* methods afterwards.
    *
    * @param input what Text to append (e.g. from {@link #toString()} of another module)
    */
   public void appendInput(String input)
   {
      try
      {
         byteInput.write(input.getBytes(getCharset().name()));
      } catch (IOException e)
      {
         // does not happen
         throw new RuntimeException(e);
      }
   }

   @Override
   public void open(boolean readonly) throws PersistencyException
   {
      if (byteOutput == null)
      {
         throw new UnsupportedOperationException("cannot reopen once the stream is closed");
      }
      super.open(readonly);
   }

   @Override
   public void flush() throws PersistencyException
   {
      try
      {
         byteInput.flush();
         super.flush();
         byteOutput.flush();
      } catch (IOException e)
      {
         throw new PersistencyException(e);
      }
   }

   @Override
   public void close() throws PersistencyException
   {
      super.close();
      try
      {
         byteOutput.close();
         byteOutput = null;
         byteInput.close();
         byteInput = null;
      } catch (IOException e)
      {
         throw new PersistencyException(e);
      }
   }
}

/*
 * $Log$
 * Revision 1.6  2008/09/05 11:27:45  cschneid
 * 1.5 compatible
 *
 * Revision 1.5  2008/09/02 15:07:28  cschneid
 * implemented open, flush and close
 *
 * Revision 1.4  2008/09/02 12:02:37  cschneid
 * javadoc
 *
 * Revision 1.3  2008/09/02 11:58:21  cschneid
 * changed StringPersistencyModule ctor to appendInput(String) method; added unit test
 *
 * Revision 1.2  2008/09/02 11:27:43  zuendorf
 * added input stream support
 *
 * Revision 1.1  2008/08/28 15:32:13  cschneid
 * Persistency Module to result in a String
 *
 */


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

package de.uni_kassel.coobra.persistency.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 28.04.2005, 10:04:57
 */
public class NonClosableOutputStream extends OutputStream
{
   private final OutputStream delegate;

   public NonClosableOutputStream( OutputStream delegate )
   {
      if ( delegate == null )
      {
         throw new NullPointerException();
      }
      this.delegate = delegate;
   }

   @Override
   public void write( int b ) throws IOException
   {
      delegate.write( b );
   }

   @Override
   public void close() throws IOException
   {
      new IOException( "Not closable!" ).printStackTrace();
      //delegate.close();
   }

   @Override
   public boolean equals( Object obj )
   {
      return delegate.equals( obj );
   }

   @Override
   public void flush() throws IOException
   {
      delegate.flush();
   }

   @Override
   public int hashCode()
   {
      return delegate.hashCode();
   }

   @Override
   public String toString()
   {
      return delegate.toString();
   }

   @Override
   public void write( byte[] b ) throws IOException
   {
      delegate.write( b );
   }

   @Override
   public void write( byte[] b, int off, int len ) throws IOException
   {
      delegate.write( b, off, len );
   }
}

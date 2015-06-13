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
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 28.04.2005, 10:02:57
 */
public class NonClosableInputStream extends InputStream
{
   private final InputStream delegate;
   private static final Logger LOG = Logger.getLogger(NonClosableInputStream.class.getName());
   private final boolean logClose;

   public NonClosableInputStream(InputStream delegate, boolean logClose)
   {
      this.logClose = logClose;
      if ( delegate == null )
      {
         throw new NullPointerException();
      }
      this.delegate = delegate;
   }

    /*private static long bytesRead;
    private static long totalBytesRead;
    private static long avrgBytesRead;

    static {
        new Thread() {
            @Override
            public void run() {
                try {
                    while ( !isInterrupted() )
                    {
                        Thread.sleep( 1000 );
                        synchronized( NonClosableInputStream.class ) {
                            long bytesRead = NonClosableInputStream.bytesRead;
                            if ( bytesRead != 0 )
                            {
                                long avrgBytesRead = NonClosableInputStream.avrgBytesRead;
                                if ( avrgBytesRead != 0 )
                                {
                                    NonClosableInputStream.avrgBytesRead = (avrgBytesRead *9 + bytesRead )/10;
                                }
                                else
                                {
                                    NonClosableInputStream.avrgBytesRead = bytesRead;
                                }
                                NonClosableInputStream.bytesRead = 0;
                                totalBytesRead += bytesRead;
                                System.out.println( "Bytes read last second: " + bytesRead + " avrg: " + avrgBytesRead );
                            }
                        }
                    }
                } catch ( InterruptedException e ) {
                    //just leave
                }
            }
        }.start();
    }

    public static long getTotalBytesRead() {
        return totalBytesRead + bytesRead;
    }*/

    /**
    * @see java.io.InputStream
    */
   @Override
   public int read() throws IOException
   {
       /*synchronized( NonClosableInputStream.class ) {
      bytesRead++;     }*/
      return delegate.read();
   }

   /**
    * @see java.io.InputStream
    */
   @Override
   public int available() throws IOException
   {
      return delegate.available();
   }

   /**
    * @see java.io.InputStream
    */
   @Override
   public void close() throws IOException
   {
      if (logClose)
      {
         LOG.log(Level.WARNING, "trying to close unclosable stream", new IOException("Not closable!"));
      }
   }

   /**
    * @see java.io.InputStream
    */
   @SuppressWarnings({"EqualsWhichDoesntCheckParameterClass"})
   @Override
   public boolean equals( Object obj )
   {
      return delegate.equals( obj );
   }

   /**
    * @see java.io.InputStream
    */
   @Override
   public int hashCode()
   {
      return delegate.hashCode();
   }

   /**
    * @see java.io.InputStream
    */
   @Override
   public synchronized void mark( int readlimit )
   {
      delegate.mark( readlimit );
   }

   /**
    * @see java.io.InputStream
    */
   @Override
   public boolean markSupported()
   {
      return delegate.markSupported();
   }

   /**
    * @see java.io.InputStream
    */
   @Override
   public int read( byte[] b ) throws IOException
   {
       int read = delegate.read( b );
       /*synchronized( NonClosableInputStream.class ) {
       if ( read > 0 )
       {
          bytesRead += read;
       }             }*/
       return read;
   }

   /**
    * @see java.io.InputStream
    */
   @Override
   public int read( byte[] b, int off, int len ) throws IOException
   {
       int read = delegate.read( b, off, len );
       /*synchronized( NonClosableInputStream.class ) {
       if ( read > 0 )
       {
           bytesRead += read;
       }      }*/
       return read;
   }

   /**
    * @see java.io.InputStream
    */
   @Override
   public synchronized void reset() throws IOException
   {
      delegate.reset();
   }

   /**
    * @see java.io.InputStream
    */
   @Override
   public long skip( long n ) throws IOException
   {
      return delegate.skip( n );
   }

   /**
    * @see java.io.InputStream
    */
   @Override
   public String toString()
   {
      return "Nonclosable " + delegate.toString();
   }
}

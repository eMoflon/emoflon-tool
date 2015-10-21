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

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.errors.ErrorHandlerModule;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;

/**
 * Store changes in a file and restore them. The module is initialized to read from the beginning of the file, but to
 * write to the end of the file.
 *
 * @author christian.schneider@uni-kassel.de
 * @created 02.09.2004, 15:56:13
 */
public class FilePersistencyModule extends AbstractStreamPersistencyModule
{
   /**
    * file for this persistency module.
    */
   private final File file;

   private RandomAccessFile access;
   private FileInputStream in;
   private FileLock fileLock;
   private FileOutputStream out;

   /**
    * @param filename name of a file to store data
    */
   public FilePersistencyModule( String filename )
   {
      this( new File( filename ) );
   }

   /**
    * @param filename name of a file to store data
    * @param binary true to use binary data, false to use text data
    */
   public FilePersistencyModule( String filename, boolean binary )
   {
      this( new File( filename ), binary );
   }

   /**
    * @param file file to store data
    * @param binary true to use binary data, false to use text data
    */
   public FilePersistencyModule( final File file, boolean binary )
   {
      super(binary);
      if ( !binary )
      {
         setCharset(Charset.defaultCharset());
      }
      if ( Change.Kind.values().length > MAXIMUM_POSSIBLE_ENCODED_KINDS )
      {
         throw new RuntimeException( "FilePersistencyModule supports only " + MAXIMUM_POSSIBLE_ENCODED_KINDS + " change kinds!" );
      }
      if ( file.isFile() || ( !file.exists() && file.getAbsoluteFile().getParentFile().isDirectory() ) )
      {
         this.file = file;
      }
      else
      {
         throw new IllegalArgumentException( "filename does not exist and does not specify a new file in an existing directory!" );
      }
   }

   /**
    * @param file file to store data
    */
   public FilePersistencyModule( final File file )
   {
      this( file, false );
   }

   /**
    * @return file this repository opens.
    */
   public File getFile()
   {
      return file;
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
      close();
      try
      {
         if ( !file.exists() )
         {
            if ( !readonly )
            {
               if ( !file.createNewFile() )
               {
                  throw new IOException( "failed to create file: " + file );
               }
            }
            else
            {
               throw new FileNotFoundException( file.getAbsolutePath() );
            }
         }
         RandomAccessFile access = new RandomAccessFile(file, readonly ? "r" : "rw");
         if ( fileLock != null )
         {
            throw new IllegalStateException("file lock should not exist");
         }
         fileLock = access.getChannel().tryLock( 0, Long.MAX_VALUE, readonly );
         if ( fileLock == null ) {
            access.close();
            throw new IOException("Failed to request lock on " + file );
         }
         this.access = access;
         access.seek( filePos );
         in = new FileInputStream( access.getFD() );
         if ( !readonly )
         {
            out = new FileOutputStream(access.getFD());
         }
         seekNotify();
         inReadOnlyMode = readonly;
      } catch ( IOException e )
      {
         throw new PersistencyException( e );
      }
   }

   /**
    * Query if this PersistencyModule is already opened.
    *
    * @return true if opened
    */
   @Override
   public boolean isOpened()
   {
      return access != null;
   }

   private long filePos;

   /**
    * close the persistency module (no more writing/reading allowed). Free resources.
    */
   @Override
   public void close()
   {
      try
      {
         if ( fileLock != null ) {
            try
            {
               fileLock.release();
            } catch (IOException e)
            {
               // well, seems already released
            }
            fileLock = null;
         }
         if ( access != null )
         {
            filePos = access.getFilePointer();
         }
         long readPos = getOpenReadPosition();
         if ( readPos != -1 )
         {
            filePos = readPos;
         }
         super.close();
         if ( access != null )
         {
            try
            {
               access.close();
            } catch (IOException e)
            {
               // well, seems already closed
            }
            access = null;
            if ( in != null )
            {
               in.close();
            }
            in = null;
            out = null;
         }
      } catch ( IOException e )
      {
         throw new PersistencyException( e );
      }
   }

   protected InputStream getInput()
   {
      if ( access == null || in == null )
      {
         throw new PersistencyException( "Not opened" );
      }
      return in;
   }

   protected long getInputPosition() throws IOException
   {
      if ( access == null || in == null )
      {
         throw new PersistencyException( "Not opened" );
      }
      return access.getFilePointer();
   }

   protected OutputStream getOutput() throws IOException
   {
      RandomAccessFile access = this.access;
      if (access == null || out == null )
      {
         throw new PersistencyException("Not opened");
      }
      return out;
   }

   protected long getOutputPosition() throws IOException
   {
      RandomAccessFile access = this.access;
      if (access == null)
      {
         throw new PersistencyException("Not opened");
      }
      return access.getFilePointer();
   }

   @Override
   protected void seekOutputToEnd() throws IOException
   {
      if ( !atEOF )
      {
         flush();
         long end = access.length();
         access.seek( end );
         seekNotify();
         atEOF = true;
      }
   }

   @Override
   protected void modifyByte(StreamEntry entry, int from, int to)
   {
      //todo: provide caching here
      synchronized ( this )
      {
         try
         {
            flush();
            RandomAccessFile access = this.access;
            if ( access == null ) throw new PersistencyException( "Not opened" );
            
            long oldPos = access.getFilePointer();
            if ( this.access == null )
            {
               throw new PersistencyException( "not opened" );
            }
            long position = entry.getFilePosition();
            access.seek(position);
            byte current = access.readByte();
            if ( current == from )
            {
               access.seek(position);
               access.writeByte( to );
               access.seek( oldPos );
            }
            else if ( current == to )
            {
               //already done - ok
               access.seek( oldPos );
            }
            else
            {
               access.seek( oldPos );
               errorUpdateLineMarker(entry, new PersistencyException("Invalid line marker"));
            }
         } catch ( IOException e )
         {
            errorUpdateLineMarker(entry, e);
         }
      }
   }

   private void errorUpdateLineMarker(StreamEntry entry, Exception exception)
   {
      if ( getRepository() != null )
      {
         getRepository().getErrorHandlerModule().error( getRepository(), ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_PERSISTENCY_UPDATE_LINE_MARKER,
               "error updating change in file: Invalid line marker found for " + entry + " (wrong file offset?)",
               exception, entry );
      }
      else
      {
         throw new IllegalStateException("no repository while trying to report error", exception);
      }
   }

   /**
    * Read the change following the specified TransactionEntry. If entry is a Transaction the first enclosed change is
    * returned, if entry is a change the next change is returned. If no changes follow the specified entry null is
    * returned.
    * If affected object is not null only the changes to this object are taken into account.
    * If versionName is not null only changes up to specified version are taken into account (null is returned if entry
    * is the last change in this version or if entry occured after this version).
    *
    * @param entry          preceeding entry
    * @param filter
    * @return the next change
    */
   @Override
   public synchronized TransactionEntry receiveNext(TransactionEntry entry, EntryFilter filter)
   {
      if ( entry != null && !( entry instanceof StreamEntry ) )
      {
         throw new ClassCastException( getClass().getName() + ".receiveNext must be called with StreamChange as parameter!" );
      }
      if ( access == null )
      {
         throw new PersistencyException( "not opened" );
      }
      if ( entry == null || ( entry != lastRead && entry != beforeLastRead ) )
      {
         try
         {
            flush();

            if ( entry == null )
            {
               if ( ( access != null && access.getFilePointer() > 0 ) ||
                     ( getOpenReadPosition() > 0 ) )
               {
                  close();
                  filePos = 0;
                  open( isInReadOnlyMode() );
               }
               else
               {
                  filePos = 0;
               }
               lastRead = null;
               beforeLastRead = null;
            }
            else
            {
               close();
               filePos = ((StreamEntry) entry).getFilePosition();
               open( isInReadOnlyMode() );
            }
            if ( entry != null )
            {
               final TransactionEntry readEntry = readChange( filter );
               //todo: provide better sanity check here
               if ( entry instanceof Transaction && readEntry instanceof Transaction ) {
                  Transaction transaction = ( Transaction ) entry;
                  Transaction readTransaction = ( ( Transaction ) readEntry );
                  if ( transaction.getReference() != readTransaction.getReference() ) {
                     throw new PersistencyException( "Expected to read entry " + entry + " but found " + readEntry );
                  }
               }
               else if ( readEntry == null )
               {
                  throw new PersistencyException( "Expected to read entry " + entry + " but did not find an entry!" );
               } else {
                  if ( entry.getEnclosingTransaction() == null )
                  {
                     if ( readEntry.getEnclosingTransaction() != null ) {
                        throw new PersistencyException( "Expected to read entry " + entry + " but found " + readEntry );
                     }
                  } else if ( readEntry.getEnclosingTransaction() == null ||
                        readEntry.getEnclosingTransaction().getReference()
                              != entry.getEnclosingTransaction().getReference() ) {
                     throw new PersistencyException( "Expected to read entry " + entry + " but found " + readEntry );
                  }
               }
            }
         } catch ( EOFException e )
         {
            return null;
         } catch ( IOException e )
         {
            throw new PersistencyException( e );
         }
         beforeLastRead = null;
         lastRead = entry;
      }
      return super.receiveNext( entry, filter);
   }


   private class FileConflictMarker extends StreamChange implements ConflictMarker {

      public void markSolved() throws IOException
      {
         synchronized ( this )
         {
            try
            {
               flush();
               if ( access == null )
               {
                  throw new PersistencyException( "not opened" );
               }
               long oldPos = access.getFilePointer();
               access.seek(this.getFilePosition());
               byte current = access.readByte();
               if ( current == '<' )
               {
                  access.seek(this.getFilePosition());
                  access.writeByte( '#' );
                  access.writeByte( ' ' );
                  access.writeByte( '-' );
                  access.writeByte( '-' );
                  access.writeByte( '-' );
                  access.writeByte( '-' );
                  access.writeByte( '-' );
               }
               else if ( current == '>' || current == '=' )
               {
                  access.seek(this.getFilePosition());
                  access.writeByte( '\n' );
                  access.writeByte( '#' );
                  access.writeByte( ' ' );
                  access.writeByte( '-' );
                  access.writeByte( '-' );
                  access.writeByte( '-' );
                  access.writeByte( '-' );
               }
               else if ( current == TextualStreamPersistencyStrategy.LINE_MARKER_COMMENT
                     || current == '\n' )
               {
                  //already done - ok
               }
               else
               {
                  errorUpdateLineMarker( this, new PersistencyException("Invalid line marker"));
               }
               access.seek( oldPos );
            } catch ( IOException e )
            {
               errorUpdateLineMarker(this, e);
            }
         }
      }
   }

   @Override
   protected StreamChange obtainConflictMarker()
   {
      if ( !isTextFormat() ) throw new UnsupportedOperationException("Conflict markers supported for text files only!");
      return new FileConflictMarker();
   }
}

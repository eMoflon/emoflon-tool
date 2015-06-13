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
import de.uni_kassel.coobra.MutableChange;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.transactions.AbstractMutableTransaction;
import de.uni_kassel.coobra.transactions.MutableTransactionEntry;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;
import de.uni_kassel.features.ClassHandler;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 02.03.2005, 15:43:51
 */
public abstract class AbstractStreamPersistencyModule extends PersistencyModule
{

   /**
    * Used as key in Changes that represent SCM conflict markers in files. This indicates the local version starts.
    */
   public static final String MANAGEMENT_KEY_CONFLICT_MARKER_LOCAL = "CONFLICT_LOCAL";
   /**
    * Used as key in Changes that represent SCM conflict markers in files. This indicates the remote version starts.
    */
   public static final String MANAGEMENT_KEY_CONFLICT_MARKER_REMOTE = "CONFLICT_REMOTE";
   /**
    * Used as key in Changes that represent SCM conflict markers in files. This indicates the end of the conflict.
    */
   public static final String MANAGEMENT_KEY_CONFLICT_MARKER_END = "CONFLICT_END";

   protected static final int MAXIMUM_POSSIBLE_ENCODED_KINDS = 10;

   private final StreamPersistencyStrategy strategy;

   /**
    * holds the last change that was read from in, null if nothing was read yet.
    */
   protected TransactionEntry lastRead;
   /**
    * holds the change that was read from in before lastRead, null if nothing was read yet or only one entry was read.
    */
   protected TransactionEntry beforeLastRead;

   protected boolean atEOF;

   public static final String HEADER_KEY_MODELNAME = "ApplicationModel";

   protected long getOpenReadPosition()
   {
      return strategy.getOpenReadPosition();
   }

   protected boolean isTextFormat()
   {
      return strategy instanceof TextualStreamPersistencyStrategy;
   }

   protected void seekNotify() throws IOException
   {
      atEOF = false;
      strategy.seekNotify();
   }

   /**
    * @param binary true to use binary data, false to use text data
    */
   protected AbstractStreamPersistencyModule(boolean binary)
   {
      if (!binary)
      {
         strategy = new TextualStreamPersistencyStrategy(this);
      } else
      {
         strategy = new BinaryStreamPersistencyStrategy(this);
      }
   }

   protected boolean inReadOnlyMode;

   public boolean isInReadOnlyMode()
   {
      return inReadOnlyMode;
   }

   @Override
   public void flush() throws PersistencyException
   {
      if (isOpened() && !isInReadOnlyMode())
      {
         strategy.flush();
      }
   }

   public void close() throws PersistencyException
   {
      if (isOpened())
      {
         flush();
         strategy.close();
      }
   }

   /**
    * getter for field charset
    *
    * @return current value of field charset
    */
   protected Charset getCharset()
   {
      return this.charset;
   }

   /**
    * store the value for field charset
    */
   private Charset charset = Charset.forName("UTF-8");

   /**
    * setter for field charset
    *
    * @param value new value
    */
   protected void setCharset(final Charset value)
   {
      if ( value == null ) throw new IllegalArgumentException("charset cannot be null");
      if (!isTextFormat())
      {
         if ( !value.name().equals(this.charset.name()) )
         {
            throw new UnsupportedOperationException("cannot change charset for binary files (always "+
                  this.charset.name()+").");
         }
         else
         {
            return;
         }
      }
      final Charset oldValue = this.charset;
      if (oldValue != value)
      {
         this.charset = value;
         if (isOpened())
         {
            boolean readOnlyMode = isInReadOnlyMode();
            close();
            open(readOnlyMode);
         }
      }
   }

   protected void checkSetup()
   {
      if (getRepository() == null)
      {
         throw new NullPointerException("Repository not set!");
      }
   }

   protected abstract OutputStream getOutput() throws IOException;

   protected abstract long getOutputPosition() throws IOException;

   protected abstract InputStream getInput() throws IOException;

   protected abstract long getInputPosition() throws IOException;

   private Map<TransactionReference, StreamTransaction> transactionReferenceMap
         = new WeakHashMap<TransactionReference, StreamTransaction>();

   void putTransactionReference(TransactionReference reference, StreamTransaction streamTransaction)
   {
      transactionReferenceMap.put(reference, streamTransaction);
   }

   void modifyMarker(StreamChange change, int from, int to)
   {
      if (!isInReadOnlyMode())
      {
         if (change instanceof ConflictMarker)
         {
            return;
         }
         if (change != null && !change.belongsTo(this))
         {
            throw new IllegalArgumentException("entry does not belong to this module!!!");
         }
         if (change != null)
         {
            if (change.getFilePosition() < 0)
            {
               throw new IllegalArgumentException("File position of change not specified - seek not possible!");
            }
         } else
         {
            throw new IllegalArgumentException("Change cannot be null!");
         }
         modifyByte(change, from, to);
      } else
      {
         //todo: what about readonly part in concatenating persistency module?!
      }
   }

   static class StreamTransactionReference extends TransactionReference
   {
      StreamTransaction transaction;

      public StreamTransactionReference()
      {
      }

      public StreamTransaction getTransaction()
      {
         return transaction;
      }
   }

   public StreamTransaction resolveTransaction(TransactionReference reference)
   {
      final StreamTransaction streamTransaction;
      if (reference instanceof StreamTransactionReference)
      {
         StreamTransactionReference streamTransactionReference = (StreamTransactionReference) reference;
         streamTransaction = streamTransactionReference.getTransaction();
      } else
      {
         streamTransaction = transactionReferenceMap.get(reference);
         if (streamTransaction == null)
         {
            throw new UnsupportedOperationException("Unknown transaction reference!");
         }
      }
      return streamTransaction;
   }

   final TransactionEntry FILTERED_ENTRY = new StreamChange();

   /**
    * Read the change following the specified TransactionEntry. If entry is a Transaction the first enclosed change is
    * returned, if entry is a change the next change is returned. If no changes follow the specified entry null is
    * returned.
    * If affected object is not null only the changes to this object are taken into account.
    * If versionName is not null only changes up to specified version are taken into account (null is returned if entry
    * is the last change in this version or if entry occured after this version).
    *
    * @param entry  preceeding entry
    * @param filter
    * @return the next change
    */
   @Override
   public synchronized TransactionEntry receiveNext(TransactionEntry entry, EntryFilter filter)
   {
      if (entry != null && !(entry instanceof StreamEntry))
      {
         throw new ClassCastException(getClass().getName() + ".receiveNext must be called with StreamChange as parameter!");
      }
      if (entry != null && beforeLastRead == entry)
      {
         return lastRead;
      }
      if (lastRead == entry)
      {
         strategy.beforeRead();
         entry = readNext(filter);

         if (filter != null && entry != null)
         {
            // TODO: optimize search for next change to an object
            // list to prevent transactions from being garbage collected
            List<Transaction> transactionList = null;
            while (entry == FILTERED_ENTRY || (entry != null && !filter.accept(entry)))
            {
               if (entry instanceof Transaction)
               {
                  if (transactionList == null)
                  {
                     transactionList = new ArrayList<Transaction>();
                  }
                  transactionList.add((Transaction) entry);
               }
               entry.delete();
               entry = readNext(filter);
            }
         } else if (entry == FILTERED_ENTRY)
         {
            throw new IllegalStateException("Entry was filtered without filter?!?");
         }

         return entry;
      } else
      {
         throw new UnsupportedOperationException("seek not supported");
      }
   }

   private TransactionEntry readNext(EntryFilter filter)
   {
      try
      {
         try
         {
            if (!atEOF)
            {
               TransactionEntry readEntry;
               do
               {
                  readEntry = readChange(filter);
               } while (readEntry == null && !atEOF);

               if (readEntry != null)
               {
                  beforeLastRead = lastRead;
                  lastRead = readEntry;
               }

               return readEntry;
            } else
            {
               return null;
            }
         } catch (EOFException e)
         {
            //EOF
            atEOF = true;
            return null;
         }
      } catch (IOException e)
      {
         //todo: improve errorhandling
         throw new PersistencyException(e);
      }
   }

   protected TransactionEntry readChange(EntryFilter filter)
         throws IOException
   {
      return strategy.readEntry(filter);
   }

   /**
    * Read the change preceeding the specified TransactionEntry. If entry is a Transaction the change before the
    * first enclosed change is returned, if entry is a change the previous change is returned.
    * If no changes are before the specified entry, null is returned.
    * If affected object is not null only the changes to this object are taken into account.
    * If versionName is not null only changes up to specified version are taken into account (null is returned if entry
    * is the first change in this version or if entry occured before this version).
    *
    * @param entry  succeeding entry
    * @param filter
    * @return the previous change
    */
   @Override
   public Change receivePrevious(TransactionEntry entry, EntryFilter filter)
   {
      throw new UnsupportedOperationException("not implemented");
   }

   private void finishWrite()
   {
      //int writeOperation = this.writeOperation;
      this.writeOperation = WRITE_IDLE;
   }

   private void startWrite()
   {
      if (writeOperation != WRITE_IDLE)
      {
         throw new IllegalStateException("Cannot nest write operations!");
      }
      writeOperation = WRITE_IN_PROGRESS;
   }

   /**
    * Store info about a change at the end of the change list. The
    * eventually enclosing transaction is <b>not</b> stored. After the
    * change was stored the returned change object must be used to reference
    * the stored change in conjunction with this persistency module.
    *
    * @param change change to be stored
    * @return the change object that must be used from now on to reference
    *         the stored transaction in conjunction with this persistency module
    */
   @Override
   public synchronized Change send(Change change, Transaction activeTransaction)
   {
      try
      {
         startWrite();
         try
         {
            StreamChange streamChange = strategy.writeChange(change, activeTransaction);
            beforeLastRead = lastRead;
            lastRead = streamChange;
            streamChange.setEnclosingTransaction((StreamTransaction) activeTransaction);
            return streamChange;
         } finally
         {
            finishWrite();
         }
      } catch (Throwable e)
      {
         throw new PersistencyException(e);
      }
   }


   protected StreamChange obtainConflictMarker()
   {
      return obtainStreamChange();
   }

   /**
    * @return usually a new StreamChange
    */
   protected StreamChange obtainStreamChange()
   {
      return new StreamChange();
   }

   /**
    * Called by {@link #send} to obtain a StreamChange that caontains the data from change.
    *
    * @param change where to copy change data from
    * @return new StreamChange
    */
   protected StreamChange fillStreamChange(Change change)
   {
      StreamChange streamChange = obtainStreamChange();
      streamChange.copyFrom(change);
      return streamChange;
   }

   public void sendEOF()
   {
      try
      {
         startWrite();
         try
         {
            strategy.writeEOF();
         } finally
         {
            finishWrite();
         }
      } catch (IOException e)
      {
         throw new PersistencyException(e);
      }
   }

   /**
    * Called before wrinting a sent entry to stream.
    */
   protected void seekOutputToEnd() throws IOException
   {
      // stream is always at the end
   }

   /**
    * Query how often the persistency module automatically flushes buffers after a few seconds of inactivity.
    *
    * @return 0 if auto flush is disabled, number of ms between auto flushs if enabled
    */
   public int getAutoFlushInterval()
   {
      return autoFlushInterval;
   }

   /**
    * @param autoFlushInterval 0 to disable auto flush, number of ms between auto flushs to enable
    * @see #getAutoFlushInterval()
    */
   public void setAutoFlushInterval(int autoFlushInterval)
   {
      this.autoFlushInterval = autoFlushInterval;
      if (autoFlushInterval == 0 && autoFlushThread != null)
      {
         autoFlushThread.interrupt();
         autoFlushThread = null;
      }
   }

   private long lastWrite;

   void triggerAutoFlush()
   {
      if (getAutoFlushInterval() != 0)
      {
         lastWrite = System.currentTimeMillis();
         if (autoFlushThread == null || !autoFlushThread.isAlive())
         {
            autoFlushThread = new AutoFlushThread(this);
            autoFlushThread.start();
         }
      }
   }

   /**
    * Store info about a transaction at the end of the transaction list. The
    * eventually enclosed transaction entries are <b>not</b> stored. After the
    * transaction was stored the returned transaction object must be used to reference
    * the stored transaction in conjunction with this persistency module.
    *
    * @param transaction       transaction to be stored
    * @param activeTransaction
    * @return the transaction object that must be used from now on to reference
    *         the stored transaction in this persistency module
    */
   @Override
   public synchronized Transaction send(Transaction transaction, Transaction activeTransaction)
   {
      //Format: t;ID;Name;timeStamp;enclosingTransaction;modifier;
      if (activeTransaction != null && !(activeTransaction instanceof StreamTransaction))
      {
         throw new ClassCastException(getClass().getName() + ".send must be called with StreamTransaction as parameter!");
      }
      try
      {
         ID transactionID = getRepository().getIdentifierModule().newID(transaction.getReference());
         // obtain ID before startWrite - it can cause changes
         startWrite();
         try
         {
            return strategy.writeTransaction(transaction, activeTransaction, transactionID);
         } finally
         {
            finishWrite();
         }
      } catch (IOException e)
      {
         throw new PersistencyException(e);
      }
   }

   public interface StreamEntry extends TransactionEntry
   {
      /**
       * @return file position of entry (entry is expected to start with marker!)
       */
      long getFilePosition();

      //void setFilePosition( long filePosition );

      boolean belongsTo(AbstractStreamPersistencyModule module);
   }

   protected class StreamChange extends MutableChange implements StreamEntry
   {
      /**
       * default ctor.
       */
      public StreamChange()
      {
         super();
      }

      /**
       * copy ctor.
       *
       * @param change where to copy from (by reference)
       */
      public StreamChange(Change change)
      {
         super(change);
      }

      long filePosition = -1;

      public long getFilePosition()
      {
         return filePosition;
      }

      public boolean belongsTo(AbstractStreamPersistencyModule module)
      {
         return AbstractStreamPersistencyModule.this == module;
      }

      public void setFilePosition(long filePosition)
      {
         this.filePosition = filePosition;
      }

      /**
       * redo the change.
       */
      @Override
      public void recommitNotify()
      {
         boolean wasCommitted = !isRolledback();
         super.recommitNotify();
         if (!wasCommitted)
         {
            strategy.markRedone(this);
         }
      }

      /**
       * undo the transaction/change. Abort it if not yet committed.
       */
      @Override
      public void rollbackNotify()
      {
         boolean wasRolledback = isRolledback();
         super.rollbackNotify();
         if (!wasRolledback)
         {
            strategy.markUndone(this);
         }
      }
   }

   /**
    * Change the marker of a change that has already been stored.
    *
    * @param entry
    * @param from                 old line marker (for error checking)
    * @param to                   new line marker
    */
   protected abstract void modifyByte(StreamEntry entry, int from, int to);

   class StreamTransaction extends AbstractMutableTransaction implements StreamEntry
   {
      public StreamTransaction(Repository repository, String name, long timestamp, Status status,
                               TransactionReference reference, int modifier)
      {
         super(repository, name, timestamp, modifier);
         if (status != null)
         {
            setStatus(status);
         }
         setReference(reference);
      }

      long filePosition;

      public long getFilePosition()
      {
         return filePosition;
      }

      public boolean belongsTo(AbstractStreamPersistencyModule module)
      {
         return AbstractStreamPersistencyModule.this == module;
      }

      protected void setFilePosition(long filePosition)
      {
         this.filePosition = filePosition;
      }

      public Iterator<? extends MutableTransactionEntry> iterator()
      {
         return new ReadingEntriesIterator(this);
      }

      public Iterator<MutableTransactionEntry> iteratorReverse()
      {
         throw new UnsupportedOperationException();
      }

      /**
       * Create a 'copy' of this entry that uses as few memory as possible and holds references as IDs.
       */
      @Override
      public Transaction externalize()
      {
         final StreamTransaction streamTransaction = new StreamTransaction(getRepository(), getName(), getTimeStamp(),
               getStatus(), getReference(), getModifier());
         streamTransaction.setFilePosition(getFilePosition());
         return streamTransaction;
      }

      private class ReadingEntriesIterator implements Iterator<MutableTransactionEntry>
      {
         private MutableTransactionEntry previousEntry;
         private MutableTransactionEntry nextEntry;
         private boolean nextFetched;

         public ReadingEntriesIterator(MutableTransactionEntry previousEntry)
         {
            this.previousEntry = previousEntry;
         }

         public boolean hasNext()
         {
            fetch();
            return nextEntry != null;
         }

         private void fetch()
         {
            if (!nextFetched)
            {
               TransactionEntry lastEntry = previousEntry;
               lastEntry = AbstractStreamPersistencyModule.this.receiveNextFromTransaction(lastEntry, StreamTransaction.this);
               nextEntry = (MutableTransactionEntry) lastEntry;
               nextFetched = true;
            }
         }

         public MutableTransactionEntry next()
         {
            fetch();
            MutableTransactionEntry next = nextEntry;
            if (next == null)
            {
               throw new NoSuchElementException();
            }
            previousEntry = next;
            nextEntry = null;
            nextFetched = false;
            return next;
         }

         public void remove()
         {
            throw new UnsupportedOperationException();
         }
      }
   }

   private static final int WRITE_IDLE = 0;
   private static final int WRITE_IN_PROGRESS = 1;
   private int writeOperation = WRITE_IDLE;
   private int autoFlushInterval = 500;
   private Thread autoFlushThread;

   private static class AutoFlushThread extends Thread
   {
      Reference<AbstractStreamPersistencyModule> moduleRef;

      /**
       * @see Thread#Thread(ThreadGroup,
       *      Runnable, String)
       */
      public AutoFlushThread(AbstractStreamPersistencyModule module)
      {
         this.moduleRef = new WeakReference<AbstractStreamPersistencyModule>(module);
         this.setDaemon(true);
      }

      /**
       * @see Runnable#run()
       */
      @Override
      public void run()
      {
         try
         {
            while (!isInterrupted())
            {
               AbstractStreamPersistencyModule module = moduleRef.get();
               if (module == null || !module.isOpened())
               {
                  return;
               }
               long interval = module.getAutoFlushInterval();
               if (interval > 0 && module.lastWrite > 0 && module.lastWrite + interval < System.currentTimeMillis())
               {
                  module.flush();
                  module.lastWrite = -1;
               }
               //noinspection UnusedAssignment
               module = null; // to make garbage collection possible
               sleep(interval);
            }
         } catch (InterruptedException e)
         {
            //simply terminate
         } catch (PersistencyException e)
         {
            //not opened
         }
      }
   }

   /**
    * Write a head with version number and application defined values to the stream.
    *
    * @param values    application defined key-value set
    * @param modelName name of the 'model' or application which is written to file
    *                  <br>
    *                  it may not be null or empty and must match the modelName provided for {@link #readHeader}
    * @throws IOException if an error writing to stream occurs
    */
   public void writeHeader(Map<String, String> values, String modelName) throws IOException
   {
      if (modelName == null || modelName.length() == 0)
      {
         throw new IllegalArgumentException("Model name cannot be null or empty!");
      }
      startWrite();
      try
      {
         strategy.writeHeader(values, modelName);
         triggerAutoFlush();
      } finally
      {
         finishWrite();
      }
   }

   /**
    * Read the header written by {@link #writeHeader} from stream, check the version number and restore key-value set.
    *
    * @param modelName name of the 'model' or application which is loaded from file
    *                  <br>
    *                  it may not be null or empty and must match the modelName provided for {@link #writeHeader}
    * @return key-value pairs passed to writeHeader
    * @throws StreamCorruptedException      if header or version tag cannot be found
    * @throws EOFException                  if EOF occurs before reading the version tag
    * @throws UnsupportedOperationException if the major version differs or the minor version is too high
    * @throws IOException                   if any other read error occurs
    */
   public Map<String, String> readHeader(String modelName) throws StreamCorruptedException, EOFException, UnsupportedOperationException,
         IOException
   {
      if (modelName != null && modelName.length() == 0)
      {
         throw new IllegalArgumentException("Model name cannot be empty!");
      }
      if (!atEOF)
      {
         return strategy.readHeader(modelName);
      } else
      {
         throw new EOFException("Cannot read header at end of file!");
      }
   }

   static class FieldInfo implements Map.Entry
   {
      private final ClassHandler classHandler;
      private final String fieldName;

      public FieldInfo(ClassHandler classHandler, String fieldName)
      {
         this.classHandler = classHandler;
         this.fieldName = fieldName;
      }

      public Object getKey()
      {
         return classHandler;
      }

      public Object getValue()
      {
         return fieldName;
      }

      public Object setValue(Object value)
      {
         throw new UnsupportedOperationException();
      }
   }
}

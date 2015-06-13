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
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.errors.ErrorHandlerModule;
import de.uni_kassel.coobra.errors.UnknownIdentifierException;
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.coobra.identifiers.RequiredRepositoryMissingException;
import de.uni_kassel.coobra.persistency.filters.ManagementOnlyFilter;
import de.uni_kassel.coobra.persistency.io.NonClosableInputStream;
import de.uni_kassel.coobra.persistency.io.NonClosableOutputStream;
import de.uni_kassel.coobra.transactions.MutableTransactionEntry;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.features.io.BinaryInputStream;
import de.uni_kassel.features.io.BinaryMapping;
import de.uni_kassel.features.io.BinaryOutputStream;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 02.03.2005, 15:43:51
 */
class BinaryStreamPersistencyStrategy extends BinarySerializationStrategy implements StreamPersistencyStrategy
{
   private final AbstractStreamPersistencyModule module;

   private BinaryOutputStream out;
   private static final int CREATE_OBJECT_ORDINAL = Change.Kind.CREATE_OBJECT.ordinal();
   protected static final int MAXIMUM_POSSIBLE_ENCODED_KINDS = 10;
   private static final int BLOCK_MARKER_TRANSACTION = 't';
   private static final int BLOCK_MARKER_CHANGE = 'c';
   private static final int BLOCK_MARKER_CHANGE_UNDONE = 'u';
   private static final int BLOCK_MARKER_CHANGE_NEXT = 'x';
   private static final int BLOCK_MARKER_CHANGE_PREVIOUS = 'v';
   private static final int BLOCK_LENGTH_MARKER_EOF = 0;
   protected static final int BLOCK_MARKER_HEADER = 'h';
   private ClassHandler transactionClassHandler;

   private static final String CHANGE_STREAM_VERSION_TAG = "CoObRA2 binary change stream - Version ";
   private static final int CHANGE_STREAM_VERSION_MAJOR = 0;
   private static final int CHANGE_STREAM_VERSION_MINOR = 1;
   private ClassHandler stringClassHandler;
   private final ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream(1000);
   private final BinaryOutputStream outputBufferBOS;
   private static final Logger LOG = Logger.getLogger(BinaryStreamPersistencyStrategy.class.getName());
   private final BinaryMapping mapping = new BinaryMapping();
   private static final Change.Kind[] KIND_VALUES = Change.Kind.values();

   protected BinaryStreamPersistencyStrategy(AbstractStreamPersistencyModule module)
   {
      this.module = module;
      outputBufferBOS = new BinaryOutputStream(outputBuffer, Long.MIN_VALUE, Charset.forName("UTF-8"),
            mapping);
   }


   /**
    * @see de.uni_kassel.coobra.persistency.StreamPersistencyStrategy#flush()
    */
   // @Override when implementing an interface is not supported by Java 1.5
   public void flush() throws PersistencyException
   {
      try
      {
         getOut().flush();
      } catch (IOException e)
      {
         throw new PersistencyException(e);
      }
   }

   private int writeChange(Change change, AbstractStreamPersistencyModule.StreamTransaction activeTransaction, BinaryOutputStream out) throws IOException
   {
      try
      {
         //CREATE_OBECT:  marker+kind ; modifier ; newValue ; object ; transaction
         //ELSE:          marker+kind ; modifier ; object ; field ; newValue ; oldValue ; key ; transaction
         // ! order used below !

         //TODO: use a bitmask here to avoid storing null fields
         out.write(change.getKind().ordinal());
         out.writeInt(change.getModifier());
         ClassHandler fieldType;
         if (change.getField() != null)
         {
            fieldType = change.getField().getType();
         } else
         {
            if (change.getKind() == Change.Kind.MANAGE)
            {
               fieldType = getStringHandler();
            } else
            {
               fieldType = null;
            }
         }
         if (!change.getKind().equals(Change.Kind.CREATE_OBJECT))
         {
            serialize(change.getAffectedObjectID(), false, out);
            out.writeField(change.getField());
            serialize(change.getNewValue(), fieldType, out);
            serialize(change.getOldValue(), fieldType, out);
            ClassHandler keyType = change.getKind() == Change.Kind.MANAGE ? getStringHandler() : null;
            serialize(change.getKey(), keyType, out);
         } else
         {
            final ID affectedObject = change.getAffectedObjectID();
            serialize(affectedObject.getClassHandler(), null, out);
            serialize(change.getAffectedObjectID(), true, out);
            ClassHandler keyType = null;
            serialize(change.getKey(), keyType, out);
         }

         Transaction transaction = change.getEnclosingTransaction();
         if (transaction == null)
         {
            transaction = activeTransaction;
         }
         serialize(transaction != null ? transaction.getReference() : null, false, out);

         if (!change.isRolledback())
         {
            return BLOCK_MARKER_CHANGE;
         } else
         {
            return BLOCK_MARKER_CHANGE_UNDONE;
         }

      } catch (UnsupportedOperationException e)
      {
         getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_PERSISTENCY_SERIALIZE_CHANGE, "failed to serialize change", e, change);
         return -1;
      } catch (ClassNotFoundException e)
      {
         getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.FATAL,
               ErrorHandlerModule.ERROR_PERSISTENCY_SERIALIZE_CHANGE, "failed to serialize change." +
               " Classloader problems?", e, change);
         return -1;
      }
   }

   private ClassHandler getStringHandler() throws ClassNotFoundException
   {
      if (stringClassHandler == null)
      {
         stringClassHandler = getRepository().getFeatureAccessModule().getClassHandler(String.class.getName());
      }
      return stringClassHandler;
   }

   public TransactionEntry readEntry(EntryFilter filter) throws IOException
   {
      BinaryInputStream in = getInput();
      long blockPosition = in.getPosition();
      int marker = in.read();
      int blockLength = in.readInt();
      blockLength += in.getPosition() - blockPosition;

      TransactionEntry entry = null;
      try
      {
         entry = readEntry(in, filter, blockPosition, marker);
      } finally
      {
         long skippedBytes = nextBlock(in, blockPosition, blockLength);
         if ( skippedBytes > 0 && entry != null && entry != module.FILTERED_ENTRY )
         {
            LOG.warning("skipped "+skippedBytes+" bytes in block at offset " + blockPosition);
         }
      }
      return entry;
   }

   protected TransactionEntry readEntry(BinaryInputStream in, EntryFilter filter, long blockPosition, final int marker) throws IOException
   {
      //CREATE_OBECT:  kind ; modifier ; newValue ; object ; transaction
      //ELSE:          kind ; modifier ; object ; field ; newValue ; oldValue ; key ; transaction
      try
      {
         boolean undone = false;
         switch (marker)
         {
            case BLOCK_MARKER_TRANSACTION:

               Transaction readTransaction = readTransaction(in, blockPosition);
               //todo: the transaction is garbage collected if it is not linked to the next change!

               return readTransaction; //changeFromReader( charArrayWalker );
            case BLOCK_MARKER_CHANGE_UNDONE:
            case BLOCK_MARKER_CHANGE_NEXT:
               undone = true;
            case BLOCK_MARKER_CHANGE:
            case BLOCK_MARKER_CHANGE_PREVIOUS:
               return readChange(in, filter, blockPosition, undone);
            case BLOCK_MARKER_HEADER:
               return null;
            case -1:
               module.atEOF = true;
               return null;
            default:
               throw new IllegalStateException("First character of block invalid: " + marker);
         }
      } catch (EOFException e)
      {
         throw e;
      } catch (Exception e)
      {
         if (getRepository() == null)
         {
            throw new IllegalStateException("Reporting error not possible - no repository set", e);
         }
         getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_PERSISTENCY_READ_LINE, "bytes at offset " + blockPosition +
               " could not be converted to change due to " + e,
               e, "<binary>");
         return null;
      }
   }

   private TransactionEntry readChange(BinaryInputStream in, EntryFilter filter, long blockPosition, boolean undone)
         throws IOException, ClassNotFoundException, NoSuchFieldException
   {
      final AbstractStreamPersistencyModule.StreamChange change = module.obtainStreamChange();

      int kind = in.read();

      if (kind >= 0 && kind < KIND_VALUES.length)
      {
         final Change.Kind kindValue = KIND_VALUES[kind];
         change.setKind(kindValue);
         if (filter instanceof ManagementOnlyFilter && kindValue != Change.Kind.MANAGE)
         {
            return module.FILTERED_ENTRY;
         }
      } else
      {
         throw new IllegalStateException("Kind of change invalid: " + kind);
      }
      change.setRolledBack(undone);

      change.setFilePosition(blockPosition);
      change.setModifier(in.readInt());

      final Object object;
      if (kind == CREATE_OBJECT_ORDINAL)
      {
         final Object newValue = deserialize(in, null);
         if (!(newValue instanceof ClassHandler))
         {
            throw new IllegalStateException("expected class but read object of type " +
                  (newValue != null ? newValue.getClass() : null));
         }

         object = getAffectedObject(in, (ClassHandler) newValue);
         change.setNewValue(newValue);

         change.setKey(deserialize(in, null));
      } else
      {
         object = deserialize(in, null);

         FieldHandler field;
         try
         {
            field = in.readField();
         } catch (NoSuchFieldException e)
         {
            if (getRepository() == null)
            {
               throw e;
            }
            getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.ERROR,
                  ErrorHandlerModule.ERROR_PERSISTENCY_MISSING_FIELD, "field not found",
                  e, null /*todo: provide FieldInfo here*/);
            // error was ignored - return null
            return null;
         }
         change.setField(field);

         ClassHandler expectedType = field != null ? field.getType() : null;
         if (change.getKind() == Change.Kind.MANAGE)
         {
            expectedType = getStringHandler();
         }
         change.setNewValue(deserialize(in, expectedType));

         change.setOldValue(deserialize(in, expectedType));

         ClassHandler keyType = change.getKind() == Change.Kind.MANAGE ? getStringHandler() : null;
         Object key = deserialize(in, keyType);
         change.setKey(key);
      }
      change.setAffectedObject(object);

      readEnclosingTransaction(in, change);

      change.setRepository(getRepository());
      if (change.getKind() == Change.Kind.MANAGE)
      {
         try
         {
            Repository.ManagementDataHandler handler = getRepository().getManagementHandler(change.getKey());
            if (handler != null)
            {
               handler.handleRead(change);
            }
         } catch (RequiredRepositoryMissingException e)
         {
            // handled later in redo
         }
      }
      return change;
   }

   private long nextBlock(BinaryInputStream in, long blockPosition, int blockLength) throws IOException
   {
      final long behind = blockPosition + blockLength - in.getPosition();
      if (behind > 0)
      {
         long skipped;
         long toBeSkipped = behind;
         do
         {
            skipped = in.skip(behind);
            toBeSkipped -= skipped;
         } while (toBeSkipped > 0 && skipped > 0);
         if (toBeSkipped > 0)
         {
            throw new IOException("failed to skip block for unknown reason");
         }
         return behind;
      } else if (behind < 0)
      {
         throw new PersistencyException("read more bytes than belonging to the block" +
               " - invalid stream data");
      } else
      {
         return 0;
      }
   }

   private AbstractStreamPersistencyModule.StreamTransaction readTransaction(BinaryInputStream in, long blockPosition) throws IOException
   {
      IdentifierModule identifierModule = getRepository().getIdentifierModule();
      final ID id = identifierModule.readID(in, getTransactionClassHandler());
      final String name = in.readString();
      final long timestamp = in.readLong();
      TransactionReference loadedReference = (TransactionReference) identifierModule.getObject(id);
      TransactionReference reference = loadedReference != null ?
            loadedReference :
            new AbstractStreamPersistencyModule.StreamTransactionReference();
      final AbstractStreamPersistencyModule.StreamTransaction transaction = module.new StreamTransaction(getRepository(), name, timestamp,
            Transaction.Status.ROLLED_BACK, reference, 0);
      if (reference instanceof AbstractStreamPersistencyModule.StreamTransactionReference)
      {
         ((AbstractStreamPersistencyModule.StreamTransactionReference) reference).transaction = transaction;
      } else
      {
         module.putTransactionReference(reference, transaction);
      }
      transaction.setFilePosition(blockPosition);
      {
         readEnclosingTransaction(in, transaction);
      }
      transaction.setModifier(in.readInt());

      if (loadedReference == null)
      {
         identifierModule.registerID(id, transaction.getReference());
      }
      return transaction;
   }

   public Repository getRepository()
   {
      return module.getRepository();
   }

   private void readEnclosingTransaction(BinaryInputStream in, MutableTransactionEntry enclosedEntry)
   {
      try
      {
         final Object ref = deserialize(in, null);
         if (ref != null && !(ref instanceof TransactionReference))
         {
            if (ref instanceof ID)
            {
               getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.ERROR,
                     ErrorHandlerModule.ERROR_PERSISTENCY_UNKNOWN_TRANSACTION,
                     "Unknown transaction: " + ref, null, ref.toString());
               return;
            } else
            {
               throw new ClassCastException("Expected transaction reference but found object of " + ref.getClass());
            }
         }
         final TransactionReference enclosing = (TransactionReference) ref;
         if (enclosing != null)
         {
            final AbstractStreamPersistencyModule.StreamTransaction streamTransaction = module.resolveTransaction(enclosing);
            //todo: this also adds it to the transaction!
            enclosedEntry.setEnclosingTransaction(streamTransaction);
         }
      } catch (UnknownIdentifierException e)
      {
         getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_PERSISTENCY_UNKNOWN_TRANSACTION,
               "Unknown transaction: " + e.getMessage(), e, e.getMessage());
      } catch (Exception e)
      {
         getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_PERSISTENCY_READ_TRANSACTION,
               "Error deserializing enclosing transaction.", e, null);
      }
   }

   private ClassHandler getTransactionClassHandler()
   {
      if (transactionClassHandler == null)
      {
         try
         {
            transactionClassHandler = getRepository().getFeatureAccessModule().getClassHandler(Transaction.class.getName());
         } catch (ClassNotFoundException e)
         {
            throw new PersistencyException("Classhandler for Transaction not found in FeatureAccessModule!");
         }
      }
      return transactionClassHandler;
   }

   private BinaryInputStream binaryInputStream;

   private BinaryInputStream getInput() throws IOException
   {
      if (binaryInputStream == null)
      {
         binaryInputStream = new BinaryInputStream(new BufferedInputStream(
               new NonClosableInputStream(module.getInput(), true)),
               module.getInputPosition(), getRepository().getFeatureAccessModule(),
               module.getCharset(), mapping);
      }
      return binaryInputStream;
   }

   protected BinaryOutputStream getOut() throws IOException
   {
      if (module.isInReadOnlyMode())
      {
         throw new PersistencyException("Writing in readonly mode!");
      }
      if (out == null)
      {
         out = new BinaryOutputStream(
               new NonClosableOutputStream(module.getOutput()), module.getOutputPosition(), module.getCharset(),
               mapping);
      }
      return out;
   }

   public void close()
   {
      out = null;
      binaryInputStream = null;
   }

   public void seekNotify() throws IOException
   {
      if (out != null)
      {
         out.setPosition(module.getInputPosition());
      }
   }

   public void markRedone(AbstractStreamPersistencyModule.StreamChange change)
   {
      module.modifyMarker(change, BLOCK_MARKER_CHANGE_UNDONE, BLOCK_MARKER_CHANGE);
   }

   public void markUndone(AbstractStreamPersistencyModule.StreamChange change)
   {
      module.modifyMarker(change, BLOCK_MARKER_CHANGE, BLOCK_MARKER_CHANGE_UNDONE);
   }

   public long getOpenReadPosition()
   {
      if (binaryInputStream != null)
      {
         return binaryInputStream.getPosition();
      }
      return -1;
   }

   public AbstractStreamPersistencyModule.StreamChange writeChange(Change change, Transaction activeTransaction) throws IOException
   {
      module.seekOutputToEnd();
      // collect change serialized data
      int marker = writeChange(change, (AbstractStreamPersistencyModule.StreamTransaction) activeTransaction, getOutBuffer());
      long blockPosition;
      if (marker > 0)
      {
         blockPosition = writeBufferAsBlock(marker);
      }
      else
      {
         blockPosition = -1;
      }
      AbstractStreamPersistencyModule.StreamChange streamChange = module.fillStreamChange(change);
      streamChange.setFilePosition(blockPosition);
      return streamChange;
   }

   private long writeBufferAsBlock(int marker)
         throws IOException
   {
      // append it if successful
      BinaryOutputStream out = getOut();
      long blockPosition = out.getPosition();
      outputBufferBOS.flush();
      out.write(marker);
      out.writeInt(outputBuffer.size());
      outputBuffer.writeTo(out);
      module.triggerAutoFlush();
      return blockPosition;
   }

   public void writeEOF() throws IOException
   {
      BinaryOutputStream out = getOut();
      module.seekOutputToEnd();
      out.writeInt(BLOCK_LENGTH_MARKER_EOF); // will be read as block length 0
      out.flush();
   }

   public Transaction writeTransaction(Transaction transaction, Transaction activeTransaction, ID transactionID) throws IOException
   {
      BinaryOutputStream out = getOutBuffer();
      module.seekOutputToEnd();
      int modifier = transaction.getModifier();
      final AbstractStreamPersistencyModule.StreamTransaction streamTransaction = module.new StreamTransaction(transaction.getRepository(),
            transaction.getName(),
            transaction.getTimeStamp(),
            transaction.getStatus(),
            transaction.getReference(),
            modifier);
      if (!(transaction.getReference() instanceof AbstractStreamPersistencyModule.StreamTransactionReference))
      {
         module.putTransactionReference(transaction.getReference(), streamTransaction);
      }
//            streamTransaction.complete = true; //written transactions should not try to read their changes
      streamTransaction.setEnclosingTransaction((AbstractStreamPersistencyModule.StreamTransaction) activeTransaction);
      transactionID.writeTo(out);
      out.writeString(transaction.getName());
      out.writeLong(transaction.getTimeStamp());
      serialize(activeTransaction != null ?
            activeTransaction.getReference()
            : null, false, out);
      out.writeInt(modifier);
      long blockPosition = writeBufferAsBlock(BLOCK_MARKER_TRANSACTION);
      streamTransaction.setFilePosition(blockPosition);
      return streamTransaction;
   }

   public void beforeRead()
   {

   }

   /**
    * Write a head with version number and application defined values to the stream.
    *
    * @param values    application defined key-value set
    * @param modelName name of the 'model' or application which is written to file
    *                  <br>
    *                  it may not be null or empty and must match the modelName provided for {@link #readHeader}
    * @throws java.io.IOException if an error writing to stream occurs
    */
   public void writeHeader(Map<String, String> values, String modelName) throws IOException
   {
      BinaryOutputStream outBuf = getOutBuffer();
      module.seekOutputToEnd();

      // write stream format version
      outBuf.writeString(CHANGE_STREAM_VERSION_TAG);
      outBuf.writeInt(CHANGE_STREAM_VERSION_MAJOR);
      outBuf.writeInt(CHANGE_STREAM_VERSION_MINOR);
      outBuf.writeString(String.valueOf(module.getCharset().name()));
      outBuf.writeString(modelName);

      // write other values
      if (values != null)
      {
         for (Map.Entry<String, String> entry : values.entrySet())
         {
            if ( entry.getKey() != null )
            {
               outBuf.writeString(entry.getKey());
               outBuf.writeString(entry.getValue());
            }
         }
      }
      outBuf.writeString(null);
      writeBufferAsBlock(BLOCK_MARKER_HEADER);
   }

   private BinaryOutputStream getOutBuffer()
   {
      outputBuffer.reset();
      BinaryOutputStream outBuf = outputBufferBOS;
      return outBuf;
   }

   /**
    * Read the header written by {@link #writeHeader} from stream, check the version number and restore key-value set.
    *
    * @param modelName name of the 'model' or application which is loaded from file
    *                  <br>
    *                  it may not be null or empty and must match the modelName provided for {@link #writeHeader}
    * @return key-value pairs passed to writeHeader
    * @throws java.io.StreamCorruptedException
    *                                       if header or version tag cannot be found
    * @throws java.io.EOFException          if EOF occurs before reading the version tag
    * @throws UnsupportedOperationException if the major version differs or the minor version is too high
    * @throws java.io.IOException           if any other read error occurs
    */
   @SuppressWarnings({"DuplicateThrows"})
   public Map<String, String> readHeader(String modelName) throws StreamCorruptedException, EOFException, UnsupportedOperationException,
         IOException
   {
      BinaryInputStream in = getInput();
      long blockPosition = in.getPosition();
      if (!checkHeaderMarker(in))
      {
         throw new StreamCorruptedException("Header block must start with '" + BLOCK_MARKER_HEADER +
               "' - invalid stream/file!");
      }
      int blockLength = in.readInt();
      blockLength += in.getPosition() - blockPosition;
      try
      {
         String tag = in.readString();
         if (!CHANGE_STREAM_VERSION_TAG.equals(tag))
         {
            throw new StreamCorruptedException("Header tag not found - invalid stream/file!");
         }
         int major = in.readInt();
         int minor = in.readInt();
         if (major != CHANGE_STREAM_VERSION_MAJOR)
         {
            throw new UnsupportedOperationException("Major version '" + major + "' not supported.");
         }
         if (minor > CHANGE_STREAM_VERSION_MINOR)
         {
            throw new UnsupportedOperationException("Minor version '" + minor + "' not supported - update your application.");
         }
         String charsetName = in.readString();
         module.setCharset(Charset.forName(charsetName));
         String readModelName = in.readString();
         Map<String, String> values = new TreeMap<String, String>();
         if (readModelName == null || !readModelName.equals(modelName))
         {
            if (modelName != null)
            {
               throw new UnsupportedOperationException("The file contains data for '" + readModelName + "', " +
                     "expected data for '" + modelName + "'");
            } else
            {
               values.put(AbstractStreamPersistencyModule.HEADER_KEY_MODELNAME, modelName);
               getRepository().getErrorHandlerModule().error(getRepository(),
                     ErrorHandlerModule.Level.WARNING, ErrorHandlerModule.ERROR_UNKNOWN,
                     "No model name specified while reading stream header!",
                     null, this);
            }
         }

         try
         {
            String key;
            while ((key = in.readString()) != null)
            {
               String value = in.readString();
               values.put(key, value);
            }
         } catch (EOFException e)
         {
            module.atEOF = true;
         }
         if ( nextBlock(in, blockPosition, blockLength) > 0 )
         {
            throw new PersistencyException("additional bytes in block - invalid stream data");
         }
         return values;
      } finally
      {
         nextBlock(in, blockPosition, blockLength);
      }
   }

   private boolean checkHeaderMarker(BinaryInputStream in)
         throws IOException
   {
      final int marker = in.read();
      return marker == BLOCK_MARKER_HEADER;
   }
}
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
import de.uni_kassel.coobra.transactions.MutableTransactionEntry;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.lang.IntegerUtil;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StreamCorruptedException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

class TextualStreamPersistencyStrategy extends TextualSerializationStrategy implements StreamPersistencyStrategy
{
   private final AbstractStreamPersistencyModule module;

   public Repository getRepository()
   {
      return module.getRepository();
   }

   TextualStreamPersistencyStrategy(AbstractStreamPersistencyModule module)
   {
      this.module = module;
   }

   protected CountingWriter out;
   private static final int CREATE_OBJECT_ORDINAL = Change.Kind.CREATE_OBJECT.ordinal();
   protected CharArrayWalker charArrayWalker;
   private static final char LINE_MARKER_TRANSACTION = 't';
   private static final char LINE_MARKER_CHANGE = 'c';
   private static final char LINE_MARKER_CHANGE_UNDONE = 'u';
   private static final char LINE_MARKER_CHANGE_NEXT = 'x';
   private static final char LINE_MARKER_CHANGE_PREVIOUS = 'v';
   private static final char LINE_MARKER_EOF = ']';
   private static final char LINE_MARKER_CONFLICT_START = '<';
   private static final char LINE_MARKER_CONFLICT_MID = '=';
   private static final char LINE_MARKER_CONFLICT_END = '>';
   protected static final char LINE_MARKER_COMMENT = '#';
   protected static final char LINE_MARKER_HEADER = 'h';
   private static final int SHOWN_CHARACTERS_OF_LINE_IN_ERROR_MESSAGE = 100;
   private static final char SEPARATOR_CHAR = ';';
   private ClassHandler transactionClassHandler;

   private static final String CHANGE_STREAM_VERSION_TAG = "CoObRA2 Change stream - Version ";
   private static final String CHANGE_STREAM_ENCODING = "Encoding";
   private static final int CHANGE_STREAM_VERSION_MAJOR = 0;
   private static final int CHANGE_STREAM_VERSION_MINOR = 3;
   private static final String LINE_SEPARATOR = System.getProperty("line.separator");
   private final StringWriter stringWriter = new StringWriter();

   private EscapingWriter escapingWriter = new EscapingWriter();

   public void beforeRead()
   {
      if (charArrayWalker != null)
      {
         charArrayWalker.streamFinished = false;
         charArrayWalker.lineFinished = false;
      }
   }

   public TransactionEntry readEntry(EntryFilter filter) throws IOException
   {
      return readChange(getCharArrayWalker(), filter);
   }

   public AbstractStreamPersistencyModule.StreamChange writeChange(Change change, Transaction activeTransaction) throws IOException
   {
      CountingWriter out = getOut();
      module.seekOutputToEnd();
      long filePosition = out.getFilePosition();
      // collect change serialized data
      StringBuffer buffer = stringWriter.getBuffer();
      buffer.delete(0, buffer.length());
      boolean dataWritten = changeToString(change, (AbstractStreamPersistencyModule.StreamTransaction) activeTransaction, stringWriter);
      if (dataWritten)
      {
         // append it if successful
         out.append(buffer);
         writeNewLine(out);
         module.triggerAutoFlush();
      }
      AbstractStreamPersistencyModule.StreamChange streamChange = module.fillStreamChange(change);
      streamChange.setFilePosition(filePosition);
      return streamChange;
   }

   private CountingWriter getOut() throws IOException
   {
      if (module.isInReadOnlyMode())
      {
         throw new PersistencyException("Writing in readonly mode!");
      }
      if (out == null)
      {
         out = new CountingWriter(
               module.getOutput(), module.getCharset()
               , module.getOutputPosition());
      }
      return out;
   }

   public void writeEOF() throws IOException
   {
      CountingWriter out = getOut();
      module.seekOutputToEnd();
      out.write(LINE_MARKER_EOF);
      writeNewLine(out);
      out.flush();
   }

   public Transaction writeTransaction(Transaction transaction, Transaction activeTransaction, final ID transactionID) throws IOException
   {
      CountingWriter out = getOut();
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
      streamTransaction.setFilePosition(out.getFilePosition());
//            streamTransaction.complete = true; //written transactions should not try to read their changes
      streamTransaction.setEnclosingTransaction((AbstractStreamPersistencyModule.StreamTransaction) activeTransaction);
      escapingWriter.out = out;
      out.write(LINE_MARKER_TRANSACTION);
      out.write(SEPARATOR_CHAR);
      out.write(transactionID.toString());
      out.write(SEPARATOR_CHAR);
      escapingWriter.write(transaction.getName());
      out.write(SEPARATOR_CHAR);
      out.write(String.valueOf(transaction.getTimeStamp()));
      out.write(SEPARATOR_CHAR);
      serialize(activeTransaction != null ?
            activeTransaction.getReference()
            : null, false, escapingWriter);
      out.write(SEPARATOR_CHAR);
      if (modifier != TransactionEntry.MODIFIER_DEFAULT)
      {
         IntegerUtil.appendTo(modifier, out);
      }
      out.write(SEPARATOR_CHAR);
      writeNewLine(out);
      module.triggerAutoFlush();
      return streamTransaction;
   }

   public void markRedone(AbstractStreamPersistencyModule.StreamChange change)
   {
      module.modifyMarker(change, LINE_MARKER_CHANGE_UNDONE, LINE_MARKER_CHANGE);
   }

   public void markUndone(AbstractStreamPersistencyModule.StreamChange change)
   {
      module.modifyMarker(change, LINE_MARKER_CHANGE, LINE_MARKER_CHANGE_UNDONE);
   }

   public void flush()
   {
      try
      {
         getOut().flush();
      } catch (IOException e)
      {
         throw new PersistencyException(e);
      }
   }

   public void writeHeader(Map<String, String> values, String modelName) throws IOException
   {
      CountingWriter out = getOut();
      module.seekOutputToEnd();


      EscapingWriter esc = escapingWriter;
      esc.out = out;

      // write stream format version
      out.append(LINE_MARKER_HEADER);
      out.append(SEPARATOR_CHAR);
      out.append(CHANGE_STREAM_VERSION_TAG);
      out.append(SEPARATOR_CHAR);
      out.append(String.valueOf(CHANGE_STREAM_VERSION_MAJOR));
      out.append(SEPARATOR_CHAR);
      out.append(String.valueOf(CHANGE_STREAM_VERSION_MINOR));
      writeNewLine(out);

      // write encoding
      out.append(LINE_MARKER_HEADER);
      out.append(SEPARATOR_CHAR);
      out.append(CHANGE_STREAM_ENCODING);
      out.append(SEPARATOR_CHAR);
      out.append(String.valueOf(module.getCharset().name()));
      writeNewLine(out);

      //write model name
      out.append(LINE_MARKER_HEADER);
      out.append(SEPARATOR_CHAR);
      out.append(AbstractStreamPersistencyModule.HEADER_KEY_MODELNAME);
      out.append(SEPARATOR_CHAR);
      out.append(modelName);
      writeNewLine(out);

      // write other values
      if (values != null)
      {
         for (Map.Entry<String, String> entry : values.entrySet())
         {
            out.append(LINE_MARKER_HEADER);
            out.append(SEPARATOR_CHAR);
            esc.append(entry.getKey());
            out.append(SEPARATOR_CHAR);
            esc.append(entry.getValue());
            writeNewLine(out);
         }
      }
   }

   private boolean checkHeaderMarker(CharArrayWalker charArrayWalker)
         throws IOException
   {
      final int markerPos = charArrayWalker.peek();
      final char marker = charArrayWalker.chars[markerPos];
      if (markerPos != charArrayWalker.lineStart &&
            (markerPos != charArrayWalker.lineStart + 1 ||
                  (charArrayWalker.chars[markerPos - 1] != '\n' && charArrayWalker.chars[markerPos - 1] != '\r')))
      {
         throw new IllegalStateException("not at the beginning of a line!");
      }
      return marker == LINE_MARKER_HEADER;
   }

   public Map<String, String> readHeader(String modelName) throws IOException
   {
      CharArrayWalker charArrayWalker = getCharArrayWalker();
      if (!checkHeaderMarker(charArrayWalker))
      {
         throw new StreamCorruptedException("Header line must start with '" + LINE_MARKER_HEADER +
               "' - invalid stream/file!");
      }
      charArrayWalker.next();
      String tag = charArrayWalker.nextString();
      if (!CHANGE_STREAM_VERSION_TAG.equals(tag))
      {
         throw new StreamCorruptedException("Header tag not found - invalid stream/file!");
      }
      try
      {
         int major = Integer.parseInt(charArrayWalker.nextString());
         int minor = Integer.parseInt(charArrayWalker.nextString().trim());
         if (major != CHANGE_STREAM_VERSION_MAJOR)
         {
            throw new UnsupportedOperationException("Major version '" + major + "' not supported.");
         }
         if (minor > CHANGE_STREAM_VERSION_MINOR)
         {
            throw new UnsupportedOperationException("Minor version '" + minor + "' not supported - update your application.");
         }
      } catch (NumberFormatException e)
      {
         throw new StreamCorruptedException("Error reading header version number - invalid stream/file: " + e.toString());
      }

      charArrayWalker.nextLine(true);

      Map<String, String> values = new TreeMap<String, String>();
      try
      {
         while (checkHeaderMarker(charArrayWalker))
         {
            charArrayWalker.next();
            String key = charArrayWalker.nextString();
            String value = charArrayWalker.nextString();
            if (CHANGE_STREAM_ENCODING.equals(key))
            {
               if (!module.getCharset().name().equals(value))
               {
                  try
						{
							module.setCharset(Charset.forName(value));
						} catch (Exception e)
						{
                     getRepository().getErrorHandlerModule().error(getRepository(),
                           ErrorHandlerModule.Level.WARNING,
                           ErrorHandlerModule.ERROR_UNKNOWN,
                           "Cannot convert charset/encoding to '" + value + "', local: " + Charset.defaultCharset().displayName(), e, null);
						}
               }
            } else if (AbstractStreamPersistencyModule.HEADER_KEY_MODELNAME.equals(key))
            {
               if (!value.equals(modelName))
               {
                  if (modelName != null)
                  {
                     throw new UnsupportedOperationException("The file contains data for '" + value + "', " +
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
            } else
            {
               values.put(key, value);
            }
            charArrayWalker.nextLine(true);
         }
      } catch (EOFException e)
      {
         module.atEOF = true;
      }
      return values;
   }

   public void close()
   {
      charArrayWalker = null;
      out = null;
   }

   public long getOpenReadPosition()
   {
      if ( charArrayWalker != null )
      {
         return charArrayWalker.getNextFilePosition();
      }
      else
      {
         return -1;
      }
   }

   public void seekNotify() throws IOException
   {
      lastLineHadConflictMarker = null;
      if ( out != null )
      {
         out.setFilePosition(module.getInputPosition());
      }
   }


   static class EscapingWriter extends Writer
   {
      Writer out;
      private char[] escapeBuf = new char[512];
      private char[] copyBuf = new char[256];

      @Override
      public Writer append(CharSequence csq) throws IOException
      {
         write(csq == null ? null : csq.toString());
         return this;
      }

      @Override
      public void write(String str) throws IOException
      {
         if (str == null)
         {
            out.append('-');
         } else
         {
            int len = str.length();
            if (len != 0)
            {
               char[] buf = copyBuf;
               if (buf.length < len)
               {
                  copyBuf = buf = new char[len];
               }
               str.getChars(0, len, buf, 0);
               write(buf, 0, len);
            }
         }
      }

      @Override
      public void close() throws IOException
      {
         out.close();
      }

      @Override
      public void flush() throws IOException
      {
         out.flush();
      }

      @Override
      public void write(char cbuf[], int off, int len) throws IOException
      {
         if (cbuf == null)
         {
            out.write('-');
         } /*else if (len == 0)
         {
            out.write('_');
         } */else
         {
            char[] myBuf = this.escapeBuf;
            if (myBuf.length < len * 2)
            {
               this.escapeBuf = myBuf = new char[len * 2];
            }
            int pos = 0;
            for (int i = off; i < off + len; i++)
            {
               char c = cbuf[i];
               switch (c)
               {
                  case '\\':
                     myBuf[pos++] = '\\';
                     myBuf[pos++] = '/';
                     break;
                  case '\n':
                     myBuf[pos++] = '\\';
                     myBuf[pos++] = 'n';
                     break;
                  case '\r':
                     myBuf[pos++] = '\\';
                     myBuf[pos++] = 'r';
                     break;
                  case ';':
                     myBuf[pos++] = '\\';
                     myBuf[pos++] = 's';
                     break;
                  default:
                     myBuf[pos++] = c;
               }
            }
            out.write(myBuf, 0, pos);
         }
      }

      @Override
      public void write(char cbuf[]) throws IOException
      {
         if (cbuf != null)
         {
            write(cbuf, 0, cbuf.length);
         } else
         {
            write(cbuf, 0, 0);
         }
      }
   }

   private boolean changeToString(Change change, AbstractStreamPersistencyModule.StreamTransaction activeTransaction, Writer out) throws IOException
   {
      try
      {
         //CREATE_OBECT:  marker+kind ; modifier ; newValue ; object ; transaction
         //ELSE:          marker+kind ; modifier ; object ; field ; newValue ; oldValue ; key ; transaction
         // ! order used below !

         EscapingWriter esc = escapingWriter;
         esc.out = out;

         if (!change.isRolledback())
         {
            out.append(LINE_MARKER_CHANGE);
         } else
         {
            out.append(LINE_MARKER_CHANGE_UNDONE);
         }
         out.append((char) ('0' + change.getKind().ordinal()));
         out.append(SEPARATOR_CHAR);
         if (change.getModifier() != TransactionEntry.MODIFIER_DEFAULT)
         {
            IntegerUtil.appendTo(change.getModifier(), out);
         }
         out.append(SEPARATOR_CHAR);
         String fieldType;
         if (change.getField() != null)
         {
            final ClassHandler type = change.getField().getType();
            fieldType = type != null ? type.getName() : null;
         } else
         {
            if (change.getKind() == Change.Kind.MANAGE)
            {
               fieldType = String.class.getName();
            } else
            {
               fieldType = null;
            }
         }
         if (!change.getKind().equals(Change.Kind.CREATE_OBJECT))
         {
            serialize(change.getAffectedObjectID(), false, esc);
            out.append(SEPARATOR_CHAR);
            esc.append((change.getField() != null ? change.getField().getName() : null));
            out.append(SEPARATOR_CHAR);
            serialize(change.getNewValue(), fieldType, esc);
            out.append(SEPARATOR_CHAR);
            serialize(change.getOldValue(), fieldType, esc);
            out.append(SEPARATOR_CHAR);
            String keyType = change.getKind() == Change.Kind.MANAGE ? String.class.getName() : null;
            serialize(change.getKey(), keyType, esc);
         } else
         {
            final ID affectedObject = change.getAffectedObjectID();
//            if ( affectedObject instanceof ID )
            {
               serialize(affectedObject.getClassHandler().getName(), String.class.getName(), esc);
            }
//            else
//            {
//               serialize( getRepository().getFeatureAccessModule().getClassHandler( affectedObject ).getName(),
//                     String.class.getName(), esc );
//            }
            out.append(SEPARATOR_CHAR);
            serialize(change.getAffectedObjectID(), true, esc);
            out.append(SEPARATOR_CHAR);
            String keyType = null;
            serialize(change.getKey(), keyType, esc);
         }
         out.append(SEPARATOR_CHAR);

         Transaction transaction = change.getEnclosingTransaction();
         if (transaction == null)
         {
            transaction = activeTransaction;
         }
         serialize(transaction != null ? transaction.getReference() : null, false, esc);
         out.append(SEPARATOR_CHAR);

         return true;

      } catch (UnsupportedOperationException e)
      {
         getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_PERSISTENCY_SERIALIZE_CHANGE, "failed to serialize change", e, change);
         return false;
      } catch (ClassNotFoundException e)
      {
         getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.FATAL,
               ErrorHandlerModule.ERROR_PERSISTENCY_SERIALIZE_CHANGE, "failed to serialize change." +
               " Classloader problems?", e, change);
         return false;
      }
   }

   protected TransactionEntry readChange(CharArrayWalker charArrayWalker, EntryFilter filter) throws IOException
   {
      //CREATE_OBECT:  kind ; modifier ; newValue ; object ; transaction
      //ELSE:          kind ; modifier ; object ; field ; newValue ; oldValue ; key ; transaction
      try
      {
         Change lastLineHadConflictMarker = this.lastLineHadConflictMarker;
         if (lastLineHadConflictMarker != null)
         {
            this.lastLineHadConflictMarker = null;
            return lastLineHadConflictMarker;
         }
         final int markerPos = charArrayWalker.next();
         if (charArrayWalker.length == 0)
         {
            throw new EOFException();
         }
         final char marker = charArrayWalker.chars[markerPos];
         if (markerPos != charArrayWalker.lineStart)
         {
            throw new IllegalStateException("not at the beginning of a line!");
         }
         boolean undone = false;
         switch (marker)
         {
            case LINE_MARKER_TRANSACTION:

               Transaction readTransaction = readTransaction(charArrayWalker, charArrayWalker.readerPosition(markerPos));
               //TODO: the transaction is garbage collected if it is not linked to the next change!

               return readTransaction; //changeFromReader( charArrayWalker );
            case LINE_MARKER_CHANGE_UNDONE:
            case LINE_MARKER_CHANGE_NEXT:
               undone = true;
            case LINE_MARKER_CHANGE:
            case LINE_MARKER_CHANGE_PREVIOUS:
               final AbstractStreamPersistencyModule.StreamChange change = module.obtainStreamChange();

               final char kindChar = charArrayWalker.chars[markerPos + 1];
               int kind = kindChar - '0';

               if (kind >= 0 && kind < Change.Kind.values().length)
               {
                  final Change.Kind kindValue = Change.Kind.values()[kind];
                  change.setKind(kindValue);
                  if (filter instanceof ManagementOnlyFilter && kindValue != Change.Kind.MANAGE)
                  {
                     charArrayWalker.nextLine(false);
                     return module.FILTERED_ENTRY;
                  }
               } else
               {
                  throw new IllegalStateException("Kind of change invalid: " + kindChar);
               }
               change.setRolledBack(undone);

               change.setFilePosition(charArrayWalker.readerPosition(markerPos));
               final int modifierPos = charArrayWalker.next();
               final int modifierLength = charArrayWalker.length;
               if (modifierLength == 0)
               {
                  change.setModifier(TransactionEntry.MODIFIER_DEFAULT);
               } else
               {
                  change.setModifier(IntegerUtil.parseInt(charArrayWalker.chars, modifierPos, modifierLength));
               }

               final Object object;
               if (kind == CREATE_OBJECT_ORDINAL)
               {
                  final int valueStringPos = charArrayWalker.nextUnescaped();
                  final int valueStringLength = charArrayWalker.length;
                  final Object newValue = deserialize(charArrayWalker.chars, valueStringPos, valueStringLength, String.class.getName());

                  final int objectStringPos = charArrayWalker.nextUnescaped();
                  final int objectStringLength = charArrayWalker.length;
                  object = getAffectedObject(charArrayWalker.chars, objectStringPos, objectStringLength, (String) newValue);
                  change.setNewValue(newValue);

                  final int keyStringPos = charArrayWalker.nextUnescaped();
                  final int keyStringLength = charArrayWalker.length;
                  change.setKey(deserialize(charArrayWalker.chars, keyStringPos, keyStringLength, null));
               } else
               {
                  try
                  {
                     final int objectStringPos = charArrayWalker.nextUnescaped();
                     final int objectStringLength = charArrayWalker.length;
                     try
                     {
                        object = deserialize(charArrayWalker.chars, objectStringPos, objectStringLength, null);
                     } catch (UnknownIdentifierException e)
                     {
                        // FIXME: to load erroneous files with delete changes for transactions
                        if (kind == Change.Kind.DESTROY_OBJECT.ordinal())
                        {
                           // object is unknown, no need to delete it - skip the change, but warn
                           getRepository().getErrorHandlerModule().error(getRepository(),
                                 ErrorHandlerModule.Level.WARNING,
                                 ErrorHandlerModule.ERROR_PERSISTENCY_READ_LINE_DESTROY,
                                 "Probable problem loading file: object to be deleted is missing.", e, null);
                           charArrayWalker.nextLine(false);
                           return null;
                        } else
                        {
                           throw e;
                        }
                     }
                  } catch (PersistencyException e)
                  {
                     throw e;
                  }

                  final int offset = charArrayWalker.nextUnescaped();
                  final int length1 = charArrayWalker.length;
                  //todo: don't create new String here
                  final String fieldName = length1 < 0 ? null : new String(charArrayWalker.chars, offset, length1);

                  final FieldHandler field;
                  if (fieldName != null)
                  {
                     final ClassHandler classHandler;
                     if (!(object instanceof ID))
                     {
                        classHandler = getRepository().getFeatureAccessModule().getClassHandler(object);
                     } else
                     {
                        classHandler = ((ID) object).getClassHandler();
                     }
                     try
                     {
                        field = classHandler.getField(fieldName);
                     } catch (NoSuchFieldException e)
                     {
                        if (getRepository() == null)
                        {
                           throw e;
                        }
                        getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.ERROR,
                              ErrorHandlerModule.ERROR_PERSISTENCY_MISSING_FIELD, "field not found: "
                              + classHandler + "." + fieldName, e, new AbstractStreamPersistencyModule.FieldInfo(classHandler, fieldName));
                        // error was ignored - return null
                        charArrayWalker.nextLine(false);
                        return null;
                     }
                  } else
                  {
                     field = null;
                  }
                  change.setField(field);

                  final int valueStringPos = charArrayWalker.nextUnescaped();
                  final int valueStringLength = charArrayWalker.length;
                  String expectedClassName = field != null && field.getType() != null ? field.getType().getName() : null;
                  if (change.getKind() == Change.Kind.MANAGE)
                  {
                     expectedClassName = String.class.getName();
                  }
                  change.setNewValue(deserialize(charArrayWalker.chars, valueStringPos, valueStringLength,
                        expectedClassName));

                  {
                     final int pos = charArrayWalker.nextUnescaped();
                     final int length = charArrayWalker.length;
                     change.setOldValue(deserialize(charArrayWalker.chars, pos, length, expectedClassName));
                  }

                  {
                     final int pos = charArrayWalker.nextUnescaped();
                     final int length = charArrayWalker.length;
                     String keyType = change.getKind() == Change.Kind.MANAGE ? String.class.getName() : null;
                     Object key = deserialize(charArrayWalker.chars, pos, length, keyType);
                     change.setKey(key);
                  }
               }
               change.setAffectedObject(object);

            {
               final int pos = charArrayWalker.nextUnescaped();
               final int length = charArrayWalker.length;
               readEnclosingTransaction(charArrayWalker, pos, length, change);
            }

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
            charArrayWalker.nextLine();
            return change;
            case LINE_MARKER_EOF:
               module.atEOF = true;
               return null;
            case LINE_MARKER_CONFLICT_START:
               return processConflictLine(charArrayWalker, markerPos, AbstractStreamPersistencyModule.MANAGEMENT_KEY_CONFLICT_MARKER_LOCAL);
            case LINE_MARKER_CONFLICT_MID:
               return processConflictLine(charArrayWalker, markerPos, AbstractStreamPersistencyModule.MANAGEMENT_KEY_CONFLICT_MARKER_REMOTE);
            case LINE_MARKER_CONFLICT_END:
               AbstractStreamPersistencyModule.StreamChange conflict = createConflictChangeMarker(
                     charArrayWalker.readerPosition(markerPos),
                     AbstractStreamPersistencyModule.MANAGEMENT_KEY_CONFLICT_MARKER_END);
               int length = charArrayWalker.length;
               char[] chars = charArrayWalker.chars;
               readConflictVersion(chars, markerPos, length, conflict);
               charArrayWalker.nextLine();
               return conflict;
            case LINE_MARKER_HEADER:
            case LINE_MARKER_COMMENT:
            case '\n':
            case '\r':
               charArrayWalker.nextLine(false);
               return null;
            default:
               throw new IllegalStateException("First character of line invalid: " + marker);
         }
      } catch (EOFException e)
      {
         throw e;
      } catch (Exception e)
      {
         String line = lineForErrorMessage(charArrayWalker);
         charArrayWalker.nextLine(false);
         if (getRepository() == null)
         {
            throw new IllegalStateException("Reporting error not possible - no repository set", e);
         }
         getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_PERSISTENCY_READ_LINE, "line could not be converted to change: " + line +
               " \ndue to " + e, e, line);
         return null;
      }
   }

   private void readConflictVersion(char[] chars, int offset, int length, AbstractStreamPersistencyModule.StreamChange change)
   {
      if (length > 8)
      {
         String versionName = new String(chars, offset + 8, length - 8);
         change.setNewValue(versionName);
      }
   }

   private TransactionEntry processConflictLine(CharArrayWalker charArrayWalker, int markerPos, String type)
         throws IOException
   {
      long filePosition = charArrayWalker.readerPosition(markerPos);
      AbstractStreamPersistencyModule.StreamChange conflictChange = createConflictChangeMarker(filePosition, type);
      charArrayWalker.nextLine();
      return conflictChange;
   }

   private AbstractStreamPersistencyModule.StreamChange createConflictChangeMarker(long filePosition, String key)
   {
      AbstractStreamPersistencyModule.StreamChange conflictChange = module.obtainConflictMarker();
      conflictChange.setFilePosition(filePosition);
      conflictChange.setKind(Change.Kind.MANAGE);
      conflictChange.setKey(key);
      conflictChange.setRepository(getRepository());
      conflictChange.setModifier(Change.MODIFIER_LOCAL);
      return conflictChange;
   }

   protected Change lastLineHadConflictMarker = null;

   private void skippedCharactersAtEndOfLine(char[] chars, int offset, int length)
   {
      if (length == 7)
      {
         boolean isConflictMarker = true;
         for (int i = offset; i < offset + length; i++)
         {
            if ('=' != chars[i])
            {
               isConflictMarker = false;
               break;
            }
         }
         if (isConflictMarker)
         {
            lastLineHadConflictMarker = createConflictChangeMarker(
                  charArrayWalker.readerPosition(offset),
                  AbstractStreamPersistencyModule.MANAGEMENT_KEY_CONFLICT_MARKER_REMOTE);
            return;
         }
      } else if (length > 7)
      {
         boolean isConflictMarker = true;
         for (int i = offset; i < offset + 7; i++)
         {
            if ('>' != chars[i])
            {
               isConflictMarker = false;
               break;
            }
         }
         if (isConflictMarker)
         {
            AbstractStreamPersistencyModule.StreamChange change = createConflictChangeMarker(
                  charArrayWalker.readerPosition(offset),
                  AbstractStreamPersistencyModule.MANAGEMENT_KEY_CONFLICT_MARKER_END);
            lastLineHadConflictMarker = change;
            readConflictVersion(chars, offset, length, change);
            return;
         }
      }
      Logger.getAnonymousLogger().warning("Found additional tokens at end of line: "
            + new String(chars, offset, length));
   }

   private String lineForErrorMessage(CharArrayWalker charArrayWalker)
   {
      int len = Math.min(SHOWN_CHARACTERS_OF_LINE_IN_ERROR_MESSAGE, charArrayWalker.chars.length - charArrayWalker.lineStart);
      String s = new String(charArrayWalker.chars, charArrayWalker.lineStart, len);
      int newLinePos = s.indexOf('\n');
      if (newLinePos >= 0)
      {
         s = s.substring(0, newLinePos);
      } else
      {
         s += "...";
      }
      return s;
   }

   private AbstractStreamPersistencyModule.StreamTransaction readTransaction(CharArrayWalker charArrayWalker, long filePosition) throws IOException
   {
      IdentifierModule identifierModule = getRepository().getIdentifierModule();
      final int offset2 = charArrayWalker.next();
      //FIXME: don't create Strings here
      final ID id = identifierModule.readID(new String(charArrayWalker.chars, offset2, charArrayWalker.length), getTransactionClassHandler());
      final int offset1 = charArrayWalker.next();
      final String name = new String(charArrayWalker.chars, offset1, charArrayWalker.length);
      final int offset = charArrayWalker.next();
      final long timestamp = Long.valueOf(new String(charArrayWalker.chars, offset, charArrayWalker.length));
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
      transaction.setFilePosition(filePosition);
      {
         final int pos = charArrayWalker.nextUnescaped();
         final int length = charArrayWalker.length;
         readEnclosingTransaction(charArrayWalker, pos, length, transaction);
      }
      final int modifierPos = charArrayWalker.next();
      final int modifierLength = charArrayWalker.length;
      if (modifierLength == 0)
      {
         transaction.setModifier(TransactionEntry.MODIFIER_DEFAULT);
      } else
      {
         transaction.setModifier(IntegerUtil.parseInt(charArrayWalker.chars, modifierPos, modifierLength));
      }

      charArrayWalker.nextLine();
      if (loadedReference == null)
      {
         identifierModule.registerID(id, transaction.getReference());
      }
      return transaction;
   }

   private void readEnclosingTransaction(CharArrayWalker charArrayWalker, int pos, int length, MutableTransactionEntry enclosedEntry)
   {
      try
      {
         final Object ref = deserialize(charArrayWalker.chars, pos, length, null);
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

   protected CharArrayWalker getCharArrayWalker() throws IOException
   {
      if (charArrayWalker == null)
      {
         Reader reader = new InputStreamReader(new NonClosableInputStream(module.getInput(), true), module.getCharset());
         charArrayWalker = new CharArrayWalker(reader, module.getInputPosition());
      }
      return charArrayWalker;
   }


   protected class CharArrayWalker
   {
      private static final int INTIAL_BUFFER_SIZE = 1024;

      char[] chars = new char[INTIAL_BUFFER_SIZE];
      int length = 0;
      private static final int MINIMUM_READ_CHARACTERS = 100;
      private long readerPosition;

      public CharArrayWalker(Reader reader, long readerPosition)
      {
         this.reader = reader;
         this.readerPosition = readerPosition;
      }

      private Reader reader;
      private int nextPos = 0;
      private int readChars = 0;
      private int lineStart = 0;
      private boolean lineFinished = false;
      private boolean streamFinished = false;

      int next() throws IOException
      {
         if (streamFinished)
         {
            throw new EOFException();
         }
         if (lineFinished)
         {
            length = 0;
            return 0;
         }
         int newPos = nextPos;
         int nextDelim = newPos;
         loop:
         while (true)
         {
            while (nextDelim >= readChars)
            {
               final int moved = fetch();
               if (moved != 0)
               {
                  newPos += moved;
                  nextDelim += moved;
               }
               if (streamFinished)
               {
                  lineFinished = true;
                  break loop;
               }
            }
            final char c = chars[nextDelim];
            switch (c)
            {
               case SEPARATOR_CHAR:
                  break loop;
               case '\r':
               case '\n':
                  if (nextDelim != lineStart)
                  {
                     lineFinished = true;
                     break loop;
                  } else
                  {
                     //skip \n after \r or empty lines
                     newPos++;
                     lineStart++;
                  }
            }
            ++nextDelim;
         }
         length = nextDelim - newPos;
         if (length == 0 && streamFinished)
         {
            throw new EOFException();
         }
         nextPos = nextDelim + 1;
         return newPos;
      }

      public int peek() throws IOException
      {
         if (lineFinished)
         {
            return 0;
         }
         int oldNextPos = nextPos;
         int oldLength = length;
         int oldLineStart = lineStart;
         int pos = next();
         lineStart = oldLineStart;
         length = oldLength;
         nextPos = oldNextPos;
         streamFinished = false;
         lineFinished = false;
         return pos;
      }

      private int fetch() throws IOException
      {
         final int moved;
         if (chars.length <= readChars + MINIMUM_READ_CHARACTERS)
         {
            int possibleMove = Math.min(lineStart, nextPos);
            if (possibleMove > MINIMUM_READ_CHARACTERS)
            {
               System.arraycopy(chars, possibleMove, chars, 0, readChars - possibleMove);
               moved = -possibleMove;
               readChars += moved;
               readerPosition -= moved;
               nextPos += moved;
               lineStart += moved;
            } else
            {
               //enlarge buffer
               final char[] newChars = new char[chars.length * 2];
               System.arraycopy(chars, 0, newChars, 0, readChars);
               chars = newChars;
               moved = 0;
            }
         } else
         {
            moved = 0;
         }
         if (mustBeReset)
         {
            reader.reset();
            reader.skip(readerPosition);
            mustBeReset = false;
         }
         final int read = reader.read(chars, readChars, chars.length - readChars);

         //Debug :
//         if ( read > 0 )
//         {
//            System.out.print( new String( chars, readChars, read ) );
//         }


         if (read >= 0)
         {
            readChars += read;
         } else
         {
            streamFinished = true;
         }
         return moved;
      }

      public long readerPosition(int offset)
      {
         return readerPosition + offset;
      }

      int nextUnescaped() throws IOException
      {
         if (streamFinished)
         {
            throw new EOFException();
         }
         if (lineFinished)
         {
            length = 0;
            return 0;
         }
         int newPos = nextPos;
         int nextDelim = newPos;
         boolean wasEscapeCharacter = false;
         int newEnd = newPos;
         loop:
         while (true)
         {
            while (nextDelim >= readChars)
            {
               final int moved = fetch();
               if (moved != 0)
               {
                  newPos += moved;
                  nextDelim += moved;
                  newEnd += moved;
               }
               if (streamFinished)
               {
                  lineFinished = true;
                  break loop;
               }
            }
            final char c = chars[nextDelim];
            if (wasEscapeCharacter)
            {
               switch (c)
               {
                  case 'n':
                     chars[newEnd++] = '\n';
                     break;
                  case 'r':
                     chars[newEnd++] = '\r';
                     break;
                  case 's':
                     chars[newEnd++] = SEPARATOR_CHAR;
                     break;
                  case '/':
                     chars[newEnd++] = '\\';
                     break;
                  default:
                     throw new IllegalStateException("Invalid escape character: " + c);
               }
               wasEscapeCharacter = false;
            } else
            {
               switch (c)
               {
                  case '\\':
                     wasEscapeCharacter = true;
                     break;
                  case SEPARATOR_CHAR:
                     break loop;
                  case '\n':
                  case '\r':
                     if (nextDelim != lineStart)
                     {
                        lineFinished = true;
                        break loop;
                     } else
                     {
                        newPos++;
                        lineStart++;
                     }
                  default:
                     chars[newEnd++] = c;
               }
            }
            ++nextDelim;
         }
         int length = newEnd - newPos;
         nextPos = nextDelim + 1;
         if (length == 1)
         {
            switch (chars[newPos])
            {
               case '-':
                  length = -1;
                  break;
//               case '_':
//                  length = 0;
//                  break;
            }
         }
         this.length = length;
         if (length == 0 && streamFinished)
         {
            throw new EOFException();
         }
         return newPos;
      }

      void nextLine() throws IOException
      {
         nextLine(true);
      }

      void nextLine(boolean reportSkipped) throws IOException
      {
         while (!lineFinished)
         {
            int skipped = next();
            if (reportSkipped && length > 0)
            {
               skippedCharactersAtEndOfLine(chars, skipped, length);
            }
         }
         lineFinished = false;
         lineStart = nextPos;
      }

      public void close() throws IOException
      {
         this.reader = null;
      }

      private boolean mustBeReset;

      public void reset()
      {
         streamFinished = false;
         mustBeReset = true;
      }

      public String nextString()
            throws IOException
      {
         int keyPos = next();
         String string = new String(chars, keyPos, length);
         return string;
      }

      public long getNextFilePosition()
      {
         return readerPosition + nextPos;
      }
   }

   private void writeNewLine(CountingWriter out)
         throws IOException
   {
      out.write(LINE_SEPARATOR);
   }


   private class CountingStream extends BufferedOutputStream
   {
      private long filePosition;
      public boolean interceptFlush;

      public CountingStream(OutputStream out, long startingPosition)
      {
         super(out);
         if (startingPosition < 0)
         {
            throw new IllegalArgumentException("start may not be smaller than zero");
         }
         filePosition = startingPosition;
      }

      public long getFilePosition()
      {
         return filePosition;
      }

      public void setFilePosition(long position)
      {
         filePosition = position;
      }

      @Override
      public synchronized void write(byte b[], int off, int len) throws IOException
      {
         super.write(b, off, len);
         if (len > 0)
         {
            filePosition += len;
            resetCharArrayWalker();
         }
      }

      @Override
      public synchronized void write(int b) throws IOException
      {
         super.write(b);
         filePosition++;
         resetCharArrayWalker();
      }

      private void resetCharArrayWalker()
      {
         if (charArrayWalker != null)
         {
            charArrayWalker.reset();
         }
      }

      @Override
      public synchronized void flush() throws IOException
      {
         if (!interceptFlush)
         {
            super.flush();
         }
      }
   }

   protected class CountingWriter extends OutputStreamWriter
   {
      private CountingStream stream;

      /**
       * Create a buffered character-output stream that uses a default-sized
       * output buffer.
       *
       * @param out an output stream
       */
      public CountingWriter(OutputStream out, Charset encoding, long startingPosition)
      {
         this(new CountingStream(out, startingPosition), encoding);
      }

      private CountingWriter(CountingStream stream, Charset encoding)
      {
         super(stream, encoding);
         this.stream = stream;
      }

      public long getFilePosition() throws IOException
      {
         stream.interceptFlush = true;
         try
         {
            flush();
         } finally
         {
            stream.interceptFlush = false;
         }
         return stream.getFilePosition();
      }

      public void setFilePosition(long position)
      {
         stream.setFilePosition(position);
      }
   }
}

/*
 * $Log$
 * Revision 1.2  2008/11/28 11:49:05  cschneid
 * removed unnecessary _ encoding for zero length strings
 *
 * Revision 1.1  2008/10/23 14:38:23  cschneid
 * introduced binary persistency modules
 *
 */


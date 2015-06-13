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
package de.uni_kassel.coobra.errors;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;

/**
 * Used by the {@link Repository} to let error be handled application specific.
 */
public abstract class ErrorHandlerModule
{
   public enum Level
   {
      /**
       * An operation could not be performed normally. Though further processing is possible without problems.
       */
      WARNING,
      /**
       * An operation could not be performed. For further processing data might be inconsistent.
       */
      ERROR,
      /**
       * An operation could not be performed. Further processing is not possible.
       */
      FATAL
   }

   /**
    * Type of error is unspecified.
    */
   public static final int ERROR_UNKNOWN = 0;
   /**
    * Redo of a change failed. No further information about the cause, possibly an exception. Context is a Change.
    */
   public static final int ERROR_REDO_CHANGE_FAILED = ConstantManager.constantCounter++;
   /**
    * Redo of a change failed. Changing the field is unsupported (e.g. readonly). Context is a Change.
    */
   public static final int ERROR_REDO_CHANGE_FAILED_UNSUPPORTED = ConstantManager.constantCounter++;
   /**
    * Redo of a change failed. No fieldhandler was found. Context is a Change.
    */
   public static final int ERROR_REDO_CHANGE_FAILED_NO_FIELDHANDLER = ConstantManager.constantCounter++;
   /**
    * Redo of a change failed. Invocation of the access method failed. Context is a Change.
    */
   public static final int ERROR_REDO_CHANGE_FAILED_INVOCATION = ConstantManager.constantCounter++;
   /**
    * Redo of a change failed. The classhandler for deleting an object was not found.
    * Should not happen if classloader behaves. Context is a Change.
    */
   public static final int ERROR_REDO_CHANGE_FAILED_DELETE_OBJECT = ConstantManager.constantCounter++;
   /**
    * Undo of a change failed. No further information about the cause, possibly an exception. Context is a Change.
    */
   public static final int ERROR_UNDO_CHANGE_FAILED = ConstantManager.constantCounter++;
   /**
    * Undo of a change failed. Changing the field is unsupported (e.g. readonly). Context is a Change.
    */
   public static final int ERROR_UNDO_CHANGE_FAILED_UNSUPPORTED = ConstantManager.constantCounter++;
   /**
    * Undo of a change failed. No fieldhandler was found. Context is a Change.
    */
   public static final int ERROR_UNDO_CHANGE_FAILED_NO_FIELDHANDLER = ConstantManager.constantCounter++;
   /**
    * Undo of a change failed. Invocation of the access method failed. Context is a Change.
    */
   public static final int ERROR_UNDO_CHANGE_FAILED_INVOCATION = ConstantManager.constantCounter++;
   /**
    * Undo of a delete object change reached the end of the persistency module while not having found
    * the delete object change. Persistence may be corrupted. Context is a Change.
    */
   public static final int ERROR_UNDO_CHANGE_WARNING_DELETE_OBJECT = ConstantManager.constantCounter++;
   /**
    * Creation of an object failed because the classhandler could not be found. Context is a Change.
    */
   public static final int ERROR_CREATE_OBJECT_FAILED = ConstantManager.constantCounter++;
   /**
    * No class handler was found while registering a new object in the change recorder. Context is the object.
    */
   public static final int ERROR_RECORD_NEW_OBJECT = ConstantManager.constantCounter++;
   /**
    * No class handler was found while recording a change in the change recorder. Context is the PropertyChangeEvent.
    */
   public static final int ERROR_RECORD_CHANGE = ConstantManager.constantCounter++;
   /**
    * An object was created while redoing a change. This is usually a bad side effect. Context is the redone change.
    */
   public static final int ERROR_RECORD_CREATE_IN_REDO = ConstantManager.constantCounter++;
   /**
    * No field handler was found while recording a change in the change recorder. Context is the PropertyChangeEvent.
    */
   public static final int ERROR_RECORD_CHANGE_FIELD = ConstantManager.constantCounter++;
   /**
    * A line of text that should represent a change could not be converted into a change. Context is the beginning
    * of the line. Actual cause is the throwable.
    */
   public static final int ERROR_PERSISTENCY_READ_LINE = ConstantManager.constantCounter++;
   /**
    * A line of text that should represent a change could not be converted into a change because a field was not found
    * which was named in the line. The context is a {@link java.util.Map.Entry Map.Entry} with a
    * {@link de.uni_kassel.features.ClassHandler ClassHandler} as key and the field name as value.
    */
   public static final int ERROR_PERSISTENCY_MISSING_FIELD = ConstantManager.constantCounter++;
   /**
    * A line of text that should represent a change could not be converted into a change.
    * The change in question seems to be a DESTROY_OBJECT change and the id was not found. Context is null.
    * Actual cause is the throwable.
    */
   public static final int ERROR_PERSISTENCY_READ_LINE_DESTROY = ConstantManager.constantCounter++;
   /**
    * Updating a line marker failed. File-Memory state is not consistent any more! Context is the change. 
    */
   public static final int ERROR_PERSISTENCY_UPDATE_LINE_MARKER = ConstantManager.constantCounter++;
   /**
    * Serializing a change failed. Stream/File-Memory state is not consistent any more! Context is the change.
    */
   public static final int ERROR_PERSISTENCY_SERIALIZE_CHANGE = ConstantManager.constantCounter++;
   /**
    * The transaction specified as enclosing transaction in a change line could not be found. Context is the id text.
    */
   public static final int ERROR_PERSISTENCY_UNKNOWN_TRANSACTION = ConstantManager.constantCounter++;
   /**
    * The transaction specified as enclosing transaction in a change line could not be read. Cause is throwable.
    * Context is null.
    */
   public static final int ERROR_PERSISTENCY_READ_TRANSACTION = ConstantManager.constantCounter++;
   /**
    * A change does specify (a field with) a type unknown to the compact mechanism. Context is the change.
    */
   public static final int ERROR_PERSISTENCY_COMPACT_TYPE_UNKNOWN = ConstantManager.constantCounter++;
   /**
    * The compact mechanism noticed a dublicate CREATE change for the same object ID. Context is the change.
    */
   public static final int ERROR_PERSISTENCY_COMPACT_DUBLICATE_CREATE = ConstantManager.constantCounter++;
   /**
    * The compact mechanism noticed a dublicate DESTROY change for the same object ID. Context is the change.
    */
   public static final int ERROR_PERSISTENCY_COMPACT_DUBLICATE_DESTROY = ConstantManager.constantCounter++;
   /**
    * The server detected a duplicate version name! Context is the version name.
    */
   public static final int ERROR_PERSISTENCY_SERVER_DUPLICATE_VERSION = ConstantManager.constantCounter++;
   /**
    * The id module for a prfix was changed. This is probably an error. Context is the prefix.
    */
   public static final int ERROR_IDENTIFIERS_CHANGED_PREFIX_MODULE = ConstantManager.constantCounter++;
   /**
    * The values/keys in the redone change do not match the type of the field. Redo impossible. Context is the change.
    */
   public static final int ERROR_REDO_TYPE_MISMATCH = ConstantManager.constantCounter++;
   /**
    * The repository received an unhandled management change. This usually indicates misconfigured repositories.
    * Context is the change.
    */
   public static final int ERROR_MANAGEMENT_UNHANDLED = ConstantManager.constantCounter++;
   /**
    * The repository caught an exception while notifying a listener. Context is the listener.
    */
   public static final int ERROR_LISTENER_EXCEPTION = ConstantManager.constantCounter++;
   /**
    * A repository did not receive a name.
    */
   public static final int ERROR_REPOSITORY_NO_NAME = ConstantManager.constantCounter++;
   /**
    * Two repositories with the same IDManager have the same name.
    */
   public static final int ERROR_REPOSITORY_NAME_DUPLICATE = ConstantManager.constantCounter++;
   /**
    * The repository was not registered with the IDManager. 
    */
   public static final int ERROR_REPOSITORY_NOT_REGISTERED = ConstantManager.constantCounter++;
   /**
    * The repository could not read the previous change from the persistency module. Undo not possible.
    */
   public static final int ERROR_UNDO_GET_PREVIOUS_UNSUPPORTED = ConstantManager.constantCounter++;
   /**
    * The repository could not read the next change from the persistency module. Redo not possible. 
    */
   public static final int ERROR_REDO_GET_NEXT_UNSUPPORTED = ConstantManager.constantCounter++;


   public static final int ERROR_OFFSET_APPLICATION_SPECIFIC = 1024;

   /**
    * This method is invoked for warnings and errors that occur while performing CoObRA 2 operations. This method
    * does not return normally when invoked with {@link Level#FATAL}.
    *
    * @param repository the repo
    * @param level      severity of the error
    * @param errorCode  a constant from ErrorHandlerModule.ERROR_*, or {@link #ERROR_OFFSET_APPLICATION_SPECIFIC} +
    *                   some application defined positive number
    * @param message    message describing the error (should not be null)
    * @param exception  the exception that caused the error, possibly null if not applicable
    * @param context    context of the error, defined by the errorCode
    * @throws UnhandledErrorException when an error could not be handled on application side.
    * @throws HandledErrorException   when a error was resolved/displayed but further processing should not be performed.
    *                                 The current operation should be silently aborted.
    */
   public final void error(Repository repository, Level level, int errorCode,
                           String message, Throwable exception, Object context)
         throws UnhandledErrorException, HandledErrorException
   {
      if (repository == null)
      {
         throw new NullPointerException("repository must not be null when reporting errors!");
      }
      if (message == null && exception != null)
      {
         message = exception.getMessage();
      }
      if ( context instanceof Change) {
         Change change = (Change) context;
         message += " ("+change+")";
      }
      handleError(level, errorCode, message, exception, context);
      if (level == Level.FATAL)
      {
         throw new UnhandledErrorException(message, exception);
      }
   }

   /**
    * This method is must be implemented by subclasses and is invoked for warnings and errors that occur
    * while performing CoObRA 2 operations. This method
    * should not return normally when invoked with {@link Level#FATAL}.
    *
    * @param level      severity of the error
    * @param errorCode  a constant from ErrorHandlerModule.ERROR_*, or {@link #ERROR_OFFSET_APPLICATION_SPECIFIC} +
    *                   some application defined positive number
    * @param message    message describing the error (should not be null)
    * @param throwable  the exception that caused the error, possibly null if not applicable
    * @param context    context of the error, defined by the errorCode @throws UnhandledErrorException when an error could not be handled on application side.
    * @throws HandledErrorException   when a error was resolved/displayed but further processing should not be performed.
    *                                 The current operation should be silently aborted.
    * @throws UnhandledErrorException when an error could not be handled on application side.
    */
   protected abstract void handleError(Level level, int errorCode,
                                       String message, Throwable throwable, Object context)
         throws UnhandledErrorException, HandledErrorException;
}

class ConstantManager
{
   static int constantCounter = 1;
}

/*
 * $Log$
 * Revision 1.7  2008/01/29 16:32:54  cschneid
 * added error message instead of ex
 *
 * Revision 1.6  2008/01/23 12:40:45  cschneid
 * error handling for missing fields
 *
 * Revision 1.5  2008/01/23 09:41:22  cschneid
 * improved error reporting for dublicate changes
 *
 * Revision 1.4  2007/06/12 13:10:36  cschneid
 * fixed transaction gc
 *
 * Revision 1.3  2007/03/05 12:58:40  cschneid
 * allow using NonResolvingVlassHandler in undo/redo
 *
 * Revision 1.2  2007/02/07 17:48:49  cschneid
 * ctr file version incremented due to new modifiers, print change context, report unopened file module
 *
 * Revision 1.1  2006/12/20 14:26:08  cschneid
 * Introduced ErrorHandlerModule
 *
 */

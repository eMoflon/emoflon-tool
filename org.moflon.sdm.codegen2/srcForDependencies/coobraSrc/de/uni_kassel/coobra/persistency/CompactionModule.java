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
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;
import de.uni_kassel.features.CollectionFieldHandler;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.features.PlainFieldHandler;
import de.uni_kassel.features.QualifiedCollectionFieldHandler;
import de.uni_kassel.features.QualifiedFieldHandler;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 04.03.2005, 10:39:17
 */
public class CompactionModule extends MemoryPersistencyModule
{
   private final PersistencyModule delegate;
   private boolean forwardOpenAndClose;
   private static final MemoryChangeImpl SKIP = new MemoryChangeImpl();

   public CompactionModule( PersistencyModule delegate, boolean forwardOpenAndClose ) {
      this( delegate );
      this.forwardOpenAndClose = forwardOpenAndClose;
   }

   public CompactionModule( PersistencyModule delegate )
   {
      if ( delegate == null )
      {
         throw new NullPointerException( "delegate may not be null!" );
      }
      this.delegate = delegate;
   }

   public synchronized void remove( TransactionEntry entry )
   {
      if ( entry instanceof MemoryChangeImpl )
      {
         MemoryChangeImpl change = ( MemoryChangeImpl ) entry;
         ID affectedObject = change.getAffectedObjectID();
         if ( affectedObject != null )
         {
            ObjectInfo objectInfo = getObjectInfo( affectedObject );
            if ( objectInfo != null )
            {
               switch ( change.getKind() )
               {
                  case ALTER_FIELD:
                     if ( objectInfo.plainFieldChanges != null )
                     {
                        objectInfo.plainFieldChanges.values().remove( change );
                     }
                     if ( objectInfo.collectionFieldChanges != null )
                     {
                        Map<Object, MemoryChangeImpl> map = objectInfo.collectionFieldChanges.get( change.getField() );
                        if ( map != null )
                        {
                           map.values().remove( change );
                        }
                     }
                     if ( objectInfo.qualifiedCollectionFieldChanges != null )
                     {
                        Map<Object, Map<Object, MemoryChangeImpl>> map = objectInfo.qualifiedCollectionFieldChanges.get( change.getField() );
                        if ( map != null )
                        {
                           Map<Object, MemoryChangeImpl> changeMap = map.get( change.getKey() );
                           if ( changeMap != null )
                           {
                              changeMap.values().remove( change );
                           }
                        }
                     }
                     break;
                  case CREATE_OBJECT:
                     if ( objectInfo.create == entry )
                     {
                        objectInfo.create = null;
                     }
                     break;
                  case DESTROY_OBJECT:
                     if ( objectInfo.destroy == entry )
                     {
                        objectInfo.destroy = null;
                     }
                     break;
               }
            }
         }  else if ( change.getAffectedObjectRaw() != null ) {
            throw new IllegalStateException( "Objects need IDs before changes can be compacted!" );
         }
      }
      if ( entry instanceof MemoryTransactionEntry )
      {
         MemoryTransactionEntry memoryTransactionEntry = ( MemoryTransactionEntry ) entry;
         delete( memoryTransactionEntry );
      }
   }

   /**
    * Compare this persisteny modules with another CompactionModule regarding the contained changes.
    * @param cmp the second module to read from
    * @param additionalChanges changes that are in cmp/second but not in this module
    * @param missingChanges changes that are in this module but not in cmp/second
    * @param conflictingChanges map from changes in this module that are also in cmp/second and pertain
    * to the same object and field(part)
    */
   public void diff( CompactionModule cmp, Set<Change> additionalChanges, Set<Change> missingChanges,
                     Map<Change, Change> conflictingChanges )
   {
      diff( this, cmp, additionalChanges, missingChanges, conflictingChanges, false );
      diff( cmp, this, missingChanges, additionalChanges, null, true );
   }

   private static void diff( CompactionModule src, CompactionModule cmp, Set<Change> additionalChanges, Set<Change> missingChanges,
                             Map<Change, Change> conflictingChanges, boolean flipped )
   {
      for ( Map.Entry<ID, ObjectInfo> entry : src.objectInfos.entrySet() )
      {
         ObjectInfo info = entry.getValue();
         ObjectInfo infoCmp = cmp.objectInfos.get( entry.getKey() );

         if ( info.create != null ) {
            MemoryChangeImpl changeCmp = infoCmp != null ? infoCmp.create : null;
            CompactionModule.cmpChanges( info.create, changeCmp, additionalChanges, missingChanges, conflictingChanges, flipped );
         }
         if ( info.destroy != null ) {
            MemoryChangeImpl changeCmp = infoCmp != null ? infoCmp.destroy : null;
            CompactionModule.cmpChanges( info.destroy, changeCmp, additionalChanges, missingChanges, conflictingChanges, flipped );
         }

         if ( info.plainFieldChanges != null )
         {
            for ( Map.Entry<FieldHandler, MemoryChangeImpl> plainEntry : info.plainFieldChanges.entrySet() )
            {
               MemoryChangeImpl change = plainEntry.getValue();
               MemoryChangeImpl changeCmp = infoCmp != null && infoCmp.plainFieldChanges != null ?
                     infoCmp.plainFieldChanges.get( plainEntry.getKey() ) : null;
               CompactionModule.cmpChanges( change, changeCmp, additionalChanges, missingChanges, conflictingChanges, flipped );
            }
         }

         if ( info.collectionFieldChanges != null )
         {
            for ( Map.Entry<FieldHandler, Map<Object, MemoryChangeImpl>> collectionEntry : info.collectionFieldChanges.entrySet() )
            {
               Map<Object, MemoryChangeImpl> fieldMap = infoCmp != null && infoCmp.collectionFieldChanges != null ?
                     infoCmp.collectionFieldChanges.get( collectionEntry.getKey() ) : null;
               for ( Map.Entry<Object, MemoryChangeImpl> fieldEntry : collectionEntry.getValue().entrySet() )
               {
                  MemoryChangeImpl change = fieldEntry.getValue();
                  MemoryChangeImpl changeCmp = fieldMap != null ? fieldMap.get( fieldEntry.getKey() ) : null;
                  CompactionModule.cmpChanges( change, changeCmp, additionalChanges, missingChanges, conflictingChanges, flipped );
               }
            }
         }

         if ( info.qualifiedCollectionFieldChanges != null )
         {
            for ( Map.Entry<FieldHandler, Map<Object, Map<Object, MemoryChangeImpl>>> qualifiedEntry : info.qualifiedCollectionFieldChanges.entrySet() )
            {
               Map<Object, Map<Object, MemoryChangeImpl>> fieldMap = infoCmp != null && infoCmp.qualifiedCollectionFieldChanges != null ?
                     infoCmp.qualifiedCollectionFieldChanges.get( qualifiedEntry.getKey() ) : null;
               for ( Map.Entry<Object, Map<Object, MemoryChangeImpl>> fieldEntry : qualifiedEntry.getValue().entrySet() )
               {
                  Map<Object, MemoryChangeImpl> keyMap = fieldMap != null ? fieldMap.get( fieldEntry.getKey() ) : null;
                  for ( Map.Entry<Object, MemoryChangeImpl> keyEntry : fieldEntry.getValue().entrySet() )
                  {
                     MemoryChangeImpl change = keyEntry.getValue();
                     MemoryChangeImpl changeCmp = keyMap != null ? keyMap.get( fieldEntry.getKey() ) : null;
                     CompactionModule.cmpChanges( change, changeCmp, additionalChanges, missingChanges, conflictingChanges, flipped );
                  }
               }
            }
         }
      }
   }

   private static void cmpChanges( MemoryChangeImpl change, MemoryChangeImpl changeCmp, Set<Change> additionalChanges,
                                   Set<Change> missingChanges, Map<Change, Change> conflictingChanges,
                                   boolean flipped )
   {
      if ( change != null && changeCmp != null )
      {
         if ( !flipped )
         {
            if ( change.getKind() != Change.Kind.CREATE_OBJECT ) {
               Object value = change.getNewValue();
               Object valueCmp = changeCmp.getNewValue();
               if ( !equals( value, valueCmp ) )
               {
                  conflictingChanges.put( change, changeCmp );
               }
               //todo: check old value?
            }
         }
      }
      else if ( change != null )
      {
         missingChanges.add( change );
      }
      else if ( changeCmp != null )
      {
         additionalChanges.add( changeCmp );
      }
   }

   private static boolean equals( Object value, Object valueCmp )
   {
      if ( value == null )
      {
         return valueCmp == null;
      }
      else
      {
         return valueCmp != null && value.equals( valueCmp );
      }
   }

   private class ObjectInfo
   {
      private MemoryChangeImpl create;
      private MemoryChangeImpl destroy;
      private Map<FieldHandler, MemoryChangeImpl> plainFieldChanges;
      private Map<FieldHandler, Map<Object, MemoryChangeImpl>> collectionFieldChanges;
      private Map<FieldHandler, Map</*Key*/Object, Map</*Value*/Object, MemoryChangeImpl>>> qualifiedCollectionFieldChanges;


      public MemoryChangeImpl putChangeToSameFieldPart( MemoryChangeImpl newChange )
      {
         switch ( newChange.getKind() )
         {
            case ALTER_FIELD:
            {
               FieldHandler field = newChange.getField();
               if ( field instanceof PlainFieldHandler )
               {
                  return putPlain( newChange );
               }
               else if ( field instanceof QualifiedCollectionFieldHandler )
               {
                  Object value = value( newChange );
                  return putQualifiedCollection( newChange, newChange.getKey(), value );
               }
               else if ( field instanceof CollectionFieldHandler )
               {
                  Object value = value( newChange );
                  return putCollection( newChange, value );

               }
               else if ( field instanceof QualifiedFieldHandler )
               {
                  if ( newChange.getKey() != null )
                  {
                     return putCollection( newChange, newChange.getKey() );
                  }
                  else
                  {
                     // allow multiple values for null key to prevent errors
                     Object value = value( newChange );
                     return putQualifiedCollection( newChange, newChange.getKey(), value );
                  }
               }
               else
               {
                  getRepository().getErrorHandlerModule().error( getRepository(), ErrorHandlerModule.Level.WARNING,
                        ErrorHandlerModule.ERROR_PERSISTENCY_COMPACT_TYPE_UNKNOWN,
                        "unknown field type in compact", null, newChange );
               }
               break;
            }
            case CREATE_OBJECT:
            {
               if ( create != null )
               {
                  getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.WARNING,
                        ErrorHandlerModule.ERROR_PERSISTENCY_COMPACT_DUBLICATE_CREATE,
                        "Internal Problem: Two changes creating " + newChange.getAffectedObjectID() + " were found! " +
                              "Contact developers to check data for dublicated changes.", null, newChange );
                  delete(newChange);
               }
               else
               {
                  create = newChange;
               }
               break;
            }
            case DESTROY_OBJECT:
            {
               if ( destroy != null )
               {
                  getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.WARNING,
                        ErrorHandlerModule.ERROR_PERSISTENCY_COMPACT_DUBLICATE_DESTROY,
                        "Internal Problem: Two changes destroying " + newChange.getAffectedObjectID() + " were found! " +
                              "Contact developers to check data for dublicated changes.", null, newChange );
                  delete(destroy);
               }
               destroy = newChange;
               break;
            }
            case MANAGE:
               break;
            default:
               getRepository().getErrorHandlerModule().error( getRepository(), ErrorHandlerModule.Level.ERROR,
                     ErrorHandlerModule.ERROR_PERSISTENCY_COMPACT_TYPE_UNKNOWN,
                     "unknown change in compact", null, newChange );
         }
         return null;
      }

      private Object value( MemoryChangeImpl newChange )
      {
         Object value;
         if ( newChange.getNewValue() != null )
         {
            if ( newChange.getOldValue() != null )
            {
               throw new UnsupportedOperationException( "Compaction of replace-changes not supported for collection" +
                     " fields - use two changes (remove+add): " + newChange );
            }
            //add
            value = newChange.getNewValue();
         }
         else if ( newChange.getOldValue() != null )
         {
            //remove
            value = newChange.getOldValue();
         }
         else
         {
            //todo: nothing changed - ignore
            value = null;
         }
         return value;
      }

      private MemoryChangeImpl putQualifiedCollection( MemoryChangeImpl newChange, Object key, Object value )
      {
         if ( qualifiedCollectionFieldChanges == null )
         {
            qualifiedCollectionFieldChanges = new HashMap<FieldHandler, Map<Object, Map<Object, MemoryChangeImpl>>>();
         }
         Map<Object, Map<Object, MemoryChangeImpl>> fieldMap = qualifiedCollectionFieldChanges.get( newChange.getField() );
         if ( fieldMap == null )
         {
            fieldMap = new HashMap<Object, Map<Object, MemoryChangeImpl>>();
            qualifiedCollectionFieldChanges.put( newChange.getField(), fieldMap );
         }

         Map<Object, MemoryChangeImpl> keyMap = fieldMap.get( key );
         if ( keyMap == null )
         {
            keyMap = new HashMap<Object, MemoryChangeImpl>();
            fieldMap.put( key, keyMap );
         }

         MemoryChangeImpl lastChange = keyMap.put( value, newChange );
         return lastChange;
      }

      private MemoryChangeImpl putCollection( MemoryChangeImpl newChange, Object key )
      {
         if ( collectionFieldChanges == null )
         {
            collectionFieldChanges = new HashMap<FieldHandler, Map<Object, MemoryChangeImpl>>();
         }
         Map<Object, MemoryChangeImpl> fieldMap = collectionFieldChanges.get( newChange.getField() );
         if ( fieldMap == null )
         {
            fieldMap = new HashMap<Object, MemoryChangeImpl>();
            collectionFieldChanges.put( newChange.getField(), fieldMap );
         }

         MemoryChangeImpl lastChange = fieldMap.put( key, newChange );
         return lastChange;
      }

      private MemoryChangeImpl putPlain( MemoryChangeImpl newChange )
      {
         if ( plainFieldChanges == null )
         {
            plainFieldChanges = new HashMap<FieldHandler, MemoryChangeImpl>();
         }
         MemoryChangeImpl lastChange = plainFieldChanges.put( newChange.getField(), newChange );
         return lastChange;
      }

      void deleteAll()
      {
         if ( create != null )
         {
            delete( create );
            create = null;
         }
         if ( destroy != null )
         {
            delete( destroy );
            destroy = null;
         }
         if ( plainFieldChanges != null )
         {
            for ( MemoryChangeImpl change : plainFieldChanges.values() )
            {
               delete( change );
            }
            plainFieldChanges = null;
         }
         if ( collectionFieldChanges != null )
         {
            for ( Map<Object, MemoryChangeImpl> map : collectionFieldChanges.values() )
            {
               for ( MemoryChangeImpl change : map.values() )
               {
                  delete( change );
               }
            }
            collectionFieldChanges = null;
         }
         if ( qualifiedCollectionFieldChanges != null )
         {
            for ( Map<Object, Map<Object, MemoryChangeImpl>> map : qualifiedCollectionFieldChanges.values() )
            {
               for ( Map<Object, MemoryChangeImpl> map2 : map.values() )
               {
                  for ( MemoryChangeImpl change : map2.values() )
                  {
                     delete( change );
                  }
               }
            }
            qualifiedCollectionFieldChanges = null;
         }
         // the object could still be referenced from other objects/changes if the object was
         // not deleted from the model cleanly (by the application)
         // TODO: check that and ignore the delete change?
      }
   }

   private Map<ID, ObjectInfo> objectInfos = new HashMap<ID, ObjectInfo>();
   private Transaction transaction;

   /**
    * Open the persistency module. If readonly parameter is false tries to open the resource for reading and writing.
    * If this is not possible, for writing only, if still not possible for reading only.<br>
    * Delegate must be already opened.
    *
    * @param readonly if true the resource is opened for reading only always
    * @throws PersistencyException if the operation fails
    */
   @Override
   public void open( boolean readonly ) throws PersistencyException
   {
      super.open( readonly );
      if ( forwardOpenAndClose ) {
         delegate.open( readonly );
      }
      if ( isReadonly() )
      {
         receive();
      }
   }

   /**
    * close the persistency module (no more writing/reading allowed). Free resources.<br>
    * Delegate must still be open as data is written on close.
    *
    * @throws PersistencyException if the operation fails
    */
   @Override
   public void close() throws PersistencyException
   {
      flush();
      super.close();
      if ( forwardOpenAndClose ) {
         delegate.close();
      }
   }

   @Override
   public void flush()
   {
      removeChangesOfDestroyedObjects();

      if ( !isReadonly() )
      {
         MemoryTransactionEntry entry = getFirst();
         while ( entry != null )
         {
            MemoryTransactionEntry next = entry.getNext();

            final Transaction enclosingTransaction = entry.getEnclosingTransaction();
            Transaction activeTransaction = null;
            if ( enclosingTransaction != null )
            {
               try
               {
                  while ( activeTransactions.peek() != enclosingTransaction )
                  {
                     activeTransactions.pop();
                     activeSentTransactions.pop();
                  }
                  activeTransaction = activeSentTransactions.peek();
               } catch ( EmptyStackException e )
               {
                  throw new IllegalStateException( "Enclosing transaction " + enclosingTransaction + " was not sent before change!" );
               }
            }

            if (entry.getModifier() == TransactionEntry.MODIFIER_INVALID )
            {
               throw new IllegalStateException("invalid change!");
            }
            final TransactionEntry sent = delegate.send( entry, activeTransaction );

            if ( entry instanceof Transaction )
            {
               activeTransactions.push( ( Transaction ) entry );
               activeSentTransactions.push( ( Transaction ) sent );
            }
            entry = next;
         }
         clear();
      }

      objectInfos.clear();
      transaction = null;
   }

   public Transaction resolveTransaction(TransactionReference reference)
   {
      throw new UnsupportedOperationException("Resolving transactions not implemented");
   }

   /**
    * removes all changes regarding objects that were created and destroyed.
    */
   public void removeChangesOfDestroyedObjects()
   {
      for ( ObjectInfo objectInfo : objectInfos.values() )
      {
         if ( objectInfo.create != null && objectInfo.destroy != null )
         {
            objectInfo.deleteAll();
         }
      }
   }

//   private void sendTransactions( MemoryTransactionEntry change )
//   {
//      Transaction transaction = change.getEnclosingTransaction();
//      while ( transaction != null )
//      {
//         if ( transactionsToBeSent.contains( transaction ) )
//         {
//            delegate.send( transaction );
//            transactionsToBeSent.remove( transaction );
//         }
//         transaction = transaction.getEnclosingTransaction();
//      }
//   }

   /**
    * Stores the change as a MemoryChange and returns that.
    *
    * @param change            what to store
    * @param activeTransaction currently active transaction
    * @return memory copy of the change
    */
   @Override
   public Change send( Change change, Transaction activeTransaction )
   {
      if ( activeTransaction != null && !( activeTransaction instanceof MemoryTransaction ) )
      {
         throw new ClassCastException( getClass().getName() + ".send must be called with a MemoryTransaction as parameter." );
      }
      if ( !receiving && isReadonly() )
      {
         throw new UnsupportedOperationException( "Opened in readonly mode!" );
      }
      return change != SKIP ? super.send( change, activeTransaction ) : SKIP;
   }


   /**
    * @return true if {@link #flush()} is called for each newly received transaction
    */
   public boolean isFlushingOnNewTransaction()
   {
      return this.flushingOnNewTransaction;
   }

   /**
    * store the value for field flushEachTransaction.
    */
   private boolean flushingOnNewTransaction = true;

   /**
    * @param value true to enable flushing on new transaction
    * @see #isFlushingOnNewTransaction()
    */
   public void setFlushingOnNewTransaction( final boolean value )
   {
      this.flushingOnNewTransaction = value;
   }

   @Override
   protected MemoryChangeImpl createMemoryChange( Change change, MemoryTransaction activeTransaction )
   {
      if ( change.isRolledback() )
      {
         return SKIP;
      }
      MemoryChangeImpl memoryChange = super.createMemoryChange( change, activeTransaction );
      if ( !change.getKind().equals( Change.Kind.MANAGE ) && transaction != activeTransaction )
      {
         if ( isFlushingOnNewTransaction() ) {
            flush();
         }
         transaction = activeTransaction;
      }
      memoryChange.setAutoResolving( false );
      try {
         merge( memoryChange );
      } finally {
         memoryChange.setAutoResolving( change.isAutoResolving() );
      }
      return memoryChange;
   }

   private synchronized void merge( MemoryChangeImpl change )
   {
      MemoryChangeImpl lastChange = putChangeToSameFieldPart( change );
      if ( lastChange != null )
      {
         boolean changeWasAutoResolving = change.isAutoResolving();
         change.setAutoResolving( false );
         try
         {
            lastChange.setAutoResolving( false );
            change.setOldValue( lastChange.getOldValue() );
            delete( lastChange );
            if ( change.getOldValue() == change.getNewValue() )
            {
               delete( change );
            }
         } finally
         {
            change.setAutoResolving( changeWasAutoResolving );
            lastChange.setAutoResolving( changeWasAutoResolving );
         }
      }
   }

   /**
    * @param change change of interest
    * @return the change that would be merged with the given change upon send
    */
   private MemoryChangeImpl putChangeToSameFieldPart( MemoryChangeImpl change )
   {
      if ( change.getKind() == Change.Kind.UNDEFINED )
      {
         throw new IllegalArgumentException("changes must not be UNDEFINED");
      }
      if ( change.getKind() == Change.Kind.MANAGE )
      {
         return null; //TODO: some management changes can be compacted as well
      }
      MemoryChangeImpl lastChange = null;
      ID affectedObject = change.getAffectedObjectID();
      if ( affectedObject != null )
      {
         ObjectInfo objectInfo = getObjectInfo( affectedObject );
         lastChange = objectInfo.putChangeToSameFieldPart( change );
      } else if ( change.getAffectedObjectRaw() != null ) {
         throw new IllegalStateException( "Objects need IDs before changes can be compacted!" );
      }
      if ( lastChange != null && lastChange.getKind() == Change.Kind.UNDEFINED )
      {
         // change was deleted
         lastChange = null;
      }
      return lastChange;
   }

   private ObjectInfo getObjectInfo( ID affectedObject )
   {
      ObjectInfo objectInfo = objectInfos.get( affectedObject );
      if ( objectInfo == null )
      {
         objectInfo = new ObjectInfo();
         objectInfos.put( affectedObject, objectInfo );
      }
      return objectInfo;
   }

   //private Set<Transaction> transactionsToBeSent = new HashSet<Transaction>();

   /**
    * Simply returns transaction - transaction info stays in memory.
    *
    * @param transaction       what to store
    * @param activeTransaction
    * @return transaction
    */
   @Override
   public Transaction send( Transaction transaction, Transaction activeTransaction )
   {
      if ( !receiving )
      {
         if ( isReadonly() )
         {
            throw new UnsupportedOperationException( "Opened in readonly mode!" );
         }
         if ( isFlushingOnNewTransaction() ) {
            flush();
         }
      }
      transaction = super.send( transaction, activeTransaction );
      return transaction;
   }

   private TransactionEntry lastReadFromDelegate;

   private boolean receiving;

   private Stack<Transaction> activeTransactions = new Stack<Transaction>();
   private Stack<Transaction> activeSentTransactions = new Stack<Transaction>();

   private void receive()
   {
      receiving = true;
      try
      {
         TransactionEntry received = lastReadFromDelegate;
         do
         {
            if ( received != null )
            {
               received = delegate.receiveNext( received );
            }
            else
            {
               received = delegate.receiveFirst();
            }

            if ( received != null )
            {
               Transaction enclosingTransaction = received.getEnclosingTransaction();
               Transaction activeTransaction = null;
               if ( enclosingTransaction != null )
               {
                  try
                  {
                     while ( activeSentTransactions.peek() != enclosingTransaction )
                     {
                        activeTransactions.pop();
                        activeSentTransactions.pop();
                     }
                     activeTransaction = activeTransactions.peek();
                  } catch ( EmptyStackException e )
                  {
                     throw new IllegalStateException( "Enclosing transaction " + enclosingTransaction + " was not sent before change!" );
                  }
               }

               final TransactionEntry stored = send( received, activeTransaction );

               lastReadFromDelegate = received;
               if ( received instanceof Transaction )
               {
                  activeTransactions.push( ( Transaction ) stored );
                  activeSentTransactions.push( ( Transaction ) received );
               }
            }
         } while ( received != null );

         flush();
      } finally
      {
         receiving = false;
      }
   }

   /**
    * Unsupported.
    *
    * @param entry          preceeding entry
    * @param filter
    * @return the next change
    * @throws PersistencyException if the operation fails
    */
   @Override
   public TransactionEntry receiveNext(TransactionEntry entry, EntryFilter filter)
   {
      if ( filter != null)
      {
         throw new UnsupportedOperationException( "Compaction module supports chronological receive only!" );
      }
      else
      { if ( isReadonly() )
         {
            return super.receiveNext( entry, filter);
         }
         else
         {
            if (entry == null)
            {
               // read changes that were sent into this module but not flushed
               return getFirst();
            } else
            {
               return ((MemoryTransactionEntry) entry).getNext();
            }
         }
      }
   }

   @Override
   public Change receivePrevious(TransactionEntry entry, EntryFilter filter)
   {
      throw new UnsupportedOperationException( "Compaction module supports chronological receive only!" );
   }

   /**
    * Set the repository this persistency module belongs to.
    *
    * @param value repository this persistency module belongs to
    * @return true if repository was changed
    */
   @Override
   public boolean setRepository( Repository value )
   {
      delegate.setRepository( value );
      return super.setRepository( value );
   }
}

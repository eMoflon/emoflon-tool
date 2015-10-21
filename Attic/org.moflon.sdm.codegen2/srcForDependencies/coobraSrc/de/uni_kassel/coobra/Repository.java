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

package de.uni_kassel.coobra;

import de.uni_kassel.coobra.adapter.ChangeRecorder;
import de.uni_kassel.coobra.adapter.PropertyChangeRecorder;
import de.uni_kassel.coobra.errors.DefaultErrorHandlerModule;
import de.uni_kassel.coobra.errors.ErrorHandlerModule;
import de.uni_kassel.coobra.errors.RepositorySetupException;
import de.uni_kassel.coobra.identifiers.DependencyHandler;
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.identifiers.IDManager;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.coobra.identifiers.RepositoryCrossReferenceException;
import de.uni_kassel.coobra.identifiers.RequiredRepositoryMissingException;
import de.uni_kassel.coobra.persistency.AbstractStreamPersistencyModule;
import de.uni_kassel.coobra.persistency.ConflictMarker;
import de.uni_kassel.coobra.persistency.FilterPersistencyModule;
import de.uni_kassel.coobra.persistency.MemoryPersistencyModule;
import de.uni_kassel.coobra.persistency.PersistencyException;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.persistency.filters.AffectedObjectFilter;
import de.uni_kassel.coobra.persistency.filters.ManagementOnlyFilter;
import de.uni_kassel.coobra.server.DefaultServerModule;
import de.uni_kassel.coobra.server.ServerModule;
import de.uni_kassel.coobra.server.errors.RemoteException;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionModule;
import de.uni_kassel.coobra.transactions.TransactionModuleImpl;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.FeatureAccessModule;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.features.InvocationException;
import de.uni_kassel.util.ConcurrentHashSet;
import de.uni_kassel.util.EmptyIterator;
import de.uni_kassel.util.PropertyChangeSourceImpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Central class for persistency management. All changes are registered and processed via this class.
 *
 * @author christian.schneider@uni-kassel.de
 * @created 02.06.2004, 13:36:59
 */
public class Repository extends PropertyChangeSourceImpl
{
   /**
    * Timeout in ms for threads accessing the repository while another thread is in operationalization (undo/redo).
    */
   private static final int OPERATIONALIZATION_TIMEOUT = 1000;
   /**
    * next change that would be redone (null if nothing can be redone)
    */
   private TransactionEntry next;
   /**
    * next change that would be undone (null if nothing can be undone)
    */
   private TransactionEntry previous;
   /**
    * next or previous has to be recalculated.
    */
   private boolean checkNextPrevious = true;
   /**
    * store value for field changeFactory.
    */
   private ChangeFactory changeFactory;
   /**
    * store value for field transactionModule.
    */
   private TransactionModule transactionModule;
   /**
    * store value for field persistencyModule
    */
   private PersistencyModule persistencyModule;
   /**
    * store value for field reflectionModule
    */
   private FeatureAccessModule featureAccessModule;
   private Transaction activeTransaction;
   /**
    * store value for field identifierModule
    */
   private IdentifierModule identifierModule;

   /**
    * the thread that currently performs undo/redo and thus holds the op. lock.
    */
   private volatile Thread operationalizationLockHolder;
   /**
    * transaction that was active before undo/redo started.
    */
   private volatile boolean inOperationalization;
   private Change operationalizedChange;
   /**
    * store handlers
    */
   private Map<Object, ManagementDataHandler> managementHandlers;
   /**
    * listens to active transaction and enclosing transactions.
    */
   private final PropertyChangeListener transactionListener = new PropertyChangeListener()
   {
      public void propertyChange( PropertyChangeEvent evt )
      {
         final Transaction activeTransaction = Repository.this.activeTransaction;
         if ( evt.getSource() == activeTransaction )
         {
            if ( activeTransaction.getStatus() == Transaction.Status.COMMITTED
                  || activeTransaction.getStatus() == Transaction.Status.ABORTED )
            {
               dropActiveTransaction( activeTransaction );
            }
         }
      }
   };

   private void dropActiveTransaction( Transaction activeTransaction )
   {
      activeTransaction.removePropertyChangeListener( transactionListener );
      setActiveTransaction( activeTransaction.getEnclosingTransaction() );
   }

   /**
    * Key for management changes to store required repositories. 
    * 
    * @see #putManagementDataHandler
    */
   public static final String MANAGEMENT_KEY_REQUIRED_REPOSITORY = "REQUIRED_REPOSITORY";

   public static final String MANAGEMENT_KEY_REPOSITORY_NAME = "REPOSITORY_NAME";
   public static final String LOGGER_NAME = "CoObRA";

   /**
    * default ctor.
    */
   public Repository()
   {
      setChangeFactory( new ChangeFactory() );
      setPersistencyModule( new MemoryPersistencyModule() );
      setTransactionModule( new TransactionModuleImpl() );
      setIdentifierModule( new IdentifierModule( this, new IDManager(),
            IdentifierModule.generateRandomPrefix( 30 ), true ) );
      setServerModule( new DefaultServerModule() );
      putManagementDataHandler( MANAGEMENT_KEY_REPOSITORY_NAME, nameHandler );
      putManagementDataHandler( MANAGEMENT_KEY_REQUIRED_REPOSITORY, requiredHandler );
   }

   /**
    * Called by ServerModule to perform updates.
    *
    * @param entry what was received from the server.
    */
   public void acknowledgeUpdate( TransactionEntry entry )
   {

      Transaction activeTransaction = getActiveTransaction();
      final Transaction enclosingTransaction = entry.getEnclosingTransaction();
      if ( enclosingTransaction == null )
      {
         activeTransaction = dropClosedTransactions( activeTransaction );
      } else if ( activeTransaction == null )
      {
         throw new IllegalStateException( "Enclosing transaction was not acknowledged before!" );
      } else
      {
         while ( enclosingTransaction.getReference() != activeTransaction.getReference() )
         {
            activeTransaction = activeTransaction.getEnclosingTransaction();
            if ( activeTransaction == null )
            {
               throw new IllegalStateException( "Enclosing transaction was not acknowledged before " +
                     "or already closed!" );
            }
            setActiveTransaction( activeTransaction );
         }
      }
      boolean entryWasAutoResolving = entry.isAutoResolving();
      entry = getPersistencyModule().send( entry, activeTransaction );
      entry.setAutoResolving(entryWasAutoResolving);

      applyUpdate(entry);
   }

   /**
    * Redo a changed received in an update. The change has already been stored (sent to persistency module). This
    * method should not be called by applications.
    * @param entry new entry
    */
   public void applyUpdate(TransactionEntry entry)
   {
      if ( entry instanceof Change && entry.isAutoResolving() )
      {
         redo( (Change) entry );
      } else
      {
         entry.commit();
      }
      notifyListeners( entry, RepositoryListener.EventType.UPDATE );

      if ( entry instanceof Change )
      {
         setPreviousAndNext( (Change) entry, null );
      } else if ( entry instanceof Transaction )
      {
         setActiveTransaction( (Transaction) entry );
      }
   }

   /**
    * each change that is managed by this repository is passed to this method upon occurence.
    *
    * @param change what has changed
    * @return sent change
    */
   public synchronized Change acknowledgeChange( Change change )
   {
      untouched = false;
      Transaction activeTransaction = getActiveTransaction();
      if ( !isInOperationalization() )
      {
         activeTransaction = dropClosedTransactions( activeTransaction );
         boolean isTransient;
         isTransient = change.getField() != null && Boolean.TRUE.equals(change.getField().isTransient());
         if ( !isTransient )
         {
            if ( change.isAutoResolving() )
            {
               if ( change.getNewValue() != null && change.getField() != null && change.getField().getType() != null )
               {
                  if ( !change.getField().getType().isInstance( change.getNewValue() ) )
                  {
                     getErrorHandlerModule().error( this, ErrorHandlerModule.Level.ERROR,
                           ErrorHandlerModule.ERROR_REDO_TYPE_MISMATCH, "error applying change - " +
                           "new value and field type do not match", null, change );
                     return null;
                  }
               }
            }
            //todo: make id assignment and reference check optinal
            if ( Change.Kind.CREATE_OBJECT.equals( change.getKind() ) )
            {
               IdentifierModule identifierModule = getIdentifierModule();
               if ( identifierModule.getID( change.getAffectedObject() ) == null )
               {
                  identifierModule.newID( change.getAffectedObject() );
               }
            }
            ChangeAcceptance changeAcceptance = changeAcceptance(change);
            if (changeAcceptance != ChangeAcceptance.BOTH_ACCEPTED)
            {
               Object newValue = changeAcceptance == ChangeAcceptance.NEW_ACCEPTED ? change.getNewValue() : null;
               Object oldValue = changeAcceptance == ChangeAcceptance.OLD_ACCEPTED ? change.getOldValue() : null;
               if ( oldValue != newValue )
               {
                  final MutableChange mutableChange;
                  if ( change instanceof MutableChange )
                  {
                     mutableChange = (MutableChange) change; //todo: is it ok to mutate the change itself?
                  } else
                  {
                     mutableChange = new MutableChange( change );
                     change = mutableChange;
                  }
                  mutableChange.setNewValue( newValue );
                  mutableChange.setOldValue( oldValue );
               } else
               {
                  return null; //if not-accepted values are changed the changes would be useless
               }
            }

            notifyListeners( change, RepositoryListener.EventType.RECORD_CHANGE );
            Change sentChange = getPersistencyModule().send( change, activeTransaction );
            setPreviousAndNext( sentChange, null );
            notifyListeners( sentChange, RepositoryListener.EventType.RECORDED_CHANGE );
            return sentChange;
         } else
         {
            return change;
         }
      } else
      {
         //ignore changes in undo/redo
         return null;
      }
   }

   private enum ChangeAcceptance
   {
      NOT_ACCEPTED, BOTH_ACCEPTED, NEW_ACCEPTED, OLD_ACCEPTED
   }

   private ChangeAcceptance changeAcceptance(Change change)
   {
      final boolean keyAccepted = checkRepositoryCrossReference( change.getKey() );
      if ( !keyAccepted )
      {
         return ChangeAcceptance.NOT_ACCEPTED; //cannot accept a change for a key that is not in this repository
      }
      //check if we have unaccepted cross references
      final boolean newValueAccepted = checkRepositoryCrossReference( change.getNewValue() );
      final boolean oldValueAccepted = checkRepositoryCrossReference( change.getOldValue() );
      if ( newValueAccepted )
      {
         return oldValueAccepted ? ChangeAcceptance.BOTH_ACCEPTED : ChangeAcceptance.NEW_ACCEPTED;
      }
      else
      {
         return oldValueAccepted ? ChangeAcceptance.OLD_ACCEPTED : ChangeAcceptance.NOT_ACCEPTED;
      }
   }

   /**
    * Checks acceptance of a change concerning cross references.
    * @param change the change of interest
    * @return true if the change would be sent to the persistency module
    */
   public boolean changeWouldBeAccepted(Change change)
   {
      ChangeAcceptance changeAcceptance = changeAcceptance(change);
      switch (changeAcceptance)
      {
         case BOTH_ACCEPTED:
            return true;
         case NEW_ACCEPTED:
            return change.getNewValue() != null;
         case OLD_ACCEPTED:
            return change.getOldValue() != null;
         case NOT_ACCEPTED:
            return false;
      }
      throw new IllegalArgumentException("Unknown result: " + changeAcceptance);
   }

   private Transaction dropClosedTransactions( Transaction activeTransaction )
   {
      while ( activeTransaction != null && activeTransaction.getStatus() != Transaction.Status.STARTED )
      {
         dropActiveTransaction( activeTransaction );
         activeTransaction = getActiveTransaction();
      }
      return activeTransaction;
   }

   private void notifyListeners( TransactionEntry sentEntry, RepositoryListener.EventType type )
   {
      //todo: invoke this only for _sent_ changes ?!
      if ( listeners != null )
      {
         for ( int i = listeners.size() - 1; i >= 0; i-- )
         {
            RepositoryListener listener = listeners.get( i );
            try
            {
               listener.acknowledgeChange( sentEntry, type );
            } catch (Exception e)
            {
               getErrorHandlerModule().error(this, ErrorHandlerModule.Level.WARNING,
                     ErrorHandlerModule.ERROR_LISTENER_EXCEPTION, "Error notifying listener: " + e, e, listener );
            }
         }
      }
   }

   /**
    * Repository listeners can be subscribed to be notified on each model change.
    */
   public static interface RepositoryListener
   {
      public enum EventType
      {
         /**
          * This event is fired <u>before</u> a change is written to the persistency module.
          * Thus the change passed to the listener is <u>not</u> a change from the persistency module
          * and must not be used to reference/receive changes.
          */
         RECORD_CHANGE,
         /**
          * TODO
          */
         RECORDED_CHANGE,
         UPDATE,
         REDO,
         UNDO,
         RESTORED_TRANSACTION
      }

      /**
       * Called upon occurence of a change to the model.
       *
       * @param entry what has changed
       * @param type why this method is invoked, see {@link EventType}
       */
      void acknowledgeChange( TransactionEntry entry, EventType type );
   }

   /**
    * list of listeners.
    */
   private ArrayList<RepositoryListener> listeners;

   /**
    * Subscribe listener to be notified on each model change.
    *
    * @param listener what to subscribe
    */
   public void addListener( RepositoryListener listener )
   {
      if ( listener != null )
      {
         if ( listeners == null )
         {
            listeners = new ArrayList<RepositoryListener>();
         } else if ( listeners.contains( listener ) )
         {
            return; // already in list
         }
         listeners.add( listener );
      }
   }

   /**
    * Unsubscribe listener.
    *
    * @param listener what to be unsubscribed.
    */
   public void removeListener( RepositoryListener listener )
   {
      if ( listeners != null )
      {
         listeners.remove( listener );
      }
   }

   /**
    * Check if the a reference is across repository bounds.
    *
    * @param introducedReference referenced object
    * @throws RepositoryCrossReferenceException if reference is not allowed
    * @return true if reference was accepted
    */
   private boolean checkRepositoryCrossReference( Object introducedReference ) throws RepositoryCrossReferenceException
   {
      boolean accepted = true;
      if ( introducedReference != null )
      {
         final ID id = getIdentifierModule().getID( introducedReference );
         if ( id != null )
         {
            final IDManager manager = getIdentifierModule().getManager();
            final IdentifierModule identifierModuleOfReference = manager.getIdentifierModule( id );
            if ( identifierModuleOfReference != null )
            {
               if ( identifierModuleOfReference != getIdentifierModule() )
               {
                  final Repository referencedRepository = identifierModuleOfReference.getRepository();
                  if ( referencedRepository != this )
                  {
                     if ( !hasInRequiredRepositories( referencedRepository ) )
                     {
                        if ( hasInIgnoredRepositories( referencedRepository ) )
                        {
                           accepted = false;
                        } else
                        {
                           final DependencyHandler.Result result = manager.getDependencyHandler().introduceDependency(
                                 this, referencedRepository );
                           switch ( result )
                           {
                              case ACCEPT:
                                 addToRequiredRepositories( referencedRepository );
                                 break;
                              case ERROR:
                                 throw new RepositoryCrossReferenceException();
                              case IGNORE:
                                 addToIgnoredRepositories( referencedRepository );
                                 accepted = false;
                                 break;
                              default:
                                 throw new IllegalArgumentException( "unknown enum value: " + result );
                           }
                        }
                     }
                  }
               }
            } else
            {
               //no known id - identifier module removed (project closed)
               accepted = false;
            }
         }
      }
      return accepted;
   }

   /**
    * check if a call to {@link #undo} can rollback some changes.
    *
    * @return true if undo may be invoked
    */
   public boolean isUndoPossible()
   {
      try
      {
         if ( !isInOperationalization() || operationalizationLockHolder == Thread.currentThread() )
         {
            return getPrevious() != null;
         } else
         {
            return false;
         }
      } catch ( UnsupportedOperationException e )
      {
         getErrorHandlerModule().error( this, ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_UNDO_GET_PREVIOUS_UNSUPPORTED, e.getMessage(), e, null );
         return false;
      }
   }

   /**
    * check if a call to {@link #redo} can recommit some changes again.
    *
    * @return true if redo may be invoked
    */
   public boolean isRedoPossible()
   {
      try
      {
         if ( !isInOperationalization() || operationalizationLockHolder == Thread.currentThread() )
         {
            return getNext() != null;
         } else
         {
            return false;
         }
      } catch ( UnsupportedOperationException e )
      {
         //todo: this may happen though there is a next change
         getErrorHandlerModule().error( this, ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_REDO_GET_NEXT_UNSUPPORTED, e.getMessage(), e, null );
         return false;
      }
   }

   /**
    * roll back the last top level transaction.
    *
    * @return the entry that was undone
    */
   public TransactionEntry undo()
   {
      return undo(Integer.MAX_VALUE);
   }

   /**
    * roll back the last change or transation transaction.
    *
    * @param transactionDepth 0 to undo a single change, 1 to undo the enclosing transaction,
    *        higher values to recurse into enclosing transactions
    * @return the entry that was undone
    */
   public TransactionEntry undo(int transactionDepth)
   {
      TransactionEntry entry;
      do
      {
         entry = getPrevious();
         if ( entry != null )
         {
            if ( transactionDepth == 0 )
            {
               while ( entry instanceof Transaction )
               {
                  setNext( entry );
                  entry = getPrevious();
               }
            } else
            while ( transactionDepth > 0 && entry.getEnclosingTransaction() != null
                  && entry.getEnclosingTransaction().getStatus().equals( Transaction.Status.COMMITTED ) )
            {
               entry = entry.getEnclosingTransaction();
               transactionDepth--;
            }
            entry.rollback();
            setNext( entry );
         }
         //while only management changes have been redone go on
      } while ( entry instanceof Change && ( (Change) entry ).getKind().equals( Change.Kind.MANAGE ) );
      return entry;
   }

   /**
    * commit the formerly rolled back transaction again.
    *
    * @return the entry that was redone
    */
   public TransactionEntry redo()
   {
      TransactionEntry entry;
      do
      {
         entry = redoOnce();
         //while only management changes have been redone go on
      } while ( entry != null && entry.isManagementEntry() );
      return entry;
   }


   /**
    * commit the formerly rolled back change or transation transaction again.
    *
    * @param transactionDepth 0 to undo a single change, 1 to undo the enclosing transaction,
    *        higher values to recurse into enclosing transactions
    * @return the entry that was redone
    */
   public TransactionEntry redo( int transactionDepth )
   {
      TransactionEntry entry;
      do
      {
         entry = redoOnce( transactionDepth );
         //while only management changes have been redone go on
      } while ( entry != null && entry.isManagementEntry() );
      return entry;
   }

   private TransactionEntry redoOnce()
   {
      int transactionDepth = Integer.MAX_VALUE;
      return redoOnce(transactionDepth);
   }

   private TransactionEntry redoOnce(int transactionDepth)
   {
      TransactionEntry entry = getNext();
      if ( entry != null )
      {
         if ( transactionDepth == 0 )
         {
            while ( entry instanceof Transaction )
            {
               setPrevious( entry );
               entry = getNext();
            }
         } else
         while ( transactionDepth > 0 && entry.getEnclosingTransaction() != null
               && entry.getEnclosingTransaction().getStatus().equals( Transaction.Status.ROLLED_BACK ) )
         {
            entry = entry.getEnclosingTransaction();
            transactionDepth--;
         }
         redoSingle( entry );
      }
      //TODO: on HandledErrorException etc. the partial redo should be rolled back!
      return entry;
   }

   private void redoSingle( TransactionEntry entry )
   {
      final TransactionEntry lastEntry = entry.recommit();
      if ( lastEntry != null )
      {
         setPrevious( lastEntry );
      } else
      {
         throw new NullPointerException("TransactionEntry.commit() must not return null!");
      }
   }

   /**
    * receive and apply changes from the parent repository.
    * @return list of changes in the update
    * @throws RemoteException if an exception occured remotely or while communicating
    */
   @SuppressWarnings( "unchecked" )
   public List<Change> update() throws RemoteException
   {
      return getServerModule().update();
   }

   /**
    * send changes made in this repository to the parent.
    * @throws RemoteException if an exception occured remotely or while communicating
    */
   public void checkin() throws RemoteException
   {
      getServerModule().checkin();
   }

   /**
    * @return current value of the field changeFactory
    */
   public ChangeFactory getChangeFactory()
   {
      return this.changeFactory;
   }

   /**
    * @return current value of the field transactionModule.
    */
   public TransactionModule getTransactionModule()
   {
      return this.transactionModule;
   }

   /**
    * @return current value of the field persistencyModule
    */
   public PersistencyModule getPersistencyModule()
   {
      return this.persistencyModule;
   }

   /**
    * @return current value of the field reflectionModule
    */
   public FeatureAccessModule getFeatureAccessModule()
   {
      if ( featureAccessModule == null )
      {
         featureAccessModule = new FeatureAccessModule();
      }
      return this.featureAccessModule;
   }

   /**
    * @return current value of the field identifierModule
    */
   public IdentifierModule getIdentifierModule()
   {
      return this.identifierModule;
   }

   /**
    * getter for field activeTransaction
    *
    * @return current value of field activeTransaction
    */
   public Transaction getActiveTransaction()
   {
      Transaction activeTransaction = this.activeTransaction;
      while ( inOperationalization )
      {
         synchronized ( this )
         {
            if ( operationalizationLockHolder == Thread.currentThread() )
            {
               return activeTransaction;
            }
            try
            {
               this.wait( OPERATIONALIZATION_TIMEOUT );
               activeTransaction = this.activeTransaction;
            } catch ( InterruptedException e )
            {
               throw new RuntimeException( e );
            }
         }
      }
      return activeTransaction;
   }

   /**
    * Start a new transaction.
    *
    * @param transactionName name of the new transaction
    * @return the new transaction object
    */
   public Transaction startTransaction( String transactionName )
   {
      if ( !isInOperationalization() )
      {
         Transaction activeTransaction = getActiveTransaction();
         activeTransaction = dropClosedTransactions( activeTransaction );
         Transaction transaction = getTransactionModule().start( transactionName );
         transaction = getPersistencyModule().send( transaction, activeTransaction );
         setActiveTransaction( transaction );
         return transaction;
      } else
      {
         throw new IllegalStateException( "start transaction not allowed while in operationalization " +
               "(commit or rollback)" );
      }
   }

   /**
    * Aquires an opearationalization lock for recommit and rollback for the current thread.
    * If and only if this method returns true the lock has to be released by a call to
    * {@link #releaseOperationalizationLock()}. Do not call this method if you are not
    * sure you have to do this!
    *
    * @return true if the lock was not aquired and is aquired now, false if the lock
    *         was already aquired by this thread
    * @throws IllegalStateException if the lock was already aquired by another thread
    */
   public boolean aquireOperationalizationLock()
   {
      synchronized ( this )
      {
         if ( operationalizationLockHolder == null )
         {
            operationalizationLockHolder = Thread.currentThread();
            inOperationalization = true;
            this.notifyAll();
            return true;
         } else if ( operationalizationLockHolder != Thread.currentThread() )
         {
            throw new IllegalStateException( "Lock already held by " + operationalizationLockHolder
                  + ", though requested by " + Thread.currentThread() );
         } else
         {
            return false;
         }
      }
   }

   /**
    * Releases the opearationalization lock that was previously aquired by the current thread
    * via {@link #aquireOperationalizationLock()}. Do not call this method if you are not
    * sure you have to do this!
    *
    * @throws IllegalStateException if the lock was aquired by another thread
    */
   public synchronized void releaseOperationalizationLock()
   {
      if ( operationalizationLockHolder == Thread.currentThread() )
      {
         inOperationalization = false;
         operationalizationLockHolder = null;
         this.notifyAll();
      } else if ( operationalizationLockHolder != null )
      {
         throw new IllegalStateException( "Lock held by " + operationalizationLockHolder
               + ", though release requested by " + Thread.currentThread() );
      }
   }


   /**
    * Query if repository has redone/acknowledged any changes.
    *
    * @return true if some changes were redone/acknowledged
    */
   public boolean isUntouched()
   {
      return this.untouched;
   }

   /**
    * store the value for field untouched
    */
   private boolean untouched = true;

   /**
    * Operationalization: redo (recommit) a single change.
    * May only be called by {@link Change#recommit()}
    *
    * @param change what should be redone
    */
   void redo( Change change )
   {
      untouched = false;

      if ( !Change.Kind.MANAGE.equals( change.getKind() ) )
      {
         notifyListeners( change, RepositoryListener.EventType.REDO );
      }

      final boolean aquiredLock = aquireOperationalizationLock();
      operationalizedChange = change;
      try
      {
         setPrevious( change );
         switch ( change.getKind() )
         {
            case ALTER_FIELD:
            {
               try
               {
                  FieldHandler field = change.getField();
                  if ( field != null )
                  {
                     field.alter( change.getAffectedObject(), change.getKey(), change.getOldValue(),
                           change.getNewValue() );
                  } else
                  {
                     getErrorHandlerModule().error( this, ErrorHandlerModule.Level.ERROR,
                           ErrorHandlerModule.ERROR_REDO_CHANGE_FAILED_NO_FIELDHANDLER,
                           "failed to redo change: no fieldhandler found!", null, change );
                  }
               } catch ( UnsupportedOperationException e )
               {
                  getErrorHandlerModule().error(this, ErrorHandlerModule.Level.ERROR,
                        ErrorHandlerModule.ERROR_REDO_CHANGE_FAILED_UNSUPPORTED,
                        "failed to redo change: altering field is unsupported!", e, change );
               } catch ( InvocationException e )
               {
                  getErrorHandlerModule().error( this, ErrorHandlerModule.Level.ERROR,
                        ErrorHandlerModule.ERROR_REDO_CHANGE_FAILED_INVOCATION,
                        "failed to redo change: invoke access method failed!", e.getCause(), change );
               } catch ( Exception e )
               {
                  getErrorHandlerModule().error( this, ErrorHandlerModule.Level.ERROR,
                        ErrorHandlerModule.ERROR_REDO_CHANGE_FAILED,
                        "failed to redo change", e, change );
               }
            }
            break;
            case REMOVE_KEY:
               throw new UnsupportedOperationException( "not yet implemented" );
            case MANAGE:
            {
               ManagementDataHandler handler = getManagementHandler( change.getKey() );
               if ( handler != null )
               {
                  handler.handleRedo( change );
               } else
               {
                  getErrorHandlerModule().error( this, ErrorHandlerModule.Level.WARNING,
                        ErrorHandlerModule.ERROR_MANAGEMENT_UNHANDLED, "unhandled management change" , null, change );
               }
            }
            break;
            case CREATE_OBJECT:
            {
               createObject( change );
            }
            break;
            case DESTROY_OBJECT:
               deleteAffectedObjectIfCreated( change );
               break;
            default:
            {
               throw new UnsupportedOperationException( "no redo handler for change kind " + change.getKind() );
            }
         }
      } finally
      {
         operationalizedChange = null;
         if ( aquiredLock )
         {
            releaseOperationalizationLock();
         }
      }
   }

   private Object createObject( Change change )
   {
      ID id = change.getAffectedObjectID();
      ClassHandler classHandler = id.getClassHandler();
      Object alreadyCreatedObject = getIdentifierModule().getObject( id );
      if ( alreadyCreatedObject == null )
      {
         Object object = createObject( classHandler, change.getKey() );
         getIdentifierModule().registerID( id, object );
         if ( createdObjects != null )
         {
            createdObjects.add( object );
         }
         if ( isAutoRegisteringRestoredObjects() )
         {
            getDefaultChangeRecorder().registerExistingObject( object );
         }
         return object;
      } else
      {
         //todo: handle object already created
         return alreadyCreatedObject;
      }
   }

   private void deleteAffectedObjectIfCreated( Change change )
   {
      boolean autoResolving = change.isAutoResolving();
      change.setAutoResolving( false );
      Object affectedObject = change.getAffectedObject();
      if ( affectedObject instanceof ID )
      {
         affectedObject = getIdentifierModule().getObject( (ID) affectedObject );
      }
      if ( affectedObject != null )
      {
         try
         {
            getFeatureAccessModule().getClassHandler( affectedObject ).deleteInstance( affectedObject );
         } catch ( ClassNotFoundException e )
         {
            getErrorHandlerModule().error( this, ErrorHandlerModule.Level.ERROR,
                  ErrorHandlerModule.ERROR_REDO_CHANGE_FAILED_DELETE_OBJECT,
                  "failed to redo change: classhandler not found to delete object!", e, change );
         }
      }
      change.setAutoResolving( autoResolving );
      getIdentifierModule().markObjectAsAlreadyDeleted( change.getAffectedObjectID() );
   }

   /**
    * Create a new instance of an object for redo {@link Change.Kind#CREATE_OBJECT}.
    *
    * @param classHandler type of object to be created
    * @param key          key for recreating/finding the object
    * @return the newly created/found object (not null!)
    * @throws PersistencyException if creation/search fails
    */
   protected Object createObject( ClassHandler classHandler, Object key )
         throws PersistencyException
   {
      try
      {
         Object object;
         try
         {
            object = classHandler.getConstructor( Repository.class.getName() ).newInstance( this );
         } catch ( NoSuchMethodException e )
         {
            object = classHandler.newInstance();
         }
         return object;
      } catch ( NoSuchMethodException e )
      {
         throw new PersistencyException( "no suitable constructor in class " + classHandler.getName() + " found!", e );
      } catch ( IllegalAccessException e )
      {
         throw new PersistencyException( e );
      } catch ( InstantiationException e )
      {
         throw new PersistencyException( e );
      }
   }

   /**
    * set of objects that were created in redo/restore.
    */
   @SuppressWarnings( { "MismatchedQueryAndUpdateOfCollection" } )
   // used to prevent garbage collection
   private Collection<Object> createdObjects = new ArrayList<Object>();

   /**
    * Remove all objects from the list of created objects to allow garbage collection. No objects will be added to the
    * list afterwards.
    */
   public void enableGarbageCollection()
   {
      createdObjects = null;
   }

   /**
    * Operationalization: undo (rollback) a single change.
    * May only be called by {@link Change#rollback()}.
    *
    * @param change what should be undone
    */
   void undo( Change change )
   {
      if ( !Change.Kind.MANAGE.equals( change.getKind() ) )
      {
         notifyListeners( change, RepositoryListener.EventType.UNDO );
      }

      switch ( change.getKind() )
      {
         case ALTER_FIELD:
         {

            final boolean aquiredLock = aquireOperationalizationLock();
            operationalizedChange = change;
            try
            {
               setNext(change);
               if (change.getField() != null)
               {
                  change.getField().alter(change.getAffectedObject(), change.getKey(), change.getNewValue(),
                        change.getOldValue());
               } else
               {
                  getErrorHandlerModule().error(this, ErrorHandlerModule.Level.ERROR,
                        ErrorHandlerModule.ERROR_UNDO_CHANGE_FAILED_NO_FIELDHANDLER,
                        "failed to undo change: no fieldhandler found!", null, change);
               }
            }
            catch (UnsupportedOperationException e)
            {
               getErrorHandlerModule().error(this, ErrorHandlerModule.Level.ERROR,
                     ErrorHandlerModule.ERROR_UNDO_CHANGE_FAILED_UNSUPPORTED,
                     "failed to undo change: altering field is unsupported!", e, change );
            } catch (InvocationException e)
            {
               getErrorHandlerModule().error(this, ErrorHandlerModule.Level.ERROR,
                     ErrorHandlerModule.ERROR_UNDO_CHANGE_FAILED_INVOCATION,
                     "failed to undo change: invoke access method failed!", e.getCause(), change);
            } catch (Exception e)
            {
               getErrorHandlerModule().error(this, ErrorHandlerModule.Level.ERROR,
                     ErrorHandlerModule.ERROR_UNDO_CHANGE_FAILED,
                     "failed to undo change", e, change);
            }
            finally
            {
               operationalizedChange = null;
               if (aquiredLock)
               {
                  releaseOperationalizationLock();
               }
            }
            break;
         }
         case REMOVE_KEY:
            throw new UnsupportedOperationException( "not yet implemented" );
         case CREATE_OBJECT:
            deleteAffectedObjectIfCreated( change );
            break;
         case DESTROY_OBJECT:
            Object object = createObject( change );
            // we need to retrieve all changes to that object and reapply them
            AffectedObjectFilter filter = new AffectedObjectFilter( getIdentifierModule().getID( object ) );
            Change entry = (Change) getPersistencyModule().receiveNext( null, filter );
            if ( entry.getKind().equals( Change.Kind.CREATE_OBJECT ) )
            {
               // fine - skip it
               entry = (Change) getPersistencyModule().receiveNext( entry, filter);
            } else
            {
               throw new IllegalStateException( "First change to an object must be its creation! Found: " + change );
            }
            while ( entry != null )
            {
               if ( !entry.isRolledback() )
               {
                  if ( !entry.getKind().equals( Change.Kind.DESTROY_OBJECT ) )
                  {
                     entry.recommit();
                  } else
                  {
                     // ok we're finished
                     break;
                  }
               }
               entry = (Change) getPersistencyModule().receiveNext( entry, filter );
               if ( entry == null )
               {
                  getErrorHandlerModule().error( this, ErrorHandlerModule.Level.WARNING,
                        ErrorHandlerModule.ERROR_UNDO_CHANGE_WARNING_DELETE_OBJECT,
                        "reached end of persistency module before finding 'destroy object' " +
                              "change which is undone!", null, change );
               }
            }
            break;
         case MANAGE:
            {
               ManagementDataHandler handler = getManagementHandler( change.getKey() );
               if ( handler != null )
               {
                  handler.handleUndo( change );
               } else
               {
                  getErrorHandlerModule().error( this, ErrorHandlerModule.Level.WARNING,
                        ErrorHandlerModule.ERROR_MANAGEMENT_UNHANDLED, "unhandled management change" , null, change );
               }
               break;
            }
         default:
         {
            throw new UnsupportedOperationException( "no undo handler for change kind " + change.getKind() );
         }
      }
   }

   /**
    * redo all changes in the repository.
    * @return a map of all named objects
    */
   public Map<String, Object> restore()
   {
      return restore( false );
   }

   /**
    * For internal use - call restore() instead. Redo changes in the repository.
    *
    * @param restoreManagementOnly if true only changes with kind == MANAGE are redone and transactions are loaded
    * @return a map of all named objects
    */
   public Map<String, Object> restore( boolean restoreManagementOnly )
   {
      final PersistencyModule persistencyModule = getPersistencyModule();
      if( restoreManagementOnly )
      {
         // adapt the persistence module: filter with a ManagementOnlyFilter
         final FilterPersistencyModule filterPersistencyModule = new FilterPersistencyModule( persistencyModule );
         filterPersistencyModule.setFilter( new ManagementOnlyFilter() );
         this.setPersistencyModule( filterPersistencyModule );
      }

      try
      {
         // keep hard references to all objects while loading
         // noinspection MismatchedQueryAndUpdateOfCollection
         Set<Object> objects = new HashSet<Object>();
         final boolean aquired = aquireOperationalizationLock();
         try
         {
            Change next;
            boolean inRemoteSectionOfSCMConflict = false;
            while( ( next = restoreNextChange() ) != null )
            {
               if( !next.isRolledback() )
               {
                  if( next instanceof ConflictMarker )
                  {
                     inRemoteSectionOfSCMConflict = AbstractStreamPersistencyModule.MANAGEMENT_KEY_CONFLICT_MARKER_REMOTE
                           .equals( next.getKey() );
                  }
                  if( !inRemoteSectionOfSCMConflict )
                  {
                     if( restoreManagementOnly )
                     {
                        next.setAutoResolving( false );
                        if( next.isManagementEntry() )
                        {
                           redoSingle( next );
                        } else
                        {
                           String message = "Entries other than ManagemenChanges are not allowed when only restoring management changes.";
                           throw new IllegalStateException( message );
                        }
                        objects.add( next.getAffectedObjectID() );
                     } else
                     {
                        boolean isDestroy = Change.Kind.DESTROY_OBJECT.equals( next.getKind() );
                        if( isDestroy )
                        {
                           Object object = getIdentifierModule().getObject( next.getAffectedObjectID() );
                           if( object != null )
                           {
                              objects.remove( object );
                           }
                        }
                        redoOnce();
                        if( !isDestroy )
                        {
                           objects.add( next.getAffectedObject() );
                        }
                     }
                  } else
                  {
                     setPrevious( next );
                  }
               } else
               {
                  do
                  {
                     TransactionEntry entry = getPersistencyModule().receiveNext( next );
                     while( entry instanceof Transaction )
                     {
                        entry = getPersistencyModule().receiveNext( entry );
                     }
                     next = (Change) entry;
                  }
                  while( next != null && next.isRolledback() );
                  if( next == null )
                  {
                     break;
                  } else
                  {
                     setNext( next );
                     if( !restoreManagementOnly ) redoOnce();
                  }
               }
            }
         } finally
         {
            if( aquired )
            {
               releaseOperationalizationLock();
            }
         }
      } finally
      {
         if( restoreManagementOnly )
         {
            // reset to the previously used persistence module
            this.setPersistencyModule( persistencyModule );
         }
      }

      Map<String, Object> namedObjects = new HashMap<String, Object>();
      for ( Iterator<String> it = getIdentifierModule().iteratorOfNames(); it.hasNext(); )
      {
         String name = it.next();
         namedObjects.put( name, getIdentifierModule().getNamedObject( name ) );
      }

      return namedObjects;
   }

   /**
    * @return true if this repository is currently in an undo or redo operation
    */
   public boolean isInOperationalization()
   {
      return inOperationalization; //TODO: use getActiveTransaction for 'better' thread safety?
   }

   /**
    * Query which change is currently operationalized (redone or undone).
    *
    * @return null if no redo or undo is in progress, redone/undone change else
    */
   public Change getOperationalizedChange()
   {
      return operationalizedChange;
   }

   private ChangeRecorder defaultChangeRecorder;

   /**
    * Default property change recorder. This is only required if automatic registering of restored objects is enabled.
    * If not enabled this recorder is just for convenience.
    *
    * @see #setAutoRegisteringRestoredObjects(boolean)
    * @return the default property change recorder used for generating change logs from property changes if only the
    *         correct repository is known.
    */
   public ChangeRecorder getDefaultChangeRecorder()
   {
      if ( defaultChangeRecorder == null )
      {
         defaultChangeRecorder = new PropertyChangeRecorder( this );
      }
      return defaultChangeRecorder;
   }

   /**
    * @param defaultChangeRecorder new default property change recorder
    * @see #getDefaultChangeRecorder()
    */
   public void setDefaultChangeRecorder( ChangeRecorder defaultChangeRecorder)
   {
      this.defaultChangeRecorder = defaultChangeRecorder;
   }

   /**
    * delete the repository (including all modules that can be deleted) from memory
    */
   public void delete()
   {
      getIdentifierModule().delete();
      getPersistencyModule().delete();
      getTransactionModule().delete();
   }

   /**
    * Clear the list of created objects, run {@link System#gc()} and generate
    * {@link de.uni_kassel.coobra.Change.Kind#DESTROY_OBJECT}-changes
    * for all collected objects in the repository.
    *
    * @see de.uni_kassel.coobra.identifiers.IdentifierModule#gc()
    * @see System#gc()
    */
   public void gc()
   {
      enableGarbageCollection();
      System.gc();
      Thread.yield();
      getIdentifierModule().gc();
   }

   /**
    * set both, previous and next (none will be recalculated).
    *
    * @param previous new value for previous
    * @param next     new value for next
    */
   private void setPreviousAndNext( Change previous, Change next )
   {
      this.previous = previous;
      this.next = next;
      checkNextPrevious = false;
   }

   /**
    * @return entry that would be redone as next
    */
   private TransactionEntry getNext()
   {
      if ( checkNextPrevious && next == null )
      {
         if ( previous != null )
         {
            next = getPersistencyModule().receiveNext( previous );
         } else
         {
            next = getPersistencyModule().receiveFirst();
         }
         checkNextPrevious = false;
      }
      return next;
   }

   /**
    * @return change that would be redone as next change
    */
   private Change restoreNextChange()
   {
      TransactionEntry entry = getNext();
      while ( entry instanceof Transaction )
      {
         notifyListeners( entry, RepositoryListener.EventType.RESTORED_TRANSACTION );
         entry = getPersistencyModule().receiveNext( entry );
      }
      next = entry;
      return (Change) entry;
   }

   /**
    * @param next change that would be redone as next change
    */
   private void setNext( TransactionEntry next )
   {
      this.next = next;
      this.previous = null;
      checkNextPrevious = true;
   }

   /**
    * @return change that would be undone as next change
    */
   private TransactionEntry getPrevious()
   {
      if ( checkNextPrevious && previous == null && next != null )
      {
         previous = getPersistencyModule().receivePrevious( next );
//         while ( entry instanceof Transaction )
//         {
//            entry = getPersistencyModule().receivePrevious( entry );
//         }
//         previous = ( Change ) entry;
         checkNextPrevious = false;
      }
      return previous;
   }

   /**
    * @param previous change that would be undone as next change
    */
   private void setPrevious( TransactionEntry previous )
   {
      this.previous = previous;
      this.next = null;
      checkNextPrevious = true;
   }

   /**
    * @param value new value for field changeFactory
    * @return true if changeFactory was changed
    */
   public boolean setChangeFactory( ChangeFactory value )
   {
      final ChangeFactory oldValue = this.changeFactory;
      boolean changed = false;
      if ( oldValue != value )
      {
         if ( oldValue != null )
         {
            this.changeFactory = null;
            oldValue.setRepository( null );
         }
         this.changeFactory = value;
         if ( value != null )
         {
            value.setRepository( this );
         }
         firePropertyChange( "changeFactory", oldValue, value );
         changed = true;
      }
      return changed;
   }

   /**
    * @param value new value for field transactionModule
    * @return true if transactionModule was changed
    */
   public boolean setTransactionModule( TransactionModule value )
   {
      boolean changed = false;
      final TransactionModule oldValue = this.transactionModule;
      if ( oldValue != value )
      {
         if ( this.transactionModule != null )
         {
            this.transactionModule = null;
            oldValue.setRepository( null );
         }
         this.transactionModule = value;
         if ( value != null )
         {
            value.setRepository( this );
         }
         firePropertyChange( "persistencyModule", oldValue, value );
         changed = true;
      }
      return changed;
   }

   /**
    * @param value new value for field persistencyModule
    * @return true if persistencyModule was changed
    */
   public boolean setPersistencyModule( PersistencyModule value )
   {
      boolean changed = false;
      final PersistencyModule oldValue = this.persistencyModule;
      if ( oldValue != value )
      {
         if ( oldValue != null )
         {
            this.persistencyModule = null;
            oldValue.setRepository( null );
         }
         this.persistencyModule = value;
         if ( value != null )
         {
            value.setRepository( this );
         }
         firePropertyChange( "persistencyModule", oldValue, value );
         changed = true;
      }
      return changed;
   }

   /**
    * @param value new value for field identifierModule
    * @return true if identifierModule was changed
    * @throws RepositorySetupException if repository already received changes
    */
   public boolean setIdentifierModule( final IdentifierModule value ) throws RepositorySetupException
   {
      final IdentifierModule oldValue = this.identifierModule;
//      if ( value == null && oldValue != null )
//      {
//         throw new IllegalArgumentException( "IdentifierModule cannot be null!" );
//      }
      boolean changed = false;
      if ( oldValue != value )
      {
         if ( !isUntouched() && value != null )
         {
            throw new RepositorySetupException( "Identifier module must be set up " +
                  "right after creating the repository!" );
         }
         //don't set repository -> allow one IDModule for two repositories
//         if ( oldValue != null )
//         {
//            this.identifierModule = null;
//            oldValue.setRepository( null );
//         }
         this.identifierModule = value;
         firePropertyChange( "identifierModule", oldValue, value );
         //don't set repository -> allow one IDModule for two repositories
         //value.setRepository( this );
         changed = true;
      }
      return changed;
   }

   /**
    * @param value new value for field reflectionModule
    * @return true if reflectionModule was changed
    */
   public boolean setFeatureAccessModule( FeatureAccessModule value )
   {
      boolean changed = false;
      final FeatureAccessModule oldValue = this.featureAccessModule;
      if ( oldValue != value )
      {
         this.featureAccessModule = value;
         firePropertyChange( "fieldAccessModule", oldValue, value );
         changed = true;
      }
      return changed;
   }

   /**
    * Query the handler for a specific key.
    *
    * @param key key of interest
    * @return the handler for management changes with the specified key
    */
   public ManagementDataHandler getManagementHandler( Object key )
   {
      return managementHandlers != null && key != null ? managementHandlers.get( key ) : null;
   }

   /**
    * Subscribe a handler for a spcific key. A handler will receive all management changes with the specified key.
    *
    * @param key which key to subscribe for.
    * @param handler new handler
    * @return the old handler for this key
    * @see de.uni_kassel.coobra.Repository.ManagementDataHandler
    */
   public ManagementDataHandler putManagementDataHandler( Object key, ManagementDataHandler handler )
   {
      if ( managementHandlers == null && handler != null )
      {
         managementHandlers = new ConcurrentHashMap<Object, ManagementDataHandler>();
      }
      if ( managementHandlers != null )
      {
         if ( handler != null )
         {
            ManagementDataHandler oldHandler = managementHandlers.put( key, handler );
            return oldHandler;
         } else
         {
            ManagementDataHandler oldHandler = managementHandlers.remove( key );
            return oldHandler;
         }
      } else
      {
         return null;
      }
   }

   /**
    * setter for field activeTransaction
    *
    * @param value new value
    * @return true if active transaction was changed
    */
   boolean setActiveTransaction( Transaction value )
   {
      boolean changed = false;
//      if ( value != null && !value.getStatus().equals( Transaction.Status.STARTED ) )
//      {
//         value = null;
//      }
      if ( this.activeTransaction != value )
      {
         Transaction oldValue = this.activeTransaction;
         if ( value != null )
         {
            value.addPropertyChangeListener( transactionListener );
         }
         this.activeTransaction = value;
         firePropertyChange( "activeTransaction", oldValue, value );
         changed = true;
      }
      return changed;
   }

   /**
    * @see #setAutoRegisteringRestoredObjects(boolean)
    */
   private boolean autoRegisteringRestoredObjects = false;

   /**
    * If enabled the repository registers each object that is created in redo/restore with the default
    * property change recorder.
    *
    * @param flag true to enable auto registration
    * @see #getDefaultChangeRecorder()
    */
   public void setAutoRegisteringRestoredObjects( boolean flag )
   {
      autoRegisteringRestoredObjects = flag;
   }

   /**
    * @return true if enabled
    * @see #setAutoRegisteringRestoredObjects(boolean)
    */
   public boolean isAutoRegisteringRestoredObjects()
   {
      return autoRegisteringRestoredObjects;
   }

   /**
    * Interface for handler of changes that have the kind {@link de.uni_kassel.coobra.Change.Kind#MANAGE}.
    * These handler are subscribed for a specific key via the
    * {@link Repository#putManagementDataHandler(Object, de.uni_kassel.coobra.Repository.ManagementDataHandler)}
    * method.
    */
   public static abstract class ManagementDataHandler
   {
      /**
       * Receives changes that should be handled (redone).
       *
       * @param change management change
       */
      public abstract void handleRedo( Change change );

      /**
       * Receives changes that were read.
       *
       * @param change management change
       */
      public void handleRead( Change change )
      {
         
      }

      /**
       * Receives changes that should not be handled any more (undone).
       *
       * @param change management change
       */
      public void handleUndo( Change change )
      {

      }
   }

   /**
    * store value for field serverModule
    */
   private ServerModule<?, ?> serverModule;

   /**
    * @return current value of the field serverModule
    */
   public ServerModule getServerModule()
   {
      return this.serverModule;
   }

   /**
    * @param value new value for field serverModule
    * @return true if serverModule was changed
    */
   public boolean setServerModule( final ServerModule<?, ?> value )
   {
      final ServerModule oldValue = this.serverModule;
      boolean changed = false;
      if ( oldValue != value )
      {
         if ( oldValue != null )
         {
            this.serverModule = null;
            oldValue.setRepository( null );
         }
         this.serverModule = value;
         firePropertyChange( "serverModule", oldValue, value );
         if ( value != null )
         {
            value.setRepository( this );
         }
         changed = true;
      }
      return changed;
   }


   /**
    * The name of a repository is used to resolve repository dependencies.
    *
    * @return current name for this repository
    */
   public String getName()
   {
      return this.name;
   }

   /**
    * store the value for field name.
    */
   private String name;

   /**
    * The name of a repository is used to resolve repository dependencies. It should be chosen wisely by an
    * application to facilitate correct repository linking (should be kind of unique). The name can be set only
    * once.
    *
    * @param value new name for the repository
    */
   public void setName( final String value )
   {
      changeName( value );
      acknowledgeChange( getChangeFactory().changeManagement( null, MANAGEMENT_KEY_REPOSITORY_NAME, null,
            getName(), false ) );
   }


   /**
    * getter for field description
    *
    * @return current value of field description
    */
   public String getDescription()
   {
      return this.description;
   }

   /**
    * store the value for field description
    */
   private String description;

   /**
    * setter for field description
    *
    * @param value new value
    */
   public void setDescription( final String value )
   {
      final String oldValue = this.description;
      if ( ( oldValue == null && value != null ) || ( oldValue != null && !oldValue.equals( value ) ) )
      {
         this.description = value;
         firePropertyChange( "description", oldValue, value );
      }
   }

   private void changeName( final String value )
   {
      final String oldValue = this.name;
      if ( ( oldValue == null && value != null ) )
      {
         this.name = value;
         firePropertyChange( "name", oldValue, value );
      } else if ( oldValue != null && !oldValue.equals( value ) )
      {
         throw new IllegalStateException( "Repository name can be set only once!" );
      }
   }

   /**
    * To restore name.
    */
   private ManagementDataHandler nameHandler = new ManagementDataHandler()
   {
      @Override
      public void handleRedo( Change change )
      {
         changeName( (String) change.getNewValue() );
      }
   };

   /**
    * To restore required repositories.
    */
   private ManagementDataHandler requiredHandler = new ManagementDataHandler()
   {
      @Override
      public void handleRedo( Change change )
      {
         if ( change.getNewValue() != null )
         {
            findRequiredRepository( (String) change.getNewValue(), change.getOldValue() );
         }
      }
      
      @Override
      public void handleRead( Change change )
      {
         if ( change.getNewValue() != null )
         {
            findRequiredRepository( (String) change.getNewValue(), change.getOldValue() );
         }
      }
   };

   /**
    * Find a required repository by name and add it to the list of required repositories. Used while restoring.
    * It searches through all repositories known to the IDManager for this repository.
    *
    * @param repositoryName name of the required repository
    * @param description repository description (used in the case of a failure) 
    * @throws RequiredRepositoryMissingException
    *          if the repository was not found
    */
   protected void findRequiredRepository( String repositoryName, Object description )
         throws RequiredRepositoryMissingException
   {
      for ( Iterator<IdentifierModule> it = getIdentifierModule().getManager().iteratorOfIdentifierModule();
            it.hasNext();)
      {
         IdentifierModule module = it.next();
         if ( module != null )
         {
            final Repository repository = module.getRepository();
            if ( repositoryName.equals( repository.getName() ) )
            {
               addToRequiredRepositories( repository );
               return;
            }
         }
      }
      if ( description == null )
      {
         description = " named '" + repositoryName + "'";
      } else
      {
         description = "'" + description + "'";
      }
      throw new RequiredRepositoryMissingException( "The required repository " + description + " was not found!",
            repositoryName );
   }

   /**
    * required repositories.
    */
   private Set<Repository> requiredRepositories;

   /**
    * Add a repository to the set of required repositories.
    *
    * @param value required repository, must have same IDManager as this repository
    * @return false if the value was already contained in the set
    * @see Repository#addToDependentRepositories
    */
   public boolean addToRequiredRepositories( Repository value )
   {
      boolean changed = false;
      if ( value != null )
      {
         if ( requiredRepositories == null || !requiredRepositories.contains(value) )
         {
            if ( value.getIdentifierModule().getManager() != getIdentifierModule().getManager() )
            {
               throw new IllegalArgumentException( "Required repository must have the same IDManager" );
            }
            if ( this.requiredRepositories == null )
            {
               this.requiredRepositories = new ConcurrentHashSet<Repository>();
            }
            changed = this.requiredRepositories.add( value );
            firePropertyChange( "requiredRepositories", null, value );
            value.checkName();
            acknowledgeChange( getChangeFactory().changeManagement( null, MANAGEMENT_KEY_REQUIRED_REPOSITORY,
                  value.getDescription(), value.getName(), false ) );
            if ( changed )
            {
               value.addToDependentRepositories( this );
            }
         }
      }
      return changed;
   }

   private void checkName()
   {
      if ( getName() == null )
      {
         getErrorHandlerModule().error( this, ErrorHandlerModule.Level.WARNING,
               ErrorHandlerModule.ERROR_REPOSITORY_NO_NAME, "Repository does not have a name!" , null, null );
      } else
      {
         boolean foundThis = false;
         for ( Iterator<IdentifierModule> it = getIdentifierModule().getManager().iteratorOfIdentifierModule();
               it.hasNext();)
         {
            IdentifierModule module = it.next();
            if ( module != null )
            {
               final Repository repository = module.getRepository();
               if ( getName().equals( repository.getName() ) )
               {
                  if ( repository == this )
                  {
                     foundThis = true;
                  } else
                  {
                     getErrorHandlerModule().error( this, ErrorHandlerModule.Level.WARNING,
                           ErrorHandlerModule.ERROR_REPOSITORY_NAME_DUPLICATE,
                           "Repository nane '" + getName() + "' is used more than once!",
                           null, null );
                  }
               }
            }
         }
         if ( !foundThis )
         {
            getErrorHandlerModule().error( this, ErrorHandlerModule.Level.WARNING,
                  ErrorHandlerModule.ERROR_REPOSITORY_NOT_REGISTERED,
                  "Repository '" + getName() + "' was not registered with the IDManager!",
                  null, null );
         }
      }
   }

   /**
    * @param value value of interest
    * @return true if the second repository is in the set of required repositories
    */
   public boolean hasInRequiredRepositories( Repository value )
   {
      return ( ( this.requiredRepositories != null ) &&
            ( value != null ) &&
            this.requiredRepositories.contains( value ) );
   }

   /**
    * @return iterator through set of required repositories.
    */
   public Iterator<Repository> iteratorOfRequiredRepositories()
   {
      if ( this.requiredRepositories == null )
      {
         return EmptyIterator.get();
      } else
      {
         return this.requiredRepositories.iterator();
      }
   }

   /**
    * clear set of required repositories.
    */
   protected void removeAllFromRequiredRepositories()
   {
      Iterator<Repository> iter = this.iteratorOfRequiredRepositories();
      while ( iter.hasNext() )
      {
         Repository tmpValue = iter.next();
         this.removeFromRequiredRepositories( tmpValue );
      }
   }

   /**
    * Remove value from set of required repositories.
    *
    * @param value what to remove
    * @return false if value was not in set
    */
   protected boolean removeFromRequiredRepositories( Repository value )
   {
      boolean changed = false;
      if ( ( this.requiredRepositories != null ) && ( value != null ) )
      {
         changed = this.requiredRepositories.remove( value );
         firePropertyChange( "requiredRepositories", value, null );
         if ( changed )
         {
            //TODO: 'required repository' management change: remove/flag/revoke
            value.removeFromDependentRepositories( this );
         }
      }
      return changed;
   }

   /**
    * @return number of required repositories
    */
   public int sizeOfRequiredRepositories()
   {
      return ( ( this.requiredRepositories == null )
            ? 0
            : this.requiredRepositories.size() );
   }

   /**
    * dependent repositories.
    */
   private Set<Repository> dependentRepositories;

   /**
    * Add a repository to the set of dependent repositories.
    *
    * @param value new value
    * @return false if the value was already contained in the set
    * @see Repository#addToRequiredRepositories
    */
   public boolean addToDependentRepositories( Repository value )
   {
      boolean changed = false;
      if ( value != null )
      {
         if ( this.dependentRepositories == null )
         {
            this.dependentRepositories = new ConcurrentHashSet<Repository>();
         }
         changed = this.dependentRepositories.add( value );
         firePropertyChange( "dependentRepositories", null, value );
         if ( changed )
         {
            value.addToRequiredRepositories( this );
         }
      }
      return changed;
   }

   /**
    * @param value value of interest
    * @return true if the second repository is in the set of dependent repositories
    */
   public boolean hasInDependentRepositories( Repository value )
   {
      return ( ( this.dependentRepositories != null ) &&
            ( value != null ) &&
            this.dependentRepositories.contains( value ) );
   }

   /**
    * @return iterator through set of dependent repositories.
    */
   public Iterator<Repository> iteratorOfDependentRepositories()
   {
      if ( this.dependentRepositories == null )
      {
         return EmptyIterator.get();
      } else
      {
         return this.dependentRepositories.iterator();
      }
   }

   /**
    * clear set of dependent repositories.
    */
   protected void removeAllFromDependentRepositories()
   {
      Iterator<Repository> iter = this.iteratorOfDependentRepositories();
      while ( iter.hasNext() )
      {
         Repository tmpValue = iter.next();
         this.removeFromDependentRepositories( tmpValue );
      }
   }

   /**
    * Remove value from set of dependent repositories.
    *
    * @param value what to remove
    * @return false if value was not in set
    */
   protected boolean removeFromDependentRepositories( Repository value )
   {
      boolean changed = false;
      if ( ( this.dependentRepositories != null ) && ( value != null ) )
      {
         changed = this.dependentRepositories.remove( value );
         firePropertyChange( "dependentRepositories", value, null );
         if ( changed )
         {
            value.removeFromRequiredRepositories( this );
         }
      }
      return changed;
   }

   /**
    * @return number of dependent repositories
    */
   public int sizeOfDependentRepositories()
   {
      return ( ( this.dependentRepositories == null )
            ? 0
            : this.dependentRepositories.size() );
   }

   /**
    * ignored repositories.
    */
   private Set<Repository> ignoredRepositories;

   /**
    * Add a repository to the set of ignored repositories.
    *
    * @param value new value
    * @return false if the value was already contained in the set
    */
   public boolean addToIgnoredRepositories( Repository value )
   {
      boolean changed = false;
      if ( value != null )
      {
         if ( this.ignoredRepositories == null )
         {
            this.ignoredRepositories = new ConcurrentHashSet<Repository>();
         }
         changed = this.ignoredRepositories.add( value );
         firePropertyChange( "ignoredRepositories", null, value );
//         if (changed)
//         {
//            value.addToOtherRoleName (this);
//         }
      }
      return changed;
   }

   /**
    * @param value value of interest
    * @return true if the second project is in the set of ignored repositories
    */
   public boolean hasInIgnoredRepositories( Repository value )
   {
      return ( ( this.ignoredRepositories != null ) &&
            ( value != null ) &&
            this.ignoredRepositories.contains( value ) );
   }

   /**
    * @return iterator through set of ignored repositories.
    */
   public Iterator<Repository> iteratorOfIgnoredRepositories()
   {
      if ( this.ignoredRepositories == null )
      {
         return EmptyIterator.get();
      } else
      {
         return this.ignoredRepositories.iterator();
      }
   }

   /**
    * clear set of ignored repositories.
    */
   public void removeAllFromIgnoredRepositories()
   {
      Iterator<Repository> iter = this.iteratorOfIgnoredRepositories();
      while ( iter.hasNext() )
      {
         Repository tmpValue = iter.next();
         this.removeFromIgnoredRepositories( tmpValue );
      }
   }

   /**
    * Remove value from set of ignored repositories.
    *
    * @param value what to remove
    * @return false if value was not in set
    */
   public boolean removeFromIgnoredRepositories( Repository value )
   {
      boolean changed = false;
      if ( ( this.ignoredRepositories != null ) && ( value != null ) )
      {
         changed = this.ignoredRepositories.remove( value );
         firePropertyChange( "ignoredRepositories", value, null );
//         if (changed)
//         {
//            value.removeFromOtherRoleName (this);
//         }
      }
      return changed;
   }

   /**
    * @return number of ignored repositories
    */
   public int sizeOfIgnoredRepositories()
   {
      return ( ( this.ignoredRepositories == null )
            ? 0
            : this.ignoredRepositories.size() );
   }


   /**
    * @return the current error handler module
    */
   public ErrorHandlerModule getErrorHandlerModule()
   {
      ErrorHandlerModule errorHandlerModule = this.errorHandlerModule;
      if ( errorHandlerModule == null ) {
         this.errorHandlerModule = errorHandlerModule = new DefaultErrorHandlerModule(this);
      }
      return errorHandlerModule;
   }

   /**
    * store current error handler module.
    */
   private ErrorHandlerModule errorHandlerModule;

   /**
    * Property name for firing property change events of field errorHandlerModule.
    */
   public static final String PROPERTY_ERROR_MODULE = "errorHandlerModule";

   /**
    * Change the current error handler module.
    *
    * @param value new error module
    */
   public void setErrorHandlerModule(final ErrorHandlerModule value)
   {
      final ErrorHandlerModule oldValue = this.errorHandlerModule;
      if (oldValue != value)
      {
         this.errorHandlerModule = value;
         firePropertyChange(PROPERTY_ERROR_MODULE, oldValue, value);
      }
   }
}


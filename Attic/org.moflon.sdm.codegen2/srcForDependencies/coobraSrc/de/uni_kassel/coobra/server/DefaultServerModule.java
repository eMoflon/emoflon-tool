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

package de.uni_kassel.coobra.server;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.errors.RepositorySetupException;
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.coobra.persistency.PersistencyException;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.server.errors.RemoteException;
import de.uni_kassel.coobra.transactions.MutableTransactionEntry;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.features.PlainFieldHandler;
import de.uni_kassel.features.QualifiedValueFieldHandler;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DefaultServerModule extends ServerModule<InetAddressAndPort, UserNameAndPassword> {
    private static final String MANAGEMENT_KEY_UPDATE = "SERVER_UPDATE";
    private static final String MANAGEMENT_KEY_CHECKIN = "SERVER_CHECKIN";
    private boolean compactingUpdate;

    /**
     * store value for field connectionModule
     */
    private ConnectionModule connectionModule;
    private Repository.ManagementDataHandler managementHandler = new Repository.ManagementDataHandler()
    {
       public void handleRedo( Change change )
       {
          if ( MANAGEMENT_KEY_UPDATE.equals( change.getKey() ) )
          {
             lastKnownServerVersion = (String) change.getNewValue();
          } else
          if ( MANAGEMENT_KEY_CHECKIN.equals( change.getKey() ) )
          {
             //FIXME: this might be a delegate we cannot use with the persistency module afterwards :/
             lastCommittedChangeMarker = change;
             lastKnownServerVersion = (String) change.getNewValue();
          }
       }
    };
    private Change lastCommittedChangeMarker;
    private String lastKnownServerVersion;
    public static final long VERSION = 1;

    /**
     * @param value new value for field repository
     * @return true if repository was changed
     * @throws RepositorySetupException if repository already received changes
     */
    @Override
    public boolean setRepository( final Repository value ) throws RepositorySetupException
    {
        final Repository oldValue = getRepository();
        boolean changed = false;
        if ( oldValue != value )
        {
            if ( value != null && !value.isUntouched() )
            {
                throw new RepositorySetupException( "Server module must be set up right after creating the repository!" );
            }
            if ( oldValue != null )
            {
//                oldValue.removeListener( this );
                oldValue.putManagementDataHandler( MANAGEMENT_KEY_UPDATE, null );
                oldValue.putManagementDataHandler( MANAGEMENT_KEY_CHECKIN, null );
            }
            super.setRepository( value );
            if ( value != null )
            {
//                value.addListener( this );
                value.putManagementDataHandler( MANAGEMENT_KEY_UPDATE, managementHandler );
                value.putManagementDataHandler( MANAGEMENT_KEY_CHECKIN, managementHandler );
            }
            changed = true;
        }
        return changed;
    }

    /**
     * @return current value of the field connectionModule
     */
    public ConnectionModule getConnectionModule()
    {
       return this.connectionModule;
    }

    /**
     * @param value new value for field connectionModule
     * @return true if connectionModule was changed
     */
    public boolean setConnectionModule( final ConnectionModule value )
    {
       final ConnectionModule oldValue = this.connectionModule;
       boolean changed = false;
       if ( oldValue != value )
       {
          if ( oldValue != null )
          {
             this.connectionModule = null;
          }
          this.connectionModule = value;
          firePropertyChange( "connectionModule", oldValue, value );
          if ( value != null )
          {
             value.setServerModule( this );
          }
          changed = true;
       }
       return changed;
    }

    public void setCompactingUpdate( boolean compacting ) {
        compactingUpdate = compacting;
    }

    public boolean isCompactingUpdate() {
        return compactingUpdate;
    }

    public void updateContinuous() throws RemoteException
    {
        performUpdate( true );
    }

    public void stopContinuosUpdate() throws RemoteException
    {
        getConnectionModule().stopContinuousUpdate();
    }

    public void setupClient() throws RemoteException
    {
       String currentPrefix = getRepository().getIdentifierModule().getCurrentPrefix();
       String result = getConnectionModule().validateIDPrefix( currentPrefix );
       if ( result != null )
       {
          if ( getRepository().isUntouched() ) {
             IdentifierModule idModule = new IdentifierModule( getRepository(),
                   getRepository().getIdentifierModule().getManager(), result, true );
             getRepository().setIdentifierModule( idModule );
             idModule.writePrefixToPersistencyModule();
          } else {
             throw new RepositorySetupException( "The server did not accept the id prefix '"+currentPrefix+"' " +
                   "but suggested '"+result+"' - repository was already changed thus altering prefix is not possible." );
          }
       }
    }

    /**
     * Update to current version of the server.
     *
     * @return list of conflicting local changes
     */
    @Override
    public List<Change> update() throws RemoteException
    {
        return performUpdate( false );
    }

    private List<Change> performUpdate( boolean continuous ) throws RemoteException
    {
        String newVersion = getConnectionModule().update( lastKnownServerVersion, compactingUpdate, continuous );
        if ( newVersion != null )
        {
           List<Change> conflicts = new ArrayList<Change>();
           Repository repository = getRepository();
           boolean lockAquired = repository.aquireOperationalizationLock();
           try {
              TransactionEntry entry = getConnectionModule().receiveFirst();
              while ( entry != null )
              {
                 checkConflictWithCurrentModel(entry, conflicts);

                 if ( entry instanceof Transaction )
                 {
                    entry.setAutoResolving( false );
                 }

                 if ( !(entry instanceof MutableTransactionEntry) ) {
                    throw new IllegalStateException("Entries received by DefaultServerModule must be mutable!");
                 }
                 ((MutableTransactionEntry)entry).setModifier(Change.MODIFIER_SERVER);
                 
                 repository.acknowledgeUpdate( entry );
                 entry = getConnectionModule().receiveNext( entry );
              }
           } finally {
              if ( lockAquired ) repository.releaseOperationalizationLock();
              getConnectionModule().finishUpdate();
           }
           lastKnownServerVersion = newVersion;
           Change managementChange = repository.getChangeFactory().changeManagement( null,
                 MANAGEMENT_KEY_UPDATE, lastKnownServerVersion, newVersion, Change.MODIFIER_SERVER );
//           lastCommittedChangeMarker =
           repository.acknowledgeChange( managementChange );

           return conflicts;
        } else
        {
           return null;
        }
    }

   /**
    * Checks if either the old value in the change matches the current model (affected object) or the new value matches.
    * @param entry entry to check
    * @param conflicts entry is added to this list if it's conflicting with the model state
    */
   public static void checkConflictWithCurrentModel(TransactionEntry entry, List<Change> conflicts)
   {
      Repository repository = entry.getRepository();
      boolean autoResolving = entry.isAutoResolving(); //todo: always true?
      entry.setAutoResolving(false);
      checkConflict:
      if (entry instanceof Change)
      {
         Change change = (Change) entry;
         // check affected objects and values resolution to detect deletion conflicts
         if (change.getKind() != Change.Kind.CREATE_OBJECT)
         {
            if (!checkId(change.getAffectedObjectID(), repository))
            {
               // affected object cannot be resolved - possibly deleted
               conflicts.add(change);
               break checkConflict;
            }
         }
         if (!check(change.getNewValue(), repository) || !check(change.getOldValue(), repository))
         {
            // new or old value cannot be resolved - possibly deleted
            conflicts.add(change);
            break checkConflict;
         }

         // check old value by comparing to model
         entry.setAutoResolving(true);
         final FieldHandler field = change.getField();
         if (change.getKind() == Change.Kind.ALTER_FIELD && field != null)
         {
            boolean lockAquired = repository.aquireOperationalizationLock();// for safety
            try
            {
               Object oldValue = change.getOldValue();
               Object newValue = change.getNewValue();
               if (field instanceof PlainFieldHandler)
               {
                  Object readValue = field.read(change.getAffectedObject());
                  if (readValue == null ? newValue != null && oldValue != null :
                        !readValue.equals(newValue) && !readValue.equals(oldValue))
                  {
                     conflicts.add(change);
                     break checkConflict;
                  }
               } else if (field instanceof QualifiedValueFieldHandler)
               {
                  QualifiedValueFieldHandler qualifiedField = (QualifiedValueFieldHandler) field;
                  final Object readValue = qualifiedField.get(change.getAffectedObject(), change.getKey());
                  if (readValue == null ? newValue != null && oldValue != null :
                        !readValue.equals(newValue) && !readValue.equals(oldValue))
                  {
                     conflicts.add(change);
                     break checkConflict;
                  }
               }
            } finally {
               if ( lockAquired ) repository.releaseOperationalizationLock();
            }
            //todo: check collection fields?
         }
      }
      entry.setAutoResolving( autoResolving );
   }

   private static boolean check(Object value, Repository repository)
   {
      if ( value instanceof ID ) {
         return checkId( (ID) value, repository);
      }
      else
      {
         return true;
      }
   }

   private static boolean checkId(ID id, Repository repository)
   {
      return id == null ||
            repository.getIdentifierModule().getObject( id ) != null;
   }

   /**
    * Determines if a checkin operation is necessary.
    * 
    * @return true, if a checkin operation is necessary.
    */
   public boolean isCheckinNecessary()
   {
      PersistencyModule persistencyModule = getRepository().getPersistencyModule();
      TransactionEntry entry = searchNextTransactionEntry( persistencyModule );
      if( entry == null ) return false;
      try
      {
         while (entry != null)
         {
            if (entry.getModifier() != Change.MODIFIER_SERVER && !entry.isRolledback())
            {
               return true;
            }
            entry = persistencyModule.receiveNext(entry);
         }
      } catch (RuntimeException e)
      {
         // maybe there are some changes to checkin
         return true;
      } catch (Error e)
      {
         // maybe there are some changes to checkin
         return true;
      }
      
      return false;
   }
   
   @Override
   public void checkin( String suggestedVersionName ) throws RemoteException
   {
      PersistencyModule persistencyModule = getRepository().getPersistencyModule();
      TransactionEntry entry = searchNextTransactionEntry( persistencyModule );
      if ( entry != null )
      {
         getConnectionModule().beginCheckin(lastKnownServerVersion, suggestedVersionName);
         String newVersion = null;
         boolean failed = false;
         try
         {
            while (entry != null)
            {
               if (entry.getModifier() != Change.MODIFIER_SERVER
                     && !entry.isLocal()
                     && !entry.isRolledback())
               {
                  boolean wasAutoResolving = entry.isAutoResolving();
                  entry.setAutoResolving(false);
                  getConnectionModule().send(entry, null); //todo: use sent entry.getEnclosingTransaction() here
                  entry.setAutoResolving(wasAutoResolving);
               }
               entry = persistencyModule.receiveNext(entry);
            }
            getConnectionModule().sendEOF();
         } catch (RuntimeException e)
         {
            failed = true;
            throw e;
         }  catch (Error e)
         {
            failed = true;
            throw e;
         }
         finally
         {
            if ( !failed )
            {
               newVersion = getConnectionModule().finishCheckin();
            }
         }
         if (newVersion != null)
         {
            Change managementChange = getRepository().getChangeFactory().changeManagement(null,
                  MANAGEMENT_KEY_CHECKIN, lastKnownServerVersion, newVersion, true);
            lastCommittedChangeMarker = getRepository().acknowledgeChange(managementChange);
            lastKnownServerVersion = newVersion;
         } else
         {
            throw new IllegalStateException("Server returned 'null' version!");
         }
      }
   }

   private TransactionEntry searchNextTransactionEntry( PersistencyModule persistencyModule )
   {
      TransactionEntry entry;
      if ( lastCommittedChangeMarker == null )
      {
         entry = persistencyModule.receiveFirst();
      } else
      {
         try
         {
            entry = persistencyModule.receiveNext( lastCommittedChangeMarker );
         } catch (IllegalArgumentException e)
         {
            entry = null;
            // the change does not reside in the active persistency module any more - search it again
            TransactionEntry anEntry = persistencyModule.receiveFirst();
            while ( anEntry != null )
            {
               if ( anEntry.isManagementEntry() )
               {
                  Change change = (Change) anEntry;
                  Object key = change.getKey();
                  if ( MANAGEMENT_KEY_CHECKIN.equals(key) )
                  {
                     entry = change;
                     lastCommittedChangeMarker = change;
                  }
               }
               anEntry = persistencyModule.receiveNext( anEntry );
            }
         }
      }
      return entry;
   }

   private UserNameAndPassword credentials;

   public void useAuthentication(UserNameAndPassword credentials)
   {
      this.credentials = credentials;
   }

   @Override
   public void connect( InetAddressAndPort target ) throws RemoteException
   {
       setConnectionModule( new TCPConnectionModule( target.address, target.port ) );
       setupClient();
   }

   public boolean isConnected()
   {
      return getConnectionModule() != null;
   }

   UserNameAndPassword getCredentials()
   {
      return credentials;
   }


   public static abstract class ConnectionModule extends PersistencyModule
   {
       /**
        * Connect the server module to the remote site/open a file etc.
        * May be called multiple times, but does not cascade.
        *
        * @param readonly if true the connection is opened for reading only
        * @throws de.uni_kassel.coobra.persistency.PersistencyException
        *          if the operation fails
        */
       @Override
       public abstract void open( boolean readonly ) throws PersistencyException;

       /**
        * Disconnect the module from the server/close file.
        * May be called multiple times, but does not cascade.
        *
        * @throws de.uni_kassel.coobra.persistency.PersistencyException
        *          if the operation fails
        */
       @Override
       public abstract void close() throws PersistencyException;

      /**
        * store value for field serverModule
        */
       private DefaultServerModule serverModule;

       /**
        * @return current value of the field serverModule
        */
       public DefaultServerModule getServerModule()
       {
           return this.serverModule;
       }

       /**
        * @param value new value for field serverModule
        * @return true if serverModule was changed
        */
       public boolean setServerModule( final DefaultServerModule value )
       {
           final DefaultServerModule oldValue = this.serverModule;
           boolean changed = false;
           if ( oldValue != value )
           {
               if ( oldValue != null )
               {
                   this.serverModule = null;
                   oldValue.setConnectionModule( null );
               }
               this.serverModule = value;
               if ( value != null )
               {
                   value.setConnectionModule( this );
               }
               changed = true;
           }
           return changed;
       }

       public abstract void beginCheckin(String lastKnownServerVersion, String suggestedVersionName) throws RemoteException;

       public abstract String finishCheckin() throws RemoteException;

       /**
        * @return null if no new changes are available, newest Version else
        */
       public abstract String update( String lastKnownServerVersion, boolean compacting, boolean continuous ) throws RemoteException;

       public abstract void sendEOF();

       public abstract String validateIDPrefix( String idPrefix ) throws RemoteException;

       public abstract void stopContinuousUpdate() throws RemoteException;

      protected abstract String finishUpdate();
   }


   /**
    * @return the lastKnownServerVersion
    */
   public String getLastKnownServerVersion()
   {
      return this.lastKnownServerVersion;
   }
}

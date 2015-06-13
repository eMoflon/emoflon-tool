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
import de.uni_kassel.coobra.Repository.ManagementDataHandler;
import de.uni_kassel.coobra.errors.ErrorHandlerModule;
import de.uni_kassel.coobra.identifiers.IDManager;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.coobra.server.errors.CredentialsException;
import de.uni_kassel.coobra.server.errors.NotAuthenticatedException;
import de.uni_kassel.coobra.server.errors.NotAuthorizedException;
import de.uni_kassel.coobra.server.handlers.NonResolvingClasshandlerFactory;
import de.uni_kassel.coobra.server.usermanagement.User;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 31.03.2005, 13:46:53
 */
public class DefaultServer extends NameServer
{
   public static final String ERROR_OPERATION_IN_PROGRESS = "Operation in Progress";

   private final Repository repository;
   
   /**
    * name of the repository known to users
    */
   private String repositoryName;
   public static final Logger LOGGER = Logger.getLogger( DefaultServer.class.getName() );
   public static final String MANAGEMENT_KEY_VERSION_INFO = "DefaultServer.VersionInfo";

   public Repository getRepository()
   {
      return repository;
   }

   public DefaultServer( int port ) throws IOException
   {
      serverSocket = new ServerSocket( port );
      repository = new Repository();

      repository.setIdentifierModule( new IdentifierModule( repository,
            new IDManager( true ), IdentifierModule.generateRandomPrefix( 30 ), true ) );
      repository.getFeatureAccessModule().setClassHandlerFactory( null,
            new NonResolvingClasshandlerFactory( repository.getFeatureAccessModule() ) );
      repository.addListener( new Repository.RepositoryListener()
      {
         public void acknowledgeChange(TransactionEntry entry, EventType type)
         {
            if ( type == EventType.RESTORED_TRANSACTION && entry instanceof Transaction ) {
               Transaction transaction = (Transaction) entry;
               if ( transaction.getEnclosingTransaction() == null ) {
                  // top level transaction is a version
                  registerVersion( null, transaction ); // username is assigned upon read of the management change
               }
            }
         }
      });
      repository.putManagementDataHandler(MANAGEMENT_KEY_VERSION_INFO, new ManagementDataHandler()
      {
         public void handleRead(Change change)
         {
            TransactionReference ref = (TransactionReference) change.getAffectedObject();
            Transaction transaction = repository.getPersistencyModule().resolveTransaction(ref);
            DefaultServer.VersionInfo info = getVersionInfo(transaction.getName());
            if ( info != null ) // info might be null while in checkin
            {
               info.username = (String) change.getNewValue();
            }
         }

         public void handleRedo(Change change)
         {


         }
      });

      // add a dummy handler for management changes (as dependent projects should not be resolved on the server)
      repository.putManagementDataHandler(Repository.MANAGEMENT_KEY_REQUIRED_REPOSITORY, new ManagementDataHandler()
      {
         @Override
         public void handleRedo( Change change )
         {
         }
         @Override
         public void handleRead( Change change )
         {
         }
      });
   }

   @Override
   public DefaultServer start()
   {
      super.start();
      return this;
   }

   @Override
   public void checkReadPermission( User user ) throws CredentialsException
   {
      super.checkReadPermission( user );
      if ( getUserManager().isConfigured() ) { //TODO: allow everything if no users configured?
         if( user == null )
         {
            throw new NotAuthenticatedException( "no username specified" );
         }
         if( user.isBlocked() )
         {
            throw new NotAuthorizedException( "The user '" + user.getUserName() + "' is currently blocked." );
         }
         if( !user.getRepositories().containsKey( getRepositoryName() ) )
         {
            throw new NotAuthorizedException( user.getUserName() );
         }
      }
   }

   @Override
   public void checkWritePermission( User user ) throws CredentialsException
   {
      super.checkWritePermission( user );
      if ( getUserManager().isConfigured() ) { //allow everything if no users configured
         if( user == null )
         {
            throw new NotAuthenticatedException( "no username specified" );
         }
         if( user.isGuest() )
         {
            throw new NotAuthorizedException( "Sorry, guest users may only read repositories." );
         }
         if( user.isBlocked() )
         {
            throw new NotAuthorizedException( "The user '" + user.getUserName() + "' is currently blocked." );
         }
         if( !user.getRepositories().containsKey( getRepositoryName() ) )
         {
            throw new NotAuthorizedException( user.getUserName() );
         }
      }
   }

   public Transaction announceVersion(String version, String userName)
   {
      Transaction transaction = repository.startTransaction(version);
      repository.acknowledgeUpdate(repository.getChangeFactory().changeManagement(
            transaction.getReference(), DefaultServer.MANAGEMENT_KEY_VERSION_INFO, null, userName, false ));
      return transaction;
   }

   public class VersionInfo
   {
      private final Transaction transaction;
      
      private String username;

      public VersionInfo( String username, Transaction transaction )
      {
         if( transaction == null )
         {
            throw new NullPointerException();
         }
         this.transaction = transaction;
         this.username = username;
      }

      /**
       * store value for field next
       */
      private VersionInfo next;

      /**
       * @return current value of the field next
       */
      public VersionInfo getNext()
      {
         return this.next;
      }

      public String getName()
      {
         return transaction.getName();
      }

      /**
       * @param value new value for field next
       * @return true if next was changed
       */
      boolean setNext( final VersionInfo value )
      {
         final VersionInfo oldValue = this.next;
         boolean changed = false;
         if( oldValue != value )
         {
            if( oldValue != null )
            {
               this.next = null;
               oldValue.setPrevious( null );
            }
            this.next = value;
            if( value != null )
            {
               value.setPrevious( this );
            }
            changed = true;
         }
         return changed;
      }

      /**
       * store value for field previous
       */
      private VersionInfo previous;

      /**
       * @return current value of the field previous
       */
      public VersionInfo getPrevious()
      {
         return this.previous;
      }

      /**
       * @param value new value for field previous
       * @return true if previous was changed
       */
      boolean setPrevious( final VersionInfo value )
      {
         final VersionInfo oldValue = this.previous;
         boolean changed = false;
         if( oldValue != value )
         {
            if( oldValue != null )
            {
               this.previous = null;
               oldValue.setNext( null );
            }
            this.previous = value;
            if( value != null )
            {
               value.setNext( this );
            }
            changed = true;
         }
         return changed;
      }

      public Transaction getTransaction()
      {
         return transaction;
      }

      /**
       * @return name of the user that committed this VersionInfo's transaction, if any (may be null).
       */
      public String getUsername()
      {
         return this.username;
      }
   }

   private VersionInfo first;

   private Map<String, VersionInfo> versionMap;

   private VersionInfo last;
   
   public final static String REGISTER_VERSION = "Register Version";

   public void registerVersion( String username, Transaction checkinTransaction )
   {
      if( versionMap == null )
      {
         versionMap = new HashMap<String, VersionInfo>();
      }
      VersionInfo info = new VersionInfo( username, checkinTransaction );
      VersionInfo oldValue = versionMap.put( checkinTransaction.getName(),
            info );
      if( oldValue != null )
      {
         getRepository().getErrorHandlerModule().error( getRepository(), ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_PERSISTENCY_COMPACT_TYPE_UNKNOWN,
               "Duplicate version '" + checkinTransaction.getName() + "'!", null, checkinTransaction.getName() );
      }
      if( last != null )
      {
         last.setNext( info );
      }
      else
      {
         first = info;
      }
      last = info;

      // inform listeners
      firePropertyChange( REGISTER_VERSION, null, info );

      //todo: (re)store version info
   }

   public VersionInfo getVersionInfo( String version )
   {
      return versionMap != null ? versionMap.get( version ) : null;
   }

   public VersionInfo getLastVersion()
   {
      return last;
   }

   public VersionInfo getFirstVersion()
   {
      return first;
   }
   
   @Override
   protected void createClientSession( Socket socket )
   {
      new ClientSession( this, socket );
   }

	/* (non-Javadoc)
	 * @see de.uni_kassel.coobra.server.NameServer#shutdown()
	 */
	@Override
	public void shutdown()
	{
		super.shutdown();
		repository.getPersistencyModule().close();
	}

   public void setRepositoryName(String repositoryName)
   {
      this.repositoryName = repositoryName;
   }

   public String getRepositoryName()
   {
      return repositoryName;
   }
}

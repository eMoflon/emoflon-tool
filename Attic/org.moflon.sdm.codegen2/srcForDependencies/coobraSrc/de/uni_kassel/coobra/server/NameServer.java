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

import de.uni_kassel.coobra.persistency.PersistencyException;
import de.uni_kassel.coobra.server.errors.CredentialsException;
import de.uni_kassel.coobra.server.errors.NotAuthenticatedException;
import de.uni_kassel.coobra.server.errors.NotAuthorizedException;
import de.uni_kassel.coobra.server.errors.RemoteException;
import de.uni_kassel.coobra.server.errors.UnknownResponseException;
import de.uni_kassel.coobra.server.messages.ShutDownRequest;
import de.uni_kassel.coobra.server.usermanagement.AuthFile;
import de.uni_kassel.coobra.server.usermanagement.User;
import de.uni_kassel.coobra.server.usermanagement.UserManagement;
import de.uni_kassel.util.PropertyChangeSourceImpl;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mbork
 * @version $Revision$
 */
public abstract class NameServer extends PropertyChangeSourceImpl
{
   /**
    * container of all users
    */
   private UserManagement userManager;

   /**
    * Indicates if this server runs: if {@link #start()} has been executed. 
    */
   private boolean running;
   
   protected NameServer()
   {
   }

   protected ServerSocket serverSocket;

   private AcceptThread acceptThread;

   public static final Logger LOGGER = Logger.getLogger( NameServer.class.getName() );
   public static final String NAME_SERVICE_USERNAME = "NameServiceRegistrant";

   static
   {
      //FIXME: this prevents logger configuration from loading properly!
      LOGGER.setLevel( Level.ALL );
   }

   /**
    * map from String (name) to ServiceInfo
    */
   protected final Map<String, RepositoryInfo> services = new TreeMap<String, RepositoryInfo>();

   
   /**
    * {@link AuthFile} uses a file to (re)store the data of the {@link #userManager}.
    */
   private AuthFile authFile;
   
   /**
    * Start server operation.
    * 
    * @return this
    */
   public NameServer start()
   {
      userManager = new UserManagement();
      if ( authFile != null ) {
         LOGGER.info("reading auth file: " + authFile.getLocation() );
         authFile.read( userManager );
      }
      acceptThread = new AcceptThread();
      acceptThread.start();
      return this;
   }
   
   /**
    * Updates the {@link #authFile}, if exists.
    */
   public void updateUserManagement()
   {
      if(authFile != null) authFile.read( getUserManager() );
   }

   /**
    * Sets the file to use as storagefile for the {@link UserManagement}.<br>
    * Note that this must happen before the server is started!
    * 
    * @param file the file to use as storagefile for the {@link UserManagement}.
    * @throws IllegalStateException if the server has already been started.
    */
   protected void createAuthFile(File file) throws IllegalStateException
   {
      if(running) throw new IllegalStateException("Cannot set authfile - the server has already been started.");
      authFile = new AuthFile( file );
   }
   
   /**
    * Performs some checks if the given {@link User} has read access to
    * this server. Subclasses are encouraged to call this implementation,
    * too, as it updates the {@link UserManagement} if neccessary.
    * 
    * @param user the user to check.
    * @throws CredentialsException if the user has no read access to this server.
    */
   public void checkReadPermission( User user ) throws CredentialsException
   {
      updateUserManagement();
   }
   
   /**
    * Performs some checks if the given {@link User} has write access to
    * this server. Subclasses are encouraged to call this implementation,
    * too, as it updates the {@link UserManagement} if neccessary.
    * 
    * @param user the user to check.
    * @throws CredentialsException if the user has no write access to this server.
    */
   public void checkWritePermission( User user ) throws CredentialsException
   {
      updateUserManagement();
   }

   /**
    * Performs some checks if the given {@link User} has manage access to
    * this server. Managing includes starting and stopping.<br> 
    * Subclasses are encouraged to call this implementation, too, as it 
    * updates the {@link UserManagement} if neccessary.
    * 
    * @param user the user to check.
    * @throws CredentialsException if the user has no manage access to this server.
    */
   public final void checkManagePermission( User user ) throws CredentialsException
   {
      updateUserManagement();
      //todo: ok?
      if( user == null )
      {
         throw new NotAuthenticatedException( "no username specified" );
      }
      if( user.getUserRole() != User.UserRole.ADMIN )
      {
         throw new NotAuthorizedException( user.getUserName() );
      }
   }

   /**
    * request shutdown of specified service
    *
    * @param host host of the name server
    * @param port port of the name server
    * @param user username of user who requested shutdown
    * @param password password of the user
    * @throws IOException when open/write/read/close cause errors
    */
   public static void shutdownRequest( InetAddress host, int port,
			String user, String password ) throws RemoteException, IOException,
			UnknownResponseException
	{
		TCPConnectionImpl connection = new TCPConnectionImpl( host, port );
		connection.open();
		try
		{
			ShutDownRequest shutDownRequest = new ShutDownRequest();
			shutDownRequest.setUserName( user );
			shutDownRequest.setPassword( password );
			connection.send( shutDownRequest );
			connection.readResponse();
			connection.close();
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch( PersistencyException e )
			{
			}
		}
	}

   public static final String SHUTDOWN_EVENT_NAME = "ShutdownEvent"; 
   
   /**
    * Performs a shutdown of the {@link AcceptThread} and closes the {@link #serverSocket}.<br>
    * Registered listeners receive a shutdown event.
    */
   public void shutdown()
   {
      DefaultServer.LOGGER.info( "stopping service..." );
      // TODO: care for completing current jobs and flushing persistency
      // use ERROR_OPERATION_IN_PROGRESS etc - remember to update coobrasc RepositoryProcess.java#stopRepository
      if( acceptThread != null )
      {
         acceptThread.interrupt();
         acceptThread = null;
         try
         {
            if( !serverSocket.isClosed() )
            {
               serverSocket.close();
            }
         }
         catch( IOException e )
         {
            e.printStackTrace();
         }
      }
      // inform listeners
      firePropertyChange( SHUTDOWN_EVENT_NAME, false, true );
      running = false;
      LOGGER.info( "Service terminated." );
   }

   protected abstract void createClientSession( Socket socket );

   /**
    * adds a repository to the map of registered repositories
    *
    * @param name the name of the repository
    * @param host host on which the repository is running
    * @param port port on which the repository is running
    * @throws IOException
    */
   public synchronized void addToServices( String name, String host, int port ) throws IOException
   {
	  RepositoryInfo repository = services.get( name );
      if( repository == null )
      {
         repository = new RepositoryInfo( name, host, port );
         services.put( name, repository );
         LOGGER.info( " -> new repository" );
      }
      else
      {
         repository.setHost( host );
         repository.setPort( port );
         repository.resetAnnouncementTime();
      }
   }

   /**
    * @param name of a repository
    * @return the ServiceInfo of an repository, null if it does not exist or its announcement was to long ago.
    */
   public RepositoryInfo getRepositoryInfo( String name )
   {
	   RepositoryInfo repository = services.get( name );
      if( repository != null )
      {
         if( repository.isExpired() )
         {
            NameServer.LOGGER.log( Level.INFO, "The NameServiceInfo is expired." );
         }
         return repository;
      }
      return null;
   }

   private class AcceptThread extends Thread
   {
      /**
       * @see Thread#run()
       */
      @Override
      public void run()
      {
         try
         {
            while( !serverSocket.isClosed() )
            {
               Socket socket = serverSocket.accept();
               createClientSession( socket );
            }
         }
         catch( IOException e )
         {
            if( !isInterrupted() )
            {
               e.printStackTrace();
            }
         }
      }
   }

   /**
	 * @return Returns the users.
	 */
	public UserManagement getUserManager()
	{
		return userManager;
	}

   /**
    * Only for internal use by JUnit tests!
    * 
    * @return the serverSocket
    */
   ServerSocket getServerSocket()
   {
      return this.serverSocket;
   }
}

/*
 * $Log$
 * Revision 1.25  2008/10/23 14:38:20  cschneid
 * introduced binary persistency modules
 *
 * Revision 1.24  2007/12/14 15:56:34  mbork
 * simplified user management and added the email address to the user's data
 *
 * Revision 1.23  2007/10/25 10:59:27  mbork
 * use ManagementOnlyFilter automatically if restoring management data
 * added a request to receive version information data from the repository
 *
 * Revision 1.22  2007/04/12 18:46:11  mbork
 * fixed stupid bug o_O
 *
 * Revision 1.21  2007/04/12 14:39:28  mbork
 * UserManagement now supports external changes to the file in the filesystem.
 *
 * Revision 1.20  2007/04/12 11:51:11  mbork
 * log if the RepositoryInfo is expired
 *
 * Revision 1.19  2007/04/12 11:20:33  mbork
 * fixed a major bug which caused the NameService to forget ServerRepositories after 24hs...
 *
 * Revision 1.18  2007/02/21 12:14:51  mbork
 * fixed typo
 *
 * Revision 1.17  2006/12/20 14:26:09  cschneid
 * Introduced ErrorHandlerModule
 *
 * Revision 1.16  2006/12/19 11:46:01  cschneid
 * header updated
 */

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

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.persistency.FilePersistencyModule;
import de.uni_kassel.coobra.plugins.PluginManager;
import de.uni_kassel.coobra.server.errors.RemoteException;
import de.uni_kassel.coobra.server.errors.UnknownResponseException;
import de.uni_kassel.coobra.server.messages.RegisterServerRequest;
import de.uni_kassel.coobra.server.usermanagement.AuthFile;
import de.uni_kassel.coobra.server.usermanagement.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.BindException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.FileLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author akoch
 * @created $Date$
 * @version $Revision$
 */
public class DefaultServerApplication
{
   /**
    * default extension for the server side repository files. 
    */
   public static final String DEFAULT_FILE_EXTENSION = ".ctr";

   /**
    * default extension for the lock file of the server side repository. 
    */
   public static final String LOCK_FILE_EXTENSION = ".lock";

   
   /**
    * first port that is used by the {@link #startServer} method.
    */
   public static final int DEFAULT_PORT_RANGE_START = 27501;

   /**
    * maximum number of ports used by the {@link #startServer} method.
    */
   public static final int DEFAULT_PORT_RANGE_LENGTH = 100;

   private static final String PARAMETER_MESSAGE = "DefaultServerImpl [-n nameserviceHost[:nameservicePort] [-a authfile]"
         + "repositoryName[:repositoryPort]] \n Default: nameservice on localhost: "
         + NameServerApplication.DEFAULT_PORT
         + ", default port of repository: "
         + DEFAULT_PORT_RANGE_START
         + "\n if no repositoryName is given an unnamed repository process is started. Attention: unnamed Repositorys"
         + "are not stored after stopping server.";

   /**
    * The DefaultServer.
    */
   private DefaultServer defaultServer;
   
   /**
    * After starting the DefaultServer we gain a lock on a file to indicate that the server is running.
    */
   private FileLock lock;
   
   private static final Logger LOGGER = Logger.getLogger("Server");


   /**
    * use this constructor to start an unnamed repository
    */
   public DefaultServerApplication()
   {
	   DefaultServer.LOGGER.info( "Starting unnamed repository process" );
   }
   
   /**
    * Starts a named repository server.
    * 
    * @param repositoryName Name of the repository. Optionally including the path; default value is
    *           to use the working directory.
    * @param repositoryPortRangeStart The port to register the repository on. If its not free,
    *           ++repositoryPortRangeStart is used
    * @param authFile A file containing user management data, if null none is used.
    */
   public void startServer( final String repositoryName, final int repositoryPortRangeStart, final File authFile )
   {
      startServer( null, 0, repositoryName, repositoryPortRangeStart, authFile );
   }

   /**
    * Starts a named repository server and registers it on the given name server.
    * 
    * @param nameServer Address of the name server to connect to. If null, no name server is used.
    * @param nameServerPort port of the name server, only used if the address is not null.
    * @param repositoryName name of the repository. Optionally including the path; default value is
    *           to use the working directory.
    * @param repositoryPortRangeStart the port to register the repository on. if its not free,
    *           ++repositoryPortRangeStart is used
    * @param authFile A file containing user management data, if null none is used.
    */
   public void startServer( final InetAddress nameServer, final int nameServerPort, String repositoryName,
         final int repositoryPortRangeStart, final File authFile )
   {
      String pathToRepository = "";
      if( repositoryName.contains( File.separator ) )
      {
         pathToRepository = repositoryName.substring( 0, repositoryName.lastIndexOf( File.separator ) + 1 );
         repositoryName = repositoryName.substring( repositoryName.lastIndexOf( File.separator ) + 1, repositoryName.length() );
         NameServer.LOGGER.info( "Starting repository in path: '" + pathToRepository + "' using name: '" + repositoryName + "'" );
      }
      try
      {
         int port = repositoryPortRangeStart;
         while( defaultServer == null )
         {
            try
            {
               // create DefaultServer instance
               defaultServer = new DefaultServer( port );
               defaultServer.setRepositoryName( repositoryName );
               final Repository repository = defaultServer.getRepository();

               // set authfile, if any
               if( authFile != null ) defaultServer.createAuthFile( authFile );

               // create and link persistence module
               final File file = new File( pathToRepository + repositoryName + DEFAULT_FILE_EXTENSION );
               FilePersistencyModule filePersistencyModule = new FilePersistencyModule( file );
               repository.setPersistencyModule( filePersistencyModule );

               // open file and receive changes
               filePersistencyModule.open( false );
               if( filePersistencyModule.receiveFirst() == null )
               {
                  // no changes available - write file header
                  repository.getIdentifierModule().writePrefixToPersistencyModule();
               }
               else
               {
                  final long startTime = System.nanoTime();

                  // start restoring the management data
                  repository.restore( true );

                  long time = System.nanoTime() - startTime;
                  time /= 1000 * 1000;
                  DefaultServer.LOGGER.info( "Restored data in " + time + " ms." );
               }

               // start accept thread after restoring
               defaultServer.start();
               DefaultServer.LOGGER.info( "Bound to port " + port );
            }
            catch( BindException e )
            {
               if( port < DEFAULT_PORT_RANGE_START + DEFAULT_PORT_RANGE_LENGTH )
               {
                  port++;
               }
               else
               {
                  throw e;
               }
            }
         }

         // create .lock file
         File lockFile = new File( pathToRepository + repositoryName + LOCK_FILE_EXTENSION );
         lockFile.createNewFile();
         lockFile.deleteOnExit();

         // gain lock
         this.lock = new RandomAccessFile( lockFile, "rw" ).getChannel().tryLock();
         
         // register plugins, if any
         new PluginManager().setupAndStartPlugins( defaultServer );

         if( nameServer != null )
         {
            // start (re-)announcement-thread
            new ReannouncementThread( nameServer, nameServerPort, repositoryName, port, defaultServer.getUserManager().getHostname() ).start();
         }
      }
      catch( RuntimeException e )
      {
         DefaultServer.LOGGER.log( Level.SEVERE, "failed to start repository server " + nameServer, e );
         if( defaultServer != null )
         {
            defaultServer.shutdown();
         }
         System.exit( -1 );
      }
      catch( IOException e )
      {
         DefaultServer.LOGGER.log( Level.SEVERE, "failed to start repository server " + nameServer, e );
         if( defaultServer != null )
         {
            defaultServer.shutdown();
         }
         System.exit( -1 );
      }
   }

   /**
    * Starts an unnamed repository server.
    *
    * @param repositoryPortRangeStart
    */
   private void startServer( final int repositoryPortRangeStart, File authfile )
	{
		try
		{
			int port = repositoryPortRangeStart;
			while( defaultServer == null )
			{
				try
				{
					defaultServer = new DefaultServer( port );
               defaultServer.createAuthFile( authfile );
               defaultServer.start();
					DefaultServer.LOGGER.info( "Bound to port " + port );
				}
				catch( BindException e )
				{
					if( port < DEFAULT_PORT_RANGE_START
							+ DEFAULT_PORT_RANGE_LENGTH )
					{
						port++;
					} else
					{
						throw e;
					}
				}
			}
		}
		catch( IOException e )
		{
			if( defaultServer != null )
			{
				DefaultServer.LOGGER.info( "Error: " + e.getMessage() );
				DefaultServer.LOGGER.info( "stopping service..." );
				defaultServer.shutdown();
				System.exit( -1 );
			}
		}
	}

   private void registerRepository(InetAddress nameServerHost, int nameServerPort, String repositoryName,
         int repositoryPort, String hostname) throws IOException
   {
      TCPConnectionImpl connection = new TCPConnectionImpl(nameServerHost, nameServerPort);
      connection.open();
      RegisterServerRequest registerServerRequest = new RegisterServerRequest( repositoryName, hostname, repositoryPort );
      User nameserviceUser = defaultServer.getUserManager().getUserByName( NameServer.NAME_SERVICE_USERNAME );
      if( nameserviceUser != null )
      {
         registerServerRequest.setUserName( nameserviceUser.getUserName() );
         registerServerRequest.setPassword( nameserviceUser.getPasswordHash() );
      }
      connection.send(registerServerRequest);
      try
      {
         connection.readResponse();
         connection.close();
      }
      catch( UnknownResponseException e )
      {
         DefaultServer.LOGGER.log(Level.SEVERE, "failure registering at nameservice " + nameServerHost, e);
         connection.close();
         defaultServer.shutdown();
         System.exit(-1);
      }
      catch( RemoteException e )
      {
         DefaultServer.LOGGER.log(Level.SEVERE, "failure registering at nameservice " + nameServerHost, e.getCause());
         connection.close();
         defaultServer.shutdown();
         System.exit(-1);
      }
      // TODO check sequence number
      DefaultServer.LOGGER.info("repository successfully registered at " + nameServerHost + ":" + nameServerPort);
   }
   
   
   /**
    * Performs a shutdown of the {@link AcceptThread} and closes the {@link #serverSocket}, then
    * releases the FileLock on the .lock file.<br/>
    * Registered listeners receive a shutdown event.
    */
   public void shutdown()
   {
      // shutdown default server
      defaultServer.shutdown();

      // remove lock
      try
      {
         lock.release();
      }
      catch( IOException ex )
      {
         ;
      }
   }
   
   /**
    * This thread is responsible to reannounce the repository on the NameServer.<br>
    * The interval is set just one hour below the {@link RepositoryInfo#TIME_TO_REANNOUNCE}.
    * 
    * @author Manuel Bork <manuel.bork@uni-kassel.de>
    * @version $Id: DefaultServerApplication.java 611 2009-01-06 16:56:01Z mbork $
    */
   private class ReannouncementThread extends Thread implements PropertyChangeListener
   {
      private static final int ONE_HOUR = 60*60*1000;
      private boolean isRunning = true;
      private final InetAddress nameServerHost;
      private final int nameServerPort;
      private final String repositoryName;
      private final int repositoryPort;
      private long reannouncementInterval;
      private final String hostname;
      
      private ReannouncementThread (InetAddress nameServerHost, int nameServerPort, String repositoryName, int repositoryPort, String hostname)
      {
         this.nameServerHost = nameServerHost;
         this.nameServerPort = nameServerPort;
         this.repositoryName = repositoryName;
         this.repositoryPort = repositoryPort;
         this.hostname = hostname;
         
         // register as PropertyChangeListener on the defaultServer
         defaultServer.addPropertyChangeListener( this );
         // set the reannouncement interval
         resetReannouncementInterval();
      }
      
      @Override
      public void run()
      {
         // sleep and then reset the announcement time
         while( isRunning )
         {
            try
            {
               // (re)send the RegisterServerRequest
               registerRepository( nameServerHost, nameServerPort, repositoryName, repositoryPort, hostname );
               // reset reannouncement interval on success
               resetReannouncementInterval();
               // sleep some time..
               Thread.sleep( reannouncementInterval );
            }
            catch( IOException e )
            {
               DefaultServer.LOGGER.log( Level.SEVERE, "failed to reset the reannouncement time on NameServer" + nameServerHost.getHostName(), e );
               DefaultServer.LOGGER.log( Level.SEVERE, "Now trying to shut down :-/." );
               if( defaultServer != null )
               {
                  defaultServer.shutdown();
                  isRunning = false;
               }
               else
               {
                  DefaultServer.LOGGER.log( Level.SEVERE, "WAHHH.. cannot shutdown.. setting reannouncement interval to 5 minutes - perhaps the NameServer will be reachable again o_O" );
                  reannouncementInterval = 5 * 60 * 1000; // 5 minutes
               }
            }
            catch( InterruptedException e )
            {
               isRunning = false;
            }
         }
      }

      /**
       * Tries to set the timeToReannounce 1h less than the RepositoryInfo.TIME_TO_REANNOUNCE.
       * 
       * @return
       */
      private void resetReannouncementInterval()
      {
         if( RepositoryInfo.TIME_TO_REANNOUNCE > ONE_HOUR ) 
         {
            this.reannouncementInterval = RepositoryInfo.TIME_TO_REANNOUNCE - ONE_HOUR;
         }
         else
         {
            this.reannouncementInterval = RepositoryInfo.TIME_TO_REANNOUNCE;
         }
      }

      /**
       * Reacts on {@link PropertyChangeEvent} events. <br> 
       * If the eventname is {@link NameServer#SHUTDOWN_EVENT_NAME} this 
       * {@link ReannouncementThread} terminates.
       */
      public void propertyChange( PropertyChangeEvent evt )
      {
         if( NameServer.SHUTDOWN_EVENT_NAME.equals( evt.getPropertyName() ) )
         {
            this.interrupt();
         }
      }
   }

   /**
	 * @param args command line arguments
	 */
	public static void main( String[] args )
	{
		InetAddress nameserver = NameServerApplication.DEFAULT_HOST;
		int nameserverPort = NameServerApplication.DEFAULT_PORT;

		String repositoryName = "";
		int repositoryPortrangeStart = DEFAULT_PORT_RANGE_START;

		File authFile = new File( AuthFile.DEFAULT_NAME );

		for( int i = 0; i < args.length; ++i )
		{
			if( args[ i ].equals( "-n" ) && (i+1) < args.length )
			{
				++i;
				try
				{
					int indexOfColon = args[ i ].indexOf( ':' );
					if( indexOfColon > 0 )
					{
						nameserver = InetAddress.getByName( args[ i ].substring( 0, indexOfColon ) );
						nameserverPort = Integer.parseInt( args[ i ]	.substring( indexOfColon + 1 ) );
					} else
					{
						nameserver = InetAddress.getByName( args[ i ] );
					}
				}
				catch( NumberFormatException e )
				{
					LOGGER.log( Level.SEVERE, PARAMETER_MESSAGE );
					System.exit( -1 );
				}
				catch( UnknownHostException e )
				{
               LOGGER.log( Level.SEVERE, PARAMETER_MESSAGE);
               LOGGER.log( Level.SEVERE, "Host not found: " + e.getMessage());
               System.exit( -1 );
				}
			}
			else if( args[ i ].equals( "-a" ) && (i+1) < args.length )
			{
				++i;
				authFile = new File( args[ i ] );
			}
			else
			{
				try
				{
					repositoryName = args[ i ];
               Pattern p = Pattern.compile(":(\\d+)$");
               Matcher m = p.matcher(repositoryName);
               if (m.matches())
               {
                  final String matchedPort = m.group(1);
                  repositoryPortrangeStart = Integer.parseInt( matchedPort );
						repositoryName = args[ i ].substring( 0, args[ i ].length()-matchedPort.length()-1 );
					}
				}
				catch( NumberFormatException e )
				{
               LOGGER.log( Level.SEVERE, PARAMETER_MESSAGE);
               System.exit( -1 );
				}
			}
		}

		// create server object
		if( repositoryName.length() == 0 )
		{
			DefaultServerApplication server = new DefaultServerApplication();
			server.startServer( repositoryPortrangeStart, authFile );
		}
		else
		{
         DefaultServer.LOGGER.info("Starting server with name '" + repositoryName + "'");
         DefaultServerApplication server = new DefaultServerApplication();
			// try starting and registering server
			server.startServer( nameserver, nameserverPort, repositoryName, repositoryPortrangeStart, authFile );
		}
	}

   /**
    * Only for internal use by JUnit tests!
    * 
    * @return the defaultServer
    */
   DefaultServer getDefaultServer()
   {
      return this.defaultServer;
   }

}

/*
 * $Log$
 * Revision 1.24  2009/01/06 16:56:00  mbork
 * bugfix for coobra server: if running on the same machine as the nameservice, remember correct hostname
 *
 * Revision 1.23  2008/10/23 14:38:20  cschneid
 * introduced binary persistency modules
 *
 * Revision 1.22  2007/12/07 12:54:21  mbork
 * added a UserName to the VersionInfo
 *
 * Revision 1.21  2007/10/25 10:59:27  mbork
 * use ManagementOnlyFilter automatically if restoring management data
 * added a request to receive version information data from the repository
 *
 * Revision 1.20  2007/08/23 11:30:26  cschneid
 * removed sysout
 *
 * Revision 1.19  2007/06/11 14:01:40  mbork
 * fixed a bug resulting in tons of reannouncement-threads...
 *
 * Revision 1.18  2007/04/12 14:39:28  mbork
 * UserManagement now supports external changes to the file in the filesystem.
 *
 * Revision 1.17  2007/04/12 11:20:33  mbork
 * fixed a major bug which caused the NameService to forget ServerRepositories after 24hs...
 *
 * Revision 1.16  2007/04/05 10:08:37  creckord
 * - fixed parsing of port
 * - repository address is determined by nameserver
 *
 * Revision 1.15  2007/02/21 12:15:49  mbork
 * bugfix: creation of lock file
 *
 * Revision 1.14  2007/01/15 12:45:06  mbork
 * fixed bug concerning startup in coobrasc
 *
 * Revision 1.13  2006/12/20 14:26:09  cschneid
 * Introduced ErrorHandlerModule
 *
 * Revision 1.12  2006/12/19 11:46:01  cschneid
 * header updated
 *
 * Revision 1.11  2006/11/10 15:06:44  mbork
 * added possibility to use a fully qualified pathname to start a repository.
 *
 * Revision 1.10  2006/06/22 12:59:27  mbork
 * locks on repository file. removed .lock files.
 *
 * Revision 1.9  2006/06/21 15:24:34  mbork
 * server startup: now using file-locks to indicate server running
 */
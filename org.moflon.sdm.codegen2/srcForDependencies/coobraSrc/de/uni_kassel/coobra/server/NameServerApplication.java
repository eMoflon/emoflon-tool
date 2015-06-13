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
import de.uni_kassel.coobra.server.errors.RemoteException;
import de.uni_kassel.coobra.server.errors.UnknownResponseException;
import de.uni_kassel.coobra.server.messages.QueryRepositoryRequest;
import de.uni_kassel.coobra.server.messages.Response;
import de.uni_kassel.coobra.server.usermanagement.AuthFile;
import de.uni_kassel.coobra.server.usermanagement.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author manuel bork
 */
public class NameServerApplication extends NameServer
{
   /**
    * default network port for the name service
    */
   public final static int DEFAULT_PORT = 27500;

   /**
    * default network host for the name service
    */
   public final static InetAddress DEFAULT_HOST;

   static
   {
      try
      {
         DEFAULT_HOST = InetAddress.getLocalHost();
      }
      catch( UnknownHostException e )
      {
         throw new RuntimeException( e );
      }
   }

   /**
    * persistency file name
    */
   private String filename;

   /**
    * @param port     port the new name service is listening on
    * @param filename used for persistency
    */
   public NameServerApplication( int port, File authFile, String filename )
   {
      this( port, authFile );
      this.filename = filename;
      LOGGER.info( "NameService startup on port " + port + " with storage file " + filename );
      try
      {
         restore();
      }
      catch( IOException e )
      {
         LOGGER.severe( "Restore failed" );
         e.printStackTrace();
         synchronized( services )
         {
            services.clear();
         }
      }
   }

   /**
    * @param port the new name service is listening on
    */
   public NameServerApplication( int port, File authFile )
   {
      try
      {
         serverSocket = new ServerSocket( port );
         createAuthFile( authFile );
         start();
      }
      catch( IOException e )
      {
         throw new RuntimeException( e );
      }
   }

   /**
    * The main program for the LightWeightNameService class
    *
    * @param args
    *            The command line arguments
    */
   public static void main( String[] args )
   {
      File authFile = new File( AuthFile.DEFAULT_NAME );
      int port = DEFAULT_PORT;
      String repositoryFileName = "repository.names";

      for( int i = 0; i < args.length; ++i )
      {
         if( args[ i ].equals( "-a" ) && ( i + 1 ) < args.length )
         {
            ++i;
            authFile = new File( args[ i ] );
         }
         else if( args[ i ].equals( "-p" ) && ( i + 1 ) < args.length )
         {
            ++i;
            try
            {
               port = Integer.parseInt( args[ i ] );
            }
            catch( NumberFormatException e )
            {
               port = DEFAULT_PORT;
            }
         }
         else
         {
            repositoryFileName = args[ i ];
         }
      }
      new NameServerApplication( port, authFile, repositoryFileName );
   }

   @Override
   protected void createClientSession( Socket socket )
   {
      ClientSocketProcessor csp = new ClientSocketProcessor( this, socket );
      csp.start();
   }

   @Override
   public synchronized void addToServices( String name, String host, int port ) throws IOException
   {
      super.addToServices( name, host, port );

      try
      {
         store();
      }
      catch ( IOException e )
      {
         LOGGER.severe( "store failed:" );
         e.printStackTrace();
         throw e;
      }
   }

   /**
    * Stores the list of registered (and unexpired) repositories to a local file.
    *
    * @throws IOException when store failed
    */
   private void store() throws IOException
   {
      synchronized( services )
      {
         Writer writer = new OutputStreamWriter( new FileOutputStream( filename ) );
         for ( RepositoryInfo repository: services.values() )
         {
            if( !repository.isExpired() )
            {
               writer.write( "SERVICE" + "\n" );
               writer.write( repository.getRepositoryName() + "\n" );
               writer.write( repository.getHost() + "\n" );
               writer.write( repository.getPort() + "\n" );
            }
         }
         writer.close();
      }
   }

   /**
    * restore from file
    *
    * @throws IOException
    *             when restore failed
    */
   private void restore() throws IOException
   {
      if( new File( filename ).exists() )
      {
         BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( filename ) ) );
         try
         {
            String line;
            while( ( line = reader.readLine() ) != null )
            {
               if( "SERVICE".equals( line ) )
               {
                  String name = reader.readLine();
                  String host = reader.readLine();
                  String port = reader.readLine();
                  if( name != null && host != null && port != null )
                  {
                	  	RepositoryInfo repository = new RepositoryInfo( name, host, Integer.parseInt( port ) );
                     synchronized( services )
                     {
                        services.put( name, repository );
                     }
                  }
                  else
                  {
                     LOGGER.severe( "discarded incomplete dataset on restore!" );
                  }
               }
               else
               {
                  throw new RuntimeException( "wrong file format: " + filename );
               }
            }
         }
         finally
         {
            reader.close();
         }
      }
   }

   /**
    * query host and port of a named service
    *
    * @param nameServerHost host of the name server
    * @param nameServerPort port of the name server
    * @param name           service name of interest
    * @return info containing the information for the named service
    * @throws IOException when open/write/read/close cause errors
    * @throws UnknownResponseException when an unknown response was received
    * @throws RemoteException when an exception occured on server side
    */
   public static RepositoryInfo query( InetAddress nameServerHost,
			int nameServerPort, String name ) throws IOException,
			UnknownResponseException, RemoteException
	{
		TCPConnectionImpl connection = new TCPConnectionImpl( nameServerHost, nameServerPort );
		connection.open();
		try
		{
			connection.send( new QueryRepositoryRequest( name ) );
			Response response = connection.readResponse();

			String data = response.getData();
			String nameserver = "localhost";
			int indexOfColon = data.indexOf( ':' );
			if( indexOfColon > 0 )
			{
				nameserver = data.substring( 0, indexOfColon );
				data = data.substring( indexOfColon + 1 );
			}
			return new RepositoryInfo( name, nameserver, Integer	.parseInt( data ) );
		} 
		finally
		{
			try
			{
				connection.close();
			} 
			catch( PersistencyException e )
			{
            // ignore failure of close
         }
		}
	}

   @Override
   public void checkWritePermission(User user) throws CredentialsException
   {
      // don't check credentials if name service username does not exist
      if ( getUserManager().getUserByName(NAME_SERVICE_USERNAME) != null )
      {
         if (user == null)
         {
            throw new NotAuthenticatedException("No username specified.");
         }
         super.checkWritePermission( user );
      }
   }
}
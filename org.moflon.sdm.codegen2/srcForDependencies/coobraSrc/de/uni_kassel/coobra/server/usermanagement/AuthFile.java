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

package de.uni_kassel.coobra.server.usermanagement;

import de.uni_kassel.coobra.server.DefaultServer;
import de.uni_kassel.coobra.server.usermanagement.User.UserRole;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * AuthFile
 * parses the auth.conf - file
 * 
 * @author manuel
 */
public class AuthFile implements ExportImport
{
   public static final String DEFAULT_NAME = "auth.conf";
   
	private static final String PREFIX_REPOSITORY = "allow ";
	private static final String PREFIX_PASSWORD = "pass ";
	private static final String PREFIX_EMAIL = "email ";
	private static final String PREFIX_USER = "user ";
	private static final String PREFIX_BLOCKED = "blocked";
	private static final String PREFIX_ZONE_ADMIN = "zoneadmin";
	private static final String PREFIX_ADMIN = "admin";

	private static final String PREFIX_HOSTNAME = "hostname ";

	private File authFile;
	private long lastReadAuthFile = 0;

	public AuthFile( File authFile )
	{
      if( authFile == null || !authFile.exists() || !authFile.isFile() )
      {
         throw new IllegalArgumentException( "The AuthFile must point to an existing file." );
      }
      this.authFile = authFile;
	}
   
	/**
	 * Opens auth.conf for reading and adds/ updates {@link User}a and {@link RepositoryACL}s to the {@link UserManagement}.
    * 
    * @param manager the {@link UserManagement} to use.
    * @return true on success. 
	 */
	public boolean read( UserManagement manager )
	{
      if( isDirty() )
      {
         // delegate to #updateUserManagement
         return updateUserManagement( manager );
      }
      return true;
	}

   /**
    * Opens auth.conf for reading and adds/ updates {@link User}a and {@link RepositoryACL}s to the {@link UserManagement}.
    * 
    * @param manager the {@link UserManagement} to use.
    * @return true on success. 
    */
   private boolean updateUserManagement( UserManagement manager )
   {
      User newUser = null;
		BufferedReader fin = null;

		try
		{
			fin = new BufferedReader( new FileReader( authFile ) );
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}

		try
		{
         String line;
         while( ( line = fin.readLine() ) != null )
			{
				line = line.trim();

				if( line.startsWith( PREFIX_USER ) )
				{
					if( newUser != null )
					{ // save user, if new
						manager.insertUser( newUser.getUserName(), newUser );
						newUser = null;
					}
					if( line.length() > PREFIX_USER.length() )
					{
						// this is our new user
						String userName = line.substring( PREFIX_USER.length() );
						// workaround for the guest user
						if( UserManagement.DEFAULT_GUEST.equals( userName ) )
						{
						   newUser = manager.getUserByName( UserManagement.DEFAULT_GUEST );
						}
						else
						{
						   newUser = new User( userName );
						   newUser.setUserRole( UserRole.DEFAULT );
						}
					}
				}
				else if( line.startsWith( PREFIX_PASSWORD )
						&& ( line.length() > PREFIX_PASSWORD.length() ) && newUser != null )
				{
					String pass = line.substring( PREFIX_PASSWORD.length() );
					newUser.setPasswordOrHash( pass );
				}
				else if( line.startsWith( PREFIX_BLOCKED ) && newUser != null )
				{
					newUser.setBlocked( true );
				}
				else if( line.startsWith( PREFIX_ADMIN ) && newUser != null )
				{
					newUser.setUserRole( UserRole.ADMIN );
				}
				else if( line.startsWith( PREFIX_ZONE_ADMIN ) && newUser != null )
				{
					newUser.setUserRole( UserRole.ZONE );
				}
            else if( line.startsWith( PREFIX_EMAIL ) && ( line.length() > PREFIX_EMAIL.length() ) && newUser != null )
            {
               String mail = line.substring( PREFIX_EMAIL.length() );
               newUser.setEMailAddress( mail );
            }
				else if( line.startsWith( PREFIX_REPOSITORY )
						&& ( line.length() > PREFIX_REPOSITORY.length() ) && newUser != null )
				{
					String rep = line.substring( PREFIX_REPOSITORY.length() );
					if( !newUser.hasAccessTo( rep ) )
					{
						// check, if the repository is already registered
						RepositoryACL newRep;
						if( manager.getRepository( rep ) == null )
						{
							manager.addNewRepository( rep );
						}
						newRep = manager.getRepository( rep );

						// add the repositoriy to the user's map
						newUser.getRepositories().put( rep, newRep );
						// add the user to the repository's map
						newRep.grantAccessToRepository( newUser.getUserName(), newUser );
					}
				}
            else if( line.startsWith( PREFIX_HOSTNAME ) )
            {
               String hostname = line.substring(PREFIX_HOSTNAME.length()).trim();
               if (hostname.length() > 0)
               {
                  manager.setHostname(hostname);
               }
            }
			}
			if( newUser != null )
			{
				manager.insertUser( newUser.getUserName(), newUser );
			}
			fin.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
      resetAuthFileDate();
		return true;
   }

   /**
    * Stores the current {@link UserManagement} to the {@link #authFile}.
    * 
    * @param manager the {@link UserManagement} to store.
    */
	public boolean write( UserManagement manager )
	{
		try
		{
			BufferedWriter out = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( authFile ) ) );

			if( manager.getHostname() != null ) {
			   out.write( PREFIX_HOSTNAME + manager.getHostname() + "\n" );
			}
			
			for ( User user: manager.getUserValues() )
			{
				out.write( "\n" + PREFIX_USER + user.getUserName() + "\n" );
				out.write( PREFIX_PASSWORD + user.getPasswordHash() + "\n" );
				if( user.getUserRole().equals( UserRole.ZONE ) )
				{
					out.write( PREFIX_ZONE_ADMIN + "\n" );
				}
            if( user.getEMailAddress() != null && user.getEMailAddress().length() > 0 )
            {
               out.write( PREFIX_EMAIL + user.getEMailAddress() + "\n" );
            }
				if( user.getUserRole().equals( UserRole.ADMIN ) )
				{
					out.write( PREFIX_ADMIN + "\n" );
				}
				if( user.isBlocked() ) 
				{
					out.write( PREFIX_BLOCKED + "\n" );
				}

				for ( RepositoryACL repository: user.getRepositories().values() )
				{
					out.write( PREFIX_REPOSITORY + repository.getName() + "\n" );
				}
			}
			out.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

   /**
    * @return true, if the {@link #authFile} has been modified in the filesystem since it
    * has been read the last time.
    */
	public boolean isDirty()
	{
		return lastReadAuthFile < authFile.lastModified();
	}

	private void resetAuthFileDate()
	{
		lastReadAuthFile = authFile.lastModified();
	}
   
   /**
    * Creates an AuthFile containing only a NameServiceRegistrant-User.
    * 
    * @param authFile The AuthFile to create.
    * @throws IOException on error creating the file.
    */
   public static void createNewAuthFile( File authFile ) throws IOException
   {
      if( authFile == null )
      {
         throw new IllegalArgumentException( "The AuthFile must not be null." );
      }
      if( authFile.exists() )
      {
         throw new IllegalArgumentException( "The AuthFile must not exist." );
      }
      if( !authFile.createNewFile() ) return;
      // TODO generate a random password for the NameServiceRegistrant user
      String authFileText = "user " + DefaultServer.NAME_SERVICE_USERNAME + "\npass #acb098acb567acb098acb596acbd08ac";
      BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter( authFile ) );
      bufferedWriter.write( authFileText );
      bufferedWriter.close();
   }

   /**
    * @return the {@link File#getAbsolutePath()} of the storage file.
    */
   public String getLocation()
   {
      return authFile.getAbsolutePath();
   }
}

/*
 * $Log$
 * Revision 1.18  2009/01/06 16:56:01  mbork
 * bugfix for coobra server: if running on the same machine as the nameservice, remember correct hostname
 *
 * Revision 1.17  2008/10/23 14:38:31  cschneid
 * introduced binary persistency modules
 *
 * Revision 1.16  2007/12/17 14:59:43  mbork
 * usermanagement: implemented guest stuff
 *
 * Revision 1.15  2007/12/14 15:56:33  mbork
 * simplified user management and added the email address to the user's data
 *
 * Revision 1.14  2007/04/12 20:05:49  mbork
 * isDirty with public accessor
 *
 * Revision 1.13  2007/04/12 14:39:30  mbork
 * UserManagement now supports external changes to the file in the filesystem.
 *
 * Revision 1.12  2007/02/22 17:50:58  mbork
 * removed ß from password
 */

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

import de.uni_kassel.coobra.server.usermanagement.User.UserRole;

import java.util.Collection;
import java.util.Observable;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author manuel.bork@uni-kassel.de
 * @version $Revision$
 */
public class UserManagement extends Observable
{
   /**
    * The name of the default guest user.
    */
	public static final String DEFAULT_GUEST = "guest";

   /**
	 * SortedMap of all known users 
	 */
	private SortedMap<String, User> myUsers;

	private SortedMap<String, RepositoryACL> repositories;
	
	/**
	 * This server's hostname.
	 */
	private String hostname;

   public UserManagement()
	{
      myUsers = new TreeMap<String, User>();
      repositories = new TreeMap<String, RepositoryACL>();

      // create and add guest user
      User guest = new User( DEFAULT_GUEST, DEFAULT_GUEST, UserRole.DEFAULT );
      guest.setGuest( true );
      myUsers.put( DEFAULT_GUEST, guest );
   }
	
	/**
	 * revokes a user's access from _all_ repositories
	 * 
	 * @param userName
	 */
	public void revokeUsersAccessFromAll( String userName )
	{
		for( RepositoryACL repository: repositories.values() )
		{
			repository.revokeAccessFromRepository( userName );
		}
	}

	public void addNewRepository( String name )
	{
		repositories.put( name, new RepositoryACL( name ) );
		setChanged();
		notifyObservers( name );
	}

	public RepositoryACL getRepository( String name )
	{
		return repositories.get( name );
	}

	public void removeRepository( String name )
	{
		repositories.remove( name );
		notifyObservers( name );
	}

	/**
	 * @param userName
	 * @param passwd
	 * @return an user identified by username and password
	 */
	public User getUser( String userName, String passwd )
	{
		if( myUsers.containsKey( userName ) )
		{
			User tmpUser = myUsers.get( userName );
			if( tmpUser.getPasswordHash().equals( passwd ) ) return tmpUser;
		}
		return null;
	}

	/**
	 * revokes access of all users from the given repository
	 * @param repName
	 */
	public void removeRepositoryFromAllUsers( String repName )
	{
		for ( User user: myUsers.values() )
		{
			user.getRepositories().remove( repName );
		}
	}

	public User getUserByName( String userName )
	{
		return myUsers.get( userName );
	}

	public boolean insertUser( String userName, User newUser )
	{
	   // check if the user already exists
	   if( myUsers.containsKey( userName ) ) return false;
	   
	   // verify that its not the guest user
	   if( DEFAULT_GUEST.equals( userName ) ) return false;
	   
	   // check integrity
	   if( !userName.equals( newUser.getUserName() ) ) return false;
	   
	   // insert new user
		return ( myUsers.put( userName, newUser ) == null );
	}

	public Set<String> getUsersKeySet()
	{
		return myUsers.keySet();
	}

	public Collection<User> getUserValues()
	{
		return myUsers.values();
	}

	/**
	 * @return true, if the user management is configured
	 */
	public boolean isConfigured()
	{
	   switch( myUsers.size() )
      {
      case 0:
         // there are no users configured (wont happen, as a guest user is added per default)
         return false;
      case 1:
         if( getUserByName( DEFAULT_GUEST ) != null )
         {
            // only the guest user exists: no other users are configured.
            return false;
         }
      default:
         return true;
      }
	}

	public void deleteUser( String userName )
	{
		for( RepositoryACL repository: myUsers.get( userName ).getRepositories().values() )
		{
			repository.revokeAccessFromRepository( userName );
		}
		myUsers.remove( userName );
	}
   
   public String getHostname()
   {
      return hostname;
   }

   public void setHostname(String hostname)
   {
      this.hostname = hostname;
   }
}

/*
 * $Log$
 * Revision 1.6  2009/01/06 16:56:01  mbork
 * bugfix for coobra server: if running on the same machine as the nameservice, remember correct hostname
 *
 * Revision 1.5  2008/10/23 14:38:31  cschneid
 * introduced binary persistency modules
 *
 * Revision 1.4  2007/12/17 14:59:43  mbork
 * usermanagement: implemented guest stuff
 *
 * Revision 1.3  2007/12/14 15:56:33  mbork
 * simplified user management and added the email address to the user's data
 *
 * Revision 1.2  2006/12/19 11:46:03  cschneid
 * header updated
 *
 * Revision 1.1  2006/04/26 15:51:04  mbork
 * - starting of an unnamed repositoryserver
 * - close connection after nameservice-info
 * - user management changed, added UserManagement class, separated RepositoryInfo and RepositoryACL
 * - create auto.conf only if storing information is requested
 *
 * attention: some bugs left :-/
 *
 */
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

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * the user-class
 * 
 * @author manuel bork
 * @version
 */
public class User
{
	/**
	 *  the user's name 
	 */
	private String userName = null;

	/**
	 *  the user's password
	 */
	private String passwordHash = null;
	
	/**
	 * the user's role.
	 */
	private UserRole userRole = UserRole.NONE;

	/**
	 * The user's mail address.
	 */
	private String eMailAddress;
	
	/**
	 * Indicates if the user is blocked. 
	 */
	private boolean blocked = false;
	
	/**
	 * Indicates if this user is a guest user: Guests may only read repositories.
	 */
	private boolean guest = false;

	/**
	 *  SortedMap of all repositories the user has access to 
	 */
	private SortedMap<String, RepositoryACL> repositories;
	
	/**
	 * default constructor
	 * 
	 * @param name
	 */
	public User( String name )
	{
		this.userName = name;
		repositories = new TreeMap<String, RepositoryACL>();
	}

	/**
	 * @param name
	 * @param passwordOrHash passwords not starting with # are clear-text passwords
	 * @param adminState
	 */
	public User( String name, String passwordOrHash, UserRole role )
	{
		this( name );
		setPasswordOrHash( passwordOrHash );
		this.userRole = role;
	}

	/**
	 * @param name
	 * @return if a use has access to the given repository
	 */
	public boolean hasAccessTo( String name )
	{
		return repositories.get( name ) != null;
	}

	/**
	 * revokes this user's access to a given repository
	 * 
	 * @param rep
	 */
	public void removeRepositoryFromUser( String rep )
	{
		repositories.remove( rep );
	}

	/**
	 * grants this user access to a given repository
	 * 
	 * @param repositoryName
	 * @param myMain
	 */
	public void addRepositoryToUser( String repositoryName, RepositoryACL repository )
	{
		repositories.put( repositoryName, repository );
	}

	/**
	 * Switches the blocked flag of the user.
	 */
	public boolean switchBlock() throws IOException
	{
		if( this.blocked )
		{
			this.blocked = false;
			return false;
		}
		else
		{
			this.blocked = true;
			return true;
		}
	}

	// getters/ setters
	public SortedMap<String, RepositoryACL> getRepositories()
	{
		return repositories;
	}

	public String getPasswordHash()
	{
		return passwordHash;
	}

	public String getUserName()
	{
		return userName;
	}

	public UserRole getUserRole()
	{
		return this.userRole;
	}

	public boolean isBlocked()
	{
		return blocked;
	}

	/**
	 * @param passwordOrHash passwords not starting with # are clear-text passwords
	 * @see Md5#passwordHash(String)
	 */
	public void setPasswordOrHash( String passwordOrHash )
	{
		if( !passwordOrHash.startsWith( "#" ) ) passwordOrHash = Md5.passwordHash( passwordOrHash );
		this.passwordHash = passwordOrHash;
	}

	public void setUserRole( UserRole userRole )
	{
		this.userRole = userRole;
	}

	public void setBlocked( boolean blocked )
	{
		this.blocked = blocked;
	}

	/**
	 *  The state of the current user. 
	 */
	public enum UserRole
	{
		NONE, DEFAULT, ZONE, ADMIN;
	}

   /**
    * @return the eMailAddress
    */
   public String getEMailAddress()
   {
      return this.eMailAddress;
   }

   /**
    * @param mailAddress the eMailAddress to set
    */
   public void setEMailAddress( String mailAddress )
   {
      this.eMailAddress = mailAddress;
   }

   /**
    * @return true, if this user is a guest: Guests may only read repositories.
    */
   public boolean isGuest()
   {
      return this.guest;
   }

   /**
    * Sets the 'guest' state: Guests may only read repositories.
    * 
    * @param guest the guest to set
    */
   public void setGuest( boolean guest )
   {
      this.guest = guest;
   };
   
   /**
    * Switches the 'guest' state: Guests may only read repositories.
    */
   public void switchGuest()
   {
      if( isGuest() )
      {
         setGuest( false );
      }
      else
      {
         setGuest( true );
      }
   };
}
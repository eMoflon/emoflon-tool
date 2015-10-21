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

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author manuel bork
 */
public class RepositoryACL
{
	/**
	 *  this repository's name 
	 */
	private String name;
	
	/**
	 *  Map of all users that have access to this repository
	 */
	private SortedMap<String, User> users = new TreeMap<String, User>();

	public RepositoryACL( String name )
	{
		this.name = name;
	}
	
	public void grantAccessToRepository( String userName, User newUser )
	{
		users.put( userName, newUser );
	}

	public void revokeAccessFromRepository( String userName )
	{
		users.remove( userName );
	}
	
	/**
	 * @return Returns this repository's name.
	 */
	public String getName()
	{
		return name;
	}

	public Iterator<String> getUserKeyIterator()
	{
		return users.keySet().iterator();
	}
}

/*
 * $Log$
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
 * Revision 1.2  2006/04/06 13:22:13  mbork
 * merged development with head
 *
 * Revision 1.1.2.2  2006/02/17 14:55:47  mbork
 * name service starting / stopping
 * repository starting/ stopping
 * name service <-> repository interaction
 * coobrasc integration
 *
 * Revision 1.1.2.1  2006/02/15 22:52:30  mbork
 * user management added
 *
 */
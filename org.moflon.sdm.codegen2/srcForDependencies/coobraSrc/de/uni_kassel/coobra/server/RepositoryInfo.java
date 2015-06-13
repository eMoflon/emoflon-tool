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

public class RepositoryInfo
{
	/**
	 * time after which this repository isnt valid any longer
	 */
	public static final long TIME_TO_REANNOUNCE = 24*60*60*1000; // 24h

	private String repositoryName;
	private String host;
	private int port;
	
	/**
	 * time when this repository server announced himself for the last time
	 */
	private long announceTime;
	
	public RepositoryInfo( String name, String host, int port )
	{
		this.host = host;
		this.port = port;
      this.repositoryName = name;
      resetAnnouncementTime();
	}
	
	public void resetAnnouncementTime()
	{
		announceTime = System.currentTimeMillis();
	}
	
	public boolean isExpired()
	{
		return announceTime + TIME_TO_REANNOUNCE < System.currentTimeMillis();
	}
	
	
	/**
	 * @return Returns the repositoryName.
	 */
	public String getRepositoryName()
	{
		return repositoryName;
	}

	/**
	 * @return Returns the repositoryHost.
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * @return Returns the repositoryPort.
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * @param host The repositoryHost to set.
	 */
	public void setHost( String host )
	{
		this.host = host;
	}

	/**
	 * @param name The repositoryName to set.
	 */
	public void setRepositoryName( String name )
	{
		this.repositoryName = name;
	}

	/**
	 * @param port The repositoryPort to set.
	 */
	public void setPort( int port )
	{
		this.port = port;
	}
}

/*
 * $Log$
 * Revision 1.3  2006/12/19 11:46:01  cschneid
 * header updated
 *
 * Revision 1.2  2006/04/27 12:59:14  cschneid
 * destroy object changes operationalized
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
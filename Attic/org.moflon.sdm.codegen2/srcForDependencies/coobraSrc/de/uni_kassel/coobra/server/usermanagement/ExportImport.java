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

/**
 * Export/Import interface
 * this interface feeds allUsers and Allrepositories
 * 
 * @author manuel
 * @version
 */
public interface ExportImport
{
	public abstract boolean read( UserManagement manager );
	public abstract boolean write( UserManagement manager );
}

/*
 * $Log$
 * Revision 1.4  2006/12/19 11:46:03  cschneid
 * header updated
 *
 * Revision 1.3  2006/04/26 15:51:04  mbork
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
 * Revision 1.1.2.1  2006/02/15 22:52:34  mbork
 * user management added
 *
*/
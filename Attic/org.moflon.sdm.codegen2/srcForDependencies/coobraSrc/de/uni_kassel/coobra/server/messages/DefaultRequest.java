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

package de.uni_kassel.coobra.server.messages;

import de.uni_kassel.coobra.server.AbstractClientSession;
import de.uni_kassel.coobra.server.ClientSession;

/**
 * @author mbork
 * @version $Revision$
 */
public abstract class DefaultRequest extends Request
{

	/* (non-Javadoc)
	 * @see de.uni_kassel.coobra.server.messages.Request#check(de.uni_kassel.coobra.server.AbstractClientSession)
	 */
	@Override
	protected void check( AbstractClientSession session ) throws Exception
	{
		if( session instanceof ClientSession )
		{
			check( (ClientSession)session );
		}
	}
	
	protected abstract void check( ClientSession session ) throws Exception;
	

	/* (non-Javadoc)
	 * @see de.uni_kassel.coobra.server.messages.Request#execute(de.uni_kassel.coobra.server.AbstractClientSession)
	 */
	@Override
	protected void execute(AbstractClientSession session) throws Exception
	{
		if (session instanceof ClientSession)
		{
         ClientSession clientSession = (ClientSession) session;
         clientSession.getPersistencyModule().reset();
         execute(clientSession);
		}
		else
		{
			throw new UnsupportedOperationException( "Unsupported Request" );
		}
	}

	protected abstract void execute( ClientSession session ) throws Exception;
}


/*
 * $Log$
 * Revision 1.4  2007/01/31 08:53:58  cschneid
 * deferred commit working (commit after update), modifier moved from Change to TransactionEntry
 *
 * Revision 1.3  2006/12/19 11:46:02  cschneid
 * header updated
 *
 * Revision 1.2  2006/04/06 13:22:13  mbork
 * merged development with head
 *
 * Revision 1.1.2.2  2006/04/05 13:39:30  mbork
 * user authentication integrated
 *
 * Revision 1.1.2.1  2006/02/15 16:32:24  mbork
 * nameservice
 *
 */

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

import de.uni_kassel.coobra.persistency.io.NonClosableInputStream;
import de.uni_kassel.coobra.server.messages.Request;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

/**
 * @author mbork
 * @version $Revision$
 */
public abstract class AbstractClientSession
{
	private final Socket socket;
	
	public AbstractClientSession( Socket socket )
	{
		this.socket = socket;
	}

	public abstract NameServer getServer();

	public Socket getSocket()
	{
		return socket;
	}

	protected void start()
	{
		new ProcessThread().start();
	}

	protected Request readCommand() throws IOException, ClassNotFoundException
	{
		return (Request) new ObjectInputStream(new NonClosableInputStream(
				getSocket().getInputStream(), true)).readObject();
	}

	private class ProcessThread extends Thread
	{
		/**
		 * @see Thread#run()
		 */
		@Override
		public void run()
		{
			try
			{
				while (!socket.isClosed())
				{
					try
					{
						readCommand().perform( AbstractClientSession.this );
					} catch ( StreamCorruptedException e ) {
                  e.printStackTrace();
                  TCPConnectionModule.NullRequest nullRequest = new TCPConnectionModule.NullRequest();
                  nullRequest.respondCheckFailed( e, AbstractClientSession.this, "invalid command format" );
               } catch ( ClassNotFoundException e ) {
                  e.printStackTrace();
                  TCPConnectionModule.NullRequest nullRequest = new TCPConnectionModule.NullRequest();
                  nullRequest.respondCheckFailed( e, AbstractClientSession.this, "invalid command" );
               }
					catch (EOFException e)
					{
						// e.printStackTrace();
						socket.close();
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				try
				{
					socket.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
}

/*
 * $Log$
 * Revision 1.5  2008/10/23 14:38:20  cschneid
 * introduced binary persistency modules
 *
 * Revision 1.4  2006/12/19 11:46:01  cschneid
 * header updated
 *
 * Revision 1.3  2006/04/07 08:54:44  cschneid
 * fixed non-auth server tests
 *
 * Revision 1.2  2006/04/06 13:22:11  mbork
 * merged development with head
 *
 * Revision 1.1.2.1  2006/02/15 16:32:22  mbork
 * nameservice
 *
 */

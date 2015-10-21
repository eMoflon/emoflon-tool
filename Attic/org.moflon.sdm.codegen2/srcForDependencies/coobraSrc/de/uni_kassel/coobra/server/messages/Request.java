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

import de.uni_kassel.coobra.persistency.io.NonClosableOutputStream;
import de.uni_kassel.coobra.server.AbstractClientSession;
import de.uni_kassel.coobra.server.DefaultServerModule;
import de.uni_kassel.coobra.server.errors.CredentialsException;
import de.uni_kassel.coobra.server.errors.UnknownUserException;
import de.uni_kassel.coobra.server.errors.WrongPasswordException;
import de.uni_kassel.coobra.server.usermanagement.Md5;
import de.uni_kassel.coobra.server.usermanagement.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 31.03.2005, 15:10:15
 */
public abstract class Request implements Serializable
{
   private static final long serialVersionUID = 1;
   private long clientVersion = DefaultServerModule.VERSION;

   private boolean responseSent;

   private static long nextRequestSequenceNumber = 0;
   private long requestSequenceNumber = nextRequestSequenceNumber++;

   /**
    * username of the user requesting this request
    */
   private String userName;
   /**
    * password of the user requesting this request
    */
   private String passwordHash;

   private transient User user;
   
   public long getRequestSequenceNumber()
   {
      return requestSequenceNumber;
   }

   public final void perform( AbstractClientSession session )
	{
		try
		{
			responseSent = false;
			if( clientVersion != DefaultServerModule.VERSION )
			{
				// todo: better exception type
				respondCheckFailed(
						new Exception(
								"Incompatible client version - please obtain a matching library/application version."
										+ " ( Server: v"
										+ DefaultServerModule.VERSION
										+ " Client: v" + clientVersion + ")" ),
						session, null );
				return;
			}

			try
			{
				checkAuthentication( session );
			} 
			catch( CredentialsException e )
			{
				respondCheckFailed( e, session, null );
				return;
			}
			try
			{
				check( session );
			} 
			catch( CredentialsException e )
			{
            e.printStackTrace();
            respondCheckFailed( e, session, null );
            return;
         }
			catch( Throwable e )
			{
				e.printStackTrace();
				respondCheckFailed( e, session, null );
            return;
         }
			try
			{
            execute( session );
			} 
			catch( Throwable e )
			{
				e.printStackTrace();
				respondOperationFailed( e, session );
			}
			if( !responseSent )
			{
				respondSuccess( session );
			}
		} 
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}

   private void checkAuthentication(AbstractClientSession session) throws CredentialsException
   {
      session.getServer().updateUserManagement();
      if( !session.getServer().getUserManager().isConfigured() )
      {
         return; // FIXME: allow everything when auth file was not found?!?!
      }
      if( userName == null || userName.length() == 0 )
      {
         userName = null;
         passwordHash = null;
         return;
      }

		user = session.getServer().getUserManager().getUserByName( userName );
		if( user == null )
		{
			throw new UnknownUserException( userName );
		}
		if( !user.getPasswordHash().equals( passwordHash ) )
		{
			throw new WrongPasswordException();
		}
		setPassword( null );
	}

   private void respondOperationFailed(Throwable e, AbstractClientSession session) throws IOException
   {
      Response response = new Response("Operation failed: " + e.toString(), getRequestSequenceNumber());
      response.setThrowable( e );
      send(response, session);
   }

   public void respondCheckFailed( Throwable e, AbstractClientSession session, String message ) throws IOException
   {
      Response response = new Response("Check failed: " + e.toString(), getRequestSequenceNumber());
      response.setThrowable( e );
      response.setMessage( message );
      send(response, session);
   }

   private void respondSuccess(AbstractClientSession session) throws IOException
   {
      send(new Response(getRequestSequenceNumber()), session);
   }

   protected void send(Response response, AbstractClientSession session) throws IOException
   {
      if (responseSent)
      {
         throw new IllegalStateException("Response already sent!");
      }
      responseSent = true;
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(new NonClosableOutputStream(session.getSocket().getOutputStream()));
      objectOutputStream.writeObject(response);
      objectOutputStream.flush();
   }

   protected abstract void check(AbstractClientSession session) throws Exception;

   protected abstract void execute(AbstractClientSession session) throws Exception;
   
   /**
    * @param passwordOrHash passwords not starting with # are clear-text passwords
    * @see Md5#passwordHash(String)
    */ 
   public void setPassword( String passwordOrHash )
   {
      if( passwordOrHash != null && !passwordOrHash.startsWith( "#" ) ) passwordOrHash = Md5.passwordHash( passwordOrHash );
      this.passwordHash = passwordOrHash;
   }
	
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName( String userName )
	{
		this.userName = userName;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * @return Returns the user.
	 */
	public User getUser()
	{
		return user;
	}
}

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

import java.io.Serializable;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 31.03.2005, 15:36:21
 */
public class Response implements Serializable
{
   private static final long serialVersionUID = 1;

   private String data;
   private Throwable throwable;

   public Response( long requestSequenceNumber )
   {
      this( null, requestSequenceNumber );
   }

   public Response( String data, long requestSequenceNumber )
   {
      this.data = data;
      this.requestSequenceNumber = requestSequenceNumber;
   }

   public String getData()
   {
      return data;
   }
   
   /**
    * @return a string representation of the object.
    */
   @Override
   public String toString()
   {
      if ( getThrowable() == null )
      {
         return "Response: OK - Data: " + getData();
      }
      else
      {
         return "Response: FAILURE - " + getThrowable().getMessage();
      }
   }

   private long requestSequenceNumber;

   public long getRequestSequenceNumber()
   {
      return requestSequenceNumber;
   }

	/**
	 * @return Returns the throwable.
	 */
	public Throwable getThrowable()
	{
		return throwable;
	}
	
	/**
	 * @param throwable The throwable to set.
	 */
	public void setThrowable( Throwable throwable )
	{
		this.throwable = throwable;
	}

   private String message;

   public String getMessage()
   {
      return message;
   }

   public void setMessage( String message )
   {
      this.message = message;
   }
}

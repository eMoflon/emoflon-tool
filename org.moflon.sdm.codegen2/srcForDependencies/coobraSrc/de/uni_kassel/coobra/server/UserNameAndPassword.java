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

/**
 * @author
 */
public class UserNameAndPassword
{
   public UserNameAndPassword()
   {
   }

   public UserNameAndPassword(String userName, String password)
   {
      this();
      setUserName( userName );
      setPassword( password );
   }

   /**
    * getter for field userName
    *
    * @return current value of field userName
    */
   public String getUserName()
   {
      return this.userName;
   }

   /**
    * store the value for field userName
    */
   private String userName;

   /**
    * setter for field userName
    *
    * @param value new value
    */
   public void setUserName(final String value)
   {
      this.userName = value;
   }


   /**
    * getter for field password
    *
    * @return current value of field password
    */
   public String getPassword()
   {
      return this.password;
   }

   /**
    * store the value for field password
    */
   private String password;

   /**
    * setter for field password
    *
    * @param value new value
    */
   public void setPassword(final String value)
   {
      this.password = value;
   }
}

/*
 * $Log$
 * Revision 1.2  2006/12/19 11:46:01  cschneid
 * header updated
 *
 * Revision 1.1  2006/05/23 09:48:40  cschneid
 * credentials and authentication quick fix
 *
 */


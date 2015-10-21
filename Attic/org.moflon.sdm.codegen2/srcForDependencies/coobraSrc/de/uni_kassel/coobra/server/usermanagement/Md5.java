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

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author christian.schneider@uni-kassel.de
 * @version $Revision$ $Date$
 * @created 08.06.2004, 09:37:33
 */
public class Md5
{

   /**
    * for conmverting to HEX
    */
   private final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


   /**
    * @param password clear text password
    * @return a hash for the password
    */
   public static String passwordHash( String password )
   {
      try
      {
         MessageDigest md = MessageDigest.getInstance( "MD5" );
         java.nio.ByteBuffer bf = Charset.forName( "UTF-8" ).encode( password );
         md.update( bf.array() );
         byte[] digest = md.digest();
         StringBuffer digestString = new StringBuffer( digest.length + 1 );
         digestString.append( '#' );
         for (byte b : digest)
         {
            digestString.append(digits[(b >> 4) & 0xF]);
            digestString.append(digits[b & 0xF]);
         }
         return digestString.toString();
      } catch ( NoSuchAlgorithmException e )
      {
         e.printStackTrace();
         return null;
      }
   }
}

/*
 * $Log$
 * Revision 1.4  2006/12/19 11:46:03  cschneid
 * header updated
 *
 * Revision 1.3  2006/11/14 15:09:24  cschneid
 * persistency filter, some fixes
 *
 * Revision 1.2  2006/04/06 13:22:13  mbork
 * merged development with head
 *
 * Revision 1.1.2.1  2006/02/15 22:52:29  mbork
 * user management added
 *
 * Revision 1.3  2005/06/10 12:26:13  mbork
 * log tag korrigiert
 *
*/
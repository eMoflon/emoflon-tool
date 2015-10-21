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

package de.uni_kassel.coobra.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

/**
 * 
 */
public class SimpleMerge
{
   public void merge(
         BufferedReader localData,
         BufferedReader originalData,
         BufferedReader remoteData,
         Writer result,
         String filename,
         String version ) throws IOException
   {
      String local;
      String original;
      String remote;
      boolean unchanged = true;
      boolean first = true;
      do
      {
         local = localData.readLine();
         remote = remoteData.readLine();
         original = originalData.readLine();
         boolean localUnchanged = local != null && local.equals( original );
         boolean remoteUnchanged = remote != null && remote.equals( original );
         if ( localUnchanged && remoteUnchanged )
         {
            if ( !first )
            {
               result.write( "\n" );
            }
            first = false;
            result.write( original );
         }
         else
         {
            //first difference
            unchanged = false;
         }
      } while ( unchanged );

      if ( !first )
      {
         result.write( "\n" );
      }
      result.write( "<<<<<<< " );
      result.write( filename );

      while ( local != null )
      {
         result.write( "\n" );
         result.write( local );
         local = localData.readLine();
      }

      result.write( "=======" );

      while ( remote != null )
      {
         result.write( "\n" );
         result.write( remote );
         remote = remoteData.readLine();
      }

      result.write( ">>>>>>> " );
      result.write( version );
   }

}

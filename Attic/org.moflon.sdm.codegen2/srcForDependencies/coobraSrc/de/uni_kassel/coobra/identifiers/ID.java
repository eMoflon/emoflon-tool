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

package de.uni_kassel.coobra.identifiers;

import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.io.BinaryOutputStream;

import java.io.IOException;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 16.02.2005, 10:04:16
 */
public final class ID
{
   private long index;

   private String prefix;

   ID( String prefix, long index, ClassHandler classHandler )
   {
      if ( index < 0 || prefix == null )
      {
         throw new IllegalArgumentException();
      }
      this.index = index;
      this.prefix = prefix;
      this.classHandler = classHandler;
   }

   static long parseIndex( String indexString, int offset )
   {
      int index = 0;
      for ( int i = indexString.length() - 1; i >= offset; i-- )
      {
         char c = indexString.charAt( i );
         index = ( index << 4 ) + (int) c;
         if ( c >= 'A' && c <= 'F' )
         {
            index -= (int) 'A' - 10;
         }
         else if ( c >= '0' && c <= '9' )
         {
            index -= (int) '0';
         }
         else
         {
            //invalid char
            return -1;
         }
      }
      return index;
   }

   /**
    * character used for separation of prefixes and indexes.
    */
   public static final char ID_SEPARATION_CHARACTER = '#';

   public static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

   /**
    * @return the textual representation of this ID
    */
   @Override
   public String toString()
   {
      StringBuffer buf = new StringBuffer( prefix.length() + 10 );
      try
      {
         writeTo( buf );
      } catch ( IOException e )
      {
         //does not happen for StringBuffer
         throw new RuntimeException( e );
      }
      return buf.toString();
   }

   /**
    * Indicates whether some other object is "equal to" this one.
    *
    * @param obj the reference object with which to compare.
    * @return <code>true</code> if this object is the same as the obj
    *         argument; <code>false</code> otherwise.
    * @see Object#equals(Object)
    * @see #hashCode()
    */
   @Override
   public boolean equals( Object obj )
   {
      if ( obj == this ) {
         return true;
      }
      else if ( obj instanceof ID )
      {
         ID otherID = (ID) obj;
         if ( otherID.index != index )
         {
            return false;
         }
         return prefix.equals( otherID.prefix );
      }
      else if ( obj instanceof String )
      {
         String str = (String) obj;
         if ( str.startsWith( prefix ) )
         {
            if ( str.length() > prefix.length() && str.charAt( prefix.length() ) == ID_SEPARATION_CHARACTER )
            {
               return parseIndex( str, prefix.length() + 1 ) == index;
            }
            else
            {
               return false;
            }
         }
         else
         {
            return false;
         }
      }
      else
      {
         return false;
      }
   }

   private int hash;

   /**
    * Returns a hash code value for the object.
    *
    * @return a hash code value for this object.
    * @see Object#equals(Object)
    * @see Object#hashCode()
    */
   @Override
   public int hashCode()
   {
      int h = hash;
      if ( h == 0 )
      {
         h = prefix.hashCode();

         h = 31 * h + (int) ID_SEPARATION_CHARACTER;

         long index = this.index;
         while ( index > 0 )
         {
            h = 31 * h + (int) HEX_CHARS[(int) ( index & 0xF )];
            index = index >> 4;
         }
         hash = h;
      }
      return h;
   }

   private ClassHandler classHandler;

   public ClassHandler getClassHandler()
   {
      return classHandler;
   }
   
   void setClassHandler( ClassHandler classHandler )
   {
      this.classHandler = classHandler;
   }

   /**
    * @return prefix of this ID
    */
   public String getPrefix()
   {
      return prefix;
   }

   /**
    * Write the string representation of the ID to an Appendable.
    * @param out where to write to
    * @throws IOException if append causes such an Exception
    */
   public void writeTo( Appendable out ) throws IOException
   {
      writeIDString(out, prefix, this.index);
   }

   static String asIDString( String prefix, long index )
   {
      StringBuilder builder = new StringBuilder();
      try
      {
         writeIDString( builder, prefix, index );
         return builder.toString();
      } catch (IOException e)
      {
         //cannot happen
         throw new IllegalStateException(e);
      }
   }

   private static void writeIDString(Appendable out, final String prefix, long index)
         throws IOException
   {
      out.append(prefix);
      out.append( ID_SEPARATION_CHARACTER );
      while ( index > 0 )
      {
         out.append( HEX_CHARS[(int) ( index & 0xF )] );
         index = index >> 4;
      }
   }

   public void writeTo(BinaryOutputStream out) throws IOException
   {
      out.writeRepeatingString( prefix );
      out.writeLong( index );
   }

   long getIndex()
   {
      return index;
   }
}

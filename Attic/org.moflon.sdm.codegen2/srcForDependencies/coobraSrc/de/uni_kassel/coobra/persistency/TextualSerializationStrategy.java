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

package de.uni_kassel.coobra.persistency;

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.error.SerializationNotSupportedException;
import de.uni_kassel.features.reflect.DefaultPrimitiveClassHandler;

import java.io.IOException;
import java.io.Writer;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 14.09.2004, 10:03:52
 */
abstract class TextualSerializationStrategy
{
   public abstract Repository getRepository();

   protected void serialize( Object object, String expectedClassName, Writer out ) throws ClassNotFoundException, UnsupportedOperationException, IOException
   {
      if ( object == getRepository() )
      {
         out.write( "i:repository" );
      }
      else if ( object != null )
      {
         ID id = getRepository().getIdentifierModule().getID( object );
         if ( id != null )
         {
            out.write( "i:" );
            id.writeTo( out );
         }
         else
         {
            out.write( "v:" );
            if ( object instanceof ClassHandler )
            {
               ClassHandler classHandler = (ClassHandler) object;
               out.append("class:");
               out.append( classHandler.getName() );
            }
            else
            {
               final ClassHandler classHandler = getRepository().getFeatureAccessModule().getClassHandler(object);
               final String partnerClassName;
               if ( classHandler instanceof DefaultPrimitiveClassHandler )
               {
                  DefaultPrimitiveClassHandler primitiveClassHandler = (DefaultPrimitiveClassHandler) classHandler;
                  partnerClassName = primitiveClassHandler.getPartner().getName();
               } else
               {
                  partnerClassName = null;
               }
               final String className = classHandler.getName();
               if (className != null // null can happen with NonResolvingClassHandler
                     && !className.equals(expectedClassName)
                     && (partnerClassName == null || !partnerClassName.equals(expectedClassName) ))
               {
                  out.append( className );
               }
               out.append( ":" );
               try
               {
                  classHandler.serialize( object, out );
               } catch (SerializationNotSupportedException e)
               {
                  throw new SerializationNotSupportedException( "Error attempting to serialize an instance of class "
                        + e.getUnsupportedClass().getName() + " as no ID for it was present. It is neither a " +
                        "properly registed model object nor an instance of a serializable class." ,
                        e.getUnsupportedClass(), e );
               }
            }
         }
      }
      else
      {
         out.write( ( char[] ) null );
      }
   }

   protected void serialize( Object changeAware, boolean allowNewId, Writer out ) throws IOException
   {
      if ( changeAware != null )
      {
         IdentifierModule identifierModule = getRepository().getIdentifierModule();
         ID id = identifierModule.getID( changeAware );
         if ( id == null )
         {
            if ( allowNewId )
            {
               id = identifierModule.newID( changeAware );
            }
            else
            {
               throw new PersistencyException( "Object should already have an ID: " + changeAware );
            }
         }
         out.write( "i:" );
         id.writeTo( out );
      }
      else
      {
         out.write( (char[]) null );
      }
   }

   protected Object getAffectedObject( char[] chars, int offset, int length, String className ) throws ClassNotFoundException
   {
      if ( length <= 0 )
      {
         throw new PersistencyException( "Cannot create object from empty or null string!" );
      }
      if ( ( chars[offset] == 'i' ) && ( chars[offset + 1] == ':' ) )
      {
         //is id
         IdentifierModule identifierModule = getRepository().getIdentifierModule();
         ClassHandler classHandler = getRepository().getFeatureAccessModule().getClassHandler( className );
         final ID id = identifierModule.readID( new String( chars, offset + 2, length - 2 ),
               classHandler );
         Object object = identifierModule.getObject( id );
         if ( object != null )
         {
            //FIXME: do we need this check? (crashes Fujaba 4 to Fujaba 5 import)
//            if ( className != null && classHandler ==
//                  getRepository().getFeatureAccessModule().getClassHandler( object ) )
//            {
               return object;
//            }
//            else
//            {
//               throw new PersistencyException( "Object with id " + id + " already created but class does not match: "
//                     + object.getClass().getName() + " not " + className );
//            }
         }
         else
         {
            return id;
         }
      }
      else
      {
         throw new UnsupportedOperationException( "invalid text representation for creating object: " + new String( chars, offset, length ) );
      }
   }

   /**
    * @param textRepresentation
    * @param expectedClassName
    * @return
    * @throws ClassNotFoundException if class in text representation of value is not found
    */
   protected Object deserialize( String textRepresentation, String expectedClassName )
         throws ClassNotFoundException, PersistencyException
   {
      if ( textRepresentation != null )
      {
         return deserialize( textRepresentation.toCharArray(), 0, textRepresentation.length(), expectedClassName );
      }
      else
      {
         return null;
      }
   }

   /**
    * @param expectedClassName
    * @return
    * @throws ClassNotFoundException if class in text representation of value is not found
    */
   protected Object deserialize( char[] chars, int offset, int length, String expectedClassName )
         throws ClassNotFoundException, PersistencyException
   {
      //TODO: pass expected class as ClassHandler
      if ( length >= 0 )
      {
         if ( ( chars[offset] == 'i' ) && ( chars[offset + 1] == ':' ) )
         {
            //is id
            IdentifierModule identifierModule = getRepository().getIdentifierModule();
            String idString = new String( chars, offset + 2, length - 2 );
            if ( "repository".equals( idString ) )
            {
               return getRepository();
            }
            else
            {
               final ID id = identifierModule.readID( idString, null );
               Object object = identifierModule.getObject( id );
               if ( object != null )
               {
                  return object;
               }
               else
               {
                  return id;
               }
            }
         }
         else if ( ( chars[offset] == 'v' ) && ( chars[offset + 1] == ':' ) )
         {
            //is value
            int indexOfColon = offset + 2;

            while ( chars[indexOfColon] != ':' )
            {
               ++indexOfColon;
            }

            final String cls;
            if ( indexOfColon == offset + 2 )
            {
               cls = expectedClassName;
               // TODO: not on server:
//               if ( expectedClassName == null )
//               {
//                  throw new UnsupportedOperationException( "Classname was not stored - expected classname must be provided" );
//               }
            }
            else
            {
               cls = new String( chars, offset + 2, indexOfColon - offset - 2 );
            }

            final String value = indexOfColon < offset + length - 1 ? new String( chars, indexOfColon + 1, length - indexOfColon + offset - 1 ) : "";
            if ( !"class".equals(cls) )
            {
               return getRepository().getFeatureAccessModule().getClassHandler( cls ).deserialize( value );
            }
            else
            {
               return getRepository().getFeatureAccessModule().getClassHandler( value );
            }

         }
         else
         {
            throw new UnsupportedOperationException( "Value cannot be deserialized: " + new String( chars, offset, length ) );
         }
      }
      else
      {
         return null;
      }
   }

}

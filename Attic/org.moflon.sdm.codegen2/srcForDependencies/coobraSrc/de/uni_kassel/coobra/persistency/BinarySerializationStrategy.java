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
import de.uni_kassel.coobra.persistency.io.NonClosableBinaryInputStream;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.error.SerializationNotSupportedException;
import de.uni_kassel.features.io.BinaryInputStream;
import de.uni_kassel.features.io.BinaryOutputStream;
import de.uni_kassel.features.reflect.DefaultPrimitiveClassHandler;

import java.io.EOFException;
import java.io.IOException;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 14.09.2004, 10:03:52
 */
abstract class BinarySerializationStrategy
{
   public abstract Repository getRepository();

   private static final int MARKER_NULL = 0x70;
   private static final int MARKER_REPOSITORY_REFERENCE = 0x71;
   private static final int MARKER_ID = 0x72;
   private static final int MARKER_OBJECT = 0x73;
   private static final int MARKER_CLASS = 0x74;

   protected void serialize(Object object, ClassHandler expectedType, BinaryOutputStream out) throws SerializationNotSupportedException, IOException, ClassNotFoundException
   {
      if (object == getRepository())
      {
         out.write(MARKER_REPOSITORY_REFERENCE);
      } else if (object != null)
      {
         ID id = getRepository().getIdentifierModule().getID(object);
         if (id != null)
         {
            out.write(MARKER_ID);
            id.writeTo(out);
         } else
         {
            if (object instanceof ClassHandler)
            {
               out.write(MARKER_CLASS);
               ClassHandler classHandler = (ClassHandler) object;
               out.writeClass(classHandler);
            } else
            {
               out.write(MARKER_OBJECT);
               final ClassHandler classHandler = getRepository().getFeatureAccessModule().getClassHandler(object);
               final DefaultPrimitiveClassHandler partnerClass;
               if (classHandler instanceof DefaultPrimitiveClassHandler)
               {
                  DefaultPrimitiveClassHandler primitiveClassHandler = (DefaultPrimitiveClassHandler) classHandler;
                  partnerClass = primitiveClassHandler.getPartner();
               } else
               {
                  partnerClass = null;
               }
               final String className = classHandler.getName();
               if (className != null && ( expectedType == null // null can happen with NonResolvingClassHandler
                     || ( !className.equals(expectedType.getName())
                     && (partnerClass == null || !partnerClass.getName().equals(expectedType.getName())))) )
               {
                  out.writeClass(classHandler);
               } else
               {
                  out.writeClass(null);
               }
               try
               {
                  //TODO: optimize binary serialization
                  classHandler.serialize(object, out);
               } catch (SerializationNotSupportedException e)
               {
                  throw new SerializationNotSupportedException("Error attempting to serialize an instance of class "
                        + e.getUnsupportedClass().getName() + " as no ID for it was present. It is neither a " +
                        "properly registed model object nor an instance of a serializable class.",
                        e.getUnsupportedClass(), e);
               }
            }
         }
      } else
      {
         out.write(MARKER_NULL);
      }
   }

   protected void serialize(Object changeAware, boolean allowNewId, BinaryOutputStream out) throws IOException
   {
      if (changeAware != null)
      {
         IdentifierModule identifierModule = getRepository().getIdentifierModule();
         ID id = identifierModule.getID(changeAware);
         if (id == null)
         {
            if (allowNewId)
            {
               id = identifierModule.newID(changeAware);
            } else
            {
               throw new PersistencyException("Object should already have an ID: " + changeAware);
            }
         }
         out.write(MARKER_ID);
         id.writeTo(out);
      } else
      {
         out.write(MARKER_NULL);
      }
   }

   protected Object getAffectedObject(BinaryInputStream in, ClassHandler classHandler) throws IOException
   {
      int marker = in.read();
      switch (marker)
      {
         case MARKER_ID:
            IdentifierModule identifierModule = getRepository().getIdentifierModule();
            final ID id = identifierModule.readID(in, classHandler);
            Object object = identifierModule.getObject(id);
            if (object != null)
            {
               return object;
            } else
            {
               return id;
            }
         default:
            throw new PersistencyException("Invalid marker for affected object: " + marker);
      }
   }

   /**
    * @throws ClassNotFoundException if class in text representation of value is not found
    */
   protected Object deserialize(BinaryInputStream in, ClassHandler expectedType)
         throws ClassNotFoundException, PersistencyException, IOException
   {
      int marker = in.read();
      switch (marker)
      {
         case MARKER_NULL:
            return null;
         case MARKER_REPOSITORY_REFERENCE:
            return getRepository();
         case MARKER_ID:
            IdentifierModule identifierModule = getRepository().getIdentifierModule();
            final ID id = identifierModule.readID(in, null);
            Object object = identifierModule.getObject(id);
            if (object != null)
            {
               return object;
            } else
            {
               return id;
            }
         case MARKER_OBJECT:
            ClassHandler cls = in.readClass();
            if (cls == null)
            {
               cls = expectedType;
               // TODO: not on server:
               if ( expectedType == null )
               {
                  throw new UnsupportedOperationException( "Classname was not stored - expected classname must be provided" );
               }
            }
            return cls.deserialize(new NonClosableBinaryInputStream(in, false));
         case MARKER_CLASS:
            return in.readClass();
         case -1:
            throw new EOFException();
         default:
            throw new UnsupportedOperationException("Value starting with marker " + marker + " could not be deserialized.");
      }
   }

}
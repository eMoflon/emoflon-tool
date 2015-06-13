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

import de.uni_kassel.coobra.server.handlers.NonResolvingFieldHandler;
import de.uni_kassel.features.AbstractClassHandler;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.ConstructorHandler;
import de.uni_kassel.features.FieldHandler;
import de.uni_kassel.features.MethodHandler;
import de.uni_kassel.features.annotation.Annotation;
import de.uni_kassel.features.error.SerializationNotSupportedException;

import java.io.IOException;
import java.util.Iterator;

class UnknownClassHandler extends AbstractClassHandler
{
   public UnknownClassHandler()
   {
      super(null, null);
   }

   protected ConstructorHandler createConstructorHandler(String... parameterTypes) throws NoSuchMethodException, ClassNotFoundException
   {
      throw new UnsupportedOperationException();
   }

   protected FieldHandler createFieldHandler(String fieldName) throws NoSuchFieldException
   {
      return new NonResolvingFieldHandler(this, fieldName);
   }

   protected MethodHandler createMethodHandler(String methodName, String... parameterTypes) throws NoSuchMethodException, ClassNotFoundException
   {
      throw new UnsupportedOperationException();
   }

   public void deleteInstance(Object instance) throws ClassCastException, IllegalStateException
   {
      throw new UnsupportedOperationException();
   }

   public Object deserialize(String data)
   {
      throw new UnsupportedOperationException();
   }

   public ClassHandler getDeclaringClass()
   {
      throw new UnsupportedOperationException();
   }

   public String getName()
   {
      throw new UnsupportedOperationException();
   }

   public boolean isAbstract()
   {
      throw new UnsupportedOperationException();
   }

   public boolean isInstance(Object probableInstance)
   {
      throw new UnsupportedOperationException();
   }

   public boolean isInterface()
   {
      throw new UnsupportedOperationException();
   }

   public boolean isSingleton()
   {
      throw new UnsupportedOperationException();
   }

   public Iterator<Annotation> iteratorOfAnnotations()
   {
      throw new UnsupportedOperationException();
   }

   public Iterator<Annotation> iteratorOfDeclaredAnnotations()
   {
      throw new UnsupportedOperationException();
   }

   protected void searchForConstructors()
   {
      throw new UnsupportedOperationException();
   }

   protected void searchForFields()
   {
      throw new UnsupportedOperationException();
   }

   protected void searchForMethods()
   {
      throw new UnsupportedOperationException();
   }

   protected void searchForSuperClasses()
   {
      throw new UnsupportedOperationException();
   }

   public void serialize(Object object, Appendable out) throws IOException, SerializationNotSupportedException, ClassCastException
   {
      throw new UnsupportedOperationException();
   }
}
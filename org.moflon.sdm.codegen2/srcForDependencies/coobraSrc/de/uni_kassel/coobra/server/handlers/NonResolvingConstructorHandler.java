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

package de.uni_kassel.coobra.server.handlers;

import de.uni_kassel.features.AbstractClassHandler.MethodSignature;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.ConstructorHandler;
import de.uni_kassel.features.FeatureHandler;
import de.uni_kassel.features.InvocationException;
import de.uni_kassel.features.annotation.Annotation;
import de.uni_kassel.util.EmptyIterator;

import java.util.Iterator;

public class NonResolvingConstructorHandler implements ConstructorHandler {

   private final NonResolvingClassHandler handler;
   
   public NonResolvingConstructorHandler(NonResolvingClassHandler handler) {
      this.handler = handler;
   }

   public Object newInstance(Object... parameters) throws InvocationException {
      return handler.new ObjectDummy("<dummy>");
   }

   public String[] getParameterTypes() {
      throw new UnsupportedOperationException();
   }

   public Iterator<String> iteratorOfParameterNames() throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   public Iterator<ClassHandler> iteratorOfParameterTypes() {
      throw new UnsupportedOperationException();
   }

   public int sizeOfParameterTypes() {
      throw new UnsupportedOperationException();
   }

   public ClassHandler getClassHandler() {
      return handler;
   }

   public String getName() {
      return handler.getSimpleName();
   }

   public ClassHandler getType() {
      return handler;
   }

   public Visibility getVisibility() {
      throw new UnsupportedOperationException();
   }

   public boolean isStatic() {
      return true;
   }

   public Iterator<? extends FeatureHandler> iteratorOfOverriddenFeatures() {
      return EmptyIterator.get();
   }

   public Iterator<Annotation> iteratorOfAnnotations() {
      return EmptyIterator.get();
   }

   public Iterator<Annotation> iteratorOfDeclaredAnnotations() {
      return EmptyIterator.get();
   }

   public String getSignature()
   {
      return MethodSignature.toString (getName(), getParameterTypes());
   }
}

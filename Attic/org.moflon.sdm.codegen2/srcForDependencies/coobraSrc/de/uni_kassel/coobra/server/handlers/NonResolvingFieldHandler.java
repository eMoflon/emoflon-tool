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

import de.uni_kassel.features.AbstractFieldHandler;
import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.InvocationException;
import de.uni_kassel.features.MethodHandler;
import de.uni_kassel.features.annotation.Annotation;

import java.util.Iterator;

public class NonResolvingFieldHandler extends AbstractFieldHandler
{
   public NonResolvingFieldHandler( ClassHandler classHandler, String fieldName )
   {
      super( classHandler, fieldName );
   }

   public void alter( Object instance, Object key, Object oldValue, Object newValue ) throws UnsupportedOperationException, InvocationException
   {
      // we ignore it to allow undo/redo with this handler
//      throw new UnsupportedOperationException();
   }

   public ClassHandler getType()
   {
      return null;
   }

   public Visibility getVisibility()
   {
      throw new UnsupportedOperationException();
   }

   public boolean isReadOnly()
   {
      throw new UnsupportedOperationException();
   }

   public boolean isStatic()
   {
      throw new UnsupportedOperationException();
   }

   public Boolean isTransient()
   {
      return null;
   }

   public Iterator<MethodHandler> iteratorOfDeclaredAccessMethods()
   {
      throw new UnsupportedOperationException();
   }

   public Object read( Object instance ) throws UnsupportedOperationException
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
}

/*
 * $log$
 */


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

import de.uni_kassel.features.ClassHandler;
import de.uni_kassel.features.FeatureAccessModule;
import de.uni_kassel.features.reflect.DefaultClassHandlerFactory;

public class NonResolvingClasshandlerFactory extends DefaultClassHandlerFactory
{
   public NonResolvingClasshandlerFactory( FeatureAccessModule featureAccessModule )
   {
      super( featureAccessModule );
   }

   public NonResolvingClasshandlerFactory()
   {
   }

   @Override
   public ClassHandler getClassHandler(Object instance) throws ClassNotFoundException
   {
      if (instance instanceof NonResolvingClassHandler.ObjectDummy)
      {
         NonResolvingClassHandler.ObjectDummy dummy = (NonResolvingClassHandler.ObjectDummy) instance;
         return dummy.getClassHandler();
      } else
      {
         return super.getClassHandler(instance);
      }
   }

   @Override
   protected ClassHandler createClassHandler( final String className ) throws ClassNotFoundException
   {
      if (String.class.getName().equals(className))
      {
         return super.createClassHandler(className);
      } else
      {
         return new NonResolvingClassHandler(getFeatureAccessModule(), className, this);
      }
   }
}

/*
 * $log$
 */


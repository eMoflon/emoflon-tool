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
package de.uni_kassel.coobra.errors;

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.features.error.FieldIsReadOnlyException;
import de.uni_kassel.util.ConcurrentHashSet;

import java.util.Set;
import java.util.logging.Logger;

public class DefaultErrorHandlerModule extends ErrorHandlerModule
{
   private final Repository repository;

   public DefaultErrorHandlerModule(Repository repository)
   {
      this.repository = repository;
   }

   protected Repository getRepository()
   {
      return repository;
   }

   private final Set<Object> reportedErrors = new ConcurrentHashSet<Object>();
   private static final Logger LOGGER = Logger.getLogger(DefaultErrorHandlerModule.class.getName());

   public void handleError(Level level, int errorCode, String message, Throwable throwable, Object context)
   {
      if (level != Level.FATAL && (throwable instanceof UnsupportedOperationException))
      {
         if (reportedErrors.contains(throwable.toString()))
         {
            return;
         } else
         {
            reportedErrors.add(throwable.toString());
         }
      }
      switch (level)
      {
         case WARNING:
            StringBuilder warning = new StringBuilder();
            warning.append("CoObRA 2 Warning: ").append(message);
            if (throwable != null)
            {
               warning.append("(").append(String.valueOf(throwable)).append(")");
            }
            LOGGER.warning(warning.toString());
            break;
         case ERROR:
            StringBuilder error = new StringBuilder();
            error.append("CoObRA 2 Error: ").append(message);
            if (throwable != null)
            {
               error.append("(").append(String.valueOf(throwable)).append(")");
               if (throwable instanceof FieldIsReadOnlyException)
               {
                  throwable = null;
               }
            }
            LOGGER.log(java.util.logging.Level.SEVERE, error.toString(), throwable);
            break;
         case FATAL:
            if (throwable != null)
            {
               throw new UnhandledErrorException(message, throwable);
            } else
            {
               throw new UnhandledErrorException(message);
            }
      }
   }
}

/*
 * $Log$
 * Revision 1.4  2008/01/23 12:40:45  cschneid
 * error handling for missing fields
 *
 * Revision 1.3  2007/06/28 15:29:24  cschneid
 * fixed auto resolve and rolledback changes for client/server mode, copy supports multiple context objects
 *
 * Revision 1.2  2006/12/20 14:55:30  cschneid
 * NPE fixed, using JDK Logger instead of serr
 *
 * Revision 1.1  2006/12/20 14:26:08  cschneid
 * Introduced ErrorHandlerModule
 *
 */


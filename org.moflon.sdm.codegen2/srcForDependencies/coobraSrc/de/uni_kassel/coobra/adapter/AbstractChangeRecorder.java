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

package de.uni_kassel.coobra.adapter;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.errors.ErrorHandlerModule;

import java.util.Map;

/**
 * Listens to property changes and protocols them in the repository.
 */
public abstract class AbstractChangeRecorder implements ChangeRecorder
{
   public Repository getRepository()
   {
      return repository;
   }

   /**
    * repository this recorder reports to.
    */
   protected final Repository repository;

   public AbstractChangeRecorder( Repository repository )
   {
      if ( repository == null )
      {
         throw new NullPointerException( "repository may not be null" );
      }
      this.repository = repository;
   }

   public void registerNewObject( Object object )
   {
      registerNewObject( object, null );
   }

   public void registerNewObject( Object object, Object key )
   {
      if ( object != null )
      {
         try
         {
            processObjectCreation( object, key );
            registerExistingObject( object );
         } catch ( ClassNotFoundException e )
         {
            getRepository().getErrorHandlerModule().error( getRepository(), ErrorHandlerModule.Level.ERROR,
                  ErrorHandlerModule.ERROR_RECORD_NEW_OBJECT, "ClassHandler for object not found!", e, object );
         }
      }
   }

   public void registerExistingObject( Object object )
   {
      registerListener(object);
   }

   protected void processObjectCreation( Object object, Object key )
         throws ClassNotFoundException
   {
      if ( !repository.isInOperationalization() )
      {
         Change change = repository.getChangeFactory().changeCreateObject( object, key );
         repository.acknowledgeChange( change );
      } else
      {
         final Change change = repository.getOperationalizedChange();
         if ( change != null )
         {
            if ( Change.Kind.CREATE_OBJECT.equals( change.getKind() ) )
            {
               Change created = repository.getChangeFactory().changeCreateObject( object, key );
               if ( !repository.getFeatureAccessModule().getClassHandler( object ).equals( created.getNewValue() ) )
               {
                  errorCreateWhileRedo(object, change);
               }
            } else
            {
               errorCreateWhileRedo(object, change);
            }
         }
      }
   }

   private void errorCreateWhileRedo(Object object, Change change)
   {
      getRepository().getErrorHandlerModule().error( getRepository(), ErrorHandlerModule.Level.WARNING,
            ErrorHandlerModule.ERROR_RECORD_CHANGE, "Created object of " +
            object.getClass() + " while in redo", null, change );
   }

   protected void processChange( Object source, String property, Object oldValue, Object newValue)
         throws ClassNotFoundException, NoSuchFieldException
   {
      if ( isObjectDestruction(property) )
      {
         processObjectDestruction(source);
      }
      else if (!repository.isInOperationalization())
      {
         Change change;
         if ((oldValue == null && newValue instanceof Map.Entry)
            || (oldValue instanceof Map.Entry && (newValue == null || newValue instanceof Map.Entry)))
         {
            Map.Entry<?, ?> oldEntry = (Map.Entry<?, ?>) oldValue;
            Map.Entry<?, ?> newEntry = (Map.Entry<?, ?>) newValue;
            Object key = (oldEntry != null) ? oldEntry.getKey() : newEntry.getKey();
            change = repository.getChangeFactory().changeAlterField(source, property, key,
               oldEntry != null ? oldEntry.getValue() : null, newEntry != null ? newEntry.getValue() : null);
         }
         else
         {
            change = repository.getChangeFactory().changeAlterField(source, property,
               oldValue, newValue);
         }
         repository.acknowledgeChange(change);
      }
   }

   protected boolean isObjectDestruction (String property)
   {
      return false;
   }

   protected void processObjectDestruction (Object source)
   {
      //object removed - garbage collection will prove that and generate change
   }

   protected abstract void registerListener (Object object);
}

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

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.errors.ErrorHandlerModule;
import de.uni_kassel.util.PropertyChangeSource;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Listens to property changes and protocols them in the repository.
 */
public class PropertyChangeRecorder extends AbstractChangeRecorder
{
   public PropertyChangeRecorder( Repository repository )
   {
      super (repository);
   }

   /**
    * Preferred method to register a new object (faster):
    * create a change to log object creation, register the default property change recorder as
    * property change listener.
    * @param object newly created object
    */
   public void registerNewObject( PropertyChangeSource object )
   {
      registerNewObject( object, null );
   }

   /**
    * Preferred method to register a new object (faster):
    * create a change to log object creation, register the default property change recorder as
    * property change listener.
    * @param object newly created object
    * @param key key neccessary for recreating/finding object in redo/loading (may be null if not required)
    */
   public void registerNewObject( PropertyChangeSource object, Object key )
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

   
   /**
    * Method to register existing object (only subscribe listener).
    *
    * @param object existing object
    */
   public void registerExistingObject( PropertyChangeSource object )
   {
      object.addPropertyChangeListener( listener );
   }

   private final Listener listener = new Listener();

   protected void propertyChange( PropertyChangeEvent evt )
         throws ClassNotFoundException, NoSuchFieldException
   {
      processChange(evt.getSource(), evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
   }
   
   private class Listener implements PropertyChangeListener
   {

      /**
       * This method gets called when a bound property is changed.
       *
       * @param evt A PropertyChangeEvent object describing the event source
       *            and the property that has changed.
       */
      public void propertyChange( PropertyChangeEvent evt )
      {
         try
         {
            PropertyChangeRecorder.this.propertyChange( evt );
         } catch ( ClassNotFoundException e )
         {
            getRepository().getErrorHandlerModule().error( getRepository(), ErrorHandlerModule.Level.ERROR,
                  ErrorHandlerModule.ERROR_RECORD_CHANGE, "ClassHandler for value not found!", e, evt );
         } catch ( NoSuchFieldException e )
         {
            getRepository().getErrorHandlerModule().error( getRepository(), ErrorHandlerModule.Level.ERROR,
                  ErrorHandlerModule.ERROR_RECORD_CHANGE_FIELD, "FieldHandler for change not found!", e, evt );
         }
      }
   }

   @Override
   protected void registerListener (Object object)
   {
      try
      {
         repository.getFeatureAccessModule().getClassHandler(object).addPropertyChangeListener(object, listener);
      } catch (ClassNotFoundException e)
      {
         getRepository().getErrorHandlerModule().error(getRepository(), ErrorHandlerModule.Level.ERROR,
               ErrorHandlerModule.ERROR_RECORD_NEW_OBJECT, "ClassHandler for object not found!", e, object);
      }
   }

   @Override
   protected boolean isObjectDestruction (String property)
   {
      return "removeYou".equals(property);
   }
}

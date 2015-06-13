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

package de.uni_kassel.coobra;

import de.uni_kassel.features.FieldHandler;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 02.06.2004, 15:40:27
 */
public class ChangeFactory
{
   /**
    * store value for field repository
    */
   private Repository repository;

   /**
    * @return current value of the field repository
    */
   public Repository getRepository()
   {
      return this.repository;
   }

   /**
    * @param value new value for field repository
    * @return true if repository was changed
    */
   public boolean setRepository( Repository value )
   {
      boolean changed = false;
      if ( this.repository != value )
      {
         if ( this.repository != null )
         {
            Repository oldValue = this.repository;
            this.repository = null;
            oldValue.setChangeFactory( null );
         }
         this.repository = value;
         if ( value != null )
         {
            value.setChangeFactory( this );
         }
         changed = true;
      }
      return changed;
   }

   /**
    *
    * @param object
    * @param key key neccessary for recreating/finding object in redo/loading (may be null if not required)
    * @return
    * @throws ClassNotFoundException
    */
   public Change changeCreateObject( Object object, Object key ) throws ClassNotFoundException
   {
      assert getRepository() != null : "repository must not be null";
      assert object != null : "object must not be null";
      MutableChange change = new MutableChange();
      change.setKind( Change.Kind.CREATE_OBJECT );
      change.setAffectedObject( object );
      change.setNewValue( getRepository().getFeatureAccessModule().getClassHandler( object ) );
      change.setKey( key );
      change.setRepository( repository );
      return change;
   }

   public Change changeDestroyObject( Object object )
   {
      assert getRepository() != null : "repository must not be null";
      assert object != null : "object must not be null";
      MutableChange change = new MutableChange();
      change.setKind( Change.Kind.DESTROY_OBJECT );
      change.setAffectedObject( object );
      change.setRepository( repository );
      return change;
   }

   public Change changeAlterField( Object object, String field, Object oldValue, Object newValue ) throws ClassNotFoundException, NoSuchFieldException
   {
      return changeAlterField( object,
            getRepository().getFeatureAccessModule().getClassHandler( object ).getField( field ),
            oldValue, newValue );
   }

   public Change changeAlterField( Object object, String field, Object key, Object oldValue, Object newValue ) throws ClassNotFoundException, NoSuchFieldException
   {
      return changeAlterField( object,
            getRepository().getFeatureAccessModule().getClassHandler( object ).getField( field ),
            key, oldValue, newValue );
   }

   public Change changeAlterField( Object object, FieldHandler field, Object oldValue, Object newValue )
   {
      assert getRepository() != null : "repository must not be null";
      assert object != null : "object must not be null";
      MutableChange change = new MutableChange();
      change.setKind( Change.Kind.ALTER_FIELD );
      change.setAffectedObject( object );
      change.setField( field );
      change.setOldValue( oldValue );
      change.setNewValue( newValue );
      change.setRepository( repository );
      return change;
   }

   public Change changeAlterField( Object object, FieldHandler field, Object key, Object oldValue, Object newValue )
   {
      assert getRepository() != null : "repository must not be null";
      assert object != null : "object must not be null";
      MutableChange change = new MutableChange();
      change.setKind( Change.Kind.ALTER_FIELD );
      change.setAffectedObject( object );
      change.setField( field );
      change.setKey( key );
      change.setOldValue( oldValue );
      change.setNewValue( newValue );
      change.setRepository( repository );
      return change;
   }

   public Change changeRemoveKey( Object object, String field, Object key, Object oldValue ) throws ClassNotFoundException, NoSuchFieldException
   {
      assert getRepository() != null : "repository must not be null";
      assert object != null : "object must not be null";
      MutableChange change = new MutableChange();
      change.setKind( Change.Kind.REMOVE_KEY );
      change.setAffectedObject( object );
      change.setField( getRepository().getFeatureAccessModule().getClassHandler( object ).getField( field ) );
      change.setKey( key );
      change.setOldValue( oldValue );
      change.setRepository( repository );
      return change;
   }

   public Change changeManagement( Object object, Object key, Object oldValue, Object newValue, boolean local )
   {
      if ( local ) {
         return changeManagement( object, key, oldValue, newValue, Change.MODIFIER_LOCAL );
      } else {
         return changeManagement( object, key, oldValue, newValue, Change.MODIFIER_DEFAULT );
      }
   }

   public Change changeManagement( Object object, Object key, Object oldValue, Object newValue, int modifier )
   {
      assert getRepository() != null : "repository must not be null";
      MutableChange change = new MutableChange();
      change.setKind( Change.Kind.MANAGE );
      change.setAffectedObject( object );
      change.setKey( key );
      change.setOldValue( oldValue );
      change.setNewValue( newValue );
      change.setRepository( repository );
      change.setModifier( modifier );
      return change;
   }
}

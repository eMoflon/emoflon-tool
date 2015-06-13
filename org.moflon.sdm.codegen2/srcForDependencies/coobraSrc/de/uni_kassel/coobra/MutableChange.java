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

import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_kassel.coobra.transactions.AbstractMutableTransaction;
import de.uni_kassel.coobra.transactions.MutableTransaction;
import de.uni_kassel.coobra.transactions.MutableTransactionEntry;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.features.FieldHandler;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 02.06.2004, 15:08:29
 */
public class MutableChange extends AbstractChange implements MutableTransactionEntry
{
   private static final int MAXIMUM_SHOWN_VALUE_LENGTH = 100;

   /**
    * default ctor.
    */
   public MutableChange()
   {
   }

   /**
    * copy ctor.
    *
    * @param change where to copy from (by reference)
    */
   public MutableChange( Change change )
   {
      this();
      copyFrom( change );
   }

   /**
    * copy data from another change
    *
    * @param change copy source
    */
   public void copyFrom( Change change )
   {
      boolean autoResolving = change.isAutoResolving();
      change.setAutoResolving( false );
      setAffectedObject( change.getAffectedObject() );
      setField( change.getField() );
      setKey( change.getKey() );
      setModifier( change.getModifier() );
      setNewValue( change.getNewValue() );
      setOldValue( change.getOldValue() );
      setRepository( change.getRepository() );
      setKind( change.getKind() );
      setRolledBack( change.isRolledback() );
      setAutoResolving( autoResolving );
      change.setAutoResolving( autoResolving );
   }

   /**
    * Can be used before the affected object has been created.
    *
    * @return the ID of the affected object.
    */
   public ID getAffectedObjectID()
   {
      Object object = this.affectedObject;
      if ( object instanceof ID )
      {
         return (ID) object;
      } else
      {
         if ( object != null )
         {
            ID id = getRepository().getIdentifierModule().getID( object );
            if ( id == null )
            {
               throw new IllegalStateException( "No ID was found for object (" + object + ")" );
            }
            return id;
         } else
         {
            return null;
         }
      }
   }

   public Object getAffectedObjectRaw()
   {
      return affectedObject;
   }

   private boolean autoResolving = true;

   public void setAutoResolving( boolean enabled )
   {
      autoResolving = enabled;
   }

   public boolean isAutoResolving()
   {
      return autoResolving;
   }

   /**
    * getter for field affectedObject
    *
    * @return current value of field affectedObject
    */
   public Object getAffectedObject()
   {
      Object object = this.affectedObject;
      if ( isAutoResolving() && object instanceof ID )
      {
         object = resolve( (ID) object );
         this.affectedObject = object;
      }
      return object;
   }

   private Object resolve( ID id )
   {
      Object object = getRepository().getIdentifierModule().getObject( id );
      if ( object == null )
      {
         throw new IllegalStateException( "Object with ID " + id + " was not created yet!" );
      }
      return object;
   }

   /**
    * store value for field
    */
   private Object affectedObject;

   /**
    * setter for field affectedObject
    *
    * @param value new value
    */
   public void setAffectedObject( Object value )
   {
      if ( this.affectedObject != value )
      {
         this.affectedObject = value;
      }
   }

   /**
    * getter for field newValue
    *
    * @return current value of field newValue
    */
   public Object getNewValue()
   {
      Object object = this.newValue;
      if ( isAutoResolving() && object instanceof ID )
      {
         object = resolve( (ID) object );
         this.newValue = object;
      }
      return object;
   }

   /**
    * store value for field
    */
   private Object newValue;

   /**
    * setter for field newValue
    *
    * @param value new value
    */
   public void setNewValue( Object value )
   {
      if ( this.newValue != value )
      {
         this.newValue = value;
      }
   }

   /**
    * getter for field oldValue
    *
    * @return current value of field oldValue
    */
   public Object getOldValue()
   {
      Object object = this.oldValue;
      if ( isAutoResolving() && object instanceof ID )
      {
         object = resolve( (ID) object );
         this.oldValue = object;
      }
      return object;
   }

   /**
    * store value for field
    */
   private Object oldValue;

   /**
    * setter for field oldValue
    *
    * @param value new value
    */
   public void setOldValue( Object value )
   {
      if ( this.oldValue != value )
      {
         this.oldValue = value;
      }
   }

   /**
    * getter for field key
    *
    * @return current value of field key
    */
   public Object getKey()
   {
      Object object = this.key;
      if ( isAutoResolving() && object instanceof ID )
      {
         object = resolve( (ID) object );
         this.key = object;
      }
      return object;
   }

   /**
    * store value for field
    */
   private Object key;

   /**
    * setter for field key
    *
    * @param value new value
    */
   public void setKey( Object value )
   {
      if ( this.key != value )
      {
         this.key = value;
      }
   }

   /**
    * getter for field field
    *
    * @return current value of field field
    */
   public FieldHandler getField()
   {
      return this.fieldHandler;
   }

   /**
    * store the value for field field
    */
   private FieldHandler fieldHandler;

   /**
    * setter for field field
    *
    * @param value new value
    */
   public void setField( FieldHandler value )
   {
      if ( this.fieldHandler != value )
      {
         this.fieldHandler = value;
      }
   }

   /**
    * getter for field type
    *
    * @return current value of field type
    */
   public Kind getKind()
   {
      Kind kind = this.kind;
      if ( kind == null ) return Kind.UNDEFINED;
      return kind;
   }

   /**
    * store the value for field type
    */
   private Kind kind;

   /**
    * setter for field type
    *
    * @param value new value
    */
   public void setKind( Kind value )
   {
      if ( this.kind != value )
      {
         this.kind = value;
      }
   }


   /**
    * getter for field modifier
    *
    * @return current value of field modifier
    */
   public int getModifier()
   {
      return this.modifier;
   }

   private boolean rolledBack;

   /**
    * undo the transaction/change. Abort it if not yet committed.
    */
   @Override
   public void rollbackNotify()
   {
      rolledBack = true;
   }

   /**
    * redo the change.
    */
   @Override
   public void recommitNotify()
   {
      rolledBack = false;
   }

   public void setRolledBack( boolean rolledBack )
   {
      this.rolledBack = rolledBack;
   }

   public boolean isRolledback()
   {
      return rolledBack;
   }

   /**
    * store the value for field modifier
    */
   private int modifier = MODIFIER_DEFAULT;

   public void setModifier( int value )
   {
      if ( this.modifier != value )
      {
         this.modifier = value;
      }
   }

   /**
    * store the value for field enclosingTransaction
    */
   private AbstractMutableTransaction enclosingTransaction;

   /**
    * Set the transaction that contains this entry.
    *
    * @param value the transaction where this entry is added, may be null
    */
   public void setEnclosingTransaction( AbstractMutableTransaction value )
   {
      AbstractMutableTransaction oldValue = this.enclosingTransaction;
      if ( oldValue != value )
      {
         if ( oldValue instanceof MutableTransaction )
         {
            MutableTransaction mutableTransaction = (MutableTransaction) oldValue;
            mutableTransaction.remove( this );
         }
         if ( value instanceof MutableTransaction )
         {
            MutableTransaction mutableTransaction = (MutableTransaction) value;
            mutableTransaction.add( this );
         }
         this.enclosingTransaction = value;
      }
   }

   /**
    * The transaction that contains this entry.
    *
    * @return the transaction where this entry was added, may be null
    */
   public Transaction getEnclosingTransaction()
   {
      return this.enclosingTransaction;
   }

   /**
    * removes this transaction entry from memory.
    */
   public void delete()
   {
      setEnclosingTransaction( null );
      setKind( Kind.UNDEFINED );
      setAffectedObject( null );
      setField( null );
      setKey( null );
      setModifier( MODIFIER_INVALID );
      setNewValue( null );
      setOldValue( null );
   }

   /**
    * Check if this entry is part of a specific transaction (ascends hierarchy).
    *
    * @param transaction transaction that possibly contains this entry (over hierarchy layers)
    * @return true if this entry is contained in the transaction
    */
   public boolean isEnclosedIn( Transaction transaction )
   {
      return getEnclosingTransaction() != null && getEnclosingTransaction().isEnclosedIn( transaction );
   }


   /**
    * getter for field repository
    *
    * @return current value of field repository
    */
   public Repository getRepository()
   {
      return this.repository;
   }

   /**
    * store the value for field repository
    */
   private Repository repository;

   /**
    * setter for field repository
    *
    * @param value new value
    */
   public void setRepository( final Repository value )
   {
      final Repository oldValue = this.repository;
      if ( oldValue != value )
      {
         this.repository = value;
      }
   }

   /**
    * Returns a 'human readable' string representation of this change
    *
    * @return this as string
    */
   @Override
   public String toString()
   {
      try
      {
         String text = "change";

         switch ( getKind() )
         {
            case ALTER_FIELD:
            case CREATE_OBJECT:
            case DESTROY_OBJECT:
               text += "(";
               if ( affectedObject != null )
               {
                  text += printRawObject( affectedObject );
               } else
               {
                  text += "null";
               }
               break;
            default:
         }

         switch ( getKind() )
         {
            case ALTER_FIELD:
               String oldV = printRawObject( oldValue );
               String newV = printRawObject( newValue );
               if ( oldV.length() > MAXIMUM_SHOWN_VALUE_LENGTH )
               {
                  oldV = oldV.substring( 0, MAXIMUM_SHOWN_VALUE_LENGTH ) + "...";
               }
               if ( newV.length() > MAXIMUM_SHOWN_VALUE_LENGTH )
               {
                  newV = newV.substring( 0, MAXIMUM_SHOWN_VALUE_LENGTH ) + "...";
               }
               text += "." + ( getField() != null ? getField().getName() : null )
                     + ( key != null ? "[" + printRawObject( key ) + "]" : "" )
                     + " from '" + oldV + "' to '" + newV + "'";
               break;
            case CREATE_OBJECT:
               text += " added";
               break;
            case DESTROY_OBJECT:
               text += " removed";
               break;
            case MANAGE:
               text += "Repository Management: " + getKey() + "("+getOldValue()+" -> "+getNewValue()+")";
               break;
            default:
               text += " unknown change";
         }

         if ( getEnclosingTransaction() != null )
         {
            text += ", transaction: ";
            text += getEnclosingTransaction().toString();
         }
         return text + ")";
      } catch (Exception e)
      {
         return "Change (toString failed: "+e+")";
      }
   }

   private String printRawObject( final Object raw )
   {
      if ( !( raw instanceof ID ) )
      {
         if ( raw != null )
         {
            return raw.getClass().getName() + "(" +
                  raw.toString() + ")";
         } else
         {
            return "null";
         }
      } else
      {
         ID affectedObjectId = (ID) raw;
         return affectedObjectId.getClassHandler().getName() + "(" +
               affectedObjectId.toString() + ")";
      }
   }

   @Override
   public Change externalize()
   {
      MutableChange copy = new MutableChange( this );
      IdentifierModule identifierModule = getRepository().getIdentifierModule();
      copy.setAffectedObject( identifierModule.getID( getAffectedObject() ) );

      ID keyId = identifierModule.getID(getKey());
      if ( keyId != null ) {
         copy.setKey( keyId );
      }
      ID newvalueId = identifierModule.getID(getNewValue());
      if ( newvalueId != null ) {
         copy.setNewValue( newvalueId );
      }
      ID oldvalueId = identifierModule.getID(getOldValue());
      if ( oldvalueId != null ) {
         copy.setOldValue( oldvalueId );
      }
      return copy;
   }
}

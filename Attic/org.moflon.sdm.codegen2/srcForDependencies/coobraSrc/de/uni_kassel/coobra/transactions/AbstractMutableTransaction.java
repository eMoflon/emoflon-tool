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

package de.uni_kassel.coobra.transactions;

import de.uni_kassel.coobra.Repository;

public abstract class AbstractMutableTransaction extends AbstractTransaction implements MutableTransactionEntry
{
   /**
    * name of the transaction
    */
   String name;
   private Status status = Status.STARTED;
   /**
    * store the value for field enclosingTransaction
    */
   private AbstractMutableTransaction enclosingTransaction;
   protected long timestamp;
   Repository repository;
   private TransactionReference reference;
   private boolean autoResolving = true;
   protected int modifier;

   public AbstractMutableTransaction(Repository repository, String name, long timestamp, int modifier)
   {
      this.modifier = modifier;
      this.timestamp = timestamp;
      this.name = name;
      this.repository = repository;
   }

   /**
    * getter for field name
    *
    * @return current value of field name
    */
   public String getName()
   {
      return this.name;
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
    * removes this transaction entry from memory.
    */
   public void delete()
   {
      setEnclosingTransaction( null );
      setStatus( Status.INVALID );
      repository = null;
   }

   /**
    * @return the return value of {@link System#currentTimeMillis()} upon start of the transaction
    */
   public long getTimeStamp()
   {
      return timestamp;
   }

   /**
    * status of the Transaction (see Status enum)
    *
    * @return value from Status enum
    */
   public Status getStatus()
   {
      return status;
   }

   /**
    * setter for field status. Set by rollback and commit only!
    *
    * @param value new value
    */
   @Override
   public void setStatus( final Status value )
   {
      final Status oldValue = this.status;
      if ( !value.equals( oldValue ) )
      {
         this.status = value;
         firePropertyChange( "status", oldValue, value );
      }
   }

   protected void setReference( TransactionReference reference )
   {
      this.reference = reference;
   }

   /**
    * Check if this entry is part of a specific transaction (ascends hierarchy).
    *
    * @param transaction transaction that possibly contains this entry (over hierarchy layers)
    * @return true if this entry is contained in the transaction
    */
   public boolean isEnclosedIn( Transaction transaction )
   {
      if ( transaction == this || transaction.getReference() == this.getReference() )
      {
         return true;
      } else
      {
         return getEnclosingTransaction() != null && getEnclosingTransaction().isEnclosedIn( transaction );
      }
   }

   /**
    * getter for field enclosingTransaction
    *
    * @return current value of field enclosingTransaction
    */
   public Transaction getEnclosingTransaction()
   {
      return this.enclosingTransaction;
   }

   /**
    * setter for field enclosingTransaction
    *
    * @param value new value
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
         this.enclosingTransaction = value;
         if ( value instanceof MutableTransaction )
         {
            MutableTransaction mutableTransaction = (MutableTransaction) value;
            mutableTransaction.add( this );
         }
         firePropertyChange( "enclosingTransaction", oldValue, value );
      }
   }

   public TransactionReference getReference()
   {
      if ( reference == null )
      {
         reference = new TransactionReference();
      }
      return reference;
   }

   /**
    * Returns a string representation of the transaction.
    *
    * @return a string representation of the transaction.
    */
   @Override
   public String toString()
   {
      String text = "Transaction '" + getName() + "'";
      if ( getEnclosingTransaction() != null )
      {
         text += ", parent: ";
         text += getEnclosingTransaction().toString();
      }
      return text;
   }

   /**
    * Query auto resolve property. True by default.
    *
    * @return true if getters for affected object, new value, old value and key always return object references.
    * @see #setAutoResolving(boolean)
    */
   public boolean isAutoResolving()
   {
      return autoResolving;
   }

   public int getModifier()
   {
      return modifier;
   }

   public void setModifier(int modifier)
   {
      this.modifier = modifier;
   }

   /**
    * Enable/disable automatic resolving of affected object, new value, old value and key.
    *
    * @param enabled if set to false ID are not resolved when getters are called
    * @see #isAutoResolving()
    */
   public void setAutoResolving( boolean enabled )
   {
      this.autoResolving = enabled;
   }
}

/*
 * $Log$
 * Revision 1.3  2008/10/23 14:38:38  cschneid
 * introduced binary persistency modules
 *
 * Revision 1.2  2008/04/04 12:34:50  cschneid
 * added API for resolving TransactionReferences; Server stores username for checkin
 *
 * Revision 1.1  2007/12/06 11:37:11  cschneid
 * improved iterator for stream transactions: read lazily
 *
 */


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

/**
 * @author christian.schneider@uni-kassel.de
 * @created 02.06.2004, 13:49:33
 */
public abstract class TransactionModule
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
      final Repository oldValue = this.repository;
      if ( oldValue != value )
      {
         if ( oldValue != null )
         {
            this.repository = null;
            oldValue.setTransactionModule( null );
         }
         this.repository = value;
         if ( value != null )
         {
            value.setTransactionModule( this );
         }
         changed = true;
      }
      return changed;
   }

   /**
    * Start a new transaction. Do not invoke this method from outside. Use {@link Repository#startTransaction(String)}
    * instead.
    *
    * @param transactionName name of the new transaction
    * @return the new transaction object
    */
   public abstract Transaction start( String transactionName );

   /**
    * delete the module from memory
    */
   public void delete()
   {
      setRepository( null );
   }
}

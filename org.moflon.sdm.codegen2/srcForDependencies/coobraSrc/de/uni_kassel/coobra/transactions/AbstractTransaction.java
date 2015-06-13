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

import de.uni_kassel.util.PropertyChangeSourceImpl;

import java.util.Iterator;

/**
 * @author christian.schneider@uni-kassel.de
 * @created 07.12.2004, 15:33:44
 */
public abstract class AbstractTransaction extends PropertyChangeSourceImpl implements Transaction
{
   public void commit()
   {
      if ( getStatus().equals( Status.STARTED ) )
      {
         try
         {
            for (Iterator<? extends TransactionEntry> it = iterator(); it.hasNext();)
            {
               TransactionEntry entry = it.next();
               entry.commit();
            }
            setStatus(Status.COMMITTED);
         } catch (RuntimeException e)
         {
            setStatus(Status.INVALID);
            throw e;
         }
      } else if ( getStatus().equals( Status.COMMITTED ) )
      {
         setStatus( Status.STARTED );
         setStatus( Status.COMMITTED );
      } else if ( getStatus().equals( Status.ROLLED_BACK ) )
      {
         setStatus( Status.COMMITTED );
      } else
      {
         throw new IllegalStateException( "Invalid status for commit: " + getStatus() );
      }
   }

   /**
    * Setter for field status. Set by rollback and commit only!
    *
    * @param value new value
    */
   protected abstract void setStatus( Status value );

   private TransactionEntry operate( final Status newStatus )
   {
      final Status currentStatus = getStatus();
      //todo: does this have to be checked?
//      if ( getRepository().getActiveTransaction() != null
//            && getRepository().getActiveTransaction().isEnclosedIn( this ) )
      {
         final boolean aquiredLock = getRepository().aquireOperationalizationLock();
         try
         {
            try
            {
               TransactionEntry lastEntry = this;

               if ( newStatus.equals( Status.COMMITTED ) )
               {
                  if ( currentStatus.equals( Status.STARTED ) )
                  {
                     throw new IllegalStateException("this method should not be called to commit started transactions");
                  } else if ( currentStatus.equals( Status.ROLLED_BACK ) )
                  {
                     for ( Iterator<? extends TransactionEntry> it = iterator(); it.hasNext(); )
                     {
                        TransactionEntry entry = it.next();
                        lastEntry = entry.recommit();
                     }
                  }
               } else
               {
                  for ( Iterator<? extends TransactionEntry> it = iteratorReverse(); it.hasNext(); )
                  {
                     TransactionEntry entry = it.next();
                     lastEntry = entry.rollback();
                  }
               }

               setStatus( newStatus );

               return lastEntry;
            } catch ( RuntimeException e )
            {
               setStatus( Status.INVALID );
               throw e;
            }
         } finally
         {
            if ( aquiredLock )
            {
               getRepository().releaseOperationalizationLock();
            }
         }
      }
//      else
//      {
//         throw new IllegalStateException( "The active transaction is no entry in this transaction: " + getRepository().getActiveTransaction() );
//      }
   }

   /**
    * redo the transaction/change.
    *
    * @return the last change that was recommited (most recently created)
    */
   public final TransactionEntry recommit()
   {
      if ( getStatus().equals( Status.ROLLED_BACK ) )
      {
         return operate( Status.COMMITTED );
      } else
      {
         throw new IllegalStateException( "Invalid status for recommit: " + getStatus() );
      }
   }

   /**
    * undo the transaction/change. Abort it if not yet committed.
    *
    * @return the last change that was rolled back (first regarding creation order)
    */
   public TransactionEntry rollback()
   {
      if ( getStatus() == Status.COMMITTED )
      {
         return operate( Status.ROLLED_BACK );
      } else if ( getStatus() == Status.STARTED )
      {
         return operate( Status.ABORTED );
      } else
      {
         throw new IllegalStateException( "Invalid status for rollback: " + getStatus() );
      }
   }

   /**
    * Create a 'copy' of this entry that uses as few memory as possible and holds references as IDs.
    */
   public Transaction externalize()
   {
      throw new UnsupportedOperationException();
   }

   public boolean isManagementEntry()
   {
      return false;
   }

   public boolean isLocal()
   {
      boolean foundLocal = false;
      for ( Iterator<? extends TransactionEntry> it = iterator(); it.hasNext(); )
      {
         TransactionEntry entry = it.next();
         if ( !entry.isLocal() ) {
            return false;
         }
         foundLocal = true;
      }
      return foundLocal;
   }

   /**
    * @return false
    */
   public final boolean isRolledback()
   {
      return false;
   }
}

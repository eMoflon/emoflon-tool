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
package de.uni_kassel.coobra.persistency;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.transactions.Transaction;
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.coobra.transactions.TransactionReference;

/**
 * Allows to apply an additional {@link EntryFilter} for reading and sending entries from/to a delegate.
 *
 * @author cschneid
 */
public class FilterPersistencyModule extends PersistencyModule
{
   private final PersistencyModule delegate;

   public FilterPersistencyModule(PersistencyModule delegate)
   {
      if ( delegate == null ) throw new NullPointerException();
      this.delegate = delegate;
   }


   /**
    * getter for field filter
    *
    * @return current value of field filter
    */
   public EntryFilter getFilter()
   {
      if (this.filter == null)
      {
         throw new NullPointerException("Filter was not set!");
      }
      return this.filter;
   }

   /**
    * store the value for field filter
    */
   private EntryFilter filter;

   /**
    * Property name for firing property change events of field $name.
    */
   public static final String PROPERTY_FILTER = "filter";

   /**
    * setter for field filter
    *
    * @param value new value
    */
   public void setFilter(final EntryFilter value)
   {
      final EntryFilter oldValue = this.filter;
      if (oldValue != value)
      {
         this.filter = value;
//         firePropertyChange(PROPERTY_FILTER, oldValue, value);
      }
   }

   public void close() throws PersistencyException
   {
      delegate.close();
   }

   public boolean isOpened()
   {
      return delegate.isOpened();
   }

   public void open(boolean readonly) throws PersistencyException
   {
      delegate.open(readonly);
   }

   public TransactionEntry receiveNext(TransactionEntry entry, final EntryFilter filter) throws PersistencyException
   {
      if (filter == null)
      {
         return delegate.receiveNext(entry, getFilter());
      } else
      {
         return delegate.receiveNext(entry, new EntryFilter()
         {
            public boolean accept(TransactionEntry entry)
            {
               return filter.accept(entry) && getFilter().accept(entry);
            }
         });
      }
   }

   public TransactionEntry receivePrevious(TransactionEntry entry, final EntryFilter filter) throws PersistencyException
   {
      if (filter == null)
      {
         return delegate.receivePrevious(entry, getFilter());
      } else
      {
         return delegate.receivePrevious(entry, new EntryFilter()
         {
            public boolean accept(TransactionEntry entry)
            {
               return filter.accept(entry) && getFilter().accept(entry);
            }
         });
      }
   }

   public Change send(Change change, Transaction activeTransaction) throws PersistencyException
   {
      if (getFilter().accept(change))
      {
         return delegate.send(change, activeTransaction);
      } else
      {
         return null;
      }
   }

   public Transaction send(Transaction transaction, Transaction activeTransaction) throws PersistencyException
   {
      if (getFilter().accept(transaction))
      {
         return delegate.send(transaction, activeTransaction);
      } else
      {
         return null;
      }
   }

   @Override
   public boolean setRepository(Repository value)
   {
      delegate.setRepository(value);
      return super.setRepository(value);
   }

   public Transaction resolveTransaction(TransactionReference reference)
   {
      Transaction transaction = delegate.resolveTransaction(reference);
      if ( transaction != null )
      {
         if (getFilter().accept(transaction))
         {
            return transaction;
         }
         else
         {
            throw new UnsupportedOperationException("transaction was filtered");
         }
      }
      else
      {
         return null;
      }
   }
}

/*
 * $Log$
 * Revision 1.4  2008/04/04 12:34:45  cschneid
 * added API for resolving TransactionReferences; Server stores username for checkin
 *
 * Revision 1.3  2007/10/25 09:14:09  cschneid
 * set repository on delegate
 *
 * Revision 1.2  2006/12/19 11:45:55  cschneid
 * header updated
 *
 * Revision 1.1  2006/11/14 15:09:21  cschneid
 * persistency filter, some fixes
 *
 */


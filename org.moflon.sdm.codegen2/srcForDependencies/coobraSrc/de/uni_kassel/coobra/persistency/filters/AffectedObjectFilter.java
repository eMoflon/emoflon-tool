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
package de.uni_kassel.coobra.persistency.filters;

import de.uni_kassel.coobra.Change;
import de.uni_kassel.coobra.identifiers.ID;
import de.uni_kassel.coobra.persistency.EntryFilter;
import de.uni_kassel.coobra.transactions.TransactionEntry;

/**
 * Accepts only changes with a given affectedObject ID (comparing with equals).
 * This class is final to allow PersistencyModules to optimize their retrieval strategy when they detect this filter.
 */
public final class AffectedObjectFilter implements EntryFilter
{
   private static final long serialVersionUID = 1;

   private final ID affectedObject;

   public AffectedObjectFilter(ID affectedObject)
   {
      this.affectedObject = affectedObject;
   }

   /**
    * @return the affectedObject this filter tries to match
    */
   public ID getAffectedObject()
   {
      return affectedObject;
   }

   public boolean accept(TransactionEntry entry)
   {
      if ( entry instanceof Change )
      {
         Change change = (Change) entry;
         return this.getAffectedObject().equals(change.getAffectedObjectID());
      }
      else
      {
         return false;
      }
   }
}

/*
 * $Log$
 * Revision 1.3  2007/07/06 09:39:23  cschneid
 * prevent gc of transaction while reading, fixed concatenated module filters
 *
 * Revision 1.2  2006/12/19 11:46:21  cschneid
 * header updated
 *
 * Revision 1.1  2006/06/30 12:32:57  cschneid
 * use EntryFilter instead of explicit parameters for PersistencyModules
 *
 */


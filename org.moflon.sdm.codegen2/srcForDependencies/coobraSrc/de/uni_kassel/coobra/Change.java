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
import de.uni_kassel.coobra.transactions.TransactionEntry;
import de.uni_kassel.features.FieldHandler;

/**
 * A Change represents an atomic change to the object data of an application.
 * The different kind of changes are described in the {@link Kind} enumeration.
 *
 * @author christian.schneider@uni-kassel.de
 * @created 02.12.2004, 10:20:45
 */
public interface Change extends TransactionEntry
{
   /**
    * Enumeration for the kind of change. See elements for details.
    */
   public enum Kind
   {
      /**
       * indicates that the type of change was not set.
       */
      UNDEFINED,
      /**
       * indicates that a new object was added to the repository.
       */
      CREATE_OBJECT,
      /**
       * indicates that a object was removed from the repository.
       */
      DESTROY_OBJECT,
      /**
       * indicates that a field was altered (remove old value, put new one).
       */
      ALTER_FIELD,
      /**
       * indicates that a key was removed from a qualified field.
       */
      REMOVE_KEY,
      /**
       * indicates that management data has been changed (e.g. named objects, ids etc.).
       */
      MANAGE
      /**
       * Attention: always append new constants at the and of the Enum, persistency may use the ordinals
       */
   }

   /**
    * Query affected object. If auto resolving is enabled and the attribute still contains an ID it is resolved via the
    * {@link de.uni_kassel.coobra.identifiers.IdentifierModule}.
    *
    * @return object affected by this change
    * @see #isAutoResolving()
    */
   Object getAffectedObject();

   /**
    * Can be used before the affected object has been created.
    *
    * @return the ID of the affected object.
    */
   ID getAffectedObjectID();

   /**
    * see {@link de.uni_kassel.coobra.AbstractChange.Kind} constants.
    *
    * @return type of this change
    */
   Change.Kind getKind();

   /**
    * changed field.
    *
    * @return field that was changed (null if NA)
    */
   FieldHandler getField();

   /**
    * key in qualified field.
    *
    * @return key for addition/removal to/from qualified field (null if NA)
    * @see #isAutoResolving()
    */
   Object getKey();

   /**
    * former value.
    *
    * @return value of the field (at key) before this change occured
    * @see #isAutoResolving()
    */
   Object getOldValue();

   /**
    * value after the change.
    *
    * @return value of the field (at key) after this change occured
    * @see #isAutoResolving()
    */
   Object getNewValue();
}

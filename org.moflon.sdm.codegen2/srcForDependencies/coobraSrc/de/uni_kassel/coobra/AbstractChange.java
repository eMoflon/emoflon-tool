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


/**
 * @author christian.schneider@uni-kassel.de
 * @created 02.06.2004, 13:49:41
 */
public abstract class AbstractChange
      implements Change
{
   protected AbstractChange()
   {
   }

   public void commit()
   {
   }

   /**
    * redo the change.
    */
   public final Change recommit()
   {
      getRepository().redo( this );
      recommitNotify();
      return this;
   }

   public void recommitNotify()
   {

   }

   /**
    * undo the transaction/change. Abort it if not yet committed.
    */
   public final Change rollback()
   {
      getRepository().undo( this );
      rollbackNotify();
      return this;
   }

   public void rollbackNotify()
   {

   }

   /**
    * Create a 'copy' of this entry that uses as few memory as possible and holds references as IDs.
    */
   public Change externalize()
   {
      throw new UnsupportedOperationException();
   }

   public boolean isManagementEntry()
   {
      return Change.Kind.MANAGE.equals( getKind() );
   }

   public boolean isLocal()
   {
      return ( MODIFIER_LOCAL & getModifier() ) == MODIFIER_LOCAL;
   }

   /**
    * Returns a string representation of the change.
    *
    * @return a string representation of the change.
    */
   @Override
   public String toString()
   {
      switch ( getKind() )
      {
         case ALTER_FIELD:
            return "alter " + getAffectedObject() + "." + getField().getName()
                  + ( getKey() != null ? "[" + getKey() + "]" : "" )
                  + " from '" + getOldValue()
                  + "' to '" + getNewValue() + "'"
                  + ( getEnclosingTransaction() != null ? " in transaction " + getEnclosingTransaction().getName() : "" );
         case CREATE_OBJECT:
            return "create object of type " + getNewValue()
                  + ( getEnclosingTransaction() != null ? " in transaction " + getEnclosingTransaction().getName() : "" );
         default:
            return super.toString();
      }
   }
}

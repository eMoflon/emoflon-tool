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

/**
 * Convenience interface for logging object creation changes and registering with created object to record field
 * events. It is not required for proper CoObRA operation to implement this interface. You may as well log the
 * changes customly. 
 */
public interface ChangeRecorder
{
   /**
    * @return repository this recorder reports to
    */
   Repository getRepository();

   /**
    * Register a new object: create a change to log object creation, register the change recorder as a
    * listener.
    * @param object newly created object
    */
   void registerNewObject( Object object );

   /**
    * Register a new object: create a change to log object creation, register the change recorder as a
    * listener.
    * @param object newly created object
    * @param key key neccessary for recreating/finding object in redo/loading (may be null if not required)
    */
   void registerNewObject( Object object, Object key );

   /**
    * Register an object without logging a creation change, register the change recorder as a
    * listener.
    * @param object object to listen to
    */
   void registerExistingObject( Object object );
}

/*
 * $Log$
 * Revision 1.2  2008/10/23 14:38:30  cschneid
 * introduced binary persistency modules
 *
 * Revision 1.1  2007/01/15 14:13:12  cschneid
 * extracted ChangeRecorder interface
 *
 */

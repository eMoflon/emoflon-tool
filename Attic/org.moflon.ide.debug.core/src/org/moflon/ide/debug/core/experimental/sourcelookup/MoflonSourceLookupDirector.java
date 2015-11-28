/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Bjorn Freeman-Benson - initial API and implementation
 *******************************************************************************/
package org.moflon.ide.debug.core.experimental.sourcelookup;

import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

/**
 * PDA source lookup director. For PDA source lookup there is one source lookup participant.
 */
public class MoflonSourceLookupDirector extends AbstractSourceLookupDirector
{
   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.debug.internal.core.sourcelookup.ISourceLookupDirector#initializeParticipants()
    */
   public void initializeParticipants()
   {
      // new ISourceContainer[]{new DefaultSourceContainer()}
      addParticipants(new ISourceLookupParticipant[] { new MoflonSourceLookupParticipant() });
   }
}

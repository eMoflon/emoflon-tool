/*
 * The FUJABA ToolSuite project:
 *
 * FUJABA is the acronym for 'From Uml to Java And Back Again'
 * and originally aims to provide an environment for round-trip
 * engineering using UML as visual programming language. During
 * the last years, the environment has become a base for several
 * research activities, e.g. distributed software, database
 * systems, modelling mechanical and electrical systems and
 * their simulation. Thus, the environment has become a project,
 * where this source code is part of. Further details are avail-
 * able via http://www.fujaba.de
 *
 *    Copyright (C) Fujaba Development Group
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA 02111-1307, USA or download the license under
 * http://www.gnu.org/copyleft/lesser.html
 *
 *  WARRANTY:
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 *  Contact address:
 *
 * Fujaba Management Board
 * Software Engineering Group
 * University of Paderborn
 * Warburgerstr. 100
 * D-33098 Paderborn
 * Germany
 *
 * URL  : http://www.fujaba.de
 * email: info@fujaba.de
 */
package de.uni_paderborn.fujaba.versioning;

import de.uni_kassel.coobra.errors.HandledErrorException;
import de.uni_kassel.coobra.util.CopyManager;
import de.uni_kassel.features.ClassHandler;
import de.uni_paderborn.fujaba.app.FrameMain;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;

import javax.swing.JOptionPane;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Collection;

public class FujabaCopyManager extends CopyManager
{
   static final String CONTEXT_PREFIX = "c:";
   private FProject project;

   public FujabaCopyManager( FProject project )
   {
      super( Versioning.get().createBareRepository( project ) );
      this.project = project;
   }


   @Override
   public PasteResult paste(Transferable data, Collection<?> context, String modelName) throws UnsupportedFlavorException, IOException
   {
      return paste(data, context, Versioning.get().createBareRepository( project ), modelName);
   }

   @Override
   @SuppressWarnings({"unchecked"})
   protected String getIdentifier(Object object, Collection<?> context)
   {
      if ( object instanceof CopyMechanism.ContextDummy )
      {
         CopyMechanism.ContextDummy contextDummy = (CopyMechanism.ContextDummy) object;
         return contextDummy.getIdentifier();
      }
      else if ( object instanceof ASGElement )
      {
         ASGElement element = (ASGElement) object;
         for (Object contextObject : context)
         {
            if ( !(contextObject instanceof ASGElement) ) {
               throw new IllegalArgumentException("context must contain only ASGElements!");
            }
         }
         // it's quite safe to cast now as it only contains ASGElements
         return element.getContextIdentifier((Collection<? extends FElement>) context);
      }
      else
      {
         return null;
      }
   }

   @Override
   protected Object getObject(String identifier, Collection<?> context, ClassHandler type)
   {
      try
      {
         boolean isContext = false;
         if (identifier.startsWith(CONTEXT_PREFIX))
         {
            isContext = true;
            identifier = identifier.substring(CONTEXT_PREFIX.length());
         }
         //todo: ensure that context contains only FElements?
         //noinspection unchecked
         return NamespaceManager.get().findByContextIdentifier((Collection<FElement>) context, identifier, type, isContext);
      } catch (ElementNotFoundByContextIdentifier e)
      {
         //TODO: improve error handling
         // FIXME !!! remove UI access
         JOptionPane.showMessageDialog(FrameMain.get().getFrame(), "Paste failed as the selected context was not able to " +
               "resolve all needed elements. Please try again with another context selection.\nERROR: " + e.getMessage(),
               "Copy&Paste", JOptionPane.ERROR_MESSAGE );
         throw new HandledErrorException(e);
      }
   }

   @Override
   protected boolean isModelElement(Object value)
   {
      return value instanceof ASGElement;
   }
}

/*
 * $Log$
 * Revision 1.3  2007/03/23 12:45:07  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.2  2007/03/12 09:26:40  cschneid
 * findByContextIdentifier gets type passed
 *
 * Revision 1.1  2007/01/09 09:31:34  cschneid
 * cut/copy/paste dummys, requiredPlugins saved correctly with ctr
 *
 */


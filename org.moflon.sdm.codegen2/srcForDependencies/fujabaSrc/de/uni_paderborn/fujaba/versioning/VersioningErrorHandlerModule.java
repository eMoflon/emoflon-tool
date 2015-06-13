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

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.errors.DefaultErrorHandlerModule;
import de.uni_kassel.coobra.errors.ErrorHandlerModule;
import de.uni_kassel.coobra.errors.HandledErrorException;
import de.uni_kassel.coobra.errors.UnhandledErrorException;
import de.uni_kassel.coobra.identifiers.RequiredRepositoryMissingException;
import de.uni_kassel.features.ClassHandler;
import de.uni_paderborn.fujaba.app.FrameMain;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivityDiagram;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import java.util.Map;

class VersioningErrorHandlerModule extends DefaultErrorHandlerModule
{
   
   private boolean ignoreErrors;
   
   VersioningErrorHandlerModule(Repository repository)
   {
      super(repository);
   }

   @Override
   public void handleError(Level level, int errorCode, String message, Throwable throwable, Object context)
   {
      if (throwable instanceof RequiredRepositoryMissingException)
      {
         // FIXME !!! remove UI access
         JOptionPane.showMessageDialog(FrameMain.get().getFrame(), "Error loading project: " + throwable.getMessage(),
               "Load project", JOptionPane.ERROR_MESSAGE);
         throw new HandledErrorException(throwable);
      }
      else if ( throwable instanceof HandledErrorException )
      {
         HandledErrorException handledErrorException = (HandledErrorException) throwable;
         throw handledErrorException;
      }
      else if ( errorCode ==  ErrorHandlerModule.ERROR_PERSISTENCY_MISSING_FIELD )
      {
         if ( context != null )
         {
            Map.Entry entry = (Map.Entry) context;
            ClassHandler cls = (ClassHandler) entry.getKey();
            String fieldName = (String) entry.getValue();
            if ("generated".equals(fieldName))
            {
               // ignore this old field
               return;
            }
            if ("revContains".equals(fieldName) && getHandler(UMLActivityDiagram.class).isAssignableFrom(cls))
            {
               // ignore removed field
               return;
            }
            if ("rootDir".equals(fieldName) && getHandler(FProject.class).isAssignableFrom(cls))
            {
               // ignore removed field
               return;
            }
         }
      }
//      if (message.contains("instances") )
//      {
//         System.err.println(message);
//         return;
//      }
      super.handleError(level, errorCode, message, throwable, context);
      if (level == Level.ERROR)
      {
         if (ProjectManager.get().isLoading() )
         {
            if (!ignoreErrors)
            {
                JCheckBox remember = new JCheckBox ("Don't ask again");
                remember.setToolTipText("Continues loading without showing further error messages.");
                
                String messagePrefix = "A fatal error occurred while loading project";
                FProject project = Versioning.get().getProject(this.getRepository());
                if (project != null)
                {
                   messagePrefix += " '" + project.getName() + ":" + project.getClass().getSimpleName() + "'";
                }
                
                // FIXME !!! remove UI access
                int choice = JOptionPane.showConfirmDialog (null,
                      new Object[]{
                         messagePrefix + ":\n" +
                         message +
                         "\nDo you want to continue loading?",
                         remember},
                      "Load Error", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.OK_OPTION)
                {
                    if (remember.isSelected ())
                    {
                        ignoreErrors = true;
                    }
                } else
                {
                   throw new UnhandledErrorException(message, throwable);
                }
            }
         }
         else
         {
            throw new UnhandledErrorException(message, throwable);
         }
      }
   }

   private ClassHandler getHandler(Class<?> cls)
   {
      return Versioning.getHandler(getRepository().getFeatureAccessModule(), cls);
   }
}

/*
 * $Log$
 * Revision 1.1  2007/03/15 16:36:26  cschneid
 * - Generating code is possible again
 * - projects failing to load entirely are removed from workspace
 *
 */


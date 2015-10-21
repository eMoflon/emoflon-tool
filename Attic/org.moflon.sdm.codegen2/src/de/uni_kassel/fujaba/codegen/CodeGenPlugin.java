/*
 * The FUJABA ToolSuite project:
 *
 *   FUJABA is the acronym for 'From Uml to Java And Back Again'
 *   and originally aims to provide an environment for round-trip
 *   engineering using UML as visual programming language. During
 *   the last years, the environment has become a base for several
 *   research activities, e.g. distributed software, database
 *   systems, modelling mechanical and electrical systems and
 *   their simulation. Thus, the environment has become a project,
 *   where this source code is part of. Further details are avail-
 *   able via http://www.fujaba.de
 *
 *      Copyright (C) Fujaba Development Group
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *   MA 02111-1307, USA or download the license under
 *   http://www.gnu.org/copyleft/lesser.html
 *
 * WARRANTY:
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 * Contact address:
 *
 *   Fujaba Management Board
 *   Software Engineering Group
 *   University of Paderborn
 *   Warburgerstr. 100
 *   D-33098 Paderborn
 *   Germany
 *
 *   URL  : http://www.fujaba.de
 *   email: info@fujaba.de
 *
 */
package de.uni_kassel.fujaba.codegen;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import de.fujaba.codegen.CodeLookupManager;
import de.uni_kassel.fujaba.codegen.dlr.DLRProjectToken;
import de.uni_kassel.fujaba.codegen.dlr.DLRToken;
import de.uni_kassel.fujaba.codegen.dlr.DLRTool;
import de.uni_kassel.fujaba.codegen.dlr.ElementReference;
import de.uni_kassel.fujaba.codegen.emf.CodeGen2EMFPlugin;
import de.uni_kassel.fujaba.codegen.engine.CodeGeneration;
import de.uni_kassel.fujaba.codegen.engine.JavaTokenMutatorTemplateEngine;
import de.uni_kassel.fujaba.codegen.utils.NavigationHelper;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.upb.lib.plugins.AbstractPlugin;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class CodeGenPlugin
    extends AbstractPlugin
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String PLUGIN_ID = CodeGenPlugin.class.getName();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public boolean initialize()
   {
      de.fujaba.codegen.CodeGeneration.set(new CodeGen2());
//      CodeGenStrategyHandler firstHandler = CodeGenFactory.get().getCurrentStrategy().getFirstOfHandlerChain();
//      CodeGenFactory.get().getCurrentStrategy().addToBeforeHandlerChain (new CodeGen2OOHandler2(), firstHandler);
      CodeGeneration.get().addToEngines (new JavaTokenMutatorTemplateEngine());
      CodeLookupManager.get().addLookup(new CodeLookupManager.CodeLookup() {
         public FElement getElementForLine(String file, int lineNr, FProject project)
         {
            DLRToken token = NavigationHelper.findInFujaba(lineNr, file, project, false);
            if (token != null)
            {
               Iterator iterator = token.iteratorOfElements();
               if (iterator.hasNext())
               {
                  return ((ElementReference) iterator.next()).getElement();
               }
            }
            return null;
         }
      });
      new CodeGen2EMFPlugin().initialize();
      ProjectManager.get().addPropertyChangeListener(ProjectManager.PROJECTS_PROPERTY, new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt)
         {
            Object newValue = evt.getNewValue();
            if (newValue == null)
            {
               FProject project = (FProject) evt.getOldValue();
               DLRProjectToken projectToken = DLRTool.get().getFromProjects(project);
               if (projectToken != null)
               {
                  projectToken.removeYou();
               }
            }
         }
      });
      return  (true);
   }

}

/*
 * $Log$
 * Revision 1.13  2006/12/06 11:33:18  cschneid
 * added @Property annotations to roles and attributes
 *
 */

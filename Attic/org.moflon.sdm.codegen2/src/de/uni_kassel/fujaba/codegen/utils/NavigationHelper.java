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
package de.uni_kassel.fujaba.codegen.utils;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.uni_kassel.fujaba.codegen.dlr.DLRProjectToken;
import de.uni_kassel.fujaba.codegen.dlr.DLRToken;
import de.uni_kassel.fujaba.codegen.dlr.DLRTool;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.project.ProjectManager;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public class NavigationHelper
{
   /**
    * @param offset
    * @param filename
    * @param project
    * @param isOffset
    */
   public static DLRToken findInFujaba(final int offset, final String filename, final boolean isOffset)
   {
      final Set<DLRToken> tokens = new HashSet<DLRToken>();
      Iterator<? extends FProject> iter = ProjectManager.get().iteratorOfProjects();
      while (iter.hasNext())
      {
         FProject umlproject = iter.next();
         DLRToken token = findInFujaba(offset, filename, umlproject, isOffset);
         if (token != null)
         {
            tokens.add (token);
         }
      }
      return tokens.isEmpty() ? null : tokens.iterator().next();
   }


   public static DLRToken findInFujaba(int offset, String filename, FProject umlproject, boolean isOffset)
   {
      DLRProjectToken projectToken = DLRImporter.get().getProjectTokenWithImport (umlproject);
      if (projectToken != null)
      {
         if (isOffset)
         {
            return DLRTool.get().findTokenByOffset(umlproject,
                  filename, offset);
         }
         else
         {
            return DLRTool.get().findTokenByLine(umlproject,
                  filename, offset);
         }
      }
      return null;
   }
}

/*
 * $Log$
 * Revision 1.3  2007/03/20 14:26:59  fklein
 * Removed references to deprecated code, used IModelAdapters instead
 * Revision 1.2 2007/01/31 11:32:59 l3_g5 extracted method
 * 
 * Revision 1.1 2006/10/13 13:56:14 l3_g5 emf and dlr working
 * 
 */

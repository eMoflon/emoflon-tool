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
package de.uni_paderborn.fujaba.uml.common;

import de.uni_kassel.coobra.Repository;
import de.uni_kassel.coobra.identifiers.IdentifierModule;
import de.uni_paderborn.fujaba.app.FrameMain;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.project.ProgressHandler;
import de.uni_paderborn.fujaba.project.ProjectLoader;
import de.uni_paderborn.fujaba.project.ProjectManager;
import de.uni_paderborn.fujaba.versioning.ActionTransactionListener;
import de.uni_paderborn.fujaba.versioning.Versioning;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class JarLoader extends ProjectLoader
{
   @Override
   protected FProject load (InputStream stream, File file, ProgressHandler progress) throws IOException
   {
      Repository repository = Versioning.get().setupRepositoryForNewProject (false, true);
      String jarId = file.getName();
      repository.setIdentifierModule (new IdentifierModule (repository,
         repository.getIdentifierModule().getManager(), jarId, true));
      JarProject jarProject = ProjectManager.get().getFromProjectFactories (JarProject.class).create (repository, true);
      setLoadingProject (jarProject);
      jarProject.setFile (file);
      jarProject.getRepository().setName (jarId);

      ActionTransactionListener.get().getActiveTransaction().setUndoable (false);

      // FIXME !!! remove UI access
      FrameMain.get().selectTreeItem (jarProject);
      return jarProject;
   }

   @Override
   public long estimateProjectLoadSize(File file)
   {
      return -1; // it does not matter what size the jat is
   }
}

/*
 * $Log$
 * Revision 1.2  2007/02/15 10:00:30  cschneid
 * do not return null in gerFromFactories, load projects with same FeatureAccessModule to speed up loading many projects, new libs
 *
 */

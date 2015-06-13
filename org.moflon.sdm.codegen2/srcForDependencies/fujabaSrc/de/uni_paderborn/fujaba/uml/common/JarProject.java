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
package de.uni_paderborn.fujaba.uml.common;

import de.uni_kassel.coobra.Repository;
import de.uni_paderborn.fujaba.asg.ASGProject;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;

import java.io.File;

public class JarProject extends ASGProject
{
   public JarProject(Repository repository)
   {
      this(repository, true);
   }

   public JarProject(Repository repository, boolean persistent)
   {
      super(repository, persistent);
   }

   @Override
   protected void initOwnFactories()
   {
      super.initOwnFactories();

      // we don't have to intialize factories right now 
   }

   private File jar;

   public File getFile()
   {
      return jar;
   }

   public FPackage getRootPackage()
   {
      return null;
   }

   public boolean isSaved()
   {
      return jar != null && jar.isFile();
   }

   public void setFile(File file)
   {
      File oldValue = jar;
      if (oldValue == null ? file != null : !oldValue.equals(file))
      {
         jar = file;
         firePropertyChange("file", oldValue, file);
         getRepository().setDescription(getName());
      }
   }

   public void setSaved(boolean value)
   {

   }

   public FPackage findPackage(String packageName, boolean create, boolean persistent)
   {
      throw new UnsupportedOperationException("deprecated");
   }

   @Override
   public String getName()
   {
      return jar != null ? jar.getName() : "";
   }

   @Override
   public void setName(String newName)
   {
      if(newName == null || !newName.equals(getName())) throw new UnsupportedOperationException("Cannot change the name of a jar project.");
   }

   @Override
   protected boolean allowReuseOfFeatureAccessModule()
   {
      return true;
   }

   public void setRootPackage(FPackage rootPackage)
   {
      throw new UnsupportedOperationException();
   }
}

/*
 * $Log$
 * Revision 1.5  2007/03/26 10:56:34  fklar
 * + activated template mechanism for automatic initialization of foreign factories and creation of initial products in ASGProject + automatic registration of new ASGProject implementation at ProjectManager
 *
 * Revision 1.4  2007/03/21 17:47:09  fklar
 * + prepared template mechanism for automatic initialization of foreign factories and creation of initial products in ASGProject
 * + projects are added to the ProjectManager from within the project constructor instead of the project factory (may be moved to ASGProject)
 * + moved initialization of factories for a project and creation of initial products from project factories to a project's constructor
 * + project initializers may now initialize projects of type 'JarProject'
 * + UMLProject is not set to 'saved' in its constructor any longer
 *
 * Revision 1.3  2007/02/15 10:00:30  cschneid
 * do not return null in gerFromFactories, load projects with same FeatureAccessModule to speed up loading many projects, new libs
 *
 * Revision 1.2  2007/02/08 13:43:29  cschneid
 * recover and project dependencies improved
 *
 * Revision 1.1  2007/02/07 13:48:17  cschneid
 * Jars can be opened as projects for dependencies, Classpath respects project dependencies
 *
 */


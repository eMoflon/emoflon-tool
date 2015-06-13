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
package de.uni_paderborn.fujaba.project;


import java.util.HashMap;

import de.uni_paderborn.lib.classloader.UPBClassLoader;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public abstract class FujabaPersistencySupport implements PersistencySupport
{
   /**
    * @see de.uni_paderborn.fujaba.project.PersistencySupport#getClassLoaderKey(java.lang.ClassLoader)
    */
   public String getClassLoaderKey(ClassLoader loader)
   {
      if (loader instanceof UPBClassLoader)
      {
         return ((UPBClassLoader) loader).getId();
      }
      else
      {
         return UPBClassLoader.DEFAULT_CLASSLOADER;
      }
   }

   /**
    * @see de.uni_paderborn.fujaba.project.PersistencySupport#getClassLoader(java.lang.String)
    */
   public ClassLoader getClassLoader(String key)
   {
      return UPBClassLoader.get(key);
   }


   /**
    * Get the pluginClassLoader attribute of the FujabaPersistencySupport object
    * 
    * @param loader No description provided
    * @return The pluginClassLoader value
    * 
    * @see de.uni_paderborn.fujaba.project.PersistencySupport#isPluginClassLoader(java.lang.ClassLoader)
    */
   public boolean isPluginClassLoader(ClassLoader loader)
   {
      if (loader instanceof UPBClassLoader)
      {
         return (!((UPBClassLoader) loader).getId().equals(
               UPBClassLoader.DEFAULT_CLASSLOADER));
      }
      else
      {
         return false;
      }
   }

   /**
    * Used by F4EPersistencySupport in conjunction with VersioningLoader. Not needed here.
    * 
    * @see de.uni_paderborn.fujaba.project.PersistencySupport#getClassLoaders()
    */
   public HashMap<String, ClassLoader> getClassLoaders()
   {
      return new HashMap<String, ClassLoader>();
   }
}

/*
 * $Log$ Revision 1.2 2006/03/29 09:51:13 fklar adjusted comment-structure to common style: 1.
 * file-prefix 'The FUJABA ToolSuite project: ...' 2. class-comment with 'Author, Revision, Date' 3.
 * file-postfix 'log'
 * 
 * Revision 1.1 2005/04/28 17:52:28 mm introduced strategy for loading and saving scan for
 * fujabaPlugin.xml as well
 */

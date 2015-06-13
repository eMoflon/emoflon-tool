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

import de.uni_kassel.features.ClassHandlerFactory;
import de.upb.lib.plugins.PluginProperty;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class PluginData
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String id;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String name;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int major;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int minor;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private int buildNumber;

   private ClassHandlerFactory classHandlerFactory;


   /**
    * Constructor for class PluginData
    *
    * @param id           No description provided
    * @param name         No description provided
    * @param major        No description provided
    * @param minor        No description provided
    * @param buildNumber  No description provided
    */
   public PluginData (String id, String name, int major, int minor, int buildNumber)
   {
      this.id = id;
      this.name = name;
      this.major = major;
      this.minor = minor;
      this.buildNumber = buildNumber;
   }


   /**
    * Get the id attribute of the PluginData object
    *
    * @return   The id value
    */
   public String getId()
   {
      return id;
   }


   /**
    * Get the major number of the project
    *
    * @return   The major value
    */
   public int getMajor()
   {
      return major;
   }


   /**
    * Get the minor number of the project
    *
    * @return   The minor value
    */
   public int getMinor()
   {
      return minor;
   }


   /**
    * Get the name of the project
    *
    * @return   The name value
    */
   public String getName()
   {
      return name;
   }


   /**
    * Get the build number of the project
    *
    * @return   The buildNumber value
    */
   public int getBuildNumber()
   {
      return buildNumber;
   }


   /**
    * @param id  The new id value
    */
   public void setId (String id)
   {
      this.id = id;
   }


   /**
    * @param major  The new major value
    */
   public void setMajor (int major)
   {
      this.major = major;
   }


   /**
    * @param minor  The new minor value
    */
   public void setMinor (int minor)
   {
      this.minor = minor;
   }


   /**
    * @param name  The new name value
    */
   public void setName (String name)
   {
      this.name = name;
   }


   /**
    * @param buildNumber  The new buildNumber value
    */
   public void setBuildNumber (int buildNumber)
   {
      this.buildNumber = buildNumber;
   }


   protected ClassHandlerFactory getClassHandlerFactory()
   {
      return this.classHandlerFactory;
   }


   protected void setClassHandlerFactory (ClassHandlerFactory classHandlerFactory)
   {
      this.classHandlerFactory = classHandlerFactory;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void checkPluginVersion()
   {
      String pluginID = getId();

      PluginProperty plugin = ProjectLoader.getPersistencySupport().getPluginProperty (pluginID);
      if (plugin != null)
      {
         if (plugin.getMajor() >= getMajor())
         {
            if ( (plugin.getMajor() == getMajor() &&
               plugin.getMinor() >= getMinor()) ||
               plugin.getMajor() > getMajor())
            {
            }
            else
            {
               // the available minor version is less than the needed minor version of plug-in
               throw new RuntimeException ("Plugin minor version is less than needed minor version\n\n" +
                  "\tNeeded Plug-In: \t \"" +
                  getName() + " version " + getMajor() + "." + getMinor() + "." + getBuildNumber() + "\"\n\t" +
                  "Available Plug-In: \t \"" + plugin.getName() + " version " + plugin.getMajor() + "." + plugin.getMinor() + "." + plugin.getBuildNumber() + "\"\n");
            }
         }
         else
         {
            // not the same major version
            throw new RuntimeException ("Plugin major version is less than needed major version\n\n" +
               "\tNeeded Plug-In: \t \"" +
               getName() + " version " + getMajor() + "." + getMinor() + "." + getBuildNumber() + "\"\n\t" +
               "Available Plug-In: \t \"" + plugin.getName() + " version " + plugin.getMajor() + "." + plugin.getMinor() + "." + plugin.getBuildNumber() + "\"\n");
         }
      }
      else
      {
         //todo: more sophisticated error handling
         //the plug-in with plug-in id does not exist
         new RuntimeException ("Plug-in \"" + getName() + "\" version " + getMajor() +
            "." + getMinor() + "." + getBuildNumber() + " is missing.\n" +
            "Please download and install the plug-in to load the project file.").printStackTrace();
      }
   }
}

/*
 * $Log$
 * Revision 1.4  2006/04/19 17:22:35  creckord
 * Hopefully fixed loading of FPR files with plugins
 *
 */

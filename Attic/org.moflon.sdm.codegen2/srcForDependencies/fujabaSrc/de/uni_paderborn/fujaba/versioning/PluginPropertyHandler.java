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

import de.uni_kassel.features.FeatureAccessModule;
import de.uni_kassel.features.reflect.DefaultClassHandler;
import de.uni_paderborn.fujaba.project.PluginData;
import de.uni_paderborn.fujaba.project.PluginNotFoundException;
import de.uni_paderborn.fujaba.project.ProjectLoader;
import de.upb.lib.plugins.PluginProperty;

import java.io.IOException;
import java.util.StringTokenizer;

class PluginPropertyHandler extends DefaultClassHandler
{
   public PluginPropertyHandler(FeatureAccessModule module) throws ClassNotFoundException
   {
      super(PluginProperty.class.getName(), module, module.getDefaultClassHandlerFactory());
   }

   @Override
   public void serialize(Object object, Appendable out) throws IOException
   {
      PluginProperty plugin = (PluginProperty) object;
      out.append(new StringBuilder()
            .append("$;").append(plugin.getName())
            .append(";").append(plugin.getPluginID())
            .append(";").append(plugin.getMajor())
            .append(";").append(plugin.getMinor())
            .append(";").append(plugin.getBuildNumber()).toString());
   }

   @Override
   public Object deserialize(String data)
   {
      StringTokenizer tokenizer = new StringTokenizer(data, ";");
      tokenizer.nextToken();
      String name = tokenizer.nextToken();
      String pluginId = tokenizer.nextToken();
      String major = tokenizer.nextToken();
      String minor = tokenizer.nextToken();
      String buildNumber = tokenizer.nextToken();
      PluginData pluginData = new PluginData(pluginId, name, Integer
            .parseInt(major), Integer.parseInt(minor), Integer
            .parseInt(buildNumber));
      pluginData.checkPluginVersion();

      PluginProperty plugin = ProjectLoader.getPersistencySupport().getPluginProperty(
            pluginId);
      if (plugin != null)
      {
         return plugin;
      } else
      {
         throw new PluginNotFoundException("Needed Plug-In \t \"" + name
               + " version " + major + "." + minor + "." + buildNumber
               + "\" not found!", pluginId);
      }
   }
}

/*
 * $Log$
 * Revision 1.1  2007/01/09 09:31:33  cschneid
 * cut/copy/paste dummys, requiredPlugins saved correctly with ctr
 *
 */


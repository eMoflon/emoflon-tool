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
package de.uni_paderborn.fujaba.basic;

import de.uni_paderborn.fujaba.app.FujabaApp;
import de.uni_paderborn.lib.classloader.UPBClassLoader;
import de.upb.lib.plugins.PluginManager;
import de.upb.lib.plugins.PluginProperty;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.StringTokenizer;


/**
 * A ClassLoader that unifies all available UPBClassLoaders (all plugins)
 *
 * @author $Author$
 * @version $Revision$
 */
public class UnifiedClassLoader extends ClassLoader
{
   /**
    * the only instance of this class
    */
   private static UnifiedClassLoader instance;


   /**
    * Loads the class with the specified name. The implementation of this method searches for
    * classes in the following order:<p>
    * <p/>
    * <p/>
    * <ol>
    * <li> call UPBClassLoader.get().loadClass( name, resolve )
    * <li> Iterate through plugin ClassLoaders and call loadClass( name, resolve )
    * </ol>
    *
    * @param name    the name of the class
    * @param resolve if <code>true</code> then resolve the class
    * @return the resulting <code>Class</code> object
    * @throws ClassNotFoundException if the class could not be found
    */
   @Override
   protected synchronized Class loadClass(String name, boolean resolve)
         throws ClassNotFoundException
   {
      try
      {
         return UPBClassLoader.get().loadClass(name, resolve);
      }
      catch (ClassNotFoundException e)
      {
         // FIXME !!! move plug-in handling to a UI class
         //main UPBClassLoader could not find the class
         //scan plugins
         PluginManager manager = FujabaApp.getPluginManager();
         if (manager != null)
         {
            Iterator pluginIter = manager.iteratorOfProperties();
            while (pluginIter.hasNext())
            {
               try
               {
                  PluginProperty property = (PluginProperty) pluginIter.next();
                  String id = property.getPluginID();
                  UPBClassLoader pluginClassLoader = UPBClassLoader.get(id);
                  return pluginClassLoader.loadClass(name, resolve);
               }
               catch (ClassNotFoundException ex)
               {
                  //go on
               }
            }
         }
      }
      //when we come here we could not load the class
      throw new ClassNotFoundException(name);
   }

   public String getClassPath()
   {
      StringBuffer cp = new StringBuffer(UPBClassLoader.get().getClassPath());
      
      // FIXME !!! move plug-in handling to a UI class
      PluginManager manager = FujabaApp.getPluginManager();
      if (manager != null)
      {
         Iterator pluginIter = manager.iteratorOfProperties();
         while (pluginIter.hasNext())
         {
            PluginProperty property = (PluginProperty) pluginIter.next();
            String id = property.getPluginID();
            UPBClassLoader pluginClassLoader = UPBClassLoader.get(id);
            StringTokenizer tokenizer = new StringTokenizer(pluginClassLoader.getClassPath(), File.pathSeparator);
            while (tokenizer.hasMoreTokens())
            {
               String path = tokenizer.nextToken();
               if ( cp.indexOf(path) < 0 ) {
                  cp.append(File.pathSeparator).append(path);
               }
            }
         }
      }
      return cp.toString();
   }


   /**
    * Finds the resource with the given name. A resource is some data (images, audio, text,
    * etc) that can be accessed by class code in a way that is independent of the location
    * of the code.<p>
    * <p/>
    * The name of a resource is a "/"-separated path name that identifies the resource.<p>
    * <p/>
    * This method will first search the UPBClassLoader for the resource; That failing, this
    * method will call getResource( name ) of all plugin class loaders.
    *
    * @param name resource name
    * @return a URL for reading the resource, or <code>null</code> if the resource could
    *         not be found or the caller doesn't have adequate privileges to get the resource.
    * @see #findResource(String)
    * @since JDK1.1
    */
   @Override
   public URL findResource(String name)
   {
      URL url = UPBClassLoader.get().getResource(name);
      if (url != null)
      {
         return url;
      }

      // FIXME !!! move plug-in handling to a UI class
      //main UPBClassLoader could not find the resource
      //scan plugins
      PluginManager manager = FujabaApp.getPluginManager();
      if (manager != null)
      {
         Iterator pluginIter = manager.iteratorOfProperties();
         String id;
         while (pluginIter.hasNext())
         {
            PluginProperty property = (PluginProperty) pluginIter.next();
            id = property.getPluginID();
            UPBClassLoader pluginClassLoader = UPBClassLoader.get(id);
            url = pluginClassLoader.getResource(name);
            if (url != null)
            {
               return url;
            }
         }
      }

      //when we come here we could not get the resource
      return null;
   }


   /**
    * ensures that no instances can be created by other classes
    */
   private UnifiedClassLoader()
   {
   }


   /**
    * @return an instance of ClassLoader that has the UnifiedClassLoader behaviour
    */
   public static UnifiedClassLoader get()
   {
      if (instance == null)
      {
         instance = new UnifiedClassLoader();
      }
      return instance;
   }
}

/*
 * $Log$
 * Revision 1.8  2007/01/26 14:29:57  cschneid
 * new actions: (un)collapse all, hide element (e.g. class), hide assoc;
 * fixed NPEs;
 * remove bends from polyline when line is removed;
 * UnifiedClassLoader exposes classpath;
 * new libs and adapted api-calls for those
 *
 * Revision 1.7  2006/04/06 12:04:34  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.6  2006/03/15 18:55:29  creckord
 * removed NPE if PluginManager is not initialized (i.e. for Fujaba4Eclipse)
 *
 */

/*
 * Copyright (c) 2004-2006 Software Engineering Kassel, various authors,
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'CoObRA' nor 'CoObRA2' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.uni_kassel.coobra.plugins;

import de.uni_kassel.coobra.server.DefaultServer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Manuel Bork - manuel.bork@uni-kassel.de
 * @version $Id: PluginManager.java 584 2008-10-23 14:38:46Z cschneid $
 */
public class PluginManager
{
   private static final Logger LOGGER = Logger.getLogger( PluginManager.class.getName() );

   private static final String PLUGINS = "Plugins";

   private final List<CoObRAPlugin> plugins = new LinkedList<CoObRAPlugin>();

   public PluginManager()
   {
      // read the manifest file
      String className = this.getClass().getSimpleName();
      String classFileName = className + ".class";
      String classFilePath = this.getClass().getPackage().toString().replace( '.', '/' ) + "/" + className;
      String pathToThisClass = this.getClass().getResource( classFileName ).toString();
      String pathToManifest = pathToThisClass.toString().substring( 0,
            pathToThisClass.length() + 2 - ( "/" + classFilePath ).length() )
            + "/META-INF/MANIFEST.MF";

      Manifest manifest;
      try
      {
         manifest = new Manifest( new URL( pathToManifest ).openStream() );
      }
      catch( MalformedURLException ex )
      {
         LOGGER.log( Level.SEVERE, "Error reading manifest: " + ex.getMessage(), ex );
         return;
      }
      catch( FileNotFoundException ex )
      {
         // this could indicate that we are not running out of a jar file but directly (in a test e.g.).
         LOGGER.log( Level.FINE, "No manifest file found - CoObRA Server PluginManager is disabled" );
         return;
      }
      catch( IOException ex )
      {
         LOGGER.log( Level.SEVERE, "Error reading manifest: " + ex.getMessage(), ex );
         return;
      }

      // get list of plugins
      String value = manifest.getMainAttributes().getValue( PLUGINS );
      if( value == null ) return;
      StringTokenizer st = new StringTokenizer( value );
      while( st.hasMoreTokens() )
      {
         String pluginName = st.nextToken();

         // instantiate the plugin
         CoObRAPlugin plugin = instantiate( pluginName );
         if( plugin != null ) plugins.add( plugin );
      }
   }

   public void setupAndStartPlugins( final DefaultServer defaultServer )
   {
      // initialise plugins
      for ( CoObRAPlugin plugin: plugins )
      {
         plugin.initialize( defaultServer );
      }

      // start a thread that checks if the configuration file for the DefaultServer has been changed
      if( plugins.size() > 0 )
      {
         new ConfigurationChecker( this, defaultServer ).start();
      }
   }

   private CoObRAPlugin instantiate( String pluginName )
   {
      try
      {
         ClassLoader cl = getClass().getClassLoader();
         Class<?> clazz = cl.loadClass( pluginName );

         // instantiate
         Object pluginInstance = clazz.newInstance();

         // verify the type
         if( pluginInstance instanceof CoObRAPlugin )
         {
            CoObRAPlugin plugin = (CoObRAPlugin) pluginInstance;
            return plugin;
         }
         else
         {
            LOGGER.log( Level.SEVERE, "Cannot instanziate a class of type '" + pluginInstance.getClass() + "'." );
         }
      }
      catch( Exception ex )
      {
         LOGGER.log( Level.SEVERE, "Error instanziating the class '" + pluginName + "': " + ex.getMessage(), ex );
      }

      return null;
   }

   public void reconfigure( Properties properties )
   {
      for ( CoObRAPlugin plugin: plugins )
      {
         plugin.reconfigure( properties );
      }
   }

   /**
    * @return list of available plugins
    */
   public List<CoObRAPlugin> getPlugins()
   {
      return this.plugins;
   }
}
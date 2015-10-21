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

import de.uni_kassel.coobra.persistency.FilePersistencyModule;
import de.uni_kassel.coobra.persistency.PersistencyModule;
import de.uni_kassel.coobra.server.DefaultServer;
import de.uni_kassel.coobra.server.NameServer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Checks if the configuration file for the DefaultServer has been changed.
 * 
 * @author Manuel Bork - manuel.bork@uni-kassel.de
 * @version $Id: ConfigurationChecker.java 584 2008-10-23 14:38:46Z cschneid $
 */
public class ConfigurationChecker extends Thread implements PropertyChangeListener
{
   public static final String CONFIG_FILE_EXTENSION = ".cfg";

   private static final Logger LOGGER = Logger.getLogger( ConfigurationChecker.class.getName() );

   private static final long A_WHILE = 60 * 1000; // 60 seconds

   private boolean running;

   private final File configurationFile;

   private long lastModified = 0;

   private final PluginManager pluginManager;

   public ConfigurationChecker( final PluginManager pluginManager, final DefaultServer defaultServer )
   {
      this.pluginManager = pluginManager;

      // register as listener to check for shutdown events
      defaultServer.addPropertyChangeListener( NameServer.SHUTDOWN_EVENT_NAME, this );

      // calculate the path to the configuration file
      PersistencyModule persistencyModule = defaultServer.getRepository().getPersistencyModule();
      if( persistencyModule instanceof FilePersistencyModule )
      {
         FilePersistencyModule filePersistencyModule = (FilePersistencyModule) persistencyModule;
         String pathToRepository = filePersistencyModule.getFile().getParent();
         if( pathToRepository == null )
         {
            pathToRepository = "";
         }
         configurationFile = new File( pathToRepository + File.separator + defaultServer.getRepositoryName()
               + CONFIG_FILE_EXTENSION );
         running = true;
      }
      else
      {
         LOGGER.log( Level.SEVERE, "Only repositories stored via FilePersistencyModule can be configured." );
         configurationFile = null;
         running = false;
      }
   }

   public void run()
   {
      while( running )
      {
         // check if a config file exists
         if( configurationFile.exists() )
         {
            // check if it has been changed since the last check
            if( lastModified < configurationFile.lastModified() )
            {
               try
               {
                  FileInputStream fileInputStream = new FileInputStream( configurationFile );
                  try
                  {
                     // read the configuration
                     Properties properties = new Properties();
                     properties.load( fileInputStream );
   
                     // notify plugins
                     pluginManager.reconfigure( properties );
                  }
                  catch( IOException ex )
                  {
                     LOGGER.log( Level.SEVERE, "Error reading properties file: " + ex.getMessage(), ex );
                  }
                  finally
                  {
                     try
                     {
                        fileInputStream.close();
                     } 
                     catch( IOException ex )
                     {
                        LOGGER.log( Level.SEVERE, "Error closing properties file: " + ex.getMessage(), ex );
                     }
                  }
               }
               catch( FileNotFoundException ex )
               {
                  LOGGER.log( Level.SEVERE, "Error reading properties file: " + ex.getMessage(), ex );
               }
            }
         }

         try
         {
            // sleep a while
            Thread.sleep( A_WHILE );
         }
         catch( InterruptedException ex )
         {
            ; // thats fine
         }
      }
   }

   public void propertyChange( PropertyChangeEvent evt )
   {
      if( !NameServer.SHUTDOWN_EVENT_NAME.equals( evt.getPropertyName() ) ) return;

      // stop this thread
      running = false;
      this.interrupt();
   }
}
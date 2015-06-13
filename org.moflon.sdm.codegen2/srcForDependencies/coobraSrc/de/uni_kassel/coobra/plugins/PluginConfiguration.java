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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Manuel Bork - manuel.bork@uni-kassel.de
 * @version $Id: PluginConfiguration.java 584 2008-10-23 14:38:46Z cschneid $
 */
public abstract class PluginConfiguration
{
   /**
    * Reads the given properties.<br/> Subclasses must override this method and set this plugin's
    * configuration according to the values in the given properties.
    * 
    * @param properties to read.
    */
   public abstract void read( final Properties properties );

   /**
    * @return a textual representation of this plugin's configuration.
    */
   public abstract String getConfigString();

   /**
    * Indicates if this plugin is enabled.
    * 
    * @return true if this plugin is enable.
    */
   public abstract boolean isEnabled();

   /**
    * Writes the current configuration into the given {@link Properties} and stores them into the
    * given file.<br/> Subclasses should override this method and fill the properties with the
    * current configuration.
    * 
    * @param properties to use.
    * @param file to store.
    * @throws FileNotFoundException if the given file could not be found.
    * @throws IOException if some IO error occurs.
    */
   public void write( final Properties properties, final File file ) throws FileNotFoundException, IOException
   {
      properties.store( new FileOutputStream( file ), "" );
   }
}
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


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import de.uni_paderborn.fujaba.basic.FileHistory;
import de.uni_paderborn.fujaba.metamodel.common.FProject;


/**
 * @author    christian.schneider@uni-kassel.de
 * @version   $Revision$ $Date$
 * @created   17.05.2005, 11:50:37
 */
public abstract class ProjectWriter
{

   public final static String SAVE_TASK = "Saving Project";


   /**
    * Write the project to a file.
    *
    *
    * @param project       what to write
    * @param outputFile    where to write
    * @param progress      progress handler to track saving progress
    * @throws IOException  if an IO error occures while writing
    */
   public final void saveProject (FProject project, File outputFile,
                                  ProgressHandler progress) throws IOException
   {
      saveProject (project, outputFile, isCompressed (outputFile), progress);
   }


   /**
    * Write the project to a new file. The project's file attribute keeps unchanged.
    *
    *
    * @param project       what to write
    * @param outputFile    where to write
    * @param progress      progress handler to track saving progress
    * @throws IOException  if an IO error occures while writing
    */
   public final void saveProjectAs (FProject project, File outputFile,
                                    ProgressHandler progress) throws IOException
   {
      saveProjectAs (project, outputFile, isCompressed (outputFile), progress);
   }


   protected boolean isCompressed (File outputFile)
   {
      return outputFile.getName().endsWith (".gz");
   }


   /**
    * Write the project to a file.
    *
    *
    * @param project       what to write
    * @param outputFile    where to write
    * @param compressed    true to enable gzip compression for the saved file
    * @param progress      progress handler to track saving progress
    * @throws IOException  if an IO error occures while writing
    */
   public final void saveProject (FProject project, File outputFile,
                                  boolean compressed, ProgressHandler progress) throws IOException
   {
      if (progress != null)
      {
         progress.setTask (SAVE_TASK + " '" + project + "'");
         progress.setTotalWork (getTotalWork());
         progress.start();
      }
      try
      {
         save (project, outputFile, progress, compressed);

         project.setSaved (true);

         FileHistory.get().addToHistory (outputFile);
         FileHistory.get().updateActions();
      }
      finally
      {
         if (progress != null)
         {
            progress.stop();
         }
      }
   }


   /**
    * Write the project to a new file. The project's file attribute keeps unchanged.
    *
    *
    * @param project       what to write
    * @param outputFile    where to write
    * @param compressed    true to enable gzip compression for the saved file
    * @param progress      progress handler to track saving progress
    * @throws IOException  if an IO error occures while writing
    */
   public final void saveProjectAs (FProject project, File outputFile,
                                    boolean compressed, ProgressHandler progress) throws IOException
   {
      if (progress != null)
      {
         progress.setTask (SAVE_TASK + " '" + project + "'");
         progress.setTotalWork (getTotalWork());
         progress.start();
      }
      try
      {
         saveAs (project, outputFile, progress, compressed);

         project.setSaved (true);

         FileHistory.get().addToHistory (outputFile);
         FileHistory.get().updateActions();
      }
      finally
      {
         if (progress != null)
         {
            progress.stop();
         }
      }
   }


   protected int getTotalWork()
   {
      return ProgressHandler.UNKNOWN;
   }


   protected OutputStream getOutputStream (File outputFile, boolean compressed)
       throws IOException
   {
      OutputStream out = new FileOutputStream (outputFile);
      if (compressed)
      {
         out = new GZIPOutputStream (out);
      }
      return out;
   }


   /**
    * Write the project to a stream. The file argument is for information purposes only. Always use
    * outputStream to write to, not file.
    *
    * This method is intended only to be called from
    * <ul>
    * <li>
    * {@link de.uni_paderborn.fujaba.project.ProjectWriter#saveProject(FProject, File, boolean, ProgressHandler)}
    * </li>
    * <li>
    * {@link de.uni_paderborn.fujaba.project.ProjectWriter#saveProject(FProject, File, ProgressHandler)}
    * </li>
    * </ul>
    *
    * If you call it from anywhere else, you have to make sure that the ProgressHandler is set up
    * and {@link de.uni_paderborn.fujaba.project.ProgressHandler#start() started} correctly
    * <b>before</b> this method is called and
    * {@link de.uni_paderborn.fujaba.project.ProgressHandler#stop() stopped} after it returns.
    *
    *
    * @param project       what to write
    * @param file          the file that the outputStream writes to, null if not writing to file or file is
    *           unknown
    * @param progress      progress handler to track saving progress
    * @param compressed
    * @throws IOException  if an IO error occures while writing
    */
   protected abstract void save (FProject project, File file,
                                 ProgressHandler progress, boolean compressed) throws IOException;


   /**
    * @param project
    * @param file
    * @param progress
    * @param compressed
    * @see                 #save(FProject, File, ProgressHandler, boolean)
    * @throws IOException
    */
   protected abstract void saveAs (FProject project, File file,
                                   ProgressHandler progress, boolean compressed) throws IOException;

}

/*
 * $Log$
 * Revision 1.9  2007/02/27 14:47:44  lowende
 * Introduced a "save as" feature into ProjectWriter to fix a bug in Reclipse.
 *
 */

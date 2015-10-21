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

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.ListIterator;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class SchemaFilter extends FileFilter implements FilenameFilter
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String description;
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private LinkedList extensions = new LinkedList();


   /**
    * Constructor for class SchemaFilter
    *
    * @param ext          No description provided
    * @param description  No description provided
    */
   public SchemaFilter (String ext, String description)
   {
      addExtension (ext.toLowerCase());
      this.description = description;
   }


   /**
    * Remove all extensions that are accepted.
    */
   public void clear()
   {
      extensions.clear();
   }


   /**
    * Access method for an one to n association.
    *
    * @param ext  The object added.
    */
   public void addExtension (String ext)
   {
      extensions.add (ext.toLowerCase());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param f  No description provided
    * @return   No description provided
    */
   @Override
   public boolean accept (File f)
   {
      if (f.isDirectory())
      {
         return true;
      }
      else
      {
         return accept (f, f.getName());
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param f     No description provided
    * @param name  No description provided
    * @return      No description provided
    */
   public boolean accept (File f, String name)
   {
      boolean result = false;

      if (f != null)
      {
         ListIterator iter = extensions.listIterator (0);
         while (iter.hasNext() && !result)
         {

            String ext = (String) iter.next();
            if (name.toLowerCase().endsWith (ext))
            {
               result = true;
            }
         }
      }

      return result;
   }


   /**
    * Get the description attribute of the SchemaFilter object
    *
    * @return   The description value
    */
   @Override
   public String getDescription()
   {
      return description;
   }


   /**
    * Analyzes the specified filename and returns the extension in lower case
    * letters.
    * <p>
    * If the given filename ends with "gz" or "zip" or "zip.gz" a preceeding
    * extension will be included in the returned value (e.g.,
    * 'extractFileExtension("myFile.myExtension.gz")' would return
    * "myExtension.gz").
    * 
    * @param fileName A filename.
    * @return The file extension.
    */
   public static String extractFileExtension (String fileName)
   {
      if (fileName == null)
      {
         return "";
      }

      String extension = "";
      {
         int lastIndexOfDot = fileName.lastIndexOf ('.');
         if (fileName.substring (lastIndexOfDot + 1).equals ("gz"))
         {
            lastIndexOfDot = fileName.lastIndexOf ('.', lastIndexOfDot - 1);
         }
         if (fileName.substring (lastIndexOfDot + 1).equals ("zip"))
         {
            lastIndexOfDot = fileName.lastIndexOf ('.', lastIndexOfDot - 1);
         }
         if (lastIndexOfDot > fileName.lastIndexOf (File.separatorChar))
         {
            extension = fileName.substring (lastIndexOfDot + 1);
         }
      }
      return extension.toLowerCase();
   }
}

/*
 * $Log$
 * Revision 1.24  2007/04/12 19:07:31  fklar
 * improved documentation
 *
 * Revision 1.23  2006/04/06 12:04:34  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.22  2006/04/05 11:57:49  fklar
 * added null-pointer check to method 'extractFileExtension(String)'
 *
 */

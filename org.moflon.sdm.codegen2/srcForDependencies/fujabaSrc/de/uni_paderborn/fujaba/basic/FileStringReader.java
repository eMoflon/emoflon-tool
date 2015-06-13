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
 *      Copyright (C) 1997-2004 Fujaba Development Group
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
 * Contact adress:
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

import java.io.*;
import org.apache.log4j.Logger;

import de.uni_paderborn.lib.classloader.UPBClassLoader;


/**
 * Class to go through a text file line by line. doCurrentLine need to be overwritten.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public abstract class FileStringReader
{
   /**
    * log4j logging
    */
   private final static transient Logger log = Logger.getLogger (FileStringReader.class);

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String comment = null;


   /**
    * Opens the textfile <fileName> and calls doCurrentLine for every line in it.
    *
    * @param fileName  No description provided
    * @return          No description provided
    */
   public boolean doFile (String fileName)
   {
      return doFile (fileName, UPBClassLoader.get());
   }


   /**
    * Opens the textfile <fileName> and calls doCurrentLine for every line in it.
    *
    * @param fileName     No description provided
    * @param classLoader  No description provided
    * @return             No description provided
    */
   public boolean doFile (String fileName, ClassLoader classLoader)
   {

      BufferedReader reader = null;
      InputStreamReader input = null;
      String currentLine;
      try
      {
         File file = new File (fileName);
         input = new FileReader (file);
         reader = new BufferedReader (input);
      }
      catch (FileNotFoundException e)
      {
         InputStream is = classLoader.getResourceAsStream (fileName);
         if (is != null)
         {
            input = new InputStreamReader (is);
            reader = new BufferedReader (input);
         }
         else
         {
            log.error ("Can not open file " + fileName);
         }
      }

      if (reader != null)
      {
         try
         {
            while ( (currentLine = reader.readLine()) != null)
            {
               if ( (comment != null) &&  (currentLine.indexOf (comment) != -1))
               {
                  currentLine = currentLine.substring (0, currentLine.indexOf (comment));
               }
               if (currentLine.length() > 0)
               {
                  doCurrentLine (currentLine);
               }
            }
            input.close();
            return true;
         }
         catch (IOException e)
         {
            try
            {
               input.close();
            }
            catch (IOException ex)
            {
            }
            e.printStackTrace();
         }
      }
      return false;
   }


   /**
    * This method is called for every line. Needs to be overwritten.
    *
    * @param currentLine  No description provided
    */
   protected abstract void doCurrentLine (String currentLine);

   // access methods

   /**
    * Sets the comment attribute of the FileStringReader object
    *
    * @param s  The new comment value
    */
   public void setComment (String s)
   {
      this.comment = s;
   }


   /**
    * Get the comment attribute of the FileStringReader object
    *
    * @return   The comment value
    */
   public String getComment()
   {
      return comment;
   }
}

/*
 * $Log$
 * Revision 1.12  2004/10/20 17:49:27  schneider
 * Introduction of interfaces for class diagram classes
 *
 */

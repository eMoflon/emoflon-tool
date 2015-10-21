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
package de.uni_paderborn.fujaba.logging;



/**
 * Information about a logger
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class LoggerInfo
{
   /*
    *  Fields for use when communicating between classes.
    */
   /**
    * Constant to represent that a logger should inherit its level
    */
   public final static String INHERITED = "Inherited";

   /**
    * Constant to represent the logging level DEBUG
    */
   public final static String DEBUG = "Debug";

   /**
    * Constant to represent the logging level INFO
    */
   public final static String INFO = "Info";

   /**
    * Constant to represent the logging level WARN
    */
   public final static String WARN = "Warn";

   /**
    * Constant to represent the logging level ERROR
    */
   public final static String ERROR = "Error";

   /**
    * Constant to represent the logging level FATAL
    */
   public final static String FATAL = "Fatal";

   /**
    * Constant to represent the logging level OFF (ie, do not log)
    */
   public final static String OFF = "Off";

   /**
    * Convenience array containing all the logging constants
    */
   public final static String[] LEVELS = new String[]{INHERITED, DEBUG, INFO, WARN, ERROR, FATAL, OFF};

   /**
    * The name of the logger
    */
   private String name;

   /**
    * The level the logger is logging at
    */
   private String level;


   /**
    * Creates a new LoggerInfo object
    *
    * @param aName   the name of the logger
    * @param aLevel  the level which the logger is logging at
    */
   public LoggerInfo (String aName, String aLevel)
   {
      name = aName;

      boolean found = false;
      try
      {
         setLevel (aLevel);
         found = true;
      }
      catch (Exception e)
      {
      }

      // following behaviour corresponds to Log4J behaviour at time of coding
      if (!found ||  ("root".equals (name) && INHERITED.equals (level)))
      { // scuse the hack ;)
         level = DEBUG;
      }
   }


   /**
    * Generate a string representing the info containing in this object
    *
    * @return   a String representation of this object
    */
   @Override
   public String toString()
   {
      return name + " - " + level;
   }


   /**
    * Get the name of the logger which this object contains info about
    *
    * @return   the name of the logger
    */
   public String getName()
   {
      return name;
   }


   /**
    * Get the level of the logger which this object contains info about
    *
    * @return   the level of the logger
    */
   public String getLevel()
   {
      return level;
   }


   /**
    * Set the level which the logger which this object contains info about should log at
    *
    * @param aLevel  the level the logger should log at
    */
   public void setLevel (String aLevel)
   {
      for (int i = 0; i < LEVELS.length; i++)
      {
         if (aLevel.equalsIgnoreCase (LEVELS[i]))
         {
            level = LEVELS[i];
            return;
         }
      }
      throw new RuntimeException ("Can't set level " + aLevel + ".");
   }
}

/*
 * $Log$
 * Revision 1.7  2006/04/06 12:05:08  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.6  2004/10/20 17:49:59  schneider
 * Introduction of interfaces for class diagram classes
 *
 */

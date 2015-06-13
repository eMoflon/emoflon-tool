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
package de.uni_paderborn.fujaba.parser;

import java.util.Iterator;

import de.upb.tools.fca.FEmptyIterator;
import de.upb.tools.fca.FHashSet;


/**
 * <h2>Associations</h2>
 *
 * <pre>
 *         0..1      currentParser      0..1
 * Parser <---------------------------------- ParserManager
 *         currentParser      parserManager
 *
 *         0..n     parsers      0..1
 * Parser ---------------------------- ParserManager
 *         parsers      parserManager
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class ParserManager
{

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static ParserManager theParserManager = null;


   /**
    * Constructor for class ParserManager
    */
   private ParserManager() { }


   /**
    * Get the instance attribute of the ParserManager class
    *
    * @return   The instance value
    */
   public static ParserManager get()
   {
      if (theParserManager == null)
      {
         theParserManager = new ParserManager();
      }

      return theParserManager;
   }


   /**
    * <pre>
    *         0..1      currentParser      0..1
    * Parser <---------------------------------- ParserManager
    *         currentParser      parserManager
    * </pre>
    */
   private Parser currentParser;


   /**
    * Sets the current parser of the ParserManager
    *
    * @param value  The new current parser
    * @return       No description provided
    */
   public boolean setCurrentParser (Parser value)
   {
      boolean changed = false;
      if ( (this.currentParser == null && value != null) ||
          (this.currentParser != null && !this.currentParser.equals (value)))
      {
         this.currentParser = value;
         changed = true;
      }
      return changed;
   }


   /**
    * Get the current parser of the ParserManager
    *
    * @return   The current parser
    */
   public Parser getCurrentParser()
   {
      return this.currentParser;
   }


   /**
    * <pre>
    *         0..n     parsers      0..1
    * Parser ---------------------------- ParserManager
    *         parsers      parserManager
    * </pre>
    */
   private FHashSet parsers;


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   public boolean addToParsers (Parser value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.parsers == null)
         {
            this.parsers = new FHashSet();
         }
         changed = this.parsers.add (value);
         if (changed)
         {
            value.setParserManager (this);
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public Iterator iteratorOfParsers()
   {
      return  ( (this.parsers == null)
         ? FEmptyIterator.get()
         : this.parsers.iterator());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean hasInParsers (Parser value)
   {
      return  ( (this.parsers != null) &&
          (value != null) &&
         this.parsers.contains (value));
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfParsers()
   {
      return  ( (this.parsers == null)
         ? 0
         : this.parsers.size());
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean removeFromParsers (Parser value)
   {
      boolean changed = false;
      if ( (this.parsers != null) &&  (value != null))
      {
         changed = this.parsers.remove (value);
         if (changed)
         {
            value.setParserManager (null);
         }
      }
      return changed;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromParsers()
   {
      Parser tmpValue;
      Iterator iter = this.iteratorOfParsers();
      while (iter.hasNext())
      {
         tmpValue = (Parser) iter.next();
         this.removeFromParsers (tmpValue);
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeYou()
   {
      removeAllFromParsers();
      Parser tmpCurrentParser = getCurrentParser();
      if (tmpCurrentParser != null)
      {
         setCurrentParser (null);
      } // if
   }

}

/*
 * $Log$
 * Revision 1.4  2005/03/14 08:39:52  lowende
 * Enhanced the parser interface. The JavaParser 3.0 version won't work.
 * A new JavaParser version 3.1 will be released soon.
 *
 */

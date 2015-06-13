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

import java.awt.*;

import de.upb.tools.fca.FHashMap;


/**
 * A class to encapsulate a container for fonts.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public final class FontContainer
{
   /**
    * The default font type for the panel.
    */
   public final static int DEFAULT_FONT = 0;

   /**
    * The font for java sources.
    */
   public final static int JAVA_SOURCE_FONT = 1;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int DEFAULT_FONT_SIZE = 12;

   // the last possible value is 99

   // scale default font size
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private static int scale = 0;


   /**
    * Read access method for scale value
    *
    * @return   The scale value
    */
   public static int getScale()
   {
      return scale;
   }


   /**
    * Write access method for scale value
    *
    * @param newScale  The new scale value
    */
   public static void setScale (int newScale)
   {
      scale = newScale;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   protected final static FHashMap fonts = new FHashMap();


   /**
    * Get the font attribute of the FontContainer class
    *
    * @param type   No description provided
    * @param style  No description provided
    * @param size   No description provided
    * @return       The font value
    */
   public static Font getFont (int type, int style, int size)
   {
      if ( (type < 0) ||  (type > 99))
      {
         throw new IllegalArgumentException ("illegal font type");
      }

      if ( (size < -99) ||  (size > 99))
      {
         throw new IllegalArgumentException ("illegal font size");
      }

      Integer key = new Integer (type +  (size + scale + 100) * 100 + style * 100000);
      Font font = (Font) fonts.get (key);

      if (font == null)
      {
         if (type == DEFAULT_FONT)
         {
            font = new Font ("SansSerif", style, DEFAULT_FONT_SIZE + size + scale);
         }
         else if (type == JAVA_SOURCE_FONT)
         {
            font = new Font ("Monospaced", style, DEFAULT_FONT_SIZE + size + scale);
         }
         else
         {
            throw new IllegalArgumentException ("illegal font type");
         }

         fonts.put (key, font);
      }

      return font;
   }


   /**
    * Get the font attribute of the FontContainer class
    *
    * @param type  No description provided
    * @param size  No description provided
    * @return      The font value
    */
   public static Font getFont (int type, int size)
   {
      return getFont (type, 0, size);
   }


   /**
    * Get the font attribute of the FontContainer class
    *
    * @param type  No description provided
    * @return      The font value
    */
   public static Font getFont (int type)
   {
      return getFont (type, 0, 0);
   }


   /**
    * Return the default font.
    *
    * @return   The defaultFont value
    */
   public static Font getDefaultFont()
   {
      return getFont (DEFAULT_FONT, 0, 0);
   }


   /**
    * Return the java source font.
    *
    * @return   The javaSourceFont value
    */
   public static Font getJavaSourceFont()
   {
      return getFont (JAVA_SOURCE_FONT, 0, 0);
   }

}

/*
 * $Log$
 * Revision 1.23  2006/06/01 15:06:44  lowende
 * Some 'bugs' reported by FindBugs removed.
 *
 */

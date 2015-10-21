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
package de.uni_paderborn.fujaba.metamodel.structure;

import de.uni_paderborn.fujaba.metamodel.common.FDiagramItem;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_kassel.features.ReferenceHandler;



/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FDeclaration extends FDiagramItem
{

   // Declaration interface constants
   /**
    * indicates a declaration element as internal used element. It is not shown as default.
    */
   public final static int CODE_DISPLAY_LEVEL = 0;

   /**
    * indicates a declaration element as user element. It is shown as default.
    */
   public final static int DESIGN_DISPLAY_LEVEL = FDeclaration.CODE_DISPLAY_LEVEL + 1;

   /**
    * indicates a declaration element as a private element.
    */
   public final static int PRIVATE = 0;

   /**
    * indicates a declaration element as a public element.
    */
   public final static int PUBLIC = FDeclaration.PRIVATE + 1;

   /**
    * indicates a declaration element as a protected element.
    */
   public final static int PROTECTED = FDeclaration.PUBLIC + 1;

   /**
    * indicates a declaration element as a package element.
    */
   public final static int PACKAGE = FDeclaration.PROTECTED + 1;

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static int USERDEFINED = FDeclaration.PACKAGE + 1;

   /**
    * declares the visibility characters.
    */
   public final static char[] VISIBILITY_CHAR = new char[]
      {'-', '+', '#', '~', '!'};

   /**
    * declares the visibility strings.
    */
   public final static String[] VISIBILITY_STRING = new String[]
      {"private", "public", "protected", "/*package*/", "/*user*/"};

   // --- Property visibility ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String VISIBILITY_PROPERTY = "visibility";


   /**
    * Model visibility of the declaration.
    *
    * @return   one from {@link #PUBLIC}, {@link #PRIVATE}, {@link #PROTECTED}, {@link #PACKAGE}, {@link #USERDEFINED}
    */
   @Property(name=VISIBILITY_PROPERTY)
   public int getVisibility();


   /**
    * Change model visibility of the declaration.
    *
    * @param visibility  new visibility value
    * @see               #getVisibility()
    */
   @Property(name=VISIBILITY_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setVisibility (int visibility);


   public static final String DISPLAY_LEVEL_PROPERTY = "displayLevel";


   /**
    * returns the display level of the declaration element.
    *
    * @return   the display level.
    */
   @Property(name=DISPLAY_LEVEL_PROPERTY)
   public int getDisplayLevel();


   /**
    * sets the display level of the declaration element.
    *
    * @param newDisplayLevel  the new display level.
    */
   @Property(name=DISPLAY_LEVEL_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setDisplayLevel (int newDisplayLevel);


   public static final String PARSED_PROPERTY = "parsed";


   /**
    * Indicates if this declaration was parsed from source code.
    *
    * @param parsed  true, if was parsed from source code
    */
   @Property(name=PARSED_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setParsed (boolean parsed);


   /**
    * Indicates if this declaration was parsed from source code.
    *
    * @return   parsed  true, if was parsed from source code
    */
   @Property(name=PARSED_PROPERTY)
   public boolean isParsed();
}

/*
 * $Log$
 * Revision 1.6  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.5  2006/04/27 14:35:28  cschneid
 * "uml" prefix removed from abstract, static and visibility; added 'undoable' flag to CompositeTransaction
 *
 */

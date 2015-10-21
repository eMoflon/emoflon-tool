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
package de.uni_paderborn.fujaba.uml.structure;


import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FDeclaration;
import de.uni_paderborn.fujaba.uml.common.UMLDiagramItem;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public abstract class UMLDeclaration extends UMLDiagramItem implements
   FDeclaration
{

   protected UMLDeclaration (FProject project, boolean persistent)
   {
      super (project, persistent);
   }


   /**
    * Indicates if the declaration (like class, attribute, method) was parsed from source code.
    */
   private boolean parsed = false;


   /**
    * Indicates if this declaration was parsed from source code.
    *
    * @param parsed  true, if was parsed from source code
    */
   public void setParsed (boolean parsed)
   {
      if (this.parsed != parsed)
      {
         this.parsed = parsed;
         firePropertyChange (FDeclaration.PARSED_PROPERTY, !parsed, parsed);
      }
   }


   /**
    * @return
    * @see      de.uni_paderborn.fujaba.metamodel.structure.FDeclaration#isParsed()
    */
   public boolean isParsed()
   {
      return this.parsed;
   }


   /**
    * @see   #getVisibility()
    */
   private int visibility = PUBLIC;


   /**
    * @return
    * @see      de.uni_paderborn.fujaba.metamodel.structure.FDeclaration#getVisibility()
    */
   public int getVisibility()
   {
      return visibility;
   }


   /**
    * @param visibility
    * @see               de.uni_paderborn.fujaba.metamodel.structure.FDeclaration#setVisibility(int)
    */
   public void setVisibility (int visibility)
   {
      if (this.visibility != visibility)
      {
         int oldValue = this.visibility;
         this.visibility = visibility;
         firePropertyChange (VISIBILITY_PROPERTY, oldValue, visibility);
      }
   }


   /**
    * declares the display level of the declaration element.
    */
   private int displayLevel = FDeclaration.DESIGN_DISPLAY_LEVEL;


   /**
    * @return
    * @see      de.uni_paderborn.fujaba.metamodel.structure.FDeclaration#getDisplayLevel()
    */
   public int getDisplayLevel()
   {
      return this.displayLevel;
   }


   /**
    * @param newDisplayLevel
    * @see                    de.uni_paderborn.fujaba.metamodel.structure.FDeclaration#setDisplayLevel(int)
    */
   public void setDisplayLevel (int newDisplayLevel)
   {
      if (newDisplayLevel != this.displayLevel)
      {
         int oldDisplayLevel = this.displayLevel;
         this.displayLevel = newDisplayLevel;
         firePropertyChange (FDeclaration.DISPLAY_LEVEL_PROPERTY, oldDisplayLevel, newDisplayLevel);
      }
   }

}

/*
 * $Log$
 * Revision 1.8  2007/03/23 12:45:06  l3_g5
 * annotations for copy-paste
 *
 */

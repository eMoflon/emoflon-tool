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
package de.uni_paderborn.fujaba.uml.behavior;

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.uml.common.UMLIncrement;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLStatementActivity extends UMLActivity
{

   public static final String STATE_PROPERTY = "state";


   /**
    * Constructor for class UMLStatementActivity
    *
    * @param project
    * @param persistent
    */
   protected UMLStatementActivity (FProject project, boolean persistent)
   {
      super (project, persistent);
   }

   
	
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property(name=STATE_PROPERTY, partner=UMLStatement.ACTIVITY_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.COMPOSITION)
   private UMLStatement state; // reverse UMLStatementActivity activity


   /**
    * Get the state attribute of the UMLStatementActivity object
    *
    * @return   The state value
    */
   public UMLStatement getState()
   {
      return state;
   }


   /**
    * Sets the state attribute of the UMLStatementActivity object
    *
    * @param state  The new state value
    */
   public void setState (UMLStatement state)
   {
      if (this.state != state)
      { // new partner

         UMLStatement oldState = this.state;
         if (this.state != null)
         { // inform old partner

            this.state = null;
            oldState.setActivity (null);
         }
         this.state = state;
         if (state != null)
         { // inform new partner

            state.setActivity (this);
         }
         firePropertyChange (STATE_PROPERTY, oldState, state);
      }
   }


   public String getStatement ()
   {
	   if (getState() == null) return null;
	   
	   return getState().getStatement();
   }
   
   public void setStatement (String txt)
   {
	   UMLStatement stat = getState();
	   
	   if (stat == null)
	   {
		   stat = this.getProject().getFromFactories(UMLStatement.class).create();
		   this.setState(stat);
	   }
	   stat.setStatement(txt);
   }
   
   /**
    * Get the text attribute of the UMLStatementActivity object
    *
    * @return   The text value
    */
   @Override
   public String getText()
   {
      if (getName() != null)
      {
         return getName();
      }
      else
      {
         return "Statement";
      }
   }


   /**
    * Isolates the object so the garbage collector can remove it.
    */
   @Override
   public void removeYou()
   {
      UMLIncrement incr;

      incr = getState();
      if (incr != null)
      {
         setState (null);
         incr.removeYou();
      }

      super.removeYou();
   }
}

/*
 * $Log$
 * Revision 1.5  2006/08/17 08:57:56  zuendorf
 * Some enhancement of the Link Editor ported from Fujaba 4
 * Some utilities added for class retrival
 * Some virtual access methods added for convinience
 *
 * Revision 1.4  2006/04/06 12:05:54  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.3  2006/03/29 09:51:11  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.2  2006/03/01 12:22:49  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

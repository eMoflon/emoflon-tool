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
package de.uni_paderborn.fujaba.metamodel.common;

import de.uni_paderborn.fujaba.asg.ASGElement;
import de.upb.tools.sdm.JavaSDM;

import java.util.Set;

/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class CodeStyle extends ASGElement implements FCodeStyle
{
   protected CodeStyle( FProject project, boolean persistent )
   {
      super( project, persistent );
   }

   /**
    * getter for field name
    *
    * @return current value of field name
    */
   @Override
   public String getName()
   {
      return this.name;
   }

   /**
    * store the value for field name
    */
   private String name;

   /**
    * setter for field name
    *
    * @param value new value
    */
   @Override
   public void setName( final String value )
   {
      final String oldValue = this.name;
      if ( ! JavaSDM.stringEquals(oldValue, value))
      {
         this.name = value;
         firePropertyChange( "name", oldValue, value );
      }
   }


   /**
    * getter for field externalizedName
    *
    * @return current value of field externalizedName
    */
   public String getExternalizedName()
   {
      return this.externalizedName;
   }

   /**
    * store the value for field externalizedName
    */
   private String externalizedName;

   /**
    * setter for field externalizedName
    *
    * @param value new value
    */
   public void setExternalizedName( final String value )
   {
      final String oldValue = this.externalizedName;
      if ( ! JavaSDM.stringEquals(oldValue, value) )
      {
         this.externalizedName = value;
         firePropertyChange( "externalizedName", oldValue, value );
      }
   }

   /**
    * getter for field description
    *
    * @return current value of field description
    */
   public String getDescription()
   {
      return this.description;
   }

   /**
    * store the value for field description
    */
   private String description;

   /**
    * setter for field description
    *
    * @param value new value
    */
   public void setDescription( final String value )
   {
      final String oldValue = this.description;
      if ( ! JavaSDM.stringEquals(oldValue, value) )
      {
         this.description = value;
         firePropertyChange( "description", oldValue, value );
      }
   }

   public Set<FCodeStyleKeyDescription> getSupportedKeys()
   {
      return null;
   }

   public String getStyleInformation( String key )
   {
      return null;
   }

   @Override
   public String toString()
   {
      return getName() != null ? getName() : "";
   }
}

/*
 * $Log$
 * Revision 1.1  2007/03/21 12:47:48  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 */


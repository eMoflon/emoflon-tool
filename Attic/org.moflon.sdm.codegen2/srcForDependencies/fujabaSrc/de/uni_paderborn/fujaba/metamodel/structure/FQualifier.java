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

import de.uni_paderborn.fujaba.metamodel.common.FIncrement;



/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FQualifier extends FIncrement
{
   // --- Property externalQualifier ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String QUALIFIED_ROLE_PROPERTY = "externalQualifier";
   public final static String EXTERNAL_QUALIFIER_PROPERTY = QUALIFIED_ROLE_PROPERTY;


   /**
    * Get the externalQualifier attribute of the FQualifier object
    *
    * @return   The externalQualifier value
    */
   public abstract boolean isExternalQualifier();

   // --- Property revQualifier ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String REV_QUALIFIER_PROPERTY = "revQualifier";


   /**
    * Get the revQualifier attribute of the FQualifier object
    *
    * @return   The revQualifier value
    */
   public abstract FRole getRevQualifier();


   /**
    * Sets the revQualifier attribute of the FQualifier object
    *
    * @param revQualifier  The new revQualifier value
    */
   public abstract void setRevQualifier (FRole revQualifier);

   // --- Property qualifiedAttr ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String QUALIFIED_ATTR_PROPERTY = "qualifiedAttr";


   /**
    * FMethod: '+ setQualifiedAttr (value: FAttr): Boolean'
    *
    * @param value  The new qualifiedAttr value
    * @return       No description provided
    */
   public abstract boolean setQualifiedAttr (FAttr value);


   /**
    * FMethod: '+ getQualifiedAttr (): FAttr'
    *
    * @return   The qualifiedAttr value
    */
   public abstract FAttr getQualifiedAttr();

   // --- Property type ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String TYPE_PROPERTY = "type";


   /**
    * Get the type attribute of the FQualifier object
    *
    * @return   The type value
    */
   public abstract FType getType();


   /**
    * Sets the type attribute of the FQualifier object
    *
    * @param type  The new type value
    */
   public abstract void setType (FType type);


   /**
    * Sets the qualifiedRole attribute of the FQualifier object
    *
    * @param qualRole  The new qualifiedRole value
    * @return          No description provided
    */
   public abstract boolean setQualifiedRole (FRole qualRole);
}

/*
 * $Log$
 * Revision 1.3  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 */

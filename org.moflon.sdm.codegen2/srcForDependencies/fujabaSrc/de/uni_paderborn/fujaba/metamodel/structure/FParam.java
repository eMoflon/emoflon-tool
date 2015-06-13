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

import de.fujaba.text.FTextReference;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.Property;




/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FParam extends FDeclaration, FTextReference
{
   // --- Property revParam ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String REV_PARAM_PROPERTY = "revParam";


   /**
    * Get the revParam attribute of the FParam object
    *
    * @return   The revParam value
    */
   @Property(name=REV_PARAM_PROPERTY)
   public abstract FMethod getRevParam();


   /**
    * Sets the revParam attribute of the FParam object
    *
    * @param revParam  The new revParam value
    */
   @Property(name=REV_PARAM_PROPERTY, partner=FMethod.PARAM_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   public abstract void setRevParam (FMethod revParam);

   // --- Property paramType ---
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String PARAM_TYPE_PROPERTY = "paramType";


   /**
    * Get the paramType attribute of the FParam object
    *
    * @return   The paramType value
    */
   @Property(name=PARAM_TYPE_PROPERTY)
   public abstract FType getParamType();


   /**
    * Sets the paramType attribute of the FParam object
    *
    * @param paramType  The new paramType value
    */
   @Property(name=PARAM_TYPE_PROPERTY, partner=FType.REV_PARAM_TYPE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.USAGE)
   public abstract void setParamType (FType paramType);
}

/*
 * $Log$
 * Revision 1.3  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.2  2006/01/06 17:09:16  rotschke
 * Removed deprecated property pointer from FParam interface [tr].
 *
 */

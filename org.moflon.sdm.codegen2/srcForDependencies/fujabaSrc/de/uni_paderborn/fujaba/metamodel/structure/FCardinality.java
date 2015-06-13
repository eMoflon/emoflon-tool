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
import de.uni_kassel.features.annotation.util.Property;
import de.uni_kassel.features.ReferenceHandler;



/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FCardinality extends FIncrement
{
   public final static String CARD_1 = "1";

   public final static String CARD_0_1 = "0..1";

   public final static String CARD_0_N = "0..*";

   public final static String CARD_1_N = "1..*";

   // --- Property lowerBound ---
   public final static String LOWER_BOUND_PROPERTY = "lowerBound";


   @Property(name=LOWER_BOUND_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public int getLowerBound();


   // --- Property upperBound ---
   public final static String UPPER_BOUND_PROPERTY = "upperBound";


   @Property(name=UPPER_BOUND_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public int getUpperBound();


   // --- Property cardString ---
   public final static String CARD_STRING_PROPERTY = "cardString";


   @Property(name=CARD_STRING_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public String getCardString();

}

/*
 * $Log$
 * Revision 1.5  2007/03/26 10:49:25  l3_g5
 * prevent exceptions when saving with fpr
 *
 * Revision 1.4  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.3  2006/12/12 16:12:33  cschneid
 * remove old workspace backup files, FCardinality.CardString made readonly, renamed FprGenTask, new libs
 *
 */

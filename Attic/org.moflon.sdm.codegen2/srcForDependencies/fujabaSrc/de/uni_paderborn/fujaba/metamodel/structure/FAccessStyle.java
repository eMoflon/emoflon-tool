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
package de.uni_paderborn.fujaba.metamodel.structure;

import de.uni_paderborn.fujaba.metamodel.common.FCodeStyle;
import de.uni_paderborn.fujaba.metamodel.common.FElement;

import java.util.Set;

/**
 * An access style encapsulates the information needed by a code generator to support different
 * code conventions to manipulate objects (create/destroy, get/set/iterate...). The information
 * returned is usually specific to a particular code generator.
 * 
 * @author creckord (last edited by $Author$)
 * @version $Revision$ $Date$
 * @deprecated use {@link de.uni_paderborn.fujaba.metamodel.common.FCodeStyle}
 */
@Deprecated
public interface FAccessStyle extends FCodeStyle
{
}

/*
 * $Log$
 * Revision 1.3  2007/03/21 12:47:49  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 * Revision 1.2  2006/05/24 08:44:42  cschneid
 * use Project factories in Versioning, selection manager can select not-displayed elements, Project tree displays packages, more dummy code for access sytles
 *
 * Revision 1.1  2006/05/04 09:16:26  creckord
 * re-added FAccessedElement,
 * new class FAccessStyle for access code handling
 * 
 */
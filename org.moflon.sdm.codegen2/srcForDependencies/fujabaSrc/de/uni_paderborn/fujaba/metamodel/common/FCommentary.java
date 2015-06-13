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

import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.Property;


/**
 * @author $Author$
 * @version $Revision$ $Date$
 */
public interface FCommentary extends FIncrement
{

   // --- Property revComment ---
   public final static String REV_COMMENT_PROPERTY = "revComment";


   @Property(name = REV_COMMENT_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
            partner = FIncrement.COMMENT_PROPERTY, adornment = ReferenceHandler.Adornment.NONE,
            accessFragment = AccessFragment.GETTER )
   public FIncrement getRevComment();

   @Property(name = REV_COMMENT_PROPERTY, accessFragment = AccessFragment.SETTER)
   public void setRevComment(FIncrement revComment);


   // --- Property text ---
   public String setText(String text);

}

/*
 * $Log$
 * Revision 1.3  2006/03/29 15:49:44  lowende
 * Changed creating/editing/hiding/showing of UMLCommentary.
 *
 */

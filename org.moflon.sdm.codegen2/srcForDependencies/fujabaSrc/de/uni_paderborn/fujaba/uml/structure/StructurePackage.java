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

import de.uni_paderborn.fujaba.metamodel.structure.FAssoc;
import de.uni_paderborn.fujaba.metamodel.structure.FAttr;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FClassDiagram;
import de.uni_paderborn.fujaba.metamodel.structure.FGeneralization;
import de.uni_paderborn.fujaba.metamodel.structure.FMethod;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.metamodel.structure.FParam;
import de.uni_paderborn.fujaba.metamodel.structure.FQualifier;
import de.uni_paderborn.fujaba.metamodel.structure.FRole;
import de.uni_paderborn.fujaba.uml.common.UMLProject;
import de.uni_paderborn.fujaba.uml.factories.UMLGenericFactory;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class StructurePackage
{
   /**
    * Constructor for class StructurePackage
    */
   private StructurePackage() { }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param project  No description provided
    */
   public static void registerFactories (UMLProject project)
   {
      project.addToFactories (new UMLGenericFactory<FClass>(project, FClass.class, UMLClass.class));
      project.addToFactories (new UMLGenericFactory<FPackage>(project, FPackage.class, UMLPackage.class));
      project.addToFactories (new UMLGenericFactory<FClassDiagram>(project, FClassDiagram.class, UMLClassDiagram.class));
      project.addToFactories (new UMLArrayFactory (project));
      project.addToFactories (new UMLBaseTypeFactory (project));
      project.addToFactories (new UMLGenericFactory<FMethod>(project, FMethod.class, UMLMethod.class));
      project.addToFactories (new UMLGenericFactory<FParam>(project, FParam.class, UMLParam.class));
      project.addToFactories (new UMLGenericFactory<FAttr>(project, FAttr.class, UMLAttr.class));
      project.addToFactories (new UMLGenericFactory<FGeneralization>(project, FGeneralization.class, UMLGeneralization.class));
      project.addToFactories (new UMLGenericFactory<FAssoc>(project, FAssoc.class, UMLAssoc.class));
      project.addToFactories (new UMLGenericFactory<FRole>(project, FRole.class, UMLRole.class));
      project.addToFactories (new UMLCardinalityFactory (project));
      project.addToFactories (new UMLGenericFactory<FQualifier>(project, FQualifier.class, UMLQualifier.class));
      project.addToFactories (new UMLTypeFactory (project));
   }
}

/*
 * $Log$
 * Revision 1.3  2006/03/29 09:51:08  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.2  2006/03/01 12:22:59  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

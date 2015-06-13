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


import java.util.Iterator;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FStereotype extends FElement
{
   public final static String TYPE = "type";

   public final static String INTERFACE = "interface";

   public final static String REFERENCE = "reference";
   
   /**
    * Normally, no "super.removeYou()"-call is added to the destructor of a subclass if its superclass is tagged as
    * reference. To add this call to destructor of the subclass the stereotype "ReferenceWithRemoveYou" can be added to
    * the superclass, so it has both the "reference" and the "ReferenceWithRemoveYou" stereotype.
    * (evaluated by CodeGen2 in "java/default/classDiag/class/destructor.vm")
    */
   public final static String REFERENCE_WITH_REMOVE_YOU = "ReferenceWithRemoveYou";

   public final static String IMMUTABLE = "immutable";

   /**
    * Stereotype name for JavaBean. JavaBeans ususally generate property changes and
    * provide a subsciption point for listeners.
    */
   public final static String JAVA_BEAN = "JavaBean";

   /**
    * Stereotype name for the 'usage' stereotype of roles.
    * @see de.uni_kassel.features.ReferenceHandler.Adornment#USAGE
    */
   public final static String USAGE = "usage";

   public final static String FINAL = "final";

   public final static String NATIVE = "native";

   public final static String POINTER = "pointer";

   public final static String SIGNAL = "signal";

   public final static String STATIC = "static";

   public final static String SYNCHRONIZED = "synchronized";

   public final static String TRANSIENT = "transient";

   public final static String VOLATILE = "volatile";

   public final static String VIRTUAL_PATH = "Virtual Path";
   
   /**
    * Associations and roles may be tagged with stereotype "void".
    * This changes the return type of some methods (add/remove methods of unqualified associations) to "void"
    * (default is "boolean" which indicates whether modification-methods have changed something).
    * Allows to generate code that is compatible with return types defined in F-interfaces.
    * (evaluated by CodeGen2 in "java/default/classDiag/assoc/import.vm")
    */
   public final static String VOID = "void";
   
   /**
    * enum stereotype may be attached to class which is then treated as enumeration.
    * All attributes will be treated as enumeration literals.
    */
   public final static String ENUM = "enum";

   // --- Property increments ---
   /**
    * <pre>
    *            +-----------+ 0..n                      1
    * FIncrement | getName() |----------------------------- FStereotype
    *            +-----------+ increments      stereotypes
    * </pre>
    */
   public final static String INCREMENTS_PROPERTY = "increments";


   public boolean addToIncrements (FIncrement value);


   public boolean hasInIncrements (FIncrement value);


   public Iterator<? extends FIncrement> iteratorOfIncrements();


   public void removeAllFromIncrements();


   public boolean removeFromIncrements (FIncrement value);


   public int sizeOfIncrements();

}

/*
 * $Log$
 * Revision 1.10  2007/01/19 13:04:11  fklar
 * removed unused import
 *
 * Revision 1.9  2006/12/06 11:34:41  cschneid
 * ProjectProperty replaced by FProject.properties map
 *
 * Revision 1.8  2006/05/18 18:21:48  fklar
 * using java 1.5 generics:
 * * adjusted return value of method 'iteratorOfElement' so it returns a parameterized iterator
 *
 * Revision 1.7  2006/05/12 14:10:14  lowende
 * Saving of stereotypes repaired.
 * Fixes for Fujaba4Eclipse.
 *
 */

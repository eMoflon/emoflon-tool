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
import de.uni_kassel.features.annotation.util.Property;

import java.util.Iterator;
import java.util.Map;

/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FIncrement extends FElement
{

   // --- Property constraints ---
   /**
    * <pre>
    *              0..n     constraints     0..n
    * FConstraint ------------------------------- FIncrement
    *              constraints        increments
    * </pre>
    */
   public final static String CONSTRAINTS_PROPERTY = "constraints";


   @Property(name=CONSTRAINTS_PROPERTY)
   public boolean hasInConstraints (FConstraint value);


   @Property(name=CONSTRAINTS_PROPERTY)
   public Iterator<? extends FConstraint> iteratorOfConstraints();


   @Property(name=CONSTRAINTS_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY,
         partner=FConstraint.INCREMENTS_PROPERTY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean addToConstraints (FConstraint value);


   @Property(name=CONSTRAINTS_PROPERTY)
   public boolean removeFromConstraints (FConstraint value);


   @Property(name=CONSTRAINTS_PROPERTY)
   public void removeAllFromConstraints();


   // --- Property comment ---
   public final static String COMMENT_PROPERTY = "comment";


   @Property(name=COMMENT_PROPERTY)
   public FCommentary getComment();


   @Property(name=COMMENT_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE,
         adornment=ReferenceHandler.Adornment.COMPOSITION)
   public void setComment (FCommentary comment);


   // --- Property assertInUnitTest ---
   public final static String ASSERT_IN_UNIT_TEST_PROPERTY = "assertInUnitTest";


   @Property(name=ASSERT_IN_UNIT_TEST_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setAssertInUnitTest (boolean value);


   @Property(name=ASSERT_IN_UNIT_TEST_PROPERTY)
   public boolean isAssertInUnitTest();


   // --- Property stereotypes
   /**
    * <pre>
    *            +-----------+ 0..n                      1
    * FIncrement | getName() |----------------------------- FStereotype
    *            +-----------+ increments      stereotypes
    * </pre>
    */
   public final static String STEREOTYPES_PROPERTY = "stereotypes";


   @Property(name=STEREOTYPES_PROPERTY, kind=ReferenceHandler.ReferenceKind.QUALIFIED_TO_ONE,
         adornment=ReferenceHandler.Adornment.USAGE)
   public boolean addToStereotypes (FStereotype obj);


   @Property(name=STEREOTYPES_PROPERTY)
   public boolean hasInStereotypes (FStereotype stereotype);


   @Property(name=STEREOTYPES_PROPERTY)
   public boolean hasKeyInStereotypes (String key);


   @Property(name=STEREOTYPES_PROPERTY)
   public Iterator<? extends FStereotype> iteratorOfStereotypes();


   @Property(name=STEREOTYPES_PROPERTY)
   public Iterator<String> keysOfStereotypes();


   @Property(name=STEREOTYPES_PROPERTY)
   public Iterator<Map.Entry<String, ? extends FStereotype>> entriesOfStereotypes();


   @Property(name=STEREOTYPES_PROPERTY)
   public int sizeOfStereotypes();


   @Property(name=STEREOTYPES_PROPERTY)
   public FStereotype getFromStereotypes (String key);


   @Property(name=STEREOTYPES_PROPERTY)
   public boolean removeFromStereotypes (FStereotype obj);


   @Property(name=STEREOTYPES_PROPERTY)
   public boolean removeKeyFromStereotypes (String key);


   @Property(name=STEREOTYPES_PROPERTY)
   public void removeAllFromStereotypes();

   /**
    * <pre>
    *           0..1     tags     0..*
    * FIncrement ------------------------- FTag
    *           revTags               tags
    * </pre>
    */
   public static final String PROPERTY_TAGS = "tags";

   @Property( name = PROPERTY_TAGS, partner = FTag.PROPERTY_REV_TAGS, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         adornment = ReferenceHandler.Adornment.COMPOSITION)
   public boolean addToTags (FTag value);

   @Property( name = PROPERTY_TAGS )
   public boolean removeFromTags (FTag value);

   @Property( name = PROPERTY_TAGS )
   public void removeAllFromTags ();

   @Property( name = PROPERTY_TAGS )
   public boolean hasInTags (FTag value);

   @Property( name = PROPERTY_TAGS )
   public Iterator iteratorOfTags ();

   @Property( name = PROPERTY_TAGS )
   public int sizeOfTags ();}

/*
 * $Log$
 * Revision 1.7  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.6  2006/05/23 15:18:40  rotschke
 * Changed the return type of setComment() back to "void", as it used to be in the old days [tr].
 *
 * Revision 1.5  2006/05/23 09:53:50  cschneid
 * using some generics, EmptyIterator.get() used again, versioning credentials
 *
 * Revision 1.4  2006/05/19 16:31:03  koenigs
 * Changed return type of setComment from void to boolean since it would have been generated this way by Fujaba. [ak]
 *
 * Revision 1.3  2006/05/16 11:20:12  fklar
 * using java 1.5 generics
 *
 * Revision 1.2  2006/05/12 14:10:14  lowende
 * Saving of stereotypes repaired.
 * Fixes for Fujaba4Eclipse.
 *
 */

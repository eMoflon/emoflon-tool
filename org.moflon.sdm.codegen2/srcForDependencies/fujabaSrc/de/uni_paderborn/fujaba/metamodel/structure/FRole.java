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
import de.uni_paderborn.fujaba.metamodel.common.FIncrement;

import java.util.Iterator;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FRole extends FIncrement, FAccessedElement, FTextReference
{
   // Adornment = {None, Aggregation, Composition, Reference, Qualified}
   public final static int NONE = 0;

   public final static int AGGREGATION = NONE + 1;

   public final static int COMPOSITION = AGGREGATION + 1;

   public final static int REFERENCE = COMPOSITION + 1;

   public final static int QUALIFIED = REFERENCE + 1;

   public static final String REV_RIGHT_ROLE_PROPERTY = "revRightRole";

   public static final String REV_LEFT_ROLE_PROPERTY = "revLeftRole";

   // --- Property adornment ---
   public final static String ADORNMENT_PROPERTY = "adornment";


   /**
    * Get the adornment attribute of the UMLRole object, one of {None, Aggregation, Composition,
    * Reference, Qualified}
    *
    * @return   The adornment value
    */
   @Property(name=ADORNMENT_PROPERTY)
   public int getAdornment();


   /**
    * Sets the adornment attribute of the UMLRole object, one of {None, Aggregation, Composition,
    * Reference, Qualified}
    *
    * @param adornment  The new adornment value
    */
   @Property(name=ADORNMENT_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setAdornment (int adornment);


   // --- Property parsed ---
   public final static String PARSED_PROPERTY = "parsed";


   /**
    * Indicate that the role was generated from parsed source code. If it is set to true, there will
    * be no code generated for the role, since access methods already exist in the parsed code.
    *
    * @param parsed  true, if the role was generated from parsed source code
    */
   @Property(name=PARSED_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setParsed (boolean parsed);


   /**
    * Indicates if the role was generated from parsed source code. If it is true, there will be no
    * code generated for the role, since access methods already exist in the parsed code.
    *
    * @return   true, if the role was generated from parsed source code
    */
   @Property(name=PARSED_PROPERTY)
   public boolean isParsed();


   // --- Property target ---
   public final static String TARGET_PROPERTY = "target";


   @Property(name=TARGET_PROPERTY)
   public FClass getTarget();


   @Property(name=TARGET_PROPERTY, partner=FClass.ROLES_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.USAGE)
   public void setTarget (FClass target);


   // --- Property revRoles ---
   public final static String REV_ROLES_PROPERTY = "revRoles";

   // --- Property card ---
   public final static String CARD_PROPERTY = "card";


   @Property(name=CARD_PROPERTY)
   public FCardinality getCard();


   @Property(name=CARD_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.USAGE)
   public void setCard (FCardinality card);


   // --- Property qualifier ---
   public final static String QUALIFIER_PROPERTY = "qualifier";


   @Property(name=QUALIFIER_PROPERTY)
   public FQualifier getQualifier();


   @Property(name=QUALIFIER_PROPERTY, partner=FQualifier.REV_QUALIFIER_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public void setQualifier (FQualifier qualifier);


   // --- Property revQualifiedRole ---
   public final static String REV_QUALIFIED_ROLE_PROPERTY = "revQualifiedRole";
   public final static String REV_QUALIFIED_ROLE = REV_QUALIFIED_ROLE_PROPERTY;


   @Property(name=REV_QUALIFIED_ROLE_PROPERTY)
   public boolean hasInRevQualifiedRole (FQualifier value);


   @Property(name=REV_QUALIFIED_ROLE_PROPERTY)
   public Iterator<? extends FQualifier> iteratorOfRevQualifiedRole();


   @Property(name=REV_QUALIFIED_ROLE_PROPERTY)
   public int sizeOfRevQualifiedRole();


   @Property(name=REV_QUALIFIED_ROLE_PROPERTY, partner=FQualifier.QUALIFIED_ROLE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE)
   public boolean addToRevQualifiedRole (FQualifier value);


   @Property(name=REV_QUALIFIED_ROLE_PROPERTY)
   public boolean removeFromRevQualifiedRole (FQualifier value);


   @Property(name=REV_QUALIFIED_ROLE_PROPERTY)
   public void removeAllFromRevQualifiedRole();


   // --- Property assoc ---
   public final static String ASSOC_PROPERTY = "assoc";


   @Property(name=ASSOC_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, derived=true)
   public FAssoc getAssoc();


   // --- Property partnerRole ---
   public final static String PARTNER_ROLE_PROPERTY = "partnerRole";


   @Property(name=PARTNER_ROLE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, derived=true)
   public FRole getPartnerRole();


   // --- Property attrName ---
   public final static String ATTR_NAME_PROPERTY = "attrName";


   /**
    * Returns an attribute name which represents this role. If this role has already a name, this
    * name will be returned. If this role does not have a name but has a target class, a default
    * name will be created. If this role does not have a name and no target class, the return value
    * is null.
    *
    * @return   The attribute name as a string.
    */
   @Property(name=ATTR_NAME_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE, derived=true)
   public String getAttrName();


   // --- Deprecated methods ---
   public static final String SORTED_COMPARATOR_PROPERTY = "sortedComparator";

   /**
    * Get the sortedComparator attribute of the UMLAssoc object
    *
    * @return       The sortedComparator value
    * @deprecated   (gets deleted in 5.1)
    */
   @Property(name=SORTED_COMPARATOR_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public String getSortedComparator();


   /**
    * @return
    * @deprecated   (gets deleted in 5.1)
    */
   @Property(name="associatedAttribute", kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public FAttr getAssociatedAttribute();


   /**
    * @return
    * @deprecated   use {@link #getVisibility()} instead
    */
   @Property(name=FDeclaration.VISIBILITY_PROPERTY)
   public int getUmlVisibility();


   /**
    * @param value
    * @deprecated   use {@link #setVisibility(int)} instead
    */
   @Property(name=FDeclaration.VISIBILITY_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setUmlVisibility (int value);


   /**
    * Model visibility of the role.
    *
    * @return   one from {@link FDeclaration#PUBLIC}, {@link FDeclaration#PRIVATE},
    *         {@link FDeclaration#PROTECTED}, {@link FDeclaration#PACKAGE},
    *         {@link FDeclaration#USERDEFINED}
    */
   @Property(name=FDeclaration.VISIBILITY_PROPERTY)
   public int getVisibility();


   /**
    * Change model visibility of the declaration.
    *
    * @param visibility  new visibility value
    * @see               #getVisibility()
    */
   @Property(name=FDeclaration.VISIBILITY_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setVisibility (int visibility);

   /**
    * @return true if role cannot be altered
    */
   @Property(name=PROPERTY_READ_ONLY)
   public boolean isReadOnly();

   /**
    * Property name for firing property change events of field readOnly.
    */
   public static final String PROPERTY_READ_ONLY = "readOnly";

   /**
    * @param value true to disallow altering this role
    */
   @Property(name=PROPERTY_READ_ONLY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setReadOnly(final boolean value);

   @Property(name=REV_LEFT_ROLE_PROPERTY)
   FAssoc getRevLeftRole();

   @Property(name=REV_LEFT_ROLE_PROPERTY, partner=FAssoc.LEFT_ROLE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   void setRevLeftRole(FAssoc revLeftRole);

   @Property(name=REV_RIGHT_ROLE_PROPERTY)
   FAssoc getRevRightRole();

   @Property(name=REV_RIGHT_ROLE_PROPERTY, partner=FAssoc.RIGHT_ROLE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   void setRevRightRole(FAssoc revRightRole);
}

/*
 * $Log$
 * Revision 1.14  2007/03/26 10:49:25  l3_g5
 * prevent exceptions when saving with fpr
 *
 * Revision 1.13  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.12  2007/03/23 11:18:43  fklar
 * changed returntype of 'getRevLeftRole' and 'getRevRightRole' from 'UMLAssoc' to 'FAssoc'
 *
 * Revision 1.11  2007/03/23 10:45:07  cschneid
 * FRole.revLeft/RightRole added
 * NPE in dialog fixed
 *
 * Revision 1.10  2007/03/06 17:18:03  cschneid
 * added "readOnly" attribute for FAttr and FRole - please adapt in MOFLON
 *
 * Revision 1.9  2006/05/18 19:20:58  fklar
 * using java 1.5 generics:
 * * adjusted return values of some methods in F-interfaces so they return a parameterized type
 *
 * Revision 1.8  2006/05/04 09:16:26  creckord
 * re-added FAccessedElement,
 * new class FAccessStyle for access code handling
 *
 */

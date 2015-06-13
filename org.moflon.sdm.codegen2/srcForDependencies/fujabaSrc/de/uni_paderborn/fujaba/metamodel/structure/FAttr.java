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


import de.fujaba.text.FParsedElement;
import de.fujaba.text.FTextReference;
import de.uni_kassel.features.ReferenceHandler;
import de.uni_kassel.features.annotation.util.AccessFragment;
import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_paderborn.fujaba.metamodel.common.FElement;

import java.util.Iterator;

/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FAttr extends FDeclaration, FAccessedElement, FTextReference, FParsedElement
{
   public final static int CREATE_ACCESS_METHODS_YES = 0;

   public final static int CREATE_ACCESS_METHODS_NO = 1;

   public final static int CREATE_ACCESS_METHODS_DEFAULT = 2;

   // --- Property static ---
   public final static String STATIC_PROPERTY = "static";


   @Property(name=STATIC_PROPERTY)
   public boolean isStatic();


   @Property(name=STATIC_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setStatic (boolean value);


   // --- Property initialValue ---
   public final static String INITIAL_VALUE_PROPERTY = "initialValue";


   @Property(name=INITIAL_VALUE_PROPERTY)
   public String getInitialValue();


   @Property(name=INITIAL_VALUE_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setInitialValue (String initialValue);


   // --- Property parent ---
   public final static String PARENT_PROPERTY = "parent";


   @Property( name = PARENT_PROPERTY, partner = FClass.ATTRS_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.PARENT)
   public boolean setParent (FClass obj);


   @Property(name=PARENT_PROPERTY)
   public FClass getParent();


   // --- Property revQualifiedAttr ---
   public final static String REV_QUALIFIED_ATTR = "revQualifiedAttr";
   public final static String REV_QUALIFIED_ATTR_PROPERTY = REV_QUALIFIED_ATTR;

   @Property(name=REV_QUALIFIED_ATTR_PROPERTY)
   public boolean hasInRevQualifiedAttr (FQualifier value);


   @Property(name=REV_QUALIFIED_ATTR_PROPERTY)
   public Iterator<? extends FQualifier> iteratorOfRevQualifiedAttr();


   @Property(name=REV_QUALIFIED_ATTR_PROPERTY)
   public int sizeOfRevQualifiedAttr();


   @Property(name=REV_QUALIFIED_ATTR_PROPERTY, partner=FQualifier.QUALIFIED_ATTR_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE)
   public boolean addToRevQualifiedAttr (FQualifier value);


   @Property(name=REV_QUALIFIED_ATTR_PROPERTY)
   public boolean removeFromRevQualifiedAttr (FQualifier value);


   @Property(name=REV_QUALIFIED_ATTR_PROPERTY)
   public void removeAllFromRevQualifiedAttr();


   // --- Property attrType ---
   public final static String ATTR_TYPE_PROPERTY = "attrType";


   @Property( name = ATTR_TYPE_PROPERTY )
   public FType getAttrType();


   @Property( name = ATTR_TYPE_PROPERTY, partner = FType.REV_ATTR_TYPE_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_ONE,
         adornment = ReferenceHandler.Adornment.USAGE)
   public void setAttrType (FType attrType);


   // --- Property instances ---
   public final static String INSTANCES_PROPERTY = "instances";


   @Property( name = INSTANCES_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         adornment = ReferenceHandler.Adornment.NONE,
         accessFragment = AccessFragment.ADDER )
   public void addToInstances (FElement pair);


   @Property( name = INSTANCES_PROPERTY, accessFragment = AccessFragment.REMOVER )
   public void removeFromInstances (FElement pair);


   // --- Deprecated methods ---
   /**
    * @return
    * @deprecated   (gets deleted in final 5.0)
    */
   @NoProperty
   public boolean isNeedsAccessMethods();


   /**
    * @return
    * @deprecated   (gets deleted in final 5.0)
    */
   @NoProperty
   public FRole getImplementingAssocRole();


   /**
    * @param role
    * @return
    * @deprecated   (gets deleted in final 5.0)
    */
   @Property(name="implementingAssocRole", partner="associatedAttribute", kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.PARENT)
   public boolean setImplementingAssocRole (FRole role);


   public final static String CREATE_ACCESS_METHODS_PROPERTY = "createAccessMethods";

   @Property(name=CREATE_ACCESS_METHODS_PROPERTY)
   public int getCreateAccessMethods();


   @Property(name=CREATE_ACCESS_METHODS_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setCreateAccessMethods (int value);


   public final static String ACCESS_METHODS_PROPERTY = "accessMethods";

   /**
    * @return
    * @deprecated   (gets deleted in final 5.0)
    */
   @Property(name=ACCESS_METHODS_PROPERTY)
   public Iterator<? extends FMethod> iteratorOfAccessMethods();


   @Property(name=ACCESS_METHODS_PROPERTY, partner=FMethod.ACCESSED_ATTRIBUTE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.COMPOSITION)
   public boolean addToAccessMethods (FMethod value);


   @Property(name=ACCESS_METHODS_PROPERTY)
   public boolean removeFromAccessMethods (FMethod value);


   /**
    * @return true if attr cannot be set
    */
   @Property(name=PROPERTY_READ_ONLY)
   public boolean isReadOnly();

   /**
    * Property name for firing property change events of field readOnly.
    */
   public static final String PROPERTY_READ_ONLY = "readOnly";

   /**
    * @param value true to disallow setting this attr
    */
   @Property(name=PROPERTY_READ_ONLY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setReadOnly(final boolean value);
}

/*
 * $Log$
 * Revision 1.12  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.11  2007/03/06 17:18:03  cschneid
 * added "readOnly" attribute for FAttr and FRole - please adapt in MOFLON
 *
 * Revision 1.10  2006/05/18 19:20:58  fklar
 * using java 1.5 generics:
 * * adjusted return values of some methods in F-interfaces so they return a parameterized type
 *
 * Revision 1.9  2006/05/10 12:29:28  lowende
 * Fixed loading/saving problem of some properties.
 *
 */

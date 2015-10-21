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

import de.uni_paderborn.fujaba.metamodel.common.FElement;

import java.util.Iterator;

import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_kassel.features.annotation.util.Property;
import de.uni_kassel.features.ReferenceHandler;


/**
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FType extends FElement
{

   /**
    * Get the name attribute of the FType object
    *
    * @return   The name value
    */
   @Property(name=NAME_PROPERTY)
   public String getName();


   /**
    * Sets the name attribute of the FType object
    *
    * @param name  The new name value
    */
   @Property(name=NAME_PROPERTY, kind=ReferenceHandler.ReferenceKind.ATTRIBUTE)
   public void setName (String name);


   /**
    * @return       The progLangType value
    * @deprecated   (gets deleted in 5.1)
    */
   @NoProperty
   public String getProgLangType();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String REV_ARRAY_TYPE_PROPERTY = "revArrayType";


   /**
    * Get the revArrayType attribute of the FType object
    *
    * @return   The revArrayType value
    */
   @Property(name=REV_ARRAY_TYPE_PROPERTY)
   public FArray getRevArrayType();


   /**
    * Sets the revArrayType attribute of the FBaseType object
    *
    * @param revArrayType  The new revArrayType value
    */
   @Property(name=REV_ARRAY_TYPE_PROPERTY, partner=FArray.ARRAY_TYPE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_ONE, adornment=ReferenceHandler.Adornment.NONE)
   void setRevArrayType (FArray revArrayType);


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String REV_PARAM_TYPE_PROPERTY = "revParamType";


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Property(name=REV_PARAM_TYPE_PROPERTY, partner=FParam.PARAM_TYPE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE)
   public Iterator<? extends FParam> iteratorOfRevParamType();


   @Property(name=REV_PARAM_TYPE_PROPERTY)
   int sizeOfRevParamType();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String REV_RESULT_TYPE_PROPERTY = "revResultType";


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Property(name=REV_RESULT_TYPE_PROPERTY, partner=FMethod.RESULT_TYPE_PROPERTY, kind=ReferenceHandler.ReferenceKind.TO_MANY, adornment=ReferenceHandler.Adornment.NONE)
   public Iterator<? extends FMethod> iteratorOfRevResultType();


   @Property(name=REV_RESULT_TYPE_PROPERTY)
   int sizeOfRevResultType();


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String REV_ATTR_TYPE_PROPERTY = "revAttrType";
   
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   @Property(name=REV_ATTR_TYPE_PROPERTY)
   boolean hasInRevAttrType (FAttr value);

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Property(name=REV_ATTR_TYPE_PROPERTY)
   Iterator<? extends FAttr> iteratorOfRevAttrType();

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   @Property(name=REV_ATTR_TYPE_PROPERTY)
   int sizeOfRevAttrType();

   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   @Property( name = REV_ATTR_TYPE_PROPERTY, partner = FAttr.ATTR_TYPE_PROPERTY, kind = ReferenceHandler.ReferenceKind.TO_MANY,
         adornment = ReferenceHandler.Adornment.NONE)
   boolean addToRevAttrType (FAttr value);

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   @Property(name=REV_ATTR_TYPE_PROPERTY)
   boolean removeFromRevAttrType (FAttr value);

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   @Property(name=REV_ATTR_TYPE_PROPERTY)
   void removeAllFromRevAttrType();
}

/*
 * $Log$
 * Revision 1.8  2007/03/23 12:45:05  l3_g5
 * annotations for copy-paste
 *
 * Revision 1.7  2007/03/06 16:14:24  cschneid
 * moved revAttrType from FBaseType to FType
 *
 * Revision 1.6  2006/05/18 19:20:58  fklar
 * using java 1.5 generics:
 * * adjusted return values of some methods in F-interfaces so they return a parameterized type
 *
 * Revision 1.5  2006/05/11 20:25:03  fklar
 * cleaned up type hierarchy of FType: additionally extended interfaces are already defined in FElement... no need to define them again
 *
 */

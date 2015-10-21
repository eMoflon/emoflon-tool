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
import de.uni_paderborn.fujaba.metamodel.common.FIncrement;


/**
 * <h2>Associations</h2>
 *
 * <pre>
 *             +------+ 1                 1
 * UMLTypeList | name +--------------------- UMLType
 *             +------+ revTypes      types
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FBaseType extends FIncrement, FType, FTextReference
{
   // standard basetype names
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String INITIALIZER = "Initializer";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String BOOLEAN = "Boolean";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String CHARACTER = "Character";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String STRING = "String";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String INTEGER = "Integer";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String BYTE = "Byte";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String SHORT_INTEGER = "ShortInteger";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String LONG_INTEGER = "LongInteger";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String FLOAT = "Float";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String DOUBLE = "Double";

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String VOID = "Void";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String CONSTRUCTOR = "constructor";

   // ######################################################################

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean hasInRevType (FQualifier value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfRevType();


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   public boolean addToRevType (FQualifier value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean removeFromRevType (FQualifier value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromRevType();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean hasInRevResultType (FMethod value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfRevResultType();


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   public boolean addToRevResultType (FMethod value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean removeFromRevResultType (FMethod value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromRevResultType();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean hasInRevParamType (FParam value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfRevParamType();


   /**
    * Access method for an one to n association.
    *
    * @param value  The object added.
    * @return       No description provided
    */
   public boolean addToRevParamType (FParam value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public boolean removeFromRevParamType (FParam value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromRevParamType();

}

/*
 * $Log$
 * Revision 1.5  2007/03/06 16:14:24  cschneid
 * moved revAttrType from FBaseType to FType
 *
 */

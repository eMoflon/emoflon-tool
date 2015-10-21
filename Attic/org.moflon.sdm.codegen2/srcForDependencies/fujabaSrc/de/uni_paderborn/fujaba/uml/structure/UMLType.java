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


import java.util.Iterator;

import de.uni_paderborn.fujaba.metamodel.structure.*;


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
public interface UMLType extends FType
{

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param elem  No description provided
    * @return      No description provided
    */
   public boolean hasInRevResultType (FMethod elem);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfRevResultType();


   /**
    * Access method for a To N-association.
    *
    * @param elem  The object added.
    * @return      No description provided
    */
   public boolean addToRevResultType (FMethod elem);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param elem  No description provided
    * @return      No description provided
    */
   public boolean removeFromRevResultType (FMethod elem);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromRevResultType();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param elem  No description provided
    * @return      No description provided
    */
   public boolean hasInRevParamType (FParam elem);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfRevParamType();


   /**
    * Access method for a To N-association.
    *
    * @param elem  The object added.
    * @return      No description provided
    */
   public boolean addToRevParamType (FParam elem);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param elem  No description provided
    * @return      No description provided
    */
   public boolean removeFromRevParamType (FParam elem);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromRevParamType();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param elem  No description provided
    * @return      No description provided
    */
   public boolean hasInRevAttrType (FAttr elem);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfRevAttrType();


   /**
    * Access method for a To N-association.
    *
    * @param elem  The object added.
    * @return      No description provided
    */
   public boolean addToRevAttrType (FAttr elem);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param elem  No description provided
    * @return      No description provided
    */
   public boolean removeFromRevAttrType (FAttr elem);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public void removeAllFromRevAttrType();


   /**
    * Sets the revArrayType attribute of the UMLType object
    *
    * @param revArrayType  The new revArrayType value
    */
   public void setRevArrayType (FArray revArrayType);


   /**
    * Property name used for change events, unparsing and persistency - do not change.
    */
   public final static String REV_TYPE_PROPERTY = "revType";


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
   public Iterator iteratorOfRevType();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public int sizeOfRevType();


   /**
    * Access method for a To N-association.
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

}

/*
 * $Log$
 * Revision 1.7  2006/06/01 09:10:06  fklar
 * removed duplicate 'implements'-relation to 'LogicUnparseInterface', 'PropertyChangeClient' and 'UniqueIdentifier': already defined in superinterface
 *
 */

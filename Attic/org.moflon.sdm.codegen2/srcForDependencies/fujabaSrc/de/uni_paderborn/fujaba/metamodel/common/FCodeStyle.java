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

import java.util.Set;


/**
 * A code style encapsulates the information needed by a code generator to support different
 * code conventions to manipulate objects (create/destroy, get/set/iterate...). The information
 * returned is usually specific to a particular code generator.
 * 
 * @author  $Author$
 * @version $Revision$ $Date$
 */
public interface FCodeStyle extends FElement
{
	
	static String[] DEFAULT_CODESTYLE_NAMES = {
		"default",
		"Fujaba 4",
		"Fujaba 5",
		"AssocsLibrary",
		"MatlabAccess",
		"emf",
		"breaks",
		"GWT",
		"hibernate" };
	
   /**
    * A unique name for this code style 
    * @return the name
    */
   String getName ();
   
   /**
    * A name suitable to identify this code style to the user
    * @return the externalized name
    */
   String getExternalizedName ();
   
   /**
    * A description of this code style that can be presented to the user
    * @return the description
    */
   String getDescription ();
   
   /**
    * Descriptions for all keys supported by this code style.
    * @return an array of descriptions for the supported keys
    */
   Set<FCodeStyleKeyDescription> getSupportedKeys ();
   
   /**
    * A (codegen specific) string used by the code generator to realize the code 
    * specified by the key (e.g. create object, set, get, iterator ...)
    * @param key the key to identify the code
    * @return a code string for the code generator
    * @throws IllegalArgumentException if the key is not supported by this code style
    * @see #getSupportedKeys()
    * @see FCodeStyleKeyDescription
    */
   String getStyleInformation (String key);
   
   /**
    * Instances of this class describe the keys supported by an code style.
    */
   public static interface FCodeStyleKeyDescription
   {
      /**
       * The key identifying the requested code method used in {@link FCodeStyle#getStyleInformation(String)}
       * @return the key
       * @see FCodeStyle#getStyleInformation(String)
       */
      String getKey ();
      
      /**
       * A name suitable to identify this key to the user
       * @return the externalized name
       */
      String getExternalizedName ();
      
      /**
       * A description of the code method identified by this key
       * @return the description
       */
      String getDescription ();
   }
}
/*
 * $Log$
 * Revision 1.1  2007/03/21 12:47:48  creckord
 * - deprecated FAccessStyle and replaced with FCodeStyle
 * - added FInstanceElement
 * - moved toOneAccess from UMLLink to FRoleUtility
 *
 */
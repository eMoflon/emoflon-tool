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
package de.uni_paderborn.fujaba.basic;

import org.apache.log4j.Logger;

import de.uni_kassel.features.annotation.util.NoProperty;
import de.uni_paderborn.fujaba.preferences.FujabaCorePreferenceKeys;
import de.uni_paderborn.fujaba.project.FPRCommon;


/**
 * Implementation of the BasicIncrement interface.
 * <p/>
 * <h2>Associations</h2>
 * <p/>
 * <pre>
 *                 0..1         Assoc         0..1 ------
 * BasicIncrement ---------------------------------| ID | UMLProject
 *                 objectHashTable      refProject ------
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$
 */
public abstract class BasicIncrement implements UniqueIdentifier
{
   /**
    * log4j logging
    */
   private final static transient Logger log = Logger.getLogger (BasicIncrement.class);


   /**
    * Default constructor for BasicIncrement
    */
   protected BasicIncrement() { }


   /**
    * This method allows to obtain a String which is capable of referencing this object uniquely in a certain scope.
    * The actual ID depends on the persistency mechanism which is used for the project.
    * <p>
    * Please note the ID may not be available in while the constructor of an object is executed (esp. while loading).
    * <p>
    * You should generally avoid to use IDs for anything else than storing persistent references.
    * @return an identifier for this object, not null
    */
   @NoProperty
   public String getID()
   {
      String iD = FPRCommon.getID(this);
      if (iD == null)
      {
         iD = FPRCommon.getUniqueID(this);
      }
      return iD;
   }


   /**
    * Remove all links to other objects to make garbage collection of this object possible.
    */
   public void removeYou()
   {
      if (FD.isOn (FujabaCorePreferenceKeys.DEBUG_REMOVE_YOU))
      {
         FD.setRemoveYouPrinted (false);
         log.info (this.getClass() + "[" + getID() + "].removeYou() ");
      }
      FPRCommon.removeID(this, FPRCommon.getID(this));
   }
}

/*
 * $Log: BasicIncrement.java,v $
 * Revision 1.199  2007/03/23 14:04:06  cschneid
 * some copy improvements
 *
 * Revision 1.198  2007/03/13 13:40:35  weisemoeller
 * Restored deleted classes from last commit. Those classes have been marked as deprecated now and will probably be deleted in a few weeks (we can discuss the point of time for this on the developer days, I think.)
 *
 * The new classes have been moved back to de.fujaba for now.
 *
 * Moreover, the usage of some workspace settings has been replaced with the corresponding project specific settings.
 *
 */

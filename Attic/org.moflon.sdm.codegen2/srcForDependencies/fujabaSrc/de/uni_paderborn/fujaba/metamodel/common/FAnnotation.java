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
import java.util.Map;



/**
 * <h2>Associations</h2> <pre>
 *             ------- 0..n   Annotations   0..n
 * FAnnotation | key |----------------------------- FElement
 *             ------- annotations      elements
 * </pre>
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public interface FAnnotation extends FElement
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String ELEMENTS_PROPERTY = "elements";


   /**
    * Access method for an one to n association.
    *
    * @param key    The object added.
    * @param value  The object added.
    * @return       No description provided
    */
   public abstract boolean addToElements (String key, FElement value);


   /**
    * Access method for an one to n association.
    *
    * @param entry  The object added.
    * @return       No description provided
    */
   public abstract boolean addToElements (Map.Entry entry);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param key  No description provided
    * @return     No description provided
    */
   public abstract Iterator<? extends FElement> iteratorOfElements (String key);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public abstract Iterator<? extends FElement> iteratorOfElements();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param key  No description provided
    * @return     No description provided
    */
   public abstract boolean hasKeyInElements (String key);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param key    No description provided
    * @param value  No description provided
    * @return       No description provided
    */
   public abstract boolean hasInElements (String key, FElement value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public abstract boolean hasInElements (FElement value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public abstract Iterator keysOfElements();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public abstract Iterator entriesOfElements();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param key  No description provided
    * @return     No description provided
    */
   public abstract int sizeOfElements (String key);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @return   No description provided
    */
   public abstract int sizeOfElements();


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param key    No description provided
    * @param value  No description provided
    * @return       No description provided
    */
   public abstract boolean removeFromElements (String key, FElement value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param key  No description provided
    * @return     No description provided
    */
   public abstract boolean removeFromElements (String key);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param value  No description provided
    * @return       No description provided
    */
   public abstract boolean removeFromElements (FElement value);


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public abstract void removeAllFromElements();

}

/*
 * $Log$
 * Revision 1.3  2006/05/18 18:21:48  fklar
 * using java 1.5 generics:
 * * adjusted return value of method 'iteratorOfElement' so it returns a parameterized iterator
 *
 * Revision 1.2  2006/03/29 09:51:12  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.1  2005/08/24 09:41:17  koenigs
 * Introduced new structure package for all Class Diagram related F-interfaces.
 * Introduced new common package for all remaining F-interfaces. [ak]
 *
 */

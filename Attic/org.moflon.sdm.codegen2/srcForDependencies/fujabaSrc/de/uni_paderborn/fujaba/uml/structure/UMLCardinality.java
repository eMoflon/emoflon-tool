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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import de.uni_kassel.util.ConcurrentHashSet;
import de.uni_kassel.util.EmptyIterator;
import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;
import de.uni_paderborn.fujaba.metamodel.structure.FCardinality;
import de.uni_paderborn.fujaba.uml.common.UMLIncrement;
import de.uni_paderborn.fujaba.versioning.Versioning;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class UMLCardinality extends UMLIncrement implements FCardinality
{

   /**
    * Don't call directly use appropriate Factory instead.
    *
    * @param project
    * @param persistent
    * @param factoryKey  No description provided
    * @see               de.uni_paderborn.fujaba.metamodel.factories.FFactory
    */
   protected UMLCardinality (FProject project, boolean persistent, String factoryKey)
   {
      super (project, persistent, factoryKey);
      cardString = factoryKey;
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private final String cardString;


   /**
    * Get the lowerBound attribute of the UMLCardinality object
    *
    * @return   The lowerBound value
    */
   public int getLowerBound()
   {
      if ( (cardString != null) &&  (cardString.length() > 0))
      {
         char c = cardString.charAt (0);
         if ( (c < '0') ||  (c > '9'))
         {
            return 0;
         }
         else
         {
            int result = 0;

            for (int i = 0; i < cardString.length(); i++)
            {
               c = cardString.charAt (i);
               if ( ('0' <= c && c <= '9'))
               {
                  result = result * 10 + c - '0';
               }
               else
               {
                  return result;
               }
            }
            return result;
         }
      }
      else
      {
         return 1;
      }
   }


   /**
    * Sets the name attribute of the ASGElement object
    *
    * @param newName  The new name value
    */
   @Override
   public void setName (String newName)
   {
      throw new UnsupportedOperationException("Name of a cardinality cannot be changed!");
   }


   /**
    * Get the name attribute of the ASGElement object
    *
    * @return   The name value
    */
   @Override
   public String getName()
   {
      return getCardString();
   }


   /**
    * Get the upperBound attribute of the UMLCardinality object
    *
    * @return   The upperBound value
    */
   public int getUpperBound()
   {
      if ( (cardString != null) &&  (cardString.length() > 0))
      {
         char c = cardString.charAt (cardString.length() - 1);
         if ( (c < '0') ||  (c > '9'))
         {
            return Integer.MAX_VALUE; // infinity

         }
         else
         {
            int result = 0;
            int multi = 1;

            for (int i = cardString.length() - 1; i >= 0; i--)
            {
               c = cardString.charAt (i);
               if ( ('0' <= c && c <= '9'))
               {
                  result =  (c - '0') * multi + result;
                  multi *= 10;
               }
               else
               {
                  return result;
               }
            }
            return result;
         }
      }
      else
      {
         return 1;
      }
   }


   /**
    * Get the cardString attribute of the UMLCardinality object
    *
    * @return   The cardString value
    */
   public String getCardString()
   {
      return cardString;
   }

   @Override
   public String toString()
   {
      return getCardString();
   }

   @Override
   public String getContextIdentifier(Collection<? extends FElement> context)
   {
      return getCardString();
   }

   @Override
   public void removeYou()
   {
      // to fix a bug with UMLRole.removeYou() we ignore deleting cardinalities while loading
      if ( Versioning.get().isInOperationalization(this) ) return; // this line can be removed in some future version (e.g. Fujaba 6)
      
      removeAllFromRoles();
      super.removeYou();
   }

   private transient Set<UMLRole> roles;

   public boolean addToRoles(UMLRole value)
   {
      boolean changed = false;
      if (value != null)
      {
         if (this.roles == null)
         {
            this.roles = new ConcurrentHashSet<UMLRole>();
         }
         changed = this.roles.add(value);
         if (changed)
         {
            firePropertyChange("roles", null, value);
            value.setCard(this);
         }
      }
      return changed;
   }

   public boolean hasInRoles(UMLRole value)
   {
      return ((this.roles != null) &&
            (value != null) &&
            this.roles.contains(value));
   }

   public Iterator<UMLRole> iteratorOfRoles()
   {
      if (this.roles == null)
      {
         return EmptyIterator.get();
      } else
      {
         return this.roles.iterator();
      }
   }

   public void removeAllFromRoles()
   {
      Iterator<UMLRole> iter = this.iteratorOfRoles();
      while (iter.hasNext())
      {
         this.removeFromRoles( iter.next());
      }
   }

   public boolean removeFromRoles(UMLRole value)
   {
      boolean changed = false;
      if ((this.roles != null) && (value != null))
      {
         changed = this.roles.remove(value);
         if (changed)
         {
            firePropertyChange("roles", value, null);
            value.setCard(null);
         }
      }
      return changed;
   }

   public int sizeOfRoles()
   {
      return ((this.roles == null)
            ? 0
            : this.roles.size());
   }
}

/*
 * $Log$
 * Revision 1.11  2007/03/22 10:21:41  cschneid
 * - open small projects without progress dialog
 * - group JarProjects into a category
 * - show cardinality in property editor
 *
 * Revision 1.10  2006/12/12 16:12:31  cschneid
 * remove old workspace backup files, FCardinality.CardString made readonly, renamed FprGenTask, new libs
 *
 * Revision 1.9  2006/04/25 11:58:26  cschneid
 * added deprecation expiration note, work on versioning
 *
 * Revision 1.8  2006/04/06 12:06:11  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.7  2006/03/29 09:51:08  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.6  2006/03/01 12:23:00  cschneid
 * Generic factory for behavior classes, ASG constructor has mandatory project and persistent parameters, Convenience ctors removed, all UML ctors protected
 *
 */

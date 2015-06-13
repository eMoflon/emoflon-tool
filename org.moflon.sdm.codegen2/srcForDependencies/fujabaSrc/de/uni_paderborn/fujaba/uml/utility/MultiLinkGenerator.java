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
package de.uni_paderborn.fujaba.uml.utility;


import java.util.Iterator;

import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.uml.behavior.UMLLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLMultiLink;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.common.UMLDiagram;
import de.uni_paderborn.fujaba.uml.common.UMLIncrement;


/**
 * Utility class to generate MultiLinks
 *
 *
 * @author    $Author$
 * @version   $Revision$ $Date$
 */
public class MultiLinkGenerator
{

   /**
    * Constructor for class MultiLinkGenerator
    */
   public MultiLinkGenerator() { }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    *
    * @param containerObject  No description provided
    * @param umlIncrement     No description provided
    * @return                 No description provided
    */
   public UMLMultiLink generateFirstMultiLink (UMLObject containerObject,
                                               UMLIncrement umlIncrement)
   {
      return generateMultiLink (UMLMultiLink.FIRST, 0, false, containerObject,
         umlIncrement, umlIncrement);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    *
    * @param containerObject  No description provided
    * @param umlIncrement     No description provided
    * @return                 No description provided
    */
   public UMLMultiLink generateLastMultiLink (UMLObject containerObject,
                                              UMLIncrement umlIncrement)
   {
      return generateMultiLink (UMLMultiLink.LAST, 0, false, containerObject,
         umlIncrement, umlIncrement);
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    *
    * @param type              No description provided
    * @param index             No description provided
    * @param negative          No description provided
    * @param containerObject   No description provided
    * @param asgElement        No description provided
    * @param secondAsgElement  No description provided
    * @return                  No description provided
    */
   public UMLMultiLink generateMultiLink (int type, int index, boolean negative,
                                          UMLObject containerObject, FElement asgElement,
                                          FElement secondAsgElement)
   {
      UMLMultiLink multiLink = null;

      if (asgElement instanceof UMLMultiLink)
      {
         // types: FIRST, LAST, DIRECT, INDIRECT, INDEX

         multiLink = (UMLMultiLink) asgElement;

         multiLink.setType (type);

         if (type == UMLMultiLink.INDEX)
         {
            multiLink.setIndex (index);
         }

         multiLink.setNegative (negative);
      }
      else if ( (asgElement instanceof UMLLink) &&  (secondAsgElement != null)
         &&  (secondAsgElement instanceof UMLLink)
         &&  (asgElement != secondAsgElement))
      {
         // types: DIRECT, INDIRECT, INDEX
         UMLLink sourceLink = (UMLLink) asgElement;
         UMLLink targetLink = (UMLLink) secondAsgElement;

         // consistence check
         if (sourceLink.getRevSourceLink() != null)
         {
            sourceLink.getRevSourceLink().removeYou();
         }

         if (targetLink.getRevTargetLink() != null)
         {
            targetLink.getRevTargetLink().removeYou();
         }

         // create new mulitlink
         multiLink = asgElement.getProject().getFromFactories (
            UMLMultiLink.class).create (true);
         multiLink.setSourceLink (sourceLink);
         multiLink.setTargetLink (targetLink);
         multiLink.setContainerObject (containerObject);
         multiLink.setType (type);
         multiLink.update();

         if (type == UMLMultiLink.INDEX)
         {
            multiLink.setIndex (index);
         }

         // neagtive
         multiLink.setNegative (negative);

         Iterator diags =  ((UMLLink) asgElement).iteratorOfDiagrams();
         if (diags != null)
         {
            UMLDiagram diag = null;
            while (diags.hasNext())
            {
               diag = (UMLDiagram) diags.next();
               if ( (diag.hasInElements (asgElement))
                  &&  (diag.hasInElements (secondAsgElement)))
               {
                  diag.addToElements (multiLink);
               }
            }
         }
      }
      else if (asgElement instanceof UMLLink)
      {
         UMLLink link = (UMLLink) asgElement;
         UMLLink sourceLink;
         UMLLink targetLink;

         // consistence check
         if (type == UMLMultiLink.FIRST)
         {
            if (link.getRevTargetLink() != null)
            {
               link.getRevTargetLink().removeYou();
            }

            // FIRST cannot be LAST simultaneously
            if (link.getRevSourceLink() != null
               && link.getRevSourceLink().getType() == UMLMultiLink.LAST)
            {
               link.getRevSourceLink().removeYou();
            }
         }
         else
         {
            // multiLinkType = UMLMultiLink.LAST
            if (link.getRevSourceLink() != null)
            {
               link.getRevSourceLink().removeYou();
            }

            // LAST cannot be FIRST simultaneously
            if (link.getRevTargetLink() != null
               && link.getRevTargetLink().getType() == UMLMultiLink.FIRST)
            {
               link.getRevTargetLink().removeYou();
            }
         }

         // type FIRST, LAST
         if (type == UMLMultiLink.FIRST)
         {
            link.setName (link.getName() + " {first}");
         }
         else if (type == UMLMultiLink.LAST)
         {
            link.setName (link.getName() + " {last}");
         }

         // create new multilink
         if (type == UMLMultiLink.FIRST)
         {
            sourceLink = null;
            targetLink = link;
         }
         else
         {
            // multiLinkType = UMLMultiLink.LAST
            sourceLink = link;
            targetLink = null;
         }

         multiLink = asgElement.getProject().getFromFactories (
            UMLMultiLink.class).create (true);
         multiLink.setSourceLink (sourceLink);
         multiLink.setTargetLink (targetLink);
         multiLink.setContainerObject (containerObject);
         multiLink.setType (type);
         multiLink.update();

         Iterator diags = link.iteratorOfDiagrams();
         if (diags != null)
         {
            UMLDiagram diag = null;
            while (diags.hasNext())
            {
               diag = (UMLDiagram) diags.next();
               if (diag.hasInElements (asgElement))
               {
                  diag.addToElements (multiLink);
               }
            }
         }
      } // if

      return multiLink;
   }

}

/*
 * $Log$
 * Revision 1.15  2006/03/29 09:51:14  fklar
 * adjusted comment-structure to common style:
 *  1. file-prefix 'The FUJABA ToolSuite project: ...'
 *  2. class-comment with 'Author, Revision, Date'
 *  3. file-postfix 'log'
 *
 * Revision 1.14  2006/03/01 14:27:12  lowende
 * Removed a lot of compile warnings.
 *
 */

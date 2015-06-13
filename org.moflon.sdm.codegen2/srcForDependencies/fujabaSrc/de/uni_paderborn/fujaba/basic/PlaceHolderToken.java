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
 *      Copyright (C) 1997-2004 Fujaba Development Group
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
 * Contact adress:
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

import de.upb.tools.fca.FHashMap;


/**
 * No comment provided by developer, please add a comment to improve documentation.
 *
 * @author    $Author$
 * @version   $Revision$
 */
public class PlaceHolderToken extends TemplateToken
{
   /**
    * Constructor for class PlaceHolderToken
    *
    * @param placeholder  No description provided
    */
   public PlaceHolderToken (String placeholder)
   {
      this.setToken (placeholder);
   }

   // Attributes

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   private String token = "";


   /**
    * Sets the token attribute of the PlaceHolderToken object
    *
    * @param token  The new token value
    * @return       No description provided
    */
   public String setToken (String token)
   {
      if ( (this.token == null) ||  (this.token != null && !this.token.equals (token)))
      {
         this.token = token;
      }

      return this.token;
   }


   /**
    * Get the token attribute of the PlaceHolderToken object
    *
    * @return   The token value
    */
   public String getToken()
   {
      return this.token;
   }


   // Methods

   /**
    * Get the sourceCode attribute of the PlaceHolderToken object
    *
    * @param parameter  No description provided
    * @return           The sourceCode value
    */
   @Override
   public String getSourceCode (FHashMap parameter)
   {
      Object tmp = parameter.get (this.getToken());
      String code =  (tmp == null ? null : tmp.toString());

      if (code == null)
      {
         code = ""; //this.getToken();
      }

      return code;
   }
}

/*
 * $Log$
 * Revision 1.12  2006/04/06 12:04:34  cschneid
 * added missing @Override everywhere
 *
 * Revision 1.11  2004/10/20 17:49:28  schneider
 * Introduction of interfaces for class diagram classes
 *
 */

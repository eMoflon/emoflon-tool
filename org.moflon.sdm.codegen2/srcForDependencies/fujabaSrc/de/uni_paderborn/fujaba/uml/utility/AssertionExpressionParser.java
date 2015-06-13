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

import de.uni_paderborn.fujaba.metamodel.common.FIncrement;
import de.uni_paderborn.fujaba.metamodel.structure.*;
import de.uni_paderborn.fujaba.uml.behavior.UMLActivityDiagram;
import de.uni_paderborn.fujaba.uml.behavior.UMLAttrExprPair;
import de.uni_paderborn.fujaba.uml.behavior.UMLObject;
import de.uni_paderborn.fujaba.uml.behavior.UMLStoryPattern;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * This is a first heuristic java expression parser. We should aim for a full-blown 
 * grammar-based parser.
 *  
 * @author    rotschke
 * @version   $Revision$ $Date$
 */
public class AssertionExpressionParser {
   /**
    * This method is intended for the code generator
    * @param myAttrExprPair
    * @return
    */
   public static String parse(UMLAttrExprPair myAttrExprPair) {
      return parse(myAttrExprPair, (UMLStoryPattern) myAttrExprPair.getParentElement().getParentElement());
   }
   
   /**
    * This method is intended for the assertion dialog, 
    * as the attr expr pair might not have been assigned to the story pattern yet.
    * @param myAttrExprPair
    * @param myStoryPattern
    * @return
    */
   public static String parse(UMLAttrExprPair myAttrExprPair, UMLStoryPattern myStoryPattern) {
      String expression = myAttrExprPair.getExpression();
      FAttr myAttr = myAttrExprPair.getInstanceOf();
      FType myAttrType = myAttr.getAttrType();
      if ((myAttrType != null) && ((FIncrement) myAttrType).hasKeyInStereotypes("enumeration")) {
         // Check whether the expression is a literal of the enumeration
         Iterator<FAttr> iterLiterals = (Iterator<FAttr>)((FClass)myAttrType).iteratorOfAttrs();
         while (iterLiterals.hasNext()) {            
            if (iterLiterals.next().getName().equals(expression)) {
               // Put the name of the Enumeration in front of the selected literal
               return myAttrType.getName() + "." + expression;                
            }
         }
      }      

      // Does the expression contain something looking like an attribute access?
      Pattern pattern = Pattern.compile("([a-z]([A-Za-z0-9_]*))[.]([a-z]([A-Za-z0-9_]*))(.*)");
      if ( expression == null ) expression = "";
      Matcher matcher = pattern.matcher(expression);
      if (!matcher.matches()) {
        // Pattern not recognized, just return what the user had entered
        return expression;
      }
      
      // Is the part in front of the dot the name of an object in the current story pattern?
      UMLActivityDiagram myActivityDiagram = myStoryPattern.getRevStoryPattern().getActivityDiagram();
      String frontPart = matcher.group(1);
      
      // Try "this" first, event if not part of the Storydiagram
      if (frontPart.equals("this")) {
         String result = parseRearPart(matcher, myActivityDiagram.getStoryMethod().getParent(), myAttrType);
         if (result != null) {
            return result;
         }
      }
      
      // Try explicit objects
      Iterator<UMLObject> iterObjects = myActivityDiagram.iteratorOfObjects();
      while (iterObjects.hasNext()) {
         UMLObject currentObject = iterObjects.next();
         if (frontPart.equals(currentObject.getObjectName())) {
            // The front part looks good, try the rear part after the dot
            String result = parseRearPart(matcher, currentObject.getInstanceOf(), myAttrType);
            if (result != null) {
               return result;
            }
         }
      }
      
      // Try parameters
      Iterator<FParam> iterParams = (Iterator<FParam>) myActivityDiagram.getStoryMethod().iteratorOfParam();
      while (iterParams.hasNext()) {
         FParam currentParam = iterParams.next();
         if (frontPart.equals(currentParam.getName()) && !(currentParam.getParamType() instanceof FBaseType)) {
            // The front part looks good, try the rear part after the dot
            String result = parseRearPart(matcher, (FClass) currentParam.getParamType(), myAttrType);
            if (result != null) {
               return result;
            }
         }
      }
      // If all else fails, return what the user had entered
      return expression;
   }

   private static String parseRearPart (Matcher matcher, FClass myClass, FType myAttrType) {
      String frontPart = matcher.group(1);                  
      String rearPart = matcher.group(3);
      Iterator<FAttr> iterAttrs = (Iterator<FAttr>) myClass.iteratorOfAllAttrs();
      while (iterAttrs.hasNext()) {
         FAttr currentAttr = iterAttrs.next();
         if ((rearPart.equals(currentAttr.getName())) && (currentAttr.getAttrType() == myAttrType)) {
            // The rear part looks good, first letter of rearPart must be upper case
            char[] letters = rearPart.toCharArray();
            letters[0] = Character.toUpperCase(letters[0]);
            rearPart = new String(letters);
            // Determine the right access method
            if ((currentAttr.getAttrType() != null) && (FBaseType.BOOLEAN.equals(currentAttr.getAttrType().getName()))) {
               return matcher.replaceFirst(frontPart + ".is" + rearPart + "() " + matcher.group(5));
            }
            else {
               return matcher.replaceFirst(frontPart + ".get" + rearPart + "() " + matcher.group(5));
            }
         }
      }
      // The rear part doesn't match, just return what the user had entered
      return null;

   }
}

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
package de.uni_paderborn.fujaba.asg.utility;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.apache.log4j.Logger;

import de.uni_paderborn.fujaba.asg.ASGDiagram;
import de.uni_paderborn.fujaba.asg.ASGElement;
import de.uni_paderborn.fujaba.asg.ASGInformation;
import de.uni_paderborn.fujaba.asg.ASGUnparseInformation;


/**
 * @author    Dietrich Travkin (travkin)
 * @author    Last editor: $Author$
 * @version   $Revision$ $Date$
 */
public class ASGInformationHelper
{
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String DIMENSION_KEY = "dimension";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String LOCATION_KEY = "entry";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String LOCATION_X_KEY = "location_X";
   /**
    * No comment provided by developer, please add a comment to improve documentation.
    */
   public final static String LOCATION_Y_KEY = "location_Y";


   /**
    * Get the constraint attribute of the ASGInformationHelper class
    *
    * @param element        No description provided
    * @param constraintKey  No description provided
    * @return               The constraint value
    */
   public static Rectangle getConstraint (ASGElement element, ASGElement constraintKey)
   {
      ASGUnparseInformation asgUnparseInformation = getOrCreateUnparseInformation (element, constraintKey);

      ASGInformation asgInfoDimension = getOrCreateDimensionInformation (asgUnparseInformation);
      Dimension dimension = getDimension (asgInfoDimension);

      ASGInformation asgInfoLocation = getOrCreateLocationInformation (asgUnparseInformation);
      Point location = getLocation (asgInfoLocation);

      if (dimension == null || location == null)
      {
         return null;
      }

      return new Rectangle (location, dimension);
   }


   /**
    * Sets the constraint attribute of the ASGInformationHelper class
    *
    * @param element        The new constraint value
    * @param constraint     The new constraint value
    * @param constraintKey  The new constraint value
    */
   public static void setConstraint (ASGElement element, Rectangle constraint, ASGElement constraintKey)
   {
      ASGUnparseInformation asgUnparseInformation = getOrCreateUnparseInformation (element, constraintKey);

      ASGInformation asgDimensionInfo = getOrCreateDimensionInformation (asgUnparseInformation);
      setDimension (asgDimensionInfo, constraint.width, constraint.height);

      ASGInformation asgLocationInfo = getOrCreateLocationInformation (asgUnparseInformation);
      setLocation (asgLocationInfo, constraint.x, constraint.y);
   }


   /**
    * Get the orCreateUnparseInformation attribute of the ASGInformationHelper class
    *
    * @param element  No description provided
    * @param key      No description provided
    * @return         The orCreateUnparseInformation value
    */
   public static ASGUnparseInformation getOrCreateUnparseInformation (ASGElement element, ASGElement key)
   {
      ASGUnparseInformation asgUnparseInformation = element.getFromUnparseInformations (key);
      if (asgUnparseInformation == null)
      {
         asgUnparseInformation = new ASGUnparseInformation (element.getProject(), true);
         element.addToUnparseInformations (key, asgUnparseInformation);
      }
      return asgUnparseInformation;
   }


   /**
    * Get the orCreateDimensionInformation attribute of the ASGInformationHelper class
    *
    * @param element  No description provided
    * @param key      No description provided
    * @return         The orCreateDimensionInformation value
    */
   public static ASGInformation getOrCreateDimensionInformation (ASGElement element, ASGElement key)
   {
      ASGUnparseInformation asgUnparseInformation = getOrCreateUnparseInformation (element, key);
      return getOrCreateDimensionInformation (asgUnparseInformation);
   }


   /**
    * Get the orCreateLocationInformation attribute of the ASGInformationHelper class
    *
    * @param element  No description provided
    * @param key      No description provided
    * @return         The orCreateLocationInformation value
    */
   public static ASGInformation getOrCreateLocationInformation (ASGElement element, ASGElement key)
   {
      ASGUnparseInformation asgUnparseInformation = getOrCreateUnparseInformation (element, key);
      return getOrCreateLocationInformation (asgUnparseInformation);
   }


   /**
    * Get the orCreateDimensionInformation attribute of the ASGInformationHelper class
    *
    * @param asgUnparseInformation  No description provided
    * @return                       The orCreateDimensionInformation value
    */
   private static ASGInformation getOrCreateDimensionInformation (ASGUnparseInformation asgUnparseInformation)
   {
      ASGInformation asgDimensionInfo = asgUnparseInformation.getFromASGInformation (DIMENSION_KEY);
      if (asgDimensionInfo == null)
      {
         asgDimensionInfo = new ASGInformation (asgUnparseInformation.getProject(), true);
         asgUnparseInformation.addToASGInformation (DIMENSION_KEY, asgDimensionInfo);
      }
      return asgDimensionInfo;
   }


   /**
    * Get the orCreateLocationInformation attribute of the ASGInformationHelper class
    *
    * @param asgUnparseInformation  No description provided
    * @return                       The orCreateLocationInformation value
    */
   private static ASGInformation getOrCreateLocationInformation (ASGUnparseInformation asgUnparseInformation)
   {
      ASGInformation asgLocationInfo = asgUnparseInformation.getFromASGInformation (LOCATION_KEY);
      if (asgLocationInfo == null)
      {
         asgLocationInfo = new ASGInformation (asgUnparseInformation.getProject(), true);
         asgUnparseInformation.addToASGInformation (LOCATION_KEY, asgLocationInfo);
      }
      return asgLocationInfo;
   }


   /**
    * Get the orCreateDimension attribute of the ASGInformationHelper class
    *
    * @param asgDimensionInfo  No description provided
    * @param defaultWidth      No description provided
    * @param defaultHeight     No description provided
    * @return                  The orCreateDimension value
    */
   public static Dimension getOrCreateDimension (ASGInformation asgDimensionInfo, int defaultWidth, int defaultHeight)
   {
      Dimension dimension = getDimension (asgDimensionInfo);

      if (dimension == null)
      {
         setDimension (asgDimensionInfo, defaultWidth, defaultHeight);
         dimension = getDimension (asgDimensionInfo);
      }

      return dimension;
   }


   /**
    * Get the dimension attribute of the ASGInformationHelper class
    *
    * @param asgDimensionInfo  No description provided
    * @return                  The dimension value
    */
   public static Dimension getDimension (ASGInformation asgDimensionInfo)
   {
      String dimension = asgDimensionInfo.getFromInformation (DIMENSION_KEY);

      if (dimension != null)
      {
         String dimensions[] = dimension.split (",");
         if (dimensions.length != 2)
         {
            return null;
         }
         return new Dimension (Integer.valueOf (dimensions[0]).intValue(), Integer.valueOf (dimensions[1]).intValue());
      }

      return null;
   }
   
   /**
    * Returns the given elements dimension in the given diagram if there is any.
    * 
    * @param element the element whose location is required
    * @param diagram the diagram where the element is contained
    * 
    * @return the element's dimension in the diagram or <code>null</code>
    */
   public static Dimension getDimension(ASGElement element, ASGDiagram diagram)
   {
      if (element != null && diagram != null)
      {
         ASGUnparseInformation asgUnparseInformation = element.getFromUnparseInformations(diagram);
         if (asgUnparseInformation != null)
         {
            ASGInformation asgDimensionInfo = asgUnparseInformation.getFromASGInformation(DIMENSION_KEY);
            if (asgDimensionInfo != null)
            {
               Dimension dimension = ASGInformationHelper.getDimension(asgDimensionInfo);
               if (dimension != null)
               {
                  return dimension;
               }
            }
         }
      }
      return null;
   }


   /**
    * Sets the dimension attribute of the ASGInformationHelper class
    *
    * @param asgDimensionInfo  The new dimension value
    * @param width             The new dimension value
    * @param height            The new dimension value
    */
   public static void setDimension (ASGInformation asgDimensionInfo, int width, int height)
   {
      asgDimensionInfo.addToInformation (DIMENSION_KEY, width + "," + height);
   }


   /**
    * Get the orCreateLocation attribute of the ASGInformationHelper class
    *
    * @param asgLocationInfo  No description provided
    * @param defaultX         No description provided
    * @param defaultY         No description provided
    * @return                 The orCreateLocation value
    */
   public static Point getOrCreateLocation (ASGInformation asgLocationInfo, int defaultX, int defaultY)
   {
      Point location = getLocation (asgLocationInfo);

      if (location == null)
      {
         setLocation (asgLocationInfo, defaultX, defaultY);
         location = getLocation (asgLocationInfo);
      }

      return location;
   }


   /**
    * Get the location attribute of the ASGInformationHelper class
    *
    * @param asgLocationInfo  No description provided
    * @return                 The location value
    */
   public static Point getLocation(ASGInformation asgLocationInfo)
   {
      String xString = asgLocationInfo.getFromInformation (LOCATION_X_KEY);
      String yString = asgLocationInfo.getFromInformation (LOCATION_Y_KEY);

      if (xString == null || yString == null)
      {
         return null;
      }

      return new Point (Integer.valueOf (xString).intValue(), Integer.valueOf (yString).intValue());
   }
   
   /**
    * Returns the given elements location in the given diagram if there is any.
    * 
    * @param element the element whose location is required
    * @param diagram the diagram where the element is contained
    * 
    * @return the element's location in the diagram or <code>null</code>
    */
   public static Point getLocation(ASGElement element, ASGDiagram diagram)
   {
      if (element != null && diagram != null)
      {
         ASGUnparseInformation asgUnparseInformation = element.getFromUnparseInformations(diagram);
         if (asgUnparseInformation != null)
         {
            ASGInformation asgLocationInfo = asgUnparseInformation.getFromASGInformation(LOCATION_KEY);
            if (asgLocationInfo != null)
            {
               Point location = ASGInformationHelper.getLocation(asgLocationInfo);
               if (location != null)
               {
                  return location;
               }
            }
         }
      }
      return null;
   }


   public static void setLocation(ASGDiagram diag, ASGElement item, int x, int y)
   {
      ASGInformation asgInform = ASGInformationHelper.getOrCreateLocationInformation (item, diag);
      ASGInformationHelper.setLocation (asgInform, x, y);
   }


   /**
    * Sets the location attribute of the ASGInformationHelper class
    *
    * @param asgLocationInfo  The new location value
    * @param x                The new location value
    * @param y                The new location value
    */
   public static void setLocation (ASGInformation asgLocationInfo, int x, int y)
   {
      asgLocationInfo.addToInformation (LOCATION_X_KEY, String.valueOf (x));
      asgLocationInfo.addToInformation (LOCATION_Y_KEY, String.valueOf (y));
   }


   public static void copyLocation (ASGElement source, ASGElement dest)
   {
      ASGDiagram sourceDiag = (ASGDiagram) source.getFirstFromDiagrams();
      ASGDiagram destDiag = (ASGDiagram) dest.getFirstFromDiagrams();

      copyLocation (source, sourceDiag, dest, destDiag);
   }


   public static void copyLocation (ASGElement source, ASGDiagram sourceDiag, ASGElement dest, ASGDiagram destDiag)
   {
      if (source == null || sourceDiag == null || dest == null || destDiag == null)
      {
         Logger.getLogger (ASGInformationHelper.class).error ("Can't copy location: at least one parameter is null [source=" + source + ", sourceDiag=" + sourceDiag + ", dest=" + dest + ", destDiag=" + destDiag + "]");
         return;
      }
      ASGInformation sourceLocationInfo = getOrCreateLocationInformation (source, sourceDiag);
      Point pos = getLocation (sourceLocationInfo);

      ASGInformation destLocationInfo = getOrCreateLocationInformation (dest, destDiag);
      setLocation (destLocationInfo, pos.x, pos.y);
   }
}

/*
 * $Log$
 * Revision 1.2  2007/04/02 13:18:23  fklar
 * + reviewed method 'copyLocation(ASGElement, ASGElement)': set location of destination object in first diagram of the destination object not in the first diagram of the source object
 * + new method 'copyLocation(ASGElement, ASGDiagram, ASGElement, ASGDiagram)': allow specifying diagrams in which location should be set
 *
 */

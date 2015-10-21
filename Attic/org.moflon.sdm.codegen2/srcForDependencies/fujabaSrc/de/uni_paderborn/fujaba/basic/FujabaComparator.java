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


import java.util.Comparator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.structure.FArray;
import de.uni_paderborn.fujaba.metamodel.structure.FBaseType;
import de.uni_paderborn.fujaba.metamodel.structure.FClass;
import de.uni_paderborn.fujaba.metamodel.structure.FPackage;
import de.uni_paderborn.fujaba.metamodel.structure.FType;


/**
 * @author $Author$
 * @version $Revision$
 */
public class FujabaComparator
{
   static Logger logger = Logger.getLogger(FujabaComparator.class);

   final static Comparator lessString;

   static
   {
      lessString = createLessString();
   }


   public static Comparator getLessString()
   {
      return lessString;
   }


   private static Comparator createLessString()
   {
      return new Comparator()
      {
         /**
          * @see java.lang.Object#equals(java.lang.Object)
          */
         @Override
         public boolean equals(Object obj)
         {
            return this.compare(this, obj) == 0;
         }


         public int compare(Object o1, Object o2)
         {
            int result = 0;
            if (o1 == null)
            {
               if (o2 != null)
               {
                  result = -1;
               }
            }
            else if (o2 == null)
            {
               result = 1;
            }
            else
            {
               result = ((String) o1).compareTo((String) o2);
            }
            return result;
         }
      };
   }


   public static Comparator getLessType()
   {
      return new Comparator()
      {
         public final int compare(final Object o1, final Object o2)
         {
            final FType t1 = (FType) o1;
            final FType t2 = (FType) o2;
            int result = 0;
            if (t1 == null)
            {
               if (t2 != null)
               {
                  result = -1;
               }
            }
            else if (t2 == null)
            {
               result = 1;
            }
            else if (t1 instanceof FBaseType)
            {
               if (t2 instanceof FBaseType)
               {
                  result = lessString.compare(t1.getName(), t2.getName());
               }
               else
               {
                  result = -1;
               }
            }
            else if (t2 instanceof FBaseType)
            {
               result = 1;
            }
            else if (t1 instanceof FClass)
            {
               if (t2 instanceof FClass)
               {
                  result = lessString.compare(t1.getName(), t2.getName());
                  if (result == 0)
                  {
                     final FClass c1 = (FClass) t1;
                     final FClass c2 = (FClass) t2;

                     result = lessString.compare(c1.getFullClassName(), c2
                           .getFullClassName());
                  }
               }
               else
               { // t2 is an instance of UMLArray

                  result = -1; // UMLClass is less than UMLArray
               }
            }
            else if (t2 instanceof FClass)
            { // t1 is an instance of UMLArray

               // UMLClass is less than UMLArray
               result = 1;
            }
            else if (t1 instanceof FArray)
            {
               if (t2 instanceof FArray)
               {
                  this.compare(((FArray) t1).getBaseType(), ((FArray) t2)
                        .getBaseType());
               }
               else
               {
                  result = 1;
               }
            }
            else if (t2 instanceof FArray)
            {
               result = -1;
            }
            else
            {
               // this case shouldn't occure!!!!
               // only FBaseType, FClass and FArray are
               // classes derived from FType
               result = lessString.compare(t1.getName(), t2.getName());

               if (logger.isEnabledFor(Level.WARN))
               {
                  logger
                        .warn("unknown implementations of interface 'FType': t1 '"
                              + t1.getClass().getName()
                              + "', t2 '"
                              + t2.getClass().getName() + "'.");
               }
            }
            return result;
         }
      };
   }


   public static Comparator getLessClassName()
   {
      return new Comparator()
      {

         /**
          * @see java.lang.Object#equals(java.lang.Object)
          */
         @Override
         public boolean equals(Object obj)
         {
            return this.compare(this, obj) == 0;
         }


         public int compare(Object o1, Object o2)
         {
            return o1.getClass().getName().compareTo(o2.getClass().getName());
         }
      };
   }


   public static Comparator getLessBasicIncr()
   {
      return new Comparator()
      {
         /**
          * @see java.lang.Object#equals(java.lang.Object)
          */
         @Override
         public boolean equals(Object obj)
         {
            return this.compare(this, obj) == 0;
         }


         public int compare(Object o1, Object o2)
         {
            return ((BasicIncrement) o1).getID().compareTo(
                  ((BasicIncrement) o2).getID());
         }
      };
   }


   public static Comparator getPackageComparator()
   {
      return new Comparator()
      {
         /**
          * @see java.lang.Object#equals(java.lang.Object)
          */
         @Override
         public boolean equals(Object obj)
         {
            return this.compare(this, obj) == 0;
         }


         public int compare(Object o1, Object o2)
         {
            return ((FPackage) o1).getFullPackageName().compareTo(
                  ((FPackage) o2).getFullPackageName());
         }
      };
   }
   
   /**
    * Returns a comparator that compares two FElements by their names that
    * are retrieved by FElement's getName() method.
    * @return
    */
   public static Comparator<FElement> getElementNameComparator()
   {
      return new Comparator<FElement>()
      {
         public int compare(FElement o1, FElement o2)
         {
            return o1.getName().compareTo(o2.getName());
         }
      };
   }

}

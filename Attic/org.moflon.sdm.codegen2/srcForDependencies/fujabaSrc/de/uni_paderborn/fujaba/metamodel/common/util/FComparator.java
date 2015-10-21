package de.uni_paderborn.fujaba.metamodel.common.util;

import java.util.Comparator;

import de.uni_paderborn.fujaba.metamodel.common.FElement;

/**
 * 
 * @author  klar
 * @author	(last editor) $Author$
 * @version	$Revision$ $Date$
 */
public class FComparator
{
   final static Comparator<FElement> lessName;
   
   static
   {
      lessName = createLessName();
   }
   
   public static Comparator<FElement> getLessName()
   {
      return lessName;
   }
   
   private static Comparator<FElement> createLessName()
   {
      return new Comparator<FElement>()
      {
         /**
          * Compares its two FElements for ordering by name.
          * 
          * @param o1 the first element to be compared.
          * @param o2 the second element to be compared.
          * @return a negative integer, zero, or a positive integer as the first argument is less than,
          *         equal to, or greater than the second.
          */
         public final int compare(FElement e1, FElement e2)
         {
            if (e1.getName() != null)
            {
               return e1.getName().compareTo(e2.getName());
            }
            else if (e2.getName() != null)
            {
               return -1;
            }
            else
            {
               return 0;
            }
         }
      };
   }
}

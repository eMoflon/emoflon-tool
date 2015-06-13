package de.uni_paderborn.fujaba.basic;

import java.util.Comparator;

/**
 * Function that compares objects by comparing the result of their toString-methods lexicographically.
 *   
 * @author klar
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 */
public class ToStringComparator implements Comparator<Object>
{
   private static ToStringComparator singleton;
   
   public static ToStringComparator get()
   {
      if (singleton == null)
      {
         singleton = new ToStringComparator();
      }
      return singleton;
   }
   
   private ToStringComparator()
   {
   }

   public int compare(Object o1, Object o2)
   {
      String text1 = o1.toString();
      String text2 = o2.toString();
      if (text1 != null)
      {
         if (text2 != null)
         {
            return text1.compareTo(text2);
         } else
         {
            // text1 != null && text2 == null
            return 1;
         }
      } else if (text2 != null)
      {
         // text1 == null && text2 != null
         return -1;
      } else
      {
         // text1 == null && text2 == null
         return 0;
      }
   }
}
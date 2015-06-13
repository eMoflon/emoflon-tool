package de.uni_paderborn.tools.util;

import java.util.Comparator;

import de.uni_paderborn.fujaba.metamodel.structure.FClass;

public class ClassComparator implements Comparator<FClass>
{
   public int compare (FClass o1, FClass o2)
   {
      if (o1==o2)
      {
         return 0;
      }
      if (o1==null || o1.getName() == null)
      {
         return -1;
      }
      if (o2==null || o2.getName() == null)
      {
         return 1;
      }
      int compareName = o1.getName().compareTo(o2.getName());
      if (compareName==0)
      {
         compareName = o1.getFullClassName().compareTo(o2.getFullClassName());
      }
      return compareName;
   }
}
package de.fujaba.codegen;

import de.uni_paderborn.fujaba.metamodel.common.FElement;
import de.uni_paderborn.fujaba.metamodel.common.FProject;

import java.util.ArrayList;
import java.util.List;

public class CodeLookupManager
{
   private static CodeLookupManager instance;

   private CodeLookupManager()
   {
      lookups = new ArrayList<CodeLookup>();
   }

   public static CodeLookupManager get()
   {
      if (instance == null)
      {
         instance = new CodeLookupManager();
      }
      return instance;
   }

   private List<CodeLookup> lookups;

   public void addLookup (CodeLookup lookup)
   {
      lookups.add(lookup);
   }

   public FElement getElementForLine(String file, int lineNr, FProject project)
   {
      if (file.endsWith(".java"))
      {
         file = file.substring(0, file.length() - 5);
      }
      List<FElement> elems = new ArrayList<FElement>();
      for (CodeLookup lookup : lookups)
      {
         FElement elem = lookup.getElementForLine(file, lineNr, project);
         if (elem != null)
         {
            elems.add(elem);
         }
      }
      if (elems.size() >= 1)
      {
         if (elems.size() > 1)
         {
            System.err.println("More then one element should be responsible for this line???");
         }
         return elems.get(0);
      }
      return null;
   }

   public static interface CodeLookup
   {
      public FElement getElementForLine(String file, int lineNr, FProject project);
   }
}

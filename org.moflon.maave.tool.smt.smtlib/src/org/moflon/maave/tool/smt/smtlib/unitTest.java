package org.moflon.maave.tool.smt.smtlib;

public class unitTest
{

   public static void main(String[] args)
   {
      SMTLib lib=SmtLibHelper.getSMTLib();
      for (PredicateSpec preSpec : lib.getPredicateSpec())
      {
         System.out.println(preSpec.getSMTDeclaration());
      }
      
      for (Sort sort : lib.getSorts())
      {
        System.out.println(sort.getSMTVariableDeclaration("c"));
      }
   }

}

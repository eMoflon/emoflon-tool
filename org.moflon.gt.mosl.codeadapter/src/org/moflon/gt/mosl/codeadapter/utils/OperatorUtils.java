package org.moflon.gt.mosl.codeadapter.utils;

import org.moflon.core.utilities.UtilityClassNotInstantiableException;
import org.moflon.gt.mosl.moslgt.Operator;

public class OperatorUtils
{
   private OperatorUtils()
   {
      throw new UtilityClassNotInstantiableException();
   }

   /**
    * Returns true if the given {@link Operator} has 'green' (=create) semantics
    */
   public static boolean isCreated(final Operator op)
   {
      return op != null && "++".equals(op.getValue());
   }

   /**
    * Returns true if the given {@link Operator} has 'red' (=delete) semantics
    */
   public static boolean isDestroyed(final Operator op)
   {
      return op != null && "--".equals(op.getValue());
   }

   /**
    * Returns true if the given {@link Operator} has 'black' semantics
    */
   public static boolean isCheckOnly(final Operator op)
   {
      return op == null || op.getValue() == null || "".equals(op.getValue());
   }

}

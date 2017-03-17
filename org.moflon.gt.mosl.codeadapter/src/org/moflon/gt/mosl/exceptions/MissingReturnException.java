package org.moflon.gt.mosl.exceptions;

import org.moflon.gt.mosl.moslgt.MethodDec;

public class MissingReturnException extends RuntimeException
{

   private static final long serialVersionUID = 5354653250089539943L;

   public MissingReturnException()
   {
      super("No Return value is set");
   }

   public MissingReturnException(MethodDec method)
   {
      super("For the method '" + method.getName() + "' is no return value set");
   }

}

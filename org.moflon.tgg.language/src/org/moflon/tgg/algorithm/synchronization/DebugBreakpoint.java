package org.moflon.tgg.algorithm.synchronization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE, ElementType.PARAMETER,
      ElementType.TYPE, ElementType.TYPE_USE })
public @interface DebugBreakpoint {
   public enum Phase {
      INIT, ADD, DELETE, DELETE_BEFORE, TRANSLATION_START, TRANSLATION_STEP, TRANSLATION_END
   };

   Phase phase();
}
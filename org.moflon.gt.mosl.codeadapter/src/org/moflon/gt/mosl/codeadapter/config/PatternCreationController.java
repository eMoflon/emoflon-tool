package org.moflon.gt.mosl.codeadapter.config;

import org.moflon.gt.mosl.codeadapter.PatternNameGenerator;

public class PatternCreationController
{
   private PatternNameGenerator patternNameGenerator;

   public PatternCreationController()
   {
      this.patternNameGenerator = new PatternNameGenerator();
   }

   public PatternNameGenerator getPatternNameGenerator()
   {
      return this.patternNameGenerator;
   }
}

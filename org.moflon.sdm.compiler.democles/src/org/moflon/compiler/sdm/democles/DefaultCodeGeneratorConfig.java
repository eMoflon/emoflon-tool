package org.moflon.compiler.sdm.democles;

import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.utilities.preferences.EMoflonPreferencesStorage;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;

public class DefaultCodeGeneratorConfig extends DefaultValidatorConfig
{
   // Start of pattern types
   public static final String BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR = "BindingAndBlackPatternMatcherGenerator";

   public static final String BINDING_PATTERN_MATCHER_GENERATOR = "BindingPatternMatcherGenerator";

   public static final String BLACK_PATTERN_MATCHER_GENERATOR = "BlackPatternMatcherGenerator";

   public static final String RED_PATTERN_MATCHER_GENERATOR = "RedPatternMatcherGenerator";

   public static final String GREEN_PATTERN_MATCHER_GENERATOR = "GreenPatternMatcherGenerator";

   public static final String EXPRESSION_PATTERN_MATCHER_GENERATOR = "ExpressionPatternMatcherGenerator";

   // End of pattern types
   private final EMoflonPreferencesStorage preferencesStorage;

   private class GenConfigFactory
   {

      private PatternMatcher getConfig(String generator, final Resource resource)
      {
         final RegularPatternMatcherGenerator regularPatternMatcherGenerator = new RegularPatternMatcherGenerator(getPatternMatcherCompiler(generator, resource),
               generator, preferencesStorage);
         resource.getContents().add(regularPatternMatcherGenerator);
         return regularPatternMatcherGenerator;
      }

      private PatternMatcherCompiler getPatternMatcherCompiler(final String generatorType, final Resource resource)
      {
         switch (generatorType)
         {
         case BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR:
            return configureBindingAndBlackPatternMatcherCompiler(resource);
         case BINDING_PATTERN_MATCHER_GENERATOR:
            return configureBindingPatternMatcherCompiler(resource);
         case BLACK_PATTERN_MATCHER_GENERATOR:
            return configureBlackPatternMatcherCompiler(resource);
         case RED_PATTERN_MATCHER_GENERATOR:
            return configureRedPatternMatcherCompiler(resource);
         case GREEN_PATTERN_MATCHER_GENERATOR:
            return configureGreenPatternMatcherCompiler(resource);
         case EXPRESSION_PATTERN_MATCHER_GENERATOR:
            return configureExpressionPatternMatcherCompiler(resource);
         default:
            return null;
         }
      }

   }

   private GenConfigFactory genConfigFactory;

   public DefaultCodeGeneratorConfig(ResourceSet resourceSet, final IResource resourceContext)
   {
      super(resourceSet);
      this.genConfigFactory = new GenConfigFactory();
      this.preferencesStorage = EMoflonPreferencesStorage.getInstance();
   }

   @Override
   protected PatternMatcher configureBindingAndBlackPatternMatcher(final Resource resource) throws IOException
   {
      return genConfigFactory.getConfig(BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR, resource);
   }

   @Override
   protected PatternMatcher configureBindingPatternMatcher(final Resource resource) throws IOException
   {
      return genConfigFactory.getConfig(BINDING_PATTERN_MATCHER_GENERATOR, resource);
   }

   @Override
   protected PatternMatcher configureBlackPatternMatcher(final Resource resource) throws IOException
   {
      return genConfigFactory.getConfig(BLACK_PATTERN_MATCHER_GENERATOR, resource);
   }

   @Override
   protected PatternMatcher configureRedPatternMatcher(final Resource resource) throws IOException
   {
      return genConfigFactory.getConfig(RED_PATTERN_MATCHER_GENERATOR, resource);
   }

   @Override
   protected PatternMatcher configureGreenPatternMatcher(final Resource resource) throws IOException
   {
      return genConfigFactory.getConfig(GREEN_PATTERN_MATCHER_GENERATOR, resource);
   }

   @Override
   protected PatternMatcher configureExpressionPatternMatcher(final Resource resource) throws IOException
   {
      return genConfigFactory.getConfig(EXPRESSION_PATTERN_MATCHER_GENERATOR, resource);
   }
}

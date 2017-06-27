package org.moflon.compiler.sdm.democles;

import java.io.IOException;
import java.util.Arrays;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.preferences.EMoflonPreferencesStorage;
import org.moflon.sdm.compiler.democles.validation.scope.PatternMatcher;


public class DefaultCodeGeneratorConfig extends DefaultValidatorConfig {
   // Start of pattern types
	public static final String BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR = "BindingAndBlackPatternMatcherGenerator";
	public static final String BINDING_PATTERN_MATCHER_GENERATOR = "BindingPatternMatcherGenerator";
	public static final String BLACK_PATTERN_MATCHER_GENERATOR = "BlackPatternMatcherGenerator";
	public static final String RED_PATTERN_MATCHER_GENERATOR = "RedPatternMatcherGenerator";
	public static final String GREEN_PATTERN_MATCHER_GENERATOR = "GreenPatternMatcherGenerator";
	public static final String EXPRESSION_PATTERN_MATCHER_GENERATOR = "ExpressionPatternMatcherGenerator";
   // End of pattern types
   private final EMoflonPreferencesStorage preferencesStorage;
   
   private class MOSLGTDefaultCodeGeneratorConfig extends MOSLGTDefaultValidatorConfig{

      public MOSLGTDefaultCodeGeneratorConfig(ResourceSet resourceSet)
      {
         super(resourceSet);
      }
      
   }

   private class GenConfigFactory{
      
      private GenConfigFactory(boolean _isMoslGT){
         isMoslGT = _isMoslGT;
      }
      
      private PatternMatcher getConfig(String generator, final Resource resource){
         if(isMoslGT){
            final RegularPatternMatcherGenerator regularPatternMatcherGenerator =
                  new RegularPatternMatcherGenerator(getMoslCompiler(generator, resource), generator, preferencesStorage);
            resource.getContents().add(regularPatternMatcherGenerator);
            return regularPatternMatcherGenerator;
         }
         else{
            final RegularPatternMatcherGenerator regularPatternMatcherGenerator =
                  new RegularPatternMatcherGenerator(getNotMoslCompiler(generator, resource), generator, preferencesStorage);
            resource.getContents().add(regularPatternMatcherGenerator);
            return regularPatternMatcherGenerator;
         }
      }
      
      private PatternMatcherCompiler getMoslCompiler(String generator, final Resource resource)
      {
         switch (generator)
         {
         case BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR:
            return moslGeneratorDefaultCodeGeneratorConfig.configureBindingAndBlackPatternMatcherCompiler(resource);
         case BINDING_PATTERN_MATCHER_GENERATOR:
            return moslGeneratorDefaultCodeGeneratorConfig.configureBindingPatternMatcherCompiler(resource);
         case BLACK_PATTERN_MATCHER_GENERATOR:
            return moslGeneratorDefaultCodeGeneratorConfig.configureBlackPatternMatcherCompiler(resource);
         case RED_PATTERN_MATCHER_GENERATOR:
            return moslGeneratorDefaultCodeGeneratorConfig.configureRedPatternMatcherCompiler(resource);
         case GREEN_PATTERN_MATCHER_GENERATOR:
            return moslGeneratorDefaultCodeGeneratorConfig.configureGreenPatternMatcherCompiler(resource);
         case EXPRESSION_PATTERN_MATCHER_GENERATOR:
            return moslGeneratorDefaultCodeGeneratorConfig.configureExpressionPatternMatcherCompiler(resource);
         default:
            return null;
         }
      }
      
      private PatternMatcherCompiler getNotMoslCompiler(String generator, final Resource resource)
      {
         switch (generator)
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
   
   private MOSLGTDefaultCodeGeneratorConfig moslGeneratorDefaultCodeGeneratorConfig;
   
   private static boolean isMoslGT;
   
   public static boolean isMoslGT(){
      return isMoslGT;
   }
   
	public DefaultCodeGeneratorConfig(ResourceSet resourceSet, final IResource resourceContext) {
		super(resourceSet);
		moslGeneratorDefaultCodeGeneratorConfig = new MOSLGTDefaultCodeGeneratorConfig(resourceSet);
		boolean isMoslGT;
		try
      {
		   IProject project = resourceContext.getProject();
         IProjectDescription ipd =project.getDescription();
         isMoslGT = Arrays.asList(ipd.getNatureIds()).stream().filter(ids -> ids.compareTo(WorkspaceHelper.MOSL_GT_NATURE_ID) == 0).findFirst().isPresent();
         
      } catch (CoreException | NullPointerException e)
      {
         isMoslGT = false;
      }
		genConfigFactory = new GenConfigFactory(isMoslGT);
		this.preferencesStorage = EMoflonPreferencesStorage.getInstance();
	}

	@Override
	protected PatternMatcher configureBindingAndBlackPatternMatcher(final Resource resource) throws IOException {
		return genConfigFactory.getConfig(BINDING_AND_BLACK_PATTERN_MATCHER_GENERATOR, resource);
	}

   @Override
	protected PatternMatcher configureBindingPatternMatcher(final Resource resource) throws IOException {
      return genConfigFactory.getConfig(BINDING_PATTERN_MATCHER_GENERATOR, resource);
	}

   @Override
	protected PatternMatcher configureBlackPatternMatcher(final Resource resource) throws IOException {
      return genConfigFactory.getConfig(BLACK_PATTERN_MATCHER_GENERATOR, resource);
	}

   @Override
	protected PatternMatcher configureRedPatternMatcher(final Resource resource) throws IOException {
      return genConfigFactory.getConfig(RED_PATTERN_MATCHER_GENERATOR, resource);
   }

   @Override
	protected PatternMatcher configureGreenPatternMatcher(final Resource resource) throws IOException {
      return genConfigFactory.getConfig(GREEN_PATTERN_MATCHER_GENERATOR, resource);
	}

   @Override
	protected PatternMatcher configureExpressionPatternMatcher(final Resource resource) throws IOException {
      return genConfigFactory.getConfig(EXPRESSION_PATTERN_MATCHER_GENERATOR, resource);
	}
}
